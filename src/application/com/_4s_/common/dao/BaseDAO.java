package com._4s_.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.FlushMode;
import org.hibernate.criterion.MatchMode;
import org.springframework.transaction.annotation.Transactional;


public interface BaseDAO {
	
	public void saveObject(Object o);
	public void updateObject(Object o);
	public void saveObjectWithoutUpdate(Object o);
	public Object getObject(Class clazz,Serializable id);

	public Object getObjectByParameter(Class clazz,final String parameter, final Object value);

	public List getObjectsByParameter(Class clazz,final String parameter, final Object value);

	public List getObjectsByNullParameter(Class clazz,final String parameter);

	public List getObjectsLikeParameter(Class clazz,final String parameter, final String value);

//	public List getObjectsByCriteria(final List criterionList);

	public List getObjects(Class clazz);
	
	public List getObjectsOrderedByField(Class clazz, String field);
	
	public void removeObject(Object obj);

	public void removeObject(Class clazz, Serializable id);

	public void removeObjectByParameter(Class clazz, final String parameter, final Object value);

	public Integer getMaxResults();

	public void setMaxResults(Integer maxResults);

	public void setMaxResults(int maxResults);

	public MatchMode getMatchMode();

	public void setMatchMode(MatchMode matchMode);

	public void flush();
	public void setFlushModeNever();
	public void setFlushModeAuto();
	public void setFlushModeAlways();
	public void setFlushModeCommit();
	public FlushMode getFlushMode();
	public void checkFlushMode();
	public void detachObject(Object o) ;
	public void initializeCollection(Object o);
	public void lock(final Object o);
	public Object getDefaultObject(Class clazz);
	public Object getDefaultObjectByParameter(Class clazz,String parameter,Serializable id);
	public Object getDefaultObjectByParameter(Class clazz,String parameter1,String parameter2,Serializable id);
	
	public Map getObjectsByParameterForPage(Class clazz,
			String parameter, Object value, int pageNumber, int pageSize);
	public Map getObjectsOrderedByFieldForPage(Class clazz, String field,
			int pageNumber, int pageSize);
	public List getObjectsByParameterOrderedByFieldList(Class clazz, String parameter, Object value, List<String> fieldList);
	public List getObjectsOrderedByFieldASC(Class clazz,final String field);
	public Map getObjectsByParameterAndLikeForPage(Class clazz,
			final String parameter, final Object value,final String parameterLike,final String filter, final int pageNumber,
			final int pageSize);
	public Map getObjectsByParameterLikeForPage(Class clazz,final String parameterLike,final String filter, final int pageNumber,
			final int pageSize);
	public Map getObjectsByParameterAndTwoLikeParamForPage(Class clazz,
			final String parameter, final Object value,final String firstParameterLike,final String firstFilter,
			final String secondParameterLike,final String secondFilter, final int pageNumber,
			final int pageSize);
	public Object getObjectByTwoParameters(Class clazz, final String parameter1, final Object value1,final String parameter2, final String value2);
	public List getObjectsByParameterOrderedDescByFieldList(Class clazz,
			final String parameter, final Object value,
			final List<String> fieldList);
	
	public List getObjectsByTwoParametersOrderedByFieldList(Class clazz,
			final String parameter1, final Object value1,
			final String parameter2, final Object value2,
			final List<String> fieldList);
	 public List getObjectsByThreeParametersOrderedByFieldList(Class clazz,
				final String parameter1, final Object value1,
				final String parameter2, final Object value2,
				final String parameter3, final Object value3,
				final List<String> fieldList);
	 
	 public List getObjectsByThreeParametersThirdNotNullOrderedByFieldList(Class clazz,
				final String parameter1, final Object value1,
				final String parameter2, final Object value2,
				final String parameter3,
				final List<String> fieldList);
				
	 public Object getObjectByTwoObjects(Class clazz, final String parameter1, final Object value1,final String parameter2, final Object value2);
	public List getObjectsByTwoObjects(Class clazz, String parameter1,
			Object value1, String parameter2, Object value2);
}