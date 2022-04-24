package com._4s_.timesheet.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.requestsApproval.model.Requests;
import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.service.TimesheetManager;

public class ActivityView implements Controller{
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
		List activities=timesheetManager.getObjects(TimesheetActivity.class);
		System.out.println("requests.size>>>>>>>>>>"+activities.size());
		model.put("records", activities);
		
		return new ModelAndView("activityView",model);
	}
}
