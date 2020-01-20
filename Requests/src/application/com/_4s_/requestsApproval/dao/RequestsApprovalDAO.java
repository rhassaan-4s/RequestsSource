package com._4s_.requestsApproval.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HRGeographicalLevel;
import com._4s_.HR.model.HRInternalDivision;
import com._4s_.HR.model.HRInternalLevel;
import com._4s_.HR.model.HRQualificationDivision;
import com._4s_.HR.model.HRQualificationLevel;
import com._4s_.HR.model.HRSpecialtyDivision;
import com._4s_.HR.model.HRSpecialtyLevel;
import com._4s_.HR.model.HRVacation;
import com._4s_.common.dao.BaseDAO;
import com._4s_.common.model.Employee;
import com._4s_.restServices.json.AttendanceRequest;
import com._4s_.restServices.json.RequestsApprovalQuery;

@Transactional
public interface RequestsApprovalDAO extends BaseDAO {
	
	public List getRoot(final String className);
	public List getAllLeafs(final Class clazz);
	public HRInternalLevel getLastLevel();
	public HRInternalLevel getLevelByLevelNo(final Integer levelNo);
	public List getChilderenByParent(final Long parentId,final Class clazz);
	public List getParents(final Class clazz);
	public List getChildrenAndGrandChildrenByParentLongCode(final String parentLongCode,final Class clazz);
	public HRQualificationLevel getQualLastLevel();
	public HRQualificationLevel getQualLevelByLevelNo(final Integer levelNo);
	public HRGeographicalLevel getGeoLastLevel();
	public HRGeographicalLevel getGeoLevelByLevelNo(final Integer levelNo);
	public List getAllByCode(final String code,final Class clazz);
	public List getLeafsByParentId(final Long parentId,final String className);
	public HRSpecialtyLevel getSpecialtyLastLevel();
	public HRSpecialtyLevel getSpecialtyLevelByLevelNo(final Integer levelNo);
	public List getLevelsByDivisionParentId(final Integer level,final Class clazz);
	public List getExistingDivisionsForparent(final Class clazz,final String parentCode);
	public List getHigherDivisions(final Class clazz,final Long id,final Integer levelNo);
	public List getCountriesForNationality();
	public List getDivisionChildren(final Class clazz,final String longCode);
	public List getExistingDivisionsForparent(final Class clazz,final Long id,final String code);
	public List getExistingDivisionsForParentForCopy(final Class clazz,final Long parentId,final String code);
	public HRQualificationDivision getQualificationDivisionForTransaction();
	public HRSpecialtyDivision getSpecialtyDivisionForTransaction();
	public HRInternalDivision getInternalDivisionForTransaction();
	public List getExistingDivisionsForNullDivLevelParent(final Class clazz,final Long id,final String code);
	public List getEmployeesForEmployeeVacationAtInstallation(final String empCode,final String empName,final HRInternalDivision division);
	public List getEmployeeVacationAtInstall(final HREmployee employee,final HRVacation vacation);
	public List getEmployeesForEmployeeServiceLength(final String empCode,final String empName);
	public List getEmployeeServiceLength(final HREmployee employee);
	public List getEmployeesForEmployeeVacation(final String empCode,final String empName,final HRInternalDivision division);
	public List getEmployeeVacation(final HREmployee employee,final HRVacation vacation);
	public void changeFlag();
	public List getEmployeesByCodeAndName(final String codeFrom,final String codeTo,final String empName);
	
	public List getFilteredGroupedAInsuranceCala(final String month, final String year, final String insurance, final String emp_code, final String empName, final String insuranceNo, final String subscriptionDate, final String groupBy);
	
	public List getRequestsByDatePeriodAndRequestType(final Date fromDate,final Date toDate, Long requestType);
	public List getRequests(final Date fromDate, final Date toDate, final Long requestType, final Date exactFrom, final Date exactTo, 
			final Date periodFrom, final Date periodTo, String empCode, String codeFrom, String codeTo, Long statusId);
	public Map getPagedRequests(final Date fromDate, final Date toDate, final Long requestType, final Date exactFrom, final Date exactTo, 
			final Date periodFrom, final Date periodTo, String empCode, String codeFrom, String codeTo, Long statusId, String sort, List empReqTypeAccs,String requestNumber
			, boolean isWeb, final int pageNumber, final int pageSize);
	public List getRequestsByExactDatePeriodAndRequestType(Date fromDate, Date toDate, Long requestType);
	public List getRequestsByDatePeriod(final Date fromDate, final Date toDate);
	public List getRequestsByExactDatePeriod(final Date fromDate, final Date toDate);
	public List getRequestsByDatePeriodAndRequestTypeAndEmpCode(Date fromDate,Date toDate, Long requestType,String empCode);
	public List getRequestsByExactDatePeriodAndRequestTypeAndEmpCode(Date fromDate,Date toDate, Long requestType,String empCode);
	public List getRequestsByDatePeriodAndEmpCode(final Date fromDate,final Date toDate, final String empCode);
	public List getRequestsByExactDatePeriodAndEmpCode(final Date fromDate,final Date toDate, final String empCode);
	public List getRequestsByDatePeriodAndRequestTypeForEmployee(final Date fromDate, final Date toDate, final Long requestType, final String empCode);
	public List getRequestsByExactDatePeriodAndRequestTypeForEmployee(final Date fromDate, final Date toDate, final Long requestType, final String empCode);
	public List getRequestsByDatePeriodForEmployee(final Date fromDate, final Date toDate, final String empCode);
	public List getRequestsByExactDatePeriodForEmployee(final Date fromDate, final Date toDate, final String empCode);
	public List getEmployeesByCodes(final String codeFrom,final String codeTo);
	public List getEmployeesByCodesAndDatePeriod(final String codeFrom,final String codeTo,final Date fromDate, final Date toDate);
	public List getEmployeesByCodesAndRequestType(final String codeFrom,final String codeTo, final Long requestType);
	public List getEmployeesByCodesAndDatePeriodAndRequestType(final String codeFrom,final String codeTo,final Date fromDate, final Date toDate, final Long requestType);
	public List getRequestStatus(final Long req_id);
	public void signInOut(AttendanceRequest userRequest);
	public List getEmpReqTypeAccs(List accessLevels,Long requestType);
	public int insertTimeAttend(String hostName, String serviceName,
			String userName, String password, String emp_code, Date date_,
			Date time_, String trans_type);
	public Map checkStartedRequests(RequestsApprovalQuery requestQuery,
			Employee emp);
	
	public Map checkAttendance(Date today, String empCode);
	public Map checkStartedRequestsIncludingAttendance(RequestsApprovalQuery requestQuery,
			Employee emp) ;
//	public List getAttendanceRequests(Date date, String empCode);
}
