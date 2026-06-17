package com._4s_.requestsApproval.web.util;

import java.math.BigDecimal;
import java.sql.Timestamp;


public class VacationsResultWrapper {
	private Timestamp fr_date;
	private Timestamp to_date;
	private BigDecimal withdr;
	private String vacation;
	private String empCode;
	private String fName;
	
	public VacationsResultWrapper(Object fr_date, Object to_date, Object withdr,Object vacation) {
		super();
		
		if (vacation!=null) {
			this.vacation = (String)vacation;
		}
		if (fr_date !=null) {
			this.fr_date = (Timestamp)fr_date;
		}
		if (to_date !=null) {
			this.to_date = (Timestamp)to_date;
		}
		if (withdr!=null) {
			this.withdr = (BigDecimal)withdr;
		}
		
	}

	
	public VacationsResultWrapper(Object fr_date, Object to_date, Object withdr,Object vacation,Object empCode,Object fName) {
		super();
		if (empCode!=null) {
			this.empCode = (String)empCode;
		}
		
		if (fName!=null) {
			this.fName = (String)fName;
		}
		if (vacation!=null) {
			this.vacation = (String)vacation;
		}
		if (fr_date !=null) {
			this.fr_date = (Timestamp)fr_date;
		}
		if (to_date !=null) {
			this.to_date = (Timestamp)to_date;
		}
		if (withdr!=null) {
			this.withdr = (BigDecimal)withdr;
		}
		
	}

	
	public String getEmpCode() {
		return empCode;
	}


	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}


	public String getfName() {
		return fName;
	}


	public void setfName(String fName) {
		this.fName = fName;
	}


	public Timestamp getFr_date() {
		return fr_date;
	}



	public void setFr_date(Timestamp fr_date) {
		this.fr_date = fr_date;
	}



	public Timestamp getTo_date() {
		return to_date;
	}



	public void setTo_date(Timestamp to_date) {
		this.to_date = to_date;
	}


	public BigDecimal getWithdr() {
		return withdr;
	}


	public void setWithdr(BigDecimal withdr) {
		this.withdr = withdr;
	}


	public String getVacation() {
		return vacation;
	}


	public void setVacation(String vacation) {
		this.vacation = vacation;
	}



	
	
	
}
