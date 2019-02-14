package com._4s_.auditing.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com._4s_.auditing.dao.AuditLogDAO;
import com._4s_.auditing.model.Auditable;
import com._4s_.auditing.model.ExternalClass;
import com._4s_.common.service.BaseManagerImpl;

public class AuditLogManagerImpl extends BaseManagerImpl implements
		AuditLogManager {
	private AuditLogDAO auditDAO = null;

	public AuditLogDAO getAuditDAO() {
		return auditDAO;
	}

	public void setAuditDAO(AuditLogDAO auditDAO) {
		this.auditDAO = auditDAO;
	}

//	public List search(Date start, Date end, User user, String action) {
//		// TODO Auto-generated method stub
//		return auditDAO.search(start, end, user, action);
//	}

	public List search(Long user, String action,Date startDate, Date endDate,final String entityClass,String displayName) {
		// TODO Auto-generated method stub
		return auditDAO.search(user, action,startDate,endDate,entityClass,displayName);
	}

	public List getListByClass(String className) {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>start getListByClass");
		List objects = new ArrayList();
		List returnedObjects = new ArrayList();
		try{
			 objects =  auditDAO.getObjects(Class.forName(className));
			 log.debug(">>>>>>>>>>>>>>>>>>>>>>>Class.forName(className) "+Class.forName(className));
			 log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>...objects "+((Auditable)objects.get(0)).getId());
		}
		catch (Exception e) {
			log.debug(">>>>>>>>>>>>>>>>>>>>>> Exception: " +e.getMessage() + e.getStackTrace());
			// TODO: handle exception
		}	
		Iterator itr = objects.iterator();
		ExternalClass ex = new ExternalClass();
		ex.setId(new Long(0));
		ex.setName("All");
		returnedObjects.add(ex);
		
		while (itr.hasNext()){
			ex = new ExternalClass();
			Auditable oldObject = (Auditable)itr.next();
			//log.debug(">>>>>>>>>>>>>>>>>>>>>>>..oldObject "+oldObject);
			ex.setId(oldObject.getId());
			ex.setName(oldObject.getEntityDisplayName());
			returnedObjects.add(ex);
			//log.debug(">.......................returnedObjects "+returnedObjects);
			
		}
		
		return returnedObjects;
	}


}
