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

import com._4s_.HR.model.HRViolationResult;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class ViolationResultForm extends  BaseSimpleFormController{
	

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
		String hrViolationResultId=request.getParameter("hrViolationResultId");
		log.debug("hrViolationResultId"+hrViolationResultId);
		HRViolationResult hrViolationResult=null;
		if(hrViolationResultId==null || hrViolationResultId.equals(""))
		{
			hrViolationResult=new HRViolationResult();
			
			//to put code automatically
			List violationResult=hrManager.getObjects(HRViolationResult.class);
			List codesList=new ArrayList();
			Iterator itr=violationResult.iterator();
			
			while(itr.hasNext())
			{
				HRViolationResult hrViolationResults=(HRViolationResult)itr.next();
				codesList.add(Integer.parseInt(hrViolationResults.getCode()));
				log.debug("--code before zerofill----hrViolationResults.getCode()"+hrViolationResults.getCode());
			}
			
			String code = hrManager.zeroFill(codesList.toArray(),3);
			log.debug("code after zerofill----hrViolationResults"+code);
			hrViolationResult.setCode(code);
		}
		
		else
		{
			hrViolationResult=(HRViolationResult)hrManager.getObject(HRViolationResult.class, new Long(hrViolationResultId));
		}
		log.debug("hrViolationResult"+hrViolationResult);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return hrViolationResult;
	}
//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		String hrViolationResultId=request.getParameter("hrViolationResultId");
		log.debug("hrViolationResultId"+hrViolationResultId);
		model.put("hrViolationResultId",hrViolationResultId);
		List hrViolationResultsList=hrManager.getObjects(HRViolationResult.class);
		model.put("hrViolationResultsList", hrViolationResultsList);
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
		HRViolationResult hrViolationResult=(HRViolationResult)command;
		if(errors.getErrorCount()==0)
		{
			if(hrViolationResult.getCode().length()>3)
			{
				errors.rejectValue("code", "hr.errors.codeLargerThanExpected","code greater than excepected");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			HRViolationResult tit=(HRViolationResult)hrManager.getObjectByParameter(HRViolationResult.class,"code", hrViolationResult.getCode());
			if(tit!=null && (!tit.getId().equals(hrViolationResult.getId())))
					{
				       errors.rejectValue("code", "hr.error.codeExists","code exists");
					}
		}
		
		
		if(errors.getErrorCount()==0)
		{
		
			if(hrViolationResult.getName()==null || hrViolationResult.getName().equals(""))
			{
				errors.rejectValue("name", "commons.errors.requiredFields");
			}
			
			/*else if(hrViolationResult.getName()!=null && !hrViolationResult.getName().equals(""))
			{
				log.debug("errors.getErrorCount() " + errors.getErrorCount());
	

				if(!hrViolationResult.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

				errors.reject("hr.errors.invalidArabicName");

				}

				}*/
		}
		
		/*if(errors.getErrorCount()==0)
		{
		
			if(hrViolationResult.getEname()==null || hrViolationResult.getEname().equals(""))
			{
				errors.rejectValue("ename", "commons.errors.requiredFields");
			}
			
			else if(hrViolationResult.getEname()!=null && !hrViolationResult.getEname().equals(""))
			{
				log.debug("errors.getErrorCount() " + errors.getErrorCount());

				

				

				if(!hrViolationResult.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

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
		HRViolationResult hrViolationResult=(HRViolationResult)command;
		log.debug("hrViolationResult.getId()__________>>>>>>>>>>>>>>>"+hrViolationResult.getId());
		
		hrManager.saveObject(hrViolationResult);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	

}






