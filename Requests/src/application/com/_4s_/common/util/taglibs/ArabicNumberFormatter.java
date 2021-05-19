package com._4s_.common.util.taglibs;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ArabicNumberFormatter extends TagSupport {
	private static final Log log = LogFactory.getLog(ArabicNumberFormatter.class);

	private String value = null;
	Map<String, String> numberMap = new HashMap<String, String>();

	public void setValue(String value) {
		this.value = value;
	}

	public ArabicNumberFormatter() {
		super();
		numberMap.put("0", "&#1632;");
		numberMap.put("1", "&#1633;");
		numberMap.put("2", "&#1634;");
		numberMap.put("3", "&#1635;");
		numberMap.put("4", "&#1636;");
		numberMap.put("5", "&#1637;");
		numberMap.put("6", "&#1638;");
		numberMap.put("7", "&#1639;");
		numberMap.put("8", "&#1640;");
		numberMap.put("9", "&#1641;");
	}
	
	private boolean isNumber(String number){
		try{
			Double dNumber = new Double(number);
		} catch (NumberFormatException nfe){
			return false;
		}
		return true;
	}

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		String result = "";
		if (value != null) {
			String tempValue = new String(value.trim());
			String firstChar = new String();
			while (tempValue.length() > 0) {
				firstChar = tempValue.substring(0, 1);
				tempValue = tempValue.substring(1);
				if(isNumber(firstChar)){
					result = result + (String) numberMap.get(firstChar);
				} else {
					result = result + firstChar;
				}
			}
		}
		try{ 
			pageContext.getOut().write(result);
		} catch (java.io.IOException e) {
			throw new JspTagException("IO Error: " + e.getMessage());
		}
		return EVAL_PAGE;
	}

}
