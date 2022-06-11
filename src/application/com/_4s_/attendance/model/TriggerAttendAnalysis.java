package com._4s_.attendance.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity
@Table (name = "trigger_attend_analysiss")

public class TriggerAttendAnalysis implements Serializable,Auditable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2654777149248438986L;
	@Id
//	@GenericGenerator(name="religion_seq",strategy="com._4s_.attendance.dao.ReligionStringKeyGenerator")
//	@GeneratedValue(generator="religion_seq")
	private Date indate;
	private Integer month;
	private Integer year;
	private String is_handeled = "0";
	
	public Long getId() {
		return null;
	}

	public Date getIndate() {
		return indate;
	}





	public void setIndate(Date indate) {
		this.indate = indate;
	}





	public Integer getMonth() {
		return month;
	}


	public void setMonth(Integer month) {
		this.month = month;
	}


	public Integer getYear() {
		return year;
	}


	public void setYear(Integer year) {
		this.year = year;
	}


	public String getIs_handeled() {
		return is_handeled;
	}


	public void setIs_handeled(String is_handeled) {
		this.is_handeled = is_handeled;
	}


	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "TriggerAttendAnalysis";
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("indate", this.indate)
		.append("month", this.month)
		.append("year", this.year)
		/*.append("watchedThreads", this.watchedThreads)*/.toString();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof TriggerAttendAnalysis)) {
			return false;
		}
		TriggerAttendAnalysis rhs = (TriggerAttendAnalysis) o;
		return new EqualsBuilder().append(this.indate, rhs.getIndate()).append(this.month, rhs.getMonth()).append(this.year, rhs.getYear()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.indate).append(this.month).append(this.year).toHashCode();
	}

}
