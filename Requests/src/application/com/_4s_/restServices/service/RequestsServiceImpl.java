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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
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
import com._4s_.restServices.json.AttendanceRequest;
import com._4s_.restServices.json.RequestApproval;
import com._4s_.restServices.json.RequestsApprovalQuery;
import com._4s_.restServices.json.RestStatus;
import com._4s_.restServices.model.AttendanceStatus;
import com._4s_.security.dao.MySecurityDAO;
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

@Autowired
 private MySecurityDAO securityDao;

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


public LoginUsersRequests signInOut(AttendanceRequest userRequest,Long empId) {
	// TODO Auto-generated method stub
	Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class, new Long(1));

	DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	Date newDate = null;
//	try {
//		System.out.println("userRequest.getAttendanceTime() " + userRequest.getAttendanceTime());
//		newDate = df.parse(userRequest.getAttendanceTime());
		newDate = Calendar.getInstance().getTime();
		System.out.println("parsed date " + newDate);
//	} catch (ParseException e) {
		// TODO Auto-generated catch block
//		System.out.println(e.getMessage());
//	}
	MultiCalendarDate mCalDate = new MultiCalendarDate();
	mCalDate.setDate(newDate);
	
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

		requestsApprovalManager.saveObject(loginUsersRequests);

		return loginUsersRequests;
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
		return null;
	}
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
	
	if (userRequest.getAttendanceType()==null || userRequest.getAttendanceTime() == null 
			||(userRequest.getAttendanceType().equals(new Long(9)) && (userRequest.getAttendanceTime2() == null ||userRequest.getAttendanceTime2() == null))
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
		}  else if (userRequest.getAttendanceType().equals(new Long(3)) || userRequest.getAttendanceType().equals(new Long(4))) {//Permission
//			System.out.println("3 4 reqType " + reqType.getId());
			reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(3));
			System.out.println(" reqType " + reqType.getId());
		} else if (userRequest.getAttendanceType().equals(new Long(5)) ||userRequest.getAttendanceType().equals(new Long(6))) {//Full Day errand
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
			reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(1));
			vac = (Vacation)requestsApprovalManager.getObjectByParameter(Vacation.class,"vacation", "999");
			System.out.println(" reqType " + reqType.getId());
			
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
				status.setMessage("Another request is made already on the specified date, full day errand is not allowed.");
				status.setStatus("False");
				response.put("Status", status);
				return response;
			}
			////////////////////////////////////////////////////////////////////////////////////////////////////
			
		} else {
			System.out.println("userRequest.getAttendanceType() " + userRequest.getAttendanceType().getClass());
		}
		if (userRequest.getAttendanceType().equals(new Long(3)) || userRequest.getAttendanceType().equals(new Long(4))) {
			if (userRequest.getPermissionEffect() == null || userRequest.getPermissionType() == null) {
				status.setCode("305");
				status.setMessage("One or more of Permission Parameters is null");
				status.setStatus("False");
				response.put("Status", status);
				return response;
			}
		}
