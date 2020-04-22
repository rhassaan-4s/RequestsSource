package com._4s_.security.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.model.Employee;
import com._4s_.i18n.model.MyLocale;

/**
 * @hibernate.class table="security_users"
 */
@Entity
@Table (name = "security_users")
public class User implements  Serializable,Auditable,UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "userID", sequenceName = "USER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userID")
	private Long id;

	private String username;

	private String password;
	@ManyToOne
	@JoinColumn (name="defaultLocale")
	private MyLocale language;
	
	@ManyToOne
	@JoinColumn (name="defaultApplication")
	private SecurityApplication defaultApplication;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="security_user_roles",
			joinColumns={@JoinColumn(name="users")},
			inverseJoinColumns={@JoinColumn(name="role")})
	private List<Roles>roles = new ArrayList<Roles>();
	
	// this property is added so that the creation of a user automatically creates an employee if this user is an employee in the the organization
	@OneToOne(cascade=CascadeType.ALL, mappedBy="users")
	
	private Employee employee;
	
	private Boolean isActive = true;
	
	private Boolean isEmployee;
	
	private Date end_serv;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id", referencedColumnName = "user_id")
	private UserPrivilege userPrivilege;
	
//	@OneToMany(mappedBy ="users",cascade=CascadeType.ALL)
//	private List<Imei> imei = new ArrayList<Imei>();
//	
/**
	 * @author hfouad
	 * @hibernate.many-to-one column="defaultLocale"
	 *                        class="com._4s_.i18n.model.MyLocale"
	 */
	public MyLocale getLanguage() {
		return language;
	}

	public void setLanguage(MyLocale language) {
		this.language = language;
	}

/**
	 * @author hfouad
	 * @hibernate.many-to-one column="defaultApplication"
	 *                        class="com._4s_.security.model.SecurityApplication"
	 */
	public SecurityApplication getDefaultApplication() {
		return defaultApplication;
	}

	public void setDefaultApplication(SecurityApplication defaultApplication) {
		this.defaultApplication = defaultApplication;
	}

	
	/**
	 * @author hfouad
	 * @hibernate.many-to-one column="languageId"
	 *                        class="com._4s_.i18n.model.Preferences"
	 */
	

	/**
	 * @hibernate.property column="userName"
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @hibernate.property column="password"
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	 * @return
	 * @hibernate.set table = "security_user_roles" 
	 * @hibernate.collection-key column = "users"
	 * @hibernate.collection-many-to-many class ="com._4s_.security.model.Roles" column="roles" 
	 */
	
	public List getRoles() {
		return roles;
	}

	public void set(List roles) {
		this.roles = roles;
	}

	/**
	 * 
	 * @return
	 * @hibernate.many-to-one class="com._4s_.common.model.Employee" cascade="all" column="employee"
	 */
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof User)) {
			return false;
		}
		User rhs = (User) object;
		return new EqualsBuilder().append(this.username, rhs.username).append(
				this.password, rhs.password).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1366881971, 1044840463).append(
				this.username).append(this.password).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("username", this.username)
				.append("password", this.password).toString();
	}

	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return this.username;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setUserPrivilege(UserPrivilege userPrivilege) {
		this.userPrivilege = userPrivilege;
	}

	public UserPrivilege getUserPrivilege() {
		return userPrivilege;
	}

	public Boolean getIsEmployee() {
		return isEmployee;
	}

	public void setIsEmployee(Boolean isEmployee) {
		this.isEmployee = isEmployee;
	}

	public Date getEnd_serv() {
		return end_serv;
	}

	public void setEnd_serv(Date end_serv) {
		this.end_serv = end_serv;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List authorities = new ArrayList();
		Iterator itr = getRoles().iterator();
		while(itr.hasNext()) {
			Roles role = (Roles)itr.next();
			authorities.addAll(role.getPermissions());
		}
		return authorities;
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

//	public List<Imei> getImei() {
//		return imei;
//	}
//
//	public void setImei(List<Imei> imei) {
//		this.imei = imei;
//	}
//	
	

}
