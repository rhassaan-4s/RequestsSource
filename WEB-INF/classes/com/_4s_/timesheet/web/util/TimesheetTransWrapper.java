package com._4s_.timesheet.web.util;

import java.math.BigDecimal;

public class TimesheetTransWrapper {
	private BigDecimal count;
	
	
	
	public TimesheetTransWrapper(Object count) {
		super();
		this.count = (BigDecimal)count;
	}
	public BigDecimal getCount() {
		return count;
	}
	public void setCount(BigDecimal count) {
		this.count = count;
	}
	
	
	
}
