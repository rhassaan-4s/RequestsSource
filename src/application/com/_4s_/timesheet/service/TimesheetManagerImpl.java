package com._4s_.timesheet.service;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.service.BaseManagerImpl;
import com._4s_.common.service.SequenceManager;
import com._4s_.i18n.service.MessageManager;
import com._4s_.restServices.json.RestStatus;
import com._4s_.restServices.json.TimesheetTransDefaultWrapper;
import com._4s_.timesheet.dao.TimesheetDAO;
import com._4s_.timesheet.dao.TimesheetExternalQueries;
import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.model.TimesheetCostCenter;
import com._4s_.timesheet.model.TimesheetSpecs;
import com._4s_.timesheet.model.TimesheetTransaction;
import com._4s_.timesheet.model.TimesheetTransactionDefaults;
import com._4s_.timesheet.model.TimesheetTransactionParts;
import com._4s_.timesheet.web.validate.ValidationStatus;

@Service
@Transactional
public class TimesheetManagerImpl extends BaseManagerImpl implements TimesheetManager{

	private TimesheetDAO timesheetDAO;	
	
	private TimesheetExternalQueries externalQueries = null;
	
	private MessageManager messageManager;
	
	private SequenceManager sequenceManager ;


	
		public TimesheetDAO getTimesheetDAO() {
		return timesheetDAO;
	}

	public void setTimesheetDAO(TimesheetDAO timesheetDAO) {
		this.timesheetDAO = timesheetDAO;
	}

	public TimesheetExternalQueries getExternalQueries() {
		return externalQueries;
	}

