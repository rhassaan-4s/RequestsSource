/**
 * 
 */
package com._4s_.common.web.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Country;

/**
 * @author mragab
 *
 */
@Controller
@RequestMapping("/commonAdminEditCountry.html")
public class EditCountryFormController extends BaseSimpleFormController {
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response,
			Object command, BindException errors)
	throws Exception{
		
		log.debug("Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		String deleteButton = request.getParameter("delete");
		String saveButton = request.getParameter("save"); 
		
		if ((deleteButton!=null)&&(deleteButton.length()>0)) {

			log.debug("deleting object :"+command);
			baseManager.removeObject(command);
		
		} else if ((saveButton!=null)&&(saveButton.length()>0)) {
			Country country = (Country)command;
			String isDefault = request.getParameter("default");
			if (isDefault != null && isDefault.equals("true")){
				Country defaultCountry = (Country)baseManager.getDefaultObject(Country.class);
				log.debug(">>>>>>>>>>>>>>>>>>>defaultCountry "+defaultCountry);
				if (defaultCountry != null){
					defaultCountry.setIsDefault(new Boolean(false));
				}
				country.setIsDefault(new Boolean(true));
			}else{
				country.setIsDefault(new Boolean(false));
			}
			baseManager.saveObject(country);
			log.debug("saving object :"+country);
		
		}
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit");
		
		return new ModelAndView(new RedirectView("commonAdminCountries.html"));
	
	}

	protected Object formBackingObject (HttpServletRequest request)
	throws ServletException{
		
		log.debug("Start formBackingObject >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		Country country = new Country();
	
		String countryId = request.getParameter("countryId");
		log.debug(">>>>>>>>>>> country Id :" + countryId);

		if ((countryId!=null)&&(countryId.length()>0)) {
			Object obj = baseManager.getObject(Country.class,new Long(countryId));
			if (obj!=null) {
				country = (Country)obj;
			} else {
				log.warn("!!! No object found for countryId:"+countryId);
			}
		}
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< Ending FormBackingObject");
		
		return country;
	}
}
