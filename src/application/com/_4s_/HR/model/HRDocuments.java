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
@Table(name="hr_documents")
public class HRDocuments implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_documents_seq")
	@SequenceGenerator(name="hr_documents_seq",sequenceName="hr_documents_seq")
	private Long id;
    private String code;
	private String name;
	private String ename;
	private Boolean hasExpiryDate;
	private String dateType;
	
	public String getEntityDisplayName() {
		return "documents Addition";
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
		 if (!(o instanceof HRDocuments)) {
		 return false;
		 }
		 HRDocuments rhs = (HRDocuments) o;
		 return new EqualsBuilder()
		 .append(this.name, rhs.getName())
		 .append(this.ename, rhs.getEname())
		 .isEquals();
	}
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getId())

		.toHashCode();
	}
	
	@Transient
	public String getNameForCertainLocale(){
		LocaleUtil localeUtil = LocaleUtil.getInstance();
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public Boolean getHasExpiryDate() {
		return hasExpiryDate;
	}
	public void setHasExpiryDate(Boolean hasExpiryDate) {
		this.hasExpiryDate = hasExpiryDate;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
}
