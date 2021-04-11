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
@Table(name="a_insurance_cala")
public class AInsuranceCala implements Auditable,Serializable {

	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="a_insurance_cala_seq")
	@SequenceGenerator(name="a_insurance_cala_seq",sequenceName="a_insurance_cala_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
	
	private String emp_code;
	private Double basic_sal;
	private Double tot_value;
	private Double basic_co;
	private Double basic_emp;
	private Double var_co;
	private Double var_emp;
	
	private Double basic_co2;
	private Double basic_co3;
	private Double basic_co4;
	private Double basic_co5;
	private Double basic_co6;

	private Double var_co2;
	private Double var_co3;
	private Double var_co4;
	private Double var_co5;
	private Double var_co6;
	
	private Double basic_emp2;
	private Double basic_emp3;
	private Double basic_emp4;

	private Double var_emp2;
	private Double var_emp3;
	private Double var_emp4;
	
	private Double more_base_sal;
	private Double more_var_sal;
	private Double more_base;
	private Double more_var;
	
	private String month;
	private String year;

	@Transient
	private String empName;
	
	@Transient
	private String insuranceNo;
	
	@Transient
	private String insuranceCode;
	
	@Transient
	private String subscriptionDate;
	
	public String getEntityDisplayName() {
		return "AInsuranceCala Addition";
	}
	
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("id", this.id)
		 .append("month", this.month)
		 .append("year", this.year)
		 .toString();
	}
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
			 return true;
		 }
		 if (!(o instanceof AInsuranceCala)) {
			 return false;
		 }
		 AInsuranceCala aic = (AInsuranceCala) o;
		 return new EqualsBuilder()
		 .append(this.id, aic.getId())
		 .append(this.month, aic.getMonth())
		 .append(this.year, aic.getYear())
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

	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}
	
	public Double getBasic_sal() {
		return basic_sal;
	}

	public void setBasic_sal(Double basic_sal) {
		this.basic_sal = basic_sal;
	}

	public Double getTot_value() {
		return tot_value;
	}

	public void setTot_value(Double tot_value) {
		this.tot_value = tot_value;
	}

	public Double getBasic_co() {
		return basic_co;
	}

	public void setBasic_co(Double basic_co) {
		this.basic_co = basic_co;
	}

	public Double getBasic_emp() {
		return basic_emp;
	}

	public void setBasic_emp(Double basic_emp) {
		this.basic_emp = basic_emp;
	}

	public Double getVar_co() {
		return var_co;
	}

	public void setVar_co(Double var_co) {
		this.var_co = var_co;
	}

	public Double getVar_emp() {
		return var_emp;
	}

	public void setVar_emp(Double var_emp) {
		this.var_emp = var_emp;
	}

	public Double getBasic_co2() {
		return basic_co2;
	}

	public void setBasic_co2(Double basic_co2) {
		this.basic_co2 = basic_co2;
	}

	public Double getBasic_co3() {
		return basic_co3;
	}

	public void setBasic_co3(Double basic_co3) {
		this.basic_co3 = basic_co3;
	}

	public Double getBasic_co4() {
		return basic_co4;
	}

	public void setBasic_co4(Double basic_co4) {
		this.basic_co4 = basic_co4;
	}

	public Double getBasic_co5() {
		return basic_co5;
	}

	public void setBasic_co5(Double basic_co5) {
		this.basic_co5 = basic_co5;
	}

	public Double getBasic_co6() {
		return basic_co6;
	}

	public void setBasic_co6(Double basic_co6) {
		this.basic_co6 = basic_co6;
	}

	public Double getVar_co2() {
		return var_co2;
	}

	public void setVar_co2(Double var_co2) {
		this.var_co2 = var_co2;
	}

	public Double getVar_co3() {
		return var_co3;
	}

	public void setVar_co3(Double var_co3) {
		this.var_co3 = var_co3;
	}

	public Double getVar_co4() {
		return var_co4;
	}

	public void setVar_co4(Double var_co4) {
		this.var_co4 = var_co4;
	}

	public Double getVar_co5() {
		return var_co5;
	}

	public void setVar_co5(Double var_co5) {
		this.var_co5 = var_co5;
	}

	public Double getVar_co6() {
		return var_co6;
	}

	public void setVar_co6(Double var_co6) {
		this.var_co6 = var_co6;
	}

	public Double getBasic_emp2() {
		return basic_emp2;
	}

	public void setBasic_emp2(Double basic_emp2) {
		this.basic_emp2 = basic_emp2;
	}

	public Double getBasic_emp3() {
		return basic_emp3;
	}

	public void setBasic_emp3(Double basic_emp3) {
		this.basic_emp3 = basic_emp3;
	}

	public Double getBasic_emp4() {
		return basic_emp4;
	}

	public void setBasic_emp4(Double basic_emp4) {
		this.basic_emp4 = basic_emp4;
	}

	public Double getVar_emp2() {
		return var_emp2;
	}

	public void setVar_emp2(Double var_emp2) {
		this.var_emp2 = var_emp2;
	}

	public Double getVar_emp3() {
		return var_emp3;
	}

	public void setVar_emp3(Double var_emp3) {
		this.var_emp3 = var_emp3;
	}

	public Double getVar_emp4() {
		return var_emp4;
	}

	public void setVar_emp4(Double var_emp4) {
		this.var_emp4 = var_emp4;
	}

	public Double getMore_base_sal() {
		return more_base_sal;
	}

	public void setMore_base_sal(Double more_base_sal) {
		this.more_base_sal = more_base_sal;
	}

	public Double getMore_var_sal() {
		return more_var_sal;
	}

	public void setMore_var_sal(Double more_var_sal) {
		this.more_var_sal = more_var_sal;
	}

	public Double getMore_base() {
		return more_base;
	}

	public void setMore_base(Double more_base) {
		this.more_base = more_base;
	}

	public Double getMore_var() {
		return more_var;
	}

	public void setMore_var(Double more_var) {
		this.more_var = more_var;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public String getInsuranceCode() {
		return insuranceCode;
	}

	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
	}

	public String getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(String subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}
}