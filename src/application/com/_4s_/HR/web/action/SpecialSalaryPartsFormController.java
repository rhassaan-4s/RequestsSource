package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HREffectDiscDays;
import com._4s_.HR.model.HREffectExemptionLimit;
import com._4s_.HR.model.HREffectNature;
import com._4s_.HR.model.HREffectRoundType;
import com._4s_.HR.model.HREffectRule;
import com._4s_.HR.model.HRSpecialEffect;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class SpecialSalaryPartsFormController extends BaseSimpleFormController{
	
	private HRManager hrManager;
	
	public HRManager getHrManager() {
		return hrManager;
	}
	
	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}
	
	//**************************************** formBackingObject ***********************************************\\
	
	@RequestMapping(method = RequestMethod.GET)  
	public String initForm(ModelMap model,HttpServletRequest request){
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		String sEffectId=request.getParameter("sEffectId");
		log.debug("sEffectId"+sEffectId);
		HRSpecialEffect sEffect=null;
		if(sEffectId==null || sEffectId.equals("")){
			sEffect=new HRSpecialEffect();
		}else{
			sEffect=(HRSpecialEffect)hrManager.getObject(HRSpecialEffect.class, new Long(sEffectId));
		}
		log.debug("sEffect"+sEffect);
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute(sEffect);
	   return "specialSalaryPartsForm";
	}
	
	//**************************************** referenceData ***********************************************\\
	
	@ModelAttribute("model")	
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		Map model=new HashMap();
		
		String sEffectId=request.getParameter("sEffectId");
		log.debug("sEffectId"+sEffectId);
		model.put("sEffectId",sEffectId);
		
		if(sEffectId==null || sEffectId.equals("")){
			String displayed_flag=request.getParameter("displayed_flag");
			log.debug("displayed_flag"+displayed_flag);
			model.put("displayed_flag",displayed_flag);
		}else{
			HRSpecialEffect hrse = (HRSpecialEffect) hrManager.getObject(HRSpecialEffect.class, new Long(sEffectId));
			String displayed_flag=hrse.getDisplayed_flag();
			log.debug("displayed_flag"+displayed_flag);
			model.put("displayed_flag",displayed_flag);
		}
		
		List effectNaturesList=hrManager.getObjects(HREffectNature.class);
		log.debug("effectNaturesList.size()>>>>>>>>____________"+effectNaturesList.size());
		model.put("effectNaturesList", effectNaturesList);
		
		List effectRulesList=hrManager.getObjects(HREffectRule.class);
		//In DB table"hr_effect_rule" doesn't contain this new recored as it's not used in SalaryParts
		HREffectRule hrer = new HREffectRule();
		hrer.setId(new Long(2));
		hrer.setName("من مربوط الدرجة");
		hrer.setEname("ofClassAtt");
		effectRulesList.add(hrer);
		log.debug("effectRulesList.size()>>>>>>>>____________"+effectRulesList.size());
		model.put("effectRulesList", effectRulesList);
		
		List effectExemptionLimitList=hrManager.getObjects(HREffectExemptionLimit.class);
		log.debug("effectExemptionLimitList.size()>>>>>>>>____________"+effectExemptionLimitList.size());
		model.put("effectExemptionLimitList", effectExemptionLimitList);
		
		List effectDiscDaysList=hrManager.getObjects(HREffectDiscDays.class);
		//In DB table "hr_effect_disc_days" doesn't contain this new recored as it's not used in SalaryParts
		HREffectDiscDays hredd = new HREffectDiscDays();
		hredd.setId(new Long(3));
		hredd.setName("نسبة التعيين خلال شهر");
		hredd.setEname("appointment per month percentage");
		effectDiscDaysList.add(hredd);
		log.debug("effectDiscDaysList.size()>>>>>>>>____________"+effectDiscDaysList.size());
		model.put("effectDiscDaysList", effectDiscDaysList);
		
		List effectRoundTypesList=hrManager.getObjects(HREffectRoundType.class);
		log.debug("effectRoundTypesList.size()>>>>>>>>____________"+effectRoundTypesList.size());
		model.put("effectRoundTypesList", effectRoundTypesList);

		List sEffectsList=hrManager.getObjects(HRSpecialEffect.class);
		log.debug("sEffectsList.size()>>>>>>>>____________"+sEffectsList.size());
		model.put("sEffectsList", sEffectsList);
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}
	
	//**************************************** onBind ***********************************************\\	
	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onBindAndValidate ***********************************************\\		
	
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		HRSpecialEffect sEffect=(HRSpecialEffect)command;
		
		if(errors.getErrorCount()==0){
			if(sEffect.getEffcode()==null || sEffect.getEffcode().equals("")){
				errors.rejectValue("effcode", "commons.errors.requiredFields","requiredFields");
			}
		}
		
		if(errors.getErrorCount()==0){
			HRSpecialEffect tit=(HRSpecialEffect)hrManager.getObjectByParameter(HRSpecialEffect.class,"effcode", sEffect.getEffcode());
			if(tit!=null && (!tit.getEffcode().equals(sEffect.getEffcode()))){
				errors.rejectValue("effcode", "hr.error.codeExists","code exists");
			}
		}
		
//		if(errors.getErrorCount()==0){
//			if(sEffect.getEffcode().length()>3){
//				errors.rejectValue("effcode", "hr.errors.codeLargerThanExpected","codeLargerThanExpected");
//			}
//		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)throws Exception {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		HRSpecialEffect sEffect=(HRSpecialEffect)command;
		
		hrManager.saveObject(sEffect);
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("specialSalaryPartsView.html"));
	}
}
