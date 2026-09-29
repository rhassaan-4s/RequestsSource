package com._4s_.security.web.action;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.cache.EhCacheBasedUserCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.dao.TenantContext;

@Controller
public class LogoutController{// implements ApplicationContextAware{//Controller
	private final Log log = LogFactory.getLog(getClass());
	
//	ApplicationContext applicationContext;
//	
//	// @Resource
//	 private UserCache userCache;
	
	
//	SessionRegistry sessionRegistry;
//	
//	public SessionRegistry getSessionRegistry() {
//		return sessionRegistry;
//	}
//	public void setSessionRegistry(SessionRegistry sessionRegistry) {
//		this.sessionRegistry = sessionRegistry;
//	}
//	public ApplicationContext getApplicationContext() {
//		return applicationContext;
//	}
//	public void setApplicationContext(ApplicationContext applicationContext) {
//		this.applicationContext = applicationContext;
//	}

	@RequestMapping(value = "/logout.html")
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
//		SecurityContext sc = (SecurityContext) (SecurityContextHolder.getContext());
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
		
    	deleteCookie(response, "tenantID");
    	deleteCookie(response, "client");
    	deleteCookie(response, "token");
    	deleteCookie(response, "JSESSIONID");
        response.setStatus(HttpServletResponse.SC_OK);
		
        System.out.println("#######*******Logout successful for user" + (SecurityContextHolder.getContext().getAuthentication() != null ? SecurityContextHolder.getContext().getAuthentication().getName() : "unknown"));
		return new ModelAndView(new RedirectView("clients.html"));
		//return new ModelAndView("index");
	}
	
	private void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/Requests");
        response.addCookie(cookie);
    }
}
