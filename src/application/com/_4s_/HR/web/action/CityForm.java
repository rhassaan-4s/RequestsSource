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

import com._4s_.HR.model.HRCity;
import com._4s_.HR.model.HRCountry;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class CityForm extends  BaseSimpleFormController{
	
	
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
			String hrCityId=request.getParameter("hrCityId");
			log.debug("hrCityId"+hrCityId);
			HRCity hrCity=null;
			if(hrCityId==null || hrCityId.equals(""))
			{
				hrCity=new HRCity();
			}
			
			else
			{
				hrCity=(HRCity)hrManager.getObject(HRCity.class, new Long(hrCityId));
			}
			log.debug("hrCity"+hrCity);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		   return hrCity;
		}
	//**************************************** referenceData ***********************************************\\
		protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Map model=new HashMap();
			String hrCityId=request.getParameter("hrCityId");
			log.debug("hrCityId"+hrCityId);
			model.put("hrCityId",hrCityId);
			List hrCountriesList=hrManager.getObjects(HRCountry.class);
			model.put("countryList", hrCountriesList);
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
			HRCity hrCity=(HRCity)command;
			String cou=request.getParameter("hrCountry");
			log.debug("cou??????????????"+cou);
			if(errors.getErrorCount()==0)
			{
				if(hrCity.getCity().length()<4)
				{
					errors.rejectValue("city", "hr.errors.codeLargerThanExpected","codeMustBeEqual");
				}
			}
			
			if(errors.getErrorCount()==0)
			{
				HRCity tit=(HRCity)hrManager.getObjectByParameter(HRCity.class,"city", hrCity.getCity());
				if(tit!=null && (!tit.getId().equals(hrCity.getId())))
						{
					       errors.rejectValue("hrCity", "hr.error.codeExists","code exists");
						}
			}
			
			
			if(errors.getErrorCount()==0)
			{
			
				if(hrCity.getName()==null || hrCity.getName().equals(""))
				{
					errors.rejectValue("name", "commons.errors.requiredFields");
				}
				
			/*	else if(hrCity.getName()!=null && !hrCity.getName().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());
		

					if(!hrCity.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

					errors.reject("hr.errors.invalidArabicName");

					}

					}
	*/		}
			
			/*if(errors.getErrorCount()==0)
			{
			
				if(hrCity.getEname()==null || hrCity.getEname().equals(""))
				{
					errors.rejectValue("ename", "commons.errors.requiredFields");
				}
				
				else if(hrCity.getEname()!=null && !hrCity.getEname().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());

					

					

					if(!hrCity.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

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
			HRCity hrCity=(HRCity)command;
			log.debug("hrCity.getId()__________>>>>>>>>>>>>>>>"+hrCity.getId());
			hrManager.saveObject(hrCity);
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			return new ModelAndView(new RedirectView("citiesView.html"));
		}
		

	}


