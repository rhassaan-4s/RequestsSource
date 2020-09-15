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
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
import com._4s_.common.service.CommonManager;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.requestsApproval.dao.ExternalQueries;
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
import com._4s_.restServices.json.ImeiWrapper;
import com._4s_.restServices.json.PasswordWrapper;
import com._4s_.restServices.json.RequestApproval;
import com._4s_.restServices.json.RequestsApprovalQuery;
import com._4s_.restServices.json.RestStatus;
import com._4s_.restServices.json.UserWrapper;
import com._4s_.restServices.model.AttendanceStatus;
import com._4s_.security.dao.MySecurityDAO;
import com._4s_.security.model.Imei;
import com._4s_.security.model.User;
//import com.javacodegeeks.gwtspring.server.utils.NotificationsProducer;
//import com.javacodegeeks.gwtspring.shared.dto.EmployeeDTO;
//import com.javacodegeeks.gwtspring.shared.services.EmployeeService;


@Service("requestsService")
@Transactional
public class RequestsServiceImpl implements RequestsService, UserDetailsService {
 
 @Autowired
 private RequestsApprovalDAO requestsApprovalDAO;
 
 @Autowired
 private RequestsApprovalManager requestsApprovalManager;
 
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
	 System.out.println("security dao " + securityDao);
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
	System.out.println("imei " + im);
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
	 System.out.println("#########################"+System.getProperty("user.timezone"));
	    
	DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	Date newDate = null;
	
	Map response = new HashMap();
	RestStatus restStatus = new RestStatus();
	
	
//	TimeZone tz = TimeZone.getTimeZone("Europe/Rome");
//	TimeZone.setDefault(tz);
//	Calendar c = Calendar.getInstance(tz);
	
	Calendar c= Calendar.getInstance();
	
	System.out.println("timezone " + c.getTimeZone());
//	c.setTimeZone(TimeZone.getTimeZone("EST"));
//	System.out.println("timezone " + c.getTimeZone());
	newDate = c.getTime();
	System.out.println("parsed date " + newDate);
	MultiCalendarDate mCalDate = new MultiCalendarDate();
	mCalDate.setDate(newDate);
	
	System.out.println("SignInOunt: mcaldate " + mCalDate.getDate());
	
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

	LoginUsers loginUsers=(LoginUsers) requestsApprovalDAO.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
	if(loginUsers!=null){
		System.out.println("-----login.code----"+loginUsers.getEmpCode());
	}

	System.out.println("settings.getAutomaticSignInOut().booleanValue() " + settings.getAutomaticSignInOut().booleanValue());
	if (settings.getAutomaticSignInOut().booleanValue() == false) {
//		List attendanceRequests = requestsApprovalManager.getAttendanceRequests(mCalDate.getDate(),loginUsers.getEmpCode());
		System.out.println("Will sign in manually");
		
		System.out.println("will validate");
		
		restStatus = requestsApprovalManager.validateSignInOut(userRequest.getAttendanceType(), mCalDate.getDate(), emp);
		
		if (restStatus.getStatus().equals("False")) {
			response.put("Status", restStatus);
			return response;
		} else {
			return createManualAttendance(loginUsers,mCalDate,emp,userRequest);
		}
	} else {
		String trans_type = null;
		if (userRequest.getAttendanceType().equals(new Long(1))) {
			trans_type = "I";
		} else {
			trans_type = "O";
		}
		System.out.println("date_" + dateOnly + " time_ " + newDate);
		int result = requestsApprovalManager.insertTimeAttendance(settings.getServer(),settings.getService(),settings.getUsername(),settings.getPassword(),
				emp.getEmpCode(),dateOnly, newDate,trans_type);
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
	
	//10 signin 11 signout
	RequestTypes reqType = null;
	System.out.println("userRequest.getAttendanceType() " + userRequest.getAttendanceType());
	if (userRequest.getAttendanceType().equals(new Long(1))) {
		reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(10));
	} else {
		reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(11));
	}
	loginUsersRequests.setRequest_id(reqType);

	loginUsersRequests.setLatitude(userRequest.getLatitude());
	loginUsersRequests.setLongitude(userRequest.getLongitude());

	RequestApproval approvals = new RequestApproval();
	approvals.setApprove("1");
	approvals.setNotes("Android Sign in/out Automatic Approval");
	approvals.setRequestId(loginUsersRequests.getId()+"");
	requestsApprovalManager.automaticApprovalsAccessLevels(approvals, loginUsersRequests);
	
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
	LoginUsers loginUsers=(LoginUsers) requestsApprovalDAO.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
	
