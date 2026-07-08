package com._4s_.common.web.action;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Company;
import com._4s_.common.service.BaseManager;
import com._4s_.requestsApproval.model.LoginUsersRequests;

@Controller
@RequestMapping("/commonAdminCompanyForm.html")
public class CompanyFormController extends BaseSimpleFormController {
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
			@Valid @ModelAttribute("company") Company command,
			BindingResult result, SessionStatus status,Model model) {

		if (log.isDebugEnabled()) {
			log.debug("entering 'onSubmit' method....");
		}
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Company company = (Company) command;

		baseManager.saveObject(company);

		log
				.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		return new ModelAndView(new RedirectView("commonAdminCompanyView.html"));

	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request){
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Company company = new Company();

		String companyId = request.getParameter("companyId");
		log.debug(">>>>>>>>>>> companyId :" + companyId);

		if (companyId == null || companyId.equals("")) {
			company = new Company();
		} else {
			company = (Company) baseManager.getObject(Company.class,
					new Long(companyId));
		}

		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.put("company", company);
		return "companyForm";
	}

}
