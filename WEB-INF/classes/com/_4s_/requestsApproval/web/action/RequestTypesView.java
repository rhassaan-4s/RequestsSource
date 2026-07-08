package com._4s_.requestsApproval.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.requestsApproval.model.Requests;
import com._4s_.requestsApproval.service.RequestsApprovalManager;

public class RequestTypesView implements Controller{
	RequestsApprovalManager requestsApprovalManager;

	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		
		Map model=new HashMap();
		List requests=requestsApprovalManager.getObjects(Requests.class);
		System.out.println("requests.size>>>>>>>>>>"+requests.size());
		model.put("records", requests);
		
		return new ModelAndView("requestTypesView",model);
	}
	
}
