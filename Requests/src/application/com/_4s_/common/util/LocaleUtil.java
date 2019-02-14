package com._4s_.common.util;

public class LocaleUtil {

	private String locale = new String("ar");

	static LocaleUtil localeUtil = new LocaleUtil();

	public LocaleUtil() {

	}

	public static LocaleUtil getInstance() {
		return localeUtil;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String local) {
		this.locale = local;
	}
}
