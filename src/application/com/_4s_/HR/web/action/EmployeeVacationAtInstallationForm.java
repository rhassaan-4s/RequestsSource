package com._4s_.HR.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HREmployeeVacationInstall;
import com._4s_.HR.model.HRVacation;
import com._4s_.HR.model.HRVacationType;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.LoginUsersRequests;


public class EmployeeVacationAtInstallationForm extends BaseSimpleFormController{

	private final Log log = LogFactory.getLog(getClass());
	protected HRManager hrManager = null;
		
	public HRManager getHrManager() {
		return hrManager;
	}

	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}
		
	//**************************************** formBackingObject ***********************************************\\
	@RequestMapping(method = RequestMethod.GET)  
	public String initForm(ModelMap model,HttpServletRequest request){
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
		model.addAttribute(hrEmployee);
		return "employeeVacationAtInstallationForm";
	}
//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")	
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,@ModelAttribute HREmployee command,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		
		HREmployee hrEmployee=(HREmployee)command;
		String vacationString=request.getParameter("vacation");
		log.debug("vacation>>>>>>>>>"+vacationString);
		if(vacationString!=null && !vacationString.equals(""))
		{
			HRVacation vacation=(HRVacation)hrManager.getObject(HRVacation.class, new Long(vacationString));
			model.put("vacation", vacation);
		}	 
		if(request.getSession().getAttribute("sub")!=null)
		{
			HRVacation vacation=null;
			
			 vacation=(HRVacation)request.getSession().getAttribute("vacation");
			 log.debug("vacation.id>>>>>>>>>"+vacation.getId());
			 model.put("vacation", vacation);
		    
			// needed List employees=hrManager.getEmployeesForEmployeeVacationAtInstallation(employee.getEmpCode(),employee.getName(),department);
			 List employees=hrManager.getEmployeesForEmployeeVacationAtInstallation(hrEmployee.getEmpCode(),hrEmployee.getName(),null);
			 log.debug("employees.size()>>>>>>>>"+employees.size());
				for(int i=0;i<employees.size();i++)
				{
					HREmployee emp=(HREmployee)employees.get(i);
					log.debug("emp.getId>>>>>>>"+emp.getId());
					String balance=request.getParameter("balance"+i);
					log.debug("balance>>>>>>>"+balance);
					List existingEmployeeVacations=new ArrayList();
					 existingEmployeeVacations=hrManager.getEmployeeVacationAtInstall(emp,vacation );
			           
		           	HREmployeeVacationInstall vacationInstall=null;
		           	if(existingEmployeeVacations.size()!=0 && !existingEmployeeVacations.isEmpty())
		           	{
		           		vacationInstall=(HREmployeeVacationInstall)existingEmployeeVacations.get(0);
		 	           emp.setBalance(vacationInstall.getBalance());
		 	            		
		           	}
				}
			model.put("employees",employees);
			model.put("employeeCode", hrEmployee.getEmpCode());
			model.put("employeeName",hrEmployee.getName());
			model.put("vacationId",vacation.getId());
			request.getSession().removeAttribute("sub");
			request.getSession().removeAttribute("vacation");
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
		String vacationString=request.getParameter("vacation");
		log.debug("vacation>>>>>>>>>"+vacationString);
		HRVacation vacation=null;
		if(vacationString!=null && !vacationString.equals(""))
		{
			vacation=(HRVacation)hrManager.getObject(HRVacation.class, new Long(vacationString));
			
		}
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
		  employeees=hrManager.getEmployeesForEmployeeVacationAtInstallation(employeeCode,employeeName,null);
		}
		
		else if((employeeName==null ||employeeName.equals(""))&&(employeeCode!=null && !employeeCode.equals("")))
		   {
			  employeees=hrManager.getEmployeesForEmployeeVacationAtInstallation(employeeCode,null,null);
			}
		else if((employeeName!=null && !employeeName.equals("")) && (employeeCode==null || employeeCode.equals("")))
		{
			 employeees=hrManager.getEmployeesForEmployeeVacationAtInstallation(null,employeeName,null);
		}
		
		else
		{
			log.debug("indise employeeName==null");
			employeees=hrManager.getEmployeesForEmployeeVacationAtInstallation(hrEmployee.getEmpCode(),hrEmployee.getName(),null);	
		}
		
		log.debug("request.getParameterMap()>>>>>>>>>>>>>"+request.getParameterMap());
		for(int i=0;i<employeees.size();i++)
		{
			HREmployee emp=(HREmployee)employeees.get(i);
			log.debug("emp.getId>>>>>>>"+emp.getId());
			String balance=request.getParameter("balance"+i);
			log.debug("balance>>>>>>>"+balance);
			List existingEmployeeVacations=new ArrayList();
	        existingEmployeeVacations=hrManager.getEmployeeVacationAtInstall(emp,vacation );
	           
           	HREmployeeVacationInstall vacationInstall=null;
           	if(existingEmployeeVacations.size()==0 || existingEmployeeVacations.isEmpty())
           	{
           		vacationInstall=new HREmployeeVacationInstall();
 	            vacationInstall.setEmployee(emp);
 	            vacationInstall.setVacation(vacation);
 	           if(balance!=null && !balance.equals(""))
           		{
 	        	  log.debug("inside balance !=null>>>>>>>"+balance);
 	             vacationInstall.setBalance(new Long(balance));
           		}
 	           log.debug("hrEmployee.getId()"+emp.getId());
 	            hrManager.saveObject(emp);
 	           hrManager.saveObject(vacation);
 	            hrManager.saveObject(vacationInstall);
           		
           	}
           	else
           	{
           		vacationInstall=(HREmployeeVacationInstall)existingEmployeeVacations.get(0);
           		if(balance!=null && !balance.equals(""))
           		{
           		 vacationInstall.setBalance(new Long(balance));
           		}
           	   hrManager.saveObject(vacationInstall);
           	}
			
		}
		 
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
//**************************************** onBindAndValidate ***********************************************\\		
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String vacation=request.getParameter("vacation");
		log.debug("vacation>>>>>>>>>"+vacation);
		if(vacation==null || vacation.equals(""))
		{
			errors.reject("commons.errors.requiredFields","requiredField");
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
//**************************************** onSubmit ***********************************************\\
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HREmployee hrEmployee=(HREmployee)command;
		String vacation=request.getParameter("vacation");
		log.debug("vacation>>>>>>>>>"+vacation);
		request.getSession().setAttribute("hrEmployee",hrEmployee);
		request.getSession().setAttribute("sub","sub");
		if(vacation!=null && !vacation.equals(""))
		{
			HRVacation vac=(HRVacation)hrManager.getObject(HRVacation.class, new Long(vacation));
			request.getSession().setAttribute("vacation",vac);
		}
						
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("employeeVacationAtInstallationForm.html"));
	}
}
