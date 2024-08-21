package com._4s_.HR.web.action;

	import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HRCourseResult;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;


public class CourseResultForm  extends  BaseSimpleFormController{
		
		private HRManager hrManager;
		
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
			String courseResultId=request.getParameter("courseResultId");
			log.debug("courseResultId"+courseResultId);
			HRCourseResult courseResult=null;
			if(courseResultId==null || courseResultId.equals(""))
			{
				courseResult=new HRCourseResult();
			}
			
			else
			{
				courseResult=(HRCourseResult)hrManager.getObject(HRCourseResult.class, new Long(courseResultId));
			}
			log.debug("courseResult"+courseResult);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			model.addAttribute(courseResult);
		   return "courseResultForm";
		}
	//**************************************** referenceData ***********************************************\\
		@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Map model=new HashMap();
			String courseResultId=request.getParameter("courseResultId");
			log.debug("courseResultId"+courseResultId);
			model.put("courseResultId",courseResultId);
			List courseResultsList=hrManager.getObjects(HRCourseResult.class);
			model.put("courseResultsList", courseResultsList);
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
			HRCourseResult courseResult=(HRCourseResult)command;
			if(errors.getErrorCount()==0)
			{
				if(courseResult.getResult().length()<3)
				{
					errors.rejectValue("result", "hr.error.codeMustBeEqual","codeMustBeEqual");
				}
			}
			
			if(errors.getErrorCount()==0)
			{
				HRCourseResult tit=(HRCourseResult)hrManager.getObjectByParameter(HRCourseResult.class,"result", courseResult.getResult());
				if(tit!=null && (!tit.getId().equals(courseResult.getId())))
						{
					       errors.rejectValue("result", "hr.error.codeExists","code exists");
						}
			}
			
			
			if(errors.getErrorCount()==0)
			{
			
				if(courseResult.getName()==null || courseResult.getName().equals(""))
				{
					errors.rejectValue("name", "commons.errors.requiredFields");
				}
				
				/*else if(courseResult.getName()!=null && !courseResult.getName().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());
		

					if(!courseResult.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

					errors.reject("hr.errors.invalidArabicName");

					}

					}*/
			}
			
			if(errors.getErrorCount()==0)
			{
			
				if(courseResult.getEname()==null || courseResult.getEname().equals(""))
				{
					errors.rejectValue("ename", "commons.errors.requiredFields");
				}
				
				/*else if(courseResult.getEname()!=null && !courseResult.getEname().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());

					

					

					if(!courseResult.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

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
			HRCourseResult courseResult=(HRCourseResult)command;
			log.debug("courseResult.getId()__________>>>>>>>>>>>>>>>"+courseResult.getId());
		
			hrManager.saveObject(courseResult);
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			return new ModelAndView(new RedirectView("courseResultsView.html"));
		}
		

	}






