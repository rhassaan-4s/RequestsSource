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
import com._4s_.common.model.Employee;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;

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
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
		if(done!=null && reqId!=null){
			model.put("done", done);
			
			MimeMessage msg=mailSender.createMimeMessage();	
			MimeMessageHelper helper = new MimeMessageHelper(msg,"UTF-8");
			
			List toMgrs=new ArrayList();
			log.debug("---empId---"+emp.getId());
			LoginUsers employee= (LoginUsers) requestsApprovalManager.getObject(LoginUsers.class, emp.getId());
			log.debug("---employee---"+employee.getId());
			List fields=new ArrayList();
			fields.add("id");
			RequestTypes req=(RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, Long.parseLong(reqId));
			List empAcc= (List)requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(EmpReqTypeAcc.class, "emp_id", employee,"req_id",req,fields);
			for (int j = 0; j < empAcc.size(); j++) {
				EmpReqTypeAcc empacc= (EmpReqTypeAcc) empAcc.get(j);
				log.debug("---empreqacc---"+empacc.getId());
				log.debug("---group---"+empacc.getGroup_id().getId());
				List mgrs=new ArrayList();
				mgrs= (List) requestsApprovalManager.getObjectsByParameter(AccessLevels.class, "level_id", empacc.getGroup_id());
				//toMgrs.addAll(toMgrs);
				log.debug("---mgrs list size---"+mgrs.size());
				for (int i = 0; i < mgrs.size(); i++) {
					AccessLevels mgr= (AccessLevels) mgrs.get(i);
					//toMgrs.add(mgr);
					log.debug("---mgr ---"+mgr.getEmp_id().getId());
					String mm="<html dir=\"rtl\" lang=\"ar\">";
					mm+="<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head>";
					mm+="<body>";
					
					mm+="<br>√” «–/ "+mgr.getEmp_id().getName();
					mm+="<br>·œÌﬂ ÿ·»«  ··„Ê«›ﬁÂ ⁄·ÌÂ« »—Ã«¡ «·«ÿ·«⁄ ⁄·ÌÂ« „‰: http://10.8.2.203:8080/Requests/security/login.html";
					mm+="<br>„⁄ Œ«·’ «·‘ﬂ—";
					
					try
					{	Properties props = new Properties();
						props.put("mail.smtp.starttls.enable","true");
						props.put( "mail.smtp.auth", "true" );
						props.put("mail.smtp.starttls.required", "true");
						props.put("mail.smtp.port", "25");
						//props.put("mail.smtp.host", getServletContext().getInitParameter("host"));
						
						mailSender.setJavaMailProperties(props);
						helper.setFrom("att@4s-systems.com");
						helper.setTo(mgr.getEmp_id().getEmail());
						//helper.setTo("wesamsobhy89@gmail.com");
						//mailSender.setHost(getServletContext().getInitParameter("host"));
				      // helper.setFrom(getServletContext().getInitParameter("username"));
						//helper.setTo(getServletContext().getInitParameter("to"));
						helper.setSubject("Requests to be approved");
						helper.setText(mm,true);
						helper.setSentDate(new Date());
						
						mailSender.send(msg);
						log.debug("mail sent to emp");
					}
					catch(MessagingException ex)
					{
						log.debug(ex.getMessage());
						log.debug(ex.getStackTrace());
					}
					catch(MailSendException me) {
						log.debug("can't send email");
					} finally {
						log.debug(">>>>>>>>>>>>>>>>>>>>>>> finally block >>>>>>>>>>>>>>>>>>>>>>>>>>>");
					}
					
				}
			}
		}
	
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}
	

	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests)command;
//		String req_type= request.getParameter("request_id");
//		RequestTypes requestTypes = new RequestTypes();
//		if(req_type.equals("10")){
//			requestTypes.setId(new Long(10));
//			requestTypes.setDescription("In");
//		}else if(req_type.equals("11")){
//			requestTypes.setId(new Long(11));
//			requestTypes.setDescription("Out");
//		}
//		loginUsersRequests.setRequest_id(requestTypes);
		log.debug("-------period from---"+loginUsersRequests.getPeriod_from());
		String emp_code = request.getParameter("empCode");
		log.debug("code entered--------"+emp_code);
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests)command;
	
		
		if(errors.getErrorCount()==0)
		{	
			if(loginUsersRequests==null || loginUsersRequests.equals("")){
				errors.rejectValue("empCode", "commons.errors.requiredFields");
			}
			else{
					
				if(loginUsersRequests.getRequest_id()==null || loginUsersRequests.getRequest_id().equals(""))
				{
					errors.rejectValue("request_id", "commons.errors.requiredFields");
				}
				
				if(loginUsersRequests.getPeriod_from()==null || loginUsersRequests.getPeriod_from().equals(""))
				{
					errors.rejectValue("request_id", "commons.errors.requiredFields");
				}	
				
//				if(loginUsersRequests.getNotes()==null || loginUsersRequests.getNotes().equals(""))
//				{
//					errors.rejectValue("request_id", "commons.errors.requiredFields");
//				}
				
			}
		}

		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests)command;
		
		loginUsersRequests.setInputType(new Integer(1));//request to sign in

		log.debug("----loginUsersRequests.getId()-onsubmit-----"+loginUsersRequests.getId()+"-----loginUsersRequests---"+loginUsersRequests.getLogin_user().getEmpCode());
		
		Map model=new HashMap();
		
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
			

			requestsApprovalManager.saveObject(loginUsersRequests);
				
			
			String url="attendanceRequestForm.html?done=true&requestId="+reqId;
	
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<  End onSubmit : <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	
			return new ModelAndView(new RedirectView(url));
		
		//return new ModelAndView(new RedirectView(getSuccessView()));
	}
}
