package com._4s_.dbUpdate.web.action;

import java.io.BufferedWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.dbUpdate.service.SQLManager;
import com._4s_.security.web.command.ChangePasswordCommand;

public class FileWriter extends BaseSimpleFormController {

	SQLManager mgr = null;

	public SQLManager getMgr() {
		return mgr;
	}

	public void setMgr(SQLManager mgr) {
		this.mgr = mgr;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(HttpServletRequest request,
			@Valid @ModelAttribute("object") Object command, BindingResult result,
			SessionStatus status, Model model) 
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

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request){
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Object object = new Object();
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute("object", object);
		return "fileWriter";
	}
}