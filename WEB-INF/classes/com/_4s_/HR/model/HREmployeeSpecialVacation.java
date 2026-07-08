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
@Table(name="hr_employee_special_vacation")
public class HREmployeeSpecialVacation implements Auditable,Serializable {

	   @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_emp_special_vac_seq")
	   @SequenceGenerator(name="hr_emp_special_vac_seq",sequenceName="hr_emp_special_vac_seq")
      private Long id;
	 
	   private String decisionNumber;
	   private Date decisionDate;
	   private Date fromDate;
	   private Date toDate;
	   
		@ManyToOne
		@JoinColumn (name="employee")
		private HREmployee employee;
		
		@ManyToOne
		@JoinColumn (name="vacation")
		private HRVacation vacation;
		
		
		
    
		//***********************************************************************************

		public String getEntityDisplayName() {
			return "employeeAccusation Addition";
		}
		
		
		public String toString() {
			 return new ToStringBuilder(this)
			 .append("employee", this.employee)
			 .append("vacation", this.vacation)
			 .toString();
		}
		public boolean equals(Object o) {
			 if (o == this) {
			 return true;
			 }
			 if (!(o instanceof HREmployeeSpecialVacation)) {
			 return false;
			 }
			 HREmployeeSpecialVacation rhs = (HREmployeeSpecialVacation) o;
			 return new EqualsBuilder()
			 .append(this.employee, rhs.getEmployee())
			 .append(this.vacation, rhs.getVacation())
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


		public String getDecisionNumber() {
			return decisionNumber;
		}


		public void setDecisionNumber(String decisionNumber) {
			this.decisionNumber = decisionNumber;
		}


		public Date getDecisionDate() {
			return decisionDate;
		}


		public void setDecisionDate(Date decisionDate) {
			this.decisionDate = decisionDate;
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


		public HRVacation getVacation() {
			return vacation;
		}


		public void setVacation(HRVacation vacation) {
			this.vacation = vacation;
		}

}
