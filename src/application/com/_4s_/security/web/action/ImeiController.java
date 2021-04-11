package com._4s_.security.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.service.CommonManager;
import com._4s_.common.web.action.BaseController;
import com._4s_.security.model.Imei;
import com._4s_.security.model.User;
import com._4s_.security.service.MySecurityManager;

public class ImeiController extends BaseController {
	
	private MySecurityManager mgr = null;
	
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

	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>> handleRequest()<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Map model = new HashMap();
		String userId = request.getParameter("userId");
		log.debug("user id " + userId);
		String deleteId = request.getParameter("deleteId");
		if(deleteId != null && !deleteId.equals("")) {
			Long deleteIdLong = Long.parseLong(deleteId);
			Object o = baseManager.getObjectByParameter(Imei.class, "id", deleteIdLong);
			baseManager.removeObject(o);
		}
		User user = null;
		if (userId!= null && !userId.isEmpty()) {
			user = (User)mgr.getObject(User.class, new Long(userId));
			log.debug("user " + user);
			List imei = mgr.getObjectsByParameter(Imei.class, "users", user);
			log.debug("imei " + imei.size());
			model.put("imei", imei);
		}
		model.put("userId", userId);
		return new ModelAndView("imeiView", model);
	}
	
}
