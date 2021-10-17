package com._4s_.requestsApproval.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.AccessLevels;
import com._4s_.requestsApproval.model.EmpReqApproval;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.service.RequestsApprovalManager;

public class RequestsApprovalForm extends BaseSimpleFormController {
	RequestsApprovalManager requestsApprovalManager;

	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		AccessLevels accessLevel = new AccessLevels();

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		return accessLevel;
	}

	// **************************************** referenceData
	// ***********************************************\\
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws ServletException {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Map model = new HashMap();
		LoginUsers empInfo = new LoginUsers();
		String reqId = request.getParameter("reqId");
		String requestType = request.getParameter("requestType");
		//String canCancel = null;
		Long idToBeCanceled = null;
		String cancelApproval=request.getParameter("cancelApproval");
		if (reqId != null) {
			LoginUsersRequests requestInfo = (LoginUsersRequests) requestsApprovalManager
					.getObject(LoginUsersRequests.class, new Long(reqId));
			log.debug("request info " + requestInfo);
			log.debug("model " + model);
			log.debug(requestInfo.getPosted());
			model.put("posted",requestInfo.getPosted());
			List<String> orderfieldList = new ArrayList();
			orderfieldList.add(new String("order"));

			List empReqAcc = requestsApprovalManager
					.getObjectsByTwoParametersOrderedByFieldList(
							EmpReqTypeAcc.class, "req_id", requestInfo
									.getRequest_id(), "emp_id", requestInfo
									.getLogin_user(), orderfieldList);
			EmpReqApproval empReqApproval = null;

			Employee emp =(Employee) request.getSession().getAttribute("employee");
			LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
			List accLevels = requestsApprovalManager.getObjectsByParameter(AccessLevels.class, "emp_id", loginUsers);
			model.put("emp", loginUsers.getName());
			Map approvalRequest = new HashMap();
			List<String> ordered1= new ArrayList();
			ordered1.add("emp_id");

			List approvalList = new ArrayList();
			orderfieldList.clear();
			orderfieldList.add(new String("level_id"));

			String showbSubmit = "1";
			String last = "0";

			log.debug("---- size of list empReqAcc---"+empReqAcc.size());
			if(empReqAcc.size()>0){
				log.debug("---- size of list---"+empReqAcc.size());
			for (int i = 0; i < empReqAcc.size(); i++) {
				log.debug("---- i fo2---"+i);
				try {
					
					/**
					 * Detect if access level is the last order
					 **/
					
					if (i == (empReqAcc.size() - 1)){
						last = "1";
					}
					else{
						last="0";
					}
					/*************************************/

					EmpReqTypeAcc temp = new EmpReqTypeAcc();
					temp = (EmpReqTypeAcc) empReqAcc.get(i);
					log.debug("-----temp id---"+temp.getId());
					log.debug("-----temp group--"+temp.getGroup_id().getId());
					
					List<String> orderfieldListApproval = new ArrayList();
					orderfieldListApproval.add(new String("level_id"));
					List apps = requestsApprovalManager.getObjectsByThreeParametersThirdNotNullOrderedByFieldList(EmpReqApproval.class,"level_id", temp, "req_id", requestInfo,"approval_date",orderfieldListApproval);
					log.debug("approvals " + apps.size());
					if (apps.size()>0) {
						empReqApproval = (EmpReqApproval)apps.get(0);

						log.debug("------empreqapp-----"+empReqApproval.getId());

						log.debug("------id ele wafe2-----"+empReqApproval.getUser_id().getId());

						log.debug("------code ele wafe2-----"+empReqApproval.getUser_id().getEmpCode());
						model.put("approvedBy", empReqApproval.getUser_id().getEmpCode());
						log.debug("------code ele da5el-----"+emp.getEmpCode());
						model.put("operator", emp.getEmpCode());
						log.debug("---- i t7t---"+i);
						log.debug("---i==empReqAcc.size()-1-------"+(i==empReqAcc.size()-1));
						if((i+1)<empReqAcc.size() || (i==empReqAcc.size()-1)){
							try{
								model.put("lastOne", "false");
								EmpReqTypeAcc nextTemp=(EmpReqTypeAcc)empReqAcc.get(i+1);
								log.debug("-----nextTemp id---"+nextTemp.getId());
								log.debug("-----nextTemp group--"+nextTemp.getGroup_id().getId());
								EmpReqApproval empReqApprovalNext= new EmpReqApproval();
								empReqApprovalNext = (EmpReqApproval) requestsApprovalManager.getObjectByTwoObjects(EmpReqApproval.class,"level_id", nextTemp, "req_id", requestInfo);
								log.debug("------empReqApprovalNext.getId()!null nor ''----");
								log.debug("------empReqApprovalNext-----"+empReqApprovalNext.getId());
								model.put("lastOne", "false");
							}
							catch (Exception e) {
								model.put("lastOne", "false");
								if(empReqApproval.getUser_id().getEmpCode().equals(emp.getEmpCode())){
									log.debug("---empReqApproval.getReq_id().getPosted()---"+empReqApproval.getReq_id().getPosted());
									if(empReqApproval.getReq_id().getPosted()==0){
										idToBeCanceled=empReqApproval.getId();
										log.debug("------idToBeCanceled-----"+idToBeCanceled);
										approvalRequest.put("idToBeCanceled", idToBeCanceled);
										log.debug("----catch----no empReqApprovalNext-----");

										log.debug("--------cancelApproval-before---"+cancelApproval);
										if(cancelApproval!=null && !cancelApproval.equals("")){
											log.debug("--------cancelApproval----"+cancelApproval);
											requestsApprovalManager.removeObject(EmpReqApproval.class, idToBeCanceled);
											log.debug("----true---");
											requestInfo.setApproved(new Long(0));
											log.debug("--requestInfo.getApproved()---"+requestInfo.getApproved());
											log.debug("----true---after--");
											String done=request.getParameter("done");
											if(done!=null){
												model.put("done", done);
											}
										}
									}
									else{
										model.put("lastOne", "false");
									}
								}else{
									model.put("lastOne", "false");
								}
							}

						}
						else{
							model.put("lastOne", "false");
						}

						approvalRequest = new HashMap();

						approvalRequest.put("title", temp.getGroup_id().getTitle());
						log.debug("-----title of group-----"+temp.getGroup_id().getTitle());
						approvalRequest.put("id", temp.getId());
						approvalRequest.put("status", empReqApproval.getApproval());
						log.debug("-----status-empReqApproval.getApproval()--"+empReqApproval.getApproval());
						approvalRequest.put("user", empReqApproval.getUser_id().getName());
						log.debug("-----user-----"+empReqApproval.getUser_id().getName());
						approvalRequest.put("note", empReqApproval.getNote());
						approvalList.add(approvalRequest);

						/**
						 * Detect if access level is finished to hide submit button
						 ***/
						if ((i == (empReqAcc.size() - 1))){
							log.debug("-------last one---- & not posted");
							model.put("lastOne", "true");
							showbSubmit = "0";
						}else{
							model.put("lastOne", "false");
						}
						/**
						 * Detect if last access level is rejected to hide submit
						 * button
						 **/
						if (empReqApproval.getApproval() == 0) {
							log.debug("---empReqApproval.getApproval() == 0----cancelApproval---"+cancelApproval);
							if(requestInfo.getApproved()==0 && (cancelApproval==null || cancelApproval.equals(""))){
								log.debug("------pproved=0-ddd--");
								requestInfo.setApproved(new Long(99));
							}
							requestsApprovalManager.saveObject(requestInfo);
							//showbSubmit = "0";
							break;

						}else if(i == (empReqAcc.size() - 1)){
							log.debug("---last one ---cancelApproval-"+cancelApproval);
							log.debug("--------requestInfo.getApproved()------"+requestInfo.getApproved());
							if(requestInfo.getApproved()==0 && (cancelApproval==null || cancelApproval.equals(""))){
								log.debug("------pproved=0---");
								requestInfo.setApproved(new Long(1));
							}
							requestsApprovalManager.saveObject(requestInfo);

						}
					} else {
						// no one approved till now
						//should check the logged in user if he is the one 
						//supposed to approve a radio button should be displayed
						
						EmpReqTypeAcc empAcc = null;
						List accLev = null;
						log.debug("empReqAcc for the requestor size " + empReqAcc.size());
						if(empReqAcc.size()>0) {
							empAcc =  (EmpReqTypeAcc)empReqAcc.get(i);
							log.debug("empAcc " + empAcc.getId());
							accLev = empAcc.getGroup_id().getAccessLevel();//levels of the current employee request to be approved
							log.debug("requestor group id " + empAcc.getGroup_id().getId());
						}
						Iterator iter = accLevels.iterator();//manager levels
						while(iter.hasNext()) {
							AccessLevels managerAccLev = (AccessLevels)iter.next();
							log.debug("manager acc lev " + managerAccLev.getId());
							if (accLev!=null && accLev.contains(managerAccLev)) {
								approvalRequest.put("title", managerAccLev.getLevel_id().getTitle());
								log.debug("-----title of group-----"+managerAccLev.getLevel_id().getTitle());
								approvalRequest.put("id", empAcc.getId());
//								approvalRequest.put("status", empReqApproval.getApproval());
//								log.debug("-----status-empReqApproval.getApproval()--"+empReqApproval.getApproval());
								approvalRequest.put("user", loginUsers.getName());
								log.debug("-----user-----"+loginUsers.getName());
//								approvalRequest.put("note", empReqApproval.getNote());
								approvalList.add(approvalRequest);
							}
						}
					}
				} catch (Exception e) {
					log.debug("execption " + e);
					e.printStackTrace();
					
					/**
					 * Data of  user which will approve
					 * button
					 **/
					model.put("lastOne", "false");
					EmpReqTypeAcc temp = new EmpReqTypeAcc();

					temp = (EmpReqTypeAcc) empReqAcc.get(i);
					log.debug("------catch temp ---"+temp.getGroup_id());
					log.debug("------catch title---"+temp.getGroup_id().getTitle());
					approvalRequest = new HashMap();

					approvalRequest.put("title", temp.getGroup_id().getTitle());
					
					approvalRequest.put("id", temp.getId());
					log.debug("------catch temp id---"+temp.getId());
					

					List accessLevels= requestsApprovalManager.getObjectsByTwoParametersOrderedByFieldList(AccessLevels.class, "level_id",temp.getGroup_id() , "emp_id", loginUsers, ordered1);
					log.debug("access levels " + accessLevels.size());
					if(accessLevels.size()>0){
						approvalRequest.put("user", loginUsers.getName());
						log.debug("------catch if user---"+loginUsers.getName());
						approvalRequest.put("status", "2");
						model.put("lastOne", "false");
					}
					else{
						approvalRequest.put("user", loginUsers.getName());
						log.debug("------catch else user---"+loginUsers.getName());
						approvalRequest.put("status", "3");
						showbSubmit = "0";
						model.put("lastOne", "false");
					}
					approvalList.add(approvalRequest);

					break;

				}
			  }
			}
			else{
				showbSubmit = "0";
				model.put("lastOne", "false");
			}
			
			// if(requestInfo!=null)
			// empInfo=(LoginUsers)requestsApprovalManager.getObject(LoginUsers.class,new
			// Long(requestInfo.get));

			model.put("approvalList", approvalList);
			model.put("requestInfo", requestInfo);
			model.put("showbSubmit", showbSubmit);
			model.put("data", "1");
			model.put("last", last);
			model.put("requestType", requestType);
			
			
//			Long vacId = requestInfo.getVacation().getId();
			requestInfo = (LoginUsersRequests) requestsApprovalManager
					.getObject(LoginUsersRequests.class, new Long(reqId));
			log.debug("request+info " + requestInfo.getId());
			if (requestInfo.getVacation()!=null){
				String vacId =  requestInfo.getVacation().getVacation();
				
				log.debug("vac id " + vacId);
			
				Date from_date = requestInfo.getFrom_date();
				log.debug("from_date " + from_date);
				log.debug("emp code " + requestInfo.getEmpCode());
				if (from_date!=null){
					Long vacLimit = requestsApprovalManager.getVacationLimit(requestInfo.getEmpCode(), vacId, from_date);
					Long vacConsumed = requestsApprovalManager.getEmpVacation(requestInfo.getEmpCode(), vacId, from_date);
					Long vacCredit = vacLimit - vacConsumed;

					model.put("vacLimit", vacLimit);
					model.put("vacConsumed", vacConsumed);
					model.put("vacCredit", vacCredit);
				}

			}
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			
		} else
			model.put("data", "0");
		
		String errand=request.getParameter("errand");
		model.put("errand", errand);
		
		return model;

	}

	// **************************************** onBind
	// ***********************************************\\
	protected void onBind(HttpServletRequest request, Object command,
			BindException errors) throws Exception {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	// **************************************** onBindAndValidate
	// ***********************************************\\
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		
		    log.debug(">> >> >> >> > >> > >> >> >>>  >> >> > > >> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		    String reqId = request.getParameter("reqId");		    
		    LoginUsersRequests requestInfo = (LoginUsersRequests) requestsApprovalManager
					.getObject(LoginUsersRequests.class, new Long(reqId));
		    
		    
		    Date from_date = requestInfo.getFrom_date();
			log.debug("from_date " + from_date);
			log.debug("emp code " + requestInfo.getEmpCode());
			
		if (requestInfo.getVacation() != null) {
			String vacId = requestInfo.getVacation().getVacation();
			Long vacLimit = requestsApprovalManager.getVacationLimit(
					requestInfo.getEmpCode(), vacId,
					from_date);
			Long vacConsumed = requestsApprovalManager.getEmpVacation(
					requestInfo.getEmpCode(), vacId,
					from_date);
			Long vacCredit = vacLimit - vacConsumed;

			if (vacCredit == 0) {
				errors.reject("requestsApproval.errors.zerovacationcredit");
			}
		}
		    
		    log.debug(" >> >> >> >> >> >> >> >> >> >> >> >> >> >> End of onBindAndValidate >> >> >> >> >> >> >> >> >> >> >> >> >> >");
	
	}

	// **************************************** onSubmit
	// ***********************************************\\
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		Employee emp =(Employee) request.getSession().getAttribute("employee");
		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
		
		String reqId = request.getParameter("reqId");
		String status = request.getParameter("status");
//		String empCode = request.getParameter("empCode");
		String accId = request.getParameter("accId");
		String note = request.getParameter("note");
		String last = request.getParameter("last");

		LoginUsersRequests requestOb = new LoginUsersRequests();
		EmpReqTypeAcc empReqTypeAcc = new EmpReqTypeAcc();
//		LoginUsers loginUsers = new LoginUsers();
log.debug("requestOb " + requestOb);
		requestOb = (LoginUsersRequests) requestsApprovalManager.getObject(
				LoginUsersRequests.class, new Long(reqId));
		log.debug("requestOb 2 " + requestOb);
		empReqTypeAcc = (EmpReqTypeAcc) requestsApprovalManager.getObject(
				EmpReqTypeAcc.class, new Long(accId));

		EmpReqApproval empReqApproval = new EmpReqApproval();

		empReqApproval.setApproval(new Integer(status));
		empReqApproval.setReq_id(requestOb);
		empReqApproval.setLevel_id(empReqTypeAcc);
		empReqApproval.setUser_id(loginUsers);
		empReqApproval.setNote(note);
		empReqApproval.setApproval(new Integer(status));

		requestsApprovalManager.saveObject(empReqApproval);
		System.out.println("Status " + status);
		System.out.println("last " + last);
		if(status.equals("0")){
			
			requestOb.setApproved(new Long(99));

			requestsApprovalManager.saveObject(requestOb);
			
		}else if(last.equals("1")){
			
			requestOb.setApproved(new Long(1));
			requestsApprovalManager.saveObject(requestOb);
		}
		

		String requestType = request.getParameter("requestType");
		String errand ="";
		if(requestOb.getVacation()!=null && !requestOb.getVacation().equals("")){
			if(requestOb.getVacation().getVacation().equals("999")){
				errand="true";
			}
		}
		String url="empRequestsReportsForm.html?requestType="+requestType+"&errand="+errand;//"&empCode="+requestOb.getEmpCode()+
		 
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<  End onSubmit : <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		return new ModelAndView(new RedirectView(url));

	}
}
