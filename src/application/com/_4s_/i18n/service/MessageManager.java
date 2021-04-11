package com._4s_.i18n.service;

import java.util.List;

import com._4s_.common.service.BaseManager;
import com._4s_.i18n.dao.MessageDAO;
import com._4s_.i18n.model.Key;
import com._4s_.i18n.model.MyLocale;
import com._4s_.i18n.model.MyMessage;

public interface MessageManager extends BaseManager{
	public void setMessageDAO(MessageDAO messageDAO);
	public MyMessage getMessageByKeyName(final String keyName,MyLocale myLocale);
	public Key getKeyByName(final String keyName);
	public MyLocale getDefault();
	public List getMessagesByLocale(final MyLocale myLocale);
}
