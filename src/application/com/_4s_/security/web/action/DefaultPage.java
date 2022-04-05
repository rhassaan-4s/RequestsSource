package com._4s_.security.web.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.service.CommonManager;
import com._4s_.common.web.action.BaseController;
//import com._4s_.gl.model.GLSettings;
//import com._4s_.gl.service.GlManagerImpl;
import com._4s_.i18n.model.MyLocale;
import com._4s_.i18n.service.DefaultLocale;
import com._4s_.i18n.service.LocaleSource;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
//import com._4s_.portal.model.Reports;
import com._4s_.security.model.Roles;
import com._4s_.security.model.SecurityApplication;
import com._4s_.security.model.User;
import com._4s_.security.service.MySecurityManager;
import com._4s_.security.service.UsersMap;

@Controller
public class DefaultPage {
	private final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private DefaultLocale defaultOne;
	@Autowired
	private LocaleSource localeSource = null;
	@Autowired
	private UsersMap userMap = null;
	@Autowired
	private CommonManager commonManager;
	@Autowired
	private MySecurityManager securityManager;
	@Autowired
	RequestsApprovalManager requestsApprovalManager;

	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}
	
	
	public CommonManager getCommonManager() {
		return commonManager;
	}

	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}

	public UsersMap getUserMap() {
		return userMap;
	}

	public void setUserMap(UsersMap userMap) {
		this.userMap = userMap;
	}

	public LocaleSource getLocaleSource() {
		return localeSource;
	}

	public void setLocaleSource(LocaleSource localeSource) {
		this.localeSource = localeSource;
	}

	public DefaultLocale getDefaultOne() {
		return defaultOne;
	}

	public void setDefaultOne(DefaultLocale defaultOne) {
		this.defaultOne = defaultOne;
	}

	@GetMapping(value = "/defaultPage.html")
    public RedirectView defaultPage(HttpServletRequest request) {
		// TODO Auto-generated method stub
		SecurityContext sc = (SecurityContext) (SecurityContextHolder.getContext());
		log.debug("------------------------------------------username:--- "
				+ sc.getAuthentication().getName());
		String username = sc.getAuthentication().getName();
		log.fatal("ussssssssssssser ---------------------------- >>>>>>>>>>>>>> " + username);
		User user = (User) commonManager.getObjectByParameter(User.class,
				"username", username);
		
		String defaultPage = null;
		log.debug("----user.getIsEmployee()-----"+user.getIsEmployee());
		
		String reqId = (String)request.getSession().getAttribute("requestId");
		String requestNumber = (String)request.getSession().getAttribute("requestNumber");
		
		log.debug("reqId " + reqId + " requestNumber " + requestNumber);
		
		if (reqId != null && !reqId.equals("")) {
			log.debug("will go to reports");
			defaultPage = "requestsApprovalForm.html?requestType="+reqId+"&reqId="+requestNumber;
			defaultPage = "/Requests/requestsApproval/" + defaultPage;
		} else if(user.getIsEmployee()!=true){
			defaultPage="";
		}
		
		if (user.getDefaultApplication() != null && (defaultPage == null || defaultPage.isEmpty())) {
			log.debug("-----default not null-----");
			//defaultPage = user.getDefaultApplication().getDefaultPage();
			List userRoles = user.getRoles();
			List defaultApplicationRoles = user.getDefaultApplication().getRoles();
			Iterator itr = defaultApplicationRoles.iterator();
			while(itr.hasNext()){
				Roles role = (Roles)itr.next();
				if(userRoles.contains(role)){
					defaultPage = role.getDefaultPage();
					if(defaultPage != null && !defaultPage.equals("")){
						break;
					}
				}
			}
		}
		

		MyLocale myLocale = user.getLanguage();
		myLocale.getCountry();
		log.debug("locale " + myLocale.getLanguage());
		if (myLocale != null) {
			userMap.getUsers().put(username,myLocale);
		}
//		defaultPage.replaceAll("4s", "Requests");
		
		if (defaultPage == null || defaultPage.equals("")) {
			defaultPage = "userDetails.html";
			defaultPage = "/Requests/" + defaultPage.substring(10);
		} else {
			defaultPage = "/Requests/" + defaultPage.substring(10);
		}
		log.debug("----default----"+defaultPage);
		
		request.getSession().removeAttribute("requestId");
		request.getSession().removeAttribute("requestNumber");
		
		log.debug("-----"+defaultPage);
		// important properties that should be present in the session
		request.getSession().setAttribute("loginDate",new Date());
		request.getSession().setAttribute("user",user);
		Employee employee = commonManager.getEmployeeByUser(user.getId());
		
		
		
		Settings settings = (Settings)commonManager.getObjectsOrderedByField(Settings.class,"id").get(0);
		log.debug("username " + settings.getUsername() + "password " + settings.getPassword());
//		int salary_from_day =  requestsApprovalManager.getSalaryFromDay();
		int salary_from_day = settings.getSalaryFromDay();
		log.fatal("salary_from_day " + salary_from_day);
		int requestsDeadline = settings.getRequestsDeadline();
//		glManager.setEmployee(employee);
		request.getSession().setAttribute("employee",employee);
		request.getSession().setAttribute("appName",user.getDefaultApplication().getName());
		request.getSession().setAttribute("currentApplication", user.getDefaultApplication());
		request.getSession().setAttribute("locale", user.getLanguage().getCode());
		request.getSession().setAttribute("activeApplications", securityManager.getApplicationsByUser(user));
		
		System.out.println("settings - default page controller "+settings);
		request.getSession().setAttribute("settings",settings);
		request.getSession().setAttribute("salary_from_day", salary_from_day);
		request.getSession().setAttribute("requestsDeadline", requestsDeadline);
		
//		
//		if (user.getDefaultApplication().getName().equals("GL")) {
//			//Throwing gl settings in session/////////////////////////////////////////////
//			List settings = commonManager.getObjects(GLSettings.class);
//			request.getSession().setAttribute("settings", ((GLSettings)(settings.get(0))));
//			/////////////////////////////////////////////////////////////////////////////
//		}
		
		if (user.getDefaultApplication() != null) {
			//// Reports Portal ////////////////////////////////////////
			Roles role = null;

			Iterator itr = user.getRoles().iterator();
			while (itr.hasNext()) {
				Roles itr_role = (Roles) itr.next();
				if (itr_role.getApplication().getId().equals(
						user.getDefaultApplication().getId())) {
					role = itr_role;
				}
			}
			Object o = request.getSession().getAttribute("reports");
			if (o != null) {
				request.getSession().removeAttribute("reports");
			}
//			request.getSession().setAttribute("reports",
//					commonManager.getObjectsByParameter(Reports.class, "role", role));
			//////////////////////////////////////////////////////////
		}
		//Putting applications in session
		Object purchasing = null;
		try {
			purchasing = securityManager.getObject(SecurityApplication.class, new Long(6));
		} catch (Exception e) {
			purchasing = null;
		}
		
		if(purchasing != null) {
			request.getSession().setAttribute("purchasing", (SecurityApplication)purchasing);
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>> appName "+user.getDefaultApplication().getName());
//		return new ModelAndView(new RedirectView(defaultPage));
		RedirectView rView = new RedirectView();
		rView.setUrl(defaultPage);
		return rView;
		
	}

//	public GlManagerImpl getGlManager() {
//		return glManager;
//	}
//
//	public void setGlManager(GlManagerImpl glManager) {
//		this.glManager = glManager;
//	}

	public MySecurityManager getSecurityManager() {
		return securityManager;
	}

	public void setSecurityManager(MySecurityManager securityManager) {
		this.securityManager = securityManager;
	}

}
