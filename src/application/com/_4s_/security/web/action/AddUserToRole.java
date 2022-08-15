package com._4s_.security.web.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com._4s_.attendance.web.binders.AttendanceDepartmentBinder;
import com._4s_.common.model.Branch;
import com._4s_.common.model.City;
import com._4s_.common.model.Department;
import com._4s_.common.model.Employee;
import com._4s_.common.service.CommonManager;
import com._4s_.common.web.action.BaseFormControllerInterface;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.common.web.binders.DomainObjectBinder;
import com._4s_.i18n.model.MyLocale;
import com._4s_.i18n.service.MessageManager;
import com._4s_.security.model.Roles;
import com._4s_.security.model.SecurityApplication;
import com._4s_.security.model.User;
import com._4s_.security.web.validators.ValidateUser;

@Controller
@RequestMapping("/addUserToRole.html")
public class AddUserToRole  extends BaseSimpleFormController implements BaseFormControllerInterface {

	@Autowired
	private MessageManager mgr;

	@Autowired
	public CommonManager commonManager;
	
	private ValidateUser userValidator;
	
	private List<SecurityApplication> applications = new ArrayList<SecurityApplication>();
	
	@Autowired
	@Qualifier("roleBinder")
	private DomainObjectBinder roleBinder;
	@Autowired
	@Qualifier("branchBinder")
	private DomainObjectBinder branchBinder;
	@Autowired
	@Qualifier("departmentBinder")
	private AttendanceDepartmentBinder departmentBinder;
	@Autowired
	@Qualifier("cityBinder")
	private DomainObjectBinder cityBinder;
	@Autowired
	@Qualifier("securityApplicationBinder")
	private DomainObjectBinder securityApplicationBinder;
	
