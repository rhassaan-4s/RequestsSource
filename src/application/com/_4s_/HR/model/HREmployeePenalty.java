package com._4s_.HR.model;

import java.io.Serializable;
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
@Table(name="hr_employee_penalty")
public class HREmployeePenalty implements Auditable,Serializable {

	   @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_employee_penalty_seq")
	   @SequenceGenerator(name="hr_employee_penalty_seq",sequenceName="hr_employee_penalty_seq")//(generate=GeneratorType.IDENTITY)
       private Long id;
	   
		private Long decisionNumber;
		private Date penaltyDate;
		private Date implementBeginDate;
		private Integer discountDays;
		private Integer discountMonths;
		private Date penaltyRemoveDate;
		private String confirmed;
		
		@ManyToOne
		@JoinColumn (name="penalty")
		private Punishment penalty;
		
		@ManyToOne
		@JoinColumn (name="employee")
		private HREmployee employee;
		
		 
		
		
		public String getEntityDisplayName() {
			return "empPenalty Addition";
		}
		public String toString() {
			 return new ToStringBuilder(this)
			 .append("penalty", this.penalty.getName().toString())
			 .toString();
		}
		public boolean equals(Object o) {
			 if (o == this) {
			 return true;
			 }
			 if (!(o instanceof HREmployeePenalty)) {
			 return false;
			 }
			 HREmployeePenalty rhs = (HREmployeePenalty) o;
			 return new EqualsBuilder()
			 .append(this.penalty, rhs.getPenalty().getName())
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
		public Long getDecisionNumber() {
			return decisionNumber;
		}
		public void setDecisionNumber(Long decisionNumber) {
			this.decisionNumber = decisionNumber;
		}
		public Date getPenaltyDate() {
			return penaltyDate;
		}
		public void setPenaltyDate(Date penaltyDate) {
			this.penaltyDate = penaltyDate;
		}
		public Date getImplementBeginDate() {
			return implementBeginDate;
		}
		public void setImplementBeginDate(Date implementBeginDate) {
			this.implementBeginDate = implementBeginDate;
		}
		public Integer getDiscountDays() {
			return discountDays;
		}
		public void setDiscountDays(Integer discountDays) {
			this.discountDays = discountDays;
		}
		public Integer getDiscountMonths() {
			return discountMonths;
		}
		public void setDiscountMonths(Integer discountMonths) {
			this.discountMonths = discountMonths;
		}
		public Date getPenaltyRemoveDate() {
			return penaltyRemoveDate;
		}
		public void setPenaltyRemoveDate(Date penaltyRemoveDate) {
			this.penaltyRemoveDate = penaltyRemoveDate;
		}
	
		public Punishment getPenalty() {
			return penalty;
		}
		public void setPenalty(Punishment penalty) {
			this.penalty = penalty;
		}
		public String getConfirmed() {
			return confirmed;
		}
		public void setConfirmed(String confirmed) {
			this.confirmed = confirmed;
		}
}
