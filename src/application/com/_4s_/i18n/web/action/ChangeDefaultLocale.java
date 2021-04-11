package com._4s_.i18n.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.i18n.model.MyLocale;
import com._4s_.i18n.service.DefaultLocale;
import com._4s_.i18n.service.LocaleSource;
import com._4s_.i18n.service.MessageManager;
import com._4s_.i18n.service.ResourceMapMessageSource;

public class ChangeDefaultLocale extends BaseSimpleFormController {

	private MessageManager mgr = null;

	private LocaleSource localeSource = null;

	private ResourceMapMessageSource cacheMap = null;

	private DefaultLocale defaultOne;

	public DefaultLocale getDefaultOne() {
		return defaultOne;
	}

	public void setDefaultOne(DefaultLocale defaultOne) {
		this.defaultOne = defaultOne;
	}

	public ResourceMapMessageSource getCacheMap() {
		return cacheMap;
	}

	public void setCacheMap(ResourceMapMessageSource cacheMap) {
		this.cacheMap = cacheMap;
	}

	public LocaleSource getLocaleSource() {
		return localeSource;
	}

	public void setLocaleSource(LocaleSource localeSource) {
		this.localeSource = localeSource;
	}

	public MessageManager getMgr() {
		return mgr;
	}

	public void setMgr(MessageManager mgr) {
		this.mgr = mgr;
	}

	
	
	protected Map referenceData(HttpServletRequest arg0, Object arg1,
			Errors arg2) throws Exception {
		// TODO Auto-generated method stub
		List languages = new ArrayList();
		languages = mgr.getObjects(MyLocale.class);
		Map model = new HashMap();
		model.put("languages", languages);
		return model;
	}

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException error)
			throws Exception {
		// TODO Auto-generated method stub

		String selectLanguage = request.getParameter("selectLanguage");
		MyLocale myLocale = mgr.getDefault();
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>request.getParameter(selectLanguage)"
						+ request.getParameter("selectLanguage"));
		MyLocale locale = (MyLocale) mgr.getObjectByParameter(MyLocale.class,
				"id", new Long(selectLanguage));

		log.debug(">>>>>>>>>>>>>..loc " + myLocale);
		if (locale != myLocale) {
			log.debug(">>>>>>>>>>>>>>>.......locale != loc" + locale);
			myLocale.setIsDefault(false);
			locale.setIsDefault(true);
			// mgr.saveObject(locale);
			cacheMap.getCachedResourceMaps().clear();
			log
					.debug(">>>>>>>>>>>>>>>>>>>>>>>cacheMap.getCachedResourceMaps() "
							+ cacheMap.getCachedResourceMaps());
			// locale=defaultOne.getDefaultMyLocale();
			defaultOne.setDefaultMyLocale(locale);
			Map resource = (Map) (cacheMap.getCachedResourceMaps().get(locale));
		}
		request.getSession().setAttribute("locale", locale.getCode());
		return new ModelAndView(new RedirectView("changeDefaultLocale.html"));

	}

	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub

		MyLocale myLocale = mgr.getDefault();
		log.debug(">>>>>>>>>>>>>>>.......My locale in formbackingobject"
				+ myLocale);
		return myLocale;
	}

}
