package com._4s_.timesheet.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com._4s_.common.model.Employee;
import com._4s_.restServices.json.TimesheetTransWrapper;

@Entity
@Table(name="emp_dist_time_sheet")
@IdClass(TimesheetTransactionPK.class)
public class TimesheetTransaction {
	@Id
	@ManyToOne
	@JoinColumn (name="empCode")
	private Employee empCode;
	private Date inDate;
	@Id
	@ManyToOne
	@JoinColumn (name="costCode")
	private TimesheetCostCenter costCode;
	@Id
	@ManyToOne
	@JoinColumn (name="activity")
	private TimesheetActivity activity;
	@Id
	@ManyToOne
	@JoinColumn (name="part1")
	private TimesheetTransactionParts part1;
	@Id
	@ManyToOne
	@JoinColumn (name="part2")
	private TimesheetTransactionParts part2;
	@Id
	@ManyToOne
	@JoinColumn (name="part3")
	private TimesheetTransactionParts part3;
	private Integer chour;
	private Integer cminute;
	private String remark;
	
	public Employee getEmpCode() {
		return empCode;
	}

	public void setEmpCode(Employee empCode) {
		this.empCode = empCode;
	}

	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public TimesheetCostCenter getCostCode() {
		return costCode;
	}

	public void setCostCode(TimesheetCostCenter costCode) {
		this.costCode = costCode;
	}

	public TimesheetTransactionParts getPart1() {
		if (part1!=null && part1.getCode().equals("9999999999")) {
			return null;
		}
		return part1;
	}

	public void setPart1(TimesheetTransactionParts part1) {
		this.part1 = part1;
	}

	public TimesheetTransactionParts getPart2() {
		if (part2!=null && part2.getCode().equals("9999999999")) {
			return null;
		}
		return part2;
	}

	public void setPart2(TimesheetTransactionParts part2) {
		this.part2 = part2;
	}

	public TimesheetTransactionParts getPart3() {
		if (part3!=null && part3.getCode().equals("9999999999")) {
			return null;
		}
		return part3;
	}

	public void setPart3(TimesheetTransactionParts part3) {
		this.part3 = part3;
	}

	public Integer getChour() {
		return chour;
	}

	public void setChour(Integer chour) {
		this.chour = chour;
	}

	

	public Integer getCminute() {
		return cminute;
	}

	public void setCminute(Integer cminute) {
		this.cminute = cminute;
	}

	public TimesheetActivity getActivity() {
		return activity;
	}

	public void setActivity(TimesheetActivity activity) {
		this.activity = activity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getEmpCode()).append(this.getInDate()).append(this.costCode).append(this.getPart1()).append(this.getPart2()).append(this.getPart3())
		.toHashCode();
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "";
		return str.concat("empcode " + this.getEmpCode().getEmpCode()).concat(" data " + this.getInDate().toString())
				.concat(" costcode " + this.costCode.getCostCode());
	}

	@Override
	public boolean equals(Object obj) {
		TimesheetTransaction trans = (TimesheetTransaction)obj;
		// TODO Auto-generated method stub
		return (this.getEmpCode().equals(trans.getEmpCode())&& this.getInDate().equals(trans.getInDate()) && 
				this.costCode.equals(trans.getCostCode()) && this.part1.equals(trans.getPart1()) && 
				this.part2.equals(trans.getPart2()) && this.part3.equals(trans.getPart3()));
	}

	public Long getId() {
		return null;
	}

	public TimesheetTransWrapper getWrapper() {
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		TimesheetTransWrapper wrapper = new TimesheetTransWrapper();
		if (this.activity!=null) {
			wrapper.setActivityCode(this.activity.getActivity());
		}
		System.out.println(this.activity);
		wrapper.setcHour(this.chour);
		System.out.println(this.chour);
		wrapper.setcMinute(this.cminute);
		System.out.println(this.cminute);
		if (this.costCode!=null) {
			wrapper.setCostCenterCode(this.costCode.getCostCode());
		}
		wrapper.setEmpCode(this.empCode.getEmpCode());
		if (this.inDate!=null) {
			wrapper.setInDate(df.format(this.inDate));
		}
		System.out.println(this.inDate);
		if (this.getPart1()!=null) {
			wrapper.setPartCode1(this.getPart1().getCode());
		}
		if (this.getPart2()!=null) {
			wrapper.setPartCode2(this.getPart2().getCode());
		}
		System.out.println(this.part2);
		if (this.getPart3()!=null) {
			wrapper.setPartCode3(this.getPart3().getCode());
		}
		return wrapper;
	}
}
