package com._4s_.attendance.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.hibernate.hql.ast.tree.DeleteStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com._4s_.common.model.Settings;
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
	private String driverClass = "";
	private String url = "";
	private String dialect = "";
	private String username="";
	private String password="";
	
	private PlatformTransactionManager platformTransactionManager;
	
	public PlatformTransactionManager getPlatformTransactionManager() {
		return platformTransactionManager;
	}


	public void setPlatformTransactionManager(
			PlatformTransactionManager platformTransactionManager) {
		this.platformTransactionManager = platformTransactionManager;
	}

	private BasicDataSource createDataSource(String contextPath) {
		Properties prop = loadProperties(contextPath);
		if(basicDataSource == null){
			basicDataSource = new BasicDataSource();
			driverClass = prop.getProperty("hibernate.connection.driver_class");
			url = prop.getProperty("hibernate.connection.url");
			username = prop.getProperty("hibernate.connection.username");
			password = prop.getProperty("hibernate.connection.password");
			
			basicDataSource.setDriverClassName(driverClass);
			basicDataSource.setUrl(url);
			basicDataSource.setUsername(username);
			basicDataSource.setPassword(password);
		}
		return basicDataSource;
	}
	
	private Properties loadProperties(String contextPath) {
//		String contextPath = request.getSession().getServletContext().getRealPath("/");
		Properties prop = null;
		try {
			InputStream input = new FileInputStream(contextPath+"/WEB-INF/classes/hibernate.properties");
			prop = new Properties();
			prop.load(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}
public Integer getNumberOfAttendees(String contextPath,Date fromDate, Date toDate,Settings settings) {
		
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

		jdbcTemplate = new JdbcTemplate(createDataSource(contextPath));
		String query = "";
		String select = "";
		String from = "";
		String where = "";
		String groupBy = "";

//		where = " and  ((trunc(login.from_date) >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') and  trunc(login.from_date)  <= TO_DATE('"+to_dateString+"','DD/MM/YYYY'))) ";

		if (settings.getSqlServerConnectionEnabled()) {
			where = " and  (CONVERT(VARCHAR, login.from_date, 101) = '"+from_dateString+"' ) ";
		} else {
			where = " and  (trunc(login.from_date) = TO_DATE('"+from_dateString+"','DD/MM/YYYY') ) ";
		}
		
		query = " select  COUNT(emp.location) attendance "
				+ "	from LOCATION location "
				+ "	JOIN empbasic emp ON location.location = emp.LOCATION "
				+ "	LEFT OUTER JOIN LOGIN_USERS_REQUESTS login ON  login.empCode = emp.empCode WHERE emp.END_SERV IS NULL AND login.REQUEST_TYPE=10  "+ where;
		log.debug("query " + query);
		List in=(List) jdbcTemplate.queryForList(query);
		log.debug("in list " + in);
		
		LinkedCaseInsensitiveMap resultMap = (LinkedCaseInsensitiveMap) in.get(0);
		if (settings.getSqlServerConnectionEnabled()) {
			Integer atten =   (Integer)resultMap.get("attendance");
			return atten.intValue();
		} else {
			BigDecimal atten =   (BigDecimal)resultMap.get("attendance");
			return atten.intValue();
		}
}
	
	public List getNumberOfAttendeesAndWorkersByDepartment(String contextPath,Date fromDate, Date toDate,Settings settings) {
		
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

		jdbcTemplate = new JdbcTemplate(createDataSource(contextPath));
		String query = "";
		String select = "";
		String from = "";
		String where = "";
		String groupBy = "";

//		where = " and  ((trunc(login.from_date) >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') and  trunc(login.from_date)  <= TO_DATE('"+to_dateString+"','DD/MM/YYYY'))) ";

		if (settings.getSqlServerConnectionEnabled()) {
			where = " and  (CONVERT(VARCHAR, login.from_date, 101) = '"+from_dateString+"' ) ";
		} else {
			where = " and  (trunc(login.from_date) = TO_DATE('"+from_dateString+"','DD/MM/YYYY') ) ";
		}
		
		
		query = " SELECT allWorkers.count1 workers,attendees.count2 attendance, allWorkers.loc1 locationCode, allWorkers.locName1 locationName "
				+ "FROM "
				+ "(select  COUNT(emp.location) count1 ,emp.LOCATION loc1, LOCATION.NAME locName1 "
				+ "from LOCATION location  JOIN empbasic emp ON location.location = emp.LOCATION  "
				+ "LEFT OUTER JOIN LOGIN_USERS_REQUESTS login ON  login.empCode = emp.empCode AND login.REQUEST_TYPE=10  " + where
				+ "WHERE emp.END_SERV IS NULL "
				+ "group by emp.location,location.location,location.NAME "
				+ ") allWorkers "
				+ "LEFT OUTER JOIN  "
				+ "(select  COUNT(emp.location) count2,emp.LOCATION loc2, LOCATION.NAME locName2 "
				+ "from LOCATION location  JOIN empbasic emp ON location.location = emp.LOCATION "
				+ "LEFT OUTER JOIN LOGIN_USERS_REQUESTS login ON  login.empCode = emp.empCode "
				+ "WHERE emp.END_SERV IS NULL AND login.REQUEST_TYPE=10  " + where
				+ "group by emp.location,location.location,location.NAME "
				+ ") attendees ON allWorkers.loc1=attendees.loc2 ORDER BY locationCode";
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
	
	
public List getDashboardRequests(String contextPath,Date fromDate, Date toDate,Settings settings) {
		
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

		jdbcTemplate = new JdbcTemplate(createDataSource(contextPath));
		String query = "";
		String select = "";
		String from = "";
		String where = "";
		String groupBy = "";

		

		if (settings.getSqlServerConnectionEnabled()) {
			where = " and  (CONVERT(VARCHAR, login.from_date, 101) = '"+from_dateString+"' ) ";
		} else {
			where = " and  (trunc(login.from_date) = TO_DATE('"+from_dateString+"','DD/MM/YYYY') ) ";
		}
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





	
	
}
