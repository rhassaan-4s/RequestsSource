package com._4s_.HR.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HREmployeeEmployeeStatus;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseController;

public class EmployeeStatusView extends BaseController {

	 protected HRManager hrManager = null;
	
	public HRManager getHrManager() {
		return hrManager;
	}


	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.start handleRequest()");
		Map model = new HashMap();
	    String employeeId=request.getParameter("employeeId");
	       log.debug("employeeId>>>>>>>>"+employeeId);
	       String empName=request.getParameter("employeeName");
	       log.debug("employeeName>>>>>>>>"+empName);
	  
	       String empCode=request.getParameter("employeeCode");
	       log.debug("employeeCode>>>>>>>>"+empCode);
	       String add=request.getParameter("add");
	       log.debug("add>>>>>>>"+add);
	       
	       String deleteId=request.getParameter("deleteId");
	       log.debug("deleteId>>>>>>>"+deleteId);
	       
	       String formType=request.getParameter("formType");
	       HREmployee employee=null;
	    
	       List empStatusList=new ArrayList();
	       if(employeeId!=null && !employeeId.equals("") && (deleteId==null || deleteId.equals("")))
	       {
	          employee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
	           formType="employee";
	           empStatusList=hrManager.getObjectsByParameter(HREmployeeEmployeeStatus.class,"employee", employee);
	       }
				
				
	       else
	       {
	    	   formType="transaction";
				if((empName!=null && !empName.equals("")) && (empCode!=null && !empCode.equals("")))
				{
					HREmployee emp=(HREmployee)hrManager.getObjectByParameter(HREmployee.class, "empCode", empCode);
					if(emp!=null)
					{
						employeeId=emp.getId().toString();
					}
					HREmployee emp1=(HREmployee)hrManager.getObjectByParameter(HREmployee.class, "name", empName);
					if(emp.equals(emp1))
					{
						empStatusList=hrManager.getObjectsByParameter(HREmployeeEmployeeStatus.class,"employee", emp);
					}
				}
				
				else if((empName!=null && !empName.equals("")) && (empCode==null || empCode.equals("")))
				{
					HREmployee emp=(HREmployee)hrManager.getObjectByParameter(HREmployee.class, "name", empName);
					if(emp!=null)
					{
						employeeId=emp.getId().toString();
						empCode=emp.getEmpCode();
					}
					log.debug("emp>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+emp);
					empStatusList=hrManager.getObjectsByParameter(HREmployeeEmployeeStatus.class,"employee", emp);
				}
				
				else if((empName==null || empName.equals("")) && (empCode!=null && !empCode.equals("")))
				{
					HREmployee emp=(HREmployee)hrManager.getObjectByParameter(HREmployee.class, "empCode", empCode);
					if(emp!=null)
					{
						employeeId=emp.getId().toString();
					}
					log.debug("emp>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+emp);
					empStatusList=hrManager.getObjectsByParameter(HREmployeeEmployeeStatus.class,"employee", emp);
				}
				
				else if((empName==null || empName.equals("")) && (empCode==null || empCode.equals("")) && request.getMethod()=="POST")
				{
					
					empStatusList=hrManager.getObjects(HREmployeeEmployeeStatus.class);
				}
	       }
	       
	    	
	       if((add!=null && !add.equals(""))&& add.equals("true"))
	       {
	    	   log.debug("empCode>>>>>>"+empCode);
	    	   log.debug("empName>>>>>>"+empName);
	    	  if((empName==null || empName.equals("")) && (empCode==null || empCode.equals("")))
	    	  {
	    		  model.put("errorMessage", "hr.errors.noEmployeeSelected");
	    	  }
	    	  
	    	  else
	    	  {
	    			if((empName!=null && !empName.equals("")) && (empCode!=null && !empCode.equals("")))
					{
						HREmployee emp=(HREmployee)hrManager.getObjectByParameter(HREmployee.class, "empCode", empCode);
						
						HREmployee emp1=(HREmployee)hrManager.getObjectByParameter(HREmployee.class, "name", empName);
						if(emp.equals(emp1))
						{
							employeeId=emp.getId().toString();
							
						}
						
					}
					
					else if((empName!=null && !empName.equals("")) && (empCode==null || empCode.equals("")))
					{
						HREmployee emp=(HREmployee)hrManager.getObjectByParameter(HREmployee.class, "name", empName);
						if(emp!=null)
						{
							employeeId=emp.getId().toString();
						}
						
					}
					
					else if((empName==null || empName.equals("")) && (empCode!=null && !empCode.equals("")))
					{
						HREmployee emp=(HREmployee)hrManager.getObjectByParameter(HREmployee.class, "empCode", empCode);
						if(emp!=null)
						{
							employeeId=emp.getId().toString();
						}
						
					}
	    			log.debug("employeeId::::::::::::"+employeeId);
	    			model.put("formType", formType);
	    	    	model.put("employeeId", employeeId);
	    		 return new ModelAndView(new RedirectView("employeeStatusForm.html"),model);
	    	  }
	       }
	       
	       //deleting object
	       if(deleteId!=null && !deleteId.equals(""))
	       {
	    	HREmployeeEmployeeStatus empStatus=(HREmployeeEmployeeStatus)hrManager.getObject(HREmployeeEmployeeStatus.class, new Long(deleteId));
	    	
	    	  hrManager.removeObject(empStatus);
	       }
	       
	       
				model.put("empStatusList", empStatusList);
				model.put("employeeName", empName);
				model.put("employeeCode", empCode);
				model.put("formType", formType);
		    	model.put("employeeId", employeeId);
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.end handleRequest()");	
		return  new ModelAndView("employeeStatusView", model);
}


}
