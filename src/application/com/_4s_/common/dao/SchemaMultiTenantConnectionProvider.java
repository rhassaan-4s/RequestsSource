package com._4s_.common.dao;

import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;


public class SchemaMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider implements ServiceRegistryAwareService {
	public static final String hibernate_multi_tenant_connection_provider = "com._4s_.common.dao.SchemaMultiTenantConnectionProvider";
	public static final String hibernate_tenant_identifier_resolver = "com._4s_.common.dao.CurrentTenantIdentifierResolverImpl";
	public static final String hibernate_multiTenancy = "SCHEMA";
	public static final boolean allowAggressiveRelease = true;
	
	private static HikariCPConnectionProvider defaultProvider = null;
	private static String defaultProviderSchemaUser = null;
	
    Log logger = LogFactory.getLog(getClass());
    private static final long serialVersionUID = 14535345L;
    
    private static Map<String, HikariCPConnectionProvider> connectionProviderMap = new HashMap<>();
    private static Map<String,String> clientSchemaMap = new HashMap<String,String>();
    
    private ServiceRegistryImplementor serviceRegistry;
    private static Properties properties = null;
    private String server;
    
 
    Map<String, HikariCPConnectionProvider> getConnectionProviderMap() {
        return connectionProviderMap;
    }

    @Override
    public void injectServices(ServiceRegistryImplementor serviceRegistry) {
    	logger.debug("*********SchemaProvider: injectServices***********");
        this.serviceRegistry = serviceRegistry;
        
		if (defaultProvider == null) {
			logger.debug("default provider is null & will start initialization intialization");
			try {
				logger.debug("will try initializing");
				if (properties == null) {
					properties = new Properties();
					properties.load(getClass().getResourceAsStream("/hibernate.properties"));
					properties.put("hibernate.multiTenancy", "SCHEMA");
					properties.put("hibernate.multi_tenant_connection_provider",
							"com._4s_.common.dao.SchemaMultiTenantConnectionProvider");
					properties.put("hibernate.tenant_identifier_resolver",
							"com._4s_.common.dao.CurrentTenantIdentifierResolverImpl");
				}
				defaultProvider = new HikariCPConnectionProvider() {
					public boolean supportsAggressiveRelease() {
						logger.debug("inject services:  supportsAggressiveRelease" );
						return allowAggressiveRelease;
					}
				};
//				defaultProvider.injectServices(serviceRegistry);
//				logger.debug("injected services");
				defaultProvider.configure(properties);
				
				defaultProviderSchemaUser = (String)properties.getProperty("hibernate.connection.username");
				
				logger.debug("properties configured");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug("exception " + e.getMessage());
				e.printStackTrace();
			}
			String connection_url = (String) properties.getProperty("hibernate.connection.url");
			if (connection_url.contains("oracle")) {
				server = "oracle";
			} else if (connection_url.contains("sql")) {
				server = "sql";
			}
			Connection c = null;
			logger.debug("will try getting connection for default provider " + defaultProvider);
			try {
				c = defaultProvider.getConnection();
				logger.debug("connection " + c);
				String statement = String
						.format("SELECT clientname, schema\r\n" + "		FROM COMMON_CLIENTS where schema is not null and server='%s'", server);
				
				logger.debug("statement " + statement);
				ResultSet rs = c.createStatement().executeQuery(statement);
				defaultProvider.closeConnection(c);
				if (rs != null) {
//					logger.debug("resultset size " + rs.getFetchSize() + " - ROW NUMBER " + rs.getRow());
					logger.debug("############ Will try clients loop########");
					while (rs.next()) {
						logger.debug("ROW NUMBER " + rs.getRow());
						logger.debug("############clients loop########");
						String clientName = rs.getString("clientname");
						String schema = rs.getString("schema");
						clientSchemaMap.put(schema, clientName);
						logger.debug("***clientSchemaMap***" + clientName + "-" + schema);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.debug("***## exception client ##***");
				e.printStackTrace();
			} finally {
				logger.debug("############ After clients resultset loop########");
			}
		}
        logger.debug("*********SchemaProvider: finished injectServices***********");
    }

//	public SchemaMultiTenantConnectionProvider() {
//		logger.debug("#################SchemaMultiTenantConnectionProvider constructor");
//		try {
//			logger.debug("will try initializing");
//			properties = new Properties();
//			properties.load(getClass().getResourceAsStream("/hibernate.properties"));
//			properties.put("hibernate.multiTenancy", "SCHEMA");
//			properties.put("hibernate.multi_tenant_connection_provider","com._4s_.common.dao.SchemaMultiTenantConnectionProvider");
//			properties.put("hibernate.tenant_identifier_resolver","com._4s_.common.dao.CurrentTenantIdentifierResolverImpl");
//			properties.put("hibernate.c3p0.min_size", 10);
//			properties.put("hibernate.c3p0.max_size", 100);
//			properties.put("hibernate.c3p0.acquire_increment", 5);
//			properties.put("hibernate.c3p0.timeout", 300);
//			properties.put("hibernate.c3p0.max_statements", 50);
//			properties.put("hibernate.c3p0.idle_test_period", 3000);
//			properties.put("hibernate.c3p0.debugUnreturnedConnectionStackTraces", true);
//			properties.put("hibernate.c3p0.unreturnedConnectionTimeout", 240);
//			properties.put("hibernate.c3p0.show_sql", true);
//			C3P0ConnectionProvider pp = new C3P0ConnectionProvider();
//			pp.injectServices(serviceRegistry);
//			pp.configure(properties);
//			this.defaultProvider=pp;
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			logger.debug("exception " + e.getMessage());
//			e.printStackTrace();
//		}
//	}

//	private Map<String, C3P0ConnectionProvider> initConnectionProvider() throws IOException{
//		logger.debug("*********SchemaProvider: initializing connection provider for multitenant project");
//		Properties properties = new Properties();
//		properties.load(getClass().getResourceAsStream("/hibernate.properties"));
//		properties.put("hibernate.multiTenancy", "SCHEMA");
//		properties.put("hibernate.multi_tenant_connection_provider","com._4s_.common.dao.SchemaMultiTenantConnectionProvider");
//		properties.put("hibernate.tenant_identifier_resolver","com._4s_.common.dao.CurrentTenantIdentifierResolverImpl");
//		properties.put("hibernate.c3p0.min_size", 10);
//		properties.put("hibernate.c3p0.max_size", 100);
//		properties.put("hibernate.c3p0.acquire_increment", 5);
//		properties.put("hibernate.c3p0.timeout", 300);
//		properties.put("hibernate.c3p0.max_statements", 50);
//		properties.put("hibernate.c3p0.idle_test_period", 3000);
//		properties.put("hibernate.c3p0.debugUnreturnedConnectionStackTraces", true);
//		properties.put("hibernate.c3p0.unreturnedConnectionTimeout", 240);
//		properties.put("hibernate.c3p0.show_sql", true);
//		String DEFAULT_TENANTID = (String)properties.getProperty("hibernate.connection.username");
//		
////		DriverManagerConnectionProviderImpl pp = new DriverManagerConnectionProviderImpl();
//		C3P0ConnectionProvider pp = new C3P0ConnectionProvider();
//		pp.injectServices(serviceRegistry);
//		pp.configure(properties);
//		this.defaultProvider=pp;
//		
//		
//		String connection_url = (String)properties.getProperty("hibernate.connection.url");
//		String server = "";
//		if (connection_url.contains("oracle")) {
//			server = "oracle";
//		} else if (connection_url.contains("sql")){
//			server = "sql";
//		}
//		Connection c = null;
//		try {
//			c = defaultProvider.getConnection();
//				String statement = String.format("SELECT clientname, schema\r\n"
//						+ "		FROM COMMON_CLIENTS where server='%s'", server);
//				logger.debug("statement " + statement);
//			ResultSet rs =  c.createStatement().executeQuery(statement);
////			logger.debug("resultset size " + rs.getFetchSize());
//			while(rs.next()) {
//				String clientName = rs.getString("clientname");
//				String schema = rs.getString("schema");
//				clientSchemaMap.put(schema,clientName);
////				DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();
//				C3P0ConnectionProvider connectionProvider = new C3P0ConnectionProvider();
//				properties.replace("hibernate.connection.username", schema);
//				properties.replace("hibernate.connection.password", schema);
//				logger.debug("SchemaProvider: "+clientName + "-"+ schema);
//				
//				
//				connectionProvider.injectServices(serviceRegistry);
//				connectionProvider.configure(properties);
//				this.connectionProviderMap.put(clientName, connectionProvider);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return connectionProviderMap;
//	}
	
	
	@Override
	public boolean supportsAggressiveRelease() {
		// TODO Auto-generated method stub
		logger.debug("schema multitenant supportsAggressiveRelease" );
		return allowAggressiveRelease;
	}

	private String getDefaultTenant() {
		Properties properties = new Properties();
		try {
			properties.load(getClass().getResourceAsStream("/hibernate.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		defaultProviderSchemaUser = (String)properties.getProperty("hibernate.connection.username");
		return (String)properties.getProperty("hibernate.connection.username");
	}

	@Override
	protected ConnectionProvider getAnyConnectionProvider() {
		 return defaultProvider;
	}

	@Override
	protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
		logger.debug("SchemaProvider: getting provider from map");
		Object[] schemas = clientSchemaMap.keySet().toArray();
		Object[] clients = clientSchemaMap.values().toArray();

		String client = clientSchemaMap.get(tenantIdentifier);
		logger.debug("SchemaProvider: getting client of schema " + tenantIdentifier + " ==>" + client);
//		logger.debug("clients map length " + schemas.length + "-" + clients.length);
		for (int i = 0; i<clients.length; i++) {
			logger.debug("########"+schemas[i] + " - " +  clients[i]);
		}
		HikariCPConnectionProvider prov = null;
		if (client !=null) {
			prov = connectionProviderMap.get(client);
		} 
		logger.debug("SchemaProvider: selecting connection provider from map ###" + prov +"###");
		
		if (prov==null) {
			logger.debug("@@@@SchemaProvider: provider is null");
			logger.debug("@@@@tenantIdentifier " + tenantIdentifier);
			logger.debug("@@@@defaultProviderSchemaUser " + defaultProviderSchemaUser);
			if (tenantIdentifier.equals(defaultProviderSchemaUser)) {
				logger.debug("SchemaProvider: provider is the default provider");
				prov = defaultProvider;
				logger.debug("SchemaProvider: ****** default provider " + prov);
				connectionProviderMap.put(client, defaultProvider);
			} else {
				prov = new HikariCPConnectionProvider() {
					public boolean supportsAggressiveRelease() {
						logger.debug("^^^^^^^select connection provider:  supportsAggressiveRelease" );
						return allowAggressiveRelease;
					}
				};
				logger.debug("^^^^^^SchemaProvider: new provider " + prov);
				logger.debug("^^^^^^will inject services " + serviceRegistry);
//				prov.injectServices(serviceRegistry);

				properties.replace("hibernate.connection.username", tenantIdentifier);
				logger.debug("replaced username");
				properties.replace("hibernate.connection.password", tenantIdentifier);
				logger.debug("replaced password");
				logger.debug("SchemaProvider: " + tenantIdentifier + "-" + tenantIdentifier);

				logger.debug("will configure properties");
				prov.configure(properties);
				if (client != null && prov !=null) {
					connectionProviderMap.put(client, prov);
				}
			}
			
			logger.debug("$%$%$%$%***placed the following in providers map " + client + " - " + prov);
			
		} else {
			logger.debug("SchemaProvider: provider $$$$");
			
		}
		
		return prov;
	}
	

	@Override
	public Connection getConnection(String tenantIdentifier) {// throws SQLException {
		logger.debug("SchemaProvider: tenantIdentifier " + tenantIdentifier);
		Connection connection = null;
		try {
			if (!connectionProviderMap.isEmpty()) {
				ConnectionProvider p = selectConnectionProvider(tenantIdentifier);
				logger.debug("SchemaProvider: provider " + p);
				p.supportsAggressiveRelease();
				connection = p.getConnection();
			} else {
				connection = super.getConnection(tenantIdentifier);
				String statement = String.format("ALTER SESSION SET CURRENT_SCHEMA= %S", tenantIdentifier);
				logger.debug("SchemaProvider: statement " + statement);
				connection.createStatement().execute(statement);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.debug("exception while getting connection");
			logger.debug("mafish fayda");
			e.printStackTrace();
			
//			try {
//				if (!connectionProviderMap.isEmpty()) {
//					ConnectionProvider p = selectConnectionProvider(tenantIdentifier);
//					logger.debug("SchemaProvider: provider " + p);
//					connection = p.getConnection();
//				} else {
//					connection = super.getConnection(tenantIdentifier);
//					String statement = String.format("ALTER SESSION SET CURRENT_SCHEMA= %S", tenantIdentifier);
//					logger.debug("SchemaProvider: statement " + statement);
//					connection.createStatement().execute(statement);
//				}
//			} catch (SQLException ex) {
//				logger.debug("mafish fayda");
//			}
		}
		logger.debug("SchemaProvider: will return connection");
		return connection;
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		logger.debug("SchemaProvider: releasing any connection");
		getAnyConnectionProvider().closeConnection( connection );
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		logger.debug("SchemaProvider: releasing connection");
		selectConnectionProvider(tenantIdentifier).closeConnection(connection);
	}
    

//    @Override
//	public Connection getAnyConnection() throws SQLException {
//		// TODO Auto-generated method stub
//		return super.getAnyConnection();
//	}
//
////	@Autowired
//    private DataSource dataSource;//defaultDataSource
//    public DataSource getDataSource() {
//		return dataSource;
//	}
//
//	public void setDataSource(DataSource dataSource) {
//		logger.debug("datasource setting " + dataSource);
//		this.dataSource = dataSource;
//	}

//	@Autowired
//    private final MultiTenantDataSourceLookup dataSourceLookup = new MultiTenantDataSourceLookup();
//    private DataSourceLookup dataSourceLookup;

//    public MultiTenantDataSourceLookup getDataSourceLookup() {
//		return dataSourceLookup;
//	}
//
//	public void setDataSourceLookup(MultiTenantDataSourceLookup dataSourceLookup) {
//		this.dataSourceLookup = dataSourceLookup;
//	}

	/**
     * Select datasources in situations where not tenantId is used (e.g. startup processing).
    */
//    @Override
//    protected DataSource selectAnyDataSource() {
//        logger.debug("Select any dataSource: " + dataSource);
//        logger.debug("*************************MultiTenantConnectionProviderImpl*****************************" + dataSource+"####");
//        if (dataSource == null) {
//        	selectDataSource("4s");	
//        }
//        return dataSource;
//    }
//
//    /**
//     * Obtains a DataSource based on tenantId
//    */
//    @Override
//    protected DataSource selectDataSource(String tenantIdentifier) {
//    	 logger.debug("*************************MultiTenantConnectionProviderImpl2*********************"+dataSourceLookup+"********");
//        DataSource ds = dataSourceLookup.getDataSource(tenantIdentifier);
//        logger.debug("*************************MultiTenantConnectionProviderImpl3*****************************");
//        logger.trace("Select dataSource from "+ tenantIdentifier+ ": " + ds);
//        logger.debug("Select dataSource from "+ tenantIdentifier+ ": " + ds);
//        return ds;
//    }
}