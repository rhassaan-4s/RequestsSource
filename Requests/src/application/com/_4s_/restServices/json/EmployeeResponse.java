package com._4s_.restServices.json;

import com._4s_.common.model.Branch;
import com._4s_.common.model.City;
import com._4s_.common.model.Department;

public class EmployeeResponse {

	private RestStatus restStatus;
	private Long id;
	private String firstName;
	private String lastName;
	private String empCode;
	private Department department;
	private String jobTitle;
	private String address;
	private City city;
	private String tel;
	private String ext;
	private String email;
	
	private Boolean isDepartmentManager = new Boolean(false);
	private Boolean isManager = new Boolean(false);
	private String gender = new String("Male");
	private Long employeeCode;
	private Long attendanceCode;
	private Branch branch;
	
	
	private Integer requiredAndroidVersion;
	
	public Integer getRequiredAndroidVersion() {
		return requiredAndroidVersion;
	}
	public void setRequiredAndroidVersion(Integer requiredAndroidVersion) {
		this.requiredAndroidVersion = requiredAndroidVersion;
	}
	public RestStatus getRestStatus() {
		return restStatus;
	}
	public void setRestStatus(RestStatus restStatus) {
		this.restStatus = restStatus;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Long getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(Long employeeCode) {
		this.employeeCode = employeeCode;
	}
	public Long getAttendanceCode() {
		return attendanceCode;
	}
	public void setAttendanceCode(Long attendanceCode) {
		this.attendanceCode = attendanceCode;
	}
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	
	

}
