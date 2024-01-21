package com._4s_.common.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com._4s_.auditing.model.Auditable;

@Entity
@Table(name="common_company")
public class Company  implements Serializable,Auditable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	private String description;
	
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Company)) {
			return false;
		}
		Company rhs = (Company) o;
		return new EqualsBuilder().append(this.description, rhs.getDescription()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.description).toHashCode();
	}
	public String getEntityDisplayName() {
		
		return this.description;
	}

}