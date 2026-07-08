package com._4s_.common.web.validators;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com._4s_.common.service.CommonManager;
import com._4s_.i18n.model.MyLocale;
import com._4s_.i18n.model.MyMessage;
import com._4s_.i18n.service.MessageManager;

public class MandatoryConstraintValidator implements ConstraintValidator<Mandatory, String> {

	Class<?> aClass;
	String fieldName;
	Mandatory mandatory;

	@Autowired
	private CommonManager commonMgr;

	@Autowired
	private MessageManager messageManager;

	MyLocale myLocale = new MyLocale();



	public MessageManager getMessageManager() {
		return messageManager;
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public CommonManager getCommonMgr() {
		return commonMgr;
	}

	public void setCommonMgr(CommonManager commonMgr) {
		this.commonMgr = commonMgr;
	}

	@Override
	public void initialize(Mandatory mandatory) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		System.out.println(mandatory.property());
		System.out.println(mandatory.value());
		aClass = mandatory.value();
		this.mandatory = mandatory;
		fieldName = mandatory.property();
	}

	@Override
	@Transactional
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		try {
			System.out.print("Valid :: ");
			myLocale = (MyLocale) commonMgr.getObjectByParameter(
					MyLocale.class, "isDefault", new Boolean(true));
			System.out.println("getting locale "+myLocale);
			List entryList = commonMgr.getObjectsByParameter(aClass, fieldName, s);
			if (s.getClass().equals(String.class)) {
				if(s==null || s.isEmpty()){
					//            constraintValidatorContext.disableDefaultConstraintViolation();
					System.out.println("getting message ");
					MyMessage error = messageManager.getMessageByKeyName("commons.errors.requiredFields", myLocale);
					System.out.println("getting message " + error);
					String errorString = error.getMessage();
					//				String errorString = "test";
					constraintValidatorContext.buildConstraintViolationWithTemplate(errorString).addConstraintViolation();
					System.out.println("error message will return");
					return false;
				}
				else {
					System.out.println("no error message will return");
					return true;
				}
			} else {
				if(s==null){
					//            constraintValidatorContext.disableDefaultConstraintViolation();
					System.out.println("getting message ");
					MyMessage error = messageManager.getMessageByKeyName("commons.errors.requiredFields", myLocale);
					System.out.println("getting message " + error);
					String errorString = error.getMessage();
					//				String errorString = "test";
					constraintValidatorContext.buildConstraintViolationWithTemplate(errorString).addConstraintViolation();
					System.out.println("error message will return");
					return false;
				}
				else {
					System.out.println("no error message will return");
					return true;
				}
			}
		}catch (Exception e) {
			System.out.println("Failed to validate <or better message>" + e); 
			e.printStackTrace();
			return false;
		}
	}
}