//	LoginUsersRequests withoutSalVac= new LoginUsersRequests();
	
	DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	Date from = null;
	try {
		from = df.parse(userRequest.getAttendanceTime());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
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
		System.out.println(e.getMessage());
	}
	cal.setTime(to);
	cal.set(Calendar.HOUR_OF_DAY, 23);
	cal.set(Calendar.MINUTE, 59);
	cal.set(Calendar.SECOND, 59);
	MultiCalendarDate mCalDateTo = new MultiCalendarDate();
	mCalDateTo.setDate(cal.getTime());
	
	Long diff = mCalDateTo.getDate().getTime()-mCalDateFrom.getDate().getTime();
	
	if (diff < 0) {
		return null;
	}
	Double daysDiff = Double.valueOf(Math.round((diff/(1000*60*60*24.0))));
	System.out.println("daysDiff " + daysDiff);
	withoutSalVac.setWithdrawDays(daysDiff);
	withoutSalVac.setEmpCode(emp.getEmpCode());
	withoutSalVac.setLogin_user(loginUsers);
	withoutSalVac.setRequest_date(Calendar.getInstance().getTime());
//	withoutSalVac.setNotes(userRequest.getNotes());
	withoutSalVac.setApproved(new Long(0));
	withoutSalVac.setApplicable(new Long(1));
	withoutSalVac.setPosted(new Long(0));
	withoutSalVac.setReply("--");
	
	RequestTypes request_id = null;
	
	Vacation vacation = null;

	if (userRequest.getVacation()!= null && !userRequest.getVacation().isEmpty()){
		vacation = (Vacation)requestsApprovalManager.getObject(Vacation.class, userRequest.getVacation());
	}
	
	if (userRequest.getAttendanceType().equals(new Long(7))) {
		request_id= (RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, new Long (1));
	} else if (userRequest.getAttendanceType().equals(new Long(8))) {
		request_id= (RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, new Long (2));
	}
	System.out.println("handlevacations request " + request_id.getId());
	if(withoutSalaryVacEnabled==true){//Lotus
		if(userRequest.getAttendanceType().equals(new Long(7)) && userRequest.getVacation().equals("008")){
			//tasree7 object
			System.out.println("---without Salary vacation ---");
//			System.out.println("---------loginUsersRequests.getPeriod_from()-------"+loginUsersRequests.getPeriod_from());
			
			request_id= (RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, new Long (3));
			System.out.println("1.request " + request_id.getId());
			
			withoutSalVac.setRequest_id(request_id);
			
		} else if(userRequest.getAttendanceType().equals(new Long(7))) {
			request_id= (RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, new Long (1));
			System.out.println("2.request " + request_id.getId());
			withoutSalVac.setVacation(vacation);
		}
	}  else if(userRequest.getAttendanceType().equals(new Long(8))) {
		request_id= (RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, new Long (2));
		System.out.println("request " + request_id.getId());
		withoutSalVac.setVacation(vacation);
	}
	else {
		withoutSalVac.setVacation(vacation);
	}
	System.out.println("request " + request_id.getId());
	withoutSalVac.setRequest_id(request_id);
	
	if(periodFromToEnabled==true){//Lehaa
			withoutSalVac.setFrom_date(mCalDateFrom.getDate());
			withoutSalVac.setTo_date(mCalDateTo.getDate());
			withoutSalVac.setPeriod_from(mCalDateFrom.getDate());
			withoutSalVac.setPeriod_to(mCalDateTo.getDate());
	}else {//Lotus
			withoutSalVac.setFrom_date(mCalDateFrom.getDate());
			withoutSalVac.setTo_date(mCalDateTo.getDate());
	}
	
	String requestNumber="";
	requestNumber=requestsApprovalManager.CreateRequestNumber();
	withoutSalVac.setRequestNumber(requestNumber);
	System.out.println("requestNumber in handle vacation " + requestNumber);
	
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
			||(userRequest.getAttendanceType().equals(new Long(9)) && (userRequest.getAttendanceTime2() == null ||userRequest.getAttendanceTime2() == null))
			||(userRequest.getAttendanceType().equals(new Long(3)) && (userRequest.getAttendanceTime2() == null ||userRequest.getAttendanceTime2() == null))
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
		if (!userRequest.getAttendanceType().equals(new Long(9))) {
			newDate = df.parse(userRequest.getAttendanceTime());
		} else {
			newDate = df2.parse(userRequest.getAttendanceTime());
		}
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
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
			if (!userRequest.getAttendanceType().equals(new Long(9))) {
				to = df.parse(userRequest.getAttendanceTime2());
			} else {
				to = df2.parse(userRequest.getAttendanceTime2());
				System.out.println("to " + to);
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
		System.out.println(e.getMessage());
		status.setCode("312");
		status.setMessage("Date is not well formated");
		status.setStatus("False");
		response.put("Status", status);
		return response;
	}  catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
		status.setCode("312");
		status.setMessage("Date Exception");
		status.setStatus("False");
		response.put("Status", status);
		return response;
	}
	
	
	
	Employee emp = (Employee)requestsApprovalDAO.getObject(Employee.class, empId);

	LoginUsers loginUsers=(LoginUsers) requestsApprovalDAO.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
	if(loginUsers!=null){
		System.out.println("-----login.code----"+loginUsers.getEmpCode());
	}
	LoginUsersRequests loginUsersRequests = null;
	Vacation vac = null;
	//1 agaza khasa 2 agaza dawreya 3 tasree7 4 ma2moreya yom kamel
	
	//7:Special Vacation-e3teyadi(1-001) Special Vacation-3arda(1-002) Special Vacation-marady(1-003) Special Vacation-ra7et wardeya(1-888) 
		//8:Periodic Vacation(2)
	
		RequestTypes reqType = null;
		System.out.println("test userRequest.getAttendanceType() " + userRequest.getAttendanceType());
		if (userRequest.getAttendanceType().equals(new Long(0))) {
//			System.out.println("0 reqType " + reqType.getId()); 
			reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(1));
			System.out.println(" reqType " + reqType.getId());
		}  else if (userRequest.getAttendanceType().equals(new Long(3))) {//Permission	// || userRequest.getAttendanceType().equals(new Long(4))
//			System.out.println("3 4 reqType " + reqType.getId());
			reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(3));
			System.out.println(" reqType " + reqType.getId());
		} else if (userRequest.getAttendanceType().equals(new Long(5)) ||userRequest.getAttendanceType().equals(new Long(6))) {//errand
//			System.out.println("5 6 reqType " + reqType.getId());
			reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(1));
			vac = (Vacation)requestsApprovalManager.getObjectByParameter(Vacation.class,"vacation", "999");
			System.out.println(" reqType " + reqType.getId());
		} else if (userRequest.getAttendanceType().equals(new Long(7))) { //special vacation
//			System.out.println("7 reqType " + reqType);
			loginUsersRequests = new LoginUsersRequests();
			handleVacations(userRequest, empId,loginUsersRequests);
			if (loginUsersRequests != null) {
				reqType = loginUsersRequests.getRequest_id();
				System.out.println(" reqType " + reqType.getId());
			} else {
				status.setCode("313");
				status.setMessage("To date is before from date.");
				status.setStatus("False");
				response.put("Status", status);
				return response;
			}
		} else if (userRequest.getAttendanceType().equals(new Long(8))) {//periodic vacation
//			System.out.println("8 reqType " + reqType.getId());
			loginUsersRequests = new LoginUsersRequests();
			handleVacations(userRequest, empId,loginUsersRequests);
			reqType = loginUsersRequests.getRequest_id();
			System.out.println(" reqType " + reqType.getId());
		} else if (userRequest.getAttendanceType().equals(new Long(9))) {//Full Day errand
//			System.out.println("5 6 reqType " + reqType.getId());
//			reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(1));
//			vac = (Vacation)requestsApprovalManager.getObjectByParameter(Vacation.class,"vacation", "999");
			reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(4));
			System.out.println(" reqType " + reqType.getId());
		} else {
			System.out.println("userRequest.getAttendanceType() " + userRequest.getAttendanceType().getClass());
		}
		if (userRequest.getAttendanceType().equals(new Long(3))) {
			System.out.println("validating");
			if (userRequest.getPermissionEffect() == null) {
				System.out.println("validating2");
				status.setCode("305");
				status.setMessage("One or more of Permission Parameters is null");
				status.setStatus("False");
				response.put("Status", status);
				return response;
			}
		}
		
		
