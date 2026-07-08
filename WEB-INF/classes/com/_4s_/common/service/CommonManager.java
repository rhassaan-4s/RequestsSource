package com._4s_.common.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com._4s_.common.dao.CommonDAO;
import com._4s_.common.model.Employee;
import com._4s_.common.model.HijriDateAdjustment;
import com._4s_.common.model.LastSequence;
import com._4s_.common.model.Types;

public interface CommonManager extends BaseManager {
	public abstract void setCommonDAO(CommonDAO commonDAO);
	public abstract LastSequence getSequenceByClassName (String tableName);
	public abstract void saveSequence(LastSequence lastSequence);
	public abstract List getCitiesByCountry(Long countryId);
	public abstract Employee getEmployeeByUsername(String userName);
	//public abstract Employee getEmployeeByAccount(Account account);
	public abstract Employee getEmployeeByUser(Long userId);
	public abstract List getEmployeesNotInInternalCommunicator();
	
	public abstract List getBranchesRelatedByCompany(Long companyId);
	public abstract List getDataByTypes(Types type);
	public abstract List getEmployeesByFirstName(final String namePart);
	public abstract List getAutoCompleteSuggestions(final String value, final String functionType);
	public abstract List search(final String searchCommandId,final String searchCommandName,final Class className,final String firstParam,final String secondParam,final String match1,final String match2,String branchId);
	public abstract List getCitiesByDescription(final String namePart);
	public abstract List getCountriesByDescription(final String namePart);
	public abstract List getCountries();
	public abstract String getHijriDate(Date date);
	public abstract HijriDateAdjustment getHijriDateAdjustment(Date date);
	public abstract String generateCCNumbers(String prefix,int lenghth);
	public abstract String zeroFill(String str, int length);
	public abstract void removeSearchAttributesFromSession(HttpSession session);
	//public abstract TrialDestination getDestination(Long id);
	//public abstract List getDestinations();
	
	public abstract List getEmployeesByBranchAndDepartment(String branchId , String departmentId );
	
}
