package com._4s_.i18n.service;

import com._4s_.i18n.model.MyLocale;

/**
 * @author mragab
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface LocaleSource {
	public MyLocale getPreferenceMyLocale();
	//public MyLocale getMyLocale();
	public MyLocale getDefaultMyLocale();
}
