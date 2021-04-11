package com._4s_.auditing.service;


import java.util.Date;
import java.util.List;

import com._4s_.common.service.BaseManager;

public interface AuditLogManager extends BaseManager{
	//public List search(Date start,Date end,User user,String action);
	public List search(Long user,String action,Date startDate, Date endDate,final String entityClass,String displayName);
	public List getListByClass(String className);
	
	
}
