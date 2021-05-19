package com._4s_.auditing.web.action;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.auditing.model.AuditLogRecord;
import com._4s_.auditing.model.AuditSearchCommand;
import com._4s_.auditing.model.Auditable;
import com._4s_.auditing.model.ClassName;
import com._4s_.auditing.service.AuditLogManager;
import com._4s_.common.web.action.BaseController;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.security.model.User;

public class SearchFormController extends BaseController {
	private AuditLogManager mgr = null;

	public AuditLogManager getMgr() {
		return mgr;
	}

	public void setMgr(AuditLogManager mgr) {
		this.mgr = mgr;
	}


	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stu
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>..handleRequest()");
		SimpleDateFormat dateFormat = null;
		SimpleDateFormat timeFormat = null;
		java.util.Date utilDate2 = null;
		java.sql.Date sqlDate2 = null;
		java.util.Date utilDate = null;
		java.sql.Date sqlDate = null;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		timeFormat = new SimpleDateFormat("hh:mm:ss");
		if (request.getParameter("fromDate") != null && !request.getParameter("fromDate").equals("")) {
			log.debug(">>>>>>>>>>>>>>>>>>. 1 not null");
			utilDate = dateFormat.parse(request.getParameter("fromDate"));
			sqlDate = new java.sql.Date(utilDate.getTime());
		}
		if (request.getParameter("toDate") != null && !request.getParameter("toDate").equals("")) {
			log.debug(">>>>>>>>>>>>>>>>>>. 2 not null");
			utilDate2 = dateFormat.parse(request.getParameter("toDate"));
			sqlDate2 = new java.sql.Date(utilDate2.getTime());
		}
		
		String action = request.getParameter("action");
		log.debug(">>>>>>>>>>>>>action "+action);
		String users = request.getParameter("user");
		log.debug(">>>>>>>>>>>>>>>>>>> user "+users);
		ClassName c = null;
		String className = request.getParameter("className");
		String s= null;
		Auditable o = null ;
		String display = null;
		if(className != null && !className.equals("")){
			c = (ClassName)baseManager.getObjectByParameter(ClassName.class,"entityClass",className);
			s=c.getEntityClass();
		}
		
		String classDetail = request.getParameter("classDetail");
		if((classDetail != null && !classDetail.equals("")) && !classDetail.equals("0")  ){
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.not equal null");
			 o =(Auditable) baseManager.getObject(Class.forName(s),new Long(classDetail));
			 display = o.getEntityDisplayName();
		}
		Long userId = null;
		if (users != null && !users.equals("")) {
			userId = new Long(users);
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>.............End handleRequest()");
		List audits = mgr.search(userId, action, sqlDate, sqlDate2,s,display);
		audits = adjustAudits(audits);
		return new ModelAndView("searchForm", "search", audits);
	}

	public List adjustAudits(List audits) {
		Iterator itr = audits.iterator();
		log.debug(">>>>>>>>>>.........audits " + audits);
		while (itr.hasNext()) {
			AuditLogRecord soso = ((AuditLogRecord) itr.next());

			Long userId = soso.getUserId();
			//log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>..........soso " + soso);
			User user = (User) baseManager.getObjectByParameter(User.class,"id", userId);
			String username = user.getUsername();
			soso.setUserName(username);

			String className = soso.getEntityClass().getName();
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>..........className " + className);
			if(className != null && !className.equals("")){
				log.debug(">>>>>>>>>>>>>>>>>>...........inside if statement");
			ClassName classNameEntity = (ClassName) baseManager.getObjectByParameter(ClassName.class,"entityClass",className);
			String classDisplayName = classNameEntity.getName();
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>..........classDisplayName " + classDisplayName);
			soso.setEntityClassName(classDisplayName);
			}

		}
		return audits;
	}

}
