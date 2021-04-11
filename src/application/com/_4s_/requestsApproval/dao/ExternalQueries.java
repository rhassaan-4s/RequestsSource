package com._4s_.requestsApproval.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
//import org.hibernate.hql.ast.tree.DeleteStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com._4s_.common.util.MultiCalendarDate;
//import com._4s_.stores.model.DBConnection;
//import com._4s_.stores.model.ExternalDependenceTo;
//import com._4s_.stores.model.ItemData;
//import com._4s_.stores.model.ShippingData;
//import com._4s_.stores.model.StoreTransactionM;
//import com._4s_.stores.model.StoreTransactionMDep;
//import com._4s_.stores.model.StoreTransactionMDepExt;
//import com._4s_.stores.model.StoreTransactionO;
//import com._4s_.stores.model.StoreTrnsDep;
//import com._4s_.stores.service.StoresManager;
//import com._4s_.stores.web.action.ExternalTypeAndCode;
import com._4s_.requestsApproval.web.action.TimeAttend;

public class ExternalQueries {
	protected final Log log = LogFactory.getLog(getClass());

	private JdbcTemplate jdbcTemplate;
	private BasicDataSource basicDataSource;
	private JdbcTemplate exjt;
	
	private PlatformTransactionManager platformTransactionManager;
	
	
	
//	private StoresManager mgr ;
//	public void setMgr(StoresManager mgr) {
//		this.mgr = mgr;
//	}
//
//	public StoresManager getMgr() {
//		return mgr;
//	}
//	private BasicDataSource createDataSource(DBConnection dbConnection) {
//		if(basicDataSource == null){
//			basicDataSource = new BasicDataSource();
//			basicDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
//			basicDataSource.setUrl("jdbc:oracle:thin:@"
//					+ dbConnection.getHostName() + ":1521:"
//					+ dbConnection.getServiceName());
//			basicDataSource.setUsername(dbConnection.getUserName());
//			basicDataSource.setPassword(dbConnection.getPassword());
//		}
//		return basicDataSource;
//	}
	
	public PlatformTransactionManager getPlatformTransactionManager() {
		return platformTransactionManager;
	}


	public void setPlatformTransactionManager(
			PlatformTransactionManager platformTransactionManager) {
		this.platformTransactionManager = platformTransactionManager;
	}


