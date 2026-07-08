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

import com._4s_.HR.model.HRSchool;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;



public class SchoolForm  extends  BaseSimpleFormController{
	
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
			String schoolId=request.getParameter("schoolId");
			log.debug("schoolId"+schoolId);
			HRSchool school=null;
			if(schoolId==null || schoolId.equals(""))
			{
				school=new HRSchool();
			}
			
			else
			{
				school=(HRSchool)hrManager.getObject(HRSchool.class, new Long(schoolId));
			}
			log.debug("school"+school);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			model.addAttribute(school);
		   return "schoolForm";
		}
	//**************************************** referenceData ***********************************************\\
		@ModelAttribute("model")	
		public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Map model=new HashMap();
			String schoolId=request.getParameter("schoolId");
			log.debug("schoolId"+schoolId);
			model.put("schoolId",schoolId);
			List schoolsList=hrManager.getObjects(HRSchool.class);
			model.put("schoolsList", schoolsList);
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
			HRSchool school=(HRSchool)command;
			if(errors.getErrorCount()==0)
			{
				if(school.getSchool().length()<3)
				{
					errors.rejectValue("school", "hr.error.codeMustBeEqual","codeMustBeEqual");
				}
			}
			
			if(errors.getErrorCount()==0)
			{
				HRSchool tit=(HRSchool)hrManager.getObjectByParameter(HRSchool.class,"school", school.getSchool());
				if(tit!=null && (!tit.getId().equals(school.getId())))
						{
					       errors.rejectValue("school", "hr.error.codeExists","code exists");
						}
			}
			
			
			if(errors.getErrorCount()==0)
			{
			
				if(school.getName()==null || school.getName().equals(""))
				{
					errors.rejectValue("name", "commons.errors.requiredFields");
				}
				
				/*else if(school.getName()!=null && !school.getName().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());
		

					if(!school.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

					errors.reject("hr.errors.invalidArabicName");

					}

					}*/
			}
			
			/*if(errors.getErrorCount()==0)
			{
			
				if(school.getEname()==null || school.getEname().equals(""))
				{
					errors.rejectValue("ename", "commons.errors.requiredFields");
				}
				
				else if(school.getEname()!=null && !school.getEname().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());

					

					

					if(!school.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

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
			HRSchool school=(HRSchool)command;
			log.debug("school.getId()__________>>>>>>>>>>>>>>>"+school.getId());
			
			hrManager.saveObject(school);
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			return new ModelAndView(new RedirectView("schoolsView.html"));
		}
		

	}






