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
@Table(name="tax")
public class HRTax implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_tax_seq")
	@SequenceGenerator(name="hr_tax_seq",sequenceName="hr_tax_seq")
     private Long id ;
	
	private String effCode  ;
	private String effName ;
	private String eng_name;
	private Long effType  ;
	private Long effNature;
	private Long scrpos  ;
	private Long prnpos  ;
	private String scrbrif ;
	private String prnbrif ;
	private Double min1 ;
	private Double min2 ;
	private Double min3 ;
	private Double min4 ;
	private Double min5 ;
	private Double min6 ;
	private Double max1 ;
	private Double max2 ;
	private Double max3 ;
	private Double max4 ;
	private Double max5 ;
	private Double max6 ;
	private Double p1  ;
	private Double p2  ;
	private Double p3  ;
	private Double p4  ;
	private Double p5  ;
	private Double p6; 
	private Double single;
	private Double married;
	private Double haveChild;
	private Double discount_val;
	private Double dicount_ratio;
	private Double more_tax_discount;
	private String factorise_marital;//Dafault 'T'
	
	
	public String getEntityDisplayName() {
		return "tax Addition";
	}
	
	
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("effName", this.effName)
		 
		 .toString();
	}
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HRTax)) {
		 return false;
		 }
		 HRTax rhs = (HRTax) o;
		 return new EqualsBuilder()
		 
		 .append(this.effName, rhs.getEffName())
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
	public String getEffCode() {
		return effCode;
	}
	public void setEffCode(String effCode) {
		this.effCode = effCode;
	}
	public String getEffName() {
		return effName;
	}
	public void setEffName(String effName) {
		this.effName = effName;
	}
	public String getEng_name() {
		return eng_name;
	}
	public void setEng_name(String eng_name) {
		this.eng_name = eng_name;
	}
	public Long getEffType() {
		return effType;
	}
	public void setEffType(Long effType) {
		this.effType = effType;
	}
	public Long getEffNature() {
		return effNature;
	}
	public void setEffNature(Long effNature) {
		this.effNature = effNature;
	}
	public Long getScrpos() {
		return scrpos;
	}
	public void setScrpos(Long scrpos) {
		this.scrpos = scrpos;
	}
	public Long getPrnpos() {
		return prnpos;
	}
	public void setPrnpos(Long prnpos) {
		this.prnpos = prnpos;
	}
	public String getScrbrif() {
		return scrbrif;
	}
	public void setScrbrif(String scrbrif) {
		this.scrbrif = scrbrif;
	}
	public String getPrnbrif() {
		return prnbrif;
	}
	public void setPrnbrif(String prnbrif) {
		this.prnbrif = prnbrif;
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
	public Double getMin5() {
		return min5;
	}
	public void setMin5(Double min5) {
		this.min5 = min5;
	}
	public Double getMin6() {
		return min6;
	}
	public void setMin6(Double min6) {
		this.min6 = min6;
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
	public Double getMax5() {
		return max5;
	}
	public void setMax5(Double max5) {
		this.max5 = max5;
	}
	public Double getMax6() {
		return max6;
	}
	public void setMax6(Double max6) {
		this.max6 = max6;
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
	public Double getP5() {
		return p5;
	}
	public void setP5(Double p5) {
		this.p5 = p5;
	}
	public Double getP6() {
		return p6;
	}
	public void setP6(Double p6) {
		this.p6 = p6;
	}
	
	
	public Double getSingle() {
		return single;
	}
	public void setSingle(Double single) {
		this.single = single;
	}
	public Double getMarried() {
		return married;
	}
	public void setMarried(Double married) {
		this.married = married;
	}
	public Double getHaveChild() {
		return haveChild;
	}
	public void setHaveChild(Double haveChild) {
		this.haveChild = haveChild;
	}
	public Double getDiscount_val() {
		return discount_val;
	}
	public void setDiscount_val(Double discount_val) {
		this.discount_val = discount_val;
	}
	public Double getDicount_ratio() {
		return dicount_ratio;
	}
	public void setDicount_ratio(Double dicount_ratio) {
		this.dicount_ratio = dicount_ratio;
	}
	public Double getMore_tax_discount() {
		return more_tax_discount;
	}
	public void setMore_tax_discount(Double more_tax_discount) {
		this.more_tax_discount = more_tax_discount;
	}
	public String getFactorise_marital() {
		return factorise_marital;
	}
	public void setFactorise_marital(String factorise_marital) {
		this.factorise_marital = factorise_marital;
	}
	
	
	         
}
