package com._4s_.security.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Company;
import com._4s_.common.model.Employee;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.security.model.User;
import com._4s_.security.web.command.ChangePasswordCommand;

@Controller
@RequestMapping("/changeUserPasswordForm.html")
public class ChangeUserPasswordFormController extends BaseSimpleFormController {

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(HttpServletRequest request,
			@Valid @ModelAttribute("changePassword") ChangePasswordCommand command, BindingResult result,
			SessionStatus status, Model model) {

		log.debug(">>>>>>>>>>>>>>>>>>> strat onSubmit");
		ChangePasswordCommand changePasswordCommand = (ChangePasswordCommand) command;
		Employee employee = (Employee) request.getSession().getAttribute("employee");
		User user = employee.getUser();
		log.debug(">>>>>>>>>>>>>>>>>>> user.getPassword " + user.getPassword());
		user.setPassword(changePasswordCommand.getNewPassword());
		baseManager.saveObject(user);
		log.debug(">>>>>>>>>>>>>>>>>>> user.getPassword " + user.getPassword());
		log.debug(">>>>>>>>>>>>>>>>>>> end onSubmit");
		String message = "true";
		return new ModelAndView(new RedirectView("changeUserPassword.html"), "message", message);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request){
		log.debug(">>>>>>>>>>>>>>>>>>> strat formBackingObject");
		ChangePasswordCommand cmd =  new ChangePasswordCommand();
		model.addAttribute("cmd",cmd);
		return "changeUserPassword";
	}

	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors)
			throws Exception {
		log.debug(">>>>>>>>>>>>>>>>>>> strat onBindAndValidate");
		ChangePasswordCommand changePasswordCommand = (ChangePasswordCommand) command;
		Employee employee = (Employee) request.getSession().getAttribute("employee");
		User user = employee.getUser();
		if (changePasswordCommand.getOldPassword().equals("") || changePasswordCommand.getNewPassword().equals("")
				|| changePasswordCommand.getConfirmPassword().equals("")) {
			log.debug(">>>>>>>>>>>>>>>>>>>if 1");
			errors.rejectValue("oldPassword", "commons.errors.requiredFields", "");
		} else if (!user.getPassword().endsWith(changePasswordCommand.getOldPassword())) {
			log.debug(">>>>>>>>>>>>>>>>>>> if 2");
			errors.rejectValue("oldPassword", "security.errors.invalidOldPassword", "");
		} else if (!changePasswordCommand.getNewPassword().equals(changePasswordCommand.getConfirmPassword())) {
			log.debug(">>>>>>>>>>>>>>>>>>> if 3");
			errors.rejectValue("newPassword", "security.errors.newPasswordAndConfirmShouldBeEqual", "");
		}
		log.debug(">>>>>>>>>>>>>>>>>>> end onBindAndValidate");
	}

	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request,@ModelAttribute("changePassword") ChangePasswordCommand command) {
		ChangePasswordCommand changePasswordCommand = (ChangePasswordCommand) command;
		Map model = new HashMap();
		String message = request.getParameter("message");
//		if (message != null) {
//			if (message.equals("true")) {
//				errors.rejectValue("oldPassword", "security.caption.passwordChangedSuccessfully", "");
//			}
//		}
		model.put("message", message);
		return model;
	}

}
