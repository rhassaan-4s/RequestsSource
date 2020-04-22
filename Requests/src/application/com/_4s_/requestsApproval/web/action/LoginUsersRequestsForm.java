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
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.AccessLevels;
import com._4s_.requestsApproval.model.AnnualVacLimit;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.model.Vacation;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.restServices.json.RequestsApprovalQuery;
import com._4s_.restServices.model.AttendanceStatus;

public class LoginUsersRequestsForm extends BaseSimpleFormController{

	RequestsApprovalManager requestsApprovalManager;
	
	private String SMTP_AUTH_USER; 
	
	private String SMTP_AUTH_PWD; 

	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}
	
	private JavaMailSenderImpl mailSender;
	
	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
	protected Object formBackingObject(HttpServletRequest request) throws ServletException 
	{	
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		String newDate =df.format(new Date());
		log.debug("----newDate--"+newDate);
		MultiCalendarDate mCalDate = new MultiCalendarDate();
		mCalDate.setDateString(newDate);
//		Date reqDate =m;
//		Calendar cal = Calendar.getInstance();
//		java.sql.Date reqDate=new java.sql.Date (cal.getTimeInMillis());
//		log.debug("----reqDate--"+reqDate);
//		String newSearch= (String) request.getParameter("new");
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		log.debug("----emp from session---"+request.getSession().getAttribute("employee"));
		
		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
		if(loginUsers!=null){
			log.debug("-----login.code----"+loginUsers.getEmpCode());
		}
		LoginUsersRequests loginUsersRequests = new LoginUsersRequests();
		String empRequestTypeId=request.getParameter("empRequestTypeId");
//		log.debug("newSearch----"+newSearch);
//		if(newSearch!=null && !newSearch.equals("")){
		if(empRequestTypeId == null || empRequestTypeId.equals("")){
			
			log.debug("loginUsersRequests------"+loginUsersRequests);
			loginUsersRequests = new LoginUsersRequests();
			if(loginUsers!=null){
				loginUsersRequests.setLogin_user(loginUsers);
				loginUsersRequests.setEmpCode(loginUsers.getEmpCode());
//				loginUsersRequests.setRequest_date(reqDate);
			}
		}else{
			log.debug("loginUsersRequests------"+loginUsersRequests);
			loginUsersRequests = (LoginUsersRequests) requestsApprovalManager.getObject(LoginUsersRequests.class, new Long(empRequestTypeId));
		}
//		}else{
//			
//			log.debug("loginUsersRequests------"+loginUsersRequests);
//			if(request.getSession().getAttribute("loginUsersRequests") !=null){
//				loginUsersRequests=(LoginUsersRequests) request.getSession().getAttribute("loginUsersRequests");
////				if(loginUsersRequests != null && !loginUsersRequests.equals("")){
////					log.debug("empRequestCode----"+loginUsersRequests.getEmpCode());
////					if(loginUsersRequests.getRequest_id()!=null){
////						log.debug("getRequest_id----"+loginUsersRequests.getRequest_id().getId());
////					}
////				}
//			}
//			
//		}
	//	log.debug("newSearch----"+newSearch);
		
		log.debug("empRequestTypeId------"+empRequestTypeId);
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		if(settings.getVacationRequestExcep()==false){
			if(loginUsersRequests!=null && !loginUsersRequests.equals("")){
				if(loginUsersRequests.getId()!=null && !loginUsersRequests.getId().equals("")){
					log.debug("----loginUsersRequests.getRequest_id().getId()---------"+loginUsersRequests.getRequest_id().getId());
					RequestTypes specVac= (RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, new Long(1));
					if(loginUsersRequests.getRequest_id().getId()==new Long(999)){
						Vacation errand=(Vacation) requestsApprovalManager.getObject(Vacation.class, "999");
						loginUsersRequests.setRequest_id(specVac);
						loginUsersRequests.setVacation(errand);
					}
				}
			}
		}
		
//		log.debug("----loginUsersRequests.code----"+loginUsersRequests.getEmpCode());
//		log.debug("----loginUsersRequests.getvacation----"+loginUsersRequests.getVacation());
//		log.debug("---------loginUsersRequests.getId()-------"+loginUsersRequests.getId());
		log.debug("-------period from--formbacking-"+loginUsersRequests.getPeriod_from());
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		String fromMail = request.getSession().getServletContext().getInitParameter("mailMgr");
//		Settings settings = (Settings)request.getSession().getAttribute("settings");
		String fromMail = settings.getMailMgr();
		log.debug("fromMail------"+fromMail);
	   return loginUsersRequests ;
	}
	
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests) command;
		log.debug("-------period from--referense-"+loginUsersRequests.getPeriod_from());
//		request.getSession().setAttribute("loginUsersRequests", loginUsersRequests);
//		log.debug("-----loginUsersRequests.code----"+employeeRequests.getEmployee().getEmployeeCode());
		Map model=new HashMap();
//		String fromMail = request.getSession().getServletContext().getInitParameter("mailMgr");
		String fromMail = settings.getMailMgr();
		log.debug("mailMgr---fromMail----"+fromMail);
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		log.debug("---ref-emp from session---"+request.getSession().getAttribute("employee"));
		
		log.debug("=====emp.getEmpCode()==="+emp.getEmpCode());
		
		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
		
		String empRequestTypeId=request.getParameter("empRequestTypeId");
		log.debug("empRequestTypeId------"+empRequestTypeId);
		model.put("empRequestTypeId",empRequestTypeId);
		
		String vac_period_from=request.getParameter("vac_period_from");
		log.debug("vac_period_from------"+vac_period_from);
		model.put("vac_period_from",vac_period_from);
		
		String vac_period_to=request.getParameter("vac_period_to");
		log.debug("vac_period_to------"+vac_period_to);
		model.put("vac_period_to",vac_period_to);
		
//		String request_id=request.getParameter("request_id");
//		log.debug("request_id------"+request_id);
//		model.put("request_id",request_id);
		
//		String request_date=request.getParameter("request_date");
//		log.debug("request_date------"+request_date);
//		model.put("request_date",request_date);
		
		List requestTypeList=requestsApprovalManager.getObjects(RequestTypes.class);
		List requestTypes= new ArrayList();
		for (int i = 0; i < requestTypeList.size(); i++) {
			RequestTypes types = (RequestTypes) requestTypeList.get(i);
			if(!types.getId().equals(new Long(10)) && !types.getId().equals(new Long(11))){
				requestTypes.add(types);
			}
		}
		model.put("requestTypeList", requestTypes);
		
		boolean specialVacExcep = settings.getSpecialVacExcep();
		
		List specialVacations=requestsApprovalManager.getObjectsByParameter(Vacation.class, "type", "B");
		for (int i = 0; i < specialVacations.size(); i++) {
			Vacation vac= (Vacation) specialVacations.get(i);
			log.debug("------vacation--before-==="+vac.getVacation());
			if(vac.getVacation().equals("999")){
				model.put("errand", vac);
				specialVacations.remove(vac);
			}
			if (specialVacExcep == true){//Lotus
				if(vac.getVacation().equals("008")){
					specialVacations.remove(vac);
				}
			}
			log.debug("------vacation- after--==="+vac.getVacation());
		}
		model.put("specialVacations", specialVacations);
		
		List annualVacations=requestsApprovalManager.getObjectsByParameter(Vacation.class,"type","A");
		model.put("annualVacations", annualVacations);
		
//		String emp_code = request.getParameter("empCode");
//		log.debug("code entered--------"+emp_code);
		model.put("employeeCode", emp.getEmpCode());
		if(loginUsers!=null){
			model.put("employeeName", loginUsers.getName());
			log.debug("====loginUsers.getName()==="+loginUsers.getName());
		}
		MultiCalendarDate mCalDate = new MultiCalendarDate();
	//	log.debug("------date from command---"+loginUsersRequests.getFrom_date());
		Date from_date;
		String dateFrom=request.getParameter("fromDate");
		if(dateFrom!=null && !dateFrom.equals("")){
			log.debug("-----date entered---"+dateFrom);
			mCalDate.setDateTimeString(dateFrom,new Boolean(true));
		}
		from_date = mCalDate.getDate();
		model.put("from_date", from_date);
		
//		String vacId=request.getParameter("vacation");
//		log.debug("--------vacId---"+vacId);
//		if(vacId!=null && !vacId.equals("") && from_date!=null){
//			log.debug("----fromdate--"+from_date);
//			model.put("vacCredit", requestsApprovalManager.getVacationCredit("oraserv", "oraserv", "lotus_pay10", "lotus_pay10", loginUsersRequests.getEmpCode(), new Long(2),vacId, from_date));
//		}
//		
//		loginUsersRequests.setVacCredit(requestsApprovalManager.getVacationCredit("oraserv", "oraserv", "lotus_pay10", "lotus_pay10", loginUsersRequests.getEmpCode(), new Long(2),vacId, from_date));
//		
		Date to_date;
		String dateTo=request.getParameter("to_date");
		if(dateTo!=null && !dateTo.equals("")){
			log.debug("-----dateTo entered---"+dateTo);
			dateTo=dateTo.substring(0,10);
			log.debug("-----dateTo entered---"+dateTo);
			mCalDate.setDateString(dateTo);
		}
		to_date = mCalDate.getDate();
		model.put("to_date", to_date);
//		int withdrawDays=calculateDifference(from_date, to_date);
//		log.debug("----noDays---"+withdrawDays);
//		
		String withdrawDays=request.getParameter("withdrawDays");
		log.debug("///withdrawDays--------"+withdrawDays);
		model.put("withdrawDays", withdrawDays);
		
//		String empName = request.getParameter("name");
//		log.debug("empName entered--------"+empName);
//		log.debug("----loginUsersRequests--"+loginUsersRequests);
		if(empRequestTypeId!=null && !empRequestTypeId.equals("")){
			LoginUsersRequests logRequests = (LoginUsersRequests) requestsApprovalManager.getObject(LoginUsersRequests.class, new Long(empRequestTypeId));
			log.debug("----mmname---"+logRequests.getLogin_user().getName());
//			LoginUsers loginUsers =(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp_code);
			model.put("mm_name", logRequests.getLogin_user().getName());
			log.debug("----logRequests.getEmpCode()---"+logRequests.getEmpCode());
			model.put("emplCode", logRequests.getEmpCode());
		}
