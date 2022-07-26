package com._4s_.requestsApproval.web.action;


import java.util.ArrayList;
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

import com._4s_.common.model.Settings;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.GroupAcc;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.service.RequestsApprovalManager;

public class EmpReqTypeGroupForm extends BaseSimpleFormController{
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
		
		EmpReqTypeAcc empReqTypeAcc=new EmpReqTypeAcc();;
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	  
		return empReqTypeAcc;
	}
	
	//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Map model=new HashMap();
		
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		boolean empRequestCheckDate = settings.getEmpRequestCheckDate();
		boolean empRequestTypeException = settings.getEmpRequestTypeException();
		
		List loginUsers=requestsApprovalManager.getObjectsOrderedByField(LoginUsers.class, "name");
		log.debug("-------loginUsers.size---before"+loginUsers.size());
		List currentEmps=new ArrayList();
		Date date = new Date();
		log.debug("date check 2  =  "+date);
		for (int i = 0; i < loginUsers.size(); i++) {
			LoginUsers loginUser=(LoginUsers) loginUsers.get(i);
			log.debug("----i----"+loginUser.getEmpCode());
			if((empRequestCheckDate == true && (loginUser.getEndServ()==null || loginUser.getEndServ().equals("") || loginUser.getEndServ().equals(date) || loginUser.getEndServ().after(date)))//Lotus
					||
					(empRequestCheckDate == false && (loginUser.getEndServ()==null || loginUser.getEndServ().equals("")))//Lehaa
							){
				log.debug("---before removing--i----"+loginUser.getEmpCode());
				currentEmps.add(loginUser);
//				log.debug("----login----"+loginUsers.get(i));	
			}
			
		}
		log.debug("-------currentEmps.size---after"+currentEmps.size());
		model.put("loginUsers", currentEmps);
		
		List requests=requestsApprovalManager.getObjectsByParameter(RequestTypes.class,"hidden",0);
		if (empRequestTypeException == true) {//Lotus
			List reList = new ArrayList();
			for (int i = 0; i < requests.size(); i++) {
				RequestTypes requestTypes = (RequestTypes) requests.get(i);
				if(!requestTypes.getId().equals(new Long(10)) && !requestTypes.getId().equals(new Long(11))){
					reList.add(requestTypes);
				}
			}
			model.put("requests", reList);
		} else { //Lehaa
			model.put("requests", requests);
		}
		
		List groups=requestsApprovalManager.getObjects(GroupAcc.class);
		model.put("groups", groups);
		
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
		
		String[] group=request.getParameterValues("submitgroup");
		if(group==null || group.equals("")){
			errors.reject("requestsApproval.errors.nullGroup");
		}
		String[] requests=request.getParameterValues("request");
		if(requests==null || requests.equals("")){
			errors.reject("requestsApproval.errors.nullRequests");
		}
		String[] loginUser=request.getParameterValues("loginUser");
		if(loginUser==null || loginUser.equals("")){
			errors.reject("requestsApproval.errors.nullLoginUser");
		}
		
		//Lehaa////////////////////////////////////////////////////////
		if(requests.length>0 && loginUser.length>0){
			for(int j=0;j<requests.length;j++){
				RequestTypes req_id=(RequestTypes)requestsApprovalManager.getObject(RequestTypes.class, new Long(requests[j]));
				for(int k=0;k<loginUser.length;k++){
					LoginUsers emp_id=(LoginUsers)requestsApprovalManager.getObject(LoginUsers.class, new Long(loginUser[k]));
					List fieldOrderList= new ArrayList();
					fieldOrderList.add("emp_id");
					List empReqTypeAccList =(List) requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(EmpReqTypeAcc.class, "emp_id", emp_id, "req_id", req_id, fieldOrderList);
					log.debug("-----size of list----"+empReqTypeAccList.size());
					if(empReqTypeAccList.size()>0){
						log.debug("----->0----"+empReqTypeAccList.size());
						errors.reject("requestsApproval.errors.doneBefore");
					}
				}
			}
		}
		/////////////////////////////////////////////////////////////////
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{	
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		EmpReqTypeAcc empReqTypeAcc=(EmpReqTypeAcc) command;
		
		String[] group=request.getParameterValues("submitgroup");
//		log.debug("-----group----"+group.length);
		String[] requests=request.getParameterValues("request");
//		log.debug("-----requests----"+requests.length);
		String[] loginUser=request.getParameterValues("loginUser");
//		log.debug("-----loginUser----"+loginUser.length);
		
			for(int j=0;j<requests.length;j++){
				RequestTypes req_id=(RequestTypes)requestsApprovalManager.getObject(RequestTypes.class, new Long(requests[j]));
				for(int k=0;k<loginUser.length;k++){
					LoginUsers emp_id=(LoginUsers)requestsApprovalManager.getObject(LoginUsers.class, new Long(loginUser[k]));
					List fieldOrderList= new ArrayList();
					fieldOrderList.add("emp_id");
					List empReqTypeAccList =(List) requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(EmpReqTypeAcc.class, "emp_id", emp_id, "req_id", req_id, fieldOrderList);
					log.debug("-----empReqTypeAccList----"+empReqTypeAccList.size());
					if(empReqTypeAccList.size()==0){
						log.debug("-----if----"+empReqTypeAccList.size());
						for(int i=0;i<group.length;i++){			
							GroupAcc groupAcc=(GroupAcc) requestsApprovalManager.getObject(GroupAcc.class, new Long(group[i]));
							empReqTypeAcc=new EmpReqTypeAcc();
							empReqTypeAcc.setGroup_id(groupAcc);
							empReqTypeAcc.setReq_id(req_id);
							empReqTypeAcc.setEmp_id(emp_id);
							empReqTypeAcc.setOrder(i+1);
							requestsApprovalManager.saveObject(empReqTypeAcc);
						}
					}
				}
			}
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("empReqTypeGroupForm.html"));
	}
}
