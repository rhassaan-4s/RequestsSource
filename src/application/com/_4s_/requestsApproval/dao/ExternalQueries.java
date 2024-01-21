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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
//import org.hibernate.hql.ast.tree.DeleteStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com._4s_.common.dao.CommonQueries;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.requestsApproval.web.action.TimeAttend;

public class ExternalQueries extends CommonQueries{
	protected final Log log = LogFactory.getLog(getClass());

	public int insertTimeAttend (String emp_code, Date date_, Date time_, String trans_type) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

		DefaultTransactionDefinition paramTransactionDefinition = new    DefaultTransactionDefinition();

		TransactionStatus status=getPlatformTransactionManager().getTransaction(paramTransactionDefinition );
		log.debug("date_ " + date_);
		log.debug("simpleDateFormat.format(date_) " + simpleDateFormat.format(date_));

		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		StringBuilder sql = new StringBuilder(
				" insert into time_attend (emp_code,date_,time_,trans_type) values ('" +emp_code
				+ "',to_date('" + simpleDateFormat.format(date_) + "','dd-MM-YYYY hh:mi:ss'),to_date('" + simpleDateFormat.format(time_) + "','dd-MM-YYYY hh:mi:ss'),'" + trans_type+"')");
		log.debug(sql.toString());
		try {
			getJdbcTemplate().update(sql.toString());

			log.debug("will commit");
			getPlatformTransactionManager().commit(status);
			return 1;
			//			getJdbcTemplate().getDataSource().getConnection().commit();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug("will rollback " + e.getMessage());
			getPlatformTransactionManager().rollback(status);
			return -1;
		}
	}

	public Long getVacationLimit (String empCode, String vacId, Date from_date){
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



		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		StringBuilder sql = new StringBuilder(
				" select entitled+previous from vac_limit where empcode = '" +empCode
				+ "' and vacation = '" + vacId + "' and year = '"
				+ year+"'");

		log.debug("----sql1---"+sql);
		try{
			cc1=getJdbcTemplate().queryForLong(sql.toString());
			log.debug("----cc1---"+cc1);
		}catch (Exception e) {
			cc1=new Long(0);
		}

		return cc1;
	}


	@SuppressWarnings("deprecation")
	public Long getVacationLimit (String empCode, Long reqId, String vacId, Date from_date){
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



		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		StringBuilder sql = new StringBuilder(
				" select entitled+previous from vac_limit where empcode = '" +empCode
				+ "' and vacation = '" + vacId + "' and year = '"
				+ year+"'");

		log.debug("----sql1---"+sql);
		try{
			cc1=getJdbcTemplate().queryForLong(sql.toString());
			log.debug("----cc1---"+cc1);
		}catch (Exception e) {
			cc1=new Long(0);
		}

		return cc1;
	}

	public Long getEmpVacation (String empCode, String vacId, Date from_date){
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
			cc2=getJdbcTemplate().queryForLong(sql.toString());
		}catch (Exception e) {
			cc2=new Long(0);
		}

		//		log.debug("----cc2---"+cc2);
		//		Long result=cc1-cc2;
		//		log.debug("----cc1-cc2---"+result);

		return cc2;
	}


	@SuppressWarnings("deprecation")
	public Long getEmpVacation (String empCode, Long reqId, String vacId, Date from_date){
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
			cc2=getJdbcTemplate().queryForLong(sql.toString());
		}catch (Exception e) {
			cc2=new Long(0);
		}

		//		log.debug("----cc2---"+cc2);
		//		Long result=cc1-cc2;
		//		log.debug("----cc1-cc2---"+result);

		return cc2;
	}

	@SuppressWarnings("deprecation")
	public Long getVacationCredit (String empCode, Long reqId, String vacId, Date from_date){
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


		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		StringBuilder sql = new StringBuilder(
				" select entitled+previous from vac_limit where empcode = '" +empCode
				+ "' and vacation = '" + vacId + "' and year = '"
				+ year+"'");

		log.debug("getVacationCredit----sql1---"+sql);
		try{
			cc1=getJdbcTemplate().queryForLong(sql.toString());
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
			cc2=getJdbcTemplate().queryForLong(sql.toString());
		}catch (Exception e) {
			cc2=new Long(0);
		}

		log.debug("----cc2---"+cc2);
		Long result=cc1-cc2;
		log.debug("----cc1-cc2---"+result);

		return result;
	}

	public List getTimeAttend (String empCode, Date from_date, Date to_date){

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
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
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


		List in=(List) getJdbcTemplate().queryForList(sql.toString());


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


	public List getTimeAttendAndroid (String empCode, Date from_date, Date to_date){

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
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		StringBuilder sql = new StringBuilder(
				" select min(t.period_from) as minDate, max(t.period_from) as maxDate, t.empcode as emp , e.firstName fName, e.lastName lName " 
						+" from LOGIN_USERS_REQUESTS t, common_employee e where " + emp
						+ " e.empCode=t.empcode and t.from_date between to_date('" + from_dateString + "', 'DD/MM/YYYY') and to_date('"
						+ to_dateString+"', 'DD/MM/YYYY') group by t.from_date, t.empcode,e.firstName,e.lastName order by t.from_date");
		log.debug("----sql 1---"+sql);



		List in=(List) getJdbcTemplate().queryForList(sql.toString());


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


	public List getTimeAttendFromViewForTimeAttendanceReport (String empCode, Date from_date, Date to_date,Settings settings){

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
		String empCodeOrder = "";
		if (empCode.contains(",")) {
			empCodeOrder = " empCode, ";
		}
		setJdbcTemplate(new JdbcTemplate(createDataSource()));


		String unionAll = "";
		String selectUnionDate = "";
		String joinDateUnionCondition1 = "";
		if (settings.getSqlServerConnectionEnabled()) {
			selectUnionDate = "convert(varchar, ta.TIME_,20) AS attendance_time,\n";
		} else {
			selectUnionDate = "TO_CHAR(ta.TIME_,'YYYY-MM-DD hh24:MI:ss') AS attendance_time,\n";
		}
		
		if (settings.getSqlServerConnectionEnabled()) {
			joinDateUnionCondition1 = " convert(varchar, ta.DATE_,23)=convert(varchar, empdays.DD,23)\n" ;
		} else {
			joinDateUnionCondition1 = " TO_CHAR(ta.DATE_,'YYYY-MM-DD')=TO_CHAR(EMPDAYS.DD,'YYYY-MM-DD')\n" ;
		}
		
		if (empCode.contains(",")) {
			unionAll = "UNION ALL\n" + 
					"(SELECT empdays.DD , empdays.EMPCODE,emp.FIRSTNAME fName,-- empcode2, ta.DATE_ attendanceDate, ta.TIME_ attendanceTime\n" + 
					"ta.DATE_  AS  attendance_date,\n" + 
					selectUnionDate + 
					"(CASE WHEN ta.TRANS_TYPE='I' THEN 'IN' WHEN ta.TRANS_TYPE='O' THEN 'OUT' END ) AS ATTENDANCE_Type,\n" + 
					"(CASE WHEN ta.date_ IS NOT NULL THEN 'Approved' END) AS approval,\n" + 
					"(CASE WHEN ta.INPUTTYPE=0 THEN 'Web_Attendance' WHEN ta.INPUTTYPE=1 THEN 'Request_Attendance' WHEN ta.INPUTTYPE=2 THEN 'Android_Attendance' ELSE 'Fingerprint_Attendance' END) AS input_type,\n" +
					"longitude as longitude, latitude as latitude \n"+
					"FROM ALL_EMP_DAYS empdays JOIN TIME_ATTEND ta\n" + 
					 "ON  ta.EMP_CODE=empDays.EMPCODE AND " +joinDateUnionCondition1+
					"JOIN COMMON_EMPLOYEE emp ON emp.EMPCODE=empdays.EMPCODE \n" +
					"where\n" + 
					"empdays.EMPCODE in ("+empCode+")\n" + 
					"AND empdays.DD >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') AND empdays.DD <= TO_DATE('"+to_dateString+"','DD/MM/YYYY')\n" + 
					")\n" ;
		}
		String selectSqlDate = "";
		String joinDateCondition1 = "";
		String joinDateCondition2 = "";
		if (settings.getSqlServerConnectionEnabled()) {
			selectSqlDate = "convert(varchar, req.PERIOD_FROM,20) AS attendance_time,\n";
		} else {
			selectSqlDate = "TO_CHAR(req.PERIOD_FROM,'YYYY-MM-DD hh24:MI:ss') AS attendance_time,\n";
		}
		
		if (settings.getSqlServerConnectionEnabled()) {
			joinDateCondition1 = "and convert(varchar, req.FROM_DATE,23)=convert(varchar, empdays.DD,23)\n" ;
		} else {
			joinDateCondition1 = "and TO_CHAR(req.FROM_DATE,'YYYY-MM-DD')=TO_CHAR(empdays.DD,'YYYY-MM-DD')\n" ;
		}
		
		if (settings.getSqlServerConnectionEnabled()) {
			joinDateCondition2 = "AND empdays.DD >= CONVERT(datetime,'"+from_dateString+"',103) AND empdays.DD <= CONVERT(datetime,'"+to_dateString+"',103)\n";
		} else {
			joinDateCondition2 = "AND empdays.DD >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') AND empdays.DD <= TO_DATE('"+to_dateString+"','DD/MM/YYYY')\n";
		}
		
		StringBuilder sql = new StringBuilder("(SELECT empdays.DD , empdays.EMPCODE,emp.FIRSTNAME fName,-- empcode1, req.FROM_DATE requestfromdate, req.PERIOD_FROM requestperiod , req.POSTED posted, req.APPROVED approved\n" + 
				"req.FROM_DATE AS  attendance_date,\n" + 
				selectSqlDate + 
				"(CASE WHEN req.REQUEST_TYPE=10 THEN 'IN' WHEN req.REQUEST_TYPE=11 THEN 'OUT' END ) AS ATTENDANCE_Type,\n" + 
				"(CASE WHEN req.APPROVED =1 THEN 'Approved' WHEN req.APPROVED =99 THEN 'Rejected' WHEN (req.PERIOD_FROM IS NOT NULL AND req.approved IS NULL) THEN  'Incomplete' END) AS approval ,\n" + 
				"(CASE WHEN req.INPUTTYPE=0 THEN 'Web_Attendance' WHEN req.INPUTTYPE=1 THEN 'Request_Attendance' WHEN req.INPUTTYPE=2 THEN 'Android_Attendance' ELSE 'Absent' END) AS input_type,\n" +
				"longitude as longitude, latitude as latitude \n"+
				"FROM ALL_EMP_DAYS empdays join LOGIN_USERS_REQUESTS req\n" + 
				"on req.EMPCODE=empdays.EMPCODE  AND (\n" + 
				"(req.REQUEST_TYPE=10 OR req.REQUEST_TYPE=11)\n" + 
				joinDateCondition1+ 
				//			"AND req.POSTED != 1\n" + 
				")\n" + 
				"JOIN COMMON_EMPLOYEE emp ON emp.EMPCODE=empdays.EMPCODE \n" +
				"where\n" + 
				"empdays.EMPCODE in ("+empCode+")\n" + 
				joinDateCondition2 + 
				" AND req.APPROVED !=99 "+
				")\n" + 
				//			unionAll+	
				"ORDER BY DD,"+empCodeOrder+"attendance_time ASC --DD,\n");



		log.debug("----sql 1---"+sql);

		log.debug("sql statement " + sql.toString());
		List in=(List) getJdbcTemplate().queryForList(sql.toString());


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
				log.debug("in time " + attendanceTime);
				attendanceType = inMap.get("ATTENDANCE_TYPE").toString();
				log.debug("attendanceType " + attendanceType);
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
					log.debug("attendanceType2 " + attendanceType2);
					inputType2 = inMap2.get("INPUT_TYPE").toString();

					log.debug("inMap2.get(latitude) " + inMap2.get("latitude"));
					log.debug("inMap2.get(longitude) " + inMap2.get("longitude"));
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
	//
	//public List getTimeAttendFromViewForAttendanceVacationReport (String empCode, Date from_date, Date to_date){
	//	
	//	List result = new ArrayList();
	//	List totalList = new ArrayList();
	//	
	//	log.debug("----fromdate---"+from_date);
	//    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	//    
	//    String from_dateString=df.format(from_date);
	//    log.debug("----from_dateString- after formatting--"+from_dateString);
	//    
	//    String to_dateString=df.format(to_date);
	//    log.debug("----to_dateString- after formatting--"+to_dateString);
	//    try {
	//    	log.debug("----xxxxxxxxxxxxxxxx-----");
	//		to_date=df.parse(to_dateString);
	//		log.debug("----to_dateString- after formatting--"+to_date);
	//		log.debug("----xxxxxxxxxxxxxxxx-----");
	//	} catch (ParseException e) {
	//		// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
	//
	//    
	//    MultiCalendarDate mCalDate = new MultiCalendarDate();
	//	if(from_dateString!=null){
	//		log.debug("-----date entered---"+from_dateString);
	//		mCalDate.setDateTimeString(from_dateString,new Boolean(true));
	//	}
	//	from_date = mCalDate.getDate();
	//	log.debug("----fromdate- after formatting--"+from_date);
	//	
	//	String emp = "";
	//	if (empCode!= null && !empCode.equals("")){
	//		emp = " t.empcode in (" +empCode+ ") and ";
	//	}
	//	String empCodeOrder = "";
	//	if (empCode.contains(",")) {
	//		empCodeOrder = " empCode, ";
	//	}
	//	setJdbcTemplate(new JdbcTemplate(createDataSource()));
	//	
	//	
	//	String unionAll = "";
	//	if (empCode.contains(",")) {
	//		unionAll = "UNION ALL\n" + 
	//				"(SELECT empdays.DD , empdays.EMPCODE,emp.FIRSTNAME fName,-- empcode2, ta.DATE_ attendanceDate, ta.TIME_ attendanceTime\n" + 
	//				"ta.DATE_  AS  attendance_date,\n" + 
	//				"TO_CHAR(ta.TIME_,'YYYY-MM-DD hh24:MI:ss')  AS attendance_time,\n" + 
	//				"(CASE WHEN ta.TRANS_TYPE='I' THEN 'IN' WHEN ta.TRANS_TYPE='O' THEN 'OUT' END ) AS ATTENDANCE_Type,\n" + 
	//				"(CASE WHEN ta.date_ IS NOT NULL THEN 'Approved' END) AS approval,\n" + 
	//				"(CASE WHEN ta.INPUTTYPE=0 THEN 'Web_Attendance' WHEN ta.INPUTTYPE=1 THEN 'Request_Attendance' WHEN ta.INPUTTYPE=2 THEN 'Android_Attendance' ELSE 'Fingerprint_Attendance' END) AS input_type,\n" +
	//				"longitude as longitude, latitude as latitude \n"+
	//				"FROM ALL_EMP_DAYS empdays JOIN TIME_ATTEND ta\n" + 
	//				"ON  ta.EMP_CODE=empDays.EMPCODE AND TO_CHAR(ta.DATE_,'YYYY-MM-DD')=TO_CHAR(EMPDAYS.DD,'YYYY-MM-DD')\n" + 
	//				"JOIN COMMON_EMPLOYEE emp ON emp.EMPCODE=empdays.EMPCODE \n" +
	//				"where\n" + 
	//				"empdays.EMPCODE in ("+empCode+")\n" + 
	//				"AND empdays.DD >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') AND empdays.DD <= TO_DATE('"+to_dateString+"','DD/MM/YYYY')\n" + 
	//				")\n" ;
	//	}
	//
	//	StringBuilder sql = new StringBuilder("(SELECT empdays.DD , empdays.EMPCODE,emp.FIRSTNAME fName,-- empcode1, req.FROM_DATE requestfromdate, req.PERIOD_FROM requestperiod , req.POSTED posted, req.APPROVED approved\n" + 
	//			"req.FROM_DATE AS  attendance_date,\n" + 
	//			"TO_CHAR(req.PERIOD_FROM,'YYYY-MM-DD hh24:MI:ss') AS attendance_time,\n" + 
	//			"(CASE WHEN req.REQUEST_TYPE=10 THEN 'IN' WHEN req.REQUEST_TYPE=11 THEN 'OUT' END ) AS ATTENDANCE_Type,\n" + 
	//			"(CASE WHEN req.APPROVED =1 THEN 'Approved' WHEN req.APPROVED =99 THEN 'Rejected' WHEN (req.PERIOD_FROM IS NOT NULL AND req.approved IS NULL) THEN  'Incomplete' END) AS approval ,\n" + 
	//			"(CASE WHEN req.INPUTTYPE=0 THEN 'Web_Attendance' WHEN req.INPUTTYPE=1 THEN 'Request_Attendance' WHEN req.INPUTTYPE=2 THEN 'Android_Attendance' ELSE 'Absent' END) AS input_type,\n" +
	//			"longitude as longitude, latitude as latitude \n"+
	//			"FROM ALL_EMP_DAYS empdays join LOGIN_USERS_REQUESTS req\n" + 
	//			"on req.EMPCODE=empdays.EMPCODE  AND (\n" + 
	//			"(req.REQUEST_TYPE=10 OR req.REQUEST_TYPE=11)\n" + 
	//			"and TO_CHAR(req.FROM_DATE,'YYYY-MM-DD')=TO_CHAR(empdays.DD,'YYYY-MM-DD')\n" + 
	//			"AND req.POSTED != 1\n" + 
	//			")\n" + 
	//			"JOIN COMMON_EMPLOYEE emp ON emp.EMPCODE=empdays.EMPCODE \n" +
	//			"where\n" + 
	//			"empdays.EMPCODE in ("+empCode+")\n" + 
	//			"AND empdays.DD >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') AND empdays.DD <= TO_DATE('"+to_dateString+"','DD/MM/YYYY')\n" + 
	//			")\n" + 
	//			unionAll+	
	//			"ORDER BY DD,"+empCodeOrder+"attendance_time DESC --DD,\n");
	//	
	//	
	//
	//	log.debug("host name " + hostName);
	//	log.debug("service name " + serviceName);
	//	log.debug("username " + userName);
	//	log.debug("password " + password);
	//	log.debug("----sql 1---"+sql);
	//
	//	log.debug("sql statement " + sql.toString());
	//	List in=(List) getJdbcTemplate().queryForList(sql.toString());
	//	
	//		
	//	LinkedCaseInsensitiveMap inMap ;
	//	LinkedCaseInsensitiveMap inMap2 ;
	//	
	//	String minDate = null;
	//	
	//	
	//	log.debug("----in---"+in);		
	//	//result.add(minDate);	
	//
	//
	//	if(to_dateString!=null){
	//		log.debug("-----to_dateString entered---"+to_dateString);
	//		mCalDate.setDateTimeString(to_dateString,new Boolean(true));
	//	}
	//	to_date = mCalDate.getDate();
	//	log.debug("----to_dateString- after formatting--"+to_date);
	//	
	////	String maxDate = null;
	//	
	//	TimeAttend timeAttend=null;
	//	Date inDate=null, outDate= null;
	//	long totalMins=0, totalHrs=0;
	//	
	//	int i=0;
	//	
	//	
	//	DateFormat d= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
	//	log.debug("size "+in.size());
	//	
	//	String attendanceTime = null;
	//	String attendanceDate = null;
	//	String attendanceType = null;
	//	String inputType1 = null;
	//	String latitude1 = null;
	//	String longitude1 = null;
	//	
	//	while(i<in.size()){
	//		timeAttend=new TimeAttend();
	//		
	//
	//		
	//		String attendanceTime2 = null;
	//		String attendanceDate2 = null;
	//		String attendanceType2 = null;
	//		String inputType2 = null;
	//		String latitude2 = null;
	//		String longitude2 = null;
	//		
	//		
	////		log.debug("in.get(i) " + in.get(i).getClass());
	//		inMap = (LinkedCaseInsensitiveMap) in.get(i);
	//		Object atimeObj = inMap.get("attendance_time");
	//		if (atimeObj != null) {
	//			attendanceTime = atimeObj.toString();
	//			log.debug("in time " + attendanceTime);
	//			attendanceType = inMap.get("ATTENDANCE_TYPE").toString();
	//			log.debug("attendanceType " + attendanceType);
	//			inputType1 = inMap.get("INPUT_TYPE").toString();
	//			if (inMap.get("latitude")!=null) {
	//				latitude1 = inMap.get("latitude").toString();
	//			} else {
	//				latitude1 = null;
	//			}
	//			if (inMap.get("longitude")!=null) {
	//				longitude1 = inMap.get("longitude").toString();
	//			} else {
	//				longitude1 = null;
	//			}
	//
	//			if (attendanceType.equals("IN")) {
	//				try {
	//					inDate=d.parse(attendanceTime);
	//					log.debug("inDate  = "+inDate);
	//				} catch (ParseException e) {
	//					// TODO Auto-generated catch block
	//					e.printStackTrace();
	//				}
	//			}
	//		}
	//		
	//		log.debug("i " + i + " attendanceType " + attendanceType +  " inDate " + inDate);
	//		if(in.size()>(i+1)) {
	//			inMap2 = (LinkedCaseInsensitiveMap)in.get(i+1);
	//
	//			Object atimeObj2 = inMap2.get("attendance_time");
	//			if (atimeObj2!=null) {
	//				attendanceTime2 = atimeObj2.toString();
	//				log.debug("out time " + attendanceTime2);
	//				attendanceType2 = inMap2.get("ATTENDANCE_TYPE").toString();
	//				log.debug("attendanceType2 " + attendanceType2);
	//				inputType2 = inMap2.get("INPUT_TYPE").toString();
	//				
	//				log.debug("inMap2.get(latitude) " + inMap2.get("latitude"));
	//				log.debug("inMap2.get(longitude) " + inMap2.get("longitude"));
	//				if (inMap2.get("latitude")!=null) {
	//					latitude2 = inMap2.get("latitude").toString();
	//				} else {
	//					latitude2 = null;
	//				}
	//				if (inMap2.get("longitude")!=null) {
	//					longitude2 = inMap2.get("longitude").toString();
	//				} else {
	//					longitude2 = null;
	//				}
	//				if(attendanceType2!= null && attendanceType2.equals("OUT")) {
	//					i++;
	//				}
	//				log.debug("i " + i);
	//			} else {
	//				latitude2 = null;
	//				longitude2 = null;
	//			}
	//			log.debug("longitude " + longitude2 + " latitude " + latitude2);
	//		}
	//		timeAttend.setTimeIn(attendanceTime.substring(10));
	//		
	//		timeAttend.setLatitude1(latitude1);
	//		timeAttend.setLatitude2(latitude2);
	//		timeAttend.setLongitude1(longitude1);
	//		timeAttend.setLongitude2(longitude2);
	//		timeAttend.setInputType1(inputType1);
	//		timeAttend.setInputType2(inputType2);
	//		String[] dateOnly = attendanceTime.split(" ");
	//		String[] letters=dateOnly[0].split("-");
	//
	//		int year= Integer.parseInt(letters[0]);
	//		int month =Integer.parseInt(letters[1]);
	//		int day =Integer.parseInt(letters[2]);
	//		String dateString=year+"/"+month+"/"+day;
	//		log.debug("-----dateString-0--"+dateString);
	//		timeAttend.setDay(dateString);
	//
	//		mCalDate.setDateString(dateString);
	//		Date date=mCalDate.getDate();
	//		log.debug("-----date-0--"+date);
	//
	//		SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE",new Locale("ar","SA")); // the day of the week spelled out completely  
	//		log.debug("-------simpleDateformat.format(mCalDate.getDate())---"+simpleDateformat.format(mCalDate.getDate()));
	//		timeAttend.setDayString(simpleDateformat.format(mCalDate.getDate()));
	//
	//		String empStr =  inMap.get("empcode").toString();
	//		timeAttend.setEmployee(empStr);
	//
	//		String empName =  inMap.get("fName").toString();
	//		timeAttend.setEmpName(empName);
	//		log.debug("------timeAttend.getDay()---"+timeAttend.getDay());
	//		result.add(timeAttend);
	//		
	//
	//		log.debug("attendanceType2 " + attendanceType2);
	////		log.debug("attendanceType2!= null " + attendanceType2!= null + " attendanceType2.equals(OUT) " + attendanceType2.equals("OUT"));
	//		if (attendanceType2!= null && attendanceType2.equals("OUT")) {
	//			log.debug("attendanceType2 " + attendanceType2);
	//			try {
	//				outDate=d.parse(attendanceTime2);
	//				log.debug("outDate  = "+outDate);
	//				Calendar inCal = Calendar.getInstance();
	//				inCal.setTime(inDate);
	//				Calendar outCal = Calendar.getInstance();
	//				outCal.setTime(outDate);
	//				if (inCal.get(Calendar.DAY_OF_MONTH) == outCal.get(Calendar.DAY_OF_MONTH) 
	//						&& inCal.get(Calendar.MONTH) == outCal.get(Calendar.MONTH)
	//						&& inCal.get(Calendar.YEAR) == outCal.get(Calendar.YEAR) ) {
	//					log.debug("same day");
	//					
	//					long diffHrs= (outDate.getTime()-inDate.getTime())/(1000*60*60);
	//					long diffMins= ((outDate.getTime()-inDate.getTime())%(1000*60*60))/(1000*60);
	//					log.debug("diffHrs=== "+diffHrs);
	//					log.debug("diffMins=== "+diffMins);
	//					totalMins+=diffMins;
	//					totalHrs+=diffHrs;
	//					timeAttend.setDiffHrs(diffHrs+"");
	//					timeAttend.setDiffMins(diffMins+"");
	//					
	//					
	//					timeAttend.setTimeOut(attendanceTime2.substring(10));
	//					
	//					
	//										
	//				} else {
	//					timeAttend.setTimeOut(null);
	//					longitude2=null;
	//					latitude2=null;
	//					timeAttend.setLongitude2(longitude2);
	//					timeAttend.setLatitude2(latitude2);
	//				}
	//			} catch (ParseException e) {
	//				// TODO Auto-generated catch block
	//				log.debug("parsing exception of attendanceTime2");
	//				e.printStackTrace();
	//			}
	//		} else {
	//			timeAttend.setTimeOut(null);
	//			longitude2=null;
	//			latitude2=null;
	//			timeAttend.setLongitude2(longitude2);
	//			timeAttend.setLatitude2(latitude2);
	//		}
	//		
	//		log.debug("lat2 " + timeAttend.getLatitude2() + " long2 " + timeAttend.getLongitude2());
	//		i++;
	//		
	//	}
	//		
	//	
	//	totalList.add(result);
	//	totalList.add(totalMins+","+totalHrs);
	//	
	//	return totalList;
	//	
	//}
	//

	public List getTimeAttendAll (String empCode, Date from_date, Date to_date, String statusId,Settings settings){

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
		setJdbcTemplate(new JdbcTemplate(createDataSource()));

		String selectDate = "";
		String joinDateCondition1 = "";
		String joinDateCondition2 = "";
		if (settings.getSqlServerConnectionEnabled()) {
			selectDate = "convert(varchar, PERIOD_FROM,20) AS attendance_time,\n";
		} else {
			selectDate = "TO_CHAR(req.PERIOD_FROM,'YYYY-MM-DD hh24:MI:ss') AS attendance_time,\n";
		}
		
		if (settings.getSqlServerConnectionEnabled()) {
			joinDateCondition1 = " and  convert(varchar, req.FROM_DATE,23)=convert(varchar, empdays.DD,23)\n" ;
		} else {
			joinDateCondition1 = "and TO_CHAR(req.FROM_DATE,'YYYY-MM-DD')=TO_CHAR(empdays.DD,'YYYY-MM-DD')\n";
		}
		
		if (settings.getSqlServerConnectionEnabled()) {
			joinDateCondition2 = "AND empdays.DD >= CONVERT(datetime,'"+from_dateString+"',103) AND empdays.DD <= CONVERT(datetime,'"+to_dateString+"',103)\n";
		} else {
			joinDateCondition2 = "AND empdays.DD >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') AND empdays.DD <= TO_DATE('"+to_dateString+"','DD/MM/YYYY')\n";
		}
		
		/////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////
		String selectUnionDate = "";
		String joinDateUnionCondition1 = "";
		String joinDateUnionCondition2 = "";
		String joinCondition3 = "";
		String joinCondition4 = "";
		String joinCondition5 = "";
		String joinCondition6 = "";
		
		if (settings.getSqlServerConnectionEnabled()) {
			selectUnionDate = "convert(varchar, ta.TIME_,20) AS attendance_time,\n";
		} else {
			selectUnionDate = "TO_CHAR(ta.TIME_,'YYYY-MM-DD hh24:MI:ss')  AS attendance_time,\n";
		}
		
		if (settings.getSqlServerConnectionEnabled()) {
			joinDateUnionCondition1 = "  convert(varchar, ta.DATE_,23)=convert(varchar, empdays.DD,23)\n" ;
		} else {
			joinDateUnionCondition1 =  " TO_CHAR(ta.DATE_,'YYYY-MM-DD')=TO_CHAR(EMPDAYS.DD,'YYYY-MM-DD')\n";
		}
		
		if (settings.getSqlServerConnectionEnabled()) {
			joinDateUnionCondition2 = "AND empdays.DD >= CONVERT(datetime,'"+from_dateString+"',103) AND empdays.DD <= CONVERT(datetime,'"+to_dateString+"',103)\n";
		} else {
			joinDateUnionCondition2 = "AND empdays.DD >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') AND empdays.DD <= TO_DATE('"+to_dateString+"','DD/MM/YYYY')\n";
		}
		
		
		if (settings.getSqlServerConnectionEnabled()) {
			joinCondition3 = " (r.PERIOD_FROM=convert(varchar,q.mindate,20)) \n";;
		} else {
			joinCondition3 = " (r.PERIOD_FROM=TO_DATE(q.mindate,'yyyy-mm-dd HH24:MI:SS')) \n";
		}
		
		if (settings.getSqlServerConnectionEnabled()) {
			joinCondition4 = " tt.DATE_=convert(varchar,q.mindate,20)\n";
		} else {
			joinCondition4 = " tt.DATE_=TO_DATE(q.mindate,'yyyy-mm-dd HH24:MI:SS')\n";
		}
		
		if (settings.getSqlServerConnectionEnabled()) {
			joinCondition5 = " (r2.FROM_DATE=convert(varchar,maxdate,20)\n";
		} else {
			joinCondition5 = " (r2.FROM_DATE=TO_DATE(maxdate,'yyyy-mm-dd HH24:MI:SS')\n";
		}
		
		if (settings.getSqlServerConnectionEnabled()) {
			joinCondition6 = " tt2.DATE_=convert(varchar,q.maxdate,20) ";
		} else {
			joinCondition6 = " tt2.DATE_=TO_DATE(q.maxdate,'yyyy-mm-dd HH24:MI:SS') ";
		}
		
		
		
		////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////
		
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
				selectDate+
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
				joinDateCondition1 + 
				"AND req.POSTED != 1\n" + 
				") \n" + 
				"JOIN COMMON_EMPLOYEE emp ON emp.EMPCODE=empdays.EMPCODE \n" +
				"where\n" + 
				"empdays.EMPCODE in ("+empCode+")\n" + 
				joinDateCondition2 + 
				")  \n" + 
				"UNION ALL\n" + 
				"(SELECT empdays.DD , empdays.EMPCODE,emp.FIRSTNAME fName,-- empcode2, ta.DATE_ attendanceDate, ta.TIME_ attendanceTime\n" + 
				"ta.DATE_  AS  attendance_date,\n" + 
				selectUnionDate +
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
				"ON  ta.EMP_CODE=empDays.EMPCODE AND "
				+ joinDateUnionCondition1 + 
				"JOIN COMMON_EMPLOYEE emp ON emp.EMPCODE=empdays.EMPCODE \n" +

			"where\n" + 
			"empdays.EMPCODE in ("+empCode+")\n" + 
			joinDateUnionCondition2 +
			")\n "+
//////			"ORDER BY DD" +
			") empDays " + status +"\ngroup by DD,empcode,fName\n"+
			//					+ ",longitudeIn, LONGITUDEOut, LATITUDEIn, LATITUDEOut,addressIn,addressOut \n"
//////		+ " order by DD desc\n" +
			") q\n"+
			"LEFT JOIN LOGIN_USERS_REQUESTS r ON r.EMPCODE=q.EMP  AND r.REQUEST_TYPE=10 AND" + joinCondition3+
			"LEFT JOIN TIME_ATTEND tt ON tt.EMP_CODE=q.EMP AND tt.TRANS_TYPE='I' AND "+ joinCondition4 +
			"LEFT JOIN LOGIN_USERS_REQUESTS r2 ON r.EMPCODE=q.EMP AND r2.REQUEST_TYPE=11  AND "+joinCondition5+")\n"+
			"LEFT JOIN TIME_ATTEND tt2 ON tt2.EMP_CODE=q.EMP AND tt2.TRANS_TYPE='O' AND " + joinCondition6
			+ " ORDER BY mindate desc");



		log.debug("----sql 1---"+sql);


		List in=(List) getJdbcTemplate().queryForList(sql.toString());


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


	public List getVacations (String empCode, Long reqId, String vacId, Date from_date,Settings settings){

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
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		String dateCondition = "";
		if (settings.getSqlServerConnectionEnabled()) {
			dateCondition = "CONVERT(datetime,'"+ dd1String+"', 103) and  CONVERT(datetime,'" +from_dateString +"', 103)";
		} else {
			dateCondition = "to_date ('"+ dd1String+"', 'DD-MM-YYYY') and  to_date('" +from_dateString +"', 'DD-MM-YYYY')";
		}
		StringBuilder sql = new StringBuilder(
				" select fr_date, to_date, withdr, vacation from empvac where empcode = '" +empCode
				+ "' and vacation = '" + vacId + "' and fr_date between "
						+ dateCondition);

		log.debug("----sql1---"+sql);

		result=(List) getJdbcTemplate().queryForList(sql.toString());
		return result;
	}


	public List getVacations (String empCode, Long reqId, Date from_date, Date to_date, Settings settings){

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
		
		String dateCondition = "";
		
		if (settings.getSqlServerConnectionEnabled()) {
			dateCondition =  " empvac.fr_date >= convert(datetime,'"+ from_dateString+"', 103) and empvac.fr_date <= convert(datetime,'" +	to_dateString+"', 103)";
		} else {
			dateCondition =  " empvac.fr_date >= to_date ('"+ from_dateString+"', 'DD-MM-YYYY') and empvac.fr_date <= to_date('" +	to_dateString+"', 'DD-MM-YYYY')";
		}
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		StringBuilder sql = new StringBuilder(
				" select empvac.fr_date as fr_date, empvac.to_date as to_date, empvac.withdr as withdr, empvac.vacation as vacation, "
						+ "empvac.empcode as empCode, e.firstName as fName "
						+ "from empvac empvac, common_employee e "
						+ "where "
						+ empString
						//						+ "' and vacation = '" + vacId 
						+ " empvac.empcode=e.empcode and "
						+ dateCondition);

		log.debug("----sql1---"+sql);

		result=(List) getJdbcTemplate().queryForList(sql.toString());
		return result;
	}

	public int getSalaryFromDay(){
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		StringBuilder sql = new StringBuilder(
				" select salary_from_day from system ");

		log.debug("----sql1---"+sql);
		int day = 0;
		List result=(List) getJdbcTemplate().queryForList(sql.toString());
		if (result.size()>0) {
			ListOrderedMap m = (ListOrderedMap )result.get(0);
			day = ((BigDecimal)m.getValue(0)).intValue();
			log.debug("day " + day);
		}
		return day;
	}


	public Map getPagedRequests(Date fromDate, Date toDate, Long requestType,
			Date exactFrom, Date exactTo, Date periodFrom, Date periodTo,
			String empCode, String codeFrom, String codeTo, Long statusId,
			String sort, List empReqTypeAccs, String requestNumber, Long mgrId,
			boolean isWeb, String isInsideCompany,Settings settings, int pageNumber, int pageSize) {

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

		setJdbcTemplate(new JdbcTemplate(createDataSource()));
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
			log.debug("where " + where);
			if(where == null || where.isEmpty()) {
				where = " where ";
			} else {
				where += " and ";
			}
			log.debug("where " + where);
			from_dateString=df.format(fromDate);
			to_dateString=df.format(toDate);
			if (settings.getSqlServerConnectionEnabled()) {
				where += " ((CONVERT(date, loginUsersReq.request_date) >=convert(date,'"+from_dateString+"',103) and  "
						+ "CONVERT(date, loginUsersReq.request_date) <= convert(date,'"+to_dateString+"',103))) ";
			} else {
				where += " ((trunc(loginUsersReq.request_date) >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') and "
						+ "trunc(loginUsersReq.request_date)  <= TO_DATE('"+to_dateString+"','DD/MM/YYYY'))) ";
			}
		}

		if (periodFrom!= null && periodTo!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			periodf_dateString=df.format(periodFrom);
			periodt_dateString=df.format(periodTo);
			if (settings.getSqlServerConnectionEnabled()) {
				where = "  ((CONVERT(date, loginUsersReq.period_from) >= convert(date,'"+periodf_dateString+"',103) and  "
						+ "CONVERT(date, loginUsersReq.period_from) <= convert(date,'"+periodt_dateString+"',103))) ";
			} else {
				where += " ((trunc(loginUsersReq.period_from)  >= TO_DATE('"+periodf_dateString+"','DD/MM/YYYY') and "
						+ "trunc(loginUsersReq.period_from) <= TO_DATE('"+periodt_dateString+"','DD/MM/YYYY'))) ";
			}
		}

		if (exactFrom!= null && exactTo!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			exactf_dateString=df.format(exactFrom);
			exactt_dateString=df.format(exactTo);
			if (settings.getSqlServerConnectionEnabled()) {
				where = "  ((CONVERT(date, loginUsersReq.from_date) >= convert(date,'"+exactf_dateString+"',103) and  "
						+ "CONVERT(date, loginUsersReq.from_date) <= convert(date,'"+exactt_dateString+"',103))) ";
			} else {
			where += " ((trunc(loginUsersReq.from_date) >= TO_DATE('"+exactf_dateString+"','DD/MM/YYYY') and "
					+ "trunc(loginUsersReq.from_date) <= TO_DATE('"+exactt_dateString+"','DD/MM/YYYY'))) ";
			}
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
			} else if (statusId.equals(new Long(99))) {
				statusStr = " (approvals.approval = 0) ";
			}
			if(outerSelectWhere == null || outerSelectWhere.isEmpty()) {
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

		String rownum = "";
		if (settings.getSqlServerConnectionEnabled()) {
			if (sort!= null) {
				rownum = " ROW_NUMBER() over (order by period_from "+sort+") as rnum ";
			} else {
				rownum = " ROW_NUMBER() over (order by period_from) as rnum ";
			}
		} else {
			rownum = "rownum rnum";
		}

		query = "select * from (select "+rownum+", q.* from (" + outerSelectStart + " ("
				+select + where+
				//orderBy+
				outerSelectEnd + outerSelectWhere 
				+orderBy
				+") q"+ ") queryView where rnum>"+(pageSize*pageNumber) + " and rnum<="+((pageNumber*pageSize)+pageSize-1)+ orderBy;
		log.debug("query " + query);
		StringBuilder sql = new StringBuilder(query);

		log.debug("----sql 1---"+sql);


		List in=(List) getJdbcTemplate().queryForList(sql.toString());

		String listSizeQuery = "select count (*) count from ("+outerSelectStart+ " ("+select+where
				//+orderBy
				+outerSelectEnd + outerSelectWhere
				+orderBy
				+") queryView ";
//+orderBy;
		log.debug("listSizeQuery " + listSizeQuery);
		StringBuilder sqlListSize = new StringBuilder(listSizeQuery);
		List in2=(List) getJdbcTemplate().queryForList(sqlListSize.toString());

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
			log.debug("listSize " + ((LinkedCaseInsensitiveMap)in2.get(0)).get("count"));
			if (settings.getSqlServerConnectionEnabled()) {
				map.put("listSize", ((Integer)((LinkedCaseInsensitiveMap)in2.get(0)).get("count")).intValue());
			} else {
				map.put("listSize", ((BigDecimal)((LinkedCaseInsensitiveMap)in2.get(0)).get("count")).intValue());
			}
		} else {
			map.put("listSize", new Long(0));
		}
		return map;
	}

	public Map getSubmittedPagedRequests(Date fromDate, Date toDate, Long requestType,
			Date exactFrom, Date exactTo, Date periodFrom, Date periodTo,
			String empCode, String codeFrom, String codeTo, Long statusId,
			String sort, List empReqTypeAccs, String requestNumber, Long mgrId,
			boolean isWeb, String isInsideCompany,Settings settings, int pageNumber, int pageSize) {

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

		setJdbcTemplate(new JdbcTemplate(createDataSource()));
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
			
			
			if (settings.getSqlServerConnectionEnabled()) {		
				where += " ((CONVERT(date,loginUsersReq.request_date) >= CONVERT(date,'"+from_dateString+"',103) and "
						+ "CONVERT(date,loginUsersReq.request_date)  <= CONVERT(date,'"+to_dateString+"',103))) ";
			} else {
				where += " ((trunc(loginUsersReq.request_date) >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') and "
						+ "trunc(loginUsersReq.request_date)  <= TO_DATE('"+to_dateString+"','DD/MM/YYYY'))) ";
			}
		}

		if (periodFrom!= null && periodTo!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			periodf_dateString=df.format(periodFrom);
			periodt_dateString=df.format(periodTo);
			if (settings.getSqlServerConnectionEnabled()) {
				where += " ((CONVERT(date,loginUsersReq.period_from)  >= CONVERT(date,'"+periodf_dateString+"',103) and "
						+ "CONVERT(date,loginUsersReq.period_from) <= CONVERT(date,'"+periodt_dateString+"',103))) ";
			} else {
				where += " ((trunc(loginUsersReq.period_from)  >= TO_DATE('"+periodf_dateString+"','DD/MM/YYYY') and "
						+ "trunc(loginUsersReq.period_from) <= TO_DATE('"+periodt_dateString+"','DD/MM/YYYY'))) ";
			}
		}

		if (exactFrom!= null && exactTo!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			exactf_dateString=df.format(exactFrom);
			exactt_dateString=df.format(exactTo);
			if (settings.getSqlServerConnectionEnabled()) {
				where += " ((CONVERT(date,loginUsersReq.from_date) >= CONVERT(date,'"+exactf_dateString+"',103) and "
						+ "CONVERT(date,loginUsersReq.from_date) <= CONVERT(date,'"+exactt_dateString+"',103))) ";
			} else {
				where += " ((trunc(loginUsersReq.from_date) >= TO_DATE('"+exactf_dateString+"','DD/MM/YYYY') and "
						+ "trunc(loginUsersReq.from_date) <= TO_DATE('"+exactt_dateString+"','DD/MM/YYYY'))) ";
			}
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

		String rownum = "";
		if (settings.getSqlServerConnectionEnabled()) {
			if (sort!= null) {
				rownum = " ROW_NUMBER() over (order by period_from "+sort+") as rnum ";
			} else {
				rownum = " ROW_NUMBER() over (order by period_from) as rnum ";
			}
		} else {
			rownum = "rownum rnum";
		}


		query = "select * from (select "+rownum+", q.* from (" + outerSelectStart + " ("
				+select + where+
				outerSelectEnd + outerSelectWhere+") q ) m where rnum<="+((pageNumber*pageSize)+pageSize-1)+ " and rnum>"+(pageSize*pageNumber)  + orderBy;
		log.debug("query " + query);
		StringBuilder sql = new StringBuilder(query);

		log.debug("----sql 1---"+sql);


		List in=(List) getJdbcTemplate().queryForList(sql.toString());

		String listSizeQuery = "select count (*) count from ("+outerSelectStart+ " ("+select+where
				+outerSelectEnd + outerSelectWhere+") q";
		log.debug("listSizeQuery " + listSizeQuery);
		StringBuilder sqlListSize = new StringBuilder(listSizeQuery);
		List in2=(List) getJdbcTemplate().queryForList(sqlListSize.toString());

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
			int size = 0;
			if (settings.getSqlServerConnectionEnabled()) {
				size = ((Integer)((LinkedCaseInsensitiveMap)in2.get(0)).get("count")).intValue();
			} else {
				size = ((BigDecimal)((LinkedCaseInsensitiveMap)in2.get(0)).get("count")).intValue();
			}
			log.debug("listSize " + size);
			map.put("listSize", size);
		} else {
			map.put("listSize", new Long(0));
		}
		return map;
	}



	public Map getRequestsStatus(Date fromDate, Date toDate, Long requestType,
			Date exactFrom, Date exactTo, Date periodFrom, Date periodTo,
			String empCode, String codeFrom, String codeTo, Long statusId,
			String sort, List empReqTypeAccs, String requestNumber, Long mgrId,
			boolean isWeb, String isInsideCompany,Settings settings, int pageNumber, int pageSize) {

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

		setJdbcTemplate(new JdbcTemplate(createDataSource()));
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
		
		if (mgrId==null) {
			outerSelectWhere = " where emp.empCode="+empCode;
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
			if (settings.getSqlServerConnectionEnabled()) {
			where += " ((convert(date,loginUsersReq.request_date) >= convert(date,'"+from_dateString+"',103) and "
					+ "convert(date,loginUsersReq.request_date)  <= convert(date,'"+to_dateString+"',103))) ";
			} else {
				where += " ((trunc(loginUsersReq.request_date) >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') and "
						+ "trunc(loginUsersReq.request_date)  <= TO_DATE('"+to_dateString+"','DD/MM/YYYY'))) ";
			}
		}

		if (periodFrom!= null && periodTo!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			periodf_dateString=df.format(periodFrom);
			periodt_dateString=df.format(periodTo);
			if (settings.getSqlServerConnectionEnabled()) {
				where += " ((convert(date,loginUsersReq.period_from)  >= convert(date,'"+periodf_dateString+"',103) and "
						+ "convert(date,loginUsersReq.period_from) <= convert(date,'"+periodt_dateString+"',103))) ";
			} else {
				where += " ((trunc(loginUsersReq.period_from)  >= TO_DATE('"+periodf_dateString+"','DD/MM/YYYY') and "
						+ "trunc(loginUsersReq.period_from) <= TO_DATE('"+periodt_dateString+"','DD/MM/YYYY'))) ";
			}
		}

		if (exactFrom!= null && exactTo!=null) {
			if(where == null) {
				where = " where ";
			} else {
				where += " and ";
			}
			exactf_dateString=df.format(exactFrom);
			exactt_dateString=df.format(exactTo);
			if (settings.getSqlServerConnectionEnabled()) {
				where += " ((convert(date,loginUsersReq.from_date) >= convert(date,'"+exactf_dateString+"',103) and "
						+ "convert(date,loginUsersReq.from_date) <= convert(date,'"+exactt_dateString+"',103))) ";
			} else {
				where += " ((trunc(loginUsersReq.from_date) >= TO_DATE('"+exactf_dateString+"','DD/MM/YYYY') and "
						+ "trunc(loginUsersReq.from_date) <= TO_DATE('"+exactt_dateString+"','DD/MM/YYYY'))) ";
			}
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


		String rownum = "";
		if (settings.getSqlServerConnectionEnabled()) {
			if (sort!= null) {
				rownum = " ROW_NUMBER() over (order by period_from "+sort+") as rnum ";
			} else {
				rownum = " ROW_NUMBER() over (order by period_from) as rnum ";
			}
		}  else {
			rownum = "rownum rnum";
		}

		query = "select * from (select "+rownum+", q.* from (" + outerSelectStart + " ("
				+select + where+
//				orderBy+
				outerSelectEnd + outerSelectWhere +
				//orderBy+
				") q "+") temp where rnum>"+(pageSize*pageNumber) + " and rnum<="+((pageNumber*pageSize)+pageSize-1)+ orderBy;
		log.debug("query " + query);
		StringBuilder sql = new StringBuilder(query);

		log.debug("----sql 1---"+sql);


		List in=(List) getJdbcTemplate().queryForList(sql.toString());

		String listSizeQuery = "select count (*) count from ("+outerSelectStart+ " ("+select+where
//				+orderBy
				+outerSelectEnd + outerSelectWhere
//				+orderBy
				+") temp ";
//+orderBy;
		log.debug("listSizeQuery " + listSizeQuery);
		StringBuilder sqlListSize = new StringBuilder(listSizeQuery);
		List in2=(List) getJdbcTemplate().queryForList(sqlListSize.toString());

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
			log.debug("listSize " + ((LinkedCaseInsensitiveMap)in2.get(0)).get("count"));
			if (settings.getSqlServerConnectionEnabled()) {
				Integer count = (Integer)((LinkedCaseInsensitiveMap)in2.get(0)).get("count");
				map.put("listSize", count.intValue());
			} else {
				BigDecimal count = (BigDecimal)((LinkedCaseInsensitiveMap)in2.get(0)).get("count");
				map.put("listSize", count.intValue());
			}
			
		} else {
			map.put("listSize", new Long(0));
		}
		return map;
	}	
}
