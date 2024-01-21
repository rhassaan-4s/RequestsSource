package com._4s_.attendance.web.action;

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

import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.model.EmpBasic;

@Controller
public class EmpBasicView {// implements Controller{
	@Autowired
	AttendanceManager attendanceManager;

	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}
	
	@RequestMapping("/empBasicView.html")
	public ModelAndView handleRequest(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {	
//		Map model=new HashMap();
		List employees=attendanceManager.getObjectsOrderedByField(EmpBasic.class, "empCode");
		System.out.println("requests.size>>>>>>>>>>"+employees.size());
		model.addAttribute("records", employees);
		
		return new ModelAndView("empBasicView");
	}
}
