package com._4s_.requestsApproval.web.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Employee;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.service.RequestsApprovalManager;

public class SendEmailAlarmForm extends BaseSimpleFormController{
	
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

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception 
	{
		
		//Span span = (Span) command;
		
//		if (request.getParameter("submit") != null)
//		{
//			Calendar cal=Calendar.getInstance();
//			cal.setTime(span.getStartDate());
//			cal.set(Calendar.SECOND,0);
//			span.setStartDate(cal.getTime());
//			cal.setTime(span.getEndDate());
//			cal.set(Calendar.SECOND,0);
//			cal.add(Calendar.MINUTE,1);
//			span.setEndDate(cal.getTime());
//		}	
//		attendanceManager.saveSpan(span);
		
		//Employee emp=span.getEmployee();
		Employee emp=(Employee)request.getSession().getAttribute("employee");
		
		// send mail to emp
		
		MimeMessage msg=mailSender.createMimeMessage();	
		
		MimeMessageHelper helper = new MimeMessageHelper(msg,"UTF-8");
						
		//Calendar requestDate=Calendar.getInstance();
		//requestDate.setTime(excuseRequest.getRequestDate());
//		Calendar startDate=Calendar.getInstance();
//		startDate.setTime(span.getStartDate());
//		Calendar endDate=Calendar.getInstance();
//		endDate.setTime(span.getEndDate());
		
		String mm="<html dir=\"rtl\" lang=\"ar\">";
		mm+="<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head>";
		mm+="<body>";
		
		mm+="<br>Ø§Ù„Ø³ÙŠØ¯: "+emp.getFirstName()+" "+emp.getLastName();
		mm+="<br>Ù†ÙˆØ¯ Ø§Ø¨Ù„Ø§Øº Ø³ÙŠØ§Ø¯ØªÙƒÙ… Ø¨Ø§Ù†Ù‡ Ù‚Ø¯ ØªÙ… ";
//		if(span.getStartDate()!=null)
//		{
//			mm+="Ø§Ù„Ù…ÙˆØ§Ù�Ù‚Ø© Ø¹Ù„ÙŠ ";
//		}
//		else
//		{
//			mm+="Ø±Ù�Ø¶ ";
//		}	
//		mm+="<br>Ø·Ù„Ø¨ Ø§Ù„Ø­Ø¶ÙˆØ± Ø§Ù„Ù…Ù‚Ø¯Ù… Ù…Ù† Ø³ÙŠØ§Ø¯ØªÙƒÙ… Ù„Ø¥Ø«Ø¨Ø§Øª Ø§Ù„Ø­Ø¶ÙˆØ± Ù�ÙŠ ÙŠÙˆÙ…: "+startDate.get(Calendar.DAY_OF_MONTH)+"-"+(startDate.get(Calendar.MONTH)+1)+"-"+startDate.get(Calendar.YEAR);
//		mm+="<br>Ù…Ù†: "+startDate.get(Calendar.HOUR_OF_DAY)+":"+startDate.get(Calendar.MINUTE);
//		mm+=" Ø¥Ù„ÙŠ: "+endDate.get(Calendar.HOUR_OF_DAY)+":"+endDate.get(Calendar.MINUTE);

		mm+="<br>Ù…Ø¹ Ø®Ø§Ù„Øµ Ø§Ù„Ø´ÙƒØ±";
		mm+="<br>Ø§Ù„Ù‡ÙŠØ¦Ø© Ø§Ù„Ø³Ø¹ÙˆØ¯ÙŠØ© Ù„Ù„ØªØ®ØµØµØ§Øª Ø§Ù„Ø·Ø¨ÙŠØ©";							
		mm+="</body></html>";
		
		try
		{	Properties props = new Properties();
			props.put("mail.smtp.starttls.enable","true");
			props.put( "mail.smtp.auth", "true" );
			props.put("mail.smtp.starttls.required", "true");
			props.put("mail.smtp.port", "465");
			//props.put("mail.smtp.host", getServletContext().getInitParameter("host"));
			
			mailSender.setJavaMailProperties(props);
			helper.setFrom("mfarahat@4s-systems.com");
			helper.setTo("wesamsobhy89@gmail.com");
			//mailSender.setHost(getServletContext().getInitParameter("host"));
	      // helper.setFrom(getServletContext().getInitParameter("username"));
			//helper.setTo(getServletContext().getInitParameter("to"));
			//"ahmed.abacy@gmail.com"
			helper.setSubject("Test");
			helper.setText(mm,true);
			helper.setSentDate(new Date());
			
			mailSender.send(msg);
			log.debug("mail sendt to emp");
		}
		catch(MessagingException ex)
		{
			log.debug(ex.getMessage());
			log.debug(ex.getStackTrace());
		}
		
//		send email to the manager
//		MimeMessage msg1=mailSender.createMimeMessage();
//		MimeMessageHelper helper1 = new MimeMessageHelper(msg1,"UTF-8");
//		mm="";
//		mm+="<html dir=\"rtl\" lang=\"ar\">";
//		mm+="<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head>";
//		mm+="<body>";
//		
//		mm+="<br>Ø§Ù„Ø³ÙŠØ¯: "+sessionEmp.getFirstName()+" "+sessionEmp.getLastName();
//		mm+="<br>Ù†ÙˆØ¯ Ø§Ø¨Ù„Ø§Øº Ø³ÙŠØ§Ø¯ØªÙƒÙ… Ø¨Ø§Ù†Ù‡ Ù‚Ø¯ ØªÙ… ";
//		if(span.getStartDate()!=null)
//		{
//			mm+="Ø§Ù„Ù…ÙˆØ§Ù�Ù‚Ø© Ø¹Ù„ÙŠ ";
//		}
//		else
//		{
//			mm+="Ø±Ù�Ø¶ ";
//		}
//		mm+="<br>Ø§Ù„Ø·Ù„Ø¨ Ø§Ù„Ù…Ù‚Ø¯Ù… Ù…Ù† Ø§Ù„Ø³ÙŠØ¯: "+emp.getFirstName()+" "+emp.getLastName();
//		mm+="<br>Ù„Ø¥Ø«Ø¨Ø§Øª Ø§Ù„Ø­Ø¶ÙˆØ±Ù�ÙŠ ÙŠÙˆÙ…: "+startDate.get(Calendar.DAY_OF_MONTH)+"-"+(startDate.get(Calendar.MONTH)+1)+"-"+startDate.get(Calendar.YEAR);
//		mm+="<br>Ù…Ù†: "+startDate.get(Calendar.HOUR_OF_DAY)+":"+startDate.get(Calendar.MINUTE);
//		mm+=" Ø¥Ù„ÙŠ: "+endDate.get(Calendar.HOUR_OF_DAY)+":"+endDate.get(Calendar.MINUTE);
//		mm+="<br>";
//		
//		mm+="<br>Ù…Ø¹ Ø®Ø§Ù„Øµ Ø§Ù„Ø´ÙƒØ±";
//		mm+="<br>Ø§Ù„Ù‡ÙŠØ¦Ø© Ø§Ù„Ø³Ø¹ÙˆØ¯ÙŠØ© Ù„Ù„ØªØ®ØµØµØ§Øª Ø§Ù„Ø·Ø¨ÙŠØ©";							
//		mm+="</body></html>";
//		
//		try
//		{				
//			helper1.setFrom("schfs@schfs.com");
//			helper1.setTo(sessionEmp.getEmail());
//			helper1.setSubject("Ø­Ø§Ù„Ø© Ø§Ù„Ø·Ù„Ø¨");
//			helper1.setText(mm,true);
//			helper1.setSentDate(new Date());
//			
//			mailSender.send(msg1);
//			log.debug("mail sendt to manager");
//		}
//		catch(MessagingException ex)
//		{
//			log.debug(ex.getMessage());
//			log.debug(ex.getStackTrace());
//		}
		
		
		return new ModelAndView(new RedirectView("sendEmailAlarm.html"));
	}

