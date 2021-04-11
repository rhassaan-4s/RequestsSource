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
public class BiCalendarTimeBinder extends PropertyEditorSupport implements
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
			mCalDate.setMainTime(date);
			text = mCalDate.getTimeString(); 
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
		if(text.indexOf(":")>=0 && text.indexOf("/")<0) {
			if ((text!=null)&&(text.length()>0)) {
				log.debug(">>>>>>> set TIME as text if "+text);
				MultiCalendarDate mCalDate = new MultiCalendarDate();
				mCalDate.setTimeString(text);
				date = mCalDate.getMainTime();
				log.debug(mCalDate.getMainTime());
				setValue(date);
				
	
			} else {
				log.debug(">>>>>>> set as text else");
				setValue(null);
			}
		}
		else if(text.indexOf("/")>=0 && text.indexOf(":")<0) {
			if ((text!=null)&&(text.length()>0)) {
				log.debug(">>>>>>> set Date as text if "+text);
				MultiCalendarDate mCalDate = new MultiCalendarDate();
				mCalDate.setDateString(text);
				date = mCalDate.getDate();
				setValue(date);
				
	
			} else {
				log.debug(">>>>>>> set as text else");
				setValue(null);
			}
		}
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< SetAsText");
	}

}
