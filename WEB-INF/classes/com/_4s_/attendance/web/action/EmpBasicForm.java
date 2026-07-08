package com._4s_.attendance.web.action;


import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com._4s_.attendance.model.AttendanceDepartment;
import com._4s_.attendance.model.MaritalStatus;
import com._4s_.attendance.model.Qualification;
import com._4s_.attendance.model.Religion;
import com._4s_.attendance.model.Title;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.attendance.web.binders.AttendanceDepartmentBinder;
import com._4s_.attendance.web.binders.MaritalStatusBinder;
import com._4s_.attendance.web.binders.QualificationBinder;
import com._4s_.attendance.web.binders.ReligionBinder;
import com._4s_.attendance.web.binders.TitleBinder;
import com._4s_.common.model.EmpBasic;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.common.web.binders.TimestampBinder;

@Controller
@RequestMapping(value="/empBasicForm.html")//empBasic
public class EmpBasicForm extends BaseSimpleFormController{
	@Autowired
	private AttendanceManager attendanceManager;


	@Autowired
	@Qualifier("religionBinder")
	private ReligionBinder religionBinder;
	@Autowired
	@Qualifier("maritalStatusBinder")
	private MaritalStatusBinder maritalStatusBinder;
	@Autowired
	@Qualifier("qualificationBinder")
	private QualificationBinder qualificationBinder;
	@Autowired
	@Qualifier("departmentBinder")
	private AttendanceDepartmentBinder departmentBinder;
	@Autowired
	@Qualifier("titleBinder")
	private TitleBinder titleBinder;
	@Autowired
	@Qualifier("timestampBinder")
	private TimestampBinder timestampBinder;
	
	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}

	public ReligionBinder getReligionBinder() {
		return religionBinder;
	}

	public void setReligionBinder(ReligionBinder religionBinder) {
		this.religionBinder = religionBinder;
	}

	public MaritalStatusBinder getMaritalStatusBinder() {
		return maritalStatusBinder;
	}

	public void setMaritalStatusBinder(MaritalStatusBinder maritalStatusBinder) {
		this.maritalStatusBinder = maritalStatusBinder;
	}

	public QualificationBinder getQualificationBinder() {
		return qualificationBinder;
	}

	public void setQualificationBinder(QualificationBinder qualificationBinder) {
		this.qualificationBinder = qualificationBinder;
	}

	public AttendanceDepartmentBinder getDepartmentBinder() {
		return departmentBinder;
	}

	public void setDepartmentBinder(AttendanceDepartmentBinder departmentBinder) {
		this.departmentBinder = departmentBinder;
	}

	public TitleBinder getTitleBinder() {
		return titleBinder;
	}

	public void setTitleBinder(TitleBinder titleBinder) {
		this.titleBinder = titleBinder;
	}

	public TimestampBinder getTimestampBinder() {
		return timestampBinder;
	}

	public void setTimestampBinder(TimestampBinder timestampBinder) {
		this.timestampBinder = timestampBinder;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request) {
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
	   model.put("empBasic",empBasic);
	   return "empBasicForm";
	}
	
	//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request, @ModelAttribute("empBasic") EmpBasic command) {
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
		
//		populateData(model);
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
		return model;
	}

//	private void populateData(Map model) {
//		List marital = attendanceManager.getObjects(MaritalStatus.class);
//		model.put("maritalList", marital);
//		List religion = attendanceManager.getObjects(Religion.class);
//		model.put("religionList", religion);
//		List qualification = attendanceManager.getObjects(Qualification.class);
//		model.put("qualificationList", qualification);
//		List department = attendanceManager.getObjects(AttendanceDepartment.class);
//		model.put("departmentList", department);
//		List title = attendanceManager.getObjects(Title.class);
//		model.put("titleList",title);
//	}

	//**************************************** onBind ***********************************************\\	
