package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HREmployeeVacation;
import com._4s_.HR.model.HRVacation;
import com._4s_.HR.model.HRVacationType;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.LoginUsersRequests;

public class EmployeeVacationForm extends BaseSimpleFormController {
	 protected HRManager hrManager = null;
		
		public HRManager getHrManager() {
			return hrManager;
		}


		public void setHrManager(HRManager hrManager) {
			this.hrManager = hrManager;
		}
	
	//**************************************** formBackingObject ***********************************************\\
	@RequestMapping(method = RequestMethod.GET)  
	public String initForm(ModelMap model,HttpServletRequest request){
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HREmployeeVacation hrEmployeeVacation=null;
		String empVacationId=request.getParameter("empVacationId");
		log.debug("empVacationId>>>>>>>>>>>>"+empVacationId);
		//in case of editing object
		if(empVacationId!=null && !empVacationId.equals(""))
		{
			hrEmployeeVacation=(HREmployeeVacation)hrManager.getObject(HREmployeeVacation.class, new Long(empVacationId));
		}
		
		else
		{
			
		       hrEmployeeVacation=new HREmployeeVacation();
		}
    
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute(hrEmployeeVacation);
	   return "employeeVacationForm";
	}
//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,@ModelAttribute HREmployeeVacation command,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 Map model=new HashMap();
		 HREmployeeVacation hrEmployeeVacation=(HREmployeeVacation)command;
		
		 String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		  model.put("employeeId", employeeId);
		 	String empVacationId=request.getParameter("empVacationId");
		     model.put("empVacationId", empVacationId);
		     
		     String formType=request.getParameter("formType");
		     log.debug("formType>>>>>>>>>>>>>>>"+formType);
		      model.put("formType", formType);
		     HRVacationType vacType=(HRVacationType)hrManager.getObject(HRVacationType.class, new Long(0));
			List vacationList=hrManager.getObjectsByParameter(HRVacation.class,"vacationType",vacType);
			model.put("vacationList", vacationList);	
				
	
		
		
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		return model;
	}
	
	//**************************************** onBind ***********************************************\\	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
	
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
//**************************************** onBindAndValidate ***********************************************\\		
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 HREmployeeVacation hrEmployeeVacation=(HREmployeeVacation)command;
		 
		 if(errors.getErrorCount()==0)
		 {
			 if(hrEmployeeVacation.getVacation()==null )
			 {
				 errors.rejectValue("vacation", "commons.errors.requiredFields");
			 }
		 }
		 if(errors.getErrorCount()==0)
		 {
			 if(hrEmployeeVacation.getFromDate()==null || hrEmployeeVacation.getFromDate().equals(""))
			 {
				 errors.rejectValue("fromDate", "commons.errors.requiredFields");
			 }
		 }
		
		 if(errors.getErrorCount()==0)
		 {
			 if(hrEmployeeVacation.getToDate()==null || hrEmployeeVacation.getToDate().equals(""))
			 {
				 errors.rejectValue("toDate", "commons.errors.requiredFields");
			 }
		 }
		 if(errors.getErrorCount()==0)
		 {
			 if((hrEmployeeVacation.getFromDate()!=null && !hrEmployeeVacation.getFromDate().equals(""))
					 && (hrEmployeeVacation.getToDate()!=null && !hrEmployeeVacation.getToDate().equals("")) )
			 {
				 if(hrEmployeeVacation.getFromDate().after(hrEmployeeVacation.getToDate()))
				 {
				 errors.rejectValue("toDate", "hr.errors.endDateMustBeLargerThanStartDate","endDateMustBeLargerThanStartDate");
				 }
			 }
		 }
		 
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
	}
	
	
	
	//**************************************** onSubmit ***********************************************\\
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HREmployeeVacation hrEmployeeVacation=(HREmployeeVacation)command;
		
	     String formType=request.getParameter("formType");
	     log.debug("formType>>>>>>>>>>>>>>>"+formType);
	     String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		
		  log.debug("hrEmployeeVacation.getId()>>>>>>>>>>>>>>>"+hrEmployeeVacation.getId());
		  if((employeeId!=null && !employeeId.equals("")) && (hrEmployeeVacation.getId()==null ||hrEmployeeVacation.getId().equals("")))
		  {
		  HREmployee employee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
		  hrEmployeeVacation.setEmployee(employee);
		  }
		  //hrEmployeeVacation.getConsumedDays()
		   // hrEmployeeVacation.setDeductedDays();
			hrManager.saveObject(hrEmployeeVacation);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		if(formType.equals("employee"))
		{
		  return new ModelAndView(new RedirectView("employeeVacationView.html?employeeId="+employeeId+"&formType="+formType));
		}
		
		return new ModelAndView(new RedirectView("employeeVacationView.html?formType="+formType));
	}
	




}
