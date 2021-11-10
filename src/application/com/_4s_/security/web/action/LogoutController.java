package com._4s_.security.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.cache.EhCacheBasedUserCache;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

public class LogoutController implements Controller,ApplicationContextAware{
	private final Log log = LogFactory.getLog(getClass());
	ApplicationContext applicationContext;
	
	
//	SessionRegistry sessionRegistry;
//	
//	public SessionRegistry getSessionRegistry() {
//		return sessionRegistry;
//	}
//	public void setSessionRegistry(SessionRegistry sessionRegistry) {
//		this.sessionRegistry = sessionRegistry;
//	}
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		/*
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) return;
        Authentication authentication = context.getAuthentication();
        if (authentication == null) return;
        String sessionId = SessionRegistryUtils.obtainSessionIdFromAuthentication(authentication);
        this.sessionRegistry.removeSessionInformation(sessionId);
		*/
		
//		log.debug("sessionRegistry" + sessionRegistry);
//		
		SecurityContext sc = (SecurityContext) (SecurityContextHolder.getContext());
//		if(sc != null){
//			log.debug("sc != null)");
//			Authentication authentication = sc.getAuthentication();
//			if(authentication!= null){
//				log.debug("authentication!= null");
////		        String sessionId = SessionRegistryUtils.obtainSessionIdFromAuthentication(authentication)
//				String sessionId = request.getSession().getId();
//		        this.sessionRegistry.removeSessionInformation(sessionId);
//			}
//		}
		
		EhCacheBasedUserCache ehCacheBasedUserCache = (EhCacheBasedUserCache)applicationContext.getBean("userCache");
		if(sc != null && sc.getAuthentication() != null && sc.getAuthentication().getName()!= null){
			ehCacheBasedUserCache.removeUserFromCache( sc.getAuthentication().getName());
		}
		//sc.getAuthentication().
		request.getSession().invalidate();
		
		
		return new ModelAndView(new RedirectView("login.html"));
		//return new ModelAndView("index");
	}
}
