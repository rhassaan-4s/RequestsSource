package com._4s_.requestsApproval.web.action;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
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
import javax.validation.Valid;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import com._4s_.attendance.web.binders.EmpBasicBinder;
import com._4s_.attendance.web.binders.WorkPeriodMasterBinder;
import com._4s_.common.model.Employee;
import com._4s_.common.model.Settings;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.common.web.binders.DomainObjectBinder;
import com._4s_.common.web.binders.TimestampBinder;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.RequestTypes;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
import com._4s_.requestsApproval.web.validators.ValidateAttendanceSignInOut;
import com._4s_.restServices.json.RequestApproval;

@Controller
@RequestMapping("/attendanceSignInOutForm.html")
public class AttendanceSignInOutForm extends BaseSimpleFormController {

	@Autowired
	private RequestsApprovalManager requestsApprovalManager;
	@Autowired
	private ValidateAttendanceSignInOut validateSignInOut;
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Autowired
	@Qualifier("timestampBinder")
	private TimestampBinder timestampBinder;
	@Autowired
	@Qualifier("requestTypesBinder")
	private DomainObjectBinder requestTypesBinder;
	@Autowired
	@Qualifier("loginUsersBinder")
	private DomainObjectBinder loginUsersBinder;
//	@Autowired
//	@Qualifier("requestTypesBinder")
//	private DomainObjectBinder dateTimeBinder;
	
