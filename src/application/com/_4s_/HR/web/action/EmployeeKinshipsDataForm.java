package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HREmployeeRelative;
import com._4s_.HR.model.HRKinship;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class EmployeeKinshipsDataForm extends BaseSimpleFormController {
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
		HREmployeeRelative hrEmployeeRelative=null;
		String empRelativeId=request.getParameter("empRelativeId");
		log.debug("empRelativeId>>>>>>>>>>>>"+empRelativeId);
		//in case of editing object
		if(empRelativeId!=null && !empRelativeId.equals(""))
		{
			hrEmployeeRelative=(HREmployeeRelative)hrManager.getObject(HREmployeeRelative.class, new Long(empRelativeId));
		}
		
		else
		{
			
		       hrEmployeeRelative=new HREmployeeRelative();
		     
		}
    
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return hrEmployeeRelative;
	}
//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 Map model=new HashMap();
		 HREmployeeRelative hrEmployeeRelative=(HREmployeeRelative)command;
		 String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		  model.put("employeeId", employeeId);
		 	String empRelativeId=request.getParameter("empRelativeId");
		     model.put("empRelativeId", empRelativeId);
		     
		     String formType=request.getParameter("formType");
		     log.debug("formType>>>>>>>>>>>>>>>"+formType);
		      model.put("formType", formType);
		     
			model.put("kinshipTypes",hrManager.getObjects(HRKinship.class) );
		
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
		HREmployeeRelative hrEmployeeRelative=(HREmployeeRelative)command;
		
	     String formType=request.getParameter("formType");
	     log.debug("formType>>>>>>>>>>>>>>>"+formType);
	     String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		
		  if(employeeId!=null && !employeeId.equals(""))
		  {
		  HREmployee employee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
		  hrEmployeeRelative.setEmployee(employee);
		  }
		  
		
		    
			hrManager.saveObject(hrEmployeeRelative);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		if(formType.equals("employee"))
		{
		  return new ModelAndView(new RedirectView("employeeKinshipsDataView.html?employeeId="+employeeId+"&formType="+formType));
		}
		
		return new ModelAndView(new RedirectView("employeeKinshipsDataView.html?formType="+formType));
	}
	




}
