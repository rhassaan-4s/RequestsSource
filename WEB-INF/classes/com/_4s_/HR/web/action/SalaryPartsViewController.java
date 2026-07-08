package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.HR.model.HREffect;
import com._4s_.HR.service.HRManager;

public class SalaryPartsViewController  implements Controller{
private HRManager hrManager;
	
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
		
		String effectId =request.getParameter("effectId");
		model.put("effectId",effectId);
		
		String displayed_flag=request.getParameter("displayed_flag");
		model.put("displayed_flag",displayed_flag);
		
		
		List allEffects=hrManager.getObjects(HREffect.class);
		
		model.put("records", allEffects);
		
		return new ModelAndView("salaryPartsView",model);
	}
}
