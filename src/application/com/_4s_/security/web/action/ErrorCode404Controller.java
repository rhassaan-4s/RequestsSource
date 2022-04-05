package com._4s_.security.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.web.action.BaseController;

@Controller
public class ErrorCode404Controller{

	@RequestMapping(value="/errorCode404.html", method = RequestMethod.GET)
	 public String error404(HttpServletRequest request) {
		return "errorCode404";
	}
	
}
