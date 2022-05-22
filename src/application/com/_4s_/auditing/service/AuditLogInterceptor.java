/*
 * Created on Mar 30, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com._4s_.auditing.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;



//import org.acegisecurity.context.SecurityContext;
//import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.CallbackException;
import org.hibernate.EntityMode;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com._4s_.auditing.dao.AuditLogDAO;
import com._4s_.auditing.model.AuditLogDetail;
import com._4s_.auditing.model.AuditLogRecord;
import com._4s_.auditing.model.Auditable;
import com._4s_.security.model.User;

/**
 * @author mragab
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class AuditLogInterceptor implements Interceptor {
	protected final Log log = LogFactory.getLog(getClass());

	private AuditLogDAO auditLogDAO = null;

	private Session session;

	private Long userId;

	private String userName = null;

	private List auditLogRecords = new ArrayList();
	/**
	 * Gets the current user's id from the Acegi secureContext
	 * 
	 * @return current user's userId
	 */
//	private String getUserName() {
//		SecureContext secureContext = (SecureContext) ContextHolder
//				.getContext();
//
//		// secure context will be null when running unit tests so leave userId
//		// as null
//		if (secureContext != null) {
//			Authentication auth = (Authentication) ((SecureContext) ContextHolder
//					.getContext()).getAuthentication();
//
//			if (auth.getPrincipal() instanceof User) {
//				User userDetails = (User) auth.getPrincipal();
//				userName = userDetails.getUsername();
//			} else {
//				userName = auth.getPrincipal().toString();
//			}
//
//			if (userName == null || userName.equals("")) {
//				return "anonymousUser";
//			} else {
//				return userName;
//			}
//
//		} else {
//			return "anonymousUser";
//		}
//	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Long getUserId() {
		SecurityContext sc = (SecurityContext) (SecurityContextHolder.getContext());
		String username ="";
		if(sc!=null)
		{
			if(sc.getAuthentication()==null)
			{
				username="super";
			}
			else
			{
				username = sc.getAuthentication().getName();
			}
		}
		else
		{
			username = "super";
		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>.............username "+username);
		User user = auditLogDAO.getUserByUsername(username);
		//log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> auditLogDAO.getObjectsByParameter(User.class,username,username)");
		//User user = (User)auditLogDAO.getObjectByParameter(User.class,"username",username);
		//log.debug(">>>>>>>>>>>>>>>>>>>>>> user "+user);
		if (user != null){
			userId=user.getId();
			log.debug(">>>>>>>>>>>>>>>>>>>>>> userId "+userId);
		}
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public AuditLogDAO getAuditLogDAO() {
		return auditLogDAO;
	}

	public void setAuditLogDAO(AuditLogDAO auditLogDAO) {
		log.debug("...............................................");
		this.auditLogDAO = auditLogDAO;
		log.debug("...............................................");
		log.info("... setAuditLogDAO(AuditLogDAO auditLogDAO) ...");
		log.debug("...............................................");

	}

	public Map getCollectionDiff(Collection previousState,
			Collection currentState) {
		Set removed = new HashSet();
		Set added = new HashSet();
		Map collectionDiff = null;

		Object o;

		Iterator previousStateIterator = previousState.iterator();
		while (previousStateIterator.hasNext()) {
			o = previousStateIterator.next();
			if (!currentState.contains(o)) {
				removed.add(o);
			}
		}

		log.debug("------------------------------------------");
		log.debug(">>> previousState : " + previousState);
		log.debug(">>> removed : " + removed);

		Iterator currentStateIterator = currentState.iterator();
		while (currentStateIterator.hasNext()) {
			o = currentStateIterator.next();
			if (!previousState.contains(o)) {
				added.add(o);
			}
		}

		log.debug("------------------------------------------");
		log.debug(">>> currentState : " + currentState);
		log.debug(">>> added : " + added);

		if ((removed.size() > 0) || (added.size() > 0)) {
			collectionDiff = new HashMap();
			collectionDiff.put("removed", removed);
			collectionDiff.put("added", added);
		}
		return collectionDiff;
	}

	/**
	 * @see org.hibernate.Interceptor#onSave(java.lang.Object,
	 *      java.io.Serializable, java.lang.Object[], java.lang.String[],
	 *      org.hibernate.type.Type[])
	 * 
	 * Called before an object is saved. The interceptor may modify the state,
	 * which will be used for the SQL INSERT and propagated to the persistent
	 * object.
	 * 
	 * @return true if the user modified the state in any way.
	 */
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) throws CallbackException {
		log.debug("1.....onSave...............................");

		int propertyNamesLength = propertyNames.length;
		log.debug("2.....onSave...............................");
		if (entity instanceof Auditable) {
			// Just get the class name without the package structure
	
			AuditLogRecord auditLogRecord = new AuditLogRecord("create",
					(Auditable) entity, getUserId(), ((Auditable) entity).getEntityDisplayName());
			log.debug("3.....onSave...............................");
			if (propertyNamesLength > 0) {
				log.debug("4.....onSave...............................");
				for (int i = 0; i < propertyNamesLength; i++) {
					if (state[i] !=  null && !state[i].toString().equals("[]") && !propertyNames[i].equals("position")) {
						auditLogRecord.addAuditLogDetails(new AuditLogDetail(propertyNames[i], null, (state[i] == null) ? "":    (state[i] instanceof Auditable) ? ((Auditable)state[i]).getEntityDisplayName() :state[i].toString() )  );
					}
				}
				log.debug("5.....onSave...............................");
				auditLogRecords.add(auditLogRecord);
				log.debug("6.....onSave...............................");
			}
		}
		return false;
	}

	/**
	 * @see org.hibernate.Interceptor#onFlushDirty(java.lang.Object,
	 *      java.io.Serializable, java.lang.Object[], java.lang.Object[],
	 *      java.lang.String[], org.hibernate.type.Type[])
	 * 
	 * Called when an object is detected to be dirty, during a flush. The
	 * interceptor may modify the detected currentState, which will be
	 * propagated to both the database and the persistent object. Note that not
	 * all flushes end in actual synchronization with the database, in which
	 * case the new currentState will be propagated to the object, but not
	 * necessarily (immediately) to the database.
	 * 
	 * It is strongly recommended that the interceptor not modify the
	 * previousState.
	 * 
	 * @return true if the user modified the currentState in any way.
	 */
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) throws CallbackException {
		log.debug(".......onFlushDirty...........................");
		// Just get the class name without the package structure
		AuditLogRecord auditLogRecord = null;
		Map collectionDiff;
		int propertyNamesLength = propertyNames.length;

		if (entity instanceof Auditable && previousState != null) {
			auditLogRecord = new AuditLogRecord("update", (Auditable) entity,
					getUserId(),((Auditable) entity).getEntityDisplayName());
			if (propertyNamesLength > 0) {
				for (int i = 0; i < propertyNamesLength; i++) {
					log.debug("---------------------previousState[i]"+previousState);
					if (((previousState[i] != null) && (previousState[i] instanceof Collection))
							&& ((currentState[i] != null) && (currentState[i] instanceof Collection))) {
//						PersistentCollection collection = (PersistentCollection) getCollectionDiff(
//								(Collection) previousState[i],
//								(Collection) currentState[i]);
//
//						log
//								.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>....Inside Collection<<<<<<<<<<<<<<<<<<<<<<<<<,,");
//						log.debug(">>>>>>>>>>Previous" + previousState[i]);
//						log.debug(">>>>>>>>>>>>>>>>>>>Current"
//								+ currentState[i]);
//						collectionDiff = getCollectionDiff(
//								(Collection) previousState[i],
//								(Collection) currentState[i]);
//						
//					
//							auditLogRecord.addAuditLogDetails(new AuditLogDetail(propertyNames[i], null, (state[i] == null) ? "":    (state[i] instanceof Auditable) ? ((Auditable)state[i]).getEntityDisplayName() :state[i].toString() )  );
//					
//						if (collectionDiff != null) {
//							auditLogRecord
//									.addAuditLogDetails(new AuditLogDetail(propertyNames[i], (collectionDiff.get("removed") == null) ? "":collectionDiff.get("removed").toString(),
//											//collectionDiff.get("added").toString())  
//											(collectionDiff.get("added") instanceof Auditable) ? ((Auditable)collectionDiff.get("added")).getEntityDisplayName() :collectionDiff.get("added").toString() ));
//						}

					} else if (previousState[i] != currentState[i] && !propertyNames[i].equals("position")) {
						log
								.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>....Inside mesh collection<<<<<<<<<<<<<<<<<<<<<<<<<,,");
						log.debug(">>>>>>>>>>Previous" + previousState[i]);
						log.debug(">>>>>>>>>>>>>>>>>>>Current"
								+ currentState[i]);
						if (previousState[i] == null) {
							auditLogRecord
									.addAuditLogDetails(new AuditLogDetail(
											propertyNames[i], "",
											//currentState[i].toString())
											(currentState[i] instanceof Auditable) ? ((Auditable)currentState[i]).getEntityDisplayName() :currentState[i].toString()) );
						} else if (currentState[i] == null) {
							auditLogRecord
									.addAuditLogDetails(new AuditLogDetail(
											propertyNames[i], "",
										//	previousState[i].toString(), 
											(previousState[i] == null) ? "": (previousState[i] instanceof Auditable) ? ((Auditable)previousState[i]).getEntityDisplayName() :previousState[i].toString()));
							// }
							// else if (
							// previousState[i].equals(currentState[i]) ) {
							// auditLogRecord.addAuditLogDetails(
							// new
							// AuditLogDetail(propertyNames[i],previousState[i].toString(),currentState[i].toString()));
						} else if (!previousState[i].equals(currentState[i])) {
							auditLogRecord
									.addAuditLogDetails(new AuditLogDetail(
											propertyNames[i],
											//previousState[i].toString(),
											//currentState[i].toString()))
											(previousState[i] == null) ? "":(previousState[i] instanceof Auditable) ? ((Auditable)previousState[i]).getEntityDisplayName() :previousState[i].toString(),
											(currentState[i] instanceof Auditable) ? ((Auditable)currentState[i]).getEntityDisplayName() :currentState[i].toString()));
						}
					}

					// Object oldOne = previousState[i];
					// Object newOne = currentState[i];
					//
					// // Check for changes
					// if (oldOne == null && newOne == null) {
					// continue;
					// }
					//					      
					// if (newOne instanceof PersistentCollection) {
					// // Collections must be compared against the snapshot
					// PersistentCollection collection = (PersistentCollection)
					// currentState[i];
					// if (collection.isDirectlyAccessible() == false) {
					// continue;
					// }
					// // retrieve Snapshot
					// oldOne =
					// collection.getCollectionSnapshot().getSnapshot();
					// if (oldOne instanceof Map && newOne instanceof Set) {
					// // a Set is internally stored as Map
					// oldOne = ((Map) oldOne).values();
					// }
					// }
					// if (oldOne != null && oldOne.equals(newOne) == true) {
					// continue;
					// }
					// // Generate a new entry
					//
					// auditLogRecord.addAuditLogDetails(
					// new AuditLogDetail(
					// propertyNames[i],
					// oldOne.toString(),
					// newOne.toString()
					// )
					// );
					// log.debug("Changed " + propertyNames[i] + " from " +
					// oldOne + " to " + newOne);
				}
			}
			auditLogRecords.add(auditLogRecord);
		}
		return false;
	}

	/**
	 * @see org.hibernate.Interceptor#onDelete(java.lang.Object,
	 *      java.io.Serializable, java.lang.Object[], java.lang.String[],
	 *      org.hibernate.type.Type[])
	 * 
	 * Called before an object is deleted. It is not recommended that the
	 * interceptor modify the state.
	 */
	public void onDelete(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) throws CallbackException {
		// Just get the class name without the package structure
		int propertyNamesLength = propertyNames.length;
		if (entity instanceof Auditable) {
			AuditLogRecord auditLogRecord = new AuditLogRecord("delete",
					(Auditable) entity, getUserId(),((Auditable) entity).getEntityDisplayName());
			if (propertyNamesLength > 0) {
				for (int i = 0; i < propertyNamesLength; i++) {
					auditLogRecord.addAuditLogDetails(new AuditLogDetail(
							propertyNames[i], (state[i] == null) ? ""
									: state[i].toString(), null));
				}
				auditLogRecords.add(auditLogRecord);
			}
		}
	}

	public void postFlush(Iterator iterator) throws CallbackException {
		log.debug(".... starting postFlush .......................");
		AuditLogRecord auditLog = null;
		Iterator itr = auditLogRecords.iterator();
		while (itr.hasNext()) {
			auditLog = (AuditLogRecord) itr.next();
			if (auditLog.getEntityId() == null) {
				auditLog.setEntityId((Long) auditLog.getEntity().getId());
			}
			auditLogDAO.saveLogEvent(auditLog);
		}
		auditLogRecords.clear();

		log.debug(".... ending postFlush .......................");
	}

	/**
	 * @see org.hibernate.Interceptor#onLoad(java.lang.Object,
	 *      java.io.Serializable, java.lang.Object[], java.lang.String[],
	 *      org.hibernate.type.Type[])
	 * 
	 * Called just before an object is initialized. The interceptor may change
	 * the state, which will be propagated to the persistent object.
	 * 
	 * Note that when this method is called, entity will be an empty
	 * uninitialized instance of the class.
	 * 
	 * @return true if the user modified the state in any way.
	 */
	public boolean onLoad(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) throws CallbackException {
		// Haven't changed the state
		return false;
	}

	/**
	 * @see org.hibernate.Interceptor#preFlush(java.util.Iterator)
	 * 
	 * Called before a flush
	 */
	public void preFlush(Iterator entities) throws CallbackException {
	}

	/**
	 * @see org.hibernate.Interceptor#isUnsaved(java.lang.Object)
	 * 
	 * Called when a transient entity is passed to saveOrUpdate(). The return
	 * value determines Boolean.TRUE - the entity is passed to save(), resulting
	 * in an INSERT Boolean.FALSE - the entity is passed to update(), resulting
	 * in an UPDATE null - Hibernate uses the unsaved-value mapping to determine
	 * if the object is unsaved
	 */
	public Boolean isUnsaved(Object arg0) {
		return null;
	}

	/**
	 * @see org.hibernate.Interceptor#findDirty(java.lang.Object,
	 *      java.io.Serializable, java.lang.Object[], java.lang.Object[],
	 *      java.lang.String[], org.hibernate.type.Type[])
	 * 
	 * Called from flush(). The return value determines whether the entity is
	 * updated an array of property indices - the entity is dirty and empty
	 * array - the entity is not dirty null - use Hibernate's default
	 * dirty-checking algorithm
	 */
	public int[] findDirty(Object arg0, Serializable arg1, Object[] arg2,
			Object[] arg3, String[] arg4, Type[] arg5) {
		return null;
	}

	/**
	 * @see org.hibernate.Interceptor#instantiate(java.lang.Class,
	 *      java.io.Serializable)
	 * 
	 * Instantiate the entity class. Return null to indicate that Hibernate
	 * should use the default constructor of the class. The identifier property
	 * of the returned instance should be initialized with the given identifier.
	 */
	public Object instantiate(Class arg0, Serializable arg1)
			throws CallbackException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.Interceptor#isTransient(java.lang.Object)
	 */
	public Boolean isTransient(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.Interceptor#instantiate(java.lang.String,
	 *      org.hibernate.EntityMode, java.io.Serializable)
	 */
	public Object instantiate(String arg0, EntityMode arg1, Serializable arg2)
			throws CallbackException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.Interceptor#getEntityName(java.lang.Object)
	 */
	public String getEntityName(Object arg0) throws CallbackException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.Interceptor#getEntity(java.lang.String,
	 *      java.io.Serializable)
	 */
	public Object getEntity(String arg0, Serializable arg1)
			throws CallbackException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.Interceptor#afterTransactionBegin(org.hibernate.Transaction)
	 */
	public void afterTransactionBegin(Transaction arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.Interceptor#beforeTransactionCompletion(org.hibernate.Transaction)
	 */
	public void beforeTransactionCompletion(Transaction arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.Interceptor#afterTransactionCompletion(org.hibernate.Transaction)
	 */
	public void afterTransactionCompletion(Transaction arg0) {
		// TODO Auto-generated method stub

	}

	public void onCollectionRecreate(Object arg0, Serializable arg1)
			throws CallbackException {
		// TODO Auto-generated method stub
		
	}

	public void onCollectionRemove(Object arg0, Serializable arg1)
			throws CallbackException {
		// TODO Auto-generated method stub
		
	}

	public void onCollectionUpdate(Object arg0, Serializable arg1)
			throws CallbackException {
		// TODO Auto-generated method stub
		
	}

	public String onPrepareStatement(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
