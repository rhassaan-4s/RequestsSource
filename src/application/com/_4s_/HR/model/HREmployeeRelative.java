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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;


@Entity//(access=AccessType.FIELD)
@Table(name="hr_employee_relative")
public class HREmployeeRelative  implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_employee_relative_seq")
	@SequenceGenerator(name="hr_employee_relative_seq",sequenceName="hr_employee_relative_seq")
   
	private Long id;
	
	
	@ManyToOne
	@JoinColumn (name="employee")
	private HREmployee employee;
	
	
	private String relativeName;
	
	@ManyToOne
	@JoinColumn (name="kinshipType")
	private HRKinship kinshipType;
	
	private Date relationshipStartDate;
	private Date relationshipEndDate;
	
	public String getEntityDisplayName() {
		return "empRelative Addition";
	}
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("relativeName", this.relativeName)
		  .append("kinshipType", this.kinshipType.toString())
		 .toString();
	}
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HREmployeeRelative)) {
		 return false;
		 }
		 HREmployeeRelative rhs = (HREmployeeRelative) o;
		 return new EqualsBuilder()
		 .append(this.relativeName, rhs.getRelativeName())
		 .append(this.kinshipType, rhs.getKinshipType().getId())
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
	public String getRelativeName() {
		return relativeName;
	}
	public void setRelativeName(String relativeName) {
		this.relativeName = relativeName;
	}

	public Date getRelationshipStartDate() {
		return relationshipStartDate;
	}
	public void setRelationshipStartDate(Date relationshipStartDate) {
		this.relationshipStartDate = relationshipStartDate;
	}
	public Date getRelationshipEndDate() {
		return relationshipEndDate;
	}
	public void setRelationshipEndDate(Date relationshipEndDate) {
		this.relationshipEndDate = relationshipEndDate;
	}
	public HRKinship getKinshipType() {
		return kinshipType;
	}
	public void setKinshipType(HRKinship kinshipType) {
		this.kinshipType = kinshipType;
	}

}
