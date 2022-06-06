package com._4s_.attendance.service;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.attendance.dao.AttendanceDAO;
import com._4s_.attendance.dao.AttendanceExternalQueries;
import com._4s_.attendance.model.AttendanceDepartment;
import com._4s_.attendance.model.Qualification;
import com._4s_.attendance.model.Religion;
import com._4s_.attendance.model.Title;
import com._4s_.common.model.EmpBasic;
import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.service.BaseManagerImpl;
import com._4s_.common.service.SequenceManager;
import com._4s_.i18n.service.MessageManager;
import com._4s_.requestsApproval.model.Vacation;
import com._4s_.restServices.json.RestStatus;
import com._4s_.restServices.json.TimesheetTransDefaultWrapper;
import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.model.TimesheetCostCenter;
import com._4s_.timesheet.model.TimesheetSpecs;
import com._4s_.timesheet.model.TimesheetTransactionDefaults;
import com._4s_.timesheet.model.TimesheetTransactionParts;
import com._4s_.timesheet.web.validate.ValidationStatus;

@Service
@Transactional
public class AttendanceManagerImpl extends BaseManagerImpl implements AttendanceManager{

	private AttendanceDAO attendanceDAO;	
	
	private AttendanceExternalQueries externalQueries = null;
	
	private MessageManager messageManager;
	
	private SequenceManager sequenceManager ;
	
	private String contextPath="";
	
	private Settings settings;
	
	
	
	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public String getContextPath() {
		log.debug(contextPath);
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
		log.debug(this.contextPath);
	}

	public AttendanceDAO getAttendanceDAO() {
		return attendanceDAO;
	}

	public void setAttendanceDAO(AttendanceDAO attendanceDAO) {
		this.attendanceDAO = attendanceDAO;
	}

	public AttendanceExternalQueries getExternalQueries() {
		return externalQueries;
	}

	public void setExternalQueries(AttendanceExternalQueries externalQueries) {
		this.externalQueries = externalQueries;
	}

