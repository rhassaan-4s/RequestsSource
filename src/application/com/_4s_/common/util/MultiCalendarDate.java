package com._4s_.common.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.validation.BindException;

import com._4s_.common.service.BaseManagerImpl;
import com.ibm.icu.util.IslamicCalendar;
public class MultiCalendarDate implements ApplicationContextAware{
	private final Log log = LogFactory.getLog(getClass());
	private long millis;
	private int dateCalendarType ;
	private ApplicationContext applicationContext;
	BaseManagerImpl baseManager;
	
	// date patterns are of no use, the date format is set using dateFormat
	private String datePattern = "MM/dd/yyyy";
	private String datePattern2 = "dd/MM/yyyy";
	private String delimeter = "/";
	private String delimeter2 = "\\\\";
	private String timeDelimeter = ":";
	
	public static final int HIJRI = 1;
	public static final int MILADI = 2;
	
//	private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM,new Locale("ar","",""));
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("ar","",""));
	private SimpleDateFormat  dateFormat3 = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat dateFormat4 = new SimpleDateFormat("HH:mm:ss");
	public void setCalendarType (int dateCalendarType){
		this.dateCalendarType = dateCalendarType;
	}
	
	public String getDateString() {
		String dateString = null;
//		log.debug(" -- dateCalendarType : "+this.dateCalendarType);
		switch (dateCalendarType) {
		case HIJRI: 
//			log.debug("Going for Hijri");
			dateString = getHijriString();
//			log.debug("Hijri :"+dateString);
			break;
		case MILADI: 
//			log.debug("Going for Miladi");
			dateString = getMeladiString();
//			log.debug("Miladi :"+dateString);
			break;
		}
		return dateString;
	}
	
	public String getConvertedDateString () {
		String dateString = null;
		switch (dateCalendarType) {
		case HIJRI: dateString = getMeladiString();
			break;
		case MILADI: dateString = getHijriString();
			break;
		}
		return dateString;
	}

	public void setDateString(String dateString) throws IllegalArgumentException {
		int year,month,day;
//		log.debug(">>>>>>> getDateArray" + dateString);
		Map dateMap = null ;
		try{
			dateMap = getDateArray(dateString);
		}catch(IllegalArgumentException e){
			throw new IllegalArgumentException(e.getMessage());
		}
//		log.debug(">>>>>>> dateMap not empty");
		year = ((Integer)dateMap.get("year")).intValue();
		month = ((Integer)dateMap.get("month")).intValue();
		day = ((Integer)dateMap.get("day")).intValue();

//		log.debug(">>>>>>>>>>>>>>>>>year "+year);
//		log.debug(">>>>>>>>>>>>>>>>>month "+month);
//		log.debug(">>>>>>>>>>>>>>>>>day "+day);
		if ( year < 1700 ) {
			setHijriDate(year,month,day);
		} else {
			setMiladiDate(year,month,day,dateString);
		}
		
	}
	
	public void setDateTimeString(String dateString,Boolean startOfDay) throws IllegalArgumentException {
		int year,month,day,hour,min;
//		log.debug(">>>>>>> getDateArray" + dateString);
		Map dateMap = null ;
		try{
			dateMap = getDateTimeArray(dateString,startOfDay);
		}catch(IllegalArgumentException e){
			throw new IllegalArgumentException(e.getMessage());
		}
//		log.debug(">>>>>>> dateMap not empty");
		year = ((Integer)dateMap.get("year")).intValue();
		month = ((Integer)dateMap.get("month")).intValue();
		day = ((Integer)dateMap.get("day")).intValue();
		hour= ((Integer)dateMap.get("hour")).intValue();
		min = ((Integer)dateMap.get("min")).intValue();
		
//		log.debug(">>>>>>>>>>>>>>>>>year "+year);
//		log.debug(">>>>>>>>>>>>>>>>>month "+month);
//		log.debug(">>>>>>>>>>>>>>>>>day "+day);
//		log.debug(">>>>>>>>>>>>>>>>>hour "+hour);
//		log.debug(">>>>>>>>>>>>>>>>>min "+min);
		if ( year < 1700 ) {
			setHijriDateTime(year,month,day,hour,min,startOfDay);
		} else {
			setMiladiDateTime(year,month,day,hour,min);
		}
		
	}
	
	public Date getDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.millis);
		//cal.set(Calendar.SECOND,dateCalendarType);
		return cal.getTime();
	}
	
	public void setDate(Date date) {
		Calendar cal = Calendar.getInstance();
//		TimeZone zone = TimeZone.getTimeZone("EST");
		cal.setTime(date);
//		cal.setTimeZone(zone);
		this.millis = cal.getTimeInMillis();
		
		int seconds = cal.get(Calendar.SECOND);
		if (seconds == HIJRI) {
			this.dateCalendarType = HIJRI ;
		} else {
			this.dateCalendarType = MILADI ;
		}
		
	}

	public String getMeladiString() {
		String dateString ;
		
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.YEAR)%4==0) {
//			log.debug("lenient");
			calendar.setLenient(true);
//			dateFormat.setLenient(true);
		} else {
			calendar.setLenient(false);
//			dateFormat.setLenient(false)
//			log.debug("non lenient");
		}
		calendar.setTimeInMillis(this.millis);
