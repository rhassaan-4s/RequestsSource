package com._4s_.requestsApproval.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import com._4s_.restServices.json.TimesheetPartWrapper;

@Entity
@Table(name="time_sheet_parts")
public class TimesheetTransactionParts {

	@Id
	@GenericGenerator(name="part_seq",strategy="com._4s_.requestsApproval.dao.PartStringKeyGenerator")
	@GeneratedValue(generator="part_seq")
	private String code;
	private Short part_no;
	private String name;
	private String ename;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Short getPart_no() {
		return part_no;
	}
	public void setPart_no(Short part_no) {
		this.part_no = part_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	
	public boolean equals(TimesheetTransactionParts obj) {
		// TODO Auto-generated method stub
		return obj.getCode().equals(this.getCode());
	}
	@Override
	public String toString() {
		return this.getCode()+"-"+this.getName();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getCode())

		.toHashCode();
	}
	
	public TimesheetPartWrapper getWrapper() {
		TimesheetPartWrapper wrapper = new TimesheetPartWrapper();
		wrapper.setArName(this.name);
		wrapper.setEnName(this.ename);
		wrapper.setPartNo(this.part_no);
		wrapper.setPartCode(this.code);
		return wrapper;
	}
	public Long getId() {
		return null;
	}
	
}
