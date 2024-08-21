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
import com._4s_.timesheet.model.TimesheetTransactionParts;
import com._4s_.timesheet.service.TimesheetManager;
import com._4s_.timesheet.web.validate.ValidationStatus;
import com._4s_.common.model.EmpBasic;
import com._4s_.common.web.action.BaseSimpleFormController;
@Controller
@RequestMapping("/partForm.html")
public class PartForm extends BaseSimpleFormController{
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
		String partCode=request.getParameter("partcode");
		log.debug("--------partCode------"+partCode);
		String partNo=request.getParameter("partNo");
		log.debug("partNo " + partNo);
		TimesheetTransactionParts part = null;
		
		if(partCode==null || partCode.equals(""))
		{
			log.debug("--------partCode==null------");
			part=new TimesheetTransactionParts();
			log.debug("will set part no");
			part.setPart_no(new Short(partNo));
		} else {
			part=(TimesheetTransactionParts)timesheetManager.getObjectByParameter(TimesheetTransactionParts.class,"code", partCode);
		}
		log.debug("---------part-------"+part);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute("part", part);
	   return "partForm";
	}
	
	//**************************************** referenceData ***********************************************\\
//	@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
//	{
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request,@ModelAttribute("part") TimesheetTransactionParts command)
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		TimesheetTransactionParts part= (TimesheetTransactionParts) command;
		log.debug("-----part.code----"+part.getCode());
		Map model=new HashMap();
		String partCode=request.getParameter("partcode");
		String partNo=request.getParameter("partNo");
		log.debug("partNo " + partNo);
		log.debug("partCode------"+partCode);
		model.put("code",partCode);
		model.put("partNo",partNo);
		List partsList=timesheetManager.getObjectsByParameter(TimesheetTransactionParts.class,"part_no",new Short(partNo));
		model.put("partsList", partsList);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}

	//**************************************** onBind ***********************************************\\	
//	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//	}
//**************************************** onBindAndValidate ***********************************************\\
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		TimesheetTransactionParts part = (TimesheetTransactionParts)command;
		ValidationStatus status = timesheetManager.validatePart(part);
		if (status.getStatus().equals("False") && status.getMsg().equals("Mandatory")) {
			errors.rejectValue(status.getObjAttribute(), "commons.errors.requiredFields");
		}
		if (status.getStatus().equals("False") && status.getMsg().equals("Duplicate")) {
			errors.rejectValue(status.getObjAttribute(), "commons.errors.duplicateFieldEntry");
		}
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
			@Valid @ModelAttribute("part") TimesheetTransactionParts command,
			BindingResult result, SessionStatus status,Model model) {

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		TimesheetTransactionParts part= (TimesheetTransactionParts) command;
		log.debug("----part code ------"+part.getCode());
		
		String partNo=request.getParameter("partNo");
		log.debug("partNo " + partNo);
		log.debug("part code " + part.getCode());
		
		
//		log.debug("partNo " + partNo);
//		if (part.getCode()==null || part.getCode().isEmpty()) {
//			log.debug("will set part no");
//			part.setPart_no(new Short(partNo));
//		}
		
		
		timesheetManager.saveObject(part);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("partView.html?partNo="+partNo));
	}
}
