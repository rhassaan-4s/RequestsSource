package com._4s_.i18n.model;

public class DateException extends Exception{
	
	private String message = null;
	public String getMessage() {
		// TODO Auto-generated method stub
		String message = "Invalid Date Format";
		return message;
	}
	public DateException(){
		
	}
	public DateException(String s){
		this.message = s;
	}
}
