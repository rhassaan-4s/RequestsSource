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
@Table(name="HR__COURSE_SPECIALTY")
public class HRCourseSpecialty implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="hr_coursesp_seq")
	@SequenceGenerator(name="hr_coursesp_seq",sequenceName="hr_coursesp_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
    private String special;
	private String name;
	private String ename;

	
	public HRCourseSpecialty() {
		// TODO Auto-generated constructor stub
	}
	public String getEntityDisplayName() {
		return "courseSpecialty  Addition";
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
		 if (!(o instanceof HRCourseSpecialty )) {
		 return false;
		 }
		 HRCourseSpecialty rhs = (HRCourseSpecialty) o;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
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
