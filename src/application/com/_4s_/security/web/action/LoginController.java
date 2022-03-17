package com._4s_.security.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;

import com._4s_.security.dao.MySecurityDAO;

@Controller
public class LoginController {
	private final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private MySecurityDAO securityDAO;
	
	public MySecurityDAO getSecurityDAO() {
		return securityDAO;
	}

	public void setSecurityDAO(MySecurityDAO securityDAO) {
		this.securityDAO = securityDAO;
	}

	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
		log.debug("get login");
		return "login.html";
	}
	
	@RequestMapping(value = "/defaultPage.html", method = RequestMethod.POST)
    public String loginPage(@RequestParam(value = "error", required = false) String error, 
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model,
                            HttpServletRequest request) {
		log.debug("post login");
//		Map model = new HashMap();

//		log.debug("param: "+request.getParameter("param"));
//		log.debug("login_error: "+request.getParameter("login_error"));
//		log.debug("failed: "+request.getParameter("failed"));
//		log.debug("ParameterMap: "+request.getParameterMap());
//		log.debug("request.getAttributeNames(): "+request.getAttributeNames());
//		log.debug("request.getSession().getAttribute(ACEGI_SECURITY_LAST_USERNAME): "+request.getSession().getAttribute("ACEGI_SECURITY_LAST_USERNAME"));
//		
		securityDAO.login();
		String reqId = request.getParameter("requestId");
		String requestNumber = request.getParameter("requestNumber");
		log.debug("reqId " + reqId);
		request.getSession().setAttribute("requestId", reqId);
		request.getSession().setAttribute("requestNumber", requestNumber);
		
		Exception lastException = (Exception) request.getSession().getAttribute("ACEGI_SECURITY_LAST_EXCEPTION");
		if(lastException != null ){
			log.debug("exception " + lastException.getClass().getCanonicalName());
			if (lastException instanceof AuthenticationException ) {//concurrent login exception
				model.addAttribute("concurrentLoginException" , "true");
			}else {
				model.addAttribute("invalidUsernameOrPassword" , "true");
			}
		}
		
		log.debug("request.getSession().getAttribute(ACEGI_SECURITY_LAST_EXCEPTION): "+lastException);
		
		request.getSession().removeAttribute("ACEGI_SECURITY_LAST_EXCEPTION");
		request.getSession().removeAttribute("ACEGI_SECURITY_LAST_USERNAME");
		
		return "defaultPage.html";

	}
//	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		Map model = new HashMap();
//
//		log.debug("param: "+request.getParameter("param"));
//		log.debug("login_error: "+request.getParameter("login_error"));
//		log.debug("failed: "+request.getParameter("failed"));
//		log.debug("ParameterMap: "+request.getParameterMap());
//		log.debug("request.getAttributeNames(): "+request.getAttributeNames());
//		log.debug("request.getSession().getAttribute(ACEGI_SECURITY_LAST_USERNAME): "+request.getSession().getAttribute("ACEGI_SECURITY_LAST_USERNAME"));
//		
//		String reqId = request.getParameter("requestId");
//		String requestNumber = request.getParameter("requestNumber");
//		log.debug("reqId " + reqId);
//		request.getSession().setAttribute("requestId", reqId);
//		request.getSession().setAttribute("requestNumber", requestNumber);
//		
//		Exception lastException = (Exception) request.getSession().getAttribute("ACEGI_SECURITY_LAST_EXCEPTION");
//		if(lastException != null ){
//			log.debug("exception " + lastException.getClass().getCanonicalName());
//			if (lastException instanceof AuthenticationException ) {//concurrent login exception
//				model.put("concurrentLoginException" , "true");
//			}else {
//				model.put("invalidUsernameOrPassword" , "true");
//			}
//		}
//		
//		log.debug("request.getSession().getAttribute(ACEGI_SECURITY_LAST_EXCEPTION): "+lastException);
//		
//		request.getSession().removeAttribute("ACEGI_SECURITY_LAST_EXCEPTION");
//		request.getSession().removeAttribute("ACEGI_SECURITY_LAST_USERNAME");
//		
//		return new ModelAndView("login" , model);
//	}
}

