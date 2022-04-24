package com._4s_.requestsApproval.web.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.restServices.json.RequestApproval;

public class AttendanceRequestsApprovalEdit extends BaseSimpleFormController{

	RequestsApprovalManager requestsApprovalManager;
	
	public static Date tempDate = null;

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
		
		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
		if(loginUsers!=null){
			log.debug("-----login.code----"+loginUsers.getEmpCode());
		}
		
		String reqId = request.getParameter("reqId");
		log.debug("request id " + reqId);
		LoginUsersRequests loginUsersRequests = (LoginUsersRequests)requestsApprovalManager.getObject(LoginUsersRequests.class, new Long(reqId));
		log.debug("request " + loginUsersRequests);
		if (request.getMethod().equals("GET")) {
			log.debug("GET");
			tempDate = loginUsersRequests.getFrom_date();
//			loginUsersRequests.setFrom_date_history();
		} else {
			log.debug("POST");
		}
		log.debug("temp date " + tempDate);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
	   return loginUsersRequests ;
	}
	
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests) command;
		log.debug("request " + loginUsersRequests.getEmpCode());
		Map model=new HashMap();
	
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		log.debug("---ref-emp from session---"+emp);
		
		log.debug("=====emp.getEmpCode()==="+emp.getEmpCode());
		
		String emp_code = request.getParameter("empCodeQ");
		String request_date_from = request.getParameter("request_date_from");
		log.debug("---request_date_from--"+request_date_from);
		if (request_date_from==null || request_date_from.equals("")) {
			request_date_from = request.getParameter("date_from");
		}
		log.debug("---request_date_from--"+request_date_from);
		
	
		String request_date_to = request.getParameter("request_date_to");
		log.debug("---request_date_to--"+request_date_to);
		if (request_date_to==null || request_date_to.equals("")) {
			request_date_to = request.getParameter("date_to");
		}
		log.debug("---request_date_to--"+request_date_to);
		

		String statusId=request.getParameter("statusId");
		if (statusId!=null && statusId.isEmpty()) {
			statusId=null;
		}
		String codeFrom=request.getParameter("codeFrom");
		String codeTo=request.getParameter("codeTo");
		
		
		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
		
		String reqId = request.getParameter("reqId");
		model.put("reqId",reqId);
//		LoginUsersRequests loginUsersRequests = (LoginUsersRequests)requestsApprovalManager.getObject(LoginUsersRequests.class, new Long(reqId));
//		log.debug("request " + loginUsersRequests);
		
		model.put("from_date_history", tempDate);
		
		model.put("empCodeQ", emp_code);
		model.put("status", statusId);
		model.put("request_date_from", request_date_from);
		model.put("request_date_to", request_date_to);
		model.put("codeFrom", codeFrom);
		model.put("codeTo", codeTo);
		
		
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
		
//		loginUsersRequests.setFrom_date_history(loginUsersRequests.getFrom_date());
		if(errors.getErrorCount()==0)
		{	
			Calendar fromCal = Calendar.getInstance();
			fromCal.setTime(loginUsersRequests.getFrom_date());
			log.debug("fromCal " + fromCal.getTime());
			Calendar histCal = Calendar.getInstance();
			histCal.setTime(tempDate);
			log.debug("histCal " + histCal.getTime());
			log.debug("histCal.get(Calendar.DAY_OF_MONTH)!=fromCal.get(Calendar.DAY_OF_MONTH " + (histCal.get(Calendar.DAY_OF_MONTH)!=fromCal.get(Calendar.DAY_OF_MONTH)));
			if(histCal.get(Calendar.DAY_OF_MONTH)!=fromCal.get(Calendar.DAY_OF_MONTH) ||
					histCal.get(Calendar.MONTH)!=fromCal.get(Calendar.MONTH) ||
					histCal.get(Calendar.YEAR)!=fromCal.get(Calendar.YEAR) ) {
				errors.rejectValue("from_date", "commons.errors.youCanChangeTimeOnly");
			}
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
		
		String emp_code = request.getParameter("empCodeQ");
		String request_date_from = request.getParameter("request_date_from");
		log.debug("---request_date_from--"+request_date_from);
		if (request_date_from==null || request_date_from.equals("")) {
			request_date_from = request.getParameter("date_from");
		}
		log.debug("---request_date_from--"+request_date_from);
		
	
		String request_date_to = request.getParameter("request_date_to");
		log.debug("---request_date_to--"+request_date_to);
		if (request_date_to==null || request_date_to.equals("")) {
			request_date_to = request.getParameter("date_to");
		}
		log.debug("---request_date_to--"+request_date_to);
		

		String statusId=request.getParameter("statusId");
		if (statusId!=null && statusId.isEmpty()) {
			statusId=null;
		}
		String codeFrom=request.getParameter("codeFrom");
		String codeTo=request.getParameter("codeTo");
		///////////////////////modifing attendance time by manager//////////////////////////////
//		loginUsersRequests.setFrom_date_history(loginUsersRequests.getFrom_date());
		loginUsersRequests.setManagerModifiedDate(emp);
		loginUsersRequests.setNotes(loginUsersRequests.getNotes()+" (Attendance had been modified by manager)");
		///////////////////////////////////////////////////////////////////////////////////////
		loginUsersRequests.setPeriod_from(loginUsersRequests.getFrom_date());
		loginUsersRequests.setFrom_date_history(tempDate);
		
		loginUsersRequests.setEmpCode(loginUsersRequests.getLogin_user().getEmpCode().getEmpCode());
		requestsApprovalManager.saveObject(loginUsersRequests);
		RequestApproval approvals = new RequestApproval();
		approvals.setApprove("1");
		approvals.setNotes(loginUsersRequests.getManagerModifiedDate().getFirstName() + "had modified attendance from " + loginUsersRequests.getFrom_date_history()  + " to " + loginUsersRequests.getFrom_date());
		approvals.setRequestId(loginUsersRequests.getId()+"");
		requestsApprovalManager.approvalsAccessLevels(approvals, loginUsersRequests,emp);
//		requestsApprovalManager.automaticApprovalsAccessLevels(approvals, loginUsersRequests);

		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<  End onSubmit : <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//		String url="/attendanceRequestsReports.html";
//		log.debug(url);
		Map model = new HashMap();
		model.put("empCodeQ", emp_code);
		model.put("status", statusId);
		model.put("request_date_from", request_date_from);
		model.put("request_date_to", request_date_to);
		model.put("codeFrom", codeFrom);
		model.put("codeTo", codeTo);
		String reqId = request.getParameter("reqId");
		model.put("reqId",reqId);
		
		return new ModelAndView(new RedirectView("attendanceRequestsReports.html"),model);
	}

}
