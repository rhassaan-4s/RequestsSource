package com._4s_.common.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.service.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class MultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl
{

    Log logger = LogFactory.getLog(getClass());

    private static final long serialVersionUID = 14535345L;
    
    

    @Override
	public Connection getAnyConnection() throws SQLException {
		// TODO Auto-generated method stub
		return super.getAnyConnection();
	}

//	@Autowired
    private DataSource dataSource;//defaultDataSource
    public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		System.out.println("datasource setting " + dataSource);
		this.dataSource = dataSource;
	}

//	@Autowired
    private MultiTenantDataSourceLookup dataSourceLookup;
//    private DataSourceLookup dataSourceLookup;

    public MultiTenantDataSourceLookup getDataSourceLookup() {
		return dataSourceLookup;
	}

	public void setDataSourceLookup(MultiTenantDataSourceLookup dataSourceLookup) {
		this.dataSourceLookup = dataSourceLookup;
	}

	/**
     * Select datasources in situations where not tenantId is used (e.g. startup processing).
    */
    @Override
    protected DataSource selectAnyDataSource() {
        logger.debug("Select any dataSource: " + dataSource);
        System.out.println("*************************MultiTenantConnectionProviderImpl*****************************" + dataSource+"####");
        if (dataSource == null) {
        	selectDataSource("4s");	
        }
        return dataSource;
    }

    /**
     * Obtains a DataSource based on tenantId
    */
    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
    	 System.out.println("*************************MultiTenantConnectionProviderImpl2*********************"+dataSourceLookup+"********");
        DataSource ds = dataSourceLookup.getDataSource(tenantIdentifier);
        System.out.println("*************************MultiTenantConnectionProviderImpl3*****************************");
        logger.trace("Select dataSource from "+ tenantIdentifier+ ": " + ds);
        System.out.println("Select dataSource from "+ tenantIdentifier+ ": " + ds);
        return ds;
    }
}