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
import com._4s_.HR.model.HREmployeePenalty;
import com._4s_.HR.model.Punishment;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.LoginUsersRequests;

public class EmployeePenaltyForm extends BaseSimpleFormController {
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
		HREmployeePenalty hrEmployeePenalty=null;
		String empPenaltyId=request.getParameter("empPenaltyId");
		log.debug("empPenaltyId>>>>>>>>>>>>"+empPenaltyId);
		//in case of editing object
		if(empPenaltyId!=null && !empPenaltyId.equals(""))
		{
			hrEmployeePenalty=(HREmployeePenalty)hrManager.getObject(HREmployeePenalty.class, new Long(empPenaltyId));
		}
		
		else
		{
			
		       hrEmployeePenalty=new HREmployeePenalty();
		}
 
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute(hrEmployeePenalty);
	   return "employeePenaltyForm";
	}
//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")	
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,@ModelAttribute HREmployeePenalty command,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 Map model=new HashMap();
		 HREmployeePenalty hrEmployeePenalty=(HREmployeePenalty)command;
		
		 String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		  model.put("employeeId", employeeId);
		 	String empPenaltyId=request.getParameter("empPenaltyId");
		     model.put("empPenaltyId", empPenaltyId);
		     
		     String formType=request.getParameter("formType");
		     log.debug("formType>>>>>>>>>>>>>>>"+formType);
		      model.put("formType", formType);
		     
			List penaltyList=hrManager.getObjects(Punishment.class);
			model.put("penaltyList", penaltyList);	
				
	
		
		
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
		HREmployeePenalty hrEmployeePenalty=(HREmployeePenalty)command;
		
	     String formType=request.getParameter("formType");
	     log.debug("formType>>>>>>>>>>>>>>>"+formType);
	     String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		
		  log.debug("hrEmployeePenalty.getId()>>>>>>>>>>>>>>>"+hrEmployeePenalty.getId());
		  if((employeeId!=null && !employeeId.equals("")) && (hrEmployeePenalty.getId()==null ||hrEmployeePenalty.getId().equals("")))
		  {
		  HREmployee employee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
		  hrEmployeePenalty.setEmployee(employee);
		  }
		  
		    
			hrManager.saveObject(hrEmployeePenalty);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		if(formType.equals("employee"))
		{
		  return new ModelAndView(new RedirectView("employeePenaltyView.html?employeeId="+employeeId+"&formType="+formType));
		}
		
		return new ModelAndView(new RedirectView("employeePenaltyView.html?formType="+formType));
	}
	




}
