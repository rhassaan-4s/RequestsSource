package com._4s_.common.web.binders;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com._4s_.common.service.BaseManager;

public class MainBinder extends PropertyEditorSupport implements BaseBinder {
	protected final Log log = LogFactory.getLog(getClass());
	
	
	protected BaseManager baseManager;
	public BaseManager getBaseManager() {
		return baseManager;
	}
	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}
	
	protected Class bindedClass ;
	public Class getBindedClass() {
		return bindedClass;
	}
	public void setBindedClass(Class bindedClass) {
		this.bindedClass = bindedClass;
	}
	
	protected boolean allowCreateNew = false;
	public boolean isAllowCreateNew() {
		return allowCreateNew;
	}
	public void setAllowCreateNew(boolean allowCreateNew) {
		this.allowCreateNew = allowCreateNew;
	}
	
	
	public String getAsText() {
		if(getValue() != null){
			return getValue().toString();
		}
		return "";
	}

	/**
	 * 
	 */
	public void setAsText(String text) throws IllegalArgumentException {
		Object obj = null;
		text = text.trim();
		
		if ((text!=null)&&(text.length()>0)) {
			obj = baseManager.getObject(bindedClass,new String(text));
			
			if (obj==null) {
				log.warn("!!! No Domain Object found with id:"+text+" for class: "+bindedClass.getName());
			} else {
				log.debug("... Got an object");
			}
			
		} else {
			log.debug("No id provided to bind an entity of class "+bindedClass.getName());
			
			if (allowCreateNew==true) {
				try {
					
					obj = bindedClass.newInstance();
					
					log.info("Created new object of class "+bindedClass.getName());
					log.debug("to disable creation of new object, set allowCreateNew to false in binder bean configuration");
					
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				
			} else {
				log.info("Can not create new object of class: "+bindedClass.getName());
				log.debug("to enable creation of new object, set allowCreateNew to true in binder bean configuration");
			}
		}
		
		setValue(obj);
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< SetAsText");
	}
}
