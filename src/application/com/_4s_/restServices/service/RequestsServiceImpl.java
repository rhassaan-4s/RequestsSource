package com._4s_.restServices.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.common.dao.Queries;
import com._4s_.common.model.Clients;
import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.requestsApproval.dao.RequestsApprovalDAO;
import com._4s_.requestsApproval.model.AccessLevels;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.model.Vacation;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.requestsApproval.web.action.TimeAttend;
import com._4s_.restServices.json.AttendanceRequest;
import com._4s_.restServices.json.EmployeeResponse;
import com._4s_.restServices.json.EmployeeWrapper;
import com._4s_.restServices.json.GroupWrapper;
import com._4s_.restServices.json.LoginUserWrapper;
import com._4s_.restServices.json.PasswordWrapper;
import com._4s_.restServices.json.RequestApproval;
import com._4s_.restServices.json.RequestsApprovalQuery;
import com._4s_.restServices.json.RestStatus;
import com._4s_.restServices.json.TimesheetActivityWrapper;
import com._4s_.restServices.json.TimesheetPartWrapper;
import com._4s_.restServices.json.TimesheetSpecsWrapper;
import com._4s_.restServices.json.TimesheetTransDefaultWrapper;
import com._4s_.restServices.json.TimesheetTransWrapper;
import com._4s_.restServices.json.TimesheetTransactionFilters;
import com._4s_.restServices.json.UserWrapper;
import com._4s_.restServices.model.AttendanceStatus;
import com._4s_.security.dao.MySecurityDAO;
import com._4s_.security.model.Imei;
import com._4s_.security.model.User;
import com._4s_.timesheet.dao.TimesheetDAO;
//import com.javacodegeeks.gwtspring.server.utils.NotificationsProducer;
//import com.javacodegeeks.gwtspring.shared.dto.EmployeeDTO;
//import com.javacodegeeks.gwtspring.shared.services.EmployeeService;
import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.model.TimesheetCostCenter;
import com._4s_.timesheet.model.TimesheetSpecs;
import com._4s_.timesheet.model.TimesheetTransactionParts;
import com._4s_.timesheet.service.TimesheetManager;
import com._4s_.timesheet.web.validate.ValidationStatus;


@Service("requestsService")
@Transactional
public class RequestsServiceImpl implements RequestsService, UserDetailsService {
	
	protected final Log log = LogFactory.getLog(getClass());
 
 @Autowired
 private RequestsApprovalDAO requestsApprovalDAO;
 
 @Autowired
 private RequestsApprovalManager requestsApprovalManager;
 
 @Autowired
 private TimesheetDAO timesheetDAO;
 
 @Autowired
 private TimesheetManager timesheetManager;
 
 @Autowired
 private MySecurityDAO securityDao;


 @Autowired
 private Queries qry;
 
 @Transactional
 public RequestsApprovalManager getRequestsApprovalManager() {
	return requestsApprovalManager;
}

public void setRequestsApprovalManager(
		RequestsApprovalManager requestsApprovalManager) {
	this.requestsApprovalManager = requestsApprovalManager;
}

public RequestsApprovalDAO getRequestsApprovalDAO() {
	return requestsApprovalDAO;
}

public void setRequestsApprovalDAO(RequestsApprovalDAO requestsApprovalDAO) {
	this.requestsApprovalDAO = requestsApprovalDAO;
}

public TimesheetDAO getTimesheetDAO() {
	return timesheetDAO;
}

public void setTimesheetDAO(TimesheetDAO timesheetDAO) {
	this.timesheetDAO = timesheetDAO;
}

public TimesheetManager getTimesheetManager() {
	return timesheetManager;
}

public void setTimesheetManager(TimesheetManager timesheetManager) {
	this.timesheetManager = timesheetManager;
}

public MySecurityDAO getSecurityDao() {
	return securityDao;
}

public void setSecurityDao(MySecurityDAO securityDao) {
	this.securityDao = securityDao;
}



public Queries getQry() {
	return qry;
}

public void setQry(Queries qry) {
	this.qry = qry;
}



 @PostConstruct
 public void init() throws Exception {
 }
 
 @PreDestroy
 public void destroy() {
 }

 public User login() {
	 log.debug("security dao " + securityDao);
	 return securityDao.login();
 }

public Map changePassword(PasswordWrapper passwordWrapper, User user) {
	Map results = new HashMap();
	RestStatus restStatus = new RestStatus();
	
	if(user.getPassword().equals(passwordWrapper.getOldPassword())) {
		user.setPassword(passwordWrapper.getNewPassword());
		requestsApprovalManager.saveObject(user);
		restStatus.setCode("200");
		restStatus.setMessage("Request Success");
		restStatus.setStatus("True");
		results.put("Status", restStatus);
	} else {
		restStatus.setCode("332");
		restStatus.setMessage("Wrong Password");
		restStatus.setStatus("False");
		results.put("Status", restStatus);
	}
	return results;
}

public Boolean checkImei(String imei, User user) {
	// TODO Auto-generated method stub
	Imei im = securityDao.checkImei(imei,user);
	log.debug("imei " + im);
	if (im != null) {
		return new Boolean(true);
	} else {
		return new Boolean(false);
	}
}

public List getUsersImei(User user) {
	List imei = requestsApprovalDAO.getObjectsByParameter(Imei.class, "users", user);
	return imei;
}

public void saveImei(Imei im) {
	requestsApprovalDAO.saveObject(im);
}

public User getImeiUsers(String imei) {
	Object imeiObject = requestsApprovalDAO.getObjectByParameter(Imei.class, "imei", imei);
	if (imeiObject != null) {
		Imei im = (Imei)imeiObject;
		return im.getUsers();
	} else {
		return null;
	}
}

public Map signInOut(AttendanceRequest userRequest,Long empId) {
	// TODO Auto-generated method stub
	Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class, new Long(1));

//	 System.setProperty("user.timezone", "Europe/Rome");
	 log.debug("#########################"+System.getProperty("user.timezone"));
	    
	DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	Date newDate = null;
	
	Map response = new HashMap();
	RestStatus restStatus = new RestStatus();
	
	
//	TimeZone tz = TimeZone.getTimeZone("Europe/Rome");
//	TimeZone.setDefault(tz);
//	Calendar c = Calendar.getInstance(tz);
	
	Calendar c= Calendar.getInstance();
	
	log.debug("timezone " + c.getTimeZone());
//	c.setTimeZone(TimeZone.getTimeZone("EST"));
//	log.debug("timezone " + c.getTimeZone());
	newDate = c.getTime();
	log.debug("parsed date " + newDate);
	MultiCalendarDate mCalDate = new MultiCalendarDate();
	mCalDate.setDate(newDate);
	
	log.debug("SignInOunt: mcaldate " + mCalDate.getDate());
	
	MultiCalendarDate mCalDateOnly = new MultiCalendarDate();
	Calendar cal = Calendar.getInstance();
	cal.setTime(newDate);
	cal.set(Calendar.HOUR_OF_DAY, 0);
	cal.set(Calendar.MINUTE, 0);
	cal.set(Calendar.SECOND, 0);
	cal.set(Calendar.MILLISECOND,0);
	Date dateOnly = cal.getTime();
	mCalDateOnly.setDate(dateOnly);
	
	
	Employee emp = (Employee)requestsApprovalDAO.getObject(Employee.class, empId);

	LoginUsers loginUsers=(LoginUsers) requestsApprovalDAO.getObjectByParameter(LoginUsers.class, "empCode", emp);
	if(loginUsers!=null){
		log.debug("-----login.code----"+loginUsers.getEmpCode());
	}

	log.debug("settings.getAutomaticSignInOut().booleanValue() " + settings.getAutomaticSignInOut().booleanValue());
	if (settings.getAutomaticSignInOut().booleanValue() == false) {
//		List attendanceRequests = requestsApprovalManager.getAttendanceRequests(mCalDate.getDate(),loginUsers.getEmpCode());
		log.debug("Will sign in manually");
		
		log.debug("will validate");
		
		restStatus = requestsApprovalManager.validateSignInOut(userRequest.getAttendanceType(), mCalDate.getDate(), emp);
		
		if (restStatus.getStatus().equals("False")) {
			response.put("Status", restStatus);
			return response;
		} else {
			return createManualAttendance(loginUsers,mCalDate,emp,userRequest);
		}
	} else {
		String trans_type = null;
		if (userRequest.getAttendanceType().equals(new Long(10))) {
			trans_type = "I";
		} else {
			trans_type = "O";
		}
		log.debug("date_" + dateOnly + " time_ " + newDate);
		int result = requestsApprovalManager.insertTimeAttendance(emp.getEmpCode(),dateOnly, newDate,trans_type);
		restStatus.setStatus("true");
		restStatus.setCode("200");
		restStatus.setMessage("Successful Automatic Transaction");
		response.put("Status", restStatus);
		
		response.put("Response" ,null);
		return response;
	}
}



private Map createManualAttendance(LoginUsers loginUsers, MultiCalendarDate mCalDate, Employee emp, AttendanceRequest userRequest) {
	LoginUsersRequests loginUsersRequests = new LoginUsersRequests();

	loginUsersRequests.setLogin_user(loginUsers);
	/////////////////////////////////////////////////////

	// request number
	if (loginUsersRequests.getId() == null){
		String requestNumber="";
		requestNumber=requestsApprovalManager.CreateRequestNumber();
		loginUsersRequests.setRequestNumber(requestNumber);
	}

	loginUsersRequests.setRequest_date(mCalDate.getDate());
	loginUsersRequests.setPeriod_from(mCalDate.getDate());

	if(loginUsersRequests.getApproved()==null || loginUsersRequests.getApproved().equals("")){
		loginUsersRequests.setApproved(new Long(0));	
	}
	if(loginUsersRequests.getApplicable()==null || loginUsersRequests.getApplicable().equals("")){
		loginUsersRequests.setApplicable(new Long(1));			
	}
	if(loginUsersRequests.getPosted()==null||loginUsersRequests.getPosted().equals("")){
		loginUsersRequests.setPosted(new Long(0));
	}
	if(loginUsersRequests.getReply()==null||loginUsersRequests.getReply().equals("")){
		loginUsersRequests.setReply("--");
	}
	if(loginUsersRequests.getLeave_effect()==null||loginUsersRequests.getLeave_effect().equals("")){
		loginUsersRequests.setLeave_effect("0");
	}
	if(loginUsersRequests.getLeave_type()==null||loginUsersRequests.getLeave_type().equals("")){
		loginUsersRequests.setLeave_type("0");
	}
	if(loginUsersRequests.getFrom_date()==null|| loginUsersRequests.getFrom_date().equals("")){
		loginUsersRequests.setFrom_date(loginUsersRequests.getPeriod_from());
	}

	loginUsersRequests.setEmpCode(emp.getEmpCode());
	loginUsersRequests.setNotes("Android Sign In/Out");

	loginUsersRequests.setInputType(new Integer(2));//android request
	
	////////////////////////////////////////////////////////////////////////////
	Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class, new Long(1));
	
	Double distance = requestsApprovalManager.distance(new Double(userRequest.getLatitude()), new Double(userRequest.getLongitude()), new Double(settings.getCompanyLat()), new Double(settings.getCompanyLong()));
	log.debug("lat " + new Double(userRequest.getLatitude()) + " - long " +  new Double(userRequest.getLongitude()));
	log.debug("company lat " +  new Double(settings.getCompanyLat()) + " - long " +  new Double(settings.getCompanyLong()));
	log.debug("distance " + distance + " - allowed distance " + settings.getDistAllowedFromCompany());
	if (distance>settings.getDistAllowedFromCompany()) {
		loginUsersRequests.setIsInsideCompany(false);
	} else {
		loginUsersRequests.setIsInsideCompany(true);
	}
	//////////////////////////////////////////////////////////////////////////
	log.debug("will get address using coordinates");
	try{
		String address = requestsApprovalManager.getAddressByGpsCoordinates(userRequest.getLongitude()+"", userRequest.getLatitude()+"");
		log.debug("address " + address);
		loginUsersRequests.setLocationAddress(address);
	} catch(Exception e) {
		e.printStackTrace();
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	//10 signin 11 signout
	RequestTypes reqType = null;
	log.debug("userRequest.getAttendanceType() " + userRequest.getAttendanceType());
	if (userRequest.getAttendanceType().equals(new Long(10))) {
		reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(10));
	} else {
		reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(11));
	}
	loginUsersRequests.setRequest_id(reqType);

	loginUsersRequests.setLatitude(userRequest.getLatitude());
	loginUsersRequests.setLongitude(userRequest.getLongitude());

	if(settings.getAndroidAttAutomaticApproval().equals(true)) {
		RequestApproval approvals = new RequestApproval();
		approvals.setApprove("1");
		approvals.setNotes("Android Sign in/out Automatic Approval");
		approvals.setRequestId(loginUsersRequests.getId()+"");
		requestsApprovalManager.automaticApprovalsAccessLevels(approvals, loginUsersRequests);
	}
	requestsApprovalManager.saveObject(loginUsersRequests);

