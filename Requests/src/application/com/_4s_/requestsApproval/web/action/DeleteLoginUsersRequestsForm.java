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

import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.service.RequestsApprovalManager;


public class DeleteLoginUsersRequestsForm extends BaseSimpleFormController{
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
		
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		log.debug("---ref-emp from session---"+request.getSession().getAttribute("employee"));
		
		model.put("requestTypeList", requestsApprovalManager.getObjects(RequestTypes.class));
//		String emp_code = request.getParameter("empCode");
//		log.debug("----emp_code---"+emp_code);
		model.put("empCode", emp.getEmpCode());
		
		String requestNumber = request.getParameter("requestNumber");
		if(requestNumber!=null && !requestNumber.equals("")){
			List loginUserReqs=(List) requestsApprovalManager.getObjectsByParameter(LoginUsersRequests.class, "requestNumber", requestNumber);
			model.put("loginUserReqs", loginUserReqs);
		}
		String requestType= request.getParameter("requestType");
		log.debug("--requestType--"+requestType);
		model.put("requestType", requestType);
		
		if(requestType!=null && !requestType.equals("")){
			List loginUserReqs=(List) requestsApprovalManager.getObjectsByParameter(LoginUsersRequests.class, "request_id.id", new Long(requestType));
			log.debug("--loginUserReqs--"+loginUserReqs.size());
			model.put("loginUserReqs", loginUserReqs);			
		}
		if(emp.getEmpCode()!=null && !emp.getEmpCode().equals("")){
			log.debug("---xxxxxxxCodeName--");
			LoginUsers loginUser=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());

			log.debug("--loginUser.getName()--"+loginUser.getName());
			List loginUserReqs=(List) requestsApprovalManager.getObjectsByParameter(LoginUsersRequests.class, "login_user", loginUser);

			
			if(requestType!=null && !requestType.equals("")){
				List order =new ArrayList();
				order.add("empCode");
				loginUserReqs=(List) requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(LoginUsersRequests.class, "request_id.id", new Long(requestType), "empCode",emp.getEmpCode(),order);
				log.debug("--loginUserReqs--"+loginUserReqs.size());
						
			}
			model.put("loginUserReqs", loginUserReqs);
		}

		String dateFrom = request.getParameter("dateFrom");
		log.debug("--dateFrom--"+dateFrom);
		model.put("dateFrom", dateFrom);
		String dateTo = request.getParameter("dateTo");
		log.debug("--dateTo--"+dateTo);
		model.put("dateTo", dateTo);
		log.debug("---xxxxxxxDatePeriod--");		
		
		MultiCalendarDate mCalDate = new MultiCalendarDate();
		
		Date fromDate = null;
		Date toDate = null;
		
		if (dateFrom != null && dateTo != null ){
			log.debug("-00000000000---");
			if (!dateFrom.equals("") && !dateTo.equals("") ) {
					
				log.debug(">>>>>>>>>>>Valid date format");
				log.debug(">>>>>>>>>>>>>fromDateString "+ dateFrom);
				//fromDateStr = fromDateString +" 00:00";
				mCalDate.setDateTimeString(dateFrom,new Boolean(true));
				fromDate = mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
				log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
				//toDateStr = toDateString+ " 23:59";
				mCalDate.setDateTimeString(dateTo,new Boolean(false));
				toDate= mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>toDate "+ toDate);
	
				List loginUserReqs=(List) requestsApprovalManager.getRequestsByDatePeriod(fromDate, toDate);
				log.debug("--dateList.size--"+loginUserReqs.size());
				
				if(requestType!=null && !requestType.equals("")){
					 loginUserReqs=(List) requestsApprovalManager.getRequestsByDatePeriodAndRequestType(fromDate, toDate, new Long(requestType));
					log.debug("--2dateList.size--"+loginUserReqs.size());

					if(emp.getEmpCode()!=null && !emp.getEmpCode().equals("")){
						loginUserReqs=(List) requestsApprovalManager.getRequestsByDatePeriodAndRequestTypeAndEmpCode(fromDate, toDate, new Long(requestType), emp.getEmpCode());
						log.debug("--3dateList.size--"+loginUserReqs.size());
					}
				}
				if(emp.getEmpCode()!=null && !emp.getEmpCode().equals("")){
					loginUserReqs=(List) requestsApprovalManager.getRequestsByDatePeriodAndEmpCode(fromDate, toDate, emp.getEmpCode());
					log.debug("--4dateList.size--"+loginUserReqs.size());
				}
				model.put("loginUserReqs", loginUserReqs);
			}
		}
		
		if( requestNumber!=null && dateFrom!=null && dateTo!=null && requestType!=null){
			if(( requestNumber.equals(""))&&(dateFrom.equals(""))&&(dateTo.equals(""))&&(requestType.equals(""))){
				List allRequests=requestsApprovalManager.getObjectsByParameter(LoginUsersRequests.class,"empCode",emp.getEmpCode());
				model.put("loginUserReqs", allRequests);
			}
		}
		String requestId=request.getParameter("requestId");
		log.debug("----requestId----"+requestId);
		if(requestId!=null && !requestId.equals("")){
			loginUsersRequests=(LoginUsersRequests) requestsApprovalManager.getObjectByParameter(LoginUsersRequests.class, "id", Long.parseLong(requestId));
			if(loginUsersRequests!=null && !loginUsersRequests.equals("")){
				if(loginUsersRequests.getApproved()==0){
		//			log.debug("--el sha5s ele el id bta3o da---"+requestsApprovalManager.getObjectByParameter(LoginUsersRequests.class, "id", new Long(requestId)));
					requestsApprovalManager.removeObject(loginUsersRequests);
				}
				else if (loginUsersRequests.getApproved()==1){
					errors.reject("requestsApproval.errors.requestToBeDeletedIsAlreadyApproved");
				}
			}
		}
		
		String done=request.getParameter("done");
		if(done!=null){
			model.put("done", done);
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
		
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		boolean checkPostedRequests = settings.getCheckPostedRequests();
		
		String[] requestId=request.getParameterValues("requestId");
		log.debug("----requestId----"+requestId);
		String done="";
		if(requestId!=null && !requestId.equals("")){
			for(int i=0;i<requestId.length;i++){
				loginUsersRequests=(LoginUsersRequests) requestsApprovalManager.getObjectByParameter(LoginUsersRequests.class, "id", Long.parseLong(requestId[i]));
				if(loginUsersRequests!=null && !loginUsersRequests.equals("")){

					if(
							//Lotus
							(checkPostedRequests == true && (loginUsersRequests.getApproved()==0 || loginUsersRequests.getPosted()==0))
							||
							//Lehaa
							(checkPostedRequests == false && loginUsersRequests.getApproved()==0)){	
						log.debug("----loginUsersRequests.getApproved()----"+loginUsersRequests.getApproved()+" ----loginUsersRequests.getPosted()---"+loginUsersRequests.getPosted());
						requestsApprovalManager.removeObject(loginUsersRequests);
						done="true";
					} else{
						done="false";
					}
				}
		
			}
		}
		String url="deleteLoginUsersRequestsForm.html?done="+done;
		
		return new ModelAndView(new RedirectView(url));
	}
}
