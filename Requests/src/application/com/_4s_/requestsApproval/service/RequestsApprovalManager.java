package com._4s_.requestsApproval.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com._4s_.common.service.BaseManager;
import com._4s_.requestsApproval.model.AccessLevels;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.requestsApproval.model.Vacation;
import com._4s_.restServices.json.RequestApproval;
import com._4s_.restServices.json.RequestsApprovalQuery;
import com.jenkov.prizetags.tree.itf.ITree;
import com.jenkov.prizetags.tree.itf.ITreeNode;
@Transactional
public interface RequestsApprovalManager extends BaseManager {
	
	public List getRoot(final String className);
	public List getAllLeafs(final Class clazz);
	public ITree createTreeIfNotFound(HttpServletRequest request,String className);
	public ITree createTree(String className);
	public void createNode(Object higherCategory, ITreeNode treeNode,String className);
	public void deleteNodeFromTree(ITree tree, String deleteId);
	public List generateCodesList(List list,String className);
	public String zeroFill(Object[] codes, int codeLength);
	public HRInternalLevel getLastLevel();
	public HRInternalLevel getLevelByLevelNo(final Integer levelNo);
	public List getCategoryAccountsByParentCategory(final Long parentId,final Class clazz);
	public List getParents(final Class clazz);
	public void addNode(String nodeId, String nodeArDesc,
			String parentId, ITree tree);
	public void updateNodeDescription(String nodeId, String nodeArDesc,ITree tree) ;
	public List getChildrenAndGrandChildrenByParentLongCode(final String parentLongCode,final Class clazz);
	public void changeCode(HRInternalDivision changedInternalDivision,String newCode,String oldLongCode);
	public void changeCode(HRQualificationDivision changedQualificationDivision,String newCode,String oldLongCode);
	public void changeCode(HRGeographicalDivision changedGeographicalDivision,String newCode,String oldLongCode);
	public void changeCode(HRSpecialtyDivision changedSpecialtyDivision,String newCode,String oldLongCode);
	public HRGeographicalLevel getGeoLastLevel();
	public HRGeographicalLevel getGeoLevelByLevelNo(final Integer levelNo);
	public HRQualificationLevel getQualLastLevel();
	public HRQualificationLevel getQualLevelByLevelNo(final Integer levelNo);
	public List getAllByCode(final String code,final Class clazz);
	public List getLeafsByParentId(final Long parentId,final String className);
	public HRSpecialtyLevel getSpecialtyLastLevel();
	public HRSpecialtyLevel getSpecialtyLevelByLevelNo(final Integer levelNo);
	public Boolean deleteLevel(final Long id,final String className);
	public List getLevelsByDivisionParentId(final Integer levelNo,final Class clazz);
	public List getExistingDivisionsForparent(final Class clazz,final String parentCode);
	public List getHigherDivisions(final Class clazz,final Long id,final Integer levelNo);
	public List getCountriesForNationality();
	public List getDivisionChildren(final Class clazz,final String longCode);
	public List getExistingDivisionsForparent(final Class clazz,final Long parentId,final String code);
	public List getExistingDivisionsForParentForCopy(final Class clazz,final Long parentId,final String code);
	public ITree createTreeIfNotFoundForCopy(HttpServletRequest request,String className,Object parent);
	public ITree createTreeForCopy(String className,Object parent);
	public void createNodeForCopy(Object higherCategory, ITreeNode treeNode,String className,Object parent);
	public HRQualificationDivision getQualificationDivisionForTransaction();
	public HRSpecialtyDivision getSpecialtyDivisionForTransaction();
	public HRInternalDivision getInternalDivisionForTransaction();
	public List getExistingDivisionsForNullDivLevelParent(final Class clazz,final Long id,final String code);
	public Boolean deleteYear(final Long id);
	public HRVacation getVacation(Long id);
	public Boolean deleteVacationRules(final Long id);
	public Boolean deleteViolationRules(final Long id);
	public List getEmployeesForEmployeeVacationAtInstallation(final String empCode,final String empName,final HRInternalDivision division);
	public List getEmployeeVacationAtInstall(final HREmployee employee,final HRVacation vacation);
	public List getEmployeesForEmployeeServiceLength(final String empCode,final String empName);
	public List getEmployeeServiceLength(final HREmployee employee);
	public Map getDateContents(Date serviceDate);
	public Date getDateFromContents(Integer year,Integer month,Integer day);
	public List getEmployeesForEmployeeVacation(final String empCode,final String empName,final HRInternalDivision division);
	public List getEmployeeVacation(final HREmployee employee,final HRVacation vacation);
	public void changeFlag();
	public List getEmployeesByCodeAndName(final String codeFrom,final String codeTo,final String empName);
	
