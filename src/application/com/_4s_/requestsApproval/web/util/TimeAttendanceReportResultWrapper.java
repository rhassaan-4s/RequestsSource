package com._4s_.requestsApproval.web.util;

import java.math.BigDecimal;
import java.sql.Timestamp;


public class TimeAttendanceReportResultWrapper {
	private Timestamp dd;//date
	private String empCode;
	private String fName;//firstName
	private Timestamp attendance_date;
	
	private String attendance_time;
	private String attendance_type;
	private String approval;
	private String input_type;
	private String longitude;
	private String latitude;
	public TimeAttendanceReportResultWrapper(Object dd, Object empCode, Object fName,Object attendance_date, Object attendance_time,
			Object attendance_type, Object approval, Object input_type, Object longitude, Object latitude) {
		super();
		this.dd = (Timestamp)dd;
		this.empCode = (String)empCode;
		this.fName = (String)fName;
		this.attendance_date = (Timestamp)attendance_date;
		this.attendance_time = (String)attendance_time;
		this.attendance_type = (String)attendance_type;
		if (approval !=null) {
			this.approval = (String)approval;
		}
		this.input_type = (String)input_type;
		if (longitude!=null) {
			this.longitude = ((BigDecimal)longitude).toPlainString();
		}
		if (latitude!=null) {
			this.latitude = ((BigDecimal)latitude).toPlainString();
		}
	}
	
	public Timestamp getAttendance_date() {
		return attendance_date;
	}


	public void setAttendance_date(Timestamp attendance_date) {
		this.attendance_date = attendance_date;
	}
	public Timestamp getDd() {
		return dd;
	}
	public void setDd(Timestamp dd) {
		this.dd = dd;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getAttendance_time() {
		return attendance_time;
	}
	public void setAttendance_time(String attendance_time) {
		this.attendance_time = attendance_time;
	}
	public String getAttendance_type() {
		return attendance_type;
	}
	public void setAttendance_type(String attendance_type) {
		this.attendance_type = attendance_type;
	}
	public String getApproval() {
		return approval;
	}
	public void setApproval(String approval) {
		this.approval = approval;
	}
	public String getInput_type() {
		return input_type;
	}
	public void setInput_type(String input_type) {
		this.input_type = input_type;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	
	
}
