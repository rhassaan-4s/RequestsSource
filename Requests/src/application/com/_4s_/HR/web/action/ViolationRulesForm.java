package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HRCalculationMethod;
import com._4s_.HR.model.HRPeriodType;
import com._4s_.HR.model.HRViolation;
import com._4s_.HR.model.HRViolationResult;
import com._4s_.HR.model.HRViolationRules;
import com._4s_.HR.service.HRManager;
import com._4s_.HR.web.command.ViolationRulesCommand;
import com._4s_.common.web.action.BaseSimpleFormController;

public class ViolationRulesForm extends  BaseSimpleFormController{
	
	private HRManager hrManager;
	
	public HRManager getHrManager() {
		return hrManager;
	}
	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}
	
	
	//**************************************** formBackingObject ***********************************************\\
				protected Object formBackingObject(HttpServletRequest request)
				throws Exception {
			
			String stringCounter=request.getParameter("counter");
			log.debug("counter>>>>>>>>>>>>>>>>>>>>:::::::"+stringCounter);
            
			String deleteId=request.getParameter("selectedDeleteId");
			log.debug("deleteId>>>>>>"+deleteId);
			
			if(deleteId!=null && !deleteId.equals(""))
			{
				hrManager.deleteViolationRules(new Long(deleteId));
			}
			ViolationRulesCommand violationRulesCommand=null;
			if(request.getMethod().equals("GET"))
			{
				violationRulesCommand=new ViolationRulesCommand();
				List violationRulesList=hrManager.getObjects(HRViolationRules.class);
				violationRulesCommand.setViolations(violationRulesList);
				if(violationRulesList.isEmpty() || violationRulesList.size()==0)
				{
					log.debug("inside years list ==null"+violationRulesList.size());
					HRViolationRules violationRules=new HRViolationRules();
					HRPeriodType periodType=new HRPeriodType();
					violationRules.setPeriodType(periodType);
					violationRulesList.add(violationRules);
					violationRulesCommand.setViolations(violationRulesList);
					log.debug(">>>>>>>>>>>>violationRulesCommand.getViolations() in get>>>>>>>>>>>"+violationRulesCommand.getViolations());
					
				}
				
				log.debug("violationRulesCommand.getViolations().size() get>>>>>>>>>>>>>>"+violationRulesCommand.getViolations().size());
				//request.getSession().setAttribute("violationRulesCommand", violationRulesCommand);
			}
			
			else if(request.getMethod().equals("POST"))
			{
				 //log.debug(">>>>>>>>>>>>violationRulesCommand.getViolations() before>>>>>>>>>>>"+violationRulesCommand.getViolations());
				 int counter=0;
				 log.debug("request.getParameterMap()>>>>>>>>>>>>"+request.getParameterMap());
				if(stringCounter!=null && !stringCounter.equals(""))
				{
				 counter=new Long(stringCounter).intValue();
				}
				violationRulesCommand=new ViolationRulesCommand();
				List violationRulesList=hrManager.getObjects(HRViolationRules.class);
				violationRulesCommand.setViolations(violationRulesList);
				if(violationRulesList.isEmpty() || violationRulesList.size()==0)
				{
					log.debug("inside years list ==null"+violationRulesList.size());
					HRViolationRules violationRules=new HRViolationRules();
					violationRulesList.add(violationRules);
					violationRulesCommand.setViolations(violationRulesList);
					log.debug(">>>>>>>>>>>>violationRulesCommand.getViolations() in get>>>>>>>>>>>"+violationRulesCommand.getViolations());
					
				}
				
				else
				{
					log.debug("inside years list !=null"+violationRulesList.size());
						violationRulesCommand.setViolations(violationRulesList);
					
				}
				if(violationRulesCommand!=null)
				{
					int diff=counter-violationRulesCommand.getViolations().size();
					log.debug("the difference between counter and violationcommand>>>>>>>>"+diff);
					for(int i=0;i<diff;i++)
					{
						log.debug("inside for of counter"+i);
						HRViolationRules violationRules=new HRViolationRules();
						violationRulesCommand.getViolations().add(violationRules);
					}
				}
				log.debug("violationRulesCommand.getViolations().size() post>>>>>>>>>>>>>>"+violationRulesCommand.getViolations().size());
			}
			
			
			
		log.debug(">>>>>>>>>>>>end of formBackingObject>>>>>>>>>>>");
			return violationRulesCommand;
			}
				
	//*******************************************************************************************			
			
			protected Map referenceData(HttpServletRequest request, Object command,Errors errors) throws Exception {
				log.debug(">>>>>>>>>>>>start of referenceData>>>>>>>>>>>");
			Map model = new HashMap();
			ViolationRulesCommand result = (ViolationRulesCommand) command;
			
			String edit = request.getParameter("edit");
			String add = request.getParameter("add");
			
	         List violationList=hrManager.getObjectsOrderedByField(HRViolation.class,"code");
	         List periodTypeList=hrManager.getObjects(HRPeriodType.class);
	         List calculationMethodList=hrManager.getObjects(HRCalculationMethod.class);
	         List violationResultList=hrManager.getObjects(HRViolationResult.class);
	         model.put("violationList", violationList);
	         model.put("periodTypeList",periodTypeList);
	         model.put("violationResultList",violationResultList);
	         model.put("calculationMethodList", calculationMethodList);
			 model.put("edit", edit);
			 model.put("add", add);
			 log.debug(">>>>>>>>>>>>end of referenceData>>>>>>>>>>>");
			return model;
			}
			
			
			protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
			{
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				ViolationRulesCommand violationRulesCommand=(ViolationRulesCommand)command;
				log.debug("request.getParameterMap()>>>>>>>>>>>"+request.getParameterMap());
				log.debug("request.getParameterNames()>>>>>>>>>>>"+request.getParameterNames());
				log.debug("violationRulesCommand.getViolations().size()>>>>>>>>>"+violationRulesCommand.getViolations().size());
				for(int i=0;i<violationRulesCommand.getViolations().size();i++)
				{
					log.debug("request.getParameterMap()>>>>>>>>>>"+request.getParameterMap());
				String vac=request.getParameter("violations["+i+"].violation");
				log.debug("violation>>>>>>>>>>>>>>>>"+vac);
					if(errors.getErrorCount()==0)
					{
						//log.debug("yearAllowViolationTransfer.getYear().SIZE>>>>>>>>>>"+year.length());
						if(vac==null || vac.equals(""))
						{
							
								errors.reject( "common.errors.reqiredFields","reqiredFields");
							
						}
					}
				}
				
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> end onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			}
			
			protected ModelAndView onSubmit(HttpServletRequest request,
				HttpServletResponse response, Object command, BindException errors)
				throws Exception {
			ViolationRulesCommand result = (ViolationRulesCommand) command;
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit >>>>>>>>>>>>>>>>>>>>>>>>>>>");	
				int noOfViolations = 0;
				noOfViolations =result.getViolations().size();
				////////////////////////////////////////////////////////////////////////////////////////////
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>getViolations().size() "
						+ result.getViolations().size());
			
				for (int i = 0; i < noOfViolations; i++) {
				
					HRViolationRules violationRulesObj=(HRViolationRules)result.getViolations().get(i);
					log.debug("violationRulesObj.getId()>>>>>>>>>>>"+violationRulesObj.getId());
				
	
						  hrManager.saveObject(violationRulesObj);
					   
					
				}
				
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> end onSubmit>>>>>>>>>>>>>>>>>>>>>>>>>>>");	
				
			
			return new ModelAndView(new RedirectView(
					"violationRulesForm.html"));
			}
     }
			