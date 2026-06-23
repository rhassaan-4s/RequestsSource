package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HRAdditional;
import com._4s_.HR.model.HRAdditionalCalcWay;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class AdditionalFormController extends  BaseSimpleFormController{
	
	@Autowired
	private HRManager hrManager;
	
//	public HRManager getHrManager() {
//		return hrManager;
//	}
//	public void setHrManager(HRManager hrManager) {
//		this.hrManager = hrManager;
//	}
	
	
	//**************************************** formBackingObject ***********************************************\\
	@RequestMapping(method = RequestMethod.GET)  
	public String initForm(ModelMap model,HttpServletRequest request){
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		List additionalList=hrManager.getObjects(HRAdditional.class);
		HRAdditional additional=null;
		if(additionalList.size()!=0 && !additionalList.isEmpty())
		{
	       additional=(HRAdditional)additionalList.get(0);
		}
		if(additional==null )
		{
			additional=new HRAdditional();
		}
		
		
		log.debug("additional"+additional);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   model.addAttribute(additional);
	   return "additionalFormController";
	}
//**************************************** referenceData ***********************************************\\
	
	@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		
		List additionalList=hrManager.getObjects(HRAdditional.class);
		if(additionalList.size()!=0 && !additionalList.isEmpty())
		{
		HRAdditional additional=(HRAdditional)additionalList.get(0);
		model.put("additional", additional);
		}
		
		model.put("additionalCalcWay",hrManager.getObjects(HRAdditionalCalcWay.class));
		log.debug("list size>>>>>>>>>>>"+hrManager.getObjects(HRAdditionalCalcWay.class).size());
		log.debug("ogject with id 1>>>"+hrManager.getObject(HRAdditionalCalcWay.class, new Long(1)));
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}
	
	//**************************************** onBind ***********************************************\\	
	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HRAdditional additional=(HRAdditional)command;
		String dayApply=request.getParameter("dayApply");
		String nightApply=request.getParameter("nightApply");
		String vacationApply=request.getParameter("vacationApply");
		log.debug("dayApply>>>>>>>>>>>>>"+dayApply);
		log.debug("nightApply>>>>>>>>>>>>>"+nightApply);
		log.debug("vacationApply>>>>>>>>>>>>>"+vacationApply);
		if(dayApply!=null)
		{
			additional.setDayApply(new Boolean(true));
		}
		else
		{
			additional.setDayApply(new Boolean(false));
		}
		if(nightApply!=null)
		{
			additional.setNightApply(new Boolean(true));
		}
		else
		{
			additional.setNightApply(new Boolean(false));
		}
		if(vacationApply!=null)
		{
			additional.setVacationApply(new Boolean(true));	
		}
		else
		{
			additional.setVacationApply(new Boolean(false));	
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
//**************************************** onBindAndValidate ***********************************************\\		
	
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HRAdditional additional=(HRAdditional)command;
		
		if(errors.getErrorCount()==0)
		{
			if(additional.getAdditionalCalcWayDay()==null || additional.getAdditionalCalcWayDay().equals(""))
			{
				errors.rejectValue("additionalCalcWayDay", "commons.errors.requiredFields","requiredFields");
			}
		}
		if(errors.getErrorCount()==0)
		{
			if(additional.getAdditionalCalcWayNight()==null || additional.getAdditionalCalcWayNight().equals(""))
			{
				errors.rejectValue("additionalCalcWayNight", "commons.errors.requiredFields","requiredFields");
			}
		}
		if(errors.getErrorCount()==0)
		{
			if(additional.getAdditionalCalcWayVacation()==null || additional.getAdditionalCalcWayVacation().equals(""))
			{
				errors.rejectValue("additionalCalcWayVacation", "commons.errors.requiredFields","requiredFields");
			}
		}
		if(errors.getErrorCount()==0)
		{
			if(additional.getAdditionalCalcMaxValue()==null || additional.getAdditionalCalcMaxValue().equals(""))
			{
				errors.rejectValue("additionalCalcMaxValue", "commons.errors.requiredFields","requiredFields");
			}
		}
		
		
	

				

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HRAdditional additional=(HRAdditional)command;
		log.debug("additional.getId()__________>>>>>>>>>>>>>>>"+additional.getId());
		
		hrManager.saveObject(additional);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("additionalForm.html"));
	}
	

}


