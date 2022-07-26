	package com._4s_.HR.model;

import java.io.Serializable;

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
@Table(name="hr_employee_qualification")

public class HREmployeeQualification implements Auditable,Serializable {
		
		@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_employee_qual_seq")
		@SequenceGenerator(name="hr_employee_qual_seq",sequenceName="hr_employee_qual_seq")//(generate=GeneratorType.IDENTITY)
       
		private Long id;
		
		@ManyToOne 
		@JoinColumn (name="employee")
		private HREmployee employee;
		
		@ManyToOne
		@JoinColumn (name="qualification" )
		private HRQualificationDivision qualification;
		
		@ManyToOne
		@JoinColumn (name="specialty")
		private HRSpecialtyDivision specialty;
		
		private String additional_Description;
		
		private String qualification_date;
		
		
		
		public String getEntityDisplayName() {
			return "empQual Addition";
		}
		public String toString() {
			 return new ToStringBuilder(this)
			 .append("addDescription", this.additional_Description)
			 .toString();
		}
		public boolean equals(Object o) {
			 if (o == this) {
			 return true;
			 }
			 if (!(o instanceof HREmployeeQualification)) {
			 return false;
			 }
			 HREmployeeQualification rhs = (HREmployeeQualification) o;
			 return new EqualsBuilder()
			 .append(this.additional_Description, rhs.getAdditional_Description())
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
		public HRQualificationDivision getQualification() {
			return qualification;
		}
		public void setQualification(
				HRQualificationDivision qualification) {
			this.qualification = qualification;
		}
		
		public HRSpecialtyDivision getSpecialty() {
			return specialty;
		}
		public void setSpecialty(HRSpecialtyDivision specialty) {
			this.specialty = specialty;
		}
		public String getAdditional_Description() {
			return additional_Description;
		}
		public void setAdditional_Description(String additional_Description) {
			this.additional_Description = additional_Description;
		}
		public String getQualification_date() {
			return qualification_date;
		}
		public void setQualification_date(String qualification_date) {
			this.qualification_date = qualification_date;
		}

}
