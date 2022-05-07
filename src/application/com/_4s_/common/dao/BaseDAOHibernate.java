package com._4s_.common.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com._4s_.common.util.Page;
//import org.springframework.orm.ObjectRetrievalFailureException;
//import org.springframework.orm.hibernate5.HibernateCallback;
//import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common methods that they might all use. Can be used for standard CRUD
 * operations.</p>
 *
 * <p><a href="BaseDAOHibernate.java.html"><i>View Source</i></a></p>
 *
 * @spring.property name="sessionFactory" ref="sessionFactory"
**/
@Transactional
@Repository
public class BaseDAOHibernate  implements BaseDAO {//extends HibernateDaoSupport
//    protected final Log log = LogFactory.getLog(getClass());

	 public static final Logger log = LoggerFactory.getLogger(BaseDAOHibernate.class);
	 
	@Autowired
	private SessionFactory sessionFactory;
    
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private CriteriaBuilder builder;

	public CriteriaBuilder getBuilder() {
		return builder;
	}

	@Transactional
	public Session getCurrentSession(){
		Session session = null;
    	log.debug("$$$$$$$$$$$$$$$$$$getting current session");
    	log.debug("session factory " + sessionFactory);
    	try {
    	    session = sessionFactory.getCurrentSession();
    	} catch (HibernateException e) {
    	    session = sessionFactory.openSession();
    	}
	      return session;
	}
    
	@Transactional
    public void init() {
    	log.debug("###########################initiallizing#####################");
    	builder = getCurrentSession().getCriteriaBuilder();
    	log.debug("builder " + builder);
    }
    
//	    
//    public BaseDAOHibernate() {
//    	log.debug("sess. fact. " + sessionFactory);
//    	log.debug("rana");
//    	builder = getCurrentSession().getCriteriaBuilder();
//	}

//	protected final Session getCurrentSession(){
//		System.out.println("#####session factory " + sessionFactory);
////		if (sessionFactory == null) {
////			try {
////				// A SessionFactory is set up once for an application!
////				String hibernatePropsFilePath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5_Tomcat8_branch\\webapps\\Requests\\WEB-INF\\hibernate.cfg.xml";
////				 File hibernatePropsFile = new File(hibernatePropsFilePath);
////				final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
////				.configure(hibernatePropsFile) // configures settings from hibernate.cfg.xml
////				.build();
////				sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
////				System.out.println("***session factory***"+sessionFactory);
////			} catch (Exception e) {
////				System.out.println(e.getMessage());
////				e.printStackTrace();
////			}
////		}
//		return sessionFactory.getCurrentSession();
//    }
//    
    Integer maxResults = null;
    MatchMode matchMode = MatchMode.START;
    
    /* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#saveObject(java.lang.Object)
	 */
    public void saveObject(Object o) {
        if (log.isDebugEnabled()) {
            log.debug("Saving or Updating Object: " + o.toString());
        }
//        checkFlushMode();
       
        Transaction tx = null;
        Session sess = getCurrentSession();
        log.debug("flush mode " + sess.getFlushMode());
        try {
			tx= sess.beginTransaction();
			sess.saveOrUpdate(o);
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (tx!=null) {
				tx.rollback();
			}
		 }
		 finally {
			 sess.close();
		 }
    }
    
 /* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#updateObject(java.lang.Object)
	 */
    public void updateObject(Object o) {
        if (log.isDebugEnabled()) {
            log.debug("Saving or Updating Object: " + o.toString());
        }

        checkFlushMode();
        getCurrentSession().update(o);
    }
    
/* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#saveObject(java.lang.Object)
	 */
    public void saveObjectWithoutUpdate(Object o) {
        if (log.isDebugEnabled()) {
            log.debug("Saving or Updating Object: " + o.toString());
        }

        checkFlushMode();
        getCurrentSession().save(o);
    }

