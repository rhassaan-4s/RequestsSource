package com._4s_.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
@Entity
@Table (name = "common_hijri_date_adjustment")
public class HijriDateAdjustment implements Serializable,Auditable{
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	private int value;
	private Date hijriDate;
	private Date miladiDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	public Date getHijriDate() {
		return hijriDate;
	}
	public void setHijriDate(Date hijriDate) {
		this.hijriDate = hijriDate;
	}
	public Date getMiladiDate() {
		return miladiDate;
	}
	public void setMiladiDate(Date miladiDate) {
		this.miladiDate = miladiDate;
	}
	public String toString() {
		return new ToStringBuilder(this)
		.append("value", this.value)
		.toString();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof HijriDateAdjustment)) {
			return false;
		}
		HijriDateAdjustment rhs = (HijriDateAdjustment) o;
		return new EqualsBuilder().append(this.value, rhs.value)
		.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.value)
		.toHashCode();
	}
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return new Integer(this.value).toString() ;
	}
	
	
}
