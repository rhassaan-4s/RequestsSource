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
			status.setObjAttribute("name");
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
//		if (emp.getJob_join()==null) {
//			status.setStatus("False");
//			status.setObjAttribute("job_join");
//			status.setMsg("Mandatory");
//			return status;
//		}
		
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
	
	
	public Integer getNumberOfAttendees(Date from, Date to,String hostName, String serviceName, String userName, String password) {
		return externalQueries.getNumberOfAttendees(from, to, hostName, serviceName, userName, password);
	}
	
	public List getNumberOfAttendeesAndWorkersByDepartment(String hostName, String serviceName, String userName, String password) {
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
		return externalQueries.getNumberOfAttendeesAndWorkersByDepartment(from,to,hostName, serviceName, userName, password);
	}
	
	public List getDashboardRequests(String hostName, String serviceName, String userName, String password) {
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
		return externalQueries.getDashboardRequests(from,to,hostName, serviceName, userName, password);
	}

		public Map insertTimesheetActivity(String arName, String enName) {
			TimesheetActivity activity = new TimesheetActivity();
			activity.setName(arName);
			activity.setEname(enName);
			saveObject(activity);
			Map results = new HashMap();
			results.put("Results", activity);
			return results;
		}
		
		public ValidationStatus validateActivity(TimesheetActivity activity) {
			ValidationStatus status = new ValidationStatus();
			if (activity.getName()==null || activity.getName().isEmpty()) {
				status.setStatus("False");
				status.setObjAttribute("name");
				status.setMsg("Mandatory");
				return status;
			}
			if (activity.getEname()==null || activity.getEname().isEmpty()) {
				status.setStatus("False");
				status.setObjAttribute("ename");
				status.setMsg("Mandatory");
				return status;
			}
			Object actArName = getObjectByParameter(TimesheetActivity.class, "name", activity.getName());
			if (actArName!=null && !((TimesheetActivity)actArName).getActivity().equals(activity.getActivity())) {
				//duplicate ar name
				status.setStatus("False");
				status.setObjAttribute("name");
				status.setMsg("Duplicate");
				return status;
			}
			Object actEnName = getObjectByParameter(TimesheetActivity.class, "ename", activity.getEname());
			if (actEnName!=null && !((TimesheetActivity)actEnName).getActivity().equals(activity.getActivity())) {
				//duplicate en name
				status.setStatus("False");
				status.setObjAttribute("ename");
				status.setMsg("Duplicate");
				return status;
			}
			status.setStatus("True");
			return status;
		}
		
		public ValidationStatus validatePart(TimesheetTransactionParts part) {
			ValidationStatus status = new ValidationStatus();
			if (part.getName()==null || part.getName().isEmpty()) {
				status.setStatus("False");
				status.setObjAttribute("name");
				status.setMsg("Mandatory");
				return status;
			}
			if (part.getEname()==null || part.getEname().isEmpty()) {
				status.setStatus("False");
				status.setObjAttribute("ename");
				status.setMsg("Mandatory");
				return status;
			}
			Object partArName = getObjectByTwoParameters(TimesheetTransactionParts.class,"part_no", part.getPart_no(), "name", part.getName());
			log.debug("partarname " + partArName);
			if (partArName!=null && !((TimesheetTransactionParts)partArName).getCode().equals(part.getCode())) {
				//duplicate ar name
				log.debug("duplicate ar");
				status.setStatus("False");
				status.setObjAttribute("name");
				status.setMsg("Duplicate");
				return status;
			}
			Object partEnName = getObjectByTwoParameters(TimesheetTransactionParts.class,"part_no", part.getPart_no(), "ename", part.getEname());
			log.debug("partenname " + partEnName);
			System.out.println("partenname " + partEnName);
			if (partEnName!=null && ((TimesheetTransactionParts)partEnName).getPart_no().equals(part.getPart_no()) && !((TimesheetTransactionParts)partEnName).getCode().equals(part.getCode())) {
				//duplicate en name
				log.debug("duplicate ar");
				System.out.println("duplicate ar");
				status.setStatus("False");
				status.setObjAttribute("ename");
				status.setMsg("Duplicate");
				return status;
			}
			status.setStatus("True");
			return status;
		}

		public Map insertTimesheetPart(String arName, String enName, Short part_no) {
			TimesheetTransactionParts part = new TimesheetTransactionParts();
			part.setName(arName);
			part.setEname(enName);
			System.out.println("part no " + part_no);
			part.setPart_no(part_no);
			saveObject(part);
			Map results = new HashMap();
			results.put("Results", part);
			return results;
		}

		public Map insertTimesheetCostcenter(String costName, String costEName,
				Integer costLevel, String leafCost, String beforeLast) {
			TimesheetCostCenter costcenter = new TimesheetCostCenter();
			costcenter.setCostName(costName);
			costcenter.setEng_name(costEName);
			costcenter.setCostLevel(costLevel);
			costcenter.setLeafCost(leafCost);
			costcenter.setBeforeLast(beforeLast);
			saveObject(costcenter);
			Map results = new HashMap();
			results.put("Results", costcenter);
			return results;
		}

		public Map insertTimesheetSpecs(String partName1, String partEName1,
				String isUsed1, String partName2, String partEName2,
				String isUsed2, String partName3, String partEName3,
				String isUsed3) {
			TimesheetSpecs specs = new TimesheetSpecs();
			Map results = new HashMap();
			Object availableSpecs = getObject(TimesheetSpecs.class,new Long(1));
			if (availableSpecs!= null) {
				specs = (TimesheetSpecs)availableSpecs;
			} else {
				specs.setCode("1");
			}
			specs.setPart1_name(partName1);
			specs.setPart1_ename(partEName1);
			specs.setIs_used1(isUsed1);
			specs.setPart2_name(partName2);
			specs.setPart2_ename(partEName2);
			specs.setIs_used2(isUsed2);
			specs.setPart3_name(partName3);
			specs.setPart3_ename(partEName3);
			specs.setIs_used3(isUsed3);
			try {
				saveObject(specs);
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				if (e.getMessage().equals("Unable to generate Primary Key")) {
					RestStatus status = new RestStatus();
					results.put("Results", null);
					status.setCode("335");
					status.setMessage("Unable to generate specifications code (please reset the sequence)");
					status.setStatus("False");
					results.put("Status", status);
					return results;
				}
				e.printStackTrace();
			}
			
			results.put("Results", specs);
			return results;
		}

		public Map insertTimesheetTransDefaults(String empCode,
				String costCenterCode, String activityCode, String partCode1,
				String partCode2, String partCode3) {
			// TODO Auto-generated method stub
			TimesheetTransactionDefaults defaults = new TimesheetTransactionDefaults();
			TimesheetActivity activity = (TimesheetActivity)getObjectByParameter(TimesheetActivity.class, "activity", activityCode);
			TimesheetCostCenter costcenter = (TimesheetCostCenter)getObjectByParameter(TimesheetCostCenter.class, "costCode", costCenterCode);
			Employee employee = (Employee)getObjectByParameter(Employee.class, "empCode", empCode);
			if (partCode1!=null && !partCode1.isEmpty()) {
				TimesheetTransactionParts part1 = (TimesheetTransactionParts)getObjectByParameter(TimesheetTransactionParts.class, "code", partCode1);
				defaults.setPart1(part1);
			}
			if (partCode2!=null && !partCode2.isEmpty()) {
				TimesheetTransactionParts part2 = (TimesheetTransactionParts)getObjectByParameter(TimesheetTransactionParts.class, "code", partCode2);
				defaults.setPart2(part2);
			}
			if (partCode3!=null && !partCode3.isEmpty()) {
				TimesheetTransactionParts part3 = (TimesheetTransactionParts)getObjectByParameter(TimesheetTransactionParts.class, "code", partCode3);
				defaults.setPart3(part3);
			}
			defaults.setActivity(activity);
			defaults.setCostCode(costcenter);
			defaults.setEmpCode(employee);
			saveObject(defaults);
			TimesheetTransDefaultWrapper defaultsWrapper = defaults.getWrapper();
			Map results = new HashMap();
			results.put("Results", defaultsWrapper);
			return results;
		}
}