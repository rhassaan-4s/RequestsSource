package com._4s_.security.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;


/**
 * @hibernate.class table="security_roles"
 */
@Entity
@Table (name = "security_roles")
public class Roles implements Serializable,Auditable {
	@Id
	@SequenceGenerator(name = "userID", sequenceName = "USER_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userID")
	private Long id;

	private String rolename;
	@ManyToOne
	@JoinColumn (name="application")
	private SecurityApplication application;
	@ManyToMany
	@JoinTable(name= "security_role_permissions",
			joinColumns={@JoinColumn(name="role")},
			inverseJoinColumns={@JoinColumn(name="permission")})
	private Set<Permissions> permissions = new HashSet<Permissions>();
	
//	@OneToMany (fetch = FetchType.EAGER)//(cascade = CascadeType.ALL)//(fetch = FetchType.LAZY)
//	@JoinColumn(name = "roleId")
//	@Sort(type=SortType.COMPARATOR , comparator=ReportComparator.class)
//	private Set<Reports> reports = new TreeSet<Reports>();
	
	private String defaultPage;

	public String getDefaultPage() {
		return defaultPage;
	}

	public void setDefaultPage(String defaultPage) {
		this.defaultPage = defaultPage;
	}

	/**
	 * @return
	 * @hibernate.set table = "security_role_permissions" inverse ="true"
	 *               cascade="all"
	 * @hibernate.collection-key column = "roleId"
	 * @hibernate.collection-many-to-many column = "permissionId" class =  "com._4s_.security.model.Permissions"
	 */

	

	public Set<Permissions> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permissions> permissions) {
		this.permissions = permissions;
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
	 * @hibernate.property column="rolename"
	 */
	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}


	/**
	 * @see java.lang.Object#equals()
	 */
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Roles)) {
			return false;
		}
		Roles rhs = (Roles) object;
		return new EqualsBuilder().append(this.rolename, rhs.rolename)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1366881971, 1044840463).append(
				this.rolename).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("name", this.rolename)
				.toString();
	}

	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return this.rolename;
	}

//	public Set getReports() {
//		return reports;
//	}
//
//	public void setReports(Set reports) {
//		this.reports = reports;
//	}

}