//	return loginUsersRequests;
	Map response = new HashMap();
	RestStatus restStatus = new RestStatus();
	restStatus.setStatus("true");
	restStatus.setCode("200");
	restStatus.setMessage("Successful Manual Transaction");
	response.put("Status", restStatus);
	
	Map output = new HashMap();
	output.put("request_number", loginUsersRequests.getRequestNumber());
	response.put("Response" , output);
	
	return response;
}

public User getUser(String username) {
	// TODO Auto-generated method stub
	return securityDao.getUser(username);
}



public UserDetails loadUserByUsername(String username)
		throws UsernameNotFoundException {
	User user = getUser(username);
	 if (user != null) {
		 return user;
	 } else {
		 throw new UsernameNotFoundException("User not found.");
	 }
}

public LoginUsersRequests handleVacations(AttendanceRequest userRequest, Long empId,LoginUsersRequests withoutSalVac) {
	Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class, new Long(1));
	Boolean withoutSalaryVacEnabled = settings.getWithoutSalaryVacEnabled();
	Boolean periodFromToEnabled = settings.getPeriodFromToEnabled();
	
	Employee emp = (Employee)requestsApprovalDAO.getObject(Employee.class, empId);
	LoginUsers loginUsers=(LoginUsers) requestsApprovalDAO.getObjectByParameter(LoginUsers.class, "empCode", emp);
	
	DateFormat df=new SimpleDateFormat("dd/MM/yyyy");// HH:mm:ss
	Date from = null;
	try {
		from = df.parse(userRequest.getAttendanceTime());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		log.debug(e.getMessage());
	}
	Calendar cal = Calendar.getInstance();
	cal.setTime(from);
	cal.set(Calendar.HOUR_OF_DAY, 0);
	cal.set(Calendar.MINUTE, 0);
	cal.set(Calendar.SECOND, 0);
	MultiCalendarDate mCalDateFrom = new MultiCalendarDate();
	mCalDateFrom.setDate(cal.getTime());
	
	Date to = null;
	try {
		to = df.parse(userRequest.getAttendanceTime2());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		log.debug(e.getMessage());
	}
	cal.setTime(to);
	cal.set(Calendar.HOUR_OF_DAY, 23);
	cal.set(Calendar.MINUTE, 59);
	cal.set(Calendar.SECOND, 59);
	MultiCalendarDate mCalDateTo = new MultiCalendarDate();
	mCalDateTo.setDate(cal.getTime());
	
	Long diff = mCalDateTo.getDate().getTime()-mCalDateFrom.getDate().getTime();
	log.debug("difference in vacation days " + diff);
	if (diff < 0) {
		return null;
	}
	Double daysDiff = Double.valueOf(Math.round((diff/(1000*60*60*24.0))));
	log.debug("daysDiff " + daysDiff);
	log.debug(withoutSalVac);
	withoutSalVac.setWithdrawDays(daysDiff);
	log.debug("emp " + emp);
	
	Long vacCredit = requestsApprovalManager.getVacationCredit(emp.getEmpCode(), userRequest.getAttendanceType(), userRequest.getVacation(), mCalDateFrom.getDate());
	log.debug("vaccredit " + vacCredit);
	if (vacCredit!=null) {
		withoutSalVac.setVacCredit(vacCredit);
	}
	withoutSalVac.setEmpCode(emp.getEmpCode());
	log.debug("login user " + loginUsers);
	withoutSalVac.setLogin_user(loginUsers);
	withoutSalVac.setRequest_date(Calendar.getInstance().getTime());
	log.debug("userRequest notes " + userRequest.getNotes());
	withoutSalVac.setApproved(new Long(0));
	withoutSalVac.setApplicable(new Long(1));
	withoutSalVac.setPosted(new Long(0));
	withoutSalVac.setReply("--");
	
	RequestTypes request_id = null;
	
	Vacation vacation = null;

	log.debug("vacation parameter " + userRequest.getVacation());
	if (userRequest.getVacation()!= null && !userRequest.getVacation().isEmpty()){
		vacation = (Vacation)requestsApprovalManager.getObject(Vacation.class, userRequest.getVacation());
	}
	log.debug("vacation " + vacation);
	request_id= (RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, userRequest.getAttendanceType());
	
	log.debug("handlevacations request " + request_id.getId());
	if(withoutSalaryVacEnabled==true){//Lotus
		if(userRequest.getAttendanceType().equals(new Long(1)) && userRequest.getVacation().equals("008")){
			//tasree7 object
			log.debug("---without Salary vacation ---");
//			log.debug("---------loginUsersRequests.getPeriod_from()-------"+loginUsersRequests.getPeriod_from());
			
			request_id= (RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, new Long (3));
			log.debug("1.request " + request_id.getId());
			
			withoutSalVac.setRequest_id(request_id);
			
		} else if(userRequest.getAttendanceType().equals(new Long(1))) {
			request_id= (RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, new Long (1));
			log.debug("2.request " + request_id.getId());
			withoutSalVac.setVacation(vacation);
		}
	}  else if(userRequest.getAttendanceType().equals(new Long(2))) {
		request_id= (RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, new Long (2));
		log.debug("request " + request_id.getId());
		withoutSalVac.setVacation(vacation);
	}
	else {
		withoutSalVac.setVacation(vacation);
	}
	log.debug("request " + request_id.getId());
	withoutSalVac.setRequest_id(request_id);
	
	log.debug("mCalDateFrom.getDate() " + mCalDateFrom.getDate() + " - mCalDateTo.getDate() " + mCalDateTo.getDate());
//	if(periodFromToEnabled==true){//Lehaa
			withoutSalVac.setFrom_date(mCalDateFrom.getDate());
			withoutSalVac.setTo_date(mCalDateTo.getDate());
			withoutSalVac.setPeriod_from(mCalDateFrom.getDate());
			withoutSalVac.setPeriod_to(mCalDateTo.getDate());
//	}else {//Lotus
//			withoutSalVac.setFrom_date(mCalDateFrom.getDate());
//			withoutSalVac.setTo_date(mCalDateTo.getDate());
//	}
	
	String requestNumber="";
	requestNumber=requestsApprovalManager.CreateRequestNumber();
	withoutSalVac.setRequestNumber(requestNumber);
	log.debug("requestNumber in handle vacation " + requestNumber);
	
//	requestsApprovalManager.saveObject(withoutSalVac);
	return withoutSalVac;
	

}

