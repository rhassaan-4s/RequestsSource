package com._4s_.security.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

/**
 * @hibernate.class table="security_applications"
 */
@Entity
@Table (name = "security_applications")
public class SecurityApplication implements Serializable ,Auditable{
	public SecurityApplication() {
		// TODO Auto-generated constructor stub
	}

	@Id
	private Long id;

	private String name;
	@OneToMany (mappedBy ="application")
	private List<Roles>roles = new ArrayList<Roles>();

	@OneToMany (mappedBy ="application")
	private List<Fields>fields = new ArrayList<Fields>();

	
	private String defaultPage;
	
	private Boolean display_in_gl = new Boolean(false);
	
	private Boolean is_active;
	
	private String message_key;
	
	@OrderBy
	private Long position;

	/**
	 * @hibernate.property column="defaultPage"
	 */
	public String getDefaultPage() {
		return defaultPage;
	}

	public void setDefaultPage(String defaultPage) {
		this.defaultPage = defaultPage;
	}

	/**
	 * @author hfouad
	 * @hibernate.set table="security_roles" inverse="true"
	 * @hibernate.collection-key column="applicationId"
	 * @hibernate.collection-one-to-many class="com._4s_.security.model.Roles"
	 */
	

	public List<Fields> getFields() {
		return fields;
	}

	public void setFields(List<Fields> fields) {
		this.fields = fields;
	}

	public List getRoles() {
		return roles;
	}

	public void setRoles(List roles) {
		this.roles = roles;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof SecurityApplication)) {
			return false;
		}
		SecurityApplication rhs = (SecurityApplication) object;
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

	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public Boolean getDisplay_in_gl() {
		return display_in_gl;
	}

	public void setDisplay_in_gl(Boolean display_in_gl) {
		this.display_in_gl = display_in_gl;
	}

	public Boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}

	public String getMessage_key() {
		return message_key;
	}

	public void setMessage_key(String message_key) {
		this.message_key = message_key;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

}
