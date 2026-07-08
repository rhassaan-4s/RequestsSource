package com._4s_.requestsApproval.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="co_profile")
public class CompanyDetails implements Auditable,Serializable {
	
	public CompanyDetails() {
		// TODO Auto-generated constructor stub
	}
	@Id 
	private String co_name;
	

	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setCo_name(String co_name) {
		this.co_name = co_name;
	}
	public String getCo_name() {
		return co_name;
	} 
	
}