package com._4s_.i18n.service;

import org.springframework.beans.factory.annotation.Autowired;

import com._4s_.i18n.dao.MessageDAO;
import com._4s_.i18n.model.MyLocale;

public class DefaultLocale implements LocaleSource {

	private MyLocale defaultMyLocale = null;

	private MyLocale myLocale = null;

	private MessageDAO messageDAO;

	public MessageDAO getMessageDAO() {
		return messageDAO;
	}

	public void setMessageDAO(MessageDAO messageDAO) {
		System.out.println("messageDAO " + messageDAO);
		this.messageDAO = messageDAO;
	}

	public void setDefaultMyLocale(MyLocale defaultMyLocale) {
		this.defaultMyLocale = defaultMyLocale;
	}

	public void setMyLocale(MyLocale myLocale) {
		this.myLocale = myLocale;
	}

	public MyLocale getDefaultMyLocale() {
		// TODO Auto-generated method stub

		return defaultMyLocale;
		//return myLocale;
	}

	public MyLocale getPreferenceMyLocale() {
		return myLocale;
		//return defaultMyLocale;
	}

	public void init() {
		// get the default locale from database and
		// create MyLocale object and assign it
		// to the defaultMyLocale attribute
		myLocale = messageDAO.getDefault();
		//System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>" + myLocale.getLanguage());
		defaultMyLocale = myLocale;
	}

}
