package com._4s_.requestsApproval.web.action;

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
import com._4s_.common.model.Employee;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;


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
		model.put("firstDay", formattedDate);
		model.put("today", formatedToday);
		
		String requestType= request.getParameter("requestType");
		log.debug("--requestType--"+requestType);
		model.put("requestType", requestType);
		
		String request_date_from = request.getParameter("request_date_from");
		log.debug("--request_date_from--"+request_date_from);
		model.put("request_date_from", request_date_from);
		
		String request_date_to = request.getParameter("request_date_to");
		log.debug("--request_date_to--"+request_date_to);
		model.put("request_date_to", request_date_to);
		
//		List actualRequest= new ArrayList();
//		Employee emp =(Employee) request.getSession().getAttribute("employee");
//		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
//		List<String> ordered= new ArrayList();
//		ordered.add("order");
//		List<String> ordered1= new ArrayList();
//		ordered1.add("emp_id");
//		List approval=new ArrayList();
//		EmpReqApproval empReqApproval=null;
//		for(int i=0;i<tempneededRequestTypes.size() ;i++){
//			boolean flag=true;
//			LoginUsersRequests temp=(LoginUsersRequests)tempneededRequestTypes.get(i);
//			log.debug("-----temp- code---"+temp.getEmpCode());
//			log.debug("-----req ID---"+temp.getId());
//			List allRequests= requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(EmpReqTypeAcc.class, "req_id", temp.getRequest_id() , "emp_id", temp.getLogin_user(), ordered);
//			
//			log.debug("------allRequests.size()--------"+allRequests.size());
//			
//			if(allRequests.size()>0){
//				log.debug("------allRequests.size()>0---");
//				for(int j=0;j<allRequests.size() ;j++){
//					EmpReqTypeAcc tempEmpReqTypeAcc=(EmpReqTypeAcc)allRequests.get(j);
////					try{
////						approval =  requestsApprovalManager.getObjectsByParameter(EmpReqApproval.class, "req_id", temp);
////						log.debug("------approval.size-----"+approval.size());
////						if(approval.size()>0){
////							for (int k = 0; k <approval.size(); k++) {
////								empReqApproval=(EmpReqApproval) approval.get(k);	
////
////							}
////	
////						}
////					}
////					catch (Exception e) {
////						// TODO: handle exception
////					}
//					List accessLevels= requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(AccessLevels.class, "level_id",tempEmpReqTypeAcc.getGroup_id() , "emp_id", loginUsers, ordered1);
//					
//					
//					if(accessLevels.size()>0&&flag){
//						log.debug("-------my order---"+tempEmpReqTypeAcc.getOrder());
//						//if(tempEmpReqTypeAcc.getOrder()==1){
//							actualRequest.add(temp);
//							flag=false;
//						//}
//					}	
//					
//					else{
//						//log.debug("------empreqapp-----"+empReqApproval.getId());
//						
//						//log.debug("------id ele wafe2-----"+empReqApproval.getUser_id().getId());
//					}					
//				}
//			}
//		}
//		
//		log.debug("-----actualRequest---size-"+actualRequest.size());
//		for (int i = 0; i < actualRequest.size(); i++) {
//			LoginUsersRequests s=(LoginUsersRequests) actualRequest.get(i);
//			log.debug("-----actualRequest----"+s.getEmpCode());
//		}
//		List allRequests= new ArrayList();
//		List errandRequests= new ArrayList();
//		//List allRequests= requestsApprovalManager.getObjectsByParameter(EmpReqTypeAcc.class, "request_id.id", Long.parseLong(requestType));
//				
//		for (int i = 0; i < actualRequest.size(); i++) {
//			LoginUsersRequests req=(LoginUsersRequests) actualRequest.get(i);
//			if((req.getRequest_id().getId()==1 && req.getVacation().getVacation().equals("999")) || req.getRequest_id().getId()==4){
//				log.debug("-----m2morea----");
//				errandRequests.add(req);
//			}else{
//				allRequests.add(req);
//			}
//		}
		String errand=request.getParameter("errand");
		model.put("errand", errand);
