package com._4s_.requestsApproval.service;


import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HRGeographicalDivision;
import com._4s_.HR.model.HRGeographicalLevel;
import com._4s_.HR.model.HRInternalDivision;
import com._4s_.HR.model.HRInternalLevel;
import com._4s_.HR.model.HRQualificationDivision;
import com._4s_.HR.model.HRQualificationLevel;
import com._4s_.HR.model.HRSpecialtyDivision;
import com._4s_.HR.model.HRSpecialtyLevel;
import com._4s_.HR.model.HRVacation;
import com._4s_.common.model.LastSequence;
import com._4s_.common.model.Settings;
import com._4s_.common.service.BaseManagerImpl;
import com._4s_.common.service.SequenceManager;
import com._4s_.requestsApproval.dao.ExternalQueries;
import com._4s_.requestsApproval.dao.RequestsApprovalDAO;
import com.ibm.icu.util.Calendar;
import com.jenkov.prizetags.tree.itf.ITree;
import com.jenkov.prizetags.tree.itf.ITreeNode;


public class RequestsApprovalManagerImpl extends BaseManagerImpl implements RequestsApprovalManager{


	private RequestsApprovalDAO requestsApprovalDAO;	
	
	private ExternalQueries externalQueries = null;
	
	private SequenceManager sequenceManager ;
	public RequestsApprovalDAO getRequestsApprovalDAO() {
		return requestsApprovalDAO;
	}

	public void setRequestsApprovalDAO(RequestsApprovalDAO requestsApprovalDAO) {
		this.requestsApprovalDAO = requestsApprovalDAO;
	}


	public void addNode(String nodeId, String nodeArDesc, String parentId,
			ITree tree) {
		// TODO Auto-generated method stub
		
	}

	public void changeCode(HRInternalDivision changedInternalDivision,
			String newCode, String oldLongCode) {
		// TODO Auto-generated method stub
		
	}

	public void changeCode(
			HRQualificationDivision changedQualificationDivision,
			String newCode, String oldLongCode) {
		// TODO Auto-generated method stub
		
	}

	public void changeCode(HRGeographicalDivision changedGeographicalDivision,
			String newCode, String oldLongCode) {
		// TODO Auto-generated method stub
		
	}

	public void changeCode(HRSpecialtyDivision changedSpecialtyDivision,
			String newCode, String oldLongCode) {
		// TODO Auto-generated method stub
		
	}

	public void changeFlag() {
		// TODO Auto-generated method stub
		
	}

	public void createNode(Object higherCategory, ITreeNode treeNode,
			String className) {
		// TODO Auto-generated method stub
		
	}

	public void createNodeForCopy(Object higherCategory, ITreeNode treeNode,
			String className, Object parent) {
		// TODO Auto-generated method stub
		
	}

