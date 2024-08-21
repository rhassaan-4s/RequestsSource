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
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.common.web.action.CommonController;
import com._4s_.common.web.binders.DateTimeBinder;
import com._4s_.common.web.binders.DomainObjectBinder;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.restServices.json.RequestApproval;

@Controller
@RequestMapping("/requestStatusReports.html")
public class RequestStatusReports extends BaseSimpleFormController {
	@Autowired
	RequestsApprovalManager requestsApprovalManager;

	@Autowired
	@Qualifier("requestTypesBinder")
	private DomainObjectBinder requestTypesBinder;
	@Autowired
	@Qualifier("loginUsersBinder")
	private DomainObjectBinder loginUsersBinder;
	@Autowired
	@Qualifier("requestTypesBinder")
	private DomainObjectBinder dateTimeBinder;
	
	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}
	
	@Override
	public void initBinder(HttpServletRequest request,WebDataBinder binder) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Starting init binder: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		super.initBinder(request,binder);
		binder.registerCustomEditor(RequestTypes.class, requestTypesBinder);
		binder.registerCustomEditor(LoginUsers.class, loginUsersBinder);
		binder.registerCustomEditor(Date.class, dateTimeBinder);
	}
	
//	@RequestMapping(method = RequestMethod.GET)  public String initForm(ModelMap model,HttpServletRequest request){
//	{
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request){
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		LoginUsersRequests loginUsersRequests=new LoginUsersRequests();
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	  
//		return loginUsersRequests;
		return "requestStatusReports";
	}
	
	//**************************************** referenceData ***********************************************\\
//	@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
//	{
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		MultiCalendarDate mCalDate = new MultiCalendarDate();
		int year, month;
		
//		LoginUsersRequests loginUsersRequests=(LoginUsersRequests) command;
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
		if (request_date_to==null || request_date_to.equals("")) {
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
		if (statusId!=null && statusId.isEmpty()) {
			statusId=null;
		}
		String codeFrom=request.getParameter("codeFrom");
		String codeTo=request.getParameter("codeTo");
		
		Employee employee = (Employee)request.getSession().getAttribute("employee");
		
		String requestNumber = null;
		String exactDateFrom = null;
		String exactDateTo = null;
		
//		String req = null;//sign in and out
		String requestType = request.getParameter("requestType");
		if (requestType==null || requestType.isEmpty()) {
			requestType = null;
		}
		
		
		List orderBy = new ArrayList();
		orderBy.add("id");
		List requests=requestsApprovalManager.getObjectsByParameterOrderedByFieldList(RequestTypes.class,"hidden" , new Integer(0), orderBy);
		requests.addAll(requestsApprovalManager.getObjectsByParameterOrderedByFieldList(RequestTypes.class,"hidden" , new Integer(1), orderBy));
		
		
		String sort = "desc";
		
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		
		log.debug("employee " + employee.getId());
		if (pageNumber>=0) {
			LoginUsers loggedInUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", employee);
			log.debug("logged in user " + loggedInUser.getId());
			List empReqTypeAccs = requestsApprovalManager.getEmpReqTypeAcc(employee, requestType);
			log.debug("empReqTypeAccs " + empReqTypeAccs);
			if (empReqTypeAccs.size()==0) {
				emp_code = loggedInUser.getEmpCode().getEmpCode();
			}
			model = requestsApprovalManager.getRequestsStatus(requestNumber, emp_code, request_date_from, request_date_to, exactDateFrom, exactDateTo, requestType, codeFrom, codeTo, statusId, sort, loggedInUser, empReqTypeAccs, true,null, pageNumber, 20);
		}
		log.debug("status " + statusId);
		model.put("empCode", emp_code);
		model.put("status", statusId);
		model.put("firstDay", formattedDate);
		model.put("today", formatedToday);
		model.put("request_date_from", request_date_from);
		model.put("request_date_to", request_date_to);
		model.put("pageNumber", pageNumber);
		model.put("requestTypeList",requests);
		model.put("requestType", requestType);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}

