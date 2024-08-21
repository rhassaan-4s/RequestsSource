package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HRDocuments;
import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HREmployeeDocuments;
import com._4s_.HR.model.HREmployeeRelative;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.LoginUsersRequests;

public class EmployeeDocumentsForm extends BaseSimpleFormController {
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
		HREmployeeDocuments hrEmployeeDocuments=null;
		String empDocumentsId=request.getParameter("empDocumentsId");
		log.debug("empDocumentsId>>>>>>>>>>>>"+empDocumentsId);
		//in case of editing object
		if(empDocumentsId!=null && !empDocumentsId.equals(""))
		{
			hrEmployeeDocuments=(HREmployeeDocuments)hrManager.getObject(HREmployeeDocuments.class, new Long(empDocumentsId));
		}
		
		else
		{
			
		       hrEmployeeDocuments=new HREmployeeDocuments();
		       
		}
    
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute("hrEmployeeDocuments",hrEmployeeDocuments);
	   return "employeeDocumentsForm";
	}
//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,@ModelAttribute HREmployeeDocuments command,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 Map model=new HashMap();
		 HREmployeeDocuments hrEmployeeDocuments=(HREmployeeDocuments)command;
		
		 String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		  model.put("employeeId", employeeId);
		 	String empDocumentsId=request.getParameter("empDocumentsId");
		     model.put("empDocumentsId", empDocumentsId);
		     
		     String formType=request.getParameter("formType");
		     log.debug("formType>>>>>>>>>>>>>>>"+formType);
		      model.put("formType", formType);
		      
		      if(employeeId!=null && !employeeId.equals(""))
		      {
		    	  HREmployee employee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
		    	  log.debug("list.size????????????????????????>>>>>>>>>>>"+hrManager.getObjectsByParameter(HREmployeeRelative.class,"employee",employee).size());
		    	  model.put("empRelativeList",hrManager.getObjectsByParameter(HREmployeeRelative.class,"employee",employee));  
		      }
		        model.put("documentsList",hrManager.getObjects(HRDocuments.class));
		

				
		
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		return model;
	}
	
	//**************************************** onBind ***********************************************\\	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HREmployeeDocuments hrEmployeedDocuments=(HREmployeeDocuments)command;
		
//		 attach document photo
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		
		if (req.getFile("employeeDocumentFile")!=null  &&  !req.getFile("employeeDocumentFile").isEmpty())
		{
			MultipartFile newFile =  req.getFile("employeeDocumentFile");
//			hrEmployeedDocuments.setDocumentPhoto(Hibernate.createBlob(newFile.getInputStream()));
			hrEmployeedDocuments.setDocumentPhotoName(newFile.getOriginalFilename());
			log.debug(">>>>>>>>>>>>>>photo name: "+hrEmployeedDocuments.getDocumentPhotoName());
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
		HREmployeeDocuments hrEmployeeDocuments=(HREmployeeDocuments)command;
		
	     String formType=request.getParameter("formType");
	     log.debug("formType>>>>>>>>>>>>>>>"+formType);
	     String employeeId=request.getParameter("employeeId");
		  log.debug("employeeId>>>>>>>>>>>>>>>"+employeeId);
		 
		
		  if(employeeId!=null && !employeeId.equals(""))
		  {
		  HREmployee employee=(HREmployee)hrManager.getObject(HREmployee.class, new Long(employeeId));
		  hrEmployeeDocuments.setEmployee(employee);
		  }
		  
		
		    
			hrManager.saveObject(hrEmployeeDocuments);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		if(formType.equals("employee"))
		{
		  return new ModelAndView(new RedirectView("employeeDocumentsView.html?employeeId="+employeeId+"&formType="+formType));
		}
		
		return new ModelAndView(new RedirectView("employeeDocumentsView.html?formType="+formType));
	}
	




}
