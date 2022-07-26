package com._4s_.attendance.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com._4s_.auditing.model.Auditable;

@Entity
@Table (name = "installation_dates")

public class InstallationDates implements Serializable,Auditable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 383982479148713837L;

	@Id
	@GenericGenerator(name="installationdates_seq",strategy="com._4s_.attendance.dao.InstallationDatesStringKeyGenerator")
	@GeneratedValue(generator="installationdates_seq")
	private String code;
	private Date vacation_D;
	private Date attend_close_month;
	
	public Long getId() {
		return null;
	}

	
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "InstallationDates";
	}

	



	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Date getVacation_D() {
		return vacation_D;
	}


	public void setVacation_D(Date vacation_D) {
		this.vacation_D = vacation_D;
	}


	public Date getAttend_close_month() {
		return attend_close_month;
	}


	public void setAttend_close_month(Date attend_close_month) {
		this.attend_close_month = attend_close_month;
	}


	public String toString() {
		return new ToStringBuilder(this)
		.append("vacation D", this.vacation_D)
		/*.append("watchedThreads", this.watchedThreads)*/.toString();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof InstallationDates)) {
			return false;
		}
		InstallationDates rhs = (InstallationDates) o;
		return new EqualsBuilder().append(this.vacation_D, rhs.getAttend_close_month()).append(this.attend_close_month, rhs.getAttend_close_month()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.vacation_D).toHashCode();
	}

}
