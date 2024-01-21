package com._4s_.common.web.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.Clients;
import com._4s_.common.service.CommonManager;

@Controller
public class ClientsController {// extends BaseController {

	protected final Log log = LogFactory.getLog(getClass());
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
	@RequestMapping("/commonAdminClientsView.html")
	public String handleRequest(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {	
		log.debug("entering 'handleRequest' method...");
		
		String clientId = request.getParameter("clientId");
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>..clientId ="+clientId);
		List list= commonManager.getObjects(Clients.class);
//		HashMap map=new HashMap();
		model.addAttribute("clients",list);
		model.addAttribute("clientId",clientId);
			
//		return new ModelAndView("commonAdminClientsView",map);
		return "commonAdminClientsView";
		}
}
