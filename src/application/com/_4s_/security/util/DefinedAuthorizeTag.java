package com._4s_.security.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;
//import org.springframework.web.util.ExpressionEvaluationUtils;

/**
 * An implementation of {@link javax.servlet.jsp.tagext.Tag} that allows it's
 * body through if some authorizations are granted to the request's principal.
 * 
 * @author Francois Beausoleil
 * @version $Id: AuthorizeTag.java,v 1.6 2004/07/23 01:24:54 fbos Exp $
 */
public class DefinedAuthorizeTag extends TagSupport {

	// ~ Instance fields
	// ========================================================
	protected final Log log = LogFactory.getLog(getClass());

	private String ifAllGranted = "";

	private String ifAnyGranted = "";

	private String ifNotGranted = "";
	
	// ~ Methods
	// ================================================================

	public void setIfAllGranted(String ifAllGranted) throws JspException {
		this.ifAllGranted = ifAllGranted;
	}

	public String getIfAllGranted() {
		return ifAllGranted;
	}

	public void setIfAnyGranted(String ifAnyGranted) throws JspException {
		this.ifAnyGranted = ifAnyGranted;
	}

	public String getIfAnyGranted() {
		return ifAnyGranted;
	}

	public void setIfNotGranted(String ifNotGranted) throws JspException {
		this.ifNotGranted = ifNotGranted;
	}

	public String getIfNotGranted() {
		return ifNotGranted;
	}

	public int doStartTag() throws JspException {
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>start do start tag<<<<<<<<<<<<<<<<<<<<<<,,");
		SecurityContext sc = (SecurityContext) (SecurityContextHolder.getContext());
		Authentication currentUser = sc.getAuthentication();
		ApplicationContext context = getContext(pageContext);

		AffirmativeBased affirm = (AffirmativeBased) context
				.getBean("myAccessDecisionManager");

		
		if (((null == ifAllGranted) || "".equals(ifAllGranted))
				&& ((null == ifAnyGranted) || "".equals(ifAnyGranted))
				&& ((null == ifNotGranted) || "".equals(ifNotGranted))) {
			return Tag.SKIP_BODY;
		}

		final Collection granted = getPrincipalAuthorities();

		
		final String evaledIfNotGranted = (String) ExpressionEvaluationUtils.evaluate(ifNotGranted, String.class, pageContext);
		if ((null != evaledIfNotGranted) && !"".equals(evaledIfNotGranted)) {
			Set grantedCopy = retainAll(granted,
					parseAuthoritiesString(evaledIfNotGranted));

			if (!grantedCopy.isEmpty()) {
				return Tag.SKIP_BODY;
			}
		}

		final String evaledIfAllGranted = (String) ExpressionEvaluationUtils.evaluate(ifAllGranted, String.class, pageContext);
//				ExpressionEvaluationUtils
//				.evaluateString("ifAllGranted", ifAllGranted, pageContext);
		if ((null != evaledIfAllGranted) && !"".equals(evaledIfAllGranted)) {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>skip body");
			final StringTokenizer tokenizer;
			SecurityConfig  securityConfig;
			Set soso = new HashSet();
			tokenizer = new StringTokenizer(ifAllGranted , ",", false);

			while (tokenizer.hasMoreTokens()) {
				String role = tokenizer.nextToken();
				  securityConfig = new SecurityConfig(role);	  
				  soso.add(securityConfig);
				  log.debug(">>>>>>>>>>>>>>>>>>>>>.role" +role+">>>>>>>>>>>>>>>>>>>>>");
			}
			Object obj = null;
//			ConfigAttributeDefinition configAttributeDefinition = new ConfigAttributeDefinition();
//			
//			Iterator itr= soso.iterator();
//			while(itr.hasNext()){
//				  securityConfig = ((SecurityConfig)itr.next());
//				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>config "+securityConfig.toString());
//				configAttributeDefinition.addConfigAttribute(securityConfig);
//			}
//			
//			try {
//				log.debug(">>>>>>>>>>>>>>>>>>>>>..inside try not denied");
//				affirm.decide(currentUser, obj, configAttributeDefinition);
//			} catch (AccessDeniedException e) {
//				return Tag.SKIP_BODY;
//			}

		}

		final String evaledIfAnyGranted = (String) ExpressionEvaluationUtils.evaluate(ifAnyGranted, String.class, pageContext);
//				.evaluateString("ifAnyGranted", ifAnyGranted, pageContext);
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>inside my class");
		if ((null != evaledIfAnyGranted) && !"".equals(evaledIfAnyGranted)) {
			Set grantedCopy = retainAll(granted,
					parseAuthoritiesString(evaledIfAnyGranted));

			if (grantedCopy.isEmpty()) {
				return Tag.SKIP_BODY;
			}
		}

		return Tag.EVAL_BODY_INCLUDE;
	}

	private Collection getPrincipalAuthorities() {
		SecurityContext sc = (SecurityContext) (SecurityContextHolder.getContext());

		if (null == sc) {
			return Collections.EMPTY_LIST;
		}

		Authentication currentUser = sc.getAuthentication();

		if (null == currentUser) {
			return Collections.EMPTY_LIST;
		}

		Collection granted = Arrays.asList(currentUser.getAuthorities());

		return granted;
	}

	private Set parseAuthoritiesString(String authorizationsString) {
		final Set requiredAuthorities = new HashSet();
		final StringTokenizer tokenizer;
		tokenizer = new StringTokenizer(authorizationsString, ",", false);

		while (tokenizer.hasMoreTokens()) {
			String role = tokenizer.nextToken();
			requiredAuthorities.add(new SimpleGrantedAuthority(role.trim()));
		}

		return requiredAuthorities;
	}

	private Set retainAll(final Collection granted,
			final Set requiredAuthorities) {
		Set grantedCopy = new HashSet(granted);
		grantedCopy.retainAll(requiredAuthorities);

		return grantedCopy;
	}

	protected ApplicationContext getContext(PageContext pageContext) {
		ServletContext servletContext = pageContext.getServletContext();

		return WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
	}

}
