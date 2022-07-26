package com._4s_.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import com._4s_.common.dao.BaseDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

/**
 * Base class for Business Services - use this class for utility methods and
 * generic CRUD methods.
 * 
 * <p><a href="BaseManager.java.html"><i>View Source</i></a></p>
 *
 */
public class BaseManagerImpl implements BaseManager {
    protected final Log log = LogFactory.getLog(getClass());
    protected BaseDAO baseDAO;
    
    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    
    public Object getObject(Class clazz, Serializable id) {
        return baseDAO.getObject(clazz, id);
    }
    
    
    public Object getObjectByParameter (Class clazz,String parameter,Object value){
    	return baseDAO.getObjectByParameter(clazz,parameter,value);
    }
    
    /* (non-Javadoc)
	 * @see com._4s_.commons.service.BaseManager2#getObjects(java.lang.Class)
	 */
    public List getObjects(Class clazz) {
        return baseDAO.getObjects(clazz);
    }
    
    /* (non-Javadoc)
	 * @see com._4s_.commons.service.BaseManager2#removeObject(java.lang.Class, java.io.Serializable)
	 */
    public void removeObject(Class clazz, Serializable id) {
        baseDAO.removeObject(clazz, id);
    }
    /* (non-Javadoc)
	 * @see com._4s_.commons.service.BaseManager2#removeObject(java.lang.Object)
	 */
    public void removeObject(Object object) {
        baseDAO.removeObject(object);
    }
    
    /* (non-Javadoc)
	 * @see com._4s_.commons.service.BaseManager2#saveObject(java.lang.Object)
	 */
    public void saveObject(Object o) {
        baseDAO.saveObject(o);
    }
    
    public void updateObject(Object o) {
        baseDAO.updateObject(o);
    }
    
    public void saveObjectWithoutUpdate(Object o) {
        baseDAO.saveObjectWithoutUpdate(o);
    }

    public void flush() throws Exception{
    	baseDAO.flush();
    }
    public void setFlushModeNever() {
    	baseDAO.setFlushModeNever();
    }
    public void setFlushModeAuto() {
    	baseDAO.setFlushModeAuto();
    }
    public void setFlushModeAlways() {
    	baseDAO.setFlushModeAlways();
    }
    public void setFlushModeCommit() {
    	baseDAO.setFlushModeCommit();
    }
    public void detachObject(Object o){
    	baseDAO.detachObject(o);
    }

	public void initializeCollection(Object o) {
		baseDAO.initializeCollection(o);
		
	}

	public void lock(Object o) {
		baseDAO.lock(o);
	}

	public Object getDefaultObject(Class clazz) {
		return baseDAO.getDefaultObject(clazz);
	}

	public Object getDefaultObjectByParameter(Class clazz, String parameter, Serializable id) {
		return baseDAO.getDefaultObjectByParameter(clazz,parameter,id);
	}
	
	public Object getDefaultObjectByParameter(Class clazz, String parameter1,String parameter2, Serializable id) {
		return baseDAO.getDefaultObjectByParameter(clazz,parameter1,parameter2,id);
	}
	public List getObjectsByParameter(final Class clazz,final String parameter, final Object value)
	{
		return baseDAO.getObjectsByParameter(clazz,parameter,value);
	}

	public List getObjectsOrderedByField(Class clazz, String field) {
		
		return baseDAO.getObjectsOrderedByField(clazz,field);
	}

	public List getObjectsByNullParameter(Class clazz, String parameter) {
		return baseDAO.getObjectsByNullParameter(clazz , parameter);
	}
	
	public Map getObjectsByParameterForPage(Class clazz, String parameter,
			Object value, int pageNumber, int pageSize) {
		return baseDAO.getObjectsByParameterForPage(clazz, parameter, value,
				pageNumber, pageSize);
	}

