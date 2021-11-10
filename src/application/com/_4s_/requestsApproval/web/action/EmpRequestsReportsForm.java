package com._4s_.requestsApproval.web.action;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import com._4s_.common.model.Employee;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.restServices.json.RequestApproval;
import com._4s_.restServices.json.RestStatus;


public class EmpRequestsReportsForm extends BaseSimpleFormController{
	RequestsApprovalManager requestsApprovalManager;
	
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
		
		Employee emp = (Employee)request.getSession().getAttribute("employee");
		
		Object obj = request.getSession().getAttribute("loginUsersRequests");
		LoginUsersRequests loginUsersRequests;
		if (obj != null) {
			loginUsersRequests = (LoginUsersRequests)obj;
		} else {
			loginUsersRequests = (LoginUsersRequests) command;
		}
		Map model=new HashMap();		
		


		String pageString = request.getParameter("page");
		int pageNumber;
		if (pageString != null && !pageString.equals("")){
			pageNumber = new Long(pageString).intValue();
		}   
		else{
			pageNumber = 0;
		}
		
		MultiCalendarDate mCalDate = new MultiCalendarDate();
		int year, month;
		
		
		String requestNumber = request.getParameter("requestNumber");
		
//		Employee emp = (Employee)request.getSession().getAttribute("employee");
		String emp_code = request.getParameter("empCode");
		log.debug("----emp_code---"+emp_code);
		
		
		Calendar c = Calendar.getInstance();
		log.debug("----c---"+c.getTime());
		c.setTime(new Date());
		year=c.get(Calendar.YEAR);
		log.debug("----year---"+year);
		month=c.get(Calendar.MONTH);
		log.debug("----month---"+month);
		log.debug("----(Integer)request.getSession().getAttribute(salary_from_day)---"+request.getSession().getAttribute("salary_from_day"));
		int salary_from_day = (Integer)request.getSession().getAttribute("salary_from_day");
		if (salary_from_day == 0) {
			c.set(year,month, 1);
		} else {
			c.set(year, month-1, salary_from_day);
		}
		Date firstDay = c.getTime();
		log.debug("----firstDay---"+firstDay);
		DateFormat d=new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate=d.format(firstDay);
		log.debug("----formattedDate---"+formattedDate);
		
		Date today=new Date();
		String formatedToday=d.format(today);
		log.debug("----formatedToday---"+formatedToday);
		
		
		String requestType= request.getParameter("requestType");
		log.debug("--requestType--"+requestType);
		
		
		String request_date_from = request.getParameter("request_date_from");
		log.debug("--request_date_from--"+request_date_from);
		
		
		String request_date_to = request.getParameter("request_date_to");
		log.debug("--request_date_to--"+request_date_to);
		
		
		String errand=request.getParameter("errand");
		
		
		String dateFrom = request.getParameter("dateFrom");
		log.debug("--dateFrom--"+dateFrom);
		if (dateFrom == null || dateFrom.equals("")) {
			dateFrom = formattedDate;
		}
		
		String dateTo = request.getParameter("dateTo");
		log.debug("--dateTo--"+dateTo);
		if (dateTo == null || dateTo.equals("")) {
			dateTo = formatedToday;
		}
		
		String exactDateFrom = request.getParameter("exactDateFrom");
		log.debug("--exactDateFrom--"+exactDateFrom);
		
		String exactDateTo = request.getParameter("exactDateTo");
		log.debug("--exactDateTo--"+exactDateTo);
		
		String statusId=request.getParameter("statusId");
		String status=request.getParameter("status");
		Long statusIdLong = null;
		log.debug("statusId " + statusId);
		log.debug("status " + status);
		if (statusId!=null && !statusId.isEmpty()) {
			statusIdLong=Long.parseLong(statusId);
		}
		String codeFrom=request.getParameter("codeFrom");
		String codeTo=request.getParameter("codeTo");
	
		
//		if(errand!=null && !errand.equals("")){
//			model.put("loginUserReqs", errandRequests);
//		}
//		else{
//			model.put("loginUserReqs", allRequests);
//		}	

		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
		List empReqTypeAccs = requestsApprovalManager.getEmpReqTypeAcc(emp, requestType);
		
