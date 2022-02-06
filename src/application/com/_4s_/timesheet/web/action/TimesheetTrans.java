package com._4s_.timesheet.web.action;

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
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.model.TimesheetCostCenter;
import com._4s_.timesheet.model.TimesheetSpecs;
import com._4s_.timesheet.service.TimesheetManager;

public class TimesheetTrans implements Controller{
	protected final Log log = LogFactory.getLog(getClass());
	TimesheetManager timesheetManager;
	List<String> fields=new ArrayList<String>();
	
	public TimesheetManager getTimesheetManager() {
		return timesheetManager;
	}

	public void setTimesheetManager(TimesheetManager timesheetManager) {
		this.timesheetManager = timesheetManager;
	}


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		List all=new ArrayList();
		
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		log.debug("---ref-emp from session---"+request.getSession().getAttribute("employee"));
		
		LoginUsers loginUsers=(LoginUsers) timesheetManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
		
		log.debug("loginUsers " + loginUsers.getId());
		
		
		fields.add("period_from");
		Map model=new HashMap();
		
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
		
		
		///////////////////////////////////////////////////////////
		List specsList = timesheetManager.getObjects(TimesheetSpecs.class);
		Object specs = null;
		if (specsList.size()>0) {
			specs = specsList.get(0);
		}
		TimesheetSpecs availableSpecs = null;
		if (specs!=null) {
			availableSpecs = (TimesheetSpecs)specs;
		}
		//////////////////////////////////////////////////////////
		List activityList = timesheetManager.getObjects(TimesheetActivity.class);
		String selectedActivity=request.getParameter("activity");
		log.debug("selectedActivity " + selectedActivity);
		
		if (selectedActivity!=null && selectedActivity.equals("")) {
			selectedActivity=null;
		}
		
		///////////////////////////////////////////////////////////
		
		List costcenterList = timesheetManager.getObjects(TimesheetCostCenter.class);
		String selectedCostcenter=request.getParameter("costcenter");
		log.debug("selectedCostcenter " + selectedCostcenter);
		
		if (selectedCostcenter!=null && selectedCostcenter.equals("")) {
			selectedCostcenter=null;
		}
		/////////////////////////////////////////////////////////////////
		
		
		model = timesheetManager.getTimesheetTransactions(emp.getEmpCode(), dateFrom, dateTo, selectedCostcenter, selectedActivity, null, null, null, pageNumber, 80, "asc");//costcenter, activity, part1, part2, part3, pageNo, pageSize, sort
		model.put("specs", availableSpecs);
//		model.put("request_id", requestType);
		model.put("dateFrom", dateFrom);
		model.put("dateTo", dateTo);
		
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		
		
		model.put("activityList", activityList);
		model.put("costcenterList", costcenterList);
		model.put("selectedActivity", selectedActivity);
		model.put("selectedCostcenter", selectedCostcenter);
				
		return new ModelAndView("timesheetTrans",model);
	}
	
}



