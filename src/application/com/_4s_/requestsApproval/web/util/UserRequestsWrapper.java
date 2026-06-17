package com._4s_.requestsApproval.web.util;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class UserRequestsWrapper {
	private Integer rnum = null;
	private long id ;
	private long request_type;
	private long login_user;
	private String empCode = null;
	private Timestamp request_date = null;
	private Timestamp from_date = null;
	private Timestamp to_date = null;
	private Timestamp period_from = null;
	private Timestamp period_to = null;
	private String notes = null;
	private String leave_type = null;
	private String leave_effect = null;
	private Long payed = null;
	private Long approved = null;
	private Long posted = null;
	private Long applicable = null;
	private String vacation = null;
	private String reply = null;
	private String requestNumber = null;
	private Long withdrawDays = null;
	private Long vacCredit = null;
	private String str_request_date = null;
	private String str_from_date = null;
	private String str_to_date = null;
	private String str_period_from = null;
	private String str_period_to = null;
	private String altDate = null;
	private Double longitude = null;
	private Double latitude = null;
	private Long inputType = null;
	private String locationAddress = null;
	private String isInsideCompany = null;
	private Timestamp from_date_history = null;
	private Long managerModifiedDat = null;
	private String description = null;
	private String vacName = null;
	private Long empId = null;
	private String employeeCode = null;
	private String name = null;
	
	public UserRequestsWrapper(Object rnum, Object id, Object request_type, Object login_user, Object empcode,
			Object request_date, Object from_date, Object to_date, Object period_from, Object period_to, Object notes,
			Object leave_type, Object leave_effect, Object payed, Object approved, Object posted, Object applicable,
			Object vacation, Object reply, Object requestnumber, Object withdrawdays, Object vaccredit,
			Object str_request_date, Object str_from_date, Object str_to_date, Object str_period_from,
			Object str_period_to, Object altdate, Object longitude, Object latitude, Object inputtype,
			Object locationaddress, Object isinsidecompany, Object from_date_history, Object managermodifieddat,
			Object description, Object vacname, Object empid, Object employeecode, Object name) {
		super();
		this.rnum = ((BigDecimal) rnum).intValue();
		this.id = ((BigDecimal) id).longValue();
		this.request_type = ((BigDecimal) request_type).longValue();
		this.login_user = ((BigDecimal) login_user).longValue();
		this.empCode = (String) empcode;
		this.request_date = (Timestamp) request_date;
		this.from_date = (Timestamp) from_date;
		this.to_date = (Timestamp) to_date;
		this.period_from = (Timestamp) period_from;
		this.period_to = (Timestamp) period_to;
		this.notes = (String) notes;
		this.leave_type = (String) leave_type;
		this.leave_effect = (String) leave_effect;
		if (payed != null) {
			this.payed = ((BigDecimal) payed).longValue();
		} else {
			this.payed = null;
		}
		this.approved = ((BigDecimal) approved).longValue();
		this.posted = ((BigDecimal) posted).longValue();
		this.applicable = ((BigDecimal) applicable).longValue();
		if (vacation != null) {
			this.vacation = (String) vacation;
		} else {
			this.vacation = null;
		}
		this.reply = (String) reply;
		this.requestNumber = (String) requestnumber;
		if (withdrawdays != null) {
			this.withdrawDays = ((BigDecimal) withdrawdays).longValue();
		} else {
			this.withdrawDays = null;
		}
		if (vaccredit != null) {
			this.vacCredit = ((BigDecimal) vaccredit).longValue();
		} else {
			this.vacCredit = null;
		}
		this.str_request_date = (String) str_request_date;
		System.out.println("*********str_from_date in wrapper is: "+str_from_date + "**********");
		if (str_from_date != null) {
			this.str_from_date = (String) str_from_date;
		}
		this.str_to_date = (String) str_to_date;
		this.str_period_from = (String) str_period_from;
		this.str_period_to = (String) str_period_to;
		this.altDate = (String) altdate;
		if (longitude != null) {
			this.longitude = ((BigDecimal) longitude).doubleValue();
		} else {
			this.longitude = null;
		}
		if (latitude != null) {
			this.latitude = ((BigDecimal) latitude).doubleValue();
		} else {
			this.latitude = null;
		}
		if (inputtype != null) {
			this.inputType = ((BigDecimal) inputtype).longValue();
		} else {
			this.inputType = null;
		}
		this.locationAddress = (String) locationaddress;
		this.isInsideCompany = (String) isinsidecompany;
		this.from_date_history = (Timestamp) from_date_history;
		if (managermodifieddat!=null) {
			this.managerModifiedDat = ((BigDecimal) managermodifieddat).longValue();
		} else {
			this.managerModifiedDat = null;
		}
		this.description = (String) description;
		this.vacName = (String) vacname;
		this.empId = ((BigDecimal) empid).longValue();
		this.employeeCode = (String) employeecode;
		this.name = (String) name;
	}

	public Integer getRnum() {
		return rnum;
	}

	public void setRnum(Integer rnum) {
		this.rnum = rnum;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRequest_type() {
		return request_type;
	}

	public void setRequest_type(long request_type) {
		this.request_type = request_type;
	}

	public long getLogin_user() {
		return login_user;
	}

	public void setLogin_user(long login_user) {
		this.login_user = login_user;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empcode) {
		this.empCode = empcode;
	}

	public Timestamp getRequest_date() {
		return request_date;
	}

	public void setRequest_date(Timestamp request_date) {
		this.request_date = request_date;
	}

	public Timestamp getFrom_date() {
		return from_date;
	}

	public void setFrom_date(Timestamp from_date) {
		this.from_date = from_date;
	}

	public Timestamp getTo_date() {
		return to_date;
	}

	public void setTo_date(Timestamp to_date) {
		this.to_date = to_date;
	}

	public Timestamp getPeriod_from() {
		return period_from;
	}

	public void setPeriod_from(Timestamp period_from) {
		this.period_from = period_from;
	}

	public Timestamp getPeriod_to() {
		return period_to;
	}

	public void setPeriod_to(Timestamp period_to) {
		this.period_to = period_to;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getLeave_type() {
		return leave_type;
	}

	public void setLeave_type(String leave_type) {
		this.leave_type = leave_type;
	}

	public String getLeave_effect() {
		return leave_effect;
	}

	public void setLeave_effect(String leave_effect) {
		this.leave_effect = leave_effect;
	}

	public Long getPayed() {
		return payed;
	}

	public void setPayed(Long payed) {
		this.payed = payed;
	}

	public Long getApproved() {
		return approved;
	}

	public void setApproved(Long approved) {
		this.approved = approved;
	}

	public Long getPosted() {
		return posted;
	}

	public void setPosted(Long posted) {
		this.posted = posted;
	}

	public Long getApplicable() {
		return applicable;
	}

	public void setApplicable(Long applicable) {
		this.applicable = applicable;
	}

	public String getVacation() {
		return vacation;
	}

	public void setVacation(String vacation) {
		this.vacation = vacation;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public Long getWithdrawDays() {
		return withdrawDays;
	}

	public void setWithdrawDays(Long withdrawDays) {
		this.withdrawDays = withdrawDays;
	}

	public Long getVacCredit() {
		return vacCredit;
	}

	public void setVacCredit(Long vacCredit) {
		this.vacCredit = vacCredit;
	}

	public String getAltDate() {
		return altDate;
	}

	public void setAltDate(String altDate) {
		this.altDate = altDate;
	}

	public Long getInputType() {
		return inputType;
	}

	public void setInputType(Long inputType) {
		this.inputType = inputType;
	}

	public String getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	public String getIsInsideCompany() {
		return isInsideCompany;
	}

	public void setIsInsideCompany(String isInsideCompany) {
		this.isInsideCompany = isInsideCompany;
	}

	public Long getManagerModifiedDat() {
		return managerModifiedDat;
	}

	public void setManagerModifiedDat(Long managerModifiedDat) {
		this.managerModifiedDat = managerModifiedDat;
	}

	public String getVacName() {
		return vacName;
	}

	public void setVacName(String vacName) {
		this.vacName = vacName;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getStr_request_date() {
		return str_request_date;
	}

	public void setStr_request_date(String str_request_date) {
		this.str_request_date = str_request_date;
	}

	public String getStr_from_date() {
		return str_from_date;
	}

	public void setStr_from_date(String str_from_date) {
		this.str_from_date = str_from_date;
	}

	public String getStr_to_date() {
		return str_to_date;
	}

	public void setStr_to_date(String str_to_date) {
		this.str_to_date = str_to_date;
	}

	public String getStr_period_from() {
		return str_period_from;
	}

	public void setStr_period_from(String str_period_from) {
		this.str_period_from = str_period_from;
	}

	public String getStr_period_to() {
		return str_period_to;
	}

	public void setStr_period_to(String str_period_to) {
		this.str_period_to = str_period_to;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Timestamp getFrom_date_history() {
		return from_date_history;
	}

	public void setFrom_date_history(Timestamp from_date_history) {
		this.from_date_history = from_date_history;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
