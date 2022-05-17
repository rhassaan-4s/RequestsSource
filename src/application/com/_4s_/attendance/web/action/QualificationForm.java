package com._4s_.attendance.web.action;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.attendance.model.Qualification;
import com._4s_.attendance.model.Title;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.timesheet.web.validate.ValidationStatus;

public class QualificationForm extends BaseSimpleFormController{
	AttendanceManager attendanceManager;


	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}

	protected Object formBackingObject(HttpServletRequest request) throws ServletException 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String qualificationCode=request.getParameter("qualificationCode");
		log.debug("--------qualificationCode------"+qualificationCode);
		
		Qualification qualification = null;
		
		if(qualificationCode==null || qualificationCode.equals(""))
		{
			log.debug("--------qualificationCode==null------");
			qualification=new Qualification();
			
		} else {
			qualification=(Qualification)attendanceManager.getObjectByParameter(Qualification.class,"qual", qualificationCode);
		}
		log.debug("---------qualification-------"+qualification);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return qualification;
	}
	
	//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Qualification qualification= (Qualification) command;
		log.debug("-----qualification code----"+qualification.getQual());
		Map model=new HashMap();
		String qualificationCode=request.getParameter("qualificationCode");
		log.debug("qualificationCode------"+qualificationCode);
		model.put("qualificationCode",qualificationCode);
		List qualificationsList=attendanceManager.getObjects(Qualification.class);
		model.put("qualificationsList", qualificationsList);
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
		Qualification qualification= (Qualification) command;
		ValidationStatus status = attendanceManager.validateQualification(qualification);
		
		if (status.getStatus().equals("False") && status.getMsg().equals("Mandatory")) {
			errors.rejectValue(status.getObjAttribute(), "commons.errors.requiredFields");
		}
		if (status.getStatus().equals("False") && status.getMsg().equals("Duplicate")) {
			errors.rejectValue(status.getObjAttribute(), "commons.errors.duplicateFieldEntry");
		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{	
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Qualification qualification= (Qualification) command;
		log.debug("----qualification code -onsubmit-----"+qualification.getQual());
		
		attendanceManager.saveObject(qualification);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("qualificationView.html"));
	}
}
