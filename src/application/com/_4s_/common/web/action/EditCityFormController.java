/**
 * 
 */
package com._4s_.common.web.action;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.City;
import com._4s_.common.model.Country;
import com._4s_.common.web.binders.BaseBinder;
import com._4s_.common.web.binders.CountryBinder;

/**
 * @author mragab
 *
 */
@Controller
@RequestMapping("/commonAdminEditCity.html")
public class EditCityFormController extends BaseSimpleFormController {
	
//	@Autowired
	@Qualifier("countryBinder")
	private BaseBinder countryBinder;
	
	
	
	public BaseBinder getCountryBinder() {
		return countryBinder;
	}
	public void setCountryBinder(BaseBinder countryBinder) {
		this.countryBinder = countryBinder;
	}
	@Override
	public void initBinder(HttpServletRequest request,WebDataBinder binder) {

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Starting init binder: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		super.initBinder(request,binder);
		binder.registerCustomEditor(Country.class, countryBinder);
	}
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response,
			Object command, BindException errors)
	throws Exception{
		
		log.debug("Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		City city = (City)command;
		if (city.getId()==null) {
			city.getCountry().getCities().add(city);
		} else {
			// dont know what to do
			// I think we need to disconnect the old customer 
			// if customer is modified, and attache the new customer object (spring does the last part)
		}

		String deleteButton = request.getParameter("delete");
		String saveButton = request.getParameter("save"); 
		
		if ((deleteButton!=null)&&(deleteButton.length()>0)) {
			city.getCountry().getCities().remove(city);
			log.debug("deleting object :"+city);
			baseManager.removeObject(city);
		
		} else if ((saveButton!=null)&&(saveButton.length()>0)) {
			String isDefault = request.getParameter("default");
			if (isDefault != null && isDefault.equals("true")){
				City defaultCity = (City)baseManager.getDefaultObjectByParameter(City.class,"country",city.getCountry().getId());
				log.debug(">>>>>>>>>>>>>>>>>>>defaultCity "+defaultCity);
				if (defaultCity != null){
					defaultCity.setIsDefault(new Boolean(false));
				}
				city.setIsDefault(new Boolean(true));
			}else{
				city.setIsDefault(new Boolean(false));
			}
			baseManager.saveObject(city);
			log.debug("saving object :"+city);
		
		}

		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit");
		
		return new ModelAndView(new RedirectView("commonAdminCountryCities.html"),"countryId",city.getCountry().getId());
	
	}

	protected Object formBackingObject (HttpServletRequest request)
	throws ServletException{
		
		log.debug("Start formBackingObject >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		City city = new City();
		Country country = new Country();
		
		String cityId = request.getParameter("cityId");
		log.debug(">>>>>>>>>>> city Id :" + cityId);

		String countryId = request.getParameter("countryId");
		log.debug(">>>>>>>>>>> country Id :" + countryId);

		if ((cityId!=null)&&(cityId.length()>0)) {
			Object obj = baseManager.getObject(City.class,new Long(cityId));
			if (obj!=null) {
				city = (City)obj;
			} else {
				log.warn("!!! No object found for cityId:"+cityId);
			}
		} else { 
			if ((countryId!=null)&&(countryId.length()>0)) {
				Object obj = baseManager.getObject(Country.class,new Long(countryId));
				if (obj!=null) {
					country = (Country)obj;
				} else {
					log.warn("!!! No object found for countryId:"+countryId);
				}
			}
			city.setCountry(country);
		}
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< Ending FormBackingObject");
		
		return city;
	}
	
//	protected Map referenceData(HttpServletRequest request)
//    throws ServletException {
//		log.debug("Start referenceData >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		
//		Map model = new HashMap();
//		model.put("countries",baseManager.getObjects(Country.class));
//		
//		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< Ending referenceData");
//		
//		return model;
//		
//    }

}
