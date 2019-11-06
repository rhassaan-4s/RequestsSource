package com._4s_.dbUpdate.web.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.common.model.Flag;
import com._4s_.common.model.LastSequence;
import com._4s_.common.service.CommonManager;

public class UpdateTriggers implements Controller {
	
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
			
			String contextPath = request.getSession().getServletContext().getRealPath("/");
			log.debug(">>>>>>>>>>>>>>>>>>>>contextPath "+contextPath);
			String fileName = contextPath+"/dbUpdates/Triggers.xml";
			String absolutePath= request.getRealPath("/");
			File witeTo=new File(contextPath+"/dbUpdates/TriggersLogs.txt");
			FileWriter witer=new FileWriter(witeTo);
			BufferedWriter wirter=new BufferedWriter(witer);
			
	        SAXBuilder builder = new SAXBuilder();
	        Document doc = builder.build(new File(fileName));
	        Element webapp = doc.getRootElement();
            List lst=webapp.getChildren();
//            Long lastDesiredIndex = new Long(((Element) lst.get(lst.size() -1 )).getChild("sqlindex").getText());

            JdbcTemplate jt = new JdbcTemplate(dataSource);

//            LastSequence seq= comMger.getSequenceByClassName("QueryIndex");
//            Long oldIndex=seq.getClassSequence();
//            log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>oldIndex " +oldIndex);
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
             
//        		if(currentIndex>oldIndex){ // new Blocks only
        			queryBlock=block.getChild("statment").getText();
        			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Query IS "+queryBlock);
        			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>blockIndex "+blockIndex);
        			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>before executing");
        			totalNumber++;
        			StringTokenizer tokenizer = new StringTokenizer(queryBlock, ";");
        			
        			
//        			jt.execute("start transaction");
        			while (tokenizer.hasMoreTokens()) { //for all statements in the Block
        				qry = tokenizer.nextToken();
        				qry = StringUtils.trim(qry);
        				try{
        					if (qry != null && qry.length() != 0){
        						jt.execute(qry);
        					}
        				}catch (Exception ec) {
                    		noErrors=false;
//                    		TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
            	        	String BadLine="Error in line" + "\n"+qry;
            				witer.append(BadLine+"\n \t"+ec.getCause()+"\n\n\n\n\n the block:\n"+queryBlock);
            				for (int i =0; i<ec.getStackTrace().length; i++) {
            					witer.append("\n"+ec.getStackTrace()[i]);
            				}
//            				
            				ec.printStackTrace();
//            				break; // stop this block
            	       }
        			}
        			
//    				if(noErrors) {
//    					String updateSQL="update  common_last_sequence set classSequence="+blockIndex+" where className='QueryIndex'";
//    					jt.execute(updateSQL);
    					jt.execute("commit");
//    				}else{
//    					jt.execute("rollback");
//    					currentIndex--;
//    					break; // this will terminater the run of the script
//    				}

//        		}
            }
			witer.close();
			HashMap model=new HashMap();
//			model.put("error",errorNumber);
			model.put("total",totalNumber);

//			model.put("lastDesiredIndex" , lastDesiredIndex);
//			model.put("oldIndex" , oldIndex);
//			model.put("currentIndex" ,currentIndex );
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
			return new ModelAndView("updateTriggersView",model);
		}
		
		
		private void migrate_gristration_anotherServicePackage(List codeMigrationList) { // flag No.1
			Flag flag = (Flag)comMger.getObject(Flag.class , new Long(1));
		}
		
		

}