    /* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#getObject(java.lang.Class, java.io.Serializable)
	 */
    public Object getObject(Class clazz,Serializable id) {
        if (log.isDebugEnabled()) {
            log.debug("Getting object of class :"+ clazz +", with id :"+ id);
        }
        try {
        	Object object = getCurrentSession().get(clazz, id);
        	log.debug("Getting object of class :"+ clazz +" 2");
        	if (object == null) {
        		//            throw new ObjectRetrievalFailureException(clazz, id);
        		try {
        			throw new Exception("object of class " + clazz.getCanonicalName()+ " with id " + id + " is not found");
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        	}

        	if (log.isDebugEnabled()) {
        		log.debug("Got object :"+ object);
        	}
        	return object;
        } catch (Exception e) {
//        	e.printStackTrace();
//        	log.debug(e.getClass() + " - " +e.getMessage());
        	StackTraceElement[] stackTrace = e.getStackTrace();
        	System.out.println(e.getClass());
        	System.out.println(e.getMessage());
        	for (int i= 0 ; i < stackTrace.length ; i++) {
        		System.out.println(stackTrace[i]);
        	}
        	try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	return null;
        }
    }

    /* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#getObjectByParameter(java.lang.Class, java.lang.String, java.lang.Object)
	 */
    public Object getObjectByParameter(Class clazz,final String parameter, final Object value) {
        if (log.isDebugEnabled()) {
            log.debug("Getting object of class :"+ clazz +", with "+parameter+" :"+ value);
        }
     // Create CriteriaQuery
    	CriteriaQuery queryCriteria = builder.createQuery(clazz);
    	Root<Object> root = queryCriteria.from(clazz);
    	Predicate restrictions = builder.equal(root.get(parameter), value);
    	queryCriteria.select(root).where(restrictions).distinct(true);
    	
		TypedQuery<Object> query = getCurrentSession().createQuery(queryCriteria);
    	Object object = query.getSingleResult();
        if ((object == null)&&(log.isDebugEnabled())) {
            log.debug("No object found");
        }
        else if (log.isDebugEnabled()) {
            log.debug("Got object :"+ object);
        }
        return object;
    }
    
    
    
    /* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#getObjectsByParameter(java.lang.Class, java.lang.String, java.lang.Object)
	 */
    public List getObjectsByParameter(Class clazz,final String parameter, final Object value) {
        if (log.isDebugEnabled()) {
            log.debug("Getting objects of class :"+ clazz +", with "+parameter+" :"+ value);
        }
        CriteriaQuery queryCriteria = builder.createQuery(clazz);
    	Root<Object> root = queryCriteria.from(clazz);
    	Predicate restrictions = builder.equal(root.get(parameter), value);
    	queryCriteria.select(root).where(restrictions).distinct(true);
    	TypedQuery<Object> query = getCurrentSession().createQuery(queryCriteria);
        List list =  query.getResultList();
		
        if ((list.size() == 0)&&(log.isDebugEnabled())) {
            log.debug("No objects found");
        }
        else if (log.isDebugEnabled()) {
            log.debug("Got "+list.size()+" objects");
        }
        return list;
    }
    
    public List getObjectsByNullParameter(Class clazz,final String parameter) {
        if (log.isDebugEnabled()) {
            log.debug("Getting objects of class :"+ clazz +", with null "+parameter);
        }
        
        CriteriaQuery queryCriteria = builder.createQuery(clazz);
    	Root<Object> root = queryCriteria.from(clazz);
    	Predicate restrictions = builder.isNull(root.get(parameter));
    	queryCriteria.select(root).where(restrictions).distinct(true);
    	TypedQuery<Object> query = getCurrentSession().createQuery(queryCriteria);
        List list =  query.getResultList();
        
//		List list = (List)queryCriteria
//				.add(Expression.isNull(parameter))
//				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
//				.list();
		
        if ((list.size() == 0)&&(log.isDebugEnabled())) {
            log.debug("No objects found");
        }
        else if (log.isDebugEnabled()) {
            log.debug("Got "+list.size()+" objects");
        }
        return list;
    }

    /* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#getObjectsLikeParameter(java.lang.Class, java.lang.String, java.lang.String)
	 */
    public List getObjectsLikeParameter(Class clazz, final String parameter, final String value) {
        if (log.isDebugEnabled()) {
            log.debug("Getting object of class :"+ clazz +", with "+parameter+" like :"+ value);
        }
		
        CriteriaQuery queryCriteria = builder.createQuery(clazz);
    	Root<Object> root = queryCriteria.from(clazz);
    	Predicate restrictions = null;
    	if (matchMode.equals(MatchMode.START)) {
    		restrictions = builder.like(root.<String>get(parameter), value+"%");
    	} else if (matchMode.equals(MatchMode.END)) {
    		restrictions = builder.like(root.<String>get(parameter), "%"+value);
    	}
    	queryCriteria.select(root);
    	queryCriteria.where(restrictions);
    	queryCriteria.orderBy(builder.asc(root.get(parameter)));
    	queryCriteria.distinct(true);
    	
    	TypedQuery<Object> query = null;
    	
    	if ((maxResults != null) && (maxResults.intValue() > 0)) {
    		query = getCurrentSession().createQuery(queryCriteria).setMaxResults(maxResults.intValue());
    	} else {
    		query = getCurrentSession().createQuery(queryCriteria);
    	}
        List list =  query.getResultList();
        
        if ((list.size() == 0)&&(log.isDebugEnabled())) {
            log.debug("No objects found");
        }
        else if (log.isDebugEnabled()) {
            log.debug("Got "+list.size()+" objects");
        }
        return list;
    }
    
    /* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#getObjectsByCriteria(java.lang.Class, java.util.List)
	 */
    
    //this method need hibernate upgrade methods
//    public List getObjectsByCriteria(final List criterionList) {
//        if (log.isDebugEnabled()) {
//            log.debug("Getting objects of class :"+ clazz +", by criteria");
//        }
//		
//		        Criteria criteria = getCurrentSession().createCriteria(clazz)
//		        Iterator itr = criterionList.iterator();
//		        while (itr.hasNext()) {
//		            criteria.add((Criterion)itr.next());
//		        }
//		        List list = (List)criteria.list();
//		
//        if ((list.size() == 0)&&(log.isDebugEnabled())) {
//            log.debug("No objects found");
//        }
//        else if (log.isDebugEnabled()) {
//            log.debug("Got "+list.size()+" objects");
//        }
//        return list;
//    }
    
    /* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#getObjects(java.lang.Class)
	 */
    public List getObjects(Class clazz) {
 		
    	 CriteriaQuery queryCriteria = builder.createQuery(clazz);
     	Root<Object> root = queryCriteria.from(clazz);
     	queryCriteria.select(root).distinct(true);
     	TypedQuery<Object> query = getCurrentSession().createQuery(queryCriteria);
         List list =  query.getResultList();
 		
         if ((list.size() == 0)&&(log.isDebugEnabled())) {
             log.debug("No objects found");
         }
         else if (log.isDebugEnabled()) {
             log.debug("Got "+list.size()+" objects");
         }
         return list;
    }  
    
    public List getObjectsOrderedByField(Class clazz,final String field) {
    	
    	CriteriaQuery queryCriteria = builder.createQuery(clazz);
      	Root<Object> root = queryCriteria.from(clazz);
      	queryCriteria.select(root);
      	queryCriteria.orderBy(builder.asc(root.get(field)));
      	queryCriteria.distinct(true);
      	TypedQuery<Object> query = getCurrentSession().createQuery(queryCriteria);
        List list =  query.getResultList();
        return list;
    }
    /* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#removeObject(java.lang.Object)
	 */
    public void removeObject(Object obj) {
        if (log.isDebugEnabled()) {
            log.debug("Removing object :"+ obj.toString());
        }

        checkFlushMode();

        getCurrentSession().delete(obj);
        if (log.isDebugEnabled()) {
            log.debug("Object removed from database");
        }
    }

    /* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#removeObject(java.lang.Class, java.io.Serializable)
	 */
    public void removeObject(Class clazz, Serializable id) {
        if (log.isDebugEnabled()) {
            log.debug("Removing object of class :"+ clazz +", with id :"+ id);
        }
    	Object obj = getObject(clazz,id);
        if (log.isDebugEnabled()) {
            log.debug("Object to be removed :"+ obj.toString());
        }
        
        checkFlushMode();

        getCurrentSession().delete(obj);
        if (log.isDebugEnabled()) {
            log.debug("Object removed from database");
        }
    }

    /* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#removeObjectByParameter(java.lang.Class, java.lang.String, java.lang.Object)
	 */
    public void removeObjectByParameter(Class clazz, final String parameter, final Object value) {
        if (log.isDebugEnabled()) {
            log.debug("Removing object of class :"+ clazz +", with "+parameter+" :"+ value);
        }
        Object obj = getObjectByParameter(clazz,parameter,value);
        if (log.isDebugEnabled()) {
            log.debug("Object to be removed :"+ obj.toString());
        }
        
        checkFlushMode();

        getCurrentSession().delete(obj);
        if (log.isDebugEnabled()) {
            log.debug("Object removed from database");
        }
    }

	/* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#getMaxResults()
	 */
	public Integer getMaxResults() {
		return maxResults;
	}
	/* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#setMaxResults(java.lang.Integer)
	 */
	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}
	/* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#setMaxResults(int)
	 */
	public void setMaxResults(int maxResults) {
		this.maxResults = new Integer(maxResults);
	}
	/* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#getMatchMode()
	 */
	public MatchMode getMatchMode() {
		return matchMode;
	}
	/* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#setMatchMode(org.hibernate.criterion.MatchMode)
	 */
	public void setMatchMode(MatchMode matchMode) {
		this.matchMode = matchMode;
	}
	
