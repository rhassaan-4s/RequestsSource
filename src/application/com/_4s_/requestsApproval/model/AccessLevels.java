package com._4s_.requestsApproval.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="access_levels")
public class AccessLevels implements Auditable,Serializable {
	public AccessLevels() {
		// TODO Auto-generated constructor stub
	}

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="access_levels_seq")
	@SequenceGenerator(name="access_levels_seq",sequenceName="access_levels_seq")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="level_id")
	private GroupAcc level_id;
	
	@ManyToOne
	@JoinColumn(name="emp_id")
	private LoginUsers emp_id;
//	
//	private String title;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LoginUsers getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(LoginUsers emp_id) {
		this.emp_id = emp_id;
	}

//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
	
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "AccessLevels Addition";
	}
	
//	@Override
//	public String toString() {
//		 return new ToStringBuilder(this)
//		 .append("name", this.title)
//		 .toString();
//	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getId())
		.toHashCode();
	}

	public void setLevel_id(GroupAcc level_id) {
		this.level_id = level_id;
	}

	public GroupAcc getLevel_id() {
		return level_id;
	}
}
