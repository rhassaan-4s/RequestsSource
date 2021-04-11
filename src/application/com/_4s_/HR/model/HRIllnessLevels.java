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

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="illness_levels")
public class HRIllnessLevels implements Auditable,Serializable {
	
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="HR_SEQ_ILLNESS_LEVELS")
	@SequenceGenerator(name="HR_SEQ_ILLNESS_LEVELS",sequenceName="HR_SEQ_ILLNESS_LEVELS")//(generate=GeneratorType.IDENTITY)
   private  Long id ;
	
	private Double min1 ;
	private Double min2 ;
	private Double min3 ;
	private Double min4 ;
	private Double max1 ;
	private Double max2 ;
	private Double max3 ;
	private Double max4 ;
	private Double p1  ;
	private Double p2  ;
	private Double p3  ;
	private Double p4  ;
	private Double pv1  ;
	private Double pv2  ;
	private Double pv3  ;
	private Double pv4  ;
	

	public String getEntityDisplayName() {
		return "HRIllnessLevels Addition";
	}
	
	

	@Override
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HRIllnessLevels)) {
		 return false;
		 }
		 HRIllnessLevels rhs = (HRIllnessLevels) o;
		 return new EqualsBuilder()
		 
		 .append(this.min1, rhs.getMin1())
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
	
	public Double getMin1() {
		return min1;
	}
	public void setMin1(Double min1) {
		this.min1 = min1;
	}
	public Double getMin2() {
		return min2;
	}
	public void setMin2(Double min2) {
		this.min2 = min2;
	}
	public Double getMin3() {
		return min3;
	}
	public void setMin3(Double min3) {
		this.min3 = min3;
	}
	public Double getMin4() {
		return min4;
	}
	public void setMin4(Double min4) {
		this.min4 = min4;
	}
	public Double getMax1() {
		return max1;
	}
	public void setMax1(Double max1) {
		this.max1 = max1;
	}
	public Double getMax2() {
		return max2;
	}
	public void setMax2(Double max2) {
		this.max2 = max2;
	}
	public Double getMax3() {
		return max3;
	}
	public void setMax3(Double max3) {
		this.max3 = max3;
	}
	public Double getMax4() {
		return max4;
	}
	public void setMax4(Double max4) {
		this.max4 = max4;
	}
	public Double getP1() {
		return p1;
	}
	public void setP1(Double p1) {
		this.p1 = p1;
	}
	public Double getP2() {
		return p2;
	}
	public void setP2(Double p2) {
		this.p2 = p2;
	}
	public Double getP3() {
		return p3;
	}
	public void setP3(Double p3) {
		this.p3 = p3;
	}
	public Double getP4() {
		return p4;
	}
	public void setP4(Double p4) {
		this.p4 = p4;
	}
	public Double getPv1() {
		return pv1;
	}
	public void setPv1(Double pv1) {
		this.pv1 = pv1;
	}
	public Double getPv2() {
		return pv2;
	}
	public void setPv2(Double pv2) {
		this.pv2 = pv2;
	}
	public Double getPv3() {
		return pv3;
	}
	public void setPv3(Double pv3) {
		this.pv3 = pv3;
	}
	public Double getPv4() {
		return pv4;
	}
	public void setPv4(Double pv4) {
		this.pv4 = pv4;
	}


	
	
}