//	//**************************************** onBind ***********************************************\\	
//	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		
//		
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//	}
////**************************************** onBindAndValidate ***********************************************\\
//	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
//	{
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DomainObjectBinder loginUserBinder = new DomainObjectBinder();
		loginUserBinder.setBaseManager(requestsApprovalManager);
		loginUserBinder.setBindedClass(LoginUsers.class);
		binder.registerCustomEditor(LoginUsers.class, loginUserBinder);

		DomainObjectBinder requestTypeBinder = new DomainObjectBinder();
		requestTypeBinder.setBaseManager(requestsApprovalManager);
		requestTypeBinder.setBindedClass(RequestTypes.class);
		binder.registerCustomEditor(RequestTypes.class, requestTypeBinder);

		DateTimeBinder dateTimeBinder = new DateTimeBinder();
		binder.registerCustomEditor(Date.class, dateTimeBinder);
	}
	
	//**************************************** onSubmit ***********************************************\\	
//	public ModelAndView onSubmit(HttpServletRequest request,
//			HttpServletResponse response, Object command, BindException errors)throws Exception 
//	{	
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(HttpServletRequest request,
			@Valid @ModelAttribute("loginUsersRequests") LoginUsersRequests command,
			BindingResult result, SessionStatus status,Model model) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests) command;
		
		List tempneededRequestTypes = new ArrayList();
		
		String statusId=request.getParameter("statusId");
		if (statusId.isEmpty()) {
			statusId=null;
		}
		log.debug("statusId " + statusId);
		String codeFrom=request.getParameter("codeFrom");
		String codeTo=request.getParameter("codeTo");
		
		MultiCalendarDate mCalDate = new MultiCalendarDate();
		int year, month;
		
		Employee employee = (Employee)request.getSession().getAttribute("employee");
		log.debug("employee " + employee);
		
		
//		Map model=new HashMap();
		
		String emp_code = request.getParameter("empCode");
//		log.debug("----emp_code---"+emp_code);
		
		
//		if(codeFrom!=null && codeTo!=null && !codeFrom.equals("")&& !codeTo.equals("")){
//			List loginUserReqs=(List) requestsApprovalManager.getEmployeesByCodes(codeFrom, codeTo);
////			log.debug("---codesList---"+loginUserReqs.size());
//			List neededRequestTypes = new ArrayList();
//
//			for (int i = 0; i < loginUserReqs.size(); i++) {
//				LoginUsersRequests s=(LoginUsersRequests) loginUserReqs.get(i);
//				if(s.getRequest_id().getId().equals(new Long(10)) || s.getRequest_id().getId().equals(new Long(11))){
//					neededRequestTypes.add(s);
//					log.debug("---neededList---"+neededRequestTypes.size());
//				}
//				log.debug("---code code---"+s.getEmpCode());
//			}
//			log.debug("case 1");
//			tempneededRequestTypes=neededRequestTypes;
//		}
		
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
		model.addAttribute("request_date_from", request_date_from);
	
		String request_date_to = request.getParameter("request_date_to");
		log.debug("---request_date_to--"+request_date_to);
		if (request_date_to==null || request_date_to.equals("")) {
			request_date_to = request.getParameter("date_to");
		}
		log.debug("---request_date_to--"+request_date_to);
		model.addAttribute("request_date_to", request_date_to);
		
		
		dateFrom = request_date_from;
		dateTo = request_date_to;
		
		
		log.debug("--dateFrom--"+dateFrom);
		
		
		log.debug("--dateTo--"+dateTo);
		
		log.debug("dateFrom != null && dateTo != null " + (dateFrom != null && dateTo != null) );
		log.debug(" 2: " + ( emp_code.equals("") && codeFrom.equals("") && codeTo.equals("")));
		
		String requestNumber = null;
		String exactDateFrom = null;
		String exactDateTo = null;
		
//		String requestType = "7";//sign in and out
		
		String requestType = request.getParameter("requestType");
		if (requestType==null || requestType.isEmpty()) {
			requestType = null;
		}
		
		String sort = "desc";
		
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
		Map results = requestsApprovalManager.getRequestsStatus(requestNumber, emp_code, dateFrom, dateTo, exactDateFrom, exactDateTo, requestType, codeFrom, codeTo, statusId, sort, loggedInUser, empReqTypeAccs, true,null, pageNumber, 20);
		
		model.addAttribute("Results",results);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("requestType", requestType);
		
