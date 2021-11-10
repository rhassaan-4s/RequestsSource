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

import com._4s_.HR.model.Center;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class CenterForm extends  BaseSimpleFormController{

		
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
				String centerId=request.getParameter("centerId");
				log.debug("centerId"+centerId);
				Center center=null;
				if(centerId==null || centerId.equals(""))
				{
					center=new Center();
				}
				
				else
				{
					center=(Center)hrManager.getObject(Center.class, new Long(centerId));
				}
				log.debug("center"+center);
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			   return center;
			}
		//**************************************** referenceData ***********************************************\\
			protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
			{
				log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				Map model=new HashMap();
				String centerId=request.getParameter("centerId");
				log.debug("centerId"+centerId);
				model.put("centerId",centerId);
				List centersList=hrManager.getObjects(Center.class);
				model.put("centersList", centersList);
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
				Center center=(Center)command;
				if(errors.getErrorCount()==0)
				{
					if(center.getCenter().length()<4)
					{
						errors.rejectValue("center", "hr.errors.codeLargerThanExpected","codeMustBeEqual");
					}
				}
				
				if(errors.getErrorCount()==0)
				{
					Center tit=(Center)hrManager.getObjectByParameter(Center.class,"center", center.getCenter());
					if(tit!=null && (!tit.getId().equals(center.getId())))
							{
						       errors.rejectValue("center", "hr.error.codeExists","code exists");
							}
				}
				
				
				if(errors.getErrorCount()==0)
				{
				
					if(center.getName()==null || center.getName().equals(""))
					{
						errors.rejectValue("name", "commons.errors.requiredFields");
					}
					
				/*	else if(center.getName()!=null && !center.getName().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());
			

						if(!center.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

						errors.reject("hr.errors.invalidArabicName");

						}

						}*/
				}
				
				/*if(errors.getErrorCount()==0)
				{
				
					if(center.getEname()==null || center.getEname().equals(""))
					{
						errors.rejectValue("ename", "commons.errors.requiredFields");
					}
					
					else if(center.getEname()!=null && !center.getEname().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());

						

						

						if(!center.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

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
				Center center=(Center)command;
				log.debug("center.getId()__________>>>>>>>>>>>>>>>"+center.getId());
				
				hrManager.saveObject(center);
				log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				return new ModelAndView(new RedirectView("centersView.html"));
			}
			

		}





