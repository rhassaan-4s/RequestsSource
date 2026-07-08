package com._4s_.common.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public class MapMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

	private static final long serialVersionUID = 1L;
	protected final Log log = LogFactory.getLog(MapMultiTenantConnectionProvider.class);
    private final Map<String, ConnectionProvider> connectionProviderMap = new HashMap<>();

    public MapMultiTenantConnectionProvider() throws IOException {
    	Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/tenantID.properties"));
        Map<String, Object> tenantProperties = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
//            tenantProperties.put(key, value);
            log.debug("value " + value);
            initConnectionProviderForTenant(value);
        }
//        initConnectionProviderForTenant(TenantIdNames.MYDB1);
//        initConnectionProviderForTenant(TenantIdNames.MYDB2);
    }

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return connectionProviderMap.values()
            .iterator()
            .next();
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
        return connectionProviderMap.get(tenantIdentifier);
    }

    private void initConnectionProviderForTenant(String tenantId) throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream(String.format("/hibernate-database-%s.properties", tenantId)));
        Map<String, Object> configProperties = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            configProperties.put(key, value);
        }
        DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();
        connectionProvider.configure(configProperties);
        this.connectionProviderMap.put(tenantId, connectionProvider);
    }

}
