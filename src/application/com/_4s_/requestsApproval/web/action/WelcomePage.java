package com._4s_.requestsApproval.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.requestsApproval.model.CompanyDetails;
import com._4s_.requestsApproval.service.RequestsApprovalManager;

@Controller	
public class WelcomePage{
	@Autowired
	RequestsApprovalManager requestsApprovalManager;

	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}
	
	@RequestMapping(value = "/welcome.html")
	public String handleRequest(Model model,HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		List requests=requestsApprovalManager.getObjects(CompanyDetails.class);
		System.out.println("requests.size>>>>>>>>>>"+requests.size());
		model.addAttribute("records", requests);
		
		return "welcomePage";
	}
	
}
