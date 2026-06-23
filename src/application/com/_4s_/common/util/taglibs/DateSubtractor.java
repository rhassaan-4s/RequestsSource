package com._4s_.common.util.taglibs;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DateSubtractor extends TagSupport{
	private final Log log = LogFactory.getLog(getClass());
	private Date value = null;

	
	


	public Date getValue() {
		return value;
	}



	public void setValue(Date value) {
		this.value = value;
	}



	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		return SKIP_BODY;
	}
	
	
	
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		int numberOfDays = 0;
		//Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		if ( value != null ) {
			try{
				log.debug(">>>>>>>>>>>>> value "+value);
				log.debug(">>>>>>>>>>>>> today "+today);
				long differenceInMilliSeconds = today.getTime() - value.getTime();
				long difference = (differenceInMilliSeconds / (1000 * 60 * 60 * 24))+1;
				pageContext.getOut().write(new Long(difference).toString());
			}catch(Exception e){
			
			}
				
		}
//		try{ 
//			pageContext.getOut().write(result);
//		} catch (java.io.IOException e) {
//			throw new JspTagException("IO Error: " + e.getMessage());
//		}
		return EVAL_PAGE;
	}

}
