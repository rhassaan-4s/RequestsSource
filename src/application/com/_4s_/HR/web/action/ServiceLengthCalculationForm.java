package com._4s_.HR.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HRServiceLengthCalculation;
import com._4s_.HR.model.HRVacation;
import com._4s_.HR.model.HRVacationType;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class ServiceLengthCalculationForm extends BaseSimpleFormController{

	private final Log log = LogFactory.getLog(getClass());
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
			HREmployee hrEmployee=(HREmployee)request.getSession().getAttribute("hrEmployee");
			if(hrEmployee==null)
			{
				hrEmployee=new HREmployee();
			}
			else
			{
				request.getSession().removeAttribute("hrEmployee");
			}
			
			
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return hrEmployee;
		}
	//**************************************** referenceData ***********************************************\\
		protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Map model=new HashMap();
			
			HREmployee hrEmployee=(HREmployee)command;
			
			
		 
			if(request.getSession().getAttribute("sub")!=null)
			{
				HRVacation vacation=null;
				
			    
				
				 List employees=hrManager.getEmployeesForEmployeeServiceLength(hrEmployee.getEmpCode(),hrEmployee.getName());
				 log.debug("employees.size()>>>>>>>>"+employees.size());
					for(int i=0;i<employees.size();i++)
					{
						HREmployee emp=(HREmployee)employees.get(i);
						log.debug("emp.getId>>>>>>>"+emp.getId());
						String date=request.getParameter("date"+i);
						log.debug("date>>>>>>>"+date);
						List existingEmployeeServiceLength=new ArrayList();
						 existingEmployeeServiceLength=hrManager.getEmployeeServiceLength(emp);
				           
			           	HRServiceLengthCalculation serviceLengthCalculation=null;
			           	if(existingEmployeeServiceLength.size()!=0 && !existingEmployeeServiceLength.isEmpty())
			           	{
			           		serviceLengthCalculation=(HRServiceLengthCalculation)existingEmployeeServiceLength.get(0);
			 	           emp.setServiceDate(serviceLengthCalculation.getServiceDate());
			 	           Map map=hrManager.getDateContents(emp.getServiceDate());
			 	           log.debug(" map.getyear.toString()>>>>>"+ map.get("year").toString());
			 	          log.debug(" map.getmonth.toString()>>>>>"+ map.get("month").toString());
			 	          log.debug(" map.getday.toString()>>>>>"+ map.get("day").toString());
			 	           emp.setYear( map.get("year").toString());
			 	           emp.setMonth(map.get("month").toString());
			 	           emp.setDay(map.get("day").toString());
			           	}
			           	
						
					}
				model.put("employees",employees);
				model.put("employeeCode", hrEmployee.getEmpCode());
				model.put("employeeName",hrEmployee.getName());
				request.getSession().removeAttribute("sub");
				
			}
			
			
					model.put("vacationList",hrManager.getObjectsByParameter(HRVacation.class,"vacationType",hrManager.getObject(HRVacationType.class, new Long(0))));
			
			
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return model;
		}
		
	//**************************************** onBind ***********************************************\\		
		protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			HREmployee hrEmployee=(HREmployee)command;
			
			String employeeName=request.getParameter("employeeName");
			String employeeCode=request.getParameter("employeeCode");
			List employeees=new ArrayList();
			
			/* needed if((employeeName!=null && !employeeName.equals("")) && (employeeCode!=null && !employeeCode.equals("")))
			{
			  employeees=hrManager.getEmployeesForEmployeeVacationAtInstallation(new Long(employeeCode),employeeName,department,vacation);
			}
			
			else if((employeeName==null ||employeeName.equals(""))&&(employeeCode!=null && !employeeCode.equals("")))
			   {
				  employeees=hrManager.getEmployeesForEmployeeVacationAtInstallation(new Long(employeeCode),null,department,vacation);
				}
			else if((employeeName!=null && !employeeName.equals("")) && (employeeCode==null || employeeCode.equals("")))
			{
				 employeees=hrManager.getEmployeesForEmployeeVacationAtInstallation(null,employeeName,department,vacation);
			}
			
			else
			{
				log.debug("indise employeeName==null");
				employeees=hrManager.getEmployeesForEmployeeVacationAtInstallation(employee.getEmpCode(),employee.getName(),department,vacation);	
			}
			*/
			 if((employeeName!=null && !employeeName.equals("")) && (employeeCode!=null && !employeeCode.equals("")))
			{
			  employeees=hrManager.getEmployeesForEmployeeServiceLength(employeeCode,employeeName);
			}
			
			else if((employeeName==null ||employeeName.equals(""))&&(employeeCode!=null && !employeeCode.equals("")))
			   {
				  employeees=hrManager.getEmployeesForEmployeeServiceLength(employeeCode,null);
				}
			else if((employeeName!=null && !employeeName.equals("")) && (employeeCode==null || employeeCode.equals("")))
			{
				 employeees=hrManager.getEmployeesForEmployeeServiceLength(null,employeeName);
			}
			
			else
			{
				log.debug("indise employeeName==null");
				employeees=hrManager.getEmployeesForEmployeeServiceLength(hrEmployee.getEmpCode(),hrEmployee.getName());	
			}
			
		
			log.debug("request.getParameterMap()>>>>>>>>>>>>>"+request.getParameterMap());
			for(int i=0;i<employeees.size();i++)
			{
				HREmployee emp=(HREmployee)employeees.get(i);
				log.debug("emp.getId>>>>>>>"+emp.getId());
				String date=request.getParameter("date"+i);
				log.debug("date>>>>>>>"+date);
				List existingEmployeeServiceLength=new ArrayList();
		        existingEmployeeServiceLength=hrManager.getEmployeeServiceLength(emp );
		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
 				String[] dateStringArray=null;
 		  
	           	HRServiceLengthCalculation serviceLengthCalc=null;
	           	if(existingEmployeeServiceLength.size()==0 || existingEmployeeServiceLength.isEmpty())
	           	{
	           		serviceLengthCalc=new HRServiceLengthCalculation();
	 	            serviceLengthCalc.setEmployee(emp);
	 	         
	 	         		  
	 	           if(date!=null && !date.equals(""))
	           		{
	 	        	  Date convertedDate = dateFormat.parse(date); 
	 				    
	 		            log.debug("convertedDate>>>>>>>>>>>>"+convertedDate);
	 		            Calendar cal=Calendar.getInstance();
	 		            cal.setTime(convertedDate);
	 		            cal.add(Calendar.HOUR_OF_DAY,0);
	 		            cal.add(Calendar.MINUTE, 0);
	 		            cal.add(Calendar.SECOND, 0);
	 		            cal.add(Calendar.MILLISECOND,0);
	 	        	   
	 	        	  log.debug("inside date !=null>>>>>>>"+date);
	 	        	 log.debug("convertedDate>>>>>>>"+convertedDate);
	 	             serviceLengthCalc.setServiceDate(convertedDate);
	           		}
	 	           log.debug("hrEmployee.getId()"+emp.getId());
	 	            hrManager.saveObject(emp);
	 	            hrManager.saveObject(serviceLengthCalc);
	           		
	           	}
	           	else
	           	{
	           		serviceLengthCalc=(HRServiceLengthCalculation)existingEmployeeServiceLength.get(0);
	           		if(date!=null && !date.equals(""))
	           		{
                       Date convertedDate = dateFormat.parse(date); 
	 				    
	 		            log.debug("convertedDate>>>>>>>>>>>>"+convertedDate);
	 		            Calendar cal=Calendar.getInstance();
	 		            cal.setTime(convertedDate);
	 		            cal.add(Calendar.HOUR_OF_DAY,0);
	 		            cal.add(Calendar.MINUTE, 0);
	 		            cal.add(Calendar.SECOND, 0);
	 		            cal.add(Calendar.MILLISECOND,0);
	 	        	   
	 	        	  log.debug("inside date !=null>>>>>>>"+date);
	 	        	 log.debug("convertedDate>>>>>>>"+convertedDate);
	           		 serviceLengthCalc.setServiceDate(convertedDate);
	           		}
	           	   hrManager.saveObject(serviceLengthCalc);
	           	}
				
			}
			
	          
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
			HREmployee hrEmployee=(HREmployee)command;
			request.getSession().setAttribute("hrEmployee",hrEmployee);
			request.getSession().setAttribute("sub","sub");
			
			
							
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			return new ModelAndView(new RedirectView(getSuccessView()));
		}
	

}
