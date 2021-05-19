package com._4s_.HR.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.HR.model.EmpInstExperYears;
import com._4s_.HR.model.HREmployee;

import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseController;

public class EmpInstExperYearsFormController extends BaseController {

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
	    String empNameS=request.getParameter("employeeName");
	    log.debug("employeeName>>>>>>>>"+empNameS);
	    String empCodeFrom=request.getParameter("employeeCodeFrom");
	    log.debug("employeeCode>>>>>>>>"+empCodeFrom);
	    String empCodeTo=request.getParameter("employeeCodeTo");
	    log.debug("employeeCodeTo>>>>>>>>"+empCodeTo);
	    String formType=request.getParameter("formType");
	    log.debug(">>>>>>>>>>>>>>>>>>>>>>>formType"+formType);
	    
	    List empList=new ArrayList();
	    List empInstExperYearsList = new ArrayList();
	    
	    if(formType!=null && !formType.equals("")){
	    	empList=hrManager.getEmployeesByCodeAndName(empCodeFrom,empCodeTo,empNameS);
	    	log.debug("empList>>>>>>>"+empList);
	    		
	    	for(int i=0;i<empList.size();i++){
	    		String empCode=request.getParameter("empCode"+i); 
	    		String empName=request.getParameter("empName"+i);
	    		String years=request.getParameter("years"+i); 
	    		String monthes=request.getParameter("monthes"+i); 
	    		String days=request.getParameter("days"+i); 
	    		
	    		HREmployee emp=(HREmployee) empList.get(i);
	    		EmpInstExperYears empIEYs = (EmpInstExperYears)hrManager.getObjectByParameter(EmpInstExperYears.class, "empCode", emp.getEmpCode());
	    		
	    		if(empIEYs!=null){
	    			log.debug("spAnualRaise>>>>>>>>>>>"+empIEYs);
	    			if(empCode!=null && !empCode.equals("")){
	    				empIEYs.setEmpCode(empCode);
	    			}
	    			
	    				empIEYs.setEmpName(emp.getName());
	    			
	    			if(years!=null && !years.equals("")){
	    				empIEYs.setYears(new Long(years));
	    			}
	    			if(monthes!=null && !monthes.equals("")){
	    				empIEYs.setMonthes(new Integer(monthes));
	    			}
	    			if(days!=null && !days.equals("")){
	    				empIEYs.setDays(new Integer(days));
	    			}
	    			empInstExperYearsList.add(empIEYs);
	    		}else{
	    			EmpInstExperYears eIEYs = new EmpInstExperYears();
	    			eIEYs.setEmpCode(emp.getEmpCode());
	    			eIEYs.setEmpName(emp.getName());	    			
	    			if(years!=null && !years.equals("") && monthes!=null && !monthes.equals("") && days!=null && !days.equals("")){
	    				eIEYs.setYears(new Long(years));
	    				eIEYs.setMonthes(new Integer(monthes));
	    				eIEYs.setDays(new Integer(days));
	    				hrManager.saveObject(eIEYs);
	    			}
	    			empInstExperYearsList.add(eIEYs);
	    		}
	    	}
	    }
	    model.put("results", empInstExperYearsList);
		model.put("employeeName", empNameS);
		model.put("employeeCodeFrom", empCodeFrom);
		model.put("formType", formType);
		model.put("employeeCodeTo", empCodeTo);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.end handleRequest()");	
		
		return  new ModelAndView("empInstExperYearsForm", model);
	}
}
