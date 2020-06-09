package com._4s_.restServices.endpoint;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.restServices.json.AttendanceRequest;
import com._4s_.restServices.json.ClientWrapper;
import com._4s_.restServices.json.EmployeeResponse;
import com._4s_.restServices.json.EmployeeWrapper;
import com._4s_.restServices.json.ImeiWrapper;
import com._4s_.restServices.json.RequestApproval;
import com._4s_.restServices.json.RequestTypeWrapper;
import com._4s_.restServices.json.RequestsApprovalQuery;
import com._4s_.restServices.json.RestStatus;
import com._4s_.restServices.service.RequestsService;
import com._4s_.security.model.Imei;
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
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody
	public Map login(ImeiWrapper imei) {
		Map response = new HashMap();
		try {
			System.out.println("trying to login");
			User user = requestsService.login();
			System.out.println("after login");
			
			Map settingsMap = requestsService.getSettings();
			Map settings = (Map)settingsMap.get("Response");
			Integer requiredVersion = (Integer)settings.get("requiredAndroidVersion");
			System.out.println("settings " + settings);
			System.out.println("version required " + requiredVersion);
			
			if (user == null) {
				System.out.println("Not authenticated");
				return null;
			} else {
				if (imei!= null && imei.getImei()!= null && !imei.getImei().isEmpty()) {
					System.out.println("Authenticated with employee id is " + user.getEmployee().getId());
					Boolean checked = requestsService.checkImei(imei.getImei(),user);
					if(checked.equals(true)) {
						UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();

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

						e.setRequiredAndroidVersion(requiredVersion);

						response.put("Response", e);
						return response;
					} else {
						System.out.println("user's persisted imei doesn't match the input imei");
						List userImeis = requestsService.getUsersImei(user);
						if (userImeis.isEmpty()) {
							System.out.println("user didn't register imei yet");
							User ifuserexist = requestsService.getImeiUsers(imei.getImei());
							if(ifuserexist == null) {
								Imei im = new Imei();
								im.setUsers(user);
								im.setImei(imei.getImei());
								requestsService.saveImei(im);
								System.out.println("new imei id " + im.getId());

								RestStatus status = new RestStatus();
								status.setStatus("true");
								status.setCode("200");
								status.setMessage("Successful Authorization. IMEI Initialized");
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

								e.setRequiredAndroidVersion(requiredVersion);

								response.put("Response", e);

								return response;
							} else {
								RestStatus status = new RestStatus();
								status.setStatus("False");
								status.setCode("341");
								status.setMessage("IMEI is already registered for another user");
								response.put("Status", status);
								return response;
							}
						} else {
							RestStatus status = new RestStatus();
							status.setStatus("False");
							status.setCode("340");
							status.setMessage("Successful Authorization with wrong IMEI");
							response.put("Status", status);
							return response;
						}
					}
				} else {
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

					e.setRequiredAndroidVersion(requiredVersion);

					response.put("Response", e);
					return response;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception " + e.getClass());
			System.out.println(e.getMessage());
			StackTraceElement[] elements = e.getStackTrace();
			for(int i = 0; i<elements.length; i++) {
				System.out.println(elements[i]);
			}
			return null;
		}
	}

	//3. Permission Start
//	4. Permission End
//	5. Errand Start
//	6. Errand End
	@RequestMapping(value="/userRequest", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody
	public Map userRequest(AttendanceRequest userRequest)
	{
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDet = (UserDetails)token.getPrincipal();
		User user = requestsService.getUser(userDet.getUsername());
		try {
			Map response = requestsService.userRequest(userRequest,user.getEmployee().getId());
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/checkAttendance", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map checkAttendance()
	{
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDet = (UserDetails)token.getPrincipal();
		User user = requestsService.getUser(userDet.getUsername());
		try {
			Map response = requestsService.checkAttendance(Calendar.getInstance().getTime(),user.getEmployee().getEmpCode());
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/checkStartedRequests", method=RequestMethod.POST, 
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody
	public Map checkStartedRequests(RequestsApprovalQuery requestQuery)
	{
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDet = (UserDetails)token.getPrincipal();
		User user = requestsService.getUser(userDet.getUsername());
		Map response = requestsService.checkStartedRequests(requestQuery,user.getEmployee());
		return response;
	}

	@Transactional
	@RequestMapping(value="/signInOut", method=RequestMethod.POST, 
	consumes=MediaType.APPLICATION_FORM_URLENCODED,produces=MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map signInOut( AttendanceRequest userRequest)
	{
		System.out.println("Controller sign in/out");
		Map response = new HashMap();
		RestStatus restStatus = new RestStatus();
		Settings settings = (Settings)requestsService.getRequestsApprovalManager().getObject(Settings.class, new Long(1));
		
		System.out.println("userRequest.getAttendanceType() "+userRequest.getAttendanceType());
		System.out.println("userRequest.getAttendanceTime() " + userRequest.getAttendanceTime());
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
			System.out.println("will sign in");
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
			System.out.println("token " + token);
			UserDetails userDet = (UserDetails)token.getPrincipal();
			System.out.println("userdet" + userDet);
			User user = requestsService.getUser(userDet.getUsername());
			System.out.println("user " + user);
			response = requestsService.signInOut(userRequest,user.getEmployee().getId());
			
			return response;
		}
	}


	@RequestMapping(value="/requestsForApproval", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody
	public Map requestsForApproval( RequestsApprovalQuery approvalQuery)
	{
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDet = (UserDetails)token.getPrincipal();
		User user = requestsService.getUser(userDet.getUsername());
		Map response = new HashMap();		
		RestStatus restStatus = new RestStatus();
		RestStatus status = new RestStatus();
		System.out.println(" sort " + approvalQuery.getSort());
		System.out.println(!approvalQuery.getSort().equalsIgnoreCase("asc"));
		System.out.println(!approvalQuery.getSort().equalsIgnoreCase("desc"));
		System.out.println((!approvalQuery.getSort().equalsIgnoreCase("asc") && !approvalQuery.getSort().equalsIgnoreCase("desc")));
		if (approvalQuery.getSort()==null || approvalQuery.getSort().isEmpty() 
				|| (!approvalQuery.getSort().equalsIgnoreCase("asc") && !approvalQuery.getSort().equalsIgnoreCase("desc"))) {
			System.out.println("sort value error");
			System.out.println("sort value is wrong " + approvalQuery.getSort()==null || approvalQuery.getSort().isEmpty() 
				|| (!approvalQuery.getSort().equalsIgnoreCase("asc") && !approvalQuery.getSort().equalsIgnoreCase("desc")));
			status.setCode("303");
			status.setMessage("Null/Empty/Wrong Input Parameter");
			status.setStatus("false");
			response.put("Status", status);
			return response;
		}
		if (approvalQuery.getPageSize()!=0) {
			List empReqTypeAccs = requestsService.getEmpReqTypeAcc(user.getEmployee(), approvalQuery.getRequestType());
			response =	requestsService.getRequestsForApproval(approvalQuery,empReqTypeAccs,user.getEmployee());	

			List resp = (List)response.get("results");
			if (resp == null || resp.size()==0) {
				restStatus.setStatus("true");
				restStatus.setCode("200");
				restStatus.setMessage("Empty List");
			} else {
				restStatus.setStatus("true");
				restStatus.setCode("200");
				restStatus.setMessage("Successful Transaction");
			}

			response.put("Status", restStatus);
		} else {
			restStatus.setStatus("false");
			restStatus.setCode("310");
			restStatus.setMessage("Page Size Value Can't be ZERO");
			response.put("Status", restStatus);
		}
		return response;
	}
	
	
	@RequestMapping(value="/submittedRequests", method=RequestMethod.POST, 
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody
	public Map submittedRequests(RequestsApprovalQuery approvalQuery)
	{
		Map response = new HashMap();		
		RestStatus restStatus = new RestStatus();

		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDet = (UserDetails)token.getPrincipal();
		User user = requestsService.getUser(userDet.getUsername());
		approvalQuery.setEmp_code(user.getEmployee().getEmpCode());
		approvalQuery.setCodeFrom(null);
		approvalQuery.setCodeTo(null);
		RestStatus status = new RestStatus();
		System.out.println(" sort " + approvalQuery.getSort());
		System.out.println(!approvalQuery.getSort().equalsIgnoreCase("asc"));
		System.out.println(!approvalQuery.getSort().equalsIgnoreCase("desc"));
		System.out.println((!approvalQuery.getSort().equalsIgnoreCase("asc") && !approvalQuery.getSort().equalsIgnoreCase("desc")));
		if (approvalQuery.getSort()==null || approvalQuery.getSort().isEmpty() 
				|| (!approvalQuery.getSort().equalsIgnoreCase("asc") && !approvalQuery.getSort().equalsIgnoreCase("desc"))) {
			System.out.println("sort value error");
			System.out.println("sort value is wrong " + approvalQuery.getSort()==null || approvalQuery.getSort().isEmpty() 
				|| (!approvalQuery.getSort().equalsIgnoreCase("asc") && !approvalQuery.getSort().equalsIgnoreCase("desc")));
			status.setCode("303");
			status.setMessage("Null/Empty/Wrong Input Parameter");
			status.setStatus("false");
			response.put("Status", status);
			return response;
		}
		if (approvalQuery.getPageSize()!=0) {
			response =	requestsService.getRequestsForApproval(approvalQuery,null,user.getEmployee());
			List resp = (List)response.get("results");
			if (resp == null || resp.size()==0) {
				restStatus.setStatus("true");
				restStatus.setCode("200");
				restStatus.setMessage("Empty List");
			} else {
				restStatus.setStatus("true");
				restStatus.setCode("200");
				restStatus.setMessage("Successful Transaction");
			}
		} else {
			restStatus.setStatus("false");
			restStatus.setCode("310");
			restStatus.setMessage("Page Size Value Can't be ZERO");
		}
		response.put("Status", restStatus);
		return response;
	}


	@RequestMapping(value="/approveRequest", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody
	public Map approveRequest (RequestApproval requestApproval) {
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
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody
	public Map vacInfo (RequestApproval requestApproval) {
		return requestsService.getVacInfo(requestApproval);
	}
	
	@RequestMapping(value="/vacTypes", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody
	public Map vacTypes(RequestTypeWrapper requestType) {
		Map m =  requestsService.getVacTypes(requestType.getRequestType());
		System.out.println("test vac types");
		return m;
	}
	
	@RequestMapping(value="/getSettings", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map getSettings() {
		Map m =  requestsService.getSettings();
		System.out.println("test getSettings");
		return m;
	}
	
	@RequestMapping(value="/getPortNo", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody
	public Map getPortNo(ClientWrapper client) {
		Map m =  requestsService.getPortNo(client.getClientName());
		System.out.println("test getPortNo");
		return m;
	}
	
	@RequestMapping(value="/searchEmployees" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map searchEmployees(EmployeeWrapper emp) {
		Map m = requestsService.searchEmployees(emp);
		return m;
	}
	
	
	@RequestMapping(value="/getAttendanceVacationReport" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map getAttendanceVacationReport(RequestApproval requestApproval) {
		System.out.println("getAttendanceVacationReport ");
		Map m = requestsService.getAttendanceVacationReport(requestApproval);
		return m;
	}
	
}