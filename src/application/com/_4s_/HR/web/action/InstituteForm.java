package com._4s_.HR.web.action;
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

import com._4s_.HR.model.HRInstitute;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;


public class InstituteForm extends  BaseSimpleFormController{
	
		private HRManager hrManager;
		
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
			String instituteId=request.getParameter("instituteId");
			log.debug("instituteId"+instituteId);
			HRInstitute institute=null;
			if(instituteId==null || instituteId.equals(""))
			{
				institute=new HRInstitute();
			}
			
			else
			{
				institute=(HRInstitute)hrManager.getObject(HRInstitute.class, new Long(instituteId));
			}
			log.debug("institute"+institute);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		   return institute;
		}
	//**************************************** referenceData ***********************************************\\
		protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Map model=new HashMap();
			String instituteId=request.getParameter("instituteId");
			log.debug("instituteId"+instituteId);
			model.put("instituteId",instituteId);
			List institutesList=hrManager.getObjects(HRInstitute.class);
			model.put("institutesList", institutesList);
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
			HRInstitute institute=(HRInstitute)command;
			if(errors.getErrorCount()==0)
			{
				if(institute.getInstit().length()<3)
				{
					errors.rejectValue("instit", "hr.error.codeMustBeEqual","codeMustBeEqual");
				}
			}
			
			if(errors.getErrorCount()==0)
			{
				HRInstitute tit=(HRInstitute)hrManager.getObjectByParameter(HRInstitute.class,"instit", institute.getInstit());
				if(tit!=null && (!tit.getId().equals(institute.getId())))
						{
					       errors.rejectValue("instit", "hr.error.codeExists","code exists");
						}
			}
			
			
			if(errors.getErrorCount()==0)
			{
			
				if(institute.getName()==null || institute.getName().equals(""))
				{
					errors.rejectValue("name", "commons.errors.requiredFields");
				}
				
				/*else if(institute.getName()!=null && !institute.getName().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());
		

					if(!institute.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

					errors.reject("hr.errors.invalidArabicName");

					}

					}*/
			}
			
			/*if(errors.getErrorCount()==0)
			{
			
				if(institute.getEname()==null || institute.getEname().equals(""))
				{
					errors.rejectValue("ename", "commons.errors.requiredFields");
				}
				
				else if(institute.getEname()!=null && !institute.getEname().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());

					

					

					if(!institute.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

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
			HRInstitute institute=(HRInstitute)command;
			log.debug("institute.getId()__________>>>>>>>>>>>>>>>"+institute.getId());
			
			hrManager.saveObject(institute);
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			return new ModelAndView(new RedirectView("institutesView.html"));
		}
		

	}





