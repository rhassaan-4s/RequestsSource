package com._4s_.attendance.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.hibernate.hql.ast.tree.DeleteStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.model.TimesheetCostCenter;
import com._4s_.timesheet.model.TimesheetTransactionParts;
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

public class AttendanceExternalQueries {
	protected final Log log = LogFactory.getLog(getClass());

	private JdbcTemplate jdbcTemplate;
	private BasicDataSource basicDataSource;
	private JdbcTemplate exjt;
	
	private PlatformTransactionManager platformTransactionManager;
	
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
	
public Integer getNumberOfAttendees(Date fromDate, Date toDate, String hostName, String serviceName, String userName, String password) {
		
		log.debug("///////////////////////getNumberOfAttendeesAndWorkersByDepartment//////////////////////////");
		//////////////////////////////////////////////////////////////////
		//////////////Date Strings Definition/////////////////////////////
		//////////////////////////////////////////////////////////////////
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String from_dateString=null;
		String to_dateString=null;
		
		from_dateString=df.format(fromDate);
		to_dateString=df.format(toDate);
		log.debug("from " + from_dateString);
		log.debug("to " + to_dateString);
		//////////////////////////////////////////////////////////////////
		///////////////End of date strings definition
		/////////////////////////////////////////////////////////////////

		Map map = new HashMap();

		jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));
		String query = "";
		String select = "";
		String from = "";
		String where = "";
		String groupBy = "";

		where = " and  ((trunc(login.from_date) >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') and  trunc(login.from_date)  <= TO_DATE('"+to_dateString+"','DD/MM/YYYY'))) ";

		query = " select  COUNT(emp.location) attendance "
				+ "	from LOCATION location "
				+ "	JOIN empbasic emp ON location.location = emp.LOCATION "
				+ "	LEFT OUTER JOIN LOGIN_USERS_REQUESTS login ON  login.empCode = emp.empCode WHERE emp.END_SERV IS NULL AND login.REQUEST_TYPE=10  "+ where;
		log.debug("query " + query);
		List in=(List) jdbcTemplate.queryForList(query);
		log.debug("in list " + in);
		
		LinkedCaseInsensitiveMap resultMap = (LinkedCaseInsensitiveMap) in.get(0);
		BigDecimal atten =   (BigDecimal)resultMap.get("attendance");
		return atten.intValue();
}
	
	public List getNumberOfAttendeesAndWorkersByDepartment(Date fromDate, Date toDate, String hostName, String serviceName, String userName, String password) {
		
		log.debug("///////////////////////getNumberOfAttendeesAndWorkersByDepartment//////////////////////////");
		//////////////////////////////////////////////////////////////////
		//////////////Date Strings Definition/////////////////////////////
		//////////////////////////////////////////////////////////////////
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String from_dateString=null;
		String to_dateString=null;
		
		from_dateString=df.format(fromDate);
		to_dateString=df.format(toDate);
		log.debug("from " + from_dateString);
		log.debug("to " + to_dateString);
		//////////////////////////////////////////////////////////////////
		///////////////End of date strings definition
		/////////////////////////////////////////////////////////////////

		Map map = new HashMap();

		jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));
		String query = "";
		String select = "";
		String from = "";
		String where = "";
		String groupBy = "";

		where = " and  ((trunc(login.from_date) >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') and  trunc(login.from_date)  <= TO_DATE('"+to_dateString+"','DD/MM/YYYY'))) ";

		query = " SELECT allWorkers.count1 workers,attendees.count2 attendance, allWorkers.loc1 locationCode, allWorkers.locName1 locationName "
				+ "FROM "
				+ "(select  COUNT(emp.location) count1 ,emp.LOCATION loc1, LOCATION.NAME locName1 "
				+ "from LOCATION location  JOIN empbasic emp ON location.location = emp.LOCATION  "
				+ "LEFT OUTER JOIN LOGIN_USERS_REQUESTS login ON  login.empCode = emp.empCode AND login.REQUEST_TYPE=10  " + where
				+ "WHERE emp.END_SERV IS NULL "
				+ "group by emp.location,location.location,location.NAME "
				+ "ORDER BY LOCATION.location ) allWorkers "
				+ "LEFT OUTER JOIN  "
				+ "(select  COUNT(emp.location) count2,emp.LOCATION loc2, LOCATION.NAME locName2 "
				+ "from LOCATION location  JOIN empbasic emp ON location.location = emp.LOCATION "
				+ "LEFT OUTER JOIN LOGIN_USERS_REQUESTS login ON  login.empCode = emp.empCode "
				+ "WHERE emp.END_SERV IS NULL AND login.REQUEST_TYPE=10  " + where
				+ "group by emp.location,location.location,location.NAME "
				+ "ORDER BY LOCATION.location ) attendees ON allWorkers.loc1=attendees.loc2";
		log.debug("query " + query);
		List in=(List) jdbcTemplate.queryForList(query);
		log.debug("in list " + in);
		Iterator itr = in.iterator();
		LinkedCaseInsensitiveMap resultMap = null;
		Object workersObj = null;
		String workers = "";
		Integer diff = 0;
		Object attendanceObj = null;
		String attendance = "";
		String locCode = "";
		String locName = "";
		
		List chartAbsence = new ArrayList();
		List chartAttendance = new ArrayList();
		List chartLocations = new ArrayList();
		while (itr.hasNext()) {
			resultMap = (LinkedCaseInsensitiveMap) itr.next();
			workersObj = resultMap.get("workers");
//			log.debug("workers no " + workersObj);
			if (workersObj==null) {
				workers = "0";
			} else {
				workers = workersObj.toString();
			}
			attendanceObj = resultMap.get("attendance");
//			log.debug("attendance " + attendanceObj);
			if (attendanceObj==null) {
				attendance = "0";
			} else {
				attendance = attendanceObj.toString();
			}
//			log.debug("workers " + workers + " attendance " + attendance);
			diff = Integer.parseInt(workers)-Integer.parseInt(attendance);
//			log.debug("diff " + diff);
			locCode = resultMap.get("locationCode").toString();
//			log.debug("loccode " + locCode);
			locName = resultMap.get("locationName").toString();
//			log.debug("locName " + locName);
			chartAbsence.add(diff);
			chartAttendance.add(Integer.parseInt(attendance));
			chartLocations.add(locName);
		}
		log.debug("chart absence " + chartAbsence);
		log.debug("chartAttendance " + chartAttendance);
		log.debug("chartLocations " + chartLocations);
		List results = new ArrayList();
		results.add(chartAbsence);
		results.add(chartAttendance);
		results.add(chartLocations);
		return results;
}
	
	
public List getDashboardRequests(Date fromDate, Date toDate, String hostName, String serviceName, String userName, String password) {
		
		log.debug("///////////////////////getNumberOfAttendeesAndWorkersByDepartment//////////////////////////");
		//////////////////////////////////////////////////////////////////
		//////////////Date Strings Definition/////////////////////////////
		//////////////////////////////////////////////////////////////////
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String from_dateString=null;
		String to_dateString=null;
		
		from_dateString=df.format(fromDate);
		to_dateString=df.format(toDate);
		log.debug("from " + from_dateString);
		log.debug("to " + to_dateString);
		//////////////////////////////////////////////////////////////////
		///////////////End of date strings definition
		/////////////////////////////////////////////////////////////////

		Map map = new HashMap();

		jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));
		String query = "";
		String select = "";
		String from = "";
		String where = "";
		String groupBy = "";

		where = " and  (trunc(login.from_date) = TO_DATE('"+from_dateString+"','DD/MM/YYYY') ) ";

		query = "select  COUNT(LOGIN.REQUEST_TYPE) empCount,reqType.DESCRIPTION reqName "
				+ "	from REQUEST_TYPES reqType  "
				+ "	LEFT OUTER JOIN LOGIN_USERS_REQUESTS login ON  login.REQUEST_TYPE = REQTYPE.ID " + where
				+ " where reqType.hidden=0  " 
				+ "	GROUP BY LOGIN.REQUEST_TYPE,reqType.DESCRIPTION";
		log.debug("query " + query);
		List in=(List) jdbcTemplate.queryForList(query);
		log.debug("in list " + in);
		
		LinkedCaseInsensitiveMap resultMap = null;
		Iterator itr = in.iterator();
		List empCount = new ArrayList();
		List reqName = new ArrayList();
		
		Object empCountObj = null;
		Object reqNameObj = null;
		
		String empC = "";
		String reqN = "";
		
		while (itr.hasNext()) {
			resultMap = (LinkedCaseInsensitiveMap) itr.next();
			empCountObj = resultMap.get("empCount");
//			log.debug("workers no " + workersObj);
			if (empCountObj==null) {
				empC = "0";
			} else {
				empC = empCountObj.toString();
			}
			reqNameObj = resultMap.get("reqName");
//			log.debug("attendance " + attendanceObj);
			if (reqNameObj==null) {
				reqN = "";
			} else {
				reqN = reqNameObj.toString();
			}
			empCount.add(empC);
			reqName.add(reqN);
		}
		log.debug("empCount " + empCount);
		log.debug("reqName " + reqName);
		List results = new ArrayList();
		results.add(empCount);
		results.add(reqName);
		return results;

		
}


	
	
	public Map getTimesheetTransactions(String hostName,String serviceName,String userName,String password,String empCode, Date fromDate,
			Date toDate, TimesheetCostCenter costcenter,
			TimesheetActivity activity, TimesheetTransactionParts part1,
			TimesheetTransactionParts part2, TimesheetTransactionParts part3,
			int pageNo, int pageSize, String sort) {
		Calendar cFrom= Calendar.getInstance();

		Date dateFrom=null;
		Date dateTo= null;
		log.debug("------fromDate---"+fromDate);
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

		//////////////////////////////////////////////////////////////////
		//////////////Date Strings Definition/////////////////////////////
		//////////////////////////////////////////////////////////////////
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String from_dateString=null;
		String to_dateString=null;
		//////////////////////////////////////////////////////////////////
		///////////////End of date strings definition
		/////////////////////////////////////////////////////////////////

		Map map = new HashMap();

		jdbcTemplate = new JdbcTemplate(createDataSource(hostName,serviceName,userName,password));
		String query = "";
		String select = "";
		String where = "";
		String orderBy = "";

		select = "select trans.empCode empCode,to_char(trans.inDate , 'DD/MM/YYYY') inDate,trans.activity activityCode,act.name,trans.costcode costCode,cost.costName costName,"
				+ " (CASE WHEN trans.part1 = 9999999999 THEN null ELSE trans.part1 END) AS partCode1, "
				+ "part1.name partName1, "
				+ "(CASE WHEN trans.part2 = 9999999999 THEN null ELSE trans.part2 END) AS partCode2,"
				+ " part2.name partName2,"
				+ "(CASE WHEN trans.part3 = 9999999999 THEN null ELSE trans.part3 END) AS partCode3,part3.name partName3 "
				+ " , trans.cHour cHour, trans.cMinute cMinute, trans.remark remark "
				+ " from  emp_dist_time_sheet trans, cost cost, activity act, time_sheet_parts part1, time_sheet_parts part2, time_sheet_parts part3 ";
		where = " where trans.costcode=cost.costcode and act.activity=trans.activity and part1.code=trans.part1 and part2.code=trans.part2 and part3.code=trans.part3 "
				+ " and trans.empCode in (" + empCode+") ";

		log.debug("from date " + fromDate + "to date " + toDate);
		if (fromDate!= null && toDate!=null) {
			if(where == null || where.isEmpty()) {
				where = " where ";
			} else {
				where += " and ";
			}
			from_dateString=df.format(fromDate);
			to_dateString=df.format(toDate);
			where += " ((trunc(trans.inDate) >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') and "
					+ "trunc(trans.inDate)  <= TO_DATE('"+to_dateString+"','DD/MM/YYYY'))) ";
		}
		if (costcenter!=null) {
			if(where == null || where.isEmpty()) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " trans.costCode =" + costcenter.getCostCode();
		}
		if (activity!=null) {
			if(where == null || where.isEmpty()) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " trans.activity= " + activity.getActivity();
		}
		if (part1!=null) {
			if(where == null || where.isEmpty()) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " trans.part1 = " + part1.getCode();
		}
		if (part2!=null) {
			if(where == null || where.isEmpty()) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " trans.part2 = " + part2.getCode(); 
		}
		if (part3!=null) {
			if(where == null || where.isEmpty()) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " trans.part3 = " + part3.getCode();
		}

		///////////////////////////////////////////////////////////////////

		if (sort!= null && !sort.isEmpty()) {
			if (sort.equalsIgnoreCase("desc")) {
				orderBy = " order by trans.inDate desc ";
			} else if (sort.equalsIgnoreCase("asc")) {
				orderBy = " order by trans.inDate asc ";
			}
		}
		/////////////////////////////////////////////////////////////////
		
		query = select + where + orderBy;
		log.debug(query);
		StringBuilder sql = new StringBuilder(query);
		
		List in=(List) jdbcTemplate.queryForList(sql.toString());
		
		String listSizeQuery = "select count (*) from ("+query+")";
		log.debug(listSizeQuery);
		log.debug("listSizeQuery " + listSizeQuery);
		StringBuilder sqlListSize = new StringBuilder(listSizeQuery);
		List in2=(List) jdbcTemplate.queryForList(sqlListSize.toString());
		
		log.debug(in2.size());
		map.put("Results", in);
		if (in2.size()>0) {
			log.debug("listSize " + ((LinkedCaseInsensitiveMap)in2.get(0)).get("count(*)"));
			map.put("listSize", ((BigDecimal)((LinkedCaseInsensitiveMap)in2.get(0)).get("count(*)")).intValue());
		} else {
			map.put("listSize", new Long(0));
		}
		return map;
	}


	


	
	
}
