/*
 * Created on Mar 30, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com._4s_.auditing.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
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
@Repository
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

		log
		.info(">>>>>>>>>>>>>>>>>>>>. entered doInHibernate()");
		Criteria criteria = getCurrentSession()
				.createCriteria(AuditLogRecord.class);
		if (user != null && !user.equals("")) {
			log.debug(">>>>>>>>>>>>>>>>1 not ");
			criteria.add(Expression.eq("userId", user));
		}
		if (action != null && !action.equals("")) {
			log.debug(">>>>>>>>>>>>>>>>2 not ");
			criteria.add(Expression.eq("message", action));
		}
		if (startDate != null && !startDate.equals("")) {
			log.debug(">>>>>>>>>>>>>>>>3 not ");
			criteria.add(Expression.ge("created", startDate));
		}
		if (endDate != null && !endDate.equals("")) {
			log.debug(">>>>>>>>>>>>>>>>4 not ");
			criteria.add(Expression.le("created", endDate));
		}
		if (entityClass != null && !entityClass.equals("")) {
			log.debug(">>>>>>>>>>>>>>>>5 not ");
			try {
				criteria.add(Expression.eq("entityClass", Class
						.forName(entityClass)));
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (displayName != null && !displayName.equals("") && !displayName.equals("0")) {
				log.debug(">>>>>>>>>>>>>>>>4 not ");
				criteria.add(Expression.eq("entityDisplayName", displayName));
			}
		}
		return criteria.list();
	}

	public User getUserByUsername(final String userName) {

		if (log.isDebugEnabled()) {
			log.debug("Getting objects of class :" + User.class
					+ ", by criteria");
		}
		Criteria criteria = getCurrentSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", userName));
		criteria.uniqueResult();
		List list =  criteria.list();

		if ((list.size() == 0) && (log.isDebugEnabled())) {
			log.debug("No objects found");
		} else if (log.isDebugEnabled()) {
			log.debug("Got " + list.size() + " objects");
		}
		return (User) list.iterator().next();
	}


}
