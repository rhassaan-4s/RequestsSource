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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.attendance.model.TriggerAttendAnalysis;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.model.Month;
import com.ibm.icu.util.Calendar;

@Controller
public class TriggerAttendAnalysisView {// implements Controller{
	 protected final Log log = LogFactory.getLog(getClass());
	 @Autowired
	 private AttendanceManager attendanceManager;

	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}
	
	@RequestMapping("/triggerAttendAnalysisView.html")
	public ModelAndView handleRequest(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {	
		
		log.debug("handling request");
//		Map model=new HashMap();
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
		model.addAttribute("months", months);
		List years = new ArrayList();
		Integer y = Calendar.getInstance().get(Calendar.YEAR);
		for (int i=10;i>=0;i--) {
			years.add(y-i);
		}
		model.addAttribute("years", years);
		
		if (month!=null && year!=null) {
			trigger.setMonth(month);
			trigger.setYear(year);
			model.addAttribute("month", month);
			model.addAttribute("year", year);
			try {
				attendanceManager.saveObject(trigger);
				model.addAttribute("failed", new Boolean(false));
				log.debug("failed false");
			} catch (DataIntegrityViolationException e) {
				// TODO Auto-generated catch block
				model.addAttribute("failed", new Boolean(true));
				log.debug("failed true DataIntegrityViolationException");
				//			e.printStackTrace();
			} catch (ConstraintViolationException e3) {
				// TODO Auto-generated catch block
				model.addAttribute("failed", new Boolean(true));
				log.debug("failed true ConstraintViolationException");
				//			e3.printStackTrace();
			}  catch (Exception e2) {
				// TODO Auto-generated catch block
				model.addAttribute("failed", new Boolean(true));
				log.debug("failed true Exception");
				e2.printStackTrace();
			} finally {
				log.debug("finallyyyyyyyyyyyyy");
				return new ModelAndView("triggerAttendAnalysisView");
			}
		} else {
			return new ModelAndView("triggerAttendAnalysisView");
		}
		
	}
}
