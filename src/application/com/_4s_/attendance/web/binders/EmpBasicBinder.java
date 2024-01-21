package com._4s_.attendance.web.binders;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com._4s_.attendance.model.Title;
import com._4s_.common.model.EmpBasic;
import com._4s_.common.service.BaseManager;
import com._4s_.common.web.binders.BaseBinder;

public class EmpBasicBinder extends PropertyEditorSupport implements BaseBinder{
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
		EmpBasic master = ((EmpBasic) getValue());
		String id = null;
		String text = "";
		if (master != null) {
			id = master.getEmpCode();
			log.debug(">>>>>>>>>>master.id" + id);
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
			EmpBasic master = (EmpBasic) baseManager
					.getObjectByParameter(EmpBasic.class,"empCode", text.replace(",", ""));
			log.debug(">>>>>>>>>>>>>>> master " + master);
			setValue(master);
		}else{
			setValue(null);
		}
		log.debug(">>>>>>>>>>>> Ending setAsText");
	}

	public Class getBindedClass() {
		// TODO Auto-generated method stub
		return EmpBasic.class;
	}
}