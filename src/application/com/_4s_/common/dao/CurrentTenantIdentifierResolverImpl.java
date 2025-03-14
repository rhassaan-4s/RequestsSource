package com._4s_.common.dao;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/* It specify what Tenant should be use when the hibernate session is created.
* @author jm
*/
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
	
	private static final String KEY_TENANTID_SESSION = "tenantID";
    private static String DEFAULT_TENANTID;

	Log logger = LogFactory.getLog(getClass());
	
	public CurrentTenantIdentifierResolverImpl() throws IOException {
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/hibernate.properties"));
//		while (properties.elements().hasMoreElements()) {
//			logger.debug(properties.elements().nextElement());
//		}
		properties.put("hibernate.multi_tenant_connection_provider", SchemaMultiTenantConnectionProvider.hibernate_multi_tenant_connection_provider);
		properties.put("hibernate.tenant_identifier_resolver", SchemaMultiTenantConnectionProvider.hibernate_tenant_identifier_resolver);
		properties.put("hibernate.multiTenancy", SchemaMultiTenantConnectionProvider.hibernate_multiTenancy);
		logger.debug("***loaded Properties***");
		DEFAULT_TENANTID = (String)properties.getProperty("hibernate.connection.username");
		logger.debug("DEFAULT_TENANTID " + DEFAULT_TENANTID);
	}

//   @Override
   public String resolveCurrentTenantIdentifier() {
	   logger.debug("*************************CurrentTenantIdentifierResolverImpl*****************************");
       String tenant = resolveTenantByHttpSession();
       logger.debug("Tenant resolved: " + tenant);
       return tenant;
   }

   /**
    * Get tenantId in the session attribute KEY_TENANTID_SESSION
    * @return TenantId on KEY_TENANTID_SESSION
    */
   public String resolveTenantByHttpSession()
   {
       ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
       //If session attribute exists returns tenantId saved on the session
      
       if(attr != null){
    	   logger.debug("ServletRequestAttributes " + attr.getRequest());
    	   
           HttpSession session = attr.getRequest().getSession(false); // true == allow create
           if(session != null){
        	   logger.debug("session " + session);
        	   Enumeration<String> attributes = session.getAttributeNames();
        	   while (attributes.hasMoreElements()) {
        		   String att = attributes.nextElement();
        		   logger.debug("attribute " +att);
        	   }
               String tenant = (String) session.getAttribute(KEY_TENANTID_SESSION);
               logger.debug("tenant " + tenant);
               if(tenant != null){
                   return tenant;
               }
           }
       }
       //otherwise return default tenant
       logger.trace("Tenant resolved in session is: " + DEFAULT_TENANTID);
       return DEFAULT_TENANTID;
   }

//   @Override
   public boolean validateExistingCurrentSessions() {
	   logger.debug("*************************CurrentTenantIdentifierResolverImpl2*****************************");
       return true;
   }
}
