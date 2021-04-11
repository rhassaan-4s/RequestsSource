package com._4s_.i18n.dao;

import java.util.List;
import java.util.Map;

import com._4s_.common.dao.BaseDAO;
import com._4s_.i18n.model.Key;
import com._4s_.i18n.model.MyLocale;
import com._4s_.i18n.model.MyMessage;

public interface MessageDAO extends BaseDAO {
	public Map getResourceMap(MyLocale myLocale);
	public MyLocale getDefault();
	public Key getKeyByName(String name);
	public MyMessage getMessageByKeyName(String keyName,MyLocale myLocale);
	public List getMessagesByLocale(final MyLocale myLocale);
}
