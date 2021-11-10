package com._4s_.common.util;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Page{
	private static Log log = LogFactory.getLog(DateUtil.class); 
	
	public Map getPage(Map map,int page,int pageSize){
		Boolean next;
		Boolean previous;
		System.out.println("page " + page);
		System.out.println("pageSize "+ pageSize);
		
		int listSize = ((Integer)map.get("listSize")).intValue();
		int numberOfPages = (listSize / pageSize);
		if (listSize % pageSize != 0){
			numberOfPages = numberOfPages +1;
		}
		System.out.println("Paging: listSize "+ listSize);
		System.out.println("Paging: numberOfPages "+ numberOfPages);
		
		map.put("numberOfPages",numberOfPages);
		map.put("page",page);
		
		if ((page+1)*pageSize < listSize){
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
		
		System.out.println("next "+ next);
		System.out.println("previous "+ previous);
		return map;
	}
}
