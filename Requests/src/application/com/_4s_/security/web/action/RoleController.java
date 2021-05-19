package com._4s_.security.web.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.web.action.BaseController;
import com._4s_.security.model.Roles;
import com._4s_.security.service.MySecurityManager;

public class RoleController extends BaseController implements Comparator{
private MySecurityManager mgr = null;
	
	

	public MySecurityManager getMgr() {
		return mgr;
	}

	public void setMgr(MySecurityManager mgr) {
		this.mgr = mgr;
	}

	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		Roles u1 = (Roles)o1;
		Roles u2 = (Roles)o2;
		return u1.getRolename().compareTo(u2.getRolename());
	}

	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>> handleRequest()<<<<<<<<<<<<<<<<<<<<<<<<<<");
		List roles = new ArrayList(mgr.getListOfRoles());
		log.debug(">>>>>>>>>>>>>>>>>roles "+roles);
		Collections.sort(roles,new RoleController());
		return new ModelAndView("roleDetails","roles",roles);
	}
}
