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

import com._4s_.HR.model.HRNationality;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;


public class NationalityForm  extends  BaseSimpleFormController{
		
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
				String nationalityId=request.getParameter("nationalityId");
				log.debug("nationalityId"+nationalityId);
				HRNationality nationality=null;
				if(nationalityId==null || nationalityId.equals(""))
				{
					nationality=new HRNationality();
				}
				
				else
				{
					nationality=(HRNationality)hrManager.getObject(HRNationality.class, new Long(nationalityId));
				}
				log.debug("nationality"+nationality);
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			   return nationality;
			}
		//**************************************** referenceData ***********************************************\\
			protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
			{
				log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				Map model=new HashMap();
				String nationalityId=request.getParameter("nationalityId");
				log.debug("nationalityId"+nationalityId);
				model.put("nationalityId",nationalityId);
				List nationalitysList=hrManager.getObjects(HRNationality.class);
				model.put("nationalitysList", nationalitysList);
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
				HRNationality nationality=(HRNationality)command;
				if(errors.getErrorCount()==0)
				{
					if(nationality.getNationality_code().length()<5)
					{
						errors.rejectValue("nationality_code", "hr.error.codeMustBeEqual","codeMustBeEqual");
					}
				}
				
				if(errors.getErrorCount()==0)
				{
					HRNationality tit=(HRNationality)hrManager.getObjectByParameter(HRNationality.class,"nationality_code", nationality.getNationality_code());
					if(tit!=null && (!tit.getId().equals(nationality.getId())))
							{
						       errors.rejectValue("nationality_code", "hr.error.codeExists","code exists");
							}
				}
				
				
				if(errors.getErrorCount()==0)
				{
				
					if(nationality.getName()==null || nationality.getName().equals(""))
					{
						errors.rejectValue("name", "commons.errors.requiredFields");
					}
					
					/*else if(nationality.getName()!=null && !nationality.getName().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());
			

						if(!nationality.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

						errors.reject("hr.errors.invalidArabicName");

						}

						}*/
				}
				
			/*	if(errors.getErrorCount()==0)
				{
				
					if(nationality.getEname()==null || nationality.getEname().equals(""))
					{
						errors.rejectValue("ename", "commons.errors.requiredFields");
					}
					
					else if(nationality.getEname()!=null && !nationality.getEname().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());

						

						

						if(!nationality.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

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
				HRNationality nationality=(HRNationality)command;
				log.debug("nationality.getId()__________>>>>>>>>>>>>>>>"+nationality.getId());
				
				hrManager.saveObject(nationality);
				log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				return new ModelAndView(new RedirectView("nationalitiesView.html"));
			}
			

		}







