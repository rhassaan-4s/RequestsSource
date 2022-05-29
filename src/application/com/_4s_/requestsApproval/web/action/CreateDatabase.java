package com._4s_.requestsApproval.web.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Property;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;

import com._4s_.common.model.Settings;
import com._4s_.common.service.CommonManager;
import com._4s_.common.service.CommonManagerImpl;
import com._4s_.common.util.DBUtils;
import com.mysql.jdbc.ResultSet;
import com.sun.corba.se.spi.logging.LogWrapperBase;

import sun.util.logging.resources.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CreateDatabase extends HttpServlet implements
		ServletContextListener {
	private static String DRIVER_NAME;
	protected final Log log = LogFactory.getLog(getClass());
	
	

//	static {
//		try {
//			Class.forName(DRIVER_NAME).newInstance();
//			System.out.println("*** Driver loaded");
//		} catch (Exception e) {
//			System.out.println("*** Error : " + e.toString());
//			System.out.println("*** ");
//			System.out.println("*** Error : ");
//			e.printStackTrace();
//		}
//	}

	private static String URL;// = "jdbc:oracle:thin:@server2008:1521:orcl";
	private static String USER;// = "fleet";
	private static String PASSWORD;// = "fleet";
	private static String INSTRUCTIONS = new String();
	
	private static String contextpath;
	

	public static Connection getConnection() throws SQLException {
		
		Properties properties = new Properties();
		try {
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			System.out.println("classLoader " + classLoader);
//			InputStream is = classLoader.getResourceAsStream("/hibernate.properties");
//			System.out.println("is " + is);
//			properties.load(is);
			System.out.println("contextpath " + contextpath);
			FileInputStream fis = new FileInputStream(contextpath + "/WEB-INF/classes/hibernate.properties");
			System.out.println("fis " + fis);
			properties.load(fis);
			DRIVER_NAME = (String)properties.get("hibernate.connection.driver_class");
			System.out.println("driver name " + DRIVER_NAME);
			URL = (String)properties.get("hibernate.connection.url");
			System.out.println("url " + URL);
			USER = (String)properties.get("hibernate.connection.username");
			PASSWORD = (String)properties.get("hibernate.connection.password");
		} catch (Exception ee){
			ee.printStackTrace();
		}
		
		try {
			Class.forName(DRIVER_NAME).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
//		DRIVER_NAME = servletContext.getInitParameter("DriverClass");
//		URL = servletContext.getInitParameter("DataBaseURL");
//		USER = servletContext.getInitParameter("UserName");
//		PASSWORD = servletContext.getInitParameter("PassWord");
		String start = servletContext.getInitParameter("start");
		contextpath = servletContext.getRealPath("/");
		
		CommonManager commonManager = new CommonManagerImpl();
//		Settings settings = (Settings)commonManager.getObjectsOrderedByField(Settings.class,"id").get(0);
		Settings settings = getSettings();
		if (settings != null && settings.getAddNewData()!=null){
			boolean addNewData = settings.getAddNewData();
			
			try {
				if(addNewData == true) { //Lotus
					addNewData(contextpath);
				} else { //Lehaa
					if (start != null && !start.equals("") && start.equals("on")) {
						addData(contextpath);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		// none
	}

	public static void addData(String path) throws SQLException {//Lehaa
		String s = new String();
		StringBuffer sb = new StringBuffer();

		try {
			FileReader fr = new FileReader(new File(path + "/schema.sql"));
			BufferedReader br = new BufferedReader(fr);
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			br.close();
			String[] inst = sb.toString().split(";");
			Connection c = CreateDatabase.getConnection();
			Statement st = c.createStatement();
			for (int i = 0; i < inst.length; i++) {
				if (!inst[i].trim().equals("")) {
					st.executeUpdate(inst[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("*** Error : " + e.toString());
			e.printStackTrace();
			System.out.println(sb.toString());
		}
	}
	
	
	public static void addNewData(String path) throws SQLException {//Lotus
		String s = new String();
		StringBuffer sb = new StringBuffer();

		try {
			Connection c = CreateDatabase.getConnection();
			Statement st = c.createStatement();
			st.execute("call update_employee()");
			
			st.close();
			c.close();
			
		} catch (Exception e) {
			System.out.println("*** Error : " + e.toString());
			e.printStackTrace();
			System.out.println(sb.toString());
		}
	}
	
	public Settings getSettings() {
// 		List list = (List) getHibernateTemplate().execute( new HibernateCallback(){
// 			public Object doInHibernate(Session session) {
// 				Criteria criteria = session.createCriteria(clazz);
// 				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
// 				criteria.addOrder(Property.forName(field).asc());
// 				return criteria.list();
// 			}
// 		});
//         return list;
		List listRows = new ArrayList();
		java.sql.ResultSet results = null;
		Connection c;
		Settings settings = new Settings();
		
		try {
			c = CreateDatabase.getConnection();
			Statement st = c.createStatement();
			results = st.executeQuery("select * from common_settings order by id ");
			
			if (results!= null && results.next()==true) {
				settings.setAccessLevelsCheckDate(results.getBoolean("accessLevelsCheckDate"));
				settings.setAddNewData(results.getBoolean("addNewData"));
				settings.setAnnualVacBalDaysEnabled(results.getBoolean("ANNUALVACBALDAYSENABLED"));
				settings.setAttendanceRequestEn(results.getBoolean("attendanceRequestEn"));
				System.out.println("attendance request en "+ settings.getAttendanceRequestEn());
				settings.setCheckPostedRequests(results.getBoolean("checkPostedRequests"));
				settings.setCompanyLogoHeader(results.getBlob("companyLogoHeader"));
				settings.setCompanyLogoHeaderName(results.getString("companyLogoHeaderName"));
				settings.setCompanyRulesEn(results.getBoolean("companyRulesEn"));
				settings.setCreatingMail(results.getBoolean("creatingMail"));
				settings.setEmpRequestCheckDate(results.getBoolean("empRequestCheckDate"));
				settings.setEmpRequestTypeException(results.getBoolean("empRequestTypeException"));
				settings.setExecuseEnabled(results.getBoolean("execuseEnabled"));
				settings.setFooterCopyrightsEnabled(results.getBoolean("footerCopyrightsEnabled"));
				settings.setFromToRequestVald(results.getBoolean("fromToRequestVald"));
				settings.setId(results.getLong("id"));
				settings.setLoginUrl(results.getString("loginUrl"));
				settings.setMailMgr(results.getString("mailMgr"));
				settings.setMailPass(results.getString("mailPass"));
				settings.setNotesValidation(results.getBoolean("notesValidation"));
//				settings.setPassword(results.getString("password"));
				settings.setPeriodFromToEnabled(results.getBoolean("periodFromToEnabled"));
				settings.setPortServer(results.getString("portServer"));
				settings.setReqPeriodDate(results.getBoolean("reqPeriodDate"));
//				settings.setServer(results.getString("server"));
//				settings.setService(results.getString("service"));
				settings.setSMTPServer(results.getString("sMTPServer"));
				settings.setSpecialVacExcep(results.getBoolean("specialVacExcep"));
				settings.settAttRepWithHrsMin(results.getBoolean("tAttRepWithHrsMin"));
//				settings.setTAttRepWithHrsMin(results.getBoolean("attRepWithHrsMin"));
//				settings.setUsername(results.getString("username"));
				settings.setVacationRequestExcep(results.getBoolean("vacationRequestExcep"));
				settings.setVacLimitProblem(results.getBoolean("vacLimitProblem"));
				settings.setWithdrawDaysQuartPolicy(results.getBoolean("withdrawDaysQuartPolicy"));
				settings.setWithoutSalaryVacEnabled(results.getBoolean("withoutSalaryVacEnabled"));
				settings.setWithoutSalPeriodValidation(results.getBoolean("withoutSalPeriodValidation"));
				
			}
			results.close();
			st.close();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
//		JdbcTemplate jt = new JdbcTemplate(DBUtils.getDataSource());
//		StringBuilder sql = new StringBuilder();
//		sql.append("select * from common_settings order by id ");
//		listRows = jt.queryForList(sql.toString());
		return settings;
    }

	
}