	public MessageManager getMgr() {
		return mgr;
	}
	public void setMgr(MessageManager mgr) {
		this.mgr = mgr;
	}
	public CommonManager getCommonManager() {
		return commonManager;
	}
	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}
	public DomainObjectBinder getRoleBinder() {
		return roleBinder;
	}
	public void setRoleBinder(DomainObjectBinder roleBinder) {
		this.roleBinder = roleBinder;
	}
	public DomainObjectBinder getBranchBinder() {
		return branchBinder;
	}
	public void setBranchBinder(DomainObjectBinder branchBinder) {
		this.branchBinder = branchBinder;
	}
	public AttendanceDepartmentBinder getDepartmentBinder() {
		return departmentBinder;
	}
	public void setDepartmentBinder(AttendanceDepartmentBinder departmentBinder) {
		this.departmentBinder = departmentBinder;
	}
	public DomainObjectBinder getCityBinder() {
		return cityBinder;
	}
	public void setCityBinder(DomainObjectBinder cityBinder) {
		this.cityBinder = cityBinder;
	}
	public DomainObjectBinder getSecurityApplicationBinder() {
		return securityApplicationBinder;
	}
	public void setSecurityApplicationBinder(DomainObjectBinder securityApplicationBinder) {
		this.securityApplicationBinder = securityApplicationBinder;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Autowired
	public AddUserToRole(ValidateUser userValidator){
		this.userValidator = userValidator;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////populating data//////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	@ModelAttribute("applications")
	public List<SecurityApplication> populateApplications() {
		applications = (List<SecurityApplication>)baseManager.getObjectsByParameter(SecurityApplication.class, "is_active", new Boolean(true));
		return applications;
	}
	//@GetMapping("/addUserToRole.html")
	@ModelAttribute("roles")
	@ResponseBody
	public List<Roles> populateRoles(@RequestParam(required = false) String option,@ModelAttribute("user") User user) {
		List<Roles> roles = new ArrayList<Roles>();
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
		return roles;
	}
	@ModelAttribute("my")
//	@GetMapping("/addUserToRole.html")
	@ResponseBody
	public String populateMy(@RequestParam(required=false) String my) {
		return my;
	}
	@ModelAttribute("userId")
//	@GetMapping("/addUserToRole.html")
	@ResponseBody
	public String populateUserId(@RequestParam(required=false) String userId) {
		return userId;
	}
	@ModelAttribute("departments")
	public List<Department> populateDepartments() {
		List<Department> departments = (List<Department>)baseManager.getObjects(Department.class);
		return departments;
	}
	@ModelAttribute("cities")
	public List<City> populateCities() {
		List<City> cities = (List<City>)commonManager.getCitiesByCountry(new Long(1));;
		return cities;
	}
	@ModelAttribute("branches")
	public List<Branch> populateBranches() {
		List<Branch> branches = (List<Branch>)baseManager.getObjects(Branch.class);
		return branches;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
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
		return "addUserToRole";
	}

	@Override
	public void initBinder(WebDataBinder binder) {
		// TODO Auto-generated method stub
		super.initBinder(binder);
		binder.registerCustomEditor(roleBinder.getBindedClass(), null, roleBinder);
		binder.registerCustomEditor(branchBinder.getBindedClass(), null, branchBinder);
		binder.registerCustomEditor(cityBinder.getBindedClass(), null, cityBinder);
		binder.registerCustomEditor(departmentBinder.getBindedClass(), null, departmentBinder);
		binder.registerCustomEditor(securityApplicationBinder.getBindedClass(), null, securityApplicationBinder);
	}
	
	@Override
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("user") Object command, HttpServletRequest request, HttpServletResponse response,
			BindingResult result, SessionStatus status) {
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
		log
		.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		String my = request.getParameter("my");
		if(my == null || my.equals("")){
			my = option;
		}

		application = (SecurityApplication) baseManager.getObject(SecurityApplication.class, new Long(my));
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>application " + application);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>my " + my);
		
		MyLocale myLocale = mgr.getDefault();
		role = (Roles)baseManager.getObject(Roles.class,new Long(5));
		List list = new ArrayList();
		list.add(role);
		baseManager.saveObject(user);
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
		if(user.getLanguage() == null){
			user.setLanguage(myLocale);
		}
		user.setLanguage(myLocale);
		user.getEmployee().setUser(user);

		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return ("addUserToRole.html?userId="+ userId+"&option="+option);
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////
//protected ModelAndView onSubmit(HttpServletRequest request,
//			HttpServletResponse response, @ModelAttribute("user") final User command, BindException error)
//	throws Exception {
//		// TODO Auto-generated method stub
//		log
//		.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		User user = (User) command;
//
//
//		String my = request.getParameter("my");
//		String option = request.getParameter("option");
//		if(my == null || my.equals("")){
//			my = option;
//		}
//
//		SecurityApplication application = (SecurityApplication) baseManager.getObject(SecurityApplication.class, new Long(my));
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>application " + application);
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>my " + my);
//		
//		MyLocale myLocale = mgr.getDefault();
//		Roles role = (Roles)baseManager.getObject(Roles.class,new Long(5));
//		List list = new ArrayList();
//		list.add(role);
//		baseManager.saveObject(user);
//		//			baseManager.saveObject(communicator);
//		//			if(request.getParameterValues("userRoles") == null || request.getParameterValues("userRoles").equals("")){
//		//				user.set(list);
//		//			}
//		if(user.getRoles().isEmpty()){
//			user.set(list);
//			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//			log.debug(">>>>>>>>>>>>>>>>>>user.getRoles()"+user.getRoles()+">>>>>>>>>>>>>>>>>>");
//			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		}
//		Long userId = user.getId();
//		if(user.getDefaultApplication() == null){
//			user.setDefaultApplication(application);
//		}
//		if(request.getParameter("myDefaultApplication") != null && !request.getParameter("myDefaultApplication").equals("")){
//			user.setDefaultApplication(application);
//		}
//		log.debug("................"+request.getParameter("canSeeAllStore"));
//		if(request.getParameter("canSeeAllStore") != null && !request.getParameter("canSeeAllStore").equals("") && request.getParameter("canSeeAllStore").equals("true")){
//			user.getEmployee().setCanSeeAllStore(true);
//		}else{
//			user.getEmployee().setCanSeeAllStore(false);
//		}
//		//user.setDefaultApplication(application);
//		if(user.getLanguage() == null){
//			user.setLanguage(myLocale);
//		}
//		user.setLanguage(myLocale);
//		user.getEmployee().setUser(user);
//
//		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//		return new ModelAndView(new RedirectView("addUserToRole.html?userId="+ userId+"&option="+option));
//	}
//	
//	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
//	protected Map referenceData(HttpServletRequest request, Object command,
//			Errors error) throws Exception {
//		// TODO Auto-generated method stub
//		log
//		.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< Start referenceData: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//		User user = (User) command;
//
//		String userId=request.getParameter("userId");
//		List applications = new ArrayList();
//		applications = baseManager.getObjectsByParameter(SecurityApplication.class, "is_active", new Boolean(true));
//		List roles = new ArrayList();
//		String option = request.getParameter("option");
//
//		SecurityApplication application;
//
//		if (option == null || option.equals("")) {
//			if(user.getDefaultApplication() == null) {
//				application = (SecurityApplication)(applications.get(0));
//				option = application.getId().toString();
//			}
//			else {
//				application = user.getDefaultApplication();
//				option = user.getDefaultApplication().getId().toString();
//			}
//		}
//		else {
//			application = (SecurityApplication) baseManager.getObject(
//					SecurityApplication.class, new Long(option));
//		}
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>application " + application);
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>option " + option);
//		roles = application.getRoles();
//		int y = Integer.parseInt(option);
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>roles " + roles);
//
//		String my = request.getParameter("my");
//		List departments = baseManager.getObjects(Department.class);
//		List cities = commonManager.getCitiesByCountry(new Long(1));
//
////		List branches = commonManager.getBranchesRelatedByCompany(new Long(1)); // SCFHS
//		List branches= commonManager.getObjects(Branch.class);
//		Map model = new HashMap();
//		model.put("applications", applications);
//		model.put("roles", roles);
//		model.put("option", y);
//		model.put("userId", userId);
//		model.put("userId",userId);
//		model.put("my",my);
//		model.put("departments",departments);
//		model.put("cities",cities);
//		model.put("branches",branches);
//
//		log
//		.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End referenceData: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//		return model;
//	}
//	////////////////////////////////////////////////////////////////////////////////////////////////////////
//	@RequestMapping(value = "/addUserToRole.html", method = RequestMethod.GET)
//	protected Object formBackingObject(HttpServletRequest request)
//	throws Exception {
//		// TODO Auto-generated method stub
//		log
//		.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		User user = null;
//		String userId = request.getParameter("userId");
//		if ((userId != null) && !userId.equals("")) {
//			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>userId" + userId);
//			user = (User) baseManager.getObject(User.class, new Long(userId));	
//			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + user + "<<<<<<<<<<<<");
//		} else {
//			user = new User();
//			Employee employee = new Employee();
//			user.setEmployee(employee);
//		}
//		log
//		.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> end formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		return user;
//	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
//	protected void onBind(HttpServletRequest request, Object command,
//			BindException error) throws Exception {
//		// TODO Auto-generated method stub
//		log.debug(">>>>>>>>>>>>>>>> strat onBind");
//		User user = (User) command;
//		Roles role;
//		String option = request.getParameter("option");
//		SecurityApplication application = (SecurityApplication) baseManager.getObject(
//				SecurityApplication.class, new Long(new Long(option)));
//		log.debug(">>>>>>>>>>>>>>>>>>>> application " + application);
//
//		Iterator itr = user.getRoles().iterator();
//		log.debug(">>>>>>>>>>.........user.getRoles() "+user.getRoles());
//		Set l = new HashSet();
//		while (itr.hasNext()) {
//			Roles soso = ((Roles) itr.next());
//			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>..........soso "+soso);
//
//			if (soso.getApplication() == application) {
//				l.add(soso);
//			}
//		}
//
//
//		user.getRoles().removeAll(l);
//		log.debug(">>>>>>>>>>>>>>>>>>>>>> user.getRoles() " + user.getRoles());
//		log.debug(">>>>>>>>>>>>>>>>>>>>-----------------------------------<<<<<<<<<<<<<<<<<<<<");
//		String[] userRolesList = request.getParameterValues("userRoles");
//
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>> userRolesList " + userRolesList);
//		if (userRolesList != null) {
//			log.debug(">>>>>>>>>>>>>>>>> if ");
//			for (int i = 0; i < userRolesList.length; i++) {
//				log.debug(">>>>>>>>>>>>>>>>>> i " + i);
//				role = (Roles) baseManager.getObject(Roles.class, new Long(
//						userRolesList[i]));
//				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>> role " + role);
//				user.getRoles().add(role);
//			}
//		}
//		log.debug(">>>>>>>>>>>>>>>>>application.getRoles() "+ application.getRoles());
//
//
//		log.debug(">>>>user.getEmployee().getIsEmployee()=="+user.getIsEmployee());
//		if(user.getIsEmployee().equals(new Boolean(false)))
//		{			
//			user.getEmployee().setAttendanceCode(null);
//			//			user.getEmployee().setAttendanceGroup(null);
//		}
//		else
//		{
//			//			if(user.getEmployee().getAttendanceGroup()==null)
//			//			{
//			//				AttendanceGroup defaultAG=(AttendanceGroup)baseManager.getObject(AttendanceGroup.class,new Long(1));
//			//				user.getEmployee().setAttendanceGroup(defaultAG);
//			//			}
//		}
//
//		log.debug(">>>>>>>>>>>>>>>> end bind");
//	}
//
//	

}
