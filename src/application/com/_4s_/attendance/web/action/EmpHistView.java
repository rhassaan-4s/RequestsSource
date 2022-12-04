package com._4s_.attendance.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.attendance.model.EmpHist;
import com._4s_.attendance.model.Title;
import com._4s_.attendance.service.AttendanceManager;

public class EmpHistView implements Controller{
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
		List hist=attendanceManager.getObjectsOrderedByField(EmpHist.class,"empCode");
		System.out.println("requests.size>>>>>>>>>>"+hist.size());
		model.put("records", hist);
		
		return new ModelAndView("empHistView",model);
	}
}
