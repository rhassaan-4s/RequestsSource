package com._4s_.attendance.web.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.attendance.model.AttendanceDegree;
import com._4s_.attendance.model.AttendanceRegion;
import com._4s_.attendance.model.AttendanceSector;
import com._4s_.attendance.model.EmpHist;
import com._4s_.attendance.model.Title;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.model.Employee;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.timesheet.web.validate.ValidationStatus;
import com.ibm.icu.util.Calendar;

@Controller
@RequestMapping("/empRequestsReports.html")
public class EmpHistForm extends BaseSimpleFormController{
	@Autowired
	AttendanceManager attendanceManager;


	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}

	@RequestMapping(method = RequestMethod.GET)  
	public String initForm(ModelMap model,HttpServletRequest request){
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String empCode=request.getParameter("empCode");
		log.debug("--------empCode------"+empCode);
		
		EmpHist empHist = null;
		empHist=new EmpHist();
		if(empCode==null || empCode.equals(""))
		{
			
			
		} else {
			Employee emp=(Employee)attendanceManager.getObjectByParameter(Employee.class,"empCode", empCode);
			empHist.setEmpCode(emp);
		}
		empHist.setFr_date(Calendar.getInstance().getTime());
		log.debug("---------empHist-------"+empHist);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute("empHist", empHist);
	   return "empHistForm";
	}
	
	//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")	
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,@ModelAttribute EmpHist command,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		EmpHist empHist= (EmpHist) command;
		log.debug("-----EmpHist code----"+empHist.getEmpCode());
		Map model=new HashMap();
		String empCode=request.getParameter("empCode");
		log.debug("empCode------"+empCode);
		model.put("empCode",empCode);
		List employees=attendanceManager.getObjects(Employee.class);
		model.put("employees", employees);
		
		List sectors=attendanceManager.getObjects(AttendanceSector.class);
		model.put("sectors", sectors);
		
		List degrees=attendanceManager.getObjects(AttendanceDegree.class);
		model.put("degrees", degrees);
		
		List regions=attendanceManager.getObjects(AttendanceRegion.class);
		model.put("regions", regions);
		
		List titles=attendanceManager.getObjects(Title.class);
		model.put("titles", titles);
		
		if(empCode==null || empCode.equals(""))
		{
			
			
		} else {
			List<String> order = new ArrayList<String>();
			order.add("fr_date");
			Employee emp=(Employee)attendanceManager.getObjectByParameter(Employee.class,"empCode", empCode);
			List history=attendanceManager.getObjectsByParameterOrderedByFieldList(EmpHist.class,"empCode",emp, order);
			model.put("history", history);
		}
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
		EmpHist empHist= (EmpHist) command;
		ValidationStatus status = attendanceManager.validateEmpHist(empHist);
		
		if (status.getStatus().equals("False") && status.getMsg().equals("Mandatory")) {
			errors.rejectValue(status.getObjAttribute(), "commons.errors.requiredFields");
		}
		if (status.getStatus().equals("False") && status.getMsg().equals("Duplicate")) {
			errors.rejectValue(status.getObjAttribute(), "commons.errors.duplicateFieldEntry");
		}
		log.debug("title " + empHist.getTitle());
		log.debug("emp " + empHist.getEmpCode());
		log.debug("region " + empHist.getRegion());
		log.debug("sector " + empHist.getSector());
		log.debug("degree " + empHist.getDegree());
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{	
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		EmpHist empHist= (EmpHist) command;
		log.debug("----activity code -onsubmit-----"+empHist.getEmpCode().getEmpCode());
		
		attendanceManager.saveObject(empHist);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("empHistView.html"));
	}
}
