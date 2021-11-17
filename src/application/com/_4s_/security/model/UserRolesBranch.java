package com._4s_.security.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.common.model.Branch;


public class UserRolesBranch {
	public UserRolesBranch() {
		// TODO Auto-generated constructor stub
	}

	@OneToMany
	@JoinColumn(name = "users")
	private List<User> users = new ArrayList<User>();
	
	@OneToMany
	@JoinColumn(name = "branch")
	private List<Branch> branches = new ArrayList<Branch>();
	
	@OneToMany
	@JoinColumn(name = "role")
	private List<Roles> roles = new ArrayList<Roles>();

	
	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof UserRolesBranch)) {
			return false;
		}
		UserRolesBranch rhs = (UserRolesBranch) object;
		return new EqualsBuilder()
		.append(this.users, rhs.getUsers())
		.append(this.branches, rhs.getBranches())
		.append(this.roles,rhs.getRoles()).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1366881971, 1044840463)
				.append(this.users)
				.append(this.branches)
				.append(this.roles).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("users", this.users)
				.append("branches", this.branches)
				.append("roles",this.roles).toString();
	}

	public List<Branch> getBranches() {
		return branches;
	}

	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}	
}