	public void flush() {
		getCurrentSession().flush();
	}
	
	public void setFlushModeNever() {
//		getCurrentSession().setFlushMode(FlushMode.NEVER);
	}
	
	public void setFlushModeAuto() {
		getCurrentSession().setFlushMode(FlushMode.AUTO);
	}

	public void setFlushModeAlways() {
		getCurrentSession().setFlushMode(FlushMode.ALWAYS);
	}
	
	public void setFlushModeCommit() {
		getCurrentSession().setFlushMode(FlushMode.COMMIT);
	}

	public FlushMode getFlushMode() {
		Object object = getCurrentSession().getFlushMode();
		
		FlushMode flushMode = (FlushMode) object;
		return flushMode;
	}
	
	public void checkFlushMode() {
//        if (getFlushMode().==FlushMode.MANUAL) {
        	setFlushModeCommit();
//        }
	}

	public void detachObject(Object o) {
		getCurrentSession().evict(o);
	}

	public void initializeCollection(Object o) {
		Hibernate.initialize(o);
	}
	
	public void lock(final Object o){
		getCurrentSession().lock(o,LockMode.NONE);
	}

	public Object getDefaultObject(Class clazz) {
		
		CriteriaQuery queryCriteria = builder.createQuery(clazz);
    	Root<Object> root = queryCriteria.from(clazz);
    	Predicate restrictions = builder.equal(root.get("isDefault"), new Boolean(true));
    	queryCriteria.select(root).where(restrictions).distinct(true);
    	TypedQuery<Object> query = getCurrentSession().createQuery(queryCriteria);
    	Object object = query.getSingleResult();
        if ((object == null)&&(log.isDebugEnabled())) {
            log.debug("No object found");
        }
        else if (log.isDebugEnabled()) {
            log.debug("Got object :"+ object);
        }
        return object;
        
	}

