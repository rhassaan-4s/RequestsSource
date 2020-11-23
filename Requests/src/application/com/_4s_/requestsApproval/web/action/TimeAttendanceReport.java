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
import com._4s_.i18n.model.MyMessage;
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
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Employee loggedInEmp =(Employee) request.getSession().getAttribute("employee");
		Employee emp = loggedInEmp;
		log.debug("---ref-emp from session---"+request.getSession().getAttribute("employee"));
		int year, month;
		
		Boolean differentEmp = false;
		
		String empCode = request.getParameter("empCode");
		log.debug("empCode " + empCode);
		if(empCode!=null && !empCode.isEmpty()) {
			if(!empCode.equals(emp.getEmpCode())) {
				Employee searchEmp = (Employee)requestsApprovalManager.getObjectByParameter(Employee.class, "empCode", empCode);
				if (!loggedInEmp.equals(searchEmp)) {
					differentEmp = true;
				}
				emp = searchEmp;
				log.debug("emp " + emp);
			}
		}
		Map model=new HashMap();
		model.put("emp", emp);
		model.put("empCode", empCode);
		
		model.put("differentEmp", differentEmp);

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
		
		List objects= new ArrayList();

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
				
				List empReqTypeAccs = requestsApprovalManager.getEmpReqTypeAccEmpCode(emp, null);
				String empArray = "";
				Iterator empItr = empReqTypeAccs.iterator();
				int count = 0;
				while(empItr.hasNext()) {
					String empReq = ((String)(empItr.next()));
					//					System.out.println("empReq " + empReq);
					if (count==0) {
						//						empArray = empReq.getEmp_id().getEmpCode();
						empArray =  "'" + empReq +  "'";
					} else {
						//						empArray += "," + empReq.getEmp_id().getEmpCode();
						empArray += ",'" + empReq + "'";
					}
					count++;
				}
				
				// VIP
				List totalObjects= new ArrayList();
//				totalObjects=requestsApprovalManager.getTimeAttend(emp.getEmpCode(), fromDate, toDate);
//				if (totalObjects!=null) {
//					totalObjects.addAll(requestsApprovalManager.getTimeAttendAndroid(emp.getEmpCode(), fromDate, toDate));
//				} else {
//					totalObjects=requestsApprovalManager.getTimeAttendAndroid(emp.getEmpCode(), fromDate, toDate);
//				}
				log.debug("getting attendance from view");
				totalObjects = requestsApprovalManager.getTimeAttendFromView(emp.getEmpCode(), fromDate, toDate);
				objects=(List) totalObjects.get(0);
//				if(totalObjects.size() ==4) {
//					objects.addAll((List)totalObjects.get(2));
//				}
				
				//Lehaa/////////////////////////////////////////////////
					
//				String totalSum=(String) totalObjects.get(1);
//				String [] totalValues=totalSum.split(",");
//				log.debug("totalMins=== "+totalValues[0]+" && totalHrs=== "+totalValues[1]);
//				String totalMins=totalValues[0];
//				String totalHrs=totalValues[1];
//				Long hrs=new Long(0), mins=new Long(0);
//				Long hrs2=new Long(0), mins2=new Long(0);
//
//				hrs= Long.parseLong(totalHrs);
//				mins=Long.parseLong(totalMins);
//
//				if(totalObjects.size() ==4) {
//					String totalSum2=(String) totalObjects.get(3);
//					String [] totalValues2=totalSum2.split(",");
//					log.debug("totalMins=== "+totalValues2[0]+" && totalHrs=== "+totalValues2[1]);
//					String totalMins2=totalValues2[0];
//					String totalHrs2=totalValues2[1];
//
//					hrs2= Long.parseLong(totalHrs2);
//					mins2=Long.parseLong(totalMins2);
//				}
//
//				hrs += hrs2;
//				mins += mins2;
//
//				if(mins>60){
//					hrs+=mins/60;
//					mins=mins%60;
//				} 
//
//				log.debug("sent mins=== "+mins+" && sent hrs=== "+hrs);
//
//				model.put("totalMins", mins);
//				model.put("totalHrs", hrs);
				
				
				String totalSum=(String) totalObjects.get(1);
				String [] totalValues=totalSum.split(",");
				log.debug("totalMins=== "+totalValues[0]+" && totalHrs=== "+totalValues[1]);
				String totalMins=totalValues[0];
				String totalHrs=totalValues[1];
				Long hrs=new Long(0), mins=new Long(0);
