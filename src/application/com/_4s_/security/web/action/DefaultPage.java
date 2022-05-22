package com._4s_.security.web.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.attendance.model.VacRule;
import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.service.CommonManager;
import com._4s_.common.util.HttpReqRespUtils;
import com._4s_.common.web.action.BaseController;
//import com._4s_.gl.model.GLSettings;
//import com._4s_.gl.service.GlManagerImpl;
import com._4s_.i18n.model.MyLocale;
import com._4s_.i18n.service.DefaultLocale;
import com._4s_.i18n.service.LocaleSource;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.security.model.IPAddress;
//import com._4s_.portal.model.Reports;
import com._4s_.security.model.Roles;
import com._4s_.security.model.SecurityApplication;
import com._4s_.security.model.User;
import com._4s_.security.service.MySecurityManager;
import com._4s_.security.service.UsersMap;
import com._4s_.timesheet.model.TimesheetSpecs;

public class DefaultPage extends BaseController {

	private DefaultLocale defaultOne;

	private LocaleSource localeSource = null;

	private UsersMap userMap = null;

	private CommonManager commonManager;



	private MySecurityManager securityManager;

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

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		SecurityContext sc = (SecurityContext) (SecurityContextHolder.getContext());
		log.debug("------------------------------------------username:--- "
				+ sc.getAuthentication().getName());
		String username = sc.getAuthentication().getName();
		log.fatal("ussssssssssssser ---------------------------- >>>>>>>>>>>>>> " + username);
		User user = (User) baseManager.getObjectByParameter(User.class,
				"username", username);

		String defaultPage = null;
		log.debug("----user.getIsEmployee()-----"+user.getIsEmployee());

		String reqId = (String)request.getSession().getAttribute("requestId");
		String requestNumber = (String)request.getSession().getAttribute("requestNumber");

		log.debug("reqId " + reqId + " requestNumber " + requestNumber);

		String currentIP = HttpReqRespUtils.getClientIpAddressIfServletRequestExist(request);
		log.debug("ip address " +currentIP);

