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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.service.RequestsApprovalManager;


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
		
		if (dateFrom==null || dateFrom.isEmpty()) {
			dateFrom = request.getParameter("request_date_from");
		}
		String dateTo = request.getParameter("dateTo");
		log.debug("--dateTo--"+dateTo);
		if (dateTo==null || dateTo.isEmpty()) {
			dateTo = request.getParameter("request_date_to");
		}
		
		model.put("request_date_from", dateFrom);
		model.put("request_date_to", dateTo);
		log.debug("---xxxxxxxDatePeriod--");
		String codeFrom=request.getParameter("codeFrom");
		String codeTo=request.getParameter("codeTo");
		String requestType= request.getParameter("requestType");
		log.debug("--requestType--"+requestType);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
//		RequestTypes requestTypeObject = null;
		if (requestType==null || requestType.isEmpty()) {
			requestType = null;
		}
		List empReqTypeAccs = requestsApprovalManager.getEmpReqTypeAcc(emp, requestType);
		
		MultiCalendarDate mCalDate = new MultiCalendarDate();
		Date fromDate = null;
		Date toDate = null;
		
		DateFormat formatInput =	new SimpleDateFormat("dd/MM/yyyy");// HH:mm:ss
		DateFormat formatOutput =	new SimpleDateFormat("yyyy/MM/dd");// HH:mm:ss
		
		if (dateFrom != null && !dateFrom.isEmpty()){
			try {
				fromDate = formatInput.parse(dateFrom);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
			log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
		}
		if (dateTo != null && !dateTo.isEmpty()){
			try {
				toDate = formatInput.parse(dateTo);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String pageString = request.getParameter("page");
		int pageNumber;
		if (pageString != null && !pageString.equals("")){
			pageNumber = new Long(pageString).intValue();
		}   
		else{
			pageNumber = 0;
		}
		if(requestType== null || !requestType.equals("4")) {
			model = requestsApprovalManager.getRequestsForApproval(null,null,dateFrom,dateTo,null,null,requestType,codeFrom,codeTo,null,"desc",loginUsers, empReqTypeAccs,true,null,pageNumber,10);
		} else {
			model = requestsApprovalManager.getRequestsForApproval(null,null,dateFrom,dateTo,null,null,"12",codeFrom,codeTo,null,"desc",loginUsers, empReqTypeAccs,true,null,pageNumber,10);
		}
		model.put("codeFrom", codeFrom);
		model.put("codeTo", codeTo);
		model.put("request_date_from", dateFrom);
		model.put("request_date_to", dateTo);
		model.put("requestType", requestType);
		
		model.put("pageNumber", pageNumber);
		
		log.debug("requestType " + requestType);
		log.debug("request date from " + dateFrom);
		log.debug("request date to " + dateTo);
		
//		List requests = requestsApprovalManager.getObjects(RequestTypes.class);
		List orderBy = new ArrayList();
		orderBy.add("id");
		List requests=requestsApprovalManager.getObjectsByParameterOrderedByFieldList(RequestTypes.class,"hidden" , new Integer(0), orderBy);
		
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
		
		String dateTo = request.getParameter("request_date_to");
		log.debug("--dateTo--"+dateTo);
		
		log.debug("---xxxxxxxDatePeriod--");
		String codeFrom=request.getParameter("codeFrom");
		String codeTo=request.getParameter("codeTo");
		String requestType= request.getParameter("requestType");
		log.debug("--requestType--"+requestType);
		
		String exportParameter = (String)request.getParameter("export");
		log.debug("export " + exportParameter);
		
		String pageString = request.getParameter("page");
		int pageNumber;
		if (pageString != null && !pageString.equals("")){
			pageNumber = new Long(pageString).intValue();
		}   
		else{
			pageNumber = 0;
		}

		Employee emp =(Employee) request.getSession().getAttribute("employee");
		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
//		RequestTypes requestTypeObject = null;
		if (requestType==null || requestType.isEmpty()) {
			requestType = null;
		}
		List empReqTypeAccs = requestsApprovalManager.getEmpReqTypeAcc(emp, requestType);
		
		MultiCalendarDate mCalDate = new MultiCalendarDate();
		Date fromDate = null;
		Date toDate = null;
		
		DateFormat formatInput =	new SimpleDateFormat("dd/MM/yyyy");// HH:mm:ss
		DateFormat formatOutput =	new SimpleDateFormat("yyyy/MM/dd");// HH:mm:ss
		
		if (dateFrom != null && !dateFrom.isEmpty()){
			try {
				fromDate = formatInput.parse(dateFrom);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
			log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
		}
		if (dateTo != null && !dateTo.isEmpty()){
			try {
				toDate = formatInput.parse(dateTo);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(requestType== null || !requestType.equals("4")) {
			model = requestsApprovalManager.getRequestsForApproval(null,null,dateFrom,dateTo,null,null,requestType,codeFrom,codeTo,null,"desc",loginUsers, empReqTypeAccs,true,null,pageNumber,80);
			Integer resultsSize = (Integer)model.get("listSize");
			if (exportParameter!=null && exportParameter.equals("true")) {
				model = requestsApprovalManager.getRequestsForApproval(null,null,dateFrom,dateTo,null,null,requestType,codeFrom,codeTo,null,"desc",loginUsers, empReqTypeAccs,true,null,0,resultsSize);
			}
		} else {
			model = requestsApprovalManager.getRequestsForApproval(null,null,dateFrom,dateTo,null,null,"12",codeFrom,codeTo,null,"desc",loginUsers, empReqTypeAccs,true,null,pageNumber,80);
			Integer resultsSize = (Integer)model.get("listSize");
			if (exportParameter!=null && exportParameter.equals("true")) {
				model = requestsApprovalManager.getRequestsForApproval(null,null,dateFrom,dateTo,null,null,requestType,codeFrom,codeTo,null,"desc",loginUsers, empReqTypeAccs,true,null,0,resultsSize);
			}
		}
		
		model.put("codeFrom", codeFrom);
		model.put("codeTo", codeTo);
		model.put("request_date_from", dateFrom);
		model.put("request_date_to", dateTo);
		model.put("requestType", requestType);
		
		log.debug("requestType " + requestType);
		log.debug("request date from " + dateFrom);
		log.debug("request date to " + dateTo);
		
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
			
			Iterator itr = ((List)(model.get("results"))).iterator();
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
			
			Map result = requestsApprovalManager.exportToExcelSheet("requestsApproval.menu.allReports", tableTitle, results);
			String title = "EmployeesRequests";
			HSSFWorkbook workBook = (HSSFWorkbook)result.get("workbook");
			try {
		    	   response.setHeader("Content-Disposition",
		    			   "attachment; filename=\""+title+".xls\"");
		    	   log.debug(workBook);
		    	   workBook.write(response.getOutputStream());
		    	   log.debug(response);
		    	   response.getOutputStream().flush();
		    	   response.getOutputStream().close();
		    	   log.debug("Response written");
		       } catch (Exception e) {
		    	   // TODO: handle exception
		    	   log.debug("exception " + e);
		    	   e.printStackTrace();
		       }
				log.debug("after export to excel");
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
