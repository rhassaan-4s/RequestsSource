package com._4s_.requestsApproval.web.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.AccessLevels;
import com._4s_.requestsApproval.model.EmpReqApproval;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.restServices.json.RequestApproval;
import com._4s_.restServices.json.RestStatus;
import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;

import java.math.BigDecimal;


public class AttendanceRequestsReports extends BaseSimpleFormController{
	RequestsApprovalManager requestsApprovalManager;
	
	String requestType = "8";//sign in and out
	
	String sort = "desc";

	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		LoginUsersRequests loginUsersRequests=new LoginUsersRequests();
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	  
		return loginUsersRequests;
	}
	
	//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		MultiCalendarDate mCalDate = new MultiCalendarDate();
		int year, month;
		
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests) command;
		Map model=new HashMap();		
		
		String emp_code = request.getParameter("empCode");
//		log.debug("----emp_code---"+emp_code);
		
		Settings settings = (Settings)requestsApprovalManager.getObject(Settings.class, new Long(1));
		model.put("settings", settings);
		
		String pageString = request.getParameter("page");
		int pageNumber;
		if (pageString != null && !pageString.equals("")){
			pageNumber = new Long(pageString).intValue();
		}   
		else{
			pageNumber = 0;
		}
		
		Calendar c = Calendar.getInstance();
//		log.debug("----c---"+c.getTime());
		c.setTime(new Date());
		year=c.get(Calendar.YEAR);
//		log.debug("----year---"+year);
		month=c.get(Calendar.MONTH);
//		log.debug("----month---"+month);
		int salary_from_day = (Integer)request.getSession().getAttribute("salary_from_day");
		if (salary_from_day == 0) {
			c.set(year,month, 1);
		} else {
			c.set(year, month-1, salary_from_day);
		}
		
		Date firstDay = c.getTime();
//		log.debug("----firstDay---"+firstDay);
		DateFormat d=new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate=d.format(firstDay);
		log.debug("----formattedDate---"+formattedDate);
		
		Date today=new Date();
		String formatedToday=d.format(today);
		log.debug("----formatedToday---"+formatedToday);
		

		String request_date_from = request.getParameter("request_date_from");
		log.debug("---request_date_from--"+request_date_from);
		if (request_date_from==null || request_date_from.equals("")) {
			request_date_from = request.getParameter("date_from");
		}
		log.debug("---request_date_from--"+request_date_from);
		
	
		String request_date_to = request.getParameter("request_date_to");
		log.debug("---request_date_to--"+request_date_to);
		if (request_date_to==null || request_date_from.equals("")) {
			request_date_to = request.getParameter("date_to");
		}
		log.debug("---request_date_to--"+request_date_to);
		
		if (request_date_from==null || request_date_from.isEmpty()) {
			request_date_from=formattedDate;
		}
		if (request_date_to==null || request_date_to.isEmpty()) {
			request_date_to=formatedToday;
		}
		
		log.debug("---request_date_from--"+request_date_from);
		log.debug("---request_date_to--"+request_date_to);

		String statusId=request.getParameter("statusId");
		String status=request.getParameter("status");
		Long statusIdLong = null;
		log.debug("statusId " + statusId);
		log.debug("status " + status);
		if (statusId!=null && !statusId.isEmpty() && !statusId.equals("null")) {
			statusIdLong=Long.parseLong(statusId);
		}
		String codeFrom=request.getParameter("codeFrom");
		String codeTo=request.getParameter("codeTo");
		
		Employee employee = (Employee)request.getSession().getAttribute("employee");
		
		String requestNumber = null;
		String exactDateFrom = null;
		String exactDateTo = null;
		
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		
		log.debug("employee " + employee.getId());
//		if (pageNumber>=0) {
			LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", employee);
			log.debug("logged in user " + loggedInUser.getId());
			List empReqTypeAccs = requestsApprovalManager.getEmpReqTypeAcc(employee, requestType);
			log.debug("empReqTypeAccs " + empReqTypeAccs);
			model = requestsApprovalManager.getRequestsForApproval(requestNumber, emp_code, request_date_from, request_date_to, exactDateFrom, exactDateTo, requestType, codeFrom, codeTo, statusId, sort, loggedInUser, empReqTypeAccs, true,null, pageNumber, 20);
