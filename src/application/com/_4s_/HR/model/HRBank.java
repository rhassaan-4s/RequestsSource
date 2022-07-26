package com._4s_.HR.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="hr_bank")
public class HRBank implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_bank_seq")
	@SequenceGenerator(name="hr_bank_seq",sequenceName="hr_bank_seq")//(generate=GeneratorType.IDENTITY)
	private  Long id ;
	
	private String code;
	private String name ;
	private String ename ;
	private String email;
	private Long type;   // 1 for bank & 2 for Treasury
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public HRBank() {
		// TODO Auto-generated constructor stub
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	
	public String getEntityDisplayName() {
		return "hrBank Addition";
	}
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		
		 .toString();
	}
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HRBank)) {
		 return false;
		 }
		HRBank rhs = (HRBank) o;
		 return new EqualsBuilder()
		
		 .isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getId())

		.toHashCode();
	}

	
	
}
