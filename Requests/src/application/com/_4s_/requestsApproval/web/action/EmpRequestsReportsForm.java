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
import com._4s_.common.model.Employee;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;


public class EmpRequestsReportsForm extends BaseSimpleFormController{
	RequestsApprovalManager requestsApprovalManager;
	List<String>list=new ArrayList<String>();
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
		
		
		list.add("period_from");
		MultiCalendarDate mCalDate = new MultiCalendarDate();
		int year, month;
		List tempneededRequestTypes = new ArrayList();
		Object obj = request.getSession().getAttribute("loginUsersRequests");
		LoginUsersRequests loginUsersRequests;
		if (obj != null) {
			loginUsersRequests = (LoginUsersRequests)obj;
		} else {
			loginUsersRequests = (LoginUsersRequests) command;
		}
		Map model=new HashMap();		
		
		String requestNumber = request.getParameter("requestNumber");
		model.put("requestNumber", requestNumber);
		
		String emp_code = request.getParameter("empCode");
		log.debug("----emp_code---"+emp_code);
		model.put("employeeCode", emp_code);
		
		
		
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
		
		model.put("firstDay", formattedDate);
		Date today=new Date();
		String formatedToday=d.format(today);
		log.debug("----formatedToday---"+formatedToday);
		model.put("today", formatedToday);
//		if(formattedDate!=null){
//			log.debug("-----formattedDate --"+formattedDate);
//			mCalDate.setDateString(formattedDate);
//		}
//		firstDay = mCalDate.getDate();
//		log.debug("----firstDay- after formatting--"+firstDay);
		
		
		String request_date_from = request.getParameter("dateFrom");
		log.debug("---request_date_from--"+request_date_from);
		model.put("request_date_from", request_date_from);
	
		String request_date_to = request.getParameter("dateTo");
		log.debug("---request_date_to--"+request_date_to);
		model.put("request_date_to", request_date_to);
		
		String exact_request_date_from = request.getParameter("exactDateFrom");
		log.debug("---exact_request_date_from--"+exact_request_date_from);
		model.put("exact_request_date_from", exact_request_date_from);
	
		String exact_request_date_to = request.getParameter("exactDateTo");
		log.debug("---exact_request_date_to--"+exact_request_date_to);
		model.put("exact_request_date_to", exact_request_date_to);
		
//		String empName = request.getParameter("empName");
//		log.debug("----empName---"+empName);
//		if(empName!=null && !empName.equals("")){
//			LoginUsers loginUser=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "id", Long.parseLong(empName));
//			log.debug("----loginUser.getName()---"+loginUser.getName());
//			List loginUserReqs= requestsApprovalManager.getObjectsByParameter(LoginUsersRequests.class, "login_user", loginUser);
//			
//			model.put("employeeName", loginUser.getName());
//			model.put("loginUserReqs", loginUserReqs);
//			
//		}
		String requestType= request.getParameter("requestType");
		log.debug("--requestType--"+requestType);
		model.put("requestType", requestType);
		
		String dateFrom = request.getParameter("dateFrom");
		log.debug("--dateFrom--"+dateFrom);
		if (dateFrom == null || dateFrom.equals("")) {
			dateFrom = formattedDate;
		}
		model.put("dateFrom", dateFrom);
		String dateTo = request.getParameter("dateTo");
		log.debug("--dateTo--"+dateTo);
		if (dateTo == null || dateTo.equals("")) {
			dateTo = formatedToday;
		}
		model.put("dateTo", dateTo);
		log.debug("---xxxxxxxDatePeriod--");
		
		String exactDateFrom = request.getParameter("exactDateFrom");
		log.debug("--exactDateFrom--"+exactDateFrom);
		if (exactDateFrom == null || exactDateFrom.equals("")) {
			exactDateFrom = formattedDate;
		}
		model.put("exactDateFrom", exactDateFrom);
		String exactDateTo = request.getParameter("exactDateTo");
		log.debug("--exactDateTo--"+exactDateTo);
		if (exactDateTo == null || exactDateTo.equals("")) {
			exactDateTo = formatedToday;
		}
		model.put("exactDateTo", exactDateTo);
		
