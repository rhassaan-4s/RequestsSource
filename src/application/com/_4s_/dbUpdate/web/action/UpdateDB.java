package com._4s_.dbUpdate.web.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.common.model.Flag;
import com._4s_.common.model.LastSequence;
import com._4s_.common.model.Settings;
import com._4s_.common.service.CommonManager;

public class UpdateDB implements Controller {
	
	private final Log log = LogFactory.getLog(getClass());
	
		
		
		CommonManager comMger=null;
		
		private DataSource dataSource;
		
		
		public CommonManager getComMger() {
			return comMger;
		}

		public void setComMger(CommonManager comMger) {
			this.comMger = comMger;
		}
		
		public DataSource getDataSource() {
			return dataSource;
		}
		public void setDataSource(DataSource dataSource) {
			this.dataSource = dataSource;
		}

		public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String fileName = "";
			Settings settings = (Settings)comMger.getObject(Settings.class, new Long(1));
			String contextPath = request.getSession().getServletContext().getRealPath("/");
			log.debug(">>>>>>>>>>>>>>>>>>>>contextPath "+contextPath);
//			if (settings.getSqlServerConnectionEnabled()) {
//				fileName=contextPath+"/dbUpdates/ERPDBUpdateSQL.xml";
//			} else {
			fileName=contextPath+"/dbUpdates/ERPDBUpdate.xml";
//			}
			String absolutePath= request.getRealPath("/");
			File witeTo=new File(contextPath+"/dbUpdates/ERPDBUpdateLogs.txt");
			FileWriter witer=new FileWriter(witeTo);
			BufferedWriter wirter=new BufferedWriter(witer);
			
	        SAXBuilder builder = new SAXBuilder();
	        Document doc = builder.build(new File(fileName));
	        Element webapp = doc.getRootElement();
            List lst=webapp.getChildren();
            Long lastDesiredIndex = new Long(((Element) lst.get(lst.size() -1 )).getChild("sqlindex").getText());

            JdbcTemplate jt = new JdbcTemplate(dataSource);

            LastSequence seq= comMger.getSequenceByClassName("QueryIndex");
            Long oldIndex=seq.getClassSequence();
            log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>oldIndex " +oldIndex);
            boolean noErrors=true;
            String blockIndex="";
            String queryBlock="";
            String qry ="";
            int totalNumber=0;
            Long currentIndex = new Long(0);
            
            Iterator itr=lst.iterator();
            while(itr.hasNext()){ // for all blocks
        		Element block=(Element)itr.next();
        		blockIndex = block.getChild("sqlindex").getText();
        		
        		currentIndex =new Long(blockIndex);
             
        		if(currentIndex>oldIndex){ // new Blocks only
        			queryBlock=block.getChild("statment").getText();
        			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Query IS "+queryBlock);
        			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>blockIndex "+blockIndex);
        			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>before executing");
        			totalNumber++;
        			StringTokenizer tokenizer = new StringTokenizer(queryBlock, ";");
        			
        			
//        			jt.execute("start transaction");
        			
        			if (settings.getSqlServerConnectionEnabled()) {
        				jt.execute("begin transaction");
        			}
        			
        			while (tokenizer.hasMoreTokens()) { //for all statements in the Block
        				qry = tokenizer.nextToken();
        				qry = StringUtils.trim(qry);
        				try{
        					if (qry != null && qry.length() != 0){
        						if (settings.getSqlServerConnectionEnabled()) {
        							qry = convertOracleToSqlScript(qry,settings);
        						}
        						jt.execute(qry);
        					}
        				}catch (Exception ec) {
                    		noErrors=false;
//                    		TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
            	        	String BadLine="Error in line" + "\n"+qry+"   with index : "+blockIndex+"\n";
            				witer.append(BadLine+"\n \t"+ec.getCause()+"\n\n\n\n\n the block:\n"+queryBlock);
            				break; // stop this block
            	       }
        			}
        			
    				if(noErrors) {
    					String updateSQL="update  common_last_sequence set classSequence="+blockIndex+" where className='QueryIndex'";
    					jt.execute(updateSQL);
    					jt.execute("commit");
    				}else{
    					jt.execute("rollback");
    					currentIndex--;
    					break; // this will terminate the run of the script
    				}

        		}
            }
			witer.close();
			HashMap model=new HashMap();
//			model.put("error",errorNumber);
			model.put("total",totalNumber);

			model.put("lastDesiredIndex" , lastDesiredIndex);
			model.put("oldIndex" , oldIndex);
			model.put("currentIndex" ,currentIndex );
			if(noErrors){
				model.put("noErrors" , "true");
			}else{
				model.put("noErrors" , "false");
			}
			
