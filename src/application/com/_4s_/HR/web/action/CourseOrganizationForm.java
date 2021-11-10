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

import com._4s_.HR.model.HRCourseOrganization;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;



public class CourseOrganizationForm  extends  BaseSimpleFormController{
		
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
			String courseOrganizationId=request.getParameter("courseOrganizationId");
			log.debug("courseOrganizationId"+courseOrganizationId);
			HRCourseOrganization courseOrganization=null;
			if(courseOrganizationId==null || courseOrganizationId.equals(""))
			{
				courseOrganization=new HRCourseOrganization();
			}
			
			else
			{
				courseOrganization=(HRCourseOrganization)hrManager.getObject(HRCourseOrganization.class, new Long(courseOrganizationId));
			}
			log.debug("courseOrganization"+courseOrganization);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		   return courseOrganization;
		}
	//**************************************** referenceData ***********************************************\\
		protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Map model=new HashMap();
			String courseOrganizationId=request.getParameter("courseOrganizationId");
			log.debug("courseOrganizationId"+courseOrganizationId);
			model.put("courseOrganizationId",courseOrganizationId);
			List courseOrganizationsList=hrManager.getObjects(HRCourseOrganization.class);
			model.put("courseOrganizationsList", courseOrganizationsList);
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
			HRCourseOrganization courseOrganization=(HRCourseOrganization)command;
			if(errors.getErrorCount()==0)
			{
				if(courseOrganization.getDircorse().length()<3)
				{
					errors.rejectValue("dircorse", "hr.error.codeMustBeEqual","codeMustBeEqual");
				}
			}
			
			if(errors.getErrorCount()==0)
			{
				HRCourseOrganization tit=(HRCourseOrganization)hrManager.getObjectByParameter(HRCourseOrganization.class,"dircorse", courseOrganization.getDircorse());
				if(tit!=null && (!tit.getId().equals(courseOrganization.getId())))
						{
					       errors.rejectValue("dircorse", "hr.error.codeExists","code exists");
						}
			}
			
			
			if(errors.getErrorCount()==0)
			{
			
				if(courseOrganization.getName()==null || courseOrganization.getName().equals(""))
				{
					errors.rejectValue("name", "commons.errors.requiredFields");
				}
				
				/*else if(courseOrganization.getName()!=null && !courseOrganization.getName().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());
		

					if(!courseOrganization.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

					errors.reject("hr.errors.invalidArabicName");

					}

					}*/
			}
			
			/*if(errors.getErrorCount()==0)
			{
			
				if(courseOrganization.getEname()==null || courseOrganization.getEname().equals(""))
				{
					errors.rejectValue("ename", "commons.errors.requiredFields");
				}
				
				else if(courseOrganization.getEname()!=null && !courseOrganization.getEname().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());

					

					

					if(!courseOrganization.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

						errors.reject("hr.errors.invalidEnglishName");

					}

					}*/


					

			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
		
		//**************************************** onSubmit ***********************************************\\	
		public ModelAndView onSubmit(HttpServletRequest request,
				HttpServletResponse response, Object command, BindException errors)throws Exception 
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			HRCourseOrganization courseOrganization=(HRCourseOrganization)command;
			log.debug("courseOrganization.getId()__________>>>>>>>>>>>>>>>"+courseOrganization.getId());
			
			hrManager.saveObject(courseOrganization);
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			return new ModelAndView(new RedirectView("courseOrganizationsView.html"));
		}
		

	}







