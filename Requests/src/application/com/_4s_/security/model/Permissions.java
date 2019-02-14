package com._4s_.security.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

/**
 * @hibernate.class table="security_permissions"
 */
@Entity
@Table (name = "security_permissions")
public class Permissions implements Serializable,Auditable {
	@Id
	private Long id;

	private String permessionname;
	
	private String permissionDescription;

	private Boolean selected = new Boolean(true);
	@ManyToMany(mappedBy ="permissions")
	private Set<Roles>roles = new HashSet<Roles>();

	
	/**
	 * @return
	 * @hibernate.set table = "security_role_permissions" cascade="all"
	 * @hibernate.collection-key column = "permissionId"
	 * @hibernate.collection-many-to-many column = "roleId" class = "com._4s_.security.model.Roles"
	 * 
	 */


	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	/**
	 * @hibernate.property column="selected"
	 */
	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
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
	 * @hibernate.property column="permissionname"
	 */
	public String getPermessionname() {
		return permessionname;
	}

	public void setPermessionname(String permessionname) {
		this.permessionname = permessionname;
	}

	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Permissions)) {
			return false;
		}
		Permissions rhs = (Permissions) object;
		return new EqualsBuilder().append(this.permessionname,
				rhs.permessionname).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1366881971, 1044840463).append(
				this.permessionname).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("name", this.permessionname)
				.toString();
	}

	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return this.permessionname;
	}


	public String getPermissionDescription() {
		return permissionDescription;
	}

	public void setPermissionDescription(String permissionDescription) {
		this.permissionDescription = permissionDescription;
	}

}
