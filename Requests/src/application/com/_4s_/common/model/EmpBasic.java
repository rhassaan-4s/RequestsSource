package com._4s_.common.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com._4s_.auditing.model.Auditable;

@Entity
@Table (name = "EMPBASIC")
public class EmpBasic  implements Serializable,Auditable {

	@Id
	String empCode;
	String empName;
	String e_emp_name;
	//////////////
	
	public String getEmpCode() {
		System.out.println("get empcode " + empCode);
		return empCode;
	}
	public void setEmpCode(String empCode) {
		System.out.println("set empcode " + empCode);
		this.empCode = empCode;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getE_emp_name() {
		return e_emp_name;
	}
	public void setE_emp_name(String e_emp_name) {
		this.e_emp_name = e_emp_name;
	}
	public String getId() {
		// TODO Auto-generated method stub
		return empCode;
	}
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "EmpBasic";
	}
	
	
	
}
