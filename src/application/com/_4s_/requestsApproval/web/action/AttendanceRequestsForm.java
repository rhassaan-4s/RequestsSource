package com._4s_.requestsApproval.web.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.restServices.json.RestStatus;

public class AttendanceRequestsForm extends BaseSimpleFormController{

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
		LoginUsersRequests loginUsersRequests = new LoginUsersRequests();
		String empRequestTypeId=request.getParameter("empRequestTypeId");
		
		if(empRequestTypeId == null || empRequestTypeId.equals("")){
			
			log.debug("loginUsersRequests------"+loginUsersRequests);
			loginUsersRequests = new LoginUsersRequests();
			if(loginUsers!=null){
				loginUsersRequests.setLogin_user(loginUsers);
				loginUsersRequests.setEmpCode(loginUsers.getEmpCode());
			}
		}else{
			log.debug("loginUsersRequests------"+loginUsersRequests);
			loginUsersRequests = (LoginUsersRequests) requestsApprovalManager.getObject(LoginUsersRequests.class, new Long(empRequestTypeId));
			log.debug("-------period from--formbacking-"+loginUsersRequests.getPeriod_from());
			log.debug("-------request id from--formbacking-"+loginUsersRequests.getRequest_id().getId());
		}
		
		log.debug("empRequestTypeId------"+empRequestTypeId);


		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
	   return loginUsersRequests ;
	}
	
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>"+ Calendar.getInstance().getTime());
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests) command;
		log.debug("-------period from--referense-"+loginUsersRequests.getPeriod_from());
		Map model=new HashMap();
	
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		log.debug("---ref-emp from session---"+request.getSession().getAttribute("employee"));
		
		log.debug("=====emp.getEmpCode()==="+emp.getEmpCode());
		
		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
		
		String empRequestTypeId=request.getParameter("empRequestTypeId");
		log.debug("empRequestTypeId------"+empRequestTypeId);
		model.put("empRequestTypeId",empRequestTypeId);
		
		List tempList= new ArrayList();
		List requestTypeList= requestsApprovalManager.getObjects(RequestTypes.class);
		for (int i = 0; i < requestTypeList.size(); i++) {
			RequestTypes temp = (RequestTypes) requestTypeList.get(i);
			log.debug("temp.id-----= "+temp.getId());
			if(temp.getId().equals(new Long(10)) || temp.getId().equals(new Long(11))){
				tempList.add(temp);
			}
		}
		log.debug("tempList.size-----= "+tempList.size());
		model.put("requestTypeList", tempList);
		model.put("employeeCode", emp.getEmpCode());
		if(loginUsers!=null){
			model.put("employeeName", loginUsers.getName());
			log.debug("====loginUsers.getName()==="+loginUsers.getName());
		}
		
		if(empRequestTypeId!=null && !empRequestTypeId.equals("")){
			LoginUsersRequests logRequests = (LoginUsersRequests) requestsApprovalManager.getObject(LoginUsersRequests.class, new Long(empRequestTypeId));
			log.debug("----mmname---"+logRequests.getLogin_user().getName());
//			LoginUsers loginUsers =(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp_code);
			model.put("mm_name", logRequests.getLogin_user().getName());
			log.debug("----logRequests.getEmpCode()---"+logRequests.getEmpCode());
			model.put("emplCode", logRequests.getEmpCode());
		}
		
		String done=request.getParameter("done");
		String reqId=request.getParameter("requestId");
		
		log.debug("before email sending " + Calendar.getInstance().getTime());
		if(done!=null && reqId!=null){
			model.put("done", done);
		}
	
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>"+ Calendar.getInstance().getTime());
		return model;
	}
	

	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests)command;
		log.debug("-------period from---"+loginUsersRequests.getPeriod_from());
		String emp_code = request.getParameter("empCode");
		log.debug("code entered--------"+emp_code);
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>" + Calendar.getInstance().getTime());
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests)command;
	
		
		if(errors.getErrorCount()==0)
		{	
			if(loginUsersRequests==null || loginUsersRequests.equals("")){
				errors.rejectValue("empCode", "commons.errors.requiredFields");
			}
			else{
				Date now = Calendar.getInstance().getTime();	
				if(loginUsersRequests.getRequest_id()==null || loginUsersRequests.getRequest_id().equals(""))
				{
					errors.rejectValue("request_id", "commons.errors.requiredFields");
				}
				
				if(loginUsersRequests.getPeriod_from()==null || loginUsersRequests.getPeriod_from().equals(""))
				{
					errors.rejectValue("request_id", "commons.errors.requiredFields");
				} else {
					Long attendanceType = null;
					Employee emp = (Employee)requestsApprovalManager.getObjectByParameter(Employee.class, "empCode", loginUsersRequests.getEmpCode());
					if (loginUsersRequests.getRequest_id().getId().equals(new Long(10))) {
						attendanceType  = new Long(1);
					} else if (loginUsersRequests.getRequest_id().getId().equals(new Long(11))) {
						attendanceType  = new Long(2);
					}
					RestStatus status = requestsApprovalManager.validateSignInOut(attendanceType, loginUsersRequests.getPeriod_from(), emp);
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
						errors.reject(i18nkey);
					}
				}
				
//				if(loginUsersRequests.getNotes()==null || loginUsersRequests.getNotes().equals(""))
//				{
//					errors.rejectValue("request_id", "commons.errors.requiredFields");
//				}
				
			}
		}

		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>"+ Calendar.getInstance().getTime());
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>" + Calendar.getInstance().getTime());
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests)command;
		
		loginUsersRequests.setInputType(new Integer(1));//request to sign in

		log.debug("----loginUsersRequests.getId()-onsubmit-----"+loginUsersRequests.getId()+"-----loginUsersRequests---"+loginUsersRequests.getLogin_user().getEmpCode());
		
		Map model=new HashMap();
		
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		String longitude = (String)request.getParameter("longitude");
		String latitude =  (String)request.getParameter("latitude");
		String accuracy =  (String)request.getParameter("accuracy");
		String address = "";
		log.debug("accuracy " + accuracy);
		if (accuracy!=null && !accuracy.isEmpty()) {
			if (settings.getLocationAccuracy()>= Integer.parseInt(accuracy)) {
				address = requestsApprovalManager.getAddressByGpsCoordinates(longitude, latitude);
			} else {
				address = "Address is not accurate to be saved";
			}
		}
		
		log.debug(longitude + "-" + latitude + "-" + address);
		
		
		String reqId="";
		if(loginUsersRequests.getRequest_id()!=null && !loginUsersRequests.getRequest_id().equals("")){
			log.debug("entered--1-----reqid=-"+loginUsersRequests.getRequest_id().getId());
			reqId=loginUsersRequests.getRequest_id().getId().toString();
		}
			 // request number
			if (loginUsersRequests.getId() == null){
				String requestNumber="";
				requestNumber=requestsApprovalManager.CreateRequestNumber();
				loginUsersRequests.setRequestNumber(requestNumber);
			}
			
			log.debug("loginUsersRequests.getEmpCode() entered--------"+loginUsersRequests.getEmpCode());
			
		
			if(loginUsersRequests.getApproved()==null || loginUsersRequests.getApproved().equals("")){
				loginUsersRequests.setApproved(new Long(0));	
			}
			if(loginUsersRequests.getApplicable()==null || loginUsersRequests.getApplicable().equals("")){
				loginUsersRequests.setApplicable(new Long(1));			
			}
			if(loginUsersRequests.getPosted()==null||loginUsersRequests.getPosted().equals("")){
				loginUsersRequests.setPosted(new Long(0));
			}
			if(loginUsersRequests.getReply()==null||loginUsersRequests.getReply().equals("")){
				loginUsersRequests.setReply("--");
			}
			if(loginUsersRequests.getLeave_effect()==null||loginUsersRequests.getLeave_effect().equals("")){
				loginUsersRequests.setLeave_effect("0");
			}
			if(loginUsersRequests.getLeave_type()==null||loginUsersRequests.getLeave_type().equals("")){
				loginUsersRequests.setLeave_type("0");
			}
			if(loginUsersRequests.getFrom_date()==null|| loginUsersRequests.getFrom_date().equals("")){
				loginUsersRequests.setFrom_date(loginUsersRequests.getPeriod_from());
			}

			log.debug("---requestType from jsp---"+request.getParameter("requestType"));
	//		loginUsersRequests.setApproved();
			
			if (latitude!= null && !latitude.isEmpty()) {
				loginUsersRequests.setLatitude(Double.parseDouble(latitude));
				loginUsersRequests.setLongitude(Double.parseDouble(longitude));
				loginUsersRequests.setLocationAddress(address);
			}

			if (accuracy!=null && !accuracy.isEmpty()) {
				double distance = requestsApprovalManager.distance(new Double(latitude),new Double(longitude),new Double(settings.getCompanyLat()),new Double(settings.getCompanyLong()));
				if (distance>settings.getDistAllowedFromCompany()) {
					loginUsersRequests.setIsInsideCompany(false);
				} else {
					loginUsersRequests.setIsInsideCompany(true);
				}
			} else {
				loginUsersRequests.setIsInsideCompany(true);
			}

			requestsApprovalManager.saveObject(loginUsersRequests);
				
			
			String url="attendanceRequestForm.html?done=true&requestId="+reqId;
	
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<  End onSubmit : <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+ Calendar.getInstance().getTime());
	
			return new ModelAndView(new RedirectView(url));
		
		//return new ModelAndView(new RedirectView(getSuccessView()));
	}
}
