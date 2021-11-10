package com._4s_.HR.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HRSyndicate;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class SyndicateFormController extends  BaseSimpleFormController{
	
	private HRManager hrManager;
	
	public HRManager getHrManager() {
		return hrManager;
	}
	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}
	
	
	//**************************************** formBackingObject ***********************************************\\
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String syndicateId=request.getParameter("syndicateId");
		log.debug("syndicateId"+syndicateId);
		HRSyndicate syndicate=null;
		if(syndicateId==null || syndicateId.equals(""))
		{
			syndicate=new HRSyndicate();
			
			//to put code automatically
			List syndicates=hrManager.getObjects(HRSyndicate.class);
			List codesList=new ArrayList();
			Iterator itr=syndicates.iterator();

			while(itr.hasNext())
			{
				HRSyndicate hrSyndicate=(HRSyndicate)itr.next();
				codesList.add(Integer.parseInt(hrSyndicate.getCode()));
				log.debug("--code before zerofill----hrSyndicate"+hrSyndicate.getCode());
				
			}
			
			String code = hrManager.zeroFill(codesList.toArray(),4);
			log.debug("code after zerofill----hrSyndicate"+code);
			syndicate.setCode(code);
		}
		
		else
		{
			syndicate=(HRSyndicate)hrManager.getObject(HRSyndicate.class, new Long(syndicateId));
		}
		log.debug("syndicate"+syndicate);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return syndicate;
	}
//**************************************** referenceData ***********************************************\\
	
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		String syndicateId=request.getParameter("syndicateId");
		log.debug("syndicateId"+syndicateId);
		model.put("syndicateId",syndicateId);
		List syndicatesList=hrManager.getObjects(HRSyndicate.class);
		model.put("syndicatesList", syndicatesList);
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
		HRSyndicate syndicate=(HRSyndicate)command;
		
		if(errors.getErrorCount()==0)
		{
			if(syndicate.getCode()==null || syndicate.getCode().equals(""))
			{
				errors.rejectValue("code", "commons.errors.requiredFields","requiredFields");
			}
		}
		if(errors.getErrorCount()==0)
		{
			if(syndicate.getCode().length()>4)
			{
				errors.rejectValue("code", "hr.errors.codeLargerThanExpected","codeLargerThanExpected");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			HRSyndicate tit=(HRSyndicate)hrManager.getObjectByParameter(HRSyndicate.class,"code", syndicate.getCode());
			if(tit!=null && (!tit.getId().equals(syndicate.getId())))
					{
				       errors.rejectValue("code", "hr.error.codeExists","code exists");
					}
		}
		
		
		if(errors.getErrorCount()==0)
		{
		
			if(syndicate.getName()==null || syndicate.getName().equals(""))
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
		HRSyndicate syndicate=(HRSyndicate)command;
		log.debug("syndicate.getId()__________>>>>>>>>>>>>>>>"+syndicate.getId());
		
		hrManager.saveObject(syndicate);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("syndicateView.html"));
	}
	

}


