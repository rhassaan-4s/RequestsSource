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
@Table(name="hr_employee_accusation")
public class HREmployeeAccusation implements Auditable,Serializable {

	   @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_employee_accusation_seq")
	   @SequenceGenerator(name="hr_employee_accusation_seq",sequenceName="hr_employee_accusation_seq")//(generate=GeneratorType.IDENTITY)
       private Long id;
       
	   private String accusationNumber;
	   private String accusationSummary;
	   private Date accusationDate;
	   private Date executionDate;
	   private String policeMinutesNumber;
	   private String judge;
	  
	   
	   
	   @ManyToOne
		@JoinColumn (name="finalAction")
	   private HRAccusationAction finalAction;
	  
		@ManyToOne
		@JoinColumn (name="employee")
		private HREmployee employee;

	//***********************************************************************************

		public String getEntityDisplayName() {
			return "employeeAccusation Addition";
		}
		
		
		public String toString() {
			 return new ToStringBuilder(this)
			 .append("employee", this.employee)
			 .append("judge", this.judge)
			 .toString();
		}
		public boolean equals(Object o) {
			 if (o == this) {
			 return true;
			 }
			 if (!(o instanceof HREmployeeAccusation)) {
			 return false;
			 }
			 HREmployeeAccusation rhs = (HREmployeeAccusation) o;
			 return new EqualsBuilder()
			 .append(this.employee, rhs.getEmployee())
			 .append(this.judge, rhs.getJudge())
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

		public String getAccusationNumber() {
			return accusationNumber;
		}

		public void setAccusationNumber(String accusationNumber) {
			this.accusationNumber = accusationNumber;
		}

		public String getAccusationSummary() {
			return accusationSummary;
		}

		public void setAccusationSummary(String accusationSummary) {
			this.accusationSummary = accusationSummary;
		}

		public Date getAccusationDate() {
			return accusationDate;
		}

		public void setAccusationDate(Date accusationDate) {
			this.accusationDate = accusationDate;
		}

		public Date getExecutionDate() {
			return executionDate;
		}

		public void setExecutionDate(Date executionDate) {
			this.executionDate = executionDate;
		}

		public String getPoliceMinutesNumber() {
			return policeMinutesNumber;
		}

		public void setPoliceMinutesNumber(String policeMinutesNumber) {
			this.policeMinutesNumber = policeMinutesNumber;
		}

		public String getJudge() {
			return judge;
		}

		public void setJudge(String judge) {
			this.judge = judge;
		}

		public HRAccusationAction getFinalAction() {
			return finalAction;
		}

		public void setFinalAction(HRAccusationAction finalAction) {
			this.finalAction = finalAction;
		}

		public HREmployee getEmployee() {
			return employee;
		}

		public void setEmployee(HREmployee employee) {
			this.employee = employee;
		}
		
}
