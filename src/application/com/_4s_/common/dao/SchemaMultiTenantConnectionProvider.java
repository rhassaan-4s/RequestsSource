package com._4s_.common.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;

public class SchemaMultiTenantConnectionProvider
        extends AbstractMultiTenantConnectionProvider
        implements ServiceRegistryAwareService {

    private static final long serialVersionUID = 1L;

    private static HikariCPConnectionProvider defaultProvider;
    private static String defaultSchema;

    private final Log logger = LogFactory.getLog(getClass());

    // ------------------------------------------------------------

    @Override
    public void injectServices(ServiceRegistryImplementor serviceRegistry) {

        try {

            Properties props = new Properties();
            props.load(
                getClass().getResourceAsStream("/hibernate.properties")
            );

            defaultSchema =
                props.getProperty("hibernate.connection.username");

            defaultProvider = new HikariCPConnectionProvider();
            defaultProvider.configure(props);

            logger.info("MultiTenant pool initialized. Default schema="
                    + defaultSchema);

        } catch (Exception e) {
            logger.error("Error initializing multitenant provider", e);
        }
    }

    // ------------------------------------------------------------
    // IMPORTANT: Hibernate uses this during bootstrap
    // ------------------------------------------------------------

    @Override
    public Connection getAnyConnection() throws SQLException {
        return defaultProvider.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection)
            throws SQLException {

        resetSchema(connection);
        connection.close();
    }

    // ------------------------------------------------------------

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return defaultProvider;
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(
            String tenantIdentifier) {
        return defaultProvider;
    }

    // ------------------------------------------------------------

    @Override
    public Connection getConnection(String tenantIdentifier)
            throws SQLException {

//        Connection connection = getAnyConnection();
//
//        String schema =
//                tenantIdentifier != null
//                        ? tenantIdentifier
//                        : defaultSchema;
//
//        setSchema(connection, schema);
//
//        return connection;
    	 Connection connection = defaultProvider.getConnection();

    	    String schema = (tenantIdentifier != null) ? tenantIdentifier : defaultSchema;

//    	    try (Statement st = connection.createStatement()) {
//    	        st.execute("ALTER SESSION SET CURRENT_SCHEMA = " + schema);
//    	    } catch (SQLException e) {
//    	        connection.close(); // 🔥 VERY IMPORTANT
//    	        throw e;
//    	    }
    	    setSchema(connection, schema); // use helper to log schema switch

    	    return connection;
    }

    @Override
    public void releaseConnection(
            String tenantIdentifier,
            Connection connection)
            throws SQLException {

//        try {
//            resetSchema(connection);
//        } finally {
//            connection.close();
//        }
    	try (Statement st = connection.createStatement()) {
//            st.execute("ALTER SESSION SET CURRENT_SCHEMA = " + defaultSchema);
//        } catch (Exception e) {
//            // log it at least
//            logger.error("Failed to reset schema", e);
    		resetSchema(connection); // use helper to log schema reset
        } finally {
            connection.close(); // ALWAYS
        }
    }

    // ------------------------------------------------------------
    // schema helpers
    // ------------------------------------------------------------

    private void setSchema(Connection connection, String schema)
            throws SQLException {

        try (Statement st = connection.createStatement()) {
            st.execute("ALTER SESSION SET CURRENT_SCHEMA = " + schema);
        }

        logger.debug("Schema switched to " + schema);
    }

    private void resetSchema(Connection connection)
            throws SQLException {

        try (Statement st = connection.createStatement()) {
            st.execute(
                "ALTER SESSION SET CURRENT_SCHEMA = " + defaultSchema
            );
        }
    }

    // ------------------------------------------------------------

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }
}


