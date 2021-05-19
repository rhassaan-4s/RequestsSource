package com._4s_.common.web.validators;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com._4s_.common.web.command.DateConverter;

public class DateConverterValidator implements Validator {
	protected final Log log = LogFactory.getLog(getClass());
	public boolean supports(Class clazz) {
		return clazz.equals(DateConverter.class);
	}

	public void validate(Object obj, Errors errors) {
		DateConverter dateConverter = (DateConverter) obj;
		validateNotEmptyDate(dateConverter, errors);
		if (errors.getErrorCount() == 0) {
			validateDate(dateConverter, errors);
		}
	}

	public void validateNotEmptyDate(DateConverter dateConverter, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"date","commons.errors.emptyDate","");
	}

	public void validateDate(DateConverter dateConverter, Errors errors) {
		String dateString = dateConverter.getDate();
		if(!dateString.matches("\\d{1,2}/\\d{1,2}/\\d{4,4}")){
			errors.rejectValue("date","commons.errors.invalidDate","");
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>... errors "+errors);
		}
	}

}
