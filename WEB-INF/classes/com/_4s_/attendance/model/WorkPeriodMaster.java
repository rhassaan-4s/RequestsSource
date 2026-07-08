package com._4s_.attendance.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com._4s_.auditing.model.Auditable;

@Entity
@Table (name = "workperiods")

public class WorkPeriodMaster implements Serializable,Auditable {
	@Id
	@GenericGenerator(name="workperiod_seq",strategy="com._4s_.attendance.dao.WorkPeriodStringKeyGenerator")
	@GeneratedValue(generator="workperiod_seq")
	private String workperiods;
	private String name;
	private String ename;
	private String form="1";
	private String Lang="A";
	
	public Long getId() {
		return null;
	}

	
	public String getWorkperiods() {
		return workperiods;
	}

	public void setWorkperiods(String workperiods) {
		this.workperiods = workperiods;
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

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getLang() {
		return Lang;
	}

	public void setLang(String lang) {
		Lang = lang;
	}

	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "work periods master";
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("code", this.workperiods).append("name", this.name).toString();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof WorkPeriodMaster)) {
			return false;
		}
		WorkPeriodMaster rhs = (WorkPeriodMaster) o;
		return new EqualsBuilder().append(this.workperiods, rhs.getWorkperiods()).append(this.name, rhs.getName()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.workperiods).append(this.name).toHashCode();
	}

}
