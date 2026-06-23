package com._4s_.timesheet.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.service.TimesheetManager;

@Controller
public class ActivityView {//	 implements Controller{
	@Autowired
	TimesheetManager timesheetManager;

	public TimesheetManager getTimesheetManager() {
		return timesheetManager;
	}

	public void setTimesheetManager(TimesheetManager timesheetManager) {
		this.timesheetManager = timesheetManager;
	}
	
	@RequestMapping("/activityView.html")
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
