package com._4s_.common.web.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.web.command.ChangePassword;
import com._4s_.requestsApproval.model.LoginUsersRequests;

@Controller
@RequestMapping("/commonAdminChangePassword.html")
public class ChangePasswordFormController  extends BaseSimpleFormController{
	 

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(HttpServletRequest request,
			@Valid @ModelAttribute("changePassword") ChangePassword command,
			BindingResult result, SessionStatus status,Model model) {
//		ChangePassword changePassword = (ChangePassword) command;
//		SecureContext sc = (SecureContext)(ContextHolder.getContext());
//		MyUser obj =  (MyUser)sc.getAuthentication().getPrincipal();	
//		String userName= obj.getUsername();
		//Account account = (Account)baseManager.getObjectByParameter(Account.class,"userName",userName);
	//	log.debug(">>>>>>>>>>>>>>>>>>> account "+account);
	//	account.setPassword(changePassword.getNewPassword());
		
		//baseManager.saveObject(account);
		String successMessage ="You've successfully changed your password. Please Re-login.";
		return new ModelAndView(new RedirectView("login.html"),"successMessage",successMessage);
	}
	
	protected Object formBackingObject (HttpServletRequest request)
	throws ServletException{
		ChangePassword changePassword = new ChangePassword();
		return  changePassword;
	}
}
