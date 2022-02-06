package com._4s_.timesheet.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com._4s_.common.model.Employee;
import com._4s_.restServices.json.TimesheetTransDefaultWrapper;

@Entity
@Table(name="emp_dist_time_sheet_defaults")
public class TimesheetTransactionDefaults implements Serializable{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9201224453547323244L;
	@Id
	@ManyToOne
	@JoinColumn (name="empCode")
	private Employee empCode;
	@ManyToOne
	@JoinColumn (name="costCode")
	private TimesheetCostCenter costCode;
	@ManyToOne
	@JoinColumn (name="part1")
	private TimesheetTransactionParts part1;
	@ManyToOne
	@JoinColumn (name="part2")
	private TimesheetTransactionParts part2;
	@ManyToOne
	@JoinColumn (name="part3")
	private TimesheetTransactionParts part3;
	@ManyToOne
	@JoinColumn (name="activity")
	private TimesheetActivity activity;
	
	
	public Employee getEmpCode() {
		return empCode;
	}


	public void setEmpCode(Employee empCode) {
		this.empCode = empCode;
	}


	public TimesheetCostCenter getCostCode() {
		return costCode;
	}


	public void setCostCode(TimesheetCostCenter costCode) {
		this.costCode = costCode;
	}


	public TimesheetTransactionParts getPart1() {
		return part1;
	}


	public void setPart1(TimesheetTransactionParts part1) {
		this.part1 = part1;
	}


	public TimesheetTransactionParts getPart2() {
		return part2;
	}


	public void setPart2(TimesheetTransactionParts part2) {
		this.part2 = part2;
	}


	public TimesheetTransactionParts getPart3() {
		return part3;
	}


	public void setPart3(TimesheetTransactionParts part3) {
		this.part3 = part3;
	}


	public TimesheetActivity getActivity() {
		return activity;
	}


	public void setActivity(TimesheetActivity activity) {
		this.activity = activity;
	}


	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.empCode)

		.toHashCode();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return empCode.equals(((TimesheetTransactionDefaults)obj).getEmpCode());
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return empCode.getEmpCode();
	}

	public TimesheetTransDefaultWrapper getWrapper() {
		TimesheetTransDefaultWrapper wrapper = new TimesheetTransDefaultWrapper();
		wrapper.setActivityCode(this.activity.getActivity());
		wrapper.setCostCenterCode(this.costCode.getCostCode());
		wrapper.setEmpCode(this.empCode.getEmpCode());
		if (this.part1!=null) {
			wrapper.setPartCode1(this.part1.getCode());
		}
		if (this.part2!=null) {
			wrapper.setPartCode2(this.part2.getCode());
		}
		if (this.part3!=null) {
			wrapper.setPartCode3(this.part3.getCode());
		}
		return wrapper;
	}
	public Long getId() {
		return null;
	}
}
