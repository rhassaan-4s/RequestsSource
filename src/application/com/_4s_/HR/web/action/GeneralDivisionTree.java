package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseController;

public class GeneralDivisionTree  extends BaseController {

	HRManager hrManager = null;
	
	public HRManager getHrManager() {
		return hrManager;
	}


	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}

	
	public ModelAndView handleRequest(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.start handleRequest()");

			Map model = new HashMap();
		
				
	       		
					
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.end handleRequest()");	
		//	********************************************************************************
			return  new ModelAndView("", model);
		}


	
		
	}
