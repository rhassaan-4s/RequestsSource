package com._4s_.common.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.orm.ObjectRetrievalFailureException;
//import org.springframework.orm.hibernate3.HibernateCallback;
//import org.springframework.orm.hibernate3.support.HibernateDaoSupport;




import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.common.util.Page;

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
public class BaseDAOHibernate implements BaseDAO {//extends HibernateDaoSupport
    protected final Log log = LogFactory.getLog(getClass());
    
    @Autowired
	private SessionFactory sessionFactory;
	
    public SessionFactory getSessionFactory() {
        return sessionFactory;}
    public void setSessionFactory(SessionFactory sessionFactory) {
         this.sessionFactory = sessionFactory;
    }

    @Transactional
	public Session getCurrentSession(){
	      return sessionFactory.getCurrentSession();
	}
	

    Integer maxResults = null;
    MatchMode matchMode = MatchMode.START;
    
    /* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#saveObject(java.lang.Object)
	 */
    public void saveObject(Object o) {
        if (log.isDebugEnabled()) {
            log.debug("Saving or Updating Object: " + o.toString());
        }

        checkFlushMode();
        getCurrentSession().saveOrUpdate(o);
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
    public Object getObject(Class clazz, Serializable id) {
        if (log.isDebugEnabled()) {
            log.debug("Getting object of class :"+ clazz +", with id :"+ id);
        }
        Object object = getCurrentSession().get(clazz, id);

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
    }

    /* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#getObjectByParameter(java.lang.Class, java.lang.String, java.lang.Object)
	 */
    public Object getObjectByParameter(final Class clazz, final String parameter, final Object value) {
        if (log.isDebugEnabled()) {
            log.debug("Getting object of class :"+ clazz +", with "+parameter+" :"+ value);
        }
		Object object =  getCurrentSession().createCriteria(clazz)
				.add(Expression.eq(parameter,value))
				.uniqueResult();
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
    public List getObjectsByParameter(final Class clazz, final String parameter, final Object value) {
        if (log.isDebugEnabled()) {
            log.debug("Getting objects of class :"+ clazz +", with "+parameter+" :"+ value);
        }
		List list = (List)getCurrentSession().createCriteria(clazz)
				.add(Expression.eq(parameter,value))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		
        if ((list.size() == 0)&&(log.isDebugEnabled())) {
            log.debug("No objects found");
        }
        else if (log.isDebugEnabled()) {
            log.debug("Got "+list.size()+" objects");
        }
        return list;
    }
    
    public List getObjectsByNullParameter(final Class clazz, final String parameter) {
        if (log.isDebugEnabled()) {
            log.debug("Getting objects of class :"+ clazz +", with null "+parameter);
        }
		List list = (List)getCurrentSession().createCriteria(clazz)
				.add(Expression.isNull(parameter))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		
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
    public List getObjectsLikeParameter(final Class clazz, final String parameter, final String value) {
        if (log.isDebugEnabled()) {
            log.debug("Getting object of class :"+ clazz +", with "+parameter+" like :"+ value);
        }
		
				Criteria criteria = getCurrentSession().createCriteria(clazz)
						.add(Expression.like(parameter, value, matchMode))
						.addOrder(Order.asc(parameter));
				if ((maxResults != null) && (maxResults.intValue() > 0)) {
					criteria = criteria.setMaxResults(maxResults.intValue());
				}
				List list = (List)criteria.list();
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
    public List getObjectsByCriteria(final Class clazz,final List criterionList) {
        if (log.isDebugEnabled()) {
            log.debug("Getting objects of class :"+ clazz +", by criteria");
        }
		
		        Criteria criteria = getCurrentSession().createCriteria(clazz);
		        Iterator itr = criterionList.iterator();
		        while (itr.hasNext()) {
		            criteria.add((Criterion)itr.next());
		        }
		        List list = (List)criteria.list();
		
        if ((list.size() == 0)&&(log.isDebugEnabled())) {
            log.debug("No objects found");
        }
        else if (log.isDebugEnabled()) {
            log.debug("Got "+list.size()+" objects");
        }
        return list;
    }
    
    /* (non-Javadoc)
	 * @see com._4s_.commons.dao.BaseDAO2#getObjects(java.lang.Class)
	 */
    public List getObjects(final Class clazz) {
 		
 				Criteria criteria = getCurrentSession().createCriteria(clazz);
 				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
 				List list = (List) criteria.list();
         return list;
    }  
    
    public List getObjectsOrderedByField(final Class clazz,final String field) {
 		
 				Criteria criteria = getCurrentSession().createCriteria(clazz);
 				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
 				criteria.addOrder(Property.forName(field).asc());
 				List list = (List)  criteria.list();
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
    	Object obj = getObject(clazz, id);
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
    public void removeObjectByParameter(final Class clazz, final String parameter, final Object value) {
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
		getCurrentSession().setFlushMode(FlushMode.NEVER);
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
        if (getFlushMode()==FlushMode.NEVER) {
        	setFlushModeCommit();
        }
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

	public Object getDefaultObject(final Class clazz) {
			Criteria criteria = getCurrentSession().createCriteria(clazz)
					.add(Expression.eq("isDefault",new Boolean(true)));	
			Object object = criteria.uniqueResult();
	        if ((object == null)&&(log.isDebugEnabled())) {
	            log.debug("No object found");
	        }
	        else if (log.isDebugEnabled()) {
	            log.debug("Got object :"+ object);
	        }
	        
	        return object;
	}

	public Object getDefaultObjectByParameter(final Class clazz,final String parameter,final Serializable id) {
			Criteria criteria = getCurrentSession().createCriteria(clazz);
			criteria.createCriteria(parameter).add(Expression.eq("id",id));
			criteria.add(Expression.eq("isDefault",new Boolean(true)));
			return criteria.uniqueResult();
	}
	
	public Object getDefaultObjectByParameter(final Class clazz,final String parameter1,final String parameter2,final Serializable id) {
				Criteria criteria = getCurrentSession().createCriteria(clazz);
				criteria.createCriteria(parameter1).createCriteria(parameter2).add(Expression.eq("id",id));
				criteria.add(Expression.eq("isDefault",new Boolean(true)));
				return criteria.uniqueResult();
}

	public Map getObjectsByParameterForPage(final Class clazz,
			final String parameter, final Object value, final int pageNumber,
			final int pageSize) {
		Page page = new Page();
				Criteria cri = getCurrentSession().createCriteria(clazz);
				cri.add(Expression.eq(parameter, value)).setResultTransformer(
						Criteria.DISTINCT_ROOT_ENTITY).setProjection(
						Projections.projectionList()
								.add(Projections.rowCount()));
				Map map = new HashMap();
				map.put("listSize", cri.list().iterator().next());
				Criteria cri2 = getCurrentSession().createCriteria(clazz);
				cri2.add(Expression.eq(parameter, value)).setResultTransformer(
						Criteria.DISTINCT_ROOT_ENTITY);
				cri2.addOrder(Property.forName("id").asc());
				cri2.setFirstResult(pageNumber * pageSize);
				cri2.setMaxResults(pageSize);				
				map.put("results", cri2.list());
		return page.getPage(map,pageNumber,pageSize);
	}
	
	public Map getObjectsOrderedByFieldForPage(final Class clazz,
			final String field, final int pageNumber, final int pageSize) {
		Page page = new Page();
				Criteria cri = getCurrentSession().createCriteria(clazz);
				cri.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
						.setProjection(
								Projections.projectionList().add(
										Projections.rowCount()));
				Map map = new HashMap();
				map.put("listSize", cri.list().iterator().next());
				Criteria criteria = getCurrentSession().createCriteria(clazz);
				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				criteria.addOrder(Property.forName(field).asc());
				map.put("results", criteria.setFirstResult(
						pageNumber * pageSize).setMaxResults(pageSize).list());
		return page.getPage(map, pageNumber, pageSize);
	}
	
	public List getObjectsByParameterOrderedByFieldList(final Class clazz,
			final String parameter, final Object value,
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
	
	public List getObjectsOrderedByFieldASC(final Class clazz,final String field) {
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Property.forName(field).asc());

		List list = (List) criteria.list();
		return list;
	}
	 
	 public Map getObjectsByParameterAndLikeForPage(final Class clazz,
				final String parameter, final Object value,final String parameterLike,final String filter, final int pageNumber,
				final int pageSize) {
			Page page = new Page();
			Criteria cri = getCurrentSession().createCriteria(clazz);
			cri.add(Expression.eq(parameter, value));
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
			cri2.add(Expression.eq(parameter, value));
			if(filter !=null && !filter.equals("")){
				cri2.add(Expression.ilike(parameterLike, '%'+filter+'%')).setResultTransformer(
						Criteria.DISTINCT_ROOT_ENTITY);
			}else{
				cri2.setResultTransformer(
						Criteria.DISTINCT_ROOT_ENTITY);
			}

			cri2.addOrder(Property.forName("id").asc());
			cri2.setFirstResult(pageNumber * pageSize);
			cri2.setMaxResults(pageSize);	
			map.put("results", cri2.list());
			return page.getPage(map,pageNumber,pageSize);
		}
	 
	 public Map getObjectsByParameterLikeForPage(final Class clazz,final String parameterLike,final String filter, final int pageNumber,
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
	 
	 public Map getObjectsByParameterAndTwoLikeParamForPage(final Class clazz,
				final String parameter, final Object value,final String firstParameterLike,final String firstFilter,
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
	 
	 public Object getObjectByTwoParameters(final Class clazz, final String parameter1, final Object value1,final String parameter2, final String value2) {
	        if (log.isDebugEnabled()) {
	            log.debug("Getting object of class :"+ clazz +", with "+parameter1+" :"+ value1);
	            log.debug("Getting object of class :"+ clazz +", with "+parameter2+" :"+ value2);
	        }
			Object object =  getCurrentSession().createCriteria(clazz)
					.add(Expression.eq(parameter1,value1))
					.add(Expression.eq(parameter2,value2))
					.uniqueResult();
				
	        if ((object == null)&&(log.isDebugEnabled())) {
	            log.debug("No object found");
	        }
	        else if (log.isDebugEnabled()) {
	            log.debug("Got object :"+ object);
	        }
	        return object;
	    }
	 
	 public List getObjectsByParameterOrderedDescByFieldList(final Class clazz,
			 final String parameter, final Object value,
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
	 
	 public List getObjectsByTwoParametersOrderedByFieldList(final Class clazz,
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
	 
	 public List getObjectsByThreeParametersOrderedByFieldList(final Class clazz,
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
	 
	 public Object getObjectByTwoObjects(final Class clazz, final String parameter1, final Object value1,final String parameter2, final Object value2) {
	        if (log.isDebugEnabled()) {
	            log.debug("Getting object of class :"+ clazz +", with "+parameter1+" :"+ value1);
	            log.debug("Getting object of class :"+ clazz +", with "+parameter2+" :"+ value2);
	        }
			Object object = getCurrentSession().createCriteria(clazz)
					.add(Expression.eq(parameter1,value1))
					.add(Expression.eq(parameter2,value2))
					.uniqueResult();
				
	        if ((object == null)&&(log.isDebugEnabled())) {
	            log.debug("No object found");
	        }
	        else if (log.isDebugEnabled()) {
	            log.debug("Got object :"+ object);
	        }
	        return object;
	    }
	 
}