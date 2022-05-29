package com._4s_.HR.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.util.LocaleUtil;


@Entity//(access=AccessType.FIELD)
@Table(name="hr_employee_marital_status")
public class HREmployeeMaritalStatus implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_employee_marital_seq")
	@SequenceGenerator(name="hr_employee_marital_seq",sequenceName="hr_employee_marital_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
	
	@ManyToOne 
	@JoinColumn (name="employee")
	private HREmployee employee;
	
	@ManyToOne 
	@JoinColumn (name="maritalStatus")
	private HRMaritalStatus maritalStatus;
	private Date statusDate;

	
	
	public String getEntityDisplayName() {
		return "Employee_maritalStatus Addition";
	}
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("employee", this.employee)
		 .append("maritalStatus", this.maritalStatus)
		 .toString();
	}
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HREmployeeMaritalStatus)) {
		 return false;
		 }
		 HREmployeeMaritalStatus rhs = (HREmployeeMaritalStatus) o;
		 return new EqualsBuilder()
		 .append(this.employee.getName(), rhs.employee.getName())
		 .append(this.employee.getEname(), rhs.employee.getEname())
		 .isEquals();
	}
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getId())

		.toHashCode();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public HREmployee getEmployee() {
		return employee;
	}
	public void setEmployee(HREmployee employee) {
		this.employee = employee;
	}
	public HRMaritalStatus getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(HRMaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public Date getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}


}