	public Object getDefaultObjectByParameter(Class clazz,final String parameter,final Serializable id) {
		CriteriaQuery queryCriteria = builder.createQuery(clazz);
    	Root<Object> root = queryCriteria.from(clazz);
    	List<Predicate> predicates = new ArrayList<Predicate>();
    	Predicate restriction1 = builder.equal(root.get("isDefault"), new Boolean(true));
    	Predicate restriction2 = builder.equal(root.get(parameter).<Long>get("id"),id);
    	predicates.add(restriction1);
    	predicates.add(restriction2);
    	
    	queryCriteria.select(root).where(predicates.toArray(new Predicate[]{})).distinct(true);
    	TypedQuery<Object> query = getCurrentSession().createQuery(queryCriteria);
    	Object object = query.getSingleResult();
        if ((object == null)&&(log.isDebugEnabled())) {
            log.debug("No object found");
        }
        else if (log.isDebugEnabled()) {
            log.debug("Got object :"+ object);
        }
        return object;
//        
//			Criteria criteria = getCurrentSession().createCriteria(clazz);
//			criteria.createCriteria(parameter).add(Expression.eq("id",id));
//			criteria.add(Expression.eq("isDefault",new Boolean(true)));
//			return criteria.uniqueResult();
	}
	
	public Object getDefaultObjectByParameter(Class clazz, final String parameter1,final String parameter2,final Serializable id) {
		CriteriaQuery queryCriteria = builder.createQuery(clazz);
    	Root<Object> root = queryCriteria.from(clazz);
    	List<Predicate> predicates = new ArrayList<Predicate>();
    	Predicate restriction1 = builder.equal(root.get("isDefault"), new Boolean(true));
    	Predicate restriction2 = builder.equal(root.get(parameter1).get(parameter2).<Long>get("id"),id);
    	predicates.add(restriction1);
    	predicates.add(restriction2);
    	
    	queryCriteria.select(root).where(predicates.toArray(new Predicate[]{})).distinct(true);
    	TypedQuery<Object> query = getCurrentSession().createQuery(queryCriteria);
    	Object object = query.getSingleResult();
        if ((object == null)&&(log.isDebugEnabled())) {
            log.debug("No object found");
        }
        else if (log.isDebugEnabled()) {
            log.debug("Got object :"+ object);
        }
        return object;
		
		
//				Criteria criteria = getCurrentSession().createCriteria(clazz);
//				criteria.createCriteria(parameter1).createCriteria(parameter2).add(Expression.eq("id",id));
//				criteria.add(Expression.eq("isDefault",new Boolean(true)));
//				return criteria.uniqueResult();
}

