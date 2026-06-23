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
@Table(name="hr_install")
public class HRInstall implements Auditable,Serializable {
	
	public HRInstall() {
		// TODO Auto-generated constructor stub
	}

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_install_seq")
	@SequenceGenerator(name="hr_install_seq",sequenceName="hr_install_seq")
	private Long id;
	private String instCode;
	private String instName;
	private String eng_name;
	private String scrbrif;
	
	public String getEntityDisplayName() {

		return "HRInstall Addition";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof DiscDays)) {
			return false;
		}
		HRInstall hrInstall = (HRInstall) o;
		return new EqualsBuilder()
		.append(this.instCode, hrInstall.getInstCode())
		.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.id)
		.toHashCode();
	}
	
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("instCode", this.instCode)
		 .toString();
	}
	
	public void setScrbrif(String scrbrif) {
		this.scrbrif = scrbrif;
	}

	public String getScrbrif() {
		return scrbrif;
	}

	public void setEng_name(String eng_name) {
		this.eng_name = eng_name;
	}

	public String getEng_name() {
		return eng_name;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	public String getInstCode() {
		return instCode;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}


	
}
