package com._4s_.timesheet.service;

import java.util.Date;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com._4s_.common.model.Employee;
import com._4s_.common.service.BaseManager;
import com._4s_.timesheet.model.TimesheetCostCenter;
import com._4s_.timesheet.model.TimesheetTransaction;
import com._4s_.timesheet.model.TimesheetTransactionParts;
@Transactional
public interface TimesheetManager extends BaseManager {
	
	public Map insertTimesheetActivity(String arName, String enName);
	public Map insertTimesheetPart(String arName, String enName, Short part_no);
	public Map insertTimesheetCostcenter(String costName, String costEName,
			Integer costLevel, String leafCost, String beforeLast);
	public Map insertTimesheetSpecs(String partName1, String partEName1,
			String isUsed1, String partName2, String partEName2,
			String isUsed2, String partName3, String partEName3, String isUsed3);
	public Map insertTimesheetTransDefaults(String empCode,
			String costCenterCode, String activityCode, String partCode1,
			String partCode2, String partCode3);
	public Map insertTimesheetTransaction(String empCode,
			String costCenterCode, String activityCode, String inDate,
			Integer getcHour, Integer getcMinute, String partCode1,
			String partCode2, String partCode3, String remark);
	public TimesheetTransaction getTimesheetTrans(Employee empCode,Date inDate, TimesheetCostCenter costcenter, TimesheetTransactionParts part1, TimesheetTransactionParts part2, TimesheetTransactionParts part3);
	public Map getTimesheetTransactions(String empCode, String fromDate, String toDate, String costcenter, String activity, String part1, String part2, String part3, int pageNo, int pageSize, String sort);

}

