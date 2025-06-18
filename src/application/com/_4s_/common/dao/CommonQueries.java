package com._4s_.common.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.transaction.PlatformTransactionManager;

public class CommonQueries {

	protected final Log log = LogFactory.getLog(getClass());
	
//	private JdbcTemplate jdbcTemplate;
	private BasicDataSource basicDataSource;
//	@Autowired
	private String driverClass;
//	@Autowired
	private String url;
//	@Autowired
	private String username;
//	@Autowired
	private String password;
//	@Autowired
	private String schema;

	private PlatformTransactionManager platformTransactionManager;


	public PlatformTransactionManager getPlatformTransactionManager() {
		return platformTransactionManager;
	}


	public void setPlatformTransactionManager(
			PlatformTransactionManager platformTransactionManager) {
		this.platformTransactionManager = platformTransactionManager;
	}
	
	
	public String getSchema() {
		return schema;
	}


	public void setSchema(String schema) {
		this.schema = schema;
	}


//	public JdbcTemplate getJdbcTemplate() {
//		return jdbcTemplate;
//	}


//	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//		this.jdbcTemplate = jdbcTemplate;
//	}


	public BasicDataSource getBasicDataSource() {
		return basicDataSource;
	}


	public void setBasicDataSource(BasicDataSource basicDataSource) {
		this.basicDataSource = basicDataSource;
	}


	public String getDriverClass() {
		return driverClass;
	}


	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	private Properties loadProperties(String contextPath) {
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
	public BasicDataSource createDataSource() {
//		Properties prop = loadProperties();
		if(basicDataSource == null){
			basicDataSource = new BasicDataSource();
//			driverClass = prop.getProperty("hibernate.connection.driver_class");
//			url = prop.getProperty("hibernate.connection.url");
//			username = prop.getProperty("hibernate.connection.username");
//			password = prop.getProperty("hibernate.connection.password");
			
			basicDataSource.setDriverClassName(driverClass);
			basicDataSource.setUrl(url);
			basicDataSource.setUsername(username);
			basicDataSource.setPassword(password);
		}
		return basicDataSource;
	}
	
	
	public <T> List<T> map(Class<T> type, List<Object[]> records){
		List<T> result = new ArrayList<T>();
		   for(Object[] record : records){
			   T rec = map(type, record);
			   System.out.println("record " + rec);
		      result.add(rec);
		   }
		   return result;
	}

	public <T> List<T> getResultList(Session session, Query query, Class<T> type){
		log.debug("####session " + session);
		List l = query.getResultList();
	//	log.debug("####First item in list " + l.get(0));
		log.debug("####class type " + type);
		List<Object[]> records = l;
		return map(type, records);
	}
	
	public <T> T map(Class<T> type, Object[] tuple){
		   List<Class<?>> tupleTypes = new ArrayList<>();
		   for(Object field : tuple){
			   if (field !=null) {
				   System.out.println("field " + field.getClass());
			   } else {
				   System.out.println("field is null");
			   }
//			   if (field==null) {
				   tupleTypes.add(Object.class);
//			   } else {
//				   tupleTypes.add(field.getClass());
//			   }
		   }
		   try {
		      Constructor<T> ctor = type.getConstructor(tupleTypes.toArray(new Class<?>[tuple.length]));
		      log.debug("constructor " + ctor);
		      return ctor.newInstance(tuple);
		   } catch (Exception e) {
		      throw new RuntimeException(e);
		   }
		}
}
