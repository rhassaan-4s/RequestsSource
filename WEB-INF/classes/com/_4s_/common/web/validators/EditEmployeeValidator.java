package com._4s_.common.web.validators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com._4s_.common.model.Employee;

public class EditEmployeeValidator implements Validator {
	
	private final Log log = LogFactory.getLog(getClass());
	public boolean supports(Class clazz){
			return clazz.equals(Employee.class);
	}
	
	public void validate(Object obj,Errors errors){
		Employee employee = (Employee) obj;
		
		validateFirstName(employee,errors);
		
		if (errors.getErrorCount() == 0){
			validateLastName( employee, errors);
		}
		
		if (errors.getErrorCount() == 0){
			validateJobTitle( employee, errors);			
		}
		
		if (errors.getErrorCount() == 0){
			validateAddress(employee,errors);
		}
		
//		if (errors.getErrorCount() == 0){
//			validateBirthDate( employee, errors);
//		}
		
//		if (errors.getErrorCount() == 0){
//			validateIdentityNumber( employee,errors);
//		}
		
		if (errors.getErrorCount() == 0){
			validateDepartment(employee, errors);
		}
		
		if (errors.getErrorCount() == 0){
			validateUserName(employee,errors);
		}
		
		if (errors.getErrorCount() == 0){
			validatePassword( employee, errors);
		}
	}
	
	public void validateFirstName(Employee employee,Errors errors){
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"firstName","commons.errors.requiredFields","");
	}

	public void validateLastName(Employee employee,Errors errors){
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"lastName","commons.errors.requiredFields","");
	}
	
	public void validateJobTitle(Employee employee,Errors errors){
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"jobTitle","commons.errors.requiredFields","");
	}

	public void validateAddress(Employee employee,Errors errors){
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"address","commons.errors.requiredFields","");
	}

	public void validateBirthDate(Employee employee,Errors errors){
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"birthDate","commons.errors.requiredFields","");
	}
	
	public void validateIdentityNumber(Employee employee,Errors errors){
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"identityNumber","commons.errors.requiredFields","");
	}
	
	public void validateDepartment(Employee employee,Errors errors){
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"department","commons.errors.requiredFields","");
	}
	
	public void validateUserName(Employee employee,Errors errors){
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"account.userName","commons.errors.requiredFields","");
	}
	
	public void validatePassword(Employee employee,Errors errors){
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"account.password","commons.errors.requiredFields","");
	}
}
