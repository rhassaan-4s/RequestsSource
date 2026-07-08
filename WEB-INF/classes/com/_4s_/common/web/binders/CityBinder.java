package com._4s_.common.web.binders;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com._4s_.common.model.City;
import com._4s_.common.service.BaseManager;

public class CityBinder extends PropertyEditorSupport {
	private final Log log = LogFactory.getLog(getClass());
	BaseManager baseManager;
	
	public BaseManager getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}

	public String getAsText() {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>> Starting getAsText ");
		City city = ((City) getValue());
		Long id = null;
		String text = "";
		if (city != null) {
			id = city.getId();
			log.debug(">>>>>>>>>>city.id" + id);
		}
		if (id != null) {
			text = id.toString();
			log.debug(">>>>>>>>>> text " + text);
		}
		log.debug(">>>>>>>>>>>> Ending getAsText");
		return text;
	}

	public void setAsText(String text) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>> Starting setAsText");
		if ((text != null) && (text.length() > 0)) {
			log.debug(">>>>>>>>>>>>text " + text);
			City city = (City) baseManager.getObject(
					City.class, new Long(text));
			log.debug(">>>>>>>>>>>>>>> city " + city);
			setValue(city);
		}else{
			setValue(null);
		}
		log.debug(">>>>>>>>>>>> Ending setAsText");
	}
}
