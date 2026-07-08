package com._4s_.common.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.GenericGenerator;

//@MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EmpBasicParent {
//	@Id
//	private Long id; 
	@Id
	@GenericGenerator(name="empBasic_seq",strategy="com._4s_.attendance.dao.EmpBasicStringKeyGenerator")
	@GeneratedValue(generator="empBasic_seq")
	private String empCode;
	private String natnl_no;
	private Timestamp birthdate;
	private String sex;
	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
	
	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	public String getNatnl_no() {
		return natnl_no;
	}

	public void setNatnl_no(String natnl_no) {
		this.natnl_no = natnl_no;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Timestamp birthdate) {
		this.birthdate = birthdate;
	}
	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}
}
