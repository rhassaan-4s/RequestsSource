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

import com._4s_.common.model.WebBranch;
import com._4s_.common.model.Company;
import com._4s_.common.service.BaseManager;

public class WebBranchFormController extends BaseSimpleFormController {
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

		WebBranch branch = (WebBranch) command;

		String branchId = request.getParameter("branchId");
		log.debug(">>>>>>>>>>> branchId :" + branchId);

		log.debug("branch " + branch);
		
		String companyId = request.getParameter("companyId");
		String isDefault = request.getParameter("default");
//		if (isDefault != null && isDefault.equals("true")){
//			WebBranch defaultBranch = (Branch)baseManager.getDefaultObject(Branch.class);
//			log.debug(">>>>>>>>>>>>>>>>>>>defaultBranch "+defaultBranch);
//			if (defaultBranch != null){
//				defaultBranch.setIsDefault(new Boolean(false));
//			}
//			branch.setIsDefault(new Boolean(true));
//		}else{
//			branch.setIsDefault(new Boolean(false));
//		}

		String location = request.getParameter("locat");
		if (location!=null && !location.isEmpty()) {
			String[] longLat = location.split(",");
			branch.setBranchLat(new Float(longLat[0]));
			branch.setBranchLong(new Float(longLat[1]));
		}
		if(branchId==null || branchId.equals(""))
		{
			log
			.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> CopmanyId: >>>>>>>>>>>>>>>>>>>>>>>>>>>"+companyId);

			Company company = (Company) baseManager.getObject(Company.class,
					new Long(1));
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

		return new ModelAndView(new RedirectView("webBranchView.html?companyId="+companyId));
	}

	
	

	@Override
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors)
			throws Exception {
		// TODO Auto-generated method stub
		String location = request.getParameter("locat");
		if (location==null || location.isEmpty()) {
			errors.rejectValue("branchLat", "empty");
		}
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		WebBranch branch = new WebBranch();

		String branchId = request.getParameter("branchId");
		log.debug(">>>>>>>>>>> branchId :" + branchId);

		if (branchId != null && !branchId.isEmpty()) {
			branch = (WebBranch) baseManager.getObjectByParameter(WebBranch.class,"branch", new Long(
					branchId));
		}
		log.debug("branch " + branch);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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

