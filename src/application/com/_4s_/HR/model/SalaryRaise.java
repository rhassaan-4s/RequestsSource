package com._4s_.HR.model;

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

import com._4s_.common.util.LocaleUtil;

@Entity//(access=AccessType.FIELD)
@Table(name="hr_salraise")
public class SalaryRaise {

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_salraise_seq")
	@SequenceGenerator(name="hr_salraise_seq",sequenceName="hr_salraise_seq")//(generate=GeneratorType.IDENTITY)
	private  Long id ;
	
	private String salraise  ;
	private String name   ;
	private  String ename ;
	private Boolean stype ;
	
	public String getEntityDisplayName() {
		return "salraise Addition";
	}
	
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("name", this.name)
		 .append("ename", this.ename)
		 .toString();
	}
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof SalaryRaise)) {
		 return false;
		 }
		 SalaryRaise rhs = (SalaryRaise) o;
		 return new EqualsBuilder()
		 .append(this.name, rhs.getName())
		 .append(this.ename, rhs.getEname())
		 .isEquals();
	}
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.salraise)

		.toHashCode();
	}
	
	@Transient
	public String getNameForCertainLocale(){
		LocaleUtil  localeUtil = LocaleUtil.getInstance();
		if(localeUtil.getLocale() != null && localeUtil.getLocale().equalsIgnoreCase("en")) {
			return getEname();
		}
		return getName();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSalraise() {
		return salraise;
	}
	public void setSalraise(String salraise) {
		this.salraise = salraise;
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
	
	public Boolean getStype() {
		return stype;
	}
	public void setStype(Boolean stype) {
		this.stype = stype;
	}
}
