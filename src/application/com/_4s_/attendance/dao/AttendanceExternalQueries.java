package com._4s_.attendance.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
//import org.hibernate.hql.ast.tree.DeleteStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com._4s_.attendance.web.util.DashboardAttendeesCountByDepWrapper;
import com._4s_.attendance.web.util.DashboardReqTypeCountsWrapper;
import com._4s_.common.dao.CommonQueries;
import com._4s_.common.model.Settings;
import com._4s_.requestsApproval.web.util.TimeAttendanceWrapper;

public class AttendanceExternalQueries extends CommonQueries{
	protected final Log log = LogFactory.getLog(getClass());
	
	private Session session;
	private SessionFactory sessionFactory;
	
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public Session getCurrentSession(){
		session = null;
    	log.debug("$$$$$$$$$$$$$$$$$$getting current session");
    	System.out.println("$$$$$$$$$$$$$$$$$$getting current session");
    	log.debug("session factory " + sessionFactory);
    	System.out.println("$$$$$$$$$$$$$$$$$$session factory " + sessionFactory);
    	try {
    	    session = sessionFactory.getCurrentSession();
    	    log.debug("***session available " + session);
    	    if (session == null || session.isOpen()==false) {
    	    	session = sessionFactory.openSession();
    	    	System.out.println("session " + session);
    	    }
    	} catch (HibernateException e) {
    		log.debug("###Exception#### session not available, will open new session");
    	    session = sessionFactory.openSession();
    	    log.debug("***********new session opened****************");
    	}
    	System.out.println("$$$$$$$$$$$$$$$$$$session " +session);
	      return session;
	}

public Integer getNumberOfAttendees(Date fromDate, Date toDate,Settings settings) {
		
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

		//setJdbcTemplate(new JdbcTemplate(createDataSource()));
		String query = "";
		String select = "";
		String from = "";
		String where = "";
		String groupBy = "";

//		where = " and  ((trunc(login.from_date) >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') and  trunc(login.from_date)  <= TO_DATE('"+to_dateString+"','DD/MM/YYYY'))) ";

		if (settings.getSqlServerConnectionEnabled()) {
			where = " and  (CONVERT(VARCHAR, login.from_date, 103) = '"+from_dateString+"' ) ";
		} else {
			where = " and  (trunc(login.from_date) = TO_DATE('"+from_dateString+"','DD/MM/YYYY') ) ";
		}
		
		query = " select  COUNT(emp.location) attendance "
				+ "	from LOCATION location "
				+ "	JOIN empbasic emp ON location.location = emp.LOCATION "
				+ "	LEFT OUTER JOIN LOGIN_USERS_REQUESTS login ON  login.empCode = emp.empCode WHERE emp.END_SERV IS NULL AND login.REQUEST_TYPE=10  "+ where;
		log.debug("query " + query);
//		List in=(List) getJdbcTemplate().queryForList(query);
		
		List in;
		if (session == null || session.isOpen()==false)  {
			session=getCurrentSession();
		}
		try{
			Query q = session.createNativeQuery(query.toString());
			in = q.getResultList();
//			cc1=getJdbcTemplate().queryForObject(sql.toString(),Long.class);
			log.debug("----cc1---"+in.size());
		}catch (Exception e) {
			in= new ArrayList();
		}
		
		log.debug("in list " + in);
		
		BigDecimal resultMap = (BigDecimal) in.get(0);
		return resultMap.intValue();
//		if (settings.getSqlServerConnectionEnabled()) {
//			Integer atten =   (Integer)resultMap.get("attendance");
//			return atten.intValue();
//		} else {
//			BigDecimal atten =   (BigDecimal)resultMap.get("attendance");
//			return atten.intValue();
//		}
}
	
