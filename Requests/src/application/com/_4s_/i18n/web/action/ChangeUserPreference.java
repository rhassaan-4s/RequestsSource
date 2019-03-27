package com._4s_.i18n.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.i18n.model.MyLocale;
import com._4s_.security.model.User;
import com._4s_.security.service.UsersMap;

public class ChangeUserPreference extends BaseSimpleFormController {
	
	private UsersMap userMap = null;

	public UsersMap getUserMap() {
		return userMap;
	}

	public void setUserMap(UsersMap userMap) {
		this.userMap = userMap;
	}

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException error)
			throws Exception {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>.......start Onsubmit.......");
		// TODO Auto-generated method stub
		User user = (User) command;
		String selectLanguage = request.getParameter("selectLanguage");
		SecurityContext sc = (SecurityContext) (SecurityContextHolder.getContext());
		log.debug("------------------------------------------username:--- "
				+ sc.getAuthentication().getName());
		String username = sc.getAuthentication().getName();
		user = (User) baseManager.getObjectByParameter(User.class, "username",
				username);
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>request.getParameter(selectLanguage)"
						+ request.getParameter("selectLanguage"));
		MyLocale locale = (MyLocale) baseManager.getObjectByParameter(
				MyLocale.class, "id", new Long(selectLanguage));

		if (locale != user.getLanguage()) {
			log.debug(">>>>>>>>>>>>>>>.......locale != loc" + locale);
			user.setLanguage(locale);
			baseManager.saveObject(user);
		}
		if (locale != null) {
			userMap.getUsers().put(username,locale);
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>.......end Onsubmit.......");
		request.getSession().setAttribute("locale", user.getLanguage().getCode());
		return new ModelAndView(new RedirectView("changeUserLanguage.html"));
	}

	protected Map referenceData(HttpServletRequest request, Object command,
			Errors error) throws Exception {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>.......start referenceData.......");
		// TODO Auto-generated method stub
		List languages = new ArrayList();
		languages = baseManager.getObjects(MyLocale.class);
		Map model = new HashMap();
		model.put("languages", languages);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>.......end ReferenceData.......");
		return model;
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>.......start fromBackingObject............<<<<<<<<<<<<<<<<<<<<<");

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
}
