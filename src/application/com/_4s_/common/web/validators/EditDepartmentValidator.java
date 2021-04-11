package com._4s_.common.web.validators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com._4s_.common.model.Department;

public class EditDepartmentValidator implements Validator{
	
	private final Log log = LogFactory.getLog(getClass());
	public boolean supports(Class clazz){
			return clazz.equals(Department.class);
	}
	
	public void validate(Object obj,Errors errors){
		Department department = (Department) obj;
		validateDescription(department,errors);
	}
	
	public void validateDescription(Department department,Errors errors){
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"description","commons.errors.requiredFields","");
	}

}
