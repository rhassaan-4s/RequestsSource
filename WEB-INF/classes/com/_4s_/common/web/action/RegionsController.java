package com._4s_.common.web.action;

import com._4s_.common.model.Region;

public class RegionsController extends BaseController 
{
	public RegionsController()
	{
		super();
		setCommandClass(Region.class);
		setListViewName("commonAdminRegions");
	}
}
