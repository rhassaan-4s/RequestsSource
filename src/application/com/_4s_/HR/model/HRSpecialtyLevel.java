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
@Table(name="hr_specialty_level")
public class HRSpecialtyLevel implements Auditable,Serializable {
	
	public HRSpecialtyLevel() {
		// TODO Auto-generated constructor stub
	}
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_specialty_level_seq")
	@SequenceGenerator(name="hr_specialty_level_seq",sequenceName="hr_specialty_level_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
	private Integer levelNo;
	private Integer length=3;
	private Boolean isLastLevel;
	
	private String name;
	private String ename;
	
	public String getEntityDisplayName() {
		return this.getLevelNo().toString();
	}
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("levelNo", this.levelNo)
		 
		 .toString();
	}
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HRSpecialtyLevel)) {
		 return false;
		 }
		 HRSpecialtyLevel rhs = (HRSpecialtyLevel) o;
		 return new EqualsBuilder()
		 .append(this.levelNo, rhs.getLevelNo())
		 .isEquals();
	}
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getId())

		.toHashCode();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getLevelNo() {
		return levelNo;
	}
	public void setLevelNo(Integer levelNo) {
		this.levelNo = levelNo;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Boolean getIsLastLevel() {
		return isLastLevel;
	}
	public void setIsLastLevel(Boolean isLastLevel) {
		this.isLastLevel = isLastLevel;
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
	


}
