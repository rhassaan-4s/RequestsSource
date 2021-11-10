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

import com._4s_.HR.model.HRTitle;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class TitleForm extends  BaseSimpleFormController{
	

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
		String hrTitleId=request.getParameter("hrTitleId");
		log.debug("hrTitleId"+hrTitleId);
		HRTitle hrTitle=null;
		if(hrTitleId==null || hrTitleId.equals(""))
		{
			hrTitle=new HRTitle();
			
			
			//to put code automatically
			List hrTitles=hrManager.getObjects(HRTitle.class);
			List codesList=new ArrayList();
			Iterator itr=hrTitles.iterator();
			
			while(itr.hasNext())
			{
				HRTitle title=(HRTitle)itr.next();
				codesList.add(Integer.parseInt(title.getCode()));
				log.debug("--code before zerofill----title.getCode()"+title.getCode());
			}
			
			String code = hrManager.zeroFill(codesList.toArray(),3);
			log.debug("code after zerofill----hrTitle"+code);
			hrTitle.setCode(code);
		}
		
		else
		{
			hrTitle=(HRTitle)hrManager.getObject(HRTitle.class, new Long(hrTitleId));
		}
		log.debug("hrTitle"+hrTitle);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return hrTitle;
	}
//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		String hrTitleId=request.getParameter("hrTitleId");
		log.debug("hrTitleId"+hrTitleId);
		model.put("hrTitleId",hrTitleId);
		List hrTitlesList=hrManager.getObjects(HRTitle.class);
		model.put("hrTitlesList", hrTitlesList);
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
		HRTitle hrTitle=(HRTitle)command;
		if(errors.getErrorCount()==0)
		{
			if(hrTitle.getCode().length()>3)
			{
				errors.rejectValue("code", "hr.errors.codeLargerThanExpected","code greater than excepected");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			HRTitle tit=(HRTitle)hrManager.getObjectByParameter(HRTitle.class,"code", hrTitle.getCode());
			if(tit!=null && (!tit.getId().equals(hrTitle.getId())))
					{
				       errors.rejectValue("code", "hr.error.codeExists","code exists");
					}
		}
		
		
		if(errors.getErrorCount()==0)
		{
		
			if(hrTitle.getName()==null || hrTitle.getName().equals(""))
			{
				errors.rejectValue("name", "commons.errors.requiredFields");
			}
			
			/*else if(hrTitle.getName()!=null && !hrTitle.getName().equals(""))
			{
				log.debug("errors.getErrorCount() " + errors.getErrorCount());
	

				if(!hrTitle.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

				errors.reject("hr.errors.invalidArabicName");

				}

				}*/
		}
		
		/*if(errors.getErrorCount()==0)
		{
		
			if(hrTitle.getEname()==null || hrTitle.getEname().equals(""))
			{
				errors.rejectValue("ename", "commons.errors.requiredFields");
			}
			
			else if(hrTitle.getEname()!=null && !hrTitle.getEname().equals(""))
			{
				log.debug("errors.getErrorCount() " + errors.getErrorCount());

				

				

				if(!hrTitle.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

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
		HRTitle hrTitle=(HRTitle)command;
		log.debug("hrTitle.getId()__________>>>>>>>>>>>>>>>"+hrTitle.getId());
		
		hrManager.saveObject(hrTitle);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("titlesView.html"));
	}
	

}





