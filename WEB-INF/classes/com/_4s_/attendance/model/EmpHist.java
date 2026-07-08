package com._4s_.attendance.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.model.Employee;
import com.ibm.icu.util.Calendar;

@Entity
@Table (name = "emphist")

public class EmpHist implements Serializable,Auditable {
	@Id
	@ManyToOne
	@JoinColumn(name="empCode")
	private Employee empCode;
	@Id
	private Date fr_date=Calendar.getInstance().getTime();
	private Date to_date;
	
	@ManyToOne
	@JoinColumn(name="title")
	private Title title;
	
	private String des_No;//decision no
	private String des_date;//decision date
	private String direction="015";
	@ManyToOne
	@JoinColumn(name="degree")
	private AttendanceDegree degree;
	@ManyToOne
	@JoinColumn(name="region")
	private AttendanceRegion region;
	private String type;
//	private String str_des_date;
//	private String str_fr_date;
//	private String str_to_date;
//	private String salary;
//	private String currentJob;
//	private String remarks;
//	private Timestamp travel_date;
	private String confirmed;
//	private String str_travel_date;
//	private String branch;
//	private String project;
//	private String workgrp;
	@ManyToOne
	@JoinColumn(name="sector")
	private AttendanceSector sector;
//	private Timestamp arrival_date;
//	private String job_typegrp;
	public Long getId() {
		return null;
	}
	public Employee getEmpCode() {
		return empCode;
	}
	public void setEmpCode(Employee empCode) {
		this.empCode = empCode;
	}
	public Date getFr_date() {
		return fr_date;
	}
	public void setFr_date(Date fr_date) {
		this.fr_date = fr_date;
	}
	public Date getTo_date() {
		return to_date;
	}
	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}
	public Title getTitle() {
		return title;
	}
	public void setTitle(Title title) {
		this.title = title;
	}
	public String getDes_No() {
		return des_No;
	}
	public void setDes_No(String des_No) {
		this.des_No = des_No;
	}
	public String getDes_date() {
		return des_date;
	}
	public void setDes_date(String des_date) {
		this.des_date = des_date;
	}
//	public String getDirection() {
//		return direction;
//	}
//	public void setDirection(String direction) {
//		this.direction = direction;
//	}
	public AttendanceDegree getDegree() {
		return degree;
	}
	public void setDegree(AttendanceDegree degree) {
		this.degree = degree;
	}
	
	public AttendanceRegion getRegion() {
		return region;
	}
	public void setRegion(AttendanceRegion region) {
		this.region = region;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}
	
	public AttendanceSector getSector() {
		return sector;
	}
	public void setSector(AttendanceSector sector) {
		this.sector = sector;
	}
	@Override
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "emphist";
	}

	

}
