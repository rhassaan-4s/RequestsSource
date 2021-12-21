package com._4s_.security.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.web.action.BaseFormControllerInterface;
//import com._4s_.common.service.BaseManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.common.web.binders.DomainObjectBinder;
import com._4s_.security.model.Permissions;
import com._4s_.security.model.Roles;
import com._4s_.security.model.SecurityApplication;
//import com._4s_.security.model.User;
import com._4s_.security.web.validators.ValidateRole;

@Controller
@RequestMapping("/addNewRole.html")
public class AddNewRole  extends BaseSimpleFormController implements BaseFormControllerInterface{

	private ValidateRole roleValidator;
	@Autowired
	private DomainObjectBinder securityApplicationBinder;
	@Autowired
	private DomainObjectBinder roleBinder;

	@Autowired
	public AddNewRole(ValidateRole roleValidator){
		this.roleValidator = roleValidator;
	}

	public DomainObjectBinder getSecurityApplicationBinder() {
		return securityApplicationBinder;
	}


	public void setSecurityApplicationBinder(DomainObjectBinder securityApplicationBinder) {
		this.securityApplicationBinder = securityApplicationBinder;
	}


	public DomainObjectBinder getRoleBinder() {
		return roleBinder;
	}


	public void setRoleBinder(DomainObjectBinder roleBinder) {
		this.roleBinder = roleBinder;
	}


	
	@ModelAttribute("applications")
	public List<SecurityApplication> populateApplications() {
		List applications = new ArrayList();
		applications = baseManager.getObjects(SecurityApplication.class);
		return applications;
	}

	@ModelAttribute("roles")
	public List<Roles> populateRoles() {
		List roles = baseManager.getObjects(Roles.class);
		return roles;
	}

	@Override
	public void initBinder(WebDataBinder binder) {
		// TODO Auto-generated method stub
		super.initBinder(binder);
		binder.registerCustomEditor(securityApplicationBinder.getBindedClass(), null, securityApplicationBinder);
		binder.registerCustomEditor(roleBinder.getBindedClass(), null, roleBinder);
	}
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request, HttpServletResponse response){

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
		//command object
		model.addAttribute("role", role);

		//return form view
		return "addNewRole";
	}

	@RequestMapping(method = RequestMethod.POST)
	@Override
	public String processSubmit(
			@ModelAttribute("role") Object command,HttpServletRequest request, HttpServletResponse response,
			BindingResult result, SessionStatus status) {

		roleValidator.validate((Roles)command, result);

		if (result.hasErrors()) { 
			return "addNewRole";
		} else {

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
			PrintWriter out;
			try {
				out = response.getWriter();

				out
				.println("<script>window.opener.location.reload();window.close();</script>");
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			status.setComplete();
			return "addNewRole";
		}
	}


	//	protected Map referenceData(HttpServletRequest arg0, Object arg1,
	//			Errors arg2) throws Exception {
	//		// TODO Auto-generated method stub
	//		List applications = new ArrayList();
	//		applications = baseManager.getObjects(SecurityApplication.class);
	//		List roles = baseManager.getObjects(Roles.class);
	//		Map model = new HashMap();
	//		model.put("applications", applications);
	//		model.put("roles", roles);
	//		return model;
	//	}

	//	@Autowired(name="securityApplicationBinder,roleBinder")
	//	@Override
	//	public void setBinders(List binders) {
	//		// TODO Auto-generated method stub
	//		super.setBinders(binders);
	//	}

	//	@RequestMapping(value = "/addNewRole", method = RequestMethod.GET)
	//	public ModelAndView showRoles(HttpServletRequest request, HttpServletResponse response) {
	//		// TODO Auto-generated method stub
	//		log.debug(">>>>>>>>>>start form Backing Object  *******************************" );
	//
	//		Roles role;
	//		SecurityApplication application = null;
	//
	//		String roleId = request.getParameter("roleId");
	//		String applicationId = request.getParameter("selectApplication");
	//		log.debug(">>>>>>>>>>..applicationId " + applicationId);
	//		if ((applicationId != null) && (applicationId.length() > 0)) {
	//			application = (SecurityApplication) baseManager.getObject(
	//					SecurityApplication.class, new Long(applicationId));
	//			log.debug(">>>>>>>>>>>>>>>>>>........application " + application);
	//		}
	//		log.debug(">>>>>>>>>>> roleId :" + roleId);
	//
	//		if (roleId != null && !roleId.equals("")) {
	//			{
	//				role = (Roles) baseManager.getObject(Roles.class, new Long(
	//						roleId));
	//				log.debug(">>>>>>>>>>>>>>>>>> if role.getApplication "
	//						+ role.getApplication());
	//			}
	//
	//		} else {
	//			log.debug(">>>>>>>>>>>>>>............roleId " + roleId);
	//			role = new Roles();
	//			role.setApplication(application);
	//			log.debug(">>>>>>>>>>>>>>>>>>else role.getApplication "
	//					+ role.getApplication());
	//		}
	//		return role;
	//	}
	//
}
