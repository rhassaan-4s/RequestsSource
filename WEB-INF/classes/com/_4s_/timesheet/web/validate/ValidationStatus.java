package com._4s_.timesheet.web.validate;

public class ValidationStatus {
	private String status;
	private String objAttribute;
	public String getObjAttribute() {
		return objAttribute;
	}
	public void setObjAttribute(String objAttribute) {
		this.objAttribute = objAttribute;
	}
	private String msg;
	private String code;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