	SimpleDateFormat finalformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	///////////////////////////////////////////////////////////////////
	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}

	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
	public ValidateAttendanceSignInOut getValidateSignInOut() {
		return validateSignInOut;
	}

	public void setValidateSignInOut(ValidateAttendanceSignInOut validateSignInOut) {
		this.validateSignInOut = validateSignInOut;
	}
	/////////////////////////////////////////////////////////////////

	//	@RequestMapping(method = RequestMethod.GET)  public String initForm(ModelMap model,HttpServletRequest request){
	//	{	
	
	@Override
	public void initBinder(HttpServletRequest request,WebDataBinder binder) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Starting init binder: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		binder.registerCustomEditor(RequestTypes.class, requestTypesBinder);
		binder.registerCustomEditor(LoginUsers.class, loginUsersBinder);
		binder.registerCustomEditor(TimestampBinder.class, timestampBinder);
		super.initBinder(request,binder);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Finished init binder: >>>>>>");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request){
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		String newDate =df.format(new Date());
		log.debug("----newDate--"+newDate);
		MultiCalendarDate mCalDate = new MultiCalendarDate();
		mCalDate.setDateString(newDate);

		Employee emp =(Employee) request.getSession().getAttribute("employee");
		//		log.debug("----emp from session---"+request.getSession().getAttribute("employee"));

		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
		if(loginUsers!=null){
			//			log.debug("-----login.code----"+loginUsers.getEmpCode());
		}
		LoginUsersRequests loginUsersRequests = new LoginUsersRequests();
		String empRequestTypeId=request.getParameter("empRequestTypeId");

		if(empRequestTypeId == null || empRequestTypeId.equals("")){

			//			log.debug("loginUsersRequests------"+loginUsersRequests);
			loginUsersRequests = new LoginUsersRequests();
			if(loginUsers!=null){
				loginUsersRequests.setLogin_user(loginUsers);
				loginUsersRequests.setEmpCode(loginUsers.getEmpCode().getEmpCode());
			}
		}else{
			log.debug("loginUsersRequests------"+loginUsersRequests);
			loginUsersRequests = (LoginUsersRequests) requestsApprovalManager.getObject(LoginUsersRequests.class, new Long(empRequestTypeId));
			log.debug("-------period from--formbacking-"+loginUsersRequests.getPeriod_from());
			log.debug("-------request id from--formbacking-"+loginUsersRequests.getRequest_id().getId());
		}

		log.debug("empRequestTypeId------"+empRequestTypeId);

		model.addAttribute("loginUsersRequests", loginUsersRequests);
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		//	   return loginUsersRequests ;
		return "attendanceSignInOutForm";
	}

	//	@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
	//	{

	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		//		LoginUsersRequests loginUsersRequests=(LoginUsersRequests) command;
		Map model=new HashMap();

		Employee emp =(Employee) request.getSession().getAttribute("employee");
		log.debug("---ref-emp from session---"+request.getSession().getAttribute("employee"));

		log.debug("=====emp.getEmpCode()==="+emp.getEmpCode());

		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);

		String empRequestTypeId=request.getParameter("empRequestTypeId");
		log.debug("empRequestTypeId------"+empRequestTypeId);
		model.put("empRequestTypeId",empRequestTypeId);

		List tempList= new ArrayList();
		List requestTypeList= requestsApprovalManager.getObjects(RequestTypes.class);
		for (int i = 0; i < requestTypeList.size(); i++) {
			RequestTypes temp = (RequestTypes) requestTypeList.get(i);
			log.debug("temp.id-----= "+temp.getId());
			if(temp.getId().equals(new Long(10)) || temp.getId().equals(new Long(11))){
				tempList.add(temp);
			}
		}
		log.debug("tempList.size-----= "+tempList.size());
		model.put("requestTypeList", tempList);
		model.put("employeeCode", emp.getEmpCode());
		if(loginUsers!=null){
			model.put("employeeName", loginUsers.getName());
			log.debug("====loginUsers.getName()==="+loginUsers.getName());
		}


		String done=request.getParameter("done");
		model.put("done", done);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(HttpServletRequest request,
			@Valid @ModelAttribute("loginUsersRequests") LoginUsersRequests command,
			BindingResult result, SessionStatus status,Model model) {

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		LoginUsersRequests loginUsersRequests=command;
		Employee emp =(Employee) request.getSession().getAttribute("employee");
		log.debug("---ref-emp from session---"+request.getSession().getAttribute("employee"));

		log.debug("=====emp.getEmpCode()==="+emp.getEmpCode());

		LoginUsers loginUsers=(LoginUsers) requestsApprovalManager.getObjectByParameter(LoginUsers.class, "empCode", emp);
		loginUsersRequests.setLogin_user(loginUsers);

		String longitude = (String)request.getParameter("longitude");
		String latitude =  (String)request.getParameter("latitude");
		String accuracy =  (String)request.getParameter("accuracy");
		log.debug("location " + longitude + " , " + latitude + " , " + accuracy);
		String address = "";


		if (result.hasErrors()) {
			log.debug("binding result error");
			List<ObjectError> err = result.getAllErrors();
			Iterator errIterator = err.iterator();
			while (errIterator.hasNext()) {
				log.debug(((ObjectError)errIterator.next()).getDefaultMessage());
			}
			String url="attendanceSignInOutForm.html?done=false";
//			RedirectView rView = new RedirectView();
//			log.debug(result.getFieldValue("error"));
//			log.debug(result.getAllErrors());
//			log.debug(result.getFieldErrorCount());
//			rView.setUrl(url);
//			return rView;
			model.addAttribute("done",false);
			return "attendanceSignInOutForm";
		} else {
			validateSignInOut.validate(loginUsersRequests, result);
			if (result.hasErrors()) {
				log.debug("binding result error");
				String url="attendanceSignInOutForm.html?done=false";
				log.debug(result.getFieldErrors());
				log.debug(result.getAllErrors());
				Iterator<ObjectError> itr = result.getAllErrors().iterator();
				while(itr.hasNext()) {
					ObjectError error = itr.next();
					log.debug("error--objectName-- " + error.getObjectName() + "--code--"+error.getCode()+"--arguments--"+error.getArguments());
				}
				log.debug(result.getFieldErrorCount());
//				RedirectView rView = new RedirectView();
//				rView.setUrl(url);
//				log.debug("attribute map" + rView.getAttributesMap());
//				model.addAttribute("result",result);
//				model.addAttribute("status",status);
//				return rView;
				model.addAttribute("done",false);
				return "attendanceSignInOutForm";
			} else {
				if (accuracy!=null && !accuracy.isEmpty()) {
					//					if (settings.getLocationAccuracy()< Integer.parseInt(accuracy)) {
					//						result.reject("requestsApproval.errors.notAccurateLocation");
					//					} 
				} else {
					result.reject("requestsApproval.errors.locationIsNotSet");
				}

				if (longitude == null || latitude == null || Double.parseDouble(longitude)==0 || Double.parseDouble(latitude)==0) {
					result.reject("requestsApproval.errors.locationIsNotSet");
				}
			}
		}



		log.debug("login user " + loginUsersRequests.getLogin_user());
		loginUsersRequests.setInputType(new Integer(0));//request to sign in

		Calendar c = Calendar.getInstance();
		//			c.setTimeZone(TimeZone.getTimeZone("EST"));
		Date now = c.getTime();
		log.debug("Time now " + now);
		loginUsersRequests.setPeriod_from(now);
		loginUsersRequests.setRequest_date(Timestamp.valueOf(finalformat.format(c.getTime())));

		//			log.debug("----loginUsersRequests.getId()-onsubmit-----"+loginUsersRequests.getId()+"-----loginUsersRequests---"+loginUsersRequests.getLogin_user().getEmpCode());

		//			Map model=new HashMap();
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		//			String longitude = (String)request.getParameter("longitude");
		//			String latitude =  (String)request.getParameter("latitude");
		//			String accuracy =  (String)request.getParameter("accuracy");
		//			String address = "";
		log.debug("accuracy " + accuracy);
		if (accuracy!=null && !accuracy.isEmpty()) {
			Long acc = Math.round(Double.parseDouble(accuracy));
			if (settings.getLocationAccuracy()>= acc.intValue()) {
				try {
					address = requestsApprovalManager.getAddressByGpsCoordinates(longitude, latitude);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				address = "Address is not accurate to be saved";
			}
		}

		log.debug(longitude + "-" + latitude + "-" + address);

		if (accuracy!=null && !accuracy.isEmpty()) {
			double distance = requestsApprovalManager.distance(new Double(latitude),new Double(longitude),new Double(settings.getCompanyLat()),new Double(settings.getCompanyLong()));
			if (distance>settings.getDistAllowedFromCompany()) {
				loginUsersRequests.setIsInsideCompany(false);
			} else {
				loginUsersRequests.setIsInsideCompany(true);
			}
		} else {
			loginUsersRequests.setIsInsideCompany(true);
		}

		String reqId="";
		if(loginUsersRequests.getRequest_id()!=null && !loginUsersRequests.getRequest_id().equals("")){
			log.debug("entered--1-----reqid=-"+loginUsersRequests.getRequest_id().getId());
			reqId=loginUsersRequests.getRequest_id().getId().toString();
		}
		// request number
		if (loginUsersRequests.getId() == null){
			String requestNumber="";
			requestNumber=requestsApprovalManager.CreateRequestNumber();
			loginUsersRequests.setRequestNumber(requestNumber);
		}

		log.debug("loginUsersRequests.getEmpCode() entered--------"+loginUsersRequests.getEmpCode());


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
		if(loginUsersRequests.getFrom_date()==null|| loginUsersRequests.getFrom_date().equals("")){
			loginUsersRequests.setFrom_date(loginUsersRequests.getPeriod_from());
		}

		log.debug("---requestType from jsp---"+request.getParameter("requestType"));
		log.debug("---requestType from command object---"+loginUsersRequests.getRequest_id());
		//		loginUsersRequests.setApproved();


		//				requestsApprovalManager.saveObject(loginUsersRequests);

		if (latitude!= null && !latitude.isEmpty()) {
			loginUsersRequests.setLatitude(Double.parseDouble(latitude));
			loginUsersRequests.setLongitude(Double.parseDouble(longitude));
			loginUsersRequests.setLocationAddress(address);
		}
		log.debug("will save request");
		try {
			requestsApprovalManager.saveObject(loginUsersRequests);
		} catch (Exception e) {
			log.debug("exception while saving");
			e.printStackTrace();
		}
		log.debug("after saving request with id " + loginUsersRequests.getId());
		RequestApproval approvals = new RequestApproval();
		approvals.setApprove("1");
		approvals.setNotes("Web Sign in/out Automatic Approval");
		approvals.setRequestId(loginUsersRequests.getId()+"");
		requestsApprovalManager.automaticApprovalsAccessLevels(approvals, loginUsersRequests);
		String url="attendanceSignInOutForm.html?done=true&requestId="+reqId;

		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<  End onSubmit : <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		//				return new ModelAndView(new RedirectView(url));
		//			return "attendanceSignInOutForm";
//		RedirectView rView = new RedirectView();
//		rView.setUrl(url);
//		return rView;
		
		model.addAttribute("done",true);
		model.addAttribute("requestId", reqId);
		return "attendanceSignInOutForm";
	}
	//	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	//	{
	//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	//		LoginUsersRequests loginUsersRequests=(LoginUsersRequests)command;
	//	
	//		Settings settings = (Settings)request.getSession().getAttribute("settings");
	//		
	//		if(errors.getErrorCount()==0)
	//		{	
	//			if(loginUsersRequests==null || loginUsersRequests.equals("")){
	//				errors.rejectValue("empCode", "commons.errors.requiredFields");
	//			}
	//			else {
	//				Calendar c = Calendar.getInstance();
	//				c.setTimeZone(TimeZone.getTimeZone("EST"));
	//				Date now = c.getTime();
	//				if(loginUsersRequests.getRequest_id()==null || loginUsersRequests.getRequest_id().equals(""))
	//				{
	//					errors.rejectValue("request_id", "commons.errors.requiredFields");
	//				} else {
	//					Long attendanceType = null;
	//					Employee emp = (Employee)requestsApprovalManager.getObjectByParameter(Employee.class, "empCode", loginUsersRequests.getEmpCode());
	////					if (loginUsersRequests.getRequest_id().getId().equals(new Long(10))) {
	////						attendanceType  = new Long(1);
	////					} else if (loginUsersRequests.getRequest_id().getId().equals(new Long(11))) {
	////						attendanceType  = new Long(2);
	////					}
	//					attendanceType = loginUsersRequests.getRequest_id().getId();
	//					
	//					RestStatus status = requestsApprovalManager.validateSignInOut(attendanceType, now, emp);
	//					if (status.getStatus().equals("False")) {
	//						String statusMsg = status.getMessage();
	//						String i18nkey = "";
	//						if (statusMsg.equals("User signed In Before and didn't signed out")) {
	//							i18nkey = "requests.errors.signedInBefore";
	//						} else if (statusMsg.equals("User didn't sign In yet")) {
	//							i18nkey = "requests.errors.didnotSignInYet";
	//						} else if (statusMsg.equals("Sign out date is before sign in date")) {
	//							i18nkey = "requests.errors.signoutBeforeSignin";
	//						} else if (statusMsg.equals("Sign in on a full errand day is not allowed")) {
	//							i18nkey = "requests.errors.signinOnFullErrandDay";
	//						} else if (statusMsg.contains("Finish Started Request First")) {
	//							i18nkey = "requests.errors.finishStartedRequestFirst";
	//						}  else {
	//							log.debug(statusMsg);
	//							i18nkey = "requests.errors.unknownError";
	//						}
	//						errors.reject(i18nkey);
	//					}
	//				}
	//				
	//			}
	//			
	//		}
	//
	//		String longitude = (String)request.getParameter("longitude");
	//		String latitude =  (String)request.getParameter("latitude");
	//		String accuracy =  (String)request.getParameter("accuracy");
	//		log.debug("location " + longitude + " , " + latitude + " , " + accuracy);
	//		String address = "";
	//		
	//		if(errors.hasErrors()==false) {
	//			if (accuracy!=null && !accuracy.isEmpty()) {
	////				if (settings.getLocationAccuracy()< Integer.parseInt(accuracy)) {
	////					errors.reject("requestsApproval.errors.notAccurateLocation");
	////				} 
	//			} else {
	//				errors.reject("requestsApproval.errors.locationIsNotSet");
	//			}
	//		}
	//		if(errors.hasErrors()==false) {
	//			if (longitude == null || latitude == null || Double.parseDouble(longitude)==0 || Double.parseDouble(latitude)==0) {
	//				errors.reject("requestsApproval.errors.locationIsNotSet");
	//			}
	//		}
	//		
	//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	//	}

	//	public ModelAndView onSubmit(HttpServletRequest request,
	//			HttpServletResponse response, Object command, BindException errors)throws Exception 
	//	{



}
