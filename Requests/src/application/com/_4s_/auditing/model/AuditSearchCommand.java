package com._4s_.auditing.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com._4s_.security.model.User;



public class AuditSearchCommand {
	private User user;

	private Date fromDate;

	private Date toDate;

	private String action;

	private String className;

	private String classDetail;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	

	

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	

	public String getClassDetail() {
		return classDetail;
	}

	public void setClassDetail(String classDetail) {
		this.classDetail = classDetail;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

}