		if (pageNumber>0) {

			
			if (!((dateFrom==null || dateFrom.isEmpty()) && (dateTo==null || dateTo.isEmpty()) 
					&& (exactDateFrom==null || exactDateFrom.isEmpty()) && (exactDateTo==null || exactDateTo.isEmpty()))) {
				model = requestsApprovalManager.getRequestsForApproval(requestNumber,emp_code,dateFrom,dateTo,exactDateFrom,exactDateTo,requestType,codeFrom,codeTo,statusId,"desc",loginUsers, empReqTypeAccs,true,null,pageNumber,10);
				log.debug("status id 1 " + statusId);
			}
		} else {
			if (!((dateFrom==null || dateFrom.isEmpty()) && (dateTo==null || dateTo.isEmpty()) 
					&& (exactDateFrom==null || exactDateFrom.isEmpty()) && (exactDateTo==null || exactDateTo.isEmpty()))) {
				model = requestsApprovalManager.getRequestsForApproval(requestNumber,emp_code,dateFrom,dateTo,exactDateFrom,exactDateTo,requestType,codeFrom,codeTo,statusId,"desc",loginUsers, empReqTypeAccs,true,null,pageNumber,10);
			}
			log.debug("status id 2 " + statusId);
		}
		model.put("dateTo", dateTo);
		model.put("status", statusIdLong);
		model.put("firstDay", formattedDate);
		model.put("today", formatedToday);
		model.put("requestType", requestType);
		model.put("request_date_from", request_date_from);
		model.put("request_date_to", request_date_to);
		model.put("errand", errand);
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
		request.getSession().setAttribute("loginUsersRequests", loginUsersRequests);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		Map model = new HashMap();
		
		String pageString = request.getParameter("page");
		int pageNumber;
		if (pageString != null && !pageString.equals("")){
			pageNumber = new Long(pageString).intValue();
		}   
		else{
			pageNumber = 0;
		}
		
		MultiCalendarDate mCalDate = new MultiCalendarDate();
		int year, month;
		List tempneededRequestTypes = new ArrayList();
		
		String requestNumber = request.getParameter("requestNumber");
		
		Employee emp = (Employee)request.getSession().getAttribute("employee");
		String emp_code = request.getParameter("empCode");
		log.debug("----emp_code---"+emp_code);
		
		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
		
		List<String> orderfieldList = new ArrayList();
		orderfieldList.add(new String("order"));

		List empReqAcc = requestsApprovalManager
				.getObjectsByTwoParametersOrderedByFieldList(
						EmpReqTypeAcc.class, "req_id", loginUsersRequests
								.getRequest_id(), "emp_id", loginUsersRequests
								.getLogin_user(), orderfieldList);
		
		
//		String accId = request.getParameter("accId")
		
		Calendar c = Calendar.getInstance();
		log.debug("----c---"+c.getTime());
		c.setTime(new Date());
		year=c.get(Calendar.YEAR);
		log.debug("----year---"+year);
		month=c.get(Calendar.MONTH);
		log.debug("----month---"+month);
		int salary_from_day = (Integer)request.getSession().getAttribute("salary_from_day");
		if (salary_from_day == 0) {
			c.set(year,month, 1);
		} else {
			c.set(year, month-1, salary_from_day);
		}
		Date firstDay = c.getTime();
		log.debug("----firstDay---"+firstDay);
		DateFormat d=new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate=d.format(firstDay);
		log.debug("----formattedDate---"+formattedDate);
		
		Date today=new Date();
		String formatedToday=d.format(today);
		log.debug("----formatedToday---"+formatedToday);

		String request_date_from = request.getParameter("request_date_from");
		log.debug("--request_date_from--"+request_date_from);
		
		
		String request_date_to = request.getParameter("request_date_to");
		log.debug("--request_date_to--"+request_date_to);
		
		String exact_request_date_from = request.getParameter("exactDateFrom");
		log.debug("---exact_request_date_from--"+exact_request_date_from);
	
		String exact_request_date_to = request.getParameter("exactDateTo");
		log.debug("---exact_request_date_to--"+exact_request_date_to);
		
		String requestType= request.getParameter("requestType");
		log.debug("--requestType--"+requestType);
		
		String dateFrom = request.getParameter("dateFrom");
		log.debug("--dateFrom--"+dateFrom);
		if (dateFrom == null || dateFrom.equals("")) {
			dateFrom = formattedDate;
		}
		
		String dateTo = request.getParameter("dateTo");
		log.debug("--dateTo--"+dateTo);
		if (dateTo == null || dateTo.equals("")) {
			dateTo = formatedToday;
		}
		
		log.debug("---xxxxxxxDatePeriod--");
		
		String exactDateFrom = request.getParameter("exactDateFrom");
		log.debug("--exactDateFrom--"+exactDateFrom);
//		if (exactDateFrom == null || exactDateFrom.equals("")) {
//			exactDateFrom = formattedDate;
//		}
		
		String exactDateTo = request.getParameter("exactDateTo");
		log.debug("--exactDateTo--"+exactDateTo);
		
		String statusId=request.getParameter("statusId");
		String status=request.getParameter("status");
		Long statusIdLong = null;
		log.debug("statusId " + statusId);
		log.debug("status " + status);
		if (statusId!=null && !statusId.isEmpty()) {
			statusIdLong=Long.parseLong(statusId);
		}
		String codeFrom=request.getParameter("codeFrom");
		String codeTo=request.getParameter("codeTo");
		
		List empReqTypeAccs = requestsApprovalManager.getEmpReqTypeAcc(emp, requestType);
		
