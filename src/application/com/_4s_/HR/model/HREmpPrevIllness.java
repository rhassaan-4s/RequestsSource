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
@Table(name="emp_prev_illness")
public class HREmpPrevIllness implements Auditable,Serializable {

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HR_SEQ_PREV_ILLNESS")
	@SequenceGenerator(name="HR_SEQ_PREV_ILLNESS",sequenceName="HR_SEQ_PREV_ILLNESS")//(generate=GeneratorType.IDENTITY)
   private  Long id ;
	private  String empcode;
	private  String empName;
	private Double  val ;
	

	public String getEntityDisplayName() {
		return "HREmpPrevIllness Addition";
	}
	
	
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("empCode", this.empcode)
		 
		 .toString();
	}
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HREmpPrevIllness)) {
		 return false;
		 }
		 HREmpPrevIllness rhs = (HREmpPrevIllness) o;
		 return new EqualsBuilder()
		 
		 .append(this.empcode, rhs.getEmpcode())
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
	public String getEmpcode() {
		return empcode;
	}
	public void setEmpcode(String empcode) {
		this.empcode = empcode;
	}
	public Double getVal() {
		return val;
	}
	public void setVal(Double val) {
		this.val = val;
	}


	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	

}
