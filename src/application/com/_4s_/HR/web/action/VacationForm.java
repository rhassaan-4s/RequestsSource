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

import com._4s_.HR.model.HRVacation;
import com._4s_.HR.model.HRVacationType;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class VacationForm extends  BaseSimpleFormController{
	
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
		String vacationId=request.getParameter("vacationId");
		log.debug("vacationId"+vacationId);
		HRVacation vacation=null;
		if(vacationId==null || vacationId.equals(""))
		{
			vacation=new HRVacation();
			
			
			//to put code automatically
			List hrVacations=hrManager.getObjects(HRVacation.class);
			List codesList=new ArrayList();
			Iterator itr=hrVacations.iterator();
			
			while(itr.hasNext())
			{
				HRVacation hrVacation=(HRVacation)itr.next();
				codesList.add(Integer.parseInt(hrVacation.getCode()));
				log.debug("--code before zerofill----hrVacation.getCode()"+hrVacation.getCode());
			}
			
			String code = hrManager.zeroFill(codesList.toArray(),4);
			log.debug("code after zerofill----hrVacation"+code);
			vacation.setCode(code);
		}
		
		else
		{
			vacation=(HRVacation)hrManager.getObject(HRVacation.class, new Long(vacationId));
		}
		log.debug("vacation"+vacation);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute(vacation);
	   return "vacationForm";
	}
//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")	
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		String vacationId=request.getParameter("vacationId");
		log.debug("vacationId"+vacationId);
		model.put("vacationId",vacationId);
		List vacationTypeList=hrManager.getObjects(HRVacationType.class);
		log.debug("vacationTypeList.size()>>>>>>>>____________"+vacationTypeList.size());
		model.put("vacationTypeList", vacationTypeList);
		List vacationList=hrManager.getObjectsByParameter(HRVacation.class,"vacationType",hrManager.getObject(HRVacationType.class,new Long(0)));
		log.debug("vacationList.size()>>>>>>>>____________"+vacationList.size());
		model.put("vacationList", vacationList);
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
		HRVacation vacation=(HRVacation)command;
		String isMigrationAllowed=request.getParameter("isMigrationAllowed");
		if(errors.getErrorCount()==0)
		{
			if(vacation.getCode()==null || vacation.getCode().equals(""))
			{
				errors.rejectValue("code", "commons.errors.requiredFields","requiredFields");
			}
		}
		if(errors.getErrorCount()==0)
		{
			if(vacation.getCode().length()>4)
			{
				errors.rejectValue("code", "hr.errors.codeLargerThanExpected","codeLargerThanExpected");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			HRVacation tit=(HRVacation)hrManager.getObjectByParameter(HRVacation.class,"code", vacation.getCode());
			if(tit!=null && (!tit.getId().equals(vacation.getId())))
					{
				       errors.rejectValue("code", "hr.error.codeExists","code exists");
					}
		}
		
		
		if(errors.getErrorCount()==0)
		{
		
			if(vacation.getName()==null || vacation.getName().equals(""))
			{
				errors.rejectValue("name", "commons.errors.requiredFields");
			}
			
			/*else if(religion.getName()!=null && !religion.getName().equals(""))
			{
				log.debug("errors.getErrorCount() " + errors.getErrorCount());
	

				if(!religion.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

				errors.reject("hr.errors.invalidArabicName");

				}

				}*/
		}
		
		if(errors.getErrorCount()==0)
		{
		
			if(vacation.getVacationType()==null )
			{
				errors.rejectValue("vacationType", "commons.errors.requiredFields");
			}
		}
		
		
		if(errors.getErrorCount()==0)
		{
			log.debug("vacation.getIsMigrationAllowed()>>>>>>>>>>>>"+vacation.getIsMigrationAllowed());
		  if(isMigrationAllowed!=null && !isMigrationAllowed.equals(""))
		  {
			if(vacation.getMaxNumberForMigration()==null || vacation.getMaxNumberForMigration().equals("") )
			{
				errors.rejectValue("maxNumberForMigration", "commons.errors.requiredFields");
			}
			
			
		  }
		}
		
		if(errors.getErrorCount()==0)
		{
		  if(isMigrationAllowed!=null && !isMigrationAllowed.equals(""))
		  {
			
			if(vacation.getMigratedTo()==null || vacation.getMigratedTo().equals("") )
			{
				errors.rejectValue("maxNumberForMigration", "commons.errors.requiredFields");
			}
		  }
		}
	log.debug("vacation.getIsMigrationAllowed()>>>>>"+isMigrationAllowed);	
	if(isMigrationAllowed!=null && !isMigrationAllowed.equals(""))
	{
		vacation.setIsMigrationAllowed(new Boolean(true));
	}
	else
	{
		vacation.setIsMigrationAllowed(new Boolean(false));
	}
	/*	if(errors.getErrorCount()==0)
		{
		
			if(religion.getEname()==null || religion.getEname().equals(""))
			{
				errors.rejectValue("ename", "commons.errors.requiredFields");
			}
			
			else if(religion.getEname()!=null && !religion.getEname().equals(""))
			{
				log.debug("errors.getErrorCount() " + errors.getErrorCount());

				

				

				if(!religion.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

					errors.reject("hr.errors.invalidEnglishName");

				}

				}
*/

				

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HRVacation vacation=(HRVacation)command;
		log.debug("vacation.getId()__________>>>>>>>>>>>>>>>"+vacation.getId());
		
		hrManager.saveObject(vacation);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("vacationsView.html"));
	}
	

}


