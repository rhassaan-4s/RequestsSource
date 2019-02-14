package com._4s_.security.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

/**
 * @hibernate.class table="security_fields"
 */
@Entity
@Table(name = "security_fields")
public class Fields implements Serializable, Auditable {
	@Id
	private Long id;

	private String name;

	private Boolean flag;

	@OneToMany
	@JoinColumn(name = "fieldId")
	private List<Permissions> permissions = new ArrayList<Permissions>();

	@ManyToOne
	@JoinColumn(name = "application")
	private SecurityApplication application;

	@ManyToOne
	@JoinColumn(name = "parentId")
	private Fields parentField;

	@OneToMany(mappedBy = "parentField")
	private List<Fields> childFields = new ArrayList<Fields>();

	public List<Fields> getChildFields() {
		return childFields;
	}

	public void setChildFields(List<Fields> childFields) {
		this.childFields = childFields;
	}

	public Fields getParentField() {
		return parentField;
	}

	public void setParentField(Fields parentField) {
		this.parentField = parentField;
	}

	/**
	 * @author hfouad
	 * @hibernate.many-to-one column="applicationId"
	 *                        class="com._4s_.security.model.SecurityApplication"
	 */
	public SecurityApplication getApplication() {
		return application;
	}

	public void setApplication(SecurityApplication application) {
		this.application = application;
	}

	/**
	 * @hibernate.id generator-class="native"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @hibernate.property column="name"
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @author hfouad
	 * @hibernate.list table="security_permissions" outer-join="true"
	 * @hibernate.collection-key column="fieldId"
	 * @hibernate.collection-index column="fieldPermissionPosition"
	 * @hibernate.collection-one-to-many class="com._4s_.security.model.Permissions"
	 */
	public List getPermissions() {
		return permissions;
	}

	public void setPermissions(List permissions) {
		this.permissions = permissions;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Fields)) {
			return false;
		}
		Fields rhs = (Fields) object;
		return new EqualsBuilder().append(this.name, rhs.name).append(
				this.permissions, rhs.permissions).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1366881971, 1044840463).append(this.name)
				.append(this.permissions).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("name", this.name).append(
				"permissions", this.permissions).toString();
	}

	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

}
