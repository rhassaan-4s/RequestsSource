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
import com._4s_.HR.model.HREmployeeQualification;
import com._4s_.HR.model.HRQualificationDivision;
import com._4s_.HR.model.HRSpecialtyDivision;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com.jenkov.prizetags.tree.itf.ITree;

public class QualificationsTransactionForm extends BaseSimpleFormController {
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
        String employeeId=request.getParameter("employeeId");
        log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
        HREmployee employee=null;
        if(employeeId!=null && !employeeId.equals(""))
        {
        	employee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
        }
		String empQualificationId=request.getParameter("empQualificationId");
        HREmployeeQualification hrEmployeeQualification=null;
      //in case of editing object
        if(empQualificationId!=null && !empQualificationId.equals(""))
        {
        	hrEmployeeQualification=(HREmployeeQualification)hrManager.getObject(HREmployeeQualification.class,new Long(empQualificationId));
        }
        
        else
        {
        	hrEmployeeQualification=new HREmployeeQualification();
        	HRQualificationDivision qDiv=new HRQualificationDivision();
        	HRSpecialtyDivision spDiv=new HRSpecialtyDivision();
        	hrEmployeeQualification.setQualification(qDiv);
        	hrEmployeeQualification.setSpecialty(spDiv);
        }
	
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return hrEmployeeQualification ;
	}
//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 Map model=new HashMap();
		HREmployeeQualification hrEmployeeQualification=(HREmployeeQualification)command;
		String qualDivisionId=request.getParameter("qualDivisionId");
		log.debug("qualDivisionId>>>>>>>>>>>>>>>"+qualDivisionId);
		String specDivisionId=request.getParameter("specDivisionId");
		log.debug("specDivisionId>>>>>>>>>>>>>>>"+specDivisionId);
		 String employeeId=request.getParameter("employeeId");
	     log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
	     model.put("employeeId", employeeId);
	 	String empQualificationId=request.getParameter("empQualificationId");
	     model.put("empQualificationId", empQualificationId);
	     
	     String formType=request.getParameter("formType");
	     log.debug("formType>>>>>>>>>>>>>>>"+formType);
	    model.put("formType", formType);
	     
		String qualDivString= request.getParameter("qualDiv");
		if(qualDivString!=null && !qualDivString.equals(""))
		{
			HRQualificationDivision qualDivObj=(HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class, new Long(qualDivString));
			model.put("qualDiv",qualDivObj);
		}
		log.debug("request.getParameter(qualDiv)>>>>>>>>>>>>>"+request.getParameter("qualDiv"));
		String specDivString=request.getParameter("specDiv");
		if(specDivString!=null && !specDivString.equals(""))
		{
			HRSpecialtyDivision specDivObj=(HRSpecialtyDivision)hrManager.getObject(HRSpecialtyDivision.class, new Long(specDivString));
			model.put("specDiv",specDivObj );
		}
		log.debug("request.getParameter(specDivision)>>>>>>>>>>>>>"+request.getParameter("specDiv"));
		log.debug("request.getParameter(spec)>>>>>>>>>>>>>>>>>>>>>>>>>>"+request.getParameter("spec"));
		HRQualificationDivision selectedQualDiv=null;
		if(qualDivisionId!=null && !qualDivisionId.equals(""))
		{
		  selectedQualDiv=(HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class, new Long(qualDivisionId));
		}
		
		HRSpecialtyDivision selectedSpecDiv=null;
		if(specDivisionId!=null && !specDivisionId.equals(""))
		{
		  selectedSpecDiv=(HRSpecialtyDivision)hrManager.getObject(HRSpecialtyDivision.class, new Long(specDivisionId));
	    }
		
			
			HRQualificationDivision qualDiv=hrManager.getQualificationDivisionForTransaction();
			HRSpecialtyDivision specDiv=hrManager.getSpecialtyDivisionForTransaction();
			if(qualDiv!=null && selectedQualDiv!=null)
			{
				if(qualDiv.getDivisionLevel()!=null && selectedQualDiv.getDivisionLevel()!=null)
				{
					log.debug("selectedQualDiv.getDivisionLevel().getLevelNo()>>>>>>>>"+selectedQualDiv.getDivisionLevel().getLevelNo());
					log.debug("qualDiv.getDivisionLevel().getLevelNo()>>>>>>>>"+qualDiv.getDivisionLevel().getLevelNo());
					if(selectedQualDiv.getDivisionLevel().getLevelNo().equals(qualDiv.getDivisionLevel().getLevelNo()))
					{
						model.put("qualDiv", selectedQualDiv);
					}
				}
			}
			
			if(specDiv!=null && selectedSpecDiv!=null)
			{
				if(specDiv.getDivisionLevel()!=null && selectedSpecDiv.getDivisionLevel()!=null)
				{
					log.debug("selectedSpecDiv.getDivisionLevel().getLevelNo()>>>>>>>>>>>>>>"+selectedSpecDiv.getDivisionLevel().getLevelNo());
					log.debug("specDiv.getDivisionLevel().getLevelNo()>>>>>>>>>>>>>>"+specDiv.getDivisionLevel().getLevelNo());
					if(selectedSpecDiv.getDivisionLevel().getLevelNo().equals(specDiv.getDivisionLevel().getLevelNo()))
					{
						model.put("specDiv", selectedSpecDiv);
					
					}
				}
			}
			ITree qualDivisionTree = hrManager.createTreeIfNotFound(request,"HRQualificationDivision");
			model.put("qualDivisionTree",qualDivisionTree);
			
			ITree specDivisionTree = hrManager.createTreeIfNotFound(request,"HRSpecialtyDivision");
			model.put("specDivisionTree",specDivisionTree);
			
			
		
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		return model;
	}
	
	//**************************************** onBind ***********************************************\\	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
	
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
		HREmployeeQualification hrEmployeeQualification=(HREmployeeQualification)command;
		 String employeeId=request.getParameter("employeeId");
	     log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
	     String formType=request.getParameter("formType");
	     log.debug("formType>>>>>>>>>>>>>>>"+formType);
	     
	     String qualDivId=request.getParameter("qualDivId");
	     String specDivId=request.getParameter("specDivId");
	     HREmployee employee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
	     hrEmployeeQualification.setEmployee(employee);
	     
	     if(qualDivId!=null && !qualDivId.equals(""))
		  {
		  HRQualificationDivision qualDivision=(HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class, new Long(qualDivId));
		  hrEmployeeQualification.setQualification(qualDivision);
		  }
	     
	     if(specDivId!=null && !specDivId.equals(""))
	     {
	    	 HRSpecialtyDivision specDivision=(HRSpecialtyDivision)hrManager.getObject(HRSpecialtyDivision.class, new Long(specDivId));
	    	 hrEmployeeQualification.setSpecialty(specDivision);
	     }
	     hrManager.saveObject(hrEmployeeQualification.getEmployee());
		hrManager.saveObject(hrEmployeeQualification);

		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		if(formType.equals("employee"))
		{
		  return new ModelAndView(new RedirectView("qualificationsTransactionView.html?employeeId="+employeeId+"&formType="+formType));
		}
		
		return new ModelAndView(new RedirectView("qualificationsTransactionView.html?formType="+formType));
	}
	

}
