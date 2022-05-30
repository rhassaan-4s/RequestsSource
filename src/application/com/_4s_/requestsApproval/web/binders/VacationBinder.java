package com._4s_.requestsApproval.web.binders;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com._4s_.attendance.model.Title;
import com._4s_.common.service.BaseManager;
import com._4s_.common.web.binders.BaseBinder;
import com._4s_.requestsApproval.model.Vacation;

public class VacationBinder extends PropertyEditorSupport implements BaseBinder{
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
		log.debug(">>>>>>>>>>>> Starting");
		Vacation vac = ((Vacation) getValue());
		String id = null;
		String text = "";
		if (vac != null) {
			id = vac.getVacation();
			log.debug(">>>>>>>>>>vac.id" + id);
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
			Vacation vac = (Vacation) baseManager
					.getObjectByParameter(Vacation.class,"vacation", text.replace(",", ""));
			log.debug(">>>>>>>>>>>>>>> vac " + vac);
			setValue(vac);
		}else{
			setValue(null);
		}
		log.debug(">>>>>>>>>>>> Ending setAsText");
	}

	public Class getBindedClass() {
		// TODO Auto-generated method stub
		return Vacation.class;
	}
}
