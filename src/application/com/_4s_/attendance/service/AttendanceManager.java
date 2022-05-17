package com._4s_.attendance.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com._4s_.attendance.model.AttendanceDepartment;
import com._4s_.attendance.model.Qualification;
import com._4s_.attendance.model.Religion;
import com._4s_.attendance.model.Title;
import com._4s_.common.model.EmpBasic;
import com._4s_.common.service.BaseManager;
import com._4s_.timesheet.web.validate.ValidationStatus;
@Transactional
public interface AttendanceManager extends BaseManager {
	public ValidationStatus validateDepartment(AttendanceDepartment department);
	public ValidationStatus validateTitle(Title title);
	public ValidationStatus validateQualification(Qualification qualification);
	public ValidationStatus validateReligion(Religion religion);
	public ValidationStatus validateEmpBasic(EmpBasic title);
	
	
	public Integer getNumberOfAttendees(Date from, Date to,String hostName, String serviceName, String userName, String password);
	public List getNumberOfAttendeesAndWorkersByDepartment(String hostName, String serviceName, String userName, String password);
	public List getDashboardRequests(Date from, Date to,String hostName, String serviceName, String userName, String password);
	
}

