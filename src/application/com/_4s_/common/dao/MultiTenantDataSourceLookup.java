package com._4s_.common.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;
import org.springframework.stereotype.Component;

import com.jolbox.bonecp.BoneCPDataSource;

//@Component(value="dataSourceLookup")
public class MultiTenantDataSourceLookup extends MapDataSourceLookup {

	Log logger = LogFactory.getLog(getClass());

	private String tenantDbConfigs = "classpath:Requests/WEB-INF/classes/database.properties";// For testing
	private String tenantDbConfigsOverride = "classpath:Requests/WEB-INF/classes/*.properties";
	private String tenantRegex = "database.*.properties";
	private String DEFAULT_TENANTID = "4s";

	public MultiTenantDataSourceLookup(BoneCPDataSource defaultDataSource) {
		super();
		System.out.println("##########################MultiTenantDataSourceLookup#################################");
		try {
			initializeDataSources(defaultDataSource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initializeDataSources(BoneCPDataSource defaultDataSource) throws IOException {
		// TODO Auto-generated method stub
		String catalinaBase = System.getProperties().getProperty("catalina.base");
		System.out.println("##########################MultiTenantDataSourceLookup2#################################");
		logger.debug("MultiTenancy Configuration");
		logger.debug("--------------------------");
		//Add default tenant
		addDataSource(DEFAULT_TENANTID,defaultDataSource);
		logger.debug("Configuring default tenant: DefaultTenant - Properties: " + defaultDataSource.toString());
		//Add other tenants
		logger.debug("-- CLASSPATH TENANTS --");
		addTenantDataSources(defaultDataSource, "file:"+ catalinaBase + tenantDbConfigsOverride);
		logger.debug("---------------------------");

	}

	private void addTenantDataSources(BoneCPDataSource defaultDataSource,	String dbConfigs) {
		// TODO Auto-generated method stub
		Pattern tenantPattern = Pattern.compile(this.tenantRegex);
		PathMatchingResourcePatternResolver fileResolver = new PathMatchingResourcePatternResolver();

		InputStream dbProperties = null;

		try {
			Resource[] resources = fileResolver.getResources(dbConfigs);
			for (Resource resource: resources) {
				Properties props = new Properties(defaultDataSource.getClientInfo());
				dbProperties = resource.getInputStream();
				props.load(dbProperties);


				// Get tenantId using the filename and pattern
				String tenantId = getTenantId(tenantPattern, resource.getFilename());

				// Add new datasource with own configuration per tenant
				BoneCPDataSource customDataSource = createTenantDataSource(props, defaultDataSource);
				addDataSource(tenantId, customDataSource); // It replace if tenantId was already there.

				logger.info("Configured tenant: " + tenantId + " - Properties: " + customDataSource.toString());

			}
		} catch (FileNotFoundException fnfe) {
			logger.warn("Not tenant configurations or path not found: " + fnfe.getMessage());
		} catch (IOException ioe) {
			logger.error("Error getting the tenants: " + ioe.getMessage());
		} finally {
			if (dbProperties != null) {
				try {
					dbProperties.close();
				} catch (IOException e) {
					logger.error("Error closing a property tenant: " + dbProperties.toString());
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Create a datasource with tenant properties, if a property is not found in Properties 
	 * it takes the property from the defaultDataSource
	 *
	 * @param defaultDataSource a default datasource
	 * @return a BoneCPDataSource based on tenant and default properties
	 */
	private BoneCPDataSource createTenantDataSource(Properties tenantProps, BoneCPDataSource defaultDataSource)
	{
		BoneCPDataSource customDataSource = new BoneCPDataSource();

		//url, username and password must be unique per tenant so there is not default value
		customDataSource.setJdbcUrl(tenantProps.getProperty("database.url")); 
		customDataSource.setUsername(tenantProps.getProperty("database.username")); 
		customDataSource.setPassword(tenantProps.getProperty("database.password"));
		//These has default values in defaultDataSource
		customDataSource.setDriverClass(tenantProps.getProperty("database.driverClassName", defaultDataSource.getDriverClass()));
		customDataSource.setIdleConnectionTestPeriodInMinutes(Long.valueOf(tenantProps.getProperty(
				"database.idleConnectionTestPeriod",String.valueOf(defaultDataSource.getIdleConnectionTestPeriodInMinutes()))));
		customDataSource.setIdleMaxAgeInMinutes(Long.valueOf(tenantProps.getProperty(
				"database.idleMaxAge", String.valueOf(defaultDataSource.getIdleMaxAgeInMinutes()))));
		customDataSource.setMaxConnectionsPerPartition(Integer.valueOf(tenantProps.getProperty(
				"database.maxConnectionsPerPartition", String.valueOf(defaultDataSource.getMaxConnectionsPerPartition()))));
		customDataSource.setMinConnectionsPerPartition(Integer.valueOf(tenantProps.getProperty(
				"database.minConnectionsPerPartition", String.valueOf(defaultDataSource.getMinConnectionsPerPartition()))));
		customDataSource.setPartitionCount(Integer.valueOf(tenantProps.getProperty(
				"database.partitionCount", String.valueOf(defaultDataSource.getPartitionCount()))));
		customDataSource.setAcquireIncrement(Integer.valueOf(tenantProps.getProperty(
				"database.acquireIncrement", String.valueOf(defaultDataSource.getAcquireIncrement()))));
		customDataSource.setStatementsCacheSize(Integer.valueOf(tenantProps.getProperty(
				"database.statementsCacheSize",String.valueOf(defaultDataSource.getStatementCacheSize()))));
		customDataSource.setReleaseHelperThreads(Integer.valueOf(tenantProps.getProperty(
				"database.releaseHelperThreads", String.valueOf(defaultDataSource.getReleaseHelperThreads()))));customDataSource.setDriverClass(tenantProps.getProperty("database.driverClassName"));

				return customDataSource;
	}

	/**
	 * Get the tenantId from filename using the pattern
	 * 
	 * @param tenantPattern
	 * @param filename
	 * @return tenantId
	 * @throws IOException
	 */
	private String getTenantId(Pattern tenantPattern, String filename) throws IOException {
		Matcher matcher = tenantPattern.matcher(filename);
		boolean findMatch = matcher.matches();
		if (!findMatch) {
			throw new IOException("Error reading tenant name in the filename");
		}
		return matcher.group(1);
	}


}
