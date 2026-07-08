/*
 * Created on Mar 30, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com._4s_.auditing.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com._4s_.auditing.model.AuditLogRecord;
import com._4s_.common.dao.BaseDAOHibernate;
import com._4s_.security.model.User;

/**
 * @author mragab
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@Repository(value="auditDAO")
public class AuditLogDAOHibernate extends BaseDAOHibernate implements
AuditLogDAO {

	// public void saveLogEvent(String message, Auditable entity, Long userId){
	//		
	// // Talfeeaa
	// if( userId == null ) userId = new Long(5);
	//		
	// AuditLogRecord record = new AuditLogRecord(message, entity.getId(),
	// entity.getClass(), userId);
	//		
	// log.debug("save audit record");
	// getHibernateTemplate().saveOrUpdate(record);
	// log.debug("After saveOrUpdate audit record ...");
	// getHibernateTemplate().flush();
	//
	// }

	public void saveLogEvent(AuditLogRecord auditLogRecord) {

		log.debug("save audit auditLogRecord object");
		getCurrentSession().saveOrUpdate(auditLogRecord);

		log.debug("After saveOrUpdate auditLogRecord object ...");
		getCurrentSession().flush();

	}

	public void saveLogEvents(Set auditLogRecords) {
		Iterator it = auditLogRecords.iterator();
		while (it.hasNext()) {
			saveLogEvent((AuditLogRecord) it.next());
		}
	}

	public List search(final Long user, final String action,
			final Date startDate, final Date endDate, final String entityClass,final String displayName) {

//		log
//		.info(">>>>>>>>>>>>>>>>>>>>. entered doInHibernate()");
//		Criteria criteria = getCurrentSession()
//				.createCriteria(AuditLogRecord.class);
//		if (user != null && !user.equals("")) {
//			log.debug(">>>>>>>>>>>>>>>>1 not ");
//			criteria.add(Expression.eq("userId", user));
//		}
//		if (action != null && !action.equals("")) {
//			log.debug(">>>>>>>>>>>>>>>>2 not ");
//			criteria.add(Expression.eq("message", action));
//		}
//		if (startDate != null && !startDate.equals("")) {
//			log.debug(">>>>>>>>>>>>>>>>3 not ");
//			criteria.add(Expression.ge("created", startDate));
//		}
//		if (endDate != null && !endDate.equals("")) {
//			log.debug(">>>>>>>>>>>>>>>>4 not ");
//			criteria.add(Expression.le("created", endDate));
//		}
//		if (entityClass != null && !entityClass.equals("")) {
//			log.debug(">>>>>>>>>>>>>>>>5 not ");
//			try {
//				criteria.add(Expression.eq("entityClass", Class
//						.forName(entityClass)));
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			if (displayName != null && !displayName.equals("") && !displayName.equals("0")) {
//				log.debug(">>>>>>>>>>>>>>>>4 not ");
//				criteria.add(Expression.eq("entityDisplayName", displayName));
//			}
//		}
//		return criteria.list();
		
		log.info(">>>>>>>>>>>>>>>>>>>>. entered doInHibernate()");

		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<AuditLogRecord> query =
		        builder.createQuery(AuditLogRecord.class);

		Root<AuditLogRecord> root =
		        query.from(AuditLogRecord.class);

		List<Predicate> predicates = new ArrayList<>();

		/*
		 * userId = user
		 */
		if (user != null) {
		    log.debug(">>>>>>>>>>>>>>>>1 not ");
		    predicates.add(
		            builder.equal(root.get("userId"), user)
		    );
		}

		/*
		 * message = action
		 */
		if (action != null && !action.isEmpty()) {
		    log.debug(">>>>>>>>>>>>>>>>2 not ");
		    predicates.add(
		            builder.equal(root.get("message"), action)
		    );
		}

		/*
		 * created >= startDate
		 */
		if (startDate != null) {
		    log.debug(">>>>>>>>>>>>>>>>3 not ");
		    predicates.add(
		            builder.greaterThanOrEqualTo(root.get("created"), startDate)
		    );
		}

		/*
		 * created <= endDate
		 */
		if (endDate != null) {
		    log.debug(">>>>>>>>>>>>>>>>4 not ");
		    predicates.add(
		            builder.lessThanOrEqualTo(root.get("created"), endDate)
		    );
		}

		/*
		 * entityClass filter
		 */
		if (entityClass != null && !entityClass.isEmpty()) {
		    log.debug(">>>>>>>>>>>>>>>>5 not ");

		    try {
		        Class<?> clazz = Class.forName(entityClass);

		        predicates.add(
		                builder.equal(root.get("entityClass"), clazz)
		        );

		    } catch (Exception ignored) {
		        // invalid class name ignored
		    }

		    /*
		     * displayName filter (only if entityClass is valid)
		     */
		    if (displayName != null &&
		            !displayName.isEmpty() &&
		            !displayName.equals("0")) {

		        predicates.add(
		                builder.equal(root.get("entityDisplayName"), displayName)
		        );
		    }
		}

		query.select(root)
		     .where(predicates.toArray(new Predicate[0]));

		return session.createQuery(query).getResultList();
	}

	public User getUserByUsername(final String userName) {

//		if (log.isDebugEnabled()) {
//			log.debug("Getting objects of class :" + User.class
//					+ ", by criteria");
//		}
//		Criteria criteria = getCurrentSession().createCriteria(User.class);
//		criteria.add(Restrictions.eq("username", userName));
//		criteria.uniqueResult();
//		List list =  criteria.list();
//
//		if ((list.size() == 0) && (log.isDebugEnabled())) {
//			log.debug("No objects found");
//		} else if (log.isDebugEnabled()) {
//			log.debug("Got " + list.size() + " objects");
//		}
//		return (User) list.iterator().next();
		
		if (log.isDebugEnabled()) {
		    log.debug("Getting objects of class : User by criteria");
		}

		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<User> query =
		        builder.createQuery(User.class);

		Root<User> root =
		        query.from(User.class);

		query.select(root)
		     .where(
		             builder.equal(root.get("username"), userName)
		     );

		TypedQuery<User> typedQuery =
		        session.createQuery(query);

		List<User> list = typedQuery.getResultList();

		if (log.isDebugEnabled()) {
		    if (list.isEmpty()) {
		        log.debug("No objects found");
		    } else {
		        log.debug("Got " + list.size() + " objects");
		    }
		}

		return list.isEmpty() ? null : list.get(0);
	}


}
