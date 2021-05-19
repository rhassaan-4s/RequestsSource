package com._4s_.common.web.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com._4s_.common.model.Country;
import com._4s_.common.model.TypesData;

public class TypesDataValidator implements Validator{
	public boolean supports(Class clazz){
		return clazz.equals(TypesData.class);
	}
	
	public void validate(Object obj,Errors errors){
		TypesData typesData = (TypesData) obj;
		validateDescription(typesData,errors);
	}

	public void validateDescription(TypesData typesData,Errors errors){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"description","commons.errors.requiredFields","");
	}
}
