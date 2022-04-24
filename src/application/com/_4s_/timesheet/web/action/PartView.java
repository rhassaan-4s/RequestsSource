package com._4s_.timesheet.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.requestsApproval.model.Requests;
import com._4s_.restServices.json.TimesheetPartWrapper;
import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.model.TimesheetSpecs;
import com._4s_.timesheet.model.TimesheetTransactionParts;
import com._4s_.timesheet.service.TimesheetManager;

public class PartView implements Controller{
	TimesheetManager timesheetManager;

	public TimesheetManager getTimesheetManager() {
		return timesheetManager;
	}

	public void setTimesheetManager(TimesheetManager timesheetManager) {
		this.timesheetManager = timesheetManager;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		
		Map model=new HashMap();
		Object part = request.getParameter("partNo");
		String partNo = null;
		if (part!=null) {
			partNo = (String)part;
		}
		List parts = new ArrayList();
		if (partNo!=null && !partNo.isEmpty()) {
			parts=timesheetManager.getObjectsByParameter(TimesheetTransactionParts.class,"part_no",new Short(partNo));
			request.getSession().setAttribute("partNo", partNo);
		}
		TimesheetTransactionParts nullPart = (TimesheetTransactionParts)timesheetManager.getObjectByParameter(TimesheetTransactionParts.class, "code", "9999999999");
		parts.remove(nullPart);
		System.out.println("requests.size>>>>>>>>>>"+parts.size());
		model.put("records", parts);
		model.put("partNo", partNo);
		return new ModelAndView("partView",model);
	}
}