	protected void onBindAndValidate(HttpServletRequest request, Object command,
			BindException errors) throws Exception {
		//((RequestValidator)getValidator()).setRequest(request);
		Employee employee = ( Employee )command;
//
//		if (request.getParameter("submit") != null){
//			Date startDate = span.getStartDate();
//			log.debug(">>>>>>>>>>>>>>>>>>>startDate "+startDate+">>>>>>>>>>>>>>>>>>>>>");
//			Date requestedStartDate = span.getRequestedStartDate();
//			log.debug(">>>>>>>>>>>>>>>>>>>requestedStartDate "+requestedStartDate+">>>>>>>>>>>>>>>>>>>>>");
//			
//			Date endDate = span.getEndDate();
//			log.debug(">>>>>>>>>>>>>>>>>>>endDate "+endDate+">>>>>>>>>>>>>>>>>>>>>");
//			Date requestedEndDate = span.getRequestedEndDate();
//			log.debug(">>>>>>>>>>>>>>>>>>>requestedEndDate "+requestedEndDate+">>>>>>>>>>>>>>>>>>>>>");
//			
//			if (startDate.before(requestedStartDate)){
//				log.debug(">>>>>>>>>>>>>>>>>>>Start Date Error>>>>>>>>>>>>>>>>>>>>>");
//				errors.rejectValue("requestedStartDate","attendance.errors.fromTimeShouldBeGreaterThanOrEqualRequestedStartTime","error1");
//			}
//			else {
//				if (endDate.after(requestedEndDate)){
//					log.debug(">>>>>>>>>>>>>>>>>>>End Date Error>>>>>>>>>>>>>>>>>>>>>");
//					log.debug(">>> endDate.getTime(): "+ endDate.getTime() ) ;
//					log.debug(">>> requestedEndDate.getTime(): "+ requestedEndDate.getTime() ) ;
//					errors.rejectValue("requestedEndDate","attendance.errors.toTimeShouldBeLessThanOrEqualRequestedEndTime","error2");
//				}
//				else {
//					if (startDate.after(endDate)){
//						log.debug(">>>>>>>>>>>>>>>>>>>Start/End Date Error>>>>>>>>>>>>>>>>>>>>>");
//						errors.rejectValue("requestedEndDate","attendance.errors.toTimeShouldBeGreaterThanOrEqualFromTime","error3");
//					}
//				}
//			}
//		}
//		
//		super.onBindAndValidate(request, command, errors);
	}
	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception {
		Employee employee = ( Employee )command;
	}
	
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		//String spanId = request.getParameter("spanId");
		Employee employee = new Employee();
		return employee;
	}
	
	protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
		Employee employee = new Employee();
		Map model = new HashMap();
		
		return model;
	}
}
