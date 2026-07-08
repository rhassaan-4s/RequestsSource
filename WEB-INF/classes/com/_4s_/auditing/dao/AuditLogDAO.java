/*
 * Created on Mar 30, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com._4s_.auditing.dao;


import java.util.Date;
import java.util.List;
import java.util.Set;

import com._4s_.auditing.model.AuditLogRecord;
import com._4s_.common.dao.BaseDAO;
import com._4s_.security.model.User;

/**
 * @author mragab
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface AuditLogDAO extends BaseDAO {
	
//	public void saveLogEvent(String message, Auditable entity,
//			Long userId);
	
	public void saveLogEvent(AuditLogRecord auditLogRecord);
	
	public void saveLogEvents(Set auditLogRecords);
	//public List search(Date start,Date end,User user,String action);
	public List search(Long user,String action,final Date startDate, final Date endDate,final String entityClass,final String displayName);
	public User getUserByUsername(final String userName);
	
}