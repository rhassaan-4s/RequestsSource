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

import com._4s_.HR.model.Sector;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class SectorForm extends  BaseSimpleFormController{
	 

			
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
					String sectorId=request.getParameter("sectorId");
					log.debug("sectorId"+sectorId);
					Sector sector=null;
					if(sectorId==null || sectorId.equals(""))
					{
						sector=new Sector();
					}
					
					else
					{
						sector=(Sector)hrManager.getObject(Sector.class, new Long(sectorId));
					}
					log.debug("sector"+sector);
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				   return sector;
				}
			//**************************************** referenceData ***********************************************\\
				protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
				{
					log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
					Map model=new HashMap();
					String sectorId=request.getParameter("sectorId");
					log.debug("sectorId"+sectorId);
					model.put("sectorId",sectorId);
					List sectorsList=hrManager.getObjects(Sector.class);
					model.put("sectorsList", sectorsList);
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
					Sector sector=(Sector)command;
					if(errors.getErrorCount()==0)
					{
						if(sector.getSector().length()<3)
						{
							errors.rejectValue("sector", "hr.error.codeMustBeEqual","codeMustBeEqual");
						}
					}
					
					if(errors.getErrorCount()==0)
					{
						Sector tit=(Sector)hrManager.getObjectByParameter(Sector.class,"sector", sector.getSector());
						if(tit!=null && (!tit.getId().equals(sector.getId())))
								{
							       errors.rejectValue("sector", "hr.error.codeExists","code exists");
								}
					}
					
					
					if(errors.getErrorCount()==0)
					{
					
						if(sector.getName()==null || sector.getName().equals(""))
						{
							errors.rejectValue("name", "commons.errors.requiredFields");
						}
						
					/*	else if(sector.getName()!=null && !sector.getName().equals(""))
						{
							log.debug("errors.getErrorCount() " + errors.getErrorCount());
				

							if(!sector.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

							errors.reject("hr.errors.invalidArabicName");

							}

							}*/
					}
					
					/*if(errors.getErrorCount()==0)
					{
					
						if(sector.getEname()==null || sector.getEname().equals(""))
						{
							errors.rejectValue("ename", "commons.errors.requiredFields");
						}
						
						else if(sector.getEname()!=null && !sector.getEname().equals(""))
						{
							log.debug("errors.getErrorCount() " + errors.getErrorCount());

							

							

							if(!sector.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

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
					Sector sector=(Sector)command;
					log.debug("sector.getId()__________>>>>>>>>>>>>>>>"+sector.getId());
					
					hrManager.saveObject(sector);
					log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					return new ModelAndView(new RedirectView(getSuccessView()));
				}
				

			}





