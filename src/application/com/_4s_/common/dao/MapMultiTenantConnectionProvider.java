package com._4s_.common.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;


public class MapMultiTenantConnectionProvider implements MultiTenantConnectionProvider  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final Logger log = LogManager.getLogger(MapMultiTenantConnectionProvider.class);
	
	private HashMap<String,DriverManagerDataSource> dataSourceHashMap = new HashMap<String,DriverManagerDataSource>();
	
	private ComboPooledDataSource defaultDataSource;
	private ComboPooledDataSource cpds;
	
	
    public MapMultiTenantConnectionProvider()  throws PropertyVetoException {
    	Properties properties = new Properties();
    	String propertiesFile = "/hibernate.properties";
    	try {
			properties.load(getClass().getResourceAsStream(propertiesFile));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	log.debug("Properties file in costurctor " + properties);
    	System.out.println("Properties file in costurctor " + properties);
    	cpds = new ComboPooledDataSource(properties.getProperty("hibernate.connection.username"));
    	cpds.setJdbcUrl(properties.getProperty("hibernate.connection.url"));
    	cpds.setUser(properties.getProperty("hibernate.connection.username"));
    	cpds.setPassword(properties.getProperty("hibernate.connection.password"));
    	cpds.setInitialPoolSize(16);
    	cpds.setMaxConnectionAge(10000);
    	cpds.setDriverClass(properties.getProperty("hibernate.connection.driver_class"));
		
    	log.debug("cpds " + cpds);
		System.out.println("cpds " + cpds);
//		initConnectionProvider();
	}
    
    
//	@Override
	public Connection getAnyConnection() throws SQLException {
		log.debug("Get Default Connection:::Number of connections (max: busy - idle): {} : {} - {}",new int[]{cpds.getMaxPoolSize(),cpds.getNumBusyConnectionsAllUsers(),cpds.getNumIdleConnectionsAllUsers()});
        if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize()){
            log.warn("Maximum number of connections opened");
        }
        if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize() && cpds.getNumIdleConnectionsAllUsers()==0){
            log.error("Connection pool empty!");
        }
        return cpds.getConnection();
	}


//	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		log.debug("Get {} Connection:::Number of connections (max: busy - idle): {} : {} - {}",new Object[]{tenantIdentifier, cpds.getMaxPoolSize(),cpds.getNumBusyConnectionsAllUsers(),cpds.getNumIdleConnectionsAllUsers()});
		if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize()){
			log.warn("Maximum number of connections opened");
		}
		if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize() && cpds.getNumIdleConnectionsAllUsers()==0){
			log.error("Connection pool empty!");
		}
