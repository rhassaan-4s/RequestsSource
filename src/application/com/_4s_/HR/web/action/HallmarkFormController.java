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

import com._4s_.HR.model.HRHallmark;
import com._4s_.HR.model.HRInsuranceRule;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class HallmarkFormController  extends  BaseSimpleFormController{
	
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
		String hallmarkId=request.getParameter("hallmarkId");
		log.debug("hallmarkId"+hallmarkId);
		HRHallmark hallmark=null;
		if(hallmarkId==null || hallmarkId.equals(""))
		{
			hallmark=new HRHallmark();
			
			//to put code automatically
			List hrHallmarks=hrManager.getObjects(HRHallmark.class);
			List codesList=new ArrayList();
			Iterator itr=hrHallmarks.iterator();

			while(itr.hasNext())
			{
				HRHallmark hrHallmark=(HRHallmark)itr.next();
				codesList.add(Integer.parseInt(hrHallmark.getEffCode()));
				log.debug("--code before zerofill----hallmark"+hrHallmark.getEffCode());
				
			}
			
			String code = hrManager.zeroFill(codesList.toArray(),4);
			log.debug("code after zerofill----hallmark"+code);
			hallmark.setEffCode(code);
		}
		
		else
		{
			hallmark=(HRHallmark)hrManager.getObject(HRHallmark.class, new Long(hallmarkId));
		}
		log.debug("hallmark"+hallmark);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return hallmark;
	}
//**************************************** referenceData ***********************************************\\
	
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		String hallmarkId=request.getParameter("hallmarkId");
		log.debug("hallmarkId"+hallmarkId);
		model.put("hallmarkId",hallmarkId);
		List insuranceRulesList=hrManager.getObjects(HRInsuranceRule.class);
		model.put("insuranceRulesList", insuranceRulesList);
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
		HRHallmark hallmark=(HRHallmark)command;
		
		if(errors.getErrorCount()==0)
		{
			if(hallmark.getEffCode()==null || hallmark.getEffCode().equals(""))
			{
				errors.rejectValue("effCode", "commons.errors.requiredFields","requiredFields");
			}
		}
		if(errors.getErrorCount()==0)
		{
			if(hallmark.getEffCode().length()>4)
			{
				errors.rejectValue("effCode", "hr.errors.codeLargerThanExpected","codeLargerThanExpected");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			HRHallmark tit=(HRHallmark)hrManager.getObjectByParameter(HRHallmark.class,"effCode", hallmark.getEffCode());
			if(tit!=null && (!tit.getId().equals(hallmark.getId())))
					{
				       errors.rejectValue("effCode", "hr.error.codeExists","code exists");
					}
		}
		
		
		if(errors.getErrorCount()==0)
		{
		
			if(hallmark.getEffName()==null || hallmark.getEffName().equals(""))
			{
				errors.rejectValue("effName", "commons.errors.requiredFields");
			}
			
		}
		
		if(errors.getErrorCount()==0)
		{
			if((hallmark.getMin1()==null ||hallmark.getMin1().equals("")) ||(hallmark.getMax1()==null ||hallmark.getMax1().equals(""))||(hallmark.getP1()==null ||hallmark.getP1().equals("")))
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
		HRHallmark hallmark=(HRHallmark)command;
		log.debug("hallmark.getId()__________>>>>>>>>>>>>>>>"+hallmark.getId());
		
		hrManager.saveObject(hallmark);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("hallmarkView.html"));
	}
	

}



