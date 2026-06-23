package com._4s_.HR.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

@Entity
@Table(name = "DEFINE_PREFERENCES")
public class HRDefinedPreferences implements Serializable {
	public HRDefinedPreferences() {
		// TODO Auto-generated constructor stub
	}

	@EmbeddedId
	private final HRDefinedPreferencesID id = new HRDefinedPreferencesID();
	//private String property;
	private String descr;
	//private String pvalue;
	private String defaultv;
	//private Long user_type;
	private String app;
	
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDefaultv() {
		return defaultv;
	}
	public void setDefaultv(String defaultv) {
		this.defaultv = defaultv;
	}
	
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public HRDefinedPreferencesID getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object o) {// //my Business Key is (name)
		if (o == this) {
			return true;
		}
		if (!(o instanceof HRDefinedPreferences)) {
			return false;
		}
		HRDefinedPreferences rhs = (HRDefinedPreferences) o;
		return new EqualsBuilder().append(this.getDescr(), rhs.getDescr())
				.isEquals();
	}


	
	
	

}
