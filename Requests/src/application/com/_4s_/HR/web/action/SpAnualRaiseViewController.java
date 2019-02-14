package com._4s_.HR.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.SpAnualRaise;

import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseController;

public class SpAnualRaiseViewController extends BaseController {

	protected HRManager hrManager = null;
	
	public HRManager getHrManager() {
		return hrManager;
	}

	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
	    List spAnRaiseList=new ArrayList();
	    
	    if(formType!=null && !formType.equals("")){
	    	empList=hrManager.getEmployeesByCodeAndName(empCodeFrom,empCodeTo,empName);
	    	log.debug("empList>>>>>>>"+empList);
	    		
	    	for(int i=0;i<empList.size();i++){
	    		String year=request.getParameter("year"+i); 
	    		String val=request.getParameter("val"+i); 
	    		HREmployee emp=(HREmployee) empList.get(i);
	    		SpAnualRaise spAnRaise=(SpAnualRaise)hrManager.getObjectByParameter(SpAnualRaise.class, "empCode", emp.getEmpCode());
	    		if(spAnRaise!=null){
	    			log.debug("spAnualRaise>>>>>>>>>>>"+spAnRaise);
	    			spAnRaise.setEmpName(emp.getName());
	    			if(year!=null && !year.equals("")){
	    				spAnRaise.setYear(new Long(year));
	    			}
	    			if(val!=null && !val.equals("")){
	    				spAnRaise.setVal(new Double(val));
	    			}
	    			spAnRaiseList.add(spAnRaise);
	    		}
	    		else{
	    			SpAnualRaise spAnRa=new SpAnualRaise();
	    			spAnRa.setEmpCode(emp.getEmpCode());
	    			spAnRa.setEmpName(emp.getName());	    			
	    			if(year!=null && !year.equals("") && val!=null && !val.equals("")){
	    				spAnRa.setYear(new Long(year));
		    			spAnRa.setVal(new Double(val));
		    			String search = request.getParameter("search");
		    			if(search!= null && search.equals("true")){
		    				hrManager.saveObject(spAnRa);
		    			}
	    				
	    			}
	    			spAnRaiseList.add(spAnRa);
	    		}
	    	}
	    }
	    
		model.put("results", spAnRaiseList);
		model.put("employeeName", empName);
		model.put("employeeCodeFrom", empCodeFrom);
		model.put("formType", formType);
		model.put("employeeCodeTo", empCodeTo);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.end handleRequest()");	
		
		return  new ModelAndView("spAnualRaiseView", model);
	}
}