package com._4s_.attendance.web.action;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.attendance.model.VacRule;
import com._4s_.attendance.model.VacRulesListWrapper;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.Vacation;
import com._4s_.requestsApproval.web.binders.VacationBinder;

@Controller
@RequestMapping("/vacRuleForm.html")
public class VacRuleForm extends BaseSimpleFormController{
	@Autowired
	AttendanceManager attendanceManager;


	@Autowired
	@Qualifier("vacBinder")
	private VacationBinder vacBinder;
	
	public VacationBinder getVacBinder() {
		return vacBinder;
	}

	public void setVacBinder(VacationBinder vacBinder) {
		this.vacBinder = vacBinder;
	}

	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request){
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		VacRulesListWrapper rules = new VacRulesListWrapper();
		List r = (List)attendanceManager.getObjects(VacRule.class);
		
		log.debug("##################  vac rules list size " + r.size());
		
		rules.setRules(r);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.put("rules",rules);
	   return "vacRuleForm";
	}
	
	//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request,@ModelAttribute("rules") VacRulesListWrapper command)
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		VacRulesListWrapper rules =  (VacRulesListWrapper)command;
		
		
		log.debug("-----rules----"+rules.getClass());
		Map model=new HashMap();
		
		
		List vacList=attendanceManager.getObjectsByParameter(Vacation.class,"type","A");
		model.put("vacList", vacList);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}

	
	@Override
	public void initBinder(WebDataBinder binder) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Starting init binder: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		super.initBinder(binder);
		binder.registerCustomEditor(Vacation.class, vacBinder);
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
		VacRulesListWrapper rules =  (VacRulesListWrapper)command;
//		ValidationStatus status = attendanceManager.validateTitle(title);
//		
//		if (status.getStatus().equals("False") && status.getMsg().equals("Mandatory")) {
//			errors.rejectValue(status.getObjAttribute(), "commons.errors.requiredFields");
//		}
//		if (status.getStatus().equals("False") && status.getMsg().equals("Duplicate")) {
//			errors.rejectValue(status.getObjAttribute(), "commons.errors.duplicateFieldEntry");
//		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(HttpServletRequest request,
			@ModelAttribute("rules") VacRulesListWrapper command,
//			BindingResult result,
			Model model) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		VacRulesListWrapper rules =  (VacRulesListWrapper)command;
		Iterator<VacRule> itrRule = rules.getRules().iterator();
		while(itrRule.hasNext()) {
			VacRule rule = itrRule.next();
			log.debug("rule " + rule);
			log.debug("service years " + rule.getSrvYear());
			log.debug("entitles " + rule.getEntitled());
			log.debug("vac " + rule.getVacation());
			attendanceManager.saveObject(rule);
		}
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("vacRuleForm.html"));
	}
}
