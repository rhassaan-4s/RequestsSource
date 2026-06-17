package com._4s_.i18n.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@Autowired
	@Qualifier("messageSource")
	private ResourceMapMessageSource cacheMap = null;

	private DefaultLocale defaultLocale;

	public DefaultLocale getDefaultLocale() {
		return defaultLocale;
	}

	public void setDefaultLocale(DefaultLocale defaultLocale) {
		this.defaultLocale = defaultLocale;
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

	
	
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) {
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
			defaultLocale.setDefaultMyLocale(locale);
			Map resource = (Map) (cacheMap.getCachedResourceMaps().get(locale));
		}
		request.getSession().setAttribute("locale", locale.getCode());
		return new ModelAndView(new RedirectView("changeDefaultLocale.html"));

	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request){
		
		// TODO Auto-generated method stub

		MyLocale myLocale = mgr.getDefault();
		log.debug(">>>>>>>>>>>>>>>.......My locale in formbackingobject"
				+ myLocale);
		model.addAttribute("myLocale", myLocale);
		return "changeDefaultLocale";
	}

}
