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
@Table(name="hr_vacation_rules")
public class HRVacationRules implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_vacation_rules_seq")
	@SequenceGenerator(name="hr_vacation_rules_seq",sequenceName="hr_vacation_rules_seq")
    private Long id;
	
	@ManyToOne
	@JoinColumn (name="vacation")
	private HRVacation vacation;
	private Long serviceLength;
	private Long due;
	

	
	public String getEntityDisplayName() {
		return "vacation Rules Addition";
	}
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("vacation", this.vacation)
		 
		 .toString();
	}
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HRVacationRules)) {
		 return false;
		 }
		 HRVacationRules rhs = (HRVacationRules) o;
		 return new EqualsBuilder()
		 .append(this.serviceLength, rhs.getServiceLength())
		 .isEquals();
	}
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.id)

		.toHashCode();
	}
	public HRVacation getVacation() {
		return vacation;
	}
	public void setVacation(HRVacation vacation) {
		this.vacation = vacation;
	}
	public Long getServiceLength() {
		return serviceLength;
	}
	public void setServiceLength(Long serviceLength) {
		this.serviceLength = serviceLength;
	}
	public Long getDue() {
		return due;
	}
	public void setDue(Long due) {
		this.due = due;
	}

}
