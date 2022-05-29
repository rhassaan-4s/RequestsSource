package com._4s_.HR.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;


@Entity//(access=AccessType.FIELD)
@Table(name="hr_employee_course")
public class HREmployeeCourse implements Auditable,Serializable {

   @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_employee_course_seq")
   @SequenceGenerator(name="hr_employee_course_seq",sequenceName="hr_employee_course_seq")//(generate=GeneratorType.IDENTITY)

    private Long id;
   
   

	@ManyToOne
	@JoinColumn (name="employee")
	private HREmployee employee;
	
	
	private HRSpecialtyDivision course;
	
	private HRCourseOrganization courseOrganization;
	
	private String observations;
	
	
	public String getEntityDisplayName() {
		return "empCourse Addition";
	}
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("course", this.course.getArdesc().toString())
		  .append("organization", this.courseOrganization.getName().toString())
		 .toString();
	}
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HREmployeeCourse)) {
		 return false;
		 }
		 HREmployeeCourse rhs = (HREmployeeCourse) o;
		 return new EqualsBuilder()
		 .append(this.course, rhs.getCourse().getArdesc())
		 .append(this.courseOrganization, rhs.getOrganization().getName())
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
	public HREmployee getEmployee() {
		return employee;
	}
	public void setEmployee(HREmployee employee) {
		this.employee = employee;
	}
	public HRSpecialtyDivision getCourse() {
		return course;
	}
	public void setCourse(HRSpecialtyDivision course) {
		this.course = course;
	}
	public HRCourseOrganization getOrganization() {
		return courseOrganization;
	}
	public void setOrganization(HRCourseOrganization organization) {
		this.courseOrganization = organization;
	}
	public String getObservations() {
		return observations;
	}
	public void setObservations(String observations) {
		this.observations = observations;
	}

}