//	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//	}
//**************************************** onBindAndValidate ***********************************************\\
//	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
//	{
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		EmpBasic empBasic= (EmpBasic) command;
//		
//		
////		 SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.s");
////		 if (emp)
////		 String formatedDate = formatDate.format(empBasic.getBirthdate());
////		 Date bd = formatDate.parse(formatedDate);
////		 empBasic.setBirthdate(bd);
////		 
////		 formatedDate = formatDate.format(empBasic.getEmplDate());
////		 Date empD = formatDate.parse(formatedDate);
////		 empBasic.setEmplDate(empD);
////
////		 formatedDate = formatDate.format(empBasic.getJob_join());
////		 Date jD = formatDate.parse(formatedDate);
////		 empBasic.setJob_join(jD);
//		 
//		 
//		log.debug("'"+empBasic.getAddress()+"','"+empBasic.getApply_overtime()+"','"+empBasic.getBasic()+"','"+empBasic.getBirthdate()+"','"+empBasic.getCurrency()+"','"+
//				empBasic.getDepartment().getLocation()+"','"+empBasic.getDisplay_flag()+"','"+empBasic.geteAddress()+"','"+empBasic.getE_emp_name()+"','"+empBasic.getEldiana()+"','"+
//				empBasic.getEmpName()+"','"+empBasic.getEmplDate()+"','"+empBasic.getEnd_serv()+"','"+empBasic.getForm()+"','"+empBasic.getHandicapped()+"','"+empBasic.getIllness_code()+"','"+
//				empBasic.getJob_join()+"','"+empBasic.getLang()+"','"+empBasic.getMaritalStatus().getMaritalCode()+"','"+empBasic.getMobile()+"','"+empBasic.getNatnl_no()+"','"+
//				empBasic.getOvertime_code()+"','"+empBasic.getPermenant()+"','"+empBasic.getPhone()+"','"+empBasic.getQual().getQual()+"','"+empBasic.getQualYear()+"','"+
//				empBasic.getQual_specific()+"','"+empBasic.getSex()+"','"+empBasic.getShould_sign()+"','"+empBasic.getTitle()+"','"+
//				empBasic.getTotal_no_hour()+"','"+empBasic.getVacrule_code()+"','"+empBasic.getWork_status()+"','"+empBasic.getWorkingD()+"','"+empBasic.getWorkingH()+" where empCode= " +empBasic.getEmpCode());
//		
//		ValidationStatus status = attendanceManager.validateEmpBasic(empBasic);
//		
//		if (status.getStatus().equals("False") && status.getMsg().equals("Mandatory")) {
//			log.debug("status.getObjAttribute  mandatory " + status.getObjAttribute());
//			errors.rejectValue(status.getObjAttribute(), "commons.errors.requiredFields");
//			log.debug(errors);
//		}
//		if (status.getStatus().equals("False") && status.getMsg().equals("Duplicate")) {
//			log.debug("status.getObjAttribute  duplicated " + status.getObjAttribute());
//			errors.rejectValue(status.getObjAttribute(), "commons.errors.duplicateFieldEntry");
//			log.debug(errors);
//		}
//		if (status.getStatus().equals("False") && status.getMsg().equals("Incorrect Length")) {
//			log.debug("incorrect length");
//			errors.rejectValue(status.getObjAttribute(), "commons.errors.nationalidlength");
//			log.debug(errors);
//		}
//		
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//	}
	
	@Override
	public void initBinder(HttpServletRequest request,WebDataBinder binder) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Starting init binder: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		super.initBinder(request,binder);
		binder.registerCustomEditor(Religion.class, religionBinder);
		binder.registerCustomEditor(MaritalStatus.class, maritalStatusBinder);
		binder.registerCustomEditor(Qualification.class, qualificationBinder);
		binder.registerCustomEditor(AttendanceDepartment.class, departmentBinder);
		binder.registerCustomEditor(Title.class, titleBinder);
		binder.registerCustomEditor(Date.class, timestampBinder);
	}
	//**************************************** onSubmit ***********************************************\\	
	@RequestMapping(method = RequestMethod.POST)//,
//	consumes=MediaType.APPLICATION_FORM_URLENCODED
	public String processSubmit(HttpServletRequest request,HttpServletResponse response,
			@Valid@ModelAttribute("empBasic") EmpBasic command,
			BindingResult errors,
			Model model) throws Exception {
		try {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			EmpBasic empBasic= (EmpBasic) command;
			
			if (errors.hasErrors()) {
				log.debug("errors " + errors.getErrorCount());
				Iterator<ObjectError> itr = errors.getAllErrors().iterator();
				while(itr.hasNext()) {
					ObjectError error = itr.next();
					log.debug("error " + error.getDefaultMessage());
				}
				return "empBasicForm";
			} else {
				log.debug("---- code -onsubmit-----"+empBasic.getEmpCode());
				log.debug("response " + response.getContentType());
				log.debug("request " + request.getContentType());
				empBasic.setJob_join(empBasic.getEmplDate());
				log.debug(empBasic.getEmpCode());
				if(empBasic.getEmpCode()!=null && empBasic.getEmpCode().isEmpty()) {
					empBasic.setEmpCode(null);
				}

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
				return "empBasicView";
			}
		}catch (Exception e) {
			System.out.println("Failed to validate <or better message>" + e); 
			e.printStackTrace();
			return "empBasicForm";
		}
	}
}
