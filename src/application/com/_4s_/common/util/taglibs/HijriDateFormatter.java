package com._4s_.common.util.taglibs;

import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com._4s_.common.util.MultiCalendarDate;

public class HijriDateFormatter extends TagSupport {

	private Date value = null;

	public void setValue(Date value) {
		this.value = value;
	}

	public HijriDateFormatter() {
		super();
	}

	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		return SKIP_BODY;
	}
	private final Log log = LogFactory.getLog(getClass());
	
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		String result = "";
		if (( value != null ) && ( !value.equals("") ) ) {
			log.debug(">>>>>>>>>>>>>>>>>>>>>value "+value);
			try{
				log.debug(">>>>>>>>>>>> try 1");
				MultiCalendarDate mCalDate = new MultiCalendarDate();
				log.debug(">>>>>>>>>>>> try 2");
				mCalDate.setDate(value);
				log.debug(">>>>>>>>>>>> mCalDate"+mCalDate.getDate());
				log.debug(">>>>>>>>>>>> mCalDate.getMeladiString()"+mCalDate.getMeladiString());
				log.debug(">>>>>>>>>>>>>>>>>>>>>mCalDate.getHijriString() "+mCalDate.getHijriString());
				
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
