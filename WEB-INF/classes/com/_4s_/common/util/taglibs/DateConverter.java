package com._4s_.common.util.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com._4s_.common.util.MultiCalendarDate;

public class DateConverter extends TagSupport {

	private String value = "";

	public void setValue(String value) {
		this.value = value;
	}

	public DateConverter() {
		super();
	}

	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		String result = " ";
		if (( value != null ) && ( !value.equals("") ) ) {
			try {
				MultiCalendarDate mCalDate = new MultiCalendarDate();
				StringUtils  escapeUtil =  new StringUtils();
				value = escapeUtil.replace(value, "\\","\\\\");
				mCalDate.setDateString(value);
				result = mCalDate.getConvertedDateString();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		try {
				pageContext.getOut().write(result);
		} catch (java.io.IOException e) {
			throw new JspTagException("IO Error: " + e.getMessage());
		}
		return EVAL_PAGE;
	}

}
