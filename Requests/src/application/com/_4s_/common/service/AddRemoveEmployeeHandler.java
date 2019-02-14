package com._4s_.common.service;

import com._4s_.common.model.Employee;

public interface AddRemoveEmployeeHandler {
	public abstract boolean handlePostAddEmployee(Employee employee);
	public abstract boolean handlePostEditEmployee(Employee employee);
	public abstract boolean handlePostRemoveEmployee(Employee employee);
}
