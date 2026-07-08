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

import com._4s_.HR.model.HRFunctionalGroup;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class FunctionalGroupForm extends  BaseSimpleFormController{
	

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
		String hrFunctionalGroupId=request.getParameter("hrFunctionalGroupId");
		log.debug("hrFunctionalGroupId"+hrFunctionalGroupId);
		HRFunctionalGroup hrFunctionalGroup=null;
		if(hrFunctionalGroupId==null || hrFunctionalGroupId.equals(""))
		{
			hrFunctionalGroup=new HRFunctionalGroup();
			
			
			//to put code automatically
			List hrTitles=hrManager.getObjects(HRFunctionalGroup.class);
			List codesList=new ArrayList();
			Iterator itr=hrTitles.iterator();
			
			while(itr.hasNext())
			{
				HRFunctionalGroup functionalGroups=(HRFunctionalGroup)itr.next();
				codesList.add(Integer.parseInt(functionalGroups.getCode()));
				log.debug("--code before zerofill----functionalGroups.getCode()"+functionalGroups.getCode());
			}
			
			String code = hrManager.zeroFill(codesList.toArray(),3);
			log.debug("code after zerofill----functionalGroups"+code);
			hrFunctionalGroup.setCode(code);
		}
		
		else
		{
			hrFunctionalGroup=(HRFunctionalGroup)hrManager.getObject(HRFunctionalGroup.class, new Long(hrFunctionalGroupId));
		}
		log.debug("hrFunctionalGroup"+hrFunctionalGroup);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute(hrFunctionalGroup);
	   return "functionalGroupForm";
	}
//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		String hrFunctionalGroupId=request.getParameter("hrFunctionalGroupId");
		log.debug("hrFunctionalGroupId"+hrFunctionalGroupId);
		model.put("hrFunctionalGroupId",hrFunctionalGroupId);
		List hrFunctionalGroupsList=hrManager.getObjects(HRFunctionalGroup.class);
		model.put("hrFunctionalGroupsList", hrFunctionalGroupsList);
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
		HRFunctionalGroup hrFunctionalGroup=(HRFunctionalGroup)command;
		if(errors.getErrorCount()==0)
		{
			if(hrFunctionalGroup.getCode()==null || hrFunctionalGroup.getCode().equals(""))
			{
				errors.rejectValue("code", "commons.errors.requiredFields");
			}
			
		}
		if(errors.getErrorCount()==0)
		{
			if(hrFunctionalGroup.getCode().length()>3)
			{
				errors.rejectValue("code", "hr.errors.codeLargerThanExpected","code greater than excepected");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			HRFunctionalGroup tit=(HRFunctionalGroup)hrManager.getObjectByParameter(HRFunctionalGroup.class,"code", hrFunctionalGroup.getCode());
			if(tit!=null && (!tit.getId().equals(hrFunctionalGroup.getId())))
					{
				       errors.rejectValue("code", "hr.error.codeExists","code exists");
					}
		}
		
		
		if(errors.getErrorCount()==0)
		{
		
			if(hrFunctionalGroup.getName()==null || hrFunctionalGroup.getName().equals(""))
			{
				errors.rejectValue("name", "commons.errors.requiredFields");
			}
			
			/*else if(hrFunctionalGroup.getName()!=null && !hrFunctionalGroup.getName().equals(""))
			{
				log.debug("errors.getErrorCount() " + errors.getErrorCount());
	

				if(!hrFunctionalGroup.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

				errors.reject("hr.errors.invalidArabicName");

				}

				}*/
		}
		
		/*if(errors.getErrorCount()==0)
		{
		
			if(hrFunctionalGroup.getEname()==null || hrFunctionalGroup.getEname().equals(""))
			{
				errors.rejectValue("ename", "commons.errors.requiredFields");
			}
			
			else if(hrFunctionalGroup.getEname()!=null && !hrFunctionalGroup.getEname().equals(""))
			{
				log.debug("errors.getErrorCount() " + errors.getErrorCount());

				

				

				if(!hrFunctionalGroup.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

					errors.reject("hr.errors.invalidEnglishName");

				}

				}*/


				

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HRFunctionalGroup hrFunctionalGroup=(HRFunctionalGroup)command;
		log.debug("hrFunctionalGroup.getId()__________>>>>>>>>>>>>>>>"+hrFunctionalGroup.getId());
		
		hrManager.saveObject(hrFunctionalGroup);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("functionalGroupsView.html"));
	}
	

}






