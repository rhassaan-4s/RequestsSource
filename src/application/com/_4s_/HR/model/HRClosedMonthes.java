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
@Table(name="hr_closed_monthes")
public class HRClosedMonthes implements Auditable,Serializable {

	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="hr_closed_monthes_seq")
	@SequenceGenerator(name="hr_closed_monthes_seq",sequenceName="hr_closed_monthes_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
	
	public HRClosedMonthes() {
		// TODO Auto-generated constructor stub
	}

	@ManyToOne
	@JoinColumn(name="month_id")
	private HRMonth month_id;
	
	@ManyToOne
	@JoinColumn(name="year_id")
	private HRYear year_id;
	
	public String getEntityDisplayName() {
		return "HRClosedMonthes Addition";
	}
	
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("id", this.id)
		 .append("year_id", this.year_id)
		 .append("month_id", this.month_id)
		 .toString();
	}
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HRClosedMonthes)) {
		 return false;
		 }
		 HRClosedMonthes hrcm = (HRClosedMonthes) o;
		 return new EqualsBuilder()
		 .append(this.id, hrcm.getId())
		 .append(this.year_id, hrcm.getYear_id())
		 .append(this.month_id, hrcm.getMonth_id())
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

	public HRMonth getMonth_id() {
		return month_id;
	}

	public void setMonth_id(HRMonth month_id) {
		this.month_id = month_id;
	}

	public HRYear getYear_id() {
		return year_id;
	}

	public void setYear_id(HRYear year_id) {
		this.year_id = year_id;
	}
}
