package com._4s_.HR.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com._4s_.common.service.BaseManager;
import com.jenkov.prizetags.tree.itf.ITree;
import com.jenkov.prizetags.tree.itf.ITreeNode;

public interface HRManager extends BaseManager {
	
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
}

