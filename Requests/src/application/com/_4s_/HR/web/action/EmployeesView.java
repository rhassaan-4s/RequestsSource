package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.Punishment;
import com._4s_.HR.service.HRManager;

public class EmployeesView implements Controller{
	
	
	HRManager hrManager=null;

	public HRManager getHrManager() {
		return hrManager;
	}

	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		
		Map model=new HashMap();
		List allEmployees=hrManager.getObjects(HREmployee.class);
		System.out.println("records.size>>>>>>>>>>"+allEmployees.size());
		model.put("records", allEmployees);
		
		return new ModelAndView("employeesView",model);
	}
	

}