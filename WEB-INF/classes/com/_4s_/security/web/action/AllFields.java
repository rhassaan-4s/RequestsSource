package com._4s_.security.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.web.action.BaseController;
import com._4s_.security.service.MySecurityManager;

public class AllFields extends BaseController{
	private MySecurityManager mgr= null;

	public MySecurityManager getMgr() {
		return mgr;
	}

	public void setMgr(MySecurityManager mgr) {
		this.mgr = mgr;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return new ModelAndView("allFields","fields",mgr.getFields());
	}
	
}
