package com._4s_.HR.web.action;

import java.util.Date;
import java.util.HashMap;
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
import com._4s_.HR.model.HREmployeeErrand;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.LoginUsersRequests;

public class EmployeeErrandForm extends BaseSimpleFormController {
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
		HREmployeeErrand hrEmployeeErrand=null;
		String empErrandId=request.getParameter("empErrandId");
		log.debug("empErrandId>>>>>>>>>>>>"+empErrandId);
		//in case of editing object
		if(empErrandId!=null && !empErrandId.equals(""))
		{
			hrEmployeeErrand=(HREmployeeErrand)hrManager.getObject(HREmployeeErrand.class, new Long(empErrandId));
		}
		
		else
		{
			
		       hrEmployeeErrand=new HREmployeeErrand();
		}

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute("hrEmployeeErrand",hrEmployeeErrand);
	   return "employeeErrandForm";
	}
//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,@ModelAttribute HREmployeeErrand command,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 Map model=new HashMap();
		 HREmployeeErrand hrEmployeeErrand=(HREmployeeErrand)command;
		
		 String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		  model.put("employeeId", employeeId);
		 	String empErrandId=request.getParameter("empErrandId");
		     model.put("empErrandId", empErrandId);
		     
		     String formType=request.getParameter("formType");
		     log.debug("formType>>>>>>>>>>>>>>>"+formType);
		      model.put("formType", formType);
		     
		
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
		HREmployeeErrand hrEmployeeErrand=(HREmployeeErrand)command;
		
	     String formType=request.getParameter("formType");
	     log.debug("formType>>>>>>>>>>>>>>>"+formType);
	     String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		
		  log.debug("hrEmployeeErrand.getId()>>>>>>>>>>>>>>>"+hrEmployeeErrand.getId());
		  if((employeeId!=null && !employeeId.equals("")) && (hrEmployeeErrand.getId()==null ||hrEmployeeErrand.getId().equals("")))
		  {
		  HREmployee employee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
		  hrEmployeeErrand.setEmployee(employee);
		  }
		  
		    
			hrManager.saveObject(hrEmployeeErrand);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		if(formType.equals("employee"))
		{
		  return new ModelAndView(new RedirectView("employeeErrandView.html?employeeId="+employeeId+"&formType="+formType));
		}
		
		return new ModelAndView(new RedirectView("employeeErrandView.html?formType="+formType));
	}
	




}


