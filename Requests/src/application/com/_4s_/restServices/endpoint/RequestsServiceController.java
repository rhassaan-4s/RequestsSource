package com._4s_.restServices.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Employee;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.restServices.json.AttendanceRequest;
import com._4s_.restServices.json.EmployeeResponse;
import com._4s_.restServices.service.RequestsService;
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
 
	@RequestMapping(value = "/login/{username}/{password}", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON)
	@ResponseBody
	public EmployeeResponse login(@PathVariable("username") String username, @PathVariable("password") String password) {
		System.out.println("testing service username " + username);
		System.out.println("testing service password " + password);
		User user = requestsService.login(username,password);
		if (user == null) {
			System.out.println("Not authenticated");
			return null;
		} else {
			System.out.println("Authenticated with employee id is " + user.getEmployee().getId());
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
			
			return e;
		}
	}

	@RequestMapping(value="/userRequest", method=RequestMethod.POST, 
            produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_JSON)
	@ResponseBody
    public LoginUsersRequests userRequest(@RequestBody AttendanceRequest userRequest)
    {
        return requestsService.userRequest(userRequest);
    }
	
	@Transactional
	@RequestMapping(value="/signInOut", method=RequestMethod.POST, 
             consumes=MediaType.APPLICATION_JSON,produces=MediaType.APPLICATION_JSON)
	@ResponseBody
    public LoginUsersRequests signInOut(@RequestBody AttendanceRequest userRequest)
    {
		System.out.println("Controller sign in/out");
        return requestsService.signInOut(userRequest);
    }
	
    
	
}