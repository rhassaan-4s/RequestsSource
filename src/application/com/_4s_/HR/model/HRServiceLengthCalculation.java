package com._4s_.HR.model;

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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="HR_SERVICELENGTH_CALCULATION")
public class HRServiceLengthCalculation implements Auditable,Serializable {
	
	public HRServiceLengthCalculation() {
		// TODO Auto-generated constructor stub
	}
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HR_SERLENGTH_CALC_SEQ")
	@SequenceGenerator(name="HR_SERLENGTH_CALC_SEQ",sequenceName="HR_SERLENGTH_CALC_SEQ")//(generate=GeneratorType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn (name="employee")
	private HREmployee employee;
	
	private Date serviceDate;
	
	public String getEntityDisplayName() {
		return "SERVICELENGTH_CALCULATION Addition";
	}
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("name", this.employee.getName())
		 .append("ename", this.employee.getEname())
		 .toString();
	}
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HRServiceLengthCalculation)) {
		 return false;
		 }
		 HRServiceLengthCalculation rhs = (HRServiceLengthCalculation) o;
		 return new EqualsBuilder()
		 .append(this.employee, rhs.getEmployee())
		 
		 .isEquals();
	}
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.id)

		.toHashCode();
	}
	public HREmployee getEmployee() {
		return employee;
	}
	public void setEmployee(HREmployee employee) {
		this.employee = employee;
	}
	public Date getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}
	

}
