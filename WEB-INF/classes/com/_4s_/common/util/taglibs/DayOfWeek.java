package com._4s_.common.util.taglibs;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DayOfWeek extends TagSupport{
private Date value = null;

	public void setValue(Date value) {
		this.value = value;
	}

	public DayOfWeek() {
		super();
	}

	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		return SKIP_BODY;
	}

	private final Log log = LogFactory.getLog(getClass());
	
	public int doEndTag() throws JspException {
		
			try{
				Calendar cal = Calendar.getInstance();
				//cal.setTime(value);
//				log.debug(">>>>>>>>>>>>>>>>>>>>>>>> date "+cal.getTime());
				int day = cal.get(Calendar.DAY_OF_WEEK);
//				log.debug(">>>>>>>>>>>>>>>>>>>>>>> day "+day);
				String dayString = new Long(day).toString();
				if (dayString.equals("7")){
//					log.debug(">>>>>>>>>>>>>>>>>>>>>>> 7 ");
					//pageContext.getOut().write("<fmt:message key='commons.caption.sunday'/>");
					pageContext.setAttribute("dayOfWeek","commons.caption.saturday");
				}
				if (dayString.equals("1")){
//					log.debug(">>>>>>>>>>>>>>>>>>>>>>> 1 ");
					//pageContext.getOut().write("<fmt:message key='commons.caption.monday'/>");
					pageContext.setAttribute("dayOfWeek","commons.caption.sunday");
				}
				if (dayString.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>>>>>>>> 2 ");
					//pageContext.getOut().write("<fmt:message key='commons.caption.tuesday'/>");
					pageContext.setAttribute("dayOfWeek","commons.caption.monday");
				}
				if (dayString.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>>>>>>>> 3 ");
					//pageContext.getOut().write("<fmt:message key='commons.caption.wednesday'/>");
					pageContext.setAttribute("dayOfWeek","commons.caption.tuesday");
				}
				if (dayString.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>>>>>>>> 4 ");
					//pageContext.getOut().write("<fmt:message key='commons.caption.thursday'/>");
					pageContext.setAttribute("dayOfWeek","commons.caption.wednesday");
				}
				if (dayString.equals("5")){
					log.debug(">>>>>>>>>>>>>>>>>>>>>>> 5 ");
					//pageContext.getOut().write("<fmt:message key='commons.caption.friday'/>");
					pageContext.setAttribute("dayOfWeek","commons.caption.thursday");
				}
				if (dayString.equals("6")){
					log.debug(">>>>>>>>>>>>>>>>>>>>>>> 6 ");
					//pageContext.getOut().write("<fmt:message key='commons.caption.saturday'/>");
					pageContext.setAttribute("dayOfWeek","commons.caption.friday");
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
		return EVAL_PAGE;
	}
}