	public List getNumberOfAttendeesAndWorkersByDepartment(Date fromDate, Date toDate,Settings settings) {
		
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

		//setJdbcTemplate(new JdbcTemplate(createDataSource()));
		String query = "";
		String select = "";
		String from = "";
		String where = "";
		String groupBy = "";

//		where = " and  ((trunc(login.from_date) >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') and  trunc(login.from_date)  <= TO_DATE('"+to_dateString+"','DD/MM/YYYY'))) ";

		if (settings.getSqlServerConnectionEnabled()) {
			where = " and  (CONVERT(VARCHAR, login.from_date, 103) = '"+from_dateString+"' ) ";
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
//		List in=(List) getJdbcTemplate().queryForList(query);
		if (session == null || session.isOpen()==false) {
    		session = getCurrentSession();
    	}
		List in;
		try{
			Query q = session.createNativeQuery(query.toString());
//			in = q.getResultList();
			in = getResultList(session,q,DashboardAttendeesCountByDepWrapper.class);
//			cc1=getJdbcTemplate().queryForObject(sql.toString(),Long.class);
			log.debug("----cc1---"+in.size());
		}catch (Exception e) {
			in= new ArrayList();
		}
		
		log.debug("in list " + in);
		Iterator itr = in.iterator();
		DashboardAttendeesCountByDepWrapper resultMap = null;
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
			resultMap = (DashboardAttendeesCountByDepWrapper) itr.next();
			workersObj = resultMap.getWorkers();
//			log.debug("workers no " + workersObj);
			if (workersObj==null) {
				workers = "0";
			} else {
				workers = workersObj.toString();
			}
			attendanceObj = resultMap.getAttendance();
//			log.debug("attendance " + attendanceObj);
			if (attendanceObj==null) {
				attendance = "0";
			} else {
				attendance = attendanceObj.toString();
			}
//			log.debug("workers " + workers + " attendance " + attendance);
			diff = Integer.parseInt(workers)-Integer.parseInt(attendance);
//			log.debug("diff " + diff);
			locCode = resultMap.getLocationCode();
//			log.debug("loccode " + locCode);
			locName = resultMap.getLocationName();
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
	
	
public List getDashboardRequests(Date fromDate, Date toDate,Settings settings) {
		
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

		//setJdbcTemplate(new JdbcTemplate(createDataSource()));
		String query = "";
		String select = "";
		String from = "";
		String where = "";
		String groupBy = "";

		

		if (settings.getSqlServerConnectionEnabled()) {
			where = " and  (CONVERT(VARCHAR, login.from_date, 103) = '"+from_dateString+"' ) ";
		} else {
			where = " and  (trunc(login.from_date) = TO_DATE('"+from_dateString+"','DD/MM/YYYY') ) ";
		}
		query = "select  COUNT(LOGIN.REQUEST_TYPE) empCount,reqType.DESCRIPTION reqName "
				+ "	from REQUEST_TYPES reqType  "
				+ "	LEFT OUTER JOIN LOGIN_USERS_REQUESTS login ON  login.REQUEST_TYPE = REQTYPE.ID " + where
				+ " where reqType.hidden=0  " 
				+ "	GROUP BY LOGIN.REQUEST_TYPE,reqType.DESCRIPTION";
		log.debug("query " + query);
		
		
//		List in=(List) getJdbcTemplate().queryForList(query);
		if (session == null || session.isOpen()==false)  {
			session=getCurrentSession();
		}
		List in;
		try{
			Query q = session.createNativeQuery(query.toString());
			in = getResultList(session,q,DashboardReqTypeCountsWrapper.class);
//			in = q.getResultList();
//			cc1=getJdbcTemplate().queryForObject(sql.toString(),Long.class);
			log.debug("----cc1---"+in.size());
		}catch (Exception e) {
			in= new ArrayList();
		}
		log.debug("in list " + in);
		
		DashboardReqTypeCountsWrapper resultMap = null;
		Iterator itr = in.iterator();
		List empCount = new ArrayList();
		List reqName = new ArrayList();
		
		Object empCountObj = null;
		Object reqNameObj = null;
		
		String empC = "";
		String reqN = "";
		
		while (itr.hasNext()) {
			resultMap = (DashboardReqTypeCountsWrapper) itr.next();
			empCountObj = resultMap.getEmpCount();
//			log.debug("workers no " + workersObj);
			if (empCountObj==null) {
				empC = "0";
			} else {
				empC = empCountObj.toString();
			}
			reqNameObj = resultMap.getReqName();
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
