package com._4s_.requestsApproval.web.action;

import java.text.DateFormat;
import java.text.ParseException;
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

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.AccessLevels;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;


public class ReportsController extends BaseSimpleFormController{
	RequestsApprovalManager requestsApprovalManager;
	List<String> ordered1= new ArrayList<String>();
	
	
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
		Map model=new HashMap();

		String dateFrom = request.getParameter("dateFrom");
		log.debug("--dateFrom--"+dateFrom);
		model.put("request_date_from", dateFrom);
		String dateTo = request.getParameter("dateTo");
		log.debug("--dateTo--"+dateTo);
		model.put("request_date_to", dateTo);
		log.debug("---xxxxxxxDatePeriod--");
		String codeFrom=request.getParameter("codeFrom");
		String codeTo=request.getParameter("codeTo");
		String requestType= request.getParameter("requestType");
		log.debug("--requestType--"+requestType);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		model.put("codeFrom", codeFrom);
		model.put("codeTo", codeTo);
		String exportParameter = (String)request.getParameter("export");
		log.debug("export " + exportParameter);
		model.put("export", exportParameter);
		
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
		
		List all = new ArrayList();
		List tableTitle = new ArrayList();
		List results = new ArrayList();
		
		Map model=new HashMap();		
		
		String dateFrom = request.getParameter("request_date_from");
		log.debug("--dateFrom--"+dateFrom);
		model.put("request_date_from", dateFrom);
		String dateTo = request.getParameter("request_date_to");
		log.debug("--dateTo--"+dateTo);
		model.put("request_date_to", dateTo);
		log.debug("---xxxxxxxDatePeriod--");
		String codeFrom=request.getParameter("codeFrom");
		String codeTo=request.getParameter("codeTo");
		String requestType= request.getParameter("requestType");
		log.debug("--requestType--"+requestType);
		//model.put("request_id", requestType);
		
		if(requestType!=null && dateFrom!=null && dateTo!=null && codeFrom!=null && codeTo!=null){
			if((requestType.equals(""))&&(dateFrom.equals(""))&&(dateTo.equals(""))&&(codeTo.equals(""))&&(codeFrom.equals(""))){
				tempneededRequestTypes=new ArrayList();
				//List<String> fields=new ArrayList<String>();
				//fields.add("period_from");
				List allRequests=requestsApprovalManager.getObjectsOrderedByField(LoginUsersRequests.class,"period_from");
				//model.put("loginUserReqs", allRequests);
				tempneededRequestTypes=allRequests;
			}
		} 
		

		model.put("codeFrom", codeFrom);
		model.put("codeTo", codeTo);
		if(codeFrom!=null && codeTo!=null && !codeFrom.equals("")&& !codeTo.equals("")){
			tempneededRequestTypes=new ArrayList();
			List loginUserReqs=(List) requestsApprovalManager.getEmployeesByCodes(codeFrom, codeTo);
			log.debug("---codesList---"+loginUserReqs.size());
			tempneededRequestTypes=loginUserReqs;
			//model.put("records", loginUserReqs);
			for (int i = 0; i < loginUserReqs.size(); i++) {
				LoginUsersRequests s=(LoginUsersRequests) loginUserReqs.get(i);
				log.debug("---code code---"+s.getEmpCode());
			}
		}
		
		MultiCalendarDate mCalDate = new MultiCalendarDate();


