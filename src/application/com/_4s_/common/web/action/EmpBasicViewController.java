package com._4s_.common.web.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.EmpBasic;
import com._4s_.common.service.CommonManager;

public class EmpBasicViewController extends BaseController {

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
		
		List list= commonManager.getObjectsOrderedByFieldASC(EmpBasic.class,"empCode");
		HashMap map=new HashMap();
		map.put("employees",list);
			
		return new ModelAndView("commonAdminEmpBasicView",map);
		}
}
