package com._4s_.HR.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com._4s_.HR.model.HREffect;
import com._4s_.HR.model.HREffectApplyType;
import com._4s_.HR.model.HREffectDiscDays;
import com._4s_.HR.model.HREffectNature;
import com._4s_.HR.model.HREffectRoundType;
import com._4s_.HR.model.HREffectRule;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class SalaryPartsFormController extends  BaseSimpleFormController{
	
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
		
		String effectId=request.getParameter("effectId");
		log.debug("effectId"+effectId);
		HREffect effect=null;
		if(effectId==null || effectId.equals("")){
			effect=new HREffect();
			
			//to put code automatically
			List hrEffects=hrManager.getObjects(HREffect.class);
			List codesList=new ArrayList();
			Iterator itr=hrEffects.iterator();

			while(itr.hasNext())
			{
				HREffect hrEffect=(HREffect)itr.next();
				codesList.add(Integer.parseInt(hrEffect.getEffcode()));
				log.debug("--code before zerofill----hrEffect"+hrEffect.getEffcode());
				
			}
			
			String code = hrManager.zeroFill(codesList.toArray(),3);
			log.debug("code after zerofill----hrEffect"+code);
			effect.setEffcode(code);
		}else{
			effect=(HREffect)hrManager.getObject(HREffect.class, new Long(effectId));
		}
		log.debug("effect"+effect);
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute(effect);
	   return "salaryPartsForm";
	}
	
	//**************************************** referenceData ***********************************************\\
	
	@ModelAttribute("model")	
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		Map model=new HashMap();
		
		String effectId=request.getParameter("effectId");
		log.debug("effectId"+effectId);
		model.put("effectId",effectId);
		
		if(effectId==null || effectId.equals("")){
			String displayed_flag=request.getParameter("displayed_flag");
			log.debug("displayed_flag"+displayed_flag);
			model.put("displayed_flag",displayed_flag);
		}else{
			HREffect hre = (HREffect) hrManager.getObject(HREffect.class, new Long(effectId));
			String displayed_flag=hre.getDisplayed_flag();
			log.debug("displayed_flag"+displayed_flag);
			model.put("displayed_flag",displayed_flag);
		}
		
		List effectsList=hrManager.getObjects(HREffect.class);
		log.debug("effectsList.size()>>>>>>>>____________"+effectsList.size());
		model.put("effectsList", effectsList);
		
		List effectNaturesList=hrManager.getObjects(HREffectNature.class);
		log.debug("effectNaturesList.size()>>>>>>>>____________"+effectNaturesList.size());
		model.put("effectNaturesList", effectNaturesList);
		
		List effectRulesList=hrManager.getObjects(HREffectRule.class);
		log.debug("effectRulesList.size()>>>>>>>>____________"+effectRulesList.size());
		model.put("effectRulesList", effectRulesList);
		
		List effectDiscDaysList=hrManager.getObjects(HREffectDiscDays.class);
		log.debug("effectDiscDaysList.size()>>>>>>>>____________"+effectDiscDaysList.size());
		model.put("effectDiscDaysList", effectDiscDaysList);
		
		List effectApplyTypesList=hrManager.getObjects(HREffectApplyType.class);
		log.debug("effectApplyTypesList.size()>>>>>>>>____________"+effectApplyTypesList.size());
		model.put("effectApplyTypesList", effectApplyTypesList);
		
		List effectRoundTypesList=hrManager.getObjects(HREffectRoundType.class);
		log.debug("effectRoundTypesList.size()>>>>>>>>____________"+effectRoundTypesList.size());
		model.put("effectRoundTypesList", effectRoundTypesList);

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
		
		HREffect effect=(HREffect)command;
		
		if(errors.getErrorCount()==0){
			if(effect.getEffcode()==null || effect.getEffcode().equals("")){
				errors.rejectValue("effcode", "commons.errors.requiredFields","requiredFields");
			}
		}
		
		if(errors.getErrorCount()==0){
			if(effect.getEffcode().length()>3){
				errors.rejectValue("effcode", "hr.errors.codeLargerThanExpected","codeLargerThanExpected");
			}
		}
		
		if(errors.getErrorCount()==0){
			HREffect tit=(HREffect)hrManager.getObjectByParameter(HREffect.class,"effcode", effect.getEffcode());
			if(tit!=null && (!tit.getId().equals(effect.getId()))){
				errors.rejectValue("effcode", "hr.error.codeExists","code exists");
			}
		}

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)throws Exception {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		HREffect effect=(HREffect)command;
		log.debug("effect.getId()__________>>>>>>>>>>>>>>>"+effect.getId());
		hrManager.saveObject(effect);
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("salaryPartsView.html"));
	}
	

}



