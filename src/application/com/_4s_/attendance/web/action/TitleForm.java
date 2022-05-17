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

import com._4s_.attendance.model.Title;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.web.validate.ValidationStatus;

public class TitleForm extends BaseSimpleFormController{
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
		String titleCode=request.getParameter("titleCode");
		log.debug("--------titleCode------"+titleCode);
		
		Title title = null;
		
		if(titleCode==null || titleCode.equals(""))
		{
			log.debug("--------titleCode==null------");
			title=new Title();
			
		} else {
			title=(Title)attendanceManager.getObjectByParameter(Title.class,"title", titleCode);
		}
		log.debug("---------title-------"+title);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return title;
	}
	
	//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Title title= (Title) command;
		log.debug("-----title code----"+title.getTitle());
		Map model=new HashMap();
		String titleCode=request.getParameter("titleCode");
		log.debug("titleCode------"+titleCode);
		model.put("titleCode",titleCode);
		List titlesList=attendanceManager.getObjects(Title.class);
		model.put("titlesList", titlesList);
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
		Title title= (Title) command;
		ValidationStatus status = attendanceManager.validateTitle(title);
		
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
		Title title= (Title) command;
		log.debug("----activity code -onsubmit-----"+title.getTitle());
		
		attendanceManager.saveObject(title);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("titleView.html"));
	}
}