	public Map getObjectsByParameterForPage(Class clazz, 
			final String parameter, final Object value, final int pageNumber,
			final int pageSize) {
		Page page = new Page();
		Map map = new HashMap();
		CriteriaQuery queryCriteria = builder.createQuery(clazz);
		Root<Object> root = queryCriteria.from(clazz);
		Predicate restrictions = null;
		restrictions = builder.equal(root.<Object>get(parameter), value);
		queryCriteria.select(root);
		queryCriteria.where(restrictions);
		queryCriteria.distinct(true);
		queryCriteria.orderBy(builder.asc(root.get("id")));

		TypedQuery<Object> query = null;
		query = getCurrentSession().createQuery(queryCriteria).setMaxResults(pageSize).setFirstResult(pageNumber * pageSize);

		List list =  query.getResultList();
		return page.getPage(map, pageNumber, pageSize);
	        
	        
//		Page page = new Page();
//				Criteria cri = getCurrentSession().createCriteria(clazz);
//				cri.add(Expression.eq(parameter, value)).setResultTransformer(
//						Criteria.DISTINCT_ROOT_ENTITY).setProjection(
//						Projections.projectionList()
//								.add(Projections.rowCount()));
//				Map map = new HashMap();
//				map.put("listSize", cri.list().iterator().next());
//				Criteria cri2 = getCurrentSession().createCriteria(clazz);
//				cri2.add(Expression.eq(parameter, value)).setResultTransformer(
//						Criteria.DISTINCT_ROOT_ENTITY);
//				cri2.addOrder(Property.forName("id").asc());
//				cri2.setFirstResult(pageNumber * pageSize);
//				cri2.setMaxResults(pageSize);				
//				map.put("results", cri2.list());
//		return page.getPage(map,pageNumber,pageSize);
	}
	
	public Map getObjectsOrderedByFieldForPage(Class clazz, 
			final String field, final int pageNumber, final int pageSize) {
		Page page = new Page();
		Map map = new HashMap();
		CriteriaQuery queryCriteria = builder.createQuery(clazz);
		Root<Object> root = queryCriteria.from(clazz);
		Predicate restrictions = null;
//		restrictions = builder.equal(root.<Object>get(parameter), value);
		queryCriteria.select(root);
//		queryCriteria.where(restrictions);
		queryCriteria.distinct(true);
		queryCriteria.orderBy(builder.asc(root.get(field)));

		TypedQuery<Object> query = null;
		query = getCurrentSession().createQuery(queryCriteria).setMaxResults(pageSize).setFirstResult(pageNumber * pageSize);

		List list =  query.getResultList();
		return page.getPage(map, pageNumber, pageSize);
//		Page page = new Page();
//				Criteria cri = getCurrentSession().createCriteria(clazz);
//				cri.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
//						.setProjection(
//								Projections.projectionList().add(
//										Projections.rowCount()));
//				Map map = new HashMap();
//				map.put("listSize", cri.list().iterator().next());
//				Criteria criteria = getCurrentSession().createCriteria(clazz);
//				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//				criteria.addOrder(Property.forName(field).asc());
//				map.put("results", criteria.setFirstResult(
//						pageNumber * pageSize).setMaxResults(pageSize).list());
//		return page.getPage(map, pageNumber, pageSize);
	}
	
	public List getObjectsByParameterOrderedByFieldList(Class clazz, 
			final String parameter, final Object value,
			final List<String> fieldList) {

		CriteriaQuery queryCriteria = builder.createQuery(clazz);
		Root<Object> root = queryCriteria.from(clazz);
		Predicate restrictions = null;
//		restrictions = builder.equal(root.<Object>get(parameter), value);
		queryCriteria.select(root);
//		queryCriteria.where(restrictions);
		queryCriteria.distinct(true);
		for (int i = 0; i < fieldList.size(); i++) {
			queryCriteria.orderBy(builder.desc(root.get(fieldList.get(i))));
		}

		TypedQuery<Object> query = null;
		query = getCurrentSession().createQuery(queryCriteria);

		List list =  query.getResultList();
		return list;
//		Criteria criteria = getCurrentSession().createCriteria(clazz).add(
//				Expression.eq(parameter, value));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		for (int i = 0; i < fieldList.size(); i++) {
//			criteria.addOrder(Property.forName(fieldList.get(i)).desc());
//		}
//		List list = (List) criteria.list();
//		return list;
	}
	
	public List getObjectsOrderedByFieldASC(Class clazz, final String field) {
		CriteriaQuery queryCriteria = builder.createQuery(clazz);
		Root<Object> root = queryCriteria.from(clazz);
		Predicate restrictions = null;
//		restrictions = builder.equal(root.<Object>get(parameter), value);
		queryCriteria.select(root);
//		queryCriteria.where(restrictions);
		queryCriteria.distinct(true);
		queryCriteria.orderBy(builder.asc(root.get(field)));

		TypedQuery<Object> query = null;
		query = getCurrentSession().createQuery(queryCriteria);

		List list =  query.getResultList();
		return list;
//		Criteria criteria = getCurrentSession().createCriteria(clazz);
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		criteria.addOrder(Property.forName(field).asc());
//
//		List list = (List) criteria.list();
//		return list;
	}
	 