//		}
		

		log.debug("status " + statusId);
		model.put("empCode", emp_code);
		model.put("status", statusIdLong);
		model.put("firstDay", formattedDate);
		model.put("today", formatedToday);
		model.put("request_date_from", request_date_from);
		model.put("request_date_to", request_date_to);
		model.put("pageNumber", pageNumber);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}

	//**************************************** onBind ***********************************************\\	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
//**************************************** onBindAndValidate ***********************************************\\
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{	
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests) command;
		
		List tempneededRequestTypes = new ArrayList();
		
		String statusId=request.getParameter("statusId");
		String status=request.getParameter("status");
		Long statusIdLong = null;
		log.debug("statusId " + statusId);
		log.debug("status " + status);
		if (statusId!=null && !statusId.isEmpty() && !statusId.equals("null")) {
			statusIdLong=Long.parseLong(statusId);
		}
		String codeFrom=request.getParameter("codeFrom");
		String codeTo=request.getParameter("codeTo");
		
		MultiCalendarDate mCalDate = new MultiCalendarDate();
		int year, month;
		
		Employee employee = (Employee)request.getSession().getAttribute("employee");
		log.debug("employee " + employee);
		
		
		Map model=new HashMap();
		
		String emp_code = request.getParameter("empCode");

		
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		
		log.debug("---xxxxxxxDatePeriod--");	
		
		Calendar c = Calendar.getInstance();
//		log.debug("----c---"+c.getTime());
		c.setTime(new Date());
		year=c.get(Calendar.YEAR);
//		log.debug("----year---"+year);
		month=c.get(Calendar.MONTH);
//		log.debug("----month---"+month);
		int salary_from_day = (Integer)request.getSession().getAttribute("salary_from_day");
		if (salary_from_day == 0) {
			c.set(year,month, 1);
		} else {
			c.set(year, month-1, salary_from_day);
		}
		
		Date firstDay = c.getTime();
//		log.debug("----firstDay---"+firstDay);
		DateFormat d=new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate=d.format(firstDay);
//		log.debug("----formattedDate---"+formattedDate);
		
		
		Date today=new Date();
		String formatedToday=d.format(today);
