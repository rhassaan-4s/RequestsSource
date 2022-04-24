package com._4s_.restServices.json;

public class TimesheetCostcenterWrapper {
	private String costCode;
	private String arName;
	private String enName;
	private Integer costLevel;
	private String leafCost;
	private String beforLast;
	
	public String getCostCode() {
		return costCode;
	}
	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}
	
	public String getArName() {
		return arName;
	}
	public void setArName(String arName) {
		this.arName = arName;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
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
	public String getBeforLast() {
		return beforLast;
	}
	public void setBeforLast(String beforLast) {
		this.beforLast = beforLast;
	}
	
}
