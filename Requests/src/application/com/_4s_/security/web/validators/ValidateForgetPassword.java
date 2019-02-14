package com._4s_.security.web.validators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oro.text.perl.Perl5Util;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com._4s_.security.model.User;
import com._4s_.security.service.MySecurityManager;

public class ValidateForgetPassword implements Validator {
	protected final Log log = LogFactory.getLog(getClass());

	private MySecurityManager mgr = null;

	public MySecurityManager getMgr() {
		return mgr;
	}

	public void setMgr(MySecurityManager mgr) {
		this.mgr = mgr;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(User.class);
	}

	public void validate(Object obj, Errors error) {
		User user = (User) obj;

		if (error.getErrorCount() == 0) {
			validateEmail(user, error);
		}
	}

	private void validateEmail(User user, Errors errors) {

		final String EMAIL_REGEXP = "/^[a-z0-9]+([_\\.-][a-z0-9]+)*@([a-z0-9]+([\\.-][a-z0-9]+)*)+\\.[a-z]{2,}$/i";

		if (errors.getErrorCount() == 0) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employee.email",
					"commons.errors.requiredFields", "");
		}
		if (errors.getErrorCount() == 0) {
			Perl5Util perl5Util = new Perl5Util();

			if (!perl5Util.match(EMAIL_REGEXP, user.getEmployee().getEmail())) {
				errors.reject("commons.errors.invalidEmailFormat", "");
			}
		}

	}

}
