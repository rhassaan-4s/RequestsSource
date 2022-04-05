package com._4s_.security.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.web.action.BaseController;

public class ErrorCode403Controller extends BaseController{

	@RequestMapping(value="/errorCode403.html", method = RequestMethod.GET)
	 public String error403(HttpServletRequest request) {
		return "errorCode403";
	}
	
}
