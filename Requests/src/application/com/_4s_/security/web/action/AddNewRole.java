package com._4s_.security.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.security.model.Permissions;
import com._4s_.security.model.Roles;
import com._4s_.security.model.SecurityApplication;

public class AddNewRole extends BaseSimpleFormController {

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException error)
			throws Exception {
		// TODO Auto-generated method stub
		String application = request.getParameter("selectApplication");
		String clonedRole = request.getParameter("clonedRole");
		SecurityApplication app = (SecurityApplication) baseManager.getObjectByParameter(SecurityApplication.class, "id", new Long(application));
		Roles role = (Roles) command;
		if (clonedRole != null && !clonedRole.equals("")) {
			Roles cRole = (Roles) baseManager.getObjectByParameter(Roles.class,
					"id", new Long(clonedRole));
			Roles rr = new Roles();
			rr.setApplication(cRole.getApplication());

			rr.setRolename(((Roles) command).getRolename());
			role = rr;
			Iterator itr = cRole.getPermissions().iterator();
			log.debug(">>>>>>>>>>>>>>>>>>>>>> cRole.getPermissions() "
					+ cRole.getPermissions());
			while (itr.hasNext()) {
				log.debug(">>>>>>>>>>>>>>>>>1");
				Permissions perm = (Permissions) itr.next();
				rr.getPermissions().add(perm);
				perm.getRoles().add(rr);

			}
		}
		role.getApplication().getRoles().add(role);
		baseManager.saveObject(role);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<script>window.opener.location.reload();window.close();</script>");
		out.close();
		return new ModelAndView();
	}

	protected Map referenceData(HttpServletRequest arg0, Object arg1,
			Errors arg2) throws Exception {
		// TODO Auto-generated method stub
		List applications = new ArrayList();
		applications = baseManager.getObjects(SecurityApplication.class);
		List roles = baseManager.getObjects(Roles.class);
		Map model = new HashMap();
		model.put("applications", applications);
		model.put("roles", roles);
		return model;
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>start form Backing Object  *******************************" );
		
		Roles role;
		SecurityApplication application = null;

		String roleId = request.getParameter("roleId");
		String applicationId = request.getParameter("selectApplication");
		log.debug(">>>>>>>>>>..applicationId " + applicationId);
		if ((applicationId != null) && (applicationId.length() > 0)) {
			application = (SecurityApplication) baseManager.getObject(
					SecurityApplication.class, new Long(applicationId));
			log.debug(">>>>>>>>>>>>>>>>>>........application " + application);
		}
		log.debug(">>>>>>>>>>> roleId :" + roleId);

		if (roleId != null && !roleId.equals("")) {
			{
				role = (Roles) baseManager.getObject(Roles.class, new Long(
						roleId));
				log.debug(">>>>>>>>>>>>>>>>>> if role.getApplication "
						+ role.getApplication());
			}

		} else {
			log.debug(">>>>>>>>>>>>>>............roleId " + roleId);
			role = new Roles();
			role.setApplication(application);
			log.debug(">>>>>>>>>>>>>>>>>>else role.getApplication "
					+ role.getApplication());
		}
		return role;
	}

}
