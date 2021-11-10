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

import com._4s_.HR.model.HRCourse;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class CourseForm extends  BaseSimpleFormController{
	
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
		String courseId=request.getParameter("courseId");
		log.debug("courseId"+courseId);
		HRCourse course=null;
		if(courseId==null || courseId.equals(""))
		{
			course=new HRCourse();
		}
		
		else
		{
			course=(HRCourse)hrManager.getObject(HRCourse.class, new Long(courseId));
		}
		log.debug("course"+course);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return course;
	}
//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		String courseId=request.getParameter("courseId");
		log.debug("courseId"+courseId);
		model.put("courseId",courseId);
		List coursesList=hrManager.getObjects(HRCourse.class);
		model.put("coursesList", coursesList);
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
		HRCourse course=(HRCourse)command;
		if(errors.getErrorCount()==0)
		{
			if(course.getCourse().length()<3)
			{
				errors.rejectValue("course", "hr.error.codeMustBeEqual","codeMustBeEqual");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			HRCourse tit=(HRCourse)hrManager.getObjectByParameter(HRCourse.class,"course", course.getCourse());
			if(tit!=null && (!tit.getId().equals(course.getId())))
					{
				       errors.rejectValue("course", "hr.error.codeExists","code exists");
					}
		}
		
		
		if(errors.getErrorCount()==0)
		{
		
			if(course.getName()==null || course.getName().equals(""))
			{
				errors.rejectValue("name", "commons.errors.requiredFields");
			}
			
			/*else if(course.getName()!=null && !course.getName().equals(""))
			{
				log.debug("errors.getErrorCount() " + errors.getErrorCount());
	

				if(!course.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

				errors.reject("hr.errors.invalidArabicName");

				}

				}*/
		}
		
		/*if(errors.getErrorCount()==0)
		{
		
			if(course.getEname()==null || course.getEname().equals(""))
			{
				errors.rejectValue("ename", "commons.errors.requiredFields");
			}
			
			else if(course.getEname()!=null && !course.getEname().equals(""))
			{
				log.debug("errors.getErrorCount() " + errors.getErrorCount());

				

				

				if(!course.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

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
		HRCourse course=(HRCourse)command;
		log.debug("course.getId()__________>>>>>>>>>>>>>>>"+course.getId());
		
		hrManager.saveObject(course);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("coursesView.html"));
	}
	

}






