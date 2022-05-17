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

import com._4s_.attendance.model.AttendanceDepartment;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.web.validate.ValidationStatus;

public class DepartmentForm extends BaseSimpleFormController{
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
		String departmentCode=request.getParameter("departmentCode");
		log.debug("--------departmentCode------"+departmentCode);
		
		AttendanceDepartment department = null;
		
		if(departmentCode==null || departmentCode.equals(""))
		{
			log.debug("--------departmentCode==null------");
			department=new AttendanceDepartment();
			
		} else {
			department=(AttendanceDepartment)attendanceManager.getObjectByParameter(AttendanceDepartment.class,"location", departmentCode);
		}
		log.debug("---------department-------"+department);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return department;
	}
	
	//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		AttendanceDepartment department= (AttendanceDepartment) command;
		log.debug("-----location code----"+department.getLocation());
		Map model=new HashMap();
		String departmentCode=request.getParameter("departmentCode");
		log.debug("departmentCode------"+departmentCode);
		model.put("departmentCode",departmentCode);
		List departmentsList=attendanceManager.getObjects(AttendanceDepartment.class);
		model.put("departmentsList", departmentsList);
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
		AttendanceDepartment department= (AttendanceDepartment) command;
		ValidationStatus status = attendanceManager.validateDepartment(department);
		
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
		AttendanceDepartment department= (AttendanceDepartment) command;
		log.debug("----activity code -onsubmit-----"+department.getLocation());
		
		attendanceManager.saveObject(department);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("departmentView.html"));
	}
}
