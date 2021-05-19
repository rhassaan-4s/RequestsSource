package com._4s_.common.util.taglibs;

import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com._4s_.common.util.DateUtil;
import com._4s_.common.util.MultiCalendarDate;





public class DateString extends TagSupport{
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
		if ( value != null ) {
			try{
				log.debug(">>>>>>>>>>>>> value "+value);
			//	DateUtil.setDatePattern("dd-MM-yyyy HH:mm:ss");
				MultiCalendarDate mCalDate = new MultiCalendarDate();
				mCalDate.setDate(value);
				//String dateString = DateUtil.convertDateToString(value);
			//	log.debug(">>>>>>>>>>>>>>>>>>>>> dateString "+dateString);
			//	dateString = dateString.substring(0,10);
			//	log.debug(">>>>>>>>>>>>>>>>>>>>> dateString "+dateString);
				pageContext.getOut().write(mCalDate.getDateString());
			}catch(Exception e){
			
			}
				
		}
		return EVAL_PAGE;
	}
	
}
