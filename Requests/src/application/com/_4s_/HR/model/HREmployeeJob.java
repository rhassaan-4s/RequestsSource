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
import javax.persistence.OneToOne;
	import javax.persistence.SequenceGenerator;
	import javax.persistence.Table;

	import org.apache.commons.lang.builder.EqualsBuilder;
	import org.apache.commons.lang.builder.HashCodeBuilder;
	import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;


@Entity//(access=AccessType.FIELD)
@Table(name="hr_employee_job")
public class HREmployeeJob  implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="hr_employee_job_seq")
	@SequenceGenerator(name="hr_employee_job_seq",sequenceName="hr_employee_job_seq")//(generate=GeneratorType.IDENTITY)
   
	private Long id;
	
	@ManyToOne 
	@JoinColumn (name="employee")
	private HREmployee employee;
	
	@ManyToOne 
	@JoinColumn (name="job")
	private HRInternalDivision job;
	
	private String functionalGroup;
	
	private Date job_start_date;
	
//	@OneToOne
//	@JoinColumn(name="jobNumber")
//	private HRJob jobNumber;
//	// da ele hoa el id fl HRJob
	
	
	public String getEntityDisplayName() {
		return "empJob Addition";
	}
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("jobDescription", this.job)
		 .toString();
	}
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HREmployeeJob)) {
		 return false;
		 }
		 HREmployeeJob rhs = (HREmployeeJob) o;
		 return new EqualsBuilder()
		 .append(this.job.getArdesc(), rhs.getJob().getArdesc())
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
	public HRInternalDivision getJob() {
		return job;
	}
	public void setJob(HRInternalDivision job) {
		this.job = job;
	}
	public String getFunctionalGroup() {
		return functionalGroup;
	}
	public void setFunctionalGroup(String functionalGroup) {
		this.functionalGroup = functionalGroup;
	}
	public Date getJob_start_date() {
		return job_start_date;
	}
	public void setJob_start_date(Date job_start_date) {
		this.job_start_date = job_start_date;
	}
	

}
