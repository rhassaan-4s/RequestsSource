package com._4s_.HR.web.validators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oro.text.perl.Perl5Util;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com._4s_.HR.model.HREmployee;


public class EmployeeFormValidator implements Validator
{
	private final Log log = LogFactory.getLog(getClass());
	public boolean supports(Class clazz)
	{
		return clazz.equals(HREmployee.class);
	}
	/////////////////////////////////////////////////////////////////////////////////////
	public void validate(Object obj, Errors errors) 
	{
		HREmployee hrEmployee=(HREmployee)obj;
		
		if (errors.getErrorCount() == 0)
		{
			log.debug("inside validateArabicName");
			validateArabicName(hrEmployee, errors);
		}
		if (errors.getErrorCount() == 0)
		{
			log.debug("inside validateEnglishName");
			validateEnglishName(hrEmployee, errors);
		}
		if (errors.getErrorCount() == 0)
		{
			log.debug("inside validateNationality");
			validateNationality(hrEmployee, errors);
		}
		/*if (errors.getErrorCount() == 0)
		{
			log.debug("inside validateBirthDate");
			validateBirthDate(hrEmployee, errors);
		}*/
		if (errors.getErrorCount() == 0)
		{
			log.debug("inside validateGender");
			validateGender(hrEmployee, errors);
		}
		/*if (errors.getErrorCount() == 0)
		{
			validateNumber(hrEmployee, errors);
		}
		*/
		if (errors.getErrorCount() == 0)
		{
			log.debug("inside validateTel");
			validateTel(hrEmployee, errors);
		}
		if (errors.getErrorCount() == 0)
		{
			log.debug("inside validateMobile");
			validateMobile(hrEmployee, errors);
		}
		
		if (errors.getErrorCount() == 0)
		{
			log.debug("inside ");
			validateEmail(hrEmployee, errors);
		}
		
	}
	
/////////////////////////////////////////////////////////////////////////////////////
	
	public static boolean isOnlyNumbers(String str){
		for(int i = 0 ; i<str.length(); i++){
			char ch = str.charAt(i);
			if(ch < '0' || ch >'9'){ // not a character
				return false;
			}
		}
		return true;
	}
	//to validate email
	private static final String EMAIL_REGEXP =
		"/^[a-z0-9]+([_\\.-][a-z0-9]+)*@([a-z0-9]+([\\.-][a-z0-9]+)*)+\\.[a-z]{2,}$/i";
	
	public void validateArabicName(String name, Errors errors) {

		log.debug("errors.getErrorCount() " + errors.getErrorCount());

		if (errors.getErrorCount() == 0){

		if (name!="" && name!=null) {

		if(!name.matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

		errors.reject("training.errors.invalidArabicName");

		}

		}

		}

		}


		public void validateEnglishName(String name, Errors errors) {

		log.debug("errors.getErrorCount() " + errors.getErrorCount());

		if (errors.getErrorCount() == 0){

		if (name!="" && name!=null) {

		if(!name.matches("([a-zA-Z]+\\s{0,1})*")) {

		errors.reject("training.errors.invalidEnglishName");

		}

		}

		}

		}

	
	private void validateArabicName(HREmployee hrEmployee,Errors errors)
	{
		if(hrEmployee.getName()==null||hrEmployee.getName().equals("")
			)
		{
			errors.rejectValue("name","commons.errors.requiredFields","");
		}
		else if(hrEmployee.getName()!=null && !hrEmployee.getName().equals(""))
		{
			validateArabicName(hrEmployee.getName(), errors);
		}
	
	}
	private void validateEnglishName(HREmployee hrEmployee,Errors errors)
	{
		if(hrEmployee.getEname()==null||hrEmployee.getEname().equals(""	))
		{
			errors.rejectValue("ename","commons.errors.requiredFields","");
		}
		else if(hrEmployee.getEname()!=null &&!hrEmployee.getEname().equals(""))
		{
			validateEnglishName(hrEmployee.getEname(), errors);
			
		}
		
	}
	private void validateNationality(HREmployee hrEmployee,Errors errors)
	{
		if(hrEmployee.getNationality()==null)
		{
			errors.rejectValue("nationality","commons.errors.requiredFields","");
		}
	}
	private void validateBirthDate(HREmployee hrEmployee,Errors errors)
	{
		if(hrEmployee.getBirthDate()==null)
		{
			errors.rejectValue("birthDate","commons.errors.requiredFields","");
		}
	}
	private void validateGender(HREmployee hrEmployee,Errors errors)
	{
		if(hrEmployee.getGender()==null|| hrEmployee.getGender().equals(""))
		{
			errors.rejectValue("gender","commons.errors.requiredFields","");
		}
	}
	
	
	
	
	private void validateNumber(HREmployee hrEmployee,Errors errors)
	{
		if(hrEmployee.getIdNumber()==null || hrEmployee.getIdNumber().equals(""))
		{
			
			errors.rejectValue("idNumber","commons.errors.requiredFields","");
				
		}
		if(hrEmployee.getIdNumber()!=null && !hrEmployee.getIdNumber().equals(""))
		{
			if(!isOnlyNumbers(hrEmployee.getIdNumber())){
				errors.rejectValue("idNumber", "training.errors.invalidIdNumber");
				
				
			}
		}
			
		
	}
	private void validateTel(HREmployee hrEmployee,Errors errors) {
		
		if(hrEmployee.getPhone() !=null && !hrEmployee.getPhone().equals(""))
		{
			if(!isOnlyNumbers(hrEmployee.getPhone()))
			{
			errors.rejectValue("phone","commons.errors.invalidTelNumber");
			}
		
		}
	}
	
	private void validateMobile(HREmployee hrEmployee,Errors errors) {
		
		if(hrEmployee.getMobile() !=null && !hrEmployee.getMobile().equals(""))
		{
			if(!isOnlyNumbers(hrEmployee.getMobile()))
					{
			         errors.rejectValue("mobile", "common.errors.invalidCellularNumber"," ");
					}
		}
			
		}
	

	
	private void validateEmail(HREmployee hrEmployee, Errors errors) {
		if (hrEmployee.getEmail() != null && !hrEmployee.getEmail().equals("")) 
		{
		Perl5Util perl5Util = new Perl5Util();
		if(!perl5Util.match(EMAIL_REGEXP, hrEmployee.getEmail())) {
			errors.rejectValue("email","commons.errors.invalidEmailFormat","");
		}
		}
		else
		{
			errors.rejectValue("email","commons.errors.requiredFields","");
		}
		
	}
	
	
	
	
}
