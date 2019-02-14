/************************************************************************
 TypesData.java - Copyright hfouad
 **************************************************************************/
package com._4s_.common.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity
@Table(name = "common_typesData")
public class TypesData implements Serializable, Auditable {
	@Id @GeneratedValue(strategy=GenerationType.AUTO,generator="common_typesData_seq")
	@SequenceGenerator(name="common_typesData_seq",sequenceName="common_typesData_seq")
	private Long id;

	private String description;

	@ManyToOne
	@JoinColumn(name = "typeId")
	private Types type;

	

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
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
		if (!(object instanceof TypesData)) {
			return false;
		}
		TypesData rhs = (TypesData) object;
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
