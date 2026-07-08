package com._4s_.i18n.web.validators;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com._4s_.common.service.BaseManager;
import com._4s_.i18n.model.MyLocale;

public class ValidateLocale implements Validator {
	protected final Log log = LogFactory.getLog(getClass());

	private BaseManager baseManager = null;

	public BaseManager getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}

	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return clazz.equals(MyLocale.class);
	}

	public void validate(Object obj, Errors error) {
		log.debug(">>>>>.................. start validate().....");
		// TODO Auto-generated method stub
		MyLocale myLocale = (MyLocale) obj;
		if (error.getErrorCount() == 0) {
			log.debug(">......>>>>>>>>>>>>>>>>>............getErrorCount==0");
			validateLanguage(myLocale, error);
		}
		if (error.getErrorCount() == 0) {
			log.debug(">......>>>>>>>>>>>>>>>>>............getErrorCount==0");
			validateCountry(myLocale, error);
		}
		if (error.getErrorCount() == 0) {
			log.debug(">......>>>>>>>>>>>>>>>>>............getErrorCount==0");
			validateCode(myLocale, error);
		}

		log.debug(">>>>>>>>>>>>>>>>.........end validate()>>>>>>>>>>>");
	}

	public void validateLanguage(MyLocale myLocale, Errors errors) {
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		List locales = baseManager.getObjects(MyLocale.class);
		log.debug("----------------------------------------");
		log.debug("-----------------getUsers()" + locales
				+ "-----------------------");
		log.debug("-----------------------------------------");
		Iterator itr = locales.iterator();
		while (itr.hasNext()) {
			MyLocale loc = ((MyLocale) itr.next());
			log.debug(">>>>>>>>>>>>>>>>>>loc.getLanguage() "
					+ loc.getLanguage());
			log.debug(">>>.................mylocale.getLanguage() "
					+ myLocale.getLanguage());

			if (loc.getLanguage().equals(myLocale.getLanguage())) {
				log
						.debug("--------------------->>>>>>>>>>>>>>>>>>>>>>>language------------<<<<<<<<<<<<<<<< ");
				errors.rejectValue("language", "commons.errors.duplicateLocale",
						"This Language already exists");
			}
		}
	}

	public void validateCountry(MyLocale myLocale, Errors errors) {
		log.debug(">>>>>>>>>>validateCountry>>>>>>>>>>  ");
		if(myLocale.getCountry() == null || myLocale.getCountry().equals("")){
			errors.rejectValue("country", "commons.errors.requiredFields", "country22");
			
		}
		log.debug(">>>>>>>>>>validateCountry ------- country>>>>>>>>>>  "+myLocale.getCountry());
	}

	public void validateCode(MyLocale myLocale, Errors errors) {
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"code", "commons.errors.requiredFields","code12");
	}

}
