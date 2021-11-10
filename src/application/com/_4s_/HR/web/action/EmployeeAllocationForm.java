package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HREmployeeJob;
import com._4s_.HR.model.HRInternalDivision;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com.jenkov.prizetags.tree.itf.ITree;

public class EmployeeAllocationForm extends BaseSimpleFormController {
		 protected HRManager hrManager = null;
			
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
			HREmployeeJob hrEmployeeJob=null;
			String empJobId=request.getParameter("empJobId");
			log.debug("empJobId>>>>>>>>>>>>"+empJobId);
			//in case of editing object
			if(empJobId!=null && !empJobId.equals(""))
			{
				hrEmployeeJob=(HREmployeeJob)hrManager.getObject(HREmployeeJob.class, new Long(empJobId));
			}
			
			//add new object
			else
			{
				
			       hrEmployeeJob=new HREmployeeJob();
			       HRInternalDivision intDiv=new HRInternalDivision();
			       hrEmployeeJob.setJob(intDiv);
			}
	       
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		   return hrEmployeeJob;
		}
	//**************************************** referenceData ***********************************************\\
		protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			 Map model=new HashMap();
			 HREmployeeJob hrEmployeeJob=(HREmployeeJob)command;
			 String internalDivisionId=request.getParameter("intDivisionId");
				log.debug("internalDivisionId>>>>>>>>>>>>>>>"+internalDivisionId);
			 String employeeId=request.getParameter("employeeId");
			  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
			  model.put("employeeId", employeeId);
			 	String empJobId=request.getParameter("empJobId");
			     model.put("empJobId", empJobId);
			     
			     String formType=request.getParameter("formType");
			     log.debug("formType>>>>>>>>>>>>>>>"+formType);
			      model.put("formType", formType);
			     
				String intDivString= request.getParameter("intDiv");
				if(intDivString!=null && !intDivString.equals(""))
				{
					HRInternalDivision intDivObj=(HRInternalDivision)hrManager.getObject(HRInternalDivision.class, new Long(intDivString));
					model.put("intDiv",intDivObj);
				}
				log.debug("request.getParameter(intDiv)>>>>>>>>>>>>>"+request.getParameter("intDiv"));
				HRInternalDivision selectedIntDiv=null;
				if(internalDivisionId!=null && !internalDivisionId.equals(""))
				{
				  selectedIntDiv=(HRInternalDivision)hrManager.getObject(HRInternalDivision.class, new Long(internalDivisionId));
				}
				
				
					
					HRInternalDivision intDiv=hrManager.getInternalDivisionForTransaction();
					
					log.debug("intDiv>>>>>>>>>>"+intDiv);
					log.debug("intDiv.getDivisionLevel()>>>>>>>>>>>"+intDiv.getDivisionLevel());
					if(intDiv!=null && selectedIntDiv!=null)
					{
						if(intDiv.getDivisionLevel()!=null && selectedIntDiv.getDivisionLevel()!=null)
						{
							log.debug("selectedIntDiv.getDivisionLevel().getLevelNo()>>>>>>>>"+selectedIntDiv.getDivisionLevel().getLevelNo());
							log.debug("intDiv.getDivisionLevel().getLevelNo()>>>>>>>>"+intDiv.getDivisionLevel().getLevelNo());
							log.debug("selectedIntDiv.getDivisionLevel().getLevelNo().equals(intDiv.getDivisionLevel().getLevelNo())>>::::::::::"+selectedIntDiv.getDivisionLevel().getLevelNo().equals(intDiv.getDivisionLevel().getLevelNo()));
							if(selectedIntDiv.getDivisionLevel().getLevelNo().equals(intDiv.getDivisionLevel().getLevelNo()))
							{
								log.debug("inside two div are equal");
								model.put("intDiv", selectedIntDiv);
								hrEmployeeJob.setJob(selectedIntDiv);
								log.debug("hrEmployeeJob.getJob()"+hrEmployeeJob.getJob());
							}
						}
					}
					
					
			 ITree internalDivisionTree = hrManager.createTreeIfNotFound(request,"HRInternalDivision");
				model.put("internalDivisionTree",internalDivisionTree);
			
			
				log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

			return model;
		}
		
		//**************************************** onBind ***********************************************\\	
		protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			HREmployeeJob hrEmployeeJob=(HREmployeeJob)command;
		
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
		
	//**************************************** onBindAndValidate ***********************************************\\		
		protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		}
		
		
		
		//**************************************** onSubmit ***********************************************\\
		
		public ModelAndView onSubmit(HttpServletRequest request,
				HttpServletResponse response, Object command, BindException errors)throws Exception 
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			HREmployeeJob hrEmployeeJob=(HREmployeeJob)command;
			
		     String formType=request.getParameter("formType");
		     log.debug("formType>>>>>>>>>>>>>>>"+formType);
		     String employeeId=request.getParameter("employeeId");
			  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
			  String intDivId=request.getParameter("intDivId");
			  log.debug("intDivId>>>>>>>>>>>>>>>"+intDivId);
			  log.debug("hrEmployeeJob.getJob()>>>>>>>>>>>>>>>"+hrEmployeeJob.getJob());
			  if(employeeId!=null && !employeeId.equals(""))
			  {
			  HREmployee employee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
			  hrEmployeeJob.setEmployee(employee);
			  }
			  
			  if(intDivId!=null && !intDivId.equals(""))
			  {
			  HRInternalDivision intDivision=(HRInternalDivision)hrManager.getObject(HRInternalDivision.class, new Long(intDivId));
			  hrEmployeeJob.setJob(intDivision);
			  }
			    
				hrManager.saveObject(hrEmployeeJob);
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			if(formType.equals("employee"))
			{
			  return new ModelAndView(new RedirectView("employeeAllocationView.html?employeeId="+employeeId+"&formType="+formType));
			}
			
			return new ModelAndView(new RedirectView("employeeAllocationView.html?formType="+formType));
		}
		

	


}
