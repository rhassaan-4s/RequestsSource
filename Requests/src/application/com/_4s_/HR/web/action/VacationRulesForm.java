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

import com._4s_.HR.model.HRVacation;
import com._4s_.HR.model.HRVacationRules;
import com._4s_.HR.model.HRVacationType;
import com._4s_.HR.service.HRManager;
import com._4s_.HR.web.command.VacationRulesCommand;
import com._4s_.common.web.action.BaseSimpleFormController;

public class VacationRulesForm extends  BaseSimpleFormController{
	
	private HRManager hrManager;
	
	public HRManager getHrManager() {
		return hrManager;
	}
	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}
	
	
	//**************************************** formBackingObject ***********************************************\\
				@Override
				protected Object formBackingObject(HttpServletRequest request)
				throws Exception {
			
			String stringCounter=request.getParameter("counter");
			log.debug("counter>>>>>>>>>>>>>>>>>>>>:::::::"+stringCounter);
            
			String deleteId=request.getParameter("selectedDeleteId");
			log.debug("deleteId>>>>>>"+deleteId);
			
			if(deleteId!=null && !deleteId.equals(""))
			{
				hrManager.deleteVacationRules(new Long(deleteId));
			}
			VacationRulesCommand vacationRulesCommand=null;
			if(request.getMethod().equals("GET"))
			{
				vacationRulesCommand=new VacationRulesCommand();
				List vacationRulesList=hrManager.getObjects(HRVacationRules.class);
				vacationRulesCommand.setVacations(vacationRulesList);
				if(vacationRulesList.isEmpty() || vacationRulesList.size()==0)
				{
					log.debug("inside years list ==null"+vacationRulesList.size());
					HRVacationRules vacationRules=new HRVacationRules();
					vacationRulesList.add(vacationRules);
					vacationRulesCommand.setVacations(vacationRulesList);
					log.debug(">>>>>>>>>>>>vacationRulesCommand.getVacations() in get>>>>>>>>>>>"+vacationRulesCommand.getVacations());
					
				}
				
				log.debug("vacationRulesCommand.getVacations().size() get>>>>>>>>>>>>>>"+vacationRulesCommand.getVacations().size());
				//request.getSession().setAttribute("vacationRulesCommand", vacationRulesCommand);
			}
			
			else if(request.getMethod().equals("POST"))
			{
				 //log.debug(">>>>>>>>>>>>vacationRulesCommand.getVacations() before>>>>>>>>>>>"+vacationRulesCommand.getVacations());
				 int counter=0;
				 log.debug("request.getParameterMap()>>>>>>>>>>>>"+request.getParameterMap());
				if(stringCounter!=null && !stringCounter.equals(""))
				{
				 counter=new Long(stringCounter).intValue();
				}
				vacationRulesCommand=new VacationRulesCommand();
				List vacationRulesList=hrManager.getObjects(HRVacationRules.class);
				vacationRulesCommand.setVacations(vacationRulesList);
				if(vacationRulesList.isEmpty() || vacationRulesList.size()==0)
				{
					log.debug("inside years list ==null"+vacationRulesList.size());
					HRVacationRules vacationRules=new HRVacationRules();
					vacationRulesList.add(vacationRules);
					vacationRulesCommand.setVacations(vacationRulesList);
					log.debug(">>>>>>>>>>>>vacationRulesCommand.getVacations() in get>>>>>>>>>>>"+vacationRulesCommand.getVacations());
					
				}
				
				else
				{
					log.debug("inside years list !=null"+vacationRulesList.size());
						vacationRulesCommand.setVacations(vacationRulesList);
					
				}
				if(vacationRulesCommand!=null)
				{
					int diff=counter-vacationRulesCommand.getVacations().size();
					log.debug("the difference between counter and vacationcommand>>>>>>>>"+diff);
					for(int i=0;i<diff;i++)
					{
						HRVacationRules vacationRules=new HRVacationRules();
						vacationRulesCommand.getVacations().add(vacationRules);
					}
				}
				log.debug("vacationRulesCommand.getVacations().size() post>>>>>>>>>>>>>>"+vacationRulesCommand.getVacations().size());
			}
			
			
			
		log.debug(">>>>>>>>>>>>end of formBackingObject>>>>>>>>>>>");
			return vacationRulesCommand;
			}
				
				
			
			@Override
			protected Map referenceData(HttpServletRequest request, Object command,Errors errors) throws Exception {
				log.debug(">>>>>>>>>>>>start of referenceData>>>>>>>>>>>");
			Map model = new HashMap();
			VacationRulesCommand result = (VacationRulesCommand) command;
			
			String edit = request.getParameter("edit");
			String add = request.getParameter("add");
			
	         List vacationList=hrManager.getObjectsByParameter(HRVacation.class,"vacationType",hrManager.getObject(HRVacationType.class,new Long(0)));
	         model.put("vacationList", vacationList);
			 model.put("edit", edit);
			 model.put("add", add);
			 log.debug(">>>>>>>>>>>>end of referenceData>>>>>>>>>>>");
			return model;
			}
			
			
			@Override
			protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
			{
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				VacationRulesCommand vacationRulesCommand=(VacationRulesCommand)command;
				log.debug("vacationRulesCommand.getVacations().size()>>>>>>>>>"+vacationRulesCommand.getVacations().size());
				for(int i=0;i<vacationRulesCommand.getVacations().size();i++)
				{
					log.debug("request.getParameterMap()>>>>>>>>>>"+request.getParameterMap());
				String vac=request.getParameter("vacations["+i+"].vacation");
				log.debug("vacation>>>>>>>>>>>>>>>>"+vac);
					if(errors.getErrorCount()==0)
					{
						//log.debug("yearAllowVacationTransfer.getYear().SIZE>>>>>>>>>>"+year.length());
						if(vac==null || vac.equals(""))
						{
							
								errors.reject("commons.errors.requiredFields","reqiredFields");
							
						}
					}
				}
				
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> end onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			}
			
			@Override
			protected ModelAndView onSubmit(HttpServletRequest request,
				HttpServletResponse response, Object command, BindException errors)
				throws Exception {
			VacationRulesCommand result = (VacationRulesCommand) command;
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit >>>>>>>>>>>>>>>>>>>>>>>>>>>");	
				int noOfVacations = 0;
				noOfVacations =result.getVacations().size();
				////////////////////////////////////////////////////////////////////////////////////////////
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>getVacations().size() "
						+ result.getVacations().size());
				
				for (int i = 0; i < noOfVacations; i++) {
				
					HRVacationRules vacationRulesObj=(HRVacationRules)result.getVacations().get(i);
					log.debug("vacationRulesObj.getId()>>>>>>>>>>>"+vacationRulesObj.getId());
					log.debug("vacationRulesObj.getDue()>>>>>>>>>>" + vacationRulesObj.getDue());
					log.debug("vacationRulesObj.getServiceLength()>>>>>>>>>>" + vacationRulesObj.getServiceLength());
					/*	String vacation = request.getParameter("vacation"+i);
		            String due= request.getParameter("vacations["+i+"].due");
		            String serviceLength=request.getParameter("vacations["+i+"].serviceLength");
					log.debug("vacation>>>>>>>>>>>>>>>>"+vacation);
					log.debug("due>>>>>>>>>>>>>>>>"+due);
					log.debug("serviceLength>>>>>>>>>>>>>>>>"+serviceLength);
					log.debug("request.getParameterMap()>>>>>>>>>>"+request.getParameterMap());
					log.debug("request.getParameterMap()>>>>>>>>>>"+request.getParameterMap().values());
					
					if(vacation!=null &&!vacation.equals(""))
					{
						log.debug("inside vacation!=null");
						HRVacation vacationObj=(HRVacation)hrManager.getObject(HRVacation.class,new Long(vacation));
				        vacationRulesObj.setVacation(vacationObj);
					}
					if(due!=null && !due.equals(""))
					{
					  vacationRulesObj.setDue(new Long(due));
					}
					
					if(serviceLength!=null && !serviceLength.equals(""))
					{
					 vacationRulesObj.setServiceLength(new Long(serviceLength));
					}*/
					int j = i + 1;
					log.debug("------------------------------j= " + j);
			
					
						  hrManager.saveObject(vacationRulesObj);
					   
					
				}
				
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> end onSubmit>>>>>>>>>>>>>>>>>>>>>>>>>>>");	
				
			
			return new ModelAndView(new RedirectView(
					"vacationRulesForm.html"));
			}
     }
			