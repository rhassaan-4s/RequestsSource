package com._4s_.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.FlushMode;
import org.hibernate.criterion.MatchMode;



public interface BaseDAO {

	public abstract void saveObject(Object o);
	public abstract void updateObject(Object o);
	public abstract void saveObjectWithoutUpdate(Object o);
	public abstract Object getObject(Class clazz, Serializable id);

	public abstract Object getObjectByParameter(final Class clazz,
			final String parameter, final Object value);

	public abstract List getObjectsByParameter(final Class clazz,
			final String parameter, final Object value);

	public abstract List getObjectsByNullParameter(final Class clazz,
			final String parameter);

	public abstract List getObjectsLikeParameter(final Class clazz,
			final String parameter, final String value);

	public abstract List getObjectsByCriteria(final Class clazz,
			final List criterionList);

	public abstract List getObjects(Class clazz);
	
	public abstract List getObjectsOrderedByField(Class clazz,String field);
	
	public abstract void removeObject(Object obj);

	public abstract void removeObject(Class clazz, Serializable id);

	public abstract void removeObjectByParameter(final Class clazz,
			final String parameter, final Object value);

	public abstract Integer getMaxResults();

	public abstract void setMaxResults(Integer maxResults);

	public abstract void setMaxResults(int maxResults);

	public abstract MatchMode getMatchMode();

	public abstract void setMatchMode(MatchMode matchMode);

	public abstract void flush();
	public abstract void setFlushModeNever();
	public abstract void setFlushModeAuto();
	public abstract void setFlushModeAlways();
	public abstract void setFlushModeCommit();
	public abstract FlushMode getFlushMode();
	public abstract void checkFlushMode();
	public abstract void detachObject(Object o) ;
	public abstract void initializeCollection(Object o);
	public abstract void lock(final Object o);
	public abstract Object getDefaultObject(Class clazz);
	public abstract Object getDefaultObjectByParameter(Class clazz,String parameter,Serializable id);
	public abstract Object getDefaultObjectByParameter(Class clazz,String parameter1,String parameter2,Serializable id);
	
	public abstract Map getObjectsByParameterForPage(Class clazz,
			String parameter, Object value, int pageNumber, int pageSize);
	public Map getObjectsOrderedByFieldForPage(Class clazz, String field,
			int pageNumber, int pageSize);
	public List getObjectsByParameterOrderedByFieldList(Class clazz, String parameter, Object value, List<String> fieldList);
	public List getObjectsOrderedByFieldASC(final Class clazz,final String field);
	public Map getObjectsByParameterAndLikeForPage(final Class clazz,
			final String parameter, final Object value,final String parameterLike,final String filter, final int pageNumber,
			final int pageSize);
	public Map getObjectsByParameterLikeForPage(final Class clazz,final String parameterLike,final String filter, final int pageNumber,
			final int pageSize);
	public Map getObjectsByParameterAndTwoLikeParamForPage(final Class clazz,
			final String parameter, final Object value,final String firstParameterLike,final String firstFilter,
			final String secondParameterLike,final String secondFilter, final int pageNumber,
			final int pageSize);
	public Object getObjectByTwoParameters(final Class clazz, final String parameter1, final Object value1,final String parameter2, final String value2);
	public List getObjectsByParameterOrderedDescByFieldList(final Class clazz,
			final String parameter, final Object value,
			final List<String> fieldList);
	
	public List getObjectsByTwoParametersOrderedByFieldList(final Class clazz,
			final String parameter1, final Object value1,
			final String parameter2, final Object value2,
			final List<String> fieldList);
	 public List getObjectsByThreeParametersOrderedByFieldList(final Class clazz,
				final String parameter1, final Object value1,
				final String parameter2, final Object value2,
				final String parameter3, final Object value3,
				final List<String> fieldList);
	 public Object getObjectByTwoObjects(final Class clazz, final String parameter1, final Object value1,final String parameter2, final Object value2);
}