		if (requestType!= null && !requestType.isEmpty() && requestNumber!=null && !requestNumber.isEmpty()){
			if (dateFrom != null && dateTo != null){
				if (!dateFrom.equals("") && !dateTo.equals("") ) {
					
					Date fromDate = null;
					Date toDate = null;
					log.debug(">>>>>>>>>>>>> if ");
					log.debug(">>>>>>>>>>>Valid date format");
					log.debug(">>>>>>>>>>>>>fromDateString "+ dateFrom);
					//fromDateStr = fromDateString +" 00:00";
					mCalDate.setDateTimeString(dateFrom,new Boolean(true));
					fromDate = mCalDate.getDate();
					log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
					log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
					//toDateStr = toDateString+ " 23:59";
					mCalDate.setDateTimeString(dateTo,new Boolean(false));
					toDate= mCalDate.getDate();
					log.debug(">>>>>>>>>>>>>toDate "+ toDate);
					
					List loginUserReqs=(List) requestsApprovalManager.getRequestsByDatePeriodAndRequestType(fromDate, toDate,Long.parseLong(requestType));
					log.debug("--dateList.size--"+loginUserReqs.size());
					//model.put("loginUserReqs", loginUserReqs);
					tempneededRequestTypes=loginUserReqs;
				}
			}
			
			
			if (exactDateFrom != null && exactDateTo != null){
				if (!exactDateFrom.equals("") && !exactDateTo.equals("") ) {
					
					Date fromDate = null;
					Date toDate = null;
					log.debug(">>>>>>>>>>>>> if ");
					log.debug(">>>>>>>>>>>Valid date format");
					log.debug(">>>>>>>>>>>>>fromDateString "+ exactDateFrom);
					//fromDateStr = fromDateString +" 00:00";
					mCalDate.setDateTimeString(exactDateFrom,new Boolean(true));
					fromDate = mCalDate.getDate();
					log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
					log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
					//toDateStr = toDateString+ " 23:59";
					mCalDate.setDateTimeString(dateTo,new Boolean(false));
					toDate= mCalDate.getDate();
					log.debug(">>>>>>>>>>>>>toDate "+ toDate);
					
					List loginUserReqs=(List) requestsApprovalManager.getRequestsByExactDatePeriodAndRequestType(fromDate, toDate,Long.parseLong(requestType));
					log.debug("--dateList.size--"+loginUserReqs.size());
					//model.put("loginUserReqs", loginUserReqs);
					tempneededRequestTypes=loginUserReqs;
				}
			}
			
			
			
		}
		
		if(emp_code!=null && !emp_code.equals("")){
			log.debug("---xxxxxxxCodeName--");
			if(isOnlyNumbers(emp_code) && (requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp_code)!=null && !requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp_code).equals(""))){
				LoginUsers loginUser=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp_code);
				log.debug("--loginUser.getName()--"+loginUser.getName());

				// to get requests corresponding to request type
				List loginUserReqs=(List) requestsApprovalManager.getObjectsByParameterOrderedByFieldList(LoginUsersRequests.class, "login_user", loginUser, list);
				List neededRequestTypes = new ArrayList();
				for(int i=0;i<loginUserReqs.size();i++){
					log.debug("---needed---");
					LoginUsersRequests reqs= (LoginUsersRequests) loginUserReqs.get(i);
					log.debug("---needed reqs---"+reqs.getEmpCode()+"----reqs.getRequest_id().getId()--"+reqs.getRequest_id().getId());
					String request_type=Long.toString(reqs.getRequest_id().getId());
					log.debug("-----requestType.equals(reqs.getRequest_id().getId())----"+requestType.equals(reqs.getRequest_id().getId()));
					
					log.debug("-X--requestType.equals(request_type)--"+requestType.equals(request_type));
					if(requestType.equals(request_type)){
						neededRequestTypes.add(reqs);
						log.debug("---neededList---"+neededRequestTypes.size());
					}
					log.debug("---after IF neededList---"+neededRequestTypes.size());				
				}
//				log.debug("--list.size--"+neededRequestTypes.size());
				tempneededRequestTypes=neededRequestTypes;
				//model.put("loginUserReqs", neededRequestTypes);
			}

		}
		
		String statusId=request.getParameter("statusId");
		String codeFrom=request.getParameter("codeFrom");
		String codeTo=request.getParameter("codeTo");
		if(codeFrom!=null && codeTo!=null && !codeFrom.equals("")&& !codeTo.equals("")){
			List loginUserReqs=(List) requestsApprovalManager.getEmployeesByCodes(codeFrom, codeTo);
			log.debug("---codesList---"+loginUserReqs.size());
			tempneededRequestTypes=loginUserReqs;
			//model.put("records", loginUserReqs);
			for (int i = 0; i < loginUserReqs.size(); i++) {
				LoginUsersRequests s=(LoginUsersRequests) loginUserReqs.get(i);
				log.debug("---code code---"+s.getEmpCode());
			}
		}
		
		//http://localhost:20000/Requests/requestsApproval/requestsApprovalForm.html?reqId=382&cancelApproval=true&done=true&requestType=1
