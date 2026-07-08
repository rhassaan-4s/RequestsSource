package com._4s_.attendance.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com._4s_.attendance.model.WorkPeriodMaster;
import com._4s_.common.dao.BaseDAO;
import com._4s_.common.model.Employee;
import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.model.TimesheetCostCenter;
import com._4s_.timesheet.model.TimesheetTransaction;
import com._4s_.timesheet.model.TimesheetTransactionParts;

@Transactional
public interface AttendanceDAO extends BaseDAO {
	public Integer getNumberOfAttendees(Date from, Date to);

	public WorkPeriodMaster getWorkPeriodMaster(String workperiodCode);

	public List getActiveEmpBasic();
}
