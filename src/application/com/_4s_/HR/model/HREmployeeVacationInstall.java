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
@Table(name="hr_employee_vacation_install")
public class HREmployeeVacationInstall implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_employee_vacation_inst_seq")
	@SequenceGenerator(name="hr_employee_vacation_inst_seq",sequenceName="hr_employee_vacation_inst_seq")
	private Long id;
	
	@ManyToOne 
	@JoinColumn (name="employee")
	private HREmployee employee;
	
	@ManyToOne 
	@JoinColumn (name="vacation")
	private HRVacation vacation;
	
	private Long balance;
	
	public String getEntityDisplayName() {
		return "Employee_vacation_install Addition";
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
		 if (!(o instanceof HREmployeeVacationInstall)) {
		 return false;
		 }
		 HREmployeeVacationInstall rhs = (HREmployeeVacationInstall) o;
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

	public HRVacation getVacation() {
		return vacation;
	}

	public void setVacation(HRVacation vacation) {
		this.vacation = vacation;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

}
