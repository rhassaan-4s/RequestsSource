package com._4s_.common.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.Company;
import com._4s_.common.service.CommonManager;

public class CompanyController extends BaseController {

	protected CommonManager commonManager = null;

	public CommonManager getCommonManager() 
	{
		return commonManager;
	}

	public void setCommonManager(CommonManager commonManager)
	{
		this.commonManager = commonManager;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		log.debug("entering 'handleRequest' method...");
			
		return new ModelAndView("commonAdminCompanyView","results",commonManager.getObjects(Company.class));
		}
}
