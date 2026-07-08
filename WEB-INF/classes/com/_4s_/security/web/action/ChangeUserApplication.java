package com._4s_.security.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.web.action.BaseFormControllerInterface;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.security.model.SecurityApplication;
import com._4s_.security.model.User;
import com._4s_.security.service.MySecurityManager;

@Controller
@RequestMapping("/changeUserApplication.html")
public class ChangeUserApplication extends BaseSimpleFormController  implements BaseFormControllerInterface{

	@Autowired
	private MySecurityManager mgr;
	
	@RequestMapping(method = RequestMethod.POST)
	@Override
	public ModelAndView processSubmit(
			@ModelAttribute("role") Object command,HttpServletRequest request, HttpServletResponse response,
			BindingResult result, SessionStatus status) {

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

	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request,@ModelAttribute("model") Object command)
	{
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

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request, HttpServletResponse response){

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
		model.addAttribute(user);
		return "changeUserApplication";
	}

	public MySecurityManager getMgr() {
		return mgr;
	}

	public void setMgr(MySecurityManager mgr) {
		this.mgr = mgr;
	}

}
