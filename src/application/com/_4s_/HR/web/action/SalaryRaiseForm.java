package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.SalaryRaise;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class SalaryRaiseForm  extends  BaseSimpleFormController{
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
			String salaryRaiseId=request.getParameter("salaryRaiseId");
			log.debug("salaryRaiseId"+salaryRaiseId);
			SalaryRaise salaryRaise=null;
			if(salaryRaiseId==null || salaryRaiseId.equals(""))
			{
				salaryRaise=new SalaryRaise();
			}
			
			else
			{
				salaryRaise=(SalaryRaise)hrManager.getObject(SalaryRaise.class, new Long(salaryRaiseId));
			}
			log.debug("salaryRaise>>>>>>>>>"+salaryRaise);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		   return salaryRaise;
		}
	//**************************************** referenceData ***********************************************\\
		protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Map model=new HashMap();
			String salaryRaiseId=request.getParameter("salaryRaiseId");
			log.debug("salaryRaiseId>>>>>>"+salaryRaiseId);
			model.put("salaryRaiseId",salaryRaiseId);
			List salaryRaisesList=hrManager.getObjects(SalaryRaise.class);
			model.put("salaryRaisesList", salaryRaisesList);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return model;
		}
		
		//**************************************** onBind ***********************************************\\	
		protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			SalaryRaise salaryRaise=(SalaryRaise)command;
			String stype=request.getParameter("stype");
			log.debug("stype>>>>>>>>>"+stype);
			if(stype!=null && !stype.equals(""))
			{
				salaryRaise.setStype(new Boolean(true));
			}
			else
			{
				salaryRaise.setStype(new Boolean(false));
			}
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
		
	//**************************************** onBindAndValidate ***********************************************\\		
		protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			SalaryRaise salaryRaise=(SalaryRaise)command;
			if(errors.getErrorCount()==0)
			{
				log.debug("salaryRaise.getsalaryRaise()>>>>>>>>>>>>>>>"+salaryRaise.getSalraise());
				if(salaryRaise.getSalraise().length()>1)
				{
					errors.rejectValue("salraise", "hr.errors.codeLargerThanExpected","codeLargerThanExpected");
				}
				
				if(errors.getErrorCount()==0)
				{
					SalaryRaise sal=(SalaryRaise)hrManager.getObjectByParameter(SalaryRaise.class,"salraise", salaryRaise.getSalraise());
					if(sal!=null && (!sal.getId().equals(salaryRaise.getId())))
							{
						       errors.rejectValue("salraise", "hr.error.codeExists","code exists");
							}
				}
				if(errors.getErrorCount()==0)
				{
				
					if(salaryRaise.getName()==null || salaryRaise.getName().equals(""))
					{
						errors.rejectValue("name", "commons.errors.requiredFields");
					}
					
					/*else if(salaryRaise.getName()!=null && !salaryRaise.getName().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());
			

						if(!salaryRaise.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

						errors.reject("hr.errors.invalidArabicName");

						}

						}*/
				}
				
				/*if(errors.getErrorCount()==0)
				{
				
					if(salaryRaise.getEname()==null || salaryRaise.getEname().equals(""))
					{
						errors.rejectValue("salraise", "commons.errors.requiredFields");
					}
					
					else if(salaryRaise.getEname()!=null && !salaryRaise.getEname().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());

						

						

						if(!salaryRaise.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

							errors.reject("hr.errors.invalidEnglishName");

						}

						}
*/

				
			}
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
		
		//**************************************** onSubmit ***********************************************\\	
		public ModelAndView onSubmit(HttpServletRequest request,
				HttpServletResponse response, Object command, BindException errors)throws Exception 
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			SalaryRaise salaryRaise=(SalaryRaise)command;
			log.debug("salaryRaise.getId()__________>>>>>>>>>>>>>>>"+salaryRaise.getId());
			
			hrManager.saveObject(salaryRaise);
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			return new ModelAndView(new RedirectView(getSuccessView()));
		}
		

	}