		Map model = new HashMap();
		Settings settings = (Settings)securityManager.getObject(Settings.class, new Long(1)); 
		log.debug("settings " + settings);
		String contextPath = request.getSession().getServletContext().getRealPath("/");
		request.getSession().setAttribute("contextPath", contextPath);
//		log.debug("settings.getIpAddressEnabled() " + settings.getIpAddressEnabled());
		if (settings.getIpAddressEnabled()) {
		if (currentIP!= null && !currentIP.isEmpty()) {
			IPAddress ipAdd = null;
			IPAddress ipAddForUser = (IPAddress)securityManager.getObjectByParameter(IPAddress.class, "users", user);
			if (ipAddForUser!=null) {
				log.debug("ipAddForUser " + ipAddForUser.getIp());
			}
//			if (!currentIP.equals("0:0:0:0:0:0:0:1")) {
				ipAdd = securityManager.checkIP(currentIP, user);
				if (ipAdd!=null) {
					log.debug("ipAdd " + ipAdd.getIp());
				}
//			}
			if ((ipAdd==null && ipAddForUser==null) || (ipAdd!=null && ipAddForUser!=null && ipAdd.equals(ipAddForUser)) || (ipAddForUser!=null && ipAddForUser.getIp().equals(currentIP)) || (currentIP.equals("0:0:0:0:0:0:0:1") && ipAddForUser==null)) {
				if (ipAdd==null ) {//&& !currentIP.equals("0:0:0:0:0:0:0:1")
					log.debug("will save the current IP");
					IPAddress ip = new IPAddress();
					ip.setIp(currentIP);
					ip.setUsers(user);
					securityManager.saveObject(ip);
				}
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



				Settings settings1 = (Settings)commonManager.getObjectsOrderedByField(Settings.class,"id").get(0);
				log.debug("username " + settings1.getUsername() + "password " + settings1.getPassword());
				//		int salary_from_day =  requestsApprovalManager.getSalaryFromDay();
				int salary_from_day = settings1.getSalaryFromDay();
				log.fatal("salary_from_day " + salary_from_day);
				int requestsDeadline = settings1.getRequestsDeadline();
				//		glManager.setEmployee(employee);
				request.getSession().setAttribute("employee",employee);
				request.getSession().setAttribute("appName",user.getDefaultApplication().getName());
				request.getSession().setAttribute("currentApplication", user.getDefaultApplication());
				request.getSession().setAttribute("locale", user.getLanguage().getCode());
				request.getSession().setAttribute("activeApplications", securityManager.getApplicationsByUser(user));

				System.out.println("settings - default page controller "+settings1);
				request.getSession().setAttribute("settings",settings1);
				request.getSession().setAttribute("salary_from_day", salary_from_day);
				request.getSession().setAttribute("requestsDeadline", requestsDeadline);
				
				
//				
//				TimesheetSpecs specs = null;
//				List specsList = commonManager.getObjects(TimesheetSpecs.class);
//				String partName1 = null;
//				String partName2 = null;
//				String partName3 = null;
//				log.debug("spec list size " + specsList.size());
//				if (specsList.size()>0) {
//					specs = (TimesheetSpecs)specsList.get(0);
//					log.debug("specs " + specs);
//					log.debug("specs used 1 " + specs.getIs_used1());
//					log.debug("specs used 2 " + specs.getIs_used2());
//					log.debug("specs used 3 " + specs.getIs_used3());
//					if (specs.getIs_used1().equals("1")) {
//						partName1 = specs.getPart1_name();
//					}
//					if (specs.getIs_used2().equals("1")) {
//						partName2 = specs.getPart2_name();
//					}
//					if (specs.getIs_used3().equals("1")) {
//						partName3 = specs.getPart3_name();
//					}
//				}
//				request.getSession().setAttribute("timesheetSpecs", specs);
//				request.getSession().setAttribute("partName1", partName1);
//				request.getSession().setAttribute("partName2", partName2);
//				request.getSession().setAttribute("partName3", partName3);
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
				return new ModelAndView(new RedirectView(defaultPage));
			} else {
				model.put("error", "wrongIPAdd");
				log.debug("model " + model);
				return new ModelAndView(new RedirectView("/Requests/security/login.html"),model);
			}
		} else {
			model.put("error", "NoIPAddFound");
			return new ModelAndView(new RedirectView("/Requests/security/login.html"),model);
		}
		} else {
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

			TimesheetSpecs specs = null;
			List specsList = commonManager.getObjects(TimesheetSpecs.class);
			String partName1 = null;
			String partName2 = null;
			String partName3 = null;
			log.debug("spec list size " + specsList.size());
			if (specsList.size()>0) {
				specs = (TimesheetSpecs)specsList.get(0);
				log.debug("specs " + specs);
				log.debug("specs used 1 " + specs.getIs_used1());
				log.debug("specs used 2 " + specs.getIs_used2());
				log.debug("specs used 3 " + specs.getIs_used3());
				if (specs.getIs_used1().equals("1")) {
					partName1 = specs.getPart1_name();
				}
				if (specs.getIs_used2().equals("1")) {
					partName2 = specs.getPart2_name();
				}
				if (specs.getIs_used3().equals("1")) {
					partName3 = specs.getPart3_name();
				}
			}
			request.getSession().setAttribute("timesheetSpecs", specs);
			request.getSession().setAttribute("partName1", partName1);
			request.getSession().setAttribute("partName2", partName2);
			request.getSession().setAttribute("partName3", partName3);

//			Settings settings = (Settings)commonManager.getObjectsOrderedByField(Settings.class,"id").get(0);
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
			
//			List rules = baseManager.getObjects(VacRule.class);
//			log.debug("##################  vac rules list size " + rules.size());
//			request.getSession().setAttribute("vacRules", rules);
			
			
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
			return new ModelAndView(new RedirectView(defaultPage));
		}
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