//		log.debug("----formatedToday---"+formatedToday);
		

		String request_date_from = request.getParameter("request_date_from");
		log.debug("---request_date_from--"+request_date_from);
		if (request_date_from==null || request_date_from.equals("")) {
			request_date_from = request.getParameter("date_from");
		}
		log.debug("---request_date_from--"+request_date_from);
		model.put("request_date_from", request_date_from);
	
		String request_date_to = request.getParameter("request_date_to");
		log.debug("---request_date_to--"+request_date_to);
		if (request_date_to==null || request_date_from.equals("")) {
			request_date_to = request.getParameter("date_to");
		}
		log.debug("---request_date_to--"+request_date_to);
		model.put("request_date_to", request_date_to);
		
		
		dateFrom = request_date_from;
		dateTo = request_date_to;
		
		
		log.debug("--dateFrom--"+dateFrom);
		
		
		log.debug("--dateTo--"+dateTo);
		
		log.debug("dateFrom != null && dateTo != null " + (dateFrom != null && dateTo != null) );
		log.debug(" 2: " + ( emp_code.equals("") && codeFrom.equals("") && codeTo.equals("")));
		
		String requestNumber = null;
		String exactDateFrom = null;
		String exactDateTo = null;
		
		String pageString = request.getParameter("page");
		int pageNumber;
		if (pageString != null && !pageString.equals("")){
			pageNumber = new Long(pageString).intValue();
		}   
		else{
			pageNumber = 0;
		}
		
		LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", employee);
		log.debug("logged in user " + loggedInUser);
		List empReqTypeAccs = requestsApprovalManager.getEmpReqTypeAcc(employee, requestType);
		log.debug("empReqTypeAccs " + empReqTypeAccs);
		log.debug("datefrom " + dateFrom);
		log.debug("dateTo " + dateTo);
		log.debug("exactDateFrom " + exactDateFrom);
		log.debug("exactDateTo " + exactDateTo);
		model = requestsApprovalManager.getRequestsForApproval(requestNumber, emp_code, dateFrom, dateTo, exactDateFrom, exactDateTo, requestType, codeFrom, codeTo, statusId, sort, loggedInUser, empReqTypeAccs, true,null, pageNumber, 20);
		model.put("pageNumber", pageNumber);
		
		List actualRequest = (List)model.get("results");
		
	/////approve all////////////////////////////
			int approveCounter = 0;
			
			 String check=request.getParameter("approveAll");
				log.debug("value of check>>>>>>>>>>>>>"+check);
				
				List results = (List)model.get("results");
				List errorsList = new ArrayList();
				int notApprovedCounter = 0;
				
				if (check != null && !check.equals("")) {
					log.debug("**********************if for second*********************");

					for (int i = 0; i < results.size(); i++) {
						log.debug("**********************inside for *********************");
						LinkedCaseInsensitiveMap map = (LinkedCaseInsensitiveMap) results
								.get(i);
						LoginUsersRequests loginUserRequest = (LoginUsersRequests) requestsApprovalManager
								.getObject(LoginUsersRequests.class,
										((BigDecimal) map.get("id")).longValue());
						String approve = request.getParameter("approve" + i);
						log.debug("approve>>>>>>>>>>" + approve);
						if (approve != null && !approve.equals("")) {
							approveCounter++;
							log.debug("approveCounter" + approveCounter);

							RequestApproval approvals = new RequestApproval();
							approvals.setApprove("1");
							approvals.setRequestId(loginUserRequest.getId() + "");
							Map approvalResult = requestsApprovalManager.approvalsAccessLevels(approvals,
									loginUserRequest, employee);
							log.debug("approval results " + approvalResult);
							RestStatus status1 = (RestStatus)approvalResult.get("Status");
							log.debug("status " + status1);
//							log.debug("status " + status.getStatus());
							if(status1!=null && status1.getStatus().equals("false")) {
								log.debug("false");
								log.debug(status1.getMessage());
								notApprovedCounter++;
								errorsList.add(status1.getMessage());
							}
						} else {
							log.debug("approve checkbox is empty " + approve);
						}
						// LoginUsersRequests loginUserRequest=
						// (LoginUsersRequests)results.get(i);
						// String approve=request.getParameter("approve"+i);
						// log.debug("approve>>>>>>>>>>"+approve);
						// if(approve!=null && !approve.equals(""))
						// {
						// approveCounter ++;
						// log.debug("approveCounter"+approveCounter);
						//
						// RequestApproval approvals = new RequestApproval();
						// approvals.setApprove("1");
						// approvals.setRequestId(loginUserRequest.getId()+"");
						// requestsApprovalManager.approvalsAccessLevels(approvals,
						// loginUserRequest, employee);
						// }

					}
				} else {
					log.debug("**********************else for check*********************");

				}
			
			////////////////////////////////////////////
				model = new HashMap();
				log.debug("model " + model);
				requestsApprovalManager.flush();
				log.debug("flushed");
				model = requestsApprovalManager.getRequestsForApproval(requestNumber, emp_code, dateFrom, dateTo, exactDateFrom, exactDateTo, requestType, codeFrom, codeTo, statusId, sort, loggedInUser, empReqTypeAccs, true,null, pageNumber, 20);
				log.debug("model " + model);
				model.put("pageNumber", pageNumber);
				model.put("errors", errorsList);
				model.put("firstDay", formattedDate);
				model.put("empCode", emp_code);
				model.put("status", statusIdLong);
				model.put("codeFrom", codeFrom);
				model.put("codeTo", codeTo);
				model.put("today", formatedToday);
				model.put("request_date_from", request_date_from);
				model.put("request_date_to", request_date_to);
//				model.put("dateFrom", dateFrom);
//				model.put("dateTo", dateTo);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		if(check !=null && !check.equals("") && errorsList.isEmpty()) {
			String url="attendanceRequestsReports.html?requestType="+requestType+"&request_date_from="+request_date_from+"&request_date_to="+request_date_to+"&employeeCode="+emp_code+"&statusId="+statusId;
			return new ModelAndView(new RedirectView(url),model);
		} else {
			return new ModelAndView("attendanceRequestsReports",model);
		}
		
	}
	
	public static boolean isOnlyNumbers(String str){
		for(int i = 0 ; i<str.length(); i++){
			char ch = str.charAt(i);
			if(ch < '0' || ch >'9'){ // not a character
				return false;
			}
		}
		return true;
	}
}
