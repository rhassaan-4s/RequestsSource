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

import com._4s_.HR.model.HRQualification;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;



public class QualificationForm   extends  BaseSimpleFormController{
		
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
				String qualificationId=request.getParameter("qualificationId");
				log.debug("qualificationId"+qualificationId);
				HRQualification qualification=null;
				if(qualificationId==null || qualificationId.equals(""))
				{
					qualification=new HRQualification();
				}
				
				else
				{
					qualification=(HRQualification)hrManager.getObject(HRQualification.class, new Long(qualificationId));
				}
				log.debug("qualification"+qualification);
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				model.addAttribute(qualification);
			   return "qualificationForm";
			}
		//**************************************** referenceData ***********************************************\\
			@ModelAttribute("model")	
			public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
			{
				log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				Map model=new HashMap();
				String qualificationId=request.getParameter("qualificationId");
				log.debug("qualificationId"+qualificationId);
				model.put("qualificationId",qualificationId);
				List qualificationsList=hrManager.getObjects(HRQualification.class);
				model.put("qualificationsList", qualificationsList);
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
				HRQualification qualification=(HRQualification)command;
				if(errors.getErrorCount()==0)
				{
					if(qualification.getQual().length()<3)
					{
						errors.rejectValue("qual", "hr.error.codeMustBeEqual","codeMustBeEqual");
					}
				}
				
				if(errors.getErrorCount()==0)
				{
					HRQualification tit=(HRQualification)hrManager.getObjectByParameter(HRQualification.class,"qual", qualification.getQual());
					if(tit!=null && (!tit.getId().equals(qualification.getId())))
							{
						       errors.rejectValue("qual", "hr.error.codeExists","code exists");
							}
				}
				
				
				if(errors.getErrorCount()==0)
				{
				
					if(qualification.getName()==null || qualification.getName().equals(""))
					{
						errors.rejectValue("name", "commons.errors.requiredFields");
					}
					
					else if(qualification.getName()!=null && !qualification.getName().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());
			

						if(!qualification.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

						errors.reject("hr.errors.invalidArabicName");

						}

						}
				}
				
				if(errors.getErrorCount()==0)
				{
				
					if(qualification.getEname()==null || qualification.getEname().equals(""))
					{
						errors.rejectValue("ename", "commons.errors.requiredFields");
					}
					
					else if(qualification.getEname()!=null && !qualification.getEname().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());

						

						

						if(!qualification.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

							errors.reject("hr.errors.invalidEnglishName");

						}

						}


						

				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			}
			
			//**************************************** onSubmit ***********************************************\\	
			public ModelAndView onSubmit(HttpServletRequest request,
					HttpServletResponse response, Object command, BindException errors)throws Exception 
			{
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				HRQualification qualification=(HRQualification)command;
				log.debug("qualification.getId()__________>>>>>>>>>>>>>>>"+qualification.getId());
			
				hrManager.saveObject(qualification);
				log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				return new ModelAndView(new RedirectView("qualificationsView.html"));
			}
			

		}






