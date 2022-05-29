package com._4s_.HR.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="emp_inst_exper_years")
public class EmpInstExperYears implements Auditable,Serializable {
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="emp_inst_exper_years_seq")
	@SequenceGenerator(name="emp_inst_exper_years_seq",sequenceName="emp_inst_exper_years_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
    private String empCode; 
	private Long years;
	private Integer monthes;
	private Integer days;
	@Transient
	private String empName;
	
	public String getEntityDisplayName() {
		return "EmpInstExperYears Addition";
	}
	
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("empCode", this.empCode)
		 .toString();
	}
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof EmpInstExperYears)) {
		 return false;
		 }
		 EmpInstExperYears eiey = (EmpInstExperYears) o;
		 return new EqualsBuilder()
		 .append(this.empCode, eiey.getEmpCode())
		 .isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.id)
		.toHashCode();
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

	public Long getYears() {
		return years;
	}

	public void setYears(Long years) {
		this.years = years;
	}

	public Integer getMonthes() {
		return monthes;
	}

	public void setMonthes(Integer monthes) {
		this.monthes = monthes;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}
	
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpName() {
		return empName;
	}
}
