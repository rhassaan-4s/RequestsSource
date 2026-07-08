package com._4s_.security.service;

public class UserLanguage {
	private String username;
	private String locale;
	public UserLanguage(){
		this.username = null;
		this.locale = null;
	}
	public UserLanguage(String username,String locale){
		this.username = username;
		this.locale = locale;
	}
}
