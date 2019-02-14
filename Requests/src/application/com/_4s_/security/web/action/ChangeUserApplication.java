package com._4s_.security.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.security.model.SecurityApplication;
import com._4s_.security.model.User;
import com._4s_.security.service.MySecurityManager;

public class ChangeUserApplication extends BaseSimpleFormController {

	private MySecurityManager mgr;
	
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException error)
			throws Exception {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>>>>>>>>.......start Onsubmit.......");
		// TODO Auto-generated method stub
		User user = (User) command;
		String selectApplication = request.getParameter("selectApplication");
		SecurityContext sc = (SecurityContext) (SecurityContextHolder.getContext());
		log.debug("------------------------------------------username:--- "
				+ sc.getAuthentication().getName());
		String username = sc.getAuthentication().getName();
		user = (User) baseManager.getObjectByParameter(User.class, "username",
				username);
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>request.getParameter(selectApplication)"
						+ request.getParameter("selectApplication"));
		
		SecurityApplication application = (SecurityApplication) baseManager.getObject(
				SecurityApplication.class, new Long(selectApplication));

		if (application != user.getDefaultApplication()) {
			log.debug(">>>>>>>>>>>>>>>.......application!=defaultApplication"
					+ application);
			user.setDefaultApplication(application);
			baseManager.saveObject(user);
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>.......end Onsubmit.......");
		return new ModelAndView(new RedirectView("changeUserApplication.html"));
	}

	protected Map referenceData(HttpServletRequest request, Object arg1,
			Errors arg2) throws Exception {
		// TODO Auto-generated method stub
		List applications = new ArrayList();
		User user = (User)request.getSession().getAttribute("user");
//		applications = baseManager.getObjectsByParameter(SecurityApplication.class, "is_active", new Boolean(true));
		applications = mgr.getApplicationsByUser(user);
		Map model = new HashMap();
		model.put("applications", applications);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>.......end ReferenceData.......");
		return model;
	}

	protected Object formBackingObject(HttpServletRequest arg0)
			throws Exception {
		// TODO Auto-generated method stub
		SecurityContext sc = (SecurityContext) (SecurityContextHolder.getContext());
		log.debug("------------------------------------------username:--- "
				+ sc.getAuthentication().getName());
		String username = sc.getAuthentication().getName();

		User user = (User) baseManager.getObjectByParameter(User.class,
				"username", username);
		log.debug(">>>>>>>>>>>>>...user " + user);
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>.......end fromBackingObject............<<<<<<<<<<<<<<<<<<<<<");
		return user;
	}

	public MySecurityManager getMgr() {
		return mgr;
	}

	public void setMgr(MySecurityManager mgr) {
		this.mgr = mgr;
	}

}
