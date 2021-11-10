package com._4s_.common.web.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.Clients;
import com._4s_.common.service.CommonManager;

public class ClientsController extends BaseController {

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
		
		String clientId = request.getParameter("clientId");
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>..clientId ="+clientId);
		List list= commonManager.getObjects(Clients.class);
		HashMap map=new HashMap();
		map.put("clients",list);
		map.put("clientId",clientId);
			
		return new ModelAndView("commonAdminClientsView",map);
		}
}
