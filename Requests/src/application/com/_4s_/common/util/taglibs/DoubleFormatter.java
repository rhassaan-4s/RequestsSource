package com._4s_.common.util.taglibs;


import java.text.NumberFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DoubleFormatter extends TagSupport {
	private static final Log log = LogFactory.getLog(DoubleFormatter.class);

	private Double value = null;

	public void setValue(Double value) {
		this.value = value;
	}

	public DoubleFormatter() {
		super();
	}

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		String result = "";
		if(value != null){
			NumberFormat numberFormat = NumberFormat.getInstance();
			result = numberFormat.format(value);
		}
		try{ 
			pageContext.getOut().write(result);
		} catch (java.io.IOException e) {
			throw new JspTagException("IO Error: " + e.getMessage());
		}
		return EVAL_PAGE;
	}

}