//		System.out.println("mCalDate " + mCalDate);
		System.out.println("mCalDate.getDate() " + mCalDate.getDate());
		if (userRequest.getAttendanceType().equals(new Long(4)) || userRequest.getAttendanceType().equals(new Long(6))
				||userRequest.getAttendanceType().equals(new Long(3)) || userRequest.getAttendanceType().equals(new Long(5))) {//Permission End || //Full Day Permission End
			
			
			if (userRequest.getAttendanceType().equals(new Long(4)) || userRequest.getAttendanceType().equals(new Long(6))) {
				RequestsApprovalQuery requestQueryending = new RequestsApprovalQuery();
				
				//1 permission 2 errands
				if (userRequest.getAttendanceType().equals(new Long(4))) {//permission start
					requestQueryending.setRequestType("1");
				} else if (userRequest.getAttendanceType().equals(new Long(6))) {//errand start
					requestQueryending.setRequestType("2");
				} else {
					System.out.println("condition not handled 3 " + userRequest.getAttendanceType());
				}
				requestQueryending.setDateFrom(userRequest.getAttendanceTime());
				requestQueryending.setDateTo(userRequest.getAttendanceTime());
				List requests = (List)(requestsApprovalManager.checkStartedRequests(requestQueryending, emp)).get("Response");
				Iterator itrrequests = requests.iterator();
				while(itrrequests.hasNext()) {
					System.out.println("ending request other requests "+((LoginUsersRequests)itrrequests.next()).getRequestNumber());
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
				
				//1 permission 2 errands
				if (userRequest.getAttendanceType().equals(new Long(3))) {//permission start
					requestQuery.setRequestType("1");
				} else if (userRequest.getAttendanceType().equals(new Long(5))) {//errand start
					requestQuery.setRequestType("2");
				} else {
					System.out.println("condition not handled 2 " + userRequest.getAttendanceType());
				}
				
				Map startedRequests = checkStartedRequests(requestQuery, emp);
				List startedRequestsResponse = (List)startedRequests.get("Response");
				System.out.println("permission/errand start , size =" + startedRequests.size());
				Iterator itr = startedRequestsResponse.iterator();
				while(itr.hasNext()) {
					System.out.println(itr.next());
				}
				if (startedRequestsResponse.size() == 1 && ((LoginUsersRequests)startedRequestsResponse.get(0)).getTo_date()==null) {
					status.setCode("311");
					status.setMessage("A request is already started that hasn't been ended yet.");
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
					status.setCode("314");
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


		Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class, new Long(1));
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
	if(userRequest.getPermissionType()!=null){
		loginUsersRequests.setLeave_type(userRequest.getPermissionType());
	}
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
	System.out.println("logged in user " + loggedInUser);
	Map response = requestsApprovalManager.checkStartedRequests(requestQuery,emp);
	System.out.println("response " + response);
	return response;
}

public List getEmpReqTypeAcc(Employee emp,String requestType) {
	return requestsApprovalManager.getEmpReqTypeAcc(emp, requestType);
}

public Map getRequestsForApproval(RequestsApprovalQuery approvalQuery, List empReqTypeAccs,Employee emp) {
	LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
	return requestsApprovalManager.getRequestsForApproval(approvalQuery.getRequestNumber(), approvalQuery.getEmp_code(), 
			approvalQuery.getDateFrom(), approvalQuery.getDateTo(), approvalQuery.getExactDateFrom(), approvalQuery.getExactDateTo(), approvalQuery.getRequestType(),
			approvalQuery.getCodeFrom(), approvalQuery.getCodeTo(), approvalQuery.getStatusId(),approvalQuery.getSort(),loggedInUser,empReqTypeAccs,false, approvalQuery.getPageNumber(), approvalQuery.getPageSize());	
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
//			log.debug("------vacation- after--==="+vac.getVacation());
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
	Settings settings = (Settings)requestsApprovalManager.getObject(Settings.class, new Long(1));
	Map temp = new HashMap();
	temp.put("companyName", settings.getCompany().getDescription());
	temp.put("locationAccuracy",settings.getLocationAccuracy());
	temp.put("isLocationAccuracyEnabled", settings.getIsLocationAccuracyEnabled());
	temp.put("showAddressOnForm", settings.getShowAddressOnForm());
	temp.put("showRequestsOnCalendar", settings.getShowRequestsOnCalendar());
	temp.put("errandTimeFromSystem", settings.getErrandTimeFromSystem());
	temp.put("obligateNotes", settings.getObligateNotes());
	temp.put("showVacationBalance", settings.getAnnualVacBalDaysEnabled());
	
	
	response.put("Response", temp);
	RestStatus status = new RestStatus();
	status.setCode("200");
	status.setMessage("Success");
	status.setStatus("true");
	response.put("Status", status);
	
	
	return response;
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
