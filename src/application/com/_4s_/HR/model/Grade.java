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
@Table(name="hr_grade")
public class Grade {

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_grade_seq")
	@SequenceGenerator(name="hr_grade_seq",sequenceName="hr_grade_seq")//(generate=GeneratorType.IDENTITY)
	private  Long id ;
	
	private  String grade;
	private  String name;                                            
	private  String ename ;                                           
	private  Long min_val ;
	
	public String getEntityDisplayName() {
		return "degree Addition";
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
		 if (!(o instanceof Grade)) {
		 return false;
		 }
		 Grade rhs = (Grade) o;
		 return new EqualsBuilder()
		 .append(this.name, rhs.getName())
		 .append(this.ename, rhs.getEname())
		 .isEquals();
	}
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.grade)

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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
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
	
	public Long getMin_val() {
		return min_val;
	}
	public void setMin_val(Long min_val) {
		this.min_val = min_val;
	}

}