public Map userRequest(AttendanceRequest userRequest,Long empId) {
	// TODO Auto-generated method stub
	Map response = new HashMap();
	RestStatus status = new RestStatus();
	
	 Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class, new Long(1));
	 boolean automaticRequestsValidation = settings.getAutomaticRequestsValidation();
	 
	if (userRequest.getAttendanceType()==null || userRequest.getAttendanceTime() == null 
			||(userRequest.getAttendanceType().equals(new Long(1)) && (userRequest.getAttendanceTime2().isEmpty() ||userRequest.getAttendanceTime2() == null))
			||(userRequest.getAttendanceType().equals(new Long(2)) && (userRequest.getAttendanceTime2().isEmpty() ||userRequest.getAttendanceTime2() == null))
			||(userRequest.getAttendanceType().equals(new Long(3)) && (userRequest.getAttendanceTime2().isEmpty() ||userRequest.getAttendanceTime2() == null))
			||(userRequest.getAttendanceType().equals(new Long(4)) && (userRequest.getAttendanceTime2().isEmpty() ||userRequest.getAttendanceTime2() == null))
			|| userRequest.getAttendanceTime().isEmpty()|| userRequest.getLatitude()==null || userRequest.getLongitude()==null){
		status.setCode("303");
		status.setMessage("Null/Empty Input Parameter");
		status.setStatus("False");
		response.put("Status", status);
		return response;
	}
	
	DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	DateFormat df2=new SimpleDateFormat("dd/MM/yyyy");
	Date newDate = null;
	try {
		if (!userRequest.getAttendanceType().equals(new Long(4)) && !userRequest.getAttendanceType().equals(new Long(1)) && !userRequest.getAttendanceType().equals(new Long(2))) {
			newDate = df.parse(userRequest.getAttendanceTime());
		} else {
			newDate = df2.parse(userRequest.getAttendanceTime());
		}
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		log.debug(e.getMessage());
		status.setCode("312");
		status.setMessage("Date is not well formated");
		status.setStatus("False");
		response.put("Status", status);
		return response;
	}
	MultiCalendarDate mCalDate = new MultiCalendarDate();
	mCalDate.setDate(newDate);
	MultiCalendarDate mCalDateTo = new MultiCalendarDate();
	
	MultiCalendarDate mCalDateToPermission = new MultiCalendarDate();
	Date to = null;
	Calendar cal = Calendar.getInstance();
	try {
		if (userRequest.getAttendanceTime2()!=null && !userRequest.getAttendanceTime2().isEmpty()) {
			if (!userRequest.getAttendanceType().equals(new Long(1)) && !userRequest.getAttendanceType().equals(new Long(2)) && !userRequest.getAttendanceType().equals(new Long(4))) {
				to = df.parse(userRequest.getAttendanceTime2());
				log.debug("to " + to);
			} else {
				to = df2.parse(userRequest.getAttendanceTime2());
				log.debug("to " + to);
			}
			
			cal.setTime(to);
			
			mCalDateToPermission.setDate(cal.getTime());
			
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);

			mCalDateTo.setDate(cal.getTime());
		}
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		log.debug(e.getMessage());
		status.setCode("312");
		status.setMessage("Date is not well formated");
		status.setStatus("False");
		response.put("Status", status);
		return response;
	}  catch (Exception e) {
		// TODO Auto-generated catch block
		log.debug(e.getMessage());
		status.setCode("312");
		status.setMessage("Date Exception");
		status.setStatus("False");
		response.put("Status", status);
		return response;
	}
	
	
	
	Employee emp = (Employee)requestsApprovalDAO.getObject(Employee.class, empId);

	LoginUsers loginUsers=(LoginUsers) requestsApprovalDAO.getObjectByParameter(LoginUsers.class, "empCode", emp);
	if(loginUsers!=null){
		log.debug("-----login.code----"+loginUsers.getEmpCode());
	}
	LoginUsersRequests loginUsersRequests = null;
	Vacation vac = null;
	//1 agaza khasa 2 agaza dawreya 3 tasree7 
	//4 ma2moreya yom kamel
	//5 errand
	//7:Special Vacation-e3teyadi(1-001) Special Vacation-3arda(1-002) Special Vacation-marady(1-003) Special Vacation-ra7et wardeya(1-888) 
		//8:Periodic Vacation(2)
	
		RequestTypes reqType = null;
		log.debug("test userRequest.getAttendanceType() " + userRequest.getAttendanceType());
		if (userRequest.getAttendanceType().equals(new Long(1))) { //special vacation
//			log.debug("1 reqType " + reqType);
			loginUsersRequests = new LoginUsersRequests();
			loginUsersRequests = handleVacations(userRequest, empId,loginUsersRequests);
			if (loginUsersRequests != null) {
				reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, userRequest.getAttendanceType());
				log.debug(" reqType " + reqType.getId());
			} else {
				status.setCode("313");
				status.setMessage("To date is before from date.");
				status.setStatus("False");
				response.put("Status", status);
				return response;
			}
		} else if (userRequest.getAttendanceType().equals(new Long(2))) {//periodic vacation
//			log.debug("2 reqType " + reqType.getId());
			loginUsersRequests = new LoginUsersRequests();
			loginUsersRequests = handleVacations(userRequest, empId,loginUsersRequests);
			log.debug("approved " + loginUsersRequests.getApproved());
			log.debug("vacation " + loginUsersRequests.getVacation());
			if (loginUsersRequests != null) {
				reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, userRequest.getAttendanceType());
				log.debug(" reqType " + reqType.getId());
			} else {
				status.setCode("313");
				status.setMessage("To date is before from date.");
				status.setStatus("False");
				response.put("Status", status);
				return response;
			}
		}  else if (userRequest.getAttendanceType().equals(new Long(3))) {//Permission	// || userRequest.getAttendanceType().equals(new Long(4))
//			log.debug("3 4 reqType " + reqType.getId());
			reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(3));
			log.debug(" reqType " + reqType.getId());
			
			log.debug("validating");
			if (userRequest.getPermissionEffect() == null) {
				log.debug("validating2");
				status.setCode("305");
				status.setMessage("One or more of Permission Parameters is null");
				status.setStatus("False");
				response.put("Status", status);
				return response;
			}
		} else if (userRequest.getAttendanceType().equals(new Long(4))) {//errand
//			log.debug("5 6 reqType " + reqType.getId());
			reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(4));
			log.debug("request type " + reqType);
			log.debug(" reqType " + reqType.getId());
		}else if (userRequest.getAttendanceType().equals(new Long(5)) ||userRequest.getAttendanceType().equals(new Long(6))) {//errand
//			log.debug("5 6 reqType " + reqType.getId());
			reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(5));
			log.debug("request type " + reqType);
			log.debug(" reqType " + reqType.getId());
		} else {
			log.debug("userRequest.getAttendanceType() " + userRequest.getAttendanceType().getClass());
		}
		
		
		log.debug("mCalDate.getDate() " + mCalDate.getDate());
		if (userRequest.getAttendanceType().equals(new Long(6))
				||userRequest.getAttendanceType().equals(new Long(3)) || userRequest.getAttendanceType().equals(new Long(5))) {//Permission End || //Full Day Permission End
			
			
			if (userRequest.getAttendanceType().equals(new Long(6))) {//userRequest.getAttendanceType().equals(new Long(4)) ||permission start and end depricated with only one request
				RequestsApprovalQuery requestQueryending = new RequestsApprovalQuery();
				
				
				if (userRequest.getAttendanceType().equals(new Long(6))) {//errand 
					requestQueryending.setRequestType("2");
				} else {
					log.debug("condition not handled 3 " + userRequest.getAttendanceType());
				}
				requestQueryending.setDateFrom(userRequest.getAttendanceTime());
				requestQueryending.setDateTo(userRequest.getAttendanceTime());
				List requests = (List)(requestsApprovalManager.checkStartedRequests(requestQueryending, emp)).get("Response");
				Iterator itrrequests = requests.iterator();
				while(itrrequests.hasNext()) {
					log.debug("####Strarted requests "+((LoginUsersRequests)itrrequests.next()).getRequestNumber());
				}
				if (requests.size() == 1) {
					loginUsersRequests = (LoginUsersRequests)requests.get(0);
					if (loginUsersRequests.getPeriod_to()==null){
						loginUsersRequests.setPeriod_to(mCalDate.getDate());
					} else {
						status.setCode("302");
						status.setMessage("The Request started on the specified date has already been ended.");
						status.setStatus("False");
						response.put("Status", status);
						return response;
					}
				} else if (requests.size() == 0){
					status.setCode("300");
					status.setMessage("No Requests Started on the Specified Date");
					status.setStatus("False");
					response.put("Status", status);
					return response;
				} else if (requests.size() > 1){
					status.setCode("301");
					status.setMessage("Too Many Requests Started on the Specified Date");
					status.setStatus("False");
					response.put("Status", status);
					return response;
				} else {
					loginUsersRequests = new LoginUsersRequests();
					loginUsersRequests.setLogin_user(loginUsers);
					loginUsersRequests.setEmpCode(loginUsers.getEmpCode().getEmpCode());
					

					// request number
					if (loginUsersRequests.getId() == null){
						String requestNumber="";
						requestNumber=requestsApprovalManager.CreateRequestNumber();
						loginUsersRequests.setRequestNumber(requestNumber);
						log.debug("requestNumber in user request " + requestNumber);
						cal.setTime(newDate);
						cal.set(Calendar.HOUR_OF_DAY, 0);
						cal.set(Calendar.MINUTE, 0);
						cal.set(Calendar.SECOND, 0);
						mCalDate.setDate(cal.getTime());
						loginUsersRequests.setPeriod_from(mCalDate.getDate());
						loginUsersRequests.setPeriod_to(mCalDateTo.getDate());

						loginUsersRequests.setLeave_type("0");
						loginUsersRequests.setLeave_effect("0");
						loginUsersRequests.setPayed(new Long(1));
					}
				}
			} else {//starting permissions and errands
				RequestsApprovalQuery requestQuery = new RequestsApprovalQuery();
				log.debug("#$#$starting permissions or errands");
//				//1 permission 2 errands
				if (userRequest.getAttendanceType().equals(new Long(3))) {//permission start
					requestQuery.setRequestType("1");
				} else if (userRequest.getAttendanceType().equals(new Long(5))) {//errand start
					requestQuery.setRequestType("2");
				} else {
					log.debug("condition not handled 2 " + userRequest.getAttendanceType());
				}
				
//				requestQuery.setDateFrom(userRequest.getAttendanceTime());
				
				requestQuery.setDateTo(userRequest.getAttendanceTime());
				requestQuery.setEmp_code(emp.getEmpCode());
				
				
				Map startedRequests = checkStartedRequests(requestQuery, emp);
				List startedRequestsResponse = (List)startedRequests.get("Response");
				log.debug("#$#$starting permissions or errands ---- checked started requests ---" + startedRequestsResponse.size());
				///////////////////////end requests automatically////////////////////////////////////////////////
//				Settings settings = (Settings)requestsApprovalManager.getObject(Settings.class, new Long(1));
//				List results = (List)response.get("Response");
				Iterator itr = startedRequestsResponse.iterator();
				
				log.debug("checkStartedRequests: automatic errands end " + settings.getAutomaticErrandEnd());
				Calendar dayBeforeEndDate = Calendar.getInstance();
				dayBeforeEndDate.setTime(mCalDate.getDate());
				dayBeforeEndDate.set(Calendar.HOUR, 0);
				dayBeforeEndDate.set(Calendar.MINUTE, 0);
				dayBeforeEndDate.set(Calendar.SECOND, 0);
				dayBeforeEndDate.set(Calendar.MILLISECOND, 0);
				if (settings.getAutomaticErrandEnd() != null && !settings.getAutomaticErrandEnd().isEmpty()) {
					while (itr.hasNext()) {
						LoginUsersRequests req = (LoginUsersRequests)itr.next();
						log.debug("checkStartedRequests: period to " + req.getPeriod_to());
						log.debug("req.getRequest_id().equals(loginUsersRequests.getRequest_id() " + req.getRequest_id().equals(reqType));
						if (!req.getRequest_id().getId().equals(new Long(10)) && !req.getRequest_id().getId().equals(new Long(11)) &&req.getPeriod_from().before(dayBeforeEndDate.getTime()) && req.getPeriod_to() == null && req.getRequest_id().equals(reqType)) {
							String requestEndTime = settings.getAutomaticErrandEnd();
							String [] time =  requestEndTime.split(":");
							log.debug("Time hour " + time[0] + " minutes " + time[1]);
							if (req.getPeriod_from() != null) {
								log.debug("request no " + req.getRequestNumber() + " started on " + req.getPeriod_from());
								Calendar cal2 = Calendar.getInstance();
								cal2.setTime(req.getPeriod_from());
								cal2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
								cal2.set(Calendar.MINUTE, Integer.parseInt(time[1]));
								cal2.set(Calendar.SECOND, 0);
								log.debug("will end request on " + cal2.getTime());
								req.setPeriod_to(cal2.getTime());
								req.setTo_date(cal2.getTime());
								req.setNotes(req.getNotes() + " (Request ended automatically by the system)");
								requestsApprovalManager.saveObject(req);
							}
						} else if ( !req.getRequest_id().getId().equals(new Long(10)) && !req.getRequest_id().getId().equals(new Long(11)) && req.getPeriod_to() == null) {
							log.debug("request opened in the last 24hrs is not ended");
						} else if (req.getRequest_id().getId().equals(new Long(10)) && settings.getSignoutBeforePermissionErrand().equals(new Boolean(true))){
							log.debug("sign in request opened in the last 24hrs is not ended");
							status.setCode("331");
							status.setMessage("A sign in request is already started (#" + ((LoginUsersRequests)startedRequestsResponse.get(0)).getRequestNumber() + ") that hasn't been ended yet.");
							status.setStatus("False");
							response.put("Status", status);
							return response;
						}
					}
					startedRequests = requestsApprovalManager.checkStartedRequests(requestQuery,emp);
					startedRequestsResponse = (List)startedRequests.get("Response");
				}
				////////////////////////////////////////////////////////////////////////////////////////////////////
				log.debug("permission/errand start , size =" + startedRequestsResponse.size());
				if (startedRequestsResponse.size() == 1 && ((LoginUsersRequests)startedRequestsResponse.get(0)).getTo_date()==null) {
					status.setCode("311");
					status.setMessage("A request is already started (#" + ((LoginUsersRequests)startedRequestsResponse.get(0)).getRequestNumber() + ") that hasn't been ended yet.");
					status.setStatus("False");
					response.put("Status", status);
					return response;
				} else if (startedRequestsResponse.size() > 1){
					status.setCode("301");
					status.setMessage("Too Many Requests Already Started");
					status.setStatus("False");
					response.put("Status", status);
					return response;
				} else if (startedRequestsResponse.size() > 0) {
					status.setCode("315");
					status.setMessage("Another Request is already Started for the logged in user");
					status.setStatus("False");
					response.put("Status", status);
					return response;
				}else {
					loginUsersRequests = new LoginUsersRequests();
					loginUsersRequests.setLogin_user(loginUsers);
					loginUsersRequests.setEmpCode(loginUsers.getEmpCode().getEmpCode());
					

					// request number
					if (loginUsersRequests.getId() == null && loginUsersRequests.getRequestNumber() == null){
						String requestNumber="";
						requestNumber=requestsApprovalManager.CreateRequestNumber();
						loginUsersRequests.setRequestNumber(requestNumber);
						loginUsersRequests.setPeriod_from(mCalDate.getDate());
						if (userRequest.getAttendanceType().equals(new Long(3)) || userRequest.getAttendanceType().equals(new Long(1)) || userRequest.getAttendanceType().equals(new Long(2))) {
							loginUsersRequests.setPeriod_to(mCalDateToPermission.getDate());
						}
					}
				}
			}
		}else if (userRequest.getAttendanceType().equals(new Long(4))) {//full day errand
			loginUsersRequests = new LoginUsersRequests();
			loginUsersRequests.setLogin_user(loginUsers);
			loginUsersRequests.setEmpCode(loginUsers.getEmpCode().getEmpCode());
			

			// request number
			if (loginUsersRequests.getId() == null && loginUsersRequests.getRequestNumber() == null){
				String requestNumber="";
				requestNumber=requestsApprovalManager.CreateRequestNumber();
				loginUsersRequests.setRequestNumber(requestNumber);
				loginUsersRequests.setPeriod_from(mCalDate.getDate());
				
				loginUsersRequests.setPeriod_to(mCalDateTo.getDate());
				loginUsersRequests.setTo_date(mCalDateTo.getDate());
			}
			loginUsersRequests.setLeave_effect("0");
			loginUsersRequests.setLeave_type("0");
			loginUsersRequests.setPayed(new Long(1));
			loginUsersRequests.setApplicable(new Long(1));
			loginUsersRequests.setApproved(new Long(0));
			Long diff = mCalDateTo.getDate().getTime()-mCalDate.getDate().getTime();
			
			if (diff < 0) {
				return null;
			}
			Double daysDiff = Double.valueOf(Math.round((diff/(1000*60*60*24.0))));
			log.debug("daysDiff " + daysDiff);
			loginUsersRequests.setWithdrawDays(daysDiff);
		} else if (!userRequest.getAttendanceType().equals(new Long(1))&& !userRequest.getAttendanceType().equals(new Long(2))){
			loginUsersRequests = new LoginUsersRequests();
			loginUsersRequests.setLogin_user(loginUsers);
			loginUsersRequests.setEmpCode(loginUsers.getEmpCode().getEmpCode());
			

			// request number
			if (loginUsersRequests.getId() == null){
				String requestNumber="";
				requestNumber=requestsApprovalManager.CreateRequestNumber();
				loginUsersRequests.setRequestNumber(requestNumber);
				loginUsersRequests.setPeriod_from(mCalDate.getDate());
			}
		} else {
			log.debug("condition not handled " + userRequest.getAttendanceType());
		}

		if (vac!=null) {
			loginUsersRequests.setVacation(vac);
		}
