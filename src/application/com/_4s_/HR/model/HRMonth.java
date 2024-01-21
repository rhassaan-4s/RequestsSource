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
@Table(name="hr_month")
public class HRMonth implements Auditable,Serializable {
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_month_seq")
	@SequenceGenerator(name="hr_month_seq",sequenceName="hr_month_seq")
	private Long id;
	private Integer month; 
	
	public String getEntityDisplayName() {
		return "HRMonth Addition";
	}
	
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("id", this.id)
		 .append("month", this.month)
		 .toString();
	}
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HRMonth)) {
		 return false;
		 }
		 HRMonth hrm = (HRMonth) o;
		 return new EqualsBuilder()
		 .append(this.id, hrm.getId())
		 .append(this.month, hrm.getMonth())
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

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
}
