package com._4s_.security.web.action;

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
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Branch;
import com._4s_.common.model.Department;
import com._4s_.common.model.Employee;
import com._4s_.common.service.CommonManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.i18n.model.MyLocale;
import com._4s_.i18n.service.MessageManager;
import com._4s_.security.model.Roles;
import com._4s_.security.model.SecurityApplication;
import com._4s_.security.model.User;

public class AddUserToRole extends BaseSimpleFormController {

	private MessageManager mgr;

	public MessageManager getMgr() {
		return mgr;
	}

	//	public CommunicationManager communicationManager;

	public CommonManager commonManager;
	//	public CommunicationManager getCommunicationManager() {
	//		return communicationManager;
	//	}
	//
	//	public void setCommunicationManager(CommunicationManager communicationManager) {
	//		this.communicationManager = communicationManager;
	//	}

	public void setMgr(MessageManager mgr) {
		this.mgr = mgr;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException error)
	throws Exception {
		// TODO Auto-generated method stub
		log
		.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		User user = (User) command;


		// adding internal Communicator
		//		InternalCommunicator communicator;
		//		if (user.getId() == null){
		//			communicator = new InternalCommunicator();
		//			Employee employee = user.getEmployee();
		//			employee.setIsInternalCommunicator(new Boolean (true));
		//			communicator.setEmployee(employee);
		//			communicator.setCreationDate(new Date());
		//			communicator.setFullName(employee.getFirstName() +" "+ employee.getLastName());
		//			communicator.setAddress(employee.getAddress());
		//			communicator.setCity(employee.getCity());
		//			communicator.setCompanyName(employee.getDepartment().getDescription());
		//		}
		//		
		//		else{
		//			
		//			Employee employee = user.getEmployee();
		//			communicator = communicationManager.getInternalCommunicatorByEmployee(employee);
		//			 communicator.setFullName(employee.getFirstName() +" "+ employee.getLastName());
		//			 communicator.setAddress(employee.getAddress());
		//			 communicator.setCity(employee.getCity());
		//			 communicator.setCompanyName(employee.getDepartment().getDescription());
		//		}


		String my = request.getParameter("my");
		String option = request.getParameter("option");
		if(my == null || my.equals("")){
			my = option;
		}

		SecurityApplication application = (SecurityApplication) baseManager.getObject(SecurityApplication.class, new Long(my));
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>application " + application);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>my " + my);
		
