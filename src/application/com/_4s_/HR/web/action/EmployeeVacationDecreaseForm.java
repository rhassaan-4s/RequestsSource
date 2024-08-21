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
import com._4s_.HR.model.HREmployeeVacationDecrease;
import com._4s_.HR.model.HRVacation;
import com._4s_.HR.model.HRVacationType;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.LoginUsersRequests;

public class EmployeeVacationDecreaseForm extends BaseSimpleFormController {
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
		HREmployeeVacationDecrease hrEmployeeVacationDecrease=null;
		String empVacationDecreaseId=request.getParameter("empVacationDecreaseId");
		log.debug("empVacationDecreaseId>>>>>>>>>>>>"+empVacationDecreaseId);
		//in case of editing object
		if(empVacationDecreaseId!=null && !empVacationDecreaseId.equals(""))
		{
			hrEmployeeVacationDecrease=(HREmployeeVacationDecrease)hrManager.getObject(HREmployeeVacationDecrease.class, new Long(empVacationDecreaseId));
		}
		
		else
		{
			
		       hrEmployeeVacationDecrease=new HREmployeeVacationDecrease();
		}
 
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute(hrEmployeeVacationDecrease);
	   return "employeeVacationDecreaseForm";
	}
//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,@ModelAttribute HREmployeeVacationDecrease command,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 Map model=new HashMap();
		 HREmployeeVacationDecrease hrEmployeeVacationDecrease=(HREmployeeVacationDecrease)command;
		
		 String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		  model.put("employeeId", employeeId);
		 	String empVacationDecreaseId=request.getParameter("empVacationDecreaseId");
		     model.put("empVacationDecreaseId", empVacationDecreaseId);
		     
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
		 HREmployeeVacationDecrease hrEmployeeVacationDecrease=(HREmployeeVacationDecrease)command;
		 
		 if(errors.getErrorCount()==0)
		 {
			 if(hrEmployeeVacationDecrease.getVacation()==null)
			 {
				 errors.rejectValue("vacation", "commons.errors.requiredFields");
			 }
		 }
		
		 
		 
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
	}
	
	
	
	//**************************************** onSubmit ***********************************************\\
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HREmployeeVacationDecrease hrEmployeeVacationDecrease=(HREmployeeVacationDecrease)command;
		
	     String formType=request.getParameter("formType");
	     log.debug("formType>>>>>>>>>>>>>>>"+formType);
	     String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		
		  log.debug("hrEmployeeVacationDecrease.getId()>>>>>>>>>>>>>>>"+hrEmployeeVacationDecrease.getId());
		  if((employeeId!=null && !employeeId.equals("")) && (hrEmployeeVacationDecrease.getId()==null ||hrEmployeeVacationDecrease.getId().equals("")))
		  {
		  HREmployee employee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
		  hrEmployeeVacationDecrease.setEmployee(employee);
		  }
		  //hrEmployeeVacationDecrease.getConsumedDays()
		   // hrEmployeeVacationDecrease.setDeductedDays();
			hrManager.saveObject(hrEmployeeVacationDecrease);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		if(formType.equals("employee"))
		{
		  return new ModelAndView(new RedirectView("employeeVacationDecreaseView.html?employeeId="+employeeId+"&formType="+formType));
		}
		
		return new ModelAndView(new RedirectView("employeeVacationDecreaseView.html?formType="+formType));
	}
	




}
