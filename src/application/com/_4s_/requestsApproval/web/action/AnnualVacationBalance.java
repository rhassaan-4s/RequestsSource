package com._4s_.requestsApproval.web.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.CommonController;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.Vacation;
import com._4s_.requestsApproval.service.RequestsApprovalManager;

@Controller
@RequestMapping("/annualVacationBalance.html")
public class AnnualVacationBalance extends CommonController {
	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	RequestsApprovalManager requestsApprovalManager;

	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}

	
	@RequestMapping(method = RequestMethod.GET)
	public String handleRequest(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

//		Map model=new HashMap();

		Date inDate = new Date();	
		Employee loggedInEmp =(Employee) request.getSession().getAttribute("employee");
		Employee emp = loggedInEmp;
		log.debug("---ref-emp from session---"+request.getSession().getAttribute("employee"));
		
		String empCode = request.getParameter("empCode");
		log.debug("empCode " + empCode);
		if(empCode!=null && !empCode.isEmpty()) {
			if(!empCode.equals(emp.getEmpCode())) {
				Employee searchEmp = (Employee)requestsApprovalManager.getObjectByParameter(Employee.class, "empCode", empCode);
				if (!loggedInEmp.equals(searchEmp)) {
//					differentEmp = true;
				}
				emp = searchEmp;
				log.debug("emp " + emp);
			}
		}
		model.addAttribute("emp", emp);
		model.addAttribute("empCode", empCode);
		LoginUsers loginUser=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
		model.addAttribute("empName", loginUser.getName());

		String inDateString = request.getParameter("inDate");
		log.debug("-------inDateString---"+inDateString);
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		MultiCalendarDate mCalDate = new MultiCalendarDate();

		Settings settings = (Settings)request.getSession().getAttribute("settings");


		try {
			if(inDateString!=null){
				inDate =df.parse(inDateString);
				inDateString = df.format(inDate);
				log.debug("indatestring " + inDateString);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			mCalDate.setDatePattern("dd/MM/yyyy");
		if(inDate!=null){
			if(inDate.equals("")){
				inDate=new Date();
			}

			mCalDate.setDate(inDate);
			inDate= mCalDate.getDate();
		}
		//		Calendar c = Calendar.getInstance();
		//		c.setTime(inDate);
		//		inDate=c.getTime();

		log.debug("-------formattedInDate---"+inDate);
		//		try {
		//			inDate=df.parse(formattedInDate);
		//		} catch (ParseException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}

		model.addAttribute("inDate", inDate);	


		String vacType = request.getParameter("vacType");
		model.addAttribute("vacId", vacType);
		log.debug("-----vacType entered---"+vacType);
		if(vacType!=null && !vacType.equals("")){
			Vacation vacOb=(Vacation)requestsApprovalManager.getObjectByParameter(Vacation.class, "vacation", vacType);
			model.addAttribute("vacType", vacOb.getName());
		} 

		Long balance = 0L;
		Long balance1 = 0L;
		Long balance2 = 0L;
		List days=new ArrayList();

		
		boolean annualVacationBalanceDaysEnabled = settings.getAnnualVacBalDaysEnabled();

		if((inDate!=null && !inDate.equals("")) && (vacType!=null && !vacType.equals(""))){
			balance=requestsApprovalManager.getVacationCredit( emp.getEmpCode(), new Long(2), vacType, inDate);

			if (annualVacationBalanceDaysEnabled == true){//lotus
				days=requestsApprovalManager.getVacations( emp.getEmpCode(), new Long(2), vacType, inDate,settings);
				log.debug("-----days ---"+days.size());
				model.addAttribute("days", days);
			}
			log.debug("-----balance ---"+balance);
			model.addAttribute("balance",balance);
		}

		String name1 = "";
		String name2 = "";

		if(inDate!=null && !inDate.equals("")){
			if (annualVacationBalanceDaysEnabled == true){//lotus
				days=requestsApprovalManager.getVacations( emp.getEmpCode(), new Long(2), "001", inDate,settings);
				log.debug("-----days 001 ---"+days.size());
				model.addAttribute("days1", days);
			}
			balance1=requestsApprovalManager.getVacationCredit(emp.getEmpCode(), new Long(2), "001", inDate);
			log.debug("-----balance1 ---"+balance1);
			Vacation vacOb1=(Vacation)requestsApprovalManager.getObjectByParameter(Vacation.class, "vacation", "001");
			name1 = vacOb1.getName();
			model.addAttribute("name1",name1);
			model.addAttribute("balance1", balance1);
			balance2=requestsApprovalManager.getVacationCredit(emp.getEmpCode(), new Long(2), "002", inDate);
			log.debug("-----balance2 ---"+balance2);

			if (annualVacationBalanceDaysEnabled == true){//lotus
				days=requestsApprovalManager.getVacations(emp.getEmpCode(), new Long(2), "002", inDate,settings);
				log.debug("-----days 002 ---"+days.size());
				model.addAttribute("days2", days);
			}
			Vacation vacOb2=(Vacation)requestsApprovalManager.getObjectByParameter(Vacation.class, "vacation", "002");
			name2 = vacOb2.getName();
			model.addAttribute("name2", name2);
			model.addAttribute("balance2", balance2);
			model.addAttribute("daysEnabled", annualVacationBalanceDaysEnabled);
		}

		model.addAttribute("annualVacList", requestsApprovalManager.getObjectsByParameter(Vacation.class ,"type","A"));
		
		String exportParameter = (String)request.getParameter("export");
		if (exportParameter!=null && exportParameter.equals("true")) {
			List tableTitle = new ArrayList();

			tableTitle.add("requestsApproval.caption.userName");
			tableTitle.add("commons.caption.inDate");
			tableTitle.add("requestsApproval.caption.vacationType");
			tableTitle.add("commons.button.getVacCredit");

			List results = new ArrayList();
			List temp = new ArrayList();
			temp.add(loginUser.getName());
			temp.add(mCalDate.getMeladiString());
			if (vacType==null || vacType.equals("")) {
				temp.add(name1+"\n"+name2);
				temp.add(balance1+"\n"+balance2);
			} else {
				temp.add(vacType);
				temp.add(balance);
			}
				
			log.debug("adding to results");
			results.add(temp);
			log.debug("results size " + results.size());
			
			Map result = requestsApprovalManager.exportToExcelSheet("requestsApproval.header.annualVacationBalance2", tableTitle, results);
			//			String title = (String)result.get("title");
			String title = "AnnualVacationBalance";
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
//			return new ModelAndView("annualVacationBalance",model);
			return "annualVacationBalance";
		}
//		return new ModelAndView("annualVacationBalance",model);
		return "annualVacationBalance";

	}

//	protected Object formBackingObject(HttpServletRequest request) throws ServletException 
//	{
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//
//		LoginUsersRequests loginUsersRequests=new LoginUsersRequests();
//
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//
//		return loginUsersRequests;
//	}
//
//	//**************************************** referenceData ***********************************************\\
//	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
//	{
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//
//		LoginUsersRequests loginUsersRequests=(LoginUsersRequests) command;
//		Map model=new HashMap();		
//
//
//		//EmpReqTypeAcc a;
//
//
//		Date inDate = new Date();	
//		Employee emp =(Employee) request.getSession().getAttribute("employee");
//		log.debug("---ref-emp from session---"+request.getSession().getAttribute("employee"));
//		model.put("empCode", emp.getEmpCode());
//		LoginUsers loginUser=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
//		model.put("empName", loginUser.getName());
//
//		String inDateString = request.getParameter("inDate");
//		model.put("inDate", inDate);
//		
//		String vacType = request.getParameter("vacType");
//		log.debug("-----vacType entered---"+vacType);
//		if(vacType!=null && !vacType.equals("")){
//			Vacation vacOb=(Vacation)requestsApprovalManager.getObjectByParameter(Vacation.class, "vacation", vacType);
//			model.put("vacType", vacOb.getName());
//			model.put("vacId", vacOb.getVacation());
//		} 
//		model.put("annualVacList", requestsApprovalManager.getObjectsByParameter(Vacation.class ,"type","A"));
//
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		return model;
//	}
//
//	//**************************************** onBind ***********************************************\\	
//	//**************************************** onBindAndValidate ***********************************************\\
//	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
//	{
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//	}
//
	//**************************************** onSubmit ***********************************************\\	
//	public ModelAndView onSubmit(HttpServletRequest request,
//			HttpServletResponse response, Object command, BindException errors)throws Exception 
//	{	
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		LoginUsersRequests loginUsersRequests=(LoginUsersRequests) command;
//
//		Map model=new HashMap();
//
//
//		Date inDate = new Date();	
//		Employee emp =(Employee) request.getSession().getAttribute("employee");
//		log.debug("---ref-emp from session---"+request.getSession().getAttribute("employee"));
//		model.put("empCode", emp.getEmpCode());
//		LoginUsers loginUser=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
//		model.put("empName", loginUser.getName());
//
//		String inDateString = request.getParameter("inDate");
//		log.debug("-------inDateString---"+inDateString);
//		DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
//		MultiCalendarDate mCalDate = new MultiCalendarDate();
//
//		Settings settings = (Settings)request.getSession().getAttribute("settings");
//
//
//		try {
//			if(inDateString!=null){
//				inDate =df.parse(inDateString);
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//	mCalDate.setDatePattern("dd/MM/yyyy");
//		if(inDate!=null){
//			if(inDate.equals("")){
//				inDate=new Date();
//			}
//
//			mCalDate.setDate(inDate);
//			inDate= mCalDate.getDate();
//		}
//		//		Calendar c = Calendar.getInstance();
//		//		c.setTime(inDate);
//		//		inDate=c.getTime();
//
//		log.debug("-------formattedInDate---"+inDate);
//		//		try {
//		//			inDate=df.parse(formattedInDate);
//		//		} catch (ParseException e) {
//		//			// TODO Auto-generated catch block
//		//			e.printStackTrace();
//		//		}
//
//		model.put("inDate", inDate);	
//
//
//		String vacType = request.getParameter("vacType");
//		log.debug("-----vacType entered---"+vacType);
//		if(vacType!=null && !vacType.equals("")){
//			Vacation vacOb=(Vacation)requestsApprovalManager.getObjectByParameter(Vacation.class, "vacation", vacType);
//			model.put("vacType", vacOb.getName());
//			model.put("vacId", vacOb.getVacation());
//		} 
//
//		Long balance = 0L;
//		Long balance1 = 0L;
//		Long balance2 = 0L;
//		List days=new ArrayList();
//
//		
//		String server = settings.getServer();
//		String service = settings.getService();
//		String username = settings.getUsername();
//		String password = settings.getPassword();
//
//		boolean annualVacationBalanceDaysEnabled = settings.getAnnualVacBalDaysEnabled();
//
//		if((inDate!=null && !inDate.equals("")) && (vacType!=null && !vacType.equals(""))){
//			balance=requestsApprovalManager.getVacationCredit( emp.getEmpCode(), new Long(2), vacType, inDate);
//
//			if (annualVacationBalanceDaysEnabled == true){//lotus
//				days=requestsApprovalManager.getVacations( emp.getEmpCode(), new Long(2), vacType, inDate);
//				log.debug("-----days ---"+days.size());
//				model.put("days", days);
//			}
//			log.debug("-----balance ---"+balance);
//			model.put("balance",balance);
//		}
//
//		String name1 = "";
//		String name2 = "";
//
//		if(inDate!=null && !inDate.equals("")){
//			if (annualVacationBalanceDaysEnabled == true){//lotus
//				days=requestsApprovalManager.getVacations( emp.getEmpCode(), new Long(2), "001", inDate);
//				log.debug("-----days 001 ---"+days.size());
//				model.put("days1", days);
//			}
//			balance1=requestsApprovalManager.getVacationCredit(emp.getEmpCode(), new Long(2), "001", inDate);
//			log.debug("-----balance1 ---"+balance1);
//			Vacation vacOb1=(Vacation)requestsApprovalManager.getObjectByParameter(Vacation.class, "vacation", "001");
//			name1 = vacOb1.getName();
//			model.put("name1",name1);
//			model.put("balance1", balance1);
//			balance2=requestsApprovalManager.getVacationCredit(emp.getEmpCode(), new Long(2), "002", inDate);
//			log.debug("-----balance2 ---"+balance2);
//
//			if (annualVacationBalanceDaysEnabled == true){//lotus
//				days=requestsApprovalManager.getVacations(emp.getEmpCode(), new Long(2), "002", inDate);
//				log.debug("-----days 002 ---"+days.size());
//				model.put("days2", days);
//			}
//			Vacation vacOb2=(Vacation)requestsApprovalManager.getObjectByParameter(Vacation.class, "vacation", "002");
//			name2 = vacOb2.getName();
//			model.put("name2", name2);
//			model.put("balance2", balance2);
//			model.put("daysEnabled", annualVacationBalanceDaysEnabled);
//		}
//
//		String exportParameter = (String)request.getParameter("export");
//		if (exportParameter!=null && exportParameter.equals("true")) {
//			List tableTitle = new ArrayList();
//
//			tableTitle.add("requestsApproval.caption.userName");
//			tableTitle.add("commons.caption.inDate");
//			tableTitle.add("requestsApproval.caption.vacationType");
//			tableTitle.add("commons.button.getVacCredit");
//
//			List results = new ArrayList();
//			List temp = new ArrayList();
//			temp.add(loginUser.getName());
//			temp.add(inDate);
//			if (vacType==null) {
//				temp.add(name1+"\n"+name2);
//				temp.add(balance1+"\n"+balance2);
//			} else {
//				temp.add(vacType);
//				temp.add(balance);
//			}
//				
//			log.debug("adding to results");
//			results.add(temp);
//			log.debug("results size " + results.size());
//			
//			Map result = requestsApprovalManager.exportToExcelSheet("requestsApproval.header.annualVacationBalance2", tableTitle, results);
//			//			String title = (String)result.get("title");
//			String title = "AnnualVacationBalance";
//			HSSFWorkbook workBook = (HSSFWorkbook)result.get("workbook");
//			try {
//				response.setHeader("Content-Disposition",
//						"attachment; filename=\""+title+".xls\"");
//				log.debug(workBook);
//				workBook.write(response.getOutputStream());
//				log.debug(response);
//				response.getOutputStream().flush();
//				response.getOutputStream().close();
//				log.debug("Response written");
//			} catch (Exception e) {
//				// TODO: handle exception
//				log.debug("exception " + e);
//				e.printStackTrace();
//			}
//			log.debug("after export to excel");
//			//			return new ModelAndView(new RedirectView("timeAttendanceReport.html"));
//			return new ModelAndView("annualVacationBalance",model);
//		}
//		return new ModelAndView("annualVacationBalance",model);
//	}
}
