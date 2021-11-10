package com._4s_.HR.web.action;

import java.util.Calendar;
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
import com._4s_.HR.model.HREmployeeEmployeeStatus;
import com._4s_.HR.model.HREmployeeStatus;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class EmployeeStatusForm extends BaseSimpleFormController {
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
		HREmployeeEmployeeStatus hrEmployeeStatus=null;
		String empStatusId=request.getParameter("empStatusId");
		log.debug("empStatusId>>>>>>>>>>>>"+empStatusId);
		//in case of editing object
		if(empStatusId!=null && !empStatusId.equals(""))
		{
			hrEmployeeStatus=(HREmployeeEmployeeStatus)hrManager.getObject(HREmployeeEmployeeStatus.class, new Long(empStatusId));
		}
		
		else
		{
			
		       hrEmployeeStatus=new HREmployeeEmployeeStatus();
		}
 
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return hrEmployeeStatus;
	}
//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 Map model=new HashMap();
		 HREmployeeEmployeeStatus hrEmployeeStatus=(HREmployeeEmployeeStatus)command;
		
		 String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		  model.put("employeeId", employeeId);
		 	String empStatusId=request.getParameter("empStatusId");
		     model.put("empStatusId", empStatusId);
		     
		     String formType=request.getParameter("formType");
		     log.debug("formType>>>>>>>>>>>>>>>"+formType);
		      model.put("formType", formType);
		     
			List employeeStatusList=hrManager.getObjects(HREmployeeStatus.class);
			model.put("employeeStatusList", employeeStatusList);	
				
		
		
		
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
		 HREmployeeEmployeeStatus hrEmployeeStatus=(HREmployeeEmployeeStatus)command;
		 
		 String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		  HREmployee employee=null;
		  if(employeeId!=null && !employeeId.equals(""))
		  {
			  employee=(HREmployee)hrManager.getObject(HREmployee.class,new Long(employeeId));
		  }
		  
		  if(errors.getErrorCount()==0)
		  {
			  if(hrEmployeeStatus.getEmployeeStatus()==null)
			  {
				  errors.rejectValue("employeeStatus", "commons.errors.requiredFields", "requiredFields");
			  }
		  }
		  
		  if(errors.getErrorCount()==0)
		  {
			  if(hrEmployeeStatus.getStatusStartDate()==null || hrEmployeeStatus.getStatusStartDate().equals(""))
			  {
				  errors.rejectValue("statusStartDate", "commons.errors.requiredFields", "requiredFields");
			  }
			  
		  }
		 if(errors.getErrorCount()==0)
		 {
			 Calendar cal=Calendar.getInstance();
			 cal.setTime(hrEmployeeStatus.getStatusStartDate());
			  cal.set(Calendar.HOUR_OF_DAY,0);
			  cal.set(Calendar.MINUTE,0);
			  cal.set(Calendar.SECOND,0);
			  cal.set(Calendar.MILLISECOND,0);
			 
			 List empStatusList=hrManager.getObjectsByParameter(HREmployeeEmployeeStatus.class, "employee",employee);
			 for(int i=0;i<empStatusList.size();i++)
			 {
				 HREmployeeEmployeeStatus empStatus=(HREmployeeEmployeeStatus)empStatusList.get(i);
				
				 
				  
				  Calendar cal1=Calendar.getInstance();
					 cal1.setTime(empStatus.getStatusStartDate());
					  cal1.set(Calendar.HOUR_OF_DAY,0);
					  cal1.set(Calendar.MINUTE,0);
					  cal1.set(Calendar.SECOND,0);
					  cal1.set(Calendar.MILLISECOND,0);
					 
					  if(hrEmployeeStatus.getId()!=null && !hrEmployeeStatus.equals(""))
					  {
							if(!empStatus.getId().equals(hrEmployeeStatus.getId()))
							{
							 if( cal1.getTime().equals(cal.getTime()))
							 {
								 errors.rejectValue("statusStartDate","hr.errors.thereIsExistingStatusThatOverlapsDatePeriod", "there Is Existing Status Overlaps Date Period");
							 }
							}
					  }
					  else
					  {
						  if(cal1.getTime().equals(cal.getTime()))
							 {
								 errors.rejectValue("statusStartDate","hr.errors.thereIsExistingDateThatOverlapsThisDate", "there Is Existing Status Overlaps Date Period");
							 }
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
		HREmployeeEmployeeStatus hrEmployeeStatus=(HREmployeeEmployeeStatus)command;
		
	     String formType=request.getParameter("formType");
	     log.debug("formType>>>>>>>>>>>>>>>"+formType);
	     String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		
		  log.debug("hrEmployeeStatus.getId()>>>>>>>>>>>>>>>"+hrEmployeeStatus.getId());
		  
		  Calendar cal=Calendar.getInstance();
			 cal.setTime(hrEmployeeStatus.getStatusStartDate());
			  cal.set(Calendar.HOUR_OF_DAY,0);
			  cal.set(Calendar.MINUTE,0);
			  cal.set(Calendar.SECOND,0);
			  cal.set(Calendar.MILLISECOND,0);
			  hrEmployeeStatus.setStatusStartDate(cal.getTime());
			  HREmployee employee=null;
		  if((employeeId!=null && !employeeId.equals("")) )
		  {
		   employee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
		   
		  }
		  
		  if(hrEmployeeStatus.getId()==null ||hrEmployeeStatus.getId().equals(""))
		  {
			  hrEmployeeStatus.setEmployee(employee);
		  }
		  
		    
			hrManager.saveObject(hrEmployeeStatus);
			
		log.debug("employee.getCurrentEmployeeStatus().getId()>>>>>>>>>>>>>"+employee.getCurrentEmployeeStatus().getId())	;
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		if(formType.equals("employee"))
		{
		  return new ModelAndView(new RedirectView("employeeStatusView.html?employeeId="+employeeId+"&formType="+formType));
		}
		
		return new ModelAndView(new RedirectView("employeeStatusView.html?formType="+formType));
	}
	




}
