package com._4s_.requestsApproval.model;

import java.io.Serializable;

import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity//(access=AccessType.FIELD)
@Table(name="vacation")
public class Vacation implements Auditable,Serializable  {
	
//	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="vacation_seq")
//	@SequenceGenerator(name="vacation_seq",sequenceName="vacation_seq")//(generate=GeneratorType.IDENTITY)
	
	@Id
	private String vacation;
	private String name;
	private String ename;
	@JsonIgnore
	private String type;
	@JsonIgnore
	private String payed;
	
	
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
