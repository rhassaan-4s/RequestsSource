package com._4s_.HR.model;

import java.io.Serializable;

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
@Table(name="hr_violation")
public class HRViolation implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_violation_seq")
	@SequenceGenerator(name="hr_violation_seq",sequenceName="hr_violation_seq")
	private Long id;
	
	private String code;
	private String name;
	private String ename;
	
	@ManyToOne
	@JoinColumn (name="calculationMethod")
	private HRCalculationMethod calculationMethod;
	
	@ManyToOne
	@JoinColumn (name="violationReason")
	private HRViolationReason violationReason;
	
	private Boolean defaultViolation;
	private Boolean discountOfLatenessFromSalary;//in case of violation reason is lateness
	
   private Boolean discountLeaveEarlyFromSalary;// in case of violation reason is leavingEarly
   
   private Boolean dayAbsenceWithoutPay;//in case of violation reason is absence
   
   public String getEntityDisplayName() {
		return "vacation Addition";
	}
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("name", this.name)
		 .append("ename", this.ename)
		 .toString();
	}
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HRViolation)) {
		 return false;
		 }
		 HRViolation rhs = (HRViolation) o;
		 return new EqualsBuilder()
		 .append(this.name, rhs.getName())
		 .append(this.ename, rhs.getEname())
		 .isEquals();
	}
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.id)

		.toHashCode();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public HRCalculationMethod getCalculationMethod() {
		return calculationMethod;
	}
	public void setCalculationMethod(HRCalculationMethod calculationMethod) {
		this.calculationMethod = calculationMethod;
	}
	public HRViolationReason getViolationReason() {
		return violationReason;
	}
	public void setViolationReason(HRViolationReason violationReason) {
		this.violationReason = violationReason;
	}
	public Boolean getDefaultViolation() {
		return defaultViolation;
	}
	public void setDefaultViolation(Boolean defaultViolation) {
		this.defaultViolation = defaultViolation;
	}
	public Boolean getDiscountOfLatenessFromSalary() {
		return discountOfLatenessFromSalary;
	}
	public void setDiscountOfLatenessFromSalary(Boolean discountOfLatenessFromSalary) {
		this.discountOfLatenessFromSalary = discountOfLatenessFromSalary;
	}
	
	public Boolean getDayAbsenceWithoutPay() {
		return dayAbsenceWithoutPay;
	}
	public void setDayAbsenceWithoutPay(Boolean dayAbsenceWithoutPay) {
		this.dayAbsenceWithoutPay = dayAbsenceWithoutPay;
	}
	public Boolean getDiscountLeaveEarlyFromSalary() {
		return discountLeaveEarlyFromSalary;
	}
	public void setDiscountLeaveEarlyFromSalary(Boolean discountLeaveEarlyFromSalary) {
		this.discountLeaveEarlyFromSalary = discountLeaveEarlyFromSalary;
	}
	
}
