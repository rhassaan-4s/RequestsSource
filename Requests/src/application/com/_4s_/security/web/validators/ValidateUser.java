package com._4s_.security.web.validators;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com._4s_.common.model.Employee;
import com._4s_.security.model.User;
import com._4s_.security.service.MySecurityManager;

public class ValidateUser implements Validator {
	protected final Log log = LogFactory.getLog(getClass());

	private MySecurityManager mgr = null;

	public MySecurityManager getMgr() {
		return mgr;
	}

	public void setMgr(MySecurityManager mgr) {
		this.mgr = mgr;
	}

	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return clazz.equals(User.class);
	}

	public void validate(Object obj, Errors error) {
		log.debug(">>>>>.................. start validate().....");
		// TODO Auto-generated method stub
		User user = (User) obj;
		log.debug(">>>>user.getEmployee().getIsEmployee()=="+user.getEmployee().getIsEmployee());

		if (error.getErrorCount() == 0) {
			log.debug(">......>>>>>>>>>>>>>>>>>............getErrorCount==0");
			validatePassword(user, error);
		}

//		if (error.getErrorCount() == 0) {
//			log.debug(">......>>>>>>>>>>>>>>>>>............getErrorCount==0");
//			validateDepartment(user, error);
//		}

//		if (error.getErrorCount() == 0) {
//			log.debug(">......>>>>>>>>>>>>>>>>>............getErrorCount==0");
//			validateCode(user, error);
//		}
//		if (error.getErrorCount() == 0) 
//		{
//			if(user.getEmployee().getIsEmployee().equals(new Boolean(true)))
//			{
//				log.debug(">......>>>>>>>>>>>>>>>>>............getErrorCount==0");
//				validateAttendanceCode(user, error);
//			}
//		}

//		if (error.getErrorCount() == 0) {
//			log.debug(">......>>>>>>>>>>>>>>>>>............getErrorCount==0");
//			validateDuplicateCode(user, error);
//		}
//		
//		if (error.getErrorCount() == 0 && user.getEmployee().getIsEmployee().equals(new Boolean(true))) 
//		{
//			log.debug(">>>>>>>>>>>>>>>>>>............getErrorCount==0 >>>>>>>>>validateDuplicateAttendanceCode");
//			validateDuplicateAttendanceCode(user, error);
//		}

		

		
		log.debug(">>>>>>>>>>>>>>>>.........end validate()>>>>>>>>>>>");
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void validateName(User user, Errors errors) {
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employee.firstName",
				"commons.errors.requiredFields", "");
	}

	public void validateLastName(User user, Errors errors) {
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employee.lastName",
				"commons.errors.requiredFields", "");
	}

	public void validateUsername(User user, Errors errors) {
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
				"commons.errors.requiredFields", "");
	}

	public void validatePassword(User user, Errors errors) {
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"commons.errors.requiredFields", "");
	}

	public void validateDepartment(User user, Errors errors) {
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		if (user.getEmployee().getDepartment() == null
				|| user.getEmployee().getDepartment().equals("")) {
			errors.rejectValue("employee.department",
					"commons.errors.requiredFields", "");
		}
	}

	public void validateJobTitle(User user, Errors errors) {
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employee.jobTitle",
				"commons.errors.requiredFields", "");
	}
	
	public void validateCode(User user, Errors errors) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"employee.employeeCode","commons.errors.requiredFields", "");
		
	}
	
	public void validateAttendanceCode(User user, Errors errors) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"employee.attendanceCode","commons.errors.requiredFields", "");
	}


	public void validateDuplicate(User user, Errors errors)
		{		
			List duplicatedUsers=mgr.getObjectsByParameter(User.class,"username",user.getUsername());
			if (duplicatedUsers.size() >0){
				if(user.getId() == null){
					errors.rejectValue("username","commons.errors.duplicateUser","This username already exists");
				}
				else{
					for(int i=0;i<duplicatedUsers.size();i++)
					{
						User duplicatedUser = (User)duplicatedUsers.get(i);
						if(!user.getId().equals(duplicatedUser.getId())){
							errors.rejectValue("username","commons.errors.duplicateUser","This username already exists");
							break;
						}
					}
				}
			}
			/*log.debug("duplicatedUsers "+duplicatedUsers.size());
			log.debug("duplicatedUsers "+duplicatedUsers);
			for(int i=0;i<duplicatedUsers.size();i++)
			{
				User duplicatedUser = (User)duplicatedUsers.get(i);
				if(!user.equals(duplicatedUser))
				{
					log.debug(">>>>>>>>>>>>>>>>>>>>>>> duplicated userName");
					errors.rejectValue("username","commons.errors.duplicateUser","This username already exists");
					break;
				}			
			}		*/
		}
	
	public void validateDuplicateCode(User user, Errors errors)
	{
		List duplicatedCodes=mgr.getObjectsByParameter(Employee.class,"employeeCode",user.getEmployee().getEmployeeCode());
		for(int i=0;i<duplicatedCodes.size();i++)
		{
			Employee duplicatedEmp=(Employee) duplicatedCodes.get(i);
			if(!user.getEmployee().equals(duplicatedEmp))
			{
				log.debug(">>>>>>>>>>>>>>>>>> duplicated Employee Code ");
				errors.rejectValue("employee.employeeCode","commons.errors.duplicateCode","This employeeCode already exists");
				break;
			}
		}		
	}
	
	public void validateDuplicateAttendanceCode(User user, Errors errors)
	{
		List duplicatedCodes=mgr.getObjectsByParameter(Employee.class,"attendanceCode",user.getEmployee().getAttendanceCode());
		for(int i=0;i<duplicatedCodes.size();i++)
		{
			Employee duplicatedEmp=(Employee) duplicatedCodes.get(i);
			if(!user.getEmployee().equals(duplicatedEmp))
			{
				log.debug(">>>>>>>>>>>>>>>>>> duplicated EmployeeAttendance Code");
				errors.rejectValue("employee.attendanceCode","commons.errors.duplicateAttendanceCode","This attendanceCode already exists");
				break;
			}
		}		
	}


	public void validateEmail(User user, Errors errors) {
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		if (user.getEmployee().getEmail() != null
				&& !user.getEmployee().getEmail().equals("")) {

			if (user.getEmployee().getEmail().indexOf('@') == -1
					|| user.getEmployee().getEmail().indexOf('.') == -1) {
				errors.rejectValue("employee.email",
						"commons.errors.invalidEmailFormat",
						"Invalid email format");
			}
		} else {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,"employee.email","commons.errors.requiredFields", "");
		}
	}
	
	public void validateBrabch(User user, Errors errors) {
		log.debug(">>>>>>>>>>>>>>>>>>>>  ");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employee.branch",
				"commons.errors.requiredFields", "");
	}

}
