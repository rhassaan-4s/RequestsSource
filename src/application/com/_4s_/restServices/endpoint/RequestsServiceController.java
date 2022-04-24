package com._4s_.restServices.endpoint;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import org.dbunit.util.Base64;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.restServices.json.AttendanceRequest;
import com._4s_.restServices.json.ClientWrapper;
import com._4s_.restServices.json.EmployeeResponse;
import com._4s_.restServices.json.EmployeeWrapper;
import com._4s_.restServices.json.GroupWrapper;
import com._4s_.restServices.json.ImeiWrapper;
import com._4s_.restServices.json.PasswordWrapper;
import com._4s_.restServices.json.RequestApproval;
import com._4s_.restServices.json.RequestTypeWrapper;
import com._4s_.restServices.json.RequestsApprovalQuery;
import com._4s_.restServices.json.RestStatus;
import com._4s_.restServices.json.TimesheetActivityWrapper;
import com._4s_.restServices.json.TimesheetCostcenterWrapper;
import com._4s_.restServices.json.TimesheetPartWrapper;
import com._4s_.restServices.json.TimesheetSpecsWrapper;
import com._4s_.restServices.json.TimesheetTransDefaultWrapper;
import com._4s_.restServices.json.TimesheetTransWrapper;
import com._4s_.restServices.json.TimesheetTransactionFilters;
import com._4s_.restServices.json.UserWrapper;
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
	
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	RequestsService requestsService;


	@RequestMapping(value = "/login", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody
	public Map login(ImeiWrapper imei) {
		Map response = new HashMap();
		try {
			log.debug("trying to login");
			User user = requestsService.login();
			log.debug("after login");
			
			Map settingsMap = requestsService.getSettings();
			Map settings = (Map)settingsMap.get("Response");
			Integer requiredVersion = (Integer)settings.get("requiredAndroidVersion");
			log.debug("settings " + settings);
			log.debug("version required " + requiredVersion);
			
			if (user == null) {
				log.debug("Not authenticated");
				return null;
			} else {
				if (imei!= null && imei.getImei()!= null && !imei.getImei().isEmpty()) {
					log.debug("Authenticated with employee id is " + user.getEmployee().getId());
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
						e.setEmail(emp.getEmail());

						e.setRequiredAndroidVersion(requiredVersion);
						
						if (emp.getProfilePicName()!=null) {
							String picString = Base64.encodeBytes(emp.getProfilePic());
							e.setProfilePic(picString);
						}

						response.put("Response", e);
						return response;
					} else {
						log.debug("user's persisted imei doesn't match the input imei");
						List userImeis = requestsService.getUsersImei(user);
						if (userImeis.isEmpty()) {
							log.debug("user didn't register imei yet");
							User ifuserexist = requestsService.getImeiUsers(imei.getImei());
							if(ifuserexist == null) {
								Imei im = new Imei();
								im.setUsers(user);
								im.setImei(imei.getImei());
								requestsService.saveImei(im);
								log.debug("new imei id " + im.getId());

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
								
								if (emp.getProfilePicName()!=null) {
									String picString = Base64.encodeBytes(emp.getProfilePic());
									e.setProfilePic(picString);
								}

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
							status.setMessage("User is already registered with another device");
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
			log.debug("Exception " + e.getClass());
			log.debug(e.getMessage());
			StackTraceElement[] elements = e.getStackTrace();
			for(int i = 0; i<elements.length; i++) {
				log.debug(elements[i]);
			}
			return null;
		}
	}
	
	@RequestMapping(value="/changePassword", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody
	public Map changePassword(PasswordWrapper passwordWrapper)
	{
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDet = (UserDetails)token.getPrincipal();
		User user = requestsService.getUser(userDet.getUsername());
		try {
			Map response = requestsService.changePassword(passwordWrapper,user);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
			log.debug(e);
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

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@RequestMapping(value="/signInOut", method=RequestMethod.POST, 
	consumes=MediaType.APPLICATION_FORM_URLENCODED,produces=MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map signInOut( AttendanceRequest userRequest)
	{
		log.debug("Controller sign in/out");
		Map response = new HashMap();
		RestStatus restStatus = new RestStatus();
		Settings settings = (Settings)requestsService.getRequestsApprovalManager().getObject(Settings.class, new Long(1));
		
		log.debug("userRequest.getAttendanceType() "+userRequest.getAttendanceType());
		log.debug("userRequest.getAttendanceTime() " + userRequest.getAttendanceTime());
		if (userRequest.getAttendanceType()==null || userRequest.getLatitude()==null || userRequest.getLongitude()==null){//|| userRequest.getAttendanceTime() == null || userRequest.getAttendanceTime().isEmpty()
			restStatus.setCode("303");
			restStatus.setMessage("Null/Empty Input Parameter");
			restStatus.setStatus("False");
			response.put("Status", restStatus);
			return response;
		} else {
			restStatus.setStatus("true");
			restStatus.setCode("200");
			log.debug("will sign in");
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
			log.debug("token " + token);
			UserDetails userDet = (UserDetails)token.getPrincipal();
			log.debug("userdet" + userDet);
			User user = requestsService.getUser(userDet.getUsername());
			log.debug("user " + user);
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
		log.debug(" sort " + approvalQuery.getSort());
		log.debug(!approvalQuery.getSort().equalsIgnoreCase("asc"));
		log.debug(!approvalQuery.getSort().equalsIgnoreCase("desc"));
		log.debug((!approvalQuery.getSort().equalsIgnoreCase("asc") && !approvalQuery.getSort().equalsIgnoreCase("desc")));
		if (approvalQuery.getSort()==null || approvalQuery.getSort().isEmpty() 
				|| (!approvalQuery.getSort().equalsIgnoreCase("asc") && !approvalQuery.getSort().equalsIgnoreCase("desc"))) {
			log.debug("sort value error");
			log.debug("sort value is wrong " + approvalQuery.getSort()==null || approvalQuery.getSort().isEmpty() 
				|| (!approvalQuery.getSort().equalsIgnoreCase("asc") && !approvalQuery.getSort().equalsIgnoreCase("desc")));
			status.setCode("303");
			status.setMessage("Null/Empty/Wrong Input Parameter");
			status.setStatus("false");
			response.put("Status", status);
			return response;
		}
		if (approvalQuery.getPageSize()!=0) {
			List empReqTypeAccs = requestsService.getEmpReqTypeAcc(user.getEmployee(), approvalQuery.getRequestType());
			log.debug("empReqTypeAccs " + empReqTypeAccs.size());
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
			restStatus.setCode("314");
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
		log.debug(" sort " + approvalQuery.getSort());
		log.debug(!approvalQuery.getSort().equalsIgnoreCase("asc"));
		log.debug(!approvalQuery.getSort().equalsIgnoreCase("desc"));
		log.debug((!approvalQuery.getSort().equalsIgnoreCase("asc") && !approvalQuery.getSort().equalsIgnoreCase("desc")));
		if (approvalQuery.getSort()==null || approvalQuery.getSort().isEmpty() 
				|| (!approvalQuery.getSort().equalsIgnoreCase("asc") && !approvalQuery.getSort().equalsIgnoreCase("desc"))) {
			log.debug("sort value error");
			log.debug("sort value is wrong " + approvalQuery.getSort()==null || approvalQuery.getSort().isEmpty() 
				|| (!approvalQuery.getSort().equalsIgnoreCase("asc") && !approvalQuery.getSort().equalsIgnoreCase("desc")));
			status.setCode("303");
			status.setMessage("Null/Empty/Wrong Input Parameter");
			status.setStatus("false");
			response.put("Status", status);
			return response;
		}
		if (approvalQuery.getPageSize()!=0) {
			response =	requestsService.getSubmittedRequestsForApproval(approvalQuery,null,user.getEmployee());
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
			restStatus.setCode("314");
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
		log.debug("test vac types");
		return m;
	}
	
	@RequestMapping(value="/getSettings", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map getSettings() {
		Map m =  requestsService.getSettings();
		log.debug("test getSettings");
		return m;
	}
	
	@RequestMapping(value="/getPortNo", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody
	public Map getPortNo(ClientWrapper client) {
		Map m =  requestsService.getPortNo(client.getClientName());
		log.debug("test getPortNo");
		return m;
	}
	
	@RequestMapping(value="/searchEmployees" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map searchEmployees(EmployeeWrapper emp) {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDet = (UserDetails)token.getPrincipal();
		User user = requestsService.getUser(userDet.getUsername());
		Map m = requestsService.searchEmployees(emp,user.getEmployee());
		return m;
	}
	
	
	@RequestMapping(value="/getAttendanceVacationReport" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map getAttendanceVacationReport(RequestsApprovalQuery requestApproval) {
		log.debug("getAttendanceVacationReport ");
		Map m = requestsService.getAttendanceVacationReport(requestApproval);
		return m;
	}
	
	@RequestMapping(value="/getAttendanceReport" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map getAttendanceReport(RequestsApprovalQuery requestApproval) {
		log.debug("getAttendanceReport ");
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDet = (UserDetails)token.getPrincipal();
		User user = requestsService.getUser(userDet.getUsername());
		Map m = requestsService.getAttendanceReport(requestApproval,user.getEmployee());
		return m;
	}
	
	@RequestMapping(value="/editUserInfo" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map editUserInfo(UserWrapper userWrapper) {
		log.debug("editUserInfo ");
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDet = (UserDetails)token.getPrincipal();
		User user = requestsService.getUser(userDet.getUsername());
		Map m = requestsService.editUserInfo(userWrapper,user.getEmployee());
		return m;
	}
	
	@RequestMapping(value="/getRequestTypes" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map getRequestTypes() {
		log.debug("getRequestTypes ");
		Map m = requestsService.getRequestTypes();
		log.debug("after requesting request type");
		return m;
	}
	
	@RequestMapping(value="/getEmployeesByGroup" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map getEmployeesByGroup(GroupWrapper group) {
		log.debug("getEmployeesByGroup ");
		Map m = requestsService.getEmployeesByGroup(group.getGroupId());
		log.debug("after requesting getEmployeesByGroup");
		return m;
	}
	
	@RequestMapping(value="/getUserGroups" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map getUserGroups() {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDet = (UserDetails)token.getPrincipal();
		User user = requestsService.getUser(userDet.getUsername());
		Map m = requestsService.getUserGroups(user.getEmployee());
		return m;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////Timesheet requests///////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/insertTimesheetActivity" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map insertTimesheetActivity(TimesheetActivityWrapper activity) {
		Map response = requestsService.insertTimesheetActivity(activity);	
		return response;
	}
	
	@RequestMapping(value="/insertTimesheetPart" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map insertTimesheetPart(TimesheetPartWrapper part) {
		Map response = requestsService.insertTimesheetPart(part);
		return response;
	}
	@RequestMapping(value="/insertTimesheetSpecs" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map insertTimesheetSpecs(TimesheetSpecsWrapper specs) {
		Map response = requestsService.insertTimesheetSpecs(specs);	
		return response;
	}
	@RequestMapping(value="/insertTimesheetTransDefaults" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map insertTimesheetTransDefaults(TimesheetTransDefaultWrapper defaults) {
		Map response = requestsService.insertTimesheetTransDefaults(defaults);
		return response;
	}
	@RequestMapping(value="/insertTimesheetTrans" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map insertTimesheetTransaction(TimesheetTransWrapper trans) {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDet = (UserDetails)token.getPrincipal();
		User user = requestsService.getUser(userDet.getUsername());
		trans.setEmpCode(user.getEmployee().getEmpCode());
		Map response = requestsService.insertTimesheetTransaction(trans);
		return response;
	}

	@RequestMapping(value="/getActivities" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map getActivities() {
		Map response = requestsService.getActivities();
		return response;
	}
	@RequestMapping(value="/getCostcenters" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map getCostcenters() {
		Map response = requestsService.getCostcenters();
		return response;
	}
	@RequestMapping(value="/getParts" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map getParts(TimesheetPartWrapper part) {
		Map response = requestsService.getParts(part.getPartNo());
		return response;
	}
	@RequestMapping(value="/getTimesheetSpecs" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map getTimesheetSpecs() {
		Map response = requestsService.getTimesheetSpecs();
		return response;
	}
	@RequestMapping(value="/getTimesheetTrans" , method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_FORM_URLENCODED)
	@ResponseBody 
	public Map getTimesheetTransactions(TimesheetTransactionFilters search) {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDet = (UserDetails)token.getPrincipal();
		User user = requestsService.getUser(userDet.getUsername());
		if (search.getEmpCode() == null || search.getEmpCode().isEmpty()) {
			search.setEmpCode(user.getEmployee().getEmpCode());
		}
		Map response = requestsService.getTimesheetTransactions(search);
		return response;
	}
}