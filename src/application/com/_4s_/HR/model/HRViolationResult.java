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
@Table(name="hr_violation_result")
public class HRViolationResult implements Auditable,Serializable {
	
	public HRViolationResult() {
		// TODO Auto-generated constructor stub
	}
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="hr_violation_result_seq")
	@SequenceGenerator(name="hr_violation_result_seq",sequenceName="hr_violation_result_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
    private String code;
	private String name;
	private String ename;
	
	public String getEntityDisplayName() {
		return "violation_result Addition";
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
		 if (!(o instanceof HRViolationResult)) {
		 return false;
		 }
		 HRViolationResult rhs = (HRViolationResult) o;
		 return new EqualsBuilder()
		 .append(this.name, rhs.getName())
		 .append(this.ename, rhs.getEname())
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
}
