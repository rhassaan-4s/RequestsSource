package com._4s_.common.model;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import com._4s_.security.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author mragab
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * 
 * @hibernate.class table = "common_employee"
 */
@Entity
@Table (name = "common_employee")
@JsonIgnoreProperties({"users"})
public class Employee implements Serializable,Auditable,Searchable {
	@Id
	@SequenceGenerator(name = "userID", sequenceName = "SEC_USER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userID")
	private Long id;
	private String firstName;
	private String lastName;
	private String empCode;
	@ManyToOne
	@JoinColumn (name="department")
	private Department department;
	private String jobTitle;
	private String address;
	@ManyToOne
	@JoinColumn (name="webBranch")
	private WebBranch webBranch;
	
	public WebBranch getWebBranch() {
		return webBranch;
	}
	public void setWebBranch(WebBranch webBranch) {
		this.webBranch = webBranch;
	}
	@ManyToOne
	@JoinColumn (name="city")
	private City city;
	
	private String tel;
	private String ext;
	private String email;
	@OneToOne
	@JoinColumn (name="users")
	@JsonIgnore
	private User users;
	private Boolean isInternalCommunicator = new Boolean (false);
	
	private Boolean canViewDepartmentMessages = new Boolean (false);
	private Boolean isDepartmentManager = new Boolean(false);
	private Boolean isManager = new Boolean(false);
	private String gender = new String("Male");
	private Boolean isActive=new Boolean(true);
	private Long employeeCode;
	private Long attendanceCode;
	@ManyToOne
	@JoinColumn (name="branch")
	private Branch branch;
	
	//private Account account;
	
	//private Date birthDate;
	//private String identityNumber;
	
	private Boolean isEmployee=new Boolean(true);
	
//	@ManyToOne
//	@JoinColumn (name="attendanceGroup")
//	private AttendanceGroup attendanceGroup;
	
	private Boolean canSeePrivateGLAccounts = new Boolean(false);
	
	@Column(name="CANSEEALLSTORE")
	private Boolean canSeeAllStore = new Boolean(false);
	
	private byte[] profilePic;
	
	private String profilePicName;
	

	public byte[] getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}
	public String getProfilePicName() {
		return profilePicName;
	}
	public void setProfilePicName(String profilePicName) {
		this.profilePicName = profilePicName;
	}
	
	
	
	
	public Long getAttendanceCode() {
		return attendanceCode;
	}
	public void setAttendanceCode(Long attendanceCode) {
		this.attendanceCode = attendanceCode;
	}
	public Boolean getIsEmployee() {
		return isEmployee;
	}
	public void setIsEmployee(Boolean isEmployee) {
		this.isEmployee = isEmployee;
	}
//	public AttendanceGroup getAttendanceGroup() {
//		return attendanceGroup;
//	}
//	public void setAttendanceGroup(AttendanceGroup attendanceGroup) {
//		this.attendanceGroup = attendanceGroup;
//	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return Returns the id.
	 * @hibernate.id generator-class = "native"
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	/**
	 * @return
	 * @hibernate.many-to-one column="city" class="com._4s_.common.model.City"
	 */
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	
	
	/**
	 * @return
	 * @hibernate.property column = "email"
	 */
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return
	 * @hibernate.property column = "ext"
	 */
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	
	
	/**
	 * @return
	 * @hibernate.property column = "tel"
	 */
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return
	 * hibernate.many-to-one column = "account" class = "com._4s_.communication.model.Account" 
	 */
//	public Account getAccount() {
//		return account;
//	}
//
//	public void setAccount(Account account) {
//		this.account = account;
//	}


	/**
	 * @return
	 * @hibernate.many-to-one column = "department" class = "com._4s_.common.model.Department"   
	 */
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * @return
	 * @hibernate.property column = "firstName"
	 */
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return
	 * @hibernate.property column = "jobTitle"
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * @return
	 * @hibernate.property column = "lastname"
	 */
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	/**
	 * 
	 * @return
	 * @hibernate.many-to-one column="user" class="com._4s_.security.model.User"
	 */
	public User getUser() {
		return users;
	}
	public void setUser(User user) {
		this.users = user;
	}
	/**
	 * @return
	 * @hibernate.property column = "address"
	 */
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 
	 * @hibernate.property column="isInternalCommunicator"
	 */
	public Boolean getIsInternalCommunicator() {
		return isInternalCommunicator;
	}
	public void setIsInternalCommunicator(Boolean isInternalCommunicator) {
		this.isInternalCommunicator = isInternalCommunicator;
	}
	

	public Boolean getCanViewDepartmentMessages() {
		return canViewDepartmentMessages;
	}
	public void setCanViewDepartmentMessages(Boolean canViewDepartmentMessages) {
		this.canViewDepartmentMessages = canViewDepartmentMessages;
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("firstName", this.firstName)
		.append("lastName", this.lastName)
		.append("jobTitle", this.jobTitle)
		.append("address", this.address)
		.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Employee)) {
			return false;
		}
		Employee rhs = (Employee) o;
		return new EqualsBuilder().append(this.firstName, rhs.getFirstName())
		.append(this.lastName, rhs.getLastName()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.firstName)
		.append(this.lastName).toHashCode();
	}
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return this.firstName + this.lastName;
	}
	public String getSearchableId() {
		
		return this.id.toString();
	}
	public String getSearchableName() {
		
		return this.firstName +" "+ this.lastName;
	}
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public Boolean getIsDepartmentManager() {
		return isDepartmentManager;
	}
	public void setIsDepartmentManager(Boolean isDepartmentManager) {
		this.isDepartmentManager = isDepartmentManager;
	}
	public Boolean getIsManager() {
		return isManager;
	}
	public void setIsManager(Boolean isManager) {
		this.isManager = isManager;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Long getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(Long employeeCode) {
		this.employeeCode = employeeCode;
	}
	public Boolean getCanSeePrivateGLAccounts() {
		return canSeePrivateGLAccounts;
	}
	public void setCanSeePrivateGLAccounts(Boolean canSeePrivateGLAccounts) {
		this.canSeePrivateGLAccounts = canSeePrivateGLAccounts;
	}
	
	public void setCanSeeAllStore(Boolean canSeeAllStore) {
		this.canSeeAllStore = canSeeAllStore;
	}
	
	public Boolean getCanSeeAllStore() {
		return canSeeAllStore;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpCode() {
		return empCode;
	}

	
}