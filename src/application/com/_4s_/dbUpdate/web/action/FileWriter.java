package com._4s_.dbUpdate.web.action;

import java.io.BufferedWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.dbUpdate.service.SQLManager;

public class FileWriter extends BaseSimpleFormController {

	SQLManager mgr = null;

	public SQLManager getMgr() {
		return mgr;
	}

	public void setMgr(SQLManager mgr) {
		this.mgr = mgr;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("entering 'onSubmit' method....");
		}
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
		Object object = (Object) command;
	
		String data = request.getParameter("data");
		log
		.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< DATA IS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+data);
		
   
	    BufferedWriter out = new BufferedWriter(new java.io.FileWriter("/home/smagied/ERPDBUpdate.txt", true));
	    out.write(data); 
		log
				.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		out.close();
		return new ModelAndView(new RedirectView("fileWriterForm.html"));

	}

	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Object object = new Object();
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return object;
	}
}