package com._4s_.common.util.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com._4s_.common.util.MultiCalendarDate;

public class HijriDateStringFormatter extends TagSupport{
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
		String result = " ";
		if (( value != null ) && ( !value.equals("") ) ) {
			log.debug(">>>>>>>>>>>>> value "+value);
			try{
				MultiCalendarDate mCalDate = new MultiCalendarDate();
				mCalDate.setDateString(value);
				log.debug(">>>>>>>>>>>>>mCalDate.getDate() "+mCalDate.getDate());
				log.debug(">>>>>>>>>>>>>mCalDate.getHijriString() "+mCalDate.getHijriString());
				//mCalDate.setDate(value);
				String dateString = mCalDate.getHijriString();
//				if(dateString != null && !dateString.equals("")){
//					String[] dateElements = dateString.split("/");
//					String day = dateElements[0];
//					String month = dateElements[1];
//					String year = dateElements[2]; 
//					pageContext.getOut().write("<table dir=rtl border=0 cellpadding=0 cellspacing=0 ><tr>" +
//							"<td>"+day+"</td><td>/</td><td>"+month+"</td><td>/</td><td>"+year+"</td>" +
//							"</tr></table>");	
//				}else{
					pageContext.getOut().write(dateString);	
//				}

				//pageContext.setAttribute(name,mCalDate.getHijriString());
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