//		///////////////////////////////////////////////////////////
//		List results2 = (List)model.get("results");
//		Iterator itr = results2.iterator();
//		while(itr.hasNext()) {
//			LoginUsersRequests s = (LoginUsersRequests)itr.next();
//			////////////////////////////////////////////////////////////////////////////
//			/////////////////started temp  code
//			////////////////////////////////////////////////////////////////////////////
//			Settings settings = (Settings)requestsApprovalManager.getObject(Settings.class, new Long(1));
//
//			if (s.getLatitude()!=null && s.getLongitude()!=null && s.getLatitude()>0 && s.getLongitude()>0) {
//				Double distance = requestsApprovalManager.distance(new Double(s.getLatitude()), new Double(s.getLongitude()), new Double(settings.getCompanyLat()), new Double(settings.getCompanyLong()));
//				log.debug("distance " +distance);
//				if (distance>settings.getDistAllowedFromCompany()) {
//					s.setIsInsideCompany(false);
//				} else {
//					s.setIsInsideCompany(true);
//				}
//
//				//////////////////////////////////////////////////////////////////////////
//				try{
//					String address = requestsApprovalManager.getAddressByGpsCoordinates(s.getLongitude()+"", s.getLatitude()+"");
//					log.debug("address " + address);
//					s.setLocationAddress(address);
//				} catch(Exception e) {
//					e.printStackTrace();
//				}
//
//				////////////////////////////////////////////////////////////////////////////
//			} else {
//				s.setIsInsideCompany(true);	
//			}
//			
//			requestsApprovalManager.saveObject(s);
//////////////////////////////////////////////////////////////////////////////
///////////////////ended temp  code
//////////////////////////////////////////////////////////////////////////////
//		}
//		//////////////////////////////////////////////////////
	
//				List loginUserReqs=(List) requestsApprovalManager.getRequestsByDatePeriod(fromDate, toDate);
//				for (int i = 0; i < loginUserReqs.size(); i++) {
//					LoginUsersRequests s=(LoginUsersRequests) loginUserReqs.get(i);
//					if(s.getRequest_id().getId().equals(new Long(10)) || s.getRequest_id().getId().equals(new Long(11))){
//						neededRequestTypes.add(s);
//						log.debug("---neededList---"+neededRequestTypes.size());
//						
//						
//
//
//						////////////////////////////////////////////////////////////////////////////
//						/////////////////started temp  code
//						////////////////////////////////////////////////////////////////////////////
//						Settings settings = (Settings)requestsApprovalManager.getObject(Settings.class, new Long(1));
//
//						if (s.getLatitude()!=null && s.getLongitude()!=null && s.getLatitude()>0 && s.getLongitude()>0) {
//							Double distance = requestsApprovalManager.distance(new Double(s.getLatitude()), new Double(s.getLongitude()), new Double(settings.getCompanyLat()), new Double(settings.getCompanyLong()));
//							if (distance>settings.getDistAllowedFromCompany()) {
//								loginUsersRequests.setIsInsideCompany(false);
//							} else {
//								loginUsersRequests.setIsInsideCompany(true);
//							}
//
//							//////////////////////////////////////////////////////////////////////////
//							try{
//								String address = requestsApprovalManager.getAddressByGpsCoordinates(s.getLongitude()+"", s.getLatitude()+"");
//								loginUsersRequests.setLocationAddress(address);
//							} catch(Exception e) {
//								e.printStackTrace();
//							}
//
//							////////////////////////////////////////////////////////////////////////////
//						} else {
//							loginUsersRequests.setIsInsideCompany(true);	
//						}
//
//						requestsApprovalManager.saveObject(s);
//						////////////////////////////////////////////////////////////////////////////
//						/////////////////ended temp  code
//						////////////////////////////////////////////////////////////////////////////
//
//
//					}
//					log.debug("---code code---"+s.getEmpCode());
//				}
//				log.debug("--dateList.size--"+neededRequestTypes.size());
//				log.debug("case 2");
//				tempneededRequestTypes=neededRequestTypes;
//			}
//		}

		
		List actualRequest = (List)results.get("results");
