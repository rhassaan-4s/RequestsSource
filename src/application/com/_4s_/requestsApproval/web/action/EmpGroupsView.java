package com._4s_.requestsApproval.web.action;

import java.util.HashMap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;


import com._4s_.requestsApproval.model.*;
import com._4s_.requestsApproval.service.RequestsApprovalManager;

import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;

public class EmpGroupsView implements Controller{

	RequestsApprovalManager requestsApprovalManager;
	protected final Log log = LogFactory.getLog(getClass());
	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub

		Map model=new HashMap();
		
		Map userdata=new HashMap();
		
		String data="no";

		String done=request.getParameter("done");
		if(done!=null){
			model.put("done", done);
		}
		
		List requList=new ArrayList();
		List info=new ArrayList();
		
		requList.add("order");
		
		String empId=request.getParameter("empId");
		log.debug("empId ------ " + empId);
		model.put("empId", empId);
		String empCode=request.getParameter("empCode");
		log.debug("empCode ==>> " + empCode);
		model.put("employeeCode", empCode);
		
		
//		String groupId=request.getParameter("groupId");
//		log.debug("----groupId ==>> " + groupId);
//		model.put("groupId", groupId);
		
		String requestId=request.getParameter("requestId");
		log.debug("xxx----requestId ==>> " + requestId);
		model.put("requestId", requestId);
		
		if(empId!=null&&!empId.equals("")){
		 
			LoginUsers  loginUsers=(LoginUsers) requestsApprovalManager.getObject(LoginUsers.class, new Long(empId));
			 
			List requeststy=requestsApprovalManager.getObjects(RequestTypes.class);
			for(int i=0;i<requeststy.size();i++){
				userdata=new HashMap();
				RequestTypes requestTypes=(RequestTypes)requeststy.get(i);				
				List empReqTypeAcc=requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(EmpReqTypeAcc.class,"emp_id", loginUsers,"req_id", requestTypes,requList);

				if(empReqTypeAcc.size()!=0){
					userdata.put("title", requestTypes);
					userdata.put("list", empReqTypeAcc);
					data="yes";
					info.add(userdata);
				}
			}
//			String group=request.getParameter("groupId");			
//			if(group!=null){
//				GroupAcc groupTobeDeleted=(GroupAcc) requestsApprovalManager.getObjectByParameter(GroupAcc.class, "title", group);
//				requestsApprovalManager.getObjectByTwoObjects(EmpReqTypeAcc.class, "emp_id", loginUsers, "group_id", groupTobeDeleted);
//			}

		}
		
		String groupId=request.getParameter("groupId");
		String empIdDel=request.getParameter("empIdDel");
		if(request.getParameter("delete")!=null && empIdDel!=null){
			if(request.getParameter("delete").equals("true")){
				List fieldOrderList= new ArrayList();
				fieldOrderList.add("emp_id");
				List tobeRemoved=(List)requestsApprovalManager.getObjectsByThreeParametersOrderedByFieldList(EmpReqTypeAcc.class,"emp_id.id", new Long(empIdDel),"req_id.id", new Long(requestId),"group_id.id",new Long(groupId), fieldOrderList);
//				List tobeRemoved =(List)requestsApprovalManager.getObjectsByParameter(EmpReqTypeAcc.class, "emp_id.id", new Long(empIdDel));
				for (int i = 0; i < tobeRemoved.size(); i++) {
					EmpReqTypeAcc obj=(EmpReqTypeAcc) tobeRemoved.get(i);
					requestsApprovalManager.removeObject(obj);
				}
				if (request.getParameter("edit").equals("true")) {
					
				}
			}
		}

		LoginUsers loginUsers = (LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode.empCode", empCode)	;	
		if(loginUsers!=null && !loginUsers.equals("")){
			log.debug("----mmname---"+loginUsers.getName());
			model.put("mm_name", loginUsers.getName());

		}
		
//		String empToBeDeleted=request.getParameter("empId");

		//List requests=requestsApprovalManager.getObjectsByParameter(clazz, parameter, value);


	  //	System.out.println("requests.size>>>>>>>>>>"+requests.size());

		//model.put("info", info);
		
		model.put("data", data);
		model.put("info", info);
		
	//	String empRecAccId=request.getParameter("empRecAccId");
		String[] empRecAccId=request.getParameterValues("empRecAccId");
		
		if(empRecAccId!=null && !empRecAccId.equals("")){
			log.debug("----empRecAccId.length----"+empRecAccId.length);
			for (int i = 0; i < empRecAccId.length; i++) {
				EmpReqTypeAcc reqTypeAcc=(EmpReqTypeAcc) requestsApprovalManager.getObjectByParameter(EmpReqTypeAcc.class, "id", Long.parseLong(empRecAccId[i]));
				if(reqTypeAcc!=null && !reqTypeAcc.equals("")){
					requestsApprovalManager.removeObject(reqTypeAcc);
				}	
			}
			String url="empGroupsView.html?done=true";
			return new ModelAndView(new RedirectView(url),model);
		}
		else		
		return new ModelAndView("empGroupsView",model);

	}
	
	

}