	public void setExternalQueries(TimesheetExternalQueries externalQueries) {
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

		public Map insertTimesheetTransaction(String empCode,
				String costCenterCode, String activityCode, String inDate,
				Integer cHour, Integer cMinute, String partCode1,
				String partCode2, String partCode3, String remark) {
			Map results = new HashMap();
			log.debug("1");
			DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
			TimesheetTransaction trans = new TimesheetTransaction();
			TimesheetActivity activity = (TimesheetActivity)getObjectByParameter(TimesheetActivity.class, "activity", activityCode);
			log.debug("activity object for code " + activityCode + " is " + activity);
			TimesheetCostCenter costcenter = (TimesheetCostCenter)getObjectByParameter(TimesheetCostCenter.class, "costCode", costCenterCode);
			Employee employee = (Employee)getObjectByParameter(Employee.class, "empCode", empCode);
			TimesheetTransactionParts nullPart = (TimesheetTransactionParts)getObjectByParameter(TimesheetTransactionParts.class, "code", "9999999999");
			
			if (activity==null) {
				System.out.println("6");
				RestStatus status = new RestStatus();
				status.setCode("346");
				status.setMessage("Timesheet activity code is mandatory");
				status.setStatus("False");
				results.put("Results", new ArrayList());
				results.put("Status", status);
				return results;
			}
			if (costcenter==null) {
				System.out.println("7");
				RestStatus status = new RestStatus();
				status.setCode("347");
				status.setMessage("Timesheet cost center  code is mandatory");
				status.setStatus("False");
				results.put("Results", new ArrayList());
				results.put("Status", status);
				return results;
			}
			
			trans.setActivity(activity);
			trans.setCostCode(costcenter);
			trans.setEmpCode(employee);
			trans.setChour(cHour);
			trans.setCminute(cMinute);
			trans.setRemark(remark);
			try {
				trans.setInDate(df.parse(inDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.debug("2");
			TimesheetTransactionParts part1 = null;
			TimesheetTransactionParts part2 = null;
			TimesheetTransactionParts part3 = null;
			if (partCode1!=null && !partCode1.isEmpty()) {
				part1 = (TimesheetTransactionParts)getObjectByParameter(TimesheetTransactionParts.class, "code", partCode1);
			} else {
				part1 = nullPart;
			}
			if (partCode2!=null && !partCode2.isEmpty()) {
				part2 = (TimesheetTransactionParts)getObjectByParameter(TimesheetTransactionParts.class, "code", partCode2);
			} else {
				part2 = nullPart;
			}
			log.debug("3");
			if (partCode3!=null && !partCode3.isEmpty()) {
				part3 = (TimesheetTransactionParts)getObjectByParameter(TimesheetTransactionParts.class, "code", partCode3);
			} else {
				part3 = nullPart;
			}
			trans.setPart1(part1);
			trans.setPart2(part2);
			trans.setPart3(part3);
			TimesheetTransaction persistedTrans = getTimesheetTrans(employee, trans.getInDate(), costcenter, part1, part2, part3);
			log.debug("persisted before " + persistedTrans);
			log.debug("4");
			if (persistedTrans == null) {
				log.debug("will save object");
				saveObject(trans);
				log.debug("transaction saved " + trans);
				log.debug("5");
				results.put("Results", trans.getWrapper());
				log.debug("6");
				return results;
			} else {
				RestStatus status = new RestStatus();
				status.setCode("351");
				status.setMessage("Timesheet transaction for the same employee, costcenter and parts are already persisted for the specified date");
				status.setStatus("False");
				results.put("Results", new ArrayList());
				results.put("Status", status);
				return results;
			}
		}

		public TimesheetTransaction getTimesheetTrans(Employee empCode,
				Date inDate, TimesheetCostCenter costcenter,
				TimesheetTransactionParts part1,
				TimesheetTransactionParts part2, TimesheetTransactionParts part3) {
			return timesheetDAO.getTimesheetTrans(empCode, inDate, costcenter, part1, part2, part3);
		}

		public Map getTimesheetTransactions(String empCode, String fromDate,
				String toDate, String costcenter,
				String activity, String part1,
				String part2,
				String part3, int pageNo, int pageSize,
				String sort) {
			Settings settings = (Settings)timesheetDAO.getObject(Settings.class,new Long(1));
			String hostName = settings.getServer();
			String serviceName = settings.getService();
			String userName = settings.getUsername();
			String password = settings.getPassword();
			
			DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
			
			TimesheetActivity activityObj = (TimesheetActivity)getObjectByParameter(TimesheetActivity.class, "activity", activity);
			TimesheetCostCenter costcenterObj = (TimesheetCostCenter)getObjectByParameter(TimesheetCostCenter.class, "costCode", costcenter);
			TimesheetTransactionParts nullPart = (TimesheetTransactionParts)getObjectByParameter(TimesheetTransactionParts.class, "code", "9999999999");
			TimesheetTransactionParts partObj1 = null;
			TimesheetTransactionParts partObj2 = null;
			TimesheetTransactionParts partObj3 = null;
			
			if (part1!=null && !part1.isEmpty()) {
				partObj1 = (TimesheetTransactionParts)getObjectByParameter(TimesheetTransactionParts.class, "code", part1);
			} 
//			else {
//				partObj1 = nullPart;
//			}
			if (part2!=null && !part2.isEmpty()) {
				partObj2 = (TimesheetTransactionParts)getObjectByParameter(TimesheetTransactionParts.class, "code", part2);
			}
//			else {
//				partObj2 = nullPart;
//			}
			log.debug("3");
			if (part3!=null && !part3.isEmpty()) {
				partObj3 = (TimesheetTransactionParts)getObjectByParameter(TimesheetTransactionParts.class, "code", part3);
			} 
//			else {
//				partObj3 = nullPart;
//			}
			Date fromDateStr = null;
			Date toDateStr = null;


			if (fromDate!=null && !fromDate.isEmpty()) {
				try {
					fromDateStr = df.parse(fromDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (toDate!=null && !toDate.isEmpty()) {
				try {
					toDateStr = df.parse(toDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return timesheetDAO.getTimesheetTransactions(hostName, serviceName, userName, password, empCode, fromDateStr, toDateStr, costcenterObj, activityObj, partObj1, partObj2, partObj3, pageNo, pageSize, sort);
		}	
}