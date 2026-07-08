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

/**
 * @author mragab
 *
 */
public class TimestampBinder extends PropertyEditorSupport implements
		BaseBinder {

	private final Log log = LogFactory.getLog(getClass());
	SimpleDateFormat finalformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	public Class getBindedClass() {
		return Timestamp.class;
	}

	
	public String getAsText() {
//		log.debug("GetAsText >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
//		log.debug(">>>>>>> getAstext getValue() "+getValue());
//		Date date = ((Date)getValue());
//		log.debug(">>>>>>> getAstext date "+date);
//		String text = null;
//		
//		if (date!=null) {
//			log.debug(">>>>>>> getAstext if ");
////			MultiCalendarDate mCalDate = new MultiCalendarDate();
////			mCalDate.setDate(date);
////			mCalDate.setCalendarType(MultiCalendarDate.MILADI);
//			text = format.format(date);
//		} else {
//			log.debug(">>>>>>> getAstext else ");
//			text = "" ;
//		}
		String text = null;
		Timestamp date = (Timestamp)getValue();
		if (date!=null) {
			text = format.format(date);
		}
		
		
//		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< GetAsText"+ text);
		
		return text;
	}
	
	public void setAsText(String text) throws IllegalArgumentException {
		log.debug("SetAsText >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("TimestampBinder Begin");
		try {
			Date inputDate = format.parse(text);
			System.out.println("TimestampBinder --- input date ------"+inputDate);
			log.debug(inputDate);
			String formatted = finalformat.format(inputDate);
			System.out.println("TimestampBinder --- formatted ------"+formatted);
			Timestamp time = Timestamp.valueOf(formatted);
			log.debug(time);
			setValue(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			setValue(null);
			e.printStackTrace();
		}
//		Date date = null;
//		
//		if ((text!=null)&&(text.length()>0)) {
//			log.debug(">>>>>>> set as text if");
//			try {
//				Date d1 = (new SimpleDateFormat("dd/MM/yyyy").parse(text));
//				date = format.parse(format.format(d1));
//				setValue(date);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				setValue(null);
//				e.printStackTrace();
//			}
//			
//
//		} else {
//			log.debug(">>>>>>> set as text else");
//			setValue(null);
//		}
		
//		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< SetAsText");
	}

}
