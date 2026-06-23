package com._4s_.attendance.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.attendance.model.WorkPeriod;
import com._4s_.attendance.service.AttendanceManager;

@Controller
public class WorkPeriodView {// implements Controller{
	@Autowired
	AttendanceManager attendanceManager;

	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}
	
	@RequestMapping("/workperiodView.html")
	public ModelAndView handleRequest(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {	
		
//		Map model=new HashMap();
		List periods=attendanceManager.getObjectsOrderedByField(WorkPeriod.class,"workperiods");
		System.out.println("requests.size>>>>>>>>>>"+periods.size());
		model.addAttribute("records", periods);
		
		return new ModelAndView("workperiodView");
	}
}