	public MessageManager getMessageManager() {
		return messageManager;
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public SequenceManager getSequenceManager() {
		return sequenceManager;
	}

	public void setSequenceManager(SequenceManager sequenceManager) {
		this.sequenceManager = sequenceManager;
	}

	
	
	public ValidationStatus validateDepartment(AttendanceDepartment department) {
		ValidationStatus status = new ValidationStatus();
		if (department.getName()==null || department.getName().isEmpty()) {
			status.setStatus("False");
			status.setObjAttribute("name");
			status.setMsg("Mandatory");
			return status;
		}
//		if (department.getEname()==null || department.getEname().isEmpty()) {
//			status.setStatus("False");
//			status.setObjAttribute("ename");
//			status.setMsg("Mandatory");
//			return status;
//		}
		Object deptArName = getObjectByParameter(AttendanceDepartment.class, "name", department.getName());
		log.debug("new " + department.getLocation());
		if (deptArName!=null && !((AttendanceDepartment)deptArName).getLocation().equals(department.getLocation())) {
			//duplicate ar name
			status.setStatus("False");
			status.setObjAttribute("name");
			status.setMsg("Duplicate");
			return status;
		}
		Object deptEnName = getObjectByParameter(AttendanceDepartment.class, "ename", department.getEname());
		if (deptEnName!=null && !((AttendanceDepartment)deptEnName).getLocation().equals(department.getLocation())) {
			//duplicate en name
			status.setStatus("False");
			status.setObjAttribute("ename");
			status.setMsg("Duplicate");
			return status;
		}
		status.setStatus("True");
		return status;
	}
	
	public ValidationStatus validateTitle(Title title) {
		ValidationStatus status = new ValidationStatus();
		if (title.getName()==null || title.getName().isEmpty()) {
			status.setStatus("False");
			status.setObjAttribute("name");
			status.setMsg("Mandatory");
			return status;
		}
//		if (title.getEname()==null || title.getEname().isEmpty()) {
//			status.setStatus("False");
//			status.setObjAttribute("ename");
//			status.setMsg("Mandatory");
//			return status;
//		}
		List titleArName = getObjectsByParameter(Title.class, "name", title.getName());
		log.debug("new " + title.getTitle());
		if (titleArName!=null && titleArName.size()>0 && !((Title)(titleArName.get(0))).getTitle().equals(title.getTitle())) {
			//duplicate ar name
			status.setStatus("False");
			status.setObjAttribute("name");
			status.setMsg("Duplicate");
			return status;
		}
		List titleEnName = getObjectsByParameter(Title.class, "ename", title.getEname());
		if (titleEnName!=null && titleEnName.size()>0 && !((Title)(titleEnName.get(0))).getTitle().equals(title.getTitle())) {
			//duplicate en name
			status.setStatus("False");
			status.setObjAttribute("ename");
			status.setMsg("Duplicate");
			return status;
		}
		status.setStatus("True");
		return status;
	}
	
	public ValidationStatus validateQualification(Qualification qual) {
		ValidationStatus status = new ValidationStatus();
		if (qual.getName()==null || qual.getName().isEmpty()) {
			status.setStatus("False");
			status.setObjAttribute("name");
			status.setMsg("Mandatory");
			return status;
		}
//		if (qual.getEname()==null || qual.getEname().isEmpty()) {
//			status.setStatus("False");
//			status.setObjAttribute("ename");
//			status.setMsg("Mandatory");
//			return status;
//		}
		List qualArName = getObjectsByParameter(Qualification.class, "name", qual.getName());
		log.debug("new " + qual.getQual());
		if (qualArName!=null && qualArName.size()>0 && !((Qualification)(qualArName.get(0))).getQual().equals(qual.getQual())) {
			//duplicate ar name
			status.setStatus("False");
			status.setObjAttribute("name");
			status.setMsg("Duplicate");
			return status;
		}
		List qualEnName = getObjectsByParameter(Qualification.class, "ename", qual.getEname());
		if (qualEnName!=null && qualEnName.size()>0 && !((Qualification)(qualEnName.get(0))).getQual().equals(qual.getQual())) {
			//duplicate en name
			status.setStatus("False");
			status.setObjAttribute("ename");
			status.setMsg("Duplicate");
			return status;
		}
		status.setStatus("True");
		return status;
	}
	
	public ValidationStatus validateReligion(Religion religion) {
		ValidationStatus status = new ValidationStatus();
		if (religion.getName()==null || religion.getName().isEmpty()) {
			status.setStatus("False");
			status.setObjAttribute("name");
			status.setMsg("Mandatory");
			return status;
		}
//		if (religion.getEname()==null || religion.getEname().isEmpty()) {
//			status.setStatus("False");
//			status.setObjAttribute("ename");
//			status.setMsg("Mandatory");
//			return status;
//		}
		List religionArName = getObjectsByParameter(Religion.class, "name", religion.getName());
		log.debug("new " + religion.getReligion());
		if (religionArName!=null && religionArName.size()>0 && !((Religion)(religionArName.get(0))).getReligion().equals(religion.getReligion())) {
			//duplicate ar name
			status.setStatus("False");
			status.setObjAttribute("name");
			status.setMsg("Duplicate");
			return status;
		}
		List religionEnName = getObjectsByParameter(Religion.class, "ename", religion.getEname());
		if (religionEnName!=null && religionEnName.size()>0 && !((Religion)(religionEnName.get(0))).getReligion().equals(religion.getReligion())) {
			//duplicate en name
			status.setStatus("False");
			status.setObjAttribute("ename");
			status.setMsg("Duplicate");
			return status;
		}
		status.setStatus("True");
		return status;
	}
	
	public ValidationStatus validateEmpBasic(EmpBasic emp) {
		ValidationStatus status = new ValidationStatus();
		if (emp.getEmpName()==null || emp.getEmpName().isEmpty()) {
			status.setStatus("False");
			status.setObjAttribute("empName");
			status.setMsg("Mandatory");
			return status;
		}
		if (emp.getTitle()==null) {
			status.setStatus("False");
			status.setObjAttribute("title");
			status.setMsg("Mandatory");
			return status;
		}
		if (emp.getDepartment()==null) {
			status.setStatus("False");
			status.setObjAttribute("department");
			status.setMsg("Mandatory");
			return status;
		}
		if (emp.getMaritalStatus()==null) {
			status.setStatus("False");
			status.setObjAttribute("maritalStatus");
			status.setMsg("Mandatory");
			return status;
		}
		if (emp.getEldiana()==null) {
			status.setStatus("False");
			status.setObjAttribute("eldiana");
			status.setMsg("Mandatory");
			return status;
		}
		if (emp.getSex()==null || emp.getSex().isEmpty()) {
			status.setStatus("False");
			status.setObjAttribute("sex");
			status.setMsg("Mandatory");
			return status;
		}
		if (emp.getEmplDate()==null) {
			status.setStatus("False");
			status.setObjAttribute("emplDate");
			status.setMsg("Mandatory");
			return status;
		}
		List empBasicArName = getObjectsByParameter(EmpBasic.class, "empName", emp.getEmpName());
		if (empBasicArName!=null && empBasicArName.size()>0 && !((EmpBasic)(empBasicArName.get(0))).getEmpName().equals(emp.getEmpName())) {
			//duplicate ar name
			status.setStatus("False");
			status.setObjAttribute("empName");
			status.setMsg("Duplicate");
			return status;
		}
		List empBasicEnName = getObjectsByParameter(EmpBasic.class, "e_emp_name", emp.getE_emp_name());
		if (empBasicEnName!=null && empBasicEnName.size()>0 && !((EmpBasic)(empBasicEnName.get(0))).getE_emp_name().equals(emp.getE_emp_name())) {
			//duplicate ar name
			status.setStatus("False");
			status.setObjAttribute("e_emp_name");
			status.setMsg("Duplicate");
			return status;
		}
		List nationalID = getObjectsByParameter(EmpBasic.class, "natnl_no", emp.getNatnl_no());
		if (nationalID!=null && nationalID.size()>0 && !((EmpBasic)(nationalID.get(0))).getNatnl_no().equals(emp.getNatnl_no())) {
			//duplicate ar name
			status.setStatus("False");
			status.setObjAttribute("natnl_no");
			status.setMsg("Duplicate");
			return status;
		}
		status.setStatus("True");
		return status;
	}
	
	
	
	@Override
	public ValidationStatus validateVacation(Vacation vacation) {
		ValidationStatus status = new ValidationStatus();
		if (vacation.getName()==null || vacation.getName().isEmpty()) {
			status.setStatus("False");
			status.setObjAttribute("name");
			status.setMsg("Mandatory");
			return status;
		}
//		if (vacation.getEname()==null || vacation.getEname().isEmpty())  {
//			status.setStatus("False");
//			status.setObjAttribute("title");
//			status.setMsg("Mandatory");
//			return status;
//		}
		List vacNames = getObjectsByParameter(Vacation.class, "name", vacation.getName());
		
		if (vacNames!=null && vacNames.size()>0 && ((Vacation)(vacNames.get(0))).getName().equals(vacation.getName())) {
			log.debug("((Vacation)(vacNames.get(0))).getName().equals(vacation.getName())"+((Vacation)(vacNames.get(0))).getName().equals(vacation.getName()));
			//duplicate ar name
			status.setStatus("False");
			status.setObjAttribute("name");
			status.setMsg("Duplicate");
			return status;
		}
		List vacENames = getObjectsByParameter(Vacation.class, "ename", vacation.getEname());
		if (vacENames!=null && vacENames.size()>0 && ((Vacation)(vacENames.get(0))).getEname().equals(vacation.getEname())) {
			//duplicate en name
			status.setStatus("False");
			status.setObjAttribute("ename");
			status.setMsg("Duplicate");
			return status;
		}
		status.setStatus("True");
		return status;
	}

	public Integer getNumberOfAttendees(Date from, Date to) {
		return externalQueries.getNumberOfAttendees(from, to,settings);
	}
	
	public List getNumberOfAttendeesAndWorkersByDepartment() {
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		Date from = cal1.getTime();
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.HOUR_OF_DAY, 23);
		cal2.set(Calendar.MINUTE, 59);
		cal2.set(Calendar.SECOND, 59);
		Date to = cal2.getTime();
		return externalQueries.getNumberOfAttendeesAndWorkersByDepartment(from,to,settings);
	}
	
	public List getDashboardRequests() {
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		Date from = cal1.getTime();
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.HOUR_OF_DAY, 23);
		cal2.set(Calendar.MINUTE, 59);
		cal2.set(Calendar.SECOND, 59);
		Date to = cal2.getTime();
		return externalQueries.getDashboardRequests(from,to,settings);
	}
}