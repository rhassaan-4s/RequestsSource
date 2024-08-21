package com._4s_.security.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Branch;
import com._4s_.common.service.CommonManager;
import com._4s_.common.web.action.BaseFormControllerInterface;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.common.web.binders.DomainObjectBinder;
import com._4s_.i18n.service.MessageManager;
import com._4s_.security.model.User;
import com._4s_.security.model.UserBranch;
import com._4s_.security.model.UserPrivilege;

@Controller
@RequestMapping("/addAuthority.html")
public class AddAuthority extends BaseSimpleFormController implements BaseFormControllerInterface{

	@Autowired
	private MessageManager mgr;
	@Autowired
	public CommonManager commonManager;
	
	@Autowired
	public DomainObjectBinder userBinder;
	
	public MessageManager getMgr() {
		return mgr;
	}

	public void setMgr(MessageManager mgr) {
		this.mgr = mgr;
	}
	public CommonManager getCommonManager() {
		return commonManager;
	}

	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public DomainObjectBinder getUserBinder() {
		return userBinder;
	}

	public void setUserBinder(DomainObjectBinder userBinder) {
		this.userBinder = userBinder;
	}

	@Override
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		UserPrivilege userPrivilege = new UserPrivilege();
		User user = null;
		String userId = (String)request.getParameter("userId");
		if (userId != null && !userId.equals("")) {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>userId" + userId);
			user = (User) baseManager.getObject(User.class, new Long(userId));
			userPrivilege = (UserPrivilege) baseManager.getObjectByParameter(UserPrivilege.class, "user", user);	
			if(userPrivilege != null){
				log.info(">>>>>>>>>>>>>>>>>>>>>>>>True Privilege<<<<<<<<<<");
			}else{
				userPrivilege = new UserPrivilege();
			}
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + user + "<<<<<<<<<<<<");
		} 
		List userBranchList = baseManager.getObjectsByNullParameter(UserBranch.class,"userprivilege");
		for(int x=0;x<userBranchList.size();x++){
			UserBranch userBranch = (UserBranch) userBranchList.get(x);
			baseManager.removeObject(userBranch);
		}
		model.put("userPrivilege",userPrivilege);
		return "addAuthority";
	}

	
	@Override
	public void initBinder(HttpServletRequest request,WebDataBinder binder) {
		// TODO Auto-generated method stub
		super.initBinder(request,binder);
		binder.registerCustomEditor(userBinder.getBindedClass(), null, userBinder);
	}

	public Map populateWebFrameworkList(String error, 
			HttpServletRequest request, @ModelAttribute("model")Object command) {
		String userId=(String)request.getParameter("userId");		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>userId" + userId);
		UserPrivilege getUserPrivilege =new UserPrivilege();
		User user = null;
		
		Map model = new HashMap();
		
		if (userId != null && !userId.equals("")) {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>userId" + userId);
			user = (User) baseManager.getObject(User.class, new Long(userId));
			getUserPrivilege = (UserPrivilege) baseManager.getObjectByParameter(UserPrivilege.class, "user", user);	
			if(getUserPrivilege != null && !getUserPrivilege.equals("")){	
				List list = baseManager.getObjectsByParameter(UserBranch.class,"userprivilege", getUserPrivilege);
				log.info(">>>>>>>>>>>>list size + "+list.size());	
				List branches = (List<UserBranch>)baseManager.getObjectsByParameter(UserBranch.class,"userprivilege", getUserPrivilege);
				model.put("userBranches ",branches);
			}
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + user + "<<<<<<<<<<<<");
		}
		
		List branchList = new ArrayList();
		branchList = mgr.getObjects(Branch.class);
		model.put("branchList",(List<Branch>)branchList);
		model.put("usedId", userId);
		return model;
	}
	
	@Override
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@ModelAttribute("userPrivilege") Object command, HttpServletRequest request, HttpServletResponse response,
			BindingResult result, SessionStatus status) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		UserPrivilege saveUserPrivilege = (UserPrivilege) command;
		String  editBranch = (String) request.getParameter("editBranch");
		String[] branchList = request.getParameterValues("branchList");
		Branch branch = new Branch();
		UserBranch saveUserBranch = new UserBranch();
		List bList = new ArrayList();
		List saveBranchList = new ArrayList();
		if (branchList != null && editBranch != null && !editBranch.equals("")) {
			for (int i = 0; i < branchList.length; i++) {
				branch = (Branch) baseManager.getObject(Branch.class, branchList[i]);
				saveUserBranch = new UserBranch();
				saveUserBranch.setUserprivilege(saveUserPrivilege);
				saveUserBranch.setBranch(branch);
				saveUserPrivilege.getUserBranch().add(saveUserBranch);
			}
		}
		baseManager.saveObject(saveUserPrivilege);
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//		return new ModelAndView(new RedirectView("addAuthority.html?userId="+ saveUserPrivilege.getUser().getId()));
		return new ModelAndView("addAuthority.html?userId="+ saveUserPrivilege.getUser().getId());
	}
}
	
