package com._4s_.auditing.validators;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com._4s_.auditing.model.AuditSearchCommand;
//import com._4s_.banks.model.Bank;
import com._4s_.common.util.DateUtil;

public class ValidateSearch implements Validator {
	protected final Log log = LogFactory.getLog(getClass());
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return clazz.equals(AuditSearchCommand.class);
	}

	public void validate(Object obj, Errors error) {
		log.debug(">>>>>.................. start validate().....");
		// TODO Auto-generated method stub
		AuditSearchCommand cmd = (AuditSearchCommand) obj;
		if (error.getErrorCount() == 0) {
			log.debug(">......>>>>>>>>>>>>>>>>>............getErrorCount==0");
			validateDate(cmd, error);
		}
		log.debug(">>>>>>>>>>>>>>>>.........end validate()>>>>>>>>>>>");
	}

	private void validateDate(AuditSearchCommand cmd, Errors errors) {
		Date fromDate = cmd.getFromDate();
//		String fromDateString = DateUtil.convertDateToString(fromDate);
//		log.debug(">..............start.validateDate()");
//		log.debug(">.>............>>>>>>>>>>.............dateString "+fromDate);
//		String datePattern = "^(?:(31)(\\D)(0?[13578]|1[02])\\2|(29|30)(\\D)(0?[13-9]|1[0-2])\\5|(0?[1-9]|1\\d|2[0-8])(\\D)(0?[1-9]|1[0-2])\\8)((?:1[6-9]|[2-9]\\d)?\\d{2})$|^(29)(\\D)(0?2)\\12((?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:16|[2468][048]|[3579][26])00)$";
//		if(fromDate !=null && fromDateString.matches(datePattern)){
//			errors.rejectValue("fromDate","commons.errors.invalidDate","invalid date format ");
//		}
		log.debug(">>>>>>>>>>>>>>>end validateDate()");
		
	}
}
