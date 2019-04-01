package com._4s_.requestsApproval.service;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com._4s_.common.model.Employee;
import com._4s_.common.model.LastSequence;
import com._4s_.common.model.Settings;
import com._4s_.common.service.BaseManagerImpl;
import com._4s_.common.service.SequenceManager;
import com._4s_.common.util.MultiCalendarDate;
import com._4s_.requestsApproval.dao.ExternalQueries;
import com._4s_.requestsApproval.dao.RequestsApprovalDAO;
import com._4s_.requestsApproval.model.AccessLevels;
import com._4s_.requestsApproval.model.EmpReqApproval;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.restServices.json.RequestApproval;
import com._4s_.restServices.json.RestStatus;
import com.ibm.icu.util.Calendar;
import com.jenkov.prizetags.tree.itf.ITree;
import com.jenkov.prizetags.tree.itf.ITreeNode;

@Service
@Transactional
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
	public Map getPagedRequests(final Date fromDate, final Date toDate, final Long requestType, final Date exactFrom, final Date exactTo, 
			final Date periodFrom, final Date periodTo, String empCode, String codeFrom, String codeTo, Long statusId, List empReqTypeAccs, final int pageNumber, final int pageSize){
		return  requestsApprovalDAO.getPagedRequests(fromDate, toDate,requestType,exactFrom,exactTo, periodFrom, periodTo, empCode, codeFrom, codeTo, statusId,empReqTypeAccs, pageNumber, pageSize);
		
	}
	
	public List getRequests(final Date fromDate, final Date toDate, final Long requestType, final Date exactFrom, final Date exactTo, 
			final Date periodFrom, final Date periodTo, String empCode, String codeFrom, String codeTo, Long statusId){
		return  requestsApprovalDAO.getRequests(fromDate, toDate,requestType,exactFrom,exactTo, periodFrom, periodTo, empCode, codeFrom, codeTo, statusId);
		
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

	public List getRequestsForApprovalList(String requestNumber, String emp_code, String dateFrom, String dateTo, String exactDateFrom, String exactDateTo, 
			String requestType, String codeFrom, String codeTo, String statusId) {

		MultiCalendarDate mCalDate = new MultiCalendarDate();
		List loginUserReqs = new ArrayList();
		if (requestType!= null && !requestType.isEmpty() && requestNumber!=null && !requestNumber.isEmpty()){
			if (dateFrom != null && dateTo != null){
				if (!dateFrom.equals("") && !dateTo.equals("") ) {
					
					Date fromDate = null;
					Date toDate = null;
					log.debug(">>>>>>>>>>>>> if ");
					log.debug(">>>>>>>>>>>Valid date format");
					log.debug(">>>>>>>>>>>>>fromDateString "+ dateFrom);
					//fromDateStr = fromDateString +" 00:00";
					mCalDate.setDateTimeString(dateFrom,new Boolean(true));
					fromDate = mCalDate.getDate();
					log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
					log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
					//toDateStr = toDateString+ " 23:59";
					mCalDate.setDateTimeString(dateTo,new Boolean(false));
					toDate= mCalDate.getDate();
					log.debug(">>>>>>>>>>>>>toDate "+ toDate);
					
					loginUserReqs= getRequests(fromDate, toDate,Long.parseLong(requestType),null,null,null,null,null,null,null,null);
					log.debug("--dateList.size--"+loginUserReqs.size());
					//model.put("loginUserReqs", loginUserReqs);
					return loginUserReqs;
				}
			}
			
			
			if (exactDateFrom != null && exactDateTo != null){
				if (!exactDateFrom.equals("") && !exactDateTo.equals("") ) {
					
					Date fromDate = null;
					Date toDate = null;
					log.debug(">>>>>>>>>>>>> if ");
					log.debug(">>>>>>>>>>>Valid date format");
					log.debug(">>>>>>>>>>>>>fromDateString "+ exactDateFrom);
					//fromDateStr = fromDateString +" 00:00";
					mCalDate.setDateTimeString(exactDateFrom,new Boolean(true));
					fromDate = mCalDate.getDate();
					log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
					log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
					//toDateStr = toDateString+ " 23:59";
					mCalDate.setDateTimeString(dateTo,new Boolean(false));
					toDate= mCalDate.getDate();
					log.debug(">>>>>>>>>>>>>toDate "+ toDate);
					
					loginUserReqs=getRequests(null,null,Long.parseLong(requestType),fromDate, toDate,null,null,null,null,null,null);
					log.debug("--dateList.size--"+loginUserReqs.size());
					//model.put("loginUserReqs", loginUserReqs);
					return loginUserReqs;
				}
			}
			
			
			
		}
		
		if(emp_code!=null && !emp_code.equals("")){
			log.debug("---xxxxxxxCodeName--");
			if(isOnlyNumbers(emp_code) && (getObjectByParameter(LoginUsers.class, "empCode", emp_code)!=null && !getObjectByParameter(LoginUsers.class, "empCode", emp_code).equals(""))){
				LoginUsers loginUser=(LoginUsers) getObjectByParameter(LoginUsers.class, "empCode", emp_code);
				log.debug("--loginUser.getName()--"+loginUser.getName());

				// to get requests corresponding to request type
				List<String>list=new ArrayList<String>();
				list.add("period_from");
				
//				loginUserReqs=(List) getObjectsByParameterOrderedByFieldList(LoginUsersRequests.class, "login_user", loginUser, list);
				loginUserReqs = getRequests(null,null,new Long(requestType),null,null,null,null,emp_code,null,null,null);
				
				return loginUserReqs;
				
//				List neededRequestTypes = new ArrayList();
//				for(int i=0;i<loginUserReqs.size();i++){
//					log.debug("---needed---");
//					LoginUsersRequests reqs= (LoginUsersRequests) loginUserReqs.get(i);
//					log.debug("---needed reqs---"+reqs.getEmpCode()+"----reqs.getRequest_id().getId()--"+reqs.getRequest_id().getId());
//					String request_type=Long.toString(reqs.getRequest_id().getId());
//					log.debug("-----requestType.equals(reqs.getRequest_id().getId())----"+requestType.equals(reqs.getRequest_id().getId()));
//					
//					log.debug("-X--requestType.equals(request_type)--"+requestType.equals(request_type));
//					if(requestType.equals(request_type)){
//						neededRequestTypes.add(reqs);
//						log.debug("---neededList---"+neededRequestTypes.size());
//					}
//					log.debug("---after IF neededList---"+neededRequestTypes.size());				
//				}
////				log.debug("--list.size--"+neededRequestTypes.size());
//				return neededRequestTypes;
				//model.put("loginUserReqs", neededRequestTypes);
			}

		}
		
		
		if(codeFrom!=null && codeTo!=null && !codeFrom.equals("")&& !codeTo.equals("")){
			loginUserReqs=getRequests(null, null, null, null, null, null, null, null, codeFrom, codeTo, null);
			log.debug("---codesList---"+loginUserReqs.size());
//			for (int i = 0; i < loginUserReqs.size(); i++) {
//				LoginUsersRequests s=(LoginUsersRequests) loginUserReqs.get(i);
//				log.debug("---code code---"+s.getEmpCode());
//			}
			return loginUserReqs;
		}
		
		if (dateFrom != null && dateTo != null){
			if (!dateFrom.equals("") && !dateTo.equals("") ) {
				
				Date fromDate = null;
				Date toDate = null;
				log.debug(">>>>>>>>>>>>> if ");
				log.debug(">>>>>>>>>>>Valid date format");
				log.debug(">>>>>>>>>>>>>fromDateString "+ dateFrom);
				//fromDateStr = fromDateString +" 00:00";
				mCalDate.setDateTimeString(dateFrom,new Boolean(true));
				fromDate = mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
				log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
				//toDateStr = toDateString+ " 23:59";
				mCalDate.setDateTimeString(dateTo,new Boolean(false));
				toDate= mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>toDate "+ toDate);
				
				loginUserReqs=getRequests(fromDate, toDate, new Long(requestType), null, null, null,null, null, null, null, null);
				log.debug("--dateList.size--"+loginUserReqs.size());
				//model.put("loginUserReqs", loginUserReqs);
				return loginUserReqs;
			}
		}
		
		if (exactDateFrom != null && exactDateTo != null){
			if (!exactDateFrom.equals("") && !exactDateTo.equals("") ) {
				
				Date fromDate = null;
				Date toDate = null;
				log.debug(">>>>>>>>>>>>> if ");
				log.debug(">>>>>>>>>>>Valid date format");
				log.debug(">>>>>>>>>>>>>fromDateString "+ exactDateFrom);
				//fromDateStr = fromDateString +" 00:00";
				mCalDate.setDateTimeString(exactDateFrom,new Boolean(true));
				fromDate = mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
				log.debug(">>>>>>>>>>>>>toDateString "+ exactDateTo);
				//toDateStr = toDateString+ " 23:59";
				mCalDate.setDateTimeString(exactDateTo,new Boolean(false));
				toDate= mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>exactDateTo "+ toDate);
				
//				loginUserReqs=getPagedRequestsByExactDatePeriodAndRequestType(fromDate, toDate,Long.parseLong(requestType),pageNumber,pageSize);
				loginUserReqs = getRequests(fromDate, toDate, new Long(requestType), null, null, null, null, null, null, null, null);
				log.debug("--dateList.size--"+loginUserReqs.size());
				//model.put("loginUserReqs", loginUserReqs);
				return loginUserReqs;
			}
		}
		
		
		
		if(emp_code!=null  && dateFrom!=null && dateTo!=null && codeFrom!=null && codeTo!=null && statusId!=null){
			if((emp_code.equals(""))&&(dateFrom.equals(""))&&(dateTo.equals(""))&&(codeTo.equals(""))&&(codeFrom.equals(""))&&(statusId.equals(""))){
				loginUserReqs= getRequests(null,null, Long.parseLong(requestType),null,null,null,null,null,null,null,null);
				//model.put("loginUserReqs", allRequests);
				return loginUserReqs;
			}
		}
		
		
		
		
		if(emp_code!=null  && dateFrom!=null && dateTo!=null){
			if((!emp_code.equals(""))&&(!dateFrom.equals(""))&&(!dateTo.equals(""))){
				
				Date fromDate = null;
				Date toDate = null;
				mCalDate.setDateTimeString(dateFrom,new Boolean(true));
				fromDate = mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
				log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
				//toDateStr = toDateString+ " 23:59";
				mCalDate.setDateTimeString(dateTo,new Boolean(false));
				toDate= mCalDate.getDate();
			
//				loginUserReqs= getPagedRequestsByDatePeriodAndRequestTypeAndEmpCode(fromDate, toDate, new Long(requestType), emp_code,pageNumber,pageSize);
				loginUserReqs= getRequests( null,null,new Long(requestType), fromDate, toDate,null,null,emp_code,null,null,null);
				//model.put("loginUserReqs", allRequests);
				return loginUserReqs;
			}
		}
		
		if(emp_code!=null  && exactDateFrom!=null && exactDateTo!=null){
			if((!emp_code.equals(""))&&(!exactDateFrom.equals(""))&&(!exactDateTo.equals(""))){
				
				Date fromDate = null;
				Date toDate = null;
				mCalDate.setDateTimeString(exactDateFrom,new Boolean(true));
				fromDate = mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
				log.debug(">>>>>>>>>>>>>toDateString "+ exactDateTo);
				//toDateStr = toDateString+ " 23:59";
				mCalDate.setDateTimeString(exactDateTo,new Boolean(false));
				toDate= mCalDate.getDate();
			
//				loginUserReqs= getPagedRequestsByExactDatePeriodAndRequestTypeAndEmpCode(fromDate, toDate, new Long(requestType), emp_code,pageNumber,pageSize);
				loginUserReqs= getRequests( null,null,new Long(requestType), fromDate, toDate,null,null,emp_code,null,null,null);
				//model.put("loginUserReqs", allRequests);
				return loginUserReqs;
			}
		}
		
		//Lotus //////////////////////////////////
		List list=new ArrayList();
		list.add("empCode");
		/////////////////////////////////////////
		
		if(statusId!=null && !statusId.equals("")){
//			loginUserReqs=getObjectsByTwoParametersOrderedByFieldList(LoginUsersRequests.class, "approved", new Long(statusId),"request_id.id",new Long(requestType),list);
			loginUserReqs = getRequests(null, null, null, null, null, null, null, null, null, null, new Long(statusId));
			//model.put("loginUserReqs", allRequests);
			return loginUserReqs;
		}
		return loginUserReqs;
	}
	
	public Map getRequestsForApproval(String requestNumber, String emp_code, String dateFrom, String dateTo, String exactDateFrom, String exactDateTo, 
			String requestType, String codeFrom, String codeTo, String statusId,LoginUsers loggedInUser, List empReqTypeAccs, int pageNumber, int pageSize) {
		MultiCalendarDate mCalDate = new MultiCalendarDate();
		log.debug("dateFrom " + dateFrom + " dateTo " + dateTo + " requestType " + requestType);
		
		Date fromDate = null;
		Date toDate = null;
		Date fromExact = null;
		Date toExact = null;
		Long status = null;
		Long reqType = null;
		if (dateFrom != null && dateTo != null){
			log.debug("dateFrom " + dateFrom + " dateTo " + dateTo + " requestType " + requestType);
			if (!dateFrom.equals("") && !dateTo.equals("") ) {
				log.debug("dateFrom " + dateFrom + " dateTo " + dateTo + " requestType " + requestType);
				
				log.debug(">>>>>>>>>>>>> if ");
				log.debug(">>>>>>>>>>>Valid date format");
				log.debug(">>>>>>>>>>>>>fromDateString "+ dateFrom);
				//fromDateStr = fromDateString +" 00:00";
				mCalDate.setDateTimeString(dateFrom,new Boolean(true));
				fromDate = mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>fromDate "+ fromDate);
				log.debug(">>>>>>>>>>>>>toDateString "+ dateTo);
				//toDateStr = toDateString+ " 23:59";
				mCalDate.setDateTimeString(dateTo,new Boolean(false));
				toDate= mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>toDate "+ toDate);
			}
		}
		Map loginUserReqs = new HashMap();
		if (requestType!= null && !requestType.isEmpty()){
			reqType = Long.parseLong(requestType);
		}
			
		if (exactDateFrom != null && exactDateTo != null){
			if (!exactDateFrom.equals("") && !exactDateTo.equals("") ) {
				
				log.debug(">>>>>>>>>>>>> if ");
				log.debug(">>>>>>>>>>>Valid date format");
				log.debug(">>>>>>>>>>>>>fromDateString "+ exactDateFrom);
				//fromDateStr = fromDateString +" 00:00";
				mCalDate.setDateTimeString(exactDateFrom,new Boolean(true));
				fromExact = mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>fromDate "+ fromExact);
				log.debug(">>>>>>>>>>>>>toDateString "+ exactDateTo);
				//toDateStr = toDateString+ " 23:59";
				mCalDate.setDateTimeString(exactDateTo,new Boolean(false));
				toExact= mCalDate.getDate();
				log.debug(">>>>>>>>>>>>>exactDateTo "+ toExact);
				
			}
		}
		
		if (emp_code!=null && emp_code.isEmpty()) {
			emp_code = null;
		}
		
		if (codeFrom!=null && codeFrom.isEmpty()) {
			codeFrom = null;
		}
		
		if (codeTo!=null && codeTo.isEmpty()) {
			codeTo = null;
		}
		
		//Lotus //////////////////////////////////
		List list=new ArrayList();
		list.add("empCode");
		/////////////////////////////////////////
		
		if(statusId!=null && !statusId.equals("")){
			status = new Long(statusId);
		}
		
		
		loginUserReqs= getPagedRequests(fromDate, toDate,reqType,fromExact,toExact,null,null,emp_code,codeFrom,codeTo,status,empReqTypeAccs,pageNumber,pageSize);
		log.debug("--dateList.size--"+loginUserReqs.size());
		//model.put("loginUserReqs", loginUserReqs);
		return loginUserReqs;
	}
	

	public List getEmpReqTypeAccs(List accessLevels,Long requestType) {
		// TODO Auto-generated method stub
		return requestsApprovalDAO.getEmpReqTypeAccs(accessLevels,requestType);
	}

	public static boolean isOnlyNumbers(String str){
		for(int i = 0 ; i<str.length(); i++){
			char ch = str.charAt(i);
			if(ch < '0' || ch >'9'){ // not a character
				return false;
			}
		}
		return true;
	}
	

	
	public Map approvalsAccessLevels(RequestApproval approval, LoginUsersRequests requestInfo, Employee emp) {

		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

//		Map model = new HashMap();
		Map response = new HashMap();
		
		RestStatus restStatus = new RestStatus();
		
		List approvalList = new ArrayList();
		
		LoginUsers empInfo = new LoginUsers();
		LoginUsers loginUsers = new LoginUsers();
		
		String status = null;
		String accId = null;
		String note = null;
		String last = null;
		
		
		Boolean lastOne = false;
		Boolean done = false;
		//String canCancel = null;
		Long idToBeCanceled = null;
		String cancelApproval = null;
		if(approval.getApprove().equals("0")) {//reject
			cancelApproval = "99";
		}
		
		List empReqAcc = new ArrayList();
		if (requestInfo != null) {
			List<String> orderfieldList = new ArrayList();
			orderfieldList.add(new String("order"));

			empReqAcc = getObjectsByTwoParametersOrderedByFieldList(
							EmpReqTypeAcc.class, "req_id", requestInfo
									.getRequest_id(), "emp_id", requestInfo
									.getLogin_user(), orderfieldList);
			EmpReqApproval empReqApproval = null;

			loginUsers=(LoginUsers) getObjectByParameter(LoginUsers.class, "empCode", emp.getEmpCode());
			Map approvalRequest = new HashMap();
			List<String> ordered1= new ArrayList();
			ordered1.add("emp_id");

			
			orderfieldList.clear();
			orderfieldList.add(new String("level_id"));

			String showbSubmit = "1";
			last = "0";

			log.debug("---- size of list empReqAcc---"+empReqAcc.size());
			if(empReqAcc.size()>0){
				log.debug("---- size of list---"+empReqAcc.size());
			for (int i = 0; i < empReqAcc.size(); i++) {
				log.debug("---- i fo2---"+i);
				try {
					
					/**
					 * Detect if access level is the last order
					 **/
					
					if (i == (empReqAcc.size() - 1)){
						last = "1";
					}
					else{
						last="0";
					}
					/*************************************/

					EmpReqTypeAcc temp = new EmpReqTypeAcc();
					temp = (EmpReqTypeAcc) empReqAcc.get(i);
					log.debug("-----temp id---"+temp.getId());
					log.debug("-----temp group--"+temp.getGroup_id().getId());
					
					empReqApproval = (EmpReqApproval) getObjectByTwoObjects(EmpReqApproval.class,"level_id", temp, "req_id", requestInfo);
					log.debug("------empreqapp-----"+empReqApproval.getId());
					
					log.debug("------id ele wafe2-----"+empReqApproval.getUser_id().getId());
					
					log.debug("------code ele wafe2-----"+empReqApproval.getUser_id().getEmpCode());
//					model.put("approvedBy", empReqApproval.getUser_id().getEmpCode());
					log.debug("------code ele da5el-----"+emp.getEmpCode());
//					model.put("operator", emp.getEmpCode());
					log.debug("---- i t7t---"+i);
					log.debug("---i==empReqAcc.size()-1-------"+(i==empReqAcc.size()-1));
					if((i+1)<empReqAcc.size() || (i==empReqAcc.size()-1)){
						try{
//							model.put("lastOne", "false");
							lastOne = false;
							EmpReqTypeAcc nextTemp=(EmpReqTypeAcc)empReqAcc.get(i+1);
							log.debug("-----nextTemp id---"+nextTemp.getId());
							log.debug("-----nextTemp group--"+nextTemp.getGroup_id().getId());
							EmpReqApproval empReqApprovalNext= new EmpReqApproval();
							empReqApprovalNext = (EmpReqApproval) getObjectByTwoObjects(EmpReqApproval.class,"level_id", nextTemp, "req_id", requestInfo);
							log.debug("------empReqApprovalNext.getId()!null nor ''----");
							log.debug("------empReqApprovalNext-----"+empReqApprovalNext.getId());
//							model.put("lastOne", "false");
							lastOne = false;
						}
						catch (Exception e) {
//							model.put("lastOne", "false");
							lastOne = false;
							if(empReqApproval.getUser_id().getEmpCode().equals(emp.getEmpCode())){
								log.debug("---empReqApproval.getReq_id().getPosted()---"+empReqApproval.getReq_id().getPosted());
								if(empReqApproval.getReq_id().getPosted()==0){
									idToBeCanceled=empReqApproval.getId();
									log.debug("------idToBeCanceled-----"+idToBeCanceled);
									approvalRequest.put("idToBeCanceled", idToBeCanceled);
									log.debug("----catch----no empReqApprovalNext-----");
									
									log.debug("--------cancelApproval-before---"+cancelApproval);
									if(cancelApproval!=null && !cancelApproval.equals("")){
										log.debug("--------cancelApproval----"+cancelApproval);
										removeObject(EmpReqApproval.class, idToBeCanceled);
										log.debug("----true---");
										requestInfo.setApproved(new Long(0));
										log.debug("--requestInfo.getApproved()---"+requestInfo.getApproved());
										log.debug("----true---after--");
//										String done=request.getParameter("done");
//										if(done!=null){
//											model.put("done", done);
											done = true;
//										}
									}
								}
								else{
//									model.put("lastOne", "false");
									lastOne = false;
								}
							}else{
//								model.put("lastOne", "false");
								lastOne = false;
							}
						}

					}
					else{
//						model.put("lastOne", "false");
						lastOne = false;
					}
					
					approvalRequest = new HashMap();

					approvalRequest.put("title", temp.getGroup_id().getTitle());
					log.debug("-----title of group-----"+temp.getGroup_id().getTitle());
					approvalRequest.put("id", temp.getId());
					approvalRequest.put("status", empReqApproval.getApproval());
					
					///////////////////////////////////////////////////
					status = empReqApproval.getApproval()+"";
					///////////////////////////////////////////////////
					
					
					log.debug("-----status-empReqApproval.getApproval()--"+empReqApproval.getApproval());
					approvalRequest.put("user", empReqApproval.getUser_id().getName());
					log.debug("-----user-----"+empReqApproval.getUser_id().getName());
					approvalRequest.put("note", empReqApproval.getNote());
					approvalList.add(approvalRequest);

					/**
					 * Detect if access level is finished to hide submit button
					 ***/
					if ((i == (empReqAcc.size() - 1))){
						log.debug("-------last one---- & not posted");
//						model.put("lastOne", "true");
						lastOne = true;
						showbSubmit = "0";
						
						restStatus.setStatus("false");
						restStatus.setCode("309");
						restStatus.setMessage("Request approvals had been finished");
						response.put("Status", restStatus);
						return response;
					}else{
//						model.put("lastOne", "false");
						lastOne = false;
					}
					/**
					 * Detect if last access level is rejected to hide submit
					 * button
					 **/
					if (empReqApproval.getApproval() == 0) {
						log.debug("---empReqApproval.getApproval() == 0----cancelApproval---"+cancelApproval);
						if(requestInfo.getApproved()==0 && (cancelApproval==null || cancelApproval.equals(""))){
							log.debug("------pproved=0-ddd--");
							requestInfo.setApproved(new Long(99));
						}
						saveObject(requestInfo);
						showbSubmit = "0";
						restStatus.setStatus("false");
						restStatus.setCode("310");
						restStatus.setMessage("Request had already been rejected");
						response.put("Status", restStatus);
						return response;
//						break;

					}else if(i == (empReqAcc.size() - 1)){
						log.debug("---last one ---cancelApproval-"+cancelApproval);
						log.debug("--------requestInfo.getApproved()------"+requestInfo.getApproved());
						if(requestInfo.getApproved()==0 && (cancelApproval==null || cancelApproval.equals(""))){
							log.debug("------pproved=0---");
							requestInfo.setApproved(new Long(1));
						}
						saveObject(requestInfo);
					}
				} catch (Exception e) {
					log.debug("execption " + e);
					e.printStackTrace();
					
					/**
					 * Data of  user which will approve
					 * button
					 **/
//					model.put("lastOne", "false");
					lastOne = false;
					EmpReqTypeAcc temp = new EmpReqTypeAcc();

					temp = (EmpReqTypeAcc) empReqAcc.get(i);
					log.debug("------catch temp ---"+temp.getGroup_id().getId());
					log.debug("------catch title---"+temp.getGroup_id().getTitle());
					
					log.debug("level id " + temp.getGroup_id().getId() + " emp id " + loginUsers.getId());
					List accessLevels= getObjectsByTwoParametersOrderedByFieldList(AccessLevels.class, "level_id",temp.getGroup_id() , "emp_id", loginUsers, ordered1);
					log.debug("access levels size " + accessLevels.size());
//					if(accessLevels.size()>0){
						approvalRequest = new HashMap();

						approvalRequest.put("title", temp.getGroup_id().getTitle());
						
						approvalRequest.put("id", temp.getId());
						log.debug("------catch temp id---"+temp.getId());
//					}
					if(accessLevels.size()>0){
						approvalRequest.put("user", loginUsers.getName());
						log.debug("------catch if user---"+loginUsers.getName());
						approvalRequest.put("status", "2");
						
						//////////////////////////////////////
						status = "2";
						/////////////////////////////////////
						
//						model.put("lastOne", "false");
						lastOne = false;
					}
					else{
						approvalRequest.put("user", loginUsers.getName());
						log.debug("------catch else user---"+loginUsers.getName());
						approvalRequest.put("status", "3");
						
						/////////////////////////////////////
						status="3";
						////////////////////////////////////
						
						showbSubmit = "0";
//						model.put("lastOne", "false");
						lastOne = false;
						
						restStatus.setStatus("false");
						restStatus.setCode("308");
						restStatus.setMessage("The logged in employee isn't privileged to approve this type of request");
						response.put("Status", restStatus);
						return response;
					}
					approvalList.add(approvalRequest);

					break;

				}
			  }
			}
			else{
				showbSubmit = "0";
//				model.put("lastOne", "false");
				lastOne = false;
				
				restStatus.setStatus("false");
				restStatus.setCode("308");
				restStatus.setMessage("The logged-in employee isn't priviledged to approve this type of requests");
				response.put("Status", restStatus);
				return response;
			}
			
//			model.put("requestInfo", requestInfo);
//			model.put("showbSubmit", showbSubmit);
//			model.put("data", "1");
//			model.put("last", last);
//			model.put("requestType", requestType);
			
			
//			requestInfo = (LoginUsersRequests) getObject(LoginUsersRequests.class, new Long(reqId));
			log.debug("request+info " + requestInfo.getId());
			
			
		} 
//		else
//			model.put("data", "0");
		
//		String errand=request.getParameter("errand");
//		model.put("errand", errand);
		
		
		
//		model.put("approvalList", approvalList);

		
		
		/////////////////////////////Submitting transaction///////////////////////////////////
		
		
//		test="${record.status==0 && record.user==emp && done!='true' && posted!=1 && approvedBy==operator && approvedBy!='' 
//		&&operator!='' && approvedBy!=null &&operator!=null}"
		Iterator iter = approvalList.iterator();
		log.debug("approval list size " + approvalList.size());
		while (iter.hasNext()) {
			Map  approvalRequest = (HashMap)iter.next();
			status = (String)approvalRequest.get("status");
			log.debug("status " + status);
//			user = (String)approvalRequest.get("user"); //emp name
			accId = approvalRequest.get("id")+"";
			log.debug("acc id " + accId);
			//record.status==2 && posted!=1
			if (status!=null && !status.isEmpty() && status.equals("2") && (requestInfo.getPosted()==null || !requestInfo.getPosted().equals(new Long("1")))) {
				LoginUsersRequests requestOb = requestInfo;
				EmpReqTypeAcc empReqTypeAcc = new EmpReqTypeAcc();
				log.debug("requestOb " + requestOb);
				requestOb = requestInfo;
				log.debug("requestOb 2 " + requestOb);
//				List empReqAcc
				empReqTypeAcc = (EmpReqTypeAcc) getObject(
						EmpReqTypeAcc.class, new Long(accId));

						EmpReqApproval empReqApproval = new EmpReqApproval();

				empReqApproval.setApproval(new Integer(status));
				empReqApproval.setReq_id(requestOb);
				empReqApproval.setLevel_id(empReqTypeAcc);
				empReqApproval.setUser_id(loginUsers);
				empReqApproval.setNote(approval.getNotes());
				empReqApproval.setApproval(new Integer(status));

				saveObject(empReqApproval);
				
				
				if(approval.getApprove().equals("0")){

					requestOb.setApproved(new Long(99));

					saveObject(requestOb);

				}else {

					requestOb.setApproved(new Long(1));
					saveObject(requestOb);
				}
				
				
				restStatus.setStatus("true");
				restStatus.setCode("200");
				restStatus.setMessage("Successful Transaction");
				response.put("Status", restStatus);
				String errand ="";
				if(requestOb.getVacation()!=null && !requestOb.getVacation().equals("")){
					if(requestOb.getVacation().getVacation().equals("999")){
						errand="true";
					}
				}
				return response;
			}
		}
		//////////////////////////////////////////////////////////////////////////////////////
	
		return response;
		
	}
	
	public Map getVacInfo(LoginUsersRequests requestInfo) {
		Map model = new HashMap();
		if (requestInfo!= null && requestInfo.getVacation()!=null){
			String vacId =  requestInfo.getVacation().getVacation();
			
			log.debug("vac id " + vacId);
		
			Date from_date = requestInfo.getFrom_date();
			if (from_date == null) {
				from_date = requestInfo.getPeriod_from();
			}
			log.debug("from_date " + from_date);
			
			
			log.debug("emp code " + requestInfo.getEmpCode());
			
			Long vacLimit = getVacationLimit(requestInfo.getEmpCode(), requestInfo.getId(), vacId, from_date);
			Long vacConsumed = getEmpVacation(requestInfo.getEmpCode(), requestInfo.getId(), vacId, from_date);
			Long vacCredit = vacLimit - vacConsumed;
			
			Map output = new HashMap();
			output.put("vacLimit", vacLimit);
			output.put("vacConsumed", vacConsumed);
			output.put("vacCredit", vacCredit);
			RestStatus restStatus = new RestStatus();
			restStatus.setStatus("true");
			restStatus.setCode("200");
			restStatus.setMessage("Successful Transaction");
			model.put("Response", output);
			model.put("Status", restStatus);
			return model;
		} else {
			RestStatus restStatus = new RestStatus();
			restStatus.setStatus("false");
			restStatus.setCode("303");
			restStatus.setMessage("Null/Empty input parameter");
			model.put("Status", restStatus);
			return model;
		}
	}
}