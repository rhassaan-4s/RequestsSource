/************************************************************************
Types.java - Copyright hfouad
**************************************************************************/
package com._4s_.common.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity
@Table(name = "common_types")
public class Types implements Serializable, Auditable{
@Id @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="common_types_seq")
@SequenceGenerator(name="common_types_seq",sequenceName="common_types_seq")
	private Long id;

	private String description;
	private String arDesc;
public String getArDesc() {
		return arDesc;
	}

	public void setArDesc(String arDesc) {
		this.arDesc = arDesc;
	}

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

public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return description;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Types)) {
			return false;
		}
		Types rhs = (Types) object;
		return new EqualsBuilder().append(this.description,
				rhs.getDescription()).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147).append(
				this.description)

		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("description", this.description).toString();
	}


}