package com._4s_.requestsApproval.dao;

import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.HR.model.AInsuranceCala;
import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HREmployeeJob;
import com._4s_.HR.model.HREmployeeVacation;
import com._4s_.HR.model.HREmployeeVacationInstall;
import com._4s_.HR.model.HRGeographicalDivision;
import com._4s_.HR.model.HRGeographicalLevel;
import com._4s_.HR.model.HRInternalDivision;
import com._4s_.HR.model.HRInternalLevel;
import com._4s_.HR.model.HRQualificationDivision;
import com._4s_.HR.model.HRQualificationLevel;
import com._4s_.HR.model.HRServiceLengthCalculation;
import com._4s_.HR.model.HRSpecialtyDivision;
import com._4s_.HR.model.HRSpecialtyLevel;
import com._4s_.HR.model.HRVacation;
import com._4s_.common.dao.BaseDAOHibernate;
import com._4s_.common.model.Employee;
import com._4s_.common.util.DBUtils;
import com._4s_.common.util.Page;
import com._4s_.requestsApproval.model.EmpReqApproval;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.restServices.json.AttendanceRequest;
import com._4s_.restServices.json.RequestOutput;
//import com._4s_.requestsApproval.model.TimeAttend;
//import com._4s_.stores.model.ViewStoreCardItem;
import com._4s_.restServices.json.RequestsApprovalQuery;
import com._4s_.restServices.json.RestStatus;
import com._4s_.restServices.model.AttendanceStatus;

@Transactional
@Repository
public class RequestsApprovalDAOHibernate extends BaseDAOHibernate implements RequestsApprovalDAO {
	
	private ExternalQueries externalQueries = null;
	
	

	public ExternalQueries getExternalQueries() {
		return externalQueries;
	}


	public void setExternalQueries(ExternalQueries externalQueries) {
		this.externalQueries = externalQueries;
	}


	public List getAllLeafs(final Class clazz){
		log.debug("inside getAllLeafs");
		Criteria criteria = null;
		criteria = getCurrentSession().createCriteria(clazz);
		criteria.createCriteria("parent").add(Restrictions.eq("isLast",new Boolean(true)));
		criteria.addOrder(Order.asc("longCode"));
		log.debug("===========================end getAllLeafs()===============");
		return criteria.list();
	}


	public List getAllLeafsOfCategory(final String catCode, final Class clazz) {
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		criteria.add( Restrictions.like("longCode",catCode , MatchMode.START ) );
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}






	public List getRoot(final String className) {
		log.debug("inside getRoot ");
		Criteria criteria=null;
		if(className.equals("HRInternalDivision"))
		{ criteria = getCurrentSession()
		.createCriteria(HRInternalDivision.class);
		criteria.add(Restrictions.isNull("parent"));
		criteria.addOrder(Order.asc("longCode"));
		}

		if(className.equals("HRGeographicalDivision"))
		{ criteria = getCurrentSession()
		.createCriteria(HRGeographicalDivision.class);
		criteria.add(Restrictions.isNull("parent"));
		criteria.addOrder(Order.asc("longCode"));
		}

		if(className.equals("HRQualificationDivision"))
		{ criteria = getCurrentSession()
		.createCriteria(HRQualificationDivision.class);
		criteria.add(Restrictions.isNull("parent"));
		criteria.addOrder(Order.asc("longCode"));
		}

		if(className.equals("HRSpecialtyDivision"))
		{ criteria = getCurrentSession()
		.createCriteria(HRSpecialtyDivision.class);
		criteria.add(Restrictions.isNull("parent"));
		criteria.addOrder(Order.asc("longCode"));
		}
		return criteria.list();
	}


	public HRInternalLevel getLastLevel() {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>..start getLastLevel()");
		Criteria criteria = getCurrentSession()
				.createCriteria(HRInternalLevel.class);
		criteria.add(Restrictions.eq("isLastLevel", true));
		HRInternalLevel list = (HRInternalLevel) criteria.uniqueResult();
		return list;
	}

	public HRInternalLevel getLevelByLevelNo(final Integer levelNo){
		Criteria criteria = getCurrentSession().createCriteria(HRInternalLevel.class);
		criteria.add(Restrictions.eq("levelNo",levelNo));
		criteria.setMaxResults(1);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List list = (List) criteria.list();
		return (HRInternalLevel) list.iterator().next();
	}

	public HRGeographicalLevel getGeoLastLevel() {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>..start getLastLevel()");

		Criteria criteria = getCurrentSession()
				.createCriteria(HRGeographicalLevel.class);
		criteria.add(Restrictions.eq("isLastLevel", true));
		HRGeographicalLevel list = (HRGeographicalLevel)  criteria.uniqueResult();
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.end getLastLevel()");
		return list;
	}

	public HRGeographicalLevel getGeoLevelByLevelNo(final Integer levelNo){
		Criteria criteria = getCurrentSession().createCriteria(HRGeographicalLevel.class);
		criteria.add(Restrictions.eq("levelNo",levelNo));
		criteria.setMaxResults(1);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List list = criteria.list();
		return (HRGeographicalLevel) list.iterator().next();
	}

	public HRQualificationLevel getQualLastLevel() {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>..start getLastLevel()");
		Criteria criteria = getCurrentSession()
				.createCriteria(HRQualificationLevel.class);
		criteria.add(Restrictions.eq("isLastLevel", true));
		HRQualificationLevel list = (HRQualificationLevel)  criteria.uniqueResult();
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.end getLastLevel()");
		return list;
	}

	public HRQualificationLevel getQualLevelByLevelNo(final Integer levelNo){
		Criteria criteria = getCurrentSession().createCriteria(HRQualificationLevel.class);
		criteria.add(Restrictions.eq("levelNo",levelNo));
		criteria.setMaxResults(1);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List list = (List)  criteria.list();
		return (HRQualificationLevel) list.iterator().next();
	}

	public HRSpecialtyLevel getSpecialtyLastLevel() {
		Criteria criteria = getCurrentSession()
				.createCriteria(HRSpecialtyLevel.class);
		criteria.add(Restrictions.eq("isLastLevel", true));
		HRSpecialtyLevel list = (HRSpecialtyLevel) criteria.uniqueResult();
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.end getLastLevel()");
		return list;
	}

	public HRSpecialtyLevel getSpecialtyLevelByLevelNo(final Integer levelNo){
		Criteria criteria = getCurrentSession().createCriteria(HRSpecialtyLevel.class);
		criteria.add(Restrictions.eq("levelNo",levelNo));
		criteria.setMaxResults(1);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List list = (List) criteria.list();
		return (HRSpecialtyLevel) list.iterator().next();
	}

