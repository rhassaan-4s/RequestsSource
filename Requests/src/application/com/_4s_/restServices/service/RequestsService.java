package com._4s_.restServices.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com._4s_.common.model.Employee;
import com._4s_.common.service.BaseManager;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.restServices.json.AttendanceRequest;
import com._4s_.restServices.json.RequestApproval;
import com._4s_.restServices.json.RequestsApprovalQuery;
import com._4s_.restServices.json.RestStatus;
import com._4s_.security.model.User;


@Transactional
public interface RequestsService {// extends BaseManager {

	public User login();
	
	public RequestsApprovalManager getRequestsApprovalManager();

	public Map signInOut(AttendanceRequest userRequest, Long empId);

	public Map userRequest(AttendanceRequest userRequest, Long empId);
	
	public User getUser(String username);

	public Map getRequestsForApproval(RequestsApprovalQuery approvalQuery, List empReqTypeAccs, Employee emp);

	public Map approveRequest(RequestApproval requestApproval,Employee emp);
	
	public Map getVacInfo(RequestApproval requestApproval);
	
	public Map getVacTypes(Long requestType);
	
	public List getEmpReqTypeAcc(Employee emp,String requestType);

	public Map checkAttendance(Date today, String empCode);
	
	public Map checkStartedRequests(RequestsApprovalQuery requestQuery,
			Employee employee);

	public Map getSettings();
}