	 public Map getObjectsByParameterAndLikeForPage(Class clazz, 
				final String parameter, final Object value,final String parameterLike,final String filter, final int pageNumber,
				final int pageSize) {
		 Page page = new Page();
			Map map = new HashMap();
			CriteriaQuery queryCriteria = builder.createQuery(clazz);
			Root<Object> root = queryCriteria.from(clazz);
			Predicate restrictions = null;
			List<Predicate> predicates = new ArrayList<Predicate>();
			restrictions = builder.equal(root.<Object>get(parameter), value);
			predicates.add(restrictions);
			if(filter !=null && !filter.equals("")){
				Predicate restrictions2 = builder.like(root.<String>get(parameterLike), "%"+filter+"%");
				predicates.add(restrictions2);
				
				
//				cri.add(Expression.ilike(parameterLike, '%'+filter+'%')).setResultTransformer(
//						Criteria.DISTINCT_ROOT_ENTITY).setProjection(
//								Projections.projectionList()
//								.add(Projections.rowCount()));
			} else {
//				cri.setResultTransformer(
//						Criteria.DISTINCT_ROOT_ENTITY).setProjection(
//								Projections.projectionList()
//								.add(Projections.rowCount()));
			}
			queryCriteria.distinct(true);
			
			queryCriteria.select(root).where(predicates.toArray(new Predicate[]{})).distinct(true);
			Query<Long> query = getCurrentSession().createQuery(queryCriteria);
	        long count = query.getSingleResult();
	        System.out.println("Count = " + count); 
			
			queryCriteria.select(root);
			queryCriteria.where(restrictions);
			queryCriteria.distinct(true);
			queryCriteria.orderBy(builder.asc(root.get("id")));

			TypedQuery<Object> query2 = null;
			query2 = getCurrentSession().createQuery(queryCriteria).setMaxResults(pageSize).setFirstResult(pageNumber * pageSize);

			List list =  query2.getResultList();
			map.put("listSize", count);
			map.put("results", query2.getResultList());
			return page.getPage(map, pageNumber, pageSize);
			
//			Map map = new HashMap();
//			map.put("listSize", cri.list().iterator().next());
//			Criteria cri2 = getCurrentSession().createCriteria(clazz);
//			cri2.add(Expression.eq(parameter, value));
//			if(filter !=null && !filter.equals("")){
//				cri2.add(Expression.ilike(parameterLike, '%'+filter+'%')).setResultTransformer(
//						Criteria.DISTINCT_ROOT_ENTITY);
//			}else{
//				cri2.setResultTransformer(
//						Criteria.DISTINCT_ROOT_ENTITY);
//			}
//
//			cri2.addOrder(Property.forName("id").asc());
//			cri2.setFirstResult(pageNumber * pageSize);
//			cri2.setMaxResults(pageSize);	
//			map.put("results", cri2.list());
//			return page.getPage(map,pageNumber,pageSize);
			
			
//			
//			Page page = new Page();
//			Criteria cri = getCurrentSession().createCriteria(clazz);
//			cri.add(Expression.eq(parameter, value));
//			if(filter !=null && !filter.equals("")){
//				cri.add(Expression.ilike(parameterLike, '%'+filter+'%')).setResultTransformer(
//						Criteria.DISTINCT_ROOT_ENTITY).setProjection(
//								Projections.projectionList()
//								.add(Projections.rowCount()));
//			}else{
//				cri.setResultTransformer(
//						Criteria.DISTINCT_ROOT_ENTITY).setProjection(
//								Projections.projectionList()
//								.add(Projections.rowCount()));
//			}
//			Map map = new HashMap();
//			map.put("listSize", cri.list().iterator().next());
//			Criteria cri2 = getCurrentSession().createCriteria(clazz);
//			cri2.add(Expression.eq(parameter, value));
//			if(filter !=null && !filter.equals("")){
//				cri2.add(Expression.ilike(parameterLike, '%'+filter+'%')).setResultTransformer(
//						Criteria.DISTINCT_ROOT_ENTITY);
//			}else{
//				cri2.setResultTransformer(
//						Criteria.DISTINCT_ROOT_ENTITY);
//			}
//
//			cri2.addOrder(Property.forName("id").asc());
//			cri2.setFirstResult(pageNumber * pageSize);
//			cri2.setMaxResults(pageSize);	
//			map.put("results", cri2.list());
//			return page.getPage(map,pageNumber,pageSize);
		}
	 
	 
	 ////////////////////////////////////////////////////stopped upgrading hibernate here
	 public Map getObjectsByParameterLikeForPage(Class clazz, final String parameterLike,final String filter, final int pageNumber,
				final int pageSize) {
			Page page = new Page();
					Criteria cri = getCurrentSession().createCriteria(clazz);
					if(filter !=null && !filter.equals("")){
					cri.add(Expression.ilike(parameterLike, '%'+filter+'%')).setResultTransformer(
							Criteria.DISTINCT_ROOT_ENTITY).setProjection(
							Projections.projectionList()
									.add(Projections.rowCount()));
					}else{
						cri.setResultTransformer(
								Criteria.DISTINCT_ROOT_ENTITY).setProjection(
								Projections.projectionList()
										.add(Projections.rowCount()));
					}
					Map map = new HashMap();
					map.put("listSize", cri.list().iterator().next());
					Criteria cri2 = getCurrentSession().createCriteria(clazz);
					if(filter !=null && !filter.equals("")){
					cri2.add(Expression.ilike(parameterLike, '%'+filter+'%')).setResultTransformer(
							Criteria.DISTINCT_ROOT_ENTITY);
				}else{
					cri2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				}
					cri2.addOrder(Property.forName("id").asc());
					cri2.setFirstResult(pageNumber * pageSize);
					cri2.setMaxResults(pageSize);	
					map.put("results", cri2.list());
			return page.getPage(map,pageNumber,pageSize);
		}
	 
