package com._4s_.requestsApproval.web.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.model.Requests;
import com._4s_.requestsApproval.service.RequestsApprovalManager;

public class RequestTypesForm extends BaseSimpleFormController{
	RequestsApprovalManager requestsApprovalManager;

	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String requestTypeId=request.getParameter("requestTypeId");
		log.debug("--------requestTypeId------"+requestTypeId);
		
		Requests requestType;
		
		if(requestTypeId==null || requestTypeId.equals(""))
		{
			log.debug("--------requestTypeId==null------");
			requestType=new Requests();
			
			//to put code automatically
			List requests=requestsApprovalManager.getObjects(Requests.class);
			List codesList=new ArrayList();
			Iterator itr=requests.iterator();
			
			while(itr.hasNext())
			{
				Requests req=(Requests)itr.next();
				codesList.add(Integer.parseInt(req.getCode()));
				log.debug("--code before zerofill----req.getCode()"+req.getCode());
			}
			
			String code = requestsApprovalManager.zeroFill(codesList.toArray(),8);
			log.debug("code after zerofill----requestType"+code);
			requestType.setCode(code);
		}
		
		else
		{
			requestType=(Requests)requestsApprovalManager.getObject(Requests.class, new Long(requestTypeId));
		}
		log.debug("---------requestType.getId()-------"+requestType.getId());
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return requestType;
	}
	
	//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Requests requestType= (Requests) command;
		log.debug("-----attendanceReq.code----"+requestType.getCode());
		Map model=new HashMap();
		String requestTypeId=request.getParameter("requestTypeId");
		log.debug("requestTypeId------"+requestTypeId);
		model.put("requestTypeId",requestTypeId);
		List requestTypeList=requestsApprovalManager.getObjects(RequestTypes.class);
		model.put("requestTypeList", requestTypeList);
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
		Requests requestType= (Requests) command;
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
		Requests requestType= (Requests) command;
		log.debug("----requestType.getId()-onsubmit-----"+requestType.getId());
		
		requestsApprovalManager.saveObject(requestType);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("requestTypesView.html"));
	}
}
