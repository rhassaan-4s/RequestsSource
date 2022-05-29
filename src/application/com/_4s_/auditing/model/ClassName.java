package com._4s_.auditing.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * @hibernate.class table="auditing_class_name"
 */
@Entity//(access=AccessType.FIELD)
@Table (name = "auditing_class_name")
public class ClassName {
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)//(generate=GeneratorType.IDENTITY)
	private Long id;

	private String name;
	
	private String entityClass;
	

	/**
	 * @return
	 * @hibernate.property column = "entityClass" not-null = "true"
	 */
	

	public String getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * @return
	 * @hibernate.id column = "id" generator-class = "native" unsaved-value =
	 *               "null"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return
	 * @hibernate.property column = "name" not-null = "true"
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof ClassName)) {
			return false;
		}
		ClassName rhs = (ClassName) object;
		return new EqualsBuilder().append(this.name, rhs.name).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1366881971, 1044840463).append(this.name)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("name", this.name).toString();
	}

}
