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

//import com._4s_.HR.model.EmployeeView;
import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HREmployeeEmployeeStatus;
import com._4s_.HR.model.HREmployeeSponsor;
import com._4s_.HR.model.HRSponsor;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class EmployeeSponsorForm extends BaseSimpleFormController {
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
		HREmployeeSponsor hrEmployeeSponsor=null;
		String empSponsorId=request.getParameter("empSponsorId");
		log.debug("empSponsorId>>>>>>>>>>>>"+empSponsorId);
		//in case of editing object
		if(empSponsorId!=null && !empSponsorId.equals(""))
		{
			hrEmployeeSponsor=(HREmployeeSponsor)hrManager.getObject(HREmployeeSponsor.class, new Long(empSponsorId));
		}
		
		else
		{
			
		       hrEmployeeSponsor=new HREmployeeSponsor();
		}
 
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return hrEmployeeSponsor;
	}
//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 Map model=new HashMap();
		 HREmployeeSponsor hrEmployeeSponsor=(HREmployeeSponsor)command;
		
		 String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		  model.put("employeeId", employeeId);
		 	String empSponsorId=request.getParameter("empSponsorId");
		     model.put("empSponsorId", empSponsorId);
		     
		     String formType=request.getParameter("formType");
		     log.debug("formType>>>>>>>>>>>>>>>"+formType);
		      model.put("formType", formType);
		     
			List sponsorList=hrManager.getObjects(HRSponsor.class);
			model.put("sponsorList", sponsorList);	
			
//			List employeeList=hrManager.getObjects(EmployeeView.class);
//				log.debug("employeeList.size()>>>>>>>>>>>>>>>>>"+employeeList.size());
//	
		
		
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
		 HREmployeeSponsor hrEmployeeSponsor=(HREmployeeSponsor)command;
		 if(errors.getErrorCount()==0)
			{
				if(hrEmployeeSponsor.getSponsor()==null || hrEmployeeSponsor.getSponsor().equals(""))
				{
				  errors.rejectValue("sponsor", "commons.errors.requiredFields", "required Fields");
				}
			}
		 
		if(errors.getErrorCount()==0)
		{
			if(hrEmployeeSponsor.getSponsorStartDate()==null || hrEmployeeSponsor.getSponsorStartDate().equals(""))
			{
			  errors.rejectValue("sponsorStartDate", "commons.errors.requiredFields", "required Fields");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			if(hrEmployeeSponsor.getSponsorEndDate()==null || hrEmployeeSponsor.getSponsorEndDate().equals(""))
			{
			  errors.rejectValue("sponsorEndDate", "commons.errors.requiredFields", "required Fields");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			if(hrEmployeeSponsor.getSponsorEndDate().before(hrEmployeeSponsor.getSponsorStartDate()))
			{
			  errors.rejectValue("sponsorEndDate", "hr.errors.endDateMustBeLargerThanStartDate", "endDateMustBeLargerThanStartDate");
			}
		}
		
		 String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		  HREmployee employee=null;
		  if(employeeId!=null && !employeeId.equals(""))
		  {
			  employee=(HREmployee)hrManager.getObject(HREmployee.class,new Long(employeeId));
		  } 
		
		 if(errors.getErrorCount()==0)
		 {
			 Calendar cal=Calendar.getInstance();
			 cal.setTime(hrEmployeeSponsor.getSponsorStartDate());
			  cal.set(Calendar.HOUR_OF_DAY,0);
			  cal.set(Calendar.MINUTE,0);
			  cal.set(Calendar.SECOND,0);
			  cal.set(Calendar.MILLISECOND,0);
			 
			 List empSponsorList=hrManager.getObjectsByParameter(HREmployeeSponsor.class, "employee",employee);
			 for(int i=0;i<empSponsorList.size();i++)
			 {
				 HREmployeeSponsor empSponsor=(HREmployeeSponsor)empSponsorList.get(i);
				
				 
				  
				  Calendar cal1=Calendar.getInstance();
					 cal1.setTime(empSponsor.getSponsorStartDate());
					  cal1.set(Calendar.HOUR_OF_DAY,0);
					  cal1.set(Calendar.MINUTE,0);
					  cal1.set(Calendar.SECOND,0);
					  cal1.set(Calendar.MILLISECOND,0);
					  if(hrEmployeeSponsor.getId()!=null && !hrEmployeeSponsor.equals(""))
					  {
							if(!empSponsor.getId().equals(hrEmployeeSponsor.getId()))
							{
							 if(cal1.getTime().equals(cal.getTime()))
							 {
								 errors.rejectValue("sponsorStartDate","hr.errors.thereIsExistingDateThatOverlapsThisDate", "there Is Existing Date Overlaps Date Period");
							 }
							}
					  }
					  else
					  {
						  if( cal1.getTime().equals(cal.getTime()))
							 {
								 errors.rejectValue("sponsorStartDate","hr.errors.thereIsExistingStatusThatOverlapsDatePeriod", "there Is Existing Status Overlaps Date Period");
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
		HREmployeeSponsor hrEmployeeSponsor=(HREmployeeSponsor)command;
		
	     String formType=request.getParameter("formType");
	     log.debug("formType>>>>>>>>>>>>>>>"+formType);
	     String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		
		  log.debug("hrEmployeeSponsor.getId()>>>>>>>>>>>>>>>"+hrEmployeeSponsor.getId());
		  
		  Calendar cal=Calendar.getInstance();
			 cal.setTime(hrEmployeeSponsor.getSponsorStartDate());
			  cal.set(Calendar.HOUR_OF_DAY,0);
			  cal.set(Calendar.MINUTE,0);
			  cal.set(Calendar.SECOND,0);
			  cal.set(Calendar.MILLISECOND,0);
			  hrEmployeeSponsor.setSponsorStartDate(cal.getTime());
			  
			  Calendar cal1=Calendar.getInstance();
				 cal1.setTime(hrEmployeeSponsor.getSponsorEndDate());
				  cal1.set(Calendar.HOUR_OF_DAY,0);
				  cal1.set(Calendar.MINUTE,0);
				  cal1.set(Calendar.SECOND,0);
				  cal1.set(Calendar.MILLISECOND,0);
				  hrEmployeeSponsor.setSponsorEndDate(cal1.getTime());
				  
				  HREmployee employee=null;
		  if((employeeId!=null && !employeeId.equals("")) )
		  {
		     employee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
		 
		  }
		  
		 if (hrEmployeeSponsor.getId()==null ||hrEmployeeSponsor.getId().equals(""))
		  
		 {
			 hrEmployeeSponsor.setEmployee(employee);
		 }
		  
		    
			hrManager.saveObject(hrEmployeeSponsor);
			
			log.debug("employee.getCurrentEmployeeSponsor().getId()"+employee.getCurrentEmployeeSponsor().getId());
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		if(formType.equals("employee"))
		{
		  return new ModelAndView(new RedirectView("employeeSponsorsView.html?employeeId="+employeeId+"&formType="+formType));
		}
		
		return new ModelAndView(new RedirectView("employeeSponsorsView.html?formType="+formType));
	
	}




}


