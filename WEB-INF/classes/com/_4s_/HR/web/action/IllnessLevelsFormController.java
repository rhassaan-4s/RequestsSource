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

import com._4s_.HR.model.HRIllnessLevels;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class IllnessLevelsFormController extends  BaseSimpleFormController{
	
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
		List illnessLevelsList=hrManager.getObjects(HRIllnessLevels.class);
		HRIllnessLevels illnessLevels=null;
		if(illnessLevelsList.isEmpty() || illnessLevelsList.size()==0)
		{
			illnessLevels=new HRIllnessLevels();
		}
		
		else
		{
			illnessLevels=(HRIllnessLevels)illnessLevelsList.get(0);
		}
		log.debug("illnessLevels"+illnessLevels);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute(illnessLevels);
	   return "illnessLevelsForm";
	}
//**************************************** referenceData ***********************************************\\
	
	@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		List illnessLevelssList=hrManager.getObjects(HRIllnessLevels.class);
		model.put("illnessLevelssList", illnessLevelssList);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}
	
	//**************************************** onBind ***********************************************\\	
	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
//**************************************** onBindAndValidate ***********************************************\\		
	
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HRIllnessLevels illnessLevels=(HRIllnessLevels)command;
		
		
		

		if(errors.getErrorCount()==0)
		{
			if((illnessLevels.getMin1()==null ||illnessLevels.getMin1().equals("")) ||(illnessLevels.getMax1()==null ||illnessLevels.getMax1().equals(""))||(illnessLevels.getP1()==null ||illnessLevels.getP1().equals("")))
			{
				errors.rejectValue("min1", "commons.errors.requiredFields");
			}
		}
		if(errors.getErrorCount()==0)
		{
			if((illnessLevels.getMin2()==null ||illnessLevels.getMin2().equals("")) ||(illnessLevels.getMax2()==null ||illnessLevels.getMax2().equals(""))||(illnessLevels.getP2()==null ||illnessLevels.getP2().equals("")))
			{
				errors.rejectValue("min2", "commons.errors.requiredFields");
			}
		}
		if(errors.getErrorCount()==0)
		{
			if((illnessLevels.getMin3()==null ||illnessLevels.getMin3().equals("")) ||(illnessLevels.getMax3()==null ||illnessLevels.getMax3().equals(""))||(illnessLevels.getP3()==null ||illnessLevels.getP3().equals("")))
			{
				errors.rejectValue("min3", "commons.errors.requiredFields");
			}
		}
		if(errors.getErrorCount()==0)
		{
			if((illnessLevels.getMin4()==null ||illnessLevels.getMin4().equals("")) ||(illnessLevels.getMax4()==null ||illnessLevels.getMax4().equals(""))||(illnessLevels.getP4()==null ||illnessLevels.getP4().equals("")))
			{
				errors.rejectValue("min4", "commons.errors.requiredFields");
			}
		}
				

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HRIllnessLevels illnessLevels=(HRIllnessLevels)command;
		log.debug("illnessLevels.getId()__________>>>>>>>>>>>>>>>"+illnessLevels.getId());
		
		hrManager.saveObject(illnessLevels);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("illnessLevelsForm.html"));
	}
	

}



