package com._4s_.i18n.web.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.web.action.BaseController;
import com._4s_.i18n.model.MyLocale;
import com._4s_.security.model.User;
import com._4s_.security.web.action.UserController;

public class LocalesController extends BaseController implements Comparator {

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		List locales = new ArrayList(baseManager.getObjects(MyLocale.class));
		log.debug(">>>>>>>>>>>>>>>>>locales " + locales);
		Collections.sort(locales, new LocalesController());
		return new ModelAndView("allLocales", "locales", locales);
	}

	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		MyLocale u1 = (MyLocale) o1;
		MyLocale u2 = (MyLocale) o2;
		return u1.getLanguage().compareTo(u2.getLanguage());
	}
}
