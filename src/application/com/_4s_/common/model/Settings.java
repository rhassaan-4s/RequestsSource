package com._4s_.common.model;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com._4s_.auditing.model.Auditable;

@Entity
@Table(name="common_settings")
public class Settings  implements Serializable,Auditable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn (name="company")
	private Company company;
	
	private Boolean accessLevelsCheckDate=false;
	private Boolean attendanceRequestEn;
	private Boolean empRequestCheckDate=false;
	private Boolean annualVacBalDaysEnabled;
//	private String server;//
//	private String service;//
//	private String username;//
//	private String password;//
	private Boolean addNewData;
	private Boolean checkPostedRequests;
	private Boolean empRequestTypeException;
	private Boolean tAttRepWithHrsMin=false;
	private Boolean specialVacExcep;
	private Boolean creatingMail;
	private String loginUrl;//
	private Boolean vacLimitProblem;
	private Boolean vacationRequestExcep;
	private Boolean reqPeriodDate;
	private Boolean withdrawDaysQuartPolicy;
	private Boolean notesValidation;
	private Boolean withoutSalPeriodValidation;
	private Boolean fromToRequestVald;
	private Boolean withoutSalaryVacEnabled;
	private Boolean periodFromToEnabled;
	private Boolean footerCopyrightsEnabled;
	private Boolean companyRulesEn;
	private Blob companyLogoHeader;
	private String companyLogoHeaderName;
	private Integer maxEmp;//
	
	private String mailMgr;//
	private String mailPass;//
	private String SMTPServer;//
	private String portServer;//
	
	private Boolean execuseEnabled;

	private Integer salaryFromDay;//
	
	private Integer requestsDeadline;//
	
	private Boolean automaticSignInOut;
	
	private Boolean webAttendanceAppEnabled;
	
	private Boolean sqlServerConnectionEnabled;
	
	private Boolean desktopHrApplication;
	
//	private String dbPortNo;
	
	
	////////////////Android Settings////////////////////
	
	public Boolean getDesktopHrApplication() {
		if (desktopHrApplication==null) {
			return false;
		} else {
			return desktopHrApplication;
		}
	}

	public void setDesktopHrApplication(Boolean desktopHrApplication) {
		this.desktopHrApplication = desktopHrApplication;
	}

	public Boolean getSqlServerConnectionEnabled() {
		if (sqlServerConnectionEnabled==null) {
			return false;
		} else {
			return sqlServerConnectionEnabled;
		}
	}

	public void setSqlServerConnectionEnabled(Boolean sqlServerConnectionEnabled) {
		this.sqlServerConnectionEnabled = sqlServerConnectionEnabled;
	}

