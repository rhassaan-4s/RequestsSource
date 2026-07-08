package com._4s_.common.util.taglibs;

import java.text.NumberFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com._4s_.common.util.MultiCalendarDate;




public class TimeString extends TagSupport{
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
				NumberFormat nf = NumberFormat.getInstance();
				nf.setMinimumIntegerDigits(2);
				nf.setMaximumIntegerDigits(2);
//				log.debug(">>>>>>>>>>>>> value "+value);
				//DateUtil.setDatePattern("dd-MM-yyyy HH:mm:ss");
				//String timeString = DateUtil.convertDateToString(value);
				//log.debug(">>>>>>>>>>>>>>>>>>>>> timeString "+timeString);
				//timeString = timeString.substring(11,16);
				//log.debug(">>>>>>>>>>>>>>>>>>>>> timeString "+timeString);
				MultiCalendarDate cal = new MultiCalendarDate();
				cal.setDate(value);
				log.debug("cal.gettime " + cal.getDate());
				//String hour = nf.format(new Integer(cal.get(Calendar.HOUR_OF_DAY)).toString());
				//String minute = nf.format(new Integer(cal.get(Calendar.MINUTE)).toString());
//				if(cal.getMainTime()(Calendar.YEAR)%4==0) {
//					cal.setLenient(true);
//				} else {
//					cal.setLenient(false);
//				}
				
				
				log.debug("cal.getTimeString() " + cal.getTimeString());
//				String hour = nf.format(cal.get(Calendar.HOUR_OF_DAY));
//				String minute = nf.format(cal.get(Calendar.MINUTE));	
//				String timeString = hour+":"+minute;
				String timeString = cal.getTimeString();
				log.debug("timestring " + timeString);
				pageContext.getOut().write(timeString);
			}catch(Exception e){
			
			}
				
		}
		return EVAL_PAGE;
	}
}
