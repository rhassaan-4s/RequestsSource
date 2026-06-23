package com._4s_.requestsApproval.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.GroupAcc;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.service.RequestsApprovalManager;

@Controller
@RequestMapping("/addNewEmpGroup.html")
public class AddNewEmpGroup extends BaseSimpleFormController {
	
	@Autowired
	private RequestsApprovalManager requestsApprovalManager;


	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}

	@Validated
	@RequestMapping(method = RequestMethod.POST) // ,consumes=MediaType.APPLICATION_FORM_URLENCODED
	public ModelAndView processSubmit(HttpServletRequest request,
			@Valid @ModelAttribute("empGroup") EmpReqTypeAcc command, BindingResult errors,
			SessionStatus status, Map model) {
		log.debug(">>>>>>>>>>>>>>>.start processsubmit>>>>>>>>>>>");
//		List fieldOrderList= new ArrayList();
//		fieldOrderList.add("emp_id");
		String empId = request.getParameter("empIdHidden");
		String empCode = request.getParameter("empCodeHidden");
		
		String reguestType = request.getParameter("reguestType");
		String groupId = request.getParameter("groupId");
		LoginUsers loginUsers = null;
		RequestTypes reqType = null;
		GroupAcc group = null;
		
		if((empId!=null&&!empId.equals(""))){
			loginUsers=(LoginUsers) requestsApprovalManager.getObject(LoginUsers.class, new Long(empId));
		}
		log.debug("######### emp id " + empId);
		log.debug("######### emp code " + empCode);
		log.debug("loginuser " + loginUsers);
		command.setEmp_id(loginUsers);
		
		
		if((reguestType!=null&&!reguestType.equals(""))){
			reqType=(RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, new Long(reguestType));
		}
		command.setReq_id(reqType);
		if((groupId!=null&&!groupId.equals(""))){
			group=(GroupAcc) requestsApprovalManager.getObject(GroupAcc.class, new Long(groupId));
		}
		command.setGroup_id(group);
		
		List groupAvailableForReqType=(List)requestsApprovalManager.getObjectsByTwoObjects(
				EmpReqTypeAcc.class,"emp_id", command.getEmp_id(),"req_id", command.getReq_id());
		log.debug("********************access levels already available size " + groupAvailableForReqType.size());
		command.setOrder(groupAvailableForReqType.size()+1);
		baseManager.saveObject(command);
//		String url="empGroupsView.html?empId="+empId + "&empCode="+empCode;
		String url = "addNewEmpGroup.html?empId="+empId+"&empCode="+empCode+"&close=true";
		return new ModelAndView(new RedirectView(url));
//		return new ModelAndView();
	}

	@ModelAttribute("model")	
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map model = new HashMap();
		
		List requestTypes = baseManager.getObjects(RequestTypes.class);
		List groups = baseManager.getObjects(GroupAcc.class);
		
		String empId = request.getParameter("empId");
		String empCode = request.getParameter("empCode");
		String close = request.getParameter("close");
		
		model.put("requestTypes", requestTypes);
		model.put("groups",groups);
		model.put("empIdHidden", empId);
		model.put("empCodeHidden", empCode);
		model.put("close", close);
		return model;
	}

	@RequestMapping(method = RequestMethod.GET)  
	public String initForm(ModelMap model,HttpServletRequest request){
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>.start formBackingObject()>>>>>>>>>>>");
		EmpReqTypeAcc empGroup = new EmpReqTypeAcc();
		String empId = request.getParameter("empId");
		String empCode = request.getParameter("empCode");
		String close = request.getParameter("close");
		model.put("empIdHidden", empId);
		model.put("empCodeHidden", empCode);
		model.put("close", close);
		LoginUsers loginUsers = null;
		if((empId!=null&&!empId.equals(""))){
			loginUsers=(LoginUsers) requestsApprovalManager.getObject(LoginUsers.class, new Long(empId));
		}
		log.debug("********emp id " + empId);
		log.debug("********emp code " + empCode);
		log.debug("loginuser " + loginUsers);
		empGroup.setEmp_id(loginUsers);
		model.addAttribute(empGroup);
		return "addNewEmpGroup";
	}
	
//	@Override
//	public void initBinder(HttpServletRequest request,WebDataBinder binder) {
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Starting init binder: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		super.initBinder(request,binder);
//		binder.registerCustomEditor(LoginUsers.class, loginUsersBinder);
//	}

}
