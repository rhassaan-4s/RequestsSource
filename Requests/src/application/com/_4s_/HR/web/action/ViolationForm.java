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

import com._4s_.HR.model.HRCalculationMethod;
import com._4s_.HR.model.HRViolation;
import com._4s_.HR.model.HRViolationReason;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class ViolationForm extends  BaseSimpleFormController{
	
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
		String violationId=request.getParameter("violationId");
		log.debug("violationId"+violationId);
		HRViolation violation=null;
		if(violationId==null || violationId.equals(""))
		{
			violation=new HRViolation();
			
			
			//to put code automatically
			List hrViolation=hrManager.getObjects(HRViolation.class);
			List codesList=new ArrayList();
			Iterator itr=hrViolation.iterator();
			
			while(itr.hasNext())
			{
				HRViolation hrViolations=(HRViolation)itr.next();
				codesList.add(Integer.parseInt(hrViolations.getCode()));
				log.debug("--code before zerofill----hrViolations.getCode()"+hrViolations.getCode());
			}
			
			String code = hrManager.zeroFill(codesList.toArray(),3);
			log.debug("code after zerofill----violation"+code);
			violation.setCode(code);
		}
		
		else
		{
			violation=(HRViolation)hrManager.getObject(HRViolation.class, new Long(violationId));
		}
		log.debug("violation"+violation);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return violation;
	}
//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		String violationId=request.getParameter("violationId");
		log.debug("violationId"+violationId);
		model.put("violationId",violationId);
		List violationReasonList=hrManager.getObjects(HRViolationReason.class);
		log.debug("violationReasonList.size()>>>>>>>>____________"+violationReasonList.size());
		model.put("violationReasonList", violationReasonList);
		List calculationMethodList=hrManager.getObjects(HRCalculationMethod.class);
		log.debug("calculationMethod.size()>>>>>>>>____________"+calculationMethodList.size());
		model.put("calculationMethodList", calculationMethodList);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}
	
	//**************************************** onBind ***********************************************\\	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HRViolation violation=(HRViolation)command;
		String defaultViolation=request.getParameter("defaultViolation");
		String defaultViolation2=request.getParameter("defaultViolation1");
		String defaultViolation3=request.getParameter("defaultViolation2");
		String discountOfLateness=request.getParameter("discountOfLatenessFromSalary");
		String discountLeaveEarly=request.getParameter("discountLeaveEarlyFromSalary");
		String dayAbsenceWithoutPay=request.getParameter("dayAbsenceWithoutPay");
		log.debug("defaultViolation>>>>>>>>>"+request.getParameter("defaultViolation"));
		log.debug("defaultViolation2>>>>>>>>>"+request.getParameter("defaultViolation2"));
		log.debug("defaultViolation3>>>>>>>>>"+request.getParameter("defaultViolation3"));
		log.debug("discountOfLatenessFromSalary>>>>>>"+request.getParameter("discountOfLatenessFromSalary"));
		log.debug("discountLeaveEarlyFromSalary>>>>>>>>"+request.getParameter("discountLeaveEarlyFromSalary"));
		log.debug("dayAbsenceWithoutPay"+request.getParameter("dayAbsenceWithoutPay"));
		if((defaultViolation!=null && !defaultViolation.equals(""))||(defaultViolation2!=null && !defaultViolation2.equals(""))||(defaultViolation3!=null && !defaultViolation3.equals("")))
		{
			log.debug("defaultViolation!=null");
			violation.setDefaultViolation(new Boolean(true));
		}
		else
		{
			violation.setDefaultViolation(new Boolean(false));
		}
		if((discountOfLateness!=null && !discountOfLateness.equals("")) && (violation.getViolationReason().getId()==0))
		{
			violation.setDiscountOfLatenessFromSalary(new Boolean(true));
			violation.setDiscountLeaveEarlyFromSalary(new Boolean(false));
			violation.setDayAbsenceWithoutPay(new Boolean(false));
		}
		else
		{
			violation.setDiscountOfLatenessFromSalary(new Boolean(false));
		}
		if((discountLeaveEarly!=null && !discountLeaveEarly.equals(""))&& (violation.getViolationReason().getId()==2))
		{
			violation.setDiscountLeaveEarlyFromSalary(new Boolean(true));
			violation.setDiscountOfLatenessFromSalary(new Boolean(false));
			violation.setDayAbsenceWithoutPay(new Boolean(false));
		}
		else
		{
			violation.setDiscountLeaveEarlyFromSalary(new Boolean(false));
		}
		if((dayAbsenceWithoutPay!=null && !dayAbsenceWithoutPay.equals(""))&& (violation.getViolationReason().getId()==1))
		{
			violation.setDayAbsenceWithoutPay(new Boolean(true));
			violation.setDiscountLeaveEarlyFromSalary(new Boolean(false));
			violation.setDiscountOfLatenessFromSalary(new Boolean(false));
		}
		
		else
		{
			violation.setDayAbsenceWithoutPay(new Boolean(false));
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
//**************************************** onBindAndValidate ***********************************************\\		
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HRViolation violation=(HRViolation)command;
		
		if(errors.getErrorCount()==0)
		{
			if(violation.getCode()==null || violation.getCode().equals(""))
			{
				errors.rejectValue("code", "commons.errors.requiredFields","requiredFields");
			}
		}
		if(errors.getErrorCount()==0)
		{
			if(violation.getCode().length()>4)
			{
				errors.rejectValue("code", "hr.errors.codeLargerThanExpected","codeLargerThanExpected");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			HRViolation tit=(HRViolation)hrManager.getObjectByParameter(HRViolation.class,"code", violation.getCode());
			if(tit!=null && (!tit.getId().equals(violation.getId())))
					{
				       errors.rejectValue("code", "hr.error.codeExists","code exists");
					}
		}
		
		
		if(errors.getErrorCount()==0)
		{
		
			if(violation.getName()==null || violation.getName().equals(""))
			{
				errors.rejectValue("name", "commons.errors.requiredFields");
			}
		}	
			
		if(errors.getErrorCount()==0)
		{
		
			
		}
		
		
		if(errors.getErrorCount()==0)
		{
			
		}
		
		if(errors.getErrorCount()==0)
		{
		 
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
		HRViolation violation=(HRViolation)command;
		log.debug("violation.getId()__________>>>>>>>>>>>>>>>"+violation.getId());
		
		hrManager.saveObject(violation);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	

}