		if (!((dateFrom==null || dateFrom.isEmpty()) && (dateTo==null || dateTo.isEmpty()) 
				&& (exactDateFrom==null || exactDateFrom.isEmpty()) && (exactDateTo==null || exactDateTo.isEmpty()))) {
			model = requestsApprovalManager.getRequestsForApproval(requestNumber,emp_code,dateFrom,dateTo,exactDateFrom,exactDateTo,requestType,codeFrom,codeTo,statusId,"desc",loginUsers, empReqTypeAccs,true,null,pageNumber,10);
		}
		
		int approveCounter = 0;
		
		 String check=request.getParameter("approveAll");
			log.debug("value of check>>>>>>>>>>>>>"+check);
			
			List results = (List)model.get("results");
			
			int notApprovedCounter = 0; 
			List errorsList = new ArrayList();
			if(check !=null && !check.equals("")) {
				log.debug("**********************if for second*********************");
				
				
				
				for(int i=0;i<results.size();i++) {
					log.debug("**********************inside for *********************");
//					LoginUsersRequests loginUserRequest= (LoginUsersRequests)results.get(i);
//					String approve=request.getParameter("approve"+i);
//					log.debug("approve>>>>>>>>>>"+approve);
//					log.debug("request number " + loginUserRequest.getRequestNumber());
//					log.debug("request id " + loginUserRequest.getId());
//					if(approve!=null && !approve.equals(""))
//					{
//						approveCounter ++;
//						log.debug("approveCounter"+approveCounter);
//						RequestApproval approval = new RequestApproval();
//						approval.setApprove("1");
//						approval.setRequestId(loginUserRequest.getId()+"");
//						requestsApprovalManager.approvalsAccessLevels(approval, loginUserRequest, emp);
//					}
					///////////////////////////////////////////////////////////////////
					
					LinkedCaseInsensitiveMap requestMap = (LinkedCaseInsensitiveMap)results.get(i);
					String approve = request.getParameter("approve"+i);
					
					if(approve!=null && !approve.equals(""))
					{
						approveCounter ++;
						log.debug("approveCounter"+approveCounter);
						RequestApproval approval = new RequestApproval();
						approval.setApprove("1");
						BigDecimal requestId = (BigDecimal)requestMap.get("id");
						LoginUsersRequests loginUserRequest = (LoginUsersRequests)requestsApprovalManager.getObject(LoginUsersRequests.class, new Long(requestId.longValue()));
						log.debug("approve>>>>>>>>>>"+approve);
						log.debug("request number " + loginUserRequest.getRequestNumber());
						log.debug("request id " + loginUserRequest.getId());
						approval.setRequestId(requestId+"");
						Map approvalResult = requestsApprovalManager.approvalsAccessLevels(approval, loginUserRequest, emp);
						log.debug("approval results " + approvalResult);
						RestStatus status1 = (RestStatus)approvalResult.get("Status");
						log.debug("status " + status1);
//						log.debug("status " + status.getStatus());
						if(status1!=null && status1.getStatus().equals("false")) {
							log.debug("false");
							log.debug(status1.getMessage());
							notApprovedCounter++;
							errorsList.add(status1.getMessage());
						}
					}
					
						
				}
				model = null;
				model = requestsApprovalManager.getRequestsForApproval(requestNumber,emp_code,dateFrom,dateTo,exactDateFrom,exactDateTo,requestType,codeFrom,codeTo,statusId,"desc",loginUsers, empReqTypeAccs,true,null,pageNumber,10);
				model.put("errors", errorsList);
			}
			else
			{
				log.debug("**********************else for check*********************");
				
				
			}
			
			model.put("requestNumber", requestNumber);
			model.put("employeeCode", emp_code);
			model.put("firstDay", formattedDate);
			model.put("today", formatedToday);
			model.put("request_date_from", request_date_from);
			model.put("request_date_to", request_date_to);
			model.put("exact_request_date_from", exact_request_date_from);
			model.put("exact_request_date_to", exact_request_date_to);
			model.put("requestType", requestType);
			model.put("dateFrom", dateFrom);
			model.put("exactDateFrom", exactDateFrom);
			model.put("exactDateTo", exactDateTo);
			model.put("request_date_from", request_date_from);
			model.put("request_date_to", request_date_to);
			model.put("dateTo", dateTo);
			model.put("status",statusIdLong);
			model.put("codeFrom", codeFrom);
			model.put("codeTo", codeTo);
			
			if(check !=null && !check.equals("") && errorsList.isEmpty()) {
				String url="empRequestsReportsForm.html?requestType="+requestType+"&request_date_from="+request_date_from+"&request_date_to="+request_date_to+"&employeeCode="+emp_code+"&statusId="+statusId;
				return new ModelAndView(new RedirectView(url),model);
			} else {
				return new ModelAndView("empRequestsReportsForm",model);	
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
