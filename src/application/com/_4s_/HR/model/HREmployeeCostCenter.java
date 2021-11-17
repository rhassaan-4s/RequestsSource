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
//import com._4s_.gl.model.Account;

@Entity
@Table(name = "HR_EMPLOYEE_COSTCENTER")
public class HREmployeeCostCenter implements Auditable, Serializable {

	public HREmployeeCostCenter() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "hr_empCostCenter_seq")
	@SequenceGenerator(name = "hr_empCostCenter_seq", sequenceName = "hr_empCostCenter_seq")
	private Long id;

//	@ManyToOne
//	@JoinColumn(name = "costCenter")
//	private Account costCenter;
	private Date fromDate;
	private Date toDate;
	private Double percent=new Double(0);
	
	@ManyToOne
	@JoinColumn(name = "employee")
	private HREmployee employee;

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

//	public Account getCostCenter() {
//		return costCenter;
//	}
//
//	public void setCostCenter(Account costCenter) {
//		this.costCenter = costCenter;
//	}

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

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public String getEntityDisplayName() {
		return "employeeCostCenter  Addition";
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("emplyee",
				this.employee.getName())

		.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof HREmployeeCostCenter)) {
			return false;
		}
		HREmployeeCostCenter rhs = (HREmployeeCostCenter) o;
		return new EqualsBuilder().append(this.employee.getName(),
				rhs.employee.getName()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147).append(this.getId())

		.toHashCode();
	}
}
