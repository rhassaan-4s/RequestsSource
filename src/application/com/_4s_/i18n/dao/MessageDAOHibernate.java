package com._4s_.i18n.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com._4s_.common.dao.BaseDAOHibernate;
import com._4s_.i18n.model.Key;
import com._4s_.i18n.model.MyLocale;
import com._4s_.i18n.model.MyMessage;

/**
 * @author mragab
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@Transactional
@Repository
public class MessageDAOHibernate extends BaseDAOHibernate implements MessageDAO 
{

	private MyLocale getMyLocale(MyLocale myLocale) 
	{

		MyLocale myLocaleFromDB = null;

		List myLocales = null;
		List cl = new ArrayList();
		cl.add(Expression.eq("language", myLocale.getLanguage()));
//		cl.add(Expression.eq("country", myLocale.getCountry()));
//		cl.add(Expression.eq("variant", myLocale.getVariant()));

		//getObjectsByCriteria returns all the objects of a class in a list
//		myLocales = getObjectsByCriteria(MyLocale.class, cl);

		//if it is only one, return it
		if ((myLocales != null) && (myLocales.size() == 1)) {
			myLocaleFromDB = (MyLocale) myLocales.iterator().next();
		}
		log.info(">>>>>>>>>>>>>>>.myLocale.getVariant() "
				+ myLocale.getVariant());
		log.info(">>>>>>>>>>>>>>>>>>>>>>myLocaleFromDB " + myLocaleFromDB);
		//else return null
		return myLocaleFromDB;
	}

	// get Resource map( key & message ) using locale 
	public Map getResourceMap(final MyLocale myLocale) 
	{

		log.info("Starting getResourceMap... locale:" + myLocale.getLanguage()
				+ "," + myLocale.getCountry() + "," + myLocale.getVariant());
		Map resourceMap = new HashMap();
		log.info(">>>>>>>>>>>> new resourceMAp created ");
		List list = (List)  getCurrentSession().createCriteria(MyMessage.class).add(Expression.eq("myLocale", getMyLocale(myLocale))).list();
		log.info(">>>>>>>>>>>>>>>>>>> exited doInHibernate()");
		MyMessage myMessage;
		Key myKey;
		log.info("Preparing resource map for locale :["
				+ myLocale.getLanguage() + "," + myLocale.getCountry() + ","
				+ myLocale.getVariant() + "]");
		log
				.info("-------------------------------------------------------------------------");

		Iterator resourceIterator = list.iterator();
		while (resourceIterator.hasNext()) {
			myMessage = (MyMessage) resourceIterator.next();
			myKey = myMessage.getKey();
			log.debug("Adding to map [" + myKey.getName() + ","
					+ myMessage.getMessage() + "]");
			resourceMap.put(myKey.getName(), myMessage.getMessage());
		}

		log.info("Ending getResourceMap... resourceMap:"
				+ ((resourceMap == null) ? "Not found" : "Found"));

		return resourceMap;
	}

	/*
	 * public Map getResourceMap(final MyLocale myLocale) {
	 * 
	 * log.info("Starting getResourceMap... locale:" + myLocale.getLanguage() +
	 * "," + myLocale.getCountry() + "," + myLocale.getVariant()); Map
	 * resourceMap = new HashMap(); log.info(">>>>>>>>>>>> new resourceMAp
	 * created "); Iterator resourceIterator = (Iterator)
	 * getHibernateTemplate().execute( new HibernateCallback() { public Object
	 * doInHibernate(Session session) throws HibernateException, SQLException {
	 * log.info(">>>>>>>>>>>>>>>>>>>>. entered doInHibernate()"); return
	 * session.createCriteria(MyMessage.class).add( Expression .eq("myLocale",
	 * getMyLocale(myLocale))) .list().iterator(); } });
	 * log.info(">>>>>>>>>>>>>>>>>>> exited doInHibernate()"); MyMessage
	 * myMessage; Key myKey; log.debug("Preparing resource map for locale :[" +
	 * myLocale.getLanguage() + "," + myLocale.getCountry() + "," +
	 * myLocale.getVariant() + "]"); while (resourceIterator.hasNext()) {
	 * myMessage = (MyMessage) resourceIterator.next(); myKey =
	 * myMessage.getKey(); log.debug("Adding to map [" + myKey.getName() + "," +
	 * myMessage.getMessage() + "]"); resourceMap.put(myKey.getName(),
	 * myMessage.getMessage()); }
	 * 
	 * log.info("Ending getResourceMap... resourceMap:" + ((resourceMap == null) ?
	 * "Not found" : "Found"));
	 * 
	 * return resourceMap; }
	 */

	// Returns the default locale
	public MyLocale getDefault() 
	{
		// TODO Auto-generated method stub
//		Criteria criteria = getCurrentSession().createCriteria(MyLocale.class).add(Restrictions.eq("isDefault",	new Boolean(true)));
//		MyLocale myLocale = (MyLocale)criteria.uniqueResult();
		MyLocale myLocale = (MyLocale)getObjectByParameter(MyLocale.class, "isDefault", new Boolean(true));
		System.out.println("locale " + myLocale.getLanguage());
		return myLocale;
	}

	// Returns the msg using key & locale
	public MyMessage getMessageByKeyName(final String keyName,
			final MyLocale myLocale) 
	{
		
			Criteria criteria = getCurrentSession()
					.createCriteria(MyMessage.class);
			criteria.createCriteria("key").add(
					Restrictions.eq("name", keyName));
			criteria.createCriteria("myLocale").add(
					Restrictions.eq("id", myLocale.getId()));

			return (MyMessage)criteria.uniqueResult();
	}

	// Returns key using key name
	public Key getKeyByName(final String keyName)
	{
		Criteria criteria = getCurrentSession().createCriteria(Key.class).add(
				Restrictions.eq("name", keyName));
		return (Key)criteria.uniqueResult();
	}

	// Returns all msgs for a local
	public List getMessagesByLocale(final MyLocale myLocale)
	{
		Criteria criteria = getCurrentSession()
				.createCriteria(MyMessage.class);
		criteria.createCriteria("myLocale").add(
				Restrictions.eq("id", myLocale.getId()));

		return (List)criteria.list();
	}
}
