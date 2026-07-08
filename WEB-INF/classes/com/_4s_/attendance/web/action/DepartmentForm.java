package com._4s_.attendance.web.action;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.attendance.model.AttendanceDepartment;
import com._4s_.attendance.model.Qualification;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.timesheet.web.validate.ValidationStatus;

@Controller
@RequestMapping(value="/departmentForm.html",produces="text/html;charset=UTF-8")
public class DepartmentForm extends BaseSimpleFormController{
	@Autowired
	private AttendanceManager attendanceManager;


	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request) {
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
		model.put("department", department);
	   return "departmentForm";
	}
	
	//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request, @ModelAttribute("department") AttendanceDepartment command)
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
//	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
//	{
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		AttendanceDepartment department= (AttendanceDepartment) command;
//		ValidationStatus status = attendanceManager.validateDepartment(department);
//		
//		if (status.getStatus().equals("False") && status.getMsg().equals("Mandatory")) {
//			errors.rejectValue(status.getObjAttribute(), "commons.errors.requiredFields");
//		}
//		if (status.getStatus().equals("False") && status.getMsg().equals("Duplicate")) {
//			errors.rejectValue(status.getObjAttribute(), "commons.errors.duplicateFieldEntry");
//		}
//		
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//	}
	
	//**************************************** onSubmit ***********************************************\\	
	@RequestMapping(method = RequestMethod.POST,
			consumes=MediaType.APPLICATION_FORM_URLENCODED)
	public String processSubmit(HttpServletRequest request,HttpServletResponse response,
			@Valid@ModelAttribute("department") AttendanceDepartment command,
			BindingResult errors,
			Model model) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		AttendanceDepartment department= (AttendanceDepartment) command;
		log.debug("----activity code -onsubmit-----"+department.getLocation());
		if (errors.hasErrors()) {
			return "departmentForm";
		} else {
			attendanceManager.saveObject(department);
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			return "departmentView";
		}
	}
}
