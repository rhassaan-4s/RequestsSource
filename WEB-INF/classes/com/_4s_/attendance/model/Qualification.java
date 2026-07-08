package com._4s_.attendance.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.web.validators.Mandatory;
import com._4s_.common.web.validators.Unique;

@Entity
@Table (name = "qual")
public class Qualification implements Serializable,Auditable {
	@Id
	@GenericGenerator(name="qual_seq",strategy="com._4s_.attendance.dao.QualStringKeyGenerator")
	@GeneratedValue(generator="qual_seq")
	private String qual;
	@Mandatory(value=Qualification.class,property="name")
	@Unique(value=Qualification.class,property = "name")
	private String name;
	@Mandatory(value=Qualification.class,property="ename")
	@Unique(value=Qualification.class,property = "ename")
	private String ename;
	private String form = "1";
	private String lang = "A";
//	private Float rank;
	
//	public Float getRank() {
//		return rank;
//	}
//
//
//	public void setRank(Float rank) {
//		this.rank = rank;
//	}


	public String getLang() {
		return lang;
	}


	public void setLang(String lang) {
		this.lang = lang;
	}


	public String getForm() {
		return form;
	}


	public void setForm(String form) {
		this.form = form;
	}


	public Long getId() {
		return null;
	}

	
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "attendance department";
	}


	public String getQual() {
		return qual;
	}


	public void setQual(String qual) {
		this.qual = qual;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEname() {
		return ename;
	}


	public void setEname(String ename) {
		this.ename = ename;
	}


	public String toString() {
		return new ToStringBuilder(this)
		.append("name", this.name)
		/*.append("watchedThreads", this.watchedThreads)*/.toString();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof AttendanceDepartment)) {
			return false;
		}
		AttendanceDepartment rhs = (AttendanceDepartment) o;
		return new EqualsBuilder().append(this.name, rhs.getName()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.name).toHashCode();
	}

}