//	public String getDbPortNo() {
//		return dbPortNo;
//	}
//
//	public void setDbPortNo(String dbPortNo) {
//		this.dbPortNo = dbPortNo;
//	}

	public Boolean getWebAttendanceAppEnabled() {
		if (webAttendanceAppEnabled==null) {
			return false;
		} else {
			return webAttendanceAppEnabled;
		}
	}

	public void setWebAttendanceAppEnabled(Boolean webAttendanceAppEnabled) {
		this.webAttendanceAppEnabled = webAttendanceAppEnabled;
	}

	private Boolean androidAttAutomaticApproval;
	
	private Float locationAccuracy;
	
	private Float locationAccuracy2;
	
	private Boolean managerCanModifyAttendance;
	
	private Boolean isTimesheetEnabled;
	
	public Boolean getIsTimesheetEnabled() {
		if (isTimesheetEnabled==null) {
			return false;
		} else {
			return isTimesheetEnabled;
		}
	}

	public void setIsTimesheetEnabled(Boolean isTimesheetEnabled) {
		this.isTimesheetEnabled = isTimesheetEnabled;
	}

	public Boolean getManagerCanModifyAttendance() {
		if (managerCanModifyAttendance==null) {
			return false;
		} else {
			return managerCanModifyAttendance;
		}
	}

	public void setManagerCanModifyAttendance(Boolean managerCanModifyAttendance) {
		this.managerCanModifyAttendance = managerCanModifyAttendance;
	}

	public Float getLocationAccuracy2() {
		return locationAccuracy2;
	}

	public void setLocationAccuracy2(Float locationAccuracy2) {
		this.locationAccuracy2 = locationAccuracy2;
	}

	private Boolean isLocationAccuracyEnabled;
	
	private Boolean showAddressOnForm;
	
	private Boolean showRequestsOnCalendar;
	
	private Boolean errandTimeFromSystem;
	
	private Boolean obligateNotes;

	private Boolean automaticRequestsValidation;
	
	private Boolean signoutBeforePermissionErrand;
	
	private Boolean ipAddressEnabled;
	
	public Boolean getIpAddressEnabled() {
		if (ipAddressEnabled==null) {
			return false;
		} else {
			return ipAddressEnabled;
		}
	}

	public void setIpAddressEnabled(Boolean ipAddressEnabled) {
		this.ipAddressEnabled = ipAddressEnabled;
	}

	public Boolean getAndroidAttAutomaticApproval() {
		if (androidAttAutomaticApproval==null) {
			return false;
		} else {
			return androidAttAutomaticApproval;
		}
	}

	public void setAndroidAttAutomaticApproval(
			Boolean androidAttAutomaticApproval) {
		this.androidAttAutomaticApproval = androidAttAutomaticApproval;
	}

	public Boolean getSignoutBeforePermissionErrand() {
		if (signoutBeforePermissionErrand==null) {
			return false;
		} else {
			return signoutBeforePermissionErrand;
		}
	}

	public void setSignoutBeforePermissionErrand(
			Boolean signoutBeforePermissionErrand) {
		this.signoutBeforePermissionErrand = signoutBeforePermissionErrand;
	}

	private String automaticErrandEnd;
	
	private Boolean fingerprintEnabled;
	
	private Float companyLat;
	
	private Float companyLong;
	
	private Float distAllowedFromCompany;
	
	private Integer requiredAndroidVersion;
	
	
	

	
	////////////////////////////////////////////////////
	
	
