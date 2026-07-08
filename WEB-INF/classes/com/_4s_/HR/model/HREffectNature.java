package com._4s_.HR.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.util.LocaleUtil;

@Entity//(access=AccessType.FIELD)
@Table(name="hr_effect_nature")
public class HREffectNature implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HR_effect_nature_SEQ")
	@SequenceGenerator(name="HR_effect_nature_SEQ",sequenceName="HR_effect_nature_SEQ")
	private Long id;
	private String name;
	private String ename;
	
	public String getEntityDisplayName() {
		return "EffectNature Addition";
	}
	
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("name", this.name)
		 .append("ename", this.ename)
		 .toString();
	}
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HREffectNature)) {
		 return false;
		 }
		 HREffectNature rhs = (HREffectNature) o;
		 return new EqualsBuilder()
		 .append(this.name, rhs.getName())
		 .append(this.ename, rhs.getEname())
		 .isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getId())
		.toHashCode();
	}
	
	@Transient
	public String getName(){
		LocaleUtil localeUtil = LocaleUtil.getInstance();
		if(localeUtil.getLocale() != null && localeUtil.getLocale().equalsIgnoreCase("en")) {
			return getEname();
		}
		return name;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
}
