package com._4s_.HR.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.HR.model.HREmpPrevIllness;
import com._4s_.HR.model.HREmployee;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseController;

public class EmpPrevIllnessViewController extends BaseController {
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
	       List empPrevList=new ArrayList();
	      
	      
	    	  if(formType!=null && !formType.equals(""))
	    	  {
	    		  empList=hrManager.getEmployeesByCodeAndName(empCodeFrom,empCodeTo,empName);
	    		  log.debug("empList>>>>>>>"+empList);
	    		
	    		  for(int i=0;i<empList.size();i++)
	    		  {
	    			 String val=request.getParameter("val"+i); 
	    			HREmployee emp=(HREmployee) empList.get(i);
	    			 
	    				 HREmpPrevIllness empPrevIllness=(HREmpPrevIllness)hrManager.getObjectByParameter(HREmpPrevIllness.class, "empcode", emp.getEmpCode());
	    				 if(empPrevIllness!=null)
	    				 {
	    					 log.debug("empprevIllness>>>>>>>>>>>"+empPrevIllness);
	    					 if(val!=null && !val.equals(""))
	    					 {
	    					 empPrevIllness.setVal(new Double(val));
	    					 }
	    					 empPrevList.add(empPrevIllness);
	    				 }
	    				 else
	    				 {
	    					 HREmpPrevIllness empPrev=new HREmpPrevIllness();
	    					 empPrev.setEmpcode(emp.getEmpCode());
	    					 empPrev.setEmpName(emp.getName());
	    					 if(val!=null && !val.equals(""))
	    					 {
	    					 empPrev.setVal(new Double(val));
	    					 hrManager.saveObject(empPrev);
	    					 }
	    					 empPrevList.add(empPrev);
	    				 }
	    			 
	    		  }
	    		  
	    	  }
				
	       	       
	       
				model.put("results", empPrevList);
				model.put("employeeName", empName);
				model.put("employeeCodeFrom", empCodeFrom);
				model.put("formType", formType);
		    	model.put("employeeCodeTo", empCodeTo);
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.end handleRequest()");	
		return  new ModelAndView("empPrevIllnessView", model);
}


}