//		log.debug("date " + calendar.getTime());
		dateString = dateFormat.format(calendar.getTime());
		log.debug("date string " + dateString);
		return dateString;
	}
	
	public void setMeladiString(String dateString) throws IllegalArgumentException {
		int year,month,day;
		Map dateMap = getDateArray(dateString);
		
		year = ((Integer)dateMap.get("year")).intValue();
		month = ((Integer)dateMap.get("month")).intValue();
		day = ((Integer)dateMap.get("day")).intValue();

		setMiladiDate(year,month,day,dateString);
	}
	
	public void setMiladiDate(int year, int month, int day,String dateString) throws IllegalArgumentException {
		Calendar miladiCalendar = Calendar.getInstance();
		miladiCalendar.setLenient(false);
		try {
			Date date1 = new SimpleDateFormat("yy/MM/dd HH:mm:ss:SSS").parse(dateString+" 00:00:00:000");
			miladiCalendar.set(year,month-1,day,0,0,0);
			Date newDate=new Date();
			newDate.setYear(year);
			newDate.setMonth(month-1);
			newDate.setDate(day);

			this.millis = miladiCalendar.getTimeInMillis();
			//this.millis = date1.getTime();
			this.dateCalendarType = MILADI ;
		} catch (Exception ex) {
			
			log.debug(">>>>>>>>>>>>>>>>>>>> ERROR "+ex.getMessage());
			throw new IllegalArgumentException(ex.getMessage());
		} 
	}

	public void setMiladiDateTime(int year, int month, int day, int hour,int min) throws IllegalArgumentException {
		Calendar miladiCalendar = Calendar.getInstance();
		miladiCalendar.setLenient(false);
		try {
//			log.debug(">>>>>>>>>>>>>>>hour "+hour);
			miladiCalendar.set(year,month-1,day,hour,min);
			this.millis = miladiCalendar.getTimeInMillis();
			this.dateCalendarType = MILADI ;
		} catch (Exception ex) {
//			log.debug(">>>>>>>>>>>>>>>>>>>> ERROR "+ex.getMessage());
			throw new IllegalArgumentException(ex.getMessage());
		} 
	}
	
	public String getHijriString() {		
		log.debug(">>>>>>>>>>>>>getHijriString ");
		String dateString =  null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String miladiDateString = formatter.format(getDate());

		//		Class.forName("com.mysql.jdbc.Driver");
		//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Requests?useUnicode=true&amp;characterEncoding=utf8","Requests","Requests");
		//		Statement stat = conn.createStatement();
		Statement stat = null;
		ResultSet rs = null;
		try {
			try {	
				stat = DBUtils.createStatement();
				rs = stat.executeQuery("select hijri from common_dates where to_char(miladi,'yyyy-mm-dd') = '"+miladiDateString+"'");
			} catch (SQLException e) {
				try {
					stat = DBUtils.createStatement();
					rs = stat.executeQuery("select hijri from common_dates where CONVERT(VARCHAR,miladi,23) = '"+miladiDateString+"'");
				} catch (SQLException ex) {
					ex.printStackTrace();
					throw new IllegalArgumentException(ex.getMessage());
				}
			}
			try {
				if (rs.next()){ // date conversion found in Database
					String dbDateString = rs.getString("hijri");
					String[] elements = dbDateString.split("/");
					String dbDayString = elements[2];
					String dbMonthString = elements[1];
					String dbYearString = elements[0];
					Long dbDay = new Long(dbDayString);
					Long dbMonth = new Long(dbMonthString);
					Long dbYear = new Long(dbYearString);
					try{
						IslamicCalendar islamicCalendar = new IslamicCalendar();

						islamicCalendar.setLenient(false); 
						islamicCalendar.setCivil(true);
						islamicCalendar.clear();
						islamicCalendar.set(IslamicCalendar.YEAR , dbYear.intValue());
						islamicCalendar.set(IslamicCalendar.MONTH , dbMonth.intValue()-1);
						islamicCalendar.set(IslamicCalendar.DAY_OF_MONTH , dbDay.intValue());
						//dateFormat.setLenient(true);


						dateString = dateFormat.format(islamicCalendar);

						Long formattedDay = new Long(dateString.split("/")[0]);
						log.debug(formattedDay);
						if(!formattedDay.equals(dbDay)){ // to handle the case of islamic february!! ex: 8/3/2008 > 30/2/1429
							dateString = dbDayString+"/" + dbMonthString + "/" + dbYearString;
						}
					}catch (Exception e) {
						dateString = dbDayString+"/" + dbMonthString + "/" + dbYearString;
					}
					//				Date hijri = rs.getDate("hijri");
					//				dateString = dateFormat.format(hijri);
					log.debug(">>>>>>>>>>> by DB ["+miladiDateString+"]miladi, in hijri is :"+dateString);
				} else { // date conversion not in DB , so calculate it by IslamicCalendar
					IslamicCalendar islamicCalendar = new IslamicCalendar();
					islamicCalendar.setLenient(false);
					islamicCalendar.setCivil(true);
					islamicCalendar.setTimeInMillis(this.millis);
//					log.debug("islamic calendar " + islamicCalendar);
					dateString = dateFormat.format(islamicCalendar.getTime());
					log.error(">>>>>>>>>>>>dateString by IslamicCalendar :"+dateString);
					log.debug(">>>>>>>>>>> by IsCal ["+miladiDateString+"]miladi, in hijri is :"+dateString);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalArgumentException(e.getMessage());
			}finally{
				rs.close();
				stat.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		log.debug(">>>>>>>>>>>>>dateString "+dateString);
		return dateString; 
} 

	// old one 
	/*public String getHijriString() {
		String dateString ;
		
		IslamicCalendar islamicCalendar = new IslamicCalendar();
		log.error(">>>>>>>>>>>>>>islamicCalendar "+islamicCalendar);
		islamicCalendar.setLenient(false);
		
		islamicCalendar.setCivil(true);
		
		islamicCalendar.setTimeInMillis(this.millis);
		dateString = dateFormat.format(islamicCalendar);
		log.error(">>>>>>>>>>>>>>>dateString "+dateString);
		return dateString; 
	} */
	
	
	public void setHijriDate(String dateString) throws IllegalArgumentException {
	int year,month,day;
		
		Map dateMap= getDateArray(dateString);
		
		year = ((Integer)dateMap.get("year")).intValue();
		month = ((Integer)dateMap.get("month")).intValue();
		day = ((Integer)dateMap .get("day")).intValue();
		setHijriDate(year,month,day);
	}
	
	// old one 
	/*public void setHijriDate(int year, int month, int day) throws IllegalArgumentException {
		IslamicCalendar islamicCalendar = new IslamicCalendar();
		islamicCalendar.setLenient(false);
		islamicCalendar.setCivil(false);
		try {
			islamicCalendar.set(year,month-1,day);
			this.millis = islamicCalendar.getTimeInMillis();
			this.dateCalendarType = HIJRI ;
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage());
		} 
	}*/
	public void setHijriDate(int year, int month, int day) throws IllegalArgumentException {
		Map dateMap = null;
		log.debug(">>>>>>>year "+year);
		log.debug(">>>>>>>month "+month);
		log.debug(">>>>>>>day "+day);
		Calendar islamicCalendar = Calendar.getInstance();
		islamicCalendar.setLenient(false);
		
		Calendar cal = Calendar.getInstance();
		cal.set(year,month-1,day);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = formatter.format(cal.getTime());
		log.debug(">>>>>>>>>>cal.getTime() "+cal.getTime());
		log.debug(">>>>>>>>>>formatter.format(cal.getTime()) "+formattedDate);
		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Requests?useUnicode=true&amp;characterEncoding=utf8","Requests","Requests");
//			Statement stat = conn.createStatement();
			Statement stat = DBUtils.createStatement();
			ResultSet rs = stat.executeQuery("select miladi from common_dates where hijri = '"+formattedDate+"'");
			
			String miladi = null;
			try {
				if(rs.next()){
					Date miladiDate = rs.getDate("miladi");
					miladi = dateFormat.format(miladiDate);
					log.debug(">>>>>>>>>>>>> miladi "+miladi);
					if (miladi != null){
						dateMap= getDateArray(miladi);
					}
					if (dateMap == null ){
						throw new IllegalArgumentException();
					}
					year = ((Integer)dateMap.get("year")).intValue();
					month = ((Integer)dateMap.get("month")).intValue();
					day = ((Integer)dateMap .get("day")).intValue();
					log.debug(">>>>>>>year "+year);
					log.debug(">>>>>>>month "+month);
					log.debug(">>>>>>>day "+day);
					try {
						islamicCalendar.set(year,month-1,day);
						this.millis = islamicCalendar.getTimeInMillis();
						this.dateCalendarType = HIJRI ;
					} catch (Exception ex) {
						throw new IllegalArgumentException(ex.getMessage());
					} 
				} else{
					
					IslamicCalendar islamicCalendar2 = new IslamicCalendar();
					islamicCalendar2.setLenient(false);
					islamicCalendar2.setCivil(false);
					try {
						islamicCalendar2.set(year,month-1,day);
						this.millis = islamicCalendar2.getTimeInMillis();
						this.dateCalendarType = HIJRI ;
					} catch (Exception ex) {
						throw new IllegalArgumentException(ex.getMessage());
					} 
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IllegalArgumentException(e.getMessage());
			}finally{
				rs.close();
				stat.close();
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug(">>>>>>>>>>>>> exception "+e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}

		
	}
	//old one
	/*public void setHijriDateTime(int year, int month, int day, int hour, int min) throws IllegalArgumentException {
		IslamicCalendar islamicCalendar = new IslamicCalendar();
		islamicCalendar.setLenient(false);
		islamicCalendar.setCivil(false);
		try {
			islamicCalendar.set(year,month-1,day,hour,min);
			
			this.millis = islamicCalendar.getTimeInMillis();
			this.dateCalendarType = HIJRI ;
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage());
		} 
	}*/
	
	public void setHijriDateTime(int year, int month, int day, int hour, int min,Boolean startOfDay) throws IllegalArgumentException {
		Map dateMap = null;
		Calendar islamicCalendar = Calendar.getInstance();
		islamicCalendar.setLenient(false);
		
		Calendar cal = Calendar.getInstance();
		cal.set(year,month-1,day,hour,min);
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = formatter.format(cal.getTime());
		log.debug(">>>>>>>>>>cal.getTime() "+cal.getTime());
		log.debug(">>>>>>>>>>formatter.format(cal.getTime()) "+formattedDate);
		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Requests?useUnicode=true&amp;characterEncoding=utf8","Requests","Requests");
//			Statement stat = conn.createStatement();
			Statement stat = DBUtils.createStatement();
			ResultSet rs = stat.executeQuery("select miladi from common_dates where hijri = '"+formattedDate+"'");
		
			String miladi = null;
			try {
				if(rs.next()){
					Date miladiDate = rs.getDate("miladi");
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					miladi = format.format(miladiDate);
					log.error(">>>>>>>>>>>>> miladi "+miladi);
					if (miladi != null){
						dateMap= getDateTimeArray(miladi,startOfDay);
						log.error(">>>>>>>>>dateMap "+dateMap);
					}
					if (dateMap == null ){
						throw new IllegalArgumentException();
					}
					year = ((Integer)dateMap.get("year")).intValue();
					month = ((Integer)dateMap.get("month")).intValue();
					day = ((Integer)dateMap .get("day")).intValue();
					hour = ((Integer)dateMap .get("hour")).intValue();
					min = ((Integer)dateMap .get("min")).intValue();
					log.debug(">>>>>>>year "+year);
					log.debug(">>>>>>>month "+month);
					log.debug(">>>>>>>day "+day);
					
					try {
						islamicCalendar.set(year,month-1,day,hour,min);
						this.millis = islamicCalendar.getTimeInMillis();
						this.dateCalendarType = HIJRI ;
					} catch (Exception ex) {
						throw new IllegalArgumentException(ex.getMessage());
					} 
				
				} else{
					
					IslamicCalendar islamicCalendar2 = new IslamicCalendar();
					islamicCalendar2.setLenient(false);
					islamicCalendar2.setCivil(false);
					try {
						islamicCalendar2.set(year,month-1,day,hour,min);
						
						this.millis = islamicCalendar2.getTimeInMillis();
						this.dateCalendarType = HIJRI ;
					} catch (Exception ex) {
						throw new IllegalArgumentException(ex.getMessage());
					} 
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IllegalArgumentException(e.getMessage());
			}finally{
				rs.close();
				stat.close();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
		
		
	}

	public void setDatePattern(String format) {
		this.datePattern = format ;
	}
	
	public void setDelimiter(String delimeter) {
		this.delimeter = delimeter ;
	}
	
	private Map getDateArray(String date) throws IllegalArgumentException {
		
//		log.debug(">>>>>>> getDateArray in function" + date);		
		DateFormatUtils dd = new DateFormatUtils();
		HashMap dateMap = new HashMap();
		String dateSection ;
		boolean firstIsDay;

//		log.debug(">>>>>>>>> getDateArray() "+date);
		
			String arr[] = date.split(this.delimeter );
			if (arr.length != 3){
//				log.debug(">>>>>>>>>>>>>>>>arr.length  "+ arr.length );
				throw new IllegalArgumentException("Could not parse date");
			}
				dateSection = arr[0];
				if (dateSection.length()==4) {
					dateMap.put("year",new Integer(dateSection));
					firstIsDay = false;
				} else {
					dateMap.put("day",new Integer(dateSection));
					firstIsDay = true;
				}
				
				dateSection = arr[1];
				dateMap.put("month",new Integer(dateSection));
		   
				dateSection = arr[2];
				if (firstIsDay) {
					if(dateSection.length() != 4){
						throw new IllegalArgumentException("Could not parse date");
					}
					dateMap.put("year",new Integer(dateSection));
				} else {
					dateMap.put("day",new Integer(dateSection));
				}	
//				log.debug (">>>>>>>>>>>> dateMap "+dateMap);
				return dateMap;
	}

	
	private Map getDateTimeArray(String date,Boolean startOfDay) throws IllegalArgumentException {
		
//		log.debug(">>>>>>> getDateArray in function" + date);		
		DateFormatUtils dd = new DateFormatUtils();
		

		HashMap dateMap = new HashMap();
		String dateSection ;
		boolean firstIsDay;
		BindException errors = new BindException("","");
//		log.debug(">>>>>>>>> getDateArray() "+date);
		
			String arr[] = date.split(this.delimeter );
			if (arr.length != 3){
//				log.debug(">>>>>>>>>>>>>>>>arr.length  "+ arr.length );
				throw new IllegalArgumentException("Could not parse date");
			}
			dateSection = arr[0];
			if (dateSection.length()==4) {
				dateMap.put("year",new Integer(dateSection));
				firstIsDay = false;
			} else {
				dateMap.put("day",new Integer(dateSection));
				firstIsDay = true;
			}
			
			dateSection = arr[1];
			dateMap.put("month",new Integer(dateSection));
	
			dateSection = arr[2];
			if (firstIsDay) {
				dateMap.put("year",new Integer(dateSection));
			} else {
				dateMap.put("day",new Integer(dateSection));
			}
		
			if (startOfDay.booleanValue() == true){
				dateMap.put("hour",new Integer(1));
				dateMap.put("min",new Integer(0));
			}else if (startOfDay.booleanValue() == false){
				dateMap.put("hour",new Integer(23));
				dateMap.put("min",new Integer(59));
			}
//			log.debug (">>>>>>>>>>>> dateMap "+dateMap);
		return dateMap;
		
	}

	public void setDelimeter2(String delimeter2) {
		this.delimeter2 = delimeter2;
	}
	
	 public static Date getPreviousDay(Date aDate) {
	    	Calendar cal = Calendar.getInstance();
	    	cal.setTime(aDate);
	    	cal.add(Calendar.DAY_OF_MONTH,-1);
	    	return cal.getTime();
	    }

	
	public BaseManagerImpl getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManagerImpl baseManager) {
		this.baseManager = baseManager;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	
	public static String getSQLDate(Date theDate , boolean isStart){
		String returnString = "";
		if(theDate != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(theDate);
			
			String year = cal.get(Calendar.YEAR)+"";
			String month = (cal.get(Calendar.MONTH)+1)+"";
			String day = cal.get(Calendar.DAY_OF_MONTH)+"";
			if(month.length() == 1){
				month = "0" + month;
			}
			if(day.length() == 1){
				day = "0" + day;
			}
			returnString = year + "-" + month + "-" +day + ((isStart) ? " 00:00:01"  :  " 23:59:59");
			
		}
		
		
		return returnString;
	}

	public String getMeladiDateByInput(Date date) {
		setDate(date);
		return getMeladiString();
	}
	
	public String getDateTimeString() {
		String dateString = null;
		log.debug(" -- dateCalendarType : "+this.dateCalendarType);
		switch (dateCalendarType) {
		case HIJRI: 
			log.debug("Going for Hijri");
			dateString = getHijriTimeString();
			log.debug("Hijri :"+dateString);
			break;
		case MILADI: 
			log.debug("Going for Miladi");
			dateString = getMeladiTimeString();
			log.debug("Miladi :"+dateString);
			break;
		}
		return dateString;
	}
	
	public String getMeladiTimeString() {
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.setTimeInMillis(this.millis);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy H:mm");
		return sdf.format(calendar.getTime());
	}
	
	public String getHijriTimeString() {
		String dateString =  null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy H:mm");
		SimpleDateFormat timeFormatter = new SimpleDateFormat("H:mm");
		String miladiDateString = formatter.format(getDate());
//		try {
//			Statement stat = DBUtils.createStatement();
//			ResultSet rs = stat.executeQuery("select hijri from common_dates where to_char(miladi,'yyyy-mm-dd') = '"+miladiDateString+"'");
		Statement stat = null;
		ResultSet rs = null;
		try {
			try {	
				stat = DBUtils.createStatement();
				rs = stat.executeQuery("select hijri from common_dates where to_char(miladi,'yyyy-mm-dd') = '"+miladiDateString+"'");
			} catch (SQLException e) {
				try {
					stat = DBUtils.createStatement();
					rs = stat.executeQuery("select hijri from common_dates where CONVERT(VARCHAR,miladi,23) = '"+miladiDateString+"'");
				} catch (SQLException ex) {
					ex.printStackTrace();
					throw new IllegalArgumentException(ex.getMessage());
				}
			}
			try {
				if (rs.next()){
					String dbDateString = rs.getString("hijri");
					String[] elements = dbDateString.split("/");
					String dbDayString = elements[2];
					String dbMonthString = elements[1];
					String dbYearString = elements[0];
					Long dbDay = new Long(dbDayString);
					Long dbMonth = new Long(dbMonthString);
					Long dbYear = new Long(dbYearString);
					try{
						IslamicCalendar islamicCalendar = new IslamicCalendar();
						islamicCalendar.setLenient(false); 
						islamicCalendar.setCivil(true);
						islamicCalendar.clear();
						islamicCalendar.setTime(getDate());
						islamicCalendar.set(IslamicCalendar.YEAR , dbYear.intValue());
						islamicCalendar.set(IslamicCalendar.MONTH , dbMonth.intValue()-1);
						islamicCalendar.set(IslamicCalendar.DAY_OF_MONTH , dbDay.intValue());
						dateString = dateFormat.format(islamicCalendar) + " " + timeFormatter.format(islamicCalendar.getTime());
					}catch (Exception e) {
						dateString = dbDayString+"/" + dbMonthString + "/" + dbYearString;
					}
				} else {
					IslamicCalendar islamicCalendar = new IslamicCalendar();
					islamicCalendar.setLenient(false);
					islamicCalendar.setCivil(true);
					islamicCalendar.setTimeInMillis(this.millis);
					dateString = dateFormat.format(islamicCalendar) + " " + timeFormatter.format(islamicCalendar.getTime());
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalArgumentException(e.getMessage());
			}finally{
				rs.close();
				stat.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
		return dateString; 
	}
	
	public void setDateTimeString(String dateString)
			throws IllegalArgumentException {
		int year, month, day, hour, min;
		log.debug(">>>>>>> getDateArray" + dateString);
		Map dateMap = null;
		try {
			dateMap = getDateTimeArray(dateString);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		log.debug(">>>>>>> dateMap not empty");
		year = ((Integer) dateMap.get("year")).intValue();
		month = ((Integer) dateMap.get("month")).intValue();
		day = ((Integer) dateMap.get("day")).intValue();
		hour = ((Integer) dateMap.get("hour")).intValue();
		min = ((Integer) dateMap.get("min")).intValue();
		if (year < 1700) {
			setHijriDateTime(year, month, day, hour, min, true);
		} else {
			setMiladiDateTime(year, month, day, hour, min);
		}
	}

	private Map getDateTimeArray(String date) throws IllegalArgumentException {

		log.debug(">>>>>>> getDateTimeArray in function" + date);
		DateFormatUtils dd = new DateFormatUtils();
		HashMap dateMap = new HashMap();
		String dateSection;
		boolean firstIsDay;
		String arr[] = date.split(" ");
		String arrDate[] = arr[0].split(this.delimeter);

		dateMap.put("hour", new Integer(0));
		dateMap.put("min", new Integer(0));
		try{
		String arrTime[] = arr[1].split(this.timeDelimeter);
		dateMap.put("hour", new Integer(arrTime[0]));
		dateMap.put("min", new Integer(arrTime[1]));
		}catch (Exception e) {
			// TODO: handle exception
		}
		if (arrDate.length != 3) {
			log.debug(">>>>>>>>>>>>>>>>arr.length  " + arrDate.length);
			throw new IllegalArgumentException("Could not parse date time");
		}
		dateSection = arrDate[0];
		if (dateSection.length() == 4) {
			dateMap.put("year", new Integer(dateSection));
			firstIsDay = false;
		} else {
			dateMap.put("day", new Integer(dateSection));
			firstIsDay = true;
		}

		dateSection = arrDate[1];
		dateMap.put("month", new Integer(dateSection));

		dateSection = arrDate[2];
		if (firstIsDay) {
			dateMap.put("year", new Integer(dateSection));
		} else {
			dateMap.put("day", new Integer(dateSection));
		}
		
		log.debug(">>>>>>>>>>>> dateMap " + dateMap);
		return dateMap;

	}
	
	public void setTimeString(String dateString) throws IllegalArgumentException {
		int hour,minute,second;
		log.debug(">>>>>>> getTimeArray" + dateString);
		Map dateMap = null ;
		try{
			dateMap = getTimeArray(dateString);
		}catch(IllegalArgumentException e){
			throw new IllegalArgumentException(e.getMessage());
		}
		log.debug(">>>>>>> dateMap not empty");
		hour = ((Integer)dateMap.get("hour")).intValue();
		minute = ((Integer)dateMap.get("minute")).intValue();
		second = ((Integer)dateMap.get("second")).intValue();

		log.debug(">>>>>>>>>>>>>>>>>hour "+hour);
		log.debug(">>>>>>>>>>>>>>>>>minute "+minute);
		log.debug(">>>>>>>>>>>>>>>>>second "+second);
		
		setTime(hour,minute,second);
		
	}
	
	private Map getTimeArray(String date) throws IllegalArgumentException {
		log.debug(">>>>>>> getDateArray in function" + date);
		DateFormatUtils dd = new DateFormatUtils();
		HashMap dateMap = new HashMap();
		String dateSection;
		log.debug(">>>>>>>>> getDateArray() " + date);
		String arr[] = date.split(this.timeDelimeter);
		if (arr.length != 3) {
			log.debug(">>>>>>>>>>>>>>>>arr.length  " + arr.length);
			throw new IllegalArgumentException("Could not parse date");
		}
		dateSection = arr[0];
		dateMap.put("hour", new Integer(dateSection));

		dateSection = arr[1];
		dateMap.put("minute", new Integer(dateSection));

		dateSection = arr[2];
		dateMap.put("second", new Integer(dateSection));
		log.debug(">>>>>>>>>>>> dateMap " + dateMap);
		return dateMap;
	}

	public void setTime(int hour, int minute, int second)
			throws IllegalArgumentException {
		Calendar timeCalendar = Calendar.getInstance();
		timeCalendar.setLenient(false);
		try {
			timeCalendar.set(Calendar.HOUR_OF_DAY, hour);
			timeCalendar.set(Calendar.MINUTE, minute);
			timeCalendar.set(Calendar.SECOND, second);
			this.millis = timeCalendar.getTimeInMillis();
		} catch (Exception ex) {
			log.debug(">>>>>>>>>>>>>>>>>>>> ERROR " + ex.getMessage());
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
	
	public Date getMainTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.millis);
		log.debug(cal.getTime());
		return cal.getTime();
	}
	
	public void setMainTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		this.millis = cal.getTimeInMillis();			
	}
	
	public String getTimeString() {
		String dateString ;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.setTimeInMillis(this.millis);
	
		dateString = dateFormat4.format(calendar.getTime());
		log.debug(">>>>>>>>>>>>>>>>>7agaa momayazaaaaaaaaa "+dateString);
		return dateString;
	}
}