//	protected Object formBackingObject(HttpServletRequest request)
//	throws Exception {
//		// TODO Auto-generated method stub
//		log
//		.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		UserPrivilege userPrivilege = new UserPrivilege();
//		User user = null;
//		String userId = (String)request.getParameter("userId");
//		if (userId != null && !userId.equals("")) {
//			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>userId" + userId);
//			user = (User) baseManager.getObject(User.class, new Long(userId));
//			userPrivilege = (UserPrivilege) baseManager.getObjectByParameter(UserPrivilege.class, "user", user);	
//			if(userPrivilege != null && !userPrivilege.equals("")){
//				log.info(">>>>>>>>>>>>>>>>>>>>>>>>True Privilege<<<<<<<<<<");
//			}else{
//				userPrivilege = new UserPrivilege();
//			}
//			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + user + "<<<<<<<<<<<<");
//		} 
//		List userBranchList = baseManager.getObjectsByNullParameter(UserBranch.class,"userprivilege");
//		for(int x=0;x<userBranchList.size();x++){
//			UserBranch userBranch = (UserBranch) userBranchList.get(x);
//			baseManager.removeObject(userBranch);
//		}
//		return userPrivilege;
//	}
//	protected void onBindAndValidate(HttpServletRequest request,
//	Object command, BindException errors) throws Exception
//
//{
//UserPrivilege UserPrivilege = (UserPrivilege) command;
//String  editBranch = (String) request.getParameter("editBranch");
//String[] branchList = request.getParameterValues("branchList");
//Branch branch = new Branch();
//UserBranch saveUserBranch = new UserBranch();
//List bList = new ArrayList();
//List saveBranchList = new ArrayList();
//
//if(editBranch!= null){
//	if(branchList==null){
//		if (errors.getErrorCount() == 0) {
//			errors.rejectValue("editBranch",
//					"commons.errors.requiredFields", "requiredFields");
//			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>error list");
//		}
//	}
//}
//
//}
//	protected Map referenceData(HttpServletRequest request, Object command,
//			Errors error) throws Exception {
//		// TODO Auto-generated method stub
//		log
//		.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< Start referenceData: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//		Map model = new HashMap();
//		String userId=(String)request.getParameter("userId");		
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>userId" + userId);
//		UserPrivilege getUserPrivilege =new UserPrivilege();
//		User user = null;
//		if (userId != null && !userId.equals("")) {
//			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>userId" + userId);
//			user = (User) baseManager.getObject(User.class, new Long(userId));
//			getUserPrivilege = (UserPrivilege) baseManager.getObjectByParameter(UserPrivilege.class, "user", user);	
//			if(getUserPrivilege != null && !getUserPrivilege.equals("")){	
//				List list = baseManager.getObjectsByParameter(UserBranch.class,"userprivilege", getUserPrivilege);
//				log.info(">>>>>>>>>>>>list size + "+list.size());	
//				model.put("userBranches",baseManager.getObjectsByParameter(UserBranch.class,"userprivilege", getUserPrivilege));
//			}
//			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + user + "<<<<<<<<<<<<");
//		} 
//		
//		List branchList = new ArrayList();
//		branchList = mgr.getObjects(Branch.class);
//		model.put("branchList", branchList);
//		model.put("userId", userId);
//		log
//		.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End referenceData: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//		return model;
//	}
//	
	
	
//	protected ModelAndView onSubmit(HttpServletRequest request,
//			HttpServletResponse response, Object command, BindException error)
//	throws Exception {
//	
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		UserPrivilege saveUserPrivilege = (UserPrivilege) command;
//		String  editBranch = (String) request.getParameter("editBranch");
//		String[] branchList = request.getParameterValues("branchList");
//		Branch branch = new Branch();
//		UserBranch saveUserBranch = new UserBranch();
//		List bList = new ArrayList();
//		List saveBranchList = new ArrayList();
//		if (branchList != null && editBranch != null && !editBranch.equals("")) {
//			for (int i = 0; i < branchList.length; i++) {
//				branch = (Branch) baseManager.getObject(Branch.class, branchList[i]);
//				saveUserBranch = new UserBranch();
//				saveUserBranch.setUserprivilege(saveUserPrivilege);
//				saveUserBranch.setBranch(branch);
//				saveUserPrivilege.getUserBranch().add(saveUserBranch);
//			}
//		}
//		baseManager.saveObject(saveUserPrivilege);
//		
//		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//		return new ModelAndView(new RedirectView("addAuthority.html?userId="+ saveUserPrivilege.getUser().getId()));
//	}
