package com._4s_.common.web.action;

import com._4s_.common.model.Country;

public class CountriesController extends BaseController {

	public CountriesController() {
		super();
		setCommandClass(Country.class);
		setListViewName("CommonAdminCountries");
	}

}
