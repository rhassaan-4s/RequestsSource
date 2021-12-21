package com._4s_.security.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.security.model.Fields;
import com._4s_.security.model.Permissions;
import com._4s_.security.model.Roles;
import com._4s_.security.model.SecurityApplication;
import com._4s_.security.model.User;
import com._4s_.security.service.MySecurityManager;

public class EditSecurity extends BaseSimpleFormController {
	private MySecurityManager mgr = null;

	public MySecurityManager getMgr() {
		return mgr;
	}

	public void setMgr(MySecurityManager mgr) {
		this.mgr = mgr;
	}

	protected void onBind(HttpServletRequest request, Object command,
			BindException error) throws Exception {
		log.debug(">>>>>>>>>>>>>>>> strat onBind");
		// Fields field = (Fields) command;
		// Roles role;
		// String permissionString = request.getParameter("zeft");
		// log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> permissionString "
		// + permissionString);
		// Permissions permission = (Permissions) baseManager.getObject(
		// Permissions.class, new Long(permissionString));
		// log.debug(">>>>>>>>>>>>>>>>>>>> permission " + permission);
		// Iterator itr = permission.getRoles().iterator();
		// log.debug(">>>>>>>>>>.........permission.getRoles() "
		// + permission.getRoles());
		// Set s = new HashSet();
		//
		// while (itr.hasNext()) {
		// Roles soso = ((Roles) itr.next());
		// soso.getPermissions().remove(permission);
		// s.add(soso);
		// }
		// permission.getRoles().removeAll(s);
		// log.debug(">>>>>>>>>>>>>>>>>>>>>> permission.getRoles() "
		// + permission.getRoles());
		// log
		// .debug(">>>>>>>>>>>>>>>>>>>------------------------------<<<<<<<<<<<<<<<<<<<<<<<<<");
		// String[] permissionRolesList = request
		// .getParameterValues("permissionRoles");
		// log.debug(">>>>>>>>>>>>>>>>>>>>>>>>> permissionRolesList "
		// + permissionRolesList);
		// if (permissionRolesList != null) {
		// log.debug(">>>>>>>>>>>>>>>>> if ");
		// for (int i = 0; i < permissionRolesList.length; i++) {
		// log.debug(">>>>>>>>>>>>>>>>>> i " + i);
		// role = (Roles) baseManager.getObject(Roles.class, new Long(
		// permissionRolesList[i]));
		// log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>> role " + role);
		// permission.getRoles().add(role);
		// role.getPermissions().add(permission);
		// baseManager.saveObject(role);
		// }
		// }
		//
		// baseManager.saveObject(permission);
		// log.debug(">>>>>>>>>>>>>>>>>permission.getRoles() "
		// + permission.getRoles());
		log.debug(">>>>>>>>>>>>>>>> end bind");
	}

	@RequestMapping(value = "/editSecurity.html", method = RequestMethod.POST)
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute("field") final Fields command, BindException arg3)
			throws Exception {
		// TODO Auto-generated method stub
		Roles selectedRole;
		String permissionString = request.getParameter("zeft");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> permissionString "
				+ permissionString);
		Permissions permission = (Permissions) baseManager.getObject(
				Permissions.class, new Long(permissionString));
		log.debug(">>>>>>>>>>>>>>>>>>>> permission " + permission);
		List permissionsRoles = new ArrayList();
		if (log.isDebugEnabled()) {
			log.debug("entering 'onSubmit' method....");
		}
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String fieldId = request.getParameter("fieldId");
		Fields field = (Fields) command;
		SecurityApplication application = field.getApplication();
/*		SecurityApplication app = (SecurityApplication) baseManager.getObject(
				SecurityApplication.class, new Long(7));
*/		List role = application.getRoles();
//		List appRole = app.getRoles();
//		role.addAll(appRole);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>..request ");
		if (request.getParameterValues("permissionRoles")!= null) {
			String[] permissionRolesList = request
					.getParameterValues("permissionRoles");

			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>lenght "
					+ permissionRolesList.length);
			for (int i = 0; i < permissionRolesList.length; i++) {
				selectedRole = (Roles) baseManager.getObject(Roles.class,
						new Long(permissionRolesList[i]));
				permissionsRoles.add(selectedRole);

			}
		}

		log.debug(">>>>>>>>>>>>>>>>permissionsRoles " + permissionsRoles);
		mgr.compareLists(field, role, permissionsRoles);

		baseManager.saveObject(field);
		log
				.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("editSecurity.html?fieldId="
				+ fieldId));

	}

	protected Map referenceData(HttpServletRequest request, Object command,
			Errors error) throws Exception {

		// TODO Auto-generated method stub
		String fieldId = request.getParameter("fieldId");
		Fields field = (Fields) command;
		SecurityApplication application = field.getApplication();
		String option = request.getParameter("option");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>option " + option);
		if (option == null || option.equals("")) {
			option = "0";
		}
		int y = Integer.parseInt(option);
/*		SecurityApplication app = (SecurityApplication) baseManager.getObject(
				SecurityApplication.class, new Long(7));
*/
		List role = application.getRoles();
//		List appRole = app.getRoles();
//		role.addAll(appRole);
		List roles = role;
		Map model = new HashMap();
		model.put("fieldId", fieldId);
		model.put("option", y);
		model.put("roles", roles);
		return model;
	}

	@RequestMapping(value = "/editSecurity.html", method = RequestMethod.GET)
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Fields field = null;
		String option = request.getParameter("option");
		String fieldId = request.getParameter("fieldId");
		log.info(">>>>>>>>>>>>>>>>>>>fieldId" + fieldId);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>fieldId" + fieldId);
		if (option != null && !option.equals("")) {

			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>option = " + option);
		} else {
			log.debug(">>>>>>>>>>>>>>>>>>....option = " + option);
		}
		if ((fieldId != null) && !fieldId.equals("")) {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>fieldId" + fieldId);
			field = (Fields) baseManager.getObjectByParameter(Fields.class,
					"id", new Long(fieldId));
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + field + "<<<<<<<<<<<<");
		} else {
			field = new Fields();
		}

		return field;
	}

	// function recursive(Fields field){
	// if (field.getParentField()==null) If the Parent is NULL, we have a top
	// level
	// Then Start looking for children
	// List fieldChildren = field.getChildFields();
	// Iterator itr = fieldChildren.iterator();
	// while(itr.hasNext()){
	// IF the row we're looking at has a parent of the region passed in, then
	// delve into its children
	// List query = select * from security_fields s where s.parentId=&currentId"
	//
	// If (PARENTID ==currentId ) Then
	// ' Call our recursive function to find the children of this record and so
	// on'.
	// }
	// }

}
