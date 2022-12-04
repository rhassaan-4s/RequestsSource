package com._4s_.attendance.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.model.EmpBasic;
import com._4s_.common.model.EmpBasicParent;
import com.ibm.icu.util.Calendar;

@Entity
@Table (name = "emp_work_period")

public class EmpWorkPeriod implements Serializable,Auditable {
	@Id
	@ManyToOne
	@JoinColumn(name="emp_code")
	private EmpBasic emp_code;
	@Id
	private Timestamp start_date=new Timestamp(Calendar.getInstance().getTimeInMillis());
	private Timestamp end_date;//=Timestamp.valueOf("2050-12-31 23:59:59");
	@ManyToOne
	@JoinColumn(name = "work_period",referencedColumnName = "workperiods")
	private WorkPeriodMaster work_period;
	private Integer cr_year;
	private Integer cr_month;
	
	public Long getId() {
		return null;
	}

	public WorkPeriodMaster getWork_period() {
		return work_period;
	}



	public void setWork_period(WorkPeriodMaster work_period) {
		this.work_period = work_period;
	}



	public Timestamp getStart_date() {
		return start_date;
	}




	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}




	public Timestamp getEnd_date() {
		return end_date;
	}




	public void setEnd_date(Timestamp end_date) {
		this.end_date = end_date;
	}

	public EmpBasic getEmp_code() {
		return emp_code;
	}



	public void setEmp_code(EmpBasic emp_code) {
		this.emp_code = emp_code;
	}



	public Integer getCr_year() {
		return cr_year;
	}



	public void setCr_year(Integer cr_year) {
		this.cr_year = cr_year;
	}



	public Integer getCr_month() {
		return cr_month;
	}



	public void setCr_month(Integer cr_month) {
		this.cr_month = cr_month;
	}



	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "work periods";
	}

	public String toString() {
		System.out.println("tostring");
		return new ToStringBuilder(this)
		.append("code", this.emp_code)
		.append("startdate", this.start_date)
		.toString();
	}

	public boolean equals(Object o) {
		System.out.println("equals");
		if (o == this) {
			return true;
		}
		if (!(o instanceof EmpWorkPeriod)) {
			return false;
		}
		EmpWorkPeriod rhs = (EmpWorkPeriod) o;
		return new EqualsBuilder().append(this.emp_code, rhs.getEmp_code()).append(this.start_date, rhs.getStart_date()).isEquals();
	}

	public int hashCode() {
		System.out.println("hashCode");
		return new HashCodeBuilder(2090939697, 874530185).append(this.emp_code).append(this.start_date).toHashCode();
	}

}
