package com._4s_.common.web.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.Branch;
import com._4s_.common.service.CommonManager;

public class BranchController extends BaseController {

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
		
		String companyId = request.getParameter("companyId");
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>..companyId ="+companyId);
//		List list= commonManager.getBranchesRelatedByCompany(new Long(companyId));
		List list= commonManager.getObjects(Branch.class);
		HashMap map=new HashMap();
		map.put("branches",list);
		map.put("companyId",companyId);
			
		return new ModelAndView("commonAdminBranchView",map);
		}
}
