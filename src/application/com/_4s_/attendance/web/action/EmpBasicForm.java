package com._4s_.attendance.web.action;


import java.text.SimpleDateFormat;
import java.util.Date;
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
import com._4s_.attendance.model.MaritalStatus;
import com._4s_.attendance.model.Qualification;
import com._4s_.attendance.model.Religion;
import com._4s_.attendance.model.Title;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.model.EmpBasic;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.timesheet.web.validate.ValidationStatus;

public class EmpBasicForm extends BaseSimpleFormController{
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
		String empCode=request.getParameter("empCode");
		log.debug("--------empCode------"+empCode);
		if (empCode!=null && empCode.isEmpty()) {
			empCode = null;
		}
		EmpBasic empBasic = new EmpBasic();
		empBasic.setEmpCode(empCode);
		String permenant = request.getParameter("permenant");
		if(permenant == null || permenant.equals("")){
			empBasic.setPermenant("F");
			log.debug("empBasic permanent " + empBasic.getPermenant());
		} else if (permenant.equals("on")) {
			empBasic.setPermenant("T");
			log.debug("empBasic permanent " + empBasic.getPermenant());
		}
		log.debug("request.getMethod() " + request.getMethod());
//		if (request.getMethod().equals("POST")) {
//			empBasic=(EmpBasic)attendanceManager.getObjectByParameter(EmpBasic.class,"empCode", empCode);
//			log.debug("national id " + empBasic.getNatnl_no());
//		} else {
			if(empCode==null || empCode.equals(""))
			{
				log.debug("--------empCode==null------");
				empBasic=new EmpBasic();

			} else {
				empBasic=(EmpBasic)attendanceManager.getObjectByParameter(EmpBasic.class,"empCode", empCode);
				log.debug("national id " + empBasic.getNatnl_no());
			}
//		}
		log.debug("---------empBasic-------"+empBasic);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return empBasic;
	}
	
	//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		EmpBasic empBasic= (EmpBasic) command;
		log.debug("-----empBasic code----"+empBasic.getEmpCode());
		Map model=new HashMap();
		String empCode=request.getParameter("empCode");
		log.debug("empCode------"+empCode);
		model.put("empCode",empCode);
		List empList=attendanceManager.getObjects(EmpBasic.class);
		model.put("empList", empList);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		populateData(model);
		return model;
	}

	private void populateData(Map model) {
		List marital = attendanceManager.getObjects(MaritalStatus.class);
		model.put("maritalList", marital);
		List religion = attendanceManager.getObjects(Religion.class);
		model.put("religionList", religion);
		List qualification = attendanceManager.getObjects(Qualification.class);
		model.put("qualificationList", qualification);
		List department = attendanceManager.getObjects(AttendanceDepartment.class);
		model.put("departmentList", department);
		List title = attendanceManager.getObjects(Title.class);
		model.put("titleList",title);
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
		EmpBasic empBasic= (EmpBasic) command;
		
		String permenant = request.getParameter("permenant");
		log.debug("permanent " + permenant);
		log.debug("perm from object " + empBasic.getPermenant());
		if(permenant == null || permenant.equals("")){
			empBasic.setPermenant("F");
			log.debug("empBasic permanent " + empBasic.getPermenant());
		} else if (permenant.equals("on")) {
			empBasic.setPermenant("T");
			log.debug("empBasic permanent " + empBasic.getPermenant());
		}
//		 SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.s");
//		 if (emp)
//		 String formatedDate = formatDate.format(empBasic.getBirthdate());
//		 Date bd = formatDate.parse(formatedDate);
//		 empBasic.setBirthdate(bd);
//		 
//		 formatedDate = formatDate.format(empBasic.getEmplDate());
//		 Date empD = formatDate.parse(formatedDate);
//		 empBasic.setEmplDate(empD);
//
//		 formatedDate = formatDate.format(empBasic.getJob_join());
//		 Date jD = formatDate.parse(formatedDate);
//		 empBasic.setJob_join(jD);
		 
		 
		log.debug("'"+empBasic.getAddress()+"','"+empBasic.getApply_overtime()+"','"+empBasic.getBasic()+"','"+empBasic.getBirthdate()+"','"+empBasic.getCurrency()+"','"+
				empBasic.getDepartment().getLocation()+"','"+empBasic.getDisplay_flag()+"','"+empBasic.geteAddress()+"','"+empBasic.getE_emp_name()+"','"+empBasic.getEldiana()+"','"+
				empBasic.getEmpName()+"','"+empBasic.getEmplDate()+"','"+empBasic.getEnd_serv()+"','"+empBasic.getForm()+"','"+empBasic.getHandicapped()+"','"+empBasic.getIllness_code()+"','"+
				empBasic.getJob_join()+"','"+empBasic.getLang()+"','"+empBasic.getMaritalStatus().getMaritalCode()+"','"+empBasic.getMobile()+"','"+empBasic.getNatnl_no()+"','"+
				empBasic.getOvertime_code()+"','"+empBasic.getPermenant()+"','"+empBasic.getPhone()+"','"+empBasic.getQual().getQual()+"','"+empBasic.getQualYear()+"','"+
				empBasic.getQual_specific()+"','"+empBasic.getSex()+"','"+empBasic.getShould_sign()+"','"+empBasic.getTitle()+"','"+
				empBasic.getTotal_no_hour()+"','"+empBasic.getVacrule_code()+"','"+empBasic.getWork_status()+"','"+empBasic.getWorkingD()+"','"+empBasic.getWorkingH()+" where empCode= " +empBasic.getEmpCode());
		
		ValidationStatus status = attendanceManager.validateEmpBasic(empBasic);
		
		if (status.getStatus().equals("False") && status.getMsg().equals("Mandatory")) {
			log.debug("status.getObjAttribute  mandatory " + status.getObjAttribute());
			errors.rejectValue(status.getObjAttribute(), "commons.errors.requiredFields");
			log.debug(errors);
		}
		if (status.getStatus().equals("False") && status.getMsg().equals("Duplicate")) {
			log.debug("status.getObjAttribute  duplicated " + status.getObjAttribute());
			errors.rejectValue(status.getObjAttribute(), "commons.errors.duplicateFieldEntry");
			log.debug(errors);
		}
		if (status.getStatus().equals("False") && status.getMsg().equals("Incorrect Length")) {
			log.debug("incorrect length");
			errors.rejectValue(status.getObjAttribute(), "commons.errors.nationalidlength");
			log.debug(errors);
		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{	
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		EmpBasic empBasic= (EmpBasic) command;
		log.debug("----activity code -onsubmit-----"+empBasic.getEmpCode());
		empBasic.setJob_join(empBasic.getEmplDate());
		log.debug(empBasic.getEmpCode());
		if(empBasic.getEmpCode()!=null && empBasic.getEmpCode().isEmpty()) {
			empBasic.setEmpCode(null);
		}
		
		log.debug("empBasic.getPermenant() " +  empBasic.getPermenant());
		
//		log.debug("'"+empBasic.getAddress()+"','"+empBasic.getApply_overtime()+"','"+empBasic.getBasic()+"','"+empBasic.getBirthdate()+"','"+empBasic.getCurrency()+"','"+
//		empBasic.getDepartment().getLocation()+"','"+empBasic.getDisplay_flag()+"','"+empBasic.geteAddress()+"','"+empBasic.getE_emp_name()+"','"+empBasic.getEldiana()+"','"+
//		empBasic.getEmpName()+"','"+empBasic.getEmplDate()+"','"+empBasic.getEnd_serv()+"','"+empBasic.getForm()+"','"+empBasic.getHandicapped()+"','"+empBasic.getIllness_code()+"','"+
//		empBasic.getJob_join()+"','"+empBasic.getLang()+"','"+empBasic.getMaritalStatus().getMaritalCode()+"','"+empBasic.getMobile()+"','"+empBasic.getNatnl_no()+"','"+
//		empBasic.getOvertime_code()+"','"+empBasic.getPermenant()+"','"+empBasic.getPhone()+"','"+empBasic.getQual().getQual()+"','"+empBasic.getQualYear()+"','"+
//		empBasic.getQual_specific().getQual_specific()+"','"+empBasic.getSex()+"','"+empBasic.getShould_sign()+"','"+empBasic.getTitle().getTitle()+"','"+
//		empBasic.getTotal_no_hour()+"','"+empBasic.getVacrule_code()+"','"+empBasic.getWork_status()+"','"+empBasic.getWorkingD()+"','"+empBasic.getWorkingH()+" where empCode= " +empBasic.getEmpCode());
		attendanceManager.saveObject(empBasic);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("empBasicView.html"));
	}
}
