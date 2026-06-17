package com._4s_.requestsApproval.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Employee;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.GroupAcc;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
@Controller	
public class EmpGroupsView {// implements Controller{
	@Autowired
	RequestsApprovalManager requestsApprovalManager;
	protected final Log log = LogFactory.getLog(getClass());
	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}
	
	
	@RequestMapping("/empGroupsView.html")
	public ModelAndView handleRequest(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {	
		// TODO Auto-generated method stub

//		Map model=new HashMap();
		
		Map userdata=new HashMap();
		
		String data="no";
		
		String deleteParam = request.getParameter("delete");
		String editParam = request.getParameter("edit");
		String saveParam = request.getParameter("save");

		String done=request.getParameter("done");
		if(done!=null){
			model.addAttribute("done", done);
		}
		
		List requList=new ArrayList();
		List info=new ArrayList();
		
		requList.add("order");
		
		String empId=request.getParameter("empId");
		log.debug("empId ------ " + empId);
		if (empId==null || empId.isEmpty()) {
			empId=request.getParameter("empIdHidden");
		}
		model.addAttribute("empId", empId);
		String empCode=request.getParameter("empCode");
		log.debug("empCode ==>> " + empCode);
		if (empCode==null || empCode.isEmpty()) {
			empCode=request.getParameter("empCodeHidden");
		}
		
		
		
		
		LoginUsers  loginUsers = null;
		GroupAcc  group = null;
		RequestTypes  reqType = null;
//		String groupId=request.getParameter("groupId");
//		log.debug("----groupId ==>> " + groupId);
//		model.put("groupId", groupId);
		
		String requestId=request.getParameter("requestId");
		log.debug("xxx----requestId ==>> " + requestId);
		if((requestId!=null&&!requestId.equals(""))) {
			reqType=(RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, new Long(requestId));
		}
		model.addAttribute("requestId", requestId);
		
		String groupId=request.getParameter("groupId");
		log.debug("new group id " + groupId);
		if((groupId!=null&&!groupId.equals(""))) {
			group=(GroupAcc) requestsApprovalManager.getObject(GroupAcc.class, new Long(groupId));
		}
		
		String empReqTypeAccId = request.getParameter("empReqTypeAcc");
		
		if((empCode!=null&&!empCode.equals(""))){
		 
			Employee emp = (Employee) requestsApprovalManager.getObjectByParameter(Employee.class, "empCode", empCode)	;
//			loginUsers=(LoginUsers) requestsApprovalManager.getObject(LoginUsers.class, new Long(empId));
			loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
			if (saveParam!=null && saveParam.equals("true")){
				EmpReqTypeAcc empAccess = null;
				log.debug("empReqTypeAccId " + empReqTypeAccId);
				if((empReqTypeAccId!=null&&!empReqTypeAccId.equals(""))) {
					empAccess=(EmpReqTypeAcc) requestsApprovalManager.getObject(EmpReqTypeAcc.class, new Long(empReqTypeAccId));
				}
				log.debug("empAccess " + empAccess.getGroup_id());
				empAccess.setGroup_id(group);
				requestsApprovalManager.saveObject(empAccess);
				log.debug("************saved modified access group");
//				requestsApprovalManager.flush();
			}
			List requeststy=requestsApprovalManager.getObjects(RequestTypes.class);
			for(int i=0;i<requeststy.size();i++){
				userdata=new HashMap();
				RequestTypes requestTypes=(RequestTypes)requeststy.get(i);		
				if (((deleteParam == null || deleteParam.isEmpty() || deleteParam.equals("false")) && (editParam == null || editParam.isEmpty() || editParam.equals("false"))) || saveParam!=null && saveParam.equals("true")) {
					log.debug("****getting list of access for user " + loginUsers.getId() + " and request type " + requestTypes.getId());
					List empReqTypeAcc = requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(
							EmpReqTypeAcc.class, "emp_id", loginUsers, "req_id", requestTypes, requList);
					log.debug("****new list size " + empReqTypeAcc.size());
					if (empReqTypeAcc.size() != 0) {
						userdata.put("title", requestTypes);
						userdata.put("list", empReqTypeAcc);
						data = "yes";
						info.add(userdata);
					}
				} else {
					userdata.put("title", requestTypes);
					data = "yes";
					info.add(userdata);
				}
			}
//			String group=request.getParameter("groupId");			
//			if(group!=null){
//				GroupAcc groupTobeDeleted=(GroupAcc) requestsApprovalManager.getObjectByParameter(GroupAcc.class, "title", group);
//				requestsApprovalManager.getObjectByTwoObjects(EmpReqTypeAcc.class, "emp_id", loginUsers, "group_id", groupTobeDeleted);
//			}

		}
		
		
		
		
		log.debug("empId " + empId);
		log.debug("requestId " + requestId);
		log.debug("groupId " + groupId);
		
		if(deleteParam!=null && empId!=null){
			if(deleteParam.equals("true")){
				List fieldOrderList= new ArrayList();
				fieldOrderList.add("emp_id");
				List tobeRemoved=(List)requestsApprovalManager.getObjectsByThreeParametersOrderedByFieldList(EmpReqTypeAcc.class,"emp_id", new Long(empId),"req_id", new Long(requestId),"group_id",new Long(groupId), fieldOrderList);
//				List tobeRemoved =(List)requestsApprovalManager.getObjectsByParameter(EmpReqTypeAcc.class, "emp_id.id", new Long(empId));
				log.debug("*****tobeRemoved " + tobeRemoved.size());
				for (int i = 0; i < tobeRemoved.size(); i++) {
					EmpReqTypeAcc obj=(EmpReqTypeAcc) tobeRemoved.get(i);
					requestsApprovalManager.removeObject(obj);
					log.debug("*****tobeRemoved  deleted");
				}
				done="true";
				deleteParam="false";
				request.removeAttribute("delete");
				saveParam="true";
				model.addAttribute("delete", deleteParam);
			}
			
			if (editParam!=null && editParam.equals("true")) {
				List fieldOrderList= new ArrayList();
				fieldOrderList.add("emp_id");
				List tobeEdited=(List)requestsApprovalManager.getObjectsByThreeParametersOrderedByFieldList(
						EmpReqTypeAcc.class,"emp_id", new Long(empId),"req_id", new Long(requestId),"group_id",new Long(groupId), 
						fieldOrderList);
				
				model.addAttribute("toBeEdited",tobeEdited);
				System.out.println("*********EMPGROUPSVIEW " + tobeEdited.size()+"************");
			}
		}

		log.debug("emp code " + empCode);
		if (empCode==null || empCode.isEmpty()) {
			if (loginUsers!=null && loginUsers.getEmpCode()!=null) {
				empCode = loginUsers.getEmpCode().getEmpCode();
			}
		}
		model.addAttribute("employeeCode", empCode);
//		if (empCode!=null) {
//			Employee emp = (Employee) requestsApprovalManager.getObjectByParameter(Employee.class, "empCode", empCode)	;	
//			LoginUsers loginUsers = (LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp)	;	
			if(loginUsers!=null){
				log.debug("----mmname---"+loginUsers.getName());
				model.addAttribute("mm_name", loginUsers.getName());

			} else {
				String empName=request.getParameter("empNameHidden");
				model.addAttribute("mm_name", empName);
			}
//		}
		
//		String empToBeDeleted=request.getParameter("empId");

		//List requests=requestsApprovalManager.getObjectsByParameter(clazz, parameter, value);


	  //	System.out.println("requests.size>>>>>>>>>>"+requests.size());

		//model.put("info", info);
			
		List groups=requestsApprovalManager.getObjects(GroupAcc.class);
		model.addAttribute("groups" , groups);
		
		model.addAttribute("data", data);
		model.addAttribute("info", info);
		
		
		model.addAttribute("edit", editParam);
		model.addAttribute("emp" , loginUsers);
		
	//	String empRecAccId=request.getParameter("empRecAccId");
		String[] empRecAccId=request.getParameterValues("empRecAccId");
		
		if(deleteParam!=null && deleteParam.equals("true")){
//			log.debug("----empRecAccId.length----"+empRecAccId.length)
//			for (int i = 0; i < empRecAccId.length; i++) {
//				EmpReqTypeAcc reqTypeAcc=(EmpReqTypeAcc) requestsApprovalManager.getObjectByParameter(EmpReqTypeAcc.class, "id", Long.parseLong(empRecAccId[i]));
//				if(reqTypeAcc!=null && !reqTypeAcc.equals("")){
//					requestsApprovalManager.removeObject(reqTypeAcc);
//				}	
//			}
			String url="empGroupsView.html?done="+done;
			return new ModelAndView(new RedirectView(url));
		}
		else if (saveParam!=null && saveParam.equals("true")) {
			String url="empGroupsView.html";//?empId="+empId + "&empCode="+empCode;
			return new ModelAndView(new RedirectView(url));
		} else {
			return new ModelAndView("empGroupsView");
		}

	}
	
	

}
