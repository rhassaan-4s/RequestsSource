package com._4s_.common.web.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.Employee;

public class SampleLiveSearchController extends BaseSimpleFormController{


	
	

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		String value = request.getParameter("q");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> value "+value);
		return new ModelAndView("sampleLiveSearchSuccess","value",value);
	}

	protected Object formBackingObject(HttpServletRequest request)
		throws ServletException {
	
		log.debug("Start formBackingObject >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
		return new Employee();
	}
}
