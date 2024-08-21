package com._4s_.requestsApproval.web.action;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Employee;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.common.web.binders.DomainObjectBinder;
import com._4s_.requestsApproval.model.AccessLevels;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.security.model.User;

@Controller
@RequestMapping("/changeProfileForm.html")
public class ChangeProfileForm extends BaseSimpleFormController{
	@Autowired
	RequestsApprovalManager requestsApprovalManager;
	@Autowired
	@Qualifier("loginUsersBinder")
	private DomainObjectBinder loginUsersBinder;

	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}
	
	public void initBinder(HttpServletRequest request,WebDataBinder binder) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Starting init binder: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		super.initBinder(request,binder);
		binder.registerCustomEditor(LoginUsers.class, loginUsersBinder);
	}
	
	@RequestMapping(method = RequestMethod.GET)  
	public String initForm(ModelMap model,HttpServletRequest request){
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String requestTypeId=request.getParameter("requestTypeId");
		log.debug("--------requestTypeId------"+requestTypeId);
		
		AccessLevels accessLevel=new AccessLevels();;
		model.addAttribute(accessLevel);
		return "changeProfileForm";
	}
	
	//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")	
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		
		
		Map model=new HashMap();
		String messagNo=request.getParameter("messagNo");
		if(messagNo==null)messagNo="1";
		model.put("messagNo", messagNo);
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}

	//**************************************** onBind ***********************************************\\	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
//		String groupTitle=request.getParameter("groupTitle");
//	
//		    
//			GroupAcc req=(GroupAcc)requestsApprovalManager.getObjectByParameter(GroupAcc.class,"title",groupTitle);
//		
//		
//		if(errors.getErrorCount()==0)
//		{
//		
//			if(req!=null)
//			{
//				errors.reject("requestsApproval.errors.invalidEmpCodeOrName");
//			}
//			
//		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
//**************************************** onBindAndValidate ***********************************************\\
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{	
		
		AccessLevels accessLevel=(AccessLevels) command;
		String currentpass=request.getParameter("currentPassword");
		String newpass=request.getParameter("newPassword");
		String messageNo ="1";
        Employee emp =(Employee) request.getSession().getAttribute("employee");
		log.debug("----emp---"+emp);
		log.debug("----username---"+emp.getUser().getUsername());
		User lUsers=(User) requestsApprovalManager.getObjectByParameter(User.class, "username", emp.getUser().getUsername());
		
		

		if(lUsers.getPassword().endsWith(currentpass)){
			
			if(newpass!=null&&!newpass.equals("")){
				lUsers.setPassword(newpass);
				requestsApprovalManager.saveObject(lUsers);
				messageNo="3";
			}
		
		}
		else
		messageNo="2";
		
		
		
		String url="changeProfileForm.html?messagNo="+messageNo;

		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<  End onSubmit : <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		return new ModelAndView(new RedirectView(url));

	}
}
