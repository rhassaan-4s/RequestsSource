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
import com._4s_.requestsApproval.model.AnnualVacLimit;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.restServices.json.RestStatus;
import com._4s_.security.model.User;
import com._4s_.security.service.MySecurityManager;

public class ValidateEmpGroup implements Validator {
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
		return clazz.equals(EmpReqTypeAcc.class);
	}

	public void validate(Object obj, Errors errors) {
		log.debug(">>>>>.................. start validate() of ValidateLoginUsersRequestForm .....");
		// TODO Auto-generated method stub
		EmpReqTypeAcc empGroup = (EmpReqTypeAcc) obj;


		Settings settings = (Settings)mgr.getObject(Settings.class, new Long(1));

		if(errors.getErrorCount()==0)
		{	
			if(empGroup==null || empGroup.equals("")){
				errors.reject("commons.errors.requiredFields", "commons.errors.requiredFields");
			} else {
				if (empGroup.getEmp_id()==null) {
					errors.rejectValue("emp_id", "commons.errors.requiredFields");
				}
				
				if (empGroup.getGroup_id()==null) {
					errors.rejectValue("group_id", "commons.errors.requiredFields");
				}
				
				if (empGroup.getReq_id()==null) {
					errors.rejectValue("req_id", "commons.errors.requiredFields");
				}
				
				if (empGroup.getOrder()<=0L) {
					errors.rejectValue("order", "commons.errors.requiredFields");
				}
				
			}
			

		}

		log.debug(">>>>>>>>>>>>>>>>.........end validate()>>>>>>>>>>>");
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