		MyLocale myLocale = mgr.getDefault();
		Roles role = (Roles)baseManager.getObject(Roles.class,new Long(5));
		List list = new ArrayList();
		list.add(role);
		baseManager.saveObject(user);
		//			baseManager.saveObject(communicator);
		//			if(request.getParameterValues("userRoles") == null || request.getParameterValues("userRoles").equals("")){
		//				user.set(list);
		//			}
		if(user.getRoles().isEmpty()){
			user.set(list);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			log.debug(">>>>>>>>>>>>>>>>>>user.getRoles()"+user.getRoles()+">>>>>>>>>>>>>>>>>>");
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
		Long userId = user.getId();
		if(user.getDefaultApplication() == null){
			user.setDefaultApplication(application);
		}
		if(request.getParameter("myDefaultApplication") != null && !request.getParameter("myDefaultApplication").equals("")){
			user.setDefaultApplication(application);
		}
		log.debug("................"+request.getParameter("canSeeAllStore"));
		if(request.getParameter("canSeeAllStore") != null && !request.getParameter("canSeeAllStore").equals("") && request.getParameter("canSeeAllStore").equals("true")){
			user.getEmployee().setCanSeeAllStore(true);
		}else{
			user.getEmployee().setCanSeeAllStore(false);
		}
		//user.setDefaultApplication(application);
		if(user.getLanguage() == null){
			user.setLanguage(myLocale);
		}
		user.setLanguage(myLocale);
		user.getEmployee().setUser(user);

		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("addUserToRole.html?userId="+ userId+"&option="+option));
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors error) throws Exception {
		// TODO Auto-generated method stub
		log
		.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< Start referenceData: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		User user = (User) command;

		String userId=request.getParameter("userId");
		List applications = new ArrayList();
		applications = baseManager.getObjectsByParameter(SecurityApplication.class, "is_active", new Boolean(true));
		List roles = new ArrayList();
		String option = request.getParameter("option");

		SecurityApplication application;

		if (option == null || option.equals("")) {
			if(user.getDefaultApplication() == null) {
				application = (SecurityApplication)(applications.get(0));
				option = application.getId().toString();
			}
			else {
				application = user.getDefaultApplication();
				option = user.getDefaultApplication().getId().toString();
			}
		}
		else {
			application = (SecurityApplication) baseManager.getObject(
					SecurityApplication.class, new Long(option));
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>application " + application);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>option " + option);
		roles = application.getRoles();
		int y = Integer.parseInt(option);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>roles " + roles);

		String my = request.getParameter("my");
		List departments = baseManager.getObjects(Department.class);
		List cities = commonManager.getCitiesByCountry(new Long(1));

//		List branches = commonManager.getBranchesRelatedByCompany(new Long(1)); // SCFHS
		List branches= commonManager.getObjects(Branch.class);
		Map model = new HashMap();
		model.put("applications", applications);
		model.put("roles", roles);
		model.put("option", y);
		model.put("userId", userId);
		model.put("userId",userId);
		model.put("my",my);
		model.put("departments",departments);
		model.put("cities",cities);
		model.put("branches",branches);

		log
		.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End referenceData: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return model;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected Object formBackingObject(HttpServletRequest request)
	throws Exception {
		// TODO Auto-generated method stub
		log
		.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		User user = null;
		String userId = request.getParameter("userId");
		if ((userId != null) && !userId.equals("")) {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>userId" + userId);
			user = (User) baseManager.getObject(User.class, new Long(userId));	
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + user + "<<<<<<<<<<<<");
		} else {
			user = new User();
			Employee employee = new Employee();
			user.setEmployee(employee);
		}
		log
		.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> end formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return user;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected void onBind(HttpServletRequest request, Object command,
			BindException error) throws Exception {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>> strat onBind");
		User user = (User) command;
		Roles role;
		String option = request.getParameter("option");
		SecurityApplication application = (SecurityApplication) baseManager.getObject(
				SecurityApplication.class, new Long(new Long(option)));
		log.debug(">>>>>>>>>>>>>>>>>>>> application " + application);

		Iterator itr = user.getRoles().iterator();
		log.debug(">>>>>>>>>>.........user.getRoles() "+user.getRoles());
		Set l = new HashSet();
		while (itr.hasNext()) {
			Roles soso = ((Roles) itr.next());
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>..........soso "+soso);

			if (soso.getApplication() == application) {
				l.add(soso);
			}
		}


		user.getRoles().removeAll(l);
		log.debug(">>>>>>>>>>>>>>>>>>>>>> user.getRoles() " + user.getRoles());
		log.debug(">>>>>>>>>>>>>>>>>>>>-----------------------------------<<<<<<<<<<<<<<<<<<<<");
		String[] userRolesList = request.getParameterValues("userRoles");

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>> userRolesList " + userRolesList);
		if (userRolesList != null) {
			log.debug(">>>>>>>>>>>>>>>>> if ");
			for (int i = 0; i < userRolesList.length; i++) {
				log.debug(">>>>>>>>>>>>>>>>>> i " + i);
				role = (Roles) baseManager.getObject(Roles.class, new Long(
						userRolesList[i]));
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>> role " + role);
				user.getRoles().add(role);
			}
		}
		log.debug(">>>>>>>>>>>>>>>>>application.getRoles() "+ application.getRoles());


		log.debug(">>>>user.getEmployee().getIsEmployee()=="+user.getIsEmployee());
		if(user.getIsEmployee().equals(new Boolean(false)))
		{			
			user.getEmployee().setAttendanceCode(null);
			//			user.getEmployee().setAttendanceGroup(null);
		}
		else
		{
			//			if(user.getEmployee().getAttendanceGroup()==null)
			//			{
			//				AttendanceGroup defaultAG=(AttendanceGroup)baseManager.getObject(AttendanceGroup.class,new Long(1));
			//				user.getEmployee().setAttendanceGroup(defaultAG);
			//			}
		}

		log.debug(">>>>>>>>>>>>>>>> end bind");
	}

	public CommonManager getCommonManager() {
		return commonManager;
	}

	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}

}
