package com._4s_.common.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.Types;
import com._4s_.common.service.CommonManager;

public class TypesDataController extends BaseController{

	private CommonManager commonManager = null;
	
	

	public CommonManager getCommonManager() {
		return commonManager;
	}



	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}



	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String typeId = request.getParameter("typeId");
		log.debug(">>>>>>>>>>>>>>>>typeId = "+typeId);
		Types type = (Types)baseManager.getObject(Types.class,new Long(typeId));
		Map model = new HashMap();
		model.put("typesData",commonManager.getDataByTypes(type));
		model.put("typeId",typeId);
		model.put("typeDescription",type.getDescription());
		model.put("arDesc",type.getArDesc());
		return new ModelAndView("typesDataController",model);
	}

}
