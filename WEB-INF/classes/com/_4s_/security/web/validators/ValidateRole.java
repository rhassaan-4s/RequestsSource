package com._4s_.security.web.validators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com._4s_.security.model.Roles;


public class ValidateRole implements Validator {
	protected final Log log = LogFactory.getLog(getClass());

	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return clazz.equals(Roles.class);
	}

	public void validate(Object obj, Errors error) {
		log.debug(">>>>>.................. start validate().....");
		// TODO Auto-generated method stub
		Roles role = (Roles) obj;
		if (error.getErrorCount() == 0) {
			log.debug(">......>>>>>>>>>>>>>>>>>............getErrorCount==0");
			validateRoleName(role, error);
		}
	
		log.debug(">>>>>>>>>>>>>>>>.........end validate()>>>>>>>>>>>");
	}

	public void validateRoleName(Roles role, Errors errors) {
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rolename",
				"commons.errors.requiredFields", "");
	}


}
