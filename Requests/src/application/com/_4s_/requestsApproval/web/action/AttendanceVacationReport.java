package com._4s_.requestsApproval.web.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.requestsApproval.service.RequestsApprovalManager;

public class AttendanceVacationReport implements Controller{
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
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		log.debug("---ref-emp from session---"+request.getSession().getAttribute("employee"));
		int year, month;
		List objects= new ArrayList();
		List days=new ArrayList();
		
		Map model=new HashMap();
		

		String dateFrom = request.getParameter("fromDate");
		log.debug("--dateFrom--"+dateFrom);
		model.put("fromDate", dateFrom);
		String dateTo = request.getParameter("toDate");
		
		String empCode = request.getParameter("empCode");
		if (empCode!=null && !empCode.equals("")){
			emp = (Employee)requestsApprovalManager.getObjectByParameter(Employee.class, "empCode", empCode);
			model.put("emp", emp);
		}
		model.put("empCode", empCode);
		
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
				
//				totalObjects=requestsApprovalManager.getTimeAttendAll(empCode, fromDate, toDate);
				
				if (empCode== null || empCode.isEmpty()) {
					List empReqTypeAccs = requestsApprovalManager.getEmpReqTypeAccEmpCode(emp, null);
					String empArray = "";
					Iterator empItr = empReqTypeAccs.iterator();
					int count = 0;
					while(empItr.hasNext()) {
						String empReq = ((String)(empItr.next()));
//						System.out.println("empReq " + empReq);
						if (count==0) {
//							empArray = empReq.getEmp_id().getEmpCode();
							empArray =  "'" + empReq +  "'";
						} else {
//							empArray += "," + empReq.getEmp_id().getEmpCode();
							empArray += ",'" + empReq + "'";
						}
						count++;
					}
//					totalObjects=requestsApprovalManager.getTimeAttend(empArray, fromDate, toDate);
//					totalObjects=requestsApprovalManager.getTimeAttendFromView(empArray, fromDate, toDate);
					if (empArray == null || empArray.isEmpty()) {
						empArray = "'" + emp.getEmpCode() +  "'";
					}
					totalObjects=requestsApprovalManager.getTimeAttendAll(empArray, fromDate, toDate);
				} else {
//					totalObjects=requestsApprovalManager.getTimeAttend(empCode, fromDate, toDate);
//					totalObjects=requestsApprovalManager.getTimeAttendFromView(empCode, fromDate, toDate);
					totalObjects=requestsApprovalManager.getTimeAttendAll(empCode, fromDate, toDate);
				}
				
				objects=(List) totalObjects.get(0);
				
//				//Lehaa/////////////////////////////////////////////////
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
				
				if (empCode== null || empCode.isEmpty()) {
					List empReqTypeAccs = requestsApprovalManager.getEmpReqTypeAccEmpCode(emp, null);
					String empArray = "";
					Iterator empItr = empReqTypeAccs.iterator();
					int count = 0;
					while(empItr.hasNext()) {
						String empReq = ((String)(empItr.next()));
//						System.out.println("empReq " + empReq);
						if (count==0) {
//							empArray = empReq.getEmp_id().getEmpCode();
							empArray =  "'" + empReq +  "'";
						} else {
//							empArray += "," + empReq.getEmp_id().getEmpCode();
							empArray += ",'" + empReq + "'";
						}
						count++;
					}
					if (empArray == null || empArray.isEmpty()) {
						empArray = "'" + emp.getEmpCode() +  "'";
					}
					days=requestsApprovalManager.getVacations( empArray, new Long(2), fromDate,toDate);
				} else {
//					totalObjects=requestsApprovalManager.getTimeAttend(empCode, fromDate, toDate);
//					totalObjects=requestsApprovalManager.getTimeAttendFromView(empCode, fromDate, toDate);
					days=requestsApprovalManager.getVacations( empCode, new Long(2), fromDate,toDate);
				}
//				days=requestsApprovalManager.getVacations( empCode, new Long(2), fromDate,toDate);
				log.debug("-----days 001 ---"+days.size());
				model.put("days1", days);
				
//				days=requestsApprovalManager.getVacations(emp.getEmpCode(), new Long(2), "002", fromDate,toDate);
//				log.debug("-----days 002 ---"+days.size());
//				model.put("days2", days);
				
			}
		}
		String exportParameter = (String)request.getParameter("export");
		if (exportParameter!=null && exportParameter.equals("true")) {
			List tableTitle = new ArrayList();

			tableTitle.add("requestsApproval.caption.userCode");
			tableTitle.add("requestsApproval.caption.userName");
			tableTitle.add("requestsApproval.caption.date");
			tableTitle.add("commons.caption.date");
			tableTitle.add("requestsApproval.caption.in");
			tableTitle.add("requestsApproval.caption.out");

			List results = new ArrayList();
			
			Iterator itr = objects.iterator();
			while(itr.hasNext()) {
				TimeAttend req = (TimeAttend)itr.next();
				log.debug("looping attendance");
				List temp = new ArrayList();
				temp.add(req.getEmployee());
				temp.add(req.getEmpName());
				temp.add(req.getDayString());
				temp.add(req.getDay());
				if (req.getTimeIn() != null ) {
					temp.add(req.getTimeIn());
				} else {
					temp.add("");
				}
				if (req.getTimeIn() != null ) {
					temp.add(req.getTimeIn());
				} else {
					temp.add("");
				}

				log.debug("adding to results");
				results.add(temp);
				log.debug("results size " + results.size());
			}
			List temp = new ArrayList();
			temp.add("");
			temp.add("");
			temp.add("");
			temp.add("");
			temp.add("");
			temp.add("");
			results.add(temp);
			
			temp = new ArrayList();
			temp.add("requestsApproval.caption.userCode");
			temp.add("requestsApproval.caption.userName");
			temp.add("commons.caption.from");
			temp.add("commons.caption.to");
			temp.add("requestsApproval.caption.vacPeriod");
			temp.add("");
			results.add(temp);
			
			Iterator itr2 = days.iterator();
			while (itr2.hasNext()) {
				log.debug("emp vacation start");
				LinkedHashMap res = (LinkedHashMap)itr2.next();
				temp = new ArrayList();
				temp.add(res.get("empCode"));
				temp.add(res.get("fName"));
				temp.add(res.get("fr_date"));
				temp.add(res.get("to_date"));
				temp.add(res.get("withdr"));
				temp.add("");
				log.debug("emp vacation end " + res.get("empCode"));
				results.add(temp);
			}
			Map result = requestsApprovalManager.exportToExcelSheet("requestsApproval.header.attendanceVacationReport", tableTitle, results);
			//			String title = (String)result.get("title");
			String title = "AttendanceVacationReport";
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
			//			return new ModelAndView(new RedirectView("timeAttendanceReport.html"));
//			return new ModelAndView("attendanceVacationReport",model);
		}
		
		return new ModelAndView("attendanceVacationReport",model);
	}
	
}
