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

import com._4s_.attendance.model.Religion;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.web.validate.ValidationStatus;

public class ReligionForm extends BaseSimpleFormController{
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
		String religionCode=request.getParameter("religionCode");
		log.debug("--------religionCode------"+religionCode);
		
		Religion religion = null;
		
		if(religionCode==null || religionCode.equals(""))
		{
			log.debug("--------religionCode==null------");
			religion=new Religion();
			
		} else {
			religion=(Religion)attendanceManager.getObjectByParameter(Religion.class,"religion", religionCode);
		}
		log.debug("---------religion-------"+religion);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return religion;
	}
	
	//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Religion religion= (Religion) command;
		log.debug("-----religion command code----"+religion.getReligion());
		Map model=new HashMap();
		String religionCode=request.getParameter("religionCode");
		log.debug("religionCode parameter------"+religionCode);
		model.put("religionCode",religionCode);
		List religionsList=attendanceManager.getObjects(Religion.class);
		model.put("religionsList", religionsList);
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
		Religion religion= (Religion) command;
		ValidationStatus status = attendanceManager.validateReligion(religion);
		
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
		Religion religion= (Religion) command;
		log.debug("----activity code -onsubmit-----"+religion.getReligion());
		
		attendanceManager.saveObject(religion);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("religionView.html"));
	}
}