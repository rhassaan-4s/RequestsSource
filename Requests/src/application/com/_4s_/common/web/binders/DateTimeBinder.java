/**
 * 
 */
package com._4s_.common.web.binders;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com._4s_.common.util.MultiCalendarDate;

/**
 * @author mragab
 *
 */
public class DateTimeBinder extends PropertyEditorSupport implements
		BaseBinder {

	private final Log log = LogFactory.getLog(getClass());

	public Class getBindedClass() {
		return Date.class;
	}

	
	public String getAsText() {
		log.debug("GetAsText >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		log.debug(">>>>>>> getAstext getValue() "+getValue());
		Date date = ((Date)getValue());
		log.debug(">>>>>>> getAstext date "+date);
		String text = null;
		
		if (date!=null) {
			log.debug(">>>>>>> getAstext if ");
			MultiCalendarDate mCalDate = new MultiCalendarDate();
			mCalDate.setDate(date);
			text = mCalDate.getDateTimeString(); 
		} else {
			log.debug(">>>>>>> getAstext else ");
			text = "" ;
		}
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< GetAsText"+ text);
		
		return text;
	}
	
	public void setAsText(String text) throws IllegalArgumentException {
		log.debug("SetAsText >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Date date = null;
		
		if ((text!=null)&&(text.length()>0)) {
			log.debug(">>>>>>> set as text if");
			MultiCalendarDate mCalDate = new MultiCalendarDate();
			mCalDate.setDateTimeString(text);
			date = mCalDate.getDate();
			date.setSeconds(0);
			setValue(date);
			

		} else {
			log.debug(">>>>>>> set as text else");
			setValue(null);
		}
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< SetAsText");
	}

}
