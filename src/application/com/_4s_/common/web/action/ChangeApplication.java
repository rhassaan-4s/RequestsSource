package com._4s_.common.web.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.service.CommonManager;
//import com._4s_.gl.model.GLSettings;
//import com._4s_.portal.model.Reports;
import com._4s_.security.model.Roles;
import com._4s_.security.model.SecurityApplication;
import com._4s_.security.model.User;

@Controller
@RequestMapping("/changeApplication.html")
public class ChangeApplication  extends CommonController {
	@Autowired
	private CommonManager commonManager;

	private final Log log = LogFactory.getLog(getClass());

	public CommonManager getCommonManager() {
		return commonManager;
	}

	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
//		 log.debug("handleRequest");
		String application = request.getParameter("application");
		 log.debug("application " +application);
		System.out.println("CHANGEAPPLICATION::::application " + application);
		String url = "";
		SecurityApplication securityApplication = null;

		if (application.equals("ADMINISTRATION")) {
			securityApplication = (SecurityApplication) commonManager
					.getObject(SecurityApplication.class, new Long(3));
			request.getSession().setAttribute("appName", "ADMINISTRATION");
		} else if (application.equals("HR")) {
			securityApplication = (SecurityApplication) commonManager
					.getObject(SecurityApplication.class, new Long(6));
			request.getSession().setAttribute("appName", "HR");
		} else if (application.equals("requestsApproval")) {
			securityApplication = (SecurityApplication) commonManager
					.getObject(SecurityApplication.class, new Long(12));
			request.getSession().setAttribute("appName", "requestsApproval");
		} else if (application.equals("timesheet")) {
			System.out.println("CHANGEAPPLICATION::::############Timesheet");
			securityApplication = (SecurityApplication) commonManager
					.getObject(SecurityApplication.class, new Long(13));
			request.getSession().setAttribute("appName", "timesheet");
		} else if (application.equals("attendance")) {
			System.out.println("CHANGEAPPLICATION::::############Attendance");
			securityApplication = (SecurityApplication) commonManager
					.getObject(SecurityApplication.class, new Long(14));
			request.getSession().setAttribute("appName", "attendance");
		}

		System.out.println("current application " + securityApplication.getDefaultPage());
		request.getSession().setAttribute("currentApplication",
				securityApplication);

		// url =securityApplication.getDefaultPage();
		String defaultPage = null;
		User user = (User) request.getSession().getAttribute("user");
		// log.debug("user "+user);
		if (securityApplication != null && user != null) {
			System.out.println("user " + user);
			// defaultPage = user.getDefaultApplication().getDefaultPage();
			List userRoles = user.getRoles();
			List applicationRoles = securityApplication.getRoles();
			Iterator itr = applicationRoles.iterator();
			while (itr.hasNext()) {
				System.out.println("user " + user);
				Roles role = (Roles) itr.next();
				System.out.println("role " + role);
				if (userRoles.contains(role)) {
					defaultPage = role.getDefaultPage();
//					System.out.println("default page " + defaultPage);
//					String[] defPage = defaultPage.split("/");
//					for (int i =0; i<defPage.length; i++) {
//						if (defPage[i].contains(".html")) {
//							defaultPage = defPage[i];
//							break;
//						}
//					}
//					log.debug("default page " + defaultPage);
//					defPage = defaultPage.split(".html");
//					for (int i =0; i<defPage.length; i++) {
//							System.out.println(defPage[i]);
//					}
//					defaultPage = defPage[0];
					break;
				}
			}
		} else {
			defaultPage = "/Requests/security/login.html";
//			defaultPage = "login";
		}
		url = defaultPage;

		log.debug("url " + url);

		// // Reports Portal ////////////////////////////////////////
		Roles role = null;

		Iterator itr = user.getRoles().iterator();
		while (itr.hasNext()) {
			Roles itr_role = (Roles) itr.next();
			System.out.println("role " + itr_role);
			log.debug("---roleId---"+itr_role.getId());
			
			if (itr_role.getApplication().getId().equals(
					securityApplication.getId())) {
				System.out.println("equal security application");
				role = itr_role;
			}
		}
		Object o = request.getSession().getAttribute("reports");
		System.out.println("object " + o);
		System.out.println("role " + role);
		// commonManager.initializeCollection(role.getReports());
		// System.out.println("reports " + role.getReports());
		if (o != null) {
			request.getSession().removeAttribute("reports");
		}
//		request.getSession().setAttribute(
//				"reports",
//				commonManager
//						.getObjectsByParameter(Reports.class, "role", role));

		// ////////////////////////////////////////////////////////

		// log.debug("url "+url);

		return new ModelAndView(new RedirectView(url));
//		return url;
	}

}

//if (application.equals("STORE")) {
//securityApplication = (SecurityApplication) commonManager
//		.getObject(SecurityApplication.class, new Long(1));
//request.getSession().setAttribute("appName", "STORE");
//if (request.getSession().getAttribute("settings") != null) {
//	request.getSession().removeAttribute("settings");
//}
//} else 

//else if (application.equals("GL")) {
//securityApplication = (SecurityApplication) commonManager
//	.getObject(SecurityApplication.class, new Long(5));
//// Throwing gl settings in
//// session/////////////////////////////////////////////
//List settings = commonManager.getObjects(GLSettings.class);
//request.getSession().setAttribute("settings",
//	((GLSettings) (settings.get(0))));
//// ///////////////////////////////////////////////////////////////////////////
//request.getSession().setAttribute("appName", "GL");
//} else if (application.equals("ASSETS")) {
//securityApplication = (SecurityApplication) commonManager
//	.getObject(SecurityApplication.class, new Long(8));
//request.getSession().setAttribute("appName", "ASSETS");
//if (request.getSession().getAttribute("settings") != null) {
//request.getSession().removeAttribute("settings");
//}
//}
//else if (application.equals("HR")) {
//securityApplication = (SecurityApplication) commonManager
//	.getObject(SecurityApplication.class, new Long(6));
//request.getSession().setAttribute("appName", "HR");
//}
//else if (application.equals("ATTENDANCE")) {
//securityApplication = (SecurityApplication) commonManager
//	.getObject(SecurityApplication.class, new Long(10));
//request.getSession().setAttribute("appName", "ATTENDANCE");
//}
//else if (application.equals("PORTAL")) {
//securityApplication = (SecurityApplication) commonManager
//	.getObject(SecurityApplication.class, new Long(9));
//request.getSession().setAttribute("appName", "PORTAL");
//if (request.getSession().getAttribute("settings") != null) {
//request.getSession().removeAttribute("settings");
//}
//}

