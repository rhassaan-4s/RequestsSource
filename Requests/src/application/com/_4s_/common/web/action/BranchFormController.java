package com._4s_.common.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Branch;
import com._4s_.common.model.Company;
import com._4s_.common.service.BaseManager;

public class BranchFormController extends BaseSimpleFormController {

	protected BaseManager baseManager = null;

	public BaseManager getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

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

	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {

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

		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return branch;
	}
	
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws ServletException {
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Map model = new HashMap();

		String companyId = request
				.getParameter("companyId");
		model.put("companyId",companyId);

	
		return model;

	}
}

