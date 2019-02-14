package com._4s_.common.web.binders;

import java.beans.PropertyEditorSupport;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ArrayOfLongBinder  extends PropertyEditorSupport{
	private final Log log = LogFactory.getLog(getClass());
		
		public String getAsText() {
		String text = "";
		if (getValue() != null){
			Long[] longArray = (Long[])getValue();
			int size = longArray.length; 
		
			for (int i=0;i<size;i++){
				text = text+","+longArray[i];
			}
		}
		return text;
	}

	public void setAsText(String text) throws IllegalArgumentException {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>> text "+text);
		if ((text != null) && (text.length() > 0)) {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>> if");
			
			StringTokenizer tokenizer = new StringTokenizer(text, ",");
			int size = tokenizer.countTokens();
			Long[] longArray = new Long[size];
			int i = 0;
			while (tokenizer.hasMoreTokens()) {
				String element = tokenizer.nextToken();
				element = StringUtils.trim(element);
				Long longElement = new Long(element);
				log.debug(">>>>>>>>>>>>>> longElement "+longElement);
				longArray[i] = longElement;
				i = i + 1;
			}
			setValue(longArray);
		}else{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>> else");
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>> getValue() "+getValue());
			setValue(null);
		}
	
	}
}