//		if(errand!=null && !errand.equals("")){
//			model.put("loginUserReqs", errandRequests);
//		}
//		else{
//			model.put("loginUserReqs", allRequests);
//		}	

		
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
		model.put("request_date_from", request_date_from);
		
		String request_date_to = request.getParameter("request_date_to");
		log.debug("--request_date_to--"+request_date_to);
		model.put("request_date_to", request_date_to);
		
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
		model.put("dateTo", dateTo);
		log.debug("---xxxxxxxDatePeriod--");
		
		String exactDateFrom = request.getParameter("exactDateFrom");
		log.debug("--exactDateFrom--"+exactDateFrom);
//		if (exactDateFrom == null || exactDateFrom.equals("")) {
//			exactDateFrom = formattedDate;
//		}
		
		String exactDateTo = request.getParameter("exactDateTo");
		log.debug("--exactDateTo--"+exactDateTo);
		
		String statusId=request.getParameter("statusId");
		String codeFrom=request.getParameter("codeFrom");
		String codeTo=request.getParameter("codeTo");
		
		if (!((dateFrom==null || dateFrom.isEmpty()) && (dateTo==null || dateTo.isEmpty()) 
				&& (exactDateFrom==null || exactDateFrom.isEmpty()) && (exactDateTo==null || exactDateTo.isEmpty()))) {
//			tempneededRequestTypes = requestsApprovalManager.getRequestsForApprovalList(requestNumber,emp_code,dateFrom,dateTo,exactDateFrom,exactDateTo,requestType,codeFrom,codeTo,statusId);
			List empReqTypeAccs = requestsApprovalManager.getEmpReqTypeAcc(emp, requestType);
			model = requestsApprovalManager.getRequestsForApproval(requestNumber,emp_code,dateFrom,dateTo,exactDateFrom,exactDateTo,requestType,codeFrom,codeTo,statusId,"desc",loginUsers, empReqTypeAccs,true,pageNumber,10);
		}
		
		int approveCounter = 0;
		
		 String check=request.getParameter("approveAll");
			log.debug("value of check>>>>>>>>>>>>>"+check);
			
			List results = (List)model.get("results");
			
			if(check !=null && !check.equals(""))
			{
				log.debug("**********************if for second*********************");
				
				for(int i=0;i<results.size();i++) {
					log.debug("**********************inside for *********************");
					LoginUsersRequests loginUserRequest= (LoginUsersRequests)results.get(i);
					String approve=request.getParameter("approve"+i);
					log.debug("approve>>>>>>>>>>"+approve);
					if(approve!=null && !approve.equals(""))
					{
						approveCounter ++;
						log.debug("approveCounter"+approveCounter);
						
//						
//						EmpReqTypeAcc empReqTypeAcc = new EmpReqTypeAcc();
////						empReqTypeAcc = (EmpReqTypeAcc) requestsApprovalManager.getObject(
////								EmpReqTypeAcc.class, new Long(accId));
//
//						EmpReqApproval empReqApproval = new EmpReqApproval();
//
//						empReqApproval.setApproval(new Integer(1));
//						empReqApproval.setReq_id(loginUserRequest);
//						empReqApproval.setLevel_id(empReqTypeAcc);
//						empReqApproval.setUser_id(loginUsers);
//						empReqApproval.setNote("Approve All Option");
//						empReqApproval.setApproval(new Integer(1));
//						
//						
//						
//						loginUserRequest.setApproved(new Long(1));
//						baseManager.saveObject(loginUserRequest);
						
						RequestApproval approval = new RequestApproval();
						approval.setApprove("1");
						approval.setRequestId(loginUserRequest.getId()+"");
						requestsApprovalManager.approvalsAccessLevels(approval, loginUserRequest, emp);
					}
					
						
				}
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
			
		return new ModelAndView("empRequestsReportsForm",model);
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
