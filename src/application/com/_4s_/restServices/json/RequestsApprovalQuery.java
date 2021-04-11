package com._4s_.restServices.json;

public class RequestsApprovalQuery {

	private String requestNumber;
	private String emp_code; 
	private String dateFrom; 
	private String dateTo; 
	private String exactDateFrom; 
	private String exactDateTo; 
	private String requestType; //1:Special Vacations 2:Periodic Vacations 3:Permissions 4:Special&Periodic Vacations 5:Sign in/out
	private String codeFrom; 
	private String codeTo; 
	private String statusId;
	private String isInsideCompany;//0:false 1: true
	private int pageNumber;
	private int pageSize;
	private String sort; //asc, desc 
	
	public String getIsInsideCompany() {
		return isInsideCompany;
	}
	public void setIsInsideCompany(String isInsideCompany) {
		this.isInsideCompany = isInsideCompany;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	public String getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getExactDateFrom() {
		return exactDateFrom;
	}
	public void setExactDateFrom(String exactDateFrom) {
		this.exactDateFrom = exactDateFrom;
	}
	public String getExactDateTo() {
		return exactDateTo;
	}
	public void setExactDateTo(String exactDateTo) {
		this.exactDateTo = exactDateTo;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getCodeFrom() {
		return codeFrom;
	}
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}
	public String getCodeTo() {
		return codeTo;
	}
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	
	
}
