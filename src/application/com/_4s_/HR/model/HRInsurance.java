package com._4s_.HR.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="hr_insurance")
public class HRInsurance implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_insurance_seq")
	@SequenceGenerator(name="hr_insurance_seq",sequenceName="hr_insurance_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
	
	private String code;
	private String name;
	private String ename;
	
	private Double maxValueFixed=new Double(0);
	private Double minValueChanged=new Double(0);
	private Double minValueFixed=new Double(0);
	private Double maxValueChanged=new Double(0);
	private Double companyRatioFixed=new Double(0);
	private Double companyRatioChanged=new Double(0);
	private Double employeeRatioFixed=new Double(0);
	private Double employeeRatioChanged=new Double(0);
	
	 
	
	public String getEntityDisplayName() {
		return "insurance Addition";
	}
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("name", this.name)
		 .append("ename", this.ename)
		 .toString();
	}
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HRInsurance)) {
		 return false;
		 }
		 HRInsurance rhs = (HRInsurance) o;
		 return new EqualsBuilder()
		 .append(this.name, rhs.getName())
		 .append(this.ename, rhs.getEname())
		 .isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.id)

		.toHashCode();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public Double getMaxValueFixed() {
		return maxValueFixed;
	}
	public void setMaxValueFixed(Double maxValueFixed) {
		this.maxValueFixed = maxValueFixed;
	}
	public Double getMinValueChanged() {
		return minValueChanged;
	}
	public void setMinValueChanged(Double minValueChanged) {
		this.minValueChanged = minValueChanged;
	}
	public Double getMinValueFixed() {
		return minValueFixed;
	}
	public void setMinValueFixed(Double minValueFixed) {
		this.minValueFixed = minValueFixed;
	}
	public Double getMaxValueChanged() {
		return maxValueChanged;
	}
	public void setMaxValueChanged(Double maxValueChanged) {
		this.maxValueChanged = maxValueChanged;
	}
	public Double getCompanyRatioFixed() {
		return companyRatioFixed;
	}
	public void setCompanyRatioFixed(Double companyRatioFixed) {
		this.companyRatioFixed = companyRatioFixed;
	}
	public Double getCompanyRatioChanged() {
		return companyRatioChanged;
	}
	public void setCompanyRatioChanged(Double companyRatioChanged) {
		this.companyRatioChanged = companyRatioChanged;
	}
	public Double getEmployeeRatioFixed() {
		return employeeRatioFixed;
	}
	public void setEmployeeRatioFixed(Double employeeRatioFixed) {
		this.employeeRatioFixed = employeeRatioFixed;
	}
	public Double getEmployeeRatioChanged() {
		return employeeRatioChanged;
	}
	public void setEmployeeRatioChanged(Double employeeRatioChanged) {
		this.employeeRatioChanged = employeeRatioChanged;
	}
	

}
