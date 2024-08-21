package com._4s_.security.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.service.CommonManager;
import com._4s_.common.web.action.BaseController;
import com._4s_.security.model.IPAddress;
import com._4s_.security.model.Imei;
import com._4s_.security.model.User;
import com._4s_.security.service.MySecurityManager;
@Controller
public class IpController extends BaseController {
	@Autowired
	private MySecurityManager mgr = null;
	@Autowired
	public CommonManager commonManager;

	public MySecurityManager getMgr() {
		return mgr;
	}

	public void setMgr(MySecurityManager mgr) {
		this.mgr = mgr;
	}
	
	public CommonManager getCommonManager() {
		return commonManager;
	}

	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}

	

	@RequestMapping("/ipView.html")
//	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
	public String handleRequest(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>> handleRequest()<<<<<<<<<<<<<<<<<<<<<<<<<<");
//		Map model = new HashMap();
		String userId = request.getParameter("userId");
		log.debug("user id " + userId);
		String deleteId = request.getParameter("deleteId");
		if(deleteId != null && !deleteId.equals("")) {
			Long deleteIdLong = Long.parseLong(deleteId);
			Object o = baseManager.getObjectByParameter(IPAddress.class, "id", deleteIdLong);
			baseManager.removeObject(o);
		}
		User user = null;
		if (userId!= null && !userId.isEmpty()) {
			user = (User)mgr.getObject(User.class, new Long(userId));
			log.debug("user " + user);
			List ips = mgr.getObjectsByParameter(IPAddress.class, "users", user);
			log.debug("ips " + ips.size());
			model.addAttribute("ip", ips);
		}
		model.addAttribute("userId", userId);
//		return new ModelAndView("ipView", model);
		return "ipView";
	}
	
}
