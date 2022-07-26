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

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.i18n.model.MyLocale;
import com._4s_.i18n.model.MyMessage;
import com._4s_.i18n.service.MessageManager;
import com._4s_.requestsApproval.model.AccessLevels;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.GroupAcc;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.service.RequestsApprovalManager;

public class TimeAttendanceReport implements Controller{
	protected final Log log = LogFactory.getLog(getClass());
	RequestsApprovalManager requestsApprovalManager;
	private MessageManager messageManager;

	public MessageManager getMessageManager() {
		return messageManager;
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

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

		//		String empCode = request.getParameter("empCode");
		//		log.debug("empCode " + empCode);
		//		if(empCode!=null && !empCode.isEmpty()) {
		//			if(!empCode.equals(emp.getEmpCode())) {
		//				Employee searchEmp = (Employee)requestsApprovalManager.getObjectByParameter(Employee.class, "empCode", empCode);
		//				if (!loggedInEmp.equals(searchEmp)) {
		//					differentEmp = true;
		//				}
		//				emp = searchEmp;
		//				log.debug("emp " + emp);
		//			}
		//		}
		Map model=new HashMap();
		model.put("emp", emp);
		//		model.put("empCode", empCode);

		model.put("differentEmp", differentEmp);

		String empCode = "";
		LoginUsers loginUser = (LoginUsers)requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", loggedInEmp);
		List groups = requestsApprovalManager.getObjectsByParameter(AccessLevels.class, "emp_id", loginUser);//(GroupAcc.class);
		List<LoginUsers> employees = new ArrayList();
		model.put("groups", groups);
		String groupId = request.getParameter("groupId");
		log.debug("groupID " + groupId);
		model.put("groupId", groupId);
		String logUser = request.getParameter("empCode");
		log.debug("loginUser " + logUser);
		if(groupId!=null && !groupId.isEmpty()) {
			//			GroupAcc groupAcc = (GroupAcc)requestsApprovalManager.getObject(GroupAcc.class, new Long(groupId));
			employees = new ArrayList();
			employees.addAll(requestsApprovalManager.getEmployeesByGroup(new Long(groupId)));
			model.put("employees", employees);			
		} 
		//		if (logUser!=null && !logUser.isEmpty()) {
		//			empCode = logUser;
		//		} else {
		//			String luser = request.getParameter("logUser");
		//			log.debug("luser" + luser);
		//			empCode=luser;
		//		}
		//		model.put("logUser", logUser);
		if (groups.size() > 0) {
			if (groupId != null && !groupId.isEmpty()) {// ///////////////////all
				// group
				// employees
				if (logUser != null && !logUser.isEmpty()) {
					empCode = logUser;
				} else {
					// String luser = request.getParameter("logUser");
					// log.debug("luser" + luser);
					// empCode=luser;
					// }
					// model.put("logUser", logUser)
					Iterator<LoginUsers> itr = employees.iterator();
					int i = 0;
					while (itr.hasNext()) {
						if (i < employees.size() - 1) {
							empCode += itr.next().getEmpCode().getEmpCode()
									+ ",";
						} else {
							empCode += itr.next().getEmpCode().getEmpCode();
						}
						i++;
					}
				}
			} else if (groupId == null || groupId.isEmpty()) {// /////////////all
				// employees
				// from all
				// groups
				Iterator it = groups.iterator();
				while (it.hasNext()) {
					AccessLevels lev = (AccessLevels) it.next();
					employees
					.addAll(requestsApprovalManager
							.getEmployeesByGroup(lev.getLevel_id()
									.getId()));
				}
				Iterator<LoginUsers> itr = employees.iterator();
				int i = 0;
				while (itr.hasNext()) {
					if (i < employees.size() - 1) {
						empCode += itr.next().getEmpCode().getEmpCode()
								+ ",";
					} else {
						empCode += itr.next().getEmpCode().getEmpCode();
					}
					i++;
				}
			}
		} else {
			if (emp!=null) {
				empCode = emp.getEmpCode();
			}
		}
		log.debug("empCode " + empCode);



		if (logUser!=null && !logUser.isEmpty()) {
			empCode = logUser;
		} else {
			String luser = request.getParameter("logUser");
			log.debug("luser" + luser);
			if (luser!= null && !luser.isEmpty()) {
				empCode=luser;
			} else {
				log.debug("testing");
			}
		}
		if (empCode!= null && !empCode.isEmpty()) {
			String [] empCodes = empCode.split(",");
			log.debug("emp codes " + empCodes);
			model.put("empCodes", empCodes);
		}

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
				totalObjects = requestsApprovalManager.getTimeAttendFromViewForTimeAttendanceReport(empCode, fromDate, toDate,settings);
				objects=(List) totalObjects.get(0);


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

					if (ob.getAddress1()== null && ob.getLatitude1()!=null && !ob.getLatitude1().isEmpty() && ob.getLatitude1()!="0" ) {
						String address1 = requestsApprovalManager.getShortAddressByGpsCoordinates(ob.getLongitude1(), ob.getLatitude1());
						log.debug("address 1 " + address1);
						ob.setAddress1(address1);
					} else {
						log.debug("long1 " +ob.getLongitude1() + " lat1 " + ob.getLatitude1() + " long2 " + ob.getLongitude2() + " lat2 " + ob.getLatitude2());
					}
					log.debug("ob.getAddress2() " + ob.getAddress2());
					log.debug("ob.getLatitude2() " + ob.getLatitude2());
					if(ob.getAddress2()==null && ob.getLatitude2()!=null && !ob.getLatitude2().isEmpty()  && ob.getLatitude2()!="0") {
						String address2 = requestsApprovalManager.getShortAddressByGpsCoordinates(ob.getLongitude2(), ob.getLatitude2());
						log.debug("address 2 " + address2);
						ob.setAddress2(address2);
					}  else {
						log.debug("long1 " +ob.getLongitude1() + " lat1 " + ob.getLatitude1() + " long2 " + ob.getLongitude2() + " lat2 " + ob.getLatitude2());
					}
					if( ob.getLatitude1()!=null && !ob.getLatitude1().isEmpty()  && ob.getLatitude1()!="0" 
							&& ob.getLongitude1()!=null && !ob.getLongitude1().isEmpty() && ob.getLongitude1()!="0"
							&& ob.getLatitude2()!=null && !ob.getLatitude2().isEmpty() && ob.getLatitude2()!="0"  
							&& ob.getLongitude2()!=null && !ob.getLongitude2().isEmpty() && ob.getLongitude2()!="0") {
						log.debug(requestsApprovalManager.distance(new Double(ob.getLatitude1()), new Double(ob.getLongitude1()), new Double(settings.getCompanyLat()), new Double(settings.getCompanyLong())));
					} else {
						log.debug("long1 " +ob.getLongitude1() + " lat1 " + ob.getLatitude1() + " long2 " + ob.getLongitude2() + " lat2 " + ob.getLatitude2());
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

			if (!groups.isEmpty()) {
				tableTitle.add("requestsApproval.caption.userName");
			}
			tableTitle.add("requestsApproval.caption.date");
			tableTitle.add("commons.caption.date");
			tableTitle.add("requestsApproval.caption.in");
			tableTitle.add("requestsApproval.caption.out");
			
			tableTitle.add("requestsApproval.caption.InputTypeIn");
			tableTitle.add("requestsApproval.caption.InputTypeOut");
			tableTitle.add("requestsApproval.caption.locationIn");
			tableTitle.add("requestsApproval.caption.locationOut");
			
			tableTitle.add("requestsApproval.caption.netTime");

			List results = new ArrayList();
			Iterator itr = objects.iterator();
			log.debug("records size again " + objects.size());
			while(itr.hasNext()) {
				TimeAttend req = (TimeAttend)itr.next();
				log.debug("looping attendance");
				List temp = new ArrayList();
				if (!groups.isEmpty()) {
					if (req.getEmpName()!=null) {
						temp.add(req.getEmpName());
					} else {
						temp.add("");
					}
				}
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
				MyMessage msg1 = null;
				MyMessage msg2 = null;
				MyLocale myLocale = new MyLocale();
				myLocale = (MyLocale) requestsApprovalManager.getObjectByParameter(
						MyLocale.class, "isDefault", new Boolean(true));
				log.debug("myLocale: " + myLocale.getCode());
				if (req.getInputType1()!=null) {
					if (req.getInputType1().equals("Web_Attendance")) {
						msg1 = messageManager.getMessageByKeyName("requestsApproval.caption.webAttendance", myLocale);
					} else if (req.getInputType1().equals("Request_Attendance")) {
						msg1 = messageManager.getMessageByKeyName("requestsApproval.caption.requestAttendance", myLocale);
					} else if (req.getInputType1().equals("Android_Attendance")) {
						msg1 = messageManager.getMessageByKeyName("requestsApproval.caption.androidAttendance", myLocale);
					} else if (req.getInputType1().equals("Fingerprint_Attendance")) {
						msg1 = messageManager.getMessageByKeyName("requestsApproval.caption.fingerprintAttendance", myLocale);
					} 
					log.debug("inputtype " + req.getInputType1() + " msg " + msg1);
					temp.add(msg1.getMessage());
				} else {
					temp.add("");
				}
				if (req.getInputType2()!=null) {
					if (req.getInputType2().equals("Web_Attendance")) {
						msg2 = messageManager.getMessageByKeyName("requestsApproval.caption.webAttendance", myLocale);
					} else if (req.getInputType2().equals("Request_Attendance")) {
						msg2 = messageManager.getMessageByKeyName("requestsApproval.caption.requestAttendance", myLocale);
					} else if (req.getInputType2().equals("Android_Attendance")) {
						msg2 = messageManager.getMessageByKeyName("requestsApproval.caption.androidAttendance", myLocale);
					} else if (req.getInputType2().equals("Fingerprint_Attendance")) {
						msg2 = messageManager.getMessageByKeyName("requestsApproval.caption.fingerprintAttendance", myLocale);
					} 
					log.debug("inputtype " + req.getInputType2() + " msg " + msg2);
					temp.add(msg2.getMessage());
				} else {
					temp.add("");
				}
				if (req.getAddress1()!=null) {
					temp.add(req.getAddress1());
				} else {
					temp.add("");
				}
				if (req.getAddress2()!=null) {
					temp.add(req.getAddress2());
				} else {
					temp.add("");
				}
				
				
				if (req.getDiffMins()!=null && !req.getDiffMins().equals("0") && !req.getDiffHrs().equals("0")) {
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
