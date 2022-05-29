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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="hr_Additional")
public class HRAdditional implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HR_ADDITIONAL_SEQ")
	@SequenceGenerator(name="HR_ADDITIONAL_SEQ",sequenceName="HR_ADDITIONAL_SEQ")//(generate=GeneratorType.IDENTITY)
     private Long id;
	
	private Boolean nightApply;
	private   Double nightHour=new Double(00.00);
	private Double nightDay =new Double(00.00);  
	private Boolean  dayApply;
	private  Double  dayHour = new Double(00.00); 
	private  Double  dayDay =new Double(00.00); 
	private Boolean  vacationApply ;
	private  Double  vacationHour  =new Double(00.00);   
	private  Double  vacationDay =new Double(00.00);  
	
	@ManyToOne
	@JoinColumn (name="additionalCalcWayNight")
	private HRAdditionalCalcWay  additionalCalcWayNight;
	@ManyToOne
	@JoinColumn (name="additionalCalcWayDay")
	private HRAdditionalCalcWay  additionalCalcWayDay ;
	@ManyToOne
	@JoinColumn (name="additionalCalcWayVacation")
	private HRAdditionalCalcWay  additionalCalcWayVacation ;
	private  Double  calcNightBegin  =new Double(00.00);           
	private  Double  calcNightEnd =new Double(00.00);             
	private  Double  maxValue =new Double(00.00); 
	
	@ManyToOne
	@JoinColumn (name="additionalCalcMaxValue")
	  private HRAdditionalCalcWay additionalCalcMaxValue ;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getNightApply() {
		return nightApply;
	}

	public void setNightApply(Boolean nightApply) {
		this.nightApply = nightApply;
	}

	public Double getNightHour() {
		return nightHour;
	}

	public void setNightHour(Double nightHour) {
		this.nightHour = nightHour;
	}

	public Double getNightDay() {
		return nightDay;
	}

	public void setNightDay(Double nightDay) {
		this.nightDay = nightDay;
	}

	public Boolean getDayApply() {
		return dayApply;
	}

	public void setDayApply(Boolean dayApply) {
		this.dayApply = dayApply;
	}

	public Double getDayHour() {
		return dayHour;
	}

	public void setDayHour(Double dayHour) {
		this.dayHour = dayHour;
	}

	public Double getDayDay() {
		return dayDay;
	}

	public void setDayDay(Double dayDay) {
		this.dayDay = dayDay;
	}

	public Boolean getVacationApply() {
		return vacationApply;
	}

	public void setVacationApply(Boolean vacationApply) {
		this.vacationApply = vacationApply;
	}

	public Double getVacationHour() {
		return vacationHour;
	}

	public void setVacationHour(Double vacationHour) {
		this.vacationHour = vacationHour;
	}

	public Double getVacationDay() {
		return vacationDay;
	}

	public void setVacationDay(Double vactionDay) {
		this.vacationDay = vactionDay;
	}

	public HRAdditionalCalcWay getAdditionalCalcWayNight() {
		return additionalCalcWayNight;
	}

	public void setAdditionalCalcWayNight(HRAdditionalCalcWay additionalCalcWayNight) {
		this.additionalCalcWayNight = additionalCalcWayNight;
	}

	public HRAdditionalCalcWay getAdditionalCalcWayDay() {
		return additionalCalcWayDay;
	}

	public void setAdditionalCalcWayDay(HRAdditionalCalcWay additionalCalcWayDay) {
		this.additionalCalcWayDay = additionalCalcWayDay;
	}

	public HRAdditionalCalcWay getAdditionalCalcWayVacation() {
		return additionalCalcWayVacation;
	}

	public void setAdditionalCalcWayVacation(
			HRAdditionalCalcWay additionalCalcWayVacation) {
		this.additionalCalcWayVacation = additionalCalcWayVacation;
	}

	public Double getCalcNightBegin() {
		return calcNightBegin;
	}

	public void setCalcNightBegin(Double calcNightBegin) {
		this.calcNightBegin = calcNightBegin;
	}

	public Double getCalcNightEnd() {
		return calcNightEnd;
	}

	public void setCalcNightEnd(Double calcNightEnd) {
		this.calcNightEnd = calcNightEnd;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public HRAdditionalCalcWay getAdditionalCalcMaxValue() {
		return additionalCalcMaxValue;
	}

	public void setAdditionalCalcMaxValue(HRAdditionalCalcWay additionalCalcMaxValue) {
		this.additionalCalcMaxValue = additionalCalcMaxValue;
	}

	
	public String getEntityDisplayName() {
		return "hrAdditional Addition";
	}
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		
		 .toString();
	}
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HRAdditional)) {
		 return false;
		 }
		HRAdditional rhs = (HRAdditional) o;
		 return new EqualsBuilder()
		
		 .isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getId())

		.toHashCode();
	}
	
	
	


}
