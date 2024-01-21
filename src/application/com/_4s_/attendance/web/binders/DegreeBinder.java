package com._4s_.attendance.web.binders;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com._4s_.attendance.model.AttendanceDegree;
import com._4s_.attendance.model.Title;
import com._4s_.common.service.BaseManager;
import com._4s_.common.web.binders.BaseBinder;

public class DegreeBinder extends PropertyEditorSupport implements BaseBinder{
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
		AttendanceDegree deg = ((AttendanceDegree) getValue());
		String id = null;
		String text = "";
		if (deg != null) {
			id = deg.getDegree();
			log.debug(">>>>>>>>>>deg.id" + id);
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
			AttendanceDegree deg = (AttendanceDegree) baseManager
					.getObjectByParameter(AttendanceDegree.class,"degree", text.replace(",", ""));
			log.debug(">>>>>>>>>>>>>>> AttendanceDegree " + deg);
			setValue(deg);
		}else{
			setValue(null);
		}
		log.debug(">>>>>>>>>>>> Ending setAsText");
	}

	public Class getBindedClass() {
		// TODO Auto-generated method stub
		return AttendanceDegree.class;
	}
}