	private BasicDataSource createDataSource(String hostName,String serviceName,String userName,String password) {
		if(basicDataSource == null){
			basicDataSource = new BasicDataSource();
			basicDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
			basicDataSource.setUrl("jdbc:oracle:thin:@"
					+ hostName + ":1521/"
					+ serviceName);
			basicDataSource.setUsername(userName);
			basicDataSource.setPassword(password);
		}
		return basicDataSource;
	}
	

//	public List getExternalTransactionTypeList(DBConnection dbConnection, String[] transNoList) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder("select * from stor_trns where ");
//		if (transNoList.length > 0) {
//			for (int i = 0; i < transNoList.length; i++) {
//				sql.append("trns_code = '" + transNoList[i] + "'");
//				if (i < transNoList.length - 1) {
//					sql.append(" or ");
//				}
//			}
//			return jdbcTemplate.queryForList(sql.toString());
//		}
//		return null;
//	}
//
//	public Map getExternalTransactionList(DBConnection dbConnection,
//			String transCode,String transNo,String branch, int pageNumber, int pageSize) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		String sqlString =null;
//		if(transNo != null){
//			sqlString= transCode +"' and trns_no = '"+transNo+"' and branch = '"+branch+"'";
//		}else{
//			sqlString= transCode +"' and branch = '"+branch+"'";
//		}
//		StringBuilder sql1 = new StringBuilder(
//				"select * from (select rownum rnum, mm.* from (select trns_code, branch, trns_no,trns_date from store_trns_m stm where trns_code = '");
//						sql1.append(sqlString);
//						sql1.append(" and (item_form = '003' or item_form = '004')");
//						sql1.append(" and not exists (select trns_code, branch, trns_no from "
//						+" coolingdep co where stm.trns_code = co.trns_code and "
//						+" stm.trns_no = co.trns_no and stm.branch = co.branch)");
//						sql1.append(" order by branch, trns_no) mm) where rnum between "
//						+ ((pageNumber * pageSize) + 1)
//						+ " and "
//						+ ((pageNumber + 1) * pageSize));
//						
//		List results = jdbcTemplate.queryForList(sql1.toString());
//		log.debug("sql1.......... "+sql1.toString());
//		StringBuilder sql2 = new StringBuilder( 
//				"select count(*) from store_trns_m stm where trns_code = '");
//				sql2.append(sqlString);
//				sql2.append(" and (item_form = '003' or item_form = '004')");
//				sql2.append(" and not exists (select trns_code, branch, trns_no from "
//						+" coolingdep co where stm.trns_code = co.trns_code and "
//						+" stm.trns_no = co.trns_no and stm.branch = co.branch)");
//		log.debug("sql2.......... "+sql2.toString());
//		int count = jdbcTemplate.queryForInt(sql2.toString());
//		Map map = new HashMap();
//		map.put("results", results);
//		map.put("listSize", count);
//		Page page = new Page();
//		return page.getPage(map, pageNumber, pageSize);
//	}
//	
//	public Map getExternalTransactionListByDate(DBConnection dbConnection,
//			String transCode,String transNo,String branch,Date fromDate,Date toDate,
//			String formType,String fromCode,String toType,String toCode,int pageNumber, int pageSize) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//		String sqlString =null;
//		log.debug("transNo.......... "+transNo);
//		log.debug("transCode.......... "+transCode);
//		log.debug("formType.......... "+formType);
//		log.debug("toType.......... "+toType);
//		log.debug("fromCode.......... "+fromCode);
//		log.debug("toCode.......... "+toCode);
//		StringBuilder sqlAppend = new StringBuilder();
//		if(transNo != null){
//			sqlString= transCode +" and trns_no = '"+transNo+"' and branch = '"+branch+"'";
//		}else{
//			log.debug("transCode.......... "+transCode);
//			sqlString= transCode +" and branch = '"+branch+"'";
//		}
//		
//		if(formType != null && !formType.equals("") && toType.equals("")){
//			log.debug("fromType1.......... "+formType);
//			sqlString= sqlString + " and FROM_DST = "+formType;
//		}else if (toType != null && !toType.equals("") && formType.equals("")){
//			log.debug("toType2.......... "+toType);
//			sqlString= sqlString + " and TO_DST = "+toType;
//		}else if(formType != null && !formType.equals("") && toType != null && !toType.equals("")){
//			log.debug("toType3.......... "+toType+"..."+formType);
//			sqlString= sqlString + " and FROM_DST = "+formType+" and TO_DST = "+toType;
//			log.debug("SQL.......... "+sqlString);
//		}
//		
//		if(fromCode != null && !fromCode.equals("") && toCode.equals("")){
//			sqlString= sqlString + " and FROM_CODE = "+fromCode;
//		}else if (toCode != null && !toCode.equals("") && fromCode.equals("")){
//			sqlString= sqlString + " and TO_CODE = "+toCode;
//		}else if(fromCode != null && !fromCode.equals("") && toCode != null && !toCode.equals("")){
//			sqlString= sqlString + " and FROM_CODE = "+fromCode+" and TO_CODE = "+toCode;
//		}
//		
//		StringBuilder sql1 = new StringBuilder(
//				"select * from (select rownum rnum, mm.* from (select trns_code, branch, trns_no,trns_date from store_trns_m stm where trns_code = ");
//						sql1.append(sqlString);
//						if(fromDate != null && !fromDate.equals("")){
//							sql1.append(" and trns_date >= to_date('"+simpleDateFormat.format(fromDate)+"','dd-MM-YYYY')");	
//						}
//						if(toDate != null && !toDate.equals("")){
//							sql1.append(" and trns_date <= to_date('"+simpleDateFormat.format(toDate)+"','dd-MM-YYYY')");
//						}	
//						sql1.append(" and (item_form = '003' or item_form = '004') ");
//						sql1.append(" and not exists (select trns_code, branch, trns_no from "
//								+" coolingdep co where stm.trns_code = co.trns_code and "
//								+" stm.trns_no = co.trns_no and stm.branch = co.branch)");
//						sql1.append(" order by branch, trns_no) mm) where rnum between "
//								+ ((pageNumber * pageSize) + 1)
//								+ " and "
//								+ ((pageNumber + 1) * pageSize));
//						
//		List results = jdbcTemplate.queryForList(sql1.toString());
//		log.debug("sql1.......... "+sql1.toString());
//		
//		
//		StringBuilder sql2 = new StringBuilder("select count(*) from store_trns_m stm where trns_code = ");
//		sql2.append(sqlString);
//				if(fromDate != null && !fromDate.equals("")){
//					sql2.append(" and trns_date >= to_date('"+simpleDateFormat.format(fromDate)+"','dd-MM-YYYY')");	
//				}
//				if(toDate != null && !toDate.equals("")){
//					sql2.append(" and trns_date <= to_date('"+simpleDateFormat.format(toDate)+"','dd-MM-YYYY')");
//				}	
//				sql2.append(" and (item_form = '003' or item_form = '004')");
//				sql2.append(" and not exists (select trns_code, branch, trns_no from "
//						+" coolingdep co where stm.trns_code = co.trns_code and "
//						+" stm.trns_no = co.trns_no and stm.branch = co.branch)");
//		log.debug("sql2.......... "+sql2.toString());
//		int count = jdbcTemplate.queryForInt(sql2.toString());
//		Map map = new HashMap();
//		map.put("results", results);
//		map.put("listSize", count);
//		Page page = new Page();
//		return page.getPage(map, pageNumber, pageSize);
//	}
//	
//	public List getExternalTransactionDetail(DBConnection dbConnection,
//			String transCode, String transNo, String branch) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				" select sum(qty1) as qty1,sum(qty2) as qty2,trns_code,trns_no,branch, "
//				+" item_form,item_code,cost_center_o "
//				+ " from store_trns_o where trns_code = '" + transCode
//						+ "' and trns_no = '" + transNo + "' and branch = '"
//						+ branch + "'"
//				+"group by trns_code,trns_no,branch,item_form,item_code,cost_center_o");
//		log.debug("sql Ex data...."+sql);
//		return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	public List getExternalItemData(DBConnection dbConnection, String itemForm,
//			String itemCode) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				"select * from item_data where item_form = '" + itemForm
//						+ "' and item_code = '" + itemCode + "'");
//		return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	public List getExternalTransport(DBConnection dbConnection,
//			String transCode, String transNo, String branch) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				"select * from trnsport_m where trns_code = '" + transCode
//						+ "' and trns_no = '" + transNo + "' and branch = '"
//						+ branch + "'");
//		return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	public List getExternalBasicUnit(DBConnection dbConnection, String itemForm) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				"select basic_unit.unit_code, basic_unit.unit_desc, basic_unit.desc_en"
//						+ " from unit_form, basic_unit where item_form = '"
//						+ itemForm
//						+ "' and calc = 1 and unit_form.unit_code=basic_unit.unit_code");
//		return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	public List getExternalCustomer(DBConnection dbConnection,
//			String customerCode) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				"select cust_code, cust_name, name_en from customer where cust_code = '"
//						+ customerCode + "'");
//		return jdbcTemplate.queryForList(sql.toString());
//	}

//	public List getExternalStore(DBConnection dbConnection, String storeCode) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				"select code, descr, name_en from stroes where code = '"
//						+ storeCode + "'");
//		return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	public List getExternalCostCenter(DBConnection dbConnection, String costCode) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				"select costcode, costname, latin_name from cost where costcode = '"
//						+ costCode + "'");
//		return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	public List getExternalTransaction(DBConnection dbConnection,
//			String transCode, String transNo, String branch) {
//		log.debug("..........exTransCode"+transCode);
//		log.debug("..........exTransBranch"+branch);
//		log.debug("..........exTransNo"+transNo);
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				"select * from store_trns_m where trns_code = '" + transCode
//						+ "' and trns_no = '" + transNo + "' and branch = '"
//						+ branch + "'");
//		log.debug("ext sql....... "+sql);
//		return jdbcTemplate.queryForList(sql.toString());
//	}
	
//	public List getExternalStoreTrans(DBConnection dbConnection) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder("select * from stor_trns order by trns_desc");
//		return jdbcTemplate.queryForList(sql.toString());
//	}
	
//	public String saveExternalTransaction(DBConnection dbConnection,
//			StoreTransactionM storeTransactionM,
//			ExternalDependenceTo externalDependenceTo, Long transNo) {
//		BasicDataSource basicDataSource = createDataSource(dbConnection);
//		log.debug("branch,,,,,,,,"+storeTransactionM.getBranch().getLehaa_branch_code());
//		String mainItemForm = null;
//		for(int i = 0; i < storeTransactionM.getStoreTransactionO().size(); i++){
//			StoreTransactionO storeTransactionO = storeTransactionM.getStoreTransactionO().get(0);
//			mainItemForm = storeTransactionO.getItem_code().getEx_item_form();
//		}
//		
//		boolean destFlagFrom = false;
//		boolean destFlagTo = false;
//		String destByCoolingAndLehaFrom ="0";
//		String destByCoolingAndLehaTo ="0";
//		
//		Connection conn = null;
//		try {
//			conn = basicDataSource.getConnection();
//			if (conn.isClosed()) {
//				log.debug("conn closed,,,,,,,,");
//				return null;
//			}
//			log.debug("storeTransactionM.getEx_shipping_document()............"+storeTransactionM.getEx_shipping_document());
//			log.debug("storeTransactionM.getEx_shipping_document().equals............."+storeTransactionM.getEx_shipping_document());
//			if (storeTransactionM.getEx_shipping_document() != null
//					&& !storeTransactionM.getEx_shipping_document().equals("")) {
//				log.debug("1.............");
//				String[] arrCodes = storeTransactionM.getEx_shipping_document().split("_");
//				if (arrCodes.length == 3) {
//					log.debug("1.............");
//					List transactionList = getExternalTransaction(dbConnection,
//							arrCodes[0], arrCodes[1], arrCodes[2]);
//					if (transactionList.size() > 0) {
//						log.debug("1.............");
//						ListOrderedMap externalTransaction = (ListOrderedMap) transactionList.get(0);
//						BigDecimal fromDest = (BigDecimal) externalTransaction.get("FROM_DST");
//						String fromCode = (String) externalTransaction.get("FROM_CODE");
//						BigDecimal toDest = (BigDecimal) externalTransaction.get("TO_DST");
//						String toCode = (String) externalTransaction.get("TO_CODE");
//						BigDecimal insertedFromDest = null;
//						String insertedFromCode = null;
//						BigDecimal insertedToDest = null;
//						String insertedToCode = null;
//						if(externalDependenceTo.getFromType() != null){
//							log.debug("1.............");
//							if(externalDependenceTo.getFromType().equals("from")){
//								insertedFromDest = fromDest;
//								insertedFromCode = fromCode;
//							} else if(externalDependenceTo.getFromType().equals("to")){
//								insertedFromDest = toDest;
//								insertedFromCode = toCode;
//							} else if(externalDependenceTo.getFromType().equals("store")){
//								insertedFromDest = new BigDecimal("4");
//								insertedFromCode = storeTransactionM.getBranch().getLehaa_code();
//							} else if(externalDependenceTo.getFromType().equals("lost")){
//								insertedFromDest = new BigDecimal("4");
//								insertedFromCode = storeTransactionM.getBranch().getLehaa_lost_code();
//							} else if(externalDependenceTo.getFromType().equals("trDepart")){
//								insertedFromDest = new BigDecimal("6");
//								insertedFromCode = storeTransactionM.getBranch().getTransfersDepartment();
//							} else if(externalDependenceTo.getFromType().equals("brDepart")){
//								insertedFromDest = new BigDecimal("6");
//								insertedFromCode = storeTransactionM.getBranch().getBranchDepartment();	
//							} else if(externalDependenceTo.getFromType().equals("fLFromC")){
//								destByCoolingAndLehaFrom ="1";
//								if(storeTransactionM.getFromDestination()!= null
//										&& !storeTransactionM.getFromDestination().equals("")
//										&& storeTransactionM.getFromDestination().getExtDestcode() != null 
//										&& !storeTransactionM.getFromDestination().getExtDestcode().equals("")
//										&& !storeTransactionM.getFromDestination().getExtDestcode().equals("0")){
//									insertedFromDest = new BigDecimal(storeTransactionM.getFromDestination().getGroupf().getExtDestination());
//									insertedFromCode = storeTransactionM.getFromDestination().getExtDestcode();
//									destFlagFrom = true;
//								}else{
//									destFlagFrom = false;
//								}
//								log.debug("destFrom1..."+destByCoolingAndLehaFrom);
//								log.debug("destFlagFrom1..."+destFlagFrom);
//							} else if(externalDependenceTo.getFromType().equals("tLFromC")){
//								destByCoolingAndLehaFrom ="1";
//								if(storeTransactionM.getToDestination()!= null
//										&& !storeTransactionM.getToDestination().equals("")
//										&& storeTransactionM.getToDestination().getExtDestcode() != null
//										&& !storeTransactionM.getToDestination().getExtDestcode().equals("")
//										&& !storeTransactionM.getToDestination().getExtDestcode().equals("0")){
//									insertedFromDest = new BigDecimal(storeTransactionM.getToDestination().getGroupf().getExtDestination());
//									insertedFromCode = storeTransactionM.getToDestination().getExtDestcode();
//									destFlagFrom = true;
//								}else{
//									destFlagFrom = false;
//								}
//								log.debug("destFrom2..."+destByCoolingAndLehaFrom);
//								log.debug("destFlagFrom2..."+destFlagFrom);
//							}else if(externalDependenceTo.getFromType().equals("extDepFrom")
//									&& storeTransactionM.getExtdestfrom()==true
//									&& storeTransactionM.getExtdestfromid()!=null
//									&& storeTransactionM.getExtdestfromcodeid() != null){
//								insertedFromDest = new BigDecimal(storeTransactionM.getExtdestfromid());
//								insertedFromCode = storeTransactionM.getExtdestfromcodeid();
//								
//							}else if(externalDependenceTo.getFromType().equals("extDepTo")
//									&& storeTransactionM.getExtdestto()==true
//									&& storeTransactionM.getExtdesttoid()!=null
//									&& storeTransactionM.getExtdesttocodeid() != null){
//								insertedFromDest = new BigDecimal(storeTransactionM.getExtdesttoid());
//								insertedFromCode = storeTransactionM.getExtdesttocodeid();
//							}
//						}
//						if(externalDependenceTo.getToType() != null){
//							log.debug("1.............");
//							if(externalDependenceTo.getToType().equals("from")){
//								insertedToDest = fromDest;
//								insertedToCode = fromCode;
//							} else if(externalDependenceTo.getToType().equals("to")){
//								insertedToDest = toDest;
//								insertedToCode = toCode;
//							} else if(externalDependenceTo.getToType().equals("store")){
//								insertedToDest = new BigDecimal("4");
//								insertedToCode = storeTransactionM.getBranch().getLehaa_code();
//							} else if(externalDependenceTo.getToType().equals("lost")){
//								insertedToDest = new BigDecimal("4");
//								insertedToCode = storeTransactionM.getBranch().getLehaa_lost_code();
//							} else if(externalDependenceTo.getToType().equals("fLFromC")){
//								destByCoolingAndLehaTo ="1";
//								if(storeTransactionM.getFromDestination()!= null
//										&& !storeTransactionM.getFromDestination().equals("")
//										&& storeTransactionM.getFromDestination().getExtDestcode() != null
//										&& !storeTransactionM.getFromDestination().getExtDestcode().equals("")
//										&& !storeTransactionM.getFromDestination().getExtDestcode().equals("0")){
//									insertedToDest = new BigDecimal(storeTransactionM.getFromDestination().getGroupf().getExtDestination());
//									insertedToCode = storeTransactionM.getFromDestination().getExtDestcode();
//									destFlagTo = true;
//								}else{
//									destFlagTo = false;
//								}
//								log.debug("destTo1..."+destByCoolingAndLehaTo);
//								log.debug("destFlagTo1..."+destFlagTo);
//							} else if(externalDependenceTo.getToType().equals("tLFromC")){
//								destByCoolingAndLehaTo ="1";
//								if(storeTransactionM.getToDestination()!= null
//										&& !storeTransactionM.getToDestination().equals("")
//										&& storeTransactionM.getToDestination().getExtDestcode() != null
//										&& !storeTransactionM.getToDestination().getExtDestcode().equals("")
//										&& !storeTransactionM.getToDestination().getExtDestcode().equals("0")){
//									insertedToDest = new BigDecimal(storeTransactionM.getToDestination().getGroupf().getExtDestination());
//									insertedToCode = storeTransactionM.getToDestination().getExtDestcode();
//									destFlagTo = true;
//								}else{
//									destFlagTo = false;
//								}
//								log.debug("destTo2..."+destByCoolingAndLehaTo);
//								log.debug("destFlagTo2..."+destFlagTo);
//							} else if(externalDependenceTo.getFromType().equals("extDepFrom")
//									&& storeTransactionM.getExtdestfrom()==true
//									&& storeTransactionM.getExtdestfromid()!=null
//									&& storeTransactionM.getExtdestfromcodeid() != null){
//								insertedToDest = new BigDecimal(storeTransactionM.getExtdestfromid());
//								insertedToCode = storeTransactionM.getExtdestfromcodeid();
//								
//							}else if(externalDependenceTo.getFromType().equals("extDepTo")
//									&& storeTransactionM.getExtdestto()==true
//									&& storeTransactionM.getExtdesttoid()!=null
//									&& storeTransactionM.getExtdesttocodeid() != null){
//								insertedToDest = new BigDecimal(storeTransactionM.getExtdesttoid());
//								insertedToCode = storeTransactionM.getExtdesttocodeid();
//							}
//						}
//						if(destByCoolingAndLehaFrom.equals("1") && destFlagFrom == false && insertedFromCode == null){
//							return "emptyDest";
//						}else if(destByCoolingAndLehaTo.equals("1") && destFlagTo == false && insertedToCode == null){
//							return "emptyDest";
//						}else{
//						
//						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//						SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
//						Calendar c = Calendar.getInstance();
//						conn.setAutoCommit(false);
//						Statement statement = conn.createStatement();
//						ResultSet rs = null;
//						if(transNo == null){
//							log.debug("1.............");
//							transNo = 1L;
//							rs = statement.executeQuery("select max(trns_no) as max_no from store_trns_m where trns_code = "
//											+ externalDependenceTo.getTransCode()
//											+ " and branch = '"
//											+ storeTransactionM.getBranch().getLehaa_branch_code()
//											+ "'");
//							if(rs.next()){
//								if(rs.getBigDecimal("MAX_NO") != null){
//									transNo = rs.getBigDecimal("MAX_NO").longValue() + 1;
//								}
//							}
//							rs.close();
//						}
//						int transtype = getTransTypeCode(externalDependenceTo.getTransCode());
//						String externalKey = externalDependenceTo.getTransCode() + "_" + storeTransactionM.getTrns_no() + "_" + storeTransactionM.getBranch().getLehaa_branch_code();
//						StringBuffer sql = new StringBuffer("insert into store_trns_m(trns_no, trns_code, trns_date, from_dst,");
//						sql.append(" from_code, to_dst, to_code,trns_type, item_form, rem, branch, trns_stamp,bookno) values (");
//						sql.append(storeTransactionM.getTrns_no() + " , '" + externalDependenceTo.getTransCode() + "', to_date('" + simpleDateFormat.format(storeTransactionM.getTrns_date()) + "','dd-MM-YYYY')");
//						sql.append(", " + insertedFromDest + ", '" + insertedFromCode + "', " + insertedToDest + ",");
//						sql.append(" '" + insertedToCode + "',"+transtype+", '" + mainItemForm + "', '"+storeTransactionM.getRem()+"',");
//						sql.append(" '" + storeTransactionM.getBranch().getLehaa_branch_code() + "', to_date('" + simpleDateFormat.format(storeTransactionM.getTrns_date()) + "','dd-MM-YYYY'),0");
//						sql.append(")");
//						log.debug("sql..............."+sql);
//						int no = statement.executeUpdate(sql.toString());
//						sql = new StringBuffer("insert into store_trns_o(trns_code, trns_no, trns_date, item_code, qty1, qty2, qty3,");
//						sql.append(" unit_price, total_price, item_form, branch, serial_no, cost_center_o, basic_qty,no_units)");
//						sql.append(" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)");
//						log.debug("sql..............."+sql);
//						PreparedStatement insertStatement = conn.prepareStatement(sql.toString());
//						c.setTime(storeTransactionM.getTrns_date());
//						java.sql.Date transDate = new java.sql.Date (storeTransactionM.getTrns_date().getTime());
//						int serial = 1;
//						for(int i = 0; i < storeTransactionM.getStoreTransactionO().size(); i++){
//							StoreTransactionO storeTransactionO = storeTransactionM.getStoreTransactionO().get(i);
//							String itemCode = storeTransactionO.getItem_code().getEx_item_code();
//							String itemForm = storeTransactionO.getItem_code().getEx_item_form();
//							if(itemCode != null && !itemCode.equals("") && itemForm != null && !itemForm.equals("")){
//								
//								rs = statement.executeQuery("select unit_ser from unit_form where item_form = '"
//										+ itemForm + "' and calc = 1");
//								BigDecimal quantity = new BigDecimal("0.0");
//								if(rs.next()){
//									quantity = rs.getBigDecimal("UNIT_SER");
//								}
//								rs.close();
//								
//								
////								if (quantity != null) {
////									if (quantity.doubleValue() == 1) {
////										qty1 = storeTransactionO.getActual_qty();
////									} else if (quantity.doubleValue() == 2) {
////										qty2 = storeTransactionO.getActual_qty();
////									} else if (quantity.doubleValue() == 3) {
////										qty3 = storeTransactionO.getActual_qty();
////									}
////								}
//								
//								rs = statement.executeQuery("select unit_no from item_spec where item_form = '"
//										+ itemForm + "'");
//								int unitNo = 0;;
//								if(rs.next()){
//									unitNo = rs.getInt("unit_no");
//								}
//								
//								
//								double qty1 = 0.0;
//								double qty2 = 0.0;
//								double qty3 = 0.0;
//								log.debug("q1,,,,,,,,"+storeTransactionO.getActual_qty());
//								log.debug("q2,,,,,,,,"+storeTransactionO.getQty2());
//								log.debug("q3,,,,,,,,"+storeTransactionO.getQty3());
//								qty1 = storeTransactionO.getActual_qty();
//								qty2 = storeTransactionO.getQty2();
//								qty3 = storeTransactionO.getQty3();
//								
//								log.debug("1,,,,,,,,"+externalDependenceTo.getTransCode());
//								insertStatement.setString(1, externalDependenceTo.getTransCode());
//								log.debug("2,,,,,,,,"+storeTransactionM.getTrns_no());
//								insertStatement.setLong(2, storeTransactionM.getTrns_no());
//								log.debug("3,,,,,,,,"+transDate);
//								insertStatement.setDate(3, transDate);
//								log.debug("4,,,,,,,,"+itemCode);
//								insertStatement.setString(4, itemCode);
//								log.debug("5,,,,,,,,"+qty1);
//								insertStatement.setDouble(5, qty1);
//								log.debug("5,,,,,,,,"+qty1);
//								log.debug("6,,,,,,,,"+storeTransactionO.getQty2());
//								insertStatement.setDouble(6, storeTransactionO.getQty2());
//								log.debug("7,,,,,,,,"+qty3);
//								insertStatement.setDouble(7, qty3);
//								log.debug("8,,,,,,,,"+storeTransactionO.getUnit_price());
//								insertStatement.setDouble(8, storeTransactionO.getUnit_price());
//								log.debug("9,,,,,,,,"+storeTransactionO.getActual_qty() * storeTransactionO.getUnit_price());
//								insertStatement.setDouble(9, storeTransactionO.getActual_qty() * storeTransactionO.getUnit_price());
//								log.debug("10,,,,,,,,"+itemForm);
//								insertStatement.setString(10, itemForm);
//								log.debug("11,,,,,,,,"+storeTransactionM.getBranch().getLehaa_branch_code());
//								insertStatement.setString(11, storeTransactionM.getBranch().getLehaa_branch_code());
//								log.debug("12,,,,,,,,"+serial);
//								insertStatement.setInt(12, serial);
//								
//								if(storeTransactionO.getCostCenter() != null){
//									log.debug("13,,,,,,,,"+storeTransactionO.getCostCenter().getCode());
//									insertStatement.setString(13, storeTransactionO.getCostCenter().getCode());
//								} else {
//									insertStatement.setString(13, null);
//								}
//								log.debug("14,,,,,,,,"+quantity);
//								insertStatement.setBigDecimal(14, quantity);
//								
//								log.debug("15,,,,,,,,"+unitNo);
//								insertStatement.setInt(15, unitNo);
//								
//								insertStatement.executeUpdate();
//								serial++;
//							}
//						}
//						conn.commit();
//						insertStatement.close();
//						if(no > 0){
//							return externalKey;
//						} else {
//							return null;
//						}
//					}
//						
//					} else {
//						return null;
//					}
//				} else {
//					return null;
//				}
//				
//			}else if(storeTransactionM.getTrns_code().getStoreTrnsDefSpec().getDep_without_ext_trans()==1){
//
//						BigDecimal insertedFromDest = null;
//						String insertedFromCode = null;
//						BigDecimal insertedToDest = null;
//						String insertedToCode = null;
//						
//						
////						String itemFormOfTransM = storeTransactionM.getStoreTransactionO().get(0).getItem_code().getEx_item_form();
//						
//						
//										/////////// Get Indirect External Dep for Dest /////////
//						BigDecimal fromDest= null;
//						String fromCode=null;
//						BigDecimal toDest = null;
//						String toCode = null;
//						log.debug("getStoreTransactionMDep()......."+storeTransactionM.getStoreTransactionMDep());
//										if(storeTransactionM.getStoreTransactionMDep()!=null){
//											List  mDepList =storeTransactionM.getStoreTransactionMDep();
//											for(int j=0;j<mDepList.size();j++){
//												StoreTransactionMDep storeTransMDep = (StoreTransactionMDep) mDepList.get(j);
//												StoreTransactionM transM = storeTransMDep.getTrans_dep_id();
//											if(transM.getTrns_code().getStoreTrnsDefSpec().getIs_contract()!=1
//													&& transM.getTrns_code().getStoreTrnsDefSpec().getIs_invoice()!=1
//													&& transM.getTrns_code().getStoreTrnsDefSpec().getIs_reservation()!=1){
//												log.debug("TransactionMDep()......."+transM.getId());
//												log.debug("transM.getEx_shipping_document()......."+transM.getEx_shipping_document());
//												if (transM.getEx_shipping_document() != null
//														&& !transM.getEx_shipping_document().equals("")) {	
//													String[] arrCodes = transM.getEx_shipping_document().split("_");
//													if (arrCodes.length == 3) {
//														log.debug("1.............");
//														List transactionList = getExternalTransaction(dbConnection,
//																arrCodes[0], arrCodes[1], arrCodes[2]);
//														if (transactionList.size() > 0) {
//															log.debug("1.............");
//															ListOrderedMap externalTransaction = (ListOrderedMap) transactionList.get(0);
//															 fromDest = (BigDecimal) externalTransaction.get("FROM_DST");
//															 fromCode = (String) externalTransaction.get("FROM_CODE");
//															 toDest = (BigDecimal) externalTransaction.get("TO_DST");
//															 toCode = (String) externalTransaction.get("TO_CODE");
//														}
//													}
//												}
//												}
//											}
//											}
//										log.debug("dest...From... "+externalDependenceTo.getFromType());
//										log.debug("dest...To... "+externalDependenceTo.getToType());
//						if(externalDependenceTo.getFromType() != null){
//							if(externalDependenceTo.getFromType().equals("from")){
//								insertedFromDest = fromDest;
//								insertedFromCode = fromCode;
//							} else if(externalDependenceTo.getFromType().equals("to")){
//								insertedFromDest = toDest;
//								insertedFromCode = toCode;							
//							} else if(externalDependenceTo.getFromType().equals("store")){
//								insertedFromDest = new BigDecimal("4");
//								insertedFromCode = storeTransactionM.getBranch().getLehaa_code();
//							} else if(externalDependenceTo.getFromType().equals("lost")){
//								insertedFromDest = new BigDecimal("4");
//								insertedFromCode = storeTransactionM.getBranch().getLehaa_lost_code();
//							} else if(externalDependenceTo.getFromType().equals("trDepart")){
//								insertedFromDest = new BigDecimal("6");
//								insertedFromCode = storeTransactionM.getBranch().getTransfersDepartment();
//							} else if(externalDependenceTo.getFromType().equals("brDepart")){
//								insertedFromDest = new BigDecimal("6");
//								insertedFromCode = storeTransactionM.getBranch().getBranchDepartment();
//							} else if(externalDependenceTo.getFromType().equals("fLFromC")){
//								destByCoolingAndLehaFrom ="1";
//								if(storeTransactionM.getFromDestination()!= null
//										&& !storeTransactionM.getFromDestination().equals("")
//										&& storeTransactionM.getFromDestination().getExtDestcode() != null 
//										&& !storeTransactionM.getFromDestination().getExtDestcode().equals("")
//										&& !storeTransactionM.getFromDestination().getExtDestcode().equals("0")){
//									insertedFromDest = new BigDecimal(storeTransactionM.getFromDestination().getGroupf().getExtDestination());
//									insertedFromCode = storeTransactionM.getFromDestination().getExtDestcode();
//									destFlagFrom = true;
//								}else{
//									destFlagFrom = false;
//								}
//								log.debug("destFrom3..."+destByCoolingAndLehaFrom);
//								log.debug("destFlagFrom3..."+destFlagFrom);
//							} else if(externalDependenceTo.getFromType().equals("tLFromC")){
//								destByCoolingAndLehaFrom ="1";
//								if(storeTransactionM.getToDestination()!= null
//										&& !storeTransactionM.getToDestination().equals("")
//										&& storeTransactionM.getToDestination().getExtDestcode() != null 
//										&& !storeTransactionM.getToDestination().getExtDestcode().equals("")
//										&& !storeTransactionM.getToDestination().getExtDestcode().equals("0")){
//									insertedFromDest = new BigDecimal(storeTransactionM.getToDestination().getGroupf().getExtDestination());
//									insertedFromCode = storeTransactionM.getToDestination().getExtDestcode();
//									destFlagFrom = true;
//								}else{
//									destFlagFrom = false;
//								}
//								log.debug("destFrom4..."+destByCoolingAndLehaFrom);
//								log.debug("destFlagFrom4..."+destFlagFrom);
//							} else if(externalDependenceTo.getFromType().equals("extDepFrom")
//									&& storeTransactionM.getExtdestfrom()==true
//									&& storeTransactionM.getExtdestfromid()!=null
//									&& storeTransactionM.getExtdestfromcodeid() != null){
//								log.debug("destTypeFrom..."+storeTransactionM.getExtdestfromid());
//								log.debug("destCodeFrom..."+storeTransactionM.getExtdestfromcodeid());
//								insertedFromDest = new BigDecimal(storeTransactionM.getExtdestfromid());
//								insertedFromCode = storeTransactionM.getExtdestfromcodeid();
//								
//							}else if(externalDependenceTo.getFromType().equals("extDepTo")
//									&& storeTransactionM.getExtdestto()==true
//									&& storeTransactionM.getExtdesttoid()!=null
//									&& storeTransactionM.getExtdesttocodeid() != null){
//								insertedFromDest = new BigDecimal(storeTransactionM.getExtdesttoid());
//								insertedFromCode = storeTransactionM.getExtdesttocodeid();
//							}
//						}
//						if(externalDependenceTo.getToType() != null){
//							if(externalDependenceTo.getFromType().equals("from")){
//								insertedFromDest = fromDest;
//								insertedFromCode = fromCode;
//							}else if(externalDependenceTo.getFromType().equals("to")){
//								insertedFromDest = toDest;
//								insertedFromCode = toCode;
//							}else if(externalDependenceTo.getToType().equals("store")){
//								insertedToDest = new BigDecimal("4");
//								insertedToCode = storeTransactionM.getBranch().getLehaa_code();
//							} else if(externalDependenceTo.getToType().equals("lost")){
//								insertedToDest = new BigDecimal("4");
//								insertedToCode = storeTransactionM.getBranch().getLehaa_lost_code();
//							} else if(externalDependenceTo.getToType().equals("trDepart")){
//								insertedToDest = new BigDecimal("6");
//								insertedToCode = storeTransactionM.getBranch().getTransfersDepartment();
//							} else if(externalDependenceTo.getToType().equals("brDepart")){
//								insertedToDest = new BigDecimal("6");
//								insertedToCode = storeTransactionM.getBranch().getBranchDepartment();
//							} else if(externalDependenceTo.getToType().equals("fLFromC")){
//								destByCoolingAndLehaTo ="1";
//								if(storeTransactionM.getFromDestination()!= null
//										&& !storeTransactionM.getFromDestination().equals("")
//										&& storeTransactionM.getFromDestination().getExtDestcode() != null
//										&& !storeTransactionM.getFromDestination().getExtDestcode().equals("")
//										&& !storeTransactionM.getFromDestination().getExtDestcode().equals("0")){
//									insertedToDest = new BigDecimal(storeTransactionM.getFromDestination().getGroupf().getExtDestination());
//									insertedToCode = storeTransactionM.getFromDestination().getExtDestcode();
//									destFlagTo = true;
//								}else{
//									destFlagTo = false;
//								}
//								log.debug("destTo1..."+destByCoolingAndLehaTo);
//								log.debug("destFlagTo1..."+destFlagTo);
//							} else if(externalDependenceTo.getToType().equals("tLFromC")){
//								destByCoolingAndLehaTo ="1";
//								if(storeTransactionM.getToDestination()!= null
//										&& !storeTransactionM.getToDestination().equals("")
//										&& storeTransactionM.getToDestination().getExtDestcode() != null
//										&& !storeTransactionM.getToDestination().getExtDestcode().equals("")
//										&& !storeTransactionM.getToDestination().getExtDestcode().equals("0")){
//									insertedToDest = new BigDecimal(storeTransactionM.getToDestination().getGroupf().getExtDestination());
//									insertedToCode = storeTransactionM.getToDestination().getExtDestcode();
//									destFlagTo = true;
//								}else{
//									destFlagTo = false;
//								}
//								log.debug("destTo2..."+destByCoolingAndLehaTo);
//								log.debug("destFlagTo2..."+destFlagTo);
//							} else if(externalDependenceTo.getFromType().equals("extDepFrom")
//									&& storeTransactionM.getExtdestfrom()==true
//									&& storeTransactionM.getExtdestfromid()!=null
//									&& storeTransactionM.getExtdestfromcodeid() != null){
//								insertedToDest = new BigDecimal(storeTransactionM.getExtdestfromid());
//								insertedToCode = storeTransactionM.getExtdestfromcodeid();
//								
//							}else if(externalDependenceTo.getFromType().equals("extDepTo")
//									&& storeTransactionM.getExtdestto()==true
//									&& storeTransactionM.getExtdesttoid()!=null
//									&& storeTransactionM.getExtdesttocodeid() != null){
//								insertedToDest = new BigDecimal(storeTransactionM.getExtdesttoid());
//								insertedToCode = storeTransactionM.getExtdesttocodeid();
//							}
//						}
//						if(destByCoolingAndLehaFrom.equals("1")&& destFlagFrom == false && insertedFromCode == null){
//							return "emptyDest";
//						}else if(destByCoolingAndLehaTo.equals("1")&& destFlagTo == false && insertedToCode == null){	
//							return "emptyDest";
//						}else{
//							
//						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//						SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
//						Calendar c = Calendar.getInstance();
//						conn.setAutoCommit(false);
//						Statement statement = conn.createStatement();
//						ResultSet rs = null;
//						if(transNo == null){
//							log.debug("1.............");
//							transNo = 1L;
//							rs = statement.executeQuery("select max(trns_no) as max_no from store_trns_m where trns_code = "
//											+ externalDependenceTo.getTransCode()
//											+ " and branch = '"
//											+ storeTransactionM.getBranch().getLehaa_branch_code()
//											+ "'");
//							if(rs.next()){
//								if(rs.getBigDecimal("MAX_NO") != null){
//									transNo = rs.getBigDecimal("MAX_NO").longValue() + 1;
//								}
//							}
//							rs.close();
//						}
//						int transtype = getTransTypeCode(externalDependenceTo.getTransCode());
//						String externalKey = externalDependenceTo.getTransCode() + "_" + storeTransactionM.getTrns_no() + "_" + storeTransactionM.getBranch().getLehaa_branch_code();
//						StringBuffer sql = new StringBuffer("insert into store_trns_m(trns_no, trns_code, trns_date, from_dst,");
//						sql.append(" from_code, to_dst, to_code,trns_type, item_form, rem, branch, trns_stamp,bookno) values (");
//						sql.append(storeTransactionM.getTrns_no() + " , '" + externalDependenceTo.getTransCode() + "', to_date('" + simpleDateFormat.format(storeTransactionM.getTrns_date()) + "','dd-MM-YYYY'),");
//						sql.append(" " + insertedFromDest + ", '" + insertedFromCode + "', " + insertedToDest + ",");
//						sql.append(" '" + insertedToCode + "',"+transtype+", '" + mainItemForm + "', '"+storeTransactionM.getRem()+"',");
//						sql.append(" '" + storeTransactionM.getBranch().getLehaa_branch_code() + "',to_date('" + simpleDateFormat.format(storeTransactionM.getTrns_date()) + "','dd-MM-YYYY'),0");
//						sql.append(")");
//						log.debug("sql..............."+sql);
//						int no = statement.executeUpdate(sql.toString());
//						sql = new StringBuffer("insert into store_trns_o(trns_code, trns_no, trns_date, item_code, qty1, qty2, qty3,");
//						sql.append(" unit_price, total_price, item_form, branch, serial_no, cost_center_o, basic_qty,no_units)");
//						sql.append(" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)");
//						log.debug("sql..............."+sql);
//						PreparedStatement insertStatement = conn.prepareStatement(sql.toString());
//						c.setTime(storeTransactionM.getTrns_date());
//						java.sql.Date transDate = new java.sql.Date (storeTransactionM.getTrns_date().getTime());
//						int serial = 1;
//						for(int i = 0; i < storeTransactionM.getStoreTransactionO().size(); i++){
//							StoreTransactionO storeTransactionO = storeTransactionM.getStoreTransactionO().get(i);
//							String itemCode = storeTransactionO.getItem_code().getEx_item_code();
//							String itemForm = storeTransactionO.getItem_code().getEx_item_form();
//							if(itemCode != null && !itemCode.equals("") && itemForm != null && !itemForm.equals("")){
//								
//								rs = statement.executeQuery("select unit_ser from unit_form where item_form = '"
//										+ itemForm + "' and calc = 1");
//								BigDecimal quantity = new BigDecimal("0.0");
//								if(rs.next()){
//									quantity = rs.getBigDecimal("UNIT_SER");
//								}
//								rs.close();
//								
////								if (quantity != null) {
////									if (quantity.doubleValue() == 1) {
////										qty1 = storeTransactionO.getActual_qty();
////									} else if (quantity.doubleValue() == 2) {
////										qty2 = storeTransactionO.getActual_qty();
////									} else if (quantity.doubleValue() == 3) {
////										qty3 = storeTransactionO.getActual_qty();
////									}
////								}
//								
//								rs = statement.executeQuery("select unit_no from item_spec where item_form = '"
//										+ itemForm + "'");
//								int unitNo = 0;;
//								if(rs.next()){
//									unitNo = rs.getInt("unit_no");
//								}
//								rs.close();
//								
//								double qty1 = 0.0;
//								double qty2 = 0.0;
//								double qty3 = 0.0;
//								log.debug("q1,,,,,,,,"+storeTransactionO.getActual_qty());
//								log.debug("q2,,,,,,,,"+storeTransactionO.getQty2());
//								log.debug("q3,,,,,,,,"+storeTransactionO.getQty3());
//								
//								qty1 = storeTransactionO.getActual_qty();
//								qty2 = storeTransactionO.getQty2();
//								qty3 = storeTransactionO.getQty3();
//								
//								log.debug("1,,,,,,,,"+externalDependenceTo.getTransCode());
//								insertStatement.setString(1, externalDependenceTo.getTransCode());
//								log.debug("2,,,,,,,,"+storeTransactionM.getTrns_no());
//								insertStatement.setLong(2, storeTransactionM.getTrns_no());
//								log.debug("3,,,,,,,,"+transDate);
//								insertStatement.setDate(3, transDate);
//								log.debug("4,,,,,,,,"+itemCode);
//								insertStatement.setString(4, itemCode);
//								log.debug("5,,,,,,,,"+qty1);
//								insertStatement.setDouble(5, qty1);
//								log.debug("6,,,,,,,,"+storeTransactionO.getQty2());
//								insertStatement.setDouble(6, storeTransactionO.getQty2());
//								log.debug("7,,,,,,,,"+qty3);
//								insertStatement.setDouble(7, qty3);
//								log.debug("8,,,,,,,,"+storeTransactionO.getUnit_price());
//								insertStatement.setDouble(8, storeTransactionO.getUnit_price());
//								log.debug("9,,,,,,,,"+storeTransactionO.getActual_qty() * storeTransactionO.getUnit_price());
//								insertStatement.setDouble(9, storeTransactionO.getActual_qty() * storeTransactionO.getUnit_price());
//								log.debug("10,,,,,,,,"+itemForm);
//								insertStatement.setString(10, itemForm);
//								log.debug("11,,,,,,,,"+storeTransactionM.getBranch().getLehaa_branch_code());
//								insertStatement.setString(11, storeTransactionM.getBranch().getLehaa_branch_code());
//								log.debug("12,,,,,,,,"+serial);
//								insertStatement.setInt(12, serial);
//								if(storeTransactionO.getCostCenter() != null){
//									log.debug("13,,,,,,,,"+storeTransactionO.getCostCenter().getCode());
//									insertStatement.setString(13, storeTransactionO.getCostCenter().getCode());
//								} else {
//									insertStatement.setString(13, null);
//								}
//								log.debug("14,,,,,,,,"+quantity);
//								insertStatement.setBigDecimal(14, quantity);
//								log.debug("15,,,,,,,,"+unitNo);
//								insertStatement.setInt(15, unitNo);
//								
//								insertStatement.executeUpdate();
//								serial++;
//							}
//						}
//						
//						conn.commit();
//						insertStatement.close();
//						if(no > 0){
//							return externalKey;
//						} else {
//							return null;
//						}
//						}
//				} else {
//					return null;
//				}
//			
////			
//		} catch (SQLException e) {
//			log.debug("sql1.........  "+e.getMessage().substring(0, e.getMessage().indexOf(":"))); 
//			String errorMessage =e.getMessage().substring(0, e.getMessage().indexOf(":"));
//			e.printStackTrace();
//			return errorMessage;
//		} finally {
//			if(conn != null){
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					log.debug("sql2.........  "+e.getMessage());
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//	
//	public void deleteExternalTransaction(DBConnection dbConnection,
//			String transCode, String branchCode, Long transNo) {
//		BasicDataSource basicDataSource = createDataSource(dbConnection);
//		
//		log.debug("branch......."+branchCode);
//		log.debug("transCode......."+transCode);
//		log.debug("transNo......."+transNo);
//		Connection conn = null;
//		try {
//			conn = basicDataSource.getConnection();
//			if (conn.isClosed()) {
//				return;
//			}
//			conn.setAutoCommit(false);
//			String sql = new String("delete from store_trns_o where trns_code = ? and trns_no = ? and branch = ?");
//			PreparedStatement deleteStatement = conn.prepareStatement(sql);
//			deleteStatement.setString(1, transCode);
//			deleteStatement.setLong(2, transNo);
//			deleteStatement.setString(3, branchCode);
//			deleteStatement.executeUpdate();
//			deleteStatement.close();
//			conn.commit();
//			log.debug("true1.......");
//			sql = new String("delete from store_trns_m where trns_code = ? and trns_no = ? and branch = ?");
//			deleteStatement = conn.prepareStatement(sql);
//			deleteStatement.setString(1, transCode);
//			deleteStatement.setLong(2, transNo);
//			deleteStatement.setString(3, branchCode);
//			deleteStatement.executeUpdate();
//			deleteStatement.close();
//			conn.commit();
//			log.debug("true2.......");
//			sql = new String("delete from store_trns_dep where trns_code = ? and trns_no = ? and branch = ?");
//			deleteStatement = conn.prepareStatement(sql);
//			deleteStatement.setString(1, transCode);
//			deleteStatement.setLong(2, transNo);
//			deleteStatement.setString(3, branchCode);
//			deleteStatement.executeUpdate();
//			deleteStatement.close();
//			conn.commit();
//			log.debug("true3.......");
//			sql = new String("delete from store_trns_dep_qty where trns_code = ? and trns_no = ? and branch = ?");
//			deleteStatement = conn.prepareStatement(sql);
//			deleteStatement.setString(1, transCode);
//			deleteStatement.setLong(2, transNo);
//			deleteStatement.setString(3, branchCode);
//			deleteStatement.executeUpdate();
//			deleteStatement.close();
//			conn.commit();
//			log.debug("true4.......");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//	
//	public void saveCoolingDep(DBConnection dbConnection,Long transId,
//			String branchCode,String transCode,Long transNo) {
//		log.debug("transId...."+transId);
//		log.debug("branchCode...."+branchCode);
//		log.debug("transCode...."+transCode);
//		log.debug("transNo...."+transNo);
//		
//		BasicDataSource basicDataSource = createDataSource(dbConnection);
//		Connection conn = null;
//		try {
//			conn = basicDataSource.getConnection();
//			if (conn.isClosed()) {
//				return;
//			}
//			conn.setAutoCommit(false);
//			String sql = new String("insert into coolingdep (trnsid,branch,trns_code,trns_no) values (?,?,?,?)");
//			PreparedStatement saveStatement = conn.prepareStatement(sql);
//			saveStatement.setLong(1, transId);
//			saveStatement.setString(2, branchCode);
//			saveStatement.setString(3, transCode);
//			saveStatement.setLong(4, transNo);	
//			saveStatement.executeUpdate();
//			saveStatement.close();
//			conn.commit(); 
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//	
//	public List getExternalBranch(DBConnection dbConnection) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder("select * from branch ");
//			return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	public List getAllExternalStore(DBConnection dbConnection) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				"select * from stroes ");
//		return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	public void saveStoreTransDep(DBConnection dbConnection,Long transNo,String transCode,
//			String branch,Long depOnTransNo ,String depOnTransCode,String depOnBranch) {
//		log.debug("transNo...."+transNo);
//		log.debug("transCode...."+transCode);
//		log.debug("branch...."+branch);
//		log.debug("onDeptransNo...."+depOnTransNo);
//		log.debug("onDeptransCode...."+depOnTransCode);
//		log.debug("onDepbranch...."+depOnBranch);
//		
//		BasicDataSource basicDataSource = createDataSource(dbConnection);
//		Connection conn = null;
//		try {
//			conn = basicDataSource.getConnection();
//			if (conn.isClosed()) {
//				return;
//			}
//			conn.setAutoCommit(false);
//			String sql = new String("insert into store_trns_dep (trns_code,trns_no,dep_on_trns_no,dep_on_trns_code,branch,dep_on_branch) values (?,?,?,?,?,?)");
//			PreparedStatement saveStatement = conn.prepareStatement(sql);
//			saveStatement.setString(1, transCode);
//			saveStatement.setLong(2, transNo);
//			saveStatement.setLong(3, depOnTransNo);
//			saveStatement.setString(4, depOnTransCode);
//			saveStatement.setString(5, branch);	
//			saveStatement.setString(6, depOnBranch);
//			saveStatement.executeUpdate();
//			saveStatement.close();
//			conn.commit(); 
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//	
//	public void saveStoreTransDepQty(StoreTransactionM storeTransaction ,DBConnection dbConnection,Long transNo,String transCode,
//			String branch,Long depOnTransNo ,String depOnTransCode,String depOnBranch) {
//		
//		log.debug("transNo...."+transNo);
//		log.debug("transCode...."+transCode);
//		log.debug("branch...."+branch);
//		log.debug("onDeptransNo...."+depOnTransNo);
//		log.debug("onDeptransCode...."+depOnTransCode);
//		log.debug("onDepbranch...."+depOnBranch);
//		
//		BasicDataSource basicDataSource = createDataSource(dbConnection);
//		Connection conn = null;
//		StoreTransactionO storeTransactionO = new StoreTransactionO();
//		ItemData itemData = new ItemData();
//		int serial = 1;
//		for(int i=0;i<storeTransaction.getStoreTransactionO().size();i++){
//			storeTransactionO = new StoreTransactionO();
//			itemData = new ItemData();
//			storeTransactionO = (StoreTransactionO) storeTransaction.getStoreTransactionO().get(i);
//			itemData = storeTransactionO.getItem_code();
//			try {
//				conn = basicDataSource.getConnection();
//				if (conn.isClosed()) {
//					return;
//				}
//				conn.setAutoCommit(false);
//				String sql = new String("insert into store_trns_dep_qty (branch,trns_code,trns_no,dep_on_branch,dep_on_trns_code,dep_on_trns_no,item_form,item_code,serial_no,dep_qty1,dep_qty2,dep_qty3) values (?,?,?,?,?,?,?,?,?,?,?,?)");
//				PreparedStatement saveStatement = conn.prepareStatement(sql);
//				saveStatement.setString(1, branch);
//				saveStatement.setString(2, transCode);
//				saveStatement.setLong(3, transNo);
//				saveStatement.setString(4, depOnBranch);
//				saveStatement.setString(5, depOnTransCode);
//				saveStatement.setLong(6, depOnTransNo);
//				saveStatement.setString(7, itemData.getEx_item_form());
//				saveStatement.setString(8, itemData.getEx_item_code());
//				saveStatement.setInt(9, serial);
//				saveStatement.setDouble(10, storeTransactionO.getActual_qty());
//				saveStatement.setDouble(11, storeTransactionO.getQty2());
//				saveStatement.setDouble(12, storeTransactionO.getQty3());
//				
//				saveStatement.executeUpdate();
//				saveStatement.close();
//				conn.commit(); 
//				serial++;
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} finally {
//				if (conn != null) {
//					try {
//						conn.close();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//	}
//	
//	public void updateStoreTransODepQty(DBConnection dbConnection,Long depOnTransNo ,String depOnTransCode,String depOnBranch) {
//		
//		
//		log.debug("onDeptransNo...."+depOnTransNo);
//		log.debug("onDeptransCode...."+depOnTransCode);
//		log.debug("onDepbranch...."+depOnBranch);
//		
//		BasicDataSource basicDataSource = createDataSource(dbConnection);
//		Connection conn = null;
//		
//			
//			try {
//				conn = basicDataSource.getConnection();
//				if (conn.isClosed()) {
//					return;
//				}
//				conn.setAutoCommit(false);
//				StringBuffer sqlDepQty1 = new StringBuffer(" update store_trns_o o set dep_qty1 =(select sum(dep_qty1) ");
//				sqlDepQty1.append(" from store_trns_dep_qty where  o.trns_code=dep_on_trns_code and o.trns_no=dep_on_trns_no ");
//				sqlDepQty1.append(" and o.item_form=item_form and o.item_code=item_code and o.serial_no=serial_no and o.branch=dep_on_branch ) ");
//				sqlDepQty1.append("where branch=?  and trns_code=? and trns_no =? ");
//				
//				PreparedStatement updateStatementDepQty1 = conn.prepareStatement(sqlDepQty1.toString());
//				updateStatementDepQty1.setString(1, depOnBranch);
//				updateStatementDepQty1.setString(2, depOnTransCode);
//				updateStatementDepQty1.setLong(3, depOnTransNo);
//				updateStatementDepQty1.executeUpdate();
//				updateStatementDepQty1.close();
//				conn.commit();
//				
//				conn.setAutoCommit(false);
//				StringBuffer sqlDepQty2 = new StringBuffer(" update store_trns_o o set dep_qty2 =(select sum(dep_qty2) ");
//				sqlDepQty2.append(" from store_trns_dep_qty where  o.trns_code=dep_on_trns_code and o.trns_no=dep_on_trns_no ");
//				sqlDepQty2.append(" and o.item_form=item_form and o.item_code=item_code and o.serial_no=serial_no and o.branch=dep_on_branch ) ");
//				sqlDepQty2.append("where branch=?  and trns_code=? and trns_no =? ");
//				
//				PreparedStatement updateStatementDepQty2 = conn.prepareStatement(sqlDepQty2.toString());
//				updateStatementDepQty2.setString(1, depOnBranch);
//				updateStatementDepQty2.setString(2, depOnTransCode);
//				updateStatementDepQty2.setLong(3, depOnTransNo);
//				updateStatementDepQty2.executeUpdate();
//				updateStatementDepQty2.close();
//				conn.commit();
//				
//				conn.setAutoCommit(false);
//				StringBuffer sqlDepQty3 = new StringBuffer(" update store_trns_o o set dep_qty3 =(select sum(dep_qty3) ");
//				sqlDepQty3.append(" from store_trns_dep_qty where  o.trns_code=dep_on_trns_code and o.trns_no=dep_on_trns_no ");
//				sqlDepQty3.append(" and o.item_form=item_form and o.item_code=item_code and o.serial_no=serial_no and o.branch=dep_on_branch ) ");
//				sqlDepQty3.append("where branch=?  and trns_code=? and trns_no =? ");
//				
//				PreparedStatement updateStatementDepQty3 = conn.prepareStatement(sqlDepQty3.toString());
//				updateStatementDepQty3.setString(1, depOnBranch);
//				updateStatementDepQty3.setString(2, depOnTransCode);
//				updateStatementDepQty3.setLong(3, depOnTransNo);
//				updateStatementDepQty3.executeUpdate();
//				updateStatementDepQty3.close();
//				conn.commit();
//				
//				
//				
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} finally {
//				if (conn != null) {
//					try {
//						conn.close();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		
//	}
//	
//	
//	public String getTransType(DBConnection dbConnection,String transTypeCode) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select trns_desc from stor_trns where trns_code='"+transTypeCode+"'");
//		list =  jdbcTemplate.queryForList(sql.toString());
//		ListOrderedMap type ;
//		String transType = null;
//		if(list.size()>0){
//			type = (ListOrderedMap) list.get(0);
//			transType = type.get("trns_desc").toString();
//		}	
//			return transType;
//	}
//	
//	public String getBranchName(DBConnection dbConnection,String branchCode) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select descr from branch where code='"+branchCode+"'");
//		list =  jdbcTemplate.queryForList(sql.toString());
//		ListOrderedMap branch ;
//		String branchDesc = null;
//		if(list.size()>0){
//			branch = (ListOrderedMap) list.get(0);
//			branchDesc = branch.get("descr").toString();
//		}	
//			return branchDesc;
//	}
//	
//	public List getExternalTransactionDetailNotByCostCenter(DBConnection dbConnection,
//			String transCode, String transNo, String branch,String itemCode,String itemForm) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				" select sum(qty1) as qty1,sum(qty2) as qty2,trns_code,trns_no,branch, "
//				+" item_form,item_code,cost_center_o "
//				+ " from store_trns_o where trns_code = '" + transCode
//						+ "' and trns_no = '" + transNo + "' and branch = '"
//						+ branch + "'"
//						+" and item_code= '"+ itemCode+"' and item_form = '"+itemForm+ "'"
//				+"group by trns_code,trns_no,branch,item_form,item_code,cost_center_o");
//		log.debug("sql Ex data...."+sql);
//		return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	public List getExternalTransactionDetailByCostCenter(DBConnection dbConnection,
//			String transCode, String transNo, String branch,String itemCode,String itemForm,String costCenter) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				" select sum(qty1) as qty1,sum(qty2) as qty2,trns_code,trns_no,branch, "
//				+" item_form,item_code,cost_center_o "
//				+ " from store_trns_o where trns_code = '" + transCode
//						+ "' and trns_no = '" + transNo + "' and branch = '"
//						+ branch + "' and cost_center_o= '"+ costCenter+"'"
//						+" and item_code= '"+ itemCode+"' and item_form = '"+itemForm+ "'"
//				+"group by trns_code,trns_no,branch,item_form,item_code,cost_center_o");
//		log.debug("sql Ex data...."+sql);
//		return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	

//	
//	public List getCoolingDepInfo(DBConnection dbConnection,String transId) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				" select * from coolingdep where trnsid = " + transId);
//		log.debug("sql Ex data...."+sql);
//		return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	public List getdestType(DBConnection dbConnection) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select name_type , type " 
//			+" from dist_names where type is not null "
//			+" group by name_type,type " 
//			+" order by type ");
//		
//		list =  jdbcTemplate.queryForList(sql.toString());
//		
//		ListOrderedMap type ;
//		
//		ExternalTypeAndCode externalTypeAndCode = new ExternalTypeAndCode();		
//		List typeList = new ArrayList();
//		
//		if(list.size()>0){
//			for(int x=0;x<list.size();x++){
//				externalTypeAndCode = new ExternalTypeAndCode();
//				type = (ListOrderedMap) list.get(x);
//				externalTypeAndCode.setTypeid(type.get("type").toString());
//				if(type.get("name_type")!=null){
//					externalTypeAndCode.setTypedesc(type.get("name_type").toString());
//				}else{
//					externalTypeAndCode.setTypedesc("");
//				}
//				
//				typeList.add(externalTypeAndCode);
//			}
//			
//		}	
//			return typeList;
//	}
//	public List getdestTypeByCode(DBConnection dbConnection) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select name_type , type " 
//			+" from dist_names where type is not null and type=3"
//			+" group by name_type,type " 
//			+" order by type ");
//		
//		list =  jdbcTemplate.queryForList(sql.toString());
//		
//		ListOrderedMap type ;
//		
//		ExternalTypeAndCode externalTypeAndCode = new ExternalTypeAndCode();		
//		List typeList = new ArrayList();
//		
//		if(list.size()>0){
//			for(int x=0;x<list.size();x++){
//				externalTypeAndCode = new ExternalTypeAndCode();
//				type = (ListOrderedMap) list.get(x);
//				externalTypeAndCode.setTypeid(type.get("type").toString());
//				if(type.get("name_type")!=null){
//					externalTypeAndCode.setTypedesc(type.get("name_type").toString());
//				}else{
//					externalTypeAndCode.setTypedesc("");
//				}
//				
//				typeList.add(externalTypeAndCode);
//			}
//			
//		}	
//			return typeList;
//	}
//	
//	public List getExtAgent(DBConnection dbConnection,String code) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select type_code ,name " 
//			+" from dist_names where type is not null and type_code='"+code+"' and name_type='agent'");
//			
//		
//		list =  jdbcTemplate.queryForList(sql.toString());
//		
//		ListOrderedMap type ;
//		
//		ExternalTypeAndCode externalTypeAndCode = new ExternalTypeAndCode();		
//		List agentList = new ArrayList();
//		
//		if(list.size()>0){
//				externalTypeAndCode = new ExternalTypeAndCode();
//				type = (ListOrderedMap) list.get(0);
//				externalTypeAndCode.setTypeid(type.get("type_code").toString());
//				if(type.get("name")!=null){
//					externalTypeAndCode.setTypedesc(type.get("name").toString());
//				}else{
//					externalTypeAndCode.setTypedesc("");
//				}
//				
//				agentList.add(externalTypeAndCode);
//			}
//			log.debug("sql...."+sql);
//			return agentList;
//	}
//	
//	public List getExtDriver(DBConnection dbConnection,String code) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select type_code ,name " 
//			+" from dist_names where type_code='"+code+"' and name_type='driver'");
//			
//		
//		list =  jdbcTemplate.queryForList(sql.toString());
//		
//		ListOrderedMap type ;
//		
//		ExternalTypeAndCode externalTypeAndCode = new ExternalTypeAndCode();		
//		List agentList = new ArrayList();
//		
//		if(list.size()>0){
//				externalTypeAndCode = new ExternalTypeAndCode();
//				type = (ListOrderedMap) list.get(0);
//				externalTypeAndCode.setTypeid(type.get("type_code").toString());
//				if(type.get("name")!=null){
//					externalTypeAndCode.setTypedesc(type.get("name").toString());
//				}else{
//					externalTypeAndCode.setTypedesc("");
//				}
//				
//				agentList.add(externalTypeAndCode);
//			}
//		log.debug("sql...."+sql);
//			return agentList;
//	}
//	
//	public List getExtCarCode(DBConnection dbConnection,String code) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select type_code ,name " 
//			+" from dist_names where type_code='"+code+"' and name_type='cars'");
//			
//		
//		list =  jdbcTemplate.queryForList(sql.toString());
//		
//		ListOrderedMap type ;
//		
//		ExternalTypeAndCode externalTypeAndCode = new ExternalTypeAndCode();		
//		List agentList = new ArrayList();
//		
//		if(list.size()>0){
//				externalTypeAndCode = new ExternalTypeAndCode();
//				type = (ListOrderedMap) list.get(0);
//				externalTypeAndCode.setTypeid(type.get("type_code").toString());
//				if(type.get("name")!=null){
//					externalTypeAndCode.setTypedesc(type.get("name").toString());
//				}else{
//					externalTypeAndCode.setTypedesc("");
//				}
//				
//				agentList.add(externalTypeAndCode);
//			}
//		log.debug("sql...."+sql);
//			return agentList;
//	}
//	
//	public List getExtCarType(DBConnection dbConnection,String code) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select type_code ,name " 
//			+" from dist_names where type_code='"+code+"' and name_type='car_type'");
//			
//		
//		list =  jdbcTemplate.queryForList(sql.toString());
//		
//		ListOrderedMap type ;
//		
//		ExternalTypeAndCode externalTypeAndCode = new ExternalTypeAndCode();		
//		List agentList = new ArrayList();
//		
//		if(list.size()>0){
//				externalTypeAndCode = new ExternalTypeAndCode();
//				type = (ListOrderedMap) list.get(0);
//				externalTypeAndCode.setTypeid(type.get("type_code").toString());
//				if(type.get("name")!=null){
//					externalTypeAndCode.setTypedesc(type.get("name").toString());
//				}else{
//					externalTypeAndCode.setTypedesc("");
//				}
//				
//				agentList.add(externalTypeAndCode);
//			}
//		log.debug("sql...."+sql);
//			return agentList;
//	}
//	
//	public List getExtFromArea(DBConnection dbConnection,String code) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select type_code ,name " 
//			+" from dist_names where type_code='"+code+"' and name_type='area'");
//			
//		
//		list =  jdbcTemplate.queryForList(sql.toString());
//		
//		ListOrderedMap type ;
//		
//		ExternalTypeAndCode externalTypeAndCode = new ExternalTypeAndCode();		
//		List agentList = new ArrayList();
//		
//		if(list.size()>0){
//				externalTypeAndCode = new ExternalTypeAndCode();
//				type = (ListOrderedMap) list.get(0);
//				externalTypeAndCode.setTypeid(type.get("type_code").toString());
//				if(type.get("name")!=null){
//					externalTypeAndCode.setTypedesc(type.get("name").toString());
//				}else{
//					externalTypeAndCode.setTypedesc("");
//				}
//				
//				agentList.add(externalTypeAndCode);
//			}
//		log.debug("sql...."+sql);
//			return agentList;
//	}
//	
//	public List getExtToArea(DBConnection dbConnection,String code) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select type_code ,name " 
//			+" from dist_names where type_code='"+code+"' and name_type='area'");
//			
//		
//		list =  jdbcTemplate.queryForList(sql.toString());
//		
//		ListOrderedMap type ;
//		
//		ExternalTypeAndCode externalTypeAndCode = new ExternalTypeAndCode();		
//		List agentList = new ArrayList();
//		
//		if(list.size()>0){
//				externalTypeAndCode = new ExternalTypeAndCode();
//				type = (ListOrderedMap) list.get(0);
//				externalTypeAndCode.setTypeid(type.get("type_code").toString());
//				if(type.get("name")!=null){
//					externalTypeAndCode.setTypedesc(type.get("name").toString());
//				}else{
//					externalTypeAndCode.setTypedesc("");
//				}
//				
//				agentList.add(externalTypeAndCode);
//			}
//		log.debug("sql...."+sql);
//			return agentList;
//	}
//	
//	public void saveExternalShippingData(DBConnection dbConnection,StoreTransactionM storeTransaction,String transCode,
//			String branch,ShippingData shippingData) {
//		
//		log.debug("transCode...."+transCode);
//		log.debug("branch...."+branch);
//		BigDecimal insertedToDest = null;
//		String insertedToCode = null;
//		BigDecimal fromDest = null;
//		String fromCode = null;
//		BigDecimal toDest = null;
//		String toCode = null;
//		ExternalDependenceTo externalDependenceTo = (ExternalDependenceTo) mgr.getObjectByParameter(ExternalDependenceTo.class, "storeTrnsDef", storeTransaction.getTrns_code());
//		
//		if (storeTransaction.getEx_shipping_document() != null
//				&& !storeTransaction.getEx_shipping_document().equals("")) {
//			String[] arrCodes = storeTransaction.getEx_shipping_document().split("_");
//			if (arrCodes.length == 3) {
//				List transactionList = getExternalTransaction(dbConnection,
//						arrCodes[0], arrCodes[1], arrCodes[2]);
//				if (transactionList.size() > 0) {
//					ListOrderedMap externalTransaction = (ListOrderedMap) transactionList.get(0);
//					fromDest = (BigDecimal) externalTransaction.get("FROM_DST");
//					fromCode = (String) externalTransaction.get("FROM_CODE");
//					toDest = (BigDecimal) externalTransaction.get("TO_DST");
//					toCode = (String) externalTransaction.get("TO_CODE");
//					if(externalDependenceTo != null && !externalDependenceTo.equals("") &&
//							externalDependenceTo.getToType() != null){
//						if(externalDependenceTo.getToType().equals("from")){
//							insertedToDest = fromDest;
//							insertedToCode = fromCode;
//						} else if(externalDependenceTo.getToType().equals("to")){
//							insertedToDest = toDest;
//							insertedToCode = toCode;
//						} else if(externalDependenceTo.getToType().equals("store")){
//							insertedToDest = new BigDecimal("4");
//							insertedToCode = storeTransaction.getBranch().getLehaa_code();
//						} else if(externalDependenceTo.getToType().equals("lost")){
//							insertedToDest = new BigDecimal("4");
//							insertedToCode = storeTransaction.getBranch().getLehaa_lost_code();
//						} else if(externalDependenceTo.getToType().equals("fLFromC")){
//							if(storeTransaction.getFromDestination()!= null
//									&& !storeTransaction.getFromDestination().equals("")
//									&& storeTransaction.getFromDestination().getExtDestcode() != null
//									&& !storeTransaction.getFromDestination().getExtDestcode().equals("")
//									&& !storeTransaction.getFromDestination().getExtDestcode().equals("0")){
//								insertedToDest = new BigDecimal(storeTransaction.getFromDestination().getGroupf().getExtDestination());
//								insertedToCode = storeTransaction.getFromDestination().getExtDestcode();
//							}
//							
//						} else if(externalDependenceTo.getToType().equals("tLFromC")){
//							if(storeTransaction.getToDestination()!= null
//									&& !storeTransaction.getToDestination().equals("")
//									&& storeTransaction.getToDestination().getExtDestcode() != null
//									&& !storeTransaction.getToDestination().getExtDestcode().equals("")
//									&& !storeTransaction.getToDestination().getExtDestcode().equals("0")){
//								insertedToDest = new BigDecimal(storeTransaction.getToDestination().getGroupf().getExtDestination());
//								insertedToCode = storeTransaction.getToDestination().getExtDestcode();
//							}
//							
//						} else if(externalDependenceTo.getFromType().equals("extDepFrom")
//								&& storeTransaction.getExtdestfrom()==true
//								&& storeTransaction.getExtdestfromid()!=null
//								&& storeTransaction.getExtdestfromcodeid() != null){
//							insertedToDest = new BigDecimal(storeTransaction.getExtdestfromid());
//							insertedToCode = storeTransaction.getExtdestfromcodeid();
//							
//						}else if(externalDependenceTo.getFromType().equals("extDepTo")
//								&& storeTransaction.getExtdestto()==true
//								&& storeTransaction.getExtdesttoid()!=null
//								&& storeTransaction.getExtdesttocodeid() != null){
//							insertedToDest = new BigDecimal(storeTransaction.getExtdesttoid());
//							insertedToCode = storeTransaction.getExtdesttocodeid();
//						}
//					}
//					
//					
//				}
//			}
//		}else if(storeTransaction.getTrns_code().getStoreTrnsDefSpec().getDep_without_ext_trans()==1){
//			if(storeTransaction.getStoreTransactionMDep()!=null){
//				List  mDepList =storeTransaction.getStoreTransactionMDep();
//				for(int j=0;j<mDepList.size();j++){
//					StoreTransactionMDep storeTransMDep = (StoreTransactionMDep) mDepList.get(j);
//					StoreTransactionM transM = storeTransMDep.getTrans_dep_id();
//				if(transM.getTrns_code().getStoreTrnsDefSpec().getIs_contract()!=1
//						&& transM.getTrns_code().getStoreTrnsDefSpec().getIs_invoice()!=1
//						&& transM.getTrns_code().getStoreTrnsDefSpec().getIs_reservation()!=1){
//					log.debug("TransactionMDep()......."+transM.getId());
//					log.debug("transM.getEx_shipping_document()......."+transM.getEx_shipping_document());
//					if (transM.getEx_shipping_document() != null
//							&& !transM.getEx_shipping_document().equals("")) {	
//						String[] arrCodes = transM.getEx_shipping_document().split("_");
//						if (arrCodes.length == 3) {
//							log.debug("1.............");
//							List transactionList = getExternalTransaction(dbConnection,
//									arrCodes[0], arrCodes[1], arrCodes[2]);
//							if (transactionList.size() > 0) {
//								log.debug("1.............");
//								ListOrderedMap externalTransaction = (ListOrderedMap) transactionList.get(0);
//								 fromDest = (BigDecimal) externalTransaction.get("FROM_DST");
//								 fromCode = (String) externalTransaction.get("FROM_CODE");
//								 toDest = (BigDecimal) externalTransaction.get("TO_DST");
//								 toCode = (String) externalTransaction.get("TO_CODE");
//							}
//						}
//					}
//					}
//				}
//				}
//			
//			if(externalDependenceTo != null && !externalDependenceTo.equals("") &&
//					externalDependenceTo.getToType() != null){
//				if(externalDependenceTo.getToType().equals("from")){
//					insertedToDest = fromDest;
//					insertedToCode = fromCode;
//				} else if(externalDependenceTo.getToType().equals("to")){
//					insertedToDest = toDest;
//					insertedToCode = toCode;
//				} else if(externalDependenceTo.getToType().equals("store")){
//					insertedToDest = new BigDecimal("4");
//					insertedToCode = storeTransaction.getBranch().getLehaa_code();
//				} else if(externalDependenceTo.getToType().equals("lost")){
//					insertedToDest = new BigDecimal("4");
//					insertedToCode = storeTransaction.getBranch().getLehaa_lost_code();
//				} else if(externalDependenceTo.getToType().equals("fLFromC")){
//					if(storeTransaction.getFromDestination()!= null
//							&& !storeTransaction.getFromDestination().equals("")
//							&& storeTransaction.getFromDestination().getExtDestcode() != null
//							&& !storeTransaction.getFromDestination().getExtDestcode().equals("")
//							&& !storeTransaction.getFromDestination().getExtDestcode().equals("0")){
//						insertedToDest = new BigDecimal(storeTransaction.getFromDestination().getGroupf().getExtDestination());
//						insertedToCode = storeTransaction.getFromDestination().getExtDestcode();
//					}
//					
//				} else if(externalDependenceTo.getToType().equals("tLFromC")){
//					if(storeTransaction.getToDestination()!= null
//							&& !storeTransaction.getToDestination().equals("")
//							&& storeTransaction.getToDestination().getExtDestcode() != null
//							&& !storeTransaction.getToDestination().getExtDestcode().equals("")
//							&& !storeTransaction.getToDestination().getExtDestcode().equals("0")){
//						insertedToDest = new BigDecimal(storeTransaction.getToDestination().getGroupf().getExtDestination());
//						insertedToCode = storeTransaction.getToDestination().getExtDestcode();
//					}
//					
//				} else if(externalDependenceTo.getFromType().equals("extDepFrom")
//						&& storeTransaction.getExtdestfrom()==true
//						&& storeTransaction.getExtdestfromid()!=null
//						&& storeTransaction.getExtdestfromcodeid() != null){
//					insertedToDest = new BigDecimal(storeTransaction.getExtdestfromid());
//					insertedToCode = storeTransaction.getExtdestfromcodeid();
//					
//				}else if(externalDependenceTo.getFromType().equals("extDepTo")
//						&& storeTransaction.getExtdestto()==true
//						&& storeTransaction.getExtdesttoid()!=null
//						&& storeTransaction.getExtdesttocodeid() != null){
//					insertedToDest = new BigDecimal(storeTransaction.getExtdesttoid());
//					insertedToCode = storeTransaction.getExtdesttocodeid();
//				}
//			}
//			
//		}
//		
//	
//		
//		BasicDataSource basicDataSource = createDataSource(dbConnection);
//		Connection conn = null;
//		try {
//			conn = basicDataSource.getConnection();
//			if (conn.isClosed()) {
//				return;
//			}
//			conn.setAutoCommit(false);
//			String sql = new String("insert into trnsport_m (TRNS_CODE,TRNS_NO,BRANCH,ROW_NU,TRNS_DATE," +
//					"AGENT_CODE,DRIVER_CODE,CAR_CODE,CAR_TYPE," +
//					"FROM_AREA,TO_AREA,TRANS_COST," +
//					"DRIVER_NAME,STAYING_NO,DRIVER_TEL,DRIVER_CAR_NO,DRIVER_CAR_OWNER,AGENT_DST,TO_DST,TO_CODE) " +
//					"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//			PreparedStatement saveStatement = conn.prepareStatement(sql);
//			saveStatement.setString(1, transCode);
//			saveStatement.setLong(2, storeTransaction.getTrns_no());
//			saveStatement.setString(3, branch);
//			saveStatement.setLong(4, shippingData.getRow_num());
//			java.sql.Date transDate = new java.sql.Date (storeTransaction.getTrns_date().getTime());
//			saveStatement.setDate(5, transDate);
//			saveStatement.setString(6, shippingData.getDestcode());
//			saveStatement.setString(7, shippingData.getDrivercode());
//			saveStatement.setString(8, shippingData.getCarcode());
//			saveStatement.setString(9, shippingData.getCartypecode());
//			saveStatement.setString(10, shippingData.getFromcode());
//			saveStatement.setString(11, shippingData.getTocode());
//			saveStatement.setDouble(12, shippingData.getRent_amount());
//			saveStatement.setString(13, shippingData.getDriver_name());
//			saveStatement.setString(14, shippingData.getAccommodation());
//			saveStatement.setString(15, shippingData.getTelephon_number());
//			saveStatement.setString(16, shippingData.getCar_number());
//			saveStatement.setString(17, shippingData.getCarown());
//			saveStatement.setDouble(18, 3);
//			if(insertedToDest != null && insertedToCode != null){
//				saveStatement.setBigDecimal(19, insertedToDest);
//				saveStatement.setString(20, insertedToCode);
//			}else{
//				saveStatement.setBigDecimal(19, null);
//				saveStatement.setString(20, null);
//			}			
//			saveStatement.executeUpdate();
//			saveStatement.close();
//			conn.commit(); 
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//	
//	public List getExtFromDestCode(DBConnection dbConnection,String type,String code) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select type_code ,name " 
//			+" from dist_names where type_code='"+code+"' and type="+type);
//			
//		
//		list =  jdbcTemplate.queryForList(sql.toString());
//		
//		ListOrderedMap typeMap ;
//		
//		ExternalTypeAndCode externalTypeAndCode = new ExternalTypeAndCode();		
//		List fromList = new ArrayList();
//		
//		if(list.size()>0){
//				externalTypeAndCode = new ExternalTypeAndCode();
//				typeMap = (ListOrderedMap) list.get(0);
//				externalTypeAndCode.setTypeid(typeMap.get("type_code").toString());
//				if(typeMap.get("name")!=null){
//					externalTypeAndCode.setTypedesc(typeMap.get("name").toString());
//				}else{
//					externalTypeAndCode.setTypedesc("");
//				}				
//				fromList.add(externalTypeAndCode);
//			}
//		log.debug("sql...."+sql);
//			return fromList;
//	}
//	
//	public List getExtToDestCode(DBConnection dbConnection,String type,String code) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select type_code ,name " 
//			+" from dist_names where type_code='"+code+"' and type="+type);
//			
//		
//		list =  jdbcTemplate.queryForList(sql.toString());
//		
//		ListOrderedMap typeMap ;
//		
//		ExternalTypeAndCode externalTypeAndCode = new ExternalTypeAndCode();		
//		List toList = new ArrayList();
//		
//		if(list.size()>0){
//				externalTypeAndCode = new ExternalTypeAndCode();
//				typeMap = (ListOrderedMap) list.get(0);
//				externalTypeAndCode.setTypeid(typeMap.get("type_code").toString());
//				if(typeMap.get("name")!=null){
//					externalTypeAndCode.setTypedesc(typeMap.get("name").toString());
//				}else{
//					externalTypeAndCode.setTypedesc("");
//				}
//				
//				toList.add(externalTypeAndCode);
//			}
//		log.debug("sql...."+sql);
//			return toList;
//	}
//	
//	public List getExtDestCode(DBConnection dbConnection,String destType,String destCode) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select name , type_code " 
//			+" from dist_names where type is not null and type='"+destType+"'"
//			+" and type_code ='"+destCode+"'"
//			+"  ");
//		
//		list =  jdbcTemplate.queryForList(sql.toString());
//		
//		ListOrderedMap type ;
//		
//		ExternalTypeAndCode externalTypeAndCode = new ExternalTypeAndCode();		
//		List typeList = new ArrayList();
//		
//		if(list.size()>0){
//			externalTypeAndCode = new ExternalTypeAndCode();
//				type = (ListOrderedMap) list.get(0);
//				externalTypeAndCode.setTypeid(type.get("type_code").toString());
//				if(type.get("name")!=null){
//					externalTypeAndCode.setTypedesc(type.get("name").toString());
//				}else{
//					externalTypeAndCode.setTypedesc("");
//				}
//				
//				typeList.add(externalTypeAndCode);
//			
//		}	
//			return typeList;
//	}
//	
//	public void deleteCoolingDepByTransaction(DBConnection dbConnection,Long transId) {
//		BasicDataSource basicDataSource = createDataSource(dbConnection);
//		Connection conn = null;
//		try {
//			conn = basicDataSource.getConnection();
//			if (conn.isClosed()) {
//				return;
//			}
//			conn.setAutoCommit(false);
//			
//			String sql = new String("delete from coolingdep where trnsid = ?");
//			PreparedStatement deleteStatement = conn.prepareStatement(sql);
//			deleteStatement.setLong(1, transId);
//			deleteStatement.executeUpdate();
//			deleteStatement.close();
//			conn.commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//	
//	public List getExternalDepartment(DBConnection dbConnection) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select name , type_code " 
//			+" from dist_names where type is not null and type=6"
//			+" and name_type ='departement'"
//			+"  ");
//		
//		list =  jdbcTemplate.queryForList(sql.toString());
//		
//		ListOrderedMap type ;
//		
//		ExternalTypeAndCode externalTypeAndCode = new ExternalTypeAndCode();		
//		List typeList = new ArrayList();
//		
//		for(int x=0;x<list.size();x++){
//			externalTypeAndCode = new ExternalTypeAndCode();
//				type = (ListOrderedMap) list.get(x);
//				externalTypeAndCode.setTypeid(type.get("type_code").toString());
//				if(type.get("name")!=null){
//					externalTypeAndCode.setTypedesc(type.get("name").toString());
//				}else{
//					externalTypeAndCode.setTypedesc("");
//				}
//				
//				typeList.add(externalTypeAndCode);
//			
//		}	
//			return typeList;
//	}
//	
//	public List getExtFromDestCodeByAjax(String type,String code) {
//		JdbcTemplate jt = new JdbcTemplate(DBUtils.getDataSource());
//		List connList = new ArrayList();
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select * from store_db_connection " 
//			+" where is_active='1'");
//		connList =  jt.queryForList(sql.toString());
//		
//		ListOrderedMap dbConnection ;
//		dbConnection = (ListOrderedMap) connList.get(0);
//		exjt =  new JdbcTemplate(createDataSource(dbConnection.get("host_name").toString(),dbConnection.get("service_name").toString(),dbConnection.get("user_name").toString(),dbConnection.get("password").toString()));
//		
//		sql = new StringBuilder("select type_code ,name " 
//			+" from dist_names where type_code='"+code+"' and type="+type);
//			
//		
//		list =  exjt.queryForList(sql.toString());
//		log.debug("list......."+list.size());
//		ListOrderedMap typeMap ;
//		
//		ExternalTypeAndCode externalTypeAndCode = new ExternalTypeAndCode();		
//		List fromList = new ArrayList();
//		
//		if(list.size()>0){
//				externalTypeAndCode = new ExternalTypeAndCode();
//				typeMap = (ListOrderedMap) list.get(0);
//				externalTypeAndCode.setTypeid(typeMap.get("type_code").toString());
//				if(typeMap.get("name")!=null){
//					externalTypeAndCode.setTypedesc(typeMap.get("name").toString());
//				}else{
//					externalTypeAndCode.setTypedesc("");
//				}
//				
//				fromList.add(externalTypeAndCode);
//			}
//		log.debug("sql...."+sql);
//			return fromList;
//	}
//	
//	public List getExternalTransactionO(DBConnection dbConnection,
//			String transCode, String transNo, String branchCode) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				" select * from store_trns_o where trns_code = '" + transCode
//						+ "' and trns_no = " + transNo + " and branch = '"
//						+ branchCode + "'");
//		log.debug("sql Ex data...." + sql);
//		return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	public List getExternalTransactionM(DBConnection dbConnection,
//			String transCode, String transNo, String branchCode, Date fromDate, Date toDate) {
//		
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//
//		StringBuilder sql = new StringBuilder(
//				" select * from store_trns_m where trns_code = '" + transCode
//						+ "' and trns_no = " + transNo + " and branch = '"
//						+ branchCode + "'");
//		
//		if(fromDate != null && !fromDate.equals("")){
//			sql.append(" and trns_date >= to_date('" + simpleDateFormat.format(fromDate) + "','dd-MM-YYYY')");	
//		}
//		if(toDate != null && !toDate.equals("")){
//			sql.append(" and trns_date <= to_date('" + simpleDateFormat.format(toDate) + "','dd-MM-YYYY')");
//		}	
//		
//		log.debug("sql of getExternalTransactionM ...." + sql);
//		return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	public List getExternalTransDefName(DBConnection dbConnection, String transCode) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				" select trns_desc from stor_trns where trns_code = '" + transCode + "'");
//		log.debug("sql Ex data...." + sql);
//		return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	public List getExternalBranchName(DBConnection dbConnection, String branchCode) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				" select descr from branch where code = '" + branchCode + "'");
//		log.debug("sql Ex data...." + sql);
//		return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	public String getItemGroupName(DBConnection dbConnection,String itemForm,String groupForm) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select descr from v_item_parts_desc where code_form='"+itemForm+"' and code='"+groupForm+"'");
//		
//		log.debug("sqlGroup... "+sql);
//		
//		list =  jdbcTemplate.queryForList(sql.toString());
//		ListOrderedMap group ;
//		String groupDesc = null;
//		if(list.size()>0){
//			group = (ListOrderedMap) list.get(0);
//			groupDesc = group.get("descr").toString();
//		}	
//			return groupDesc;
//	}
//	
////	public void saveUpdateExternalShippingData(DBConnection dbConnection,StoreTransactionM storeTransaction,String transCode,
////			String branch,ShippingData shippingData) {
////		
////		log.debug("transCode...."+transCode);
////		log.debug("branch...."+branch);
////		BigDecimal insertedToDest = null;
////		String insertedToCode = null;
////		BigDecimal fromDest = null;
////		String fromCode = null;
////		BigDecimal toDest = null;
////		String toCode = null;
////		ExternalDependenceTo externalDependenceTo = (ExternalDependenceTo) mgr.getObjectByParameter(ExternalDependenceTo.class, "storeTrnsDef", storeTransaction.getTrns_code());
////		
////		if (storeTransaction.getEx_shipping_document() != null
////				&& !storeTransaction.getEx_shipping_document().equals("")) {
////			String[] arrCodes = storeTransaction.getEx_shipping_document().split("_");
////			if (arrCodes.length == 3) {
////				List transactionList = getExternalTransaction(dbConnection,
////						arrCodes[0], arrCodes[1], arrCodes[2]);
////				if (transactionList.size() > 0) {
////					ListOrderedMap externalTransaction = (ListOrderedMap) transactionList.get(0);
////					fromDest = (BigDecimal) externalTransaction.get("FROM_DST");
////					fromCode = (String) externalTransaction.get("FROM_CODE");
////					toDest = (BigDecimal) externalTransaction.get("TO_DST");
////					toCode = (String) externalTransaction.get("TO_CODE");
////					if(externalDependenceTo != null && !externalDependenceTo.equals("") &&
////							externalDependenceTo.getToType() != null){
////						if(externalDependenceTo.getToType().equals("from")){
////							insertedToDest = fromDest;
////							insertedToCode = fromCode;
////						} else if(externalDependenceTo.getToType().equals("to")){
////							insertedToDest = toDest;
////							insertedToCode = toCode;
////						} else if(externalDependenceTo.getToType().equals("store")){
////							insertedToDest = new BigDecimal("4");
////							insertedToCode = storeTransaction.getBranch().getLehaa_code();
////						} else if(externalDependenceTo.getToType().equals("lost")){
////							insertedToDest = new BigDecimal("4");
////							insertedToCode = storeTransaction.getBranch().getLehaa_lost_code();
////						} else if(externalDependenceTo.getToType().equals("fLFromC")){
////							if(storeTransaction.getFromDestination()!= null
////									&& !storeTransaction.getFromDestination().equals("")
////									&& storeTransaction.getFromDestination().getExtDestcode() != null
////									&& !storeTransaction.getFromDestination().getExtDestcode().equals("")
////									&& !storeTransaction.getFromDestination().getExtDestcode().equals("0")){
////								insertedToDest = new BigDecimal(storeTransaction.getFromDestination().getGroupf().getExtDestination());
////								insertedToCode = storeTransaction.getFromDestination().getExtDestcode();
////							}
////							
////						} else if(externalDependenceTo.getToType().equals("tLFromC")){
////							if(storeTransaction.getToDestination()!= null
////									&& !storeTransaction.getToDestination().equals("")
////									&& storeTransaction.getToDestination().getExtDestcode() != null
////									&& !storeTransaction.getToDestination().getExtDestcode().equals("")
////									&& !storeTransaction.getToDestination().getExtDestcode().equals("0")){
////								insertedToDest = new BigDecimal(storeTransaction.getToDestination().getGroupf().getExtDestination());
////								insertedToCode = storeTransaction.getToDestination().getExtDestcode();
////							}
////							
////						} else if(externalDependenceTo.getFromType().equals("extDepFrom")
////								&& storeTransaction.getExtdestfrom()==true
////								&& storeTransaction.getExtdestfromid()!=null
////								&& storeTransaction.getExtdestfromcodeid() != null){
////							insertedToDest = new BigDecimal(storeTransaction.getExtdestfromid());
////							insertedToCode = storeTransaction.getExtdestfromcodeid();
////							
////						}else if(externalDependenceTo.getFromType().equals("extDepTo")
////								&& storeTransaction.getExtdestto()==true
////								&& storeTransaction.getExtdesttoid()!=null
////								&& storeTransaction.getExtdesttocodeid() != null){
////							insertedToDest = new BigDecimal(storeTransaction.getExtdesttoid());
////							insertedToCode = storeTransaction.getExtdesttocodeid();
////						}
////					}
////					
////					
////				}
////			}
////		}else if(storeTransaction.getTrns_code().getStoreTrnsDefSpec().getDep_without_ext_trans()==1){
////			if(storeTransaction.getStoreTransactionMDep()!=null){
////				List  mDepList =storeTransaction.getStoreTransactionMDep();
////				for(int j=0;j<mDepList.size();j++){
////					StoreTransactionMDep storeTransMDep = (StoreTransactionMDep) mDepList.get(j);
////					StoreTransactionM transM = storeTransMDep.getTrans_dep_id();
////				if(transM.getTrns_code().getStoreTrnsDefSpec().getIs_contract()!=1
////						&& transM.getTrns_code().getStoreTrnsDefSpec().getIs_invoice()!=1
////						&& transM.getTrns_code().getStoreTrnsDefSpec().getIs_reservation()!=1){
////					log.debug("TransactionMDep()......."+transM.getId());
////					log.debug("transM.getEx_shipping_document()......."+transM.getEx_shipping_document());
////					if (transM.getEx_shipping_document() != null
////							&& !transM.getEx_shipping_document().equals("")) {	
////						String[] arrCodes = transM.getEx_shipping_document().split("_");
////						if (arrCodes.length == 3) {
////							log.debug("1.............");
////							List transactionList = getExternalTransaction(dbConnection,
////									arrCodes[0], arrCodes[1], arrCodes[2]);
////							if (transactionList.size() > 0) {
////								log.debug("1.............");
////								ListOrderedMap externalTransaction = (ListOrderedMap) transactionList.get(0);
////								 fromDest = (BigDecimal) externalTransaction.get("FROM_DST");
////								 fromCode = (String) externalTransaction.get("FROM_CODE");
////								 toDest = (BigDecimal) externalTransaction.get("TO_DST");
////								 toCode = (String) externalTransaction.get("TO_CODE");
////							}
////						}
////					}
////					}
////				}
////				}
////			
////			if(externalDependenceTo != null && !externalDependenceTo.equals("") &&
////					externalDependenceTo.getToType() != null){
////				if(externalDependenceTo.getToType().equals("from")){
////					insertedToDest = fromDest;
////					insertedToCode = fromCode;
////				} else if(externalDependenceTo.getToType().equals("to")){
////					insertedToDest = toDest;
////					insertedToCode = toCode;
////				} else if(externalDependenceTo.getToType().equals("store")){
////					insertedToDest = new BigDecimal("4");
////					insertedToCode = storeTransaction.getBranch().getLehaa_code();
////				} else if(externalDependenceTo.getToType().equals("lost")){
////					insertedToDest = new BigDecimal("4");
////					insertedToCode = storeTransaction.getBranch().getLehaa_lost_code();
////				} else if(externalDependenceTo.getToType().equals("fLFromC")){
////					if(storeTransaction.getFromDestination()!= null
////							&& !storeTransaction.getFromDestination().equals("")
////							&& storeTransaction.getFromDestination().getExtDestcode() != null
////							&& !storeTransaction.getFromDestination().getExtDestcode().equals("")
////							&& !storeTransaction.getFromDestination().getExtDestcode().equals("0")){
////						insertedToDest = new BigDecimal(storeTransaction.getFromDestination().getGroupf().getExtDestination());
////						insertedToCode = storeTransaction.getFromDestination().getExtDestcode();
////					}
////					
////				} else if(externalDependenceTo.getToType().equals("tLFromC")){
////					if(storeTransaction.getToDestination()!= null
////							&& !storeTransaction.getToDestination().equals("")
////							&& storeTransaction.getToDestination().getExtDestcode() != null
////							&& !storeTransaction.getToDestination().getExtDestcode().equals("")
////							&& !storeTransaction.getToDestination().getExtDestcode().equals("0")){
////						insertedToDest = new BigDecimal(storeTransaction.getToDestination().getGroupf().getExtDestination());
////						insertedToCode = storeTransaction.getToDestination().getExtDestcode();
////					}
////					
////				} else if(externalDependenceTo.getFromType().equals("extDepFrom")
////						&& storeTransaction.getExtdestfrom()==true
////						&& storeTransaction.getExtdestfromid()!=null
////						&& storeTransaction.getExtdestfromcodeid() != null){
////					insertedToDest = new BigDecimal(storeTransaction.getExtdestfromid());
////					insertedToCode = storeTransaction.getExtdestfromcodeid();
////					
////				}else if(externalDependenceTo.getFromType().equals("extDepTo")
////						&& storeTransaction.getExtdestto()==true
////						&& storeTransaction.getExtdesttoid()!=null
////						&& storeTransaction.getExtdesttocodeid() != null){
////					insertedToDest = new BigDecimal(storeTransaction.getExtdesttoid());
////					insertedToCode = storeTransaction.getExtdesttocodeid();
////				}
////			}
////			
////		}
////		
////		BasicDataSource basicDataSource = createDataSource(dbConnection);
////		Connection conn = null;
////		try {
////			conn = basicDataSource.getConnection();
////			if (conn.isClosed()) {
////				return;
////			}
////			conn.setAutoCommit(false);
////			String sql = new String("insert into trnsport_m (TRNS_CODE,TRNS_NO,BRANCH,ROW_NU,TRNS_DATE," +
////					"AGENT_CODE,DRIVER_CODE,CAR_CODE,CAR_TYPE," +
////					"FROM_AREA,TO_AREA,TRANS_COST," +
////					"DRIVER_NAME,STAYING_NO,DRIVER_TEL,DRIVER_CAR_NO,DRIVER_CAR_OWNER,AGENT_DST) " +
////					"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
////			PreparedStatement saveStatement = conn.prepareStatement(sql);
////			saveStatement.setString(1, transCode);
////			saveStatement.setLong(2, storeTransaction.getTrns_no());
////			saveStatement.setString(3, branch);
////			saveStatement.setLong(4, shippingData.getRow_num());
////			java.sql.Date transDate = new java.sql.Date (storeTransaction.getTrns_date().getTime());
////			saveStatement.setDate(5, transDate);
////			saveStatement.setString(6, shippingData.getDestcode());
////			saveStatement.setString(7, shippingData.getDrivercode());
////			saveStatement.setString(8, shippingData.getCarcode());
////			saveStatement.setString(9, shippingData.getCartypecode());
////			saveStatement.setString(10, shippingData.getFromcode());
////			saveStatement.setString(11, shippingData.getTocode());
////			saveStatement.setDouble(12, shippingData.getRent_amount());
////			saveStatement.setString(13, shippingData.getDriver_name());
////			saveStatement.setString(14, shippingData.getAccommodation());
////			saveStatement.setString(15, shippingData.getTelephon_number());
////			saveStatement.setString(16, shippingData.getCar_number());
////			saveStatement.setString(17, shippingData.getCarown());
////			saveStatement.setDouble(18, 3);
////			saveStatement.executeUpdate();
////			saveStatement.close();
////			conn.commit(); 
////		} catch (SQLException e) {
////			e.printStackTrace();
////		} finally {
////			if (conn != null) {
////				try {
////					conn.close();
////				} catch (SQLException e) {
////					e.printStackTrace();
////				}
////			}
////		}
////	}
//	
//public void updateExternalShippingData(DBConnection dbConnection,StoreTransactionM storeTransaction,String transCode,
//		String branch,ShippingData shippingData) {
//		
//	BigDecimal insertedToDest = null;
//	String insertedToCode = null;
//	BigDecimal fromDest = null;
//	String fromCode = null;
//	BigDecimal toDest = null;
//	String toCode = null;
//	ExternalDependenceTo externalDependenceTo = (ExternalDependenceTo) mgr.getObjectByParameter(ExternalDependenceTo.class, "storeTrnsDef", storeTransaction.getTrns_code());
//	
//	if (storeTransaction.getEx_shipping_document() != null
//			&& !storeTransaction.getEx_shipping_document().equals("")) {
//		String[] arrCodes = storeTransaction.getEx_shipping_document().split("_");
//		if (arrCodes.length == 3) {
//			List transactionList = getExternalTransaction(dbConnection,
//					arrCodes[0], arrCodes[1], arrCodes[2]);
//			if (transactionList.size() > 0) {
//				ListOrderedMap externalTransaction = (ListOrderedMap) transactionList.get(0);
//				fromDest = (BigDecimal) externalTransaction.get("FROM_DST");
//				fromCode = (String) externalTransaction.get("FROM_CODE");
//				toDest = (BigDecimal) externalTransaction.get("TO_DST");
//				toCode = (String) externalTransaction.get("TO_CODE");
//				if(externalDependenceTo != null && !externalDependenceTo.equals("") &&
//						externalDependenceTo.getToType() != null){
//					if(externalDependenceTo.getToType().equals("from")){
//						insertedToDest = fromDest;
//						insertedToCode = fromCode;
//					} else if(externalDependenceTo.getToType().equals("to")){
//						insertedToDest = toDest;
//						insertedToCode = toCode;
//					} else if(externalDependenceTo.getToType().equals("store")){
//						insertedToDest = new BigDecimal("4");
//						insertedToCode = storeTransaction.getBranch().getLehaa_code();
//					} else if(externalDependenceTo.getToType().equals("lost")){
//						insertedToDest = new BigDecimal("4");
//						insertedToCode = storeTransaction.getBranch().getLehaa_lost_code();
//					} else if(externalDependenceTo.getToType().equals("fLFromC")){
//						if(storeTransaction.getFromDestination()!= null
//								&& !storeTransaction.getFromDestination().equals("")
//								&& storeTransaction.getFromDestination().getExtDestcode() != null
//								&& !storeTransaction.getFromDestination().getExtDestcode().equals("")
//								&& !storeTransaction.getFromDestination().getExtDestcode().equals("0")){
//							insertedToDest = new BigDecimal(storeTransaction.getFromDestination().getGroupf().getExtDestination());
//							insertedToCode = storeTransaction.getFromDestination().getExtDestcode();
//						}
//						
//					} else if(externalDependenceTo.getToType().equals("tLFromC")){
//						if(storeTransaction.getToDestination()!= null
//								&& !storeTransaction.getToDestination().equals("")
//								&& storeTransaction.getToDestination().getExtDestcode() != null
//								&& !storeTransaction.getToDestination().getExtDestcode().equals("")
//								&& !storeTransaction.getToDestination().getExtDestcode().equals("0")){
//							insertedToDest = new BigDecimal(storeTransaction.getToDestination().getGroupf().getExtDestination());
//							insertedToCode = storeTransaction.getToDestination().getExtDestcode();
//						}
//						
//					} else if(externalDependenceTo.getFromType().equals("extDepFrom")
//							&& storeTransaction.getExtdestfrom()==true
//							&& storeTransaction.getExtdestfromid()!=null
//							&& storeTransaction.getExtdestfromcodeid() != null){
//						insertedToDest = new BigDecimal(storeTransaction.getExtdestfromid());
//						insertedToCode = storeTransaction.getExtdestfromcodeid();
//						
//					}else if(externalDependenceTo.getFromType().equals("extDepTo")
//							&& storeTransaction.getExtdestto()==true
//							&& storeTransaction.getExtdesttoid()!=null
//							&& storeTransaction.getExtdesttocodeid() != null){
//						insertedToDest = new BigDecimal(storeTransaction.getExtdesttoid());
//						insertedToCode = storeTransaction.getExtdesttocodeid();
//					}
//				}
//				
//				
//			}
//		}
//	}else if(storeTransaction.getTrns_code().getStoreTrnsDefSpec().getDep_without_ext_trans()==1){
//		if(storeTransaction.getStoreTransactionMDep()!=null){
//			List  mDepList =storeTransaction.getStoreTransactionMDep();
//			for(int j=0;j<mDepList.size();j++){
//				StoreTransactionMDep storeTransMDep = (StoreTransactionMDep) mDepList.get(j);
//				StoreTransactionM transM = storeTransMDep.getTrans_dep_id();
//			if(transM.getTrns_code().getStoreTrnsDefSpec().getIs_contract()!=1
//					&& transM.getTrns_code().getStoreTrnsDefSpec().getIs_invoice()!=1
//					&& transM.getTrns_code().getStoreTrnsDefSpec().getIs_reservation()!=1){
//				log.debug("TransactionMDep()......."+transM.getId());
//				log.debug("transM.getEx_shipping_document()......."+transM.getEx_shipping_document());
//				if (transM.getEx_shipping_document() != null
//						&& !transM.getEx_shipping_document().equals("")) {	
//					String[] arrCodes = transM.getEx_shipping_document().split("_");
//					if (arrCodes.length == 3) {
//						log.debug("1.............");
//						List transactionList = getExternalTransaction(dbConnection,
//								arrCodes[0], arrCodes[1], arrCodes[2]);
//						if (transactionList.size() > 0) {
//							log.debug("1.............");
//							ListOrderedMap externalTransaction = (ListOrderedMap) transactionList.get(0);
//							 fromDest = (BigDecimal) externalTransaction.get("FROM_DST");
//							 fromCode = (String) externalTransaction.get("FROM_CODE");
//							 toDest = (BigDecimal) externalTransaction.get("TO_DST");
//							 toCode = (String) externalTransaction.get("TO_CODE");
//						}
//					}
//				}
//				}
//			}
//			}
//		
//		if(externalDependenceTo != null && !externalDependenceTo.equals("") &&
//				externalDependenceTo.getToType() != null){
//			if(externalDependenceTo.getToType().equals("from")){
//				insertedToDest = fromDest;
//				insertedToCode = fromCode;
//			} else if(externalDependenceTo.getToType().equals("to")){
//				insertedToDest = toDest;
//				insertedToCode = toCode;
//			} else if(externalDependenceTo.getToType().equals("store")){
//				insertedToDest = new BigDecimal("4");
//				insertedToCode = storeTransaction.getBranch().getLehaa_code();
//			} else if(externalDependenceTo.getToType().equals("lost")){
//				insertedToDest = new BigDecimal("4");
//				insertedToCode = storeTransaction.getBranch().getLehaa_lost_code();
//			} else if(externalDependenceTo.getToType().equals("fLFromC")){
//				if(storeTransaction.getFromDestination()!= null
//						&& !storeTransaction.getFromDestination().equals("")
//						&& storeTransaction.getFromDestination().getExtDestcode() != null
//						&& !storeTransaction.getFromDestination().getExtDestcode().equals("")
//						&& !storeTransaction.getFromDestination().getExtDestcode().equals("0")){
//					insertedToDest = new BigDecimal(storeTransaction.getFromDestination().getGroupf().getExtDestination());
//					insertedToCode = storeTransaction.getFromDestination().getExtDestcode();
//				}
//				
//			} else if(externalDependenceTo.getToType().equals("tLFromC")){
//				if(storeTransaction.getToDestination()!= null
//						&& !storeTransaction.getToDestination().equals("")
//						&& storeTransaction.getToDestination().getExtDestcode() != null
//						&& !storeTransaction.getToDestination().getExtDestcode().equals("")
//						&& !storeTransaction.getToDestination().getExtDestcode().equals("0")){
//					insertedToDest = new BigDecimal(storeTransaction.getToDestination().getGroupf().getExtDestination());
//					insertedToCode = storeTransaction.getToDestination().getExtDestcode();
//				}
//				
//			} else if(externalDependenceTo.getFromType().equals("extDepFrom")
//					&& storeTransaction.getExtdestfrom()==true
//					&& storeTransaction.getExtdestfromid()!=null
//					&& storeTransaction.getExtdestfromcodeid() != null){
//				insertedToDest = new BigDecimal(storeTransaction.getExtdestfromid());
//				insertedToCode = storeTransaction.getExtdestfromcodeid();
//				
//			}else if(externalDependenceTo.getFromType().equals("extDepTo")
//					&& storeTransaction.getExtdestto()==true
//					&& storeTransaction.getExtdesttoid()!=null
//					&& storeTransaction.getExtdesttocodeid() != null){
//				insertedToDest = new BigDecimal(storeTransaction.getExtdesttoid());
//				insertedToCode = storeTransaction.getExtdesttocodeid();
//			}
//		}
//		
//	}
//		
//		
//		BasicDataSource basicDataSource = createDataSource(dbConnection);
//		Connection conn = null;
//		
//			
//			try {
//				conn = basicDataSource.getConnection();
//				if (conn.isClosed()) {
//					return;
//				}
//				conn.setAutoCommit(false);
//				StringBuffer sql = new StringBuffer(" update trnsport_m m "+
//				" set AGENT_CODE=?,DRIVER_CODE=?,CAR_CODE=?,CAR_TYPE=?," +
//				" FROM_AREA=?,TO_AREA=?,TRANS_COST=?," +
//				" DRIVER_NAME=?,STAYING_NO=?,DRIVER_TEL=?,DRIVER_CAR_NO=?,DRIVER_CAR_OWNER=?,AGENT_DST=?,TO_DST=?,TO_CODE=? "+
//				" where TRNS_CODE=? and TRNS_NO=? and BRANCH=? and ROW_NU=? ");
//				
//				PreparedStatement updateStatement = conn.prepareStatement(sql.toString());
//				updateStatement.setString(1, shippingData.getDestcode());
//				updateStatement.setString(2, shippingData.getDrivercode());
//				updateStatement.setString(3, shippingData.getCarcode());
//				updateStatement.setString(4, shippingData.getCartypecode());
//				updateStatement.setString(5, shippingData.getFromcode());
//				updateStatement.setString(6, shippingData.getTocode());
//				updateStatement.setDouble(7, shippingData.getRent_amount());
//				updateStatement.setString(8, shippingData.getDriver_name());
//				updateStatement.setString(9, shippingData.getAccommodation());
//				updateStatement.setString(10, shippingData.getTelephon_number());
//				updateStatement.setString(11, shippingData.getCar_number());
//				updateStatement.setString(12, shippingData.getCarown());
//				updateStatement.setDouble(13, 3);
//				if(insertedToDest != null && insertedToCode != null){
//					updateStatement.setBigDecimal(14, insertedToDest);
//					updateStatement.setString(15, insertedToCode);
//				}else{
//					updateStatement.setBigDecimal(14, null);
//					updateStatement.setString(15, null);
//				}
//				updateStatement.setString(16, transCode);
//				updateStatement.setLong(17, storeTransaction.getTrns_no());
//				updateStatement.setString(18, branch);
//				updateStatement.setLong(19, shippingData.getRow_num());
//				
//				updateStatement.executeUpdate();
//				updateStatement.close();
//				conn.commit();
//				
//				conn.setAutoCommit(false);
//				
//				
//				
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} finally {
//				if (conn != null) {
//					try {
//						conn.close();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		
//	}
//
//	public List getExternalItemDataByItemForm(DBConnection dbConnection) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				"select * from item_data where item_form = '003' or item_form = '004' ");
//	return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	public List getExtDestByAjax(String code,String typeName) {
//		
//		JdbcTemplate jt = new JdbcTemplate(DBUtils.getDataSource());
//		List connList = new ArrayList();		
//		StringBuilder sqlCon = new StringBuilder("select * from store_db_connection " 
//			+" where is_active='1'");
//		connList =  jt.queryForList(sqlCon.toString());
//		
//		ListOrderedMap dbConnection ;
//		dbConnection = (ListOrderedMap) connList.get(0);
//		exjt =  new JdbcTemplate(createDataSource(dbConnection.get("host_name").toString(),dbConnection.get("service_name").toString(),dbConnection.get("user_name").toString(),dbConnection.get("password").toString()));
//		
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder("select type_code ,name " 
//			+" from dist_names where  type_code='"+code+"' and name_type='"+typeName+"'");
//			
//		
//		list =  exjt.queryForList(sql.toString());
//		
//		ListOrderedMap type ;
//		
//		ExternalTypeAndCode externalTypeAndCode = new ExternalTypeAndCode();		
//		List typeList = new ArrayList();
//		
//		if(list.size()>0){
//				externalTypeAndCode = new ExternalTypeAndCode();
//				type = (ListOrderedMap) list.get(0);
//				externalTypeAndCode.setTypeid(type.get("type_code").toString());
//				if(type.get("name")!=null){
//					externalTypeAndCode.setTypedesc(type.get("name").toString());
//				}else{
//					externalTypeAndCode.setTypedesc("");
//				}
//				
//				typeList.add(externalTypeAndCode);
//			}
//			log.debug("sql...."+sql);
//			return typeList;
//	}
//	
//		
//
//	public void deleteExternalShippingTrans(DBConnection dbConnection,
//			String transCode, String branchCode, Long transNo) {
//		BasicDataSource basicDataSource = createDataSource(dbConnection);
//		Connection conn = null;
//		try {
//			conn = basicDataSource.getConnection();
//			if (conn.isClosed()) {
//				return;
//			}
//			conn.setAutoCommit(false);
//			String sql = new String("delete from trnsport_m  where trns_code = ? and trns_no = ? and branch = ?");
//			PreparedStatement deleteStatement = conn.prepareStatement(sql);
//			deleteStatement.setString(1, transCode);
//			deleteStatement.setLong(2, transNo);
//			deleteStatement.setString(3, branchCode);
//			deleteStatement.executeUpdate();
//			deleteStatement.close();			
//			conn.commit();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//	
//	public List getAllExternalCostCenter(DBConnection dbConnection) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				"select costcode, costname, latin_name from cost where leafcost='T' ");
//		return jdbcTemplate.queryForList(sql.toString());
//	}
//	
//	public Integer getTransTypeCode(String transCode) {
//		JdbcTemplate jt = new JdbcTemplate(DBUtils.getDataSource());
//		List connList = new ArrayList();
//		
//		StringBuilder sqlCon = new StringBuilder("select * from store_db_connection " 
//			+" where is_active='1'");
//		connList =  jt.queryForList(sqlCon.toString());
//		
//		ListOrderedMap dbConnection ;
//		dbConnection = (ListOrderedMap) connList.get(0);
//		exjt =  new JdbcTemplate(createDataSource(dbConnection.get("host_name").toString(),dbConnection.get("service_name").toString(),dbConnection.get("user_name").toString(),dbConnection.get("password").toString()));
//
//		int transType =0;
//		StringBuilder sql = new StringBuilder(
//				"select trns_type from stor_trns where trns_code='"+transCode+"'");
//		List	list =  exjt.queryForList(sql.toString());	
//		ListOrderedMap type ;
//		if(list.size()>0){
//			type = (ListOrderedMap) list.get(0);
//			transType = new Integer (type.get("trns_type").toString()).intValue();
//		}
//		return transType;
//	}
//	
//	public boolean checkShippingData (DBConnection dbConnection,String transCode,String transNo,String branchCode,String rowNo){
//		boolean shippingFlag = false;
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				"select * from trnsport_m where TRNS_CODE='"+transCode+"' and TRNS_NO="+transNo+" and BRANCH='"+branchCode+"' and ROW_NU="+rowNo);
//		List	list = jdbcTemplate.queryForList(sql.toString());
//		if(list.size()>0){
//			return true;
//		}else {
//			return false;
//		}		
//	}
//	
//	public List getExternalGroupItems(DBConnection dbConnection) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		String descItem = "1575,1589,1606,1575,1601";
//		 if (descItem != null && !descItem.equals("")) {
//             String[] parts = descItem.split(",");
//             descItem = "";
//             for (int i = 0; i < parts.length; i++) {
//                 char c = (char) new Integer(parts[i]).intValue();
//                 descItem += c;
//             }
//         }
//		StringBuilder sql = new StringBuilder("select * from groupf where code_type like '%"+descItem+"%' ");
//		List list= jdbcTemplate.queryForList(sql.toString());
//		
//		ListOrderedMap group ;
//		ExternalTypeAndCode externalTypeAndCode = new ExternalTypeAndCode();		
//		List groupList = new ArrayList();
//		
//		for(int i=0;i<list.size();i++){
//				externalTypeAndCode = new ExternalTypeAndCode();
//				group = (ListOrderedMap) list.get(i);
//				externalTypeAndCode.setCodeid(group.get("code_form").toString());
//				externalTypeAndCode.setCodedesc(group.get("formdesc").toString());
//				groupList.add(externalTypeAndCode);
//			}
//		
//		return groupList;
//			
//	}
//	
//	public List getExternalItemDataByItemForm(DBConnection dbConnection,String itemForm) {
//		jdbcTemplate = new JdbcTemplate(createDataSource(dbConnection));
//		StringBuilder sql = new StringBuilder(
//				"select * from item_data where item_form = "+itemForm);
//	return jdbcTemplate.queryForList(sql.toString());
//	}
//

	public int insertTimeAttend (String hostName, String serviceName, String userName, String password, String emp_code, Date date_, Date time_, String trans_type) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		
		DefaultTransactionDefinition paramTransactionDefinition = new    DefaultTransactionDefinition();

		TransactionStatus status=platformTransactionManager.getTransaction(paramTransactionDefinition );
		log.debug("date_ " + date_);
		log.debug("simpleDateFormat.format(date_) " + simpleDateFormat.format(date_));
		
		basicDataSource = createDataSource(hostName,serviceName,userName,password);
		jdbcTemplate = new JdbcTemplate(basicDataSource);
		StringBuilder sql = new StringBuilder(
				" insert into time_attend (emp_code,date_,time_,trans_type) values ('" +emp_code
				+ "',to_date('" + simpleDateFormat.format(date_) + "','dd-MM-YYYY hh:mi:ss'),to_date('" + simpleDateFormat.format(time_) + "','dd-MM-YYYY hh:mi:ss'),'" + trans_type+"')");
		log.debug(sql.toString());
		try {
			jdbcTemplate.update(sql.toString());
			
			log.debug("will commit");
			platformTransactionManager.commit(status);
			return 1;
//			jdbcTemplate.getDataSource().getConnection().commit();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug("will rollback " + e.getMessage());
			platformTransactionManager.rollback(status);
			return -1;
		}
//		catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	public Long getVacationLimit (String hostName,String serviceName,String userName,String password, String empCode, String vacId, Date from_date){
//		Long result=null;
		Long cc1 = null,cc2 = null;
		int year; 
//		= from_date.getYear();
		
		log.debug("----fromdate---"+from_date);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        String from_dateString=df.format(from_date);
        log.debug("----from_dateString- after formatting--"+from_dateString);
        
        try {
        	log.debug("----xxxxxxxxxxxxxxxx-----");
			from_date=df.parse(from_dateString);
			log.debug("----from_date- after formatting--"+from_date);
			log.debug("----xxxxxxxxxxxxxxxx-----");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        MultiCalendarDate mCalDate = new MultiCalendarDate();
		if(from_dateString!=null){
			log.debug("-----date entered---"+from_dateString);
			mCalDate.setDateTimeString(from_dateString,new Boolean(true));
		}
		from_date = mCalDate.getDate();
		log.debug("----fromdate- after formatting--"+from_date);

		
        Calendar c = Calendar.getInstance();
        c.setTime(from_date);
        year=c.get(Calendar.YEAR);
        log.debug("----year---"+year);
//            System.out.println("Month = " + (c.get(Calendar.MONTH)));
//            System.out.println("Day = " + c.get(Calendar.DAY_OF_MONTH));

            
		
		jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));
		StringBuilder sql = new StringBuilder(
				" select entitled+previous from vac_limit where empcode = '" +empCode
						+ "' and vacation = '" + vacId + "' and year = '"
						+ year+"'");
		
		log.debug("----sql1---"+sql);
		try{
			cc1=jdbcTemplate.queryForLong(sql.toString());
			log.debug("----cc1---"+cc1);
		}catch (Exception e) {
			cc1=new Long(0);
		}
		
		return cc1;
	}

	
	@SuppressWarnings("deprecation")
	public Long getVacationLimit (String hostName,String serviceName,String userName,String password, String empCode, Long reqId, String vacId, Date from_date){
//		Long result=null;
		Long cc1 = null,cc2 = null;
		int year; 
//		= from_date.getYear();
		
		log.debug("----fromdate---"+from_date);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        String from_dateString=df.format(from_date);
        log.debug("----from_dateString- after formatting--"+from_dateString);
        
        try {
        	log.debug("----xxxxxxxxxxxxxxxx-----");
			from_date=df.parse(from_dateString);
			log.debug("----from_date- after formatting--"+from_date);
			log.debug("----xxxxxxxxxxxxxxxx-----");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        MultiCalendarDate mCalDate = new MultiCalendarDate();
		if(from_dateString!=null){
			log.debug("-----date entered---"+from_dateString);
			mCalDate.setDateTimeString(from_dateString,new Boolean(true));
		}
		from_date = mCalDate.getDate();
		log.debug("----fromdate- after formatting--"+from_date);

		
        Calendar c = Calendar.getInstance();
        c.setTime(from_date);
        year=c.get(Calendar.YEAR);
        log.debug("----year---"+year);
//            System.out.println("Month = " + (c.get(Calendar.MONTH)));
//            System.out.println("Day = " + c.get(Calendar.DAY_OF_MONTH));

            
		
		jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));
		StringBuilder sql = new StringBuilder(
				" select entitled+previous from vac_limit where empcode = '" +empCode
						+ "' and vacation = '" + vacId + "' and year = '"
						+ year+"'");
		
		log.debug("----sql1---"+sql);
		try{
			cc1=jdbcTemplate.queryForLong(sql.toString());
			log.debug("----cc1---"+cc1);
		}catch (Exception e) {
			cc1=new Long(0);
		}
		
		return cc1;
	}
	
	public Long getEmpVacation (String hostName,String serviceName,String userName,String password, String empCode, String vacId, Date from_date){
//		Long result=null;
		Long cc1 = null,cc2 = null;
		int year; 
//		= from_date.getYear();
		
		log.debug("----fromdate---"+from_date);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        String from_dateString=df.format(from_date);
        log.debug("----from_dateString- after formatting--"+from_dateString);
        
        try {
        	log.debug("----xxxxxxxxxxxxxxxx-----");
			from_date=df.parse(from_dateString);
			log.debug("----from_date- after formatting--"+from_date);
			log.debug("----xxxxxxxxxxxxxxxx-----");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        MultiCalendarDate mCalDate = new MultiCalendarDate();
		if(from_dateString!=null){
			log.debug("-----date entered---"+from_dateString);
			mCalDate.setDateTimeString(from_dateString,new Boolean(true));
		}
		from_date = mCalDate.getDate();
		log.debug("----fromdate- after formatting--"+from_date);

		
        Calendar c = Calendar.getInstance();
        c.setTime(from_date);
        year=c.get(Calendar.YEAR);
        log.debug("----year---"+year);
		
		Date dd1=null;
		c.set(year, 00, 01);
		dd1=c.getTime();
		log.debug("----dd1---"+dd1);
		
		String dd1String=df.format(dd1);
        log.debug("----dd1String- after formatting--"+dd1String);

		if(dd1String!=null){
			log.debug("-----dd1 -string -"+dd1String);
			mCalDate.setDateString(dd1String);
		}
		dd1 = mCalDate.getDate();
		log.debug("----dd1- after formatting--"+dd1);
		
		
		StringBuilder sql =new StringBuilder(" select sum(withdr) from empvac where empcode = '" +empCode
						+ "' and vacation = '" + vacId + "' and fr_date < to_date ('"
						+ from_dateString+"', 'DD-MM-YYYY') and fr_date >= to_date('" + dd1String+"', 'DD-MM-YYYY')");
		
		log.debug("----sql---"+sql);
		try{
			cc2=jdbcTemplate.queryForLong(sql.toString());
		}catch (Exception e) {
			cc2=new Long(0);
		}
		
//		log.debug("----cc2---"+cc2);
//		Long result=cc1-cc2;
//		log.debug("----cc1-cc2---"+result);

		return cc2;
	}

	
	@SuppressWarnings("deprecation")
	public Long getEmpVacation (String hostName,String serviceName,String userName,String password, String empCode, Long reqId, String vacId, Date from_date){
//		Long result=null;
		Long cc1 = null,cc2 = null;
		int year; 
//		= from_date.getYear();
		
		log.debug("----fromdate---"+from_date);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        String from_dateString=df.format(from_date);
        log.debug("----from_dateString- after formatting--"+from_dateString);
        
        try {
        	log.debug("----xxxxxxxxxxxxxxxx-----");
			from_date=df.parse(from_dateString);
			log.debug("----from_date- after formatting--"+from_date);
			log.debug("----xxxxxxxxxxxxxxxx-----");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        MultiCalendarDate mCalDate = new MultiCalendarDate();
		if(from_dateString!=null){
			log.debug("-----date entered---"+from_dateString);
			mCalDate.setDateTimeString(from_dateString,new Boolean(true));
		}
		from_date = mCalDate.getDate();
		log.debug("----fromdate- after formatting--"+from_date);

		
        Calendar c = Calendar.getInstance();
        c.setTime(from_date);
        year=c.get(Calendar.YEAR);
        log.debug("----year---"+year);
		
		Date dd1=null;
		c.set(year, 00, 01);
		dd1=c.getTime();
		log.debug("----dd1---"+dd1);
		
		String dd1String=df.format(dd1);
        log.debug("----dd1String- after formatting--"+dd1String);

		if(dd1String!=null){
			log.debug("-----dd1 -string -"+dd1String);
			mCalDate.setDateString(dd1String);
		}
		dd1 = mCalDate.getDate();
		log.debug("----dd1- after formatting--"+dd1);
		
		
		StringBuilder sql =new StringBuilder(" select sum(withdr) from empvac where empcode = '" +empCode
						+ "' and vacation = '" + vacId + "' and fr_date < to_date ('"
						+ from_dateString+"', 'DD-MM-YYYY') and fr_date >= to_date('" + dd1String+"', 'DD-MM-YYYY')");
		
		log.debug("----sql---"+sql);
		try{
			cc2=jdbcTemplate.queryForLong(sql.toString());
		}catch (Exception e) {
			cc2=new Long(0);
		}
		
//		log.debug("----cc2---"+cc2);
//		Long result=cc1-cc2;
//		log.debug("----cc1-cc2---"+result);

		return cc2;
	}
	
	@SuppressWarnings("deprecation")
	public Long getVacationCredit (String hostName,String serviceName,String userName,String password, String empCode, Long reqId, String vacId, Date from_date){
//		Long result=null;
		Long cc1 = null,cc2 = null;
		int year; 
//		= from_date.getYear();
		
		log.debug("getVacationCredit----fromdate---"+from_date);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        String from_dateString=df.format(from_date);
        log.debug("getVacationCredit----from_dateString- after formatting--"+from_dateString);
        
        try {
        	log.debug("----xxxxxxxxxxxxxxxx-----");
			from_date=df.parse(from_dateString);
			log.debug("getVacationCredit----from_date- after formatting--"+from_date);
			log.debug("----xxxxxxxxxxxxxxxx-----");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        MultiCalendarDate mCalDate = new MultiCalendarDate();
		if(from_dateString!=null){
			log.debug("-----date entered---"+from_dateString);
			mCalDate.setDateTimeString(from_dateString,new Boolean(true));
		}
		from_date = mCalDate.getDate();
		log.debug("----fromdate- after formatting--"+from_date);

		
        Calendar c = Calendar.getInstance();
        c.setTime(from_date);
        year=c.get(Calendar.YEAR);
        log.debug("----year---"+year);
//            System.out.println("Month = " + (c.get(Calendar.MONTH)));
//            System.out.println("Day = " + c.get(Calendar.DAY_OF_MONTH));

            
        log.debug("getVacationCredit: host " + hostName + " serviceName " + serviceName + " username " + userName + " password " + password);
		
		jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));
		StringBuilder sql = new StringBuilder(
				" select entitled+previous from vac_limit where empcode = '" +empCode
						+ "' and vacation = '" + vacId + "' and year = '"
						+ year+"'");
		
		log.debug("getVacationCredit----sql1---"+sql);
		try{
			cc1=jdbcTemplate.queryForLong(sql.toString());
			log.debug("----cc1---"+cc1);
		}catch (Exception e) {
			e.printStackTrace();
			cc1=new Long(0);
		}
		
		Date dd1=null;
		c.set(year, 00, 01);
		dd1=c.getTime();
		log.debug("----dd1---"+dd1);
		
		String dd1String=df.format(dd1);
        log.debug("----dd1String- after formatting--"+dd1String);

		if(dd1String!=null){
			log.debug("-----dd1 -string -"+dd1String);
			mCalDate.setDateString(dd1String);
		}
		dd1 = mCalDate.getDate();
		log.debug("----dd1- after formatting--"+dd1);
		
		
		sql =new StringBuilder(" select sum(withdr) from empvac where empcode = '" +empCode
						+ "' and vacation = '" + vacId + "' and fr_date < to_date ('"
						+ from_dateString+"', 'DD-MM-YYYY') and fr_date >= to_date('" + dd1String+"', 'DD-MM-YYYY')");
		
		log.debug("----sql2---"+sql);
		try{
			cc2=jdbcTemplate.queryForLong(sql.toString());
		}catch (Exception e) {
			cc2=new Long(0);
		}
		
		log.debug("----cc2---"+cc2);
		Long result=cc1-cc2;
		log.debug("----cc1-cc2---"+result);

		return result;
	}
	
	public List getTimeAttend (String hostName,String serviceName,String userName,String password, String empCode, Date from_date, Date to_date){
		
		List result = new ArrayList();
		List totalList = new ArrayList();
		
		log.debug("----fromdate---"+from_date);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        String from_dateString=df.format(from_date);
        log.debug("----from_dateString- after formatting--"+from_dateString);
        
        String to_dateString=df.format(to_date);
        log.debug("----to_dateString- after formatting--"+to_dateString);
        try {
        	log.debug("----xxxxxxxxxxxxxxxx-----");
			to_date=df.parse(to_dateString);
			log.debug("----to_dateString- after formatting--"+to_date);
			log.debug("----xxxxxxxxxxxxxxxx-----");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        MultiCalendarDate mCalDate = new MultiCalendarDate();
		if(from_dateString!=null){
			log.debug("-----date entered---"+from_dateString);
			mCalDate.setDateTimeString(from_dateString,new Boolean(true));
		}
		from_date = mCalDate.getDate();
		log.debug("----fromdate- after formatting--"+from_date);
		
		String emp = "";
		if (empCode!= null && !empCode.equals("")){
			emp = " emp_code in (" +empCode+ ") and ";
		}
		jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));
		StringBuilder sql = new StringBuilder(
				" select min(time_) as minDate, max(time_) as maxDate, emp_code as emp , e.firstName fName, e.lastName lName " 
		+" from time_attend t, common_employee e where " + emp
						+ " e.empCode=emp_code and date_ between to_date('" + from_dateString + "', 'DD/MM/YYYY') and to_date('"
						+ to_dateString+"', 'DD/MM/YYYY') group by date_, emp_code,e.firstName,e.lastName order by date_");
		log.debug("----sql 1---"+sql);

		
//		StringBuilder sql = new StringBuilder(
//				" select min(time_) as minDate, max(time_) as maxDate from time_attend t where emp_code='" +empCode
//						+ "' and date_ >= to_date('" + from_dateString + "', 'DD/MM/YYYY') and date_<=to_date('"
//						+ to_dateString+"', 'DD/MM/YYYY')  group by date_, emp_code");
//		log.debug("----sql 1---"+sql);
		
		
		List in=(List) jdbcTemplate.queryForList(sql.toString());
		
			
		LinkedCaseInsensitiveMap inMap ;

		String minDate = null;
		
		log.debug("----in---"+in);		


		if(to_dateString!=null){
			log.debug("-----to_dateString entered---"+to_dateString);
			mCalDate.setDateTimeString(to_dateString,new Boolean(true));
		}
		to_date = mCalDate.getDate();
		log.debug("----to_dateString- after formatting--"+to_date);
		
		String maxDate = null;
		
		TimeAttend timeAttend=null;
		Date inDate=null, outDate= null;
		long totalMins=0, totalHrs=0;
		for(int i=0;i<in.size();i++){
			timeAttend=new TimeAttend();
			log.debug("in.get(i) " + in.get(i).getClass());
			inMap = (LinkedCaseInsensitiveMap) in.get(i);
			minDate = inMap.get("minDate").toString();
			log.debug("----minDate---"+minDate);
			
//			minDate=minDate.substring(0,19);
			log.debug("----minDate---"+minDate);
			
			DateFormat d= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S",Locale.US);
			
			try {
				inDate=d.parse(minDate);
				log.debug("inDate  = "+inDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			mCalDate = new MultiCalendarDate();
//			mCalDate.setDateTimeString(minDate.substring(0, minDate.length()-2));
//			inDate=mCalDate.getDate();
			
			
			
			 //df=new SimpleDateFormat("dd/MM/yyyy");
		//	 log.debug("----Date1111---"+ inMap.get("dateDay").toString());
			
			//timeAttend.setDateDay(new Date (inMap.get("minDate").toString()));
			
			
			maxDate = inMap.get("maxDate").toString();
//			maxDate=maxDate.substring(0,19);
			log.debug("----maxDate---"+maxDate);
//			mCalDate = new MultiCalendarDate();
//			mCalDate.setDateTimeString(maxDate);
//			outDate=mCalDate.getDate();
			try {
				outDate=d.parse(maxDate);
				log.debug("outDate  = "+outDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(inDate!=null || outDate!=null){
				long diffHrs= (outDate.getTime()-inDate.getTime())/(1000*60*60);
				long diffMins= ((outDate.getTime()-inDate.getTime())%(1000*60*60))/(1000*60);
				log.debug("diffHrs=== "+diffHrs);
				log.debug("diffMins=== "+diffMins);
				totalMins+=diffMins;
				totalHrs+=diffHrs;
				timeAttend.setDiffHrs(diffHrs+"");
				timeAttend.setDiffMins(diffMins+"");
			}
			
			
//			log.debug("outDate  = "+outDate);
			//log.debug("----DateIn---"+test);
			if(minDate.equals(maxDate)){
				timeAttend.setTimeOut(null);
			}
			else{			
				maxDate=maxDate.substring(0, maxDate.length()-5);
				log.debug("----maxDate.substring(10)----"+maxDate.substring(10));
				timeAttend.setTimeOut(maxDate.substring(10));
			}
			minDate=minDate.substring(0, minDate.length()-5);
			log.debug("----minDate.substring(10)----"+minDate.substring(10));
			timeAttend.setTimeIn(minDate.substring(10));
			log.debug("----minDate.substring(0,10)----"+minDate.substring(0,10));
			String date1=minDate.substring(0,10);
			String[] letters=date1.split("-");
			 
			//for (int j = 0; j < letters.length; j++) {
			//	log.debug("-----letters-0--"+letters[j]);
				int year= Integer.parseInt(letters[0]);
				int month =Integer.parseInt(letters[1]);
				int day =Integer.parseInt(letters[2]);
				String dateString=year+"/"+month+"/"+day;
				log.debug("-----dateString-0--"+dateString);
				timeAttend.setDay(dateString);
				
				mCalDate.setDateString(dateString);
				Date date=mCalDate.getDate();
				log.debug("-----date-0--"+date);
				//log.debug("----after parsing--"+df.format(date));
				//mCalDate.setDate(date);
				//log.debug("---mCalDate--date-0--"+mCalDate.getDate());
				
				SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE",new Locale("ar","SA")); // the day of the week spelled out completely  
				log.debug("-------simpleDateformat.format(mCalDate.getDate())---"+simpleDateformat.format(mCalDate.getDate()));
				timeAttend.setDayString(simpleDateformat.format(mCalDate.getDate()));
				
				String empStr =  inMap.get("emp").toString();
				timeAttend.setEmployee(empStr);
				
				String empName =  inMap.get("fName").toString();
				timeAttend.setEmpName(empName);
			//}
			//timeAttend.setDay();
			log.debug("------timeAttend.getDay()---"+timeAttend.getDay());
			result.add(timeAttend);
			log.debug("------end of loop---");
		}
		totalList.add(result);
		totalList.add(totalMins+","+totalHrs);
		
//		for (int i = 0; i < result.size(); i++) {
//			TimeAttend x = (TimeAttend) result.get(i);
//			log.debug("-----from result---"+x.getDay()+"------in---"+x.getTimeIn()+"----out---"+x.getTimeOut());
//		}
		 
		
		
		//result.add(out);
		
		
		return totalList;
		
	}
	
	
public List getTimeAttendAndroid (String hostName,String serviceName,String userName,String password, String empCode, Date from_date, Date to_date){
		
		List result = new ArrayList();
		List totalList = new ArrayList();
		
		log.debug("----fromdate---"+from_date);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        String from_dateString=df.format(from_date);
        log.debug("----from_dateString- after formatting--"+from_dateString);
        
        String to_dateString=df.format(to_date);
        log.debug("----to_dateString- after formatting--"+to_dateString);
        try {
        	log.debug("----xxxxxxxxxxxxxxxx-----");
			to_date=df.parse(to_dateString);
			log.debug("----to_dateString- after formatting--"+to_date);
			log.debug("----xxxxxxxxxxxxxxxx-----");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        MultiCalendarDate mCalDate = new MultiCalendarDate();
		if(from_dateString!=null){
			log.debug("-----date entered---"+from_dateString);
			mCalDate.setDateTimeString(from_dateString,new Boolean(true));
		}
		from_date = mCalDate.getDate();
		log.debug("----fromdate- after formatting--"+from_date);
		
		String emp = "";
		if (empCode!= null && !empCode.equals("")){
			emp = " t.empcode='" +empCode+ "' and ";
		}
		jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));
		StringBuilder sql = new StringBuilder(
				" select min(t.period_from) as minDate, max(t.period_from) as maxDate, t.empcode as emp , e.firstName fName, e.lastName lName " 
		+" from LOGIN_USERS_REQUESTS t, common_employee e where " + emp
						+ " e.empCode=t.empcode and t.from_date between to_date('" + from_dateString + "', 'DD/MM/YYYY') and to_date('"
						+ to_dateString+"', 'DD/MM/YYYY') group by t.from_date, t.empcode,e.firstName,e.lastName order by t.from_date");
		log.debug("----sql 1---"+sql);

		
		
		List in=(List) jdbcTemplate.queryForList(sql.toString());
		
			
		LinkedCaseInsensitiveMap inMap ;
		LinkedCaseInsensitiveMap inMap2 ;

		String minDate = null;
		
		
		log.debug("----in---"+in);		
		//result.add(minDate);


		if(to_dateString!=null){
			log.debug("-----to_dateString entered---"+to_dateString);
			mCalDate.setDateTimeString(to_dateString,new Boolean(true));
		}
		to_date = mCalDate.getDate();
		log.debug("----to_dateString- after formatting--"+to_date);
		
		String maxDate = null;
		
		TimeAttend timeAttend=null;
		Date inDate=null, outDate= null;
		long totalMins=0, totalHrs=0;
		
		int i=0;
		while(i<in.size()){
			timeAttend=new TimeAttend();
			log.debug("in.get(i) " + in.get(i).getClass());
			inMap = (LinkedCaseInsensitiveMap) in.get(i);
			minDate = inMap.get("minDate").toString();
			log.debug("----minDate---"+minDate);
			
//			minDate=minDate.substring(0,19);
			log.debug("----minDate---"+minDate);
			
			DateFormat d= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S",Locale.US);
			
			try {
				inDate=d.parse(minDate);
				log.debug("inDate  = "+inDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
			if (i<in.size()) {
				inMap2 = (LinkedCaseInsensitiveMap) in.get(i);
				maxDate = inMap2.get("maxDate").toString();
				//			maxDate=maxDate.substring(0,19);
				log.debug("----maxDate---"+maxDate);
				//			mCalDate = new MultiCalendarDate();
				//			mCalDate.setDateTimeString(maxDate);
				//			outDate=mCalDate.getDate();
				try {
					outDate=d.parse(maxDate);
					log.debug("outDate  = "+outDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				maxDate = null;
				outDate = null;
			}
			log.debug("indate " + inDate + " outdate " + outDate);
			if(inDate!=null && outDate!=null){
				long diffHrs= (outDate.getTime()-inDate.getTime())/(1000*60*60);
				long diffMins= ((outDate.getTime()-inDate.getTime())%(1000*60*60))/(1000*60);
				log.debug("diffHrs=== "+diffHrs);
				log.debug("diffMins=== "+diffMins);
				totalMins+=diffMins;
				totalHrs+=diffHrs;
				timeAttend.setDiffHrs(diffHrs+"");
				timeAttend.setDiffMins(diffMins+"");
			}
			
			
			if (maxDate!=null) {
				if(minDate.equals(maxDate)){
					timeAttend.setTimeOut(null);
				}
				else{			
					maxDate=maxDate.substring(0, maxDate.length()-5);
					log.debug("----maxDate.substring(10)----"+maxDate.substring(10));
					timeAttend.setTimeOut(maxDate.substring(10));
				}
			}
			
			minDate=minDate.substring(0, minDate.length()-5);
			log.debug("----minDate.substring(10)----"+minDate.substring(10));
			timeAttend.setTimeIn(minDate.substring(10));
			log.debug("----minDate.substring(0,10)----"+minDate.substring(0,10));
			String date1=minDate.substring(0,10);
			String[] letters=date1.split("-");
			 
			//for (int j = 0; j < letters.length; j++) {
			//	log.debug("-----letters-0--"+letters[j]);
				int year= Integer.parseInt(letters[0]);
				int month =Integer.parseInt(letters[1]);
				int day =Integer.parseInt(letters[2]);
				String dateString=year+"/"+month+"/"+day;
				log.debug("-----dateString-0--"+dateString);
				timeAttend.setDay(dateString);
				
				mCalDate.setDateString(dateString);
				Date date=mCalDate.getDate();
				log.debug("-----date-0--"+date);
				//log.debug("----after parsing--"+df.format(date));
				//mCalDate.setDate(date);
				//log.debug("---mCalDate--date-0--"+mCalDate.getDate());
				
				SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE",new Locale("ar","SA")); // the day of the week spelled out completely  
				log.debug("-------simpleDateformat.format(mCalDate.getDate())---"+simpleDateformat.format(mCalDate.getDate()));
				timeAttend.setDayString(simpleDateformat.format(mCalDate.getDate()));
				
				String empStr =  inMap.get("emp").toString();
				timeAttend.setEmployee(empStr);
				
				String empName =  inMap.get("fName").toString();
				timeAttend.setEmpName(empName);
			//}
			//timeAttend.setDay();
			log.debug("------timeAttend.getDay()---"+timeAttend.getDay());
			result.add(timeAttend);
			i++;
			log.debug("------end of loop---");
		}
		totalList.add(result);
		totalList.add(totalMins+","+totalHrs);
		
//		for (int i = 0; i < result.size(); i++) {
//			TimeAttend x = (TimeAttend) result.get(i);
//			log.debug("-----from result---"+x.getDay()+"------in---"+x.getTimeIn()+"----out---"+x.getTimeOut());
//		}
		 
		
		
		//result.add(out);
		
		
		return totalList;
		
	}
	
	
public List getTimeAttendFromView (String hostName,String serviceName,String userName,String password, String empCode, Date from_date, Date to_date){
	
	List result = new ArrayList();
	List totalList = new ArrayList();
	
	log.debug("----fromdate---"+from_date);
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    
    String from_dateString=df.format(from_date);
    log.debug("----from_dateString- after formatting--"+from_dateString);
    
    String to_dateString=df.format(to_date);
    log.debug("----to_dateString- after formatting--"+to_dateString);
    try {
    	log.debug("----xxxxxxxxxxxxxxxx-----");
		to_date=df.parse(to_dateString);
		log.debug("----to_dateString- after formatting--"+to_date);
		log.debug("----xxxxxxxxxxxxxxxx-----");
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    
    MultiCalendarDate mCalDate = new MultiCalendarDate();
	if(from_dateString!=null){
		log.debug("-----date entered---"+from_dateString);
		mCalDate.setDateTimeString(from_dateString,new Boolean(true));
	}
	from_date = mCalDate.getDate();
	log.debug("----fromdate- after formatting--"+from_date);
	
	String emp = "";
	if (empCode!= null && !empCode.equals("")){
		emp = " t.empcode in (" +empCode+ ") and ";
	}
	jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));
	
	
	StringBuilder sql = new StringBuilder("(SELECT empdays.DD , empdays.EMPCODE,emp.FIRSTNAME fName,-- empcode1, req.FROM_DATE requestfromdate, req.PERIOD_FROM requestperiod , req.POSTED posted, req.APPROVED approved\n" + 
			"req.FROM_DATE AS  attendance_date,\n" + 
			"TO_CHAR(req.PERIOD_FROM,'YYYY-MM-DD hh24:MI:ss') AS attendance_time,\n" + 
			"(CASE WHEN req.REQUEST_TYPE=10 THEN 'IN' WHEN req.REQUEST_TYPE=11 THEN 'OUT' END ) AS ATTENDANCE_Type,\n" + 
			"(CASE WHEN req.APPROVED =1 THEN 'Approved' WHEN req.APPROVED =99 THEN 'Rejected' WHEN (req.PERIOD_FROM IS NOT NULL AND req.approved IS NULL) THEN  'Incomplete' END) AS approval ,\n" + 
			"(CASE WHEN req.INPUTTYPE=0 THEN 'Web_Attendance' WHEN req.INPUTTYPE=1 THEN 'Request_Attendance' WHEN req.INPUTTYPE=2 THEN 'Android_Attendance' ELSE 'Absent' END) AS input_type,\n" +
			"longitude as longitude, latitude as latitude \n"+
			"FROM ALL_EMP_DAYS empdays join LOGIN_USERS_REQUESTS req\n" + 
			"on req.EMPCODE=empdays.EMPCODE  AND (\n" + 
			"(req.REQUEST_TYPE=10 OR req.REQUEST_TYPE=11)\n" + 
			"and TO_CHAR(req.FROM_DATE,'YYYY-MM-DD')=TO_CHAR(empdays.DD,'YYYY-MM-DD')\n" + 
			"AND req.POSTED != 1\n" + 
			")\n" + 
			"JOIN COMMON_EMPLOYEE emp ON emp.EMPCODE=empdays.EMPCODE \n" +
			"where\n" + 
			"empdays.EMPCODE in ("+empCode+")\n" + 
			"AND empdays.DD >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') AND empdays.DD <= TO_DATE('"+to_dateString+"','DD/MM/YYYY')\n" + 
			")\n" + 
			"UNION ALL\n" + 
			"(SELECT empdays.DD , empdays.EMPCODE,emp.FIRSTNAME fName,-- empcode2, ta.DATE_ attendanceDate, ta.TIME_ attendanceTime\n" + 
			"ta.DATE_  AS  attendance_date,\n" + 
			"TO_CHAR(ta.TIME_,'YYYY-MM-DD hh24:MI:ss')  AS attendance_time,\n" + 
			"(CASE WHEN ta.TRANS_TYPE='I' THEN 'IN' WHEN ta.TRANS_TYPE='O' THEN 'OUT' END ) AS ATTENDANCE_Type,\n" + 
			"(CASE WHEN ta.date_ IS NOT NULL THEN 'Approved' END) AS approval,\n" + 
			"(CASE WHEN ta.INPUTTYPE=0 THEN 'Web_Attendance' WHEN ta.INPUTTYPE=1 THEN 'Request_Attendance' WHEN ta.INPUTTYPE=2 THEN 'Android_Attendance' ELSE 'Fingerprint_Attendance' END) AS input_type,\n" +
			"longitude as longitude, latitude as latitude \n"+
			"FROM ALL_EMP_DAYS empdays JOIN TIME_ATTEND ta\n" + 
			"ON  ta.EMP_CODE=empDays.EMPCODE AND TO_CHAR(ta.DATE_,'YYYY-MM-DD')=TO_CHAR(EMPDAYS.DD,'YYYY-MM-DD')\n" + 
			"JOIN COMMON_EMPLOYEE emp ON emp.EMPCODE=empdays.EMPCODE \n" +
			"where\n" + 
			"empdays.EMPCODE in ("+empCode+")\n" + 
			"AND empdays.DD >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') AND empdays.DD <= TO_DATE('"+to_dateString+"','DD/MM/YYYY')\n" + 
			")\n" + 
			"ORDER BY DD,attendance_time ASC --DD,\n");
	
	

	log.debug("host name " + hostName);
	log.debug("service name " + serviceName);
	log.debug("username " + userName);
	log.debug("password " + password);
	log.debug("----sql 1---"+sql);

	log.debug("sql statement " + sql.toString());
	List in=(List) jdbcTemplate.queryForList(sql.toString());
	
		
	LinkedCaseInsensitiveMap inMap ;
	LinkedCaseInsensitiveMap inMap2 ;
	
	String minDate = null;
	
	
	log.debug("----in---"+in);		
	//result.add(minDate);	


	if(to_dateString!=null){
		log.debug("-----to_dateString entered---"+to_dateString);
		mCalDate.setDateTimeString(to_dateString,new Boolean(true));
	}
	to_date = mCalDate.getDate();
	log.debug("----to_dateString- after formatting--"+to_date);
	
//	String maxDate = null;
	
	TimeAttend timeAttend=null;
	Date inDate=null, outDate= null;
	long totalMins=0, totalHrs=0;
	
	int i=0;
	
	
	DateFormat d= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
	log.debug("size "+in.size());
	
	String attendanceTime = null;
	String attendanceDate = null;
	String attendanceType = null;
	String inputType1 = null;
	String latitude1 = null;
	String longitude1 = null;
	
	while(i<in.size()){
		timeAttend=new TimeAttend();
		

		
		String attendanceTime2 = null;
		String attendanceDate2 = null;
		String attendanceType2 = null;
		String inputType2 = null;
		String latitude2 = null;
		String longitude2 = null;
		
		
//		log.debug("in.get(i) " + in.get(i).getClass());
		inMap = (LinkedCaseInsensitiveMap) in.get(i);
		Object atimeObj = inMap.get("attendance_time");
		if (atimeObj != null) {
			attendanceTime = atimeObj.toString();
			attendanceType = inMap.get("ATTENDANCE_TYPE").toString();
			inputType1 = inMap.get("INPUT_TYPE").toString();
			if (inMap.get("latitude")!=null) {
				latitude1 = inMap.get("latitude").toString();
			} else {
				latitude1 = null;
			}
			if (inMap.get("longitude")!=null) {
				longitude1 = inMap.get("longitude").toString();
			} else {
				longitude1 = null;
			}

			if (attendanceType.equals("IN")) {
				try {
					inDate=d.parse(attendanceTime);
					log.debug("inDate  = "+inDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		log.debug("i " + i + " attendanceType " + attendanceType +  " inDate " + inDate);
		if(in.size()>(i+1)) {
			inMap2 = (LinkedCaseInsensitiveMap)in.get(i+1);

			Object atimeObj2 = inMap2.get("attendance_time");
			if (atimeObj2!=null) {
				attendanceTime2 = atimeObj2.toString();
				log.debug("out time " + attendanceTime2);
				attendanceType2 = inMap2.get("ATTENDANCE_TYPE").toString();
				inputType2 = inMap2.get("INPUT_TYPE").toString();
				if (inMap2.get("latitude")!=null) {
					latitude2 = inMap2.get("latitude").toString();
				} else {
					latitude2 = null;
				}
				if (inMap2.get("longitude")!=null) {
					longitude2 = inMap2.get("longitude").toString();
				} else {
					longitude2 = null;
				}
				if(attendanceType2!= null && attendanceType2.equals("OUT")) {
					i++;
				}
				log.debug("i " + i);
			} else {
				latitude2 = null;
				longitude2 = null;
			}
			log.debug("longitude " + longitude2 + " latitude " + latitude2);
		}
		timeAttend.setTimeIn(attendanceTime.substring(10));
		
		timeAttend.setLatitude1(latitude1);
		timeAttend.setLatitude2(latitude2);
		timeAttend.setLongitude1(longitude1);
		timeAttend.setLongitude2(longitude2);
		timeAttend.setInputType1(inputType1);
		timeAttend.setInputType2(inputType2);
		String[] dateOnly = attendanceTime.split(" ");
		String[] letters=dateOnly[0].split("-");

		int year= Integer.parseInt(letters[0]);
		int month =Integer.parseInt(letters[1]);
		int day =Integer.parseInt(letters[2]);
		String dateString=year+"/"+month+"/"+day;
		log.debug("-----dateString-0--"+dateString);
		timeAttend.setDay(dateString);

		mCalDate.setDateString(dateString);
		Date date=mCalDate.getDate();
		log.debug("-----date-0--"+date);

		SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE",new Locale("ar","SA")); // the day of the week spelled out completely  
		log.debug("-------simpleDateformat.format(mCalDate.getDate())---"+simpleDateformat.format(mCalDate.getDate()));
		timeAttend.setDayString(simpleDateformat.format(mCalDate.getDate()));

		String empStr =  inMap.get("empcode").toString();
		timeAttend.setEmployee(empStr);

		String empName =  inMap.get("fName").toString();
		timeAttend.setEmpName(empName);
		log.debug("------timeAttend.getDay()---"+timeAttend.getDay());
		result.add(timeAttend);
		

		log.debug("attendanceType2 " + attendanceType2);
//		log.debug("attendanceType2!= null " + attendanceType2!= null + " attendanceType2.equals(OUT) " + attendanceType2.equals("OUT"));
		if (attendanceType2!= null && attendanceType2.equals("OUT")) {
			log.debug("attendanceType2 " + attendanceType2);
			try {
				outDate=d.parse(attendanceTime2);
				log.debug("outDate  = "+outDate);
				Calendar inCal = Calendar.getInstance();
				inCal.setTime(inDate);
				Calendar outCal = Calendar.getInstance();
				outCal.setTime(outDate);
				if (inCal.get(Calendar.DAY_OF_MONTH) == outCal.get(Calendar.DAY_OF_MONTH) 
						&& inCal.get(Calendar.MONTH) == outCal.get(Calendar.MONTH)
						&& inCal.get(Calendar.YEAR) == outCal.get(Calendar.YEAR) ) {
					log.debug("same day");
					
					long diffHrs= (outDate.getTime()-inDate.getTime())/(1000*60*60);
					long diffMins= ((outDate.getTime()-inDate.getTime())%(1000*60*60))/(1000*60);
					log.debug("diffHrs=== "+diffHrs);
					log.debug("diffMins=== "+diffMins);
					totalMins+=diffMins;
					totalHrs+=diffHrs;
					timeAttend.setDiffHrs(diffHrs+"");
					timeAttend.setDiffMins(diffMins+"");
					
					
					timeAttend.setTimeOut(attendanceTime2.substring(10));
					
					
										
				} else {
					timeAttend.setTimeOut(null);
					longitude2=null;
					latitude2=null;
					timeAttend.setLongitude2(longitude2);
					timeAttend.setLatitude2(latitude2);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				log.debug("parsing exception of attendanceTime2");
				e.printStackTrace();
			}
		} else {
			timeAttend.setTimeOut(null);
			longitude2=null;
			latitude2=null;
			timeAttend.setLongitude2(longitude2);
			timeAttend.setLatitude2(latitude2);
		}
		
		log.debug("lat2 " + timeAttend.getLatitude2() + " long2 " + timeAttend.getLongitude2());
		i++;
		
	}
		
	
	totalList.add(result);
	totalList.add(totalMins+","+totalHrs);
	
	return totalList;
	
}

	
public List getTimeAttendAll (String hostName,String serviceName,String userName,String password, String empCode, Date from_date, Date to_date, String statusId){
	
	List result = new ArrayList();
	List totalList = new ArrayList();
	
	log.debug("----fromdate---"+from_date);
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    
    String from_dateString=df.format(from_date);
    log.debug("----from_dateString- after formatting--"+from_dateString);
    
    String to_dateString=df.format(to_date);
    log.debug("----to_dateString- after formatting--"+to_dateString);
    try {
    	log.debug("----xxxxxxxxxxxxxxxx-----");
		to_date=df.parse(to_dateString);
		log.debug("----to_dateString- after formatting--"+to_date);
		log.debug("----xxxxxxxxxxxxxxxx-----");
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    
    MultiCalendarDate mCalDate = new MultiCalendarDate();
	if(from_dateString!=null){
		log.debug("-----date entered---"+from_dateString);
		mCalDate.setDateTimeString(from_dateString,new Boolean(true));
	}
	from_date = mCalDate.getDate();
	log.debug("----fromdate- after formatting--"+from_date);
	
	String emp = "";
	if (empCode!= null && !empCode.equals("")){
		emp = " t.empcode in (" +empCode+ ") and ";
	}
	
	
	String status = "";
	if (statusId!=null && !statusId.isEmpty()) {
		if(statusId.equals("0")) {
			status = " where approval like 'Incomplete' ";
		} else if(statusId.equals("1")) {
			status = " where approval like 'Approved' ";
		} else if (statusId.equals("99")) {
			status = " where approval like 'Rejected' ";
		}
	}
	jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));

	
	StringBuilder sql = new StringBuilder("SELECT  q.minDate, q.maxDate, q.emp, q.fName,\n"+
			"(CASE WHEN r.REQUEST_TYPE IS NOT NULL THEN r.LONGITUDE WHEN tt.TRANS_TYPE IS NOT NULL THEN tt.LONGITUDE END ) AS longitudeIn,\n"+
			"(CASE WHEN r.REQUEST_TYPE IS NOT NULL THEN r.LATITUDE WHEN tt.TRANS_TYPE IS NOT NULL THEN tt.LATITUDE END ) AS latitudeIn,\n"+
			"(CASE WHEN r.REQUEST_TYPE IS NOT NULL THEN r.LOCATIONADDRESS WHEN tt.TRANS_TYPE IS NOT NULL THEN tt.LOCATIONADDRESS END ) AS addressIn,\n"+
			"(CASE WHEN r2.REQUEST_TYPE IS NOT NULL THEN r2.LONGITUDE WHEN tt2.TRANS_TYPE IS NOT NULL THEN tt2.LONGITUDE END ) AS longitudeOut,\n"+
			"(CASE WHEN r2.REQUEST_TYPE IS NOT NULL THEN r2.LATITUDE WHEN tt2.TRANS_TYPE IS NOT NULL THEN tt2.LATITUDE END ) AS latitudeOut,\n"+
			"(CASE WHEN r2.REQUEST_TYPE IS NOT NULL THEN r2.LOCATIONADDRESS WHEN tt2.TRANS_TYPE IS NOT NULL THEN tt2.LOCATIONADDRESS END ) AS addressOut\n"+
			"FROM \n"+
			"(\n"
			+ "SELECT min(attendance_time) as minDate, max(attendance_time) as maxDate, empcode as emp, fName "
//			+ ",longitudeIn, LONGITUDEOut, LATITUDEIn, LATITUDEOut ,addressIn,addressOut \n"
			+ " from (\n"
			+ "(SELECT empdays.DD , empdays.EMPCODE,emp.FIRSTNAME fName, -- empcode1, req.FROM_DATE requestfromdate, req.PERIOD_FROM requestperiod , req.POSTED posted, req.APPROVED approved\n" + 
			"req.FROM_DATE AS  attendance_date,\n" + 
			"TO_CHAR(req.PERIOD_FROM,'YYYY-MM-DD hh24:MI:ss') AS attendance_time,\n" + 
			"(CASE WHEN req.REQUEST_TYPE=10 THEN 'IN' WHEN req.REQUEST_TYPE=11 THEN 'OUT' END ) AS ATTENDANCE_Type,\n" + 
			"(CASE WHEN req.APPROVED =1 THEN 'Approved' WHEN req.APPROVED =99 THEN 'Rejected' WHEN (req.PERIOD_FROM IS NOT NULL AND req.approved IS NULL) THEN  'Incomplete' END) AS approval ,\n" + 
			"(CASE WHEN req.INPUTTYPE=0 THEN 'Web_Attendance' WHEN req.INPUTTYPE=1 THEN 'Request_Attendance' WHEN req.INPUTTYPE=2 THEN 'Android_Attendance' ELSE 'Absent' END) AS input_type\n" +
//			"(CASE WHEN req.REQUEST_TYPE=10 THEN  longitude end) as longitudeIn, \n" +
//			"(CASE WHEN req.REQUEST_TYPE=10 THEN  latitude END ) as latitudeIn , \n" +
//			"(CASE WHEN req.REQUEST_TYPE=11 THEN  longitude  END ) AS longitudeOut, \n" +
//			"(CASE WHEN req.REQUEST_TYPE=11 THEN  latitude  END )  AS latitudeOut, \n"+
//			"(CASE WHEN req.REQUEST_TYPE=10 THEN locationAddress end) AS addressIn,\n"+
//			"(CASE WHEN req.REQUEST_TYPE=11 THEN locationAddress end) AS addressOut \n"+
			"FROM ALL_EMP_DAYS empdays join LOGIN_USERS_REQUESTS req\n" + 
			"on req.EMPCODE=empdays.EMPCODE  AND (\n" + 
			"(req.REQUEST_TYPE=10 OR req.REQUEST_TYPE=11)\n" + 
			"and TO_CHAR(req.FROM_DATE,'YYYY-MM-DD')=TO_CHAR(empdays.DD,'YYYY-MM-DD')\n" + 
			"AND req.POSTED != 1\n" + 
			")\n" + 
			"JOIN COMMON_EMPLOYEE emp ON emp.EMPCODE=empdays.EMPCODE \n" +
			"where\n" + 
			"empdays.EMPCODE in ("+empCode+")\n" + 
			"AND empdays.DD >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') AND empdays.DD <= TO_DATE('"+to_dateString+"','DD/MM/YYYY')\n" + 
			")\n" + 
			"UNION ALL\n" + 
			"(SELECT empdays.DD , empdays.EMPCODE,emp.FIRSTNAME fName,-- empcode2, ta.DATE_ attendanceDate, ta.TIME_ attendanceTime\n" + 
			"ta.DATE_  AS  attendance_date,\n" + 
			"TO_CHAR(ta.TIME_,'YYYY-MM-DD hh24:MI:ss')  AS attendance_time,\n" + 
			"(CASE WHEN ta.TRANS_TYPE='I' THEN 'IN' WHEN ta.TRANS_TYPE='O' THEN 'OUT' END ) AS ATTENDANCE_Type,\n" + 
			"(CASE WHEN ta.date_ IS NOT NULL THEN 'Approved' END) AS approval,\n" + 
			"(CASE WHEN ta.INPUTTYPE=0 THEN 'Web_Attendance' WHEN ta.INPUTTYPE=1 THEN 'Request_Attendance' WHEN ta.INPUTTYPE=2 THEN 'Android_Attendance' ELSE 'Fingerprint_Attendance' END) AS input_type\n" +
			//			"(CASE WHEN ta.TRANS_TYPE='I' THEN  longitude end) as longitudeIn, \n"+
			//			"(CASE WHEN ta.TRANS_TYPE='I' THEN  latitude end) as latitudeIn , \n"+
			//			"(CASE WHEN ta.TRANS_TYPE='O' THEN longitude end) AS longitudeOut,  \n"+
			//			"(CASE WHEN ta.TRANS_TYPE='O' THEN latitude end) AS latitudeOut, \n"+
			//			"(CASE WHEN ta.TRANS_TYPE='I' THEN locationAddress end) AS addressIn,\n"+
			//			"(CASE WHEN ta.TRANS_TYPE='O' THEN locationAddress end) AS addressOut\n"+
			"FROM ALL_EMP_DAYS empdays JOIN TIME_ATTEND ta\n" + 
			"ON  ta.EMP_CODE=empDays.EMPCODE AND TO_CHAR(ta.DATE_,'YYYY-MM-DD')=TO_CHAR(EMPDAYS.DD,'YYYY-MM-DD')\n" + 
			"JOIN COMMON_EMPLOYEE emp ON emp.EMPCODE=empdays.EMPCODE \n" +

			"where\n" + 
			"empdays.EMPCODE in ("+empCode+")\n" + 
			"AND empdays.DD >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') AND empdays.DD <= TO_DATE('"+to_dateString+"','DD/MM/YYYY')\n" + 
			")\n "+
			"ORDER BY DD" +
			") " + status +"\ngroup by DD,empcode,fName\n"
			//					+ ",longitudeIn, LONGITUDEOut, LATITUDEIn, LATITUDEOut,addressIn,addressOut \n"
			+ " order by DD desc\n" +
			") q\n"+
			"LEFT JOIN LOGIN_USERS_REQUESTS r ON r.EMPCODE=q.EMP AND (r.PERIOD_FROM=TO_DATE(q.mindate,'yyyy-mm-dd HH24:MI:SS') AND r.REQUEST_TYPE=10) \n"+
			"LEFT JOIN TIME_ATTEND tt ON tt.EMP_CODE=q.EMP AND tt.TRANS_TYPE='I' AND tt.DATE_=TO_DATE(q.mindate,'yyyy-mm-dd HH24:MI:SS')\n"+
			"LEFT JOIN LOGIN_USERS_REQUESTS r2 ON r.EMPCODE=q.EMP AND (r2.FROM_DATE=TO_DATE(maxdate,'yyyy-mm-dd HH24:MI:SS') AND r2.REQUEST_TYPE=11)\n"+
			"LEFT JOIN TIME_ATTEND tt2 ON tt2.EMP_CODE=q.EMP AND tt2.TRANS_TYPE='O' AND tt2.DATE_=TO_DATE(q.maxdate,'yyyy-mm-dd HH24:MI:SS')"
			+ " ORDER BY mindate desc");
	
	

	log.debug("host name " + hostName);
	log.debug("service name " + serviceName);
	log.debug("username " + userName);
	log.debug("password " + password);
	log.debug("----sql 1---"+sql);

	
	List in=(List) jdbcTemplate.queryForList(sql.toString());
	
		
	LinkedCaseInsensitiveMap inMap ;
	LinkedCaseInsensitiveMap inMap2 ;
	
	String minDate = null;
	
	log.debug("----in---"+in);		


	if(to_dateString!=null){
		log.debug("-----to_dateString entered---"+to_dateString);
		mCalDate.setDateTimeString(to_dateString,new Boolean(true));
	}
	to_date = mCalDate.getDate();
	log.debug("----to_dateString- after formatting--"+to_date);
	
	String maxDate = null;
	
	TimeAttend timeAttend=null;
	Date inDate=null, outDate= null;
	
	String longitude1=null, longitude2=null, latitude1= null, latitude2 = null, addressIn = null, addressOut = null;
	
	long totalMins=0, totalHrs=0;
	for(int i=0;i<in.size();i++){
		timeAttend=new TimeAttend();
		log.debug("in.get(i) " + in.get(i).getClass());
		inMap = (LinkedCaseInsensitiveMap) in.get(i);
		minDate = inMap.get("minDate").toString();
		log.debug("----minDate---"+minDate);
		
//		minDate=minDate.substring(0,19);
		log.debug("----minDate---"+minDate);
		
		DateFormat d= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
		
		try {
			inDate=d.parse(minDate);
			log.debug("inDate  = "+inDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		maxDate = inMap.get("maxDate").toString();
		log.debug("----maxDate---"+maxDate);
		try {
			outDate=d.parse(maxDate);
			log.debug("outDate  = "+outDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (inMap.get("longitudeIn")!=null) {
			longitude1 = inMap.get("longitudeIn").toString();
		}
		log.debug("----longitude1---"+longitude1);
		
		if (inMap.get("longitudeOut")!=null) {
			longitude2 = inMap.get("longitudeOut").toString();
		}
		log.debug("----longitude2---"+longitude2);
		
		if (inMap.get("latitudeIn")!=null) {
			latitude1 = inMap.get("latitudeIn").toString();
		}
		log.debug("----latitude1---"+latitude1);
		
		if (inMap.get("longitudeOut")!=null) {
			latitude2 = inMap.get("latitudeOut").toString();
		}
		log.debug("----latitude2---"+latitude2);
		
		if (inMap.get("addressIn")!=null) {
			addressIn = inMap.get("addressIn").toString();
		}
		log.debug("----addressIn---"+addressIn);
		
		if (inMap.get("addressOut")!=null) {
			addressOut = inMap.get("addressOut").toString();
		}
		log.debug("----addressOut---"+addressOut);
		
		if(inDate!=null || outDate!=null){
			long diffHrs= (outDate.getTime()-inDate.getTime())/(1000*60*60);
			long diffMins= ((outDate.getTime()-inDate.getTime())%(1000*60*60))/(1000*60);
			log.debug("diffHrs=== "+diffHrs);
			log.debug("diffMins=== "+diffMins);
			totalMins+=diffMins;
			totalHrs+=diffHrs;
			timeAttend.setDiffHrs(diffHrs+"");
			timeAttend.setDiffMins(diffMins+"");
		}
		
		
		timeAttend.setLatitude1(latitude1);
		timeAttend.setLatitude2(latitude2);
		timeAttend.setLongitude1(longitude1);
		timeAttend.setLongitude2(longitude2);
		timeAttend.setAddress1(addressIn);
		timeAttend.setAddress2(addressOut);
		
//		log.debug("outDate  = "+outDate);
		//log.debug("----DateIn---"+test);
		if(minDate.equals(maxDate)){
			timeAttend.setTimeOut(null);
		}
		else{			
			maxDate=maxDate.substring(0, maxDate.length()-3);
			log.debug("----maxDate.substring(10)----"+maxDate.substring(10));
			timeAttend.setTimeOut(maxDate.substring(10));
		}
		minDate=minDate.substring(0, minDate.length()-3);
		log.debug("----minDate.substring(10)----"+minDate.substring(10));
		timeAttend.setTimeIn(minDate.substring(10));
		log.debug("----minDate.substring(0,10)----"+minDate.substring(0,10));
		String date1=minDate.substring(0,10);
		String[] letters=date1.split("-");
		 
		//for (int j = 0; j < letters.length; j++) {
		//	log.debug("-----letters-0--"+letters[j]);
			int year= Integer.parseInt(letters[0].trim());
			int month =Integer.parseInt(letters[1].trim());
			int day =Integer.parseInt(letters[2].trim());
			String dateString=year+"/"+month+"/"+day;
			log.debug("-----dateString-0--"+dateString);
			timeAttend.setDay(dateString);
			
			mCalDate.setDateString(dateString);
			Date date=mCalDate.getDate();
			log.debug("-----date-0--"+date);
			//log.debug("----after parsing--"+df.format(date));
			//mCalDate.setDate(date);
			//log.debug("---mCalDate--date-0--"+mCalDate.getDate());
			
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE",new Locale("ar","SA")); // the day of the week spelled out completely  
			log.debug("-------simpleDateformat.format(mCalDate.getDate())---"+simpleDateformat.format(mCalDate.getDate()));
			timeAttend.setDayString(simpleDateformat.format(mCalDate.getDate()));
			
			String empStr =  inMap.get("emp").toString();
			timeAttend.setEmployee(empStr);
			
			String empName =  inMap.get("fName").toString();
			timeAttend.setEmpName(empName);
		//}
		//timeAttend.setDay();
		log.debug("------timeAttend.getDay()---"+timeAttend.getDay());
		result.add(timeAttend);
		log.debug("------end of loop---");
	}
	totalList.add(result);
	totalList.add(totalMins+","+totalHrs);
	return totalList;
	
}

	
	public List getVacations (String hostName,String serviceName,String userName,String password, String empCode, Long reqId, String vacId, Date from_date){
		
		log.debug("----hostName--"+hostName+" serviceName" + serviceName + " username " + userName + " password " + password);
		List result = new ArrayList();
		log.debug("----fromdate---"+from_date);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        String from_dateString=df.format(from_date);
        log.debug("----from_dateString- after formatting--"+from_dateString);
        
        try {
        	log.debug("----xxxxxxxxxxxxxxxx-----");
			from_date=df.parse(from_dateString);
			log.debug("----from_date- after formatting--"+from_date);
			log.debug("----xxxxxxxxxxxxxxxx-----");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        MultiCalendarDate mCalDate = new MultiCalendarDate();
		if(from_dateString!=null){
			log.debug("-----date entered---"+from_dateString);
			mCalDate.setDateTimeString(from_dateString,new Boolean(true));
		}
		from_date = mCalDate.getDate();
		log.debug("----fromdate- after formatting--"+from_date);
		Calendar c = Calendar.getInstance();
        c.setTime(from_date);
        int year=c.get(Calendar.YEAR);
        log.debug("----year---"+year);
        Date dd1=null;
		c.set(year, 00, 01);
		dd1=c.getTime();
		log.debug("----dd1---"+dd1);
		
		String dd1String=df.format(dd1);
        log.debug("----dd1String- after formatting--"+dd1String);

		if(dd1String!=null){
			log.debug("-----dd1 -string -"+dd1String);
			mCalDate.setDateString(dd1String);
		}
		dd1 = mCalDate.getDate();
		log.debug("----dd1- after formatting--"+dd1);
		jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));
		StringBuilder sql = new StringBuilder(
				" select fr_date, to_date, withdr, vacation from empvac where empcode = '" +empCode
						+ "' and vacation = '" + vacId + "' and fr_date between to_date ('"
						+ dd1String+"', 'DD-MM-YYYY') and  to_date('" +from_dateString +"', 'DD-MM-YYYY')");
		
		log.debug("----sql1---"+sql);
		
		result=(List) jdbcTemplate.queryForList(sql.toString());
		return result;
	}
	
	
public List getVacations (String hostName,String serviceName,String userName,String password, String empCode, Long reqId, Date from_date, Date to_date){
		
		log.debug("----hostName--"+hostName+" serviceName" + serviceName + " username " + userName + " password " + password);
		List result = new ArrayList();
		log.debug("----fromdate---"+from_date);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        String from_dateString=df.format(from_date);
        log.debug("----from_dateString- after formatting--"+from_dateString);
        
        try {
        	log.debug("----xxxxxxxxxxxxxxxx-----");
			from_date=df.parse(from_dateString);
			log.debug("----from_date- after formatting--"+from_date);
			log.debug("----xxxxxxxxxxxxxxxx-----");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        String to_dateString=df.format(to_date);
        log.debug("----to_dateString- after formatting--"+to_dateString);
        
        String empString ="";
        if (empCode !=null && !empCode.equals("")){
        	empString = "empvac.empcode in (" +empCode+") and ";
        }
        
        try {
        	log.debug("----xxxxxxxxxxxxxxxx-----");
			to_date=df.parse(to_dateString);
			log.debug("----from_date- after formatting--"+to_date);
			log.debug("----xxxxxxxxxxxxxxxx-----");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));
		StringBuilder sql = new StringBuilder(
				" select empvac.fr_date as fr_date, empvac.to_date as to_date, empvac.withdr as withdr, empvac.vacation as vacation, "
				+ "empvac.empcode as empCode, e.firstName as fName "
				+ "from empvac empvac, common_employee e "
				+ "where "
				+ empString
//						+ "' and vacation = '" + vacId 
				 + " empvac.empcode=e.empcode and empvac.fr_date >= to_date ('"
						+ from_dateString+"', 'DD-MM-YYYY') and empvac.fr_date <= to_date('" + 
				 to_dateString+"', 'DD-MM-YYYY')");
		
		log.debug("----sql1---"+sql);
		
		result=(List) jdbcTemplate.queryForList(sql.toString());
		return result;
	}

	public int getSalaryFromDay(String hostName,String serviceName,String userName,String password){
		jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));
		StringBuilder sql = new StringBuilder(
				" select salary_from_day from system ");
		
		log.debug("----sql1---"+sql);
		int day = 0;
		List result=(List) jdbcTemplate.queryForList(sql.toString());
		if (result.size()>0) {
			ListOrderedMap m = (ListOrderedMap )result.get(0);
			day = ((BigDecimal)m.getValue(0)).intValue();
			log.debug("day " + day);
		}
		return day;
	}


	public Map getPagedRequests(String hostName,String serviceName,String userName,String password,
			Date fromDate, Date toDate, Long requestType,
			Date exactFrom, Date exactTo, Date periodFrom, Date periodTo,
			String empCode, String codeFrom, String codeTo, Long statusId,
			String sort, List empReqTypeAccs, String requestNumber, Long mgrId,
			boolean isWeb, String isInsideCompany, int pageNumber, int pageSize) {

		log.debug("manager id " + mgrId);
		Calendar cFrom= Calendar.getInstance();
		
		Date dateFrom=null;
		Date dateTo= null;
		log.debug("------fromDate---"+fromDate);
		log.debug("fromDate " + fromDate + " toDate " + toDate + " requestType " + requestType);
		log.debug("exactFrom " + exactFrom + " exactTo " + exactTo + " requestType " + requestType);
		if (fromDate !=null) {
			cFrom.setTime(fromDate);
			cFrom.set(Calendar.HOUR_OF_DAY, 0);
			cFrom.set(Calendar.MINUTE, 0);
			cFrom.set(Calendar.SECOND, 0);
			dateFrom=cFrom.getTime();
		}
		
		log.debug("------dateFrom---"+dateFrom);

		log.debug("------toDate---"+toDate);
		Calendar cTo= Calendar.getInstance();
		if (toDate!=null) {
			cTo.setTime(toDate);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);
			dateTo=cTo.getTime();
		}
		
		log.debug("------dateTo---"+dateTo);
		
		Date exFrom=null;
		Date exTo= null;
		
		log.debug("------exactfrom---"+exactFrom);
		if (exactFrom !=null) {
			cFrom.setTime(exactFrom);
			cFrom.set(Calendar.HOUR_OF_DAY, 0);
			cFrom.set(Calendar.MINUTE, 0);
			cFrom.set(Calendar.SECOND, 0);
			exFrom=cFrom.getTime();
		}
		
		log.debug("------exFrom---"+exFrom);

		if (exactTo!=null) {
			cTo.setTime(exactTo);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);
			exTo=cTo.getTime();
		}
		log.debug("------exTo---"+exTo);
		
		Date pFrom=null;
		Date pTo= null;
		
		if (pFrom !=null) {
			cFrom.setTime(periodFrom);
			cFrom.set(Calendar.HOUR_OF_DAY, 0);
			cFrom.set(Calendar.MINUTE, 0);
			cFrom.set(Calendar.SECOND, 0);
			pFrom=cFrom.getTime();
		}
		
		log.debug("------pfrom---"+pFrom);

		if (pTo!=null) {
			cTo.setTime(periodTo);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);
			pTo=cTo.getTime();
		}
		log.debug("------Pto---"+pTo);
		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		 
		//////////////////////////////////////////////////////////////////
		//////////////Date Strings Definition/////////////////////////////
		//////////////////////////////////////////////////////////////////
		String from_dateString=null;
		String to_dateString=null;
		String periodf_dateString=null;
		String periodt_dateString=null;
		String exactf_dateString=null;
		String exactt_dateString=null;
		//////////////////////////////////////////////////////////////////
		///////////////End of date strings definition
		/////////////////////////////////////////////////////////////////
	    log.debug("----from_dateString- after formatting--"+from_dateString);
	    
		Map map = new HashMap();
		
		jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));
		String query = "";
		String outerSelectStart = "SELECT a.*, emp.ID empId,emp.EMPCODE employeeCode, emp.FIRSTNAME name, acc1.GROUP_ID,lev.ID levId, lev.EMP_ID mgrId"
				+ ",APPROVALS.APPROVAL, APPROVALS.APPROVAL_DATE , APPROVALS.USER_ID , APPROVALS.NOTE "
				+ "FROM ";
		String select = "select loginUsersReq.*, reqType.DESCRIPTION, vac.name vacName "
				+ " FROM login_users_requests loginUsersReq  JOIN REQUEST_TYPES reqType ON reqType.id=loginUsersReq.REQUEST_TYPE "
				+ " LEFT OUTER JOIN vacation vac ON  VAC.VACATION=loginUsersReq.VACATION   ";
		String where = null;
		String orderBy = "";

		
		
		String outerSelectEnd = ") a "
				+ "JOIN COMMON_EMPLOYEE emp ON a.empcode=emp.EMPCODE "
				+ "LEFT OUTER JOIN EMP_REQTYPE_ACC acc1 ON (acc1.REQ_ID=a.request_type AND acc1.EMP_ID=a.login_user) "
				+ "LEFT OUTER JOIN ACCESS_LEVELS lev ON acc1.GROUP_ID=lev.LEVEL_ID "
				+ "LEFT OUTER JOIN COMMON_EMPLOYEE mgr ON lev.EMP_ID = mgr.ID "
				+ "LEFT OUTER JOIN EMP_REQ_APPROVAL approvals ON approvals.LEVEL_ID=acc1.ID  AND APPROVALS.REQ_ID=a.id AND APPROVALS.USER_ID=lev.EMP_ID ";
		
		String outerSelectWhere = "";
		
		if (mgrId != null) {
			outerSelectWhere = " where mgr.id= " + mgrId;
		}
		////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////Date Filters////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////
		log.debug("from date " + fromDate + "to date " + toDate);
		if (fromDate!= null && toDate!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			from_dateString=df.format(fromDate);
			to_dateString=df.format(toDate);
			where += " ((trunc(loginUsersReq.request_date) >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') and "
					+ "trunc(loginUsersReq.request_date)  <= TO_DATE('"+to_dateString+"','DD/MM/YYYY'))) ";
		}
		
		if (periodFrom!= null && periodTo!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			periodf_dateString=df.format(periodFrom);
			periodt_dateString=df.format(periodTo);
			where += " ((trunc(loginUsersReq.period_from)  >= TO_DATE('"+periodf_dateString+"','DD/MM/YYYY') and "
					+ "trunc(loginUsersReq.period_from) <= TO_DATE('"+periodt_dateString+"','DD/MM/YYYY'))) ";
		}
		
		if (exactFrom!= null && exactTo!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			exactf_dateString=df.format(exactFrom);
			exactt_dateString=df.format(exactTo);
			where += " ((trunc(loginUsersReq.from_date) >= TO_DATE('"+exactf_dateString+"','DD/MM/YYYY') and "
					+ "trunc(loginUsersReq.from_date) <= TO_DATE('"+exactt_dateString+"','DD/MM/YYYY'))) ";
		}
		////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////End of Date Filters///////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////
		
		
		if (requestNumber!=null && !requestNumber.isEmpty()) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.requestNumber="+requestNumber+" ";
		}
		
		
		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////Request type filters///////////////////////////////
		///////////////////////////////////////////////////////////////////////////////
		
		if(requestType!=null) {
			log.debug("1.requesttype " + requestType);
			if (requestType.equals(new Long(1))){
				log.debug("requesttype is 1");
				if(where == null) {
					where = " where ";
				} else {
					where += " and ";
				}
				where += " (loginUsersReq.request_type="+requestType+" ) ";
				where += " and ((loginUsersReq.from_date is not null and loginUsersReq.to_date is not null) or (loginUsersReq.period_from is not null and loginUsersReq.period_to is not null))";
			} else if (requestType.equals(new Long(4))) {
				log.debug("requesttype is 4");
				if(where == null) {
					where = " where ";
				} else {
					where += " and ";
				}
				where += " loginUsersReq.request_type=4  ";
				where += " and ((loginUsersReq.from_date is not null and loginUsersReq.to_date is not null) or (loginUsersReq.period_from is not null and loginUsersReq.period_to is not null))";
			} else if (requestType.equals(new Long(5))) {
				log.debug("requesttype is 5");
				if(where == null) {
					where = " where ";
				} else {
					where += " and ";
				}
				where += " loginUsersReq.request_type=5  ";
				log.debug("ma2moreya");
			}   else if (requestType.equals(new Long(7))) {// all errands
				log.debug("requesttype is 4");
				if(where == null) {
					where = " where ";
				} else {
					where += " and ";
				}
				where += " (loginUsersReq.request_type=4  ";
				where += " or (loginUsersReq.request_type=5  and ((loginUsersReq.from_date is not null and loginUsersReq.to_date is not null) or (loginUsersReq.period_from is not null and loginUsersReq.period_to is not null))))";
			}else if (requestType.equals(new Long(8))) {//7odoor w enseraf
				log.debug("requesttype is 8");
				if(where == null) {
					where = " where ";
				} else {
					where += " and ";
				}
				where += " (loginUsersReq.request_type=10 or loginUsersReq.request_type=11)  ";
			}  
			else if (requestType.equals(new Long(10)) || requestType.equals(new Long(11))) {
				log.debug("requesttype is else");
				if(where == null) {
					where = " where ";
				} else {
					where += " and ";
				}
				where += " loginUsersReq.request_type="+requestType+"  ";
			} else {
				log.debug("requesttype is else");
				if(where == null) {
					where = " where ";
				} else {
					where += " and ";
				}
				where += " loginUsersReq.request_type=  " + requestType +  " ";
				where += " and ((loginUsersReq.from_date is not null and loginUsersReq.to_date is not null) or (loginUsersReq.period_from is not null and loginUsersReq.period_to is not null))";
			}
		} else {
			log.debug("2. requesttype " + requestType);
			
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += "((loginUsersReq.from_date is not null and loginUsersReq.to_date is not null) or (loginUsersReq.period_from is not null and loginUsersReq.period_to is not null) "
					+ " or (loginUsersReq.request_type=10 or loginUsersReq.request_type=11) ) ";
		}
		
		
		////////////////////////////////////////////////////////////////////////////////
		//////////////////////End of Request type filters///////////////////////////////
		///////////////////////////////////////////////////////////////////////////////
		if (empCode!= null && !empCode.isEmpty()) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.empCode="+empCode+" ";
		}
		////////////////////////////////////////////////////////////////////////////////
		if (codeFrom!= null && !codeFrom.isEmpty() && 
				codeTo!= null && !codeTo.isEmpty()) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.empCode between "+codeFrom+" and " + codeTo + " ";
		}
		///////////////////////////////////////////////////////////////////////////////
		log.debug("status id " + statusId);
		if (statusId!=null) {
			String statusStr = "";
			if (statusId.equals(new Long(0))) {
				statusStr = " approvals.approval is null ";
			} else if (statusId.equals(new Long(1))) {
				statusStr = " (approvals.approval =1 or approvals.approval=2) ";
			}
			if(outerSelectWhere == null) {
				outerSelectWhere = " where ";
			} else {
				outerSelectWhere += " and ";
			}
			outerSelectWhere += statusStr;
		}
		
		/////////////////////////////////////////////////////////////////////
		if(isInsideCompany!=null) {
			if(isInsideCompany.equals("0")) {
				if(where == null) {
					where = " where ";
				} else {
					where += " and ";
				}
				where += " loginUsersReq.isInsideCompany = 0 ";
			} else if (isInsideCompany.equals("1")) {
				if(where == null) {
					where = " where ";
				} else {
					where += " and ";
				}
				where += " loginUsersReq.isInsideCompany = 1 ";
			}
		}
		////////////////////////////////////////////////////////////////////
		
		
		if (empReqTypeAccs !=null && !empReqTypeAccs.isEmpty()) {
			log.debug("empReqTypeAccs " +  empReqTypeAccs.toString().replace("[", "").replace("]", ""));
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.login_user in (  " + empReqTypeAccs.toString().replace("[", "").replace("]", "") + " ) ";
		}
		
//		where += " AND reqType.id=loginUsersReq.REQUEST_TYPE AND VAC.VACATION=loginUsersReq.VACATION  "; 
		///////////////////////////////////////////////////////////////////
		
		if (sort.equalsIgnoreCase("desc")) {
			orderBy = " order by period_from desc ";
		} else if (sort.equalsIgnoreCase("asc")) {
			orderBy = " order by period_from asc ";
		}
		/////////////////////////////////////////////////////////////////
		
		
		
		query = "select * from (select rownum rnum, q.* from (" + outerSelectStart + " ("
				+select + where+orderBy+
				outerSelectEnd + outerSelectWhere +orderBy+") q where rownum<="+((pageNumber*pageSize)+pageSize-1)+") where rnum>"+(pageSize*pageNumber)  + orderBy;
		log.debug("query " + query);
		StringBuilder sql = new StringBuilder(query);
		
		log.debug("host name " + hostName);
		log.debug("service name " + serviceName);
		log.debug("username " + userName);
		log.debug("password " + password);
		log.debug("----sql 1---"+sql);

		
		List in=(List) jdbcTemplate.queryForList(sql.toString());
		
		String listSizeQuery = "select count (*) from ("+outerSelectStart+ " ("+select+where+orderBy
				+outerSelectEnd + outerSelectWhere+orderBy+")"+orderBy;
		log.debug("listSizeQuery " + listSizeQuery);
		StringBuilder sqlListSize = new StringBuilder(listSizeQuery);
		List in2=(List) jdbcTemplate.queryForList(sqlListSize.toString());
		
		//////class in list results is LinkedCaseInsensitiveMap 
		
		int i=0;
		
		
		DateFormat d= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
		log.debug("size "+in.size());
		
//		while(i<in.size()){
//			
//			i++;
//		}
		log.debug(in2.size());
		map.put("results", in);
		if (in2.size()>0) {
			log.debug("listSize " + ((LinkedCaseInsensitiveMap)in2.get(0)).get("count(*)"));
			map.put("listSize", ((BigDecimal)((LinkedCaseInsensitiveMap)in2.get(0)).get("count(*)")).intValue());
		} else {
			map.put("listSize", new Long(0));
		}
		return map;
	}
	
	public Map getSubmittedPagedRequests(String hostName,String serviceName,String userName,String password,
			Date fromDate, Date toDate, Long requestType,
			Date exactFrom, Date exactTo, Date periodFrom, Date periodTo,
			String empCode, String codeFrom, String codeTo, Long statusId,
			String sort, List empReqTypeAccs, String requestNumber, Long mgrId,
			boolean isWeb, String isInsideCompany, int pageNumber, int pageSize) {

		log.debug("manager id " + mgrId);
		Calendar cFrom= Calendar.getInstance();
		
		Date dateFrom=null;
		Date dateTo= null;
		log.debug("------fromDate---"+fromDate);
		log.debug("fromDate " + fromDate + " toDate " + toDate + " requestType " + requestType);
		log.debug("exactFrom " + exactFrom + " exactTo " + exactTo + " requestType " + requestType);
		if (fromDate !=null) {
			cFrom.setTime(fromDate);
			cFrom.set(Calendar.HOUR_OF_DAY, 0);
			cFrom.set(Calendar.MINUTE, 0);
			cFrom.set(Calendar.SECOND, 0);
			dateFrom=cFrom.getTime();
		}
		
		log.debug("------dateFrom---"+dateFrom);

		log.debug("------toDate---"+toDate);
		Calendar cTo= Calendar.getInstance();
		if (toDate!=null) {
			cTo.setTime(toDate);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);
			dateTo=cTo.getTime();
		}
		
		log.debug("------dateTo---"+dateTo);
		
		Date exFrom=null;
		Date exTo= null;
		
		log.debug("------exactfrom---"+exactFrom);
		if (exactFrom !=null) {
			cFrom.setTime(exactFrom);
			cFrom.set(Calendar.HOUR_OF_DAY, 0);
			cFrom.set(Calendar.MINUTE, 0);
			cFrom.set(Calendar.SECOND, 0);
			exFrom=cFrom.getTime();
		}
		
		log.debug("------exFrom---"+exFrom);

		if (exactTo!=null) {
			cTo.setTime(exactTo);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);
			exTo=cTo.getTime();
		}
		log.debug("------exTo---"+exTo);
		
		Date pFrom=null;
		Date pTo= null;
		
		if (pFrom !=null) {
			cFrom.setTime(periodFrom);
			cFrom.set(Calendar.HOUR_OF_DAY, 0);
			cFrom.set(Calendar.MINUTE, 0);
			cFrom.set(Calendar.SECOND, 0);
			pFrom=cFrom.getTime();
		}
		
		log.debug("------pfrom---"+pFrom);

		if (pTo!=null) {
			cTo.setTime(periodTo);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);
			pTo=cTo.getTime();
		}
		log.debug("------Pto---"+pTo);
		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		 
		//////////////////////////////////////////////////////////////////
		//////////////Date Strings Definition/////////////////////////////
		//////////////////////////////////////////////////////////////////
		String from_dateString=null;
		String to_dateString=null;
		String periodf_dateString=null;
		String periodt_dateString=null;
		String exactf_dateString=null;
		String exactt_dateString=null;
		//////////////////////////////////////////////////////////////////
		///////////////End of date strings definition
		/////////////////////////////////////////////////////////////////
	    log.debug("----from_dateString- after formatting--"+from_dateString);
	    
		Map map = new HashMap();
		
		jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));
		String query = "";
		String outerSelectStart = "SELECT a.*, emp.ID empId,emp.EMPCODE employeeCode, emp.FIRSTNAME name "
