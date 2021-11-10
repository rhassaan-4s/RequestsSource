/**
 * 
 */
package com._4s_.common.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.City;
import com._4s_.common.model.Country;
import com._4s_.common.model.Department;
import com._4s_.common.model.Employee;
import com._4s_.common.service.AddRemoveEmployeeHandler;


/**
 * @author mragab
 * 
 */
@Controller
@RequestMapping("/commonAdminEditEmployee.html")
public class EditEmployeeFormController extends BaseSimpleFormController {

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

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

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

	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {

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

		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< Ending FormBackingObject");

		return employee;
	}

	protected Map referenceData(HttpServletRequest request)
			throws ServletException {
		log.debug("Start referenceData >>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Map model = new HashMap();
		model.put("departments", baseManager.getObjects(Department.class));
		model.put("countries",baseManager.getObjects(Country.class));
		model.put("cities",baseManager.getObjects(City.class));
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< Ending referenceData");

		return model;

	}

}
