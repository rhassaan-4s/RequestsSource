package com._4s_.restServices.endpoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com._4s_.common.model.Employee;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.restServices.json.AttendanceRequest;
import com._4s_.restServices.json.EmployeeResponse;
import com._4s_.restServices.json.RequestApproval;
import com._4s_.restServices.json.RequestTypeWrapper;
import com._4s_.restServices.json.RequestsApprovalQuery;
import com._4s_.restServices.json.RestStatus;
import com._4s_.restServices.service.RequestsService;
import com._4s_.restServices.service.RequestsServiceImpl;
import com._4s_.security.model.User;


//@Controller
//@RequestMapping("/requestsService")
//@Component
//@Path ("/requestsService") 
@Controller
@RequestMapping("/workflow")
public class RequestsServiceController {

	@Autowired
	RequestsService requestsService;


	@RequestMapping(value = "/login", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map login() {
		Map response = new HashMap();
		try {
			System.out.println("trying to login");
			User user = requestsService.login();
			System.out.println("after login");
			if (user == null) {
				System.out.println("Not authenticated");
				return null;
			} else {
				System.out.println("Authenticated with employee id is " + user.getEmployee().getId());

				RestStatus status = new RestStatus();
				status.setStatus("true");
				status.setCode("200");
				status.setMessage("Successful Authorization");

				response.put("Status", status);
				Employee emp = user.getEmployee();
				EmployeeResponse e = new EmployeeResponse();
				e.setAddress(emp.getAddress());
				e.setAttendanceCode(emp.getAttendanceCode());
				e.setBranch(emp.getBranch());
				e.setCity(emp.getCity());
				e.setDepartment(emp.getDepartment());
				e.setEmail(emp.getEmail());
				e.setEmpCode(emp.getEmpCode());
				e.setEmployeeCode(emp.getEmployeeCode());
				e.setExt(emp.getExt());
				e.setFirstName(emp.getFirstName());
				e.setGender(emp.getGender());
				e.setId(emp.getId());
				e.setIsDepartmentManager(emp.getIsDepartmentManager());
				e.setIsManager(emp.getIsManager());
				e.setJobTitle(emp.getJobTitle());
				e.setLastName(emp.getLastName());
				e.setTel(emp.getTel());
				response.put("Response", e);
				return response;
			}
		} catch (Exception e) {
			System.out.println("Exception " + e.getClass());
			return null;
		}
	}

	//3. Permission Start
//	4. Permission End
//	5. Errand Start
//	6. Errand End
	@RequestMapping(value="/userRequest", method=RequestMethod.POST, 
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map userRequest(@RequestBody AttendanceRequest userRequest)
	{
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDet = (UserDetails)token.getPrincipal();
		User user = requestsService.getUser(userDet.getUsername());
		Map response = requestsService.userRequest(userRequest,user.getEmployee().getId());
		return response;
	}

	@Transactional
	@RequestMapping(value="/signInOut", method=RequestMethod.POST, 
	consumes=MediaType.APPLICATION_JSON,produces=MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map signInOut(@RequestBody AttendanceRequest userRequest)
	{
		System.out.println("Controller sign in/out");
		Map response = new HashMap();
		RestStatus restStatus = new RestStatus();
		if (userRequest.getAttendanceType()==null || userRequest.getAttendanceTime() == null 
				|| userRequest.getAttendanceTime().isEmpty()|| userRequest.getLatitude()==null || userRequest.getLongitude()==null){
			restStatus.setCode("303");
			restStatus.setMessage("Null/Empty Input Parameter");
			restStatus.setStatus("False");
			response.put("Status", restStatus);
			return response;
		} else {
			restStatus.setStatus("true");
			restStatus.setCode("200");
			restStatus.setMessage("Successful Transaction");
			response.put("Status", restStatus);
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDet = (UserDetails)token.getPrincipal();
			User user = requestsService.getUser(userDet.getUsername());
			LoginUsersRequests req = requestsService.signInOut(userRequest,user.getEmployee().getId());
			if (req!=null){
				response.put("Response" ,req.getId());
			} else {
				response.put("Response" ,null);
			}
			return response;
		}
	}


	@RequestMapping(value="/requestsForApproval", method=RequestMethod.POST, 
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map requestsForApproval(@RequestBody RequestsApprovalQuery approvalQuery)
	{
		Map response = new HashMap();		
		RestStatus restStatus = new RestStatus();
		response =	requestsService.getRequestsForApproval(approvalQuery);	
		
		if (approvalQuery.getRequestType()== null || approvalQuery.getRequestType().isEmpty()) {
			restStatus.setStatus("false");
			restStatus.setCode("304");
			restStatus.setMessage("Empty Search Criteria");
		} else {
		
//		response.put("Response",requests);
//		if (((List)response.get("list")).size()>0) {
			restStatus.setStatus("true");
			restStatus.setCode("200");
			restStatus.setMessage("Successful Transaction");
		} 
		
		response.put("Status", restStatus);
		return response;
	}
	
	
	@RequestMapping(value="/submittedRequests", method=RequestMethod.POST, 
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map submittedRequests(@RequestBody RequestsApprovalQuery approvalQuery)
	{
		Map response = new HashMap();		
		RestStatus restStatus = new RestStatus();
		
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDet = (UserDetails)token.getPrincipal();
		User user = requestsService.getUser(userDet.getUsername());
		approvalQuery.setEmp_code(user.getEmployee().getEmpCode());
		approvalQuery.setCodeFrom(null);
		approvalQuery.setCodeTo(null);
		
		response =	requestsService.getRequestsForApproval(approvalQuery);
		
//		response.put("Response",requests);
		if (((List)response.get("list")).size()>0) {
			restStatus.setStatus("true");
			restStatus.setCode("200");
			restStatus.setMessage("Successful Transaction");
		} 
//		else {
//			restStatus.setStatus("false");
//			restStatus.setCode("304");
//			restStatus.setMessage("Empty Search Criteria");
//		}
		response.put("Status", restStatus);
		return response;
	}


	@RequestMapping(value="/approveRequest", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map approveRequest (@RequestBody RequestApproval requestApproval) {
		Map response = new HashMap();		
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDet = (UserDetails)token.getPrincipal();
		User user = requestsService.getUser(userDet.getUsername());
		RestStatus restStatus = new RestStatus();
		if (requestApproval.getApprove()==null || requestApproval.getApprove().isEmpty()
				||requestApproval.getRequestId()==null || requestApproval.getRequestId().isEmpty()) {
			restStatus.setStatus("false");
			restStatus.setCode("303");
			restStatus.setMessage("Null/Empty input parameters");
		} else {
			return requestsService.approveRequest(requestApproval,user.getEmployee());	
		}
		response.put("Status", restStatus);
		return response;
	}
	
	@RequestMapping(value="/vacInfo", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map vacInfo (@RequestBody RequestApproval requestApproval) {
		return requestsService.getVacInfo(requestApproval);
	}
	
	@RequestMapping(value="/vacTypes", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map vacTypes(@RequestBody RequestTypeWrapper requestType) {
		Map m =  requestsService.getVacTypes(requestType.getRequestType());
		System.out.println("test vac types");
		return m;
	}
}