//				+ ", acc1.GROUP_ID,lev.ID levId, lev.EMP_ID mgrId"
//				+ ",APPROVALS.APPROVAL, APPROVALS.APPROVAL_DATE , APPROVALS.USER_ID , APPROVALS.NOTE "
				+ "FROM ";
		String select = "select loginUsersReq.*, reqType.DESCRIPTION, vac.name vacName "
				+ " FROM login_users_requests loginUsersReq  JOIN REQUEST_TYPES reqType ON reqType.id=loginUsersReq.REQUEST_TYPE "
				+ " LEFT OUTER JOIN vacation vac ON  VAC.VACATION=loginUsersReq.VACATION   ";
		String where = null;
		String orderBy = "";

		
		
		String outerSelectEnd = ") a "
				+ "JOIN COMMON_EMPLOYEE emp ON a.empcode=emp.EMPCODE "
//				+ "LEFT OUTER JOIN EMP_REQTYPE_ACC acc1 ON (acc1.REQ_ID=a.request_type AND acc1.EMP_ID=a.login_user) "
//				+ "LEFT OUTER JOIN ACCESS_LEVELS lev ON acc1.GROUP_ID=lev.LEVEL_ID "
//				+ "LEFT OUTER JOIN COMMON_EMPLOYEE mgr ON lev.EMP_ID = mgr.ID "
//				+ "LEFT OUTER JOIN EMP_REQ_APPROVAL approvals ON approvals.LEVEL_ID=acc1.ID  AND APPROVALS.REQ_ID=a.id AND APPROVALS.USER_ID=lev.EMP_ID "
				;
		
		String outerSelectWhere = "";
		
		if (mgrId != null) {
			outerSelectWhere = " where mgr.id= " + mgrId;
		}
		////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////Date Filters////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////
		log.debug("from date " + fromDate + "to date " + toDate);
		if (fromDate!= null && toDate!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			from_dateString=df.format(fromDate);
			to_dateString=df.format(toDate);
			where += " ((trunc(loginUsersReq.request_date) >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') and "
					+ "trunc(loginUsersReq.request_date)  <= TO_DATE('"+to_dateString+"','DD/MM/YYYY'))) ";
		}
		
		if (periodFrom!= null && periodTo!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			periodf_dateString=df.format(periodFrom);
			periodt_dateString=df.format(periodTo);
			where += " ((trunc(loginUsersReq.period_from)  >= TO_DATE('"+periodf_dateString+"','DD/MM/YYYY') and "
					+ "trunc(loginUsersReq.period_from) <= TO_DATE('"+periodt_dateString+"','DD/MM/YYYY'))) ";
		}
		
		if (exactFrom!= null && exactTo!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			exactf_dateString=df.format(exactFrom);
			exactt_dateString=df.format(exactTo);
			where += " ((trunc(loginUsersReq.from_date) >= TO_DATE('"+exactf_dateString+"','DD/MM/YYYY') and "
					+ "trunc(loginUsersReq.from_date) <= TO_DATE('"+exactt_dateString+"','DD/MM/YYYY'))) ";
		}
		////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////End of Date Filters///////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////
		
		
		if (requestNumber!=null && !requestNumber.isEmpty()) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.requestNumber="+requestNumber+" ";
		}
		
		
		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////Request type filters///////////////////////////////
		///////////////////////////////////////////////////////////////////////////////
		
		if(requestType!=null) {
		log.debug("1.requesttype " + requestType);
		if (requestType.equals(new Long(1))){
			log.debug("requesttype is 1");
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " (loginUsersReq.request_type="+requestType+" ) ";
			where += " and ((loginUsersReq.from_date is not null and loginUsersReq.to_date is not null) or (loginUsersReq.period_from is not null and loginUsersReq.period_to is not null))";
		} else if (requestType.equals(new Long(4))) {/////full day errand
			log.debug("requesttype is 4");
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.request_type=4  ";
//			where += " and ((loginUsersReq.from_date is not null and loginUsersReq.to_date is not null) or (loginUsersReq.period_from is not null and loginUsersReq.period_to is not null))";
			//where += " and loginUsersReq.vacation is null ";
		} else if (requestType.equals(new Long(5))) {//////errand
			log.debug("requesttype is 5");
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.request_type=5  ";
			log.debug("ma2moreya");
		}   else if (requestType.equals(new Long(7))) {// all errands
			log.debug("requesttype is 7");
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " (loginUsersReq.request_type=4 or loginUsersReq.request_type=5) ";
//			where += " and ((loginUsersReq.from_date is not null and loginUsersReq.to_date is not null) or (loginUsersReq.period_from is not null and loginUsersReq.period_to is not null))";
		} else if (requestType.equals(new Long(8))) {//7odoor w enseraf
			log.debug("requesttype is 6");
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " (loginUsersReq.request_type=10 or loginUsersReq.request_type=11)  ";
		}  
		else if (requestType.equals(new Long(10)) || requestType.equals(new Long(11))) {
			log.debug("requesttype is else");
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.request_type="+requestType+"  ";
		}  else {
			log.debug("requesttype is else");
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.request_type=  " + requestType +  " ";
			where += " and ((loginUsersReq.from_date is not null and loginUsersReq.to_date is not null) or (loginUsersReq.period_from is not null and loginUsersReq.period_to is not null))";
		}
	} else {
		log.debug("2. requesttype " + requestType);
		
		if(where == null) {
			where = " where ";
		} else {
			where += " and ";
		}
		where += "((loginUsersReq.from_date is not null and loginUsersReq.to_date is not null) or (loginUsersReq.period_from is not null and loginUsersReq.period_to is not null) "
				+ " or (loginUsersReq.request_type=10 or loginUsersReq.request_type=11) ) ";
	}
		
		////////////////////////////////////////////////////////////////////////////////
		//////////////////////End of Request type filters///////////////////////////////
		///////////////////////////////////////////////////////////////////////////////
		if (empCode!= null && !empCode.isEmpty()) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.empCode="+empCode+" ";
		}
		////////////////////////////////////////////////////////////////////////////////
		if (codeFrom!= null && !codeFrom.isEmpty() && 
				codeTo!= null && !codeTo.isEmpty()) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.empCode between "+codeFrom+" and " + codeTo + " ";
		}
		///////////////////////////////////////////////////////////////////////////////
		if (statusId!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.approved = "+statusId;
		}
		
		/////////////////////////////////////////////////////////////////////
		if(isInsideCompany!=null) {
			if(isInsideCompany.equals("0")) {
				if(where == null) {
					where = " where ";
				} else {
					where += " and ";
				}
				where += " loginUsersReq.isInsideCompany = 0 ";
			} else if (isInsideCompany.equals("1")) {
				if(where == null) {
					where = " where ";
				} else {
					where += " and ";
				}
				where += " loginUsersReq.isInsideCompany = 1 ";
			}
		}
		////////////////////////////////////////////////////////////////////
		
		
		if (empReqTypeAccs !=null && !empReqTypeAccs.isEmpty()) {
			log.debug("empReqTypeAccs " +  empReqTypeAccs.toString().replace("[", "").replace("]", ""));
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.login_user in (  " + empReqTypeAccs.toString().replace("[", "").replace("]", "") + " ) ";
		}
		