//		System.out.println("mCalDate " + mCalDate);
		System.out.println("mCalDate.getDate() " + mCalDate.getDate());
		if (userRequest.getAttendanceType().equals(new Long(6))
				||userRequest.getAttendanceType().equals(new Long(3)) || userRequest.getAttendanceType().equals(new Long(5))) {//Permission End || //Full Day Permission End
			//userRequest.getAttendanceType().equals(new Long(4)) || 
			
			
			if (userRequest.getAttendanceType().equals(new Long(6))) {//userRequest.getAttendanceType().equals(new Long(4)) ||permission start and end depricated with only one request
				RequestsApprovalQuery requestQueryending = new RequestsApprovalQuery();
				
				//1 permission 2 errands
//				if (userRequest.getAttendanceType().equals(new Long(4))) {//permission 
//					requestQueryending.setRequestType("1");
//				} else
					if (userRequest.getAttendanceType().equals(new Long(6))) {//errand 
					requestQueryending.setRequestType("2");
				} else {
					System.out.println("condition not handled 3 " + userRequest.getAttendanceType());
				}
				requestQueryending.setDateFrom(userRequest.getAttendanceTime());
				requestQueryending.setDateTo(userRequest.getAttendanceTime());
				List requests = (List)(requestsApprovalManager.checkStartedRequests(requestQueryending, emp)).get("Response");
				Iterator itrrequests = requests.iterator();
				while(itrrequests.hasNext()) {
					System.out.println("####Strarted requests "+((LoginUsersRequests)itrrequests.next()).getRequestNumber());
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
					loginUsersRequests.setEmpCode(loginUsers.getEmpCode());
					

					// request number
					if (loginUsersRequests.getId() == null){
						String requestNumber="";
						requestNumber=requestsApprovalManager.CreateRequestNumber();
						loginUsersRequests.setRequestNumber(requestNumber);
						System.out.println("requestNumber in user request " + requestNumber);
						cal.setTime(newDate);
						cal.set(Calendar.HOUR_OF_DAY, 0);
						cal.set(Calendar.MINUTE, 0);
						cal.set(Calendar.SECOND, 0);
						mCalDate.setDate(cal.getTime());
						loginUsersRequests.setPeriod_from(mCalDate.getDate());
						loginUsersRequests.setPeriod_to(mCalDateTo.getDate());
					}
				}
			} else {//starting permissions and errands
				RequestsApprovalQuery requestQuery = new RequestsApprovalQuery();
				System.out.println("#$#$starting permissions or errands");
//				//1 permission 2 errands
				if (userRequest.getAttendanceType().equals(new Long(3))) {//permission start
					requestQuery.setRequestType("1");
				} else if (userRequest.getAttendanceType().equals(new Long(5))) {//errand start
					requestQuery.setRequestType("2");
				} else {
					System.out.println("condition not handled 2 " + userRequest.getAttendanceType());
				}
				
//				requestQuery.setDateFrom(userRequest.getAttendanceTime());
				
				requestQuery.setDateTo(userRequest.getAttendanceTime());
				requestQuery.setEmp_code(emp.getEmpCode());
				
				
				Map startedRequests = checkStartedRequests(requestQuery, emp);
				List startedRequestsResponse = (List)startedRequests.get("Response");
				System.out.println("#$#$starting permissions or errands ---- checked started requests ---" + startedRequestsResponse.size());
				///////////////////////end requests automatically////////////////////////////////////////////////
//				Settings settings = (Settings)requestsApprovalManager.getObject(Settings.class, new Long(1));
//				List results = (List)response.get("Response");
				Iterator itr = startedRequestsResponse.iterator();
				
				System.out.println("checkStartedRequests: automatic errands end " + settings.getAutomaticErrandEnd());
				Calendar dayBeforeEndDate = Calendar.getInstance();
				dayBeforeEndDate.setTime(mCalDate.getDate());
				dayBeforeEndDate.set(Calendar.HOUR, 0);
				dayBeforeEndDate.set(Calendar.MINUTE, 0);
				dayBeforeEndDate.set(Calendar.SECOND, 0);
				dayBeforeEndDate.set(Calendar.MILLISECOND, 0);
				if (settings.getAutomaticErrandEnd() != null && !settings.getAutomaticErrandEnd().isEmpty()) {
					while (itr.hasNext()) {
						LoginUsersRequests req = (LoginUsersRequests)itr.next();
						System.out.println("checkStartedRequests: period to " + req.getPeriod_to());
						System.out.println("req.getRequest_id().equals(loginUsersRequests.getRequest_id() " + req.getRequest_id().equals(reqType));
						if (!req.getRequest_id().getId().equals(new Long(10)) && !req.getRequest_id().getId().equals(new Long(11)) &&req.getPeriod_from().before(dayBeforeEndDate.getTime()) && req.getPeriod_to() == null && req.getRequest_id().equals(reqType)) {
							String requestEndTime = settings.getAutomaticErrandEnd();
							String [] time =  requestEndTime.split(":");
							System.out.println("Time hour " + time[0] + " minutes " + time[1]);
							if (req.getPeriod_from() != null) {
								System.out.println("request no " + req.getRequestNumber() + " started on " + req.getPeriod_from());
								Calendar cal2 = Calendar.getInstance();
								cal2.setTime(req.getPeriod_from());
								cal2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
								cal2.set(Calendar.MINUTE, Integer.parseInt(time[1]));
								cal2.set(Calendar.SECOND, 0);
								System.out.println("will end request on " + cal2.getTime());
								req.setPeriod_to(cal2.getTime());
								req.setTo_date(cal2.getTime());
								req.setNotes(req.getNotes() + " (Request ended automatically by the system)");
								requestsApprovalManager.saveObject(req);
							}
						} else if ( !req.getRequest_id().getId().equals(new Long(10)) && !req.getRequest_id().getId().equals(new Long(11)) && req.getPeriod_to() == null) {
							System.out.println("request opened in the last 24hrs is not ended");
						} else if (req.getRequest_id().getId().equals(new Long(10)) && settings.getSignoutBeforePermissionErrand().equals(new Boolean(true))){
							System.out.println("sign in request opened in the last 24hrs is not ended");
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
				System.out.println("permission/errand start , size =" + startedRequestsResponse.size());
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
					loginUsersRequests.setEmpCode(loginUsers.getEmpCode());
					

					// request number
					if (loginUsersRequests.getId() == null && loginUsersRequests.getRequestNumber() == null){
						String requestNumber="";
						requestNumber=requestsApprovalManager.CreateRequestNumber();
						loginUsersRequests.setRequestNumber(requestNumber);
						loginUsersRequests.setPeriod_from(mCalDate.getDate());
						if (userRequest.getAttendanceType().equals(new Long(3))) {
							loginUsersRequests.setPeriod_to(mCalDateToPermission.getDate());
						}
					}
				}
			}
		}else if (userRequest.getAttendanceType().equals(new Long(9))) {
			loginUsersRequests = new LoginUsersRequests();
			loginUsersRequests.setLogin_user(loginUsers);
			loginUsersRequests.setEmpCode(loginUsers.getEmpCode());
			

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
			System.out.println("daysDiff " + daysDiff);
			loginUsersRequests.setWithdrawDays(daysDiff);
		} else if (!userRequest.getAttendanceType().equals(new Long(7))&& !userRequest.getAttendanceType().equals(new Long(8))){
			loginUsersRequests = new LoginUsersRequests();
			loginUsersRequests.setLogin_user(loginUsers);
			loginUsersRequests.setEmpCode(loginUsers.getEmpCode());
			

			// request number
			if (loginUsersRequests.getId() == null){
				String requestNumber="";
				requestNumber=requestsApprovalManager.CreateRequestNumber();
				loginUsersRequests.setRequestNumber(requestNumber);
				loginUsersRequests.setPeriod_from(mCalDate.getDate());
			}
		} else {
			System.out.println("condition not handled " + userRequest.getAttendanceType());
		}

		if (vac!=null) {
			loginUsersRequests.setVacation(vac);
		}
//		if (!userRequest.getAttendanceType().equals(new Long(7))){
//			loginUsersRequests.setRequest_date(mCalDate.getDate());
//		}


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
		
		
		System.out.println("after validation");
		if (automaticRequestsValidation==true) {
			if (userRequest.getAttendanceType().equals(new Long(9))) {
				///////////////////////////Full day errand validations///////////////////////////////////////////////
				Map att = checkAttendance(mCalDate.getDate(), emp.getEmpCode());
				AttendanceStatus attendanceResponse = (AttendanceStatus)att.get("Response");
				System.out.println("attendance status response " + attendanceResponse);
				RequestsApprovalQuery requestQuery = new RequestsApprovalQuery();
				requestQuery.setDateFrom(userRequest.getAttendanceTime());
				requestQuery.setDateTo(userRequest.getAttendanceTime2());
				System.out.println("attendanceResponse.getSignIn() " + attendanceResponse.getSignIn());
				Map checkStartedMap = checkStartedRequests(requestQuery, emp);
				System.out.println("after checking started requests " + checkStartedMap);
				List startedRequests = (List)checkStartedMap.get("Response");
				System.out.println("after checking started requests 2" + startedRequests);

				if (attendanceResponse!=null && attendanceResponse.getSignIn()!=null && attendanceResponse.getSignIn().equals(new Boolean(true))) {
					// check attendance on this day//

					System.out.println("attendance status response " + attendanceResponse.getSignIn());

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
				System.out.println("validating non full errand requests 1 " + loginUsersRequests);
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
				System.out.println("validating non full errand requests 2");
				System.out.println("loginUsersRequests.getPeriod_from() " + loginUsersRequests.getPeriod_from());
				System.out.println("loginUsersRequests.getPeriod_to() " + loginUsersRequests.getPeriod_to());
				System.out.println("from " + from + " to " + t);
//				List requests = requestsApprovalManager.getRequestsByExactDatePeriodAndEmpCode(from, t, loginUsersRequests.getEmpCode());
				
				RequestsApprovalQuery requestQuery = new RequestsApprovalQuery();
				
				requestQuery.setDateFrom(df.format(from));
				
				requestQuery.setDateTo(df.format(t));
				Map reqs = checkStartedRequestsIncludingAttendance(requestQuery, emp);
				List requests = (List)reqs.get("Response");
				
				
				Map  attendance = requestsApprovalManager.checkAttendance(loginUsersRequests.getPeriod_from(), loginUsers.getEmpCode());
				System.out.println("Bug check#@#@validating non full errand requests " + requests.size());
				
//				Iterator itr = requests.iterator();
				
				int i=0;
				if (requests.size() >0) {
					Iterator reqItr = requests.iterator();
					while (reqItr.hasNext()) {
						LoginUsersRequests req = (LoginUsersRequests)reqItr.next();
						System.out.println(i+". request number " + req.getRequestNumber());
						System.out.println("req.getPeriod_to() " + req.getPeriod_to());
						if (req.getPeriod_to() == null) {
							System.out.println("checking if overlapping requests are sign in/out " + req.getRequest_id().getId());
							if (req.getRequest_id().getId().equals(new Long(10)) || req.getRequest_id().getId().equals(new Long(11))) {
								AttendanceStatus attendanceStatus = (AttendanceStatus)attendance.get("Response");
								
								System.out.println("attendanceStatus.getSignIn() " + attendanceStatus.getSignIn());
								System.out.println("attendanceStatus.getSignOut() " + attendanceStatus.getSignOut());
								
								if (attendanceStatus.getSignIn().booleanValue() == true && attendanceStatus.getSignOut().booleanValue() == false  && settings.getSignoutBeforePermissionErrand().equals(new Boolean(true))) {
									System.out.println("request in same interval " + req.getRequestNumber() + req.getPeriod_from());
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
										System.out.println("request in same interval " + req.getRequestNumber() + req.getPeriod_from());
										status.setCode("324");
										status.setMessage("Sign out time is after the request start time");
										status.setStatus("False");
										response.put("Status", status);
										return response;
									}
								}
							} else {
								System.out.println("request in same interval " + req.getRequestNumber() + req.getFrom_date());
								status.setCode("328");
								status.setMessage("Please finish your started requests ("+ req.getRequestNumber()+") during the same time interval specified");
								status.setStatus("False");
								response.put("Status", status);
								return response;
							}
						} else {
							if (req.getPeriod_to().compareTo(loginUsersRequests.getPeriod_from()) > 0 && req.getPeriod_from().compareTo(loginUsersRequests.getPeriod_from())<0) {
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
					System.out.println("----loginUsersRequests.getRequest_id().getId()---------"+loginUsersRequests.getRequest_id().getId());
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
			System.out.println("service implmentation ma2moreya " + loginUsersRequests);
			System.out.println("service implmentation ma2moreya " + reqType);
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
	
	requestsApprovalManager.saveObject(loginUsersRequests);
	Map output = new HashMap();
	output.put("request_number", loginUsersRequests.getRequestNumber());
	response.put("Response", output);
	
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
	LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
	System.out.println("checkStartedRequests: logged in user " + loggedInUser);
	Map response = requestsApprovalManager.checkStartedRequests(requestQuery,emp);
	System.out.println("checkStartedRequests: response " + response);
	return response;
}

public Map checkStartedRequestsIncludingAttendance(RequestsApprovalQuery requestQuery,
		Employee emp) {
	LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
	System.out.println("checkStartedRequests: logged in user " + loggedInUser);
	Map response = requestsApprovalManager.checkStartedRequestsIncludingAttendance(requestQuery, emp);
	System.out.println("checkStartedRequests: response " + response);
	return response;
}

public List getEmpReqTypeAcc(Employee emp,String requestType) {
	return requestsApprovalManager.getEmpReqTypeAcc(emp, requestType);
}

public Map getRequestsForApproval(RequestsApprovalQuery approvalQuery, List empReqTypeAccs,Employee emp) {
	LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
	return requestsApprovalManager.getRequestsForApproval(approvalQuery.getRequestNumber(), approvalQuery.getEmp_code(), 
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
//			loginUser.setApproved(new Long(99));
			return requestsApprovalManager.approvalsAccessLevels(requestApproval, loginUser, emp);
		} else if (requestApproval.getApprove().equals("1")) {
//			loginUser.setApproved(new Long(1));
			return requestsApprovalManager.approvalsAccessLevels(requestApproval, loginUser, emp);
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
			System.out.println(e.getMessage());
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
		System.out.println("object " + obj);
		LoginUsersRequests loginUser = (LoginUsersRequests)obj;
		Date from_date = loginUser.getFrom_date();
		if (from_date == null) {
			from_date = loginUser.getPeriod_from();
		}
		System.out.println("vacation " + loginUser.getVacation());
		System.out.println("requestApproval.getEmpCode() " + loginUser.getEmpCode());
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
	System.out.println("vac types");
//	List vacations = requestsApprovalManager.getObjects(Vacation.class);
	Map response = new HashMap();
	List vacations = new ArrayList();
	
	Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class, new Long(1));
	Boolean specialVacExcep = settings.getSpecialVacExcep();
	
	if (requestType.equals(new Long(1))) {//special vacations
		vacations = requestsApprovalManager.getObjectsByParameter(Vacation.class, "type", "B");
		for (int i = 0; i < vacations.size(); i++) {
			Vacation vac= (Vacation) vacations.get(i);
			System.out.println("------vacation--before-==="+vac.getVacation());
			if(vac.getVacation().equals("999")){
//				model.put("errand", vac);
				vacations.remove(vac);
			}
			if (specialVacExcep == true){//Lotus
				if(vac.getVacation().equals("008")){
					vacations.remove(vac);
				}
			}
//			System.out.println("------vacation- after--==="+vac.getVacation());
		}
		response.put("Response", vacations);
	} else if (requestType.equals(new Long(2))) {//periodic vacations
		vacations=requestsApprovalManager.getObjectsByParameter(Vacation.class,"type","A");
		response.put("Response", vacations);
	}
	
	response.put("Response", vacations);
	System.out.println("vac types"+vacations.size());
	RestStatus status = new RestStatus();
	status.setCode("200");
	status.setMessage("Request Inserted Successfully");
	status.setStatus("true");
	response.put("Status", status);
	System.out.println("vac types finished");
	return response ;
}

public Map getSettings() {
	Map response = new HashMap();
	Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class, new Long(1));
	System.out.println("settings " + settings);
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
	
	Map map = qry.search(emp.getEmpCode(),emp.getEmpName(),"login_users","empCode","name","","1","1",level,emp.getPageNumber(),emp.getPageSize(),"");	
	Map response = new HashMap();
	response.put("Response", map);
	RestStatus status = new RestStatus();
	status.setCode("200");
	status.setMessage("Successfull Request");
	status.setStatus("true");
	response.put("Status", status);
	return response ;
}

public Map getAttendanceVacationReport(RequestsApprovalQuery requestApproval) {
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
		System.out.println(">>>>>>>>>>>>>fromDateString "+ dateFrom);
		mCalDate.setDateTimeString(dateFrom,new Boolean(true));
		fromDate = mCalDate.getDate();
		System.out.println(">>>>>>>>>>>>>fromDate "+ fromDate);
		System.out.println(">>>>>>>>>>>>>toDateString "+ dateTo);
		mCalDate.setDateTimeString(dateTo,new Boolean(false));
		toDate= mCalDate.getDate();
		
		// VIP
		List totalObjects= new ArrayList();
		
//		totalObjects=requestsApprovalManager.getTimeAttend(empCode, fromDate, toDate);
		totalObjects=requestsApprovalManager.getTimeAttendAll(empCode, fromDate, toDate,null);
		
		objects=(List) totalObjects.get(0);
		
//		//Lehaa/////////////////////////////////////////////////
//		if (tAttRepWithHrsMin == true) {
			
			String totalSum=(String) totalObjects.get(1);
			String [] totalValues=totalSum.split(",");
			System.out.println("totalMins=== "+totalValues[0]+" && totalHrs=== "+totalValues[1]);
			String totalMins=totalValues[0];
			String totalHrs=totalValues[1];
			Long hrs=new Long(0), mins=new Long(0);
			hrs= Long.parseLong(totalHrs);
			mins=Long.parseLong(totalMins);
			if(mins>60){
				hrs+=mins/60;
				mins=mins%60;
			} 
			
			System.out.println("sent mins=== "+mins+" && sent hrs=== "+hrs);
			
			response.put("TotalMins", mins);
			response.put("TotalHrs", hrs);
//		} 
		//////////////////////////////////////////////////////////
		
		System.out.println("-------objects- size--"+objects.size());
		for (int i = 0; i < objects.size(); i++) {
			TimeAttend ob= (TimeAttend) objects.get(i);
			
			// mCalDate.setDateString(ob.getDay());
			DateFormat df=new SimpleDateFormat("dd/mm/yyyy");
			
			Date day;
			try {
				day = df.parse(ob.getDay());
				System.out.println("-------day---"+day);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("-------objects---"+ob.getDay()+"-------getTimeIn---"+ob.getTimeIn()+"-------getTimeOut---"+ob.getTimeOut());
		}
		response.put("Attendance", objects);
		// VIP
		
		
		days=requestsApprovalManager.getVacations( empCode, new Long(2), fromDate,toDate);
		System.out.println("-----days 001 ---"+days.size());
		response.put("Vacations", days);
		
//		days=requestsApprovalManager.getVacations(emp.getEmpCode(), new Long(2), "002", fromDate,toDate);
//		System.out.println("-----days 002 ---"+days.size());
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

public Map getAttendanceReport(RequestsApprovalQuery requestApproval, Employee emp) {
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
		System.out.println(">>>>>>>>>>>>>fromDateString "+ dateFrom);
		mCalDate.setDateTimeString(dateFrom,new Boolean(true));
		fromDate = mCalDate.getDate();
		System.out.println(">>>>>>>>>>>>>fromDate "+ fromDate);
		System.out.println(">>>>>>>>>>>>>toDateString "+ dateTo);
		mCalDate.setDateTimeString(dateTo,new Boolean(false));
		toDate= mCalDate.getDate();
		
		// VIP
		List totalObjects= new ArrayList();
		System.out.println("emp code " + empCode);
		System.out.println("codeFrom " + codeFrom + " codeTo " + codeTo);
		if ((empCode== null || empCode.isEmpty()) && (codeFrom == null || codeFrom.isEmpty()) && (codeTo == null || codeTo.isEmpty())) {
//			empReqTypeAccs = requestsApprovalManager.getEmpReqTypeAccEmpCode(emp, null);
			
			LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
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
//			System.out.println("access levels size " + tempLevels.size());

			List objectss = requestsApprovalManager.getObjectsByParameter(AccessLevels.class, "emp_id", loggedInUser);
			AccessLevels lev = null;
			System.out.println("objectss size " + objectss.size());
//			List levs = new ArrayList();
			List tempLevels = new ArrayList();
			
			Iterator itrs = objectss.iterator();

			while (itrs.hasNext()) {
				Object obj = itrs.next();
				System.out.println("obj " + obj);
				if (obj!= null) {
					lev = (AccessLevels)obj;
				}
				
				if(lev!=null) {
					tempLevels.addAll(requestsApprovalDAO.getAccessLevelsBetweenCodes(lev.getLevel_id(),codeFrom,codeTo));
				}
				System.out.println("access levels size " + tempLevels.size());
			}
			
			String empArray = "";
			
			Iterator empItr = tempLevels.iterator();
			int count = 0;
			while(empItr.hasNext()) {
				EmpReqTypeAcc empReq = ((EmpReqTypeAcc)(empItr.next()));
//				System.out.println("empReq " + empReq);
				if (count==0) {
//					empArray = empReq.getEmp_id().getEmpCode();
					empArray =  "'" + empReq.getEmp_id().getEmpCode() +  "'";
				} else {
//					empArray += "," + empReq.getEmp_id().getEmpCode();
					if (!empArray.contains("'"+empReq.getEmp_id().getEmpCode()+"'")) {
						empArray += ",'" + empReq.getEmp_id().getEmpCode() + "'";
					}
				}
				count++;
			}
			System.out.println("emp array " + empArray);
//			totalObjects=requestsApprovalManager.getTimeAttend(empArray, fromDate, toDate);
//			totalObjects=requestsApprovalManager.getTimeAttendFromView(empArray, fromDate, toDate);
			if (empArray == null || empArray.isEmpty()) {
				empArray = "'" + emp.getEmpCode() +  "'";
			}
			totalObjects=requestsApprovalManager.getTimeAttendAll(empArray, fromDate, toDate,statusId);
			
		} else if (codeFrom!= null && !codeFrom.isEmpty() && codeTo!= null && !codeTo.isEmpty()) {
			String empArray = "";
			LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
//			List tempLevels = (List)requestsApprovalDAO.getAccessLevelsBetweenCodes(loggedInUser,codeFrom,codeTo);
			
			List objectss = requestsApprovalManager.getObjectsByParameter(AccessLevels.class, "emp_id", loggedInUser);
			AccessLevels lev = null;
			System.out.println("objectss size " + objectss.size());
//			List levs = new ArrayList();
			List tempLevels = new ArrayList();
			
			Iterator itrs = objectss.iterator();

			while (itrs.hasNext()) {
				Object obj = itrs.next();
				System.out.println("obj " + obj);
				if (obj!= null) {
					lev = (AccessLevels)obj;
				}
				
				if(lev!=null) {
					tempLevels.addAll(requestsApprovalDAO.getAccessLevelsBetweenCodes(lev.getLevel_id(),codeFrom,codeTo));
				}
				System.out.println("access levels size " + tempLevels.size());
			}
			Iterator empItr = tempLevels.iterator();
			int count = 0;
			while(empItr.hasNext()) {
				EmpReqTypeAcc empReq = ((EmpReqTypeAcc)(empItr.next()));
//				System.out.println("empReq " + empReq);
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
				totalObjects=requestsApprovalManager.getTimeAttendAll(empArray, fromDate, toDate,statusId);
			}
		}
		else {
//			totalObjects=requestsApprovalManager.getTimeAttend(empCode, fromDate, toDate);
//			totalObjects=requestsApprovalManager.getTimeAttendFromView(empCode, fromDate, toDate);
			String empArray = "";
			LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
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
			System.out.println("objectss size " + objectss.size());
//			List levs = new ArrayList();
			List tempLevels = new ArrayList();
			
			Iterator itrs = objectss.iterator();

			while (itrs.hasNext()) {
				Object obj = itrs.next();
				System.out.println("obj " + obj);
				if (obj!= null) {
					lev = (AccessLevels)obj;
				}
				
				if(lev!=null) {
					tempLevels.addAll(requestsApprovalDAO.getAccessLevelsBetweenCodes(lev.getLevel_id(),empCode,empCode));
				}
				System.out.println("access levels size " + tempLevels.size());
			}
			
			System.out.println("access levels size " + tempLevels.size());
			
			Iterator empItr = tempLevels.iterator();
			int count = 0;
			while(empItr.hasNext()) {
				EmpReqTypeAcc empReq = ((EmpReqTypeAcc)(empItr.next()));
//				System.out.println("empReq " + empReq);
				if (count==0) {
//					empArray = empReq.getEmp_id().getEmpCode();
					empArray =  "'" + empReq.getEmp_id().getEmpCode() +  "'";
				} else {
//					empArray += "," + empReq.getEmp_id().getEmpCode();
					empArray += ",'" + empReq.getEmp_id().getEmpCode() + "'";
				}
				count++;
			}
			if (empArray == null || empArray.isEmpty()) {
//				empArray = "'" + emp.getEmpCode() +  "'";
			} else {
				totalObjects=requestsApprovalManager.getTimeAttendAll(empArray, fromDate, toDate,statusId);
			}
		}
		if(!totalObjects.isEmpty()) {
			objects=(List) totalObjects.get(0);
		}
		
		System.out.println("-------objects- size--"+objects.size());
		for (int i = 0; i < objects.size(); i++) {
			TimeAttend ob= (TimeAttend) objects.get(i);
			
			// mCalDate.setDateString(ob.getDay());
			DateFormat df=new SimpleDateFormat("dd/mm/yyyy");
			
			Date day;
			try {
				day = df.parse(ob.getDay());
//				System.out.println("-------day---"+day);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			System.out.println("-------objects---"+ob.getDay()+"-------getTimeIn---"+ob.getTimeIn()+"-------getTimeOut---"+ob.getTimeOut());
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
	
	System.out.println("profile pic string \n"+userWrapper.getProfilePic());
	if (userWrapper.getProfilePic()!=null  && !userWrapper.getProfilePic().isEmpty()) {
		byte[] profilePicBytes = Base64.decode(userWrapper.getProfilePic());
		System.out.println("profile pic bytes " + profilePicBytes);
		employee.setProfilePic(profilePicBytes);
		employee.setProfilePicName("pic_"+employee.getEmpCode());
	}
	if (userWrapper.getMobileNo()!=null && !userWrapper.getMobileNo().isEmpty()) {
		employee.setTel(userWrapper.getMobileNo());
	}
	if (userWrapper.getEmail()!=null && !userWrapper.getEmail().isEmpty()) {
		employee.setEmail(userWrapper.getEmail());
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

	e.setRequiredAndroidVersion(requiredVersion);

	response.put("Response", e);
	
	results.put("Results", response);
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
