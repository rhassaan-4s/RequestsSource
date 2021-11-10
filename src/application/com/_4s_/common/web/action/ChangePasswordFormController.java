package com._4s_.common.web.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.web.command.ChangePassword;

@Controller
@RequestMapping("/commonAdminChangePassword.html")
public class ChangePasswordFormController  extends BaseSimpleFormController{
	 

	public ModelAndView onSubmit(HttpServletRequest request,
				HttpServletResponse response,
				Object command, BindException errors)
		throws Exception{
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
