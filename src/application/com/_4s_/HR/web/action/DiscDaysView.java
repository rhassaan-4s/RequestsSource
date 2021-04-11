package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.HR.model.DiscDays;
import com._4s_.HR.model.HREmployee;
import com._4s_.HR.service.HRManager;

public class DiscDaysView implements Controller{

	private HRManager hrManager;
	
	public HRManager getHrManager(){
		return hrManager;
	}
	
	public void setHrManager(HRManager hrManager){
		this.hrManager = hrManager;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse arg1) throws Exception {
		Map model=new HashMap();
		
		String discId=request.getParameter("discId");
		model.put("discId",discId);
		
		String disc_type=request.getParameter("disc_type");
		model.put("disc_type",disc_type);
		
		List allDiscDays=hrManager.getObjects(DiscDays.class);
		
		for(int i=0; i<allDiscDays.size(); i++){
			DiscDays dd = (DiscDays) allDiscDays.get(i);
			HREmployee emp = (HREmployee) hrManager.getObjectByParameter(HREmployee.class, "empCode", dd.getEmp_id());
			dd.setEmpName(emp.getName());
		}
		
		model.put("records", allDiscDays);
		
		return new ModelAndView("discDaysView",model);
	}
}
