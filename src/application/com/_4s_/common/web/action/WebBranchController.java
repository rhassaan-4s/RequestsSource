package com._4s_.common.web.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.WebBranch;
import com._4s_.common.service.CommonManager;

@Controller
public class WebBranchController extends BaseController {
	@Autowired
	protected CommonManager commonManager = null;

	public CommonManager getCommonManager() 
	{
		return commonManager;
	}

	public void setCommonManager(CommonManager commonManager)
	{
		this.commonManager = commonManager;
	}
	
//	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
	@RequestMapping("/webBranchView.html")
	public String handleRequest(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {	
		log.debug("entering 'handleRequest' method...");
		
		String companyId = request.getParameter("companyId");
		
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>..companyId ="+companyId);
//		List list= commonManager.getBranchesRelatedByCompany(new Long(companyId));
		List list= commonManager.getObjects(WebBranch.class);
//		HashMap map=new HashMap();
		model.addAttribute("branches",list);
//		map.put("companyId",companyId);
			
//		return new ModelAndView("webBranchView",map);
		return "webBranchView";
		}
}