//		where += " AND reqType.id=loginUsersReq.REQUEST_TYPE AND VAC.VACATION=loginUsersReq.VACATION  "; 
		///////////////////////////////////////////////////////////////////
		
		if (sort.equalsIgnoreCase("desc")) {
			orderBy = " order by period_from desc ";
		} else if (sort.equalsIgnoreCase("asc")) {
			orderBy = " order by period_from asc ";
		}
		/////////////////////////////////////////////////////////////////
		
		
		
		query = "select * from (select rownum rnum, q.* from (" + outerSelectStart + " ("
				+select + where+orderBy+
				outerSelectEnd + outerSelectWhere +orderBy+") q where rownum<="+((pageNumber*pageSize)+pageSize-1)+") where rnum>"+(pageSize*pageNumber)  + orderBy;
		log.debug("query " + query);
		StringBuilder sql = new StringBuilder(query);
		
		log.debug("host name " + hostName);
		log.debug("service name " + serviceName);
		log.debug("username " + userName);
		log.debug("password " + password);
		log.debug("----sql 1---"+sql);

		
		List in=(List) jdbcTemplate.queryForList(sql.toString());
		
		String listSizeQuery = "select count (*) from ("+outerSelectStart+ " ("+select+where+orderBy
				+outerSelectEnd + outerSelectWhere+orderBy+")"+orderBy;
		log.debug("listSizeQuery " + listSizeQuery);
		StringBuilder sqlListSize = new StringBuilder(listSizeQuery);
		List in2=(List) jdbcTemplate.queryForList(sqlListSize.toString());
		
		//////class in list results is LinkedCaseInsensitiveMap 
		
		int i=0;
		
		
		DateFormat d= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
		log.debug("size "+in.size());
		
