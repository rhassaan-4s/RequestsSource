package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.HR.model.HRInternalDivision;
import com._4s_.HR.model.HRInternalDivisionBranch;
import com._4s_.HR.service.HRManager;
import com._4s_.i18n.model.MyLocale;
import com._4s_.security.model.User;

public class InternalDivisionBranchesView implements Controller{
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
		String typeId=request.getParameter("typeId");
		HRInternalDivision internalDivision=(HRInternalDivision)hrManager.getObject(HRInternalDivision.class,new Long(typeId));
		List allDivisionBrs=hrManager.getObjectsByParameter(HRInternalDivisionBranch.class,"type",internalDivision);
		User user=(User)request.getSession().getAttribute("user");
		MyLocale locale=user.getLanguage();
			model.put("locale", locale.getId());
		
		model.put("records", allDivisionBrs);
		model.put("typeId", typeId);
		model.put("type",internalDivision);
		return new ModelAndView("internalDivisionBranchesView",model);
	}

}
