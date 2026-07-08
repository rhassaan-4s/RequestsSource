package com._4s_.requestsApproval.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.AccessLevels;
import com._4s_.requestsApproval.model.AnnualVacLimit;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.Vacation;
import com._4s_.requestsApproval.service.RequestsApprovalManager;

@Controller
@RequestMapping("/annualVacLimitSetting.html")
public class AnnualVacLimitController  extends BaseSimpleFormController{
	@Autowired
	RequestsApprovalManager requestsApprovalManager;

	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}
	
	@RequestMapping(method = RequestMethod.GET) 
	public String initForm(ModelMap model,HttpServletRequest request){
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		AnnualVacLimit annualVacLimit=new AnnualVacLimit();
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute(annualVacLimit);
		return "annualVacLimitSetting";
	}
	
	@ModelAttribute("model")	
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			@ModelAttribute AnnualVacLimit command,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		AnnualVacLimit annualVacLimit=(AnnualVacLimit) command;
		
		
		Vacation vac_id = (Vacation) requestsApprovalManager.getObjectByParameter(Vacation.class, "vacation", "001");
		Map model=new HashMap();
		model.put("vac_id", vac_id.getName());
		
		String done=request.getParameter("done");
		if(done!=null){
			model.put("done", done);
		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}
	
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		AnnualVacLimit annualVacLimit=(AnnualVacLimit) command;
				
		if(errors.getErrorCount()==0)
		{
			if(annualVacLimit.getVac_id()==null || annualVacLimit.getVac_id().equals(""))
			{
				errors.rejectValue("vac_id", "commons.errors.requiredFields");
			}
			
			if(annualVacLimit.getVac_period()==null || annualVacLimit.getVac_period().equals(""))
			{
				errors.rejectValue("vac_period", "commons.errors.requiredFields");
			}
			
			if(annualVacLimit.getVac_limit()==null || annualVacLimit.getVac_limit().equals(""))
			{
				errors.rejectValue("vac_limit", "commons.errors.requiredFields");
			}
			
		}
	}
	
	@RequestMapping(method = RequestMethod.POST) // ,consumes=MediaType.APPLICATION_FORM_URLENCODED
	public ModelAndView processSubmit(HttpServletRequest request,
			@Valid @ModelAttribute("annualVacLimit") AnnualVacLimit command, BindingResult errors,
			SessionStatus status, Map model) {
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		AnnualVacLimit annualVacLimit= command;
		log.debug("annualVacLimit.getId()__________>>>>>>>>>>>>>>>"+annualVacLimit.getId());
		List objects=(List) requestsApprovalManager.getObjectsByParameter(AnnualVacLimit.class, "vac_period", annualVacLimit.getVac_period());
		if(objects.size()>0){
			for (int i = 0; i < objects.size(); i++) {
				AnnualVacLimit object=(AnnualVacLimit) objects.get(i);
				if(object!=null){
					log.debug("---removing---");
					requestsApprovalManager.removeObject(object);
				}
			}
			requestsApprovalManager.saveObject(annualVacLimit);
		}
		if(requestsApprovalManager.getObjectByTwoParameters(AnnualVacLimit.class, "vac_period", annualVacLimit.getVac_period(), "vac_limit", annualVacLimit.getVac_limit())!=null){
			log.debug("---nothing---");
		}
		else{
			log.debug("---saving---");
			requestsApprovalManager.saveObject(annualVacLimit);
		}
		String url="annualVacLimitSetting.html?done=true";
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView(url));
		
	}
}
