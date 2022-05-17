package com._4s_.attendance.dao;

import java.util.Date;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com._4s_.common.dao.BaseDAO;
import com._4s_.common.model.Employee;
import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.model.TimesheetCostCenter;
import com._4s_.timesheet.model.TimesheetTransaction;
import com._4s_.timesheet.model.TimesheetTransactionParts;

@Transactional
public interface AttendanceDAO extends BaseDAO {
	public TimesheetTransaction getTimesheetTrans(Employee empCode,Date inDate, TimesheetCostCenter costcenter, TimesheetTransactionParts part1, TimesheetTransactionParts part2, TimesheetTransactionParts part3);
	public Map getTimesheetTransactions(String hostName,String serviceName,String userName,String password,String empCode, Date fromDate, Date toDate, TimesheetCostCenter costcenter, TimesheetActivity activity, TimesheetTransactionParts part1, TimesheetTransactionParts part2, TimesheetTransactionParts part3, int pageNo, int pageSize, String sort);
	public Integer getNumberOfAttendees(Date from, Date to);
}
