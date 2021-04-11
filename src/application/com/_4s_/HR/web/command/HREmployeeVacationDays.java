package com._4s_.HR.web.command;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

//@Entity//(access=AccessType.FIELD)
//@Table(name="disc_days")
public class HREmployeeVacationDays implements Serializable {
	
 private String emp_id;
 private Integer month ;
 private Integer year;
 private Double	  no_days;
 private Integer disc_type ;
 
	public String getEntityDisplayName() {
		return "Employee_VacationDays";
	}
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("emp_id", this.emp_id)
		 .append("disc_type", this.disc_type)
		 .toString();
	}
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HREmployeeVacationDays)) {
		 return false;
		 }
		 HREmployeeVacationDays rhs = (HREmployeeVacationDays) o;
		 return new EqualsBuilder()
		 .append(this.month, rhs.month)
		 .append(this.year, rhs.year)
		 .isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getEmp_id())

		.toHashCode();
	}
	
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Double getNo_days() {
		return no_days;
	}
	public void setNo_days(Double no_days) {
		this.no_days = no_days;
	}
	public Integer getDisc_type() {
		return disc_type;
	}
	public void setDisc_type(Integer disc_type) {
		this.disc_type = disc_type;
	}

}
