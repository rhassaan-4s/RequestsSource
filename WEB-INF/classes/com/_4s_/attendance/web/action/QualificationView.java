package com._4s_.attendance.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.attendance.model.Qualification;
import com._4s_.attendance.service.AttendanceManager;

@Controller
public class QualificationView {//implements Controller{
	@Autowired
	AttendanceManager attendanceManager;

	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}
	
	@RequestMapping("/qualificationView.html")
	public ModelAndView handleRequest(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {	
		// TODO Auto-generated method stub
		
//		Map model=new HashMap();
		List qualifications=attendanceManager.getObjects(Qualification.class);
		System.out.println("requests.size>>>>>>>>>>"+qualifications.size());
		model.addAttribute("records", qualifications);
		
		return new ModelAndView("qualificationView");
	}
}
