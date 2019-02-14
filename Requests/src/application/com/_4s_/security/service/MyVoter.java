package com._4s_.security.service;

import java.util.Iterator;

import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.vote.AccessDecisionVoter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com._4s_.common.service.BaseManager;
import com._4s_.security.model.User;

public class MyVoter implements AccessDecisionVoter {
	private String rolePrefix = "";

	private BaseManager baseManager = null;

	protected final Log log = LogFactory.getLog(getClass());

	public BaseManager getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}

	public String getRolePrefix() {
		return rolePrefix;
	}

	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		if ((attribute.getAttribute() != null)
				&& (attribute.getAttribute().startsWith(rolePrefix))) {
			return true;
		} else {
			return false;
		}
	}

	public int vote(Authentication authentication, Object object,
	        ConfigAttributeDefinition config) {
	        int result = ACCESS_ABSTAIN;
	        SecurityContext sc = (SecurityContext) (SecurityContextHolder.getContext());
			log.debug("------------------------------------------username:--- "
					+ sc.getAuthentication().getName());
			String username = sc.getAuthentication().getName();
			User user = (User) baseManager.getObjectByParameter(User.class,
					"username", username);
			
	        Iterator iter = config.getConfigAttributes();

	        while (iter.hasNext()) {
	            ConfigAttribute attribute = (ConfigAttribute) iter.next();

	            if (this.supports(attribute)) {
	                result = ACCESS_DENIED;

	                // Attempt to find a matching granted authority
	                for (int i = 0; i < authentication.getAuthorities().length;
	                    i++) {
	                    if (attribute.getAttribute().equals(authentication
	                            .getAuthorities()[i].getAuthority())) {
	                        return ACCESS_GRANTED;
	                    }
	                }
	            }
	        }

	        return result;
	    }

}
