package com._4s_.restServices.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.requestsApproval.dao.RequestsApprovalDAO;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.model.Vacation;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.restServices.json.AttendanceRequest;
import com._4s_.security.dao.MySecurityDAO;
import com._4s_.security.model.User;
//import com.javacodegeeks.gwtspring.server.utils.NotificationsProducer;
//import com.javacodegeeks.gwtspring.shared.dto.EmployeeDTO;
//import com.javacodegeeks.gwtspring.shared.services.EmployeeService;


@Service("requestsService")
@Transactional
public class RequestsServiceImpl implements RequestsService {
 
 @Autowired
 private RequestsApprovalDAO requestsApprovalDAO;
 
 @Autowired
 private RequestsApprovalManager requestsApprovalManager;
 
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

 public User login(String username, String password) {
	 System.out.println("security dao " + securityDao);
	 return securityDao.login(username,password);
 }


public LoginUsersRequests signInOut(AttendanceRequest userRequest) {
	// TODO Auto-generated method stub
	DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date newDate = null;
	try {
		newDate = df.parse(userRequest.getAttendanceTime());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
	}
	MultiCalendarDate mCalDate = new MultiCalendarDate();
	mCalDate.setDate(newDate);

	Employee emp = (Employee)requestsApprovalDAO.getObject(Employee.class, userRequest.getEmployeeId());

	LoginUsers loginUsers=(LoginUsers) requestsApprovalDAO.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
	if(loginUsers!=null){
		System.out.println("-----login.code----"+loginUsers.getEmpCode());
	}
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
}



public LoginUsersRequests userRequest(AttendanceRequest userRequest) {
	// TODO Auto-generated method stub
	
	DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date newDate = null;
	try {
		newDate = df.parse(userRequest.getAttendanceTime());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
	}
	MultiCalendarDate mCalDate = new MultiCalendarDate();
	mCalDate.setDate(newDate);

	Employee emp = (Employee)requestsApprovalDAO.getObject(Employee.class, userRequest.getEmployeeId());

	LoginUsers loginUsers=(LoginUsers) requestsApprovalDAO.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
	if(loginUsers!=null){
		System.out.println("-----login.code----"+loginUsers.getEmpCode());
	}
	LoginUsersRequests loginUsersRequests = new LoginUsersRequests();

	loginUsersRequests.setLogin_user(loginUsers);
	loginUsersRequests.setEmpCode(loginUsers.getEmpCode());

	// request number
	if (loginUsersRequests.getId() == null){
		String requestNumber="";
		requestNumber=requestsApprovalManager.CreateRequestNumber();
		loginUsersRequests.setRequestNumber(requestNumber);
	}
	
	loginUsersRequests.setRequest_date(mCalDate.getDate());
	loginUsersRequests.setPeriod_from(mCalDate.getDate());
	
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
	

	//1 agaza khasa 2 agaza dawreya 3 tasree7 4 ma2moreya yom kamel
	RequestTypes reqType = null;
	if (userRequest.getAttendanceType().equals(new Long(1))) {
		reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(1));
	} else if (userRequest.getAttendanceType().equals(new Long(2))) {
		reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(2));
	} else if (userRequest.getAttendanceType().equals(new Long(3))) {
		reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(3));
	} else if (userRequest.getAttendanceType().equals(new Long(4))) {
		reqType = (RequestTypes)requestsApprovalDAO.getObject(RequestTypes.class, new Long(4));
	}
	loginUsersRequests.setRequest_id(reqType);

	loginUsersRequests.setLatitude(userRequest.getLatitude());
	loginUsersRequests.setLongitude(userRequest.getLongitude());
	
	return loginUsersRequests ;

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
// 
// @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
// public void deleteEmployee(long employeeId) throws Exception {
// 
//  EmployeeDTO employeeDTO = employeeDAO.findById(employeeId);
// 
//  if(employeeDTO != null)
//   employeeDAO.remove(employeeDTO);
// 
// }
// 
// @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
// public void saveOrUpdateEmployee(long employeeId, String name, String surname, String jobDescription) throws Exception {
// 
//  EmployeeDTO employeeDTO = new EmployeeDTO(employeeId, name,surname, jobDescription);
// 
//  employeeDAO.merge(employeeDTO);
// 
//  notificationsProducer.sendNotification("Save Or Update Employee with values : \nID : " + employeeId + "\nName : " + name + "\nSurname : " + surname + "\nJob description : " + jobDescription);
// 
// }
 
}