//		while(i<in.size()){
//			
//			i++;
//		}
		log.debug(in2.size());
		map.put("results", in);
		if (in2.size()>0) {
			log.debug("listSize " + ((LinkedCaseInsensitiveMap)in2.get(0)).get("count(*)"));
			map.put("listSize", ((BigDecimal)((LinkedCaseInsensitiveMap)in2.get(0)).get("count(*)")).intValue());
		} else {
			map.put("listSize", new Long(0));
		}
		return map;
	}
		
	
	
	public Map getRequestsStatus(String hostName,String serviceName,String userName,String password,
			Date fromDate, Date toDate, Long requestType,
			Date exactFrom, Date exactTo, Date periodFrom, Date periodTo,
			String empCode, String codeFrom, String codeTo, Long statusId,
			String sort, List empReqTypeAccs, String requestNumber, Long mgrId,
			boolean isWeb, String isInsideCompany, int pageNumber, int pageSize) {

		log.debug("manager id " + mgrId);
		Calendar cFrom= Calendar.getInstance();
		
		Date dateFrom=null;
		Date dateTo= null;
		log.debug("------fromDate---"+fromDate);
		log.debug("fromDate " + fromDate + " toDate " + toDate + " requestType " + requestType);
		log.debug("exactFrom " + exactFrom + " exactTo " + exactTo + " requestType " + requestType);
		if (fromDate !=null) {
			cFrom.setTime(fromDate);
			cFrom.set(Calendar.HOUR_OF_DAY, 0);
			cFrom.set(Calendar.MINUTE, 0);
			cFrom.set(Calendar.SECOND, 0);
			dateFrom=cFrom.getTime();
		}
		
		log.debug("------dateFrom---"+dateFrom);

		log.debug("------toDate---"+toDate);
		Calendar cTo= Calendar.getInstance();
		if (toDate!=null) {
			cTo.setTime(toDate);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);
			dateTo=cTo.getTime();
		}
		
		log.debug("------dateTo---"+dateTo);
		
		Date exFrom=null;
		Date exTo= null;
		
		log.debug("------exactfrom---"+exactFrom);
		if (exactFrom !=null) {
			cFrom.setTime(exactFrom);
			cFrom.set(Calendar.HOUR_OF_DAY, 0);
			cFrom.set(Calendar.MINUTE, 0);
			cFrom.set(Calendar.SECOND, 0);
			exFrom=cFrom.getTime();
		}
		
		log.debug("------exFrom---"+exFrom);

		if (exactTo!=null) {
			cTo.setTime(exactTo);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);
			exTo=cTo.getTime();
		}
		log.debug("------exTo---"+exTo);
		
		Date pFrom=null;
		Date pTo= null;
		
		if (pFrom !=null) {
			cFrom.setTime(periodFrom);
			cFrom.set(Calendar.HOUR_OF_DAY, 0);
			cFrom.set(Calendar.MINUTE, 0);
			cFrom.set(Calendar.SECOND, 0);
			pFrom=cFrom.getTime();
		}
		
		log.debug("------pfrom---"+pFrom);

		if (pTo!=null) {
			cTo.setTime(periodTo);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);
			pTo=cTo.getTime();
		}
		log.debug("------Pto---"+pTo);
		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		 
		//////////////////////////////////////////////////////////////////
		//////////////Date Strings Definition/////////////////////////////
		//////////////////////////////////////////////////////////////////
		String from_dateString=null;
		String to_dateString=null;
		String periodf_dateString=null;
		String periodt_dateString=null;
		String exactf_dateString=null;
		String exactt_dateString=null;
		//////////////////////////////////////////////////////////////////
		///////////////End of date strings definition
		/////////////////////////////////////////////////////////////////
	    log.debug("----from_dateString- after formatting--"+from_dateString);
	    
		Map map = new HashMap();
		
		jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));
		String query = "";
		String outerSelectStart = "SELECT a.*,mgr.FIRSTNAME mgrName, emp.ID empId,emp.EMPCODE employeeCode, emp.FIRSTNAME name, acc1.GROUP_ID,lev.ID levId, lev.EMP_ID mgrId"
				+ ",APPROVALS.APPROVAL, APPROVALS.APPROVAL_DATE , APPROVALS.USER_ID , APPROVALS.NOTE approvalNote "
				+ "FROM ";
		String select = "select loginUsersReq.*, reqType.DESCRIPTION, vac.name vacName FROM login_users_requests loginUsersReq  "
				+ "  JOIN REQUEST_TYPES reqType ON reqType.id=loginUsersReq.REQUEST_TYPE "
						+ " LEFT OUTER JOIN vacation vac ON  VAC.VACATION=loginUsersReq.VACATION   ";
		String where = null;
		String orderBy = "";

		
		
		String outerSelectEnd = ") a "
				+ "JOIN COMMON_EMPLOYEE emp ON a.empcode=emp.EMPCODE "
				+ "LEFT OUTER JOIN EMP_REQTYPE_ACC acc1 ON (acc1.REQ_ID=a.request_type AND acc1.EMP_ID=a.login_user) "
				+ "LEFT OUTER JOIN ACCESS_LEVELS lev ON acc1.GROUP_ID=lev.LEVEL_ID "
				+ "LEFT OUTER JOIN COMMON_EMPLOYEE mgr ON lev.EMP_ID = mgr.ID "
				+ "LEFT OUTER JOIN EMP_REQ_APPROVAL approvals ON approvals.LEVEL_ID=acc1.ID  AND APPROVALS.REQ_ID=a.id AND APPROVALS.USER_ID=lev.EMP_ID ";
		
		String outerSelectWhere = "";
		