	public Map getObjectsOrderedByFieldForPage(Class clazz, String field,
			int pageNumber, int pageSize) {
		return baseDAO.getObjectsOrderedByFieldForPage(clazz, field,
				pageNumber, pageSize);
	}

	public List getObjectsByParameterOrderedByFieldList(Class clazz, String parameter, Object value, List<String> fieldList) {
		return baseDAO.getObjectsByParameterOrderedByFieldList(clazz, parameter, value, fieldList);
	}
	
	public List getObjectsOrderedByFieldASC(Class clazz, String field) {
		
		return baseDAO.getObjectsOrderedByFieldASC(clazz,field);
	}
	
	public Map getObjectsByParameterAndLikeForPage(final Class clazz,
			final String parameter, final Object value,final String parameterLike,final String filter, final int pageNumber,
			final int pageSize) {
		return baseDAO.getObjectsByParameterAndLikeForPage(clazz, parameter, value,parameterLike,filter,
				pageNumber, pageSize);
	}
	
	public Map getObjectsByParameterLikeForPage(final Class clazz,
			final String parameterLike,final String filter, final int pageNumber,
			final int pageSize){
		return baseDAO.getObjectsByParameterLikeForPage(clazz, parameterLike,filter,
				pageNumber, pageSize);
}
	public Map getObjectsByParameterAndTwoLikeParamForPage(final Class clazz,
			final String parameter, final Object value,final String firstParameterLike,final String firstFilter,
			final String secondParameterLike,final String secondFilter, final int pageNumber,
			final int pageSize){
		return baseDAO.getObjectsByParameterAndTwoLikeParamForPage(clazz, parameter, value, firstParameterLike,
				firstFilter, secondParameterLike, secondFilter, pageNumber, pageSize);
	}
	
	public Object getObjectByTwoParameters(final Class clazz, final String parameter1, final Object value1,final String parameter2, final String value2){
		return baseDAO.getObjectByTwoParameters(clazz, parameter1, value1, parameter2, value2);
	}
	
	public List getObjectsByParameterOrderedDescByFieldList(final Class clazz,
			final String parameter, final Object value,
			final List<String> fieldList){
		return baseDAO.getObjectsByParameterOrderedDescByFieldList(clazz, parameter, value, fieldList);
	}
	public List getObjectsByTwoParametersOrderedByFieldList(final Class clazz,
			final String parameter1, final Object value1,
			final String parameter2, final Object value2,
			final List<String> fieldList){
		return baseDAO.getObjectsByTwoParametersOrderedByFieldList(clazz, parameter1, value1, parameter2, value2, fieldList);
	}
	 public List getObjectsByThreeParametersOrderedByFieldList(final Class clazz,
				final String parameter1, final Object value1,
				final String parameter2, final Object value2,
				final String parameter3, final Object value3,
				final List<String> fieldList){
		 return baseDAO.getObjectsByThreeParametersOrderedByFieldList(clazz, parameter1, value1, parameter2, value2, parameter3, value3, fieldList);
	 }
	 
	 public List getObjectsByThreeParametersThirdNotNullOrderedByFieldList(final Class clazz,
				final String parameter1, final Object value1,
				final String parameter2, final Object value2,
				final String parameter3, 
				final List<String> fieldList){
		 return baseDAO.getObjectsByThreeParametersThirdNotNullOrderedByFieldList(clazz, parameter1, value1, parameter2, value2, parameter3, fieldList);
	 }
	 
	 public Object getObjectByTwoObjects(final Class clazz, final String parameter1, final Object value1,final String parameter2, final Object value2){
		 return baseDAO.getObjectByTwoObjects(clazz, parameter1, value1, parameter2, value2);
	 }
	 
	 public List getObjectsByTwoObjects(final Class clazz, final String parameter1, final Object value1,final String parameter2, final Object value2){
		 return baseDAO.getObjectsByTwoObjects(clazz, parameter1, value1, parameter2, value2);
	 }
}