//package com._4s_.common.dao;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Map;
//import java.util.Properties;
//import java.util.concurrent.ConcurrentHashMap;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
//import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
//import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;
//import org.hibernate.service.spi.ServiceRegistryAwareService;
//import org.hibernate.service.spi.ServiceRegistryImplementor;
//
//public class SchemaMultiTenantConnectionProvider
//        extends AbstractMultiTenantConnectionProvider
//        implements ServiceRegistryAwareService {
//
//    private static final long serialVersionUID = 14535345L;
//
//    private static HikariCPConnectionProvider defaultProvider = null;
//    private static String defaultProviderSchemaUser = null;
//
//    private static final Map<String, HikariCPConnectionProvider> connectionProviderMap =
//            new ConcurrentHashMap<>();
//
//    private static final Map<String, String> clientSchemaMap =
//            new ConcurrentHashMap<>();
//
//    private volatile boolean tenantsLoaded = false;
//
//    private static Properties properties = null;
//    private String server;
//
//    private ServiceRegistryImplementor serviceRegistry;
//
//    Log logger = LogFactory.getLog(getClass());
//
//    // ------------------------------------------------------------------
//
//    @Override
//    public void injectServices(ServiceRegistryImplementor serviceRegistry) {
//        logger.debug("*********SchemaProvider: injectServices***********");
//        this.serviceRegistry = serviceRegistry;
//
//        if (defaultProvider == null) {
//            try {
//                if (properties == null) {
//                    properties = new Properties();
//                    properties.load(getClass().getResourceAsStream("/hibernate.properties"));
//                    properties.put("hibernate.multiTenancy", "SCHEMA");
//                }
//
//                defaultProviderSchemaUser =
//                        properties.getProperty("hibernate.connection.username");
//
//                defaultProvider = new HikariCPConnectionProvider() {
//                    @Override
//                    public boolean supportsAggressiveRelease() {
//                        return true;
//                    }
//                };
//
//                defaultProvider.configure(properties);
//
//                try (Connection c = defaultProvider.getConnection()) {
//                    c.createStatement().execute(
//                            "ALTER SESSION SET CURRENT_SCHEMA=" + defaultProviderSchemaUser
//                    );
//                }
//
//                logger.debug("Default provider configured with user: "
//                        + defaultProviderSchemaUser);
//
//            } catch (Exception e) {
//                logger.error("Error initializing default provider", e);
//            }
//
//            String connectionUrl = properties.getProperty("hibernate.connection.url");
//            if (connectionUrl != null) {
//                connectionUrl = connectionUrl.toLowerCase();
//                if (connectionUrl.contains("oracle")) {
//                    server = "oracle";
//                } else if (connectionUrl.contains("sql")) {
//                    server = "sql";
//                }
//            }
//        }
//
//        logger.debug("*********SchemaProvider: finished injectServices***********");
//    }
//
//    // ------------------------------------------------------------------
//
//    private synchronized void loadTenantsIfNeeded() {
//        if (tenantsLoaded) return;
//
//        logger.debug("Loading tenant schemas from COMMON_CLIENTS...");
//
//        try (Connection c = defaultProvider.getConnection()) {
//
//            String sql = String.format(
//                    "SELECT clientname, schema FROM COMMON_CLIENTS " +
//                    "WHERE schema IS NOT NULL AND server='%s'",
//                    server
//            );
//
//            ResultSet rs = c.createStatement().executeQuery(sql);
//
//            while (rs.next()) {
//                String clientName = rs.getString("clientname");
//                String schema = rs.getString("schema");
//                clientSchemaMap.put(schema, clientName);
//                logger.debug("Loaded tenant: " + clientName + " -> " + schema);
//            }
//
//            tenantsLoaded = true;
//
//        } catch (SQLException e) {
//            logger.error("Error loading tenant schemas", e);
//        }
//    }
//
//    // ------------------------------------------------------------------
//
//    @Override
//    protected ConnectionProvider getAnyConnectionProvider() {
//        return defaultProvider;
//    }
//
//    @Override
//    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
//        loadTenantsIfNeeded();
//
//        if (tenantIdentifier == null) {
//            tenantIdentifier = defaultProviderSchemaUser;
//        }
//
//        String client = clientSchemaMap.get(tenantIdentifier);
//
//        HikariCPConnectionProvider prov =
//                (client != null ? connectionProviderMap.get(client) : null);
//
//        if (prov == null) {
//            if (tenantIdentifier.equals(defaultProviderSchemaUser)) {
//                prov = defaultProvider;
//            } else {
//                prov = new HikariCPConnectionProvider() {
//                    @Override
//                    public boolean supportsAggressiveRelease() {
//                        return true;
//                    }
//                };
//                Properties tenantProps = copy(properties, tenantIdentifier);
//                prov.configure(tenantProps);
//            }
//
//            if (client != null) {
//                connectionProviderMap.put(client, prov);
//            }
//        }
//
//        return prov;
//    }
//
//    // ------------------------------------------------------------------
//
//    @Override
//    public Connection getConnection(String tenantIdentifier) throws SQLException {
//        ConnectionProvider provider = selectConnectionProvider(tenantIdentifier);
//        if (provider == null) {
//            throw new SQLException("No ConnectionProvider for tenant: " + tenantIdentifier);
//        }
//
//        Connection c = provider.getConnection();
//        if (c == null) {
//            throw new SQLException("Provider returned NULL connection for tenant: " + tenantIdentifier);
//        }
//        return c;
//    }
//
//    // ------------------------------------------------------------------
//
//    @Override
//    public void releaseAnyConnection(Connection connection) throws SQLException {
//        if (connection != null) {
//            connection.close();
//        }
//    }
//
////    @Override
////    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
////        if (connection != null) {
////            connection.close();
////        }
////    }
//    
//    @Override
//    public void releaseConnection(String tenantIdentifier, Connection connection)
//            throws SQLException {
//
//        if (connection != null) {
//            try (Statement st = connection.createStatement()) {
//                st.execute("ALTER SESSION SET CURRENT_SCHEMA=" + defaultProviderSchemaUser);
//            } catch (Exception e) {
//                // ignore
//            }
//            connection.close();
//        }
//    }
//
//    // ------------------------------------------------------------------
//
//    private Properties copy(Properties src, String tenant) {
//        Properties p = new Properties();
//        p.putAll(src);
//
//        // keep DB user, switch only schema
//        p.put("hibernate.hikari.connectionInitSql",
//                "ALTER SESSION SET CURRENT_SCHEMA=" + tenant);
//
//        logger.debug("Created tenant-specific properties for tenant: " + tenant);
//        return p;
//    }
//}
//
//
////package com._4s_.common.dao;
////
////import java.io.IOException;
////import java.sql.Connection;
////import java.sql.ResultSet;
////import java.sql.SQLException;
////import java.util.HashMap;
////import java.util.Map;
////import java.util.Properties;
////import java.util.concurrent.ConcurrentHashMap;
////
////import org.apache.commons.logging.Log;
////import org.apache.commons.logging.LogFactory;
////import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
////import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
////import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;
////import org.hibernate.service.spi.ServiceRegistryAwareService;
////import org.hibernate.service.spi.ServiceRegistryImplementor;
////
/////**
//// * Multi-tenant connection provider for schema-based multitenancy.
//// */
////public class SchemaMultiTenantConnectionProvider 
////        extends AbstractMultiTenantConnectionProvider 
////        implements ServiceRegistryAwareService {
////
////    private static final long serialVersionUID = 14535345L;
////
////    private static HikariCPConnectionProvider defaultProvider = null;
////    private static String defaultProviderSchemaUser = null;
////
////    private static final Map<String, HikariCPConnectionProvider> connectionProviderMap =
////            new ConcurrentHashMap<>();
////    private static final Map<String, String> clientSchemaMap = new HashMap<>();
////
////    private ServiceRegistryImplementor serviceRegistry;
////    private static Properties properties = null;
////    private String server;
////
////    Log logger = LogFactory.getLog(getClass());
////
////    @Override
////    public void injectServices(ServiceRegistryImplementor serviceRegistry) {
////        logger.debug("*********SchemaProvider: injectServices***********");
////        this.serviceRegistry = serviceRegistry;
////
////        if (defaultProvider == null) {
////            try {
////                if (properties == null) {
////                    properties = new Properties();
////                    properties.load(getClass().getResourceAsStream("/hibernate.properties"));
////                    properties.put("hibernate.multiTenancy", "SCHEMA");
////                }
////
////                defaultProviderSchemaUser =
////                        properties.getProperty("hibernate.connection.username");
////
////                defaultProvider = new HikariCPConnectionProvider() {
////                    @Override
////                    public boolean supportsAggressiveRelease() {
////                        return true;
////                    }
////                };
////
////                defaultProvider.configure(properties);
////
////                // Set default schema for base connection
////                try (Connection c = defaultProvider.getConnection()) {
////                    c.createStatement().execute(
////                            "ALTER SESSION SET CURRENT_SCHEMA=" + defaultProviderSchemaUser
////                    );
////                }
////
////                logger.debug("Default provider configured with user: "
////                        + defaultProviderSchemaUser);
////
////            } catch (Exception e) {
////                logger.error("Error initializing default provider", e);
////            }
////
////            // Detect DB server type
////            String connectionUrl = properties.getProperty("hibernate.connection.url");
////            if (connectionUrl != null) {
////                if (connectionUrl.toLowerCase().contains("oracle")) {
////                    server = "oracle";
////                } else if (connectionUrl.toLowerCase().contains("sql")) {
////                    server = "sql";
////                }
////            }
////
////            // Load tenants
////            try (Connection c = defaultProvider.getConnection()) {
////                String sql = String.format(
////                        "SELECT clientname, schema FROM COMMON_CLIENTS " +
////                        "WHERE schema IS NOT NULL AND server='%s'",
////                        server
////                );
////
////                ResultSet rs = c.createStatement().executeQuery(sql);
////                while (rs.next()) {
////                    String clientName = rs.getString("clientname");
////                    String schema = rs.getString("schema");
////                    clientSchemaMap.put(schema, clientName);
////                    logger.debug("Loaded client schema: "
////                            + clientName + " -> " + schema);
////                }
////            } catch (SQLException e) {
////                logger.error("Error fetching client schemas", e);
////            }
////        }
////
////        logger.debug("*********SchemaProvider: finished injectServices***********");
////    }
////
////    @Override
////    protected ConnectionProvider getAnyConnectionProvider() {
////        return defaultProvider;
////    }
////
//////    @Override
//////    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
//////        logger.debug("Selecting connection provider for tenant: " + tenantIdentifier);
//////
//////        String client = clientSchemaMap.get(tenantIdentifier);
//////        HikariCPConnectionProvider prov = client != null ? connectionProviderMap.get(client) : null;
//////
//////        if (prov == null) {
//////            if (tenantIdentifier.equals(defaultProviderSchemaUser)) {
//////                prov = defaultProvider;
//////                connectionProviderMap.put(client, prov);
//////            } else {
//////                prov = new HikariCPConnectionProvider() {
//////                    @Override
//////                    public boolean supportsAggressiveRelease() {
//////                        return true;
//////                    }
//////                };
//////                Properties tenantProps = copy(properties, tenantIdentifier);
//////                prov.configure(tenantProps);
//////                if (client != null) {
//////                    connectionProviderMap.put(client, prov);
//////                }
//////            }
//////            logger.debug("Created new provider for tenant: " + tenantIdentifier);
//////        }
//////
//////        return prov;
//////    }
////    @Override
////    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
////        if (tenantIdentifier == null) {
////            tenantIdentifier = defaultProviderSchemaUser;
////        }
////
////        String client = clientSchemaMap.get(tenantIdentifier);
////
////        HikariCPConnectionProvider prov =
////                (client != null ? connectionProviderMap.get(client) : null);
////
////        if (prov == null) {
////            if (tenantIdentifier.equals(defaultProviderSchemaUser)) {
////                prov = defaultProvider;
////            } else {
////                prov = new HikariCPConnectionProvider() {
////                    @Override
////                    public boolean supportsAggressiveRelease() {
////                        return true;
////                    }
////                };
////                Properties tenantProps = copy(properties, tenantIdentifier);
////                prov.configure(tenantProps);
////            }
////
////            if (client != null) {
////                connectionProviderMap.put(client, prov);
////            }
////        }
////
////        return prov;
////    }
////
////    @Override
////    public boolean supportsAggressiveRelease() {
////        return true;
////    }
////
//////    @Override
//////    public Connection getConnection(String tenantIdentifier) {
//////        try {
//////            ConnectionProvider provider = selectConnectionProvider(tenantIdentifier);
//////            return provider.getConnection();
//////        } catch (SQLException e) {
//////            logger.error("Error getting connection for tenant: " + tenantIdentifier, e);
//////            return null;
//////        }
//////    }
////    @Override
////    public Connection getConnection(String tenantIdentifier) throws SQLException {
////        ConnectionProvider provider = selectConnectionProvider(tenantIdentifier);
////        if (provider == null) {
////            throw new SQLException("No ConnectionProvider for tenant: " + tenantIdentifier);
////        }
////
////        Connection c = provider.getConnection();
////        if (c == null) {
////            throw new SQLException("Provider returned NULL connection for tenant: " + tenantIdentifier);
////        }
////        return c;
////    }
////
//////    @Override
//////    public void releaseAnyConnection(Connection connection) throws SQLException {
//////    	ConnectionProvider provider = getAnyConnectionProvider();
//////        if (provider != null && connection != null) {
//////            provider.closeConnection(connection);
//////        } else {
//////            // Log a warning so you know this happened
//////            logger.warn("No connection provider available to release connection: " + connection);
//////        }
//////    }
//////
//////    @Override
//////    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
//////        selectConnectionProvider(tenantIdentifier).closeConnection(connection);
//////    }
////    @Override
////    public void releaseAnyConnection(Connection connection) throws SQLException {
////        if (connection != null) {
////            connection.close(); // do NOT use provider here
////        }
////    }
////
////    @Override
////    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
////        if (connection != null) {
////            connection.close();
////        }
////    }
////
////    private Properties copy(Properties src, String tenant) {
////        Properties p = new Properties();
////        p.putAll(src);
//////        p.put("hibernate.connection.username", tenant);
//////        p.put("hibernate.connection.password", tenant);
////        p.put("hibernate.hikari.connectionInitSql",
////                "ALTER SESSION SET CURRENT_SCHEMA=" + tenant); // Oracle
////        logger.debug("Created tenant-specific properties for tenant: " + tenant);
////        return p;
////    }
////}
//
////package com._4s_.common.dao;
////
////import java.io.IOException;
////import java.sql.Connection;
////import java.sql.ResultSet;
////import java.sql.SQLException;
////import java.util.HashMap;
////import java.util.Map;
////import java.util.Properties;
////import java.util.concurrent.ConcurrentHashMap;
////
////import org.apache.commons.logging.Log;
////import org.apache.commons.logging.LogFactory;
////import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
////import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
////import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;
////import org.hibernate.service.spi.ServiceRegistryAwareService;
////import org.hibernate.service.spi.ServiceRegistryImplementor;
////
////public class SchemaMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider implements ServiceRegistryAwareService {
////    private static final long serialVersionUID = 14535345L;
////    private static final boolean allowAggressiveRelease = true;
////
////    private static HikariCPConnectionProvider defaultProvider = null;
////    private static String defaultProviderSchemaUser = null;
////
////    private static final Map<String, HikariCPConnectionProvider> connectionProviderMap = new ConcurrentHashMap<>();
////    private static final Map<String, String> clientSchemaMap = new HashMap<>();
////
////    private static Properties properties = null;
////    private ServiceRegistryImplementor serviceRegistry;
////    private String server;
////
////    private final Log logger = LogFactory.getLog(getClass());
////
////    @Override
////    public void injectServices(ServiceRegistryImplementor serviceRegistry) {
////        this.serviceRegistry = serviceRegistry;
////        if (defaultProvider == null) {
////            try {
////                if (properties == null) {
////                    properties = new Properties();
////                    properties.load(getClass().getResourceAsStream("/hibernate.properties"));
////                    properties.put("hibernate.multiTenancy", "SCHEMA");
////                    properties.put("hibernate.multi_tenant_connection_provider",
////                            "com._4s_.common.dao.SchemaMultiTenantConnectionProvider");
////                    properties.put("hibernate.tenant_identifier_resolver",
////                            "com._4s_.common.dao.CurrentTenantIdentifierResolverImpl");
////                }
////
////                defaultProvider = new HikariCPConnectionProvider() {
////                    @Override
////                    public boolean supportsAggressiveRelease() {
////                        return allowAggressiveRelease;
////                    }
////                };
////                defaultProvider.configure(properties);
////                defaultProviderSchemaUser = properties.getProperty("hibernate.defaultSchema",
////                        properties.getProperty("hibernate.connection.username"));
////
////                String connectionUrl = properties.getProperty("hibernate.connection.url");
////                if (connectionUrl.contains("oracle")) server = "oracle";
////                else if (connectionUrl.contains("sql")) server = "sql";
////
////                // Load tenant schemas from COMMON_CLIENTS table
////                try (Connection c = defaultProvider.getConnection()) {
////                    String stmt = String.format(
////                            "SELECT clientname, schema FROM COMMON_CLIENTS WHERE schema IS NOT NULL AND server='%s'",
////                            server);
////                    ResultSet rs = c.createStatement().executeQuery(stmt);
////                    while (rs.next()) {
////                        String clientName = rs.getString("clientname");
////                        String schema = rs.getString("schema");
////                        clientSchemaMap.put(schema, clientName);
////                        logger.debug("Loaded tenant mapping: " + clientName + " -> " + schema);
////                    }
////                }
////
////            } catch (IOException | SQLException e) {
////                logger.error("Error initializing SchemaMultiTenantConnectionProvider", e);
////            }
////        }
////    }
////
////    @Override
////    protected ConnectionProvider getAnyConnectionProvider() {
////        return defaultProvider;
////    }
////
////    @Override
////    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
////        String client = clientSchemaMap.get(tenantIdentifier);
////
////        // Use computeIfAbsent for thread-safe lazy initialization
////        return connectionProviderMap.computeIfAbsent(client != null ? client : tenantIdentifier, key -> {
////            if (tenantIdentifier.equals(defaultProviderSchemaUser)) {
////                logger.debug("Using default provider for tenant: " + tenantIdentifier);
////                return defaultProvider;
////            }
////
////            HikariCPConnectionProvider provider = new HikariCPConnectionProvider() {
////                @Override
////                public boolean supportsAggressiveRelease() {
////                    return allowAggressiveRelease;
////                }
////            };
////            Properties tenantProps = copy(properties, tenantIdentifier);
////            provider.configure(tenantProps);
////            logger.debug("Created new provider for tenant: " + tenantIdentifier);
////            return provider;
////        });
////    }
////
////    @Override
////    public Connection getConnection(String tenantIdentifier) throws SQLException {
////        ConnectionProvider provider = selectConnectionProvider(tenantIdentifier);
////        return provider.getConnection();
////    }
////
////    @Override
////    public void releaseAnyConnection(Connection connection) throws SQLException {
////        defaultProvider.closeConnection(connection);
////    }
////
////    @Override
////    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
////        ConnectionProvider provider = selectConnectionProvider(tenantIdentifier);
////        provider.closeConnection(connection);
////    }
////
////    @Override
////    public boolean supportsAggressiveRelease() {
////        return allowAggressiveRelease;
////    }
////
////    private Properties copy(Properties src, String tenant) {
////        Properties p = new Properties();
////        p.putAll(src);
////        p.put("hibernate.connection.username", tenant);
////        p.put("hibernate.connection.password", tenant);
////        return p;
////    }
////}
//
//
//
//
//
//
//
////package com._4s_.common.dao;
////
////import java.io.IOException;
////import java.sql.Connection;
////import java.sql.ResultSet;
////import java.sql.SQLException;
////import java.util.HashMap;
////import java.util.Map;
////import java.util.Properties;
////import java.util.concurrent.ConcurrentHashMap;
////
////import org.apache.commons.logging.Log;
////import org.apache.commons.logging.LogFactory;
//////import org.hibernate.engine.config.spi.ConfigurationService;
////import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
////import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
////import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;
////import org.hibernate.service.spi.ServiceRegistryAwareService;
////import org.hibernate.service.spi.ServiceRegistryImplementor;
////
////
////public class SchemaMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider implements ServiceRegistryAwareService {
////	public static final String hibernate_multi_tenant_connection_provider = "com._4s_.common.dao.SchemaMultiTenantConnectionProvider";
////	public static final String hibernate_tenant_identifier_resolver = "com._4s_.common.dao.CurrentTenantIdentifierResolverImpl";
////	public static final String hibernate_multiTenancy = "SCHEMA";
////	public static final boolean allowAggressiveRelease = true;
////	
////	private static HikariCPConnectionProvider defaultProvider = null;
////	private static String defaultProviderSchemaUser = null;
////	
////    Log logger = LogFactory.getLog(getClass());
////    private static final long serialVersionUID = 14535345L;
////    
////    private static Map<String, HikariCPConnectionProvider> connectionProviderMap = new ConcurrentHashMap<>();
////    private static Map<String,String> clientSchemaMap = new HashMap<String,String>();
////    
////    private ServiceRegistryImplementor serviceRegistry;
////    private static Properties properties = null;
////    private String server;
////    
//// 
////    Map<String, HikariCPConnectionProvider> getConnectionProviderMap() {
////        return connectionProviderMap;
////    }
////
////    @Override
////    public void injectServices(ServiceRegistryImplementor serviceRegistry) {
////    	logger.debug("*********SchemaProvider: injectServices***********");
////        this.serviceRegistry = serviceRegistry;
////        
////		if (defaultProvider == null) {
//////			logger.debug("default provider is null & will start initialization intialization");
////			try {
//////				logger.debug("will try initializing");
////				if (properties == null) {
////					properties = new Properties();
////					properties.load(getClass().getResourceAsStream("/hibernate.properties"));
////					properties.put("hibernate.multiTenancy", "SCHEMA");
////					properties.put("hibernate.multi_tenant_connection_provider",
////							"com._4s_.common.dao.SchemaMultiTenantConnectionProvider");
////					properties.put("hibernate.tenant_identifier_resolver",
////							"com._4s_.common.dao.CurrentTenantIdentifierResolverImpl");
////				}
////				defaultProvider = new HikariCPConnectionProvider() {
////					public boolean supportsAggressiveRelease() {
//////						logger.debug("inject services:  supportsAggressiveRelease" );
////						return allowAggressiveRelease;
////					}
////				};
////				defaultProvider.configure(properties);
////				
////				defaultProviderSchemaUser = (String)properties.getProperty("hibernate.connection.username");
////				
////				logger.debug("properties configured");
////
////			} catch (IOException e) {
////				// TODO Auto-generated catch block
////				logger.debug("exception " + e.getMessage());
////				e.printStackTrace();
////			}
////			String connection_url = (String) properties.getProperty("hibernate.connection.url");
////			if (connection_url.contains("oracle")) {
////				server = "oracle";
////			} else if (connection_url.contains("sql")) {
////				server = "sql";
////			}
////			Connection c = null;
////			logger.debug("will try getting connection for default provider " + defaultProvider);
////			try {
////				c = defaultProvider.getConnection();
//////				logger.debug("connection " + c);
////				String statement = String
////						.format("SELECT clientname, schema\r\n" + "		FROM COMMON_CLIENTS where schema is not null and server='%s'", server);
////				
//////				logger.debug("statement " + statement);
////				ResultSet rs = c.createStatement().executeQuery(statement);
////				defaultProvider.closeConnection(c);
////				if (rs != null) {
//////					logger.debug("resultset size " + rs.getFetchSize() + " - ROW NUMBER " + rs.getRow());
//////					logger.debug("############ Will try clients loop########");
////					while (rs.next()) {
//////						logger.debug("ROW NUMBER " + rs.getRow());
//////						logger.debug("############clients loop########");
////						String clientName = rs.getString("clientname");
////						String schema = rs.getString("schema");
////						clientSchemaMap.put(schema, clientName);
////						logger.debug("***clientSchemaMap***" + clientName + "-" + schema);
////					}
////				}
////			} catch (SQLException e) {
////				// TODO Auto-generated catch block
////				logger.debug("***## exception client ##***");
////				e.printStackTrace();
////			} finally {
//////				logger.debug("############ After clients resultset loop########");
////			}
////		}
////        logger.debug("*********SchemaProvider: finished injectServices***********");
////    }
////
////	@Override
////	public boolean supportsAggressiveRelease() {
////		// TODO Auto-generated method stub
//////		logger.debug("schema multitenant supportsAggressiveRelease" );
////		return allowAggressiveRelease;
////	}
////
////	private String getDefaultTenant() {
////		Properties properties = new Properties();
////		try {
////			properties.load(getClass().getResourceAsStream("/hibernate.properties"));
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		defaultProviderSchemaUser = (String)properties.getProperty("hibernate.connection.username");
////		return (String)properties.getProperty("hibernate.connection.username");
////	}
////
////	@Override
////	protected ConnectionProvider getAnyConnectionProvider() {
////		 return defaultProvider;
////	}
////
////	@Override
////	protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
////		logger.debug("****************(start selectConnectionProvider)***********");
//////		logger.debug("*********SchemaProvider: getting provider from map");
////		Object[] schemas = clientSchemaMap.keySet().toArray();
////		Object[] clients = clientSchemaMap.values().toArray();
////
////		String client = clientSchemaMap.get(tenantIdentifier);
////		logger.debug("SchemaProvider: getting client of schema " + tenantIdentifier + " ==>" + client);
////		HikariCPConnectionProvider prov = null;
////		if (client !=null) {
////			prov = connectionProviderMap.get(client);
////		} 
////		
////		if (prov==null) {
////			if (tenantIdentifier.equals(defaultProviderSchemaUser)) {
////				prov = defaultProvider;
////				connectionProviderMap.put(client, defaultProvider);
////			} else {
////				prov = new HikariCPConnectionProvider() {
////					public boolean supportsAggressiveRelease() {
////						return allowAggressiveRelease;
////					}
////				};
////
////				Properties tenantProps = copy(properties, tenantIdentifier);
////				prov.configure(tenantProps);
////				if (client != null && prov !=null) {
////					connectionProviderMap.put(client, prov);
////				}
////			}
////			
////			logger.debug("$%$%$%$%***placed the following in providers map " + client + " - " + prov);
////			
////		} else {
////			logger.debug("SchemaProvider: provider $$$$");
////			
////		}
////		logger.debug("****************(end selectConnectionProvider)***********");
////		return prov;
////	}
////	
////
////	@Override
////	public Connection getConnection(String tenantIdentifier) {// throws SQLException {
////		Connection connection = null;
////		try {
////			if (!connectionProviderMap.isEmpty()) {
////				ConnectionProvider p = selectConnectionProvider(tenantIdentifier);
////				logger.debug("SchemaProvider: provider " + p);
////				p.supportsAggressiveRelease();
////				connection = p.getConnection();
////			} else {
////				connection = super.getConnection(tenantIdentifier);
////				String statement = String.format("ALTER SESSION SET CURRENT_SCHEMA= %S", tenantIdentifier);
////				connection.createStatement().execute(statement);
////			}
////		} catch (SQLException e) {
////			logger.debug("exception while getting connection");
////			e.printStackTrace();
////		}
////		logger.debug("SchemaProvider: will return connection");
////		logger.debug("****************(end getConnection)***********");
////		return connection;
////	}
////
////	@Override
////	public void releaseAnyConnection(Connection connection) throws SQLException {
////		logger.debug("SchemaProvider: releasing any connection");
////		getAnyConnectionProvider().closeConnection( connection );
////	}
////
////	@Override
////	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
////		logger.debug("SchemaProvider: releasing connection");
////		selectConnectionProvider(tenantIdentifier).closeConnection(connection);
////	}
////    
////	private Properties copy(Properties src, String tenant) {
////	    Properties p = new Properties();
////	    p.putAll(src);
////	    p.put("hibernate.connection.username", tenant);
////	    p.put("hibernate.connection.password", tenant);
////	    return p;
////	}
////
////}