//		if (mgrId != null) {
//			outerSelectWhere = " where mgr.id= " + mgrId;
//		}
		////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////Date Filters////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////
		log.debug("from date " + fromDate + "to date " + toDate);
		if (fromDate!= null && toDate!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			from_dateString=df.format(fromDate);
			to_dateString=df.format(toDate);
			where += " ((trunc(loginUsersReq.request_date) >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') and "
					+ "trunc(loginUsersReq.request_date)  <= TO_DATE('"+to_dateString+"','DD/MM/YYYY'))) ";
		}
		
		if (periodFrom!= null && periodTo!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			periodf_dateString=df.format(periodFrom);
			periodt_dateString=df.format(periodTo);
			where += " ((trunc(loginUsersReq.period_from)  >= TO_DATE('"+periodf_dateString+"','DD/MM/YYYY') and "
					+ "trunc(loginUsersReq.period_from) <= TO_DATE('"+periodt_dateString+"','DD/MM/YYYY'))) ";
		}
		
		if (exactFrom!= null && exactTo!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			exactf_dateString=df.format(exactFrom);
			exactt_dateString=df.format(exactTo);
			where += " ((trunc(loginUsersReq.from_date) >= TO_DATE('"+exactf_dateString+"','DD/MM/YYYY') and "
					+ "trunc(loginUsersReq.from_date) <= TO_DATE('"+exactt_dateString+"','DD/MM/YYYY'))) ";
		}
		////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////End of Date Filters///////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////
		
		
		if (requestNumber!=null && !requestNumber.isEmpty()) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.requestNumber="+requestNumber+" ";
		}
		
		
		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////Request type filters///////////////////////////////
		///////////////////////////////////////////////////////////////////////////////
		
		if(requestType!=null) {
		log.debug("1.requesttype " + requestType);
		if (requestType.equals(new Long(1))){
			log.debug("requesttype is 1");
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " (loginUsersReq.request_type="+requestType+" ) ";
			where += " and ((loginUsersReq.from_date is not null and loginUsersReq.to_date is not null) or (loginUsersReq.period_from is not null and loginUsersReq.period_to is not null))";
		} else if (requestType.equals(new Long(4))) {
			log.debug("requesttype is 4");
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.request_type=4  ";
			where += " and ((loginUsersReq.from_date is not null and loginUsersReq.to_date is not null) or (loginUsersReq.period_from is not null and loginUsersReq.period_to is not null))";
		} else if (requestType.equals(new Long(5))) {
			log.debug("requesttype is 5");
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.request_type=5  ";
			log.debug("ma2moreya");
		}   else if (requestType.equals(new Long(7))) {// all errands
			log.debug("requesttype is 4");
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " (loginUsersReq.request_type=4  ";
			where += " or (loginUsersReq.request_type=5  and ((loginUsersReq.from_date is not null and loginUsersReq.to_date is not null) or (loginUsersReq.period_from is not null and loginUsersReq.period_to is not null))))";
		}else if (requestType.equals(new Long(8))) {//7odoor w enseraf
			log.debug("requesttype is 8");
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " (loginUsersReq.request_type=10 or loginUsersReq.request_type=11)  ";
		}  
		else if (requestType.equals(new Long(10)) || requestType.equals(new Long(11))) {
			log.debug("requesttype is else");
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.request_type="+requestType+"  ";
		} else {
			log.debug("requesttype is else");
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.request_type=  " + requestType +  " ";
			where += " and ((loginUsersReq.from_date is not null and loginUsersReq.to_date is not null) or (loginUsersReq.period_from is not null and loginUsersReq.period_to is not null))";
		}
	} else {
		log.debug("2. requesttype " + requestType);
		
		if(where == null) {
			where = " where ";
		} else {
			where += " and ";
		}
		where += "((loginUsersReq.from_date is not null and loginUsersReq.to_date is not null) or (loginUsersReq.period_from is not null and loginUsersReq.period_to is not null) "
				+ " or (loginUsersReq.request_type=10 or loginUsersReq.request_type=11) ) ";
	}
		
		////////////////////////////////////////////////////////////////////////////////
		//////////////////////End of Request type filters///////////////////////////////
		///////////////////////////////////////////////////////////////////////////////
		if (empCode!= null && !empCode.isEmpty()) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.empCode="+empCode+" ";
		}
		////////////////////////////////////////////////////////////////////////////////
		if (codeFrom!= null && !codeFrom.isEmpty() && 
				codeTo!= null && !codeTo.isEmpty()) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.empCode between "+codeFrom+" and " + codeTo + " ";
		}
		///////////////////////////////////////////////////////////////////////////////
		if (statusId!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.approved = "+statusId;
		}
		
		/////////////////////////////////////////////////////////////////////
		if(isInsideCompany!=null) {
			if(isInsideCompany.equals("0")) {
				if(where == null) {
					where = " where ";
				} else {
					where += " and ";
				}
				where += " loginUsersReq.isInsideCompany = 0 ";
			} else if (isInsideCompany.equals("1")) {
				if(where == null) {
					where = " where ";
				} else {
					where += " and ";
				}
				where += " loginUsersReq.isInsideCompany = 1 ";
			}
		}
		////////////////////////////////////////////////////////////////////
		
		
		if (empReqTypeAccs !=null && !empReqTypeAccs.isEmpty()) {
			log.debug("empReqTypeAccs " +  empReqTypeAccs.toString().replace("[", "").replace("]", ""));
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " loginUsersReq.login_user in (  " + empReqTypeAccs.toString().replace("[", "").replace("]", "") + " ) ";
		}
		
//		where += " AND reqType.id=loginUsersReq.REQUEST_TYPE AND VAC.VACATION=loginUsersReq.VACATION  "; 
		///////////////////////////////////////////////////////////////////
		
		if (sort.equalsIgnoreCase("desc")) {
			orderBy = " order by requestNumber desc,period_from desc ";
		} else if (sort.equalsIgnoreCase("asc")) {
			orderBy = " order by requestNumber desc,period_from asc ";
		}
		/////////////////////////////////////////////////////////////////
		
		
		
		query = "select * from (select rownum rnum, q.* from (" + outerSelectStart + " ("
				+select + where+orderBy+
				outerSelectEnd + outerSelectWhere +orderBy+") q where rownum<="+((pageNumber*pageSize)+pageSize-1)+") where rnum>"+(pageSize*pageNumber)  + orderBy;
		log.debug("query " + query);
		StringBuilder sql = new StringBuilder(query);
		
		log.debug("host name " + hostName);
		log.debug("service name " + serviceName);
		log.debug("username " + userName);
		log.debug("password " + password);
		log.debug("----sql 1---"+sql);

		
		List in=(List) jdbcTemplate.queryForList(sql.toString());
		
		String listSizeQuery = "select count (*) from ("+outerSelectStart+ " ("+select+where+orderBy
				+outerSelectEnd + outerSelectWhere+orderBy+")"+orderBy;
		log.debug("listSizeQuery " + listSizeQuery);
		StringBuilder sqlListSize = new StringBuilder(listSizeQuery);
		List in2=(List) jdbcTemplate.queryForList(sqlListSize.toString());
		
		//////class in list results is LinkedCaseInsensitiveMap 
		
		int i=0;
		
		
		DateFormat d= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
		log.debug("size "+in.size());
		
//		while(i<in.size()){
//			
//			i++;
//		}
		log.debug(in2.size());
		map.put("results", in);
		if (in2.size()>0) {
			log.debug("listSize " + ((LinkedCaseInsensitiveMap)in2.get(0)).get("count(*)"));
			map.put("listSize", ((BigDecimal)((LinkedCaseInsensitiveMap)in2.get(0)).get("count(*)")).intValue());
		} else {
			map.put("listSize", new Long(0));
		}
		return map;
	}
	
	
	
	
	
	
	
}
