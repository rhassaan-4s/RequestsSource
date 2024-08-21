package com._4s_.security.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.security.model.Fields;
import com._4s_.security.model.Permissions;
import com._4s_.security.model.Roles;
import com._4s_.security.model.SecurityApplication;
public class UpdateRole extends BaseSimpleFormController {

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException arg3)
			throws Exception {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>Update Role Start onSubmit()");
		Roles role = (Roles) command;
		////////////////////////////////////////////////////
		Set<Permissions> permissions=new HashSet<Permissions>();
		if (request.getParameterValues("permissions")!= null) {
			String[] permissionsList = request.getParameterValues("permissions");

			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>lenght "
					+ permissionsList.length);
			for (int i = 0; i < permissionsList.length; i++) {
				Permissions p  = (Permissions) baseManager.getObject(Permissions.class,
						new Long(permissionsList[i]));
				permissions.add(p);

			}
		}

		////////////////////////////////////////////////////
		role.setPermissions(permissions);
		baseManager.saveObject(role);
		

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<script>window.opener.location.reload();window.close();</script>");
		out.close();
		log.debug(">>>>>>>>>>>>>>>>>>>.........End onSubmit()");
		return new ModelAndView();
	}

	protected Map referenceData(HttpServletRequest request, Object arg1,
			Errors arg2) throws Exception {
		// TODO Auto-generated method stub
		////////////////////////////////////////////////////////
		/// get the application of the edited role to get all of it's fields
		///and then return all permessions of these fields 
		String roleId = request.getParameter("roleId");
		Roles role = (Roles) baseManager.getObject(Roles.class, new Long(
				roleId));
		SecurityApplication app=role.getApplication();

		List allAppFields = app.getFields();
		List allAppPermessions = new ArrayList();
		Iterator itr = allAppFields.iterator();
		while(itr.hasNext())
		{
			Fields currentField = (Fields)itr.next();
			allAppPermessions.addAll(currentField.getPermissions());
		}
		log.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ " + allAppPermessions.size());

		
		////////////////////////////////////////////////////////		
		Map model = new HashMap();
		model.put("roleId",request.getParameter("roleId"));
		model.put("permessions",allAppPermessions);
		return model;
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		Roles role;
		String roleId = request.getParameter("roleId");
		log.debug(">>>>>>>>>>> roleId :" + roleId);

		if (roleId != null && !roleId.equals("")) {
			{
				role = (Roles) baseManager.getObject(Roles.class, new Long(
						roleId));
			}

		} else {
			log.debug(">>>>>>>>>>>>>>............roleId " + roleId);
			role = new Roles();
		}
		return role;
	}

}
