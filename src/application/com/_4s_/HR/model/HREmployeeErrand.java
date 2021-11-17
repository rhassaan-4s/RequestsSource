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
@Table(name="hr_employee_errand")
public class HREmployeeErrand implements Auditable,Serializable {

	   public HREmployeeErrand() {
		// TODO Auto-generated constructor stub
	}


	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="hr_employee_accusation_seq")
	   @SequenceGenerator(name="hr_employee_accusation_seq",sequenceName="hr_employee_accusation_seq")//(generate=GeneratorType.IDENTITY)
    private Long id;
    
	   private String observations;
	   private Date fromDate;
	   private Date toDate;
	   
	   @ManyToOne
		@JoinColumn (name="employee")
		private HREmployee employee;
		
		
		
		//***********************************************************************************

		public String getEntityDisplayName() {
			return "employeeErrand Addition";
		}
		
		
		public String toString() {
			 return new ToStringBuilder(this)
			 .append("employee", this.employee)
			 .append("fromDate", this.fromDate)
			 .toString();
		}
		public boolean equals(Object o) {
			 if (o == this) {
			 return true;
			 }
			 if (!(o instanceof HREmployeeErrand)) {
			 return false;
			 }
			 HREmployeeErrand rhs = (HREmployeeErrand) o;
			 return new EqualsBuilder()
			 .append(this.employee, rhs.getEmployee())
			 .append(this.observations, rhs.getObservations())
			 .isEquals();
		}
		public int hashCode() {
			return new HashCodeBuilder(991383961, 1226766147)
			.append(this.id)

			.toHashCode();
		}
		
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}


		public String getObservations() {
			return observations;
		}


		public void setObservations(String observations) {
			this.observations = observations;
		}


		public Date getFromDate() {
			return fromDate;
		}


		public void setFromDate(Date fromDate) {
			this.fromDate = fromDate;
		}


		public Date getToDate() {
			return toDate;
		}


		public void setToDate(Date toDate) {
			this.toDate = toDate;
		}


		public HREmployee getEmployee() {
			return employee;
		}


		public void setEmployee(HREmployee employee) {
			this.employee = employee;
		}


}
