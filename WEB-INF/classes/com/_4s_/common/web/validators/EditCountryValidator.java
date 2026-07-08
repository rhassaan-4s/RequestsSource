package com._4s_.common.web.validators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com._4s_.common.model.Country;

public class EditCountryValidator implements Validator {

private final Log log = LogFactory.getLog(getClass());
	public boolean supports(Class clazz){
			return clazz.equals(Country.class);
	}
	
	public void validate(Object obj,Errors errors){
		Country country = (Country) obj;
		validateDescription(country,errors);
	}
	
	public void validateDescription(Country country,Errors errors){
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"description","commons.errors.requiredFields","");
	}

}
