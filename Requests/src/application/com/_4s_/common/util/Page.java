package com._4s_.common.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com._4s_.common.dao.BaseDAOHibernate;

public class Page{
	private static Log log = LogFactory.getLog(DateUtil.class); 
	
	public Map getPage(Map map,int page,int pageSize){
		Boolean next;
		Boolean previous;
		
		
		int listSize = ((Long)map.get("listSize")).intValue();
		int numberOfPages = (listSize / pageSize);
		if (listSize % pageSize != 0){
			numberOfPages = numberOfPages +1;
		}
		
		map.put("numberOfPages",numberOfPages);
		map.put("page",page);
		
		if ((page)*pageSize < listSize){
			next = new Boolean(true);
		}else{
			next = new Boolean(false);
		}
		map.put("next",next);
		
		if (page != 0){
			previous = new Boolean(true);
		}else{
			previous = new Boolean(false);
		}
		map.put("previous",previous);
		
		return map;
	}
}
