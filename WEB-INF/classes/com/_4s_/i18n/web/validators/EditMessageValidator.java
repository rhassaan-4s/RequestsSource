package com._4s_.i18n.web.validators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com._4s_.i18n.model.MyMessage;

public class EditMessageValidator implements Validator {
	private final Log log = LogFactory.getLog(getClass());
	public boolean supports(Class clazz){
			return clazz.equals(MyMessage.class);
	}
	
	public void validate(Object obj,Errors errors){
		MyMessage myMessage = (MyMessage) obj;
		validateMessage(myMessage,errors);
	}
	
	public void validateMessage(MyMessage myMessage,Errors errors){
		log.debug(">>>>>>>>>>>>>>>>>>>> validateMessage ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"message","commons.errors.requiredFields","");
	}
}