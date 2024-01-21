package com._4s_.requestsApproval.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.EqualsBuilder;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.model.Employee;

@Entity//(access=AccessType.FIELD)
@Table(name="login_users_requests" ,uniqueConstraints= {@UniqueConstraint(columnNames= {"empCode","request_date","from_date","request_type"})})
public class LoginUsersRequests implements Auditable,Serializable  {
	public LoginUsersRequests() {
		// TODO Auto-generated constructor stub
	}
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="loginUsersRequests_seq")
	@SequenceGenerator(name="loginUsersRequests_seq",sequenceName="loginUsersRequests_seq", allocationSize = 1)//(generate=GeneratorType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name="login_user")
	private LoginUsers login_user;

	@ManyToOne
	@JoinColumn(name="request_type")
	private RequestTypes request_id;

	private String empCode;
	private Date request_date;
	private Date from_date;
	private Date to_date;
	private Date period_from;
	private Date period_to;
	private Long approved;
	private Long posted;
	private Long applicable;
	private Long payed;
	private String notes;
	private String leave_type;
	private String leave_effect;
	private String requestNumber;
	private Double withdrawDays;
	private Long vacCredit;
	private Date altDate;
	@ManyToOne
	@JoinColumn(name="vacation")
	private Vacation vacation;

	@Transient
	private Date vac_period_from;

	@Transient
	private Date vac_period_to;
	private Double longitude = new Double(0);

	private String reply;
	private Double latitude = new Double(0);

	private Integer inputType = 0; //0: attendance from application sign in and out
	//1: attendance request (for those who forgot to attend)
	//2: Mobile attendance

	private String locationAddress = null;

	private Boolean isInsideCompany = true;

	private Date from_date_history;

	@ManyToOne
	@JoinColumn (name="managerModifiedDate")
	private Employee managerModifiedDate;

	public Employee getManagerModifiedDate() {
		return managerModifiedDate;
	}
	public void setManagerModifiedDate(Employee managerModifiedDate) {
		this.managerModifiedDate = managerModifiedDate;
	}
	public Date getFrom_date_history() {
		return from_date_history;
	}
	public void setFrom_date_history(Date from_date_history) {
		this.from_date_history = from_date_history;
	}
	public String getLocationAddress() {
		return locationAddress;
	}
	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}
	public Boolean getIsInsideCompany() {
		return isInsideCompany;
	}
	public void setIsInsideCompany(Boolean isInsideCompany) {
		this.isInsideCompany = isInsideCompany;
	}
	public Integer getInputType() {
		return inputType;
	}
	public void setInputType(Integer inputType) {
		this.inputType = inputType;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LoginUsers getLogin_user() {
		return login_user;
	}
	public void setLogin_user(LoginUsers login_user) {
		this.login_user = login_user;
	}
	public RequestTypes getRequest_id() {
		return request_id;
	}
	public void setRequest_id(RequestTypes request_id) {
		this.request_id = request_id;
	}
	public Date getRequest_date() {
		return request_date;
	}
	public void setRequest_date(Date request_date) {
		this.request_date = request_date;
	}
	public Date getPeriod_from() {
		return period_from;
	}
	public void setPeriod_from(Date period_from) {
		this.period_from = period_from;
	}
	public Date getPeriod_to() {
		return period_to;
	}
	public void setPeriod_to(Date period_to) {
		this.period_to = period_to;
	}

	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "LoginUsersRequests Addition";
	}
	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}
	public Date getFrom_date() {
		return from_date;
	}
	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}
	public Date getTo_date() {
		return to_date;
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
	public Long getPayed() {
		return payed;
	}
	public void setPayed(Long payed) {
		this.payed = payed;
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
	public void setVacation(Vacation vacation) {
		this.vacation = vacation;
	}
	public Vacation getVacation() {
		return vacation;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getReply() {
		return reply;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof LoginUsersRequests)) {
			return false;
		}
		LoginUsersRequests rhs = (LoginUsersRequests) o;
		return new EqualsBuilder()
				.append(this.request_date, rhs.getRequest_date())
				.append(this.from_date, rhs.getFrom_date())
				.append(this.request_id, rhs.getRequest_id())
				.append(this.empCode, rhs.getEmpCode())
				.isEquals();
	}
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	public String getRequestNumber() {
		return requestNumber;
	}
	public void setWithdrawDays(Double withdrawDays) {
		this.withdrawDays = withdrawDays;
	}
	public Double getWithdrawDays() {
		return withdrawDays;
	}
	public void setVacCredit(Long vacCredit) {
		this.vacCredit = vacCredit;
	}
	public Long getVacCredit() {
		return vacCredit;
	}
	public Date getVac_period_from() {
		return vac_period_from;
	}
	public void setVac_period_from(Date vac_period_from) {
		this.vac_period_from = vac_period_from;
	}
	public Date getVac_period_to() {
		return vac_period_to;
	}
	public void setVac_period_to(Date vac_period_to) {
		this.vac_period_to = vac_period_to;
	}
	public Date getAltDate() {
		return altDate;
	}
	public void setAltDate(Date altDate) {
		this.altDate = altDate;
	}



}