	public ITree createTree(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public ITree createTreeForCopy(String className, Object parent) {
		// TODO Auto-generated method stub
		return null;
	}

	public ITree createTreeIfNotFound(HttpServletRequest request,
			String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public ITree createTreeIfNotFoundForCopy(HttpServletRequest request,
			String className, Object parent) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean deleteLevel(Long id, String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteNodeFromTree(ITree tree, String deleteId) {
		// TODO Auto-generated method stub
		
	}

	public Boolean deleteVacationRules(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean deleteViolationRules(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean deleteYear(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List generateCodesList(List list, String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getAllByCode(String code, Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getAllLeafs(Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getCategoryAccountsByParentCategory(Long parentId, Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getChildrenAndGrandChildrenByParentLongCode(
			String parentLongCode, Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getCountriesForNationality() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map getDateContents(Date serviceDate) {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDateFromContents(Integer year, Integer month, Integer day) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getDivisionChildren(Class clazz, String longCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getEmployeeServiceLength(HREmployee employee) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getEmployeeVacation(HREmployee employee, HRVacation vacation) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getEmployeeVacationAtInstall(HREmployee employee,
			HRVacation vacation) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getEmployeesByCodeAndName(String codeFrom, String codeTo,
			String empName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getEmployeesForEmployeeServiceLength(String empCode,
			String empName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getEmployeesForEmployeeVacation(String empCode, String empName,
			HRInternalDivision division) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getEmployeesForEmployeeVacationAtInstallation(String empCode,
			String empName, HRInternalDivision division) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getExistingDivisionsForNullDivLevelParent(Class clazz, Long id,
			String code) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getExistingDivisionsForParentForCopy(Class clazz,
			Long parentId, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getExistingDivisionsForparent(Class clazz, String parentCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getExistingDivisionsForparent(Class clazz, Long parentId,
			String code) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getFilteredGroupedAInsuranceCala(String month, String year,
			String insurance, String emp_code, String empName,
			String insuranceNo, String subscriptionDate, String groupBy) {
		// TODO Auto-generated method stub
		return null;
	}

	public HRGeographicalLevel getGeoLastLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	public HRGeographicalLevel getGeoLevelByLevelNo(Integer levelNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getHigherDivisions(Class clazz, Long id, Integer levelNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public HRInternalDivision getInternalDivisionForTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	public HRInternalLevel getLastLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getLeafsByParentId(Long parentId, String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public HRInternalLevel getLevelByLevelNo(Integer levelNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getLevelsByDivisionParentId(Integer levelNo, Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getParents(Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public HRQualificationLevel getQualLastLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	public HRQualificationLevel getQualLevelByLevelNo(Integer levelNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public HRQualificationDivision getQualificationDivisionForTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getRoot(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public HRSpecialtyDivision getSpecialtyDivisionForTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	public HRSpecialtyLevel getSpecialtyLastLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	public HRSpecialtyLevel getSpecialtyLevelByLevelNo(Integer levelNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public HRVacation getVacation(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateNodeDescription(String nodeId, String nodeArDesc,
			ITree tree) {
		// TODO Auto-generated method stub
		
	}

	public String zeroFill(Object[] codes, int codeLength) {
		Arrays.sort(codes);
		Integer nextCode;
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> codes.length "+codes.length);
		if (codes.length > 0){
			nextCode = (Integer) codes[codes.length - 1];
			nextCode++;
		}
		else{
			nextCode = new Integer(1);
		}
		String zeroFill = StringUtils.leftPad(nextCode.toString(), codeLength,
		"0");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.zeroFill" + zeroFill);
		return zeroFill;
	}
	
	public List getRequestsByDatePeriodAndRequestType(Date fromDate, Date toDate, Long requestType){
		return  requestsApprovalDAO.getRequestsByDatePeriodAndRequestType(fromDate, toDate,requestType);
		
	}
	public List getRequestsByExactDatePeriodAndRequestType(Date fromDate, Date toDate, Long requestType){
		return requestsApprovalDAO.getRequestsByExactDatePeriodAndRequestType(fromDate, toDate, requestType);
	}
	public List getRequestsByDatePeriod(Date fromDate, Date toDate){
		return requestsApprovalDAO.getRequestsByDatePeriod(fromDate, toDate);
		
	}
	
	public List getRequestsByExactDatePeriod(Date fromDate, Date toDate){
		return requestsApprovalDAO.getRequestsByExactDatePeriod(fromDate, toDate);
		
	}
	
	public List getRequestsByDatePeriodAndRequestTypeAndEmpCode(Date fromDate,Date toDate, Long requestType,String empCode){
		return requestsApprovalDAO.getRequestsByDatePeriodAndRequestTypeAndEmpCode( fromDate, toDate,  requestType, empCode);
	}
	
	public List getRequestsByExactDatePeriodAndRequestTypeAndEmpCode(Date fromDate,Date toDate, Long requestType,String empCode){
		return requestsApprovalDAO.getRequestsByExactDatePeriodAndRequestTypeAndEmpCode( fromDate, toDate,  requestType, empCode);
	}
	
	public List getRequestsByDatePeriodAndEmpCode(final Date fromDate,final Date toDate, final String empCode){
		return requestsApprovalDAO.getRequestsByDatePeriodAndEmpCode(fromDate, toDate, empCode);
	}
	
	public List getRequestsByExactDatePeriodAndEmpCode(final Date fromDate,final Date toDate, final String empCode){
		return requestsApprovalDAO.getRequestsByExactDatePeriodAndEmpCode(fromDate, toDate, empCode);
	}
	
	public List getRequestsByDatePeriodAndRequestTypeForEmployee(final Date fromDate, final Date toDate, final Long requestType, final String empCode){
		return requestsApprovalDAO.getRequestsByDatePeriodAndRequestTypeForEmployee( fromDate,  toDate,  requestType,  empCode);
	}
	
	public List getRequestsByExactDatePeriodAndRequestTypeForEmployee(final Date fromDate, final Date toDate, final Long requestType, final String empCode){
		return requestsApprovalDAO.getRequestsByExactDatePeriodAndRequestTypeForEmployee( fromDate,  toDate,  requestType,  empCode);
	}
	
	public List getRequestsByDatePeriodForEmployee(final Date fromDate, final Date toDate, final String empCode){
		return requestsApprovalDAO.getRequestsByDatePeriodForEmployee( fromDate,  toDate, empCode);
	}
	
	public List getRequestsByExactDatePeriodForEmployee(final Date fromDate, final Date toDate, final String empCode){
		return requestsApprovalDAO.getRequestsByExactDatePeriodForEmployee( fromDate,  toDate, empCode);
	}
	
	public List getEmployeesByCodes(final String codeFrom,final String codeTo){
		return requestsApprovalDAO.getEmployeesByCodes(codeFrom, codeTo);
	}
	
	public List getEmployeesByCodesAndDatePeriod(final String codeFrom,final String codeTo,final Date fromDate, final Date toDate){
		return requestsApprovalDAO.getEmployeesByCodesAndDatePeriod(codeFrom,codeTo, fromDate,toDate);
	}
	public List getRequestStatus(final Long req_id){
		return requestsApprovalDAO.getRequestStatus(req_id);
	}
	
	
	public List getEmployeesByCodesAndRequestType(final String codeFrom,final String codeTo, final Long requestType){
		return requestsApprovalDAO.getEmployeesByCodesAndRequestType(codeFrom, codeTo, requestType);
	}
	public List getEmployeesByCodesAndDatePeriodAndRequestType(final String codeFrom,final String codeTo,final Date fromDate, final Date toDate, final Long requestType){
		return requestsApprovalDAO.getEmployeesByCodesAndDatePeriodAndRequestType(codeFrom, codeTo, fromDate, toDate, requestType);
	}
	
	public synchronized String CreateRequestNumber() {
		
		Calendar cal = Calendar.getInstance();
		int yearInt = cal.get(Calendar.YEAR);
		String year = new Integer(yearInt).toString();
		NumberFormat nf = NumberFormat.getInstance();
		
		String requestNumber;
		
		nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(7);
		nf.setMaximumIntegerDigits(7);
		nf.setGroupingUsed(false);
		
		String serial;			
		String className = "RequestNumber"+year;
		
		Long seq = sequenceManager.getSequenceByClassName(className);
		
		if(seq == null)
		{
			log.debug(">>>>>>>>>>>>>>null");
			LastSequence sequence = new LastSequence();
			sequence.setClassName("RequestNumber"+year);
			sequence.setClassSequence(new Long(1));
			saveObject(sequence);
			serial = nf.format(1);
		}
		else
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>else"+ seq);
			log.debug("----sequenceManager.getSequenceByClassName(className)---"+ seq);
			serial = nf.format(seq);
		}
		
		requestNumber = year.concat(serial);
		
	return requestNumber;
	}
	
	public Long getEmpVacation (String empCode, Long reqId, String vacId, Date from_date){
		Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class,new Long(1));
		String hostName = settings.getServer();
		String serviceName = settings.getService();
		String userName = settings.getUsername();
		String password = settings.getPassword();
		return externalQueries.getEmpVacation(hostName, serviceName, userName, password, empCode, reqId, vacId, from_date);
	}
	
	public Long getVacationLimit (String empCode, Long reqId, String vacId, Date from_date){
		Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class,new Long(1));
		String hostName = settings.getServer();
		String serviceName = settings.getService();
		String userName = settings.getUsername();
		String password = settings.getPassword();
		return externalQueries.getVacationLimit(hostName, serviceName, userName, password, empCode, reqId, vacId, from_date);
	}

	public Long getVacationCredit (String empCode, Long reqId, String vacId, Date from_date){
		Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class,new Long(1));
		String hostName = settings.getServer();
		String serviceName = settings.getService();
		String userName = settings.getUsername();
		String password = settings.getPassword();
		return externalQueries.getVacationCredit(hostName, serviceName, userName, password, empCode, reqId, vacId, from_date);
	}
	
	public List getVacations (String empCode, Long reqId, String vacId, Date from_date){
		Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class,new Long(1));
		String hostName = settings.getServer();
		String serviceName = settings.getService();
		String userName = settings.getUsername();
		String password = settings.getPassword();
		return externalQueries.getVacations(hostName, serviceName, userName, password, empCode, reqId, vacId, from_date);
	}
	public List getVacations (String empCode, Long reqId, Date from_date,Date to_date){
		Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class,new Long(1));
		String hostName = settings.getServer();
		String serviceName = settings.getService();
		String userName = settings.getUsername();
		String password = settings.getPassword();
		return externalQueries.getVacations(hostName, serviceName, userName, password, empCode, reqId, from_date,to_date);
	}
	public List getTimeAttend (String empCode, Date from_date, Date to_date){
		Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class,new Long(1));
		String hostName = settings.getServer();
		String serviceName = settings.getService();
		String userName = settings.getUsername();
		String password = settings.getPassword();
		return externalQueries.getTimeAttend(hostName, serviceName, userName, password, empCode,from_date,to_date);
	}
	public void setSequenceManager(SequenceManager sequenceManager) {
		this.sequenceManager = sequenceManager;
	}

	public SequenceManager getSequenceManager() {
		return sequenceManager;
	}

	public void setExternalQueries(ExternalQueries externalQueries) {
		this.externalQueries = externalQueries;
	}

	public ExternalQueries getExternalQueries() {
		return externalQueries;
	}

	public int getSalaryFromDay() {
		Settings settings = (Settings)requestsApprovalDAO.getObject(Settings.class,new Long(1));
		String hostName = settings.getServer();
		String serviceName = settings.getService();
		String userName = settings.getUsername();
		String password = settings.getPassword();
		return externalQueries.getSalaryFromDay(hostName, serviceName, userName, password);
	}

}