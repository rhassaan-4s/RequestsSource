package com._4s_.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.criterion.MatchMode;

import com._4s_.common.dao.CommonDAO;
import com._4s_.common.model.Employee;
import com._4s_.common.model.HijriDateAdjustment;
import com._4s_.common.model.LastSequence;
import com._4s_.common.model.Types;
import com._4s_.common.util.MultiCalendarDate;

public class CommonManagerImpl extends BaseManagerImpl implements CommonManager {
	private CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
	public LastSequence getSequenceByClassName (String tableName){
		return commonDAO.getSequenceByClassName(tableName);
	}
	public void saveSequence(LastSequence lastSequence){
		commonDAO.saveSequence(lastSequence);
	}
	public  List getCitiesByCountry(Long countryId){
		return commonDAO.getCitiesByCountry(countryId);
	}
	public Employee getEmployeeByUsername(String userName){
		return commonDAO.getEmployeeByUsername(userName);
	}
	/*public Employee getEmployeeByAccount(Account account){
		return commonDAO.getEmployeeByAccount(account);
	}*/
	public Employee getEmployeeByUser(Long userId) {
		return commonDAO.getEmployeeByUser(userId);
	}
	public List getEmployeesNotInInternalCommunicator() {
		return commonDAO.getEmployeesNotInInternalCommunicator();
	}

	public List getBranchesRelatedByCompany(Long companyId)
	{
		return commonDAO.getBranchesRelatedByCompany(companyId);
	}
	public List getDataByTypes(Types type) {
		// TODO Auto-generated method stub
		return commonDAO.getDataByTypes(type); 
	}

	public List getEmployeesByFirstName(final String value){
		String[] nameParts = value.split(" ");
		String namePart = nameParts[0];
		List employees = commonDAO.getEmployeesByFirstName(namePart);
		List employeesNames = new ArrayList();
		Iterator itr = employees.iterator();
		while (itr.hasNext()){
			Employee employee = (Employee)itr.next();
			employeesNames.add(employee.getFirstName()+" "+employee.getLastName());
		}
		return employeesNames;
	}
	
	public List getCitiesByDescription(String namePart) {
		List cities = commonDAO.getCitiesByDescription(namePart);
		List citiesDescriptions = new ArrayList();
		return commonDAO.getCitiesByDescription(namePart);
	}
	public List getCountriesByDescription(String namePart) {
		
		return commonDAO.getCitiesByDescription(namePart);
	}
	
	public List getAutoCompleteSuggestions(String value, String functionType) {
		if (functionType.equals("1")){
			return getEmployeesByFirstName(value);
		}
		if (functionType.equals("2")){
			return getCitiesByDescription(value);
		}
		if (functionType.equals("3")){
			return getCountriesByDescription(value);
		}
		else{
			return null;
		}
	}
	
	public List search(String searchCommandId, String searchCommandName, Class className, String firstParam, String secondParam,final String match1,final String match2,String branchId) {
		MatchMode m1 = null;
		MatchMode m2 = null;
		if (match1 != null){
			if (match1.equals("1")){
				m1 = MatchMode.ANYWHERE;
			}
			if (match1.equals("2")){
				m1 = MatchMode.START;		
			}
			if (match1.equals("3")){
				m1 = MatchMode.END;
			}
			if (match1.equals("4")){
				m1 = MatchMode.EXACT;
			}
		}
		if (match2 != null){
			if (match2.equals("1")){
				m1 = MatchMode.ANYWHERE;
			}
			if (match2.equals("2")){
				m1 = MatchMode.START;	
			}
			if (match2.equals("3")){
				m1 = MatchMode.END;
			}
			if (match2.equals("4")){
				m1 = MatchMode.EXACT;
			}
		}
		return commonDAO.search(searchCommandId,searchCommandName,className,firstParam,secondParam,m1,m2);
	}
	public List getCountries() {
		return commonDAO.getCountries();
	}
	public String getHijriDate(Date date) {
		log.debug(">>>>>>>>>>>>>>>>.. date "+date);
		MultiCalendarDate mCalDate = new MultiCalendarDate();
		mCalDate.setDate(date);
		log.debug(">>>>>>>>>>>>>>>>>>>>> mCalDate.getHijriString() "+mCalDate.getHijriString());
		return mCalDate.getHijriString();
	}
	public HijriDateAdjustment getHijriDateAdjustment(Date date) {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>> HijriDateAdjustment commonManager");
		return commonDAO.getHijriDateAdjustment(date);
	}
	public String generateCCNumbers(String prefix,int length) {
		String ccNumber = prefix;
		while ( ccNumber.length() < (length - 1) ) {
	        ccNumber = ccNumber + new Double(Math.floor(Math.random()*10)).intValue(); 
	        log.debug(">>>>>>>>>>>>ccNumber "+ccNumber);
	    }
		
		log.debug(">>>>>>>>>>>>starting ccNumber "+ccNumber);
		
		String revStr ="";
		for (int i = ccNumber.length()-1; i>=0; i--){
			revStr = revStr + ccNumber.charAt(i);
			log.debug(">>>>>>>>>revStr "+revStr);
		}
		
		List reversedCCnumber = new ArrayList();
	    for ( int i=0; i < revStr.length(); i++ ) {
	    	//reversedCCnumber[i] = parseInt( revStr.charAt(i) );  
	    	reversedCCnumber.add(Integer.parseInt(revStr.substring(i,i+1)));
	    }
	    
	    int sum = 0;
	    int pos = 0;
	    int odd;
	    
	    while ( pos < length - 1 ) {
	    	log.debug(">>>>>>>>>>reversedCCnumber.get("+pos+") "+reversedCCnumber.get(pos));
	        odd = ((Integer)reversedCCnumber.get(pos)).intValue() * 2;
	        log.debug(">>>>>>>>>>>>odd "+odd);
	        if ( odd > 9 ) {
	            odd = odd - 9;
	        }

	        sum = sum + odd;
	        log.debug(">>>>>>>>>>>>sum "+sum); 
	        
	        if ( pos != (length - 2) ) {
	            sum = sum + ((Integer)reversedCCnumber.get(pos +1)).intValue();
	        }
	        
	        log.debug(">>>>>>>>>>>>sum "+sum); 
	        pos = pos + 2;
	    }
	    log.debug(">>>>>>>>>>>>>>final sum "+sum);
	    
	    int checkdigit = new Double((( Math.floor(sum/10) + 1) * 10 - sum) % 10).intValue();
	    log.debug(">>>>>>>>>>>>>checkdigit "+checkdigit);
	    ccNumber = ccNumber + checkdigit;
	    log.debug(">>>>>>>>>>>>final "+ccNumber);
	    log.debug(">>>>>>>>>>>> floor "+Math.floor(1.7));
		return ccNumber;
	}
	
	/*public TrialDestination getDestination(Long id) {
		
		return commonDAO.getDestination(id);
	}
	public List getDestinations() {
		
		return commonDAO.getDestinations();
	}
	*/
	public String zeroFill(String str, int length) {
		int max = length - str.length();
		if(str.length() < length){
			for(int i=0;i< max;i++){
				//log.debug("loop ");
				str = "0"+str;
				/*log.debug("str "+str);
				log.debug("length "+length);
				log.debug("(length - str.length()) "+(length - str.length()));
				log.debug("i<(length - str.length()) "+(i<(length - str.length())));*/
			}
		}
		return str;
	}
	public void removeSearchAttributesFromSession(HttpSession session) {
		log.debug("session "+session);
		session.removeAttribute("searchCommandId");
		session.removeAttribute("searchCommandName");
	}
	public List getEmployeesByBranchAndDepartment(String branchId, String departmentId) {
		return commonDAO.getEmployeesByBranchAndDepartment(branchId , departmentId);
	}
	
}