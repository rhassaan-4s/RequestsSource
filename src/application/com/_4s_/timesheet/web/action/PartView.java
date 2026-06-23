package com._4s_.timesheet.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.timesheet.model.TimesheetTransactionParts;
import com._4s_.timesheet.service.TimesheetManager;

@Controller
public class PartView {//implements Controller{
	@Autowired
	TimesheetManager timesheetManager;

	public TimesheetManager getTimesheetManager() {
		return timesheetManager;
	}

	public void setTimesheetManager(TimesheetManager timesheetManager) {
		this.timesheetManager = timesheetManager;
	}
	@RequestMapping("/partView.html")
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
