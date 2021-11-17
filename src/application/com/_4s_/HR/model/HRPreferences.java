package com._4s_.HR.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PREFERENCES")
public class HRPreferences implements Serializable {
	

	public HRPreferences() {
		// TODO Auto-generated constructor stub
	}
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="hr_preferences_seq")
	@SequenceGenerator(name="hr_preferences_seq",sequenceName="hr_preferences_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
	private String property;
	private String user_code;
	private String pvalue;
	private Long user_type;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getPvalue() {
		return pvalue;
	}
	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}
	public Long getUser_type() {
		return user_type;
	}
	public void setUser_type(Long user_type) {
		this.user_type = user_type;
	}
	
	

}
