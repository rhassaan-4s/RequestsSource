package com._4s_.common.util.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PositiveAndNegativeDeterminer extends TagSupport {
	protected final Log log = LogFactory.getLog(getClass());
	private String value = "";

	public void setValue(String value) {
		this.value = value;
	}
	
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		String result = " ";
		Double num =  null;
		String debit ="";
		String credit ="";
		if (( value != null ) && ( !value.equals("") ) ) {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>value "+value);
			try {
				num = new Double(value);
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>> num "+num);
				if(num != null){
					if(num > 0){
						result = "positive";
						log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>> positive");
						debit = num.toString();
					}
					if (num < 0){
						result = "negative";
						log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>> negative");
						credit = num.toString().substring(1);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		pageContext.setAttribute("numberSign",result);
		pageContext.setAttribute("debit",debit);
		pageContext.setAttribute("credit",credit);
		return EVAL_PAGE;
	}
}
