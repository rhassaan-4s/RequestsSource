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
import com._4s_.requestsApproval.service.RequestsApprovalManager;

public class TimeAttendanceReport implements Controller{
	protected final Log log = LogFactory.getLog(getClass());
	RequestsApprovalManager requestsApprovalManager;

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
		int year, month;
		Map model=new HashMap();
		model.put("emp", emp);

		String dateFrom = request.getParameter("fromDate");
		log.debug("--dateFrom--"+dateFrom);
		model.put("fromDate", dateFrom);
		String dateTo = request.getParameter("toDate");
		log.debug("--dateTo--"+dateTo);
		model.put("toDate", dateTo);
		log.debug("---xxxxxxxDatePeriod--");		
		
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
		
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		
		boolean tAttRepWithHrsMin = settings.getTAttRepWithHrsMin();
		String server = settings.getServer();
		String service = settings.getService();
		String username = settings.getUsername();
		String password = settings.getPassword();
		
		MultiCalendarDate mCalDate = new MultiCalendarDate();


		if (dateFrom != null && dateTo != null){
			if (!dateFrom.equals("") && !dateTo.equals("") ) {
				Date fromDate = null;
				Date toDate = null;
				log.debug(">>>>>>>>>>>>>fromDateString "+ dateFrom);
				mCalDate.setDateTimeString(dateFrom,new Boolean(true));
				fromDate = mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
				log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
				mCalDate.setDateTimeString(dateTo,new Boolean(false));
				toDate= mCalDate.getDate();
				
				// VIP
				List totalObjects= new ArrayList();
				List objects= new ArrayList();
				totalObjects=requestsApprovalManager.getTimeAttend(emp.getEmpCode(), fromDate, toDate);
				objects=(List) totalObjects.get(0);
				
				//Lehaa/////////////////////////////////////////////////
//				if (tAttRepWithHrsMin == true) {
					
					String totalSum=(String) totalObjects.get(1);
					String [] totalValues=totalSum.split(",");
					log.debug("totalMins=== "+totalValues[0]+" && totalHrs=== "+totalValues[1]);
					String totalMins=totalValues[0];
					String totalHrs=totalValues[1];
					Long hrs=new Long(0), mins=new Long(0);
					hrs= Long.parseLong(totalHrs);
					mins=Long.parseLong(totalMins);
					if(mins>60){
						hrs+=mins/60;
						mins=mins%60;
					} 
					
					log.debug("sent mins=== "+mins+" && sent hrs=== "+hrs);
					
					model.put("totalMins", mins);
					model.put("totalHrs", hrs);
//				} 
				//////////////////////////////////////////////////////////
				
				log.debug("-------objects- size--"+objects.size());
				for (int i = 0; i < objects.size(); i++) {
					TimeAttend ob= (TimeAttend) objects.get(i);
					
					// mCalDate.setDateString(ob.getDay());
					DateFormat df=new SimpleDateFormat("dd/mm/yyyy");
					
					Date day=df.parse(ob.getDay());
					log.debug("-------day---"+day);
					log.debug("-------objects---"+ob.getDay()+"-------getTimeIn---"+ob.getTimeIn()+"-------getTimeOut---"+ob.getTimeOut());
				}
				model.put("records", objects);
				// VIP
				
			}
		}
		
		return new ModelAndView("timeAttendanceReport",model);
	}
	
}
