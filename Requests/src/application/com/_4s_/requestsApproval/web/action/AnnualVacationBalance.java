package com._4s_.requestsApproval.web.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.model.Vacation;

import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;


public class AnnualVacationBalance extends BaseSimpleFormController{
	RequestsApprovalManager requestsApprovalManager;

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

		LoginUsersRequests loginUsersRequests=(LoginUsersRequests) command;
		Map model=new HashMap();		
		Date inDate = new Date();	
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		log.debug("---ref-emp from session---"+request.getSession().getAttribute("employee"));
		model.put("empCode", emp.getEmpCode());
		LoginUsers loginUser=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
		model.put("empName", loginUser.getName());
	
		String inDateString = request.getParameter("inDate");
		log.debug("-------inDateString---"+inDateString);
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		MultiCalendarDate mCalDate = new MultiCalendarDate();
		
		Settings settings = (Settings)request.getSession().getAttribute("settings");


		try {
			if(inDateString!=null){
				inDate =df.parse(inDateString);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	mCalDate.setDatePattern("dd/MM/yyyy");
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

		model.put("inDate", inDate);	
		
		
		String vacType = request.getParameter("vacType");
		log.debug("-----vacType entered---"+vacType);
		if(vacType!=null && !vacType.equals("")){
			Vacation vacOb=(Vacation)requestsApprovalManager.getObjectByParameter(Vacation.class, "vacation", vacType);
			model.put("vacType", vacOb.getName());
			model.put("vacId", vacOb.getVacation());
		}
		model.put("annualVacList", requestsApprovalManager.getObjectsByParameter(Vacation.class ,"type","A"));
		
		
		Long balance;
		Long balance1;
		Long balance2;
		List days=new ArrayList();
		
		//"10.8.2.109", "lehaa", "lehaa_payroll", "lehaa_payroll"
		//"oraserv", "oraserv", "lotus_pay", "lotus_pay"
		String server = settings.getServer();
		String service = settings.getService();
		String username = settings.getUsername();
		String password = settings.getPassword();
		
		boolean annualVacationBalanceDaysEnabled = settings.getAnnualVacBalDaysEnabled();
		
		if((inDate!=null && !inDate.equals("")) && (vacType!=null && !vacType.equals(""))){
			balance=requestsApprovalManager.getVacationCredit( emp.getEmpCode(), new Long(2), vacType, inDate);
			
			if (annualVacationBalanceDaysEnabled == true){//lotus
				days=requestsApprovalManager.getVacations( emp.getEmpCode(), new Long(2), vacType, inDate);
				log.debug("-----days ---"+days.size());
				model.put("days", days);
			}
			log.debug("-----balance ---"+balance);
			model.put("balance",balance);
		}
		
		
		if(inDate!=null && !inDate.equals("")){
			if (annualVacationBalanceDaysEnabled == true){//lotus
				days=requestsApprovalManager.getVacations( emp.getEmpCode(), new Long(2), "001", inDate);
				log.debug("-----days 001 ---"+days.size());
				model.put("days1", days);
			}
			balance1=requestsApprovalManager.getVacationCredit(emp.getEmpCode(), new Long(2), "001", inDate);
			log.debug("-----balance1 ---"+balance1);
			Vacation vacOb1=(Vacation)requestsApprovalManager.getObjectByParameter(Vacation.class, "vacation", "001");
			model.put("name1", vacOb1.getName());
			model.put("balance1", balance1);
			balance2=requestsApprovalManager.getVacationCredit(emp.getEmpCode(), new Long(2), "002", inDate);
			log.debug("-----balance2 ---"+balance2);
			
			if (annualVacationBalanceDaysEnabled == true){//lotus
				days=requestsApprovalManager.getVacations(emp.getEmpCode(), new Long(2), "002", inDate);
				log.debug("-----days 002 ---"+days.size());
				model.put("days2", days);
			}
			Vacation vacOb2=(Vacation)requestsApprovalManager.getObjectByParameter(Vacation.class, "vacation", "002");
			model.put("name2", vacOb2.getName());
			model.put("balance2", balance2);
			model.put("daysEnabled", annualVacationBalanceDaysEnabled);
		}
		//EmpReqTypeAcc a;
		
		

		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
		
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
}
