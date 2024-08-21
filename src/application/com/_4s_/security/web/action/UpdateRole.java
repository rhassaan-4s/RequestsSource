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
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.security.model.Fields;
import com._4s_.security.model.Permissions;
import com._4s_.security.model.Roles;
import com._4s_.security.model.SecurityApplication;
import com._4s_.security.model.User;

@Controller
@RequestMapping("/updateRole.html")
public class UpdateRole extends BaseSimpleFormController {

	//	@RequestMapping(value = "/updateRole.html", method = RequestMethod.POST)
	//	protected ModelAndView onSubmit(HttpServletRequest request,
	//			HttpServletResponse response,@ModelAttribute("role") final Roles command, BindException arg3)
	//			throws Exception {
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(HttpServletRequest request,
			@Valid @ModelAttribute("role") Roles command,
			BindingResult result, SessionStatus status,Model model) {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>Update Role Start onSubmit()");
		Roles role = (Roles) command;
		////////////////////////////////////////////////////
		Set<Permissions> permissions=new HashSet<Permissions>();
		if (request.getParameterValues("permissions")!= null) {
			String[] permissionsList = request.getParameterValues("permissions");

			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>length "
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


		//		response.setContentType("text/html");
		//		PrintWriter out = response.getWriter();
		//		out.println("<script>window.opener.location.reload();window.close();</script>");
		//		out.close();
		log.debug(">>>>>>>>>>>>>>>>>>>.........End onSubmit()");
		//		return new ModelAndView();
		return "updateRole";
	}

	//	protected Map referenceData(HttpServletRequest request, Object arg1,
	//			Errors arg2) throws Exception {
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request,@ModelAttribute("role") Roles command)
	{
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
//		model.put("role",role);
		return model;
	}

	//	@RequestMapping(value = "/updateRole.html", method = RequestMethod.GET)
	//	protected Object formBackingObject(HttpServletRequest request)
	//			throws Exception {
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request){
		// TODO Auto-generated method stub
		Roles role;
		String roleId = request.getParameter("roleId");
		log.debug(">>>>>>>>>>> roleId :" + roleId);

		if (roleId != null && !roleId.equals("")) {
			role = (Roles) baseManager.getObject(Roles.class, new Long(
					roleId));
			log.debug(role);
		} else {
			log.debug(">>>>>>>>>>>>>>............roleId " + roleId);
			role = new Roles();
		}
		model.addAttribute("role",role);
		return "updateRole";
	}

}
