package com._4s_.common.web.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com._4s_.common.web.command.ChangePassword;

public class ChangePasswordValidator implements Validator {
	
	public boolean supports(Class clazz){
		return clazz.equals(ChangePassword.class);
	}
	
	public void validate(Object obj,Errors errors){
		ChangePassword changePassword = (ChangePassword) obj;
		
		validateNotEmptyOldPassword(changePassword,errors);
		if (errors.getErrorCount() == 0){
			validateOldPassword(changePassword, errors);
		}
		if (errors.getErrorCount() == 0){
			validateNewPassword(changePassword,errors);
		}
	}
	
	public void validateNotEmptyOldPassword(ChangePassword changePassword,Errors errors){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"oldPassword","errors.emptyOldPassword","");
	}	
	
	public void validateOldPassword(ChangePassword changePassword,Errors errors){
//		SecureContext sc = (SecureContext)(ContextHolder.getContext());
//		MyUser obj =  (MyUser)sc.getAuthentication().getPrincipal();	
//		String password= obj.getPassword();
		//if(!(password.endsWith(changePassword.getOldPassword()))){
			//errors.rejectValue("oldPassword","errors.oldPassword","");
		//}
	}	
	
	public void validateNewPassword(ChangePassword changePassword,Errors errors){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"newPassword","errors.newPassword","");
	}		
}
