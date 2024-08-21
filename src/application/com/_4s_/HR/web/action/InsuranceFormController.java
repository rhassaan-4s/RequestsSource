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

import com._4s_.HR.model.HRInsurance;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class InsuranceFormController extends  BaseSimpleFormController{
	
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
		String insuranceId=request.getParameter("insuranceId");
		log.debug("insuranceId"+insuranceId);
		HRInsurance insurance=null;
		if(insuranceId==null || insuranceId.equals(""))
		{
			insurance=new HRInsurance();
			
			//to put code automatically
			List hrInsurances=hrManager.getObjects(HRInsurance.class);
			List codesList=new ArrayList();
			Iterator itr=hrInsurances.iterator();

			while(itr.hasNext())
			{
				HRInsurance hrInsurance=(HRInsurance)itr.next();
				codesList.add(Integer.parseInt(hrInsurance.getCode()));
				log.debug("--code before zerofill----hrInsurance"+hrInsurance.getCode());
				
			}
			
			String code = hrManager.zeroFill(codesList.toArray(),4);
			log.debug("code after zerofill----hrInsurance"+code);
			insurance.setCode(code);
		}
		
		else
		{
			insurance=(HRInsurance)hrManager.getObject(HRInsurance.class, new Long(insuranceId));
		}
		log.debug("insurance"+insurance);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute(insurance);
	   return "insuranceForm";
	}
//**************************************** referenceData ***********************************************\\
	
	@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		String insuranceId=request.getParameter("insuranceId");
		log.debug("insuranceId"+insuranceId);
		model.put("insuranceId",insuranceId);
		List insurancesList=hrManager.getObjects(HRInsurance.class);
		model.put("insurancesList", insurancesList);
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
		HRInsurance insurance=(HRInsurance)command;
		
		if(errors.getErrorCount()==0)
		{
			if(insurance.getCode()==null || insurance.getCode().equals(""))
			{
				errors.rejectValue("code", "commons.errors.requiredFields","requiredFields");
			}
		}
		if(errors.getErrorCount()==0)
		{
			if(insurance.getCode().length()>4)
			{
				errors.rejectValue("code", "hr.errors.codeLargerThanExpected","codeLargerThanExpected");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			HRInsurance tit=(HRInsurance)hrManager.getObjectByParameter(HRInsurance.class,"code", insurance.getCode());
			if(tit!=null && (!tit.getId().equals(insurance.getId())))
					{
				       errors.rejectValue("code", "hr.error.codeExists","code exists");
					}
		}
		
		
		if(errors.getErrorCount()==0)
		{
		
			if(insurance.getName()==null || insurance.getName().equals(""))
			{
				errors.rejectValue("name", "commons.errors.requiredFields");
			}
			
		}
				

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HRInsurance insurance=(HRInsurance)command;
		log.debug("insurance.getId()__________>>>>>>>>>>>>>>>"+insurance.getId());
		
		hrManager.saveObject(insurance);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("insuranceView.html"));
	}
	

}


