package com._4s_.requestsApproval.web.util;

import java.math.BigDecimal;
import java.sql.Timestamp;


public class AnnualVacationBalanceResultWrapper {
	private Timestamp fr_date;
	private Timestamp to_date;
	private Long withdr;
	private String vacation;
	
	public AnnualVacationBalanceResultWrapper(Object fr_date, Object to_date, Object withdr,Object vacation) {
		super();
		if (fr_date !=null) {
			this.fr_date = (Timestamp)fr_date;
		}
		if (to_date !=null) {
			this.to_date = (Timestamp)to_date;
		}
		if (withdr!=null) {
			this.withdr = ((BigDecimal)withdr).longValue();
		}
		if (vacation!=null) {
			this.vacation = (String)vacation;
		}
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



	public Long getWithdr() {
		return withdr;
	}

	public void setWithdr(Long withdr) {
		this.withdr = withdr;
	}

	public String getVacation() {
		return vacation;
	}

	public void setVacation(String vacation) {
		this.vacation = vacation;
	}
	
	
	
}
