package com._4s_.attendance.web.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.attendance.model.TriggerAttendAnalysis;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.model.Month;
import com.ibm.icu.util.Calendar;

public class TriggerAttendAnalysisView implements Controller{
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
		log.debug("handling request");
		Map model=new HashMap();
		TriggerAttendAnalysis trigger = new TriggerAttendAnalysis();
		trigger.setIndate(new Timestamp(System.currentTimeMillis()));
		
		String monthStr = request.getParameter("month");
		String yearStr = request.getParameter("year");
		Integer month = null;
		Integer year = null;
		
		if (monthStr!= null && !monthStr.isEmpty()) {
			month = Integer.parseInt(monthStr);
		}
		if (yearStr!= null && !yearStr.isEmpty()) {
			year = Integer.parseInt(yearStr);
		}
		
		
		List months = attendanceManager.getObjects(Month.class);
		model.put("months", months);
		List years = new ArrayList();
		Integer y = Calendar.getInstance().get(Calendar.YEAR);
		for (int i=10;i>=0;i--) {
			years.add(y-i);
		}
		model.put("years", years);
		
		if (month!=null && year!=null) {
			trigger.setMonth(month);
			trigger.setYear(year);
			model.put("month", month);
			model.put("year", year);
			try {
				attendanceManager.saveObject(trigger);
				model.put("failed", new Boolean(false));
				log.debug("failed false");
			} catch (DataIntegrityViolationException e) {
				// TODO Auto-generated catch block
				model.put("failed", new Boolean(true));
				log.debug("failed true DataIntegrityViolationException");
				//			e.printStackTrace();
			} catch (ConstraintViolationException e3) {
				// TODO Auto-generated catch block
				model.put("failed", new Boolean(true));
				log.debug("failed true ConstraintViolationException");
				//			e3.printStackTrace();
			}  catch (Exception e2) {
				// TODO Auto-generated catch block
				model.put("failed", new Boolean(true));
				log.debug("failed true Exception");
				e2.printStackTrace();
			} finally {
				log.debug("finallyyyyyyyyyyyyy");
				return new ModelAndView("triggerAttendAnalysisView",model);
			}
		} else {
			return new ModelAndView("triggerAttendAnalysisView",model);
		}
		
	}
}