	 public Map getObjectsByParameterAndTwoLikeParamForPage(Class clazz, final String parameter, final Object value,final String firstParameterLike,final String firstFilter,
				final String secondParameterLike,final String secondFilter, final int pageNumber,
				final int pageSize) {
			Page page = new Page();
					Criteria cri = getCurrentSession().createCriteria(clazz);
					cri.add(Expression.eq(parameter, value));
					cri.add(Expression.ilike(firstParameterLike, '%'+firstFilter+'%'));
					cri.add(Expression.ilike(secondParameterLike, '%'+secondFilter+'%')).setResultTransformer(
							Criteria.DISTINCT_ROOT_ENTITY).setProjection(
							Projections.projectionList()
									.add(Projections.rowCount()));
					Map map = new HashMap();
					map.put("listSize", cri.list().iterator().next());
					Criteria cri2 = getCurrentSession().createCriteria(clazz);
					cri2.add(Expression.eq(parameter, value));
					cri2.add(Expression.ilike(firstParameterLike, '%'+firstFilter+'%'));
					cri2.add(Expression.ilike(secondParameterLike, '%'+secondFilter+'%')).setResultTransformer(
							Criteria.DISTINCT_ROOT_ENTITY);
					cri2.addOrder(Property.forName("id").asc());
					cri2.setFirstResult(pageNumber * pageSize);
					cri2.setMaxResults(pageSize);	
					map.put("results", cri2.list());
			return page.getPage(map,pageNumber,pageSize);
		}
	 
	 public Object getObjectByTwoParameters(Class clazz, final String parameter1, final Object value1,final String parameter2, final String value2) {
	        if (log.isDebugEnabled()) {
	            log.debug("Getting object of class :"+ clazz +", with "+parameter1+" :"+ value1);
	            log.debug("Getting object of class :"+ clazz +", with "+parameter2+" :"+ value2);
	        }
	        CriteriaQuery queryCriteria = builder.createQuery(clazz);
	    	Root<Object> root = queryCriteria.from(clazz);
	    	List<Predicate> predicates = new ArrayList<Predicate>();
	    	Predicate restriction1 = builder.equal(root.get(parameter1), value1);
	    	Predicate restriction2 = builder.equal(root.get(parameter2), value2);
	    	predicates.add(restriction1);
	    	predicates.add(restriction2);
	    	
	    	queryCriteria.select(root).where(predicates.toArray(new Predicate[]{})).distinct(true);
	    	TypedQuery<Object> query = getCurrentSession().createQuery(queryCriteria);
	    	Object object = query.getSingleResult();
	        if ((object == null)&&(log.isDebugEnabled())) {
	            log.debug("No object found");
	        }
	        else if (log.isDebugEnabled()) {
	            log.debug("Got object :"+ object);
	        }
	        return object;
	        
//			Object object =  getCurrentSession().createCriteria(clazz)
//					.add(Expression.eq(parameter1,value1))
//					.add(Expression.eq(parameter2,value2))
//					.uniqueResult();
//				
//	        if ((object == null)&&(log.isDebugEnabled())) {
//	            log.debug("No object found");
//	        }
//	        else if (log.isDebugEnabled()) {
//	            log.debug("Got object :"+ object);
//	        }
//	        return object;
	    }
	 
	 public List getObjectsByParameterOrderedDescByFieldList(Class clazz, final String parameter, final Object value,
			 final List<String> fieldList) {
		 Criteria criteria = getCurrentSession().createCriteria(clazz).add(
				 Expression.eq(parameter, value));
		 criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		 for (int i = 0; i < fieldList.size(); i++) {
			 criteria.addOrder(Property.forName(fieldList.get(i)).desc());
		 }
		 List list = (List) criteria.list();
		 return list;
	 }
	 
