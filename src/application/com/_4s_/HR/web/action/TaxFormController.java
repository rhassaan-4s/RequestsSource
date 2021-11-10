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

import com._4s_.HR.model.HRTax;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class TaxFormController  extends  BaseSimpleFormController{
	
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
		String taxId=request.getParameter("taxId");
		log.debug("taxId"+taxId);
		HRTax tax=null;
		if(taxId==null || taxId.equals(""))
		{
			tax=new HRTax();
			
			//to put code automatically
			List hrTaxs=hrManager.getObjects(HRTax.class);
			List codesList=new ArrayList();
			Iterator itr=hrTaxs.iterator();

			while(itr.hasNext())
			{
				HRTax hrTax=(HRTax)itr.next();
				codesList.add(Integer.parseInt(hrTax.getEffCode()));
				log.debug("--code before zerofill----hrTax"+hrTax.getEffCode());
				
			}
			
			String code = hrManager.zeroFill(codesList.toArray(),4);
			log.debug("code after zerofill----hrTax"+code);
			tax.setEffCode(code);
		}
		
		else
		{
			tax=(HRTax)hrManager.getObject(HRTax.class, new Long(taxId));
		}
		log.debug("tax"+tax);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return tax;
	}
//**************************************** referenceData ***********************************************\\
	
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		String taxId=request.getParameter("taxId");
		log.debug("taxId"+taxId);
		model.put("taxId",taxId);
		List taxsList=hrManager.getObjects(HRTax.class);
		model.put("taxsList", taxsList);
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
		HRTax tax=(HRTax)command;
		
		if(errors.getErrorCount()==0)
		{
			if(tax.getEffCode()==null || tax.getEffCode().equals(""))
			{
				errors.rejectValue("effCode", "commons.errors.requiredFields","requiredFields");
			}
		}
		if(errors.getErrorCount()==0)
		{
			if(tax.getEffCode().length()>4)
			{
				errors.rejectValue("effCode", "hr.errors.codeLargerThanExpected","codeLargerThanExpected");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			HRTax tit=(HRTax)hrManager.getObjectByParameter(HRTax.class,"effCode", tax.getEffCode());
			if(tit!=null && (!tit.getId().equals(tax.getId())))
					{
				       errors.rejectValue("effCode", "hr.error.codeExists","code exists");
					}
		}
		
		
		if(errors.getErrorCount()==0)
		{
		
			if(tax.getEffName()==null || tax.getEffName().equals(""))
			{
				errors.rejectValue("effName", "commons.errors.requiredFields");
			}
			
		}
		

		if(errors.getErrorCount()==0)
		{
			if((tax.getMin1()==null ||tax.getMin1().equals("")) ||(tax.getMax1()==null ||tax.getMax1().equals(""))||(tax.getP1()==null ||tax.getP1().equals("")))
			{
				errors.rejectValue("min1", "commons.errors.requiredFields");
			}
		}
				

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HRTax tax=(HRTax)command;
		log.debug("tax.getId()__________>>>>>>>>>>>>>>>"+tax.getId());
		
		hrManager.saveObject(tax);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("taxView.html"));
	}
	

}


