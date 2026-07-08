package com._4s_.common.util.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com._4s_.common.util.LocaleUtil;

public class ChangeLocale extends TagSupport {

	private String value = null;

	public void setValue(String value) {
		this.value = value;
	}

	public ChangeLocale() {
		super();
	}

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		if (value != null) {
			LocaleUtil.getInstance().setLocale(value);
		}
		return EVAL_PAGE;
	}

}
