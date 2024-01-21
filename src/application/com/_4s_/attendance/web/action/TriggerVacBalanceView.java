package com._4s_.attendance.web.action;

import java.sql.Timestamp;
import java.util.HashMap;
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

import com._4s_.attendance.model.TriggerVacBalance;
import com._4s_.attendance.service.AttendanceManager;

@Controller
public class TriggerVacBalanceView {//implements Controller{
	@Autowired
	private AttendanceManager attendanceManager;

	 protected final Log log = LogFactory.getLog(getClass());
	 
	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}
	
	@RequestMapping("/triggerVacBalanceView.html")
	public ModelAndView handleRequest(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {	
		
		log.debug("handling request");
//		Map model=new HashMap();
		TriggerVacBalance trigger = new TriggerVacBalance();
		trigger.setIndate(new Timestamp(System.currentTimeMillis()));
		try {
			attendanceManager.saveObject(trigger);
			model.addAttribute("failed", new Boolean(false));
			log.debug("failed false");
			System.out.println("failed false");
		} catch (DataIntegrityViolationException e) {
			// TODO Auto-generated catch block
			model.addAttribute("failed", new Boolean(true));
			System.out.println("failed true DataIntegrityViolationException");
			e.printStackTrace();
		} catch (ConstraintViolationException e3) {
			// TODO Auto-generated catch block
			model.addAttribute("failed", new Boolean(true));
			System.out.println("failed true ConstraintViolationException");
			e3.printStackTrace();
		}  catch (Exception e2) {
			// TODO Auto-generated catch block
			model.addAttribute("failed", new Boolean(true));
			System.out.println("failed true Exception");
			e2.printStackTrace();
		} finally {
			log.debug("finallyyyyyyyyyyyyy");
			return new ModelAndView("triggerVacBalanceView");
		}
		
	}
}
