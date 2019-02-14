package com._4s_.security.web.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.Employee;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.security.model.User;
import com._4s_.security.service.MySecurityManager;

public class ForgetPasswordController extends BaseSimpleFormController {
	private final Log log = LogFactory.getLog(getClass());

	private MySecurityManager securityManager;

	private JavaMailSenderImpl mailSender;

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException error)
			throws Exception {

		Map model = new HashMap();
		User commandUser = (User) command;
		List users = securityManager.getUserByEmail(commandUser.getEmployee()
				.getEmail());
		if (users.size() > 0) {
			User user = (User) users.get(0);
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");

			String mm = "<html dir=\"rtl\" lang=\"ar\">";
			mm += "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head>";
			mm += "<body>";

			mm += "<br>" + user.getEmployee().getFirstName() + " "
					+ user.getEmployee().getLastName();
			
			mm += "<br>Your User Name :" + user.getUsername() + "<br>and Password :"
			+ user.getPassword();

			mm += "</body></html>";

			try {
				helper.setFrom("schfs@schfs.com");
				helper.setTo(user.getEmployee().getEmail());
				helper.setSubject("Forget Password");
				helper.setText(mm, true);
				helper.setSentDate(new Date());

				mailSender.send(msg);
			} catch (MessagingException ex) {
			}
		}

		return new ModelAndView("login", model);
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		User user = new User();
		Employee employee = new Employee();
		user.setEmployee(employee);
		return user;
	}

	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		User commandUser = (User) command;
		List users = securityManager.getUserByEmail(commandUser.getEmployee()
				.getEmail());
		if (users.size() != 1 && !errors.hasErrors()) {
			errors.reject("commons.errors.invalidEmailFormat");
		}

	}

	public void setSecurityManager(MySecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	public MySecurityManager getSecurityManager() {
		return securityManager;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}
}
