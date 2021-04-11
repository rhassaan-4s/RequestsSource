package com._4s_.i18n.service;

public class NoValidLocaleException extends RuntimeException {
	
	public NoValidLocaleException() {
		super();
	}

	public NoValidLocaleException(String msg) {
		super(msg);
	}
}
