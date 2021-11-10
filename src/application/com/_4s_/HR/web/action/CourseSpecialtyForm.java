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

import com._4s_.HR.model.HRCourseSpecialty;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;


public class CourseSpecialtyForm  extends  BaseSimpleFormController{
		
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
			String courseSpecialtyId=request.getParameter("courseSpecialtyId");
			log.debug("courseSpecialtyId"+courseSpecialtyId);
			HRCourseSpecialty courseSpecialty=null;
			if(courseSpecialtyId==null || courseSpecialtyId.equals(""))
			{
				courseSpecialty=new HRCourseSpecialty();
			}
			
			else
			{
				courseSpecialty=(HRCourseSpecialty)hrManager.getObject(HRCourseSpecialty.class, new Long(courseSpecialtyId));
			}
			log.debug("courseSpecialty"+courseSpecialty);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		   return courseSpecialty;
		}
	//**************************************** referenceData ***********************************************\\
		protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Map model=new HashMap();
			String courseSpecialtyId=request.getParameter("courseSpecialtyId");
			log.debug("courseSpecialtyId"+courseSpecialtyId);
			model.put("courseSpecialtyId",courseSpecialtyId);
			List courseSpecialtysList=hrManager.getObjects(HRCourseSpecialty.class);
			model.put("courseSpecialtysList", courseSpecialtysList);
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
			HRCourseSpecialty courseSpecialty=(HRCourseSpecialty)command;
			if(errors.getErrorCount()==0)
			{
				if(courseSpecialty.getSpecial().length()<3)
				{
					errors.rejectValue("special", "hr.error.codeMustBeEqual","codeMustBeEqual");
				}
			}
			
			if(errors.getErrorCount()==0)
			{
				HRCourseSpecialty tit=(HRCourseSpecialty)hrManager.getObjectByParameter(HRCourseSpecialty.class,"special", courseSpecialty.getSpecial());
				if(tit!=null && (!tit.getId().equals(courseSpecialty.getId())))
						{
					       errors.rejectValue("special", "hr.error.codeExists","code exists");
						}
			}
			
			
			if(errors.getErrorCount()==0)
			{
			
				if(courseSpecialty.getName()==null || courseSpecialty.getName().equals(""))
				{
					errors.rejectValue("name", "commons.errors.requiredFields");
				}
				
				else if(courseSpecialty.getName()!=null && !courseSpecialty.getName().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());
		

					/*if(!courseSpecialty.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

					errors.reject("hr.errors.invalidArabicName");

					}

					}*/
			}
			
			/*if(errors.getErrorCount()==0)
			{
			
				if(courseSpecialty.getEname()==null || courseSpecialty.getEname().equals(""))
				{
					errors.rejectValue("ename", "commons.errors.requiredFields");
				}
				
				else if(courseSpecialty.getEname()!=null && !courseSpecialty.getEname().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());

					

					

					if(!courseSpecialty.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

						errors.reject("hr.errors.invalidEnglishName");

					}*/

					}


					

			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
		
		//**************************************** onSubmit ***********************************************\\	
		public ModelAndView onSubmit(HttpServletRequest request,
				HttpServletResponse response, Object command, BindException errors)throws Exception 
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			HRCourseSpecialty courseSpecialty=(HRCourseSpecialty)command;
			log.debug("courseSpecialty.getId()__________>>>>>>>>>>>>>>>"+courseSpecialty.getId());
			
			hrManager.saveObject(courseSpecialty);
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			return new ModelAndView(new RedirectView("courseSpecialtiesView.html"));
		}
		

	}






