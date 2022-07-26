package com._4s_.attendance.web.action;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.attendance.model.Title;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.model.EmpBasic;
import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;

public class Dashboard implements Controller{
	protected final Log log = LogFactory.getLog(getClass());
	private AttendanceManager attendanceManager;

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
		
		Settings settings = (Settings)attendanceManager.getObject(Settings.class, new Long(1));
//		List titles=attendanceManager.getObjectsOrderedByField(Title.class,"title");
//		System.out.println("requests.size>>>>>>>>>>"+titles.size());
//		model.put("records", titles);
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.HOUR_OF_DAY, 23);
		cal2.set(Calendar.MINUTE, 59);
		cal2.set(Calendar.SECOND, 59);
		
		String contextPath = (String)request.getSession().getAttribute("contextPath");
		log.debug("context Path " + contextPath);
		attendanceManager.setContextPath(contextPath);
		attendanceManager.setSettings(settings);
		Integer attendees = attendanceManager.getNumberOfAttendees(cal1.getTime(), cal2.getTime());
		List workingEmp = attendanceManager.getObjectsByNullParameter(EmpBasic.class, "end_serv");
		
//		List attendeesAndWorkersByDepartment = attendanceManager.getNumberOfAttendeesAndWorkersByDepartment(cal1.getTime(), cal2.getTime());
		Integer currentEmployees = workingEmp.size();
		Integer absence = currentEmployees-attendees;
		log.debug("attend " + attendees + " absence " + absence);
		model.put("attendance",attendees);
		model.put("absence",absence);
		model.put("settings",settings);
//		model.put("attendanceByDepartment", attendeesAndWorkersByDepartment);
		return new ModelAndView("dashboard",model);
	}

}
