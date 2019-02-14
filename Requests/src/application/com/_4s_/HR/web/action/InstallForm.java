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

import com._4s_.HR.model.HRInstall;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class InstallForm  extends  BaseSimpleFormController{

	
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
		String installId=request.getParameter("installId");
		log.debug("installId"+installId);
		HRInstall install=null;
		if(installId==null || installId.equals(""))
		{
			install=new HRInstall();
			
			//to put code automatically
			List installs=hrManager.getObjects(HRInstall.class);
			List codesList=new ArrayList();
			Iterator itr=installs.iterator();
			
			while(itr.hasNext())
			{
				HRInstall inst=(HRInstall)itr.next();
				codesList.add(Integer.parseInt(inst.getInstCode()));
				log.debug("--code before zerofill----inst.getInstCode()"+inst.getInstCode());
			}
			
			String code = hrManager.zeroFill(codesList.toArray(),3);
			log.debug("code after zerofill----"+code);
			install.setInstCode(code);
		}
		
		else
		{
			install=(HRInstall)hrManager.getObject(HRInstall.class, new Long(installId));
		}
		log.debug("install........ "+install.getInstCode());
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return install;
	}
//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		String installId=request.getParameter("installId");
		log.debug("installId"+installId);
		model.put("installId",installId);
		List installsList=hrManager.getObjects(HRInstall.class);
		model.put("installsList", installsList);
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
		HRInstall install=(HRInstall)command;
		log.debug("----installid---"+install.getId());
		if(errors.getErrorCount()==0)
		{
			if(install.getInstCode().length()<3)
			{
				errors.rejectValue("instCode", "hr.errors.codeLargerThanExpected","codeLargerThanExpected");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			HRInstall tit=(HRInstall)hrManager.getObjectByParameter(HRInstall.class,"instCode", install.getInstCode());
			if(tit!=null && (!tit.getId().equals(install.getId())))
					{
				       errors.rejectValue("instCode", "hr.error.codeExists","code exists");
					}
		}
		
		
		if(errors.getErrorCount()==0)
		{
		
			if(install.getInstName()==null || install.getInstName().equals(""))
			{
				errors.rejectValue("instName", "commons.errors.requiredFields");
			}
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HRInstall install=(HRInstall)command;
		log.debug("install.getId()__________>>>>>>>>>>>>>>>"+install.getId());
		
		hrManager.saveObject(install);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	


}
