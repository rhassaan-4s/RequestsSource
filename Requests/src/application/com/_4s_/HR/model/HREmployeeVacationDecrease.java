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
@Table(name="hr_employee_vacation_decrease")
public class HREmployeeVacationDecrease implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="hr_emp_vac_decrease_seq")
	@SequenceGenerator(name="hr_emp_vac_decrease_seq",sequenceName="hr_emp_vac_decrease_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
    
	private Long days;
	private String reason;
	
	  

		@ManyToOne 
		@JoinColumn (name="employee")
		private HREmployee employee;
		
		@ManyToOne 
		@JoinColumn (name="vacation")
		private HRVacation vacation;
		
		
		public String getEntityDisplayName() {
			return "Employee_vacation_decrease Addition";
		}
		@Override
		public String toString() {
			 return new ToStringBuilder(this)
			 .append("employee", this.employee)
			 .append("vacation", this.vacation)
			 .toString();
		}
		@Override
		public boolean equals(Object o) {
			 if (o == this) {
			 return true;
			 }
			 if (!(o instanceof HREmployeeVacationDecrease)) {
			 return false;
			 }
			 HREmployeeVacationDecrease rhs = (HREmployeeVacationDecrease) o;
			 return new EqualsBuilder()
			 .append(this.employee.getName(), rhs.employee.getName())
			 .append(this.employee.getEname(), rhs.employee.getEname())
			 .isEquals();
		}
		@Override
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
		public Long getDays() {
			return days;
		}
		public void setDays(Long days) {
			this.days = days;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public HRVacation getVacation() {
			return vacation;
		}
		public void setVacation(HRVacation vacation) {
			this.vacation = vacation;
		}
}
