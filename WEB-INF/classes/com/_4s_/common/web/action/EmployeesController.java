package com._4s_.common.web.action;

import com._4s_.common.model.Employee;

public class EmployeesController extends BaseController {

	public EmployeesController() {
		super();
		setCommandClass(Employee.class);
		setListViewName("CommonAdminEmployees");
	}

}
