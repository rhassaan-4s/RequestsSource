package com._4s_.attendance.web.util;

import java.math.BigDecimal;

public class DashboardReqTypeCountsWrapper {
	private BigDecimal empCount;
	private String reqName;
	
	
	public DashboardReqTypeCountsWrapper(Object empCount, Object reqName) {
		super();
		this.empCount = (BigDecimal)empCount;
		this.reqName = (String)reqName;
	}
	public BigDecimal getEmpCount() {
		return empCount;
	}
	public void setEmpCount(BigDecimal empCount) {
		this.empCount = empCount;
	}
	public String getReqName() {
		return reqName;
	}
	public void setReqName(String reqName) {
		this.reqName = reqName;
	}
	
	
}
