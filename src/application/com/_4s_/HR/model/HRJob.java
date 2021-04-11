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
@Table(name="hr_job")
public class HRJob implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="hr_job_seq")
	@SequenceGenerator(name="hr_job_seq",sequenceName="hr_job_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
	
	private String code;
	private String name;
	private String ename;
	private String job_conditions;
	private String emp_type;
	private Long contract_salary;

	public String getEntityDisplayName() {
		return "Job Addition";
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
		 if (!(o instanceof HRJob)) {
		 return false;
		 }
		 HRJob rhs = (HRJob) o;
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
	
	public String getJob_conditions() {
		return job_conditions;
	}
	public void setTitle_conditions(String job_conditions) {
		this.job_conditions = job_conditions;
	}
	public String getEmp_type() {
		return emp_type;
	}
	public void setEmp_type(String emp_type) {
		this.emp_type = emp_type;
	}
	public Long getContract_salary() {
		return contract_salary;
	}
	public void setContract_salary(Long contract_salary) {
		this.contract_salary = contract_salary;
	}
	public void setJob_conditions(String job_conditions) {
		this.job_conditions = job_conditions;
	}
	
	

}
