package com._4s_.security.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


public class LoginController implements Controller {
	private final Log log = LogFactory.getLog(getClass());
	
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map model = new HashMap();

		log.debug("param: "+request.getParameter("param"));
		log.debug("login_error: "+request.getParameter("login_error"));
		log.debug("failed: "+request.getParameter("failed"));
		log.debug("ParameterMap: "+request.getParameterMap());
		log.debug("request.getAttributeNames(): "+request.getAttributeNames());
		log.debug("request.getSession().getAttribute(ACEGI_SECURITY_LAST_USERNAME): "+request.getSession().getAttribute("ACEGI_SECURITY_LAST_USERNAME"));
		
		String reqId = request.getParameter("requestId");
		String requestNumber = request.getParameter("requestNumber");
		log.debug("reqId " + reqId);
		request.getSession().setAttribute("requestId", reqId);
		request.getSession().setAttribute("requestNumber", requestNumber);
		
		Exception lastException = (Exception) request.getSession().getAttribute("ACEGI_SECURITY_LAST_EXCEPTION");
		if(lastException != null ){
			log.debug("exception " + lastException.getClass().getCanonicalName());
			if (lastException instanceof AuthenticationException ) {//concurrent login exception
				model.put("concurrentLoginException" , "true");
			}else {
				model.put("invalidUsernameOrPassword" , "true");
			}
		}
		
		log.debug("request.getSession().getAttribute(ACEGI_SECURITY_LAST_EXCEPTION): "+lastException);
		
		request.getSession().removeAttribute("ACEGI_SECURITY_LAST_EXCEPTION");
		request.getSession().removeAttribute("ACEGI_SECURITY_LAST_USERNAME");
		
		return new ModelAndView("login" , model);
	}
}

