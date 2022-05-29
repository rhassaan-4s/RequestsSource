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
@Table(name="hr_violation_rules")
public class HRViolationRules implements Auditable,Serializable {
	
		@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_violation_rules_seq")
		@SequenceGenerator(name="hr_violation_rules_seq",sequenceName="hr_violation_rules_seq")//(generate=GeneratorType.IDENTITY)
	    private Long id;
		
		
		
		private Integer  period ;
		private Integer from_count;
		private Integer to_count;
		private Double val;
		private Double max_val;
		 
		  
	  @ManyToOne
		@JoinColumn (name="violation")
		private HRViolation violation;
		
		@ManyToOne
		@JoinColumn (name="violationResult")
		private HRViolationResult violationResult;
		
		@ManyToOne
		@JoinColumn (name="period_Type")
		private HRPeriodType periodType;
		
		@ManyToOne
		@JoinColumn (name="CALCULATION_METHOD")
		private HRCalculationMethod calculationMethod;
		
		//**********************************************************************************
		
		public String getEntityDisplayName() {
			return "violation Rules Addition";
		}
		
		
		public String toString() {
			 return new ToStringBuilder(this)
			 .append("violation", this.violation)
			 
			 .toString();
		}
		public boolean equals(Object o) {
			 if (o == this) {
			 return true;
			 }
			 if (!(o instanceof HRViolationRules)) {
			 return false;
			 }
			 HRViolationRules rhs = (HRViolationRules) o;
			 return new EqualsBuilder()
			 .append(this.period, rhs.getPeriod())
			 .isEquals();
		}
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


		public Integer getPeriod() {
			return period;
		}


		public void setPeriod(Integer period) {
			this.period = period;
		}


		public Integer getFrom_count() {
			return from_count;
		}


		public void setFrom_count(Integer from_count) {
			this.from_count = from_count;
		}


		public Integer getTo_count() {
			return to_count;
		}


		public void setTo_count(Integer to_count) {
			this.to_count = to_count;
		}




		public HRViolation getViolation() {
			return violation;
		}


		public void setViolation(HRViolation violation) {
			this.violation = violation;
		}


		public HRViolationResult getViolationResult() {
			return violationResult;
		}


		public void setViolationResult(HRViolationResult violationResult) {
			this.violationResult = violationResult;
		}


		public HRPeriodType getPeriodType() {
			return periodType;
		}


		public void setPeriodType(HRPeriodType periodType) {
			this.periodType = periodType;
		}


		public HRCalculationMethod getCalculationMethod() {
			return calculationMethod;
		}


		public void setCalculationMethod(HRCalculationMethod calculationMethod) {
			this.calculationMethod = calculationMethod;
		}


		public Double getVal() {
			return val;
		}


		public void setVal(Double val) {
			this.val = val;
		}


		public Double getMax_val() {
			return max_val;
		}


		public void setMax_val(Double max_val) {
			this.max_val = max_val;
		}

}
