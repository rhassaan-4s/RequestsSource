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
@Table(name="hr_year_allow_vac_transfer")
public class HRYearAllowVacationTransfer implements Auditable,Serializable {
	
	public HRYearAllowVacationTransfer() {
		// TODO Auto-generated constructor stub
	}
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_year_allow_vac_seq")
	@SequenceGenerator(name="hr_year_allow_vac_seq",sequenceName="hr_year_allow_vac_seq")
	private Long id;
	private Long year;
	
	public String getEntityDisplayName() {
		return "year Addition";
	}
	
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("year", this.year)
		 .toString();
	}
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HRYearAllowVacationTransfer)) {
		 return false;
		 }
		 HRYearAllowVacationTransfer rhs = (HRYearAllowVacationTransfer) o;
		 return new EqualsBuilder()
		 .append(this.year, rhs.getYear())
		 .isEquals();
	}
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
	public Long getYear() {
		return year;
	}
	public void setYear(Long year) {
		this.year = year;
	}
}
