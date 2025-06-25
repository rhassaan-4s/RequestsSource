/**
 * 
 */
package com._4s_.common.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.City;
import com._4s_.common.model.Country;
import com._4s_.common.model.Department;
import com._4s_.common.model.Employee;
import com._4s_.common.service.AddRemoveEmployeeHandler;
import com._4s_.common.web.binders.BaseBinder;
import com._4s_.common.web.binders.TimestampBinder;


/**
 * @author mragab
 * 
 */
@Controller
@RequestMapping("/commonAdminEditEmployee.html")
public class EditEmployeeFormController extends BaseSimpleFormController {
	
	@Autowired
	@Qualifier("timestampBinder")
	private TimestampBinder timestampBinder;
	
	@Autowired
	@Qualifier("departmentBinder")
	private BaseBinder departmentBinder;
	
	@Autowired
	@Qualifier("cityBinder")
	private BaseBinder cityBinder;

	/**
	 * Employee Handlers: Post add/update/delete
	 */
	private List employeeHandlers = new ArrayList();
	public List getEmployeeHandlers() {
		return employeeHandlers;
	}
	public void setEmployeeHandlers(List employeeHandlers) {
		this.employeeHandlers = employeeHandlers;
	}

	@Override
	public void initBinder(HttpServletRequest request,WebDataBinder binder) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Starting init binder: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		super.initBinder(request,binder);
		binder.registerCustomEditor(TimestampBinder.class, timestampBinder);
		binder.registerCustomEditor(Department.class, departmentBinder);
		binder.registerCustomEditor(City.class, cityBinder);
	}
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(HttpServletRequest request,
			@Valid @ModelAttribute("employee") Employee command,
			BindingResult result, SessionStatus status,Model model) {

		log.debug("Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		Employee employee = (Employee) command;
		
		boolean isNew = true;
		if (employee.getId()!=null) {
			isNew = false;
		}
		
		//baseManager.saveObject(employee.getAccount());
		//baseManager.saveObject(employee);

		Iterator itr = employeeHandlers.iterator();
		AddRemoveEmployeeHandler anEmployeeHandler = null;
		
		if (isNew) {
			while (itr.hasNext()) {
				anEmployeeHandler = (AddRemoveEmployeeHandler)itr.next();
				anEmployeeHandler.handlePostAddEmployee(employee);
			}
			
		} else {
			while (itr.hasNext()) {
				anEmployeeHandler = (AddRemoveEmployeeHandler)itr.next();
				anEmployeeHandler.handlePostEditEmployee(employee);
			}
		}
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit");

		return new ModelAndView(new RedirectView("commonAdminEmployees.html"));

	}

	protected void onBind(HttpServletRequest request, Object command)
		throws Exception {
		Employee employee = (Employee) command;
		//Role role = (Role)baseManager.getObject(Role.class,new Long(2));
		//log.debug(">>>>>>>>>>>>>>>>>>>>>> role "+role);
		//employee.getAccount().getRoles().add(role);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request){

		log.debug("Start formBackingObject >>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Employee employee = new Employee();
	//	Account account = new Account();
		//employee.setAccount(account);

		String employeeId = request.getParameter("employeeId");
		log.debug(">>>>>>>>>>> employee Id :" + employeeId);

		if ((employeeId != null) && (employeeId.length() > 0)) {
			Object obj = baseManager.getObject(Employee.class, new Long(
					employeeId));
			if (obj != null) {
				employee = (Employee) obj;
			} else {
				log.warn("!!! No object found for employeeId:" + employeeId);
			}
		}

		model.addAttribute("employee", employee);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< Ending FormBackingObject");

		return "CommonAdminEditEmployee";
	}

	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) {
		log.debug("Start referenceData >>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Map model = new HashMap();
		model.put("departments", baseManager.getObjects(Department.class));
		model.put("countries",baseManager.getObjects(Country.class));
		model.put("cities",baseManager.getObjects(City.class));
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< Ending referenceData");

		return model;

	}

}
