package com._4s_.i18n.web.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.i18n.dao.MessageDAO;
import com._4s_.i18n.model.Key;
import com._4s_.i18n.model.MyLocale;
import com._4s_.i18n.model.MyMessage;
import com._4s_.i18n.service.LocaleSource;
import com._4s_.i18n.service.MessageManager;
import com._4s_.i18n.service.ResourceMapMessageSource;
import com._4s_.requestsApproval.model.AnnualVacLimit;

@Controller
@RequestMapping("/editMessage.html")
public class EditMessage extends BaseSimpleFormController {

	// get message of requested key of current locale
	@Autowired
	private MessageManager mgr = null;
	@Autowired
	private MessageDAO messageDAO = null;
	
	public MessageManager getMgr() {
		return mgr;
	}

	public void setMgr(MessageManager mgr) {
		this.mgr = mgr;
	}


	@Autowired
	private ResourceMapMessageSource messageSource = null;

	private MyLocale myLocale = null;
	private LocaleSource localeSource = null;

	public ResourceMapMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(ResourceMapMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public LocaleSource getLocaleSource() {
		return localeSource;
	}

	public void setLocaleSource(LocaleSource localeSource) {
		this.localeSource = localeSource;
	}

	public MessageDAO getMessageDAO() {
		return messageDAO;
	}

	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	@RequestMapping(method = RequestMethod.POST) // ,consumes=MediaType.APPLICATION_FORM_URLENCODED
	public ModelAndView processSubmit(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("msg") MyMessage command, BindingResult errors, SessionStatus status, Map model)
			throws Exception {
		log.debug("entering 'onSubmit' method....");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		MyMessage msg = command;
		log.debug("msg.getMyLocale() " + msg.getMyLocale());
		log.debug("key " + msg.getKey());
		log.debug("msg " + msg.getMessage());
		if (msg.getMyLocale() == null) {
			msg.setMyLocale(myLocale);
		}
		log.debug("messageSource.getCachedResourceMaps() " + messageSource.getCachedResourceMaps());

		msg.setMyLocale(myLocale);
		mgr.saveObject(msg);
		Map newResource = (Map) (messageSource.getCachedResourceMaps().get(msg.getMyLocale()));
		log.debug("-------------------------------------------------------------");
		log.debug(
				"---------------------------getMyLocale()" + msg.getMyLocale() + "----------------------------------");
		log.debug("--------------------------------------------------------------");
		newResource.put(msg.getKey().getName(), msg.getMessage());

		// add the msg to the key list of msgs
//		msg.getKey().getMessages().add(msg);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<script>window.opener.location.reload();window.close();</script>");
		out.close();
		return new ModelAndView();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model, HttpServletRequest request) {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		MyMessage msg = null;
		Key key = null;

		Long keyId;
		String myKey = request.getParameter("myKey");
		myLocale = mgr.getDefault();
		log.info(">>>>>>>>>>>>>>>>>>>LOCale" + myLocale);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>myKey " + myKey);
		if ((myKey != null) && !myKey.equals("")) {
//			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + msg + "<<<<<<<<<<<<");			
			msg = mgr.getMessageByKeyName(myKey, myLocale);
			myLocale = msg.getMyLocale();
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>myKey " + myKey);
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>myKey message according to locale " + msg + "<<<<<<<<<<<<");
		}
		model.addAttribute("msg", msg);
		model.addAttribute("myKey", myKey);
		return "editMessage";
	}

	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request) {

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>populate");
		String myKey = request.getParameter("myKey");

		Map model = new HashMap();
		model.put("myKey", myKey);
		return model;
	}
}
