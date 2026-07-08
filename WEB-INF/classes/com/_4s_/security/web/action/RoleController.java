package com._4s_.security.web.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.web.action.BaseController;
import com._4s_.security.model.Roles;
import com._4s_.security.service.MySecurityManager;

@Controller
public class RoleController  implements Comparator{//extends BaseController
	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
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

//	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
	@RequestMapping("/roleDetails.html")
	public String handleRequest(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>> handleRequest()<<<<<<<<<<<<<<<<<<<<<<<<<<");
		List roles = new ArrayList(mgr.getListOfRoles());
		model.addAttribute("roles", roles);
		log.debug(">>>>>>>>>>>>>>>>>roles "+roles);
		Collections.sort(roles,new RoleController());
//		return new ModelAndView("roleDetails","roles",roles);
		return "roleDetails";
	}
}
