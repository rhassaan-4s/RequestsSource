package com._4s_.requestsApproval.web.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.CompanyDetails;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.service.RequestsApprovalManager;

public class CompanyDetailsForm extends BaseSimpleFormController{
	@Autowired
	RequestsApprovalManager requestsApprovalManager;

	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}
	
	@RequestMapping(method = RequestMethod.GET)  
	public String initForm(ModelMap model,HttpServletRequest request){
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String companyId=request.getParameter("companyId");
		log.debug("--------companyId------"+companyId);
		
		CompanyDetails companyDetails;
		
		if(companyId==null || companyId.equals(""))
		{
			log.debug("--------companyId==null------");
			companyDetails=new CompanyDetails();
			
			//to put code automatically
			List requests=requestsApprovalManager.getObjects(CompanyDetails.class);
			List codesList=new ArrayList();
			Iterator itr=requests.iterator();
			
		}
		
		else
		{
			companyDetails=(CompanyDetails)requestsApprovalManager.getObject(CompanyDetails.class, new Long(companyId));
		}
		log.debug("---------requestType.getId()-------"+companyDetails.getId());
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute(companyDetails);
	   return "companyDetails";
	}
	
	//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")	
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			@ModelAttribute CompanyDetails command,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		CompanyDetails companyDetails= (CompanyDetails) command;
//		log.debug("-----attendanceReq.code----"+companyDetails.getCode());
		Map model=new HashMap();
		String companyId=request.getParameter("companyId");
		log.debug("companyId------"+companyId);
		model.put("companyId",companyId);
		List companyDetail=requestsApprovalManager.getObjects(CompanyDetails.class);
		model.put("companyDetail", companyDetail);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}

	//**************************************** onBind ***********************************************\\	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
//**************************************** onBindAndValidate ***********************************************\\
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		CompanyDetails companyDetails= (CompanyDetails) command;
//		if(errors.getErrorCount()==0)
//		{
//		
//			if(attendanceRequest.getCode().length()>8)
//			{
//				errors.rejectValue("code", "hr.errors.codeLargerThanExpected","hr.errors.codeLargerThanExpected");
//			}
//		}
//		
//		if(errors.getErrorCount()==0)
//		{
//			AttendanceRequest req=(AttendanceRequest)attendanceManager.getObjectByParameter(AttendanceRequest.class,"code",attendanceRequest.getCode());
//			if(req!=null && (!req.getId().equals(attendanceRequest.getId())))
//					{
//				       errors.rejectValue("code", "hr.error.codeExists","code exists");
//					}
//		}
//		
//		if(errors.getErrorCount()==0)
//		{
//		
//			if(attendanceRequest.getName()==null || attendanceRequest.getName().equals(""))
//			{
//				errors.rejectValue("name", "commons.errors.requiredFields");
//			}
//			
//		}

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{	
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		CompanyDetails companyDetails= (CompanyDetails) command;
		log.debug("----companyDetails.getId()-onsubmit-----"+companyDetails.getId());
		
		requestsApprovalManager.saveObject(companyDetails);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView(""));
	}
}