	public List getChilderenByParent(final Long parentId,final Class clazz){
		Criteria criteria = null;

		criteria = getCurrentSession().createCriteria(clazz);
		criteria.createCriteria("parent").add(Restrictions.eq("id",parentId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getParents(final Class clazz){
		log.debug("inside getParents ");
		Criteria criteria = null;

		criteria = getCurrentSession().createCriteria(clazz);
		criteria.add(Restrictions.isNull("parent"));


		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getChildrenAndGrandChildrenByParentLongCode(final String parentLongCode,final Class clazz)
	{
		log.debug("inside getChildrenAndGrandChildrenByParentLongCode ");
		Criteria criteria = null;

		criteria = getCurrentSession().createCriteria(clazz);
		criteria.add(Restrictions.like("longCode",parentLongCode+"%"));


		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}


	public List getAllByCode(final String code,final Class clazz){
		Criteria criteria = criteria = getCurrentSession().createCriteria(clazz);
		criteria.add(Restrictions.eq("longCode",code));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}


	public List getLeafsByParentId(final Long parentId,final String className) {
		log.debug("inside getLeafsByParentId");
		Criteria criteria=null;
		if(className.equals("HRInternalDivision"))
		{
			criteria = getCurrentSession().createCriteria(HRInternalDivision.class);
		}

		else if(className.equals("HRQualificationDivision"))
		{
			criteria = getCurrentSession().createCriteria(HRQualificationDivision.class);
		}

		else if(className.equals("HRGeographicalDivision"))
		{
			criteria = getCurrentSession().createCriteria(HRGeographicalDivision.class);
		}

		else if(className.equals("HRSpecialtyDivision"))
		{
			criteria = getCurrentSession().createCriteria(HRSpecialtyDivision.class);
		}
		criteria.createCriteria("parent").add(Restrictions.eq("id",parentId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("longCode"));
		return criteria.list();
	}

	public List getLevelsByDivisionParentId(final Integer levelNo,final Class clazz){
		log.debug("inside LevelsByDivisionParentId");
		Criteria criteria = null;
		criteria = getCurrentSession().createCriteria(clazz);
		criteria.add(Restrictions.ge("levelNo",levelNo));
		criteria.addOrder(Order.asc("levelNo"));
		return criteria.list();
	}

	public List getExistingDivisionsForparent(final Class clazz,final String parentCode){
		log.debug("inside getExistingDivisionsForparent");  
		log.debug("parentCode>>>>>>>>>>"+parentCode);
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		criteria.add(Restrictions.isNull("divisionLevel"));
		criteria.add(Restrictions.eq("code",parentCode));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getExistingDivisionsForParentForCopy(final Class clazz,final Long parentId,final String code){
		log.debug("inside getExistingDivisionsForparentforcopy");  
		log.debug("parentId>>>>>>>>>>"+parentId);
		log.debug("code>>>>>>>>>>>>>>>>>>>"+code);
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		criteria.add(Restrictions.isNull("divisionLevel"));
		criteria.add(Restrictions.eq("code", code));
		criteria.add(Restrictions.eq("id",parentId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getExistingDivisionsForparent(final Class clazz,final Long id,final String code){
		log.debug("inside getExistingDivisionsForparent");  
		log.debug("id>>>>>>>>>>"+id);
		log.debug("levelID>>>>>>>>>>>>>>>>>>>>>>>>"+code);
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		criteria.add(Restrictions.isNull("divisionLevel"));
		criteria.add(Restrictions.eq("code", code));
		criteria.createCriteria("parent").add(Restrictions.eq("id",id));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getExistingDivisionsForNullDivLevelParent(final Class clazz,final Long id,final String code){
		log.debug("inside getExistingDivisionsForNullDivLevelParent");  
		log.debug("id>>>>>>>>>>"+id);
		log.debug("levelID>>>>>>>>>>>>>>>>>>>>>>>>"+code);
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		criteria.add(Restrictions.isNull("divisionLevel"));
		criteria.add(Restrictions.eq("code", code));
		criteria.add(Restrictions.eq("id",id));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}


	public List getHigherDivisions(final Class clazz,final Long id,final Integer levelNo)
	{
		log.debug("inside getHigherDivisions");  
		log.debug("Id>>>>>>>>>>"+id);
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		criteria.add(Restrictions.isNotNull("divisionLevel"));
		criteria.add(Restrictions.ne("id", id));
		criteria.createCriteria("divisionLevel").add(Restrictions.lt("levelNo", levelNo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}


	public List getCountriesForNationality()
	{
		log.debug("inside getCountriesForNationality");  

		Criteria criteria = getCurrentSession().createCriteria(HRGeographicalDivision.class);
		criteria.add(Restrictions.isNotNull("divisionLevel"));
		criteria.createCriteria("divisionLevel").add(Restrictions.eq("isNationalityCountry", new Boolean(true)));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getDivisionChildren(final Class clazz,final String longCode)
	{
		log.debug("inside getDivisionChildren");  
		log.debug("longCode>>>>>>>>>>"+longCode);

		Criteria criteria = getCurrentSession().createCriteria(clazz);
		criteria.add(Restrictions.like("longCode", longCode,MatchMode.START));
		criteria.addOrder(Order.asc("longCode"));	
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public HRSpecialtyDivision getSpecialtyDivisionForTransaction(){
		Criteria criteria = getCurrentSession().createCriteria(HRSpecialtyDivision.class);
		criteria.createCriteria("divisionLevel").addOrder(Order.desc("levelNo"));
		criteria.setMaxResults(1);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List list = (List)criteria.list();
		return (HRSpecialtyDivision) list.iterator().next();
	}

	public HRQualificationDivision getQualificationDivisionForTransaction(){
		Criteria criteria = getCurrentSession().createCriteria(HRQualificationDivision.class);
		criteria.createCriteria("divisionLevel").addOrder(Order.desc("levelNo"));
		criteria.setMaxResults(1);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List list = (List) criteria.list();
		return (HRQualificationDivision) list.iterator().next();
	}

	public HRInternalDivision getInternalDivisionForTransaction(){
		Criteria criteria = getCurrentSession().createCriteria(HRInternalDivision.class);
		criteria.createCriteria("divisionLevel").addOrder(Order.desc("levelNo"));
		criteria.setMaxResults(1);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List list = (List) criteria.list();
		return (HRInternalDivision) list.iterator().next();
	}


	public List getEmployeesForEmployeeVacationAtInstallation(final String empCode,final String empName,final HRInternalDivision division)
	{
		Criteria criteria = getCurrentSession().createCriteria(HREmployee.class);
		if(empCode!=null && !empCode.equals(""))
		{
			criteria.add(Restrictions.eq("empCode",empCode));
		}
		if(empName!=null && !empName.equals(""))
		{
			criteria.add(Restrictions.eq("name",empName));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}



	public List getEmployeeVacationAtInstall(final HREmployee employee,final HRVacation vacation)
	{
		Criteria criteria = getCurrentSession().createCriteria(HREmployeeVacationInstall.class);
		if(employee!=null)
		{
			criteria.add(Restrictions.eq("employee",employee));
		}
		if(vacation!=null)
		{
			criteria.add(Restrictions.eq("vacation",vacation));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getEmployeesForEmployeeServiceLength(final String empCode,final String empName)
	{
		Criteria criteria = getCurrentSession().createCriteria(HREmployee.class);
		if(empCode!=null && !empCode.equals(""))
		{
			criteria.add(Restrictions.eq("empCode",empCode));
		}
		if(empName!=null && !empName.equals(""))
		{
			criteria.add(Restrictions.eq("name",empName));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getEmployeeServiceLength(final HREmployee employee)
	{
		Criteria criteria = getCurrentSession().createCriteria(HRServiceLengthCalculation.class);
		if(employee!=null)
		{
			criteria.add(Restrictions.eq("employee",employee));
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}


	public List getEmployeesForEmployeeVacation(final String empCode,final String empName,final HRInternalDivision division)
	{
		Criteria criteria = getCurrentSession().createCriteria(HREmployee.class);
		if(empCode!=null && !empCode.equals(""))
		{
			criteria.add(Restrictions.eq("empCode",empCode));
		}
		if(empName!=null && !empName.equals(""))
		{
			criteria.add(Restrictions.eq("name",empName));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}


	public List getEmployeeVacation(final HREmployee employee,final HRVacation vacation)
	{
		Criteria criteria = getCurrentSession().createCriteria(HREmployeeVacation.class);
		if(employee!=null)
		{
			criteria.add(Restrictions.eq("employee",employee));
		}
		if(vacation!=null)
		{
			criteria.add(Restrictions.eq("vacation",vacation));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public void changeFlag() {
		StringBuffer sql = new StringBuffer("update  INTEGRATE_WITH_DESKTOP set CALC_PAYROLL='Y'");
		try {
			Statement statement = DBUtils.createStatement();
			statement.execute(sql.toString());
			statement.close();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		log.debug("sql>>>>>>>>>>>>>>>"+sql);

	}

	public List getEmployeesByCodeAndName(final String codeFrom,final String codeTo,final String empName)
	{
		Criteria criteria = getCurrentSession().createCriteria(HREmployee.class);
		if(empName!=null && !empName.equals(""))
		{
			criteria.add(Restrictions.eq("name",empName));
		}
		if(codeFrom!=null && !codeFrom.equals(""))
		{
			criteria.add(Restrictions.ge("empCode",codeFrom));
		}

		if(codeTo!=null && !codeTo.equals(""))
		{
			criteria.add(Restrictions.le("empCode",codeTo));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getFilteredGroupedAInsuranceCala(final String month, final String year, final String insurance, final String emp_code, final String empName, final String insuranceNo, final String subscriptionDate, final String groupBy){

		List aicList = new ArrayList();
		List empList = new ArrayList();

		log.debug("HR_DAO>> Month and Year != Null: "+month+"-"+year);

		if((insurance != null && !insurance.equals(""))||(emp_code!=null && !emp_code.equals(""))
				||(empName!=null && !empName.equals(""))||(insuranceNo!=null && !insuranceNo.equals(""))){
			Criteria criteria = getCurrentSession().createCriteria(HREmployee.class);
			if(insurance != null && !insurance.equals("")){
				log.debug("HR_DAO>> Insurance_Code != Null: "+insurance);
				criteria.add(Restrictions.eq("insuranceCode",Long.parseLong(insurance)));
			}
			if(emp_code!=null && !emp_code.equals("")){
				log.debug("HR_DAO>> Emp_Code != Null: "+emp_code);
				criteria.add(Restrictions.eq("empCode",emp_code));
			}
			if(empName!=null && !empName.equals("")){
				log.debug("HR_DAO>> Emp_Name != Null: "+empName);
				criteria.add(Restrictions.eq("name",empName));
			}
			if(insuranceNo!=null && !insuranceNo.equals("")){
				log.debug("HR_DAO>> Insurance_Number != Null: "+insuranceNo);
				criteria.add(Restrictions.eq("insuranceNumber",insuranceNo));
			}
			if(groupBy != null && groupBy.length() != 0){
				//							if(groupBy.equals("1")){
				//								criteria.addOrder(Property.forName("").asc());
				//						    }else if(groupBy.equals("2")){
				//								criteria.addOrder(Property.forName("").asc());
				//							}else
				if(groupBy.equals("3")){
					log.debug("HR_DAO>> Group_By != Null: "+groupBy);
					criteria.addOrder(Property.forName("currentEmpJob").asc());
				}
				//							else if(groupBy.equals("4")){
				//								criteria.addOrder(Property.forName("").asc());
				//							}
			}
			empList = (List)criteria.list();
		}
		log.debug("HR_DAO>> ((1)) Emp List Size: "+empList.size());

		List orderlist = new ArrayList();
		orderlist.add("emp_code");

		if(subscriptionDate!=null && !subscriptionDate.equals("")){
			log.debug("HR_DAO>> Subscription_Date != Null: "+subscriptionDate);
			String subDate[]=subscriptionDate.split("-");
			log.debug("HR_DAO>> Subscription_Date after split: "+subDate[0]+"  -  "+subDate[1]+"  -  "+subDate[2]);
			List tempList = new ArrayList();
			if(empList != null && empList.size() > 0){
				for(int i=0; i<empList.size(); i++){
					HREmployee e = (HREmployee) empList.get(i);
					log.debug("HR_DAO>> "+e);
					if(e != null){
						String empSubDate[] = e.getSubscriptionDate().toString().split("-");
						log.debug("HR_DAO>> Emp_Subscription_Date after split: "+empSubDate[0]+"  -  "+empSubDate[1]+"  -  "+empSubDate[2].toString().split(" ")[0]);
						if((Integer.parseInt(subDate[0])==Integer.parseInt(empSubDate[2].split(" ")[0]))&&(Integer.parseInt(subDate[1])==Integer.parseInt(empSubDate[1]))&&(Integer.parseInt(subDate[2])==Integer.parseInt(empSubDate[0]))){
							tempList.add(e);
						}
					}
				}
				empList = tempList;
			}else{
				List aics = getObjectsByTwoParametersOrderedByFieldList(AInsuranceCala.class, "month", month, "year", year, orderlist);
				for(int i=0; i<aics.size(); i++){
					AInsuranceCala a = (AInsuranceCala) aics.get(i);
					HREmployee e = (HREmployee) getObjectByParameter(HREmployee.class, "empCode", a.getEmp_code());
					String empSubDate[] = e.getSubscriptionDate().toString().split("-");
					log.debug("HR_DAO>> Emp_Subscription_Date after split: "+empSubDate[0]+"  -  "+empSubDate[1]+"  -  "+empSubDate[2].toString().split(" ")[0]);
					if((Integer.parseInt(subDate[0])==Integer.parseInt(empSubDate[2].split(" ")[0]))&&(Integer.parseInt(subDate[1])==Integer.parseInt(empSubDate[1]))&&(Integer.parseInt(subDate[2])==Integer.parseInt(empSubDate[0]))){
						empList.add(e);
					}
				}
				if(groupBy!=null && !groupBy.equals("")){
					log.debug("HR_DAO>> Group_By != Null: "+groupBy);
					String arr[][] = new String [empList.size()][2];
					for(int i=0; i<empList.size(); i++){
						HREmployee e = (HREmployee) empList.get(i);
						log.debug("HR_DAO>> Emp: "+e);
						//						if(groupBy.equals("1")){
						//							arr[i][0] = e.get__().getId();
						//							arr[i][1] = Long.parseLong(e.getEmpCode());
						//					    }else if(groupBy.equals("2")){
						//							arr[i][0] = e.get__().getId();
						//							arr[i][1] = Long.parseLong(e.getEmpCode());
						//						}else
						if(groupBy.equals("3")){
							if(e.getCurrentEmpJob().getId() != null){
								arr[i][0] = e.getCurrentEmpJob().getId()+"";
								arr[i][1] = e.getEmpCode();
								log.debug("HR_DAO>> job "+arr[i][0]+" code "+arr[i][1]);
							}	
						}	
						//						else if(groupBy.equals("4")){
						//							arr[i][0] = e.get__().getId();
						//							arr[i][1] = Long.parseLong(e.getEmpCode());
						//						}
					}
					Arrays.sort(arr, new Comparator<String[]>() {
						public int compare(final String[] entry1, final String[] entry2) {
							final String job1 = entry1[0];
							final String job2 = entry2[0];
							return job1.compareTo(job2);
						}
					});

					empList = new ArrayList();
					for(int i=0; i<arr.length; i++){
						orderlist = new ArrayList();
						orderlist.add("empCode");
						HREmployeeJob job = (HREmployeeJob) getObjectByParameter(HREmployeeJob.class, "id", Long.parseLong(arr[i][0]));
						log.debug("HR_DAO>> Emp_Job"+job.getId());
						HREmployee e = (HREmployee) getObjectByTwoParameters(HREmployee.class, "currentEmpJob", job, "empCode", arr[i][1]+"");
						log.debug("HR_DAO>> Emp: "+e);
						empList.add(e);
					}
				}	
			}
		}
		log.debug("HR_DAO>> ((2)) Emp List Size: "+empList.size());

		for(int i=0; i<empList.size(); i++){
			HREmployee e = (HREmployee) empList.get(i);
			orderlist = new ArrayList();
			orderlist.add("month");
			List aics = getObjectsByThreeParametersOrderedByFieldList(AInsuranceCala.class, "month", month, "year", year, "emp_code", e.getEmpCode(), orderlist);

			if(aics != null && aics.size() == 1){
				log.debug("HR_DAO>> AIC != null: "+aics);
				aicList.add(aics.get(0));
			}
		}
		log.debug("HR_DAO>> ((3)) Aic List Size: "+aicList.size());

		if((empList == null || empList.size() == 0) && ((insurance == null || insurance.equals(""))&&(emp_code==null 
				|| emp_code.equals(""))&&(empName==null || empName.equals(""))&&(insuranceNo==null 
				|| insuranceNo.equals(""))&&(subscriptionDate==null || subscriptionDate.equals("")))){
			log.debug("HR_DAO>> ALL NULL BUT MONTH AND YEAR");
			aicList = getObjectsByTwoParametersOrderedByFieldList(AInsuranceCala.class, "month", month, "year", year, orderlist);

			if(groupBy!=null && !groupBy.equals("")){
				log.debug("HR_DAO>> Group_By != Null: "+groupBy);
				String arr[][] = new String [aicList.size()][2];
				for(int i=0; i<aicList.size(); i++){
					AInsuranceCala a = (AInsuranceCala) aicList.get(i);
					log.debug("HR_DAO>> AIC: "+a);
					HREmployee e = (HREmployee) getObjectByParameter(HREmployee.class, "empCode", a.getEmp_code());
					log.debug("HR_DAO>> Emp: "+e);
					if(groupBy.equals("3")){
						if(e.getCurrentEmpJob().getId() != null){
							arr[i][0] = e.getCurrentEmpJob().getId()+"";
							arr[i][1] = e.getEmpCode();
							log.debug("HR_DAO>> job "+arr[i][0]+", code "+arr[i][1]);
						}	
					}	
				}
				Arrays.sort(arr, new Comparator<String[]>() {
					public int compare(final String[] entry1, final String[] entry2) {
						final String job1 = entry1[0];
						final String job2 = entry2[0];
						return job1.compareTo(job2);
					}
				});
				aicList = new ArrayList();
				for(int i=0; i<arr.length; i++){
					HREmployeeJob job = (HREmployeeJob) getObjectByParameter(HREmployeeJob.class, "id", Long.parseLong(arr[i][0]));
					log.debug("HR_DAO>> Emp_Job: "+job);
					orderlist = new ArrayList();
					orderlist.add("empCode");
					HREmployee e = (HREmployee) getObjectByTwoParameters(HREmployee.class, "currentEmpJob", job, "empCode", arr[i][1]+"");
					log.debug("HR_DAO>> Emp: "+e);
					orderlist = new ArrayList();
					orderlist.add("emp_code");
					List aics =  getObjectsByThreeParametersOrderedByFieldList(AInsuranceCala.class, "month", month, "year", year, "emp_code", e.getEmpCode(), orderlist);
					if(aics != null && aics.size() == 1){
						log.debug("HR_DAO>> AIC: "+aics.get(0));
						aicList.add(aics.get(0));
					}
				}
			}
		}
		log.debug("HR_DAO>> ((4)) Aic List Size: "+aicList.size());

		return  aicList;
	}

	@Transactional
	public List getRequestsByDatePeriodAndRequestType(final Date fromDate, final Date toDate, final Long requestType){
		Calendar cFrom= Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);
		//cFrom.set(Calendar.AM_PM, -1);
		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
		final Date dateFrom=cFrom.getTime();

		log.debug("------dateFrom---"+dateFrom);

		Calendar cTo= Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo=cTo.getTime();
		log.debug("------dateTo---"+dateTo);
		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List list =new ArrayList();
		try {
			final Date startDate =(Date) format.parse(format.format(dateFrom));
			final Date endDate = (Date)format.parse(format.format(dateTo));

			Criteria criteria = getCurrentSession()
					.createCriteria(LoginUsersRequests.class);
			criteria.add(Expression.ge("request_date", startDate));
			criteria.add(Expression.le("request_date", endDate));
			if (requestType.equals(1)){
				criteria.add(Restrictions.or(
						Restrictions.eq("request_id.id", requestType),
						Restrictions.eq("request_id.id", 4))
						);
			} else {
				criteria.add(Restrictions.eq("request_id.id", requestType));
			}
			criteria.addOrder(Property.forName("period_from").asc());
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list = (List)criteria.list();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	public List getRequests(Date fromDate, Date toDate, Long requestType,
			Date exactFrom, Date exactTo, Date periodFrom, Date periodTo,
			String empCode, String codeFrom, String codeTo, Long statusId) {
		Calendar cFrom= Calendar.getInstance();
		
		Date dateFrom=null;
		Date dateTo= null;
		if (fromDate !=null) {
			cFrom.setTime(fromDate);
			cFrom.set(Calendar.HOUR_OF_DAY, 0);
			cFrom.set(Calendar.MINUTE, 0);
			cFrom.set(Calendar.SECOND, 0);
			dateFrom=cFrom.getTime();
		}
		
		log.debug("------dateFrom---"+dateFrom);

		Calendar cTo= Calendar.getInstance();
		if (toDate!=null) {
			cTo.setTime(toDate);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);
			dateTo=cTo.getTime();
		}
		
		log.debug("------dateTo---"+dateTo);
		
		Date exFrom=null;
		Date exTo= null;
		
		if (exactFrom !=null) {
			cFrom.setTime(exactFrom);
			cFrom.set(Calendar.HOUR_OF_DAY, 0);
			cFrom.set(Calendar.MINUTE, 0);
			cFrom.set(Calendar.SECOND, 0);
			exFrom=cFrom.getTime();
		}
		
		log.debug("------exFrom---"+exFrom);

		if (exactTo!=null) {
			cTo.setTime(exactTo);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);
			exTo=cTo.getTime();
		}
		log.debug("------exTo---"+exTo);
		
		Date pFrom=null;
		Date pTo= null;
		
		if (periodFrom !=null) {
			cFrom.setTime(periodFrom);
			cFrom.set(Calendar.HOUR_OF_DAY, 0);
			cFrom.set(Calendar.MINUTE, 0);
			cFrom.set(Calendar.SECOND, 0);
			pFrom=cFrom.getTime();
		}
		
		log.debug("------pfrom---"+pFrom);

		if (periodTo!=null) {
			cTo.setTime(periodTo);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);
			pTo=cTo.getTime();
		}
		log.debug("------Pto---"+pTo);
		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		Map map = new HashMap();
		List list =new ArrayList();
	
		
		try {

			Criteria criteria = getCurrentSession()
					.createCriteria(LoginUsersRequests.class);
			
			///////////////////////////////////////////////////////////////////////
			if (fromDate!=null && toDate!=null){
				final Date startDate =(Date) format.parse(format.format(dateFrom));
				final Date endDate = (Date)format.parse(format.format(dateTo));
				criteria.add(Expression.ge("request_date", startDate));
				criteria.add(Expression.le("request_date", endDate));
			}
			if (pFrom!=null && pTo!=null){
				final Date startDate =(Date) format.parse(format.format(pFrom));
				final Date endDate = (Date)format.parse(format.format(pTo));
				criteria.add(Expression.ge("period_from", startDate));
				criteria.add(Expression.le("period_from", endDate));
			}
			if (exactFrom!=null && exactTo!=null){
				final Date startDate =(Date) format.parse(format.format(exFrom));
				final Date endDate = (Date)format.parse(format.format(exTo));
				criteria.add(Expression.ge("from_date", startDate));
				criteria.add(Expression.le("fromDate", endDate));
			}
			/////////////////////////////////////////////////////////////////////////////////
			if(requestType!=null) {
				log.debug("requesttype " + requestType);
				if (requestType.equals(new Long(1))){
					log.debug("requesttype is 1");
					criteria.add(Restrictions.or(
							Restrictions.eq("request_id.id", requestType),
							Restrictions.eq("request_id.id", new Long(4)))
							);
				} else if (requestType.equals(new Long(4))) {
					log.debug("requesttype is 4");
					criteria.add(Restrictions.or(Restrictions.or(
							Restrictions.eq("request_id.id", new Long(1)),
							Restrictions.eq("request_id.id", new Long(2)))
							,Restrictions.eq("request_id.id", new Long(4))));
				} else if (requestType.equals(new Long(5))) {
					log.debug("requesttype is 5");
					criteria.add(Restrictions.or(
							Restrictions.eq("request_id.id", new Long(10)),
							Restrictions.eq("request_id.id", new Long(11)))
							);
				} else {
					log.debug("requesttype is else");
					criteria.add(Restrictions.eq("request_id.id", requestType));
				}
			}
			if(requestType!=null) {
				log.debug("requesttype " + requestType);
				if (requestType.equals(new Long(1))){
					log.debug("requesttype is 1");
					criteria.add(Restrictions.or(
							Restrictions.eq("request_id.id", requestType),
							Restrictions.eq("request_id.id", new Long(4)))
							);
				} else if (requestType.equals(new Long(4))) {
					log.debug("requesttype is 4");
					criteria.add(Restrictions.or(Restrictions.or(
							Restrictions.eq("request_id.id", new Long(1)),
							Restrictions.eq("request_id.id", new Long(2)))
							,Restrictions.eq("request_id.id", new Long(4))));
				} else if (requestType.equals(new Long(5))) {
					log.debug("requesttype is 5");
					criteria.add(Restrictions.or(
							Restrictions.eq("request_id.id", new Long(10)),
							Restrictions.eq("request_id.id", new Long(11)))
							);
				} else {
					log.debug("requesttype is else");
					criteria.add(Restrictions.eq("request_id.id", requestType));
				}
			}
			/////////////////////////////////////////////////////////////////////////////////
			if (empCode!=null && !empCode.isEmpty()) {
				criteria.add(Restrictions.eq("empCode", empCode));
			}
			////////////////////////////////////////////////////////////////////////////////
			if (codeFrom!=null && !codeFrom.isEmpty() && codeTo!=null && !codeTo.isEmpty()) {
				criteria.add(Restrictions.between("empCode", codeFrom, codeTo));
			}
			///////////////////////////////////////////////////////////////////////////////
			if (statusId!=null) {
				criteria.add(Restrictions.eq("approved", statusId));
			}
			///////////////////////////////////////////////////////////////////////////////
			
			criteria.addOrder(Property.forName("period_from").desc());
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list =  criteria.list();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	
	}


	public Map getPagedRequests(final Date fromDate, final Date toDate, final Long requestType, final Date exactFrom, final Date exactTo, 
			final Date periodFrom, final Date periodTo, String empCode, String codeFrom, String codeTo, Long statusId, String sort, List empReqTypeAccs,String requestNumber,boolean isWeb, final int pageNumber, final int pageSize)  {
		Calendar cFrom= Calendar.getInstance();
		
		Date dateFrom=null;
		Date dateTo= null;
		log.debug("------fromDate---"+fromDate);
		log.debug("fromDate " + fromDate + " toDate " + toDate + " requestType " + requestType);
		log.debug("exactFrom " + exactFrom + " exactTo " + exactTo + " requestType " + requestType);
		if (fromDate !=null) {
			cFrom.setTime(fromDate);
			cFrom.set(Calendar.HOUR_OF_DAY, 0);
			cFrom.set(Calendar.MINUTE, 0);
			cFrom.set(Calendar.SECOND, 0);
			dateFrom=cFrom.getTime();
		}
		
		log.debug("------dateFrom---"+dateFrom);

		log.debug("------toDate---"+toDate);
		Calendar cTo= Calendar.getInstance();
		if (toDate!=null) {
			cTo.setTime(toDate);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);
			dateTo=cTo.getTime();
		}
		
		log.debug("------dateTo---"+dateTo);
		
		Date exFrom=null;
		Date exTo= null;
		
		log.debug("------exactfrom---"+exactFrom);
		if (exactFrom !=null) {
			cFrom.setTime(exactFrom);
			cFrom.set(Calendar.HOUR_OF_DAY, 0);
			cFrom.set(Calendar.MINUTE, 0);
			cFrom.set(Calendar.SECOND, 0);
			exFrom=cFrom.getTime();
		}
		
		log.debug("------exFrom---"+exFrom);

		if (exactTo!=null) {
			cTo.setTime(exactTo);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);
			exTo=cTo.getTime();
		}
		log.debug("------exTo---"+exTo);
		
		Date pFrom=null;
		Date pTo= null;
		
		if (pFrom !=null) {
			cFrom.setTime(periodFrom);
			cFrom.set(Calendar.HOUR_OF_DAY, 0);
			cFrom.set(Calendar.MINUTE, 0);
			cFrom.set(Calendar.SECOND, 0);
			pFrom=cFrom.getTime();
		}
		
		log.debug("------pfrom---"+pFrom);

		if (pTo!=null) {
			cTo.setTime(periodTo);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);
			pTo=cTo.getTime();
		}
		log.debug("------Pto---"+pTo);
		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		Map map = new HashMap();
		List list =new ArrayList();
		try {

			Criteria criteria = getCurrentSession()
					.createCriteria(LoginUsersRequests.class);
			
			///////////////////////////////////////////////////////////////////////
			if (fromDate!=null && toDate!=null){
				final Date startDate =(Date) format.parse(format.format(dateFrom));
				final Date endDate = (Date)format.parse(format.format(dateTo));
				criteria.add(Expression.ge("request_date", startDate));
				criteria.add(Expression.le("request_date", endDate));
			}
			if (pFrom!=null && pTo!=null){
				final Date startDate =(Date) format.parse(format.format(pFrom));
				final Date endDate = (Date)format.parse(format.format(pTo));
				criteria.add(Expression.ge("period_from", startDate));
				criteria.add(Expression.le("period_from", endDate));
			}
			if (exactFrom!=null && exactTo!=null){
				final Date startDate =(Date) format.parse(format.format(exFrom));
				final Date endDate = (Date)format.parse(format.format(exTo));
				criteria.add(Expression.ge("from_date", startDate));
				criteria.add(Expression.le("from_date", endDate));
			}
//			criteria.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
//					Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))));
			/////////////////////////////////////////////////////////////////////////////////
			log.debug("requestNumber " + requestNumber);
			if(requestNumber!=null && !requestNumber.isEmpty()) {
				criteria.add(Restrictions.eq("requestNumber", requestNumber));
			}
			if(requestType!=null) {
				log.debug("requesttype " + requestType);
				if (requestType.equals(new Long(1))){
					log.debug("requesttype is 1");
					criteria.add(Restrictions.or(
							Restrictions.eq("request_id.id", requestType),
							Restrictions.eq("request_id.id", new Long(4)))
							);
				} else if (requestType.equals(new Long(4))) {
					log.debug("requesttype is 4");
					criteria.add(Restrictions.or(Restrictions.or(
							Restrictions.eq("request_id.id", new Long(1)),
							Restrictions.eq("request_id.id", new Long(2)))
							,Restrictions.eq("request_id.id", new Long(4))));
				} else if (requestType.equals(new Long(5))) {
					log.debug("requesttype is 5");
					criteria.add(Restrictions.or(
							Restrictions.eq("request_id.id", new Long(10)),
							Restrictions.eq("request_id.id", new Long(11)))
							);
				} else {
					log.debug("requesttype is else");
					criteria.add(Restrictions.eq("request_id.id", requestType));
				}
			}
			/////////////////////////////////////////////////////////////////////////////////
			if (empCode!=null && !empCode.isEmpty()) {
				criteria.add(Restrictions.eq("empCode", empCode));
			}
			////////////////////////////////////////////////////////////////////////////////
			if (codeFrom!=null && !codeFrom.isEmpty() && codeTo!=null && !codeTo.isEmpty()) {
				criteria.add(Restrictions.between("empCode", codeFrom, codeTo));
			}
			///////////////////////////////////////////////////////////////////////////////
			if (statusId!=null) {
				criteria.add(Restrictions.eq("approved", statusId));
			}
			///////////////////////////////////////////////////////////////////////////////

			log.debug("empReqTypeAccs " + empReqTypeAccs);
			if (empReqTypeAccs != null) {
				criteria.createCriteria("login_user").add(Restrictions.in("id", empReqTypeAccs));
			}
			if (sort.equalsIgnoreCase("desc")) {
				criteria.addOrder(Property.forName("period_from").desc());
			}
			if (sort.equalsIgnoreCase("asc")) {
				criteria.addOrder(Property.forName("period_from").asc());
			}
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
			list = (List)criteria.list();
			map.put("listSize", Integer.valueOf(criteria.list().iterator().next()+""));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			log.debug("exception " + e.getMessage());
			Page page = new Page();
			map.put("listSize", new Integer(0));
			return page.getPage(map,pageNumber,pageSize);
		}
		
		
		try {

			Criteria criteria = getCurrentSession()
					.createCriteria(LoginUsersRequests.class);
			
			///////////////////////////////////////////////////////////////////////
			if (fromDate!=null && toDate!=null){
				final Date startDate =(Date) format.parse(format.format(dateFrom));
				final Date endDate = (Date)format.parse(format.format(dateTo));
				criteria.add(Expression.ge("request_date", startDate));
				criteria.add(Expression.le("request_date", endDate));
			}
			if (pFrom!=null && pTo!=null){
				final Date startDate =(Date) format.parse(format.format(pFrom));
				final Date endDate = (Date)format.parse(format.format(pTo));
				criteria.add(Expression.ge("period_from", startDate));
				criteria.add(Expression.le("period_from", endDate));
			}
			if (exactFrom!=null && exactTo!=null){
				final Date startDate =(Date) format.parse(format.format(exFrom));
				final Date endDate = (Date)format.parse(format.format(exTo));
				criteria.add(Expression.ge("from_date", startDate));
				criteria.add(Expression.le("from_date", endDate));
			}
//			criteria.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
//					Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))));
			/////////////////////////////////////////////////////////////////////////////////
			log.debug("requestNumber " + requestNumber);
			if(requestNumber!=null && !requestNumber.isEmpty()) {
				criteria.add(Restrictions.eq("requestNumber", requestNumber));
			}
			if(requestType!=null) {
				log.debug("requesttype " + requestType);
				if (requestType.equals(new Long(1))){
					log.debug("requesttype is 1");
					criteria.add(Restrictions.or(
							Restrictions.eq("request_id.id", requestType),
							Restrictions.eq("request_id.id", new Long(4)))
							);
				} else if (requestType.equals(new Long(4))) {
					log.debug("requesttype is 4");
					criteria.add(Restrictions.or(Restrictions.or(
							Restrictions.eq("request_id.id", new Long(1)),
							Restrictions.eq("request_id.id", new Long(2)))
							,Restrictions.eq("request_id.id", new Long(4))));
				} else if (requestType.equals(new Long(5))) {
					log.debug("requesttype is 5");
					criteria.add(Restrictions.or(
							Restrictions.eq("request_id.id", new Long(10)),
							Restrictions.eq("request_id.id", new Long(11)))
							);
				} else {
					log.debug("requesttype is else");
					criteria.add(Restrictions.eq("request_id.id", requestType));
				}
			}
			/////////////////////////////////////////////////////////////////////////////////
			if (empCode!=null && !empCode.isEmpty()) {
				criteria.add(Restrictions.eq("empCode", empCode));
			}
			////////////////////////////////////////////////////////////////////////////////
			if (codeFrom!=null && !codeFrom.isEmpty() && codeTo!=null && !codeTo.isEmpty()) {
				criteria.add(Restrictions.between("empCode", codeFrom, codeTo));
			}
			///////////////////////////////////////////////////////////////////////////////
			if (statusId!=null) {
				criteria.add(Restrictions.eq("approved", statusId));
			}
			///////////////////////////////////////////////////////////////////////////////

			log.debug("empReqTypeAccs " + empReqTypeAccs);
			if (empReqTypeAccs != null) {
				criteria.createCriteria("login_user").add(Restrictions.in("id", empReqTypeAccs));
			}
			if (sort.equalsIgnoreCase("desc")) {
				criteria.addOrder(Property.forName("period_from").desc());
			}
			if (sort.equalsIgnoreCase("asc")) {
				criteria.addOrder(Property.forName("period_from").asc());
			}
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
			log.debug("first result " + (pageNumber*pageSize));
			log.debug("max results " + pageSize);
			criteria.setFirstResult(pageNumber * pageSize);
			criteria.setMaxResults(pageSize);
			
			List results = criteria.list();
			if (isWeb) {
				map.put("results", results);
			}  else {
				log.debug("results " + results.size());
				List output = new ArrayList();
				Iterator itr = results.iterator();
				while (itr.hasNext()) {
					LoginUsersRequests req = (LoginUsersRequests)itr.next();
					RequestOutput op = new RequestOutput();
					op.setId(req.getId());
					op.setEmpCode(req.getEmpCode());
					op.setEmpName(req.getLogin_user().getName());
					op.setRequestDate(req.getRequest_date());
					op.setRequestNumber(req.getRequestNumber());
					op.setNotes(req.getNotes());
					op.setLongitude(req.getLongitude());
					op.setLatitude(req.getLatitude());

					if (req.getRequest_id().getId().equals(new Long(1))) {
						op.setRequestDesc(req.getVacation().getName());
					} else {
						op.setRequestDesc(req.getRequest_id().getDescription());
					}

					if (req.getRequest_id().getId().equals(new Long(1)) || req.getRequest_id().getId().equals(new Long(2))) {
						op.setVacDuration(req.getWithdrawDays());
					}
					if (req.getRequest_id().getId().equals(new Long(10))) {
						op.setRequestType(new Long(5));
					} else if (req.getRequest_id().getId().equals(new Long(11))) {
						op.setRequestType(new Long(6));
					} else {
						op.setRequestType(req.getRequest_id().getId());
					} 


					if (req.getApproved()==null || req.getApproved().equals(new Long(0))) {
						op.setStatus("Waiting Approval");
					} else if (req.getApproved().equals(new Long(99))) {
						op.setStatus("Declined");
					} else if (req.getApproved().equals(new Long(1))) {
						op.setStatus("Approved");
					} 

					System.out.println("req.getFrom_date() " + req.getFrom_date());
					op.setFromDate(req.getFrom_date());
					op.setToDate(req.getTo_date());

					output.add(op);
				}
				map.put("results", output);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			log.debug("exception " + e.getMessage());
			Page page = new Page();
			return page.getPage(map,pageNumber,pageSize);
		}
		Page page = new Page();
		log.debug("paging");
		return page.getPage(map,pageNumber,pageSize);
//		return map;
	}



	
	public List getRequestsByExactDatePeriodAndRequestType(final Date fromDate, final Date toDate, final Long requestType){
		Calendar cFrom= Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);
		//cFrom.set(Calendar.AM_PM, -1);
		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
		final Date dateFrom=cFrom.getTime();

		log.debug("------dateFrom---"+dateFrom);

		Calendar cTo= Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo=cTo.getTime();
		log.debug("------dateTo---"+dateTo);
		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List list =new ArrayList();
		try {
			final Date startDate =(Date) format.parse(format.format(dateFrom));
			final Date endDate = (Date)format.parse(format.format(dateTo));

			Criteria criteria = getCurrentSession()
					.createCriteria(LoginUsersRequests.class);
			criteria.add(Expression.ge("from_date", startDate));
			criteria.add(Expression.le("from_date", endDate));
			if (requestType.equals(1)){
				criteria.add(Restrictions.or(
						Restrictions.eq("request_id.id", requestType),
						Restrictions.eq("request_id.id", 4))
						);
			} else {
				criteria.add(Restrictions.eq("request_id.id", requestType));
			}
			criteria.addOrder(Property.forName("period_from").asc());
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list = criteria.list();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	public List getRequestsByDatePeriod(final Date fromDate, final Date toDate){
		log.debug("------fromDate---"+fromDate);
		List list = new ArrayList();

		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd");// HH:mm:ss

		Criteria criteria = getCurrentSession()
				.createCriteria(LoginUsersRequests.class);
		criteria.add(Expression.ge("request_date", fromDate));
		criteria.add(Expression.le("request_date", toDate));
		//							criteria.addOrder(Property.forName("period_from").asc());
		criteria
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		log.debug("criteria.list().size()" + criteria.list().size());
		return criteria.list();
	}


	public List getRequestsByExactDatePeriod(final Date fromDate, final Date toDate){
		log.debug("------fromDate---"+fromDate);
		List list = new ArrayList();

		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd");// HH:mm:ss

		Criteria criteria = getCurrentSession()
				.createCriteria(LoginUsersRequests.class);
		criteria.add(Expression.ge("from_date", fromDate));
		criteria.add(Expression.le("from_date", toDate));
		//							criteria.addOrder(Property.forName("period_from").asc());
		criteria
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		log.debug("criteria.list().size()" + criteria.list().size());
		return criteria.list();
	}

	public List getRequestsByDatePeriodAndRequestTypeAndEmpCode(final Date fromDate,final Date toDate, final Long requestType, final String empCode){
		Calendar cFrom= Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);
		final Date dateFrom=cFrom.getTime();
		log.debug("------dateFrom---"+dateFrom);

		Calendar cTo= Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo=cTo.getTime();
		log.debug("------dateTo---"+dateTo);

		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List list =new ArrayList();
		try {
			final Date startDate =(Date) format.parse(format.format(dateFrom));
			final Date endDate = (Date)format.parse(format.format(dateTo));

			Criteria criteria = getCurrentSession()
					.createCriteria(LoginUsersRequests.class);
			criteria.add(Expression.ge("request_date", startDate));
			criteria.add(Expression.le("request_date", endDate));
			if (requestType.equals(1)){
				criteria.add(Restrictions.or(
						Restrictions.eq("request_id.id", requestType),
						Restrictions.eq("request_id.id", 4))
						);
			} else {
				criteria.add(Restrictions.eq("request_id.id", requestType));
			}
			criteria.add(Restrictions.eq("empCode", empCode));
			criteria.addOrder(Property.forName("period_from").asc());
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list = criteria.list();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List getRequestsByExactDatePeriodAndRequestTypeAndEmpCode(final Date fromDate,final Date toDate, final Long requestType, final String empCode){
		Calendar cFrom= Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);
		//cFrom.set(Calendar.AM_PM, -1);
		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
		final Date dateFrom=cFrom.getTime();
		log.debug("------dateFrom---"+dateFrom);

		Calendar cTo= Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo=cTo.getTime();
		log.debug("------dateTo---"+dateTo);

		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List list =new ArrayList();
		try {
			final Date startDate =(Date) format.parse(format.format(dateFrom));
			final Date endDate = (Date)format.parse(format.format(dateTo));

			Criteria criteria = getCurrentSession()
					.createCriteria(LoginUsersRequests.class);
			criteria.add(Expression.ge("from_date", startDate));
			criteria.add(Expression.le("from_date", endDate));
			if (requestType.equals(1)){
				criteria.add(Restrictions.or(
						Restrictions.eq("request_id.id", requestType),
						Restrictions.eq("request_id.id", 4))
						);
			} else {
				criteria.add(Restrictions.eq("request_id.id", requestType));
			}
			criteria.add(Restrictions.eq("empCode", empCode));
			criteria.addOrder(Property.forName("period_from").asc());
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list = criteria.list();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	public List getRequestsByDatePeriodAndEmpCode(final Date fromDate,final Date toDate, final String empCode){
		Calendar cFrom= Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);
		//cFrom.set(Calendar.AM_PM, -1);
		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
		final Date dateFrom=cFrom.getTime();
		log.debug("------dateFrom---"+dateFrom);

		Calendar cTo= Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo=cTo.getTime();
		log.debug("------dateTo---"+dateTo);

		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List list =new ArrayList();
		try {
			final Date startDate =(Date) format.parse(format.format(dateFrom));
			final Date endDate = (Date)format.parse(format.format(dateTo));

			Criteria criteria = getCurrentSession()
					.createCriteria(LoginUsersRequests.class);
			criteria.add(Expression.ge("request_date", startDate));
			criteria.add(Expression.le("request_date", endDate));
			criteria.add(Restrictions.eq("empCode", empCode));
			criteria.addOrder(Property.forName("period_from").asc());
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list = criteria.list();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;	
	}
	public List getRequestsByExactDatePeriodAndEmpCode(final Date fromDate,final Date toDate, final String empCode){
		Calendar cFrom= Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);
		//cFrom.set(Calendar.AM_PM, -1);
		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
		final Date dateFrom=cFrom.getTime();
		log.debug("------dateFrom---"+dateFrom);

		Calendar cTo= Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo=cTo.getTime();
		log.debug("------dateTo---"+dateTo);

		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List list =new ArrayList();
		try {
			final Date startDate =(Date) format.parse(format.format(dateFrom));
			final Date endDate = (Date)format.parse(format.format(dateTo));

			Criteria criteria = getCurrentSession()
					.createCriteria(LoginUsersRequests.class);
			criteria.add(Expression.ge("from_date", startDate));
			criteria.add(Expression.le("from_date", endDate));
			criteria.add(Restrictions.eq("empCode", empCode));
			criteria.addOrder(Property.forName("period_from").asc());
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list = criteria.list();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;	
	}

	public List getRequestsByDatePeriodAndRequestTypeForEmployee(final Date fromDate, final Date toDate, final Long requestType, final  String empCode){
		Calendar cFrom= Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);
		//cFrom.set(Calendar.AM_PM, -1);
		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
		final Date dateFrom=cFrom.getTime();
		log.debug("------dateFrom---"+dateFrom);

		Calendar cTo= Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo=cTo.getTime();
		log.debug("------dateTo---"+dateTo);

		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List list =new ArrayList();
		try {
			final Date startDate =(Date) format.parse(format.format(dateFrom));
			final Date endDate = (Date)format.parse(format.format(dateTo));

			Criteria criteria = getCurrentSession()
					.createCriteria(LoginUsersRequests.class);
			criteria.add(Expression.ge("request_date", startDate));
			criteria.add(Expression.le("request_date", endDate));
			criteria.add(Restrictions.eq("request_id.id", requestType));
			criteria.add(Restrictions.eq("empCode", empCode));
			criteria.addOrder(Property.forName("period_from").asc());
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list = criteria.list();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;	
	}

	public List getRequestsByExactDatePeriodAndRequestTypeForEmployee(final Date fromDate, final Date toDate, final Long requestType, final  String empCode){
		Calendar cFrom= Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);
		//cFrom.set(Calendar.AM_PM, -1);
		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
		final Date dateFrom=cFrom.getTime();
		log.debug("------dateFrom---"+dateFrom);

		Calendar cTo= Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo=cTo.getTime();
		log.debug("------dateTo---"+dateTo);

		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List list =new ArrayList();
		try {
			final Date startDate =(Date) format.parse(format.format(dateFrom));
			final Date endDate = (Date)format.parse(format.format(dateTo));
			Criteria criteria = getCurrentSession()
					.createCriteria(LoginUsersRequests.class);
			criteria.add(Expression.ge("from_date", startDate));
			criteria.add(Expression.le("from_date", endDate));
			criteria.add(Restrictions.eq("request_id.id", requestType));
			criteria.add(Restrictions.eq("empCode", empCode));
			criteria.addOrder(Property.forName("period_from").asc());
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list = criteria.list();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;	
	}


	public List getRequestsByDatePeriodForEmployee(final Date fromDate, final Date toDate, final String empCode){
		Calendar cFrom= Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);
		//cFrom.set(Calendar.AM_PM, -1);
		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
		final Date dateFrom=cFrom.getTime();
		log.debug("------dateFrom---"+dateFrom);

		Calendar cTo= Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo=cTo.getTime();
		log.debug("------dateTo---"+dateTo);
		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List list =new ArrayList();
		try {
			final Date startDate =(Date) format.parse(format.format(dateFrom));
			final Date endDate = (Date)format.parse(format.format(dateTo));

			Criteria criteria = getCurrentSession()
					.createCriteria(LoginUsersRequests.class);
			criteria.add(Expression.ge("request_date", startDate));
			criteria.add(Expression.le("request_date", endDate));
			criteria.add(Restrictions.eq("empCode", empCode));
			criteria.addOrder(Property.forName("period_from").asc());
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list = criteria.list();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;	
	}

	public List getRequestsByExactDatePeriodForEmployee(final Date fromDate, final Date toDate, final String empCode){
		Calendar cFrom= Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);
		//cFrom.set(Calendar.AM_PM, -1);
		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
		final Date dateFrom=cFrom.getTime();
		log.debug("------dateFrom---"+dateFrom);

		Calendar cTo= Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo=cTo.getTime();
		log.debug("------dateTo---"+dateTo);
		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List list =new ArrayList();
		try {
			final Date startDate =(Date) format.parse(format.format(dateFrom));
			final Date endDate = (Date)format.parse(format.format(dateTo));

			Criteria criteria = getCurrentSession()
					.createCriteria(LoginUsersRequests.class);
			criteria.add(Expression.ge("from_date", startDate));
			criteria.add(Expression.le("from_date", endDate));
			criteria.add(Restrictions.eq("empCode", empCode));
			criteria.addOrder(Property.forName("period_from").asc());
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list = criteria.list();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;	
	}

	public List getEmployeesByCodes(final String codeFrom,final String codeTo){
		Criteria criteria = getCurrentSession()
				.createCriteria(LoginUsersRequests.class);
		criteria.add(Restrictions.between("empCode", codeFrom, codeTo));
//		criteria.addOrder(Property.forName("period_from").asc());
		//copied from lehaa////////////////////////////////////////
		criteria.addOrder(Property.forName("period_from").asc());
		//////////////////////////////////////////////////////
		criteria
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getEmployeesByCodesAndDatePeriod(final String codeFrom,final String codeTo,final Date fromDate, final Date toDate){
		Calendar cFrom= Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);
		//		cFrom.set(Calendar.AM_PM, -1);
		//		cFrom.set(Calendar.HOUR_OF_DAY, 23);
		final Date dateFrom=cFrom.getTime();

		log.debug("------dateFrom---"+dateFrom);

		Calendar cTo= Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo=cTo.getTime();
		log.debug("------dateTo---"+dateTo);
		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List list =new ArrayList();
		try {
			final Date startDate =(Date) format.parse(format.format(dateFrom));
			final Date endDate = (Date)format.parse(format.format(dateTo));

			Criteria criteria = getCurrentSession()
					.createCriteria(LoginUsersRequests.class);
			criteria.add(Expression.ge("request_date", startDate));
			criteria.add(Expression.le("request_date", endDate));
			criteria.add(Restrictions.between("empCode", codeFrom, codeTo));
			criteria.addOrder(Property.forName("period_from").asc());
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list = criteria.list();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	public List getEmployeesByCodesAndRequestType(final String codeFrom,final String codeTo , final Long requestType){
		Criteria criteria = getCurrentSession()
				.createCriteria(LoginUsersRequests.class);
		criteria.add(Restrictions.between("empCode", codeFrom, codeTo));
		criteria.add(Restrictions.eq("request_id.id", requestType));
		criteria.addOrder(Property.forName("period_from").asc());
		criteria
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getEmployeesByCodesAndDatePeriodAndRequestType(final String codeFrom,final String codeTo,final Date fromDate, final Date toDate, final Long requestType){
		Calendar cFrom= Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);
		//		cFrom.set(Calendar.AM_PM, -1);
		//		cFrom.set(Calendar.HOUR_OF_DAY, 23);
		final Date dateFrom=cFrom.getTime();

		log.debug("------dateFrom---"+dateFrom);

		Calendar cTo= Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo=cTo.getTime();
		log.debug("------dateTo---"+dateTo);
		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List list =new ArrayList();
		try {
			final Date startDate =(Date) format.parse(format.format(dateFrom));
			final Date endDate = (Date)format.parse(format.format(dateTo));

			Criteria criteria = getCurrentSession()
					.createCriteria(LoginUsersRequests.class);
			criteria.add(Expression.ge("request_date", startDate));
			criteria.add(Expression.le("request_date", endDate));
			criteria.add(Restrictions.between("empCode", codeFrom, codeTo));
			criteria.add(Restrictions.eq("request_id.id", requestType));
			criteria.addOrder(Property.forName("period_from").asc());
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list = criteria.list();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}


	public List getRequestStatus(final Long req_id){
		try{
			Criteria criteria = getCurrentSession()
					.createCriteria(EmpReqApproval.class);
			criteria.add(Restrictions.eq("req_id.id", req_id));
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			return criteria.list();
		}
		catch (Exception e) {
			return new ArrayList();
		}

	}


	public void signInOut(AttendanceRequest userRequest) {
		// TODO Auto-generated method stub
		System.out.println("dao sign in/out");
		
	}


	public List getEmpReqTypeAccs(List accessLevels,Long requestType) {
		try{
			if (accessLevels.size()>0) {
				Criteria criteria = getCurrentSession()
						.createCriteria(EmpReqTypeAcc.class);
				if (requestType != null) {
					criteria.createCriteria("req_id").add(Restrictions.eq("id", requestType));
				}
				criteria.createCriteria("group_id").add(Restrictions.in("id", accessLevels));
				criteria.addOrder(Property.forName("id").asc());
				criteria
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				List list = criteria.list();
				Iterator itr = list.iterator();
				log.debug("empreqtypeacc size " + list.size());
				while(itr.hasNext()) {
					EmpReqTypeAcc acc = (EmpReqTypeAcc)itr.next();
					log.debug("EmpReqTypeAcc " + acc.getId() + " group id " + acc.getGroup_id().getId());
				}
				return list;
			} else return new ArrayList();
		}
		catch (Exception e) {
			return new ArrayList();
		}
	}


	@Transactional
	public int insertTimeAttend(String hostName, String serviceName,
			String userName, String password, String emp_code, Date date_,
			Date time_, String trans_type) {
		return externalQueries.insertTimeAttend(hostName, serviceName, userName, password, emp_code, date_, time_, trans_type);
	}


	public Map checkStartedRequests(RequestsApprovalQuery requestQuery,
			Employee emp) {
		Map response = new HashMap();
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		RestStatus status = new RestStatus();
		Long requestType =  Long.parseLong(requestQuery.getRequestType());
		Date dateFrom = null;
		Date dateTo = null;
		
		Date newDate1 = null;
		if (requestQuery.getDateFrom()!=null && !requestQuery.getDateFrom().isEmpty()) {
			try {
				newDate1 = df.parse(requestQuery.getDateFrom());
			} catch(Exception e){
				e.printStackTrace();
				status.setCode("312");
				status.setMessage("Date is not well formated");
				status.setStatus("False");
				response.put("Status", status);
				return response;
			}


			Calendar cFrom= Calendar.getInstance();
			cFrom.setTime(newDate1);
			cFrom.set(Calendar.HOUR_OF_DAY, 0);
			cFrom.set(Calendar.MINUTE, 0);
			cFrom.set(Calendar.SECOND, 0);
			dateFrom=cFrom.getTime();
			log.debug("------dateFrom---"+dateFrom);
		}
		Date newDate2 = null;
		if (requestQuery.getDateTo()!=null && !requestQuery.getDateTo().isEmpty()) {
			try {
				newDate2 = df.parse(requestQuery.getDateTo());
			} catch(Exception e){
				e.printStackTrace();
				status.setCode("312");
				status.setMessage("Date is not well formated");
				status.setStatus("False");
				response.put("Status", status);
				return response;
			}
			Calendar cTo= Calendar.getInstance();
			cTo.setTime(newDate2);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);

			dateTo=cTo.getTime();
			log.debug("------dateTo---"+dateTo);
		}

		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date sDate =null;
		if (dateFrom != null) {
			try {
				sDate = (Date) format.parse(format.format(dateFrom));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Date eDate = null;
		if (dateTo != null) {
			try {
				eDate = (Date)format.parse(format.format(dateTo));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		log.debug("sDate " + sDate);
		log.debug("eDate " + eDate);
		
		List list =new ArrayList();
		Criteria criteria = getCurrentSession()
				.createCriteria(LoginUsersRequests.class);
	
		criteria.add(Expression.isNull("period_to"));
		
		log.debug("request type " + requestType);
		if (requestType.equals(new Long(1))){
			log.debug("request type " + requestType);
			criteria.add(Restrictions.eq("request_id.id", new Long(3)));
			if (sDate !=null) {
				final Date startDate =sDate;
				criteria.add(Expression.ge("period_from", startDate));
			}
			if (eDate != null) {
				final Date endDate = eDate;
				criteria.add(Expression.le("period_from", endDate));
			}
		} else if (requestType.equals(new Long(2))){
			log.debug("request type " + requestType);
			criteria.add(Restrictions.eq("request_id.id", new Long(1)));
			criteria.createCriteria("vacation").add(Restrictions.eq("vacation", "999"));
			if (sDate !=null) {
				final Date startDate =sDate;
				criteria.add(Expression.ge("period_from", startDate));
			}
			if (eDate != null) {
				final Date endDate = eDate;
				criteria.add(Expression.le("period_from", endDate));
			}
		} 
		criteria.add(Restrictions.eq("empCode", emp.getEmpCode()));
		criteria.addOrder(Property.forName("period_from").desc());
		criteria
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		list = criteria.list();
		status.setCode("200");
		status.setMessage("Successful Transaction");
		status.setStatus("True");
		response.put("Status", status);
		response.put("Response", list) ;
		return response;
	}


	public Map checkAttendance(Date today, String empCode) {

		Map response = new HashMap();
		AttendanceStatus attStatus = new AttendanceStatus();
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		RestStatus status = new RestStatus();
		Date dateToday = null;
		Date dateToday2 = null;

		Calendar todayCal= Calendar.getInstance();
		todayCal.setTime(today);
		todayCal.set(Calendar.HOUR_OF_DAY, 0);
		todayCal.set(Calendar.MINUTE, 0);
		todayCal.set(Calendar.SECOND, 0);
		dateToday=todayCal.getTime();
		log.debug("------dateFrom---"+dateToday);

		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date sDate =null;
		if (dateToday != null) {
			try {
				sDate = (Date) format.parse(format.format(dateToday));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Calendar todayCal2= Calendar.getInstance();
		todayCal2.setTime(today);
		todayCal2.set(Calendar.HOUR_OF_DAY, 23);
		todayCal2.set(Calendar.MINUTE, 59);
		todayCal2.set(Calendar.SECOND, 59);
		dateToday2=todayCal2.getTime();
		log.debug("------dateFrom---"+dateToday2);
		
		Date eDate = null;
		if (dateToday2 != null) {
			try {
				eDate = (Date)format.parse(format.format(dateToday2));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		log.debug("sDate " + sDate);
		log.debug("eDate " + eDate);
		
		List list =new ArrayList();
		Criteria criteria = getCurrentSession()
				.createCriteria(LoginUsersRequests.class);

		criteria.add(Restrictions.eq("request_id.id", new Long(10)));
		criteria.add(Restrictions.eq("empCode", empCode));
		
//		if (sDate !=null) {
			final Date startDate =sDate;
			criteria.add(Expression.ge("period_from", startDate));
//		}
//		if (eDate != null) {
			final Date endDate = eDate;
			criteria.add(Expression.le("period_from", endDate));
//			}
		criteria.addOrder(Property.forName("period_from").desc());
		criteria
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		list = criteria.list();
		
		if (list.size()>0) {
			LoginUsersRequests req = (LoginUsersRequests)list.get(0);
			
			attStatus.setSignIn(new Boolean(true));
			attStatus.setSignInTime(req.getPeriod_from().getTime());
			
			log.debug(req.getId() + " - " + req.getPeriod_from());
//			response.put("Response",format.format(req.getPeriod_from()));
		} else {
			attStatus.setSignIn(new Boolean(false));
			attStatus.setSignInTime(null);
		}

		//////////////////////////////////////
		
		list =new ArrayList();
		criteria = getCurrentSession()
				.createCriteria(LoginUsersRequests.class);
	
		criteria.add(Restrictions.eq("request_id.id", new Long(11)));
		criteria.add(Restrictions.eq("empCode", empCode));
		
			
		final Date startDate2 =sDate;
		criteria.add(Expression.ge("period_from", startDate2));
		final Date endDate2 = eDate;
		criteria.add(Expression.le("period_from", endDate2));
		criteria.addOrder(Property.forName("period_from").desc());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		list = criteria.list();

		if (list.size()>0) {
			LoginUsersRequests req = (LoginUsersRequests)list.get(0);

			if (req.getPeriod_from()!=null){
				attStatus.setSignOut(new Boolean(true));
				attStatus.setSignOutTime(req.getPeriod_from().getTime());
				log.debug(req.getId() + " - " +req.getPeriod_from());
			} else {
				attStatus.setSignOut(new Boolean(false));
				attStatus.setSignOutTime(null);
				log.debug(req.getId() + " - " +req.getPeriod_from());
			}
		} else {
			attStatus.setSignOut(new Boolean(false));
			attStatus.setSignOutTime(null);
		}
		
		status.setCode("200");
		status.setMessage("Successful Transaction");
		status.setStatus("True");
		response.put("Status", status);
		response.put("Response", attStatus);
		
		return response;
	
	}
	
	





	//	public List getTimeAttend(final String empCode,final Date fromDate,final Date toDate){
	//		try{
	//			List list = (List) getHibernateTemplate().execute(
	//					
	//						
	//							
	//							Criteria criteria = getCurrentSession()
	//							.createCriteria(TimeAttend.class);
	//							ProjectionList projectionList = Projections.projectionList();
	//							criteria.add(Restrictions.between("date", fromDate,toDate));
	//							criteria.add(Restrictions.eq("emp_code",empCode));
	//							projectionList.add(Projections.groupProperty("date"));
	//							projectionList.add(Projections.min("time"));
	//							criteria.setProjection(projectionList);
	//							criteria
	//							.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	//							return criteria.list();
	//						}
	//					});
	//			return list;	
	//		}
	//		catch (Exception e) {
	//			return new ArrayList();
	//		}
	//		
	//	}
}
