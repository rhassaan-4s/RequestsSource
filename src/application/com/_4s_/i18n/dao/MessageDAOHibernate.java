package com._4s_.i18n.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
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

	private MyLocale myLocaleFromDB = null;

	private MyLocale getMyLocale(MyLocale myLocale) 
	{
		CriteriaQuery queryCriteria = getBuilder().createQuery(MyLocale.class);
		Root<Object> root = queryCriteria.from(MyLocale.class);
		Predicate restrictions = getBuilder().equal(root.get("language"), myLocale.getLanguage());
		queryCriteria.select(root).where(restrictions).distinct(true);
		TypedQuery<Object> query = getCurrentSession().createQuery(queryCriteria);
		List myLocales =  query.getResultList();


		if (myLocaleFromDB== null && (myLocales != null) && (myLocales.size() == 1)) {
			myLocaleFromDB = (MyLocale) myLocales.iterator().next();
		}
		log.info(">>>>>>>>>>>>>>>.myLocale.getVariant() "
				+ myLocale.getVariant());
		log.info(">>>>>>>>>>>>>>>>>>>>>>myLocaleFromDB " + myLocaleFromDB);
		//else return null
		return myLocaleFromDB;

		//		
		//		List myLocales = null;
		//		List cl = new ArrayList();
		//		cl.add(Expression.eq("language", myLocale.getLanguage()));
		////		cl.add(Expression.eq("country", myLocale.getCountry()));
		////		cl.add(Expression.eq("variant", myLocale.getVariant()));
		//
		//		//getObjectsByCriteria returns all the objects of a class in a list
		////		myLocales = getObjectsByCriteria(MyLocale.class, cl);
		//
		//		//if it is only one, return it
		//		if ((myLocales != null) && (myLocales.size() == 1)) {
		//			myLocaleFromDB = (MyLocale) myLocales.iterator().next();
		//		}
		//		log.info(">>>>>>>>>>>>>>>.myLocale.getVariant() "
		//				+ myLocale.getVariant());
		//		log.info(">>>>>>>>>>>>>>>>>>>>>>myLocaleFromDB " + myLocaleFromDB);
		//		//else return null
		//		return myLocaleFromDB;
	}

	// get Resource map( key & message ) using locale 
	public Map getResourceMap(final MyLocale myLocale) 
	{

		log.info("Starting getResourceMap DAO... locale:" + myLocale.getLanguage()
		+ "," + myLocale.getCountry() + "," + myLocale.getVariant());

		Map resourceMap = new HashMap();
		log.info("#### new resourceMap created");
		CriteriaQuery<Tuple> queryCriteria = getBuilder().createQuery(Tuple.class);
		Root<MyMessage> msg = queryCriteria.from(MyMessage.class);
		Path<String> message = msg.get("message");
		Path<String> locale = msg.get("myLocale");
		Join<MyMessage, Key> keyMsg = msg.join("key");
		Path<String>key = keyMsg.get("name");
		Predicate restrictions = getBuilder().equal(msg.get("myLocale"), getMyLocale(myLocale));
//		queryCriteria.select(msg.get("message")).where(restrictions).distinct(true);
//		TypedQuery<MyMessage> query = getCurrentSession().createQuery(queryCriteria);
		queryCriteria.multiselect(message.alias("message"),key.alias("name")).where(restrictions).distinct(true);
		log.debug("query created");
//		List list =  query.getResultList();
		List<Tuple> tuples = getCurrentSession().createQuery(queryCriteria).getResultList();
		log.debug("msgs for arabic locale list size " + tuples.size());
		if ((tuples.size() == 0)&&(log.isDebugEnabled())) {
			log.debug("******************No objects found");
		}
		else if (log.isDebugEnabled()) {
			log.debug("*****************Got "+tuples.size()+" objects");
		}
		String myMessage=null;
		String myKey=null;
		log.info("**************************Preparing resource map for locale :["
				+ myLocale.getLanguage() + "," + myLocale.getCountry() + ","
				+ myLocale.getVariant() + "]");
		log.info("------------------------------------------------------------------");

		Iterator<Tuple> resourceIterator = tuples.iterator();
		log.debug("tuples iterator " + resourceIterator);
		while (resourceIterator.hasNext()) {
			Tuple tuple = resourceIterator.next();
//			log.debug("tuple " + tuple);
//			myMessage = (MyMessage) resourceIterator.next();
//			myKey = myMessage.getKey();
			try {
				myMessage = (String)tuple.get("message");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.debug(e.getMessage());
				e.printStackTrace();
			}
			try {
				myKey = ((String)tuple.get("name"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.debug(e.getMessage());
				e.printStackTrace();
			}
//			log.debug("Adding to map [" + myKey + ","
//					+ myMessage+ "]");
			resourceMap.put(myKey, myMessage);
		}

		log.info("Ending getResourceMap... resourceMap:"
				+ ((resourceMap == null) ? "Not found" : "Found"));

		return resourceMap;
		//		Map resourceMap = new HashMap();
		//		log.info(">>>>>>>>>>>> new resourceMAp created ");
		//		List list = (List)  getCurrentSession().createCriteria(MyMessage.class).add(Expression.eq("myLocale", getMyLocale(myLocale))).list();
		//		log.info(">>>>>>>>>>>>>>>>>>> exited doInHibernate()");
		//		MyMessage myMessage;
		//		Key myKey;
		//		log.info("Preparing resource map for locale :["
		//				+ myLocale.getLanguage() + "," + myLocale.getCountry() + ","
		//				+ myLocale.getVariant() + "]");
		//		log
		//				.info("-------------------------------------------------------------------------");
		//
		//		Iterator resourceIterator = list.iterator();
		//		while (resourceIterator.hasNext()) {
		//			myMessage = (MyMessage) resourceIterator.next();
		//			myKey = myMessage.getKey();
		//			log.debug("Adding to map [" + myKey.getName() + ","
		//					+ myMessage.getMessage() + "]");
		//			resourceMap.put(myKey.getName(), myMessage.getMessage());
		//		}
		//
		//		log.info("Ending getResourceMap... resourceMap:"
		//				+ ((resourceMap == null) ? "Not found" : "Found"));
		//
		//		return resourceMap;
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
