package com._4s_.requestsApproval.web.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.model.Requests;
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
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		log.debug("---ref-emp from session---"+request.getSession().getAttribute("employee"));
		
		fields.add("period_from");
		//fields.add("empCode");
		Map model=new HashMap();
		//model.put("employee", emp);

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
				List loginUserReqs=(List) requestsApprovalManager.getRequestsByDatePeriodForEmployee(fromDate, toDate,emp.getEmpCode());
				log.debug("--dateList.size--"+loginUserReqs.size());
				for (int i = 0; i < loginUserReqs.size(); i++) {
					LoginUsersRequests loginUsersRequests=(LoginUsersRequests) loginUserReqs.get(i);
					model.put("empRequestTypeId", loginUsersRequests.getId());
				}
				
				List reList = new ArrayList();
//				for (int i = 0; i < loginUserReqs.size(); i++) {
//					LoginUsersRequests requestTypes = (LoginUsersRequests) loginUserReqs.get(i);
//					if(!requestTypes.getRequest_id().getId().equals(new Long(10)) && !requestTypes.getRequest_id().getId().equals(new Long(11))){
//						reList.add(requestTypes);
//					}
//					
//				}
				model.put("records", loginUserReqs);
				
				
			}
		}
		
		String requestType=request.getParameter("requestType");
		
		if(requestType!=null && !requestType.equals("")){
			List userReqs=new ArrayList();
			List reqs;
			if(requestType.equals("4")){
				 reqs=new ArrayList();
				userReqs=requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(LoginUsersRequests.class, "request_id.id", new Long(1),"empCode",emp.getEmpCode(),fields);
				for (int i = 0; i < userReqs.size(); i++) {
					LoginUsersRequests req=(LoginUsersRequests) userReqs.get(i);
					if(req.getVacation().getVacation().equals("999")){
						model.put("empRequestTypeId", req.getId());
						
						reqs.add(req);
						
					}
				}
				
			}else{
				 reqs=new ArrayList();
				List loginUserReqs= new ArrayList();
				loginUserReqs=requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(LoginUsersRequests.class, "request_id.id", new Long(requestType),"empCode",emp.getEmpCode(),fields);
				for (int i = 0; i < loginUserReqs.size(); i++) {
					LoginUsersRequests loginUsersRequests=(LoginUsersRequests) loginUserReqs.get(i);
					if(loginUsersRequests.getVacation()!=null && !loginUsersRequests.getVacation().equals("")){
						if(loginUsersRequests.getVacation().getVacation().equals("999")){
							
						}else{
							reqs.add(loginUsersRequests);
							model.put("empRequestTypeId", loginUsersRequests.getId());
						}
					}
					else{
						reqs.add(loginUsersRequests);
						model.put("empRequestTypeId", loginUsersRequests.getId());
					}
				}
				
			}
			
			List reList = new ArrayList();
//			for (int i = 0; i < reqs.size(); i++) {
//				LoginUsersRequests requestTypes = (LoginUsersRequests) reqs.get(i);
//				if(!requestTypes.getRequest_id().getId().equals(new Long(10)) && !requestTypes.getRequest_id().getId().equals(new Long(11))){
//					reList.add(requestTypes);
//				}
//				
//			}
			model.put("records", reqs);
						
			
		}
		
