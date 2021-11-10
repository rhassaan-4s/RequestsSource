package com._4s_.HR.web.action;

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

import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HREmployeeSpecialVacation;
import com._4s_.HR.model.HRVacation;
import com._4s_.HR.model.HRVacationType;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class EmployeeSpecialVacationForm extends BaseSimpleFormController {
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
		HREmployeeSpecialVacation hrEmployeeSpecialVacation=null;
		String empSpecialVacationId=request.getParameter("empSpecialVacationId");
		log.debug("empSpecialVacationId>>>>>>>>>>>>"+empSpecialVacationId);
		//in case of editing object
		if(empSpecialVacationId!=null && !empSpecialVacationId.equals(""))
		{
			hrEmployeeSpecialVacation=(HREmployeeSpecialVacation)hrManager.getObject(HREmployeeSpecialVacation.class, new Long(empSpecialVacationId));
		}
		
		else
		{
			
		       hrEmployeeSpecialVacation=new HREmployeeSpecialVacation();
		}

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return hrEmployeeSpecialVacation;
	}
//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 Map model=new HashMap();
		 HREmployeeSpecialVacation hrEmployeeSpecialVacation=(HREmployeeSpecialVacation)command;
		
		 String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		  model.put("employeeId", employeeId);
		 	String empSpecialVacationId=request.getParameter("empSpecialVacationId");
		     model.put("empSpecialVacationId", empSpecialVacationId);
		     
		     String formType=request.getParameter("formType");
		     log.debug("formType>>>>>>>>>>>>>>>"+formType);
		      model.put("formType", formType);
		      
		      HRVacationType vacType=(HRVacationType)hrManager.getObject(HRVacationType.class, new Long(1));
		      List vacationList=hrManager.getObjectsByParameter(HRVacation.class, "vacationType", vacType);
		      model.put("vacationList",vacationList);
		      model.put("dayDate",new Date());
	
		
		
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
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
	}
	
	
	
	//**************************************** onSubmit ***********************************************\\
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HREmployeeSpecialVacation hrEmployeeSpecialVacation=(HREmployeeSpecialVacation)command;
		
	     String formType=request.getParameter("formType");
	     log.debug("formType>>>>>>>>>>>>>>>"+formType);
	     String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		
		  log.debug("hrEmployeeSpecialVacation.getId()>>>>>>>>>>>>>>>"+hrEmployeeSpecialVacation.getId());
		  if((employeeId!=null && !employeeId.equals("")) && (hrEmployeeSpecialVacation.getId()==null ||hrEmployeeSpecialVacation.getId().equals("")))
		  {
		  HREmployee employee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
		  hrEmployeeSpecialVacation.setEmployee(employee);
		  }
		  
		    
			hrManager.saveObject(hrEmployeeSpecialVacation);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		if(formType.equals("employee"))
		{
		  return new ModelAndView(new RedirectView("employeeSpecialVacationView.html?employeeId="+employeeId+"&formType="+formType));
		}
		
		return new ModelAndView(new RedirectView("employeeSpecialVacationView.html?formType="+formType));
	}
	




}

