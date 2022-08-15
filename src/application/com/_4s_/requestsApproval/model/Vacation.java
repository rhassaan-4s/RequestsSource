package com._4s_.requestsApproval.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com._4s_.auditing.model.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity//(access=AccessType.FIELD)
@Table(name="vacation")
public class Vacation implements Auditable,Serializable  {
	
//	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="vacation_seq")
//	@SequenceGenerator(name="vacation_seq",sequenceName="vacation_seq")//(generate=GeneratorType.IDENTITY)
	
	public Vacation() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -8084270327439377351L;
	@Id
	@GenericGenerator(name="vacation_seq",strategy="com._4s_.attendance.dao.VacationStringKeyGenerator")
	@GeneratedValue(generator="vacation_seq")
	private String vacation;
	private String name;
	private String ename;
	@JsonIgnore
	private String type="A";
	private Integer monthes_zero_entitled;
	@JsonIgnore
	private String payed="1";
	private String may_transfer="1";
	private Integer max_days_trnsfer;
	private String transfer_to_vacation;
	private String form = "1";
	private String lang = "A";
	
	
	
	
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public Integer getMonthes_zero_entitled() {
		return monthes_zero_entitled;
	}
	public void setMonthes_zero_entitled(Integer monthes_zero_entitled) {
		this.monthes_zero_entitled = monthes_zero_entitled;
	}
	public String getMay_transfer() {
		return may_transfer;
	}
	public void setMay_transfer(String may_transfer) {
		this.may_transfer = may_transfer;
	}
	public Integer getMax_days_trnsfer() {
		return max_days_trnsfer;
	}
	public void setMax_days_trnsfer(Integer max_days_trnsfer) {
		this.max_days_trnsfer = max_days_trnsfer;
	}
	public String getTransfer_to_vacation() {
		return transfer_to_vacation;
	}
	public void setTransfer_to_vacation(String transfer_to_vacation) {
		this.transfer_to_vacation = transfer_to_vacation;
	}
	public String getVacation() {
		return vacation;
	}
	public void setVacation(String vacation) {
		this.vacation = vacation;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPayed() {
		return payed;
	}
	public void setPayed(String payed) {
		this.payed = payed;
	}
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "Vacation addition";
	}
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
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
		 if (!(o instanceof Vacation)) {
		 return false;
		 }
		 Vacation rhs = (Vacation) o;
		 return new EqualsBuilder()
		 .append(this.name, rhs.getName())
		 .append(this.ename, rhs.getEname())
		 .isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getId())

		.toHashCode();
	}
}
