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
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="disc_days")
public class DiscDays implements Auditable,Serializable {
	
	public DiscDays() {
		// TODO Auto-generated constructor stub
	}

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="disc_days_seq")
	@SequenceGenerator(name="disc_days_seq",sequenceName="disc_days_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
	
	private String emp_id; 

	@ManyToOne
	@JoinColumn(name="month")
	private HRMonth month;
	
	@ManyToOne
	@JoinColumn(name="year")
	private HRYear year;

	private Double no_days = 0.0;
	private Long disc_type;
	
	@Transient
	private String empName;
	@Transient
	private Double minute = 0.0; 
	@Transient
	private Double hour = 0.0; 
	@Transient
	private Double day = 0.0; 
	
	public String getEntityDisplayName() {
		return "DiscDays Addition";
	}
	
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("empId", this.emp_id)
		 .append("month", this.month)
		 .append("year", this.year)
		 .append("disc_type", this.disc_type)
		 .toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof DiscDays)) {
			return false;
		}
		DiscDays dd = (DiscDays) o;
		return new EqualsBuilder()
		.append(this.emp_id, dd.getEmp_id())
		.append(this.month, dd.getMonth())
		.append(this.year, dd.getYear())
		.append(this.disc_type, dd.getDisc_type())
		.isEquals();
	}
	
	@Override
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

	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String empId) {
		this.emp_id = empId;
	}

	public HRMonth getMonth() {
		return month;
	}

	public void setMonth(HRMonth month) {
		this.month = month;
	}

	public HRYear getYear() {
		return year;
	}

	public void setYear(HRYear year) {
		this.year = year;
	}

	public Double getNo_days() {
		return no_days;
	}

	public void setNo_days(Double z) {
		this.no_days = z;
	}
	
	public void setNo_days(Double d, Double h, Double m) {
		this.no_days += d + (h/24) + (m/1440);
	}

	public Long getDisc_type() {
		return disc_type;
	}

	public void setDisc_type(Long disc_type) {
		this.disc_type = disc_type;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Double getMinute() {
		return minute;
	}

	public void setMinute(Double minute) {
		this.minute = minute;
		setNo_days(0.0, 0.0, minute);
	}

	public Double getHour() {
		return hour;
	}

	public void setHour(Double hour) {
		this.hour = hour;
		setNo_days(0.0, hour, 0.0);
	}

	public Double getDay() {
		return day;
	}

	public void setDay(Double day) {
		this.day = day;
		setNo_days(day, 0.0, 0.0);
	}
}
