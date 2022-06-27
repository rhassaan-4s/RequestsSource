/**
 * 
 */
package com._4s_.common.web.binders;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;

/**
 * @author mragab
 *
 */
public class TimestampTimeBinder extends PropertyEditorSupport implements
		BaseBinder {

	private final Log log = LogFactory.getLog(getClass());
	SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	SimpleDateFormat finalformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public Class getBindedClass() {
		return Timestamp.class;
	}

	
	public String getAsText() {
		log.debug("GetAsText >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		log.debug(">>>>>>> getAstext getValue() "+getValue());
		String text = null;
		Timestamp date = (Timestamp)getValue();
		if (date!=null) {
			text = format.format(date);
		}
		
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< GetAsText"+ text);
		
		return text;
	}
	
	public void setAsText(String text) throws IllegalArgumentException {
		log.debug("SetAsText >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		try {
			Date inputDate = format.parse(text);
			log.debug(inputDate);
			Calendar today = Calendar.getInstance();
			log.debug(today.getTime());
			Calendar cal = Calendar.getInstance();
			cal.setTime(inputDate);
			cal.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));
			log.debug(cal.getTime());
			Timestamp time = Timestamp.valueOf(finalformat.format(cal.getTime()));
			setValue(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			setValue(null);
			e.printStackTrace();
		}
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< SetAsText");
	}

}
