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

import com._4s_.HR.model.HRInternalLevel;
import com._4s_.HR.model.HRYearAllowVacationTransfer;
import com._4s_.HR.service.HRManager;
import com._4s_.HR.web.command.YearCommand;
import com._4s_.common.web.action.BaseSimpleFormController;

public class YearAllowVacationTransferForm extends  BaseSimpleFormController{
	
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
			YearCommand yearCommand=null;
			String stringCounter=request.getParameter("counter");
			log.debug("counter>>>>>>>>>>>>>>>>>>>>:::::::"+stringCounter);
			
			if(request.getMethod().equals("GET"))
			{
				yearCommand=new YearCommand();
				List years=hrManager.getObjects(HRYearAllowVacationTransfer.class);
				yearCommand.setYears(years);
				if(years.isEmpty() || years.size()==0)
				{
					log.debug("inside years list ==null"+years.size());
					HRYearAllowVacationTransfer year=new HRYearAllowVacationTransfer();
					years.add(year);
					yearCommand.setYears(years);
					log.debug(">>>>>>>>>>>>yearCommand.getYears() in get>>>>>>>>>>>"+yearCommand.getYears());
					
				}
				
				else
				{
					log.debug("inside years list !=null"+years.size());
					List existingYears=hrManager.getObjectsOrderedByField(HRYearAllowVacationTransfer.class,"year");
					yearCommand.setYears(existingYears);
					
				}
				//request.getSession().setAttribute("yearCommand", yearCommand);
			}
			
			else if(request.getMethod().equals("POST"))
			{
				yearCommand=new YearCommand();
				List years=hrManager.getObjects(HRYearAllowVacationTransfer.class);
				yearCommand.setYears(years);
				if(years.isEmpty() || years.size()==0)
				{
					log.debug("inside years list ==null"+years.size());
					HRYearAllowVacationTransfer year=new HRYearAllowVacationTransfer();
					years.add(year);
					yearCommand.setYears(years);
					log.debug(">>>>>>>>>>>>yearCommand.getYears() in get>>>>>>>>>>>"+yearCommand.getYears());
					
				}
				
				else
				{
					log.debug("inside years list !=null"+years.size());
					List existingYears=hrManager.getObjectsOrderedByField(HRYearAllowVacationTransfer.class,"year");
					yearCommand.setYears(existingYears);
					
				}//log.debug(">>>>>>>>>>>>yearCommand.getYears() before>>>>>>>>>>>"+yearCommand.getYears());
				 int counter=0;
				if(stringCounter!=null && !stringCounter.equals(""))
				{
				 counter=new Long(stringCounter).intValue();
				}
				if(yearCommand!=null)
				{
					int diff=counter-yearCommand.getYears().size();
					for(int i=0;i<diff;i++)
					{
						HRYearAllowVacationTransfer year=new HRYearAllowVacationTransfer();
						yearCommand.getYears().add(year);
					}
				}
				
			}
			
//			log.debug(">>>>>>>>>>>>yearCommand.getYears()>>>>>>>>>>>"+yearCommand.getYears());
			return yearCommand;
			}
				
				
			
			protected Map referenceData(HttpServletRequest request, Object command,Errors errors) throws Exception {
			
			Map model = new HashMap();
			YearCommand result = (YearCommand) command;
			
			String edit = request.getParameter("edit");
			String add = request.getParameter("add");
			
	
			 model.put("edit", edit);
			 model.put("add", add);
			return model;
			}
			
			
			protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
			{
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				YearCommand yearCommand=(YearCommand)command;
				
				for(int i=0;i<yearCommand.getYears().size();i++)
				{
				String year = request.getParameter("year"+i);
				log.debug("year>>>>>>>>>>>>>>>>"+year);
					if(errors.getErrorCount()==0)
					{
						//log.debug("yearAllowVacationTransfer.getYear().SIZE>>>>>>>>>>"+year.length());
						if(year!=null && !year.equals(""))
						{
							if(year.length()>4)
							{
								errors.reject( "hr.errors.yearMustBeEnteredInrightWay","yearMustBeEnteredInrightWay");
							}
						}
					}
				}
			}
			
			protected ModelAndView onSubmit(HttpServletRequest request,
				HttpServletResponse response, Object command, BindException errors)
				throws Exception {
			YearCommand result = (YearCommand) command;
				
				int noOfYears = 0;
				noOfYears =result.getYears().size();
				////////////////////////////////////////////////////////////////////////////////////////////
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>getYears().size() "
						+ result.getYears().size());
				
				for (int i = 0; i < noOfYears; i++) {
				
					HRYearAllowVacationTransfer yearObj=(HRYearAllowVacationTransfer)result.getYears().get(i);
					String year = request.getParameter("year"+i);
					log.debug("year>>>>>>>>>>>>>>>>"+year);
					log.debug("request.getParameterMap()>>>>>>>>>>"+request.getParameterMap().get("year3"));
					
					if(year!=null &&!year.equals(""))
					{
						log.debug("inside year!=null");
				    yearObj.setYear(new Long(year));
					}
					int j = i + 1;
					log.debug("------------------------------j= " + j);
			
					
						  hrManager.saveObject(yearObj);
					   
					
				}
				
				
				
			
			return new ModelAndView(new RedirectView(
					"yearAllowVacationTransferForm.html"));
			}
     }
			
			
			





