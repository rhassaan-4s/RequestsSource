package com._4s_.HR.model;

import java.io.Serializable;
import java.util.Calendar;
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
@Table(name="hr_employee_vacation")
public class HREmployeeVacation implements Auditable,Serializable {
	
	
	public HREmployeeVacation() {
		// TODO Auto-generated constructor stub
	}


	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="hr_employee_vacation_seq")
	@SequenceGenerator(name="hr_employee_vacation_seq",sequenceName="hr_employee_vacation_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
	
	private Date fromDate;
	private Date toDate;
	private Long deductedDays;
	
	
	@ManyToOne 
	@JoinColumn (name="employee")
	private HREmployee employee;
	
	@ManyToOne 
	@JoinColumn (name="vacation")
	private HRVacation vacation;
	
	
	public String getEntityDisplayName() {
		return "Employee_vacation Addition";
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
		 if (!(o instanceof HREmployeeVacation)) {
		 return false;
		 }
		 HREmployeeVacation rhs = (HREmployeeVacation) o;
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
	public Long getDeductedDays() {
		return deductedDays;
	}
	public void setDeductedDays(Long deductedDays) {
		this.deductedDays = deductedDays;
	}
	public HRVacation getVacation() {
		return vacation;
	}
	public void setVacation(HRVacation vacation) {
		this.vacation = vacation;
	}
	
	
	public Long getConsumedDays()
	{
		 Calendar cal=Calendar.getInstance();
		if(this.getFromDate()!=null && !this.getFromDate().equals(""))
		 {
        cal.setTime(this.getFromDate());
        cal.add(Calendar.HOUR_OF_DAY,0);
        cal.add(Calendar.MINUTE, 0);
        cal.add(Calendar.SECOND, 0);
        cal.add(Calendar.MILLISECOND,0);
		 }
       
       Calendar cal2=Calendar.getInstance();
       if(this.getToDate()!=null && !this.getToDate().equals(""))
		 {
	       cal2.setTime(this.getToDate());
	       cal2.add(Calendar.HOUR_OF_DAY,0);
	       cal2.add(Calendar.MINUTE, 0);
	       cal2.add(Calendar.SECOND, 0);
	       cal2.add(Calendar.MILLISECOND,0);
		 }
     Integer dayDiff=cal2.get(Calendar.DAY_OF_YEAR)-cal.get(Calendar.DAY_OF_YEAR);
      
   
    return new Long(dayDiff);
		
	}
	

}