	 public List getObjectsByTwoParametersOrderedByFieldList(Class clazz, 
				final String parameter1, final Object value1,
				final String parameter2, final Object value2,
				final List<String> fieldList) {
		 Criteria criteria = getCurrentSession().createCriteria(clazz).add(
				 Expression.eq(parameter1, value1));
		 criteria.add (Expression.eq(parameter2, value2));
		 criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		 for (int i = 0; i < fieldList.size(); i++) {
			 criteria.addOrder(Property.forName(fieldList.get(i)).asc());
		 }
		 List list = (List) criteria.list();
		 return list;
		}
	 
	 public List getObjectsByThreeParametersOrderedByFieldList(Class clazz, 
				final String parameter1, final Object value1,
				final String parameter2, final Object value2,
				final String parameter3, final Object value3,
				final List<String> fieldList) {

		 Criteria criteria = getCurrentSession().createCriteria(clazz).add(
				 Expression.eq(parameter1, value1));
		 criteria.add (Expression.eq(parameter2, value2));
		 criteria.add (Expression.eq(parameter3, value3));
		 criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		 for (int i = 0; i < fieldList.size(); i++) {
			 criteria.addOrder(Property.forName(fieldList.get(i)).asc());
		 }
		 List list = (List) criteria.list();
		 return list;
		}
	 
	 public List getObjectsByThreeParametersThirdNotNullOrderedByFieldList(Class clazz, 
				final String parameter1, final Object value1,
				final String parameter2, final Object value2,
				final String parameter3, 
				final List<String> fieldList) {

		 Criteria criteria = getCurrentSession().createCriteria(clazz).add(
				 Expression.eq(parameter1, value1));
		 criteria.add (Expression.eq(parameter2, value2));
		 criteria.add (Expression.isNotNull(parameter3));
		 criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		 for (int i = 0; i < fieldList.size(); i++) {
			 criteria.addOrder(Property.forName(fieldList.get(i)).asc());
		 }
		 List list = (List) criteria.list();
		 return list;
		}
	 
	 public Object getObjectByTwoObjects(Class clazz, final String parameter1, final Object value1,final String parameter2, final Object value2) {
	        if (log.isDebugEnabled()) {
	            log.debug("Getting object of class :"+ clazz +", with "+parameter1+" :"+ value1);
	            log.debug("Getting object of class :"+ clazz +", with "+parameter2+" :"+ value2);
	        }
	        
	        CriteriaQuery queryCriteria = builder.createQuery(clazz);
	    	Root<Object> root = queryCriteria.from(clazz);
	    	List<Predicate> predicates = new ArrayList<Predicate>();
	    	Predicate restriction1 = builder.equal(root.get(parameter1), value1);
	    	Predicate restriction2 = builder.equal(root.get(parameter2), value2);
	    	predicates.add(restriction1);
	    	predicates.add(restriction2);
	    	
	    	queryCriteria.select(root).where(predicates.toArray(new Predicate[]{})).distinct(true);
	    	TypedQuery<Object> query = getCurrentSession().createQuery(queryCriteria);
	    	Object object = query.getSingleResult();
	        if ((object == null)&&(log.isDebugEnabled())) {
	            log.debug("No object found");
	        }
	        else if (log.isDebugEnabled()) {
	            log.debug("Got object :"+ object);
	        }
	        return object;
	        
//			Object object = getCurrentSession().createCriteria(clazz)
//					.add(Expression.eq(parameter1,value1))
//					.add(Expression.eq(parameter2,value2))
//					.uniqueResult();
//				
//	        if ((object == null)&&(log.isDebugEnabled())) {
//	            log.debug("No object found");
//	        }
//	        else if (log.isDebugEnabled()) {
//	            log.debug("Got object :"+ object);
//	        }
//	        return object;
	    }
	 
	 public List getObjectsByTwoObjects(Class clazz,final String parameter1, final Object value1,final String parameter2, final Object value2) {
	        if (log.isDebugEnabled()) {
	            log.debug("Getting object of class :"+ clazz +", with "+parameter1+" :"+ value1);
	            log.debug("Getting object of class :"+ clazz +", with "+parameter2+" :"+ value2);
	        }
			List list = getCurrentSession().createCriteria(clazz)
					.add(Expression.eq(parameter1,value1))
					.add(Expression.eq(parameter2,value2)).list();
				
	        if ((list == null)&&(log.isDebugEnabled())) {
	            log.debug("No list found");
	        }
	        else if (log.isDebugEnabled()) {
	            log.debug("Got list :"+ list.size());
	        }
	        return list;
	    }
	 
}