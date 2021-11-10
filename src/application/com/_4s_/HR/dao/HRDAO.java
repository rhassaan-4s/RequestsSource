package com._4s_.HR.dao;

import java.util.List;

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

public interface HRDAO extends BaseDAO {
	
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
}
