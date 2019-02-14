package com._4s_.HR.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.HR.model.HREmployee;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseController;

public class ChangeEmployeeCodesViewController extends BaseController {

	 protected HRManager hrManager = null;
	
	public HRManager getHrManager() {
		return hrManager;
	}


	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.start handleRequest()");
		Map model = new HashMap();
	      String empName=request.getParameter("employeeName");
	       log.debug("employeeName>>>>>>>>"+empName);
	  
	       String empCodeFrom=request.getParameter("employeeCodeFrom");
	       log.debug("employeeCode>>>>>>>>"+empCodeFrom);
	       String empCodeTo=request.getParameter("employeeCodeTo");
	       log.debug("employeeCodeTo>>>>>>>>"+empCodeTo);
	       
	         String formType=request.getParameter("formType");
	      log.debug(">>>>>>>>>>>>>>>>>>>>>>>formType"+formType);
	    
	       List empList=new ArrayList();
	       
	      
	      
	    	  if(formType!=null && !formType.equals(""))
	    	  {
	    		  empList=hrManager.getEmployeesByCodeAndName(empCodeFrom,empCodeTo,empName);
	    		  log.debug("empList>>>>>>>"+empList);
	    		  for(int i=0;i<empList.size();i++)
	    		  {
	    			 String newCode=request.getParameter("newCode"+i); 
	    			HREmployee emp=(HREmployee) empList.get(i);
	    			 if(newCode!=null && !newCode.equals(""))
	    			 {
	    				 HREmployee employee=(HREmployee)hrManager.getObjectByParameter(HREmployee.class, "empCode", newCode);
	    				 if(employee!=null)
	    				 {
	    					 log.debug("employee>>>>>>>>>>>"+employee);
	    					 model.put("errorMessage", "hr.errors.employeeCodeExists");
	    				 }
	    				 else
	    				 {
	    					 emp.setEmpCode(newCode);
	    					 hrManager.saveObject(emp);
	    				 }
	    			 }
	    		  }
	    		  
	    	  }
				
	       	       
	       
				model.put("results", empList);
				model.put("employeeName", empName);
				model.put("employeeCodeFrom", empCodeFrom);
				model.put("formType", formType);
		    	model.put("employeeCodeTo", empCodeTo);
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.end handleRequest()");	
		return  new ModelAndView("changeEmployeeCodes", model);
}


}
