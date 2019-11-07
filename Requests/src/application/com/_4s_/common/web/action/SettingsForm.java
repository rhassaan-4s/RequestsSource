package com._4s_.common.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Settings;
import com._4s_.common.service.BaseManager;

public class SettingsForm extends BaseSimpleFormController{

	protected BaseManager baseManager = null;

	public BaseManager getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}

	
	
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		
		Settings settings = (Settings)baseManager.getObject(Settings.class , new Long(1));
		
		if(request.getMethod().equals("POST")){
		
			String annualVacBalDaysEnabled = request.getParameter("annualVacBalDaysEnabled");
			if(annualVacBalDaysEnabled == null || annualVacBalDaysEnabled.equals("")){
				settings.setAnnualVacBalDaysEnabled(false);
			}
		}
		return settings;

	}

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse arg1, Object cmd, BindException arg3) throws Exception {
		Settings  settings = (Settings)cmd;
		log.error(">>>> start of onSubmit() ");
		
		
		baseManager.saveObject(settings);
		
				
		log.error(">>>> end of onSubmit() ");
		return new ModelAndView(new RedirectView("settingsForm.html"));
		
	}
	
}
