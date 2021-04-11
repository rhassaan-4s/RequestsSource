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

import com._4s_.HR.model.HRRegion;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class RegionForm extends  BaseSimpleFormController{
	

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
				String hrRegionId=request.getParameter("hrRegionId");
				log.debug("hrRegionId"+hrRegionId);
				HRRegion hrRegion=null;
				if(hrRegionId==null || hrRegionId.equals(""))
				{
					hrRegion=new HRRegion();
				}
				
				else
				{
					hrRegion=(HRRegion)hrManager.getObject(HRRegion.class, new Long(hrRegionId));
				}
				log.debug("hrRegion"+hrRegion);
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			   return hrRegion;
			}
		//**************************************** referenceData ***********************************************\\
			protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
			{
				log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				Map model=new HashMap();
				String hrRegionId=request.getParameter("hrRegionId");
				log.debug("hrRegionId"+hrRegionId);
				model.put("hrRegionId",hrRegionId);
				List hrRegionsList=hrManager.getObjects(HRRegion.class);
				model.put("hrRegionsList", hrRegionsList);
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
				HRRegion hrRegion=(HRRegion)command;
				if(errors.getErrorCount()==0)
				{
					if(hrRegion.getRegion().length()<3)
					{
						errors.rejectValue("region", "hr.error.codeMustBeEqual","code less than excepected");
					}
				}
				
				if(errors.getErrorCount()==0)
				{
					HRRegion tit=(HRRegion)hrManager.getObjectByParameter(HRRegion.class,"region", hrRegion.getRegion());
					if(tit!=null && (!tit.getId().equals(hrRegion.getId())))
							{
						       errors.rejectValue("region", "hr.error.codeExists","code exists");
							}
				}
				
				
				if(errors.getErrorCount()==0)
				{
				
					if(hrRegion.getName()==null || hrRegion.getName().equals(""))
					{
						errors.rejectValue("name", "commons.errors.requiredFields");
					}
					
					else if(hrRegion.getName()!=null && !hrRegion.getName().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());
			

						if(!hrRegion.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

						errors.reject("hr.errors.invalidArabicName");

						}

						}
				}
				
				if(errors.getErrorCount()==0)
				{
				
					if(hrRegion.getEname()==null || hrRegion.getEname().equals(""))
					{
						errors.rejectValue("ename", "commons.errors.requiredFields");
					}
					
					else if(hrRegion.getEname()!=null && !hrRegion.getEname().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());

						

						

						if(!hrRegion.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

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
				HRRegion hrRegion=(HRRegion)command;
				log.debug("hrRegion.getId()__________>>>>>>>>>>>>>>>"+hrRegion.getId());
				
				hrManager.saveObject(hrRegion);
				log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				return new ModelAndView(new RedirectView(getSuccessView()));
			}
			

		}




