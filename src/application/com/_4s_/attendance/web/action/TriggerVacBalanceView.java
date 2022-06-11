package com._4s_.attendance.web.action;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.attendance.model.TriggerVacBalance;
import com._4s_.attendance.service.AttendanceManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;

public class TriggerVacBalanceView implements Controller{
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
		TriggerVacBalance trigger = new TriggerVacBalance();
		trigger.setIndate(new Timestamp(System.currentTimeMillis()));
		try {
			attendanceManager.saveObject(trigger);
			model.put("failed", new Boolean(false));
		} catch (DataIntegrityViolationException e) {
			// TODO Auto-generated catch block
			model.put("failed", new Boolean(true));
//			e.printStackTrace();
		} catch (ConstraintViolationException e3) {
			// TODO Auto-generated catch block
			model.put("failed", new Boolean(true));
//			e3.printStackTrace();
		}  catch (Exception e2) {
			// TODO Auto-generated catch block
			model.put("failed", new Boolean(true));
//			e2.printStackTrace();
		} finally {
			return new ModelAndView("triggerVacBalanceView",model);
		}
		
	}
}