//				Long hrs2=new Long(0), mins2=new Long(0);

				hrs= Long.parseLong(totalHrs);
				mins=Long.parseLong(totalMins);

				if(totalObjects.size() ==4) {
					String totalSum2=(String) totalObjects.get(3);
					String [] totalValues2=totalSum2.split(",");
					log.debug("totalMins=== "+totalValues2[0]+" && totalHrs=== "+totalValues2[1]);
					String totalMins2=totalValues2[0];
					String totalHrs2=totalValues2[1];

//					hrs2= Long.parseLong(totalHrs2);
//					mins2=Long.parseLong(totalMins2);
				}

//				hrs += hrs2;
//				mins += mins2;

				if(mins>60){
					hrs+=mins/60;
					mins=mins%60;
				} 

				log.debug("sent mins=== "+mins+" && sent hrs=== "+hrs);

				model.put("totalMins", mins);
				model.put("totalHrs", hrs);
				//////////////////////////////////////////////////////////
				log.debug("-------objects- size--"+objects.size());
				for (int i = 0; i < objects.size(); i++) {
					TimeAttend ob= (TimeAttend) objects.get(i);
					
					// mCalDate.setDateString(ob.getDay());
					DateFormat df=new SimpleDateFormat("dd/mm/yyyy");
					
					Date day=df.parse(ob.getDay());
					log.debug("-------day---"+day);
					log.debug("-------objects---"+ob.getDay()+"-------getTimeIn---"+ob.getTimeIn()+"-------getTimeOut---"+ob.getTimeOut());
					
					if (ob.getAddress1()== null && ob.getLatitude1()!=null && ob.getLatitude1()!="0") {
						String address1 = requestsApprovalManager.getAddressByGpsCoordinates(ob.getLongitude1(), ob.getLatitude1());
						log.debug("address 1 " + address1);
						ob.setAddress1(address1);
					}
					if(ob.getAddress2()==null && ob.getLatitude2()!=null && ob.getLatitude2()!="0") {
						String address2 = requestsApprovalManager.getAddressByGpsCoordinates(ob.getLongitude2(), ob.getLatitude2());
						log.debug("address 2 " + address2);
						ob.setAddress2(address2);
					}
					if( ob.getLatitude1()!=null && ob.getLatitude2()!=null  && ob.getLatitude1()!="0" && ob.getLatitude2()!="0") {
						log.debug(requestsApprovalManager.distance(new Double(ob.getLatitude1()), new Double(ob.getLongitude1()), new Double(settings.getCompanyLat()), new Double(settings.getCompanyLong())));
					}
				}
				model.put("records", objects);
				model.put("empArray", empArray);
				
				// VIP
				
			}
		}
		
		String exportParameter = (String)request.getParameter("export");
		if (exportParameter!=null && exportParameter.equals("true")) {
			List tableTitle = new ArrayList();
			
			tableTitle.add("requestsApproval.caption.date");
			tableTitle.add("commons.caption.date");
			tableTitle.add("requestsApproval.caption.in");
			tableTitle.add("requestsApproval.caption.out");
			tableTitle.add("requestsApproval.caption.netTime");
			
			List results = new ArrayList();
			Iterator itr = objects.iterator();
			log.debug("records size again " + objects.size());
			while(itr.hasNext()) {
				TimeAttend req = (TimeAttend)itr.next();
				log.debug("looping attendance");
				List temp = new ArrayList();
				if (req.getDayString()!= null) {
					temp.add(req.getDayString());
				} else {
					temp.add("");
				}
				if (req.getDay()!=null) {
					temp.add(req.getDay());
				} else {
					temp.add("");
				}
				if (req.getTimeIn()!=null) {
					temp.add(req.getTimeIn());
				} else {
					temp.add("");
				}
				if (req.getTimeOut()!=null) {
					temp.add(req.getTimeOut());
				} else {
					temp.add("");
				}
				if (!req.getDiffMins().equals("0") && !req.getDiffHrs().equals("0")) {
					temp.add("0"+req.getDiffHrs()+":"+req.getDiffMins());
				} else {
					temp.add("");
				}
				
				log.debug("adding to results");
				results.add(temp);
			}
			log.debug("results size " + results.size());
			Map result = requestsApprovalManager.exportToExcelSheet("requestsApproval.header.timeAttendanceReport", tableTitle, results);
//			String title = (String)result.get("title");
			String title = "TimeAttendanceReport";
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
			return new ModelAndView("timeAttendanceReport",model);
		}
		return new ModelAndView("timeAttendanceReport",model);
	}
	
}
