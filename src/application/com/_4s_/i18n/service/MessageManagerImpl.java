package com._4s_.i18n.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com._4s_.common.service.BaseManagerImpl;
import com._4s_.i18n.dao.MessageDAO;
import com._4s_.i18n.model.Key;
import com._4s_.i18n.model.MyLocale;
import com._4s_.i18n.model.MyMessage;

public class MessageManagerImpl extends BaseManagerImpl implements MessageManager{
	private MessageDAO messageDAO;

	public MessageDAO getMessageDAO() {
		return messageDAO;
	}

	public void setMessageDAO(MessageDAO messageDAO) {
		log.debug("test " + messageDAO);
		this.messageDAO = messageDAO;
	}

	public Key getKeyByName(String keyName) {
		// TODO Auto-generated method stub
		return messageDAO.getKeyByName(keyName);
	}

	public MyMessage getMessageByKeyName(String keyName,MyLocale myLocale) {
		// TODO Auto-generated method stub
		return messageDAO.getMessageByKeyName(keyName,myLocale);
	}

	public MyLocale getDefault() {
		// TODO Auto-generated method stub
		return messageDAO.getDefault();
	}

	public List getMessagesByLocale(MyLocale myLocale) {
		// TODO Auto-generated method stub
		return messageDAO.getMessagesByLocale(myLocale);
	}
	
}
