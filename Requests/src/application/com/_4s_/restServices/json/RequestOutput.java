package com._4s_.restServices.json;

import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

//@JsonInclude(Include.NON_NULL)
public class RequestOutput {

	private Long id;
	private String empCode;
	private String empName;
	private String requestDesc;
	@JsonFormat(shape=Shape.NUMBER, timezone="GMT+2")
	private Date requestDate;
	private String requestNumber;
	private Long requestType;
//	@JsonFormat(shape=Shape.NUMBER, timezone="GMT+2")
	private Date fromDate;
//	@JsonFormat(shape=Shape.NUMBER, timezone="GMT+2")
	private Date toDate;
	private String status;//approved-declined
	private String notes;
	private Double vacDuration;
	private Double longitude;
	private Double latitude;
	private Boolean isInsideCompany;
	private String locationAddress;
	
	public String getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	public Long getRequestType() {
		return requestType;
	}
	public void setRequestType(Long requestType) {
		this.requestType = requestType;
	}
	public Double getVacDuration() {
		return vacDuration;
	}
	public void setVacDuration(Double vacDuration) {
		this.vacDuration = vacDuration;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getRequestDesc() {
		return requestDesc;
	}
	public void setRequestDesc(String requestDesc) {
		this.requestDesc = requestDesc;
	}
	public Date getRequestDate() {
		System.out.println("time zone" + JsonFormat.DEFAULT_TIMEZONE);
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public Date getFromDate() {
//		TimeZone.setDefault(TimeZone.getTimeZone("Africa/Cairo"));
//		TimeZone.getDefault().inDaylightTime(fromDate)
//		System.out.println("time zone " + TimeZone.getDefault().getDisplayName() + " date time saving " + TimeZone.getDefault().inDaylightTime(fromDate));
		return fromDate;
	}
	
	public Boolean getIsInsideCompany() {
		return isInsideCompany;
	}
	public void setIsInsideCompany(Boolean isInsideCompany) {
		this.isInsideCompany = isInsideCompany;
	}
	public String getLocationAddress() {
		return locationAddress;
	}
	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	
}
