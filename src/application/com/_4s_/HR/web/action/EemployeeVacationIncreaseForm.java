package com._4s_.HR.web.action;

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

import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HREmployeeVacationIncrease;
import com._4s_.HR.model.HRVacation;
import com._4s_.HR.model.HRVacationType;

import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class EemployeeVacationIncreaseForm extends BaseSimpleFormController {
	 protected HRManager hrManager = null;
		
		public HRManager getHrManager() {
			return hrManager;
		}


		public void setHrManager(HRManager hrManager) {
			this.hrManager = hrManager;
		}
	
	//**************************************** formBackingObject ***********************************************\\
	protected Object formBackingObject(HttpServletRequest request) throws ServletException 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HREmployeeVacationIncrease hrEmployeeVacationIncrease=null;
		String empVacationIncreaseId=request.getParameter("empVacationIncreaseId");
		log.debug("empVacationIncreaseId>>>>>>>>>>>>"+empVacationIncreaseId);
		//in case of editing object
		if(empVacationIncreaseId!=null && !empVacationIncreaseId.equals(""))
		{
			hrEmployeeVacationIncrease=(HREmployeeVacationIncrease)hrManager.getObject(HREmployeeVacationIncrease.class, new Long(empVacationIncreaseId));
		}
		
		else
		{
			
		       hrEmployeeVacationIncrease=new HREmployeeVacationIncrease();
		}
 
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return hrEmployeeVacationIncrease;
	}
//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 Map model=new HashMap();
		 HREmployeeVacationIncrease hrEmployeeVacationIncrease=(HREmployeeVacationIncrease)command;
		
		 String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		  model.put("employeeId", employeeId);
		 	String empVacationIncreaseId=request.getParameter("empVacationIncreaseId");
		     model.put("empVacationIncreaseId", empVacationIncreaseId);
		     
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
		 HREmployeeVacationIncrease hrEmployeeVacationIncrease=(HREmployeeVacationIncrease)command;
		 
		 if(errors.getErrorCount()==0)
		 {
			 if(hrEmployeeVacationIncrease.getVacation()==null )
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
		HREmployeeVacationIncrease hrEmployeeVacationIncrease=(HREmployeeVacationIncrease)command;
		
	     String formType=request.getParameter("formType");
	     log.debug("formType>>>>>>>>>>>>>>>"+formType);
	     String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		
		  log.debug("hrEmployeeVacationIncrease.getId()>>>>>>>>>>>>>>>"+hrEmployeeVacationIncrease.getId());
		  if((employeeId!=null && !employeeId.equals("")) && (hrEmployeeVacationIncrease.getId()==null ||hrEmployeeVacationIncrease.getId().equals("")))
		  {
		  HREmployee employee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
		  hrEmployeeVacationIncrease.setEmployee(employee);
		  }
		  //hrEmployeeVacationIncrease.getConsumedDays()
		   // hrEmployeeVacationIncrease.setDeductedDays();
			hrManager.saveObject(hrEmployeeVacationIncrease);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		if(formType.equals("employee"))
		{
		  return new ModelAndView(new RedirectView("employeeVacationIncreaseView.html?employeeId="+employeeId+"&formType="+formType));
		}
		
		return new ModelAndView(new RedirectView("employeeVacationIncreaseView.html?formType="+formType));
	}
	




}
