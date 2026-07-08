package com._4s_.common.web.binders;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com._4s_.common.model.Country;
import com._4s_.common.service.BaseManager;
import com._4s_.requestsApproval.model.Vacation;

public class CountryBinder extends PropertyEditorSupport implements BaseBinder{
	private final Log log = LogFactory.getLog(getClass());

	BaseManager baseManager;

	public BaseManager getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}
	
	public Class getBindedClass() {
		// TODO Auto-generated method stub
		return Country.class;
	}

	public String getAsText() {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>> Starting getAsText ");
		Country country = ((Country) getValue());
		Long id = null;
		String text = "";
		if (country != null) {
			id = country.getId();
			log.debug(">>>>>>>>>>country.id" + id);
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
			Country country = (Country) baseManager
					.getObject(Country.class, new Long(text));
			log.debug(">>>>>>>>>>>>>>> country " + country);
			setValue(country);
		}else{
			setValue(null);
		}
		log.debug(">>>>>>>>>>>> Ending setAsText");
	}
}
