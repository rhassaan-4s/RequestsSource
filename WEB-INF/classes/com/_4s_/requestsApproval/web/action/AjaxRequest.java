package com._4s_.requestsApproval.web.action;


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

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.AccessLevels;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.GroupAcc;
import com._4s_.requestsApproval.service.RequestsApprovalManager;

public class AjaxRequest extends BaseSimpleFormController{
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
		String requestTypeId=request.getParameter("requestTypeId");
		log.debug("--------requestTypeId------"+requestTypeId);
		
		AccessLevels accessLevel=new AccessLevels();;
		
		if(requestTypeId==null || requestTypeId.equals(""))
		{
			log.debug("--------requestTypeId==null------");
			//accessLevel=new AccessLevels();
			
//			//to put code automatically
//			List requests=requestsApprovalManager.getObjects(Requests.class);
//			List codesList=new ArrayList();
//			Iterator itr=requests.iterator();
//			
//			while(itr.hasNext())
//			{
//				Requests req=(Requests)itr.next();
//				codesList.add(Integer.parseInt(req.getCode()));
//				log.debug("--code before zerofill----req.getCode()"+req.getCode());
//			}
//			
//			String code = requestsApprovalManager.zeroFill(codesList.toArray(),8);
//			log.debug("code after zerofill----requestType"+code);
//			requestType.setCode(code);
		}
		
		else
		{
			//requestType=(Requests)requestsApprovalManager.getObject(Requests.class, new Long(requestTypeId));
		}
		//log.debug("---------requestType.getId()-------"+requestType.getId());
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	  model.addAttribute(accessLevel);
		return "ajaxRequest";
	}
	
	//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
	{
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//
//
	Map model=new HashMap();
	String accLevel=request.getParameter("accLevel");
	String groupId=request.getParameter("groupId");
	String grouptitle=request.getParameter("grouptitle");
	String accLevelall=request.getParameter("accLevelall");
	String suc_msg="";

	
	    
		if(groupId.equals("-1")&&accLevel!=null){  
			try{
			requestsApprovalManager.removeObject(AccessLevels.class,new Long(accLevel));
			suc_msg="ok";
			}
			catch (Exception e) {
				// TODO: handle exception
				  suc_msg="noDelete";
			}
			
			
		}else if(groupId!=null&&accLevel!=null){
			
		 GroupAcc groupAcc=(GroupAcc)requestsApprovalManager.getObject(GroupAcc.class,new Long(groupId)); //deleteLevel(new Long(accLevel), "AccessLevels");
		 List empReqTypeAcc=requestsApprovalManager.getObjectsByParameter(EmpReqTypeAcc.class,"group_id",groupAcc); 
		 if(empReqTypeAcc.size()==0){			 
			 requestsApprovalManager.removeObject(AccessLevels.class,new Long(accLevel));
			 requestsApprovalManager.removeObject(GroupAcc.class,new Long(groupId));
			 suc_msg="ok";
			
		   }else{
			   
			     suc_msg="no";
			     
			   }
		}

		if(accLevelall!=null){
			 GroupAcc groupAcc=(GroupAcc)requestsApprovalManager.getObject(GroupAcc.class,new Long(groupId)); //deleteLevel(new Long(accLevel), "AccessLevels");
			 List empReqTypeAcc=requestsApprovalManager.getObjectsByParameter(EmpReqTypeAcc.class,"group_id",groupAcc); 
			 if(empReqTypeAcc.size()==0){
				 
			     requestsApprovalManager.removeObject(GroupAcc.class,new Long(groupId));
				 suc_msg="ok";
				
			   }else{
				   
				     suc_msg="no";
				     
				   }
			
			
		}
		model.put("suc_msg", suc_msg);


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
		Map model=new HashMap();
		String suc_msg="";
		String groupId=request.getParameter("groupId");
		String grouptitle=request.getParameter("grouptitle");
		
 		GroupAcc groupAcc=new GroupAcc();
		
	    
		GroupAcc req=(GroupAcc)requestsApprovalManager.getObjectByParameter(GroupAcc.class,"title",grouptitle);
	
	
 		if(req==null){ 			
 		  groupAcc=(GroupAcc)requestsApprovalManager.getObject(GroupAcc.class, new Long(groupId));
 		 groupAcc.setTitle(grouptitle);
 		 requestsApprovalManager.saveObject(groupAcc);
 		 suc_msg="ok";
 		}
 		else
 		suc_msg="no";
 		model.put("suc_msg", suc_msg);
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView("ajaxRequest",model);
	}
}