//	mailMgr: Lehaa= ticketmanager@4s-systems.com, Lotus=
//			mailPass: Lehaa=mgemge, Lotus=
//			SMTPServer: Lehaa=smtp.tedata.net, Lotus=
//			portServer: Lehaa=25, Lotus=
	
	
//	accessLevelsCheckDate: Lehaa = false , Lotus = true
//	empRequestCheckDate: Lehaa = false , Lotus = true
//	annualVacationBalanceDaysEnabled: Lehaa=false, lotus=true
//	server: Lehaa=10.8.2.109, lotus = oraserv
//	service: Lehaa=lehaa, lotus=oraserv
//	username: Lehaa=lehaa_payroll, Lotus=lotus_pay
//	password: Lehaa=lehaa_payroll, Lotus=lotus_pay
//	addNewData: Lehaa=false, Lotus=true  (Create Database)
//	checkPostedRequests: Lehaa=false, Lotus=true
//	empRequestTypeException: Lehaa=false, Lotus=true
//	tAttRepWithHrsMin: Lehaa=true, Lotus=false
//	specialVacExcep: Lehaaa = false, Lotus = true
//	creatingMail: Lehaa = true, Lotus=false
//	loginUrl: Lehaa=http://10.8.2.46:28000/Requests/security/login.html, Lotus=��
//	vacLimitProblem: Lehaa=false, Lotus=true
//	vacationRequestExcep: Lehaa=true, Lotus=false
//	reqPeriodDate: Lehaa=false,Lotus=true
//	withdrawDaysQuartPolicy: Lehaa=false, Lotus=true
//	notesValidation: Lehaa=true, Lotus=false
//	withoutSalPeriodValidation: Lehaa=true, Lotus=false
//	fromToRequestVald: Lehaa=false, Lotus =true
//	withoutSalaryVacEnabled: Lehaa=false, Lotus=true
//	periodFromToEnabled: Lehaa=true, Lotus=false
//
//	footerCopyrightsEnabled: Lehaa=true,Lotus=false
//	companyLogoHeader: Lehaa,Lotus

	

	
	public Integer getRequiredAndroidVersion() {
		return requiredAndroidVersion;
	}

	public void setRequiredAndroidVersion(Integer requiredAndroidVersion) {
		this.requiredAndroidVersion = requiredAndroidVersion;
	}

	public Float getCompanyLat() {
		if (companyLat== null) {
			return new Float(0);
		} else {
			return companyLat;
		}
	}

	public void setCompanyLat(Float companyLat) {
		this.companyLat = companyLat;
	}

	public Float getCompanyLong() {
		if (companyLong== null) {
			return new Float(0);
		} else {
			return companyLong;
		}
	}

	public void setCompanyLong(Float companyLong) {
		this.companyLong = companyLong;
	}

	public Float getDistAllowedFromCompany() {
		return distAllowedFromCompany;
	}

	public void setDistAllowedFromCompany(Float distAllowedFromCompany) {
		this.distAllowedFromCompany = distAllowedFromCompany;
	}

	public String getAutomaticErrandEnd() {
		return automaticErrandEnd;
	}

	public void setAutomaticErrandEnd(String automaticErrandEnd) {
		this.automaticErrandEnd = automaticErrandEnd;
	}

	public Boolean getAutomaticSignInOut() {
		if (automaticSignInOut==null) {
			return false;
		} else {
			return automaticSignInOut;
		}
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Float getLocationAccuracy() {
		return locationAccuracy;
	}

	public void setLocationAccuracy(Float locationAccuracy) {
		this.locationAccuracy = locationAccuracy;
	}

	public Boolean getIsLocationAccuracyEnabled() {
		if (isLocationAccuracyEnabled==null) {
			return false;
		} else {
			return isLocationAccuracyEnabled;
		}
	}

	public void setIsLocationAccuracyEnabled(Boolean isLocationAccuracyEnabled) {
		this.isLocationAccuracyEnabled = isLocationAccuracyEnabled;
	}

	public Boolean getShowAddressOnForm() {
		if (showAddressOnForm==null) {
			return false;
		} else {
			return showAddressOnForm;
		}
	}

	public void setShowAddressOnForm(Boolean showAddressOnForm) {
		this.showAddressOnForm = showAddressOnForm;
	}

	public Boolean getShowRequestsOnCalendar() {
		if (showRequestsOnCalendar==null) {
			return false;
		} else {
			return showRequestsOnCalendar;
		}
	}

	public void setShowRequestsOnCalendar(Boolean showRequestsOnCalendar) {
		this.showRequestsOnCalendar = showRequestsOnCalendar;
	}

	public Boolean getErrandTimeFromSystem() {
		if (errandTimeFromSystem==null) {
			return false;
		} else {
			return errandTimeFromSystem;
		}
	}

	public void setErrandTimeFromSystem(Boolean errandTimeFromSystem) {
		this.errandTimeFromSystem = errandTimeFromSystem;
	}

	public Boolean getObligateNotes() {
		if (obligateNotes==null) {
			return false;
		} else {
			return obligateNotes;
		}
	}

	public void setObligateNotes(Boolean obligateNotes) {
		this.obligateNotes = obligateNotes;
	}

	public void setAutomaticSignInOut(Boolean automaticSignInOut) {
		this.automaticSignInOut = automaticSignInOut;
	}

	public Integer getRequestsDeadline() {
		return requestsDeadline;
	}

	public void setRequestsDeadline(Integer requestsDeadline) {
		this.requestsDeadline = requestsDeadline;
	}

	public Boolean getAccessLevelsCheckDate() {
		if (accessLevelsCheckDate==null) {
			return false;
		} else {
			return accessLevelsCheckDate;
		}
	}

	public void setAccessLevelsCheckDate(Boolean accessLevelsCheckDate) {
		this.accessLevelsCheckDate = accessLevelsCheckDate;
	}
	
	public Boolean getCompanyRulesEn() {
		if (companyRulesEn==null) {
			return false;
		} else {
			return companyRulesEn;
		}
	}

	public void setCompanyRulesEn(Boolean companyRulesEn) {
		this.companyRulesEn = companyRulesEn;
	}
	

	public Boolean getAttendanceRequestEn() {
		if (attendanceRequestEn==null) {
			return false;
		} else {
			return attendanceRequestEn;
		}
	}

	public void setAttendanceRequestEn(Boolean attendanceRequestEn) {
		this.attendanceRequestEn = attendanceRequestEn;
	}

	public void settAttRepWithHrsMin(Boolean tAttRepWithHrsMin) {
		this.tAttRepWithHrsMin = tAttRepWithHrsMin;
	}

	public Boolean getEmpRequestCheckDate() {
		if (empRequestCheckDate==null) {
			return false;
		} else {
			return empRequestCheckDate;
		}
	}

	public void setEmpRequestCheckDate(Boolean empRequestCheckDate) {
		this.empRequestCheckDate = empRequestCheckDate;
	}

	

	public Boolean getAnnualVacBalDaysEnabled() {
		if (annualVacBalDaysEnabled==null) {
			return false;
		} else {
			return annualVacBalDaysEnabled;
		}
	}

	public void setAnnualVacBalDaysEnabled(Boolean annualVacBalDaysEnabled) {
		this.annualVacBalDaysEnabled = annualVacBalDaysEnabled;
	}

//	public String getServer() {
//		return server;
//	}
//
//	public void setServer(String server) {
//		this.server = server;
//	}
//
//	public String getService() {
//		return service;
//	}
//
//	public void setService(String service) {
//		this.service = service;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}

	public Boolean getAddNewData() {
		if (addNewData==null) {
			return false;
		} else {
			return addNewData;
		}
	}

	public void setAddNewData(Boolean addNewData) {
		this.addNewData = addNewData;
	}

	public Boolean getCheckPostedRequests() {
		if (checkPostedRequests==null) {
			return false;
		} else {
			return checkPostedRequests;
		}
	}

	public void setCheckPostedRequests(Boolean checkPostedRequests) {
		this.checkPostedRequests = checkPostedRequests;
	}

	public Boolean getEmpRequestTypeException() {
		if (empRequestTypeException==null) {
			return false;
		} else {
			return empRequestTypeException;
		}
	}

	public void setEmpRequestTypeException(Boolean empRequestTypeException) {
		this.empRequestTypeException = empRequestTypeException;
	}

	public Boolean getTAttRepWithHrsMin() {
		if (tAttRepWithHrsMin==null) {
			return false;
		} else {
			return tAttRepWithHrsMin;
		}
	}

	public void setTAttRepWithHrsMin(Boolean attRepWithHrsMin) {
		tAttRepWithHrsMin = attRepWithHrsMin;
	}

	public Boolean getSpecialVacExcep() {
		if (specialVacExcep==null) {
			return false;
		} else {
			return specialVacExcep;
		}
	}

	
	
	public Boolean getExecuseEnabled() {
		if (execuseEnabled==null) {
			return false;
		} else {
			return execuseEnabled;
		}
	}

	public void setExecuseEnabled(Boolean execuseEnabled) {
		this.execuseEnabled = execuseEnabled;
	}

	public void setSpecialVacExcep(Boolean specialVacExcep) {
		this.specialVacExcep = specialVacExcep;
	}

	public Boolean getCreatingMail() {
		if (creatingMail==null) {
			return false;
		} else {
			return creatingMail;
		}
	}

	public void setCreatingMail(Boolean creatingMail) {
		this.creatingMail = creatingMail;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public Boolean getVacLimitProblem() {
		if (vacLimitProblem==null) {
			return false;
		} else {
			return vacLimitProblem;
		}
	}

	public void setVacLimitProblem(Boolean vacLimitProblem) {
		this.vacLimitProblem = vacLimitProblem;
	}

	public Boolean getVacationRequestExcep() {
		if (vacationRequestExcep==null) {
			return false;
		} else {
			return vacationRequestExcep;
		}
	}

	public void setVacationRequestExcep(Boolean vacationRequestExcep) {
		this.vacationRequestExcep = vacationRequestExcep;
	}

	public Boolean getReqPeriodDate() {
		if (reqPeriodDate==null) {
			return false;
		} else {
			return reqPeriodDate;
		}
	}

	public void setReqPeriodDate(Boolean reqPeriodDate) {
		this.reqPeriodDate = reqPeriodDate;
	}

	public Boolean getWithdrawDaysQuartPolicy() {
		if (withdrawDaysQuartPolicy==null) {
			return false;
		} else {
			return withdrawDaysQuartPolicy;
		}
	}

	public void setWithdrawDaysQuartPolicy(Boolean withdrawDaysQuartPolicy) {
		this.withdrawDaysQuartPolicy = withdrawDaysQuartPolicy;
	}

	public Boolean getNotesValidation() {
		if (notesValidation==null) {
			return false;
		} else {
			return notesValidation;
		}
	}

	public void setNotesValidation(Boolean notesValidation) {
		this.notesValidation = notesValidation;
	}

	public Boolean getWithoutSalPeriodValidation() {
		if (withoutSalPeriodValidation==null) {
			return false;
		} else {
			return withoutSalPeriodValidation;
		}
	}

	public void setWithoutSalPeriodValidation(Boolean withoutSalPeriodValidation) {
		this.withoutSalPeriodValidation = withoutSalPeriodValidation;
	}

	public Boolean getFromToRequestVald() {
		if (fromToRequestVald==null) {
			return false;
		} else {
			return fromToRequestVald;
		}
	}

	public void setFromToRequestVald(Boolean fromToRequestVald) {
		this.fromToRequestVald = fromToRequestVald;
	}

	public Boolean getWithoutSalaryVacEnabled() {
		if (withoutSalaryVacEnabled==null) {
			return false;
		} else {
			return withoutSalaryVacEnabled;
		}
	}

	public void setWithoutSalaryVacEnabled(Boolean withoutSalaryVacEnabled) {
		this.withoutSalaryVacEnabled = withoutSalaryVacEnabled;
	}

	public Boolean getPeriodFromToEnabled() {
		if (periodFromToEnabled==null) {
			return false;
		} else {
			return periodFromToEnabled;
		}
	}

	public void setPeriodFromToEnabled(Boolean periodFromToEnabled) {
		this.periodFromToEnabled = periodFromToEnabled;
	}

	public Boolean getFooterCopyrightsEnabled() {
		if (footerCopyrightsEnabled==null) {
			return false;
		} else {
			return footerCopyrightsEnabled;
		}
	}

	public void setFooterCopyrightsEnabled(Boolean footerCopyrightsEnabled) {
		this.footerCopyrightsEnabled = footerCopyrightsEnabled;
	}

	public Blob getCompanyLogoHeader() {
		return companyLogoHeader;
	}

	public void setCompanyLogoHeader(Blob companyLogoHeader) {
		this.companyLogoHeader = companyLogoHeader;
	}

	public String getCompanyLogoHeaderName() {
		return companyLogoHeaderName;
	}

	public void setCompanyLogoHeaderName(String companyLogoHeaderName) {
		this.companyLogoHeaderName = companyLogoHeaderName;
	}
	
	

	public String getMailMgr() {
		return mailMgr;
	}

	public void setMailMgr(String mailMgr) {
		this.mailMgr = mailMgr;
	}

	public String getMailPass() {
		return mailPass;
	}

	public void setMailPass(String mailPass) {
		this.mailPass = mailPass;
	}

	public String getSMTPServer() {
		return SMTPServer;
	}

	public void setSMTPServer(String sMTPServer) {
		SMTPServer = sMTPServer;
	}

	public String getPortServer() {
		return portServer;
	}

	public void setPortServer(String portServer) {
		this.portServer = portServer;
	}
	
	public Integer getSalaryFromDay() {
		return salaryFromDay;
	}

	public void setSalaryFromDay(Integer salaryFromDay) {
		this.salaryFromDay = salaryFromDay;
	}
	
	
	public Integer getMaxEmp() {
		return maxEmp;
	}

	public void setMaxEmp(Integer maxEmp) {
		this.maxEmp = maxEmp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Settings)) {
			return false;
		}
		Settings rhs = (Settings) o;
		return new EqualsBuilder().isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).toHashCode();
	}
	public String getEntityDisplayName() {
		
		return "Application Settings";
	}

	public Boolean getAutomaticRequestsValidation() {
		if (automaticRequestsValidation==null) {
			return false;
		} else {
			return automaticRequestsValidation;
		}
	}

	public void setAutomaticRequestsValidation(Boolean automaticRequestsValidation) {
		this.automaticRequestsValidation = automaticRequestsValidation;
	}

	public Boolean getFingerprintEnabled() {
		if (fingerprintEnabled==null) {
			return false;
		} else {
			return fingerprintEnabled;
		}
	}

	public void setFingerprintEnabled(Boolean fingerprintEnabled) {
		this.fingerprintEnabled = fingerprintEnabled;
	}
}
