package com._4s_.requestsApproval.web.util;

import java.math.BigDecimal;
import java.sql.Timestamp;


public class TimeAttendanceWrapper {
	private Timestamp minDate;//date
	private Timestamp maxDate;//date
	private String emp;
	private String fName;//firstName
	
	public TimeAttendanceWrapper(Object minDate, Object maxDate, Object emp,Object fName) {
		super();
		this.minDate = (Timestamp)minDate;
		this.maxDate = (Timestamp)maxDate;
		this.emp = (String)emp;
		this.fName = (String)fName;
	}

	public Timestamp getMinDate() {
		return minDate;
	}

	public void setMinDate(Timestamp minDate) {
		this.minDate = minDate;
	}

	public Timestamp getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Timestamp maxDate) {
		this.maxDate = maxDate;
	}

	public String getEmp() {
		return emp;
	}

	public void setEmp(String emp) {
		this.emp = emp;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}
	
	
	
	
}