//		log.debug("-----actualRequest---size-"+actualRequest.size());
//		for (int i = 0; i < actualRequest.size(); i++) {
//			LoginUsersRequests s=(LoginUsersRequests) actualRequest.get(i);
//			log.debug("-----actualRequest----"+s.getEmpCode());
//			
//			
//			////////////////////////////////////////////////////////////////////////////
//			/////////////////started temp  code
//			////////////////////////////////////////////////////////////////////////////
//			Settings settings = (Settings)requestsApprovalManager.getObject(Settings.class, new Long(1));
//
//			if (s.getLatitude()!=null && s.getLongitude()!=null && s.getLatitude()>0 && s.getLongitude()>0) {
//				Double distance = requestsApprovalManager.distance(new Double(s.getLatitude()), new Double(s.getLongitude()), new Double(settings.getCompanyLat()), new Double(settings.getCompanyLong()));
//				log.debug("distance " +distance);
//				if (distance>settings.getDistAllowedFromCompany()) {
//					loginUsersRequests.setIsInsideCompany(false);
//				} else {
//					loginUsersRequests.setIsInsideCompany(true);
//				}
//
//				//////////////////////////////////////////////////////////////////////////
//				try{
//					String address = requestsApprovalManager.getAddressByGpsCoordinates(s.getLongitude()+"", s.getLatitude()+"");
//					log.debug("address " + address);
//					loginUsersRequests.setLocationAddress(address);
//				} catch(Exception e) {
//					e.printStackTrace();
//				}
//
//				////////////////////////////////////////////////////////////////////////////
//			} else {
//				loginUsersRequests.setIsInsideCompany(true);	
//			}
//			
//			requestsApprovalManager.saveObject(s);
//////////////////////////////////////////////////////////////////////////////
///////////////////ended temp  code
//////////////////////////////////////////////////////////////////////////////
//		}
		
		
		
		
		
//		model.put("loginUserReqs", actualRequest);
	/////approve all////////////////////////////
			int approveCounter = 0;
			
			 String check=request.getParameter("approveAll");
				log.debug("value of check>>>>>>>>>>>>>"+check);
				
				
				if(check !=null && !check.equals(""))
				{
					log.debug("**********************if for second*********************");
					
					for(int i=0;i<actualRequest.size();i++) {
						log.debug("**********************inside for *********************");
						LinkedCaseInsensitiveMap  map= (LinkedCaseInsensitiveMap)actualRequest.get(i);
						LoginUsersRequests loginUserRequest = (LoginUsersRequests)requestsApprovalManager.getObject(LoginUsersRequests.class, ((BigDecimal)map.get("id")).longValue()); 
						String approve=request.getParameter("approve"+i);
						log.debug("approve>>>>>>>>>>"+approve);
						if(approve!=null && !approve.equals(""))
						{
							approveCounter ++;
							log.debug("approveCounter"+approveCounter);
							
							RequestApproval approvals = new RequestApproval();
							approvals.setApprove("1");
							approvals.setRequestId(loginUserRequest.getId()+"");
							requestsApprovalManager.approvalsAccessLevels(approvals, loginUserRequest, employee);
						}
//						LoginUsersRequests loginUserRequest= (LoginUsersRequests)actualRequest.get(i);
//						String approve=request.getParameter("approve"+i);
//						log.debug("approve>>>>>>>>>>"+approve);
//						if(approve!=null && !approve.equals(""))
//						{
//							approveCounter ++;
//							log.debug("approveCounter"+approveCounter);
//							
//							RequestApproval approvals = new RequestApproval();
//							approvals.setApprove("1");
//							approvals.setRequestId(loginUserRequest.getId()+"");
//							requestsApprovalManager.approvalsAccessLevels(approvals, loginUserRequest, employee);
//						}
						
							
					}
				}
				else
				{
					log.debug("**********************else for check*********************");
					
					
				}
			
			////////////////////////////////////////////
				
				List orderBy = new ArrayList();
				orderBy.add("id");
				List requests=requestsApprovalManager.getObjectsByParameterOrderedByFieldList(RequestTypes.class,"hidden" , new Integer(0), orderBy);
				requests.addAll(requestsApprovalManager.getObjectsByParameterOrderedByFieldList(RequestTypes.class,"hidden" , new Integer(1), orderBy));
				
				model.addAttribute("firstDay", formattedDate);
				model.addAttribute("empCode", emp_code);
				model.addAttribute("status", statusId);
				model.addAttribute("codeFrom", codeFrom);
				model.addAttribute("codeTo", codeTo);
				model.addAttribute("today", formatedToday);
				model.addAttribute("request_date_from", request_date_from);
				model.addAttribute("request_date_to", request_date_to);
//				model.addAttribute("dateFrom", dateFrom);
//				model.addAttribute("dateTo", dateTo);
				
				model.addAttribute("requestTypeList",requests);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//		return new ModelAndView("requestStatusReports",model);
		return "requestStatusReports";
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
