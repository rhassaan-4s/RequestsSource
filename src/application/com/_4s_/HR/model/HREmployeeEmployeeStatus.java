/**
 * 
 */
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

/**
 * @author hoda
 *
 */

@Entity//(access=AccessType.FIELD)
@Table(name="hr_employee_employee_status")
public class HREmployeeEmployeeStatus implements Auditable,Serializable {
		
		@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_employee_emp_status_seq")
		@SequenceGenerator(name="hr_employee_emp_status_seq",sequenceName="hr_employee_emp_status_seq")
		private Long id;
		
		@ManyToOne 
		@JoinColumn (name="employee")
		private HREmployee employee;
		
		@ManyToOne 
		@JoinColumn (name="employeeStatus")
		private HREmployeeStatus employeeStatus;
		
		private Date statusStartDate;
		private Date statusEndDate;
		
		private String observations;
		
		
		public String getEntityDisplayName() {
			return "Employee_employeeStatus Addition";
		}
		public String toString() {
			 return new ToStringBuilder(this)
			 .append("employee", this.employee)
			 .append("employeeStatus", this.employeeStatus)
			 .toString();
		}
		public boolean equals(Object o) {
			 if (o == this) {
			 return true;
			 }
			 if (!(o instanceof HREmployeeEmployeeStatus)) {
			 return false;
			 }
			 HREmployeeEmployeeStatus rhs = (HREmployeeEmployeeStatus) o;
			 return new EqualsBuilder()
			 .append(this.employee.getName(), rhs.employee.getName())
			 .append(this.employee.getEname(), rhs.employee.getEname())
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
		public HREmployeeStatus getEmployeeStatus() {
			return employeeStatus;
		}
		public void setEmployeeStatus(HREmployeeStatus employeeStatus) {
			this.employeeStatus = employeeStatus;
		}
	
		public String getObservations() {
			return observations;
		}
		public void setObservations(String observations) {
			this.observations = observations;
		}
		public Date getStatusStartDate() {
			return statusStartDate;
		}
		public void setStatusStartDate(Date statusStartDate) {
			this.statusStartDate = statusStartDate;
		}
		public Date getStatusEndDate() {
			return statusEndDate;
		}
		public void setStatusEndDate(Date statusEndDate) {
			this.statusEndDate = statusEndDate;
		}
		

}
