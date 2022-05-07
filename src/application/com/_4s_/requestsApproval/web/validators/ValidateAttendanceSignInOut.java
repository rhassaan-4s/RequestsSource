package com._4s_.requestsApproval.web.validators;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.restServices.json.RestStatus;
import com._4s_.security.model.User;
import com._4s_.security.service.MySecurityManager;

public class ValidateAttendanceSignInOut implements Validator {
	protected final Log log = LogFactory.getLog(getClass());

	private RequestsApprovalManager mgr = null;

	public RequestsApprovalManager getMgr() {
		return mgr;
	}

	public void setMgr(RequestsApprovalManager mgr) {
		this.mgr = mgr;
	}

	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return clazz.equals(User.class);
	}

	public void validate(Object obj, Errors errors) {
		log.debug(">>>>>.................. start validate().....");
		// TODO Auto-generated method stub
		LoginUsersRequests loginUsersRequests = (LoginUsersRequests) obj;

		
		Settings settings = (Settings)mgr.getObject(Settings.class, new Long(1));
		
		if(errors.getErrorCount()==0)
		{	
			if(loginUsersRequests==null || loginUsersRequests.equals("")){
				errors.rejectValue("empCode", "commons.errors.requiredFields");
			}
			else {
				Calendar c = Calendar.getInstance();
				c.setTimeZone(TimeZone.getTimeZone("EST"));
				Date now = c.getTime();
				if(loginUsersRequests.getRequest_id()==null || loginUsersRequests.getRequest_id().equals(""))
				{
					errors.rejectValue("request_id", "commons.errors.requiredFields");
				} else {
					Long attendanceType = null;
					Employee emp = (Employee)mgr.getObjectByParameter(Employee.class, "empCode", loginUsersRequests.getEmpCode());
					attendanceType = loginUsersRequests.getRequest_id().getId();
					
					RestStatus status = mgr.validateSignInOut(attendanceType, now, emp);
					if (status.getStatus().equals("False")) {
						String statusMsg = status.getMessage();
						String i18nkey = "";
						if (statusMsg.equals("User signed In Before and didn't signed out")) {
							i18nkey = "requests.errors.signedInBefore";
						} else if (statusMsg.equals("User didn't sign In yet")) {
							i18nkey = "requests.errors.didnotSignInYet";
						} else if (statusMsg.equals("Sign out date is before sign in date")) {
							i18nkey = "requests.errors.signoutBeforeSignin";
						} else if (statusMsg.equals("Sign in on a full errand day is not allowed")) {
							i18nkey = "requests.errors.signinOnFullErrandDay";
						} else if (statusMsg.contains("Finish Started Request First")) {
							i18nkey = "requests.errors.finishStartedRequestFirst";
						}  else {
							log.debug(statusMsg);
							i18nkey = "requests.errors.unknownError";
						}
						errors.rejectValue("from_date",i18nkey);
					}
				}
				
			}
			
		}

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