	public List getFilteredGroupedAInsuranceCala(final String month, final String year, final String insurance, final String emp_code, final String empName, final String insuranceNo, final String subscriptionDate, final String groupBy);
	
	public List getRequestsByDatePeriodAndRequestType(Date fromDate, Date toDate, Long requestType);
	public List getRequestsByExactDatePeriodAndRequestType(Date fromDate, Date toDate, Long requestType);
	public List getRequestsByDatePeriod(Date fromDate, Date toDate);
	public List getRequestsByExactDatePeriod(Date fromDate, Date toDate);
	public List getRequestsByDatePeriodAndRequestTypeAndEmpCode(Date fromDate,Date toDate, Long requestType,String empCode);
	public List getRequestsByExactDatePeriodAndRequestTypeAndEmpCode(Date fromDate,Date toDate, Long requestType,String empCode);
	public List getRequestsByDatePeriodAndEmpCode(final Date fromDate,final Date toDate, final String empCode);
	public List getRequestsByExactDatePeriodAndEmpCode(final Date fromDate,final Date toDate, final String empCode);
	public List getRequestsByDatePeriodAndRequestTypeForEmployee(Date fromDate, Date toDate, Long requestType, String empCode);
	public List getRequestsByExactDatePeriodAndRequestTypeForEmployee(Date fromDate, Date toDate, Long requestType, String empCode);
	public List getRequestsByDatePeriodForEmployee(Date fromDate, Date toDate, String empCode);
	public List getRequestsByExactDatePeriodForEmployee(Date fromDate, Date toDate, String empCode);
	public List getEmployeesByCodes(final String codeFrom,final String codeTo);
	public List getEmployeesByCodesAndDatePeriod(final String codeFrom,final String codeTo,final Date fromDate, final Date toDate);
	public List getEmployeesByCodesAndRequestType(final String codeFrom,final String codeTo, final Long requestType);
	public List getEmployeesByCodesAndDatePeriodAndRequestType(final String codeFrom,final String codeTo,final Date fromDate, final Date toDate, final Long requestType);
	
	public List getRequestStatus(final Long req_id);
	public String CreateRequestNumber();
	
	public Long getEmpVacation (String empCode, String vacId, Date from_date);
	public Long getVacationLimit (String empCode, String vacId, Date from_date);
	public Long getVacationCredit (String empCode, Long reqId, String vacId, Date from_date);
	public List getTimeAttend (String empCode, Date from_date, Date to_date);
	// copied from lotus /////////////////////////////////
	public List getVacations (String empCode, Long reqId, String vacId, Date from_date);
	public List getVacations (String empCode, Long reqId, Date from_date, Date to_date);
	////////////////////////////////////////////////////
	public int getSalaryFromDay();
	public List getRequestsForApprovalList(String requestNumber, String emp_code, String dateFrom, String dateTo, String exactDateFrom, String exactDateTo, 
			String requestType, String codeFrom, String codeTo, String statusId);
	public Map getRequestsForApproval(String requestNumber, String emp_code, String dateFrom, String dateTo, String exactDateFrom, String exactDateTo, 
			String requestType, String codeFrom, String codeTo, String statusId, String sort,LoginUsers loggedInUser, List empReqTypeAccs, boolean isWeb, int pageNumber, int pageSize);
	public Map approvalsAccessLevels(RequestApproval approval, LoginUsersRequests requestInfo, Employee emp);
	public Map getVacInfo(Vacation vac, Date from_date, String empCode);
	public List getEmpReqTypeAccs(List accessLevels,Long requestType);
	public int insertTimeAttendance(String hostName, String  serviceName, String  userName, String password,String empCode, Date date, Date date2,
			String trans_type);
	public Map checkStartedRequests(RequestsApprovalQuery requestQuery,
			Employee emp);
	public List getEmpReqTypeAcc(Employee emp,String requestType);
	public Map exportToExcelSheet(String reportName, List tableTitle,List results);
	
	public Map checkAttendance(Date today, Long empId, Long reqType);
}