//		return cpds.getConnection(tenantIdentifier, PropertyUtil.getCredential(tenantIdentifier));
		PooledDataSource pds = C3P0Registry.pooledDataSourceByName(tenantIdentifier);
    	
    	
    	Properties properties = new Properties();
    	String propertiesFile = String.format("/hibernate-database-%s.properties", tenantIdentifier);
    	try {
			properties.load(getClass().getResourceAsStream(propertiesFile));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	log.debug("properties file in select datasource " + properties );
    	System.out.println("properties common " + properties);
    	System.out.println("pds " + pds);
    	if(pds==null){
    		cpds = new ComboPooledDataSource(properties.getProperty("hibernate.connection.username"));
			cpds.setJdbcUrl(properties.getProperty("hibernate.connection.url"));
			cpds.setUser(properties.getProperty("hibernate.connection.username"));
			cpds.setPassword(properties.getProperty("hibernate.connection.password"));
			cpds.setInitialPoolSize(16);
			cpds.setMaxConnectionAge(10000);
			try {
				cpds.setDriverClass(properties.getProperty("hibernate.connection.driver_class"));
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cpds.getConnection();

    	}
        return null;
	}
	
//	 @Override
	    public void releaseAnyConnection(Connection connection) throws SQLException {
	        connection.close();
	    }

//	    @Override
	    public void releaseConnection(String tenantIdentifier, Connection connection){
	        try {
	            this.releaseAnyConnection(connection);
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }

//	    @Override
	    public boolean supportsAggressiveRelease() {
	        return false;
	    }

	    @SuppressWarnings("rawtypes")
//	    @Override
	    public boolean isUnwrappableAs(Class unwrapType) {
	        return ConnectionProvider.class.equals( unwrapType ) || MapMultiTenantConnectionProvider.class.equals( unwrapType ) || MapMultiTenantConnectionProvider.class.isAssignableFrom( unwrapType );
	    }

	    @SuppressWarnings("unchecked")
//	    @Override
	    public <T> T unwrap(Class<T> unwrapType) {
	        if ( isUnwrappableAs( unwrapType ) ) {
	            return (T) this;
	        }
	        else {
	            throw new UnknownUnwrapTypeException( unwrapType );
	        }
	    }

    
    private void initConnectionProvider() throws IOException{
    	Iterator<String> itr = TenantIdNames.MYDB.iterator();
    	
    	while(itr.hasNext()) {
    		String tenantId = itr.next();
    		Properties properties = new Properties();
    		System.out.println("tenantId " + tenantId);
    		String propertiesFile = String.format("/hibernate-database-%s.properties", tenantId);
    		System.out.println("properties file " + propertiesFile);
    		properties.load(getClass().getResourceAsStream(propertiesFile));
//    		System.out.println("properties " + properties);
    		DriverManagerDataSource dataSource = new DriverManagerDataSource();
    		//     DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();
//    		System.out.println("dataSource " + dataSource);
    		try {
    			dataSource.setDriverClassName(properties.getProperty("hibernate.connection.driver_class"));
    			dataSource.setUrl(properties.getProperty("hibernate.connection.url"));
    			dataSource.setUsername(properties.getProperty("hibernate.connection.username"));
    			dataSource.setPassword(properties.getProperty("hibernate.connection.password"));
    			
    			System.out.println("datasource " + tenantId+"\n"+dataSource+"\n" +" username " +  dataSource.getUsername());
    		} catch (Exception e) {
    			e.printStackTrace();
    			System.out.println("Unable to connect to DB");
    		}

    		dataSourceHashMap.put(tenantId, dataSource);
    	}
    }
}

//public class MapMultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl  {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	protected final Log log = LogFactory.getLog(getClass());
//	
//	private HashMap<String,DriverManagerDataSource> dataSourceHashMap = new HashMap<String,DriverManagerDataSource>();
//	
//	private ComboPooledDataSource defaultDataSource;
//	
//	
//    public MapMultiTenantConnectionProvider()  throws IOException {
//    	Properties properties = new Properties();
//    	String propertiesFile = "/hibernate.properties";
//    	try {
//			properties.load(getClass().getResourceAsStream(propertiesFile));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//    	log.debug("Properties file in costurctor " + properties);
//    	System.out.println("Properties file in costurctor " + properties);
//    	defaultDataSource = new ComboPooledDataSource("shared");
//		defaultDataSource.setJdbcUrl(properties.getProperty("hibernate.connection.url"));
//		defaultDataSource.setUser(properties.getProperty("hibernate.connection.username"));
//		defaultDataSource.setPassword(properties.getProperty("hibernate.connection.password"));
//		defaultDataSource.setInitialPoolSize(16);
//		defaultDataSource.setMaxConnectionAge(10000);
//		try {
//			defaultDataSource.setDriverClass(properties.getProperty("hibernate.connection.driver_class"));
//		} catch (PropertyVetoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		log.debug("defaultDataSource " + defaultDataSource);
//		System.out.println("defaultDataSource " + defaultDataSource);
////		initConnectionProvider();
//	}
//	@Override
//    protected DataSource selectAnyDataSource() {
//        return dataSourceHashMap.get("4s");
//    }
//    @Override
//    protected DataSource selectDataSource(String tenantIdentifier) {
//    	PooledDataSource pds = C3P0Registry.pooledDataSourceByName(tenantIdentifier);
//    	
//    	
//    	Properties properties = new Properties();
//    	String propertiesFile = String.format("/hibernate-database-%s.properties", tenantIdentifier);
//    	try {
//			properties.load(getClass().getResourceAsStream(propertiesFile));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//    	log.debug("properties file in select datasource " + properties );
//    	System.out.println("properties common " + properties);
//    	System.out.println("pds " + pds);
//    	ComboPooledDataSource cpds = null;
//    	if(pds==null){
//    		cpds = new ComboPooledDataSource(properties.getProperty("hibernate.connection.username"));
//			cpds.setJdbcUrl(properties.getProperty("hibernate.connection.url"));
//			cpds.setUser(properties.getProperty("hibernate.connection.username"));
//			cpds.setPassword(properties.getProperty("hibernate.connection.password"));
//			cpds.setInitialPoolSize(16);
//			cpds.setMaxConnectionAge(10000);
//			try {
//				cpds.setDriverClass(properties.getProperty("hibernate.connection.driver_class"));
//			} catch (PropertyVetoException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return cpds;
//
//    	}
//        return pds;
//    }
//    
//    private void initConnectionProvider() throws IOException{
//    	Iterator<String> itr = TenantIdNames.MYDB.iterator();
//    	
//    	while(itr.hasNext()) {
//    		String tenantId = itr.next();
//    		Properties properties = new Properties();
//    		System.out.println("tenantId " + tenantId);
//    		String propertiesFile = String.format("/hibernate-database-%s.properties", tenantId);
//    		System.out.println("properties file " + propertiesFile);
//    		properties.load(getClass().getResourceAsStream(propertiesFile));
////    		System.out.println("properties " + properties);
//    		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//    		//     DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();
////    		System.out.println("dataSource " + dataSource);
//    		try {
//    			dataSource.setDriverClassName(properties.getProperty("hibernate.connection.driver_class"));
//    			dataSource.setUrl(properties.getProperty("hibernate.connection.url"));
//    			dataSource.setUsername(properties.getProperty("hibernate.connection.username"));
//    			dataSource.setPassword(properties.getProperty("hibernate.connection.password"));
//    			
//    			System.out.println("datasource " + tenantId+"\n"+dataSource+"\n" +" username " +  dataSource.getUsername());
//    		} catch (Exception e) {
//    			e.printStackTrace();
//    			System.out.println("Unable to connect to DB");
//    		}
//
//    		dataSourceHashMap.put(tenantId, dataSource);
//    	}
//    }
//}