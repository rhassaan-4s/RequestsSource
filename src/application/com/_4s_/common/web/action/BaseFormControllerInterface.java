package com._4s_.common.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.security.model.Roles;

public interface BaseFormControllerInterface {
	public ModelAndView processSubmit(Object command,HttpServletRequest request, HttpServletResponse response,BindingResult result, SessionStatus status);
	public String initForm(ModelMap model,HttpServletRequest request, HttpServletResponse response);
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request,@ModelAttribute("cmd") Object command);
}