//		if (!userRequest.getAttendanceType().equals(new Long(7))){
//			loginUsersRequests.setRequest_date(mCalDate.getDate());
//		}

		loginUsersRequests.setRequest_date(Calendar.getInstance().getTime());
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		loginUsersRequests.setInputType(new Integer(2));//android request
		
		////////////////////////////////////////////////////////////////////////////
//		Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class, new Long(1));
		
		Double distance = requestsApprovalManager.distance(new Double(userRequest.getLatitude()), new Double(userRequest.getLongitude()), new Double(settings.getCompanyLat()), new Double(settings.getCompanyLong()));
		if (distance>settings.getDistAllowedFromCompany()) {
			loginUsersRequests.setIsInsideCompany(false);
		} else {
			loginUsersRequests.setIsInsideCompany(true);
		}
		//////////////////////////////////////////////////////////////////////////
		try{
			String address = requestsApprovalManager.getAddressByGpsCoordinates(userRequest.getLongitude()+"", userRequest.getLatitude()+"");
			loginUsersRequests.setLocationAddress(address);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		////////////////////////////////////////////////////////////////////////////
		
		
		log.debug("after validation");
		if (automaticRequestsValidation==true) {
			if (userRequest.getAttendanceType().equals(new Long(4))) {
				///////////////////////////Full day errand validations///////////////////////////////////////////////
				Map att = checkAttendance(mCalDate.getDate(), emp.getEmpCode());
				AttendanceStatus attendanceResponse = (AttendanceStatus)att.get("Response");
				log.debug("attendance status response " + attendanceResponse);
				RequestsApprovalQuery requestQuery = new RequestsApprovalQuery();
				requestQuery.setDateFrom(userRequest.getAttendanceTime());
				requestQuery.setDateTo(userRequest.getAttendanceTime2());
				log.debug("attendanceResponse.getSignIn() " + attendanceResponse.getSignIn());
				Map checkStartedMap = checkStartedRequests(requestQuery, emp);
				log.debug("after checking started requests " + checkStartedMap);
				List startedRequests = (List)checkStartedMap.get("Response");
				log.debug("after checking started requests 2" + startedRequests);

				if (attendanceResponse!=null && attendanceResponse.getSignIn()!=null && attendanceResponse.getSignIn().equals(new Boolean(true))) {
					// check attendance on this day//

					log.debug("attendance status response " + attendanceResponse.getSignIn());

					status.setCode("322");
					status.setMessage("User Signed In Already on the specified date, full day errand is not allowed.");
					status.setStatus("False");
					response.put("Status", status);
					return response;

					////////////////////////////////
				} else if (startedRequests != null && startedRequests.size() > 0) {
					status.setCode("322");
					status.setMessage("Another request (" + ((LoginUsersRequests)startedRequests.get(0)).getRequestNumber() + ") is made already on the specified date, full day errand is not allowed.");
					status.setStatus("False");
					response.put("Status", status);
					return response;
				} 
				////////////////////////////////////////////////////////////////////////////////////////////////////
			} else {
				//if not full day errand check requests overlapping/////////////////
				////////////////////////////////////1///////////////////////////////
				//Signing in ///////////////////////////////////////////////////////
				////////////////////////////////////////////////////////////////////
				//					if(loginUsersRequests.getRequest_id().getId().equals(new Long(10))) {
				Calendar temp = Calendar.getInstance();
				log.debug("validating non full errand requests 1 " + loginUsersRequests);
				if (loginUsersRequests == null) {
					temp.setTime(mCalDate.getDate());
				} else if (loginUsersRequests.getPeriod_from() != null) {
					temp.setTime(loginUsersRequests.getPeriod_from());
				} else {
					temp.setTime(loginUsersRequests.getFrom_date());
				}
				

				cal = Calendar.getInstance();
				cal.setTime(loginUsersRequests.getPeriod_from());
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				Date from = cal.getTime();
				cal.set(Calendar.HOUR, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				Date t = cal.getTime();
				log.debug("validating non full errand requests 2");
				log.debug("loginUsersRequests.getPeriod_from() " + loginUsersRequests.getPeriod_from());
				log.debug("loginUsersRequests.getPeriod_to() " + loginUsersRequests.getPeriod_to());
				log.debug("from " + from + " to " + t);
//				List requests = requestsApprovalManager.getRequestsByExactDatePeriodAndEmpCode(from, t, loginUsersRequests.getEmpCode());
				
				RequestsApprovalQuery requestQuery = new RequestsApprovalQuery();
				
				requestQuery.setDateFrom(df.format(from));
				
				requestQuery.setDateTo(df.format(t));
				Map reqs = checkStartedRequestsIncludingAttendance(requestQuery, emp);
				List requests = (List)reqs.get("Response");
				
				
				Map  attendance = requestsApprovalManager.checkAttendance(loginUsersRequests.getPeriod_from(), loginUsers.getEmpCode().getEmpCode());
				log.debug("Bug check#@#@validating non full errand requests " + requests.size());
				
//				Iterator itr = requests.iterator();
				
				int i=0;
				if (requests.size() >0) {
					Iterator reqItr = requests.iterator();
					while (reqItr.hasNext()) {
						LoginUsersRequests req = (LoginUsersRequests)reqItr.next();
						log.debug(i+". request number " + req.getRequestNumber());
						log.debug("req.getPeriod_to() " + req.getPeriod_to());
						if (req.getPeriod_to() == null) {
							log.debug("checking if overlapping requests are sign in/out " + req.getRequest_id().getId());
							if (req.getRequest_id().getId().equals(new Long(10)) || req.getRequest_id().getId().equals(new Long(11))) {
								AttendanceStatus attendanceStatus = (AttendanceStatus)attendance.get("Response");
								
								log.debug("attendanceStatus.getSignIn() " + attendanceStatus.getSignIn());
								log.debug("attendanceStatus.getSignOut() " + attendanceStatus.getSignOut());
								
								if (attendanceStatus.getSignIn().booleanValue() == true && attendanceStatus.getSignOut().booleanValue() == false  && settings.getSignoutBeforePermissionErrand().equals(new Boolean(true))) {
									log.debug("request in same interval " + req.getRequestNumber() + req.getPeriod_from());
									status.setCode("323");
									status.setMessage("Please sign out first before starting new request");
									status.setStatus("False");
									response.put("Status", status);
									return response;
								} else if (attendanceStatus.getSignIn().booleanValue() == true && attendanceStatus.getSignOut().booleanValue() == true) {
									Calendar signInCal = Calendar.getInstance();
									signInCal.setTimeInMillis(attendanceStatus.getSignInTime());
									Date signIn = signInCal.getTime();
											
									Calendar signOutCal = Calendar.getInstance();
									signOutCal.setTimeInMillis(attendanceStatus.getSignOutTime());
									Date signOut = signOutCal.getTime();
											
									if (loginUsersRequests.getPeriod_from().after(signIn) && loginUsersRequests.getPeriod_from().before(signOut)) {
										log.debug("request in same interval " + req.getRequestNumber() + req.getPeriod_from());
										status.setCode("324");
										status.setMessage("Sign out time is after the request start time");
										status.setStatus("False");
										response.put("Status", status);
										return response;
									}
								}
							} else {
								log.debug("request in same interval " + req.getRequestNumber() + req.getFrom_date());
								status.setCode("328");
								status.setMessage("Please finish your started requests ("+ req.getRequestNumber()+") during the same time interval specified");
								status.setStatus("False");
								response.put("Status", status);
								return response;
							}
						} else {
							if (req.getPeriod_to().compareTo(loginUsersRequests.getPeriod_from()) >= 0 && req.getPeriod_from().compareTo(loginUsersRequests.getPeriod_from())<=0) {
								if (!userRequest.getAttendanceType().equals(new Long(6)) && !userRequest.getAttendanceType().equals(new Long(4))) {
									status.setCode("321");
									status.setMessage("The specified time interval is overlapping with one of your requests with request number "  + req.getRequestNumber());
									status.setStatus("False");
									response.put("Status", status);
									return response;
								}
							}
						}
						i++;
					}
				}
			}
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////


		if(settings.getVacationRequestExcep()==false){
			if(loginUsersRequests!=null && !loginUsersRequests.equals("")){
				if(loginUsersRequests.getId()!=null && !loginUsersRequests.getId().equals("")){
					log.debug("----loginUsersRequests.getRequest_id().getId()---------"+loginUsersRequests.getRequest_id().getId());
					RequestTypes specVac= (RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, new Long(1));
					if(loginUsersRequests.getRequest_id().getId()==new Long(999)){
						Vacation errand=(Vacation) requestsApprovalManager.getObject(Vacation.class, "999");
						loginUsersRequests.setRequest_id(specVac);
						loginUsersRequests.setVacation(errand);
					}
				}
			}
		}


		if (!userRequest.getAttendanceType().equals(new Long(7)) && !userRequest.getAttendanceType().equals(new Long(8))) {
			log.debug("service implmentation ma2moreya " + loginUsersRequests);
			log.debug("service implmentation ma2moreya " + reqType);
			loginUsersRequests.setRequest_id(reqType);
		}
	//Setting Longitude & Latitude////////////////////////////////
	loginUsersRequests.setLatitude(userRequest.getLatitude());
	loginUsersRequests.setLongitude(userRequest.getLongitude());
	/////////////////////////////////////////////////////////////
//	if(userRequest.getPermissionType()!=null){
//		loginUsersRequests.setLeave_type(userRequest.getPermissionType());
//	}
	
	if(userRequest.getPermissionEffect()!=null){
		loginUsersRequests.setLeave_effect(userRequest.getPermissionEffect());
	}
	
	if (loginUsersRequests.getNotes()!=null) {
		loginUsersRequests.setNotes(loginUsersRequests.getNotes().concat(" "+userRequest.getNotes()));
	} else {
		loginUsersRequests.setNotes(userRequest.getNotes());
	}
	
	loginUsersRequests.setRequest_date(Calendar.getInstance().getTime());
	
	if(loginUsersRequests.getPeriod_from()!=null) {
		loginUsersRequests.setFrom_date(loginUsersRequests.getPeriod_from());
	}
	if(loginUsersRequests.getPeriod_to()!=null) {
		loginUsersRequests.setTo_date(loginUsersRequests.getPeriod_to());
	}
	
	log.debug("*****");
	
	///////////////////////////////////////////////////////
	loginUsersRequests.setApproved(new Long(0));
	loginUsersRequests.setPosted(new Long(0));
	loginUsersRequests.setApplicable(new Long(0));

	log.debug("*****");
	/////////////////////////////////////////////////////
	if (loginUsersRequests.getVacation()!=null) {
		log.debug("vacation " + loginUsersRequests.getVacation().getId());
	}
	log.debug("to date " + loginUsersRequests.getTo_date());
	requestsApprovalManager.saveObject(loginUsersRequests);
	Map output = new HashMap();
	output.put("request_number", loginUsersRequests.getRequestNumber());
	response.put("Response", output);

	log.debug("*****");
	status.setCode("200");
	status.setMessage("Request Inserted Successfully");
	status.setStatus("true");
	response.put("Status", status);
	return response ;

}

public Map checkAttendance(Date today, String empCode) {
	Map response = requestsApprovalManager.checkAttendance(today,empCode);
	return response;
}

public Map checkStartedRequests(RequestsApprovalQuery requestQuery,
		Employee emp) {
	LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
	log.debug("checkStartedRequests: logged in user " + loggedInUser);
	Map response = requestsApprovalManager.checkStartedRequests(requestQuery,emp);
	log.debug("checkStartedRequests: response " + response);
	return response;
}

public Map checkStartedRequestsIncludingAttendance(RequestsApprovalQuery requestQuery,
		Employee emp) {
	LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
	log.debug("checkStartedRequests: logged in user " + loggedInUser);
	Map response = requestsApprovalManager.checkStartedRequestsIncludingAttendance(requestQuery, emp);
	log.debug("checkStartedRequests: response " + response);
	return response;
}

public List getEmpReqTypeAcc(Employee emp,String requestType) {
	return requestsApprovalManager.getEmpReqTypeAcc(emp, requestType);
}

public Map getRequestsForApproval(RequestsApprovalQuery approvalQuery, List empReqTypeAccs,Employee emp) {
	LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
	return requestsApprovalManager.getRequestsForApproval(approvalQuery.getRequestNumber(), approvalQuery.getEmp_code(), 
			approvalQuery.getDateFrom(), approvalQuery.getDateTo(), approvalQuery.getExactDateFrom(), approvalQuery.getExactDateTo(), approvalQuery.getRequestType(),
			approvalQuery.getCodeFrom(), approvalQuery.getCodeTo(), approvalQuery.getStatusId(),approvalQuery.getSort(),loggedInUser,empReqTypeAccs,false,approvalQuery.getIsInsideCompany(), approvalQuery.getPageNumber(), approvalQuery.getPageSize());	
}

public Map getSubmittedRequestsForApproval(RequestsApprovalQuery approvalQuery, List empReqTypeAccs,Employee emp) {
	LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
	return requestsApprovalManager.getSubmittedRequestsForApproval(approvalQuery.getRequestNumber(), approvalQuery.getEmp_code(), 
			approvalQuery.getDateFrom(), approvalQuery.getDateTo(), approvalQuery.getExactDateFrom(), approvalQuery.getExactDateTo(), approvalQuery.getRequestType(),
			approvalQuery.getCodeFrom(), approvalQuery.getCodeTo(), approvalQuery.getStatusId(),approvalQuery.getSort(),loggedInUser,empReqTypeAccs,false,approvalQuery.getIsInsideCompany(), approvalQuery.getPageNumber(), approvalQuery.getPageSize());	
}

public Map approveRequest(RequestApproval requestApproval,Employee emp) {
	// TODO Auto-generated method stub
	RestStatus status = new RestStatus();
	Object obj = requestsApprovalManager.getObject(LoginUsersRequests.class, new Long(requestApproval.getRequestId()));
	Map response = new HashMap();
	if (obj == null) {
		status.setCode("306");
		status.setMessage("Request Not Found");
		status.setStatus("false");
		response.put("Status", status);
		return response;
	} else {
		LoginUsersRequests loginUser = (LoginUsersRequests)obj;
		if (requestApproval.getApprove().equals("0")) {
			log.debug("requestApproval.getModifiedDate() " + requestApproval.getModifiedDate());
			if (requestApproval.getModifiedDate()!=null && !requestApproval.getModifiedDate().isEmpty()) {
				status.setCode("380");
				status.setMessage("You can't modify date while rejecting request");
				status.setStatus("false");
				response.put("Status", status);
				return response;
			} else {
				return requestsApprovalManager.approvalsAccessLevels(requestApproval, loginUser, emp);
			}
		} else if (requestApproval.getApprove().equals("1")) {
			Calendar modifyCal = null;
			Calendar fromCal = Calendar.getInstance();
			fromCal.setTime(loginUser.getFrom_date());
			if (requestApproval.getModifiedDate()!=null && !requestApproval.getModifiedDate().isEmpty()) {
				Date modify = null;
					DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
					try {
						modify = df.parse(requestApproval.getModifiedDate());
						modifyCal = Calendar.getInstance();
						modifyCal.setTime(modify);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						log.debug(e.getMessage());
					}
					
				
			}
			if (requestApproval.getModifiedDate()!=null && !requestApproval.getModifiedDate().isEmpty() 
					&& (modifyCal.get(Calendar.DAY_OF_MONTH)!= fromCal.get(Calendar.DAY_OF_MONTH) || modifyCal.get(Calendar.MONTH)!= fromCal.get(Calendar.MONTH))) {
				status.setCode("381");
				status.setMessage("You can't modify attendance date, you are only allowed to modify time");
				status.setStatus("false");
				response.put("Status", status);
				return response;
			}  else {
				return requestsApprovalManager.approvalsAccessLevels(requestApproval, loginUser, emp);
			}
		} else {
			status.setCode("307");
			status.setMessage("Invalid Request Approval Status");
			status.setStatus("false");
			response.put("Status", status);
			return response;
		}
	}
}

public Map getVacInfo(RequestApproval requestApproval) {
	RestStatus status = new RestStatus();
	Object obj = null;
	Vacation vac = null;
	Map response = new HashMap();
	if (requestApproval.getRequestId() != null) {
		obj = requestsApprovalManager.getObject(LoginUsersRequests.class, new Long(requestApproval.getRequestId()));
	} else if (requestApproval.getVac()!= null && !requestApproval.getVac().isEmpty() 
			&& requestApproval.getEmpCode()!=null && !requestApproval.getEmpCode().isEmpty()){
		vac = (Vacation)requestsApprovalManager.getObjectByParameter(Vacation.class, "vacation", requestApproval.getVac());
	} else {
		status.setCode("303");
		status.setMessage("Null/Empty input parameter");
		status.setStatus("false");
		response.put("Status", status);
		return response;
	}
	
	Date from = null;
	MultiCalendarDate mCalDateFrom = new MultiCalendarDate();
	if (requestApproval.getFromDate() != null && !requestApproval.getFromDate().isEmpty()) {
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		try {
			from = df.parse(requestApproval.getFromDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.debug(e.getMessage());
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(from);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		mCalDateFrom.setDate(cal.getTime());
	}
	
	if (vac!= null && from != null) {
//		status.setCode("306");
//		status.setMessage("Request Not Found");
//		status.setStatus("false");
//		response.put("Status", status);
//		return response;
		return requestsApprovalManager.getVacInfo(vac,from,requestApproval.getEmpCode());
	} else if (obj!=null){
		log.debug("object " + obj);
		LoginUsersRequests loginUser = (LoginUsersRequests)obj;
		Date from_date = loginUser.getFrom_date();
		if (from_date == null) {
			from_date = loginUser.getPeriod_from();
		}
		log.debug("vacation " + loginUser.getVacation());
		log.debug("requestApproval.getEmpCode() " + loginUser.getEmpCode());
		return requestsApprovalManager.getVacInfo(loginUser.getVacation(),from_date, loginUser.getEmpCode());
	} else {
		status.setCode("303");
		status.setMessage("Null/Empty input parameter");
		status.setStatus("false");
		response.put("Status", status);
		return response;
	}
}

public Map getVacTypes(Long requestType) {
	log.debug("vac types");
//	List vacations = requestsApprovalManager.getObjects(Vacation.class);
	Map response = new HashMap();
	List vacations = new ArrayList();
	
	Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class, new Long(1));
	Boolean specialVacExcep = settings.getSpecialVacExcep();
	
	if (requestType.equals(new Long(1))) {//special vacations
		vacations = requestsApprovalManager.getObjectsByParameter(Vacation.class, "type", "B");
		for (int i = 0; i < vacations.size(); i++) {
			Vacation vac= (Vacation) vacations.get(i);
			log.debug("------vacation--before-==="+vac.getVacation());
			if(vac.getVacation().equals("999")){
//				model.put("errand", vac);
				vacations.remove(vac);
			}
			if (specialVacExcep == true){//Lotus
				if(vac.getVacation().equals("008")){
					vacations.remove(vac);
				}
			}
//			log.debug("------vacation- after--==="+vac.getVacation());
		}
		response.put("Response", vacations);
	} else if (requestType.equals(new Long(2))) {//periodic vacations
		vacations=requestsApprovalManager.getObjectsByParameter(Vacation.class,"type","A");
		response.put("Response", vacations);
	}
	
	response.put("Response", vacations);
	log.debug("vac types"+vacations.size());
	RestStatus status = new RestStatus();
	status.setCode("200");
	status.setMessage("Request Inserted Successfully");
	status.setStatus("true");
	response.put("Status", status);
	log.debug("vac types finished");
	return response ;
}

public Map getSettings() {
	Map response = new HashMap();
	Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class, new Long(1));
	log.debug("settings " + settings);
	Map temp = new HashMap();
	temp.put("companyName", settings.getCompany().getDescription());
	temp.put("locationAccuracy",settings.getLocationAccuracy());
	temp.put("locationAccuracy2",settings.getLocationAccuracy2());
	temp.put("isLocationAccuracyEnabled", settings.getIsLocationAccuracyEnabled());
	temp.put("showAddressOnForm", settings.getShowAddressOnForm());
	temp.put("showRequestsOnCalendar", settings.getShowRequestsOnCalendar());
	temp.put("errandTimeFromSystem", settings.getErrandTimeFromSystem());
	temp.put("obligateNotes", settings.getObligateNotes());
	temp.put("showVacationBalance", settings.getAnnualVacBalDaysEnabled());
	temp.put("requiredAndroidVersion", settings.getRequiredAndroidVersion());
	temp.put("managerCanModifyAttendance", settings.getManagerCanModifyAttendance());
	temp.put("isTimesheetEnabled", settings.getIsTimesheetEnabled());
	
	response.put("Response", temp);
	RestStatus status = new RestStatus();
	status.setCode("200");
	status.setMessage("Success");
	status.setStatus("true");
	response.put("Status", status);
	
	
	return response;
}

public Map getPortNo(String clientName) {
	Map response = new HashMap();
	
	Object client = requestsApprovalManager.getObjectByParameter(Clients.class, "clientName", clientName);
	
	if (client == null) {
		RestStatus status = new RestStatus();
		status.setCode("320");
		status.setMessage("Client Name Doesn't Exist");
		status.setStatus("false");
		response.put("Status", status);
		return response;
	} else {
		Clients c = (Clients)client;
		Map temp = new HashMap();
		temp.put("PortNo",c.getPortNo());
		response.put("Response", temp);
		RestStatus status = new RestStatus();
		status.setCode("200");
		status.setMessage("Success");
		status.setStatus("true");
		response.put("Status", status);
	}
	return response;
}

public Map searchEmployees(EmployeeWrapper emp, Employee loggedInEmployee) {
	//table=login_users&firstKey=commons.caption.code&secondKey=commons.caption.name&firstParam=empCode&secondParam=name&inputId=empCode&paramString=&onblur=&onlinkclick=&jsIncludes=
	String level = "";
	
	List empReqTypeAcc = new ArrayList();
	empReqTypeAcc = requestsApprovalManager.getEmpReqTypeAcc(loggedInEmployee, null);
	Iterator itr = empReqTypeAcc.iterator();
	while(itr.hasNext()) {
		Long eCode = (Long)itr.next();
		if(level.isEmpty()) {
			level +=eCode;
		} else {
			level += ","+eCode;
		}
	}
	
	Settings settings = (Settings)requestsApprovalManager.getObject(Settings.class, new Long(1));
	
	Map map = qry.search(emp.getEmpCode(),emp.getEmpName(),"login_users","empCode","name","","1","1",level,settings,emp.getPageNumber(),emp.getPageSize(),"");	
	Map response = new HashMap();
	response.put("Response", map);
	RestStatus status = new RestStatus();
	status.setCode("200");
	status.setMessage("Successfull Request");
	status.setStatus("true");
	response.put("Status", status);
	return response ;
}

public Map getAttendanceVacationReport(RequestsApprovalQuery requestApproval,Settings settings) {
Map response = new HashMap();
	
MultiCalendarDate mCalDate = new MultiCalendarDate();

String dateFrom = requestApproval.getDateFrom();
String dateTo = requestApproval.getDateTo();
String empCode = requestApproval.getEmp_code();

List objects = new ArrayList();
List days = new ArrayList();

Map results = new HashMap();

RestStatus status = new RestStatus();


if (dateFrom != null && dateTo != null && empCode != null){
	if (!dateFrom.equals("") && !dateTo.equals("")  && !empCode.isEmpty()) {
		Date fromDate = null;
		Date toDate = null;
		log.debug(">>>>>>>>>>>>>fromDateString "+ dateFrom);
		mCalDate.setDateTimeString(dateFrom,new Boolean(true));
		fromDate = mCalDate.getDate();
		log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
		log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
		mCalDate.setDateTimeString(dateTo,new Boolean(false));
		toDate= mCalDate.getDate();
		
		// VIP
		List totalObjects= new ArrayList();
		
//		totalObjects=requestsApprovalManager.getTimeAttend(empCode, fromDate, toDate);
		totalObjects=requestsApprovalManager.getTimeAttendAll(empCode, fromDate, toDate,null,settings);
		
		objects=(List) totalObjects.get(0);
		
//		//Lehaa/////////////////////////////////////////////////
//		if (tAttRepWithHrsMin == true) {
			
			String totalSum=(String) totalObjects.get(1);
			String [] totalValues=totalSum.split(",");
			log.debug("totalMins=== "+totalValues[0]+" && totalHrs=== "+totalValues[1]);
			String totalMins=totalValues[0];
			String totalHrs=totalValues[1];
			Long hrs=new Long(0), mins=new Long(0);
			hrs= Long.parseLong(totalHrs);
			mins=Long.parseLong(totalMins);
			if(mins>60){
				hrs+=mins/60;
				mins=mins%60;
			} 
			
			log.debug("sent mins=== "+mins+" && sent hrs=== "+hrs);
			
			response.put("TotalMins", mins);
			response.put("TotalHrs", hrs);
//		} 
		//////////////////////////////////////////////////////////
		
		log.debug("-------objects- size--"+objects.size());
		for (int i = 0; i < objects.size(); i++) {
			TimeAttend ob= (TimeAttend) objects.get(i);
			
			// mCalDate.setDateString(ob.getDay());
			DateFormat df=new SimpleDateFormat("dd/mm/yyyy");
			
			Date day;
			try {
				day = df.parse(ob.getDay());
				log.debug("-------day---"+day);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			log.debug("-------objects---"+ob.getDay()+"-------getTimeIn---"+ob.getTimeIn()+"-------getTimeOut---"+ob.getTimeOut());
		}
		response.put("Attendance", objects);
		// VIP
		
		
		days=requestsApprovalManager.getVacations( empCode, new Long(2), fromDate,toDate,settings);
		log.debug("-----days 001 ---"+days.size());
		response.put("Vacations", days);
		
//		days=requestsApprovalManager.getVacations(emp.getEmpCode(), new Long(2), "002", fromDate,toDate);
//		log.debug("-----days 002 ---"+days.size());
		//		model.put("days2", days);

		results.put("Results", response);
		status.setCode("200");
		status.setMessage("Request Success");
		status.setStatus("True");
		results.put("Status", status);
	} else {
		results.put("Results", response);
		status.setCode("319");
		status.setMessage("Request Failure");
		status.setStatus("False");
		results.put("Status", status);
	}
}  else {
	results.put("Results", response);
	status.setCode("318");
	status.setMessage("Request Failure, (empCode, fromDate, toDate all are mandatory)");
	status.setStatus("False");
	results.put("Status", status);
}

return results;
}

public Map getAttendanceReport(RequestsApprovalQuery requestApproval, Employee emp,Settings settings) {
Map response = new HashMap();
	
MultiCalendarDate mCalDate = new MultiCalendarDate();

String dateFrom = requestApproval.getDateFrom();
String dateTo = requestApproval.getDateTo();
String empCode = requestApproval.getEmp_code();

String codeFrom = requestApproval.getCodeFrom();
String codeTo = requestApproval.getCodeTo();
String statusId = requestApproval.getStatusId();

List objects = new ArrayList();
List days = new ArrayList();

Map results = new HashMap();

RestStatus status = new RestStatus();

List empReqTypeAccs = new ArrayList();

if (dateFrom != null && dateTo != null && !dateFrom.equals("") && !dateTo.equals("")){
		Date fromDate = null;
		Date toDate = null;
		log.debug(">>>>>>>>>>>>>fromDateString "+ dateFrom);
		mCalDate.setDateTimeString(dateFrom,new Boolean(true));
		fromDate = mCalDate.getDate();
		log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
		log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
		mCalDate.setDateTimeString(dateTo,new Boolean(false));
		toDate= mCalDate.getDate();
		
		// VIP
		List totalObjects= new ArrayList();
		log.debug("emp code " + empCode);
		log.debug("codeFrom " + codeFrom + " codeTo " + codeTo);
		if ((empCode== null || empCode.isEmpty()) && (codeFrom == null || codeFrom.isEmpty()) && (codeTo == null || codeTo.isEmpty())) {
//			empReqTypeAccs = requestsApprovalManager.getEmpReqTypeAccEmpCode(emp, null);
			
			LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
//			Object obj = requestsApprovalManager.getObjectByParameter(AccessLevels.class, "emp_id", loggedInUser);
//			
//			AccessLevels lev = null;
//			if (obj!= null) {
//			 lev = (AccessLevels)obj;
//			}
//			List tempLevels = new ArrayList();
//			if(lev!=null) {
////				tempLevels = (List)requestsApprovalDAO.getAccessLevelsBetweenCodes(lev.getLevel_id(),empCode,empCode);
//			}
//			log.debug("access levels size " + tempLevels.size());

			List objectss = requestsApprovalManager.getObjectsByParameter(AccessLevels.class, "emp_id", loggedInUser);
			AccessLevels lev = null;
			log.debug("objectss size " + objectss.size());
//			List levs = new ArrayList();
			List tempLevels = new ArrayList();
			
			Iterator itrs = objectss.iterator();

			while (itrs.hasNext()) {
				Object obj = itrs.next();
				
				if (obj!= null) {
					lev = (AccessLevels)obj;
					log.debug("level " + lev.getLevel_id() + " emp " + lev.getEmp_id().getEmpCode().getEmpCode());
				}
				
				if(lev!=null) {
					tempLevels.addAll(requestsApprovalDAO.getAccessLevelsBetweenCodes(lev.getLevel_id(),codeFrom,codeTo));
				}
				log.debug("access levels size " + tempLevels.size());
			}
			
			String empArray = "";
			
			Iterator empItr = tempLevels.iterator();
			int count = 0;
			while(empItr.hasNext()) {
				EmpReqTypeAcc empReq = ((EmpReqTypeAcc)(empItr.next()));
//				log.debug("empReq " + empReq);
				if (count==0) {
//					empArray = empReq.getEmp_id().getEmpCode();
					empArray =  "'" + empReq.getEmp_id().getEmpCode().getEmpCode() +  "'";
				} else {
//					empArray += "," + empReq.getEmp_id().getEmpCode();
					if (!empArray.contains("'"+empReq.getEmp_id().getEmpCode().getEmpCode()+"'")) {
						empArray += ",'" + empReq.getEmp_id().getEmpCode().getEmpCode() + "'";
					}
				}
				count++;
			}
			log.debug("emp array " + empArray);
//			totalObjects=requestsApprovalManager.getTimeAttend(empArray, fromDate, toDate);
//			totalObjects=requestsApprovalManager.getTimeAttendFromView(empArray, fromDate, toDate);
			if (empArray == null || empArray.isEmpty()) {
				empArray = "'" + emp.getEmpCode() +  "'";
			}
			totalObjects=requestsApprovalManager.getTimeAttendAll(empArray, fromDate, toDate,statusId,settings);
			
		} else if (codeFrom!= null && !codeFrom.isEmpty() && codeTo!= null && !codeTo.isEmpty()) {
			String empArray = "";
			LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
//			List tempLevels = (List)requestsApprovalDAO.getAccessLevelsBetweenCodes(loggedInUser,codeFrom,codeTo);
			
			List objectss = requestsApprovalManager.getObjectsByParameter(AccessLevels.class, "emp_id", loggedInUser);
			AccessLevels lev = null;
			log.debug("objectss size " + objectss.size());
//			List levs = new ArrayList();
			List tempLevels = new ArrayList();
			
			Iterator itrs = objectss.iterator();

			while (itrs.hasNext()) {
				Object obj = itrs.next();
				log.debug("obj " + obj);
				if (obj!= null) {
					lev = (AccessLevels)obj;
				}
				
				if(lev!=null) {
					tempLevels.addAll(requestsApprovalDAO.getAccessLevelsBetweenCodes(lev.getLevel_id(),codeFrom,codeTo));
				}
				log.debug("access levels size " + tempLevels.size());
			}
			Iterator empItr = tempLevels.iterator();
			int count = 0;
			while(empItr.hasNext()) {
				EmpReqTypeAcc empReq = ((EmpReqTypeAcc)(empItr.next()));
//				log.debug("empReq " + empReq);
				if (count==0) {
//					empArray = empReq.getEmp_id().getEmpCode();
					empArray =  "'" + empReq.getEmp_id().getEmpCode() +  "'";
				} else {
//					empArray += "," + empReq.getEmp_id().getEmpCode();
					empArray += ",'" + empReq.getEmp_id().getEmpCode() + "'";
				}
				count++;
			}
//			totalObjects=requestsApprovalManager.getTimeAttend(empArray, fromDate, toDate);
//			totalObjects=requestsApprovalManager.getTimeAttendFromView(empArray, fromDate, toDate);
			if (empArray == null || empArray.isEmpty()) {
//				empArray = "'" + emp.getEmpCode() +  "'";
			} else {
				totalObjects=requestsApprovalManager.getTimeAttendAll(empArray, fromDate, toDate,statusId,settings);
			}
		}
		else {
//			totalObjects=requestsApprovalManager.getTimeAttend(empCode, fromDate, toDate);
//			totalObjects=requestsApprovalManager.getTimeAttendFromView(empCode, fromDate, toDate);
			String empArray = "";
			LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
//			List tempLevels = (List)requestsApprovalDAO.getAccessLevelsBetweenCodes(loggedInUser,codeFrom,codeTo);
			
//			Object obj = requestsApprovalManager.getObjectByParameter(AccessLevels.class, "emp_id", loggedInUser);
//			AccessLevels lev = null;
//			if (obj!= null) {
//			 lev = (AccessLevels)obj;
//			}
//			List tempLevels = new ArrayList();
//			if(lev!=null) {
//				tempLevels = (List)requestsApprovalDAO.getAccessLevelsBetweenCodes(lev.getLevel_id(),codeFrom,codeTo);
//			}
			
			List objectss = requestsApprovalManager.getObjectsByParameter(AccessLevels.class, "emp_id", loggedInUser);
			AccessLevels lev = null;
			log.debug("objectss size " + objectss.size());
//			List levs = new ArrayList();
			List tempLevels = new ArrayList();
			
			Iterator itrs = objectss.iterator();

			while (itrs.hasNext()) {
				Object obj = itrs.next();
				
				if (obj!= null) {
					lev = (AccessLevels)obj;
					log.debug("level " + lev.getId() + " emp " + lev.getEmp_id().getEmpCode());
				}
				
				if(lev!=null) {
					tempLevels.addAll(requestsApprovalDAO.getAccessLevelsBetweenCodes(lev.getLevel_id(),empCode,empCode));
				}
				log.debug("access levels size " + tempLevels.size());
			}
			
			log.debug("access levels size " + tempLevels.size());
			
			Iterator empItr = tempLevels.iterator();
			int count = 0;
			while(empItr.hasNext()) {
				EmpReqTypeAcc empReq = ((EmpReqTypeAcc)(empItr.next()));
//				log.debug("empReq " + empReq);
				if (count==0) {
//					empArray = empReq.getEmp_id().getEmpCode();
					empArray =  "'" + empReq.getEmp_id().getEmpCode() +  "'";
				} else {
//					empArray += "," + empReq.getEmp_id().getEmpCode();
					empArray += ",'" + empReq.getEmp_id().getEmpCode() + "'";
				}
				count++;
			}
			log.debug("empArray " + empArray);
			if (empArray == null || empArray.isEmpty()) {
				empArray = "'" + emp.getEmpCode() +  "'";//islam can't see his reports in getAttendanceReport service
				log.debug("empArray " + empArray);
				totalObjects=requestsApprovalManager.getTimeAttendAll(empArray, fromDate, toDate,statusId,settings);
			} else {
				totalObjects=requestsApprovalManager.getTimeAttendAll(empArray, fromDate, toDate,statusId,settings);
			}
		}
		if(!totalObjects.isEmpty()) {
			objects=(List) totalObjects.get(0);
		}
		
		log.debug("-------objects- size--"+objects.size());
		for (int i = 0; i < objects.size(); i++) {
			TimeAttend ob= (TimeAttend) objects.get(i);
			
			// mCalDate.setDateString(ob.getDay());
			DateFormat df=new SimpleDateFormat("dd/mm/yyyy");
			
			Date day;
			try {
				day = df.parse(ob.getDay());
//				log.debug("-------day---"+day);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			log.debug("-------objects---"+ob.getDay()+"-------getTimeIn---"+ob.getTimeIn()+"-------getTimeOut---"+ob.getTimeOut());
		}
		response.put("Attendance", objects);
		// VIP
		
		
		results.put("Results", response);
		status.setCode("200");
		status.setMessage("Request Success");
		status.setStatus("True");
		results.put("Status", status);
}  else {
	results.put("Results", response);
	status.setCode("318");
	status.setMessage("Request Failure, (empCode, fromDate, toDate all are mandatory)");
	status.setStatus("False");
	results.put("Status", status);
}

return results;
}

public Map editUserInfo(UserWrapper userWrapper, Employee employee) {
	// TODO Auto-generated method stub
	Map results = new HashMap();
	Map response = new HashMap();
	RestStatus status = new RestStatus();
	
	log.debug("profile pic string \n"+userWrapper.getProfilePic());
	if (userWrapper.getProfilePic()!=null  && !userWrapper.getProfilePic().isEmpty()) {
		byte[] profilePicBytes = Base64.decode(userWrapper.getProfilePic());
		log.debug("profile pic bytes " + profilePicBytes);
		employee.setProfilePic(profilePicBytes);
		employee.setProfilePicName("pic_"+employee.getEmpCode());
	}
	if (userWrapper.getMobileNo()!=null && !userWrapper.getMobileNo().isEmpty()) {
		String regexStr = "^[+]?[0-9]{10,13}$";
		employee.setTel(userWrapper.getMobileNo());
		if (userWrapper.getMobileNo().length()<11 || userWrapper.getMobileNo().matches(regexStr)==false) {
			results.put("Results", response);
			status.setCode("317");
			status.setMessage("Invalid mobile no");
			status.setStatus("False");
			results.put("Status", status);
			return results;
		}
	}
	if (userWrapper.getEmail()!=null && !userWrapper.getEmail().isEmpty()) {
		if (userWrapper.getEmail().indexOf('@') == -1
				|| userWrapper.getEmail().indexOf('.') == -1) {
			results.put("Results", response);
			status.setCode("316");
			status.setMessage("Invalid email format");
			status.setStatus("False");
			results.put("Status", status);
			return results;
		} else {
			employee.setEmail(userWrapper.getEmail());
		}
	}
	
	requestsApprovalManager.saveObject(employee);
	
	Map settingsMap = getSettings();
	Map settings = (Map)settingsMap.get("Response");
	Integer requiredVersion = (Integer)settings.get("requiredAndroidVersion");
	
	EmployeeResponse e = new EmployeeResponse();
	e.setAddress(employee.getAddress());
	e.setAttendanceCode(employee.getAttendanceCode());
	e.setBranch(employee.getBranch());
	e.setCity(employee.getCity());
	e.setDepartment(employee.getDepartment());
	e.setEmail(employee.getEmail());
	e.setEmpCode(employee.getEmpCode());
	e.setEmployeeCode(employee.getEmployeeCode());
	e.setExt(employee.getExt());
	e.setFirstName(employee.getFirstName());
	e.setGender(employee.getGender());
	e.setId(employee.getId());
	e.setIsDepartmentManager(employee.getIsDepartmentManager());
	e.setIsManager(employee.getIsManager());
	e.setJobTitle(employee.getJobTitle());
	e.setLastName(employee.getLastName());
	e.setTel(employee.getTel());
	e.setEmail(employee.getEmail());
	if (employee.getProfilePic()!=null) {
		String picString = Base64.encodeBytes(employee.getProfilePic());
		e.setProfilePic(picString);
	}
//	log.debug("pic string " +  picString.equals(userWrapper.getProfilePic()));
	e.setRequiredAndroidVersion(requiredVersion);

	response.put("Response", e);
	
	results.put("Results", response);
	status.setCode("200");
	status.setMessage("Request Success");
	status.setStatus("True");
	results.put("Status", status);
	return results;
}

public Map getRequestTypes() {
	List requestTypes = requestsApprovalManager.getObjectsOrderedByField(RequestTypes.class,"id");
	log.debug("request types list " + requestTypes.size());
	Map results = new HashMap();
	RestStatus status = new RestStatus();
	results.put("Results", requestTypes);
	log.debug("1");
	status.setCode("200");
	log.debug("2");
	status.setMessage("Request Success");
	log.debug("3");
	status.setStatus("True");
	log.debug("4");
	results.put("Status", status);
	log.debug("5");
	return results;
}

public Map getEmployeesByGroup(Long groupId) {
	List loginUsers = requestsApprovalManager.getEmployeesByGroup(groupId);
	Iterator itr = loginUsers.iterator();
	List loginUsersWrapper = new ArrayList();
	while(itr.hasNext()) {
		LoginUsers login = (LoginUsers)itr.next();
		LoginUserWrapper wrap = new LoginUserWrapper();
		wrap.setId(login.getId());
		wrap.setEmpCode(login.getEmpCode().getEmpCode());
		wrap.setName(login.getName());
		loginUsersWrapper.add(wrap);
	}
	Map results = new HashMap();
	RestStatus status = new RestStatus();
	results.put("Results", loginUsersWrapper);
	status.setCode("200");
	status.setMessage("Request Success");
	status.setStatus("True");
	results.put("Status", status);
	return results;
}

public Map getUserGroups(Employee employee) {
	LoginUsers loginUser=(LoginUsers) requestsApprovalDAO.getObjectByParameter(LoginUsers.class, "empCode", employee);
	List groups = requestsApprovalManager.getObjectsByParameter(AccessLevels.class, "emp_id", loginUser);
	List groupWrapper = new ArrayList();
	Iterator itr = groups.iterator();
	while(itr.hasNext()) {
		AccessLevels lev = (AccessLevels)itr.next();
		GroupWrapper wrap = new GroupWrapper();
		wrap.setGroupId(lev.getId());
		wrap.setGroupName(lev.getLevel_id().getTitle());
		groupWrapper.add(wrap);
	}
	Map results = new HashMap();
	RestStatus status = new RestStatus();
	results.put("Results", groupWrapper);
	status.setCode("200");
	status.setMessage("Request Success");
	status.setStatus("True");
	results.put("Status", status);
	return results;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////Timesheet requests///////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////

public Map insertTimesheetActivity(TimesheetActivityWrapper activity) {
	// TODO Auto-generated method stub
	Map results = new HashMap();
	Settings settings = (Settings)timesheetManager.getObject(Settings.class, new Long(1));
	if (settings.getIsTimesheetEnabled().equals(new Boolean(false))) {
		RestStatus status = new RestStatus();
		status.setCode("352");
		status.setMessage("This request is not enabled for the current application client");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	}
	TimesheetActivity a = new TimesheetActivity();
	a.setEname(activity.getEnName());
	a.setName(activity.getArName());
	ValidationStatus validationStatus = timesheetManager.validateActivity(a);
	if (validationStatus.getMsg().equals("Mandatory") && validationStatus.getObjAttribute().equals("name")) {
		RestStatus status = new RestStatus();
		status.setCode("332");
		status.setMessage("Arabic Name is Mandatory");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	} else if (validationStatus.getMsg().equals("Mandatory") && validationStatus.getObjAttribute().equals("ename")) {
		RestStatus status = new RestStatus();
		status.setCode("332");
		status.setMessage("English Name is Mandatory");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	} else if (validationStatus.getMsg().equals("Duplicate")) {
		RestStatus status = new RestStatus();
		status.setCode("332");
		status.setMessage("Arabic/English Name is entered before");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	} else {
		results = timesheetManager.insertTimesheetActivity(activity.getArName(),activity.getEnName());
		RestStatus status = new RestStatus();
		status.setCode("200");
		status.setMessage("Request Success");
		status.setStatus("True");
		results.put("Status", status);
		return results;
	}
}

public Map insertTimesheetPart(TimesheetPartWrapper part) {
	System.out.println("part.getPartNo() " + part.getPartNo());
	Map results = new HashMap();
	Settings settings = (Settings)timesheetManager.getObject(Settings.class, new Long(1));
	if (settings.getIsTimesheetEnabled().equals(new Boolean(false))) {
		RestStatus status = new RestStatus();
		status.setCode("352");
		status.setMessage("This request is not enabled for the current application client");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	}
	TimesheetTransactionParts p = new TimesheetTransactionParts();
	p.setEname(part.getEnName());
	p.setName(part.getArName());
	p.setPart_no(part.getPartNo());
	ValidationStatus validationStatus = timesheetManager.validatePart(p);
	System.out.println("msg "+validationStatus.getMsg());
	System.out.println("attribute "+validationStatus.getObjAttribute());
	
	if(validationStatus!=null && validationStatus.getMsg()!=null && validationStatus.getObjAttribute()!=null) {
		if (validationStatus.getMsg().equals("Mandatory") && validationStatus.getObjAttribute().equals("name")) {
			RestStatus status = new RestStatus();
			status.setCode("332");
			status.setMessage("Arabic Name is Mandatory");
			status.setStatus("False");
			results.put("Results", new ArrayList());
			results.put("Status", status);
			return results;
		} else if (validationStatus.getMsg().equals("Mandatory") && validationStatus.getObjAttribute().equals("ename")) {
			RestStatus status = new RestStatus();
			status.setCode("332");
			status.setMessage("English Name is Mandatory");
			status.setStatus("False");
			results.put("Results", new ArrayList());
			results.put("Status", status);
			return results;
		} else if (validationStatus.getMsg().equals("Duplicate")) {
			RestStatus status = new RestStatus();
			status.setCode("332");
			status.setMessage("Arabic/English Name is entered before");
			status.setStatus("False");
			results.put("Results", new ArrayList());
			results.put("Status", status);
			return results;
		}  else if (part.getPartNo()== null || part.getPartNo()<=0) {
			RestStatus status = new RestStatus();
			status.setCode("334");
			status.setMessage("Part No must have a value greater than zero");
			status.setStatus("False");
			results.put("Results", new ArrayList());
			results.put("Status", status);
			return results;
		} else if (part.getPartNo()!= null && part.getPartNo()>3) {
			RestStatus status = new RestStatus();
			status.setCode("348");
			status.setMessage("Part No must have a value of 1,2 or 3");
			status.setStatus("False");
			results.put("Results", new ArrayList());
			results.put("Status", status);
			return results;
		}else {
			results = timesheetManager.insertTimesheetPart(part.getArName(),part.getEnName(),part.getPartNo());
			RestStatus status = new RestStatus();
			status.setCode("200");
			status.setMessage("Request Success");
			status.setStatus("True");
			results.put("Status", status);
			return results;
		} 
	} else {
		results = timesheetManager.insertTimesheetPart(part.getArName(),part.getEnName(),part.getPartNo());
		RestStatus status = new RestStatus();
		status.setCode("200");
		status.setMessage("Request Success");
		status.setStatus("True");
		results.put("Status", status);
		return results;
	}
}

public Map insertTimesheetSpecs(TimesheetSpecsWrapper specs) {
	Map results = new HashMap();
	Settings settings = (Settings)timesheetManager.getObject(Settings.class, new Long(1));
	if (settings.getIsTimesheetEnabled().equals(new Boolean(false))) {
		RestStatus status = new RestStatus();
		status.setCode("352");
		status.setMessage("This request is not enabled for the current application client");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	}
	if (specs.getIsUsed1()==null || specs.getIsUsed1().isEmpty()) {
		RestStatus status = new RestStatus();
		status.setCode("336");
		status.setMessage("isUsed1 is mandatory");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	} else if (specs.getIsUsed2()==null||specs.getIsUsed2().isEmpty()) {
		RestStatus status = new RestStatus();
		status.setCode("337");
		status.setMessage("isUsed2 is mandatory");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	} else if (specs.getIsUsed3()==null || specs.getIsUsed3().isEmpty()) {
		RestStatus status = new RestStatus();
		status.setCode("338");
		status.setMessage("isUsed3 is mandatory");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	} else if (specs.getIsUsed1()!=null && specs.getIsUsed1().equals("1") && (specs.getPartName1()== null || specs.getPartName1().isEmpty())) {
		RestStatus status = new RestStatus();
		status.setCode("339");
		status.setMessage("Part Name 1 is mandatory");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	} else if (specs.getIsUsed2()!=null && specs.getIsUsed2().equals("1") && (specs.getPartName2()== null || specs.getPartName2().isEmpty())) {
		RestStatus status = new RestStatus();
		status.setCode("340");
		status.setMessage("Part Name 2 is mandatory");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	} else if (specs.getIsUsed3()!=null && specs.getIsUsed3().equals("1") && (specs.getPartName3()== null || specs.getPartName3().isEmpty())) {
		RestStatus status = new RestStatus();
		status.setCode("341");
		status.setMessage("Part Name 3 is mandatory");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	}
	results = timesheetManager.insertTimesheetSpecs(specs.getPartName1(),specs.getPartEName1(),specs.getIsUsed1(),specs.getPartName2(),specs.getPartEName2(),specs.getIsUsed2(),specs.getPartName3(),specs.getPartEName3(),specs.getIsUsed3());
	RestStatus status = new RestStatus();
	status.setCode("200");
	status.setMessage("Request Success");
	status.setStatus("True");
	results.put("Status", status);
	return results;
}

public Map insertTimesheetTransDefaults(TimesheetTransDefaultWrapper defaults) {
	Map results = new HashMap();
	Settings settings = (Settings)timesheetManager.getObject(Settings.class, new Long(1));
	if (settings.getIsTimesheetEnabled().equals(new Boolean(false))) {
		RestStatus status = new RestStatus();
		status.setCode("352");
		status.setMessage("This request is not enabled for the current application client");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	}
	if (defaults.getEmpCode()==null || defaults.getEmpCode().isEmpty()) {
		RestStatus status = new RestStatus();
		status.setCode("342");
		status.setMessage("employee code is mandatory");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	} else if (defaults.getCostCenterCode()==null || defaults.getCostCenterCode().isEmpty()) {
		RestStatus status = new RestStatus();
		status.setCode("343");
		status.setMessage("cost center is mandatory");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	}
	results = timesheetManager.insertTimesheetTransDefaults(defaults.getEmpCode(),defaults.getCostCenterCode(),defaults.getActivityCode(),defaults.getPartCode1(),defaults.getPartCode2(),defaults.getPartCode3());
	RestStatus status = new RestStatus();
	status.setCode("200");
	status.setMessage("Request Success");
	status.setStatus("True");
	results.put("Status", status);
	return results;
}

public Map insertTimesheetTransaction(TimesheetTransWrapper trans) {
	System.out.println("1");
	Map results = new HashMap();
	Settings settings = (Settings)timesheetManager.getObject(Settings.class, new Long(1));
	if (settings.getIsTimesheetEnabled().equals(new Boolean(false))) {
		RestStatus status = new RestStatus();
		status.setCode("352");
		status.setMessage("This request is not enabled for the current application client");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	}
	DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
	System.out.println("2");
	if (trans.getInDate()==null || trans.getInDate().isEmpty()) {
		System.out.println("3");
		RestStatus status = new RestStatus();
		status.setCode("344");
		status.setMessage("Timesheet transaction date is mandatory");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	} else {
		System.out.println("else 3");
		try {
			df.parse(trans.getInDate());
			System.out.println("4");
		} catch (Exception e) {
			System.out.println("4 catch");
			RestStatus status = new RestStatus();
			status.setCode("345");
			status.setMessage("Timesheet transaction date must be dd/mm/yyyy");
			status.setStatus("False");
			results.put("Results", new ArrayList());
			results.put("Status", status);
			return results;
		}
	}
	System.out.println("5");
	if (trans.getActivityCode()==null || trans.getActivityCode().isEmpty()) {
		System.out.println("6");
		RestStatus status = new RestStatus();
		status.setCode("346");
		status.setMessage("Can't find activity with the given activity code");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	}
	if (trans.getCostCenterCode()==null || trans.getCostCenterCode().isEmpty()) {
		System.out.println("7");
		RestStatus status = new RestStatus();
		status.setCode("347");
		status.setMessage("Can't find cost center with the given cost center code");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	}
	System.out.println("8");
	results = timesheetManager.insertTimesheetTransaction(trans.getEmpCode(),trans.getCostCenterCode(),trans.getActivityCode(),trans.getInDate(),trans.getcHour(),trans.getcMinute(),trans.getPartCode1(),trans.getPartCode2(),trans.getPartCode3(),trans.getRemark());
	if (results.get("Status") != null) {
		return results;
	} else {
		RestStatus status = new RestStatus();
		status.setCode("200");
		status.setMessage("Request Success");
		status.setStatus("True");
		results.put("Status", status);
		return results;
	}
}

public Map getActivities() {
	Map results = new HashMap();
	Settings settings = (Settings)timesheetManager.getObject(Settings.class, new Long(1));
	if (settings.getIsTimesheetEnabled().equals(new Boolean(false))) {
		RestStatus status = new RestStatus();
		status.setCode("352");
		status.setMessage("This request is not enabled for the current application client");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	}
	List activities = timesheetManager.getObjects(TimesheetActivity.class);
	List activitiesWrapper = new ArrayList();
	Iterator itr = activities.iterator();
	while(itr.hasNext()) {
		TimesheetActivity activity = (TimesheetActivity)itr.next();
		activitiesWrapper.add(activity.getWrapper());
	}
	results.put("Results", activitiesWrapper);
	RestStatus status = new RestStatus();
	status.setCode("200");
	status.setMessage("Request Success");
	status.setStatus("True");
	results.put("Status", status);
	return results;
}

public Map getCostcenters() {
	Map results = new HashMap();
	Settings settings = (Settings)timesheetManager.getObject(Settings.class, new Long(1));
	if (settings.getIsTimesheetEnabled().equals(new Boolean(false))) {
		RestStatus status = new RestStatus();
		status.setCode("352");
		status.setMessage("This request is not enabled for the current application client");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	}
	
	List costcenters = timesheetManager.getObjects(TimesheetCostCenter.class);
	List costcentersWrapper = new ArrayList();
	Iterator itr = costcenters.iterator();
	while(itr.hasNext()) {
		TimesheetCostCenter costCenter = (TimesheetCostCenter)itr.next();
		costcentersWrapper.add(costCenter.getWrapper());
	}
	
	results.put("Results", costcentersWrapper);
	RestStatus status = new RestStatus();
	status.setCode("200");
	status.setMessage("Request Success");
	status.setStatus("True");
	results.put("Status", status);
	return results;
}

public Map getParts(Short partNo) {
	Map results = new HashMap();
	Settings settings = (Settings)timesheetManager.getObject(Settings.class, new Long(1));
	if (settings.getIsTimesheetEnabled().equals(new Boolean(false))) {
		RestStatus status = new RestStatus();
		status.setCode("352");
		status.setMessage("This request is not enabled for the current application client");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	}
	List parts = new ArrayList();
	System.out.println("part No " + partNo);
	if (partNo!= null && (partNo==1 || partNo==2 || partNo==3)) {
		System.out.println("1.part No " + partNo);
		parts = timesheetManager.getObjectsByParameter(TimesheetTransactionParts.class, "part_no", partNo);
	} else if (partNo==null) {
		System.out.println("2.part No " + partNo);
		parts = timesheetManager.getObjects(TimesheetTransactionParts.class);
		
	} else {
		System.out.println("3.part No " + partNo);
		RestStatus status = new RestStatus();
		status.setCode("351");
		status.setMessage("Part Number must be 1,2 or 3");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	}
	TimesheetTransactionParts nullPart = (TimesheetTransactionParts)timesheetManager.getObjectByParameter(TimesheetTransactionParts.class, "code", "9999999999");
	parts.remove(nullPart);
	List partsWrapper = new ArrayList();
	Iterator itr = parts.iterator();
	while(itr.hasNext()) {
		TimesheetTransactionParts part = (TimesheetTransactionParts)itr.next();
		partsWrapper.add(part.getWrapper());
	}
	
	results.put("Results", partsWrapper);
	RestStatus status = new RestStatus();
	status.setCode("200");
	status.setMessage("Request Success");
	status.setStatus("True");
	results.put("Status", status);
	return results;
}

public Map getTimesheetSpecs() {
	Map results = new HashMap();
	Settings settings = (Settings)timesheetManager.getObject(Settings.class, new Long(1));
	if (settings.getIsTimesheetEnabled().equals(new Boolean(false))) {
		RestStatus status = new RestStatus();
		status.setCode("352");
		status.setMessage("This request is not enabled for the current application client");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	}
	List specs = timesheetManager.getObjects(TimesheetSpecs.class);
	List specsWrapper = new ArrayList();
	Iterator itr = specs.iterator();
	while(itr.hasNext()) {
		TimesheetSpecs spec = (TimesheetSpecs)itr.next();
		specsWrapper.add(spec.getWrapper());
	}
	
	results.put("Results", specsWrapper);
	RestStatus status = new RestStatus();
	status.setCode("200");
	status.setMessage("Request Success");
	status.setStatus("True");
	results.put("Status", status);
	return results;
}

public Map getTimesheetTransactions(TimesheetTransactionFilters search) {
	Map results = new HashMap();
	Settings settings = (Settings)timesheetManager.getObject(Settings.class, new Long(1));
	if (settings.getIsTimesheetEnabled().equals(new Boolean(false))) {
		RestStatus status = new RestStatus();
		status.setCode("352");
		status.setMessage("This request is not enabled for the current application client");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	}
	Employee employee = null;
	log.debug("search.getEmpCode() " +search.getEmpCode());
	log.debug("!search.getEmpCode().contains(,) " + !search.getEmpCode().contains(","));
	Map transMap = new HashMap();
	if (search.getEmpCode()!= null && !search.getEmpCode().isEmpty() && !search.getEmpCode().contains(",")) {
		employee = (Employee)timesheetManager.getObjectByParameter(Employee.class, "empCode", search.getEmpCode());
		if (employee!=null) {
			transMap = timesheetManager.getTimesheetTransactions(search.getEmpCode(), search.getFromDate(), search.getToDate(), search.getCostCenter(),
					search.getActivity(), search.getPart1(), search.getPart2(), search.getPart3(), search.getPageNumber(), search.getPageSize(), search.getSort());
		} else {
			RestStatus status = new RestStatus();
			status.setCode("349");
			status.setMessage("Employee with empCode "+ search.getEmpCode() + " is not found in DB");
			status.setStatus("False");
			results.put("Results", new ArrayList());
			results.put("Status", status);
			return results;
		}
	} else if(search.getEmpCode().contains(",")){
		transMap = timesheetManager.getTimesheetTransactions(search.getEmpCode(), search.getFromDate(), search.getToDate(), search.getCostCenter(),
				search.getActivity(), search.getPart1(), search.getPart2(), search.getPart3(), search.getPageNumber(), search.getPageSize(), search.getSort());
	}else {
		RestStatus status = new RestStatus();
		status.setCode("350");
		status.setMessage("Logged in employee has no empCode, please retry to login again");
		status.setStatus("False");
		results.put("Results", new ArrayList());
		results.put("Status", status);
		return results;
	}
	List transWrapper = new ArrayList();
//	List trans = (List)transMap.get("Results");
//	if (trans != null) {
//		Iterator itr = trans.iterator();
//		while(itr.hasNext()) {
//			TimesheetTransaction t = (TimesheetTransaction)itr.next();
//			transWrapper.add(t.getWrapper());
//		}
//	}
//	results.put("Results", transWrapper);
	results = transMap;
	RestStatus status = new RestStatus();
	status.setCode("200");
	status.setMessage("Request Success");
	status.setStatus("True");
	results.put("Status", status);
	return results;
}



// @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
// public void saveEmployee(long employeeId, String name, String surname, String jobDescription) throws Exception {
// 
//  EmployeeDTO employeeDTO = employeeDAO.findById(employeeId);
// 
//  if(employeeDTO == null) {
//   employeeDTO = new EmployeeDTO(employeeId, name,surname, jobDescription);
//   employeeDAO.persist(employeeDTO);
//  }
// 
// }
// 
// @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
// public void updateEmployee(long employeeId, String name, String surname, String jobDescription) throws Exception {
// 
//  EmployeeDTO employeeDTO = employeeDAO.findById(employeeId);
// 
//  if(employeeDTO != null) {
//   employeeDTO.setEmployeeName(name);
//   employeeDTO.setEmployeeSurname(surname);
//   employeeDTO.setJob(jobDescription);
//  }
// 
//}
 
}
