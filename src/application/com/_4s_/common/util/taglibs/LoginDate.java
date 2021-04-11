package com._4s_.common.util.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public class LoginDate extends TagSupport{
	
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		//try {
			
//			SecureContext sc = (SecureContext)(ContextHolder.getContext());
//			MyUser obj =  (MyUser)sc.getAuthentication().getPrincipal();	
//			String date = obj.getLoginDate();
//			pageContext.getOut().write(date);
			
//		} catch (java.io.IOException e) {
//			throw new JspTagException("IO Error: " + e.getMessage());
//		}
		
		return EVAL_PAGE;
	}

}
