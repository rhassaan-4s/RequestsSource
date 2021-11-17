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
@Table(name="sp_anual_raise")
public class SpAnualRaise implements Auditable,Serializable {
	
	public SpAnualRaise() {
		// TODO Auto-generated constructor stub
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="sp_anual_raise_seq")
	@SequenceGenerator(name="sp_anual_raise_seq",sequenceName="sp_anual_raise_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
	
    private String empCode; 
	private Long year;
	private Double val;
	
	@Transient
	private String empName;
	
	public String getEntityDisplayName() {
		return "SpAnualRaise Addition";
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
		 if (!(o instanceof SpAnualRaise)) {
		 return false;
		 }
		 SpAnualRaise sar = (SpAnualRaise) o;
		 return new EqualsBuilder()
		 .append(this.empCode, sar.getEmpCode())
		 .isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.id)
		.toHashCode();
	}
	
	public Long getYear(){
		return year;
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
	
	public void setYear(Long year) {
		this.year = year;
	}
	public Double getVal() {
		return val;
	}
	public void setVal(Double val) {
		this.val = val;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpName() {
		return empName;
	}
}
