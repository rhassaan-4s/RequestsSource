package com._4s_.common.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Branch;
import com._4s_.common.model.Company;
import com._4s_.common.service.BaseManager;
import com._4s_.requestsApproval.model.LoginUsersRequests;

@Controller
@RequestMapping("/branchForm.html")
public class BranchFormController extends BaseSimpleFormController {
	@Autowired
	protected BaseManager baseManager = null;

	public BaseManager getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(HttpServletRequest request,
			@Valid @ModelAttribute("branch") Branch command,
			BindingResult result, SessionStatus status,Model model) {

		if (log.isDebugEnabled()) {
			log.debug("entering 'onSubmit' method....");
		}
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Branch branch = (Branch) command;

		String companyId = request.getParameter("companyId");
		String branchId=request.getParameter("branchId");
		String isDefault = request.getParameter("default");
//		if (isDefault != null && isDefault.equals("true")){
//			Branch defaultBranch = (Branch)baseManager.getDefaultObject(Branch.class);
//			log.debug(">>>>>>>>>>>>>>>>>>>defaultBranch "+defaultBranch);
//			if (defaultBranch != null){
//				defaultBranch.setIsDefault(new Boolean(false));
//			}
//			branch.setIsDefault(new Boolean(true));
//		}else{
//			branch.setIsDefault(new Boolean(false));
//		}
		if(branchId==null || branchId.equals(""))
		{
		log
		.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> CopmanyId: >>>>>>>>>>>>>>>>>>>>>>>>>>>"+companyId);

		Company company = (Company) baseManager.getObject(Company.class,
				new Long(companyId));
		log
		.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> before setting: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		branch.setCompany(company);
		baseManager.saveObject(branch);
		}
		else{
			baseManager.saveObject(branch);
		}
		

		log
				.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		return new ModelAndView(new RedirectView("commonAdminBranchView.html?companyId="+companyId));

	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request){

		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Branch branch = new Branch();

		String branchId = request.getParameter("branchId");
		log.debug(">>>>>>>>>>> branchId :" + branchId);

		if (branchId == null || branchId.equals("")) {
			branch = new Branch();
		} else {
			branch = (Branch) baseManager.getObject(Branch.class, new Long(
					branchId));
		}

		model.addAttribute("branch", branch);
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return "branchForm";
	}
	
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) {
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Map model = new HashMap();

		String companyId = request
				.getParameter("companyId");
		model.put("companyId",companyId);

	
		return model;

	}
}

