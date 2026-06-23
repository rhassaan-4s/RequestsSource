package com._4s_.requestsApproval.web.action;
	import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.DBConnections;
import com._4s_.requestsApproval.service.RequestsApprovalManager;


public class DBConnectionsForm extends  BaseSimpleFormController{
	
		private RequestsApprovalManager requestsApprovalManager;
	
		public RequestsApprovalManager getRequestsApprovalManager() {
			return requestsApprovalManager;
		}
		public void setRequestsApprovalManager(
				RequestsApprovalManager requestsApprovalManager) {
			this.requestsApprovalManager = requestsApprovalManager;
		}
		
		//**************************************** formBackingObject ***********************************************\\
		@RequestMapping(method = RequestMethod.GET)  public String initForm(ModelMap model,HttpServletRequest request){
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			String connectionId=request.getParameter("connectionId");
			log.debug("connectionId"+connectionId);
			DBConnections dbConnections=null;
			if(connectionId==null || connectionId.equals(""))
			{
				dbConnections=new DBConnections();
			}
			
			else
			{
				dbConnections=(DBConnections)requestsApprovalManager.getObject(DBConnections.class, new Long(connectionId));
			}
			log.debug("dbConnections"+dbConnections);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		   return dbConnections;
		}
	//**************************************** referenceData ***********************************************\\
		@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Map model=new HashMap();
			String connectionId=request.getParameter("connectionId");
			log.debug("connectionId"+connectionId);
			model.put("connectionId",connectionId);
			List connections=requestsApprovalManager.getObjects(DBConnections.class);
			model.put("connections", connections);
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
//			HRCountry country=(HRCountry)command;
//			if(errors.getErrorCount()==0)
//			{
//				if(country.getCountry().length()<3)
//				{
//					errors.rejectValue("country", "hr.error.codeMustBeEqual","codeMustBeEqual");
//				}
//			}
//			
//			if(errors.getErrorCount()==0)
//			{
//				HRCountry tit=(HRCountry)hrManager.getObjectByParameter(HRCountry.class,"country", country.getCountry());
//				if(tit!=null && (!tit.getId().equals(country.getId())))
//						{
//					       errors.rejectValue("country", "hr.error.codeExists","code exists");
//						}
//			}
//			
//			
//			if(errors.getErrorCount()==0)
//			{
//			
//				if(country.getName()==null || country.getName().equals(""))
//				{
//					errors.rejectValue("name", "commons.errors.requiredFields");
//				}
				
				/*else if(country.getName()!=null && !country.getName().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());
		

					if(!country.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

					errors.reject("hr.errors.invalidArabicName");

					}

					}*/
//			}
			
		/*	if(errors.getErrorCount()==0)
			{
			
				if(country.getEname()==null || country.getEname().equals(""))
				{
					errors.rejectValue("ename", "commons.errors.requiredFields");
				}
				
				else if(country.getEname()!=null && !country.getEname().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());

					

					

					if(!country.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

						errors.reject("hr.errors.invalidEnglishName");

					}

					}
*/

					

			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
		
		//**************************************** onSubmit ***********************************************\\	
		public ModelAndView onSubmit(HttpServletRequest request,
				HttpServletResponse response, Object command, BindException errors)throws Exception 
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			DBConnections dbConnections=(DBConnections)command;
			log.debug("dbConnections.getId()__________>>>>>>>>>>>>>>>"+dbConnections.getId());
			
			requestsApprovalManager.saveObject(dbConnections);
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			return new ModelAndView(new RedirectView("dbConnectionsForm.html"));
		}
		

	}



