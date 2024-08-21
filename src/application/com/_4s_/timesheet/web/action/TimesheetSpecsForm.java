package com._4s_.timesheet.web.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.model.Requests;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.model.TimesheetSpecs;
import com._4s_.timesheet.service.TimesheetManager;
import com._4s_.common.model.EmpBasic;
import com._4s_.common.web.action.BaseSimpleFormController;
@Controller
@RequestMapping("/timesheetSpecs.html")
public class TimesheetSpecsForm extends BaseSimpleFormController{
	@Autowired
	TimesheetManager timesheetManager;

	public TimesheetManager getTimesheetManager() {
		return timesheetManager;
	}

	public void setTimesheetManager(TimesheetManager timesheetManager) {
		this.timesheetManager = timesheetManager;
	}

	//	@RequestMapping(method = RequestMethod.GET)  public String initForm(ModelMap model,HttpServletRequest request){
	//	{
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request){
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		TimesheetSpecs specs = null;

		specs=(TimesheetSpecs)timesheetManager.getObjectByParameter(TimesheetSpecs.class,"code", "1");
		log.debug("---------specs-------"+specs);
		if (specs == null) {
			specs = new TimesheetSpecs();
			specs.setCode("1");
		}

		if(request.getMethod().equals("POST")){


		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		//	   return specs;
		model.addAttribute("specs", specs);
		return "timesheetSpecs";
	}

	//**************************************** referenceData ***********************************************\\
	//	@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
	//	{
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request,@ModelAttribute("specs") TimesheetSpecs command) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		TimesheetSpecs specs= (TimesheetSpecs) command;
		log.debug("-----specs----"+specs);
		Map model=new HashMap();

		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}

	//**************************************** onBind ***********************************************\\	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	//**************************************** onBindAndValidate ***********************************************\\
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		TimesheetSpecs specs= (TimesheetSpecs) command;
		//		
		//		if(errors.getErrorCount()==0)
		//		{
		//		
		//			if(attendanceRequest.getName()==null || attendanceRequest.getName().equals(""))
		//			{
		//				errors.rejectValue("name", "commons.errors.requiredFields");
		//			}
		//			
		//		}

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	//**************************************** onSubmit ***********************************************\\	
	//	public ModelAndView onSubmit(HttpServletRequest request,
	//			HttpServletResponse response, Object command, BindException errors)throws Exception 
	//	{	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(HttpServletRequest request,
			@Valid @ModelAttribute("specs") TimesheetSpecs command,
			BindingResult result, SessionStatus status,Model model) {

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		TimesheetSpecs specs= (TimesheetSpecs) command;
		log.debug("is used 1: " + specs.getIs_used1());
		String isUsed1 = request.getParameter("is_used1");
		log.debug("isused1 " + isUsed1);
		if(isUsed1 == null || isUsed1.isEmpty()){
			log.debug("empty isused1");
			specs.setIs_used1("0");
			specs.setPart1_name(null);
			specs.setPart1_ename(null);
		} else {
			log.debug("not empty isused1");
			specs.setIs_used1("1");
		}

		String isUsed2 = request.getParameter("is_used2");
		log.debug("isused2 " + isUsed2);
		if(isUsed2 == null || isUsed2.isEmpty()){
			log.debug("empty isused2");
			specs.setIs_used2("0");
			specs.setPart2_name(null);
			specs.setPart2_ename(null);
		} else {
			log.debug("not empty isused2");
			specs.setIs_used2("1");
		}

		String isUsed3 = request.getParameter("is_used3");
		log.debug("isused3 " + isUsed3);
		if(isUsed3 == null || isUsed3.isEmpty()){
			log.debug("empty isused3");
			specs.setIs_used3("0");
			specs.setPart3_name(null);
			specs.setPart3_ename(null);
		} else {
			log.debug("not empty isused3");
			specs.setIs_used3("1");
		}
		timesheetManager.saveObject(specs);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("timesheetSpecs.html"));
	}
}
