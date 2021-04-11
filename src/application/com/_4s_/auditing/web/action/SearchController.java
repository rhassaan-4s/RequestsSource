package com._4s_.auditing.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.auditing.model.AuditLogRecord;
import com._4s_.auditing.model.AuditSearchCommand;
import com._4s_.auditing.model.Auditable;
import com._4s_.auditing.model.ClassName;

import com._4s_.auditing.service.AuditLogManager;
import com._4s_.common.util.DateUtil;
import com._4s_.common.web.action.BaseController;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.security.model.User;

public class SearchController extends BaseSimpleFormController {
	private AuditLogManager mgr = null;

	public AuditLogManager getMgr() {
		return mgr;
	}

	public void setMgr(AuditLogManager mgr) {
		this.mgr = mgr;
	}

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException error)
			throws Exception {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>>>>start onSubmit()..........");

		AuditSearchCommand cmd = (AuditSearchCommand) command;
		Long userId = null;
		String action = cmd.getAction();

		User user = cmd.getUser();
		if (cmd.getUser() != null && !cmd.getUser().equals("")) {
			userId = user.getId();
		}

		Date fromDate = cmd.getFromDate();
		//String fromDateString = DateUtil.convertDateToString(fromDate);
		Date toDate = cmd.getToDate();

		ClassName c = null;
		String className = request.getParameter("className");
		String s = null;
		Auditable o = null;
		String display = null;
		if (className != null && !className.equals("")) {
			c = (ClassName) baseManager.getObjectByParameter(ClassName.class,
					"entityClass", className);
			s = c.getEntityClass();
		}

		String classDetail = request.getParameter("classDetail");
		if ((classDetail != null && !classDetail.equals(""))
				&& !classDetail.equals("0")) {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.not equal null");
			o = (Auditable) baseManager.getObject(Class.forName(s), new Long(
					classDetail));
			display = o.getEntityDisplayName();
		}
		List audits = mgr.search(userId, action, fromDate, toDate, s, display);

		audits = adjustAudits(audits);

		log.debug(">...............end onSubmit()...............");
		return new ModelAndView("searchForm", "search", audits);
	}

	public List adjustAudits(List audits) {
		Iterator itr = audits.iterator();
		log.debug(">>>>>>>>>>.........audits " + audits);
		while (itr.hasNext()) {
			AuditLogRecord soso = ((AuditLogRecord) itr.next());

			Long userId = soso.getUserId();
			// log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>..........soso " + soso);
			User user = (User) baseManager.getObjectByParameter(User.class,
					"id", userId);
			String username = user.getUsername();
			soso.setUserName(username);

			String className = soso.getEntityClass().getName();
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>..........className "
					+ className);
			if (className != null && !className.equals("")) {
				log.debug(">>>>>>>>>>>>>>>>>>...........inside if statement");
				ClassName classNameEntity = (ClassName) baseManager
						.getObjectByParameter(ClassName.class, "entityClass",
								className);
				String classDisplayName = classNameEntity.getName();
				log
						.debug(">>>>>>>>>>>>>>>>>>>>>>>>>..........classDisplayName "
								+ classDisplayName);
				soso.setEntityClassName(classDisplayName);
			}

		}
		return audits;
	}

	protected Map referenceData(HttpServletRequest request, Object command,
			Errors error) throws Exception {
		// TODO Auto-generated method stub
		List users = new ArrayList();
		List classNames = new ArrayList();
		Map model = new HashMap();

		users = baseManager.getObjects(User.class);
		classNames = baseManager.getObjects(ClassName.class);
		
		Iterator itr=classNames.iterator();
		while(itr.hasNext())
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> i am class with name :"+itr.next().toString());
		}
		model.put("users", users);
		model.put("classNames", classNames);
		return model;
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		log
				.debug(">>>>>>>>>>>>>>>>.start formBackingObject>>>>>>>>>>>>>>>>>>>");
		AuditSearchCommand cmd = new AuditSearchCommand();

		log.debug(">>>>>>>>>>>>>>>end formBackingObject>>>>>>>>>>>>>>>>>>>>>>");
		return cmd;

	}

}

// List users = new ArrayList();
// List classNames = new ArrayList();
// Map model = new HashMap();
// String selectClass = request.getParameter("selectClass");
// String option = request.getParameter("option");
// String s = null;
// int y = 0;
// if (option != null){
// y = Integer.parseInt(option);
// }
//
// log.debug(">>>>>>>>>>>>>. y "+y);
// if (y != 0 && (option != null && !option.equals(""))) {
//	
// ClassName c = (ClassName) baseManager.getObject(ClassName.class,new
// Long(option));
// log.debug(">>>>>>>>>>>>>>>>...............c>..........."+c);
// s = c.getEntityClass();
// log.debug(">>>>>>>>>>>>>>>>.............S "+s );
// List classDetails = mgr.getListByClass(s);
// log.debug(">>>>>>>>>>>>>>>>>>>>>>>classDetails "+classDetails);
// model.put("classDetails",classDetails);
// }
//
// users = baseManager.getObjects(User.class);
// classNames = baseManager.getObjects(ClassName.class);
//
// model.put("users", users);
// model.put("classNames", classNames);
// model.put("option",option);
// log.debug(">>>>>>>>>>>>>>>>>>>>>>>>.............end HandleRequest");
// return new ModelAndView("search", model);
