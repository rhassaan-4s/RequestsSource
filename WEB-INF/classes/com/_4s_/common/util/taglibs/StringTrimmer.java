package com._4s_.common.util.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringTrimmer extends TagSupport{
	private String value = "";
	private String name = "";
	protected final Log log = LogFactory.getLog(getClass());
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
//		System.out.println("executing tag");
		String result = " ";
//		System.out.println("value " + value + " name " + name);
		if (( value != null ) && ( !value.equals("") ) && ( name != null ) && ( !name.equals("") ) ) {
			log.debug(">>>>>>>>>>>>> value "+value);
			try{
				log.debug(">>>>>>>>>>>>>>>> try ");
//				System.out.println("name.length() " + name.length());
				String formattedString = name;
				if (name.length()> Integer.parseInt(value) - 3) {
//					System.out.println("long string");
					formattedString = name.substring(0,Integer.parseInt(value) - 3) + "...";
				}
//				System.out.println("formatted string " + formattedString);
					pageContext.getOut().write(formattedString);	
			}catch (Exception e) {
				// TODO: handle exception
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

