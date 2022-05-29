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
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.util.LocaleUtil;


@Entity//(access=AccessType.FIELD)
@Table(name="hr_employee_address")
public class HREmployeeAddress implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_employee_address_seq")
	@SequenceGenerator(name="hr_employee_address_seq",sequenceName="hr_employee_address_seq")//(generate=GeneratorType.IDENTITY)
    private Long id;
	
	
	@ManyToOne
	@JoinColumn (name="employee")
	private HREmployee employee;
	
	@ManyToOne
	@JoinColumn (name="geographicalDivision")
	private HRGeographicalDivision geographicalDivision;
	
	private String detailedAddress;
	
	private String anotherAddress;
	
	public String getEntityDisplayName() {
		return "empAddress Addition";
	}
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("detailedAddress", this.detailedAddress)
		 .toString();
	}
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HREmployeeAddress)) {
		 return false;
		 }
		 HREmployeeAddress rhs = (HREmployeeAddress) o;
		 return new EqualsBuilder()
		 .append(this.detailedAddress, rhs.getDetailedAddress())
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
	public HRGeographicalDivision getGeographicalDivision() {
		return geographicalDivision;
	}
	public void setGeographicalDivision(HRGeographicalDivision geographicalDivision) {
		this.geographicalDivision = geographicalDivision;
	}
	public String getDetailedAddress() {
		return detailedAddress;
	}
	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}
	public String getAnotherAddress() {
		return anotherAddress;
	}
	public void setAnotherAddress(String anotherAddress) {
		this.anotherAddress = anotherAddress;
	}
}
