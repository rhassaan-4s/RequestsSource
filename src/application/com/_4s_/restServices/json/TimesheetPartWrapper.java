package com._4s_.restServices.json;

public class TimesheetPartWrapper {
	private String arName;
	private String enName;
	private String partCode;
	
	private Short partNo;
	public Short getPartNo() {
		return partNo;
	}
	public void setPartNo(Short partNo) {
		this.partNo = partNo;
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
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
}