//		String messagNo=request.getParameter("messagNo");
//		if(messagNo==null)messagNo="1";
//		model.put("messagNo", messagNo);
		
		
		String done=request.getParameter("done");
		String reqId=request.getParameter("requestId");
		
		boolean creatingMail = settings.getCreatingMail();
		String loginUrl = settings.getLoginUrl();
		
		if(done!=null && reqId!=null){
			model.put("done", done);
//			MimeMessage msg=mailSender.createMimeMessage();	
//			MimeMessageHelper helper = new MimeMessageHelper(msg,"UTF-8");
			
			//Lehaa/////////////////////////////////////////// 
			if (creatingMail==true){
				List toMgrs=new ArrayList();
				log.debug("---empId---"+emp.getId());
				LoginUsers employee= (LoginUsers) requestsApprovalManager.getObject(LoginUsers.class, emp.getId());
				log.debug("---employee---"+employee.getId());
				List fields=new ArrayList();
				fields.add("id");
				RequestTypes req=(RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, Long.parseLong(reqId));
				List empAcc= (List)requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(EmpReqTypeAcc.class, "emp_id", employee,"req_id",req,fields);
				log.debug("---empAcc.size()---"+empAcc.size());
				for (int j = 0; j < empAcc.size(); j++) {
					EmpReqTypeAcc empacc= (EmpReqTypeAcc) empAcc.get(j);
					log.debug("---empreqacc---"+empacc.getId());
					log.debug("---group---"+empacc.getGroup_id().getId());
					List mgrs=new ArrayList();
					mgrs= (List) requestsApprovalManager.getObjectsByParameter(AccessLevels.class, "level_id", empacc.getGroup_id());
					//toMgrs.addAll(toMgrs);
					log.debug("---mgrs list size---"+mgrs.size());
					for (int i = 0; i < mgrs.size(); i++) {
						AccessLevels mgr= (AccessLevels) mgrs.get(i);
						//toMgrs.add(mgr);
						log.debug("---mgr ---"+mgr.getEmp_id().getId());
						
						String requestNumber = (Long)request.getSession().getAttribute("requestNumber")+"";
						String mm = "";
//						mm="<html dir=\"rtl\" lang=\"ar\">";
//						mm+="<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head>";
//						mm+="<body>";

						mm+="\n√” «–/ "+mgr.getEmp_id().getName();
						mm+="\n·œÌﬂ ÿ·»«  ··„Ê«›ﬁÂ ⁄·ÌÂ« »—Ã«¡ «·«ÿ·«⁄ ⁄·ÌÂ« „‰: \n"+loginUrl+"?requestId="+reqId+"&requestNumber="+requestNumber;
						mm+="\n„⁄ Œ«·’ «·‘ﬂ—";

						try
						{
							String smtpServer = settings.getSMTPServer();
							String mailPass = settings.getMailPass();
							String mailPort = settings.getPortServer();
							
							SMTP_AUTH_USER = fromMail;
							SMTP_AUTH_PWD = mailPass;
							sendMessage(smtpServer,fromMail, mgr.getEmp_id().getEmail(),"Requests to be approved",
									mailPass,mm, mailPort);
							
//							sendMessage(smtpServer,fromMail, "rhassaan@4s-systems.com","Requests to be approved",
//									mailPass,mm, mailPort);
//							sendMessage(request.getSession().getServletContext().getInitParameter("SMTPServer"),
//									fromMail, mgr.getEmp_id().getEmail(), "Requests to be approved",
//									request.getSession().getServletContext().getInitParameter("mailPass"), mm);
							log.debug("mail sent to emp");
						}
						catch(MessagingException ex)
						{
							log.debug(ex.getMessage());
							log.debug(ex.getStackTrace());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			}
			//////////////////////////////////////////////////////////////////
			
			request.getSession().removeAttribute("requestNumber");
		}
			
			
		
		// to check rules of annual vacation
		
		boolean vacLimitProblem = settings.getVacLimitProblem();
		boolean vacationRequestExcep = settings.getVacationRequestExcep();
		
		//log.debug("--diff betn reqdate & fromdate---"+calculateDifference(loginUsersRequests.getFrom_date(), loginUsersRequests.getRequest_date()));
		if(loginUsersRequests!=null){
			log.debug("loginUsersRequests.getRequest_id()---"+loginUsersRequests.getRequest_id());
			if(loginUsersRequests.getRequest_id()!=null && loginUsersRequests.getVacation()!=null){
				log.debug("loginUsersRequests.getRequest_id()-!null--");
				if(loginUsersRequests.getRequest_id().getId()==2 && loginUsersRequests.getVacation().getVacation().equals("001")){
					log.debug("loginUsersRequests.getRequest_id()-==2--");
					if(loginUsersRequests.getWithdrawDays()!=null && !loginUsersRequests.getWithdrawDays().equals("")){
						log.debug("loginUsersRequests.getWithdrawDays()-----");
						Double period=loginUsersRequests.getWithdrawDays();
						log.debug("--period---"+period);
						int difference=calculateDifference(loginUsersRequests.getFrom_date(),loginUsersRequests.getRequest_date());
						log.debug("--diff betn reqdate & fromdate---"+difference);
						if(period.intValue()<2){
							AnnualVacLimit limit=(AnnualVacLimit) requestsApprovalManager.getObjectByParameter(AnnualVacLimit.class, "vac_period", period.intValue()+"");
							
							if(limit!=null && !limit.equals("")){
								log.debug("---limit-id---"+limit.getId());
								log.debug("--limit-period"+limit.getVac_period());
								log.debug("--limit.getVac_period()--<2-"+limit.getVac_period());
								String per=limit.getVac_period();
								if(per.equals(period.intValue()+"")){
									if(limit.getVac_limit().equals(difference+"") || Integer.parseInt(limit.getVac_limit())<difference){
										log.debug("----true---");
									}
									else{
										log.debug("----error---");
										
		//								model.put("msg1", "requestsApproval.errors.vacLimit1");
										model.put("limit", limit.getVac_limit());
										if(vacLimitProblem == true){//Lotus
											errors.reject("requestsApproval.errors.vacLimitProblem");
										}
									}
								}
		//						if(limit.getVac_period().equals("1")){
		//								if(loginUsersRequests.getFrom_date().compareTo(loginUsersRequests.getRequest_date())<0){
		//									log.debug("----from<reqdate---");
		//									errors.reject("requestsApproval.errors.vacLimitOneDay");
		//								}
		//						}
								else{
									if(difference<2){
										log.debug("----difference<2---");
										model.put("limit", limit.getVac_limit());
										//errors.reject("requestsApproval.errors.vacLimitProblem");
									}
								}
							}
							
						}else if(period.intValue()>=2){
							AnnualVacLimit limit=(AnnualVacLimit) requestsApprovalManager.getObjectByParameter(AnnualVacLimit.class, "vac_period", 2+"");
							if(limit!=null && !limit.equals("")){
								log.debug("--limit.getVac_period()---"+limit.getVac_period());
								String per=limit.getVac_period();
								if(per.equals(period.intValue()+"") || Double.parseDouble(per)<period){
									log.debug("--per--"+per);
									if(limit.getVac_limit().equals(difference+"") || Integer.parseInt(limit.getVac_limit())<difference){
										log.debug("----period>=2---");
										model.put("limit", limit.getVac_limit());
										//errors.reject("requestsApproval.errors.vacLimitProblem");
									}
									else if(difference<2){
										log.debug("----period>=2---difference<2--");
										model.put("limit", limit.getVac_limit());
									//	errors.reject("requestsApproval.errors.vacLimitProblem");
									}
								}
								else{
									log.debug("--period msh fl gadwl--period>=2---");
								}
							}
						}
					}
		//			if(period ==1){
		//				if(loginUsersRequests.getFrom_date().compareTo(loginUsersRequests.getRequest_date())<0){
		//					errors.reject("requestsApproval.errors.vacLimitOneDay");
		//				}
		//			}
					
		//			if(period >=2){
		//				if(difference<2){
		//					errors.reject("requestsApproval.errors.vacLimitMoreDays");
		//				}
		//			}
				}
			}
		}
		// end of checking rules

		//Lehaa
		if(vacationRequestExcep==true){
			if(loginUsersRequests!=null && !loginUsersRequests.equals("")){
				log.debug("????? test >>>>>>>");
				if(loginUsersRequests.getId()!=null && !loginUsersRequests.getId().equals("")){
					if(loginUsersRequests.getVacation()!=null){
						log.debug("my case 25-3 ===>>>>"+loginUsersRequests.getRequest_id().getId() + "vacation ??????????"+loginUsersRequests.getVacation().getVacation());
						RequestTypes errands= (RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, new Long(4));
						if(loginUsersRequests.getRequest_id().getId().equals(new Long(1)) && loginUsersRequests.getVacation().getVacation().equals("999")){
							log.debug(">>>>>>>>>test=======");
							Vacation errand=(Vacation) requestsApprovalManager.getObject(Vacation.class, "999");

							loginUsersRequests.setRequest_id(errands);
							loginUsersRequests.setVacation(errand);
						}
					}
				}
			}
		}
		model.put("settings", settings);
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}
	

	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests)command;
		log.debug("-------period from---"+loginUsersRequests.getPeriod_from());
		String emp_code = request.getParameter("empCode");
		log.debug("code entered--------"+emp_code);
		
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		boolean vacationRequestExcep = settings.getVacationRequestExcep();
		boolean reqPeriodDate = settings.getReqPeriodDate();
		
		//Lehaa/////////////////////////////
		if(vacationRequestExcep==true){
			if(loginUsersRequests!=null && !loginUsersRequests.equals("")){
				if(loginUsersRequests.getId()!=null && !loginUsersRequests.getId().equals("")){
					log.debug("req_id---4444------"+loginUsersRequests.getRequest_id().getId());
					RequestTypes specVac= (RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, new Long(1));
					if(loginUsersRequests.getRequest_id().getId()==new Long(4)){
						Vacation errand=(Vacation) requestsApprovalManager.getObject(Vacation.class, "999");
						loginUsersRequests.setRequest_id(specVac);
						loginUsersRequests.setVacation(errand);
					}
				}
			}
		}
		//////////////////////////////////////

//		loginUsersRequests.setLogin_user((LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp_code));
		String annVacation = request.getParameter("annualVacation");
		log.debug("-----annVacation entered--------"+annVacation);
		
		if(annVacation!=null && !annVacation.equals("")){
			Vacation annVac=(Vacation) requestsApprovalManager.getObjectByParameter(Vacation.class, "vacation", annVacation);
			loginUsersRequests.setVacation(annVac);
			log.debug("annVac.getPayed() --------"+annVac.getPayed());
			loginUsersRequests.setPayed(Long.parseLong(annVac.getPayed()));
			log.debug("annVac--loginUsersRequests.getPayed() --------"+loginUsersRequests.getPayed());			
		}
		
		String speVacation = request.getParameter("specialVacation");
		log.debug("-----speVacation entered--------"+speVacation);
			
		if(speVacation!=null && !speVacation.equals("")){
			Vacation specVac=(Vacation) requestsApprovalManager.getObjectByParameter(Vacation.class, "vacation", speVacation);
			loginUsersRequests.setVacation(specVac);
			log.debug("specVac.getPayed() --------"+specVac.getPayed());
			loginUsersRequests.setPayed(Long.parseLong(specVac.getPayed()));
			log.debug("specVac--loginUsersRequests.getPayed() --------"+loginUsersRequests.getPayed());
		}
		
//		MultiCalendarDate mCalDate = new MultiCalendarDate();
//		String vac_period_from=request.getParameter("vac_period_from");
//		vac_period_from=vac_period_from.concat(":00");
//		log.debug("----period-date from entered---"+vac_period_from);
//		String vac_period_to=request.getParameter("vac_period_to");
//		vac_period_to=vac_period_to.concat(":00");
//		log.debug("----period-date to entered---"+vac_period_to);
		if(loginUsersRequests.getRequest_id()!=null && !loginUsersRequests.getRequest_id().equals("")){
			if(reqPeriodDate==true){//Lotus
				if(loginUsersRequests.getRequest_id().getId()==1 && loginUsersRequests.getVacation()!=null && loginUsersRequests.getVacation().getVacation().equals("008")){
					if(loginUsersRequests.getVac_period_from()!=null && !loginUsersRequests.getVac_period_from().equals("")){
						log.debug("----period-date from entered---"+loginUsersRequests.getVac_period_from());
						//mCalDate.setDateString(vac_period_from);
						loginUsersRequests.setPeriod_from(loginUsersRequests.getVac_period_from());
					}
					if(loginUsersRequests.getVac_period_to()!=null && !loginUsersRequests.getVac_period_to().equals("")){
						log.debug("----period-date to entered---"+loginUsersRequests.getVac_period_to());
						//mCalDate.setDateString(vac_period_from);
						loginUsersRequests.setPeriod_to(loginUsersRequests.getVac_period_to());
					}
					//Date date_period_from = mCalDate.getDate();
					//log.debug("-------date_period_from--------"+date_period_from);
					
				}
			} else {//Lehaa
				if(loginUsersRequests.getRequest_id().getId()==2 && loginUsersRequests.getVacation().getVacation().equals("001")){
					if(loginUsersRequests.getVac_period_from()!=null && !loginUsersRequests.getVac_period_from().equals("")){
						log.debug("----period-date from entered---"+loginUsersRequests.getVac_period_from());
						//mCalDate.setDateString(vac_period_from);
						loginUsersRequests.setFrom_date(loginUsersRequests.getVac_period_from());
					}
					if(loginUsersRequests.getVac_period_to()!=null && !loginUsersRequests.getVac_period_to().equals("")){
						log.debug("----period-date to entered---"+loginUsersRequests.getVac_period_to());
						//mCalDate.setDateString(vac_period_from);
						loginUsersRequests.setTo_date(loginUsersRequests.getVac_period_to());
					}
	
				}
			}
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests)command;
		
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		boolean withdrawDaysQuartPolicy = settings.getWithdrawDaysQuartPolicy();
		boolean withoutSalPeriodValidation = settings.getWithoutSalPeriodValidation();
		boolean notesValidation = settings.getNotesValidation();
		boolean fromToRequestVald = settings.getFromToRequestVald();
		boolean automaticRequestsValidation = settings.getAutomaticRequestsValidation();
		
	
		String withdrawDays=request.getParameter("withdrawDays");
		log.debug("-----withdrawDays entered--------"+withdrawDays);
		if(withdrawDays!=null && !withdrawDays.equals("")){
			
			log.debug("--calculated period---"+withdrawDays);
			loginUsersRequests.setWithdrawDays(Double.parseDouble(withdrawDays));
			log.debug("--calculated period-end--"+loginUsersRequests.getWithdrawDays());
			
		}
		
		String withoutSalPeriod= request.getParameter("withoutSalPeriod");
		log.debug("----withoutSalPeriod==="+withoutSalPeriod);
		String withdraw=request.getParameter("withdraw");
		log.debug("----withdraw==="+withdraw);
		if(withoutSalPeriod!=null && !withoutSalPeriod.equals("")){
			if(withdrawDaysQuartPolicy==true){//Lotus
				if(withoutSalPeriod.equals("quar") || withoutSalPeriod.equals("half")){
					log.debug("--fraction period---"+withoutSalPeriod);
					if(withoutSalPeriod.equals("half")){
						loginUsersRequests.setWithdrawDays(new Double(4));
					}
					else if(withoutSalPeriod.equals("quar")){
						loginUsersRequests.setWithdrawDays(new Double(2));
					//}
				//		else if(withoutSalPeriod.equals("full")){
				//			if(withdraw==null || withdraw.equals("")){
				//				errors.reject("requestsApproval.errors.nullRequestPeriod");
				//			}
				//			else{
				//				loginUsersRequests.setWithdrawDays(Double.parseDouble(withdraw));
				//			}
						}
				}
			} else {//Lehaa
				if(withoutSalPeriod.equals("half") || withoutSalPeriod.equals("full")){
					log.debug("--fraction period---"+withoutSalPeriod);
					if(withoutSalPeriod.equals("half")){
						loginUsersRequests.setWithdrawDays(0.5);
					}
	//				else if(withoutSalPeriod.equals("quar")){
	//					loginUsersRequests.setWithdrawDays(0.25);
	//				}
					else if(withoutSalPeriod.equals("full")){
						if(withdraw==null || withdraw.equals("")){
							errors.reject("requestsApproval.errors.nullRequestDatesPeriod");
						}
						else{
							loginUsersRequests.setWithdrawDays(Double.parseDouble(withdraw));
						}
					}
				}
			}
		}
		log.debug("-after setting--"+loginUsersRequests.getWithdrawDays());
		
		String annVacation = request.getParameter("annualVacation");
		log.debug("-----annVacation entered--------"+annVacation);
		
		RequestTypes reqType = (RequestTypes)requestsApprovalManager.getObject(RequestTypes.class, new Long(1));
		Vacation vac = (Vacation)requestsApprovalManager.getObjectByParameter(Vacation.class,"vacation", "999");
		
		if (annVacation != null && !annVacation.isEmpty()) {
			if (loginUsersRequests.getVacCredit()!=null && loginUsersRequests.getVacCredit() == 0) {
				errors.rejectValue("empCode", "requestsApproval.errors.zerovacationcredit");
			}
		}
		
		String speVacation = request.getParameter("specialVacation");
		log.debug("-----speVacation entered--------"+speVacation);
		if(errors.getErrorCount()==0)
		{	
			if(loginUsersRequests==null || loginUsersRequests.equals("")){
				errors.rejectValue("empCode", "commons.errors.requiredFields");
//				errors.rejectValue("name", "commons.errors.requiredFields");
			}
			else{
//				String empName=request.getParameter("name");
				if((loginUsersRequests.getEmpCode()==null || loginUsersRequests.getEmpCode().equals("")))
				{
//					errors.rejectValue("empCode", "commons.errors.requiredFields");
					log.debug("null empcode");
					errors.reject("commons.errors.requiredFields");
				}
				if((loginUsersRequests.getEmpCode()!=null && !loginUsersRequests.getEmpCode().equals("")) ){
					log.debug("----loginUsersRequests.getEmpCode()---"+loginUsersRequests.getEmpCode());
					if(!isOnlyNumbers(loginUsersRequests.getEmpCode())){
						errors.rejectValue("empCode", "requestsApproval.errors.invalidEmpCode");
					}
					LoginUsers login_user=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", loginUsersRequests.getEmpCode());
//					LoginUsers login_user2=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "name", empName);
//					log.debug("----login_user-----"+login_user.getId());
					if(login_user==null || login_user.equals("")){
						log.debug("----login_user==null-----");
						errors.rejectValue("empCode", "requestsApproval.errors.empCodeNotExistance");
					}
//					if(login_user!=null && !login_user.equals("") && login_user2!=null && !login_user2.equals("")){
//						if(!login_user.getEmpCode().equals(login_user2.getEmpCode())){
//							log.debug("----here-not match--");
//							errors.reject("requestsApproval.errors.invalidEmpCodeOrName");
//						}else{
//							loginUsersRequests.setLogin_user(login_user);
//						}
//					}
				}
	
				if(loginUsersRequests.getRequest_id()==null || loginUsersRequests.getRequest_id().equals(""))
				{
					errors.rejectValue("request_id", "commons.errors.requiredFields");
				}
				
				if(loginUsersRequests.getRequest_date()==null || loginUsersRequests.getRequest_date().equals(""))
				{
					errors.rejectValue("request_date", "commons.errors.requiredFields");
				}
				
				if(loginUsersRequests.getRequest_id()!=null && !loginUsersRequests.getRequest_id().equals("")){
					if(loginUsersRequests.getRequest_id().getId()==1 || loginUsersRequests.getRequest_id().getId()==2 || loginUsersRequests.getRequest_id().getId()==4){

						//Lehaa///////////////////////////////
						if((loginUsersRequests.getFrom_date()==null || loginUsersRequests.getFrom_date().equals(""))|| (loginUsersRequests.getTo_date()==null || loginUsersRequests.getTo_date().equals(""))){
							errors.reject("requestsApproval.errors.nullRequestPeriod");
						}
						if((loginUsersRequests.getFrom_date()!=null && !loginUsersRequests.getFrom_date().equals(""))&& (loginUsersRequests.getTo_date()!=null && !loginUsersRequests.getTo_date().equals(""))){
							if (loginUsersRequests.getFrom_date().after(loginUsersRequests.getTo_date())){
								errors.rejectValue("from_date","requestsApproval.errors.toTimeShouldBeGreaterThanOrEqualFromTime");
							}
						}
						//////////////////////////////////////////
						
						if(loginUsersRequests.getWithdrawDays()==null || loginUsersRequests.getWithdrawDays().equals("")){
							log.debug("--null Req period-   2--" +loginUsersRequests.getWithdrawDays());
							errors.reject("requestsApproval.errors.nullRequestPeriod");
						}
						
						if(loginUsersRequests.getRequest_id().getId()!=4){
							if(loginUsersRequests.getVacation()==null || loginUsersRequests.getVacation().equals("")){
								errors.reject("requestsApproval.errors.nullVacationType");
							}
						}else if(loginUsersRequests.getRequest_id().getId()==4){
							if(notesValidation==true){//Lehaa
								if(loginUsersRequests.getNotes().equals("") || loginUsersRequests.getNotes()==null){
									errors.rejectValue("request_id", "requestsApproval.errors.nullNotes");
								}
							}
						}	
						
						if(withoutSalPeriodValidation==true && loginUsersRequests.getRequest_id().getId()==2 
								&& loginUsersRequests.getVacation().getVacation().equals("001")){//Lehaa
							if(withoutSalPeriod==null || withoutSalPeriod.equals("")){
								errors.reject("commons.errors.requiredFields");
							}
						} else if ((withoutSalPeriodValidation==false &&
								!(loginUsersRequests.getRequest_id().getId()==1 && loginUsersRequests.getVacation()!=null
								&& (loginUsersRequests.getVacation().getVacation().equals("008") || 
										loginUsersRequests.getVacation().getVacation().equals("010")))) // Lotus
								|| withoutSalPeriodValidation==true && !(loginUsersRequests.getRequest_id().getId()==2 
										&& loginUsersRequests.getVacation().getVacation().equals("001")) //Lehaa
								){
							
							if((loginUsersRequests.getFrom_date()==null || loginUsersRequests.getFrom_date().equals(""))|| (loginUsersRequests.getTo_date()==null || loginUsersRequests.getTo_date().equals(""))){
							log.debug("--null Req period- 1111--" +loginUsersRequests.getWithdrawDays());
							errors.reject("requestsApproval.errors.nullRequestPeriod");
							}
							if((loginUsersRequests.getFrom_date()!=null && !loginUsersRequests.getFrom_date().equals(""))&& (loginUsersRequests.getTo_date()!=null && !loginUsersRequests.getTo_date().equals(""))){
								if (loginUsersRequests.getFrom_date().after(loginUsersRequests.getTo_date())){
									errors.rejectValue("from_date","requestsApproval.errors.toTimeShouldBeGreaterThanOrEqualFromTime");
								}
							}
							
							int requestsDeadline = (Integer)request.getSession().getAttribute("requestsDeadline");
							Calendar c = Calendar.getInstance();
							c.add(Calendar.HOUR_OF_DAY, (-1)*requestsDeadline);
							log.debug("deadline time " + c.getTime());
//							log.debug("loginUsersRequests.getFrom_date().before(c.getTime()) " + loginUsersRequests.getFrom_date().before(c.getTime()));
							if (loginUsersRequests.getFrom_date()==null) {
								errors.reject("commons.errors.requiredFields");
							} else if (loginUsersRequests.getFrom_date().before(c.getTime())){
								errors.rejectValue("from_date","requestsApproval.errors.requestDeadlineExceeded");
							}
							if(loginUsersRequests.getVacation()!=null && loginUsersRequests.getVacation().getVacation()!= null && 
									loginUsersRequests.getVacation().getVacation().equals("010")){
								if (loginUsersRequests.getAltDate()!=null && ! loginUsersRequests.getAltDate().equals("")){
									errors.reject("commons.errors.requiredFields");
								}
							}
							
							// to check rules of annual vacation
						
						//log.debug("--diff betn reqdate & fromdate---"+calculateDifference(loginUsersRequests.getFrom_date(), loginUsersRequests.getRequest_date()));
						//if(loginUsersRequests!=null){
							log.debug("loginUsersRequests.getRequest_id()---"+loginUsersRequests.getRequest_id());
							if(loginUsersRequests.getVacation()!=null){
								log.debug("loginUsersRequests.getRequest_id()-!null--");
								if(loginUsersRequests.getRequest_id().getId()==2 && loginUsersRequests.getVacation().getVacation().equals("001")){
									log.debug("loginUsersRequests.getRequest_id()-==2--");
									if(loginUsersRequests.getWithdrawDays()!=null && !loginUsersRequests.getWithdrawDays().equals("")){
										log.debug("loginUsersRequests.getWithdrawDays()-----");
										Double period=loginUsersRequests.getWithdrawDays();
										log.debug("--period->>>>>>>--"+period);
										int difference=calculateDifference(loginUsersRequests.getFrom_date(),loginUsersRequests.getRequest_date());
										log.debug("--diff --->>>"+difference);
										if(period.intValue()<2){
											AnnualVacLimit limit=(AnnualVacLimit) requestsApprovalManager.getObjectByParameter(AnnualVacLimit.class, "vac_period", period.intValue()+"");
											log.debug("---limit-limit---"+limit.getVac_limit());
											log.debug("--limit.getVac_period()--<2-"+limit.getVac_period());
											if(limit!=null && !limit.equals("")){
												log.debug("--limit period()---"+limit.getVac_period());
												String per=limit.getVac_period();
												if(per.equals(period.intValue()+"")){
													if(limit.getVac_limit().equals(difference+"") || Integer.parseInt(limit.getVac_limit())<difference){
														log.debug("--->>>>-true---");
													}
													else{
														log.debug("-->>>--error---");
														
						//								model.put("msg1", "requestsApproval.errors.vacLimit1");
														//model.put("limit", limit.getVac_limit());
														errors.reject("requestsApproval.errors.vacLimitProblem");
													}
												}
						//						if(limit.getVac_period().equals("1")){
						//								if(loginUsersRequests.getFrom_date().compareTo(loginUsersRequests.getRequest_date())<0){
						//									log.debug("----from<reqdate---");
						//									errors.reject("requestsApproval.errors.vacLimitOneDay");
						//								}
						//						}
												else{
													if(difference<2){
														log.debug("----difference<2---");
														//model.put("limit", limit.getVac_limit());
														errors.reject("requestsApproval.errors.vacLimitProblem");
													}
												}
											}
											
										}else if(period.intValue()>=2){
											AnnualVacLimit limit=(AnnualVacLimit) requestsApprovalManager.getObjectByParameter(AnnualVacLimit.class, "vac_period", 2+"");
											if(limit!=null && !limit.equals("")){
												log.debug("--limit.getVac_period()---"+limit.getVac_period());
												String per=limit.getVac_period();
												if(per.equals(period.intValue()+"") || Double.parseDouble(per)<period){
													log.debug("--per--"+per);
													if(limit.getVac_limit().equals(difference+"") || Integer.parseInt(limit.getVac_limit())<difference){
														log.debug("----period>=2---");
														//model.put("limit", limit.getVac_limit());
														
													}
													else{
														errors.reject("requestsApproval.errors.vacLimitProblem");
													}
													
												}else if(difference<2){
													log.debug("----period>=2---difference<2--");
													//model.put("limit", limit.getVac_limit());
													errors.reject("requestsApproval.errors.vacLimitProblem");
												}
												else{
													log.debug("--period msh fl gadwl--period>=2---");
												}
											}
										}
									}
						//			if(period ==1){
						//				if(loginUsersRequests.getFrom_date().compareTo(loginUsersRequests.getRequest_date())<0){
						//					errors.reject("requestsApproval.errors.vacLimitOneDay");
						//				}
						//			}
									
						//			if(period >=2){
						//				if(difference<2){
						//					errors.reject("requestsApproval.errors.vacLimitMoreDays");
						//				}
						//			}
								}
							}
						//}
						// end of checking rules
							
					}
				}
					else if(loginUsersRequests.getRequest_id().getId()==3){
						if((loginUsersRequests.getPeriod_from()==null || loginUsersRequests.getPeriod_from().equals(""))||(loginUsersRequests.getPeriod_to()==null||loginUsersRequests.getPeriod_to().equals(""))){
							errors.reject("requestsApproval.errors.nullRequestPeriod");
						}
						if(loginUsersRequests.getLeave_effect()==null || loginUsersRequests.getLeave_effect().equals("")){
							errors.reject("requestsApproval.errors.nullLeaveEffect");
						}
						if(loginUsersRequests.getLeave_type()==null || loginUsersRequests.getLeave_type().equals("")){
							errors.reject("requestsApproval.errors.nullLeaveType");
						}

						
						if(fromToRequestVald==true){//Lotus
							String perFrom=loginUsersRequests.getPeriod_from()+"";
							String perTo=loginUsersRequests.getPeriod_to()+"";
							String ar_from[]=perFrom.split(":");
							String fromHour =ar_from[0];
							fromHour=fromHour.substring(fromHour.length()-2);
							log.debug("fromHour=== "+fromHour);
							String fromMinutes =ar_from[1];
							log.debug("fromMinutes=== "+fromMinutes);
							String pmAM=perFrom.substring(6, perFrom.length()-1);
							String ar_to[]=perTo.split(":");
							String toHour =ar_to[0];
							toHour=toHour.substring(toHour.length()-2);
							log.debug("toHour=== "+toHour);
							String toMinutes =ar_to[1];
							log.debug("toMinutes=== "+toMinutes);
							if(Integer.parseInt(fromHour)>Integer.parseInt(toHour)){
								errors.reject("requestsApproval.errors.fromIsGreaterThanTo");
							}else if((Integer.parseInt(fromHour)==Integer.parseInt(toHour))&& (Integer.parseInt(fromHour)!=0 && Integer.parseInt(toHour)!=0)){
								log.debug("after parsing to int===fromMins=== "+Integer.parseInt(fromMinutes));
								log.debug("after parsing to int===toMinutes=== "+Integer.parseInt(toMinutes));
								if(Integer.parseInt(fromMinutes)>Integer.parseInt(toMinutes) || Integer.parseInt(fromMinutes)==Integer.parseInt(toMinutes)){
									errors.reject("requestsApproval.errors.fromIsGreaterThanTo");
								}
							}
							
							if((Integer.parseInt(fromHour)==0)||(Integer.parseInt(toHour)==0)){
								errors.reject("requestsApproval.errors.fromOrToIsZero");
							}
						}
						
					}
					
				}
				if(notesValidation==true){
					if(loginUsersRequests.getNotes().equals("") || loginUsersRequests.getNotes()==null){
						errors.rejectValue("notes", "commons.errors.requiredFields");
					}
				}
				

				if (automaticRequestsValidation==true) {
					log.debug("loginUsersRequests.getRequest_id() " + loginUsersRequests.getRequest_id().getId());
					if (loginUsersRequests.getRequest_id().getId().equals(new Long(4))) {

						//check if full day errand


						String perFrom=loginUsersRequests.getPeriod_from()+"";
						String perTo=loginUsersRequests.getPeriod_to()+"";

						Map att = requestsApprovalManager.checkAttendance(loginUsersRequests.getPeriod_from(), loginUsersRequests.getEmpCode());
						AttendanceStatus attendanceResponse = (AttendanceStatus)att.get("Response");
						System.out.println("attendance status response " + attendanceResponse);
						RequestsApprovalQuery requestQuery = new RequestsApprovalQuery();
						requestQuery.setDateFrom(perFrom);
						requestQuery.setDateTo(perTo);
						System.out.println("attendanceResponse.getSignIn() " + attendanceResponse.getSignIn());

						Employee e = (Employee)requestsApprovalManager.getObjectByParameter(Employee.class, "empCode", loginUsersRequests.getEmpCode());
						Map checkStartedMap = requestsApprovalManager.checkStartedRequests(requestQuery, e);
						System.out.println("after checking started requests " + checkStartedMap);
						List startedRequests = (List)checkStartedMap.get("Response");
						System.out.println("after checking started requests 2" + startedRequests);

						if (attendanceResponse!=null && attendanceResponse.getSignIn()!=null && attendanceResponse.getSignIn().equals(new Boolean(true))) {
							// check attendance on this day//

							System.out.println("attendance status response " + attendanceResponse.getSignIn());

							errors.rejectValue("request_id","requestsApproval.errors.fullDayErrandIsNotAllowedToday","User Signed In Already on the specified date, full day errand is not allowed.");

							////////////////////////////////
						} else if (startedRequests != null && startedRequests.size() > 0) {
							errors.rejectValue("request_id","requestsApproval.errors.fullDayErrandIsNotAllowedToday","Another request is made already on the specified date, full day errand is not allowed.");
						}
					} else {
						//if not full day errand check requests overlapping/////////////////
						////////////////////////////////////1///////////////////////////////
						//Signing in ///////////////////////////////////////////////////////
						////////////////////////////////////////////////////////////////////
						//					if(loginUsersRequests.getRequest_id().getId().equals(new Long(10))) {
						Calendar temp = Calendar.getInstance();
						temp.setTime(loginUsersRequests.getPeriod_from());

						List requests = requestsApprovalManager.getRequestsByExactDatePeriodAndEmpCode(loginUsersRequests.getPeriod_from(), loginUsersRequests.getPeriod_to(), loginUsersRequests.getEmpCode());
						if (requests.size() >0) {
							Iterator reqItr = requests.iterator();
							while (reqItr.hasNext()) {
								LoginUsersRequests req = (LoginUsersRequests)reqItr.next();
								if (req.getPeriod_to() == null) {
									errors.rejectValue("request_id","requestsApproval.errors.endStartedRequestsInDateIntervalSpecified");
								} else {
									if (req.getPeriod_to().compareTo(loginUsersRequests.getPeriod_from()) > 0) {
										errors.rejectValue("request_id","requestsApproval.errors.overlappingRequests");
									}
								}
							}
						}

						//					}
					}
					///////////////////////////////////////////////////////////////////
				}
			}
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests)command;
//		request.getSession().setAttribute("loginUsersRequests", loginUsersRequests);
		log.debug("----loginUsersRequests.getId()-onsubmit-----"+loginUsersRequests.getId()+"-----loginUsersRequests---"+loginUsersRequests.getLogin_user().getEmpCode());
		log.debug("------date from command---"+loginUsersRequests.getFrom_date());
		Map model=new HashMap();
		
		String reqId="";
		
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		boolean withoutSalaryVacEnabled = settings.getWithoutSalaryVacEnabled(); 
		boolean periodFromToEnabled = settings.getPeriodFromToEnabled();
		
		String requestNumber="";
		
//		Date from_date=loginUsersRequests.getFrom_date();
//		
//		String vacId=loginUsersRequests.getVacation().getId()+"";
//		
//		log.debug("--------vacId---"+vacId);
//		if(vacId!=null && !vacId.equals("") && from_date!=null){
//			log.debug("----fromdate--"+from_date);
//			model.put("vacCredit", requestsApprovalManager.getVacationCredit("oraserv", "oraserv", "lotus_pay10", "lotus_pay10", loginUsersRequests.getEmpCode(), new Long(2),vacId, from_date));
//		}
//		loginUsersRequests.setVacCredit(requestsApprovalManager.getVacationCredit("oraserv", "oraserv", "lotus_pay10", "lotus_pay10", loginUsersRequests.getEmpCode(), new Long(2),vacId, from_date));
//		
//		String getVac=request.getParameter("getVac");
//		if(getVac!=null && !getVac.equals("")){
//			return new ModelAndView(new RedirectView(),model);
//		}
//		else{
			 // request number
			if (loginUsersRequests.getId() == null){
				requestNumber=requestsApprovalManager.CreateRequestNumber();
				loginUsersRequests.setRequestNumber(requestNumber);
			}
			
	//		String emp_code = request.getParameter("empCode");
	//		log.debug("code entered--------"+emp_code);
	//		if(emp_code!=null && !emp_code.equals("")){
	//			loginUsersRequests.setEmpCode(emp_code);
	//		}
			log.debug("loginUsersRequests.getEmpCode() entered--------"+loginUsersRequests.getEmpCode());
			
			String withoutSalPeriod= request.getParameter("withoutSalPeriod");	
			log.debug("-----quar or half---"+withoutSalPeriod);
			
			
			if(loginUsersRequests.getRequest_id()!=null && !loginUsersRequests.getRequest_id().equals("")){
				log.debug("entered--1-----reqid=-"+loginUsersRequests.getRequest_id().getId());
				reqId=loginUsersRequests.getRequest_id().getId().toString();
				if(loginUsersRequests.getRequest_id().getId()==4 && loginUsersRequests.getRequest_id().getParentId()!=null && !loginUsersRequests.getRequest_id().getParentId().equals("")){
					log.debug("entered--2-----parent-"+loginUsersRequests.getRequest_id().getParentId());
					loginUsersRequests.setRequest_id(loginUsersRequests.getRequest_id().getParentId());
					Vacation vac= (Vacation)requestsApprovalManager.getObject(Vacation.class, "999");
					loginUsersRequests.setVacation(vac);
					loginUsersRequests.setPayed(new Long(1));
				}
				
				
				if(withoutSalaryVacEnabled==true){//Lotus
					if(loginUsersRequests.getRequest_id().getId()==1 && loginUsersRequests.getVacation().getVacation().equals("008")){
						//tasree7 object
						log.debug("---without Salary vacation ---");
						log.debug("---------loginUsersRequests.getPeriod_from()-------"+loginUsersRequests.getPeriod_from());
						
						LoginUsersRequests withoutSalVac= new LoginUsersRequests();
						RequestTypes request_id= (RequestTypes) requestsApprovalManager.getObject(RequestTypes.class, new Long (3));
						
						withoutSalVac.setEmpCode(loginUsersRequests.getEmpCode());
						withoutSalVac.setLogin_user(loginUsersRequests.getLogin_user());
						withoutSalVac.setRequest_date(loginUsersRequests.getRequest_date());
						withoutSalVac.setNotes(loginUsersRequests.getNotes());
						withoutSalVac.setLeave_type("0");
						withoutSalVac.setPeriod_from(loginUsersRequests.getPeriod_from());
						withoutSalVac.setPeriod_to(loginUsersRequests.getPeriod_to());
						withoutSalVac.setRequest_id(request_id);
						withoutSalVac.setApproved(new Long(0));
						withoutSalVac.setApplicable(new Long(1));
						withoutSalVac.setPosted(new Long(0));
						withoutSalVac.setReply("--");
						withoutSalVac.setTo_date(loginUsersRequests.getPeriod_to());
						withoutSalVac.setFrom_date(loginUsersRequests.getPeriod_from());
						
						String leaveTime =request.getParameter("leaveTime");
						if(leaveTime!=null &&  !leaveTime.equals("")){
							if(leaveTime.equals("start")){
								withoutSalVac.setLeave_effect("2");
							}else if(leaveTime.equals("end")){
								withoutSalVac.setLeave_effect("1");
							}
						}
						log.debug("---without Salary vacation - befor saving--"+withoutSalVac.getRequest_id().getId());
						requestsApprovalManager.saveObject(withoutSalVac);
					}
				}
				
			}
			
			if(loginUsersRequests.getApproved()==null || loginUsersRequests.getApproved().equals("")){
				loginUsersRequests.setApproved(new Long(0));	
			}
			if(loginUsersRequests.getApplicable()==null || loginUsersRequests.getApplicable().equals("")){
				loginUsersRequests.setApplicable(new Long(1));			
			}
			if(loginUsersRequests.getPosted()==null||loginUsersRequests.getPosted().equals("")){
				loginUsersRequests.setPosted(new Long(0));
			}
			if(loginUsersRequests.getReply()==null||loginUsersRequests.getReply().equals("")){
				loginUsersRequests.setReply("--");
			}
			if(loginUsersRequests.getLeave_effect()==null||loginUsersRequests.getLeave_effect().equals("")){
				loginUsersRequests.setLeave_effect("0");
			}
			if(loginUsersRequests.getLeave_type()==null||loginUsersRequests.getLeave_type().equals("")){
				loginUsersRequests.setLeave_type("0");
			}
			
			if(periodFromToEnabled==true){//Lehaa
				if(loginUsersRequests.getFrom_date()==null|| loginUsersRequests.getFrom_date().equals("")){
					loginUsersRequests.setFrom_date(loginUsersRequests.getPeriod_from());
				}
				if(loginUsersRequests.getTo_date()==null|| loginUsersRequests.getTo_date().equals("")){
					loginUsersRequests.setTo_date(loginUsersRequests.getPeriod_to());
				}
			}else {//Lotus
				if(loginUsersRequests.getFrom_date()==null|| loginUsersRequests.getFrom_date().equals("")){
					loginUsersRequests.setFrom_date(loginUsersRequests.getRequest_date());
				}
				if(loginUsersRequests.getTo_date()==null|| loginUsersRequests.getTo_date().equals("")){
					loginUsersRequests.setTo_date(loginUsersRequests.getRequest_date());
				}
			}
			log.debug("---requestType from jsp---"+request.getParameter("requestType"));
	//		loginUsersRequests.setApproved();
			
			//Lotus////////////////////////////////////////////////////////
			 // request number
			if (loginUsersRequests.getId() == null){
				requestNumber=requestsApprovalManager.CreateRequestNumber();
				loginUsersRequests.setRequestNumber(requestNumber);
			}
			//////////////////////////////////////////////////////////////
			
			requestsApprovalManager.saveObject(loginUsersRequests);
				
			request.getSession().setAttribute("requestNumber", loginUsersRequests.getId());
			
			String url="loginUsersRequestsForm.html?done=true&requestId="+reqId;
	
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<  End onSubmit : <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	
			model.put("settings", settings);
			return new ModelAndView(new RedirectView(url),model);
		
		//return new ModelAndView(new RedirectView(getSuccessView()));
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
	
	public static int calculateDifference(Date a, Date b)
	{
	    int tempDifference = 0;
	    int difference = 0;
	    Calendar earlier = Calendar.getInstance();
	    Calendar later = Calendar.getInstance();
	    System.out.println("-------a.compareTo(b)---"+a.compareTo(b));
	    if (a.compareTo(b) < 0)
	    {
	        earlier.setTime(b);
	        later.setTime(a);
	    }
	    else
	    {
	        earlier.setTime(b);
	        later.setTime(a);
	    }

	    while (earlier.get(Calendar.YEAR) != later.get(Calendar.YEAR))
	    {
	        tempDifference = 365 * (later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR));
	        difference += tempDifference;

	        earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
	    }

	    if (earlier.get(Calendar.DAY_OF_YEAR) != later.get(Calendar.DAY_OF_YEAR))
	    {
	        tempDifference = later.get(Calendar.DAY_OF_YEAR) - earlier.get(Calendar.DAY_OF_YEAR);
	        difference += tempDifference;

	        earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
	    }

	    return difference;
	}

    private void sendMessage(String smtpServer,String from, String to,
            String subject,String pass, String emailContent, String port) throws Exception {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", smtpServer);
//        properties.put("mail.password", pass);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.socketFactory.port", port);
        properties.put("mail.smtp.auth", "true"); 
        Authenticator auth =  new SMTPAuthenticator();
        Session session = Session.getDefaultInstance(properties,auth);
        MimeMessage mailMsg = new MimeMessage(session);
        mailMsg.setHeader("Content-Type", "text/plain; charset=UTF-8");
        InternetAddress[] addresses = null;
        log.debug("from mail "+from);
        log.debug("to mail "+to);
        log.debug("pass mail "+pass);
        log.debug("smtp >>>>>>"+smtpServer);
        log.debug("port >>>>>>"+port);
        
        System.out.println("I'm here inside send message");
        try {
            if (to != null) {
                addresses = InternetAddress.parse(to, false);
                mailMsg.setRecipients(Message.RecipientType.TO, addresses);
            }
            if (from != null) {
                mailMsg.setFrom(new InternetAddress(from));
            }
            if (subject != null) {
                mailMsg.setSubject(subject);
                mailMsg.setSubject(subject, "UTF-8");
            }

            if (emailContent != null) {
                mailMsg.setText(emailContent);
                mailMsg.setText(emailContent, "UTF-8");
            }
            Transport.send(mailMsg);
            log.debug("true send >>>>>>"+mailMsg);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    
    private class SMTPAuthenticator extends javax.mail.Authenticator { public PasswordAuthentication getPasswordAuthentication() { String username = SMTP_AUTH_USER; 
    String password = SMTP_AUTH_PWD; 
    return new PasswordAuthentication(username, password); } }
}
