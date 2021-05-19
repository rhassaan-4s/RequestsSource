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
import com._4s_.HR.model.HREmployeeVacation;
import com._4s_.HR.model.HRVacation;
import com._4s_.HR.model.HRVacationType;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class EmployeeVacationForm extends BaseSimpleFormController {
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
	   return hrEmployeeVacation;
	}
//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
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
