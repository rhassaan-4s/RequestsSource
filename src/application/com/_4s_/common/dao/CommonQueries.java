package com._4s_.common.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

public class CommonQueries {
	
	private JdbcTemplate jdbcTemplate;
	private BasicDataSource basicDataSource;
	
	private String driverClass;
	private String url;
	private String username;
	private String password;
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


	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


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
}
