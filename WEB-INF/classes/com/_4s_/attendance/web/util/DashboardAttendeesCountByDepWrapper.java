package com._4s_.attendance.web.util;

import java.math.BigDecimal;

public class DashboardAttendeesCountByDepWrapper {
	private BigDecimal workers;
	private BigDecimal attendance;
	private String locationCode;
	private String locationName;
	public DashboardAttendeesCountByDepWrapper(Object workers, Object attendance, Object locationCode,
			Object locationName) {
		super();
		this.workers = (BigDecimal)workers;
		this.attendance = (BigDecimal)attendance;
		this.locationCode = (String)locationCode;
		this.locationName = (String)locationName;
	}
	public BigDecimal getWorkers() {
		return workers;
	}
	public void setWorkers(BigDecimal workers) {
		this.workers = workers;
	}
	public BigDecimal getAttendance() {
		return attendance;
	}
	public void setAttendance(BigDecimal attendance) {
		this.attendance = attendance;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	
	
	
	
}