			List codeMigrationList = new ArrayList();
			if(noErrors){
				//  db updates by code migration
				migrate_gristration_anotherServicePackage(codeMigrationList);
			}
//			if(noErrors){
//				//  db updates by code migration
//				migrate_briefOldApplications(codeMigrationList);
//			}
			model.put("codeMigrationList" , codeMigrationList);
			return new ModelAndView("updateDBView",model);
		}
		
		
		private String convertOracleToSqlScript(String sql,Settings settings) {
			JdbcTemplate jt = new JdbcTemplate(dataSource);
//			String dbName = settings.getService();
			String replacedString = sql;
			replacedString = replacedString.toLowerCase();
			replacedString.replaceAll("number", "BIGINT");
			replacedString.replaceAll("VARCHAR2", "VARCHAR");
			replacedString.replaceAll("DATE", "	DATETIME2(0)");
			
			return replacedString;
//			try{
//				String statement = "DECLARE @dbName AS VARCHAR(100);"
//				+ "Declare @sql varchar(100);"
//				+ "set  @dbName='"+settings.getService()+"'; "
//				+"set @sql='select table_name from '+ @dbName+'.information_schema.tables;';"
//				+"exec(@sql)";
////				+ "set @sql='select * from '+ @dbName+'.i18n_locale;';"
////				+ "exec(@sql)";
//				List tableName = jt.queryForList(statement);
//				Iterator itr = tableName.iterator();
//				while(itr.hasNext()) {
//					LinkedCaseInsensitiveMap resultMap = (LinkedCaseInsensitiveMap) tableName.get(0);
//					String tName =   (String)resultMap.get("table_name");
////					if (replacedString.contains(tName)) {
//						replacedString.replace(tName, dbName+"."+tName);
////					}
////				}
//				log.debug("replaced query " + replacedString);
//				return replacedString;
//			}catch (Exception e) {
//				e.printStackTrace();
//				return sql;
//			}
		}
		
		private void migrate_gristration_anotherServicePackage(List codeMigrationList) { // flag No.1
			Flag flag = (Flag)comMger.getObject(Flag.class , new Long(1));
//			if(flag != null && !flag.getFlag()){
//				
//	
//				List migrationRecords = comMger.getObjectsByNullParameter(AnotherServiceApplication.class, "applicationPackage");
//				int migrationRecordsSize = migrationRecords.size();
//				log.fatal("migrationRecordsSize " + migrationRecordsSize );
//				
//				Calendar cal = Calendar.getInstance();
//				int yearInt = cal.get(Calendar.YEAR);
//				String year = new Integer(yearInt).toString();
//	
//				Iterator itr = migrationRecords.iterator();
//				while(itr.hasNext()){
//					AnotherServiceApplication anotherServiceApplication = (AnotherServiceApplication) itr.next();
//					// create an application Package for each one 
//					String packageNumber;
//					packageNumber = comMger.generateCCNumbers(year,10);
//					while(comMger.getObjectByParameter(ApplicationPackage.class,"packageNumber",packageNumber) != null){
//						packageNumber = comMger.generateCCNumbers(year,10);
//					}
//					ApplicationPackage package1  = new ApplicationPackage();
//					package1.setPackageNumber(packageNumber);
//					
//					package1.setAnotherServiceApplicationCounter(new Long(1));
//					package1.setAnotherServicePrice(anotherServiceApplication.getPrice());
//					package1.setDate(anotherServiceApplication.getDate());
//					package1.setPackageStatus("Unpaid");
//					package1.setPaymentDate(anotherServiceApplication.getPaymentDate());
//					package1.setPaymentEmployee(anotherServiceApplication.getPaymentEmployee());
//					if(anotherServiceApplication.getReceivementDocumentNumber() != null && !anotherServiceApplication.getReceivementDocumentNumber().equals("")){
//						package1.setReceivementDocumentNumber(new Long(anotherServiceApplication.getReceivementDocumentNumber()));
//					}
//					package1.setReceivementDocumentsReportCreated(anotherServiceApplication.getReceivementDocumentsReportCreated());
//					if(anotherServiceApplication.getReceivementDocumentNumber() != null && !anotherServiceApplication.getReceivementDocumentNumber().equals("")){
//						package1.setPackageStatus("Paid");
//					}
//					
//					anotherServiceApplication.setApplicationPackage(package1);
//					package1.getAnotherServiceApplications().add(anotherServiceApplication);
//					comMger.saveObject(anotherServiceApplication);
//					comMger.saveObject(package1);
//				}
//			
//				flag.setComment("migration done, application Packages created for each old anotherServiceApplication successfully, number of old anotherServiceApplications/ new applicationPackages = "+migrationRecordsSize);
//				flag.setFlag(new Boolean(true));
//				comMger.saveObject(flag);
//				codeMigrationList.add(flag);
//			}
		}
		
		
//		private void migrate_briefOldApplications(List codeMigrationList){
//			
//			
//			Flag flag = (Flag)comMger.getObject(Flag.class , new Long(2));
////			if(flag != null && !flag.getFlag()){
//				
//				//MigrateBriefApplicationFromOldApplicant.migrate(comMger);
//				
////				MigrateBriefApplicationFromSCFHS migrateBriefApplicationFromSCFHS = new MigrateBriefApplicationFromSCFHS();
////				migrateBriefApplicationFromSCFHS.migrate(comMger);
//				
//				BreifIntermediateSQLMigrationFromSCFHS migrateBriefApplicationFromSCFHS = new BreifIntermediateSQLMigrationFromSCFHS();
//				migrateBriefApplicationFromSCFHS.migrate();
//				
//				
//				flag.setComment("breif application migration done");
//				flag.setFlag(new Boolean(true));
//				comMger.saveObject(flag);
//				codeMigrationList.add(flag);
////			}
//			
//		}
}
