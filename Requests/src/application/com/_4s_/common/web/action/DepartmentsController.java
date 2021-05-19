package com._4s_.common.web.action;

import com._4s_.common.model.Department;

public class DepartmentsController extends BaseController {

	public DepartmentsController() {
		super();
		setCommandClass(Department.class);
		setListViewName("CommonAdminDepartments");
	}

}
