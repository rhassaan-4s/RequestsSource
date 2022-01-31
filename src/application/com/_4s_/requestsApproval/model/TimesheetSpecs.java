package com._4s_.requestsApproval.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import com._4s_.restServices.json.TimesheetSpecsWrapper;

@Entity
@Table(name="distribute_time_sheet_specs")
public class TimesheetSpecs {
	@Id
//	@GenericGenerator(name="specs_seq",strategy="com._4s_.requestsApproval.dao.SpecsStringKeyGenerator")
//	@GeneratedValue(generator="specs_seq")
	private String code;
	private String is_used1;//0 or 1
	private String part1_name;
	private String part1_ename;
	private String is_used2;//0or1
	private String part2_name;
	private String part2_ename;
	private String is_used3;//0 or 1
	private String part3_name;
	private String part3_ename;
	
	public boolean equals(TimesheetSpecs obj) {
		// TODO Auto-generated method stub
		return obj.getCode().equals(this.getCode());
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getCode();
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIs_used1() {
		return is_used1;
	}

	public void setIs_used1(String is_used1) {
		this.is_used1 = is_used1;
	}

	public String getPart1_name() {
		return part1_name;
	}

	public void setPart1_name(String part1_name) {
		this.part1_name = part1_name;
	}

	public String getPart1_ename() {
		return part1_ename;
	}

	public void setPart1_ename(String part1_ename) {
		this.part1_ename = part1_ename;
	}

	public String getIs_used2() {
		return is_used2;
	}

	public void setIs_used2(String is_used2) {
		this.is_used2 = is_used2;
	}

	public String getPart2_name() {
		return part2_name;
	}

	public void setPart2_name(String part2_name) {
		this.part2_name = part2_name;
	}

	public String getPart2_ename() {
		return part2_ename;
	}

	public void setPart2_ename(String part2_ename) {
		this.part2_ename = part2_ename;
	}

	public String getIs_used3() {
		return is_used3;
	}

	public void setIs_used3(String is_used3) {
		this.is_used3 = is_used3;
	}

	public String getPart3_name() {
		return part3_name;
	}

	public void setPart3_name(String part3_name) {
		this.part3_name = part3_name;
	}

	public String getPart3_ename() {
		return part3_ename;
	}

	public void setPart3_ename(String part3_ename) {
		this.part3_ename = part3_ename;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getCode())

		.toHashCode();
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.code.equals(((TimesheetSpecs)obj).getCode());
	}
	public Long getId() {
		return null;
	}
	public TimesheetSpecsWrapper getWrapper() {
		TimesheetSpecsWrapper wrapper = new TimesheetSpecsWrapper();
		wrapper.setIsUsed1(this.is_used1);
		wrapper.setIsUsed2(this.is_used2);
		wrapper.setIsUsed3(this.is_used3);
		wrapper.setPartEName1(this.part1_ename);
		wrapper.setPartEName2(this.part2_ename);
		wrapper.setPartEName3(this.part3_ename);
		wrapper.setPartName1(this.part1_name);
		wrapper.setPartName2(this.part2_name);
		wrapper.setPartName3(this.part3_name);
		return wrapper;
	}
}