//http://localhost:20000/Requests/requestsApproval/empRequestsReportsForm.html?empCode=00000007&dateFrom=01/07/2012&dateTo=02/10/2012&requestType=1&codeFrom=&codeTo=&statusId=&errand=
		if (dateFrom != null && dateTo != null){
			if (!dateFrom.equals("") && !dateTo.equals("") ) {
				
				Date fromDate = null;
				Date toDate = null;
				log.debug(">>>>>>>>>>>>> if ");
				log.debug(">>>>>>>>>>>Valid date format");
				log.debug(">>>>>>>>>>>>>fromDateString "+ dateFrom);
				//fromDateStr = fromDateString +" 00:00";
				mCalDate.setDateTimeString(dateFrom,new Boolean(true));
				fromDate = mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
				log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
				//toDateStr = toDateString+ " 23:59";
				mCalDate.setDateTimeString(dateTo,new Boolean(false));
				toDate= mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>toDate "+ toDate);
				
				List loginUserReqs=(List) requestsApprovalManager.getRequestsByDatePeriodAndRequestType(fromDate, toDate,Long.parseLong(requestType));
				log.debug("--dateList.size--"+loginUserReqs.size());
				//model.put("loginUserReqs", loginUserReqs);
				tempneededRequestTypes=loginUserReqs;
			}
		}
		
		if (exactDateFrom != null && exactDateTo != null){
			if (!exactDateFrom.equals("") && !exactDateTo.equals("") ) {
				
				Date fromDate = null;
				Date toDate = null;
				log.debug(">>>>>>>>>>>>> if ");
				log.debug(">>>>>>>>>>>Valid date format");
				log.debug(">>>>>>>>>>>>>fromDateString "+ exactDateFrom);
				//fromDateStr = fromDateString +" 00:00";
				mCalDate.setDateTimeString(exactDateFrom,new Boolean(true));
				fromDate = mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
				log.debug(">>>>>>>>>>>>>toDateString "+ exactDateTo);
				//toDateStr = toDateString+ " 23:59";
				mCalDate.setDateTimeString(exactDateTo,new Boolean(false));
				toDate= mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>exactDateTo "+ toDate);
				
				List loginUserReqs=(List) requestsApprovalManager.getRequestsByExactDatePeriodAndRequestType(fromDate, toDate,Long.parseLong(requestType));
				log.debug("--dateList.size--"+loginUserReqs.size());
				//model.put("loginUserReqs", loginUserReqs);
				tempneededRequestTypes=loginUserReqs;
			}
		}
		
		
		
		if(emp_code!=null  && dateFrom!=null && dateTo!=null && codeFrom!=null && codeTo!=null && statusId!=null){
			if((emp_code.equals(""))&&(dateFrom.equals(""))&&(dateTo.equals(""))&&(codeTo.equals(""))&&(codeFrom.equals(""))&&(statusId.equals(""))){
				List allRequests=requestsApprovalManager.getRequestsByDatePeriodAndRequestType(null,null, Long.parseLong(requestType));
				//model.put("loginUserReqs", allRequests);
				tempneededRequestTypes=allRequests;
			}
		}
		
		
		
		
		if(emp_code!=null  && dateFrom!=null && dateTo!=null){
			if((!emp_code.equals(""))&&(!dateFrom.equals(""))&&(!dateTo.equals(""))){
				
				Date fromDate = null;
				Date toDate = null;
				mCalDate.setDateTimeString(dateFrom,new Boolean(true));
				fromDate = mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
				log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
				//toDateStr = toDateString+ " 23:59";
				mCalDate.setDateTimeString(dateTo,new Boolean(false));
				toDate= mCalDate.getDate();
			
				List allRequests= requestsApprovalManager.getRequestsByDatePeriodAndRequestTypeAndEmpCode(fromDate, toDate, new Long(requestType), emp_code);
				//model.put("loginUserReqs", allRequests);
				tempneededRequestTypes=allRequests;
			}
		}
		
		if(emp_code!=null  && exactDateFrom!=null && exactDateTo!=null){
			if((!emp_code.equals(""))&&(!exactDateFrom.equals(""))&&(!exactDateTo.equals(""))){
				
				Date fromDate = null;
				Date toDate = null;
				mCalDate.setDateTimeString(exactDateFrom,new Boolean(true));
				fromDate = mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
				log.debug(">>>>>>>>>>>>>toDateString "+ exactDateTo);
				//toDateStr = toDateString+ " 23:59";
				mCalDate.setDateTimeString(exactDateTo,new Boolean(false));
				toDate= mCalDate.getDate();
			
				List allRequests= requestsApprovalManager.getRequestsByExactDatePeriodAndRequestTypeAndEmpCode(fromDate, toDate, new Long(requestType), emp_code);
				//model.put("loginUserReqs", allRequests);
				tempneededRequestTypes=allRequests;
			}
		}
		
		//Lotus //////////////////////////////////
		List list=new ArrayList();
		list.add("empCode");
		/////////////////////////////////////////
		
		if(statusId!=null && !statusId.equals("")){
			List requests=requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(LoginUsersRequests.class, "approved", new Long(statusId),"request_id.id",new Long(requestType),list	);
			//model.put("loginUserReqs", allRequests);
			tempneededRequestTypes=requests;
		}
		
		List actualRequest= new ArrayList();
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
		List<String> ordered= new ArrayList();
		ordered.add("order");
		List<String> ordered1= new ArrayList();
		ordered1.add("emp_id");
		List approval=new ArrayList();
		EmpReqApproval empReqApproval=null;
		for(int i=0;i<tempneededRequestTypes.size() ;i++){
			boolean flag=true;
			LoginUsersRequests temp=(LoginUsersRequests)tempneededRequestTypes.get(i);
			log.debug("-----temp- code---"+temp.getEmpCode());
			log.debug("-----req ID---"+temp.getId());
			List allRequests= requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(EmpReqTypeAcc.class, "req_id", temp.getRequest_id() , "emp_id", temp.getLogin_user(), ordered);
			
			log.debug("------allRequests.size()--------"+allRequests.size());
			
			if(allRequests.size()>0){
				log.debug("------allRequests.size()>0---");
				for(int j=0;j<allRequests.size() ;j++){
					EmpReqTypeAcc tempEmpReqTypeAcc=(EmpReqTypeAcc)allRequests.get(j);
//					try{
//						approval =  requestsApprovalManager.getObjectsByParameter(EmpReqApproval.class, "req_id", temp);
//						log.debug("------approval.size-----"+approval.size());
//						if(approval.size()>0){
//							for (int k = 0; k <approval.size(); k++) {
//								empReqApproval=(EmpReqApproval) approval.get(k);	
//
//							}
//	
//						}
//					}
//					catch (Exception e) {
//						// TODO: handle exception
//					}
					List accessLevels= requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(AccessLevels.class, "level_id",tempEmpReqTypeAcc.getGroup_id() , "emp_id", loginUsers, ordered1);
					
					
					if(accessLevels.size()>0&&flag){
						log.debug("-------my order---"+tempEmpReqTypeAcc.getOrder());
						//if(tempEmpReqTypeAcc.getOrder()==1){
							actualRequest.add(temp);
							flag=false;
						//}
					}	
					
					else{
						//log.debug("------empreqapp-----"+empReqApproval.getId());
						
						//log.debug("------id ele wafe2-----"+empReqApproval.getUser_id().getId());
					}					
					
//					if(empReqApproval==null || empReqApproval.equals("")){
//						log.debug("------empreqapp-----"+empReqApproval.getId());
//						
//						log.debug("------id ele wafe2-----"+empReqApproval.getUser_id().getId());
//					
//						List accessLevels= requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(AccessLevels.class, "level_id",tempEmpReqTypeAcc.getGroup_id() , "emp_id", loginUsers, ordered1);
//						
//						
//						if(accessLevels.size()>0&&flag){
//							log.debug("-------my order---"+tempEmpReqTypeAcc.getOrder());
//							if(tempEmpReqTypeAcc.getOrder()==1){
//								actualRequest.add(temp);
//								flag=false;
//							}//20120000069
//						}
//					
//					}	
					
				}
			}
		}
		
		log.debug("-----actualRequest---size-"+actualRequest.size());
		for (int i = 0; i < actualRequest.size(); i++) {
			LoginUsersRequests s=(LoginUsersRequests) actualRequest.get(i);
			log.debug("-----actualRequest----"+s.getEmpCode());
		}
		List allRequests= new ArrayList();
		List errandRequests= new ArrayList();
		//List allRequests= requestsApprovalManager.getObjectsByParameter(EmpReqTypeAcc.class, "request_id.id", Long.parseLong(requestType));
				
		for (int i = 0; i < actualRequest.size(); i++) {
			LoginUsersRequests req=(LoginUsersRequests) actualRequest.get(i);
			if((req.getRequest_id().getId()==1 && req.getVacation().getVacation().equals("999")) || req.getRequest_id().getId()==4){
				log.debug("-----m2morea----");
				errandRequests.add(req);
			}else{
				allRequests.add(req);
			}
		}
		String errand=request.getParameter("errand");
		model.put("errand", errand);
		if(errand!=null && !errand.equals("")){
			model.put("loginUserReqs", errandRequests);
		}
		else{
			model.put("loginUserReqs", allRequests);
		}	

		
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
		return new ModelAndView(new RedirectView(getSuccessView()));
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
