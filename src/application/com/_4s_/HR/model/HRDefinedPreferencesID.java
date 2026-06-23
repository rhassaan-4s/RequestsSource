package com._4s_.HR.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class HRDefinedPreferencesID implements Serializable {

	private String property;
	private String pvalue;
	private String user_type;

	public HRDefinedPreferencesID() {
		// TODO Auto-generated constructor stub
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getPvalue() {
		return pvalue;
	}
	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	

	
	

}
