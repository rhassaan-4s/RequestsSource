package com._4s_.requestsApproval.web.action;

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
import com._4s_.requestsApproval.model.EmpReqApproval;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.RequestTypes;

import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.common.model.Employee;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;


public class MasterDetailsReport extends BaseSimpleFormController{
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

		List tempneededRequestTypes = new ArrayList();
		LoginUsersRequests loginUsersRequests=(LoginUsersRequests) command;
		
		Map model=new HashMap();		
//		
//		String emp_code = request.getParameter("empCode");
//		log.debug("----emp_code---"+emp_code);
//		model.put("employeeCode", emp_code);
//		
//		String requestType= request.getParameter("requestType");
//		log.debug("--requestType--"+requestType);
//		model.put("requestType", requestType);
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
		
//		String statusId=request.getParameter("statusId");
//		String codeFrom=request.getParameter("codeFrom");
//		String codeTo=request.getParameter("codeTo");
//		if(codeFrom!=null && codeTo!=null && !codeFrom.equals("")&& !codeTo.equals("")){
//			List loginUserReqs=(List) requestsApprovalManager.getEmployeesByCodes(codeFrom, codeTo);
//			log.debug("---codesList---"+loginUserReqs.size());
//			tempneededRequestTypes=loginUserReqs;
//			//model.put("records", loginUserReqs);
//			for (int i = 0; i < loginUserReqs.size(); i++) {
//				LoginUsersRequests s=(LoginUsersRequests) loginUserReqs.get(i);
//				log.debug("---code code---"+s.getEmpCode());
//			}
//		}
		
//		String dateFrom = request.getParameter("dateFrom");
//		log.debug("--dateFrom--"+dateFrom);
//		model.put("dateFrom", dateFrom);
//		String dateTo = request.getParameter("dateTo");
//		log.debug("--dateTo--"+dateTo);
//		model.put("dateTo", dateTo);
//		log.debug("---xxxxxxxDatePeriod--");		
//		
//		MultiCalendarDate mCalDate = new MultiCalendarDate();
//
//		if (dateFrom != null && dateTo != null){
//			if (!dateFrom.equals("") && !dateTo.equals("") ) {
//				
//				Date fromDate = null;
//				Date toDate = null;
//				log.debug(">>>>>>>>>>>>> if ");
//				log.debug(">>>>>>>>>>>Valid date format");
//				log.debug(">>>>>>>>>>>>>fromDateString "+ dateFrom);
//				//fromDateStr = fromDateString +" 00:00";
//				mCalDate.setDateTimeString(dateFrom,new Boolean(true));
//				fromDate = mCalDate.getDate();
//				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
//				log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
//				//toDateStr = toDateString+ " 23:59";
//				mCalDate.setDateTimeString(dateTo,new Boolean(false));
//				toDate= mCalDate.getDate();
//				log.debug(">>>>>>>>>>>>>toDate "+ toDate);
//				List loginUserReqs=(List) requestsApprovalManager.getRequestsByDatePeriodAndRequestType(fromDate, toDate,Long.parseLong(requestType));
//				log.debug("--dateList.size--"+loginUserReqs.size());
//				//model.put("loginUserReqs", loginUserReqs);
//				tempneededRequestTypes=loginUserReqs;
//			}
//		}
		// here
//		if(emp_code!=null  && dateFrom!=null && dateTo!=null && codeFrom!=null && codeTo!=null && statusId!=null){
//			if((emp_code.equals(""))&&(dateFrom.equals(""))&&(dateTo.equals(""))&&(codeTo.equals(""))&&(codeFrom.equals(""))&&(statusId.equals(""))){
				List allRequests=requestsApprovalManager.getObjects(LoginUsersRequests.class);
				//model.put("loginUserReqs", allRequests);
				tempneededRequestTypes=allRequests;
//			}
//		}
		
//		if(emp_code!=null  && dateFrom!=null && dateTo!=null && statusId!=null){
//			if((!emp_code.equals(""))&&(!dateFrom.equals(""))&&(!dateTo.equals(""))&&(!statusId.equals(""))){
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
//				List allRequests1= requestsApprovalManager.getRequestsByDatePeriodAndRequestTypeAndEmpCode(fromDate, toDate, new Long(requestType), emp_code);
//
//				//model.put("loginUserReqs", allRequests);
//				tempneededRequestTypes=allRequests1;
//			}
//		}
		
//		List list=new ArrayList();
//		list.add("empCode");
//		if(statusId!=null && !statusId.equals("")){
//			List requests=requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(LoginUsersRequests.class, "approved", new Long(statusId),"request_id.id",new Long(requestType),list	);
//			//model.put("loginUserReqs", allRequests);
//			tempneededRequestTypes=requests;
//		}
			
		
		List actualRequest= new ArrayList();
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
		List<String> ordered= new ArrayList();
		ordered.add("order");
		List<String> ordered1= new ArrayList();
		ordered1.add("emp_id");
		List approval=new ArrayList();
		EmpReqApproval empReqApproval=null;
		for(int i=0;i<tempneededRequestTypes.size() ;i++){
			boolean flag=true;
			LoginUsersRequests temp=(LoginUsersRequests)tempneededRequestTypes.get(i);
			log.debug("-----temp- code---"+temp.getEmpCode());
			log.debug("-----req ID---"+temp.getId());
			List requests= requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(EmpReqTypeAcc.class, "req_id", temp.getRequest_id() , "emp_id", temp.getLogin_user(), ordered);
			
			log.debug("------allRequests.size()--------"+requests.size());
			
			if(requests.size()>0){
				log.debug("------allRequests.size()>0---");
				for(int j=0;j<requests.size() ;j++){
					EmpReqTypeAcc tempEmpReqTypeAcc=(EmpReqTypeAcc)requests.get(j);
					List accessLevels= requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(AccessLevels.class, "level_id",tempEmpReqTypeAcc.getGroup_id() , "emp_id", loginUsers, ordered1);
					
					if(accessLevels.size()>0&&flag){
						log.debug("-------my order---"+tempEmpReqTypeAcc.getOrder());
						actualRequest.add(temp);
						flag=false;
					}		
					
				}
			}
		}
		
		log.debug("-----actualRequest---size-"+actualRequest.size());
		List lists=new ArrayList();
		for (int i = 0; i < actualRequest.size(); i++) {
			LoginUsersRequests s=(LoginUsersRequests) actualRequest.get(i);
			log.debug("-----reqNumber----"+s.getRequestNumber()+"-----empcode----"+s.getEmpCode());
			List reqStatusList=requestsApprovalManager.getRequestStatus(s.getId());
			if(reqStatusList.size()>0){
				lists.add(reqStatusList);
			}
			for (int j = 0; j < reqStatusList.size(); j++) {
				EmpReqApproval reqApproval=(EmpReqApproval)reqStatusList.get(j);
				log.debug("-----ele wafe2---"+reqApproval.getUser_id().getName()+"-----reqStatus----"+reqApproval.getApproval());
			}
			//log.debug("-----reqStatus----"+requestsApprovalManager.getRequestStatus(s.getId()));
			log.debug("-----actualRequest----"+s.getEmpCode());
		}
		
		
		model.put("statusList", lists);
		
		//List allRequests= requestsApprovalManager.getObjectsByParameter(EmpReqTypeAcc.class, "request_id.id", Long.parseLong(requestType));
				
		model.put("loginUserReqs", actualRequest);
		
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
