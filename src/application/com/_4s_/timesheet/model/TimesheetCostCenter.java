package com._4s_.timesheet.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com._4s_.restServices.json.TimesheetActivityWrapper;
import com._4s_.restServices.json.TimesheetCostcenterWrapper;

/**
 * @author rhassaan
 *
 */
@Entity
@Table(name="cost")
public class TimesheetCostCenter {
	@Id
	private String costCode;
	private String compCode;
	
	private String costName;
	private String latin_name;
	private String eng_name;
	
	////////////////////cost centers tree
	private Integer costLevel; // level number
	private String leafCost;//T or F
	private String beforLast;//T or F
	
	private Double opDebt;
	private Double opCrdt;
	private Double cuDebt;
	private Double cuCrdt;
	private Double bud_debt;
	private Double bud_crdt;
	
	private String currnCode;
	private Double rate;
	
	private Date oDate;
	private String str_odate;
	
	
	public TimesheetCostCenter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCostCode() {
		return costCode;
	}
	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}
	public String getCompCode() {
		return compCode;
	}
	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}
	public String getCostName() {
		return costName;
	}
	public void setCostName(String costName) {
		this.costName = costName;
	}
	
	public String getLatin_name() {
		return latin_name;
	}
	public void setLatin_name(String latin_name) {
		this.latin_name = latin_name;
	}
	public String getEng_name() {
		return eng_name;
	}
	public void setEng_name(String eng_name) {
		this.eng_name = eng_name;
	}
	public Integer getCostLevel() {
		return costLevel;
	}
	public void setCostLevel(Integer costLevel) {
		this.costLevel = costLevel;
	}
	public String getLeafCost() {
		return leafCost;
	}
	public void setLeafCost(String leafCost) {
		this.leafCost = leafCost;
	}
	public String getBeforeLast() {
		return beforLast;
	}
	public void setBeforeLast(String beforeLast) {
		this.beforLast = beforeLast;
	}
	public Double getOpDebt() {
		return opDebt;
	}
	public void setOpDebt(Double opDebt) {
		this.opDebt = opDebt;
	}
	public Double getOpCrdt() {
		return opCrdt;
	}
	public void setOpCrdt(Double opCrdt) {
		this.opCrdt = opCrdt;
	}
	public Double getCuDebt() {
		return cuDebt;
	}
	public void setCuDebt(Double cuDebt) {
		this.cuDebt = cuDebt;
	}
	public Double getCuCrdt() {
		return cuCrdt;
	}
	public void setCuCrdt(Double cuCrdt) {
		this.cuCrdt = cuCrdt;
	}
	public Double getBud_debt() {
		return bud_debt;
	}
	public void setBud_debt(Double bud_debt) {
		this.bud_debt = bud_debt;
	}
	public Double getBud_crdt() {
		return bud_crdt;
	}
	public void setBud_crdt(Double bud_crdt) {
		this.bud_crdt = bud_crdt;
	}
	public String getCurrnCode() {
		return currnCode;
	}
	public void setCurrnCode(String currnCode) {
		this.currnCode = currnCode;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Date getoDate() {
		return oDate;
	}
	public void setoDate(Date oDate) {
		this.oDate = oDate;
	}
	public String getStr_odate() {
		return str_odate;
	}
	public void setStr_odate(String str_odate) {
		this.str_odate = str_odate;
	}
	public boolean equals(TimesheetCostCenter costCenter) {
		// TODO Auto-generated method stub
		if (costCenter.getCostCode().equals(this.getCostCode()) && costCenter.getCompCode().equals(this.getCompCode())) {
			return true;
		} else return false;
	}
	@Override
	public String toString() {
		return this.getCostCode()+" - " + this.getCostName();
	}	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getCostCode())

		.toHashCode();
	}
	
	public TimesheetCostcenterWrapper getWrapper() {
		TimesheetCostcenterWrapper wrapper = new TimesheetCostcenterWrapper();
		wrapper.setCostCode(this.costCode);
		wrapper.setArName(this.costName);
		wrapper.setEnName(this.eng_name);
		wrapper.setBeforLast(this.beforLast);
		wrapper.setCostLevel(this.costLevel);
		wrapper.setLeafCost(this.leafCost);
		return wrapper;
	}
	
	public Long getId() {
		return null;
	}
	
	
	
}