//		
//		if(requestType!=null  && dateFrom!=null && dateTo!=null){
//			if((requestType.equals(""))&&(dateFrom.equals(""))&&(dateTo.equals(""))){
//				List fieldList=new ArrayList();
//				fieldList.add("empCode");
//				List allRequests=requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(LoginUsersRequests.class, "request_id.id", Long.parseLong(requestType), "empCode", emp.getEmpCode(), fieldList);
//				model.put("records", allRequests);
//			}
//		}
		
		
		if(requestType!=null  && dateFrom!=null && dateTo!=null){
			if((!requestType.equals(""))&&(!dateFrom.equals(""))&&(!dateTo.equals(""))){
				
				Date fromDate = null;
				Date toDate = null;
				mCalDate.setDateTimeString(dateFrom,new Boolean(true));
				fromDate = mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
				log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
				//toDateStr = toDateString+ " 23:59";
				mCalDate.setDateTimeString(dateTo,new Boolean(false));
				toDate= mCalDate.getDate();
				List all=new ArrayList();
				List allRequests=new ArrayList();
				if(!requestType.equals("4")){
					allRequests= requestsApprovalManager.getRequestsByDatePeriodAndRequestTypeForEmployee(fromDate, toDate, new Long(requestType),emp.getEmpCode());
					for (int i = 0; i < allRequests.size(); i++) {
						LoginUsersRequests loginUsersRequests=(LoginUsersRequests) allRequests.get(i);
						if(loginUsersRequests.getVacation()!=null && !loginUsersRequests.getVacation().equals("")){
							if(!loginUsersRequests.getVacation().getVacation().equals("999")){
								all.add(loginUsersRequests);
								model.put("empRequestTypeId", loginUsersRequests.getId());
							}
						}
					}
				}
				else{
					allRequests= requestsApprovalManager.getRequestsByDatePeriodAndRequestTypeForEmployee(fromDate, toDate, new Long(1),emp.getEmpCode());
					for (int i = 0; i < allRequests.size(); i++) {
						LoginUsersRequests loginUsersRequests=(LoginUsersRequests) allRequests.get(i);
						
						if((loginUsersRequests.getRequest_id().getId()==1)&& (loginUsersRequests.getVacation().getVacation().equals("999"))){
							all.add(loginUsersRequests);
							model.put("empRequestTypeId", loginUsersRequests.getId());
						}
					}
				}
				List reList = new ArrayList();
//				for (int i = 0; i < all.size(); i++) {
//					LoginUsersRequests requestTypes = (LoginUsersRequests) all.get(i);
//					if(!requestTypes.getRequest_id().getId().equals(new Long(10)) && !requestTypes.getRequest_id().getId().equals(new Long(11))){
//						reList.add(requestTypes);
//					}
//					
//				}
				model.put("records", all);

				
			}
		}
				
		
		
		if(requestType!=null  && dateFrom!=null && dateTo!=null){
			if((requestType.equals(""))&&(dateFrom.equals(""))&&(dateTo.equals(""))){
				List requests=requestsApprovalManager.getObjectsByParameterOrderedByFieldList(LoginUsersRequests.class, "empCode", emp.getEmpCode(), fields);
				log.debug("-------requests.size---before"+requests.size());
				List reqsToBeViewed= new ArrayList();
				for (int i = 0; i < requests.size(); i++) {
					LoginUsersRequests loginUsersRequests=(LoginUsersRequests) requests.get(i);
					log.debug("----i----"+loginUsersRequests.getEmpCode());
					log.debug("----req id----"+loginUsersRequests.getId());
					
					if(loginUsersRequests.getRequest_id().getId()==3){
						loginUsersRequests.setFrom_date(null);
						loginUsersRequests.setTo_date(null);
					}
					if(loginUsersRequests.getRequest_id().getId()==1 ||loginUsersRequests.getRequest_id().getId()==2){
						loginUsersRequests.setPeriod_from(loginUsersRequests.getFrom_date());
						loginUsersRequests.setPeriod_to(loginUsersRequests.getTo_date());
						
						//Lotus ///////////////////////////////////////
//						loginUsersRequests.setPeriod_from(null);
//						loginUsersRequests.setPeriod_to(null);
						////////////////////////////////////////////////
					}
					
					if(loginUsersRequests.getLogin_user().getEndServ()==null || loginUsersRequests.getLogin_user().getEndServ().equals("")){
						log.debug("---before removing--i----"+loginUsersRequests.getEmpCode());
						reqsToBeViewed.add(loginUsersRequests);
						model.put("empRequestTypeId", loginUsersRequests.getId());
//						log.debug("----login----"+requests.get(i));
						
					}
				}
				log.debug("-------reqsToBeViewed.size---after"+reqsToBeViewed.size());
//				for (int i = 0; i < requests.size(); i++) {
//					LoginUsersRequests loginUsersRequests=(LoginUsersRequests) requests.get(i);
//					SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/mm/dd");
//					dateFormat.get
//					loginUsersRequests.getRequest_date()
//				}
				System.out.println("requests.size>>>>>>>>>>"+requests.size());
				List reList = new ArrayList();
//				for (int i = 0; i < reqsToBeViewed.size(); i++) {
//					LoginUsersRequests requestTypes = (LoginUsersRequests) reqsToBeViewed.get(i);
//					if(!requestTypes.getRequest_id().getId().equals(new Long(10)) && !requestTypes.getRequest_id().getId().equals(new Long(11))){
//						reList.add(requestTypes);
//					}
//					
//				}
				model.put("records", reqsToBeViewed);
						
			}
		}
			
				
		
		model.put("request_id", requestType);
		model.put("dateFrom", dateFrom);
		model.put("dateTo", dateTo);
		List requests = requestsApprovalManager.getObjects(RequestTypes.class);
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
		return new ModelAndView("loginUsersRequestsView",model);
	}
	
}



