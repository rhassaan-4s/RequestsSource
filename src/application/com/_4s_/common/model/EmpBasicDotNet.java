package com._4s_.common.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table (name = "EMPBASIC_DOTNET")
public class EmpBasicDotNet extends EmpBasicParent {
	@Column(name = "CODE")
	private String empCode;
	@Column(name="NATIONALITY_ID")
	private String natnl_no;
	@Column(name="birth_date")
	private Timestamp birthdate;
	
	private String alternative_code;
	private String aName;
	private String ename;
	private String middle_aname;
	private String middle_ename;
	private String last_aname;
	private String last_ename;
	private String sex;
	@Column(name = "religion_id")
	private Long eldiana;
	
	private String photo_path;
	private String is_top_management;
	
	private Long birth_geo_segment1_id;
	private Long  birth_geo_segment2_id;
	private Long birth_civil_register_id;
	
//	public String getEmpCode() {
//		return empCode;
//	}
//	public void setEmpCode(String empCode) {
//		this.empCode = empCode;
//	}
	public String getNatnl_no() {
		return natnl_no;
	}
	public void setNatnl_no(String natnl_no) {
		this.natnl_no = natnl_no;
	}
	public Timestamp getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Timestamp birthdate) {
		this.birthdate = birthdate;
	}
	public String getAlternative_code() {
		return alternative_code;
	}
	public void setAlternative_code(String alternative_code) {
		this.alternative_code = alternative_code;
	}
	public String getaName() {
		return aName;
	}
	public void setaName(String aName) {
		this.aName = aName;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getMiddle_aname() {
		return middle_aname;
	}
	public void setMiddle_aname(String middle_aname) {
		this.middle_aname = middle_aname;
	}
	public String getMiddle_ename() {
		return middle_ename;
	}
	public void setMiddle_ename(String middle_ename) {
		this.middle_ename = middle_ename;
	}
	public String getLast_aname() {
		return last_aname;
	}
	public void setLast_aname(String last_aname) {
		this.last_aname = last_aname;
	}
	public String getLast_ename() {
		return last_ename;
	}
	public void setLast_ename(String last_ename) {
		this.last_ename = last_ename;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Long getEldiana() {
		return eldiana;
	}
	public void setEldiana(Long eldiana) {
		this.eldiana = eldiana;
	}
	public String getPhoto_path() {
		return photo_path;
	}
	public void setPhoto_path(String photo_path) {
		this.photo_path = photo_path;
	}
	public String getIs_top_management() {
		return is_top_management;
	}
	public void setIs_top_management(String is_top_management) {
		this.is_top_management = is_top_management;
	}
	public Long getBirth_geo_segment1_id() {
		return birth_geo_segment1_id;
	}
	public void setBirth_geo_segment1_id(Long birth_geo_segment1_id) {
		this.birth_geo_segment1_id = birth_geo_segment1_id;
	}
	public Long getBirth_geo_segment2_id() {
		return birth_geo_segment2_id;
	}
	public void setBirth_geo_segment2_id(Long birth_geo_segment2_id) {
		this.birth_geo_segment2_id = birth_geo_segment2_id;
	}
	public Long getBirth_civil_register_id() {
		return birth_civil_register_id;
	}
	public void setBirth_civil_register_id(Long birth_civil_register_id) {
		this.birth_civil_register_id = birth_civil_register_id;
	}
	
	public String getEmpName() {
		String empName = aName + " ";
		if (middle_aname!=null && !middle_aname.isEmpty()) {
			empName += middle_aname;
		}
		if (last_aname!=null && !last_aname.isEmpty()) {
			empName += last_aname;
		}
		return empName;
	}
}
