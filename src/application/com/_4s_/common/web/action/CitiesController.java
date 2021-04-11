package com._4s_.common.web.action;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.City;
import com._4s_.common.model.Country;

public class CitiesController extends BaseController {

	public CitiesController() {
		super();
		setCommandClass(Country.class);
		setListViewName("CommonAdminCountryCities");
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("entering 'handleRequest' method...");
		
		String countryId = request.getParameter("countryId");
		
		log.debug("countryId:"+countryId);
		if ((countryId!=null)&&(countryId.length()>0)) {
			Country country = (Country)baseManager.getObject(commandClass , new Long(countryId));
			
			if (log.isDebugEnabled()) {
				log.debug("Country:"+country.toString());
				City city;
				Iterator itr = country.getCities().iterator();
				while(itr.hasNext()) {
					log.debug("City:"+itr.next().toString());
				}
			}
			
			return new ModelAndView(listViewName,"results",country);
		} else {
			
			return new ModelAndView(listViewName,"results",new Country());
		}
	}

}
