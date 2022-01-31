package com._4s_.requestsApproval.model;

import java.io.Serializable;
import java.util.Date;
import com._4s_.common.model.Employee;

public class TimesheetTransactionPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9156290842974249449L;
	protected Employee empCode;
	protected Date inDate;
	protected TimesheetCostCenter costCode;
	protected TimesheetTransactionParts part1;
	protected TimesheetTransactionParts part2;
	protected TimesheetTransactionParts part3;
	
	public TimesheetTransactionPK() {
		
	}
	public TimesheetTransactionPK(Employee empCode, Date inDate,
			TimesheetCostCenter costCode,TimesheetTransactionParts part1,TimesheetTransactionParts part2,TimesheetTransactionParts part3) {
		super();
		this.empCode = empCode;
		this.inDate = inDate;
		this.costCode = costCode;
		this.part1=part1;
		this.part2=part2;
		this.part3=part3;
	}
	
}
