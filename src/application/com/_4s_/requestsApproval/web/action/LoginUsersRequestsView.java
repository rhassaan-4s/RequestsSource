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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.service.RequestsApprovalManager;

public class LoginUsersRequestsView implements Controller{
	protected final Log log = LogFactory.getLog(getClass());
	RequestsApprovalManager requestsApprovalManager;
	List<String> fields=new ArrayList<String>();
	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		List all=new ArrayList();
		
		
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		log.debug("---ref-emp from session---"+request.getSession().getAttribute("employee"));
		
		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
		
		log.debug("loginUsers " + loginUsers.getId());
		
		fields.add("period_from");
		//fields.add("empCode");
		Map model=new HashMap();
		//model.put("employee", emp);

		
		
		String pageString = request.getParameter("page");
		int pageNumber;
		if (pageString != null && !pageString.equals("")){
			pageNumber = new Long(pageString).intValue();
		}   
		else{
			pageNumber = 0;
		}
		
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
		
		model.put("firstDay", formattedDate);
		Date today=new Date();
		String formatedToday=d.format(today);
		log.debug("----formatedToday---"+formatedToday);
		model.put("today", formatedToday);
		
		String dateFrom = request.getParameter("dateFrom");
		log.debug("--dateFrom--"+dateFrom);
		model.put("request_date_from", dateFrom);
		String dateTo = request.getParameter("dateTo");
		log.debug("--dateTo--"+dateTo);
		model.put("request_date_to", dateTo);
		log.debug("---xxxxxxxDatePeriod--");		
		
		
		MultiCalendarDate mCalDate = new MultiCalendarDate();

		List<String> orderfieldList = new ArrayList();
		orderfieldList.add(new String("order"));
		
//		List empReqAcc = requestsApprovalManager
//				.getObjectsByTwoParametersOrderedByFieldList(
//						EmpReqTypeAcc.class, "req_id", loginUsersRequests
//								.getRequest_id(), "emp_id", loginUsers, orderfieldList);
		
		
		

//		if (dateFrom != null && dateTo != null){
//			if (!dateFrom.equals("") && !dateTo.equals("") ) {
//				Date fromDate = null;
//				Date toDate = null;
//				log.debug(">>>>>>>>>>>>> if ");
//				log.debug(">>>>>>>>>>>Valid date format");
//				log.debug(">>>>>>>>>>>>>fromDateString "+ dateFrom);
//				//fromDateStr = fromDateString +" 00:00";
//				mCalDate.setDateTimeString(dateFrom,new Boolean(true));
//				fromDate = mCalDate.getDate();
//				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
//				log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
//				//toDateStr = toDateString+ " 23:59";
//				mCalDate.setDateTimeString(dateTo,new Boolean(false));
//				toDate= mCalDate.getDate();
//				log.debug(">>>>>>>>>>>>>toDate "+ toDate);
//				List loginUserReqs=(List) requestsApprovalManager.getRequestsByDatePeriodForEmployee(fromDate, toDate,emp.getEmpCode());
//				log.debug("--dateList.size--"+loginUserReqs.size());
//				for (int i = 0; i < loginUserReqs.size(); i++) {
//					LoginUsersRequests loginUsersRequests=(LoginUsersRequests) loginUserReqs.get(i);
//					model.put("empRequestTypeId", loginUsersRequests.getId());
//					
//						log.debug("to date " + loginUsersRequests.getTo_date());
//				}
//				
//				List reList = new ArrayList();
////				for (int i = 0; i < loginUserReqs.size(); i++) {
////					LoginUsersRequests requestTypes = (LoginUsersRequests) loginUserReqs.get(i);
////					if(!requestTypes.getRequest_id().getId().equals(new Long(10)) && !requestTypes.getRequest_id().getId().equals(new Long(11))){
////						reList.add(requestTypes);
////					}
////					
////				}
//				all = loginUserReqs;
//				model.put("records", loginUserReqs);
//				
//				
//			}
//		}
		
		String requestType=request.getParameter("requestType");
		log.debug("request type " + requestType);
		
		String requestTypeLong = null;
		if (requestType!=null && !requestType.isEmpty()) {
			requestTypeLong = requestType;
		}
		
		log.debug("request type " + requestTypeLong);
		
		model = requestsApprovalManager.getSubmittedRequestsForApproval(null,emp.getEmpCode(),dateFrom,dateTo,null,null,requestTypeLong,null,null,null,"desc",loginUsers, null,true,null,pageNumber,80);				
		
		model.put("request_id", requestType);
		model.put("dateFrom", dateFrom);
		model.put("dateTo", dateTo);
		
		List orderBy = new ArrayList();
		orderBy.add("id");
		List requests=requestsApprovalManager.getObjectsByParameterOrderedByFieldList(RequestTypes.class,"hidden" , new Integer(0), orderBy);
		List reList = new ArrayList();
		
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		boolean empRequestTypeException = settings.getEmpRequestTypeException();
		
		
		if (empRequestTypeException == true) { //lotus
			for (int i = 0; i < requests.size(); i++) {
				RequestTypes requestTypes = (RequestTypes) requests.get(i);
				if(!requestTypes.getId().equals(new Long(10)) && !requestTypes.getId().equals(new Long(11))){
					reList.add(requestTypes);
				}
			}
			model.put("requestTypeList",reList );
		} else {//lehaa
			model.put("requestTypeList",requests );
		}
		
		
		String exportParameter = (String)request.getParameter("export");
		if (exportParameter!=null && exportParameter.equals("true")) {
			List tableTitle = new ArrayList();
			
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
			
			List results = new ArrayList();
			Iterator itr = all.iterator();
			log.debug("records size again " + all.size());
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
			Map result = requestsApprovalManager.exportToExcelSheet("requestsApproval.header.loginUsersRequestsView", tableTitle, results);
			String title = "LoggedinUserRequests";
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
			return new ModelAndView(new RedirectView("loginUsersRequestsView.html"));
		}
		
		return new ModelAndView("loginUsersRequestsView",model);
	}
	
}



