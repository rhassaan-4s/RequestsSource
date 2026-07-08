package com._4s_.common.util.taglibs;

import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import com._4s_.common.util.MultiCalendarDate;

public class MiladiDateFormatterInTable extends TagSupport {

	private Date value = null;

	public void setValue(Date value) {
		this.value = value;
	}

	public MiladiDateFormatterInTable() {
		super();
	}

	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		
		String result = "";
		System.out.println("date value " + value);
		if (( value != null ) && ( !value.equals("") ) ) {
			try {
				MultiCalendarDate mCalDate = new MultiCalendarDate();
				mCalDate.setDate(value);
				System.out.println("mcaldate value " + mCalDate.getDateString());
				String dateString = mCalDate.getMeladiString();
				if(dateString != null && !dateString.equals("")){
					String[] dateElements = dateString.split("/");
					String day = dateElements[0];
					String month = dateElements[1];
					String year = dateElements[2]; 
					pageContext.getOut().write("<table dir=rtl border=0 cellpadding=0 cellspacing=0 ><tr>" +
							"<td>"+day+"</td><td>/</td><td>"+month+"</td><td>/</td><td>"+year+"</td>" +
							"</tr></table>");	
				}else{
					pageContext.getOut().write(dateString);	
				}

				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}	
		try {
				pageContext.getOut().write(result);
		} catch (java.io.IOException e) {
			throw new JspTagException("IO Error: " + e.getMessage());
		}
		return EVAL_PAGE;
		}
	}

