package com._4s_.security.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity
@Table(name = "security_branches")
public class Branches implements Auditable, Serializable {

	public Branches() {
		// TODO Auto-generated constructor stub
	}

	@Id
	private Long id;

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return description;
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Branches)) {
			return false;
		}
		Branches rhs = (Branches) object;
		return new EqualsBuilder().append(this.description,
				rhs.getDescription()).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1366881971, 1044840463).append(
				this.description).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("name", this.description)
				.toString();
	}

}
