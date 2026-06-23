/**
 * 
 */
package com._4s_.common.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.common.service.BaseManager;

/**
 * @author mragab
 *
 */
public class BaseController implements Controller {
	protected final Log log = LogFactory.getLog(getClass());
	protected BaseManager baseManager;
	protected Class commandClass;
	protected String listViewName;
	
	
	public BaseManager getBaseManager() {
		return baseManager;
	}
	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}
	public Class getCommandClass() {
		return commandClass;
	}
	public void setCommandClass(Class commandClass) {
		this.commandClass = commandClass;
	}
	public String getListViewName() {
		return listViewName;
	}
	public void setListViewName(String listViewName) {
		this.listViewName = listViewName;
	}
	
	
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("entering 'handleRequest' method...");
		}
		
		return new ModelAndView(listViewName,"results",baseManager.getObjects(commandClass));
		

	}
	
}
