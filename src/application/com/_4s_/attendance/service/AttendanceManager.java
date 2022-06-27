package com._4s_.attendance.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com._4s_.attendance.model.AttendanceDepartment;
import com._4s_.attendance.model.EmpWorkPeriodListWrapper;
import com._4s_.attendance.model.Qualification;
import com._4s_.attendance.model.Religion;
import com._4s_.attendance.model.Title;
import com._4s_.attendance.model.WorkPeriodMaster;
import com._4s_.common.model.EmpBasic;
import com._4s_.common.model.Settings;
import com._4s_.common.service.BaseManager;
import com._4s_.requestsApproval.model.Vacation;
import com._4s_.timesheet.web.validate.ValidationStatus;
@Transactional
public interface AttendanceManager extends BaseManager {
	public String getContextPath();
	public void setContextPath(String contextPath);
	public Settings getSettings();
	public void setSettings(Settings settings);
	public ValidationStatus validateDepartment(AttendanceDepartment department);
	public ValidationStatus validateTitle(Title title);
	public ValidationStatus validateQualification(Qualification qualification);
	public ValidationStatus validateReligion(Religion religion);
	public ValidationStatus validateEmpBasic(EmpBasic title);
	
	public Integer getNumberOfAttendees(Date from, Date to);
	public List getNumberOfAttendeesAndWorkersByDepartment();
	public List getDashboardRequests();
	public ValidationStatus validateVacation(Vacation vacation);
	public WorkPeriodMaster getWorkPeriodMaster(String workperiodCode);
	public List getActiveEmpBasic();
	public ValidationStatus validateEmpWorkPeriodWrapper(
			EmpWorkPeriodListWrapper periods);
	
}

