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

import com._4s_.HR.model.HRBank;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class BankFormController extends  BaseSimpleFormController{
	
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
		String bankId=request.getParameter("bankId");
		log.debug("bankId"+bankId);
		HRBank bank=null;
		if(bankId==null || bankId.equals(""))
		{
			bank=new HRBank();
			
			//to put code automatically
			List hrBanks=hrManager.getObjects(HRBank.class);
			List codesList=new ArrayList();
			Iterator itr=hrBanks.iterator();

			while(itr.hasNext())
			{
				HRBank hrBank=(HRBank)itr.next();
				codesList.add(Integer.parseInt(hrBank.getCode()));
				log.debug("--code before zerofill----hrBank"+hrBank.getCode());
				
			}
			
			String code = hrManager.zeroFill(codesList.toArray(),4);
			log.debug("code after zerofill----hrBank"+code);
			bank.setCode(code);
		}
		
		else
		{
			bank=(HRBank)hrManager.getObject(HRBank.class, new Long(bankId));
		}
		log.debug("bank"+bank);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return bank;
	}
//**************************************** referenceData ***********************************************\\
	
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		String bankId=request.getParameter("bankId");
		log.debug("bankId"+bankId);
		model.put("bankId",bankId);
		List banksList=hrManager.getObjects(HRBank.class);
		model.put("banksList", banksList);
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
		HRBank bank=(HRBank)command;
		
		if(errors.getErrorCount()==0)
		{
			if(bank.getCode()==null || bank.getCode().equals(""))
			{
				errors.rejectValue("code", "commons.errors.requiredFields","requiredFields");
			}
		}
		if(errors.getErrorCount()==0)
		{
			if(bank.getCode().length()>4)
			{
				errors.rejectValue("code", "hr.errors.codeLargerThanExpected","codeLargerThanExpected");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			HRBank tit=(HRBank)hrManager.getObjectByParameter(HRBank.class,"code", bank.getCode());
			if(tit!=null && (!tit.getId().equals(bank.getId())))
					{
				       errors.rejectValue("code", "hr.error.codeExists","code exists");
					}
		}
		
		
		if(errors.getErrorCount()==0)
		{
		
			if(bank.getName()==null || bank.getName().equals(""))
			{
				errors.rejectValue("name", "commons.errors.requiredFields");
			}
			
		}
		
		if(errors.getErrorCount()==0)
		{
			if(bank.getType()==null || bank.getType().equals(""))
			{
				errors.rejectValue("type", "commons.errors.requiredFields","requiredFields");
			}
		}

				

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HRBank bank=(HRBank)command;
		log.debug("bank.getId()__________>>>>>>>>>>>>>>>"+bank.getId());
		
		hrManager.saveObject(bank);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("bankView.html"));
	}
	

}



