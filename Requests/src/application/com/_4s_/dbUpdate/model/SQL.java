package com._4s_.dbUpdate.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com._4s_.auditing.model.Auditable;

@Entity
@Table(name="dbUpdate_sql_query")
public class SQL implements Auditable, Serializable {

	@Id
	private Long id;

	@Basic
	private String Location;

	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getEntityDisplayName() {
		return "";
	}
}