		if (dateFrom != null && dateTo != null){
			if (!dateFrom.equals("") && !dateTo.equals("") ) {
				tempneededRequestTypes=new ArrayList();
				Date fromDate = null;
				Date toDate = null;
				log.debug(">>>>>>>>>>>>> if ");
				log.debug(">>>>>>>>>>>Valid date format");
				log.debug(">>>>>>>>>>>>>fromDateString "+ dateFrom);
				//fromDateStr = fromDateString +" 00:00";
//				mCalDate.setDateTimeString(dateFrom,new Boolean(true));
//				fromDate = mCalDate.getDate();
				
				DateFormat formatInput =	new SimpleDateFormat("dd/MM/yyyy");// HH:mm:ss
				DateFormat formatOutput =	new SimpleDateFormat("yyyy/MM/dd");// HH:mm:ss
				
				try {
					fromDate = formatInput.parse(dateFrom);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
				log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
				//toDateStr = toDateString+ " 23:59";
//				mCalDate.setDateTimeString(dateTo,new Boolean(false));
//				toDate= mCalDate.getDate();
				
				try {
					toDate = formatInput.parse(dateTo);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				log.debug(">>>>>>>>>>>>>toDate "+ toDate);
				List loginUserReqs=(List) requestsApprovalManager.getRequestsByDatePeriod(fromDate, toDate);
//				if(fromDate.compareTo(toDate)==0){
//					loginUserReqs.add(requestsApprovalManager.getObjectByParameter(LoginUsersRequests.class, "request_date", fromDate));
//				}
				log.debug("--dateList.size--"+loginUserReqs.size());
				//model.put("loginUserReqs", loginUserReqs);
				tempneededRequestTypes=loginUserReqs;
				
				if(codeFrom!=null && codeTo!=null && !codeFrom.equals("")&& !codeTo.equals("")){
					List reqs=(List) requestsApprovalManager.getEmployeesByCodesAndDatePeriod(codeFrom, codeTo, fromDate, toDate);
					log.debug("---codesList---"+loginUserReqs.size());
					tempneededRequestTypes=reqs;
					//model.put("records", loginUserReqs);
					for (int i = 0; i < loginUserReqs.size(); i++) {
						LoginUsersRequests s=(LoginUsersRequests) loginUserReqs.get(i);
//						log.debug("---code code---"+s.getEmpCode());
					}
				}
			}
		}

		if(requestType!=null && dateFrom!=null && dateTo!=null && codeFrom!=null && codeTo!=null){
			if((!requestType.equals(""))&&(!dateFrom.equals(""))&&(!dateTo.equals("")) &&(!codeFrom.equals("")) && (!codeTo.equals(""))){
				log.debug("---------here------kkk"+tempneededRequestTypes.size());
				Date fromDate = null;
				Date toDate = null;
				mCalDate.setDateTimeString(dateFrom,new Boolean(true));
				fromDate = mCalDate.getDate();
				mCalDate.setDateTimeString(dateTo,new Boolean(false));
				toDate= mCalDate.getDate();
				if(!requestType.equals("4")){
					tempneededRequestTypes=new ArrayList();
					log.debug("----size after----"+tempneededRequestTypes.size());
					List requests=requestsApprovalManager.getEmployeesByCodesAndDatePeriodAndRequestType(codeFrom, codeTo,fromDate, toDate, new Long(requestType));
					log.debug("sie of sent list--- "+requests.size());
					List filtered= new ArrayList();
					for (int i = 0; i < requests.size(); i++) {
						LoginUsersRequests requ=(LoginUsersRequests) requests.get(i);
						if(requ.getVacation()!=null && !requ.getVacation().equals("")){
							if(!requ.getVacation().getVacation().endsWith("999")){
								filtered.add(requ);
							}
						}
						else{
							filtered.add(requ);
						}
					}
					tempneededRequestTypes=filtered;
				}
				else{
					tempneededRequestTypes=new ArrayList();
					List allRequests= requestsApprovalManager.getRequestsByDatePeriodAndRequestType(fromDate, toDate, new Long(1));
					for (int i = 0; i < allRequests.size(); i++) {
						LoginUsersRequests requests=(LoginUsersRequests) allRequests.get(i);
						if((requests.getRequest_id().getId()==1)&& (requests.getVacation().getVacation().equals("999"))){
							tempneededRequestTypes=allRequests;
						}
					}
				}
				
			}
		}
		
		if(requestType!=null && !requestType.equals("")){
			log.debug("----reqtype---"+dateFrom.equals("")+"****"+ dateTo.equals(""));
			if ((dateFrom.equals("") && dateTo.equals("")) ) {
				if(requestType.equals("4")){
					List errands=new ArrayList();
					tempneededRequestTypes=new ArrayList();
					List<String> fields=new ArrayList<String>();
					fields.add("period_from");
					tempneededRequestTypes=requestsApprovalManager.getObjectsByParameterOrderedByFieldList(LoginUsersRequests.class, "request_id.id", new Long(1), fields);
					for (int i = 0; i < tempneededRequestTypes.size(); i++) {
						LoginUsersRequests req=(LoginUsersRequests) tempneededRequestTypes.get(i);
						if(req.getVacation().getVacation().equals("999")){
							errands.add(req);
						}
					}tempneededRequestTypes=errands;
				}
				else{
					log.debug("----reqtype-!4--");
					List filtered= new ArrayList();
					tempneededRequestTypes=new ArrayList();
					List<String> fields=new ArrayList<String>();
					fields.add("period_from");
					tempneededRequestTypes=requestsApprovalManager.getObjectsByParameterOrderedByFieldList(LoginUsersRequests.class, "request_id.id", new Long(requestType), fields);
					for (int i = 0; i < tempneededRequestTypes.size(); i++) {
						LoginUsersRequests req=(LoginUsersRequests) tempneededRequestTypes.get(i);
						if(req.getVacation()!=null && !req.getVacation().equals("")){
							if(!req.getVacation().getVacation().equals("999")){
								log.debug("----ay7aga---");
								filtered.add(req);
							}
						}else{
							filtered.add(req);
						}
					}
					tempneededRequestTypes=filtered;
				}
			
				if((codeFrom!=null && !codeFrom.equals("")) && (codeTo!=null && !codeTo.equals(""))){
					if(requestType.equals("4")){
						List errands=new ArrayList();
						tempneededRequestTypes=new ArrayList();
						tempneededRequestTypes=requestsApprovalManager.getEmployeesByCodesAndRequestType(codeFrom, codeTo,  new Long(1));
						for (int i = 0; i < tempneededRequestTypes.size(); i++) {
							LoginUsersRequests req=(LoginUsersRequests) tempneededRequestTypes.get(i);
							if(req.getVacation().getVacation().equals("999")){
								errands.add(req);
							}
						}tempneededRequestTypes=errands;
					}
					else{
						log.debug("----reqtype-!4--");
						List filtered= new ArrayList();
						tempneededRequestTypes=new ArrayList();
						tempneededRequestTypes=requestsApprovalManager.getEmployeesByCodesAndRequestType(codeFrom, codeTo, new Long(requestType));
						for (int i = 0; i < tempneededRequestTypes.size(); i++) {
							LoginUsersRequests req=(LoginUsersRequests) tempneededRequestTypes.get(i);
							if(req.getVacation()!=null && !req.getVacation().equals("")){
								if(!req.getVacation().getVacation().equals("999")){
									log.debug("----ay7aga---");
									filtered.add(req);
								}
							}else{
								filtered.add(req);
							}
						}
						tempneededRequestTypes=filtered;
					}
				}
			}
			
		}

		List actualRequest= new ArrayList();
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
//		List<String> ordered= new ArrayList();
//		ordered.add("order");
		
		ordered1.add("emp_id");
		for(int i=0;i<tempneededRequestTypes.size() ;i++){
			boolean flag=true;
			LoginUsersRequests temp=(LoginUsersRequests)tempneededRequestTypes.get(i);
			log.debug("-----temp----"+temp.getEmpCode());
			List allRequests= requestsApprovalManager.getObjectsByParameter(EmpReqTypeAcc.class , "emp_id", temp.getLogin_user());
			log.debug("employee requests size " + allRequests.size());
			
			if(allRequests.size()>0)
			for(int j=0;j<allRequests.size() ;j++){
				EmpReqTypeAcc tempEmpReqTypeAcc=(EmpReqTypeAcc)allRequests.get(j);
				List accessLevels= requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(AccessLevels.class, "level_id",tempEmpReqTypeAcc.getGroup_id() , "emp_id", loginUsers, ordered1);
			  log.debug("access levels size " + accessLevels.size());
				if(accessLevels.size()>0&&flag){
				actualRequest.add(temp);
				flag=false;
				
				}
			}
		}
		
		log.debug("-----actualRequest----"+actualRequest.size());
		for (int i = 0; i < actualRequest.size(); i++) {
			LoginUsersRequests s=(LoginUsersRequests) actualRequest.get(i);
			log.debug("-----actualRequest----"+s.getEmpCode());
		}
		
		//List allRequests= requestsApprovalManager.getObjectsByParameter(EmpReqTypeAcc.class, "request_id.id", Long.parseLong(requestType));
		List allRequests = new ArrayList();
//		for (int i = 0; i < actualRequest.size(); i++) {
//			LoginUsersRequests usersRequests = (LoginUsersRequests) actualRequest.get(i);
//			if(!usersRequests.getRequest_id().getId().equals(new Long(10)) && !usersRequests.getRequest_id().getId().equals(new Long(11))){
//				allRequests.add(usersRequests);
//			}
//		}
		model.put("records", actualRequest);
		
		List requests = requestsApprovalManager.getObjects(RequestTypes.class);
		List reList = new ArrayList();
		
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		boolean empRequestTypeException = settings.getEmpRequestTypeException();
		if (empRequestTypeException == true){
			for (int i = 0; i < requests.size(); i++) {
				RequestTypes requestTypes = (RequestTypes) requests.get(i);
				if(!requestTypes.getId().equals(new Long(10)) && !requestTypes.getId().equals(new Long(11))){
					reList.add(requestTypes);
				}
			}
			model.put("requestTypeList",reList);
		} else {
			model.put("requestTypeList",requests);
		}
		
		
		String exportParameter = (String)request.getParameter("export");
		log.debug("export " + exportParameter);
		if (exportParameter!=null && exportParameter.equals("true")) {
			
			
			tableTitle.add("requestsApproval.caption.userCode");
			tableTitle.add("requestsApproval.caption.userName");
			tableTitle.add("requestsApproval.caption.requestNumber");
			tableTitle.add("requestsApproval.caption.requestType");
			tableTitle.add("requestsApproval.caption.requestDate");
			tableTitle.add("commons.caption.fromDate");
			tableTitle.add("commons.caption.toDate");
			tableTitle.add("requestsApproval.caption.reqPeriod");
			tableTitle.add("commons.caption.from");
			tableTitle.add("commons.caption.to");
			tableTitle.add("requestsApproval.requestsApprovalForm.reqStatus");
			tableTitle.add("requestsApproval.caption.reply");
			
			Iterator itr = actualRequest.iterator();
			log.debug("records size again " + actualRequest.size());
			while(itr.hasNext()) {
				LoginUsersRequests req = (LoginUsersRequests)itr.next();
				log.debug("looping requests");
				List temp = new ArrayList();
				temp.add(req.getEmpCode());
				temp.add(req.getLogin_user().getName());
				temp.add(req.getRequestNumber());
				
				if (req.getRequest_id().getId().equals(new Long(1)) && req.getVacation().getVacation().equals("999")) {
					temp.add("مأمورية");
				} else {
					temp.add(req.getRequest_id().getDescription());
				}
				///////////////////////////////////////
				Calendar cal=Calendar.getInstance();
				cal.setTime(req.getRequest_date());
				Date currentDate = cal.getTime();
				int dd = currentDate.getDate();
				int mm = currentDate.getMonth() + 1;
				int yy = currentDate.getYear();

				String dateString= dd + "/" + mm + "/" + (yy+1900);
				temp.add(dateString);
				/////////////////////////////////////
				if (req.getFrom_date()!=null) {
					cal.setTime(req.getFrom_date());
					currentDate = cal.getTime();
					dd = currentDate.getDate();
					mm = currentDate.getMonth() + 1;
					yy = currentDate.getYear();


					dateString= dd + "/" + mm + "/" + (yy+1900);
					temp.add(dateString);
				} else {
					temp.add("");
				}
				////////////////////////////////////
				if (req.getTo_date()!=null) {
				cal.setTime(req.getTo_date());
				currentDate = cal.getTime();
				dd = currentDate.getDate();
				mm = currentDate.getMonth() + 1;
				yy = currentDate.getYear();

				dateString= dd + "/" + mm + "/" + (yy+1900);
				temp.add(dateString);
				} else {
					temp.add("");
				}
				////////////////////////////////////
				if (req.getWithdrawDays()!=null){
					temp.add(req.getWithdrawDays());
				} else {
					temp.add("");
				}
				///////////////////////////////////
				if (req.getPeriod_from() != null) {
					cal.setTime(req.getPeriod_from());
					currentDate = cal.getTime();
					dd = currentDate.getDate();
					mm = currentDate.getMonth() + 1;
					yy = currentDate.getYear();

					dateString= dd + "/" + mm + "/" + (yy+1900);
					temp.add(dateString);
				} else {
					temp.add("");
				}
				////////////////////////////////////
				if (req.getPeriod_to() != null) {
					cal.setTime(req.getPeriod_to());
					currentDate = cal.getTime();
					dd = currentDate.getDate();
					mm = currentDate.getMonth() + 1;
					yy = currentDate.getYear();

					dateString= dd + "/" + mm + "/" + (yy+1900);
					temp.add(dateString);
				} else {
					temp.add("");
				}
				////////////////////////////////////
				if (req.getApproved()!=null && req.getApproved().equals(new Long(1))){
					temp.add("requestsApproval.requestsApprovalForm.reqApproval");
				} else if (req.getApproved()!=null && req.getApproved().equals(new Long(99))) {
					temp.add("requestsApproval.requestsApprovalForm.reqRejected");
				}  else if (req.getApproved()!=null && req.getApproved().equals(new Long(99))) {
					temp.add("requestsApproval.requestsApprovalForm.reqRejected");
				}  else {
					temp.add("لم تكتمل");
				}
				/////////////////////////////////////
				if (req.getReply()!=null) {
					temp.add(req.getReply());
				} else {
					temp.add("");
				}
				log.debug("adding to results");
				results.add(temp);
			}
			log.debug("results size " + results.size());
			log.debug("table title " + tableTitle);
			
			requestsApprovalManager.exportToExcelSheet("requestsApproval.menu.allReports", tableTitle, results, response);
		}
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView("reports",model);
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
