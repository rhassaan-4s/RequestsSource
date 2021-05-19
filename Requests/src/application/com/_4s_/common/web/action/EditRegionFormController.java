package com._4s_.common.web.action;

import java.util.ArrayList;
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

import com._4s_.common.model.City;
import com._4s_.common.model.Country;
import com._4s_.common.model.Region;
import com._4s_.common.service.CommonManager;

public class EditRegionFormController extends BaseSimpleFormController
{
	CommonManager commonManager=null;
	public CommonManager getCommonManager() {
		return commonManager;
	}

	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}

	protected Object formBackingObject (HttpServletRequest request)
	throws ServletException
	{
		
		log.debug("Start formBackingObject >>>>>>>>>>>>>>>>>>>>>>>>>>>>");		
		Region region = new Region();
	
		String regionId = request.getParameter("regionId");
		log.debug(">>>>>>>>>>> regionId :" + regionId);

		if ((regionId!=null)&&(regionId.length()>0)) 
		{
			Object obj = baseManager.getObject(Region.class,new Long(regionId));
			if (obj!=null) 
			{
				region = (Region)obj;
			}
			else
			{
				log.warn("!!! No object found for regionId:"+regionId);
			}
		}
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< Ending FormBackingObject");
		
		return region;
	}
	
	protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception
	{
		log.debug(">>>>>>>>>> Start of referenceData >>>>>>>>>>");
		Map model = new HashMap();
		Region region = (Region)command;
			
		model.put("countries",baseManager.getObjects(Country.class));
		
		String regionId = request.getParameter("regionId");
		/*if ((regionId!=null)&&(regionId.length()>0))
		{
			model.put("cities",commonManager.getCitiesByCountry(region.getCountry().getId()));
		}*/
		
		if(region.getCountry()!=null)
		{
			model.put("cities",commonManager.getCitiesByCountry(region.getCountry().getId()));
		}
		
		log.debug(">>>>>>>>>> End of referenceData >>>>>>>>>>>>");
		return model;
	}	
	
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>");
		Region region = (Region)command;
		
		if(errors.getErrorCount()==0)
		{
			if(region.getDescription()==null || region.getDescription().equals(""))
			{
				errors.reject("commons.errors.requiredFields");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			if(region.getCountry()==null || region.getCountry().equals(""))
			{
				errors.reject("commons.errors.requiredFields");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			String[] citiesString = request.getParameterValues("cities");
			if(citiesString!=null)
			{
				List cities=new ArrayList();
				for(int i=0;i<citiesString.length;i++)
				{
					cities.add(baseManager.getObject(City.class,new Long(citiesString[i])));
				}
				region.setCities(cities);
			}
			else
			{
				errors.reject("commons.errors.requiredFields");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			if(region.getCode()==null || region.getCode().equals(""))
			{
				errors.reject("commons.errors.requiredFields");
			}
		}
				
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>> Start of onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>");
		Region region = (Region)command;
		
		baseManager.saveObject(region);
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>> End of onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return new ModelAndView(new RedirectView(getSuccessView()));
	}


}
