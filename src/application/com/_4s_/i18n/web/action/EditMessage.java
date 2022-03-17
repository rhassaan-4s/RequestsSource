package com._4s_.i18n.web.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.i18n.dao.MessageDAO;
import com._4s_.i18n.model.Key;
import com._4s_.i18n.model.MyLocale;
import com._4s_.i18n.model.MyMessage;
import com._4s_.i18n.service.LocaleSource;
import com._4s_.i18n.service.MessageManager;
import com._4s_.i18n.service.ResourceMapMessageSource;

public class EditMessage extends BaseSimpleFormController {

	// get message of requested key of current locale

	private MessageManager mgr = null;

	private MessageDAO messageDAO = null;

	private MyLocale myLocale = null;

	private LocaleSource localeSource = null;

	private ResourceMapMessageSource cacheMap = null;

	public ResourceMapMessageSource getCacheMap() {
		return cacheMap;
	}

	public void setCacheMap(ResourceMapMessageSource cacheMap) {
		this.cacheMap = cacheMap;
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

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException arg3)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'onSubmit' method....");
		}
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		MyMessage msg = (MyMessage) command;

		mgr.saveObject(msg);
		Map newResource = (Map)(cacheMap.getCachedResourceMaps().get(msg.getMyLocale()));
		log.debug("-------------------------------------------------------------");
		log.debug("---------------------------getMyLocale()"+msg.getMyLocale()+"----------------------------------");
		log.debug("--------------------------------------------------------------");
		newResource.put(msg.getKey().getName(),msg.getMessage());
		
		//add the msg to the key list of msgs
//		msg.getKey().getMessages().add(msg);
		log
				.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<script>window.opener.location.reload();window.close();</script>");
		out.close();
		return new ModelAndView();
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		MyMessage msg = null;
		Key key = null;

		Long keyId;
		String myKey = request.getParameter("myKey");
		myLocale = mgr.getDefault();
		log.info(">>>>>>>>>>>>>>>>>>>LOCale" + myLocale);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>myKey" + myKey);
		if ((myKey != null) && !myKey.equals("")) {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>myKey" + myKey);
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + msg + "<<<<<<<<<<<<");			
			msg = mgr.getMessageByKeyName(myKey, myLocale);
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + msg + "<<<<<<<<<<<<");		
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>myKey" + myKey);
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + msg + "<<<<<<<<<<<<");
		} else {
			msg = new MyMessage();
			msg.setKey(key);

		}
		return msg;
	}

	public MessageManager getMgr() {
		return mgr;
	}

	public void setMgr(MessageManager mgr) {
		this.mgr = mgr;
	}

	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws Exception {

		String myKey = request.getParameter("myKey");

		Map model = new HashMap();
		model.put("myKey", myKey);
		return model;
	}
}
