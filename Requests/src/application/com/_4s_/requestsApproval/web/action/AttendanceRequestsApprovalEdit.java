package com._4s_.requestsApproval.web.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import sun.util.logging.resources.logging;

import com._4s_.requestsApproval.model.AccessLevels;
import com._4s_.requestsApproval.model.AnnualVacLimit;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.model.Requests;
import com._4s_.requestsApproval.model.Vacation;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.restServices.json.RequestApproval;
import com._4s_.restServices.json.RestStatus;
import com._4s_.auditing.validators.ValidateSearch;
import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;

public class AttendanceRequestsApprovalEdit extends BaseSimpleFormController{

	RequestsApprovalManager requestsApprovalManager;

	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}
	
	private JavaMailSenderImpl mailSender;
	
	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException 
	{	
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		String newDate =df.format(new Date());
		log.debug("----newDate--"+newDate);
		MultiCalendarDate mCalDate = new MultiCalendarDate();
		mCalDate.setDateString(newDate);

		Employee emp =(Employee) request.getSession().getAttribute("employee");
		log.debug("----emp from session---"+request.getSession().getAttribute("employee"));
		
		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
		if(loginUsers!=null){
			log.debug("-----login.code----"+loginUsers.getEmpCode());
		}
		
		String reqId = request.getParameter("reqId");
		LoginUsersRequests loginUsersRequests = (LoginUsersRequests)requestsApprovalManager.getObject(LoginUsersRequests.class, new Long(reqId));
		
//		loginUsersRequests.setFrom_date_history(loginUsersRequests.getFrom_date());
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
	   return loginUsersRequests ;
	}
	
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests) command;
		Map model=new HashMap();
	
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		log.debug("---ref-emp from session---"+emp);
		
		log.debug("=====emp.getEmpCode()==="+emp.getEmpCode());
		
		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
		
		String reqId = request.getParameter("reqId");
		model.put("reqId",reqId);
		
		model.put("from_date_history", loginUsersRequests.getFrom_date());
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}
	

//	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		LoginUsersRequests loginUsersRequests=(LoginUsersRequests)command;
//		log.debug("-------period from---"+loginUsersRequests.getPeriod_from());
//		String emp_code = request.getParameter("empCode");
//		log.debug("code entered--------"+emp_code);
//		
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//	}
	
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests)command;
	
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		
		loginUsersRequests.setFrom_date_history(loginUsersRequests.getFrom_date());
		if(errors.getErrorCount()==0)
		{	
//			if(loginUsersRequests.getFrom_date().compareTo(loginUsersRequests.getFrom_date_history())==0) {
//				errors.rejectValue("from_date", "commons.errors.requiredFields");
//			}
		}

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests)command;
		

		Employee emp =(Employee) request.getSession().getAttribute("employee");
		log.debug("---ref-emp from session---"+emp);
		///////////////////////modifing attendance time by manager//////////////////////////////
//		loginUsersRequests.setFrom_date_history(loginUsersRequests.getFrom_date());
		loginUsersRequests.setManagerModifiedDate(emp);
		loginUsersRequests.setNotes(loginUsersRequests.getNotes()+" (Attendance had been modified by manager)");
		///////////////////////////////////////////////////////////////////////////////////////



		requestsApprovalManager.saveObject(loginUsersRequests);
		RequestApproval approvals = new RequestApproval();
		approvals.setApprove("1");
		approvals.setNotes(loginUsersRequests.getManagerModifiedDate().getFirstName() + "had modified attendance from " + loginUsersRequests.getFrom_date_history()  + " to " + loginUsersRequests.getFrom_date());
		approvals.setRequestId(loginUsersRequests.getId()+"");
//		requestsApprovalManager.approvalsAccessLevels(approvals, loginUsersRequests,emp);
		requestsApprovalManager.automaticApprovalsAccessLevels(approvals, loginUsersRequests);

		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<  End onSubmit : <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//		String url="/attendanceRequestsReports.html";
//		log.debug(url);
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
}
