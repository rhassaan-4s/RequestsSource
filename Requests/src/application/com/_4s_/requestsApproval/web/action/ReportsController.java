package com._4s_.requestsApproval.web.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com._4s_.requestsApproval.model.AccessLevels;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;


public class ReportsController extends BaseSimpleFormController{
	RequestsApprovalManager requestsApprovalManager;
	List<String> ordered1= new ArrayList<String>();
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

		List tempneededRequestTypes = new ArrayList();
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests) command;
		Map model=new HashMap();		
		
//		String emp_code = request.getParameter("empCode");
//		log.debug("----emp_code---"+emp_code);
//		model.put("employeeCode", emp_code);
		
//		String empName = request.getParameter("empName");
//		log.debug("----empName---"+empName);
//		if(empName!=null && !empName.equals("")){
//			LoginUsers loginUser=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "id", Long.parseLong(empName));
//			log.debug("----loginUser.getName()---"+loginUser.getName());
//			List loginUserReqs= requestsApprovalManager.getObjectsByParameter(LoginUsersRequests.class, "login_user", loginUser);
//			
//			model.put("employeeName", loginUser.getName());
//			model.put("loginUserReqs", loginUserReqs);
//			
//		}
		
		String dateFrom = request.getParameter("dateFrom");
		log.debug("--dateFrom--"+dateFrom);
		model.put("request_date_from", dateFrom);
		String dateTo = request.getParameter("dateTo");
		log.debug("--dateTo--"+dateTo);
		model.put("request_date_to", dateTo);
		log.debug("---xxxxxxxDatePeriod--");
		String codeFrom=request.getParameter("codeFrom");
		String codeTo=request.getParameter("codeTo");
		String requestType= request.getParameter("requestType");
		log.debug("--requestType--"+requestType);
		//model.put("request_id", requestType);
		
		if(requestType!=null && dateFrom!=null && dateTo!=null && codeFrom!=null && codeTo!=null){
			if((requestType.equals(""))&&(dateFrom.equals(""))&&(dateTo.equals(""))&&(codeTo.equals(""))&&(codeFrom.equals(""))){
				tempneededRequestTypes=new ArrayList();
				//List<String> fields=new ArrayList<String>();
				//fields.add("period_from");
				List allRequests=requestsApprovalManager.getObjectsOrderedByField(LoginUsersRequests.class,"period_from");
				//model.put("loginUserReqs", allRequests);
				tempneededRequestTypes=allRequests;
			}
		} 
		

		
//		
//		if(emp_code!=null && !emp_code.equals("")){
//			log.debug("---xxxxxxxCodeName--");
//			if(isOnlyNumbers(emp_code) && (requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp_code)!=null && !requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp_code).equals(""))){
//				LoginUsers loginUser=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp_code);
//				log.debug("--loginUser.getName()--"+loginUser.getName());
//
//				// to get requests corresponding to request type
//				List loginUserReqs=(List) requestsApprovalManager.getObjectsByParameter(LoginUsersRequests.class, "login_user", loginUser);
//				List neededRequestTypes = new ArrayList();
//				for(int i=0;i<loginUserReqs.size();i++){
//					log.debug("---needed---");
//					LoginUsersRequests reqs= (LoginUsersRequests) loginUserReqs.get(i);
//					log.debug("---needed reqs---"+reqs.getEmpCode()+"----reqs.getRequest_id().getId()--"+reqs.getRequest_id().getId());
//					String request_type=Long.toString(reqs.getRequest_id().getId());
//					log.debug("-----requestType.equals(reqs.getRequest_id().getId())----"+requestType.equals(reqs.getRequest_id().getId()));
//					
//					log.debug("-X--requestType.equals(request_type)--"+requestType.equals(request_type));
//					if(requestType.equals(request_type)){
//						neededRequestTypes.add(reqs);
//						log.debug("---neededList---"+neededRequestTypes.size());
//					}
//					log.debug("---after IF neededList---"+neededRequestTypes.size());				
//				}
////				log.debug("--list.size--"+neededRequestTypes.size());
//				tempneededRequestTypes=neededRequestTypes;
//				//model.put("loginUserReqs", neededRequestTypes);
//			}
//
//		}


		model.put("codeFrom", codeFrom);
		model.put("codeTo", codeTo);
		if(codeFrom!=null && codeTo!=null && !codeFrom.equals("")&& !codeTo.equals("")){
			tempneededRequestTypes=new ArrayList();
			List loginUserReqs=(List) requestsApprovalManager.getEmployeesByCodes(codeFrom, codeTo);
			log.debug("---codesList---"+loginUserReqs.size());
			tempneededRequestTypes=loginUserReqs;
			//model.put("records", loginUserReqs);
			for (int i = 0; i < loginUserReqs.size(); i++) {
				LoginUsersRequests s=(LoginUsersRequests) loginUserReqs.get(i);
				log.debug("---code code---"+s.getEmpCode());
			}
		}
		
		MultiCalendarDate mCalDate = new MultiCalendarDate();


		if (dateFrom != null && dateTo != null){
			if (!dateFrom.equals("") && !dateTo.equals("") ) {
				tempneededRequestTypes=new ArrayList();
				Date fromDate = null;
				Date toDate = null;
				log.debug(">>>>>>>>>>>>> if ");
				log.debug(">>>>>>>>>>>Valid date format");
				log.debug(">>>>>>>>>>>>>fromDateString "+ dateFrom);
				//fromDateStr = fromDateString +" 00:00";
//				mCalDate.setDateTimeString(dateFrom,new Boolean(true));
//				fromDate = mCalDate.getDate();
				
				DateFormat formatInput =	new SimpleDateFormat("dd/MM/yyyy");// HH:mm:ss
				DateFormat formatOutput =	new SimpleDateFormat("yyyy/MM/dd");// HH:mm:ss
				
				try {
					fromDate = formatInput.parse(dateFrom);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
				log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
				//toDateStr = toDateString+ " 23:59";
//				mCalDate.setDateTimeString(dateTo,new Boolean(false));
//				toDate= mCalDate.getDate();
				
				try {
					toDate = formatInput.parse(dateTo);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				log.debug(">>>>>>>>>>>>>toDate "+ toDate);
				List loginUserReqs=(List) requestsApprovalManager.getRequestsByDatePeriod(fromDate, toDate);
//				if(fromDate.compareTo(toDate)==0){
//					loginUserReqs.add(requestsApprovalManager.getObjectByParameter(LoginUsersRequests.class, "request_date", fromDate));
//				}
				log.debug("--dateList.size--"+loginUserReqs.size());
				//model.put("loginUserReqs", loginUserReqs);
				tempneededRequestTypes=loginUserReqs;
				
				if(codeFrom!=null && codeTo!=null && !codeFrom.equals("")&& !codeTo.equals("")){
					List reqs=(List) requestsApprovalManager.getEmployeesByCodesAndDatePeriod(codeFrom, codeTo, fromDate, toDate);
					log.debug("---codesList---"+loginUserReqs.size());
					tempneededRequestTypes=reqs;
					//model.put("records", loginUserReqs);
					for (int i = 0; i < loginUserReqs.size(); i++) {
						LoginUsersRequests s=(LoginUsersRequests) loginUserReqs.get(i);
//						log.debug("---code code---"+s.getEmpCode());
					}
				}
			}
		}

		if(requestType!=null && dateFrom!=null && dateTo!=null && codeFrom!=null && codeTo!=null){
			if((!requestType.equals(""))&&(!dateFrom.equals(""))&&(!dateTo.equals("")) &&(!codeFrom.equals("")) && (!codeTo.equals(""))){
				log.debug("---------here------kkk"+tempneededRequestTypes.size());
				Date fromDate = null;
				Date toDate = null;
				mCalDate.setDateTimeString(dateFrom,new Boolean(true));
				fromDate = mCalDate.getDate();
				mCalDate.setDateTimeString(dateTo,new Boolean(false));
				toDate= mCalDate.getDate();
				if(!requestType.equals("4")){
					tempneededRequestTypes=new ArrayList();
					log.debug("----size after----"+tempneededRequestTypes.size());
					List requests=requestsApprovalManager.getEmployeesByCodesAndDatePeriodAndRequestType(codeFrom, codeTo,fromDate, toDate, new Long(requestType));
					log.debug("sie of sent list--- "+requests.size());
					List filtered= new ArrayList();
					for (int i = 0; i < requests.size(); i++) {
						LoginUsersRequests requ=(LoginUsersRequests) requests.get(i);
						if(requ.getVacation()!=null && !requ.getVacation().equals("")){
							if(!requ.getVacation().getVacation().endsWith("999")){
								filtered.add(requ);
							}
						}
						else{
							filtered.add(requ);
						}
					}
					tempneededRequestTypes=filtered;
				}
				else{
					tempneededRequestTypes=new ArrayList();
					List allRequests= requestsApprovalManager.getRequestsByDatePeriodAndRequestType(fromDate, toDate, new Long(1));
					for (int i = 0; i < allRequests.size(); i++) {
						LoginUsersRequests requests=(LoginUsersRequests) allRequests.get(i);
						if((requests.getRequest_id().getId()==1)&& (requests.getVacation().getVacation().equals("999"))){
							tempneededRequestTypes=allRequests;
						}
					}
				}
				
			}
		}
		
		if(requestType!=null && !requestType.equals("")){
			log.debug("----reqtype---"+dateFrom.equals("")+"****"+ dateTo.equals(""));
			if ((dateFrom.equals("") && dateTo.equals("")) ) {
				if(requestType.equals("4")){
					List errands=new ArrayList();
					tempneededRequestTypes=new ArrayList();
					List<String> fields=new ArrayList<String>();
					fields.add("period_from");
					tempneededRequestTypes=requestsApprovalManager.getObjectsByParameterOrderedByFieldList(LoginUsersRequests.class, "request_id.id", new Long(1), fields);
					for (int i = 0; i < tempneededRequestTypes.size(); i++) {
						LoginUsersRequests req=(LoginUsersRequests) tempneededRequestTypes.get(i);
						if(req.getVacation().getVacation().equals("999")){
							errands.add(req);
						}
					}tempneededRequestTypes=errands;
				}
				else{
					log.debug("----reqtype-!4--");
					List filtered= new ArrayList();
					tempneededRequestTypes=new ArrayList();
					List<String> fields=new ArrayList<String>();
					fields.add("period_from");
					tempneededRequestTypes=requestsApprovalManager.getObjectsByParameterOrderedByFieldList(LoginUsersRequests.class, "request_id.id", new Long(requestType), fields);
					for (int i = 0; i < tempneededRequestTypes.size(); i++) {
						LoginUsersRequests req=(LoginUsersRequests) tempneededRequestTypes.get(i);
						if(req.getVacation()!=null && !req.getVacation().equals("")){
							if(!req.getVacation().getVacation().equals("999")){
								log.debug("----ay7aga---");
								filtered.add(req);
							}
						}else{
							filtered.add(req);
						}
					}
					tempneededRequestTypes=filtered;
				}
			
				if((codeFrom!=null && !codeFrom.equals("")) && (codeTo!=null && !codeTo.equals(""))){
					if(requestType.equals("4")){
						List errands=new ArrayList();
						tempneededRequestTypes=new ArrayList();
						tempneededRequestTypes=requestsApprovalManager.getEmployeesByCodesAndRequestType(codeFrom, codeTo,  new Long(1));
						for (int i = 0; i < tempneededRequestTypes.size(); i++) {
							LoginUsersRequests req=(LoginUsersRequests) tempneededRequestTypes.get(i);
							if(req.getVacation().getVacation().equals("999")){
								errands.add(req);
							}
						}tempneededRequestTypes=errands;
					}
					else{
						log.debug("----reqtype-!4--");
						List filtered= new ArrayList();
						tempneededRequestTypes=new ArrayList();
						tempneededRequestTypes=requestsApprovalManager.getEmployeesByCodesAndRequestType(codeFrom, codeTo, new Long(requestType));
						for (int i = 0; i < tempneededRequestTypes.size(); i++) {
							LoginUsersRequests req=(LoginUsersRequests) tempneededRequestTypes.get(i);
							if(req.getVacation()!=null && !req.getVacation().equals("")){
								if(!req.getVacation().getVacation().equals("999")){
									log.debug("----ay7aga---");
									filtered.add(req);
								}
							}else{
								filtered.add(req);
							}
						}
						tempneededRequestTypes=filtered;
					}
				}
			}
			
		}

//		
//		if(codeFrom!=null  && codeTo!=null && dateFrom!=null && dateTo!=null){
//			if((!codeFrom.equals(""))&& (!codeTo.equals(""))&&(!dateFrom.equals(""))&&(!dateTo.equals(""))){
//				
//				Date fromDate = null;
//				Date toDate = null;
//				mCalDate.setDateTimeString(dateFrom,new Boolean(true));
//				fromDate = mCalDate.getDate();
//				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
//				log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
//				//toDateStr = toDateString+ " 23:59";
//				mCalDate.setDateTimeString(dateTo,new Boolean(false));
//				toDate= mCalDate.getDate();
//			
//				List allRequests= requestsApprovalManager.getRequestsByDatePeriod(fromDate, toDate);
//
//				//model.put("loginUserReqs", allRequests);
//				tempneededRequestTypes=allRequests;
//			}
//		}
		List actualRequest= new ArrayList();
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
//		List<String> ordered= new ArrayList();
//		ordered.add("order");
		
		ordered1.add("emp_id");
		for(int i=0;i<tempneededRequestTypes.size() ;i++){
			boolean flag=true;
			LoginUsersRequests temp=(LoginUsersRequests)tempneededRequestTypes.get(i);
			log.debug("-----temp----"+temp.getEmpCode());
			List allRequests= requestsApprovalManager.getObjectsByParameter(EmpReqTypeAcc.class , "emp_id", temp.getLogin_user());
			log.debug("employee requests size " + allRequests.size());
			
			if(allRequests.size()>0)
			for(int j=0;j<allRequests.size() ;j++){
				EmpReqTypeAcc tempEmpReqTypeAcc=(EmpReqTypeAcc)allRequests.get(j);
				List accessLevels= requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(AccessLevels.class, "level_id",tempEmpReqTypeAcc.getGroup_id() , "emp_id", loginUsers, ordered1);
			  log.debug("access levels size " + accessLevels.size());
				if(accessLevels.size()>0&&flag){
				actualRequest.add(temp);
				flag=false;
				
				}
			}
		}
		
		log.debug("-----actualRequest----"+actualRequest.size());
		for (int i = 0; i < actualRequest.size(); i++) {
			LoginUsersRequests s=(LoginUsersRequests) actualRequest.get(i);
			log.debug("-----actualRequest----"+s.getEmpCode());
		}
		
		//List allRequests= requestsApprovalManager.getObjectsByParameter(EmpReqTypeAcc.class, "request_id.id", Long.parseLong(requestType));
		List allRequests = new ArrayList();
//		for (int i = 0; i < actualRequest.size(); i++) {
//			LoginUsersRequests usersRequests = (LoginUsersRequests) actualRequest.get(i);
//			if(!usersRequests.getRequest_id().getId().equals(new Long(10)) && !usersRequests.getRequest_id().getId().equals(new Long(11))){
//				allRequests.add(usersRequests);
//			}
//		}
		model.put("records", actualRequest);
		
		List requests = requestsApprovalManager.getObjects(RequestTypes.class);
		List reList = new ArrayList();
		
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		boolean empRequestTypeException = settings.getEmpRequestTypeException();
		if (empRequestTypeException == true){
			for (int i = 0; i < requests.size(); i++) {
				RequestTypes requestTypes = (RequestTypes) requests.get(i);
				if(!requestTypes.getId().equals(new Long(10)) && !requestTypes.getId().equals(new Long(11))){
					reList.add(requestTypes);
				}
			}
			model.put("requestTypeList",reList);
		} else {
			model.put("requestTypeList",requests);
		}
		
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
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView(getSuccessView()));
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
}
