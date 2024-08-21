package com._4s_.attendance.web.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.attendance.model.EmpWorkPeriod;
import com._4s_.attendance.model.EmpWorkPeriodListWrapper;
import com._4s_.attendance.model.VacRulesListWrapper;
import com._4s_.attendance.model.WorkPeriod;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.attendance.web.binders.EmpBasicBinder;
import com._4s_.attendance.web.binders.WorkPeriodMasterBinder;
import com._4s_.common.model.EmpBasic;
import com._4s_.common.model.Settings;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.common.web.binders.TimestampBinder;
import com._4s_.requestsApproval.model.Vacation;
import com._4s_.requestsApproval.web.binders.VacationBinder;
import com._4s_.timesheet.web.validate.ValidationStatus;
import com.ibm.icu.util.Calendar;

@Controller
@RequestMapping("/empWorkPeriodForm.html")
public class EmpWorkPeriodForm extends BaseSimpleFormController{
	@Autowired
	AttendanceManager attendanceManager;

	
	@Autowired
	@Qualifier("timestampBinder")
	private TimestampBinder timestampBinder;
	@Autowired
	@Qualifier("workPeriodMasterBinder")
	private WorkPeriodMasterBinder workPeriodMasterBinder;
	@Autowired
	@Qualifier("empBasicBinder")
	private EmpBasicBinder empBasicBinder;
	
	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		EmpWorkPeriodListWrapper empPeriods = new EmpWorkPeriodListWrapper();
		
		String empCode = request.getParameter("emp_code");
		log.debug("empCode " + empCode);
		String empStr = request.getParameter("emp");
		log.debug("empStr " + empStr);
		EmpBasic ebasic = null;
		if (empStr!=null && !empStr.isEmpty()) {
			empCode=empStr;
			ebasic = (EmpBasic)attendanceManager.getObjectByParameter(EmpBasic.class,"empCode",empCode);
		}
		
		String new_period = request.getParameter("new_period");
		
		List periods = new ArrayList();
		
		if (empCode!=null && !empCode.isEmpty()) {
			periods = (List)attendanceManager.getObjectsByParameter(EmpWorkPeriod.class,"emp_code.empCode",empCode);
			if(periods.size()==0) {
				EmpWorkPeriod p = new EmpWorkPeriod();
				periods.add(p);
			}
			log.debug("periods list " + periods.size());
		} else {
			EmpWorkPeriod p = new EmpWorkPeriod();
			periods.add(p);
			log.debug("periods list " + periods.size());
		}
		if(ebasic!=null) {
			empPeriods.setEmpCode(ebasic);
		}
		
		if (new_period!=null && !new_period.isEmpty() && new_period.equals("true")) {
			EmpWorkPeriod p = new EmpWorkPeriod();
			periods.add(p);
		}
		log.debug("##################  periods list size " + periods.size());
		
		empPeriods.setEmpPeriods(periods);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.put("periods", empPeriods);
	   return "empWorkPeriodForm";
	}
	
	//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request,@ModelAttribute("periods") EmpWorkPeriodListWrapper command)
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		EmpWorkPeriodListWrapper periods =  (EmpWorkPeriodListWrapper)command;
		
		
		log.debug("-----periods----"+periods.getClass());
		Map model=new HashMap();
		
		
		List wpList=attendanceManager.getObjects(WorkPeriod.class);
		model.put("workPeriods", wpList);
		String success = request.getParameter("success");
		model.put("success", success);
		
		String empCode = request.getParameter("emp_code");
		model.put("empCode", empCode);
		
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		boolean empRequestCheckDate = settings.getEmpRequestCheckDate();
		boolean empRequestTypeException = settings.getEmpRequestTypeException();
		
		
		List orderList = new ArrayList();
		orderList.add("empCode");
		List empBasic=attendanceManager.getActiveEmpBasic();
		log.debug("empBasic " + empBasic.size());
//		List currentEmps=new ArrayList();
//		Date date = new Date();
//		log.debug("date check 2  =  "+date);
//		for (int i = 0; i < loginUsers.size(); i++) {
//			LoginUsers loginUser=(LoginUsers) loginUsers.get(i);
//			log.debug("----i----"+loginUser.getEmpCode());
//			if((empRequestCheckDate == true && (loginUser.getEndServ()==null || loginUser.getEndServ().equals("") || loginUser.getEndServ().equals(date) || loginUser.getEndServ().after(date)))//Lotus
//					||
//					(empRequestCheckDate == false && (loginUser.getEndServ()==null || loginUser.getEndServ().equals("")))//Lehaa
//							){
//				log.debug("---before removing--i----"+loginUser.getEmpCode());
//				currentEmps.add(loginUser);
////				log.debug("----login----"+loginUsers.get(i));	
//			}
//			
//		}
//		log.debug("-------currentEmps.size---after"+currentEmps.size());
//		model.put("loginUsers", currentEmps);
		model.put("empBasic", empBasic);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}

	@Override
	public void initBinder(HttpServletRequest request,WebDataBinder binder) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Starting init binder: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		super.initBinder(request,binder);
		binder.registerCustomEditor(EmpBasicBinder.class, empBasicBinder);
		binder.registerCustomEditor(TimestampBinder.class, timestampBinder);
		binder.registerCustomEditor(WorkPeriodMasterBinder.class, workPeriodMasterBinder);
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
		EmpWorkPeriodListWrapper periods =  (EmpWorkPeriodListWrapper)command;
		ValidationStatus status = attendanceManager.validateEmpWorkPeriodWrapper(periods);	
		
		if (status.getStatus().equals("False") && status.getMsg().equals("Mandatory")) {
			errors.rejectValue(status.getObjAttribute(), "commons.errors.requiredFields");
		}
		if (status.getStatus().equals("False") && status.getMsg().equals("WrongDate")) {
			errors.rejectValue(status.getObjAttribute(), "commons.errors.arrangedateorder");
		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(HttpServletRequest request,
			@ModelAttribute("periods") EmpWorkPeriodListWrapper command,
//			BindingResult result,
			Model model) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		EmpWorkPeriodListWrapper periods =  (EmpWorkPeriodListWrapper)command;
		Iterator<EmpWorkPeriod> itrP = periods.getEmpPeriods().iterator();
		while(itrP.hasNext()) {
			EmpWorkPeriod period = itrP.next();
			log.debug("period " + period);
			Calendar calStart = Calendar.getInstance(); 
			calStart.setTime(period.getStart_date());
			period.setCr_month(calStart.get(Calendar.MONTH)+1);
			period.setCr_year(calStart.get(Calendar.YEAR));
			period.setEmp_code(periods.getEmpCode());
			attendanceManager.saveObject(period);
		}
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("empWorkPeriodForm.html?success=true"));
	}
}
