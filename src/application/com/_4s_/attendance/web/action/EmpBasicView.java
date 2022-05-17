package com._4s_.attendance.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.common.model.EmpBasic;
import com._4s_.attendance.service.AttendanceManager;

public class EmpBasicView implements Controller{
	AttendanceManager attendanceManager;

	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		
		Map model=new HashMap();
		List employees=attendanceManager.getObjectsOrderedByField(EmpBasic.class, "empCode");
		System.out.println("requests.size>>>>>>>>>>"+employees.size());
		model.put("records", employees);
		
		return new ModelAndView("empBasicView",model);
	}
}
