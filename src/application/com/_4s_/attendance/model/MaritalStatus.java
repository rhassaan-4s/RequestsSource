package com._4s_.attendance.model;

import java.io.Serializable;

import javax.persistence.Column;
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
@Table (name = "maritail")

public class MaritalStatus implements Serializable,Auditable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 383982479148713837L;

	@Id
	@GenericGenerator(name="marital_seq",strategy="com._4s_.attendance.dao.MaritalStringKeyGenerator")
	@GeneratedValue(generator="marital_seq")
	@Column(name="maritail_code")
	private String maritalCode;
	
	@Column(name="maritail_name")
	private String maritalName;
	private String eng_name;
	
	public Long getId() {
		return null;
	}

	
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "Marital Status";
	}

	

	public String getMaritalCode() {
		return maritalCode;
	}


	public void setMaritalCode(String maritalCode) {
		this.maritalCode = maritalCode;
	}


	public String getMaritalName() {
		return maritalName;
	}


	public void setMaritalName(String maritalName) {
		this.maritalName = maritalName;
	}


	public String getEng_name() {
		return eng_name;
	}


	public void setEng_name(String eng_name) {
		this.eng_name = eng_name;
	}


	public String toString() {
		return new ToStringBuilder(this)
		.append("name", this.maritalName)
		/*.append("watchedThreads", this.watchedThreads)*/.toString();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof AttendanceDepartment)) {
			return false;
		}
		MaritalStatus rhs = (MaritalStatus) o;
		return new EqualsBuilder().append(this.maritalName, rhs.getMaritalName()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.maritalName).toHashCode();
	}

}
