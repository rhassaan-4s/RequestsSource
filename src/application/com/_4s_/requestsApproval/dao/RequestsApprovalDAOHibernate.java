package com._4s_.requestsApproval.dao;

import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.HR.model.AInsuranceCala;
import com._4s_.HR.model.HREmployee;
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
import com._4s_.common.model.Settings;
import com._4s_.common.util.DBUtils;
import com._4s_.common.util.Page;
import com._4s_.requestsApproval.model.AccessLevels;
import com._4s_.requestsApproval.model.EmpReqApproval;
import com._4s_.requestsApproval.model.EmpReqApprovalJSON;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.GroupAcc;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.restServices.json.AttendanceRequest;
//import com._4s_.requestsApproval.model.TimeAttend;
//import com._4s_.stores.model.ViewStoreCardItem;
import com._4s_.restServices.json.RequestsApprovalQuery;
import com._4s_.restServices.json.RestStatus;
import com._4s_.restServices.model.AttendanceStatus;

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Repository
public class RequestsApprovalDAOHibernate extends BaseDAOHibernate implements RequestsApprovalDAO {
	@Autowired
	private ExternalQueries externalQueries = null;
	
	public ExternalQueries getExternalQueries() {
		return externalQueries;
	}


	public void setExternalQueries(ExternalQueries externalQueries) {
		this.externalQueries = externalQueries;
	}


	public List getAllLeafs(final Class clazz){
		
		log.debug("inside getAllLeafs");
    	
    	CriteriaBuilder builder = getBuilder();
    	Session session = getCurrentSession();
    	
    	CriteriaQuery queryCriteria = getBuilder().createQuery(clazz);
    	Root<Object> root = queryCriteria.from(clazz);
    	
    	Predicate restrictions =  builder.equal(root.get("parent").get("isLast"), new Boolean(true));
    	
    	Order order = builder.asc(root.get("longCode"));
    	queryCriteria.select(root).where(restrictions).orderBy(order).distinct(true);
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
    	log.debug("querycriteria " + queryCriteria);
    	log.debug("query " + query);
    	
        return query.getResultList();
        
        
//		log.debug("inside getAllLeafs");
//		Criteria criteria = null;
//		criteria = getCurrentSession().createCriteria(clazz);
//		criteria.createCriteria("parent").add(Restrictions.eq("isLast",new Boolean(true)));
//		criteria.addOrder(Order.asc("longCode"));
//		log.debug("===========================end getAllLeafs()===============");
//		return criteria.list();
	}


	public List getAllLeafsOfCategory(final String catCode, final Class clazz) {
//		Criteria criteria = getCurrentSession().createCriteria(clazz);
//		criteria.add( Restrictions.like("longCode",catCode , MatchMode.START ) );
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<Object> queryCriteria = builder.createQuery(clazz);
		Root<Object> root = queryCriteria.from(clazz);

		List<Predicate> predicates = new ArrayList<>();

		predicates.add(
		        builder.like(
		                root.get("longCode"),
		                catCode + "%"
		        )
		);

		queryCriteria.select(root)
		             .where(predicates.toArray(new Predicate[] {}))
		             .distinct(true);

		TypedQuery<Object> query = session.createQuery(queryCriteria);

		return query.getResultList();
	}






	public List getRoot(final String className) {
		log.debug("inside getRoot ");
		
		CriteriaBuilder builder = getBuilder();
    	Session session = getCurrentSession();
    	
    	CriteriaQuery queryCriteria = null;
    	Root<Object> root = null;
    	if(className.equals("HRInternalDivision")) {
    		queryCriteria = getBuilder().createQuery(HRInternalDivision.class);
    		root = queryCriteria.from(HRInternalDivision.class);
    	}
		else if(className.equals("HRGeographicalDivision")) {
			queryCriteria = getBuilder().createQuery(HRGeographicalDivision.class);
			root = queryCriteria.from(HRGeographicalDivision.class);
		}
		else if(className.equals("HRQualificationDivision")) {
			queryCriteria = getBuilder().createQuery(HRQualificationDivision.class);
			root = queryCriteria.from(HRQualificationDivision.class);
		}
		else if(className.equals("HRSpecialtyDivision")) {
			queryCriteria = getBuilder().createQuery(HRSpecialtyDivision.class);
			root = queryCriteria.from(HRSpecialtyDivision.class);
		}
    	
    	
    	Predicate restrictions =  builder.isNull(root.get("parent"));
    	Order order = builder.asc(root.get("longCode"));
    	queryCriteria.select(root).where(restrictions).orderBy(order).distinct(true);
    	
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
    	log.debug("querycriteria " + queryCriteria);
    	log.debug("query " + query);
    	
        return query.getResultList();
		
		
//		Criteria criteria=null;
//		if(className.equals("HRInternalDivision"))
//		{ criteria = getCurrentSession()
//		.createCriteria(HRInternalDivision.class);
//		criteria.add(Restrictions.isNull("parent"));
//		criteria.addOrder(Order.asc("longCode"));
//		}
//
//		if(className.equals("HRGeographicalDivision"))
//		{ criteria = getCurrentSession()
//		.createCriteria(HRGeographicalDivision.class);
//		criteria.add(Restrictions.isNull("parent"));
//		criteria.addOrder(Order.asc("longCode"));
//		}
//
//		if(className.equals("HRQualificationDivision"))
//		{ criteria = getCurrentSession()
//		.createCriteria(HRQualificationDivision.class);
//		criteria.add(Restrictions.isNull("parent"));
//		criteria.addOrder(Order.asc("longCode"));
//		}
//
//		if(className.equals("HRSpecialtyDivision"))
//		{ criteria = getCurrentSession()
//		.createCriteria(HRSpecialtyDivision.class);
//		criteria.add(Restrictions.isNull("parent"));
//		criteria.addOrder(Order.asc("longCode"));
//		}
//		return criteria.list();
	}


	public HRInternalLevel getLastLevel() {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>..start getLastLevel()");
//		Criteria criteria = getCurrentSession()
//				.createCriteria(HRInternalLevel.class);
//		criteria.add(Restrictions.eq("isLastLevel", true));
//		HRInternalLevel list = (HRInternalLevel) criteria.uniqueResult();
//		return list;
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<HRInternalLevel> queryCriteria =
		        builder.createQuery(HRInternalLevel.class);

		Root<HRInternalLevel> root =
		        queryCriteria.from(HRInternalLevel.class);

		Predicate restriction =
		        builder.equal(root.get("isLastLevel"), Boolean.TRUE);

		queryCriteria.select(root)
		             .where(restriction);

		TypedQuery<HRInternalLevel> query =
		        session.createQuery(queryCriteria);

		List<HRInternalLevel> resultList = query.getResultList();

		return resultList.isEmpty() ? null : resultList.get(0);
	}

	public HRInternalLevel getLevelByLevelNo(final Integer levelNo){
//		Criteria criteria = getCurrentSession().createCriteria(HRInternalLevel.class);
//		criteria.add(Restrictions.eq("levelNo",levelNo));
//		criteria.setMaxResults(1);
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List list = (List) criteria.list();
//		return (HRInternalLevel) list.iterator().next();
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<HRInternalLevel> queryCriteria =
		        builder.createQuery(HRInternalLevel.class);

		Root<HRInternalLevel> root =
		        queryCriteria.from(HRInternalLevel.class);

		Predicate restriction =
		        builder.equal(root.get("levelNo"), levelNo);

		queryCriteria.select(root)
		             .where(restriction)
		             .distinct(true);

		TypedQuery<HRInternalLevel> query =
		        session.createQuery(queryCriteria);

		query.setMaxResults(1);

		List<HRInternalLevel> list = query.getResultList();

		return list.isEmpty() ? null : list.get(0);
	}

	public HRGeographicalLevel getGeoLastLevel() {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>..start getLastLevel()");

//		Criteria criteria = getCurrentSession()
//				.createCriteria(HRGeographicalLevel.class);
//		criteria.add(Restrictions.eq("isLastLevel", true));
//		HRGeographicalLevel list = (HRGeographicalLevel)  criteria.uniqueResult();
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.end getLastLevel()");
//		return list;
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<HRGeographicalLevel> queryCriteria =
		        builder.createQuery(HRGeographicalLevel.class);

		Root<HRGeographicalLevel> root =
		        queryCriteria.from(HRGeographicalLevel.class);

		Predicate restriction =
		        builder.equal(root.get("isLastLevel"), Boolean.TRUE);

		queryCriteria.select(root)
		             .where(restriction);

		TypedQuery<HRGeographicalLevel> query =
		        session.createQuery(queryCriteria);

		List<HRGeographicalLevel> resultList = query.getResultList();

		HRGeographicalLevel result =
		        resultList.isEmpty() ? null : resultList.get(0);

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.end getLastLevel()");

		return result;
	}

	public HRGeographicalLevel getGeoLevelByLevelNo(final Integer levelNo){
//		Criteria criteria = getCurrentSession().createCriteria(HRGeographicalLevel.class);
//		criteria.add(Restrictions.eq("levelNo",levelNo));
//		criteria.setMaxResults(1);
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List list = criteria.list();
//		return (HRGeographicalLevel) list.iterator().next();
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<HRGeographicalLevel> queryCriteria =
		        builder.createQuery(HRGeographicalLevel.class);

		Root<HRGeographicalLevel> root =
		        queryCriteria.from(HRGeographicalLevel.class);

		Predicate restriction =
		        builder.equal(root.get("levelNo"), levelNo);

		queryCriteria.select(root)
		             .where(restriction)
		             .distinct(true);

		TypedQuery<HRGeographicalLevel> query =
		        session.createQuery(queryCriteria);

		query.setMaxResults(1);

		List<HRGeographicalLevel> list = query.getResultList();

		return list.isEmpty() ? null : list.get(0);
	}

	public HRQualificationLevel getQualLastLevel() {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>..start getLastLevel()");
//		Criteria criteria = getCurrentSession()
//				.createCriteria(HRQualificationLevel.class);
//		criteria.add(Restrictions.eq("isLastLevel", true));
//		HRQualificationLevel list = (HRQualificationLevel)  criteria.uniqueResult();
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.end getLastLevel()");
//		return list;
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<HRQualificationLevel> queryCriteria =
		        builder.createQuery(HRQualificationLevel.class);

		Root<HRQualificationLevel> root =
		        queryCriteria.from(HRQualificationLevel.class);

		Predicate restriction =
		        builder.equal(root.get("isLastLevel"), Boolean.TRUE);

		queryCriteria.select(root)
		             .where(restriction);

		TypedQuery<HRQualificationLevel> query =
		        session.createQuery(queryCriteria);

		List<HRQualificationLevel> resultList = query.getResultList();

		HRQualificationLevel result =
		        resultList.isEmpty() ? null : resultList.get(0);

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.end getLastLevel()");

		return result;
	}

	public HRQualificationLevel getQualLevelByLevelNo(final Integer levelNo){
//		Criteria criteria = getCurrentSession().createCriteria(HRQualificationLevel.class);
//		criteria.add(Restrictions.eq("levelNo",levelNo));
//		criteria.setMaxResults(1);
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List list = (List)  criteria.list();
//		return (HRQualificationLevel) list.iterator().next();
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<HRQualificationLevel> queryCriteria =
		        builder.createQuery(HRQualificationLevel.class);

		Root<HRQualificationLevel> root =
		        queryCriteria.from(HRQualificationLevel.class);

		Predicate restriction =
		        builder.equal(root.get("levelNo"), levelNo);

		queryCriteria.select(root)
		             .where(restriction)
		             .distinct(true);

		TypedQuery<HRQualificationLevel> query =
		        session.createQuery(queryCriteria);

		query.setMaxResults(1);

		List<HRQualificationLevel> list = query.getResultList();

		return list.isEmpty() ? null : list.get(0);
	}

	public HRSpecialtyLevel getSpecialtyLastLevel() {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(HRSpecialtyLevel.class);
//		criteria.add(Restrictions.eq("isLastLevel", true));
//		HRSpecialtyLevel list = (HRSpecialtyLevel) criteria.uniqueResult();
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.end getLastLevel()");
//		return list;
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<HRSpecialtyLevel> queryCriteria =
		        builder.createQuery(HRSpecialtyLevel.class);

		Root<HRSpecialtyLevel> root =
		        queryCriteria.from(HRSpecialtyLevel.class);

		Predicate restriction =
		        builder.equal(root.get("isLastLevel"), Boolean.TRUE);

		queryCriteria.select(root)
		             .where(restriction);

		TypedQuery<HRSpecialtyLevel> query =
		        session.createQuery(queryCriteria);

		List<HRSpecialtyLevel> resultList = query.getResultList();

		HRSpecialtyLevel result =
		        resultList.isEmpty() ? null : resultList.get(0);

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.end getLastLevel()");

		return result;
	}

	public HRSpecialtyLevel getSpecialtyLevelByLevelNo(final Integer levelNo){
//		Criteria criteria = getCurrentSession().createCriteria(HRSpecialtyLevel.class);
//		criteria.add(Restrictions.eq("levelNo",levelNo));
//		criteria.setMaxResults(1);
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List list = (List) criteria.list();
//		return (HRSpecialtyLevel) list.iterator().next();
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<HRSpecialtyLevel> queryCriteria =
		        builder.createQuery(HRSpecialtyLevel.class);

		Root<HRSpecialtyLevel> root =
		        queryCriteria.from(HRSpecialtyLevel.class);

		Predicate restriction =
		        builder.equal(root.get("levelNo"), levelNo);

		queryCriteria.select(root)
		             .where(restriction)
		             .distinct(true);

		TypedQuery<HRSpecialtyLevel> query =
		        session.createQuery(queryCriteria);

		query.setMaxResults(1);

		List<HRSpecialtyLevel> list = query.getResultList();

		return list.isEmpty() ? null : list.get(0);
	}

	public List getChilderenByParent(final Long parentId,final Class clazz){
//		Criteria criteria = null;
//
//		criteria = getCurrentSession().createCriteria(clazz);
//		criteria.createCriteria("parent").add(Restrictions.eq("id",parentId));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		log.debug("inside getChilderenByParent ");
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = null;
		Root<Object> root = null;
		if(clazz.equals(HRInternalDivision.class)) {
			queryCriteria = getBuilder().createQuery(HRInternalDivision.class);
			root = queryCriteria.from(HRInternalDivision.class);
		}
		else if(clazz.equals(HRGeographicalDivision.class)) {
			queryCriteria = getBuilder().createQuery(HRGeographicalDivision.class);
			root = queryCriteria.from(HRGeographicalDivision.class);
		}
		else if(clazz.equals(HRQualificationDivision.class)) {
			queryCriteria = getBuilder().createQuery(HRQualificationDivision.class);
			root = queryCriteria.from(HRQualificationDivision.class);
		}
		else if(clazz.equals(HRSpecialtyDivision.class)) {
			queryCriteria = getBuilder().createQuery(HRSpecialtyDivision.class);
			root = queryCriteria.from(HRSpecialtyDivision.class);
		}
		
		
		Predicate restrictions =  builder.equal(root.get("parent").get("id"), parentId);
		Order order = builder.asc(root.get("longCode"));
		queryCriteria.select(root).where(restrictions).orderBy(order).distinct(true);
		
		TypedQuery<Object> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
	}

	public List getParents(final Class clazz){
		log.debug("inside getParents ");
//		Criteria criteria = null;
//
//		criteria = getCurrentSession().createCriteria(clazz);
//		criteria.add(Restrictions.isNull("parent"));
//
//
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = null;
		Root<Object> root = null;
		if(clazz.equals(HRInternalDivision.class)) {
			queryCriteria = getBuilder().createQuery(HRInternalDivision.class);
			root = queryCriteria.from(HRInternalDivision.class);
		}
		else if(clazz.equals(HRGeographicalDivision.class)) {
			queryCriteria = getBuilder().createQuery(HRGeographicalDivision.class);
			root = queryCriteria.from(HRGeographicalDivision.class);
		}
		else if(clazz.equals(HRQualificationDivision.class)) {
			queryCriteria = getBuilder().createQuery(HRQualificationDivision.class);
			root = queryCriteria.from(HRQualificationDivision.class);
		}
		else if(clazz.equals(HRSpecialtyDivision.class)) {
			queryCriteria = getBuilder().createQuery(HRSpecialtyDivision.class);
			root = queryCriteria.from(HRSpecialtyDivision.class);
		}
		
		
		Predicate restrictions =  builder.isNull(root.get("parent"));
		Order order = builder.asc(root.get("longCode"));
		queryCriteria.select(root).where(restrictions).orderBy(order).distinct(true);
		
		TypedQuery<Object> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
	}

	public List getChildrenAndGrandChildrenByParentLongCode(final String parentLongCode,final Class clazz)
	{
		log.debug("inside getChildrenAndGrandChildrenByParentLongCode ");
//		Criteria criteria = null;
//		criteria = getCurrentSession().createCriteria(clazz);
//		criteria.add(Restrictions.like("longCode",parentLongCode+"%"));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = null;
		Root<Object> root = null;
		if(clazz.equals(HRInternalDivision.class)) {
			queryCriteria = getBuilder().createQuery(HRInternalDivision.class);
			root = queryCriteria.from(HRInternalDivision.class);
		}
		else if(clazz.equals(HRGeographicalDivision.class)) {
			queryCriteria = getBuilder().createQuery(HRGeographicalDivision.class);
			root = queryCriteria.from(HRGeographicalDivision.class);
		}
		else if(clazz.equals(HRQualificationDivision.class)) {
			queryCriteria = getBuilder().createQuery(HRQualificationDivision.class);
			root = queryCriteria.from(HRQualificationDivision.class);
		}
		else if(clazz.equals(HRSpecialtyDivision.class)) {
			queryCriteria = getBuilder().createQuery(HRSpecialtyDivision.class);
			root = queryCriteria.from(HRSpecialtyDivision.class);
		}
		
		
		Predicate restrictions =  builder.like(root.get("longCode"), parentLongCode+"%");
		Order order = builder.asc(root.get("longCode"));
		queryCriteria.select(root).where(restrictions).orderBy(order).distinct(true);
		
		TypedQuery<Object> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
	}


	public List getAllByCode(final String code,final Class clazz){
//		Criteria criteria =  getCurrentSession().createCriteria(clazz);
//		criteria.add(Restrictions.eq("longCode",code));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = null;
		Root<Object> root = null;
		if(clazz.equals(HRInternalDivision.class)) {
			queryCriteria = getBuilder().createQuery(HRInternalDivision.class);
			root = queryCriteria.from(HRInternalDivision.class);
		}
		else if(clazz.equals(HRGeographicalDivision.class)) {
			queryCriteria = getBuilder().createQuery(HRGeographicalDivision.class);
			root = queryCriteria.from(HRGeographicalDivision.class);
		}
		else if(clazz.equals(HRQualificationDivision.class)) {
			queryCriteria = getBuilder().createQuery(HRQualificationDivision.class);
			root = queryCriteria.from(HRQualificationDivision.class);
		}
		else if(clazz.equals(HRSpecialtyDivision.class)) {
			queryCriteria = getBuilder().createQuery(HRSpecialtyDivision.class);
			root = queryCriteria.from(HRSpecialtyDivision.class);
		}
		
		
		Predicate restrictions =  builder.equal(root.get("longCode"), code);
		Order order = builder.asc(root.get("longCode"));
		queryCriteria.select(root).where(restrictions).orderBy(order).distinct(true);
		
		TypedQuery<Object> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
	}


	public List getLeafsByParentId(final Long parentId,final String className) {
		log.debug("inside getLeafsByParentId");
		
		log.debug("inside getRoot ");
		
		CriteriaBuilder builder = getBuilder();
    	Session session = getCurrentSession();
    	
    	CriteriaQuery queryCriteria = null;
    	Root<Object> root = null;
    	if(className.equals("HRInternalDivision")) {
    		queryCriteria = getBuilder().createQuery(HRInternalDivision.class);
    		root = queryCriteria.from(HRInternalDivision.class);
    	}
		else if(className.equals("HRGeographicalDivision")) {
			queryCriteria = getBuilder().createQuery(HRGeographicalDivision.class);
			root = queryCriteria.from(HRGeographicalDivision.class);
		}
		else if(className.equals("HRQualificationDivision")) {
			queryCriteria = getBuilder().createQuery(HRQualificationDivision.class);
			root = queryCriteria.from(HRQualificationDivision.class);
		}
		else if(className.equals("HRSpecialtyDivision")) {
			queryCriteria = getBuilder().createQuery(HRSpecialtyDivision.class);
			root = queryCriteria.from(HRSpecialtyDivision.class);
		}
    	
    	
    	Predicate restrictions =  builder.equal(root.get("parent").get("id"), parentId);
    	Order order = builder.asc(root.get("longCode"));
    	queryCriteria.select(root).where(restrictions).orderBy(order).distinct(true);
    	
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
    	log.debug("querycriteria " + queryCriteria);
    	log.debug("query " + query);
    	
        return query.getResultList();
		
//		Criteria criteria=null;
//		if(className.equals("HRInternalDivision"))
//		{
//			criteria = getCurrentSession().createCriteria(HRInternalDivision.class);
//		}
//
//		else if(className.equals("HRQualificationDivision"))
//		{
//			criteria = getCurrentSession().createCriteria(HRQualificationDivision.class);
//		}
//
//		else if(className.equals("HRGeographicalDivision"))
//		{
//			criteria = getCurrentSession().createCriteria(HRGeographicalDivision.class);
//		}
//
//		else if(className.equals("HRSpecialtyDivision"))
//		{
//			criteria = getCurrentSession().createCriteria(HRSpecialtyDivision.class);
//		}
//		criteria.createCriteria("parent").add(Restrictions.eq("id",parentId));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		criteria.addOrder(Order.asc("longCode"));
//		return criteria.list();
	}

	public List getLevelsByDivisionParentId(final Integer levelNo,final Class clazz){
		log.debug("inside LevelsByDivisionParentId");
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = getBuilder().createQuery(clazz);
		Root<Object> root = queryCriteria.from(clazz);
		
		Predicate restrictions =  builder.ge(root.get("levelNo"), levelNo);
		Order order = builder.asc(root.get("levelNo"));
		queryCriteria.select(root).where(restrictions).orderBy(order).distinct(true);
		
		TypedQuery<Object> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
		
//		Criteria criteria = null;
//		criteria = getCurrentSession().createCriteria(clazz);
//		criteria.add(Restrictions.ge("levelNo",levelNo));
//		criteria.addOrder(Order.asc("levelNo"));
//		return criteria.list();
	}

	public List getExistingDivisionsForparent(final Class clazz,final String parentCode){
		log.debug("inside getExistingDivisionsForparent");  
		log.debug("parentCode>>>>>>>>>>"+parentCode);
//		Criteria criteria = getCurrentSession().createCriteria(clazz);
//		criteria.add(Restrictions.isNull("divisionLevel"));
//		criteria.add(Restrictions.eq("code",parentCode));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = getBuilder().createQuery(clazz);
		Root<Object> root = queryCriteria.from(clazz);
		
		Predicate restrictions =  builder.and(
		        builder.isNull(root.get("divisionLevel")),
		        builder.equal(root.get("code"), parentCode)
		);
		Order order = builder.asc(root.get("longCode"));
		queryCriteria.select(root).where(restrictions).orderBy(order).distinct(true);
		
		TypedQuery<Object> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
	}

	public List getExistingDivisionsForParentForCopy(final Class clazz,final Long parentId,final String code){
		log.debug("inside getExistingDivisionsForparentforcopy");  
		log.debug("parentId>>>>>>>>>>"+parentId);
		log.debug("code>>>>>>>>>>>>>>>>>>>"+code);
//		Criteria criteria = getCurrentSession().createCriteria(clazz);
//		criteria.add(Restrictions.isNull("divisionLevel"));
//		criteria.add(Restrictions.eq("code", code));
//		criteria.add(Restrictions.eq("id",parentId));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = getBuilder().createQuery(clazz);
		Root<Object> root = queryCriteria.from(clazz);
		
		Predicate restrictions =  builder.and(
		        builder.isNull(root.get("divisionLevel")),
		        builder.equal(root.get("code"), code),
		        builder.equal(root.get("id"), parentId)
		);
		Order order = builder.asc(root.get("longCode"));
		queryCriteria.select(root).where(restrictions).orderBy(order).distinct(true);
		
		TypedQuery<Object> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
	}

	public List getExistingDivisionsForparent(final Class clazz,final Long id,final String code){
		log.debug("inside getExistingDivisionsForparent");  
		log.debug("id>>>>>>>>>>"+id);
		log.debug("levelID>>>>>>>>>>>>>>>>>>>>>>>>"+code);
//		Criteria criteria = getCurrentSession().createCriteria(clazz);
//		criteria.add(Restrictions.isNull("divisionLevel"));
//		criteria.add(Restrictions.eq("code", code));
//		criteria.createCriteria("parent").add(Restrictions.eq("id",id));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = getBuilder().createQuery(clazz);
		Root<Object> root = queryCriteria.from(clazz);
		
		Predicate restrictions =  builder.and(
		        builder.isNull(root.get("divisionLevel")),
		        builder.equal(root.get("code"), code),
		        builder.equal(root.get("parent").get("id"), id)
		);
		Order order = builder.asc(root.get("longCode"));
		queryCriteria.select(root).where(restrictions).orderBy(order).distinct(true);
		
		TypedQuery<Object> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
	}

	public List getExistingDivisionsForNullDivLevelParent(final Class clazz,final Long id,final String code){
		log.debug("inside getExistingDivisionsForNullDivLevelParent");  
		log.debug("id>>>>>>>>>>"+id);
		log.debug("levelID>>>>>>>>>>>>>>>>>>>>>>>>"+code);
//		Criteria criteria = getCurrentSession().createCriteria(clazz);
//		criteria.add(Restrictions.isNull("divisionLevel"));
//		criteria.add(Restrictions.eq("code", code));
//		criteria.add(Restrictions.eq("id",id));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = getBuilder().createQuery(clazz);
		Root<Object> root = queryCriteria.from(clazz);
		
		Predicate restrictions =  builder.and(
		        builder.isNull(root.get("divisionLevel")),
		        builder.equal(root.get("code"), code),
		        builder.equal(root.get("id"), id)
		);
		Order order = builder.asc(root.get("longCode"));
		queryCriteria.select(root).where(restrictions).orderBy(order).distinct(true);
		
		TypedQuery<Object> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
	}


	public List getHigherDivisions(final Class clazz,final Long id,final Integer levelNo)
	{
		log.debug("inside getHigherDivisions");  
		log.debug("Id>>>>>>>>>>"+id);
//		Criteria criteria = getCurrentSession().createCriteria(clazz);
//		criteria.add(Restrictions.isNotNull("divisionLevel"));
//		criteria.add(Restrictions.ne("id", id));
//		criteria.createCriteria("divisionLevel").add(Restrictions.lt("levelNo", levelNo));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = getBuilder().createQuery(clazz);
		Root<Object> root = queryCriteria.from(clazz);
		
		Predicate restrictions =  builder.and(
		        builder.isNotNull(root.get("divisionLevel")),
		        builder.notEqual(root.get("id"), id),
		        builder.lessThan(root.get("divisionLevel").get("levelNo"), levelNo)
		);
		Order order = builder.asc(root.get("longCode"));
		queryCriteria.select(root).where(restrictions).orderBy(order).distinct(true);
		
		TypedQuery<Object> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
	}


	public List getCountriesForNationality()
	{
		log.debug("inside getCountriesForNationality");  

//		Criteria criteria = getCurrentSession().createCriteria(HRGeographicalDivision.class);
//		criteria.add(Restrictions.isNotNull("divisionLevel"));
//		criteria.createCriteria("divisionLevel").add(Restrictions.eq("isNationalityCountry", new Boolean(true)));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = getBuilder().createQuery(HRGeographicalDivision.class);
		Root<Object> root = queryCriteria.from(HRGeographicalDivision.class);
		
		Predicate restrictions =  builder.and(
		        builder.isNotNull(root.get("divisionLevel")),
		        builder.equal(root.get("divisionLevel").get("isNationalityCountry"), Boolean.TRUE)
		);
		Order order = builder.asc(root.get("longCode"));
		queryCriteria.select(root).where(restrictions).orderBy(order).distinct(true);
		
		TypedQuery<Object> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
	}

	public List getDivisionChildren(final Class clazz,final String longCode)
	{
		log.debug("inside getDivisionChildren");  
		log.debug("longCode>>>>>>>>>>"+longCode);
		
		CriteriaBuilder builder = getBuilder();
    	Session session = getCurrentSession();
    	
    	CriteriaQuery queryCriteria = null;
    	Root<Object> root = null;
    		queryCriteria = getBuilder().createQuery(clazz);
    		root = queryCriteria.from(clazz);
    	
    	
    	Predicate restrictions =  builder.like(root.get("longCode"), longCode+"%");
    	Order order = builder.asc(root.get("longCode"));
    	queryCriteria.select(root).where(restrictions).orderBy(order).distinct(true);
    	
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
    	log.debug("querycriteria " + queryCriteria);
    	log.debug("query " + query);
    	
        return query.getResultList();

//		Criteria criteria = getCurrentSession().createCriteria(clazz);
//		criteria.add(Restrictions.like("longCode", longCode,MatchMode.START));
//		criteria.addOrder(Order.asc("longCode"));	
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
	}

	public HRSpecialtyDivision getSpecialtyDivisionForTransaction(){
		
		CriteriaQuery queryCriteria = getBuilder().createQuery(HRSpecialtyDivision.class);
    	Root<Object> root = queryCriteria.from(HRSpecialtyDivision.class);
    	
    	CriteriaBuilder builder = getBuilder();
    	Session session = getCurrentSession();
    	
    	Order order = builder.desc(root.get("divisionLevel").get("levelNo"));
    	queryCriteria.select(root).orderBy(order).distinct(true);
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
    	log.debug("querycriteria " + queryCriteria);
    	log.debug("query " + query);
    	
        List list =  query.getResultList();
        return (HRSpecialtyDivision) list.iterator().next();
		
//		Criteria criteria = getCurrentSession().createCriteria(HRSpecialtyDivision.class);
//		criteria.createCriteria("divisionLevel").addOrder(Order.desc("levelNo"));
//		criteria.setMaxResults(1);
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List list = (List)criteria.list();
//		return (HRSpecialtyDivision) list.iterator().next();
	}

	public HRQualificationDivision getQualificationDivisionForTransaction(){
		
		CriteriaQuery queryCriteria = getBuilder().createQuery(HRQualificationDivision.class);
    	Root<Object> root = queryCriteria.from(HRQualificationDivision.class);
    	
    	CriteriaBuilder builder = getBuilder();
    	Session session = getCurrentSession();
    	
    	Order order = builder.desc(root.get("divisionLevel").get("levelNo"));
    	queryCriteria.select(root).orderBy(order).distinct(true);
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
    	log.debug("querycriteria " + queryCriteria);
    	log.debug("query " + query);
    	
        List list =  query.getResultList();
        return (HRQualificationDivision) list.iterator().next();
        
        
//		Criteria criteria = getCurrentSession().createCriteria(HRQualificationDivision.class);
//		criteria.createCriteria("divisionLevel").addOrder(Order.desc("levelNo"));
//		criteria.setMaxResults(1);
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List list = (List) criteria.list();
//		return (HRQualificationDivision) list.iterator().next();
	}

	public HRInternalDivision getInternalDivisionForTransaction(){
		CriteriaQuery queryCriteria = getBuilder().createQuery(HRInternalDivision.class);
    	Root<Object> root = queryCriteria.from(HRInternalDivision.class);
    	
    	CriteriaBuilder builder = getBuilder();
    	Session session = getCurrentSession();
    	
    	Order order = builder.desc(root.get("divisionLevel").get("levelNo"));
    	queryCriteria.select(root).orderBy(order).distinct(true);
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
    	log.debug("querycriteria " + queryCriteria);
    	log.debug("query " + query);
    	
        List list =  query.getResultList();
        return (HRInternalDivision) list.iterator().next();
        
//		Criteria criteria = getCurrentSession().createCriteria(HRInternalDivision.class);
//		criteria.createCriteria("divisionLevel").addOrder(Order.desc("levelNo"));
//		criteria.setMaxResults(1);
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List list = (List) criteria.list();
//		return (HRInternalDivision) list.iterator().next();
	}


	public List getEmployeesForEmployeeVacationAtInstallation(final String empCode,final String empName,final HRInternalDivision division)
	{
//		Criteria criteria = getCurrentSession().createCriteria(HREmployee.class);
//		if(empCode!=null && !empCode.equals(""))
//		{
//			criteria.add(Restrictions.eq("empCode",empCode));
//		}
//		if(empName!=null && !empName.equals(""))
//		{
//			criteria.add(Restrictions.eq("name",empName));
//		}
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = getBuilder().createQuery(HREmployee.class);
		Root<HREmployee> root = queryCriteria.from(HREmployee.class);
		
		Predicate restrictions = builder.conjunction();
		
		if(empCode!=null && !empCode.equals("")) {
			restrictions = builder.and(restrictions, builder.equal(root.get("empCode"), empCode));
		}
		if(empName!=null && !empName.equals("")) {
			restrictions = builder.and(restrictions, builder.equal(root.get("name"), empName));
		}
		
		queryCriteria.select(root).where(restrictions).distinct(true);
		
		TypedQuery<HREmployee> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
	}



	public List getEmployeeVacationAtInstall(final HREmployee employee,final HRVacation vacation)
	{
//		Criteria criteria = getCurrentSession().createCriteria(HREmployeeVacationInstall.class);
//		if(employee!=null)
//		{
//			criteria.add(Restrictions.eq("employee",employee));
//		}
//		if(vacation!=null)
//		{
//			criteria.add(Restrictions.eq("vacation",vacation));
//		}
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = getBuilder().createQuery(HREmployeeVacationInstall.class);
		Root<HREmployeeVacationInstall> root = queryCriteria.from(HREmployeeVacationInstall.class);
		
		Predicate restrictions = builder.conjunction();
		
		if(employee!=null) {
			restrictions = builder.and(restrictions, builder.equal(root.get("employee"), employee));
		}
		if(vacation!=null) {
			restrictions = builder.and(restrictions, builder.equal(root.get("vacation"), vacation));
		}
		
		queryCriteria.select(root).where(restrictions).distinct(true);
		
		TypedQuery<HREmployeeVacationInstall> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
	}

	public List getEmployeesForEmployeeServiceLength(final String empCode,final String empName)
	{
//		Criteria criteria = getCurrentSession().createCriteria(HREmployee.class);
//		if(empCode!=null && !empCode.equals(""))
//		{
//			criteria.add(Restrictions.eq("empCode",empCode));
//		}
//		if(empName!=null && !empName.equals(""))
//		{
//			criteria.add(Restrictions.eq("name",empName));
//		}
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = getBuilder().createQuery(HREmployee.class);
		Root<HREmployee> root = queryCriteria.from(HREmployee.class);
		
		Predicate restrictions = builder.conjunction();
		
		if(empCode!=null && !empCode.equals("")) {
			restrictions = builder.and(restrictions, builder.equal(root.get("empCode"), empCode));
		}
		if(empName!=null && !empName.equals("")) {
			restrictions = builder.and(restrictions, builder.equal(root.get("name"), empName));
		}
		
		queryCriteria.select(root).where(restrictions).distinct(true);
		
		TypedQuery<HREmployee> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
	}

	public List getEmployeeServiceLength(final HREmployee employee)
	{
//		Criteria criteria = getCurrentSession().createCriteria(HRServiceLengthCalculation.class);
//		if(employee!=null)
//		{
//			criteria.add(Restrictions.eq("employee",employee));
//		}
//
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = getBuilder().createQuery(HRServiceLengthCalculation.class);
		Root<HRServiceLengthCalculation> root = queryCriteria.from(HRServiceLengthCalculation.class);
		
		Predicate restrictions = builder.conjunction();
		
		if(employee!=null) {
			restrictions = builder.and(restrictions, builder.equal(root.get("employee"), employee));
		}
		
		queryCriteria.select(root).where(restrictions).distinct(true);
		
		TypedQuery<HRServiceLengthCalculation> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
	}


	public List getEmployeesForEmployeeVacation(final String empCode,final String empName,final HRInternalDivision division)
	{
//		Criteria criteria = getCurrentSession().createCriteria(HREmployee.class);
//		if(empCode!=null && !empCode.equals(""))
//		{
//			criteria.add(Restrictions.eq("empCode",empCode));
//		}
//		if(empName!=null && !empName.equals(""))
//		{
//			criteria.add(Restrictions.eq("name",empName));
//		}
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = getBuilder().createQuery(HREmployee.class);
		Root<HREmployee> root = queryCriteria.from(HREmployee.class);
		
		Predicate restrictions = builder.conjunction();
		
		if(empCode!=null && !empCode.equals("")) {
			restrictions = builder.and(restrictions, builder.equal(root.get("empCode"), empCode));
		}
		if(empName!=null && !empName.equals("")) {
			restrictions = builder.and(restrictions, builder.equal(root.get("name"), empName));
		}
		
		queryCriteria.select(root).where(restrictions).distinct(true);
		
		TypedQuery<HREmployee> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
	}


	public List getEmployeeVacation(final HREmployee employee,final HRVacation vacation)
	{
//		Criteria criteria = getCurrentSession().createCriteria(HREmployeeVacation.class);
//		if(employee!=null)
//		{
//			criteria.add(Restrictions.eq("employee",employee));
//		}
//		if(vacation!=null)
//		{
//			criteria.add(Restrictions.eq("vacation",vacation));
//		}
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = getBuilder().createQuery(HREmployeeVacation.class);
		Root<HREmployeeVacation> root = queryCriteria.from(HREmployeeVacation.class);
		
		Predicate restrictions = builder.conjunction();
		
		if(employee!=null) {
			restrictions = builder.and(restrictions, builder.equal(root.get("employee"), employee));
		}
		if(vacation!=null) {
			restrictions = builder.and(restrictions, builder.equal(root.get("vacation"), vacation));
		}
		
		queryCriteria.select(root).where(restrictions).distinct(true);
		
		TypedQuery<HREmployeeVacation> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
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
//		Criteria criteria = getCurrentSession().createCriteria(HREmployee.class);
//		if(empName!=null && !empName.equals(""))
//		{
//			criteria.add(Restrictions.eq("name",empName));
//		}
//		if(codeFrom!=null && !codeFrom.equals(""))
//		{
//			criteria.add(Restrictions.ge("empCode",codeFrom));
//		}
//
//		if(codeTo!=null && !codeTo.equals(""))
//		{
//			criteria.add(Restrictions.le("empCode",codeTo));
//		}
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = getBuilder();
		Session session = getCurrentSession();
		
		CriteriaQuery queryCriteria = getBuilder().createQuery(HREmployee.class);
		Root<HREmployee> root = queryCriteria.from(HREmployee.class);
		
		Predicate restrictions = builder.conjunction();
		
		if(empName!=null && !empName.equals("")) {
			restrictions = builder.and(restrictions, builder.equal(root.get("name"), empName));
		}
		if(codeFrom!=null && !codeFrom.equals("")) {
			restrictions = builder.and(restrictions, builder.greaterThanOrEqualTo(root.get("empCode"), codeFrom));
		}
		if(codeTo!=null && !codeTo.equals("")) {
			restrictions = builder.and(restrictions, builder.lessThanOrEqualTo(root.get("empCode"), codeTo));
		}
		
		queryCriteria.select(root).where(restrictions).distinct(true);
		
		TypedQuery<HREmployee> query = session.createQuery(queryCriteria);
		log.debug("querycriteria " + queryCriteria);
		log.debug("query " + query);
		
		return query.getResultList();
	}

	public List getFilteredGroupedAInsuranceCala(final String month, final String year, final String insurance, final String emp_code, final String empName, final String insuranceNo, final String subscriptionDate, final String groupBy){
//
//		List aicList = new ArrayList();
//		List empList = new ArrayList();
//
//		log.debug("HR_DAO>> Month and Year != Null: "+month+"-"+year);
//
//		if((insurance != null && !insurance.equals(""))||(emp_code!=null && !emp_code.equals(""))
//				||(empName!=null && !empName.equals(""))||(insuranceNo!=null && !insuranceNo.equals(""))){
//			Criteria criteria = getCurrentSession().createCriteria(HREmployee.class);
//			if(insurance != null && !insurance.equals("")){
//				log.debug("HR_DAO>> Insurance_Code != Null: "+insurance);
//				criteria.add(Restrictions.eq("insuranceCode",Long.parseLong(insurance)));
//			}
//			if(emp_code!=null && !emp_code.equals("")){
//				log.debug("HR_DAO>> Emp_Code != Null: "+emp_code);
//				criteria.add(Restrictions.eq("empCode",emp_code));
//			}
//			if(empName!=null && !empName.equals("")){
//				log.debug("HR_DAO>> Emp_Name != Null: "+empName);
//				criteria.add(Restrictions.eq("name",empName));
//			}
//			if(insuranceNo!=null && !insuranceNo.equals("")){
//				log.debug("HR_DAO>> Insurance_Number != Null: "+insuranceNo);
//				criteria.add(Restrictions.eq("insuranceNumber",insuranceNo));
//			}
//			if(groupBy != null && groupBy.length() != 0){
//				if(groupBy.equals("3")){
//					log.debug("HR_DAO>> Group_By != Null: "+groupBy);
//					criteria.addOrder(Property.forName("currentEmpJob").asc());
//				}
//			}
//			empList = (List)criteria.list();
//		}
//		log.debug("HR_DAO>> ((1)) Emp List Size: "+empList.size());
//
//		List orderlist = new ArrayList();
//		orderlist.add("emp_code");
//
//		if(subscriptionDate!=null && !subscriptionDate.equals("")){
//			log.debug("HR_DAO>> Subscription_Date != Null: "+subscriptionDate);
//			String subDate[]=subscriptionDate.split("-");
//			log.debug("HR_DAO>> Subscription_Date after split: "+subDate[0]+"  -  "+subDate[1]+"  -  "+subDate[2]);
//			List tempList = new ArrayList();
//			if(empList != null && empList.size() > 0){
//				for(int i=0; i<empList.size(); i++){
//					HREmployee e = (HREmployee) empList.get(i);
//					log.debug("HR_DAO>> "+e);
//					if(e != null){
//						String empSubDate[] = e.getSubscriptionDate().toString().split("-");
//						log.debug("HR_DAO>> Emp_Subscription_Date after split: "+empSubDate[0]+"  -  "+empSubDate[1]+"  -  "+empSubDate[2].toString().split(" ")[0]);
//						if((Integer.parseInt(subDate[0])==Integer.parseInt(empSubDate[2].split(" ")[0]))&&(Integer.parseInt(subDate[1])==Integer.parseInt(empSubDate[1]))&&(Integer.parseInt(subDate[2])==Integer.parseInt(empSubDate[0]))){
//							tempList.add(e);
//						}
//					}
//				}
//				empList = tempList;
//			}else{
//				List aics = getObjectsByTwoParametersOrderedByFieldList(AInsuranceCala.class, "month", month, "year", year, orderlist);
//				for(int i=0; i<aics.size(); i++){
//					AInsuranceCala a = (AInsuranceCala) aics.get(i);
//					HREmployee e = (HREmployee) getObjectByParameter(HREmployee.class, "empCode", a.getEmp_code());
//					String empSubDate[] = e.getSubscriptionDate().toString().split("-");
//					log.debug("HR_DAO>> Emp_Subscription_Date after split: "+empSubDate[0]+"  -  "+empSubDate[1]+"  -  "+empSubDate[2].toString().split(" ")[0]);
//					if((Integer.parseInt(subDate[0])==Integer.parseInt(empSubDate[2].split(" ")[0]))&&(Integer.parseInt(subDate[1])==Integer.parseInt(empSubDate[1]))&&(Integer.parseInt(subDate[2])==Integer.parseInt(empSubDate[0]))){
//						empList.add(e);
//					}
//				}
//				if(groupBy!=null && !groupBy.equals("")){
//					log.debug("HR_DAO>> Group_By != Null: "+groupBy);
//					String arr[][] = new String [empList.size()][2];
//					for(int i=0; i<empList.size(); i++){
//						HREmployee e = (HREmployee) empList.get(i);
//						log.debug("HR_DAO>> Emp: "+e);
//						if(groupBy.equals("3")){
//							if(e.getCurrentEmpJob().getId() != null){
//								arr[i][0] = e.getCurrentEmpJob().getId()+"";
//								arr[i][1] = e.getEmpCode();
//								log.debug("HR_DAO>> job "+arr[i][0]+" code "+arr[i][1]);
//							}	
//						}	
//					}
//					Arrays.sort(arr, new Comparator<String[]>() {
//						public int compare(final String[] entry1, final String[] entry2) {
//							final String job1 = entry1[0];
//							final String job2 = entry2[0];
//							return job1.compareTo(job2);
//						}
//					});
//
//					empList = new ArrayList();
//					for(int i=0; i<arr.length; i++){
//						orderlist = new ArrayList();
//						orderlist.add("empCode");
//						HREmployeeJob job = (HREmployeeJob) getObjectByParameter(HREmployeeJob.class, "id", Long.parseLong(arr[i][0]));
//						log.debug("HR_DAO>> Emp_Job"+job.getId());
//						HREmployee e = (HREmployee) getObjectByTwoParameters(HREmployee.class, "currentEmpJob", job, "empCode", arr[i][1]+"");
//						log.debug("HR_DAO>> Emp: "+e);
//						empList.add(e);
//					}
//				}	
//			}
//		}
//		log.debug("HR_DAO>> ((2)) Emp List Size: "+empList.size());
//
//		for(int i=0; i<empList.size(); i++){
//			HREmployee e = (HREmployee) empList.get(i);
//			orderlist = new ArrayList();
//			orderlist.add("month");
//			List aics = getObjectsByThreeParametersOrderedByFieldList(AInsuranceCala.class, "month", month, "year", year, "emp_code", e.getEmpCode(), orderlist);
//
//			if(aics != null && aics.size() == 1){
//				log.debug("HR_DAO>> AIC != null: "+aics);
//				aicList.add(aics.get(0));
//			}
//		}
//		log.debug("HR_DAO>> ((3)) Aic List Size: "+aicList.size());
//
//		if((empList == null || empList.size() == 0) && ((insurance == null || insurance.equals(""))&&(emp_code==null 
//				|| emp_code.equals(""))&&(empName==null || empName.equals(""))&&(insuranceNo==null 
//				|| insuranceNo.equals(""))&&(subscriptionDate==null || subscriptionDate.equals("")))){
//			log.debug("HR_DAO>> ALL NULL BUT MONTH AND YEAR");
//			aicList = getObjectsByTwoParametersOrderedByFieldList(AInsuranceCala.class, "month", month, "year", year, orderlist);
//
//			if(groupBy!=null && !groupBy.equals("")){
//				log.debug("HR_DAO>> Group_By != Null: "+groupBy);
//				String arr[][] = new String [aicList.size()][2];
//				for(int i=0; i<aicList.size(); i++){
//					AInsuranceCala a = (AInsuranceCala) aicList.get(i);
//					log.debug("HR_DAO>> AIC: "+a);
//					HREmployee e = (HREmployee) getObjectByParameter(HREmployee.class, "empCode", a.getEmp_code());
//					log.debug("HR_DAO>> Emp: "+e);
//					if(groupBy.equals("3")){
//						if(e.getCurrentEmpJob().getId() != null){
//							arr[i][0] = e.getCurrentEmpJob().getId()+"";
//							arr[i][1] = e.getEmpCode();
//							log.debug("HR_DAO>> job "+arr[i][0]+", code "+arr[i][1]);
//						}	
//					}	
//				}
//				Arrays.sort(arr, new Comparator<String[]>() {
//					public int compare(final String[] entry1, final String[] entry2) {
//						final String job1 = entry1[0];
//						final String job2 = entry2[0];
//						return job1.compareTo(job2);
//					}
//				});
//				aicList = new ArrayList();
//				for(int i=0; i<arr.length; i++){
//					HREmployeeJob job = (HREmployeeJob) getObjectByParameter(HREmployeeJob.class, "id", Long.parseLong(arr[i][0]));
//					log.debug("HR_DAO>> Emp_Job: "+job);
//					orderlist = new ArrayList();
//					orderlist.add("empCode");
//					HREmployee e = (HREmployee) getObjectByTwoParameters(HREmployee.class, "currentEmpJob", job, "empCode", arr[i][1]+"");
//					log.debug("HR_DAO>> Emp: "+e);
//					orderlist = new ArrayList();
//					orderlist.add("emp_code");
//					List aics =  getObjectsByThreeParametersOrderedByFieldList(AInsuranceCala.class, "month", month, "year", year, "emp_code", e.getEmpCode(), orderlist);
//					if(aics != null && aics.size() == 1){
//						log.debug("HR_DAO>> AIC: "+aics.get(0));
//						aicList.add(aics.get(0));
//					}
//				}
//			}
//		}
//		log.debug("HR_DAO>> ((4)) Aic List Size: "+aicList.size());
//
//		return  aicList;

	    CriteriaBuilder builder = super.getBuilder();
	    Session session = super.getCurrentSession();

	    /*
	     * =========================================================
	     * 1. BUILD EMPLOYEE QUERY (ONE DB CALL)
	     * =========================================================
	     */

	    CriteriaQuery<HREmployee> empQuery =
	            builder.createQuery(HREmployee.class);

	    Root<HREmployee> empRoot =
	            empQuery.from(HREmployee.class);

	    List<Predicate> empPredicates = new ArrayList<>();

	    if (insurance != null && !insurance.isEmpty()) {
	        empPredicates.add(
	                builder.equal(empRoot.get("insuranceCode"),
	                        Long.parseLong(insurance))
	        );
	    }

	    if (emp_code != null && !emp_code.isEmpty()) {
	        empPredicates.add(
	                builder.equal(empRoot.get("empCode"), emp_code)
	        );
	    }

	    if (empName != null && !empName.isEmpty()) {
	        empPredicates.add(
	                builder.equal(empRoot.get("name"), empName)
	        );
	    }

	    if (insuranceNo != null && !insuranceNo.isEmpty()) {
	        empPredicates.add(
	                builder.equal(empRoot.get("insuranceNumber"), insuranceNo)
	        );
	    }

	    /*
	     * Optional grouping sort (replaces manual array sorting)
	     */
	    List<Order> empOrders = new ArrayList<>();

	    if ("3".equals(groupBy)) {
	        empOrders.add(
	                builder.asc(empRoot.get("currentEmpJob").get("id"))
	        );
	    }

	    empOrders.add(builder.asc(empRoot.get("empCode")));

	    empQuery.select(empRoot)
	            .where(empPredicates.toArray(new Predicate[0]))
	            .distinct(true)
	            .orderBy(empOrders);

	    List<HREmployee> empList =
	            session.createQuery(empQuery).getResultList();


	    /*
	     * =========================================================
	     * 2. IF NO EMPLOYEES → fallback direct AIC query
	     * =========================================================
	     */

	    List<AInsuranceCala> aicList = new ArrayList<>();

	    CriteriaQuery<AInsuranceCala> aicQuery =
	            builder.createQuery(AInsuranceCala.class);

	    Root<AInsuranceCala> aicRoot =
	            aicQuery.from(AInsuranceCala.class);

	    List<Predicate> aicPredicates = new ArrayList<>();

	    aicPredicates.add(builder.equal(aicRoot.get("month"), month));
	    aicPredicates.add(builder.equal(aicRoot.get("year"), year));

	    if (empList == null || empList.isEmpty()) {

	        aicQuery.select(aicRoot)
	                .where(aicPredicates.toArray(new Predicate[0]))
	                .orderBy(builder.asc(aicRoot.get("empCode")));

	        return session.createQuery(aicQuery).getResultList();
	    }

	    /*
	     * =========================================================
	     * 3. BUILD AIC QUERY USING IN CLAUSE (NO LOOP DB CALLS)
	     * =========================================================
	     */

	    List<String> empCodes = empList.stream()
	            .map(HREmployee::getEmpCode)
	             .collect(Collectors.toList());

	    aicPredicates.add(aicRoot.get("empCode").in(empCodes));

	    aicQuery.select(aicRoot)
	            .where(aicPredicates.toArray(new Predicate[0]))
	            .orderBy(builder.asc(aicRoot.get("empCode")));

	    aicList = session.createQuery(aicQuery).getResultList();

	    /*
	     * =========================================================
	     * 4. RETURN RESULT
	     * =========================================================
	     */

	    return aicList;
}

	@Transactional
	public List getRequestsByDatePeriodAndRequestType(final Date fromDate, final Date toDate, final Long requestType){
//		Calendar cFrom= Calendar.getInstance();
//		cFrom.setTime(fromDate);
//		cFrom.set(Calendar.HOUR_OF_DAY, 0);
//		cFrom.set(Calendar.MINUTE, 0);
//		cFrom.set(Calendar.SECOND, 0);
//		//cFrom.set(Calendar.AM_PM, -1);
//		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
//		final Date dateFrom=cFrom.getTime();
//
//		log.debug("------dateFrom---"+dateFrom);
//
//		Calendar cTo= Calendar.getInstance();
//		cTo.setTime(toDate);
//		cTo.set(Calendar.HOUR_OF_DAY, 23);
//		cTo.set(Calendar.MINUTE, 59);
//		cTo.set(Calendar.SECOND, 59);
//
//		final Date dateTo=cTo.getTime();
//		log.debug("------dateTo---"+dateTo);
//		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		List list =new ArrayList();
//		try {
//			final Date startDate =(Date) format.parse(format.format(dateFrom));
//			final Date endDate = (Date)format.parse(format.format(dateTo));
//
//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			criteria.add(Expression.ge("request_date", startDate));
//			criteria.add(Expression.le("request_date", endDate));
//			if (requestType.equals(1)){
//				criteria.add(Restrictions.or(
//						Restrictions.eq("request_id.id", requestType),
//						Restrictions.eq("request_id.id", 4))
//						);
//			} else {
//				criteria.add(Restrictions.eq("request_id.id", requestType));
//			}
//			criteria.addOrder(Property.forName("period_from").asc());
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			list = (List)criteria.list();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
		
		Calendar cFrom = Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);

		final Date dateFrom = cFrom.getTime();

		log.debug("------dateFrom---" + dateFrom);

		Calendar cTo = Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo = cTo.getTime();

		log.debug("------dateTo---" + dateTo);

		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		List<LoginUsersRequests> list = new ArrayList<>();

		try {

		    final Date startDate = format.parse(format.format(dateFrom));
		    final Date endDate = format.parse(format.format(dateTo));

		    CriteriaBuilder builder = super.getBuilder();
		    Session session = super.getCurrentSession();

		    CriteriaQuery<LoginUsersRequests> queryCriteria =
		            builder.createQuery(LoginUsersRequests.class);

		    Root<LoginUsersRequests> root =
		            queryCriteria.from(LoginUsersRequests.class);

		    List<Predicate> predicates = new ArrayList<>();

		    predicates.add(
		            builder.greaterThanOrEqualTo(
		                    root.get("request_date"),
		                    startDate
		            )
		    );

		    predicates.add(
		            builder.lessThanOrEqualTo(
		                    root.get("request_date"),
		                    endDate
		            )
		    );

		    if (requestType.equals(1L) || requestType.equals(1)) {

		        predicates.add(
		                builder.or(
		                        builder.equal(
		                                root.get("request_id").get("id"),
		                                requestType
		                        ),
		                        builder.equal(
		                                root.get("request_id").get("id"),
		                                4L
		                        )
		                )
		        );

		    } else {

		        predicates.add(
		                builder.equal(
		                        root.get("request_id").get("id"),
		                        requestType
		                )
		        );
		    }

		    queryCriteria.select(root)
		                 .where(predicates.toArray(new Predicate[0]))
		                 .distinct(true)
		                 .orderBy(
		                         builder.asc(
		                                 root.get("period_from")
		                         )
		                 );

		    TypedQuery<LoginUsersRequests> query =
		            session.createQuery(queryCriteria);

		    list = query.getResultList();

		} catch (ParseException e) {
		    e.printStackTrace();
		}

		return list;
	}


	public List getRequests(Date fromDate, Date toDate, Long requestType,
			Date exactFrom, Date exactTo, Date periodFrom, Date periodTo,
			String empCode, String codeFrom, String codeTo, Long statusId) {
//		Calendar cFrom= Calendar.getInstance();
//		
//		Date dateFrom=null;
//		Date dateTo= null;
//		if (fromDate !=null) {
//			cFrom.setTime(fromDate);
//			cFrom.set(Calendar.HOUR_OF_DAY, 0);
//			cFrom.set(Calendar.MINUTE, 0);
//			cFrom.set(Calendar.SECOND, 0);
//			dateFrom=cFrom.getTime();
//		}
//		
//		log.debug("------dateFrom---"+dateFrom);
//
//		Calendar cTo= Calendar.getInstance();
//		if (toDate!=null) {
//			cTo.setTime(toDate);
//			cTo.set(Calendar.HOUR_OF_DAY, 23);
//			cTo.set(Calendar.MINUTE, 59);
//			cTo.set(Calendar.SECOND, 59);
//			dateTo=cTo.getTime();
//		}
//		
//		log.debug("------dateTo---"+dateTo);
//		
//		Date exFrom=null;
//		Date exTo= null;
//		
//		if (exactFrom !=null) {
//			cFrom.setTime(exactFrom);
//			cFrom.set(Calendar.HOUR_OF_DAY, 0);
//			cFrom.set(Calendar.MINUTE, 0);
//			cFrom.set(Calendar.SECOND, 0);
//			exFrom=cFrom.getTime();
//		}
//		
//		log.debug("------exFrom---"+exFrom);
//
//		if (exactTo!=null) {
//			cTo.setTime(exactTo);
//			cTo.set(Calendar.HOUR_OF_DAY, 23);
//			cTo.set(Calendar.MINUTE, 59);
//			cTo.set(Calendar.SECOND, 59);
//			exTo=cTo.getTime();
//		}
//		log.debug("------exTo---"+exTo);
//		
//		Date pFrom=null;
//		Date pTo= null;
//		
//		if (periodFrom !=null) {
//			cFrom.setTime(periodFrom);
//			cFrom.set(Calendar.HOUR_OF_DAY, 0);
//			cFrom.set(Calendar.MINUTE, 0);
//			cFrom.set(Calendar.SECOND, 0);
//			pFrom=cFrom.getTime();
//		}
//		
//		log.debug("------pfrom---"+pFrom);
//
//		if (periodTo!=null) {
//			cTo.setTime(periodTo);
//			cTo.set(Calendar.HOUR_OF_DAY, 23);
//			cTo.set(Calendar.MINUTE, 59);
//			cTo.set(Calendar.SECOND, 59);
//			pTo=cTo.getTime();
//		}
//		log.debug("------Pto---"+pTo);
//		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		
//		Map map = new HashMap();
//		List list =new ArrayList();
//	
//		
//		try {
//
//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			
//			///////////////////////////////////////////////////////////////////////
//			if (fromDate!=null && toDate!=null){
//				final Date startDate =(Date) format.parse(format.format(dateFrom));
//				final Date endDate = (Date)format.parse(format.format(dateTo));
//				criteria.add(Expression.ge("request_date", startDate));
//				criteria.add(Expression.le("request_date", endDate));
//			}
//			if (pFrom!=null && pTo!=null){
//				final Date startDate =(Date) format.parse(format.format(pFrom));
//				final Date endDate = (Date)format.parse(format.format(pTo));
//				criteria.add(Expression.ge("period_from", startDate));
//				criteria.add(Expression.le("period_from", endDate));
//			}
//			if (exactFrom!=null && exactTo!=null){
//				final Date startDate =(Date) format.parse(format.format(exFrom));
//				final Date endDate = (Date)format.parse(format.format(exTo));
//				criteria.add(Expression.ge("from_date", startDate));
//				criteria.add(Expression.le("fromDate", endDate));
//			}
//			/////////////////////////////////////////////////////////////////////////////////
//			if(requestType!=null) {
//				log.debug("requesttype " + requestType);
//				if (requestType.equals(new Long(1))){
//					log.debug("requesttype is 1");
//					criteria.add(Restrictions.or(
//							Restrictions.eq("request_id.id", requestType),
//							Restrictions.eq("request_id.id", new Long(4)))
//							);
//				} else if (requestType.equals(new Long(4))) {
//					log.debug("requesttype is 4");
//					criteria.add(Restrictions.or(Restrictions.or(
//							Restrictions.eq("request_id.id", new Long(1)),
//							Restrictions.eq("request_id.id", new Long(2)))
//							,Restrictions.eq("request_id.id", new Long(4))));
//				} else if (requestType.equals(new Long(5))) {
//					log.debug("requesttype is 5");
//					criteria.add(Restrictions.or(
//							Restrictions.eq("request_id.id", new Long(10)),
//							Restrictions.eq("request_id.id", new Long(11)))
//							);
//				} else {
//					log.debug("requesttype is else");
//					criteria.add(Restrictions.eq("request_id.id", requestType));
//				}
//			}
//			if(requestType!=null) {
//				log.debug("requesttype " + requestType);
//				if (requestType.equals(new Long(1))){
//					log.debug("requesttype is 1");
//					criteria.add(Restrictions.or(
//							Restrictions.eq("request_id.id", requestType),
//							Restrictions.eq("request_id.id", new Long(4)))
//							);
//				} else if (requestType.equals(new Long(4))) {
//					log.debug("requesttype is 4");
//					criteria.add(Restrictions.or(Restrictions.or(
//							Restrictions.eq("request_id.id", new Long(1)),
//							Restrictions.eq("request_id.id", new Long(2)))
//							,Restrictions.eq("request_id.id", new Long(4))));
//				} else if (requestType.equals(new Long(5))) {
//					log.debug("requesttype is 5");
//					criteria.add(Restrictions.or(
//							Restrictions.eq("request_id.id", new Long(10)),
//							Restrictions.eq("request_id.id", new Long(11)))
//							);
//				} else {
//					log.debug("requesttype is else");
//					criteria.add(Restrictions.eq("request_id.id", requestType));
//				}
//			}
//			/////////////////////////////////////////////////////////////////////////////////
//			if (empCode!=null && !empCode.isEmpty()) {
//				criteria.add(Restrictions.eq("empCode", empCode));
//			}
//			////////////////////////////////////////////////////////////////////////////////
//			if (codeFrom!=null && !codeFrom.isEmpty() && codeTo!=null && !codeTo.isEmpty()) {
//				criteria.add(Restrictions.between("empCode", codeFrom, codeTo));
//			}
//			///////////////////////////////////////////////////////////////////////////////
//			if (statusId!=null) {
//				criteria.add(Restrictions.eq("approved", statusId));
//			}
//			///////////////////////////////////////////////////////////////////////////////
//			
//			criteria.addOrder(Property.forName("period_from").desc());
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			list =  criteria.list();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return list;

		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		Date dateFrom=null;
		Date dateTo= null;
		
		CriteriaQuery<LoginUsersRequests> queryCriteria =
		        builder.createQuery(LoginUsersRequests.class);

		Root<LoginUsersRequests> root =
		        queryCriteria.from(LoginUsersRequests.class);

		List<Predicate> predicates = new ArrayList<>();

		///////////////////////////////////////////////////////////////////////
		// request_date range
		///////////////////////////////////////////////////////////////////////

		if (dateFrom != null && dateTo != null) {

		    predicates.add(
		            builder.greaterThanOrEqualTo(
		                    root.get("request_date"),
		                    dateFrom
		            )
		    );

		    predicates.add(
		            builder.lessThanOrEqualTo(
		                    root.get("request_date"),
		                    dateTo
		            )
		    );
		}

		///////////////////////////////////////////////////////////////////////
		// period_from range
		///////////////////////////////////////////////////////////////////////

		if (periodFrom != null && periodTo != null) {

		    predicates.add(
		            builder.greaterThanOrEqualTo(
		                    root.get("period_from"),
		                    periodFrom
		            )
		    );

		    predicates.add(
		            builder.lessThanOrEqualTo(
		                    root.get("period_from"),
		                    periodTo
		            )
		    );
		}

		///////////////////////////////////////////////////////////////////////
		// from_date range
		///////////////////////////////////////////////////////////////////////

		if (exactFrom != null && exactTo != null) {

		    predicates.add(
		            builder.greaterThanOrEqualTo(
		                    root.get("from_date"),
		                    exactFrom
		            )
		    );

		    predicates.add(
		            builder.lessThanOrEqualTo(
		                    root.get("from_date"),
		                    exactTo
		            )
		    );
		}

		///////////////////////////////////////////////////////////////////////
		// requestType
		///////////////////////////////////////////////////////////////////////

		if (requestType != null) {

		    if (requestType.equals(1L)) {

		        predicates.add(
		                builder.or(
		                        builder.equal(
		                                root.get("request_id").get("id"),
		                                1L
		                        ),
		                        builder.equal(
		                                root.get("request_id").get("id"),
		                                4L
		                        )
		                )
		        );

		    } else if (requestType.equals(4L)) {

		        predicates.add(
		                builder.or(
		                        builder.equal(
		                                root.get("request_id").get("id"),
		                                1L
		                        ),
		                        builder.equal(
		                                root.get("request_id").get("id"),
		                                2L
		                        ),
		                        builder.equal(
		                                root.get("request_id").get("id"),
		                                4L
		                        )
		                )
		        );

		    } else if (requestType.equals(5L)) {

		        predicates.add(
		                builder.or(
		                        builder.equal(
		                                root.get("request_id").get("id"),
		                                10L
		                        ),
		                        builder.equal(
		                                root.get("request_id").get("id"),
		                                11L
		                        )
		                )
		        );

		    } else {

		        predicates.add(
		                builder.equal(
		                        root.get("request_id").get("id"),
		                        requestType
		                )
		        );
		    }
		}

		///////////////////////////////////////////////////////////////////////
		// empCode
		///////////////////////////////////////////////////////////////////////

		if (empCode != null && !empCode.isEmpty()) {

		    predicates.add(
		            builder.equal(
		                    root.get("empCode"),
		                    empCode
		            )
		    );
		}

		///////////////////////////////////////////////////////////////////////
		// empCode range
		///////////////////////////////////////////////////////////////////////

		if (codeFrom != null
		        && !codeFrom.isEmpty()
		        && codeTo != null
		        && !codeTo.isEmpty()) {

		    predicates.add(
		            builder.between(
		                    root.get("empCode"),
		                    codeFrom,
		                    codeTo
		            )
		    );
		}

		///////////////////////////////////////////////////////////////////////
		// status
		///////////////////////////////////////////////////////////////////////

		if (statusId != null) {

		    predicates.add(
		            builder.equal(
		                    root.get("approved"),
		                    statusId
		            )
		    );
		}

		///////////////////////////////////////////////////////////////////////
		// Final Query
		///////////////////////////////////////////////////////////////////////

		queryCriteria.select(root)
		             .where(predicates.toArray(new Predicate[0]))
		             .distinct(true)
		             .orderBy(
		                     builder.desc(
		                             root.get("period_from")
		                     )
		             );

		TypedQuery<LoginUsersRequests> query =
		        session.createQuery(queryCriteria);

		return query.getResultList();
	}

	public Map getPagedRequests(final Date fromDate, final Date toDate, final Long requestType, final Date exactFrom, final Date exactTo, 
			final Date periodFrom, final Date periodTo, String empCode, String codeFrom, String codeTo, Long statusId, String sort, List empReqTypeAccs,String requestNumber,Long mgrId, boolean isWeb,String isInsideCompany, final int pageNumber, final int pageSize)  {
		Settings settings = (Settings)getObject(Settings.class, new Long(1));
		log.debug("manager ID " + mgrId);
		Page page = new Page();
		return page.getPage(externalQueries.getPagedRequests(fromDate,toDate,requestType,exactFrom,exactTo,periodFrom,periodTo,empCode,codeFrom,codeTo,statusId,sort,empReqTypeAccs,requestNumber, mgrId,isWeb,isInsideCompany,settings,pageNumber,pageSize),pageNumber,pageSize);
	}
	
	public Map getSubmittedPagedRequests(final Date fromDate, final Date toDate, final Long requestType, final Date exactFrom, final Date exactTo, 
			final Date periodFrom, final Date periodTo, String empCode, String codeFrom, String codeTo, Long statusId, String sort, List empReqTypeAccs,String requestNumber,Long mgrId, boolean isWeb,String isInsideCompany, final int pageNumber, final int pageSize)  {
		Settings settings = (Settings)getObject(Settings.class, new Long(1));
		log.debug("manager ID " + mgrId);
		Page page = new Page();
		return page.getPage(externalQueries.getSubmittedPagedRequests(fromDate,toDate,requestType,exactFrom,exactTo,periodFrom,periodTo,empCode,codeFrom,codeTo,statusId,sort,empReqTypeAccs,requestNumber, mgrId,isWeb,isInsideCompany,settings,pageNumber,pageSize),pageNumber,pageSize);
	}
	
	public Map getRequestsStatus(final Date fromDate, final Date toDate, final Long requestType, final Date exactFrom, final Date exactTo, 
			final Date periodFrom, final Date periodTo, String empCode, String codeFrom, String codeTo, Long statusId, String sort, List empReqTypeAccs,String requestNumber,Long mgrId, boolean isWeb,String isInsideCompany, final int pageNumber, final int pageSize)  {
		Settings settings = (Settings)getObject(Settings.class, new Long(1));
		log.debug("manager ID " + mgrId);
		Page page = new Page();
		return page.getPage(externalQueries.getRequestsStatus(fromDate,toDate,requestType,exactFrom,exactTo,periodFrom,periodTo,empCode,codeFrom,codeTo,statusId,sort,empReqTypeAccs,requestNumber, mgrId,isWeb,isInsideCompany,settings,pageNumber,pageSize),pageNumber,pageSize);
	}

//	public Map getPagedRequests(final Date fromDate, final Date toDate, final Long requestType, final Date exactFrom, final Date exactTo, 
//			final Date periodFrom, final Date periodTo, String empCode, String codeFrom, String codeTo, Long statusId, String sort, List empReqTypeAccs,String requestNumber,boolean isWeb,String isInsideCompany, final int pageNumber, final int pageSize)  {
//		Calendar cFrom= Calendar.getInstance();
//		
//		Date dateFrom=null;
//		Date dateTo= null;
//		log.debug("------fromDate---"+fromDate);
//		log.debug("fromDate " + fromDate + " toDate " + toDate + " requestType " + requestType);
//		log.debug("exactFrom " + exactFrom + " exactTo " + exactTo + " requestType " + requestType);
//		if (fromDate !=null) {
//			cFrom.setTime(fromDate);
//			cFrom.set(Calendar.HOUR_OF_DAY, 0);
//			cFrom.set(Calendar.MINUTE, 0);
//			cFrom.set(Calendar.SECOND, 0);
//			dateFrom=cFrom.getTime();
//		}
//		
//		log.debug("------dateFrom---"+dateFrom);
//
//		log.debug("------toDate---"+toDate);
//		Calendar cTo= Calendar.getInstance();
//		if (toDate!=null) {
//			cTo.setTime(toDate);
//			cTo.set(Calendar.HOUR_OF_DAY, 23);
//			cTo.set(Calendar.MINUTE, 59);
//			cTo.set(Calendar.SECOND, 59);
//			dateTo=cTo.getTime();
//		}
//		
//		log.debug("------dateTo---"+dateTo);
//		
//		Date exFrom=null;
//		Date exTo= null;
//		
//		log.debug("------exactfrom---"+exactFrom);
//		if (exactFrom !=null) {
//			cFrom.setTime(exactFrom);
//			cFrom.set(Calendar.HOUR_OF_DAY, 0);
//			cFrom.set(Calendar.MINUTE, 0);
//			cFrom.set(Calendar.SECOND, 0);
//			exFrom=cFrom.getTime();
//		}
//		
//		log.debug("------exFrom---"+exFrom);
//
//		if (exactTo!=null) {
//			cTo.setTime(exactTo);
//			cTo.set(Calendar.HOUR_OF_DAY, 23);
//			cTo.set(Calendar.MINUTE, 59);
//			cTo.set(Calendar.SECOND, 59);
//			exTo=cTo.getTime();
//		}
//		log.debug("------exTo---"+exTo);
//		
//		Date pFrom=null;
//		Date pTo= null;
//		
//		if (pFrom !=null) {
//			cFrom.setTime(periodFrom);
//			cFrom.set(Calendar.HOUR_OF_DAY, 0);
//			cFrom.set(Calendar.MINUTE, 0);
//			cFrom.set(Calendar.SECOND, 0);
//			pFrom=cFrom.getTime();
//		}
//		
//		log.debug("------pfrom---"+pFrom);
//
//		if (pTo!=null) {
//			cTo.setTime(periodTo);
//			cTo.set(Calendar.HOUR_OF_DAY, 23);
//			cTo.set(Calendar.MINUTE, 59);
//			cTo.set(Calendar.SECOND, 59);
//			pTo=cTo.getTime();
//		}
//		log.debug("------Pto---"+pTo);
//		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		
//		Map map = new HashMap();
//		List list =new ArrayList();
//		try {
//
//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			
//			///////////////////////////////////////////////////////////////////////
//			if (fromDate!=null && toDate!=null){
//				final Date startDate =(Date) format.parse(format.format(dateFrom));
//				final Date endDate = (Date)format.parse(format.format(dateTo));
//				criteria.add(Expression.ge("request_date", startDate));
//				criteria.add(Expression.le("request_date", endDate));
//			}
//			if (pFrom!=null && pTo!=null){
//				final Date startDate =(Date) format.parse(format.format(pFrom));
//				final Date endDate = (Date)format.parse(format.format(pTo));
//				criteria.add(Expression.ge("period_from", startDate));
//				criteria.add(Expression.le("period_from", endDate));
//			}
//			if (exactFrom!=null && exactTo!=null){
//				final Date startDate =(Date) format.parse(format.format(exFrom));
//				final Date endDate = (Date)format.parse(format.format(exTo));
//				criteria.add(Expression.ge("from_date", startDate));
//				criteria.add(Expression.le("from_date", endDate));
//			}
//			
//			/////////////////////////////////////////////////////////////////////////////////
//			log.debug("requestNumber " + requestNumber);
//			if(requestNumber!=null && !requestNumber.isEmpty()) {
//				criteria.add(Restrictions.eq("requestNumber", requestNumber));
//			}
//			log.debug("requesttype " + requestType);
//			if(requestType!=null) {
//				log.debug("1.requesttype " + requestType);
//				if (requestType.equals(new Long(1))){
//					log.debug("requesttype is 1");
//					criteria.add(Restrictions.or(
//							Restrictions.eq("request_id.id", requestType),
//							Restrictions.eq("request_id.id", new Long(4)))
//							);
//					criteria.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
//							Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))));
//				} else if (requestType.equals(new Long(4))) {
//					log.debug("requesttype is 4");
////					criteria.add(Restrictions.or(Restrictions.or(
////							Restrictions.eq("request_id.id", new Long(1)),
////							Restrictions.eq("request_id.id", new Long(2)))
////							,Restrictions.eq("request_id.id", new Long(4))));
//					criteria.add(Restrictions.eq("request_id.id", new Long(4)));
//					criteria.add(Restrictions.isNull("vacation.vacation"));
//					criteria.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
//							Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))));
//				} else if (requestType.equals(new Long(5))) {
//					log.debug("requesttype is 5");
//					criteria.add(Restrictions.eq("request_id.id", new Long(4))); //Ma2moreya
//					criteria.add(Restrictions.eq("vacation.vacation", "999"));
//					log.debug("ma2moreya");
//				}   else if (requestType.equals(new Long(6))) {//7odoor w enseraf
//					log.debug("requesttype is 6");
//					criteria.add(Restrictions.or(
//							Restrictions.eq("request_id.id", new Long(10)),
//							Restrictions.eq("request_id.id", new Long(11)))
//							);
//				}  
////				else if (requestType.equals(new Long(6))) {
////					log.debug("requesttype is 6");
////					criteria.add(Restrictions.eq("request_id.id", new Long(1)));
////				} 
//				else if (requestType.equals(new Long(10)) || requestType.equals(new Long(11))) {
//					log.debug("requesttype is else");
//					criteria.add(Restrictions.eq("request_id.id", requestType));
//				} else if (requestType.equals(new Long(12))) {
//					log.debug("requesttype is 4");
//					criteria.add(Restrictions.eq("request_id.id", new Long(4)));
//					criteria.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
//							Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))));
//				} else if (requestType.equals(new Long(13))) {
//					log.debug("requesttype is 4");
//					criteria.add(Restrictions.and(Restrictions.eq("request_id.id", new Long(1)), Restrictions.eq("vacation.vacation", "999")));
//					criteria.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
//							Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))));
//				} else if (requestType.equals(new Long(14))) {
//					log.debug("requesttype is 4");
//					criteria.add(Restrictions.or(Restrictions.eq("request_id.id", new Long(4)),
//							Restrictions.and(Restrictions.eq("request_id.id", new Long(1)), Restrictions.eq("vacation.vacation", "999"))));
//					criteria.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
//							Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))));
//				}  else {
//					log.debug("requesttype is else");
//					criteria.add(Restrictions.eq("request_id.id", requestType));
//					criteria.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
//							Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))));
//				}
//			} else {
//				log.debug("2. requesttype " + requestType);
//				criteria.add(Restrictions.or(
//											Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
//															Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))),
//											Restrictions.or(Restrictions.eq("request_id.id", new Long(10)),Restrictions.eq("request_id.id", new Long(11)))
//								)
//							);
//			}
//			/////////////////////////////////////////////////////////////////////////////////
//			if (empCode!=null && !empCode.isEmpty()) {
//				criteria.add(Restrictions.eq("empCode", empCode));
//			}
//			////////////////////////////////////////////////////////////////////////////////
//			if (codeFrom!=null && !codeFrom.isEmpty() && codeTo!=null && !codeTo.isEmpty()) {
//				criteria.add(Restrictions.between("empCode", codeFrom, codeTo));
//			}
//			///////////////////////////////////////////////////////////////////////////////
//			if (statusId!=null) {
//				criteria.add(Restrictions.eq("approved", statusId));
//			}
//			///////////////////////////////////////////////////////////////////////////////
//			///////////////////////////////////////////////////////////////////////////////
//			if (isInsideCompany!=null) {
//				if (isInsideCompany.equals("0")) {
//					criteria.add(Restrictions.eq("isInsideCompany", false));
//				} else if (isInsideCompany.equals("1")) {
//					criteria.add(Restrictions.eq("isInsideCompany", true));
//				}
//			}
//			///////////////////////////////////////////////////////////////////////////////
//
//			log.debug("empReqTypeAccs " + empReqTypeAccs);
//			if (empReqTypeAccs != null) {
//				criteria.createCriteria("login_user").add(Restrictions.in("id", empReqTypeAccs));
//			} 
//			if (sort.equalsIgnoreCase("desc")) {
//				criteria.addOrder(Property.forName("period_from").desc());
//			}
//			if (sort.equalsIgnoreCase("asc")) {
//				criteria.addOrder(Property.forName("period_from").asc());
//			}
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
//			list = (List)criteria.list();
//			map.put("listSize", Integer.valueOf(criteria.list().iterator().next()+""));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			log.debug("exception " + e.getMessage());
//			Page page = new Page();
//			map.put("listSize", new Integer(0));
//			return page.getPage(map,pageNumber,pageSize);
//		}
//		
//		
//		try {
//
//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			
//			///////////////////////////////////////////////////////////////////////
//			if (fromDate!=null && toDate!=null){
//				final Date startDate =(Date) format.parse(format.format(dateFrom));
//				final Date endDate = (Date)format.parse(format.format(dateTo));
//				criteria.add(Expression.ge("request_date", startDate));
//				criteria.add(Expression.le("request_date", endDate));
//			}
//			if (pFrom!=null && pTo!=null){
//				final Date startDate =(Date) format.parse(format.format(pFrom));
//				final Date endDate = (Date)format.parse(format.format(pTo));
//				criteria.add(Expression.ge("period_from", startDate));
//				criteria.add(Expression.le("period_from", endDate));
//			}
//			if (exactFrom!=null && exactTo!=null){
//				final Date startDate =(Date) format.parse(format.format(exFrom));
//				final Date endDate = (Date)format.parse(format.format(exTo));
//				criteria.add(Expression.ge("from_date", startDate));
//				criteria.add(Expression.le("from_date", endDate));
//			}
////			criteria.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
////					Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))));
//			/////////////////////////////////////////////////////////////////////////////////
//			log.debug("requestNumber " + requestNumber);
//			if(requestNumber!=null && !requestNumber.isEmpty()) {
//				criteria.add(Restrictions.eq("requestNumber", requestNumber));
//			}
//			if(requestType!=null) {
//				log.debug("requesttype " + requestType);
//				if (requestType.equals(new Long(1))){
//					log.debug("requesttype is 1");
//					criteria.add(Restrictions.or(
//							Restrictions.eq("request_id.id", requestType),
//							Restrictions.eq("request_id.id", new Long(4)))
//							);
//					criteria.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
//							Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))));
//				} else if (requestType.equals(new Long(4))) {
//					log.debug("requesttype is 4");
////					criteria.add(Restrictions.or(Restrictions.or(
////							Restrictions.eq("request_id.id", new Long(1)),
////							Restrictions.eq("request_id.id", new Long(2)))
////							,Restrictions.eq("request_id.id", new Long(4))));
//					criteria.add(Restrictions.eq("request_id.id", new Long(4)));
//					criteria.add(Restrictions.isNull("vacation.vacation"));
//					criteria.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
//							Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))));
//				} else if (requestType.equals(new Long(5))) {
//					log.debug("requesttype is 5");
//					
//					criteria.add(Restrictions.eq("request_id.id", new Long(4))); //Ma2moreya
//					criteria.add(Restrictions.eq("vacation.vacation", "999"));
//					log.debug("ma2moreya2");
//				} else if (requestType.equals(new Long(6))) {
//					log.debug("requesttype is 6");
//					criteria.add(Restrictions.or(
//							Restrictions.eq("request_id.id", new Long(10)),
//							Restrictions.eq("request_id.id", new Long(11)))
//							);
//				} else if (requestType.equals(new Long(10)) || requestType.equals(new Long(11))) {
//					log.debug("requesttype is else");
//					criteria.add(Restrictions.eq("request_id.id", requestType));
//				} else if (requestType.equals(new Long(12))) {
//					log.debug("requesttype is 4");
//					criteria.add(Restrictions.eq("request_id.id", new Long(4)));
//					criteria.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
//							Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))));
//				} else if (requestType.equals(new Long(13))) {
//					log.debug("requesttype is 4");
//					criteria.add(Restrictions.and(Restrictions.eq("request_id.id", new Long(1)), Restrictions.eq("vacation.vacation", "999")));
//					criteria.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
//							Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))));
//				} else if (requestType.equals(new Long(14))) {
//					log.debug("requesttype is 4");
//					criteria.add(Restrictions.or(Restrictions.eq("request_id.id", new Long(4)),
//							Restrictions.and(Restrictions.eq("request_id.id", new Long(1)), Restrictions.eq("vacation.vacation", "999"))));
//					criteria.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
//							Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))));
//				} else {
//					log.debug("requesttype is else");
//					criteria.add(Restrictions.eq("request_id.id", requestType));
//					criteria.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
//							Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))));
//				}
//			} else {
//				log.debug("2. requesttype " + requestType);
//				criteria.add(Restrictions.or(
//											Restrictions.or(Restrictions.and(Restrictions.isNotNull("from_date"),Restrictions.isNotNull("to_date")),
//															Restrictions.and(Restrictions.isNotNull("period_from"),Restrictions.isNotNull("period_to"))),
//											Restrictions.or(Restrictions.eq("request_id.id", new Long(10)),Restrictions.eq("request_id.id", new Long(11)))
//								)
//							);
//			}
//			/////////////////////////////////////////////////////////////////////////////////
//			if (empCode!=null && !empCode.isEmpty()) {
//				criteria.add(Restrictions.eq("empCode", empCode));
//			}
//			////////////////////////////////////////////////////////////////////////////////
//			if (codeFrom!=null && !codeFrom.isEmpty() && codeTo!=null && !codeTo.isEmpty()) {
//				criteria.add(Restrictions.between("empCode", codeFrom, codeTo));
//			}
//			///////////////////////////////////////////////////////////////////////////////
//			if (statusId!=null) {
//				criteria.add(Restrictions.eq("approved", statusId));
//			}
//			///////////////////////////////////////////////////////////////////////////////
//
//			///////////////////////////////////////////////////////////////////////////////
//			if (isInsideCompany!=null) {
//				if (isInsideCompany.equals("0")) {
//					criteria.add(Restrictions.eq("isInsideCompany", false));
//				} else if (isInsideCompany.equals("1")) {
//					criteria.add(Restrictions.eq("isInsideCompany", true));
//				}
//			}
//			///////////////////////////////////////////////////////////////////////////////
//
//			log.debug("empReqTypeAccs " + empReqTypeAccs);
//			if (empReqTypeAccs != null) {
//				criteria.createCriteria("login_user").add(Restrictions.in("id", empReqTypeAccs));
//			}
//			if (sort.equalsIgnoreCase("desc")) {
//				criteria.addOrder(Property.forName("period_from").desc());
//			}
//			if (sort.equalsIgnoreCase("asc")) {
//				criteria.addOrder(Property.forName("period_from").asc());
//			}
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		
//			log.debug("first result " + (pageNumber*pageSize));
//			log.debug("max results " + pageSize);
//			criteria.setFirstResult(pageNumber * pageSize);
//			criteria.setMaxResults(pageSize);
//			
//			List results = criteria.list();
//			if (isWeb) {
//				map.put("results", results);
//			}  else {
//				log.debug("results " + results.size());
//				List output = new ArrayList();
//				Iterator itr = results.iterator();
//				while (itr.hasNext()) {
//					LoginUsersRequests req = (LoginUsersRequests)itr.next();
//					RequestOutput op = new RequestOutput();
//					op.setId(req.getId());
//					op.setEmpCode(req.getEmpCode());
//					op.setEmpName(req.getLogin_user().getName());
//					op.setRequestDate(req.getRequest_date());
//					op.setRequestNumber(req.getRequestNumber());
//					op.setNotes(req.getNotes());
//					op.setLongitude(req.getLongitude());
//					op.setLatitude(req.getLatitude());
//					op.setIsInsideCompany(req.getIsInsideCompany());
//					op.setLocationAddress(req.getLocationAddress());
//					
//					op.setFromDateHistory(req.getFrom_date_history());
//
//					if (req.getRequest_id().getId().equals(new Long(1))) {
//						op.setRequestDesc(req.getVacation().getName());
//					} else {
//						op.setRequestDesc(req.getRequest_id().getDescription());
//					}
//
//					if (req.getRequest_id().getId().equals(new Long(1)) || req.getRequest_id().getId().equals(new Long(2))) {
//						op.setVacDuration(req.getWithdrawDays());
//					}
//					if (req.getRequest_id().getId().equals(new Long(10))) {
//						op.setRequestType(new Long(5));
//					} else if (req.getRequest_id().getId().equals(new Long(11))) {
//						op.setRequestType(new Long(6));
//					} else {
//						op.setRequestType(req.getRequest_id().getId());
//					} 
//
//
//					if (req.getApproved()==null || req.getApproved().equals(new Long(0))) {
//						op.setStatus("Waiting Approval");
//					} else if (req.getApproved().equals(new Long(99))) {
//						op.setStatus("Declined");
//					} else if (req.getApproved().equals(new Long(1))) {
//						op.setStatus("Approved");
//					} 
//
//					System.out.println("req.getFrom_date() " + req.getFrom_date());
//					if (req.getFrom_date() != null) {
//						op.setFromDate(req.getFrom_date());
//						op.setToDate(req.getTo_date());
//					} else {
//						op.setFromDate(req.getPeriod_from());
//						op.setToDate(req.getPeriod_to());
//					}
//
//					output.add(op);
//				}
//				map.put("results", output);
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			log.debug("exception " + e.getMessage());
//			Page page = new Page();
//			return page.getPage(map,pageNumber,pageSize);
//		}
//		Page page = new Page();
//		log.debug("paging");
//		return page.getPage(map,pageNumber,pageSize);
////		return map;
//	}



	
	public List getRequestsByExactDatePeriodAndRequestType(final Date fromDate, final Date toDate, final Long requestType){
//		Calendar cFrom= Calendar.getInstance();
//		cFrom.setTime(fromDate);
//		cFrom.set(Calendar.HOUR_OF_DAY, 0);
//		cFrom.set(Calendar.MINUTE, 0);
//		cFrom.set(Calendar.SECOND, 0);
//		//cFrom.set(Calendar.AM_PM, -1);
//		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
//		final Date dateFrom=cFrom.getTime();
//
//		log.debug("------dateFrom---"+dateFrom);
//
//		Calendar cTo= Calendar.getInstance();
//		cTo.setTime(toDate);
//		cTo.set(Calendar.HOUR_OF_DAY, 23);
//		cTo.set(Calendar.MINUTE, 59);
//		cTo.set(Calendar.SECOND, 59);
//
//		final Date dateTo=cTo.getTime();
//		log.debug("------dateTo---"+dateTo);
//		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		List list =new ArrayList();
//		try {
//			final Date startDate =(Date) format.parse(format.format(dateFrom));
//			final Date endDate = (Date)format.parse(format.format(dateTo));
//
//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			criteria.add(Expression.ge("from_date", startDate));
//			criteria.add(Expression.le("from_date", endDate));
//			if (requestType.equals(1)){
//				criteria.add(Restrictions.or(
//						Restrictions.eq("request_id.id", requestType),
//						Restrictions.eq("request_id.id", 4))
//						);
//			} else {
//				criteria.add(Restrictions.eq("request_id.id", requestType));
//			}
//			criteria.addOrder(Property.forName("period_from").desc());
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			list = criteria.list();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
		
		
		Calendar cFrom = Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);

		final Date dateFrom = cFrom.getTime();

		log.debug("------dateFrom---" + dateFrom);

		Calendar cTo = Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo = cTo.getTime();

		log.debug("------dateTo---" + dateTo);

		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<LoginUsersRequests> queryCriteria =
		        builder.createQuery(LoginUsersRequests.class);

		Root<LoginUsersRequests> root =
		        queryCriteria.from(LoginUsersRequests.class);

		List<Predicate> predicates = new ArrayList<>();

		/*
		 * from_date >= start date
		 */
		predicates.add(
		        builder.greaterThanOrEqualTo(
		                root.get("from_date"),
		                dateFrom
		        )
		);

		/*
		 * from_date <= end date
		 */
		predicates.add(
		        builder.lessThanOrEqualTo(
		                root.get("from_date"),
		                dateTo
		        )
		);

		/*
		 * request type filtering
		 */
		if (requestType.equals(1L) || requestType.equals(1)) {

		    predicates.add(
		            builder.or(
		                    builder.equal(
		                            root.get("request_id").get("id"),
		                            requestType
		                    ),
		                    builder.equal(
		                            root.get("request_id").get("id"),
		                            4L
		                    )
		            )
		    );

		} else {

		    predicates.add(
		            builder.equal(
		                    root.get("request_id").get("id"),
		                    requestType
		            )
		    );
		}

		/*
		 * Final query
		 */
		queryCriteria.select(root)
		             .where(predicates.toArray(new Predicate[0]))
		             .distinct(true)
		             .orderBy(
		                     builder.desc(
		                             root.get("period_from")
		                     )
		             );

		TypedQuery<LoginUsersRequests> query =
		        session.createQuery(queryCriteria);

		return query.getResultList();
	}


	public List getRequestsByDatePeriod(final Date fromDate, final Date toDate){
		log.debug("------fromDate---"+fromDate);
//		List list = new ArrayList();
//
//		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd");// HH:mm:ss
//
//		Criteria criteria = getCurrentSession()
//				.createCriteria(LoginUsersRequests.class);
//		criteria.add(Expression.ge("request_date", fromDate));
//		criteria.add(Expression.le("request_date", toDate));
//									criteria.addOrder(Property.forName("period_from").desc());
//		criteria
//		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		log.debug("criteria.list().size()" + criteria.list().size());
//		return criteria.list();
		
		Calendar cFrom = Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);

		final Date dateFrom = cFrom.getTime();

		log.debug("------dateFrom---" + dateFrom);

		Calendar cTo = Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo = cTo.getTime();

		log.debug("------dateTo---" + dateTo);

		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<LoginUsersRequests> queryCriteria =
		        builder.createQuery(LoginUsersRequests.class);

		Root<LoginUsersRequests> root =
		        queryCriteria.from(LoginUsersRequests.class);

		List<Predicate> predicates = new ArrayList<>();

		predicates.add(
		        builder.greaterThanOrEqualTo(
		                root.get("request_date"),
		                dateFrom
		        )
		);

		predicates.add(
		        builder.lessThanOrEqualTo(
		                root.get("request_date"),
		                dateTo
		        )
		);

		queryCriteria.select(root)
		             .where(predicates.toArray(new Predicate[0]))
		             .distinct(true)
		             .orderBy(
		                     builder.desc(
		                             root.get("period_from")
		                     )
		             );

		TypedQuery<LoginUsersRequests> query =
		        session.createQuery(queryCriteria);

		return query.getResultList();
	}


	public List getRequestsByExactDatePeriod(final Date fromDate, final Date toDate){
		log.debug("------fromDate---"+fromDate);
//		List list = new ArrayList();
//
//		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd");// HH:mm:ss
//
//		Criteria criteria = getCurrentSession()
//				.createCriteria(LoginUsersRequests.class);
//		criteria.add(Expression.ge("from_date", fromDate));
//		criteria.add(Expression.le("from_date", toDate));
//									criteria.addOrder(Property.forName("period_from").desc());
//		criteria
//		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		log.debug("criteria.list().size()" + criteria.list().size());
//		return criteria.list();
		
		Calendar cFrom = Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);

		final Date dateFrom = cFrom.getTime();

		log.debug("------dateFrom---" + dateFrom);

		Calendar cTo = Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo = cTo.getTime();

		log.debug("------dateTo---" + dateTo);

		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<LoginUsersRequests> queryCriteria =
		        builder.createQuery(LoginUsersRequests.class);

		Root<LoginUsersRequests> root =
		        queryCriteria.from(LoginUsersRequests.class);

		List<Predicate> predicates = new ArrayList<>();

		predicates.add(
		        builder.greaterThanOrEqualTo(
		                root.get("from_date"),
		                dateFrom
		        )
		);

		predicates.add(
		        builder.lessThanOrEqualTo(
		                root.get("from_date"),
		                dateTo
		        )
		);

		queryCriteria.select(root)
		             .where(predicates.toArray(new Predicate[0]))
		             .distinct(true)
		             .orderBy(
		                     builder.desc(
		                             root.get("period_from")
		                     )
		             );

		TypedQuery<LoginUsersRequests> query =
		        session.createQuery(queryCriteria);

		return query.getResultList();
	}

	public List getRequestsByDatePeriodAndRequestTypeAndEmpCode(final Date fromDate,final Date toDate, final Long requestType, final String empCode){
//		Calendar cFrom= Calendar.getInstance();
//		cFrom.setTime(fromDate);
//		cFrom.set(Calendar.HOUR_OF_DAY, 0);
//		cFrom.set(Calendar.MINUTE, 0);
//		cFrom.set(Calendar.SECOND, 0);
//		final Date dateFrom=cFrom.getTime();
//		log.debug("------dateFrom---"+dateFrom);
//
//		Calendar cTo= Calendar.getInstance();
//		cTo.setTime(toDate);
//		cTo.set(Calendar.HOUR_OF_DAY, 23);
//		cTo.set(Calendar.MINUTE, 59);
//		cTo.set(Calendar.SECOND, 59);
//
//		final Date dateTo=cTo.getTime();
//		log.debug("------dateTo---"+dateTo);
//
//		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		List list =new ArrayList();
//		try {
//			final Date startDate =(Date) format.parse(format.format(dateFrom));
//			final Date endDate = (Date)format.parse(format.format(dateTo));
//
//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			criteria.add(Expression.ge("request_date", startDate));
//			criteria.add(Expression.le("request_date", endDate));
//			if (requestType.equals(1)){
//				criteria.add(Restrictions.or(
//						Restrictions.eq("request_id.id", requestType),
//						Restrictions.eq("request_id.id", 4))
//						);
//			} else {
//				criteria.add(Restrictions.eq("request_id.id", requestType));
//			}
//			criteria.add(Restrictions.eq("empCode", empCode));
//			criteria.addOrder(Property.forName("period_from").desc());
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			list = criteria.list();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
		
		Calendar cFrom = Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);

		final Date dateFrom = cFrom.getTime();

		log.debug("------dateFrom---" + dateFrom);

		Calendar cTo = Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo = cTo.getTime();

		log.debug("------dateTo---" + dateTo);

		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<LoginUsersRequests> queryCriteria =
		        builder.createQuery(LoginUsersRequests.class);

		Root<LoginUsersRequests> root =
		        queryCriteria.from(LoginUsersRequests.class);

		List<Predicate> predicates = new ArrayList<>();

		/*
		 * request_date range
		 */
		predicates.add(
		        builder.greaterThanOrEqualTo(
		                root.get("request_date"),
		                dateFrom
		        )
		);

		predicates.add(
		        builder.lessThanOrEqualTo(
		                root.get("request_date"),
		                dateTo
		        )
		);

		/*
		 * request type
		 */
		if (requestType.equals(1L) || requestType.equals(1)) {

		    predicates.add(
		            builder.or(
		                    builder.equal(
		                            root.get("request_id").get("id"),
		                            requestType
		                    ),
		                    builder.equal(
		                            root.get("request_id").get("id"),
		                            4L
		                    )
		            )
		    );

		} else {

		    predicates.add(
		            builder.equal(
		                    root.get("request_id").get("id"),
		                    requestType
		            )
		    );
		}

		/*
		 * employee code
		 */
		predicates.add(
		        builder.equal(
		                root.get("empCode"),
		                empCode
		        )
		);

		/*
		 * final query
		 */
		queryCriteria.select(root)
		             .where(predicates.toArray(new Predicate[0]))
		             .distinct(true)
		             .orderBy(
		                     builder.desc(
		                             root.get("period_from")
		                     )
		             );

		TypedQuery<LoginUsersRequests> query =
		        session.createQuery(queryCriteria);

		return query.getResultList();
		
	}

	public List getRequestsByExactDatePeriodAndRequestTypeAndEmpCode(final Date fromDate,final Date toDate, final Long requestType, final String empCode){
//		Calendar cFrom= Calendar.getInstance();
//		cFrom.setTime(fromDate);
//		cFrom.set(Calendar.HOUR_OF_DAY, 0);
//		cFrom.set(Calendar.MINUTE, 0);
//		cFrom.set(Calendar.SECOND, 0);
//		//cFrom.set(Calendar.AM_PM, -1);
//		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
//		final Date dateFrom=cFrom.getTime();
//		log.debug("------dateFrom---"+dateFrom);
//
//		Calendar cTo= Calendar.getInstance();
//		cTo.setTime(toDate);
//		cTo.set(Calendar.HOUR_OF_DAY, 23);
//		cTo.set(Calendar.MINUTE, 59);
//		cTo.set(Calendar.SECOND, 59);
//
//		final Date dateTo=cTo.getTime();
//		log.debug("------dateTo---"+dateTo);
//
//		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		List list =new ArrayList();
//		try {
//			final Date startDate =(Date) format.parse(format.format(dateFrom));
//			final Date endDate = (Date)format.parse(format.format(dateTo));
//
//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			criteria.add(Expression.ge("from_date", startDate));
//			criteria.add(Expression.le("from_date", endDate));
//			if (requestType.equals(1)){
//				criteria.add(Restrictions.or(
//						Restrictions.eq("request_id.id", requestType),
//						Restrictions.eq("request_id.id", 4))
//						);
//			} else {
//				criteria.add(Restrictions.eq("request_id.id", requestType));
//			}
//			criteria.add(Restrictions.eq("empCode", empCode));
//			criteria.addOrder(Property.forName("period_from").desc());
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			list = criteria.list();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<LoginUsersRequests> queryCriteria =
		        builder.createQuery(LoginUsersRequests.class);

		Root<LoginUsersRequests> root =
		        queryCriteria.from(LoginUsersRequests.class);

		List<Predicate> predicates = new ArrayList<>();

		predicates.add(
		        builder.greaterThanOrEqualTo(
		                root.get("from_date"),
		                fromDate
		        )
		);

		predicates.add(
		        builder.lessThanOrEqualTo(
		                root.get("from_date"),
		                toDate
		        )
		);

		queryCriteria.select(root)
		             .where(predicates.toArray(new Predicate[0]))
		             .distinct(true)
		             .orderBy(
		                     builder.desc(
		                             root.get("period_from")
		                     )
		             );

		TypedQuery<LoginUsersRequests> query =
		        session.createQuery(queryCriteria);

		List<LoginUsersRequests> list = query.getResultList();

		log.debug("criteria.list().size() " + list.size());

		return list;	
	}


	public List getRequestsByDatePeriodAndEmpCode(final Date fromDate,final Date toDate, final String empCode){
//		Calendar cFrom= Calendar.getInstance();
//		cFrom.setTime(fromDate);
//		cFrom.set(Calendar.HOUR_OF_DAY, 0);
//		cFrom.set(Calendar.MINUTE, 0);
//		cFrom.set(Calendar.SECOND, 0);
//		//cFrom.set(Calendar.AM_PM, -1);
//		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
//		final Date dateFrom=cFrom.getTime();
//		log.debug("------dateFrom---"+dateFrom);
//
//		Calendar cTo= Calendar.getInstance();
//		cTo.setTime(toDate);
//		cTo.set(Calendar.HOUR_OF_DAY, 23);
//		cTo.set(Calendar.MINUTE, 59);
//		cTo.set(Calendar.SECOND, 59);
//
//		final Date dateTo=cTo.getTime();
//		log.debug("------dateTo---"+dateTo);
//
//		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		List list =new ArrayList();
//		try {
//			final Date startDate =(Date) format.parse(format.format(dateFrom));
//			final Date endDate = (Date)format.parse(format.format(dateTo));
//
//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			criteria.add(Expression.ge("request_date", startDate));
//			criteria.add(Expression.le("request_date", endDate));
//			criteria.add(Restrictions.eq("empCode", empCode));
//			criteria.addOrder(Property.forName("period_from").desc());
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			list = criteria.list();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
		
		
		Calendar cFrom = Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);

		final Date dateFrom = cFrom.getTime();

		log.debug("------dateFrom---" + dateFrom);

		Calendar cTo = Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo = cTo.getTime();

		log.debug("------dateTo---" + dateTo);

		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<LoginUsersRequests> queryCriteria =
		        builder.createQuery(LoginUsersRequests.class);

		Root<LoginUsersRequests> root =
		        queryCriteria.from(LoginUsersRequests.class);

		List<Predicate> predicates = new ArrayList<>();

		/*
		 * request_date >= dateFrom
		 */
		predicates.add(
		        builder.greaterThanOrEqualTo(
		                root.get("request_date"),
		                dateFrom
		        )
		);

		/*
		 * request_date <= dateTo
		 */
		predicates.add(
		        builder.lessThanOrEqualTo(
		                root.get("request_date"),
		                dateTo
		        )
		);

		/*
		 * empCode = ?
		 */
		predicates.add(
		        builder.equal(
		                root.get("empCode"),
		                empCode
		        )
		);

		queryCriteria.select(root)
		             .where(predicates.toArray(new Predicate[0]))
		             .distinct(true)
		             .orderBy(
		                     builder.desc(
		                             root.get("period_from")
		                     )
		             );

		TypedQuery<LoginUsersRequests> query =
		        session.createQuery(queryCriteria);

		return query.getResultList();
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
		if (toDate!=null) {
			cTo.setTime(toDate);
			cTo.set(Calendar.HOUR_OF_DAY, 23);
			cTo.set(Calendar.MINUTE, 59);
			cTo.set(Calendar.SECOND, 59);
		}

		final Date dateTo=cTo.getTime();
		log.debug("------dateTo---"+dateTo);

		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List list =new ArrayList();
		try {
			final Date startDate =(Date) format.parse(format.format(dateFrom));
			final Date endDate = (Date)format.parse(format.format(dateTo));

//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			criteria.add(Expression.ge("from_date", startDate));
//			if (toDate!=null) {
//				criteria.add(Expression.le("from_date", endDate));
//			}
//			criteria.add(Restrictions.eq("empCode", empCode));
//			criteria.addOrder(Property.forName("period_from").desc());
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			
			CriteriaQuery queryCriteria = getBuilder().createQuery(LoginUsersRequests.class);
	    	Root<Object> root = queryCriteria.from(LoginUsersRequests.class);
	    	
	    	CriteriaBuilder builder = getBuilder();
	    	Session session = getCurrentSession();
	    	
	    	Predicate restrictions = builder.greaterThanOrEqualTo(root.get("from_date"), startDate);
	    	if (toDate!=null) {
	    		restrictions = builder.and(restrictions, getBuilder().lessThanOrEqualTo(root.<Date>get("from_date"), endDate));
	    	}
	    	restrictions = builder.and(restrictions,	    					
	    			builder.equal(root.get("empCode"), empCode));
	    	
	    	Order order = builder.desc(root.get("period_from"));
	    	queryCriteria.select(root).where(restrictions).orderBy(order).distinct(true);
	    	TypedQuery<Object> query = session.createQuery(queryCriteria);
	    	log.debug("querycriteria " + queryCriteria);
	    	log.debug("query " + query);
	    	
	        list =  query.getResultList();
	        
//			list = criteria.list();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;	
	}

	public List getRequestsByDatePeriodAndRequestTypeForEmployee(final Date fromDate, final Date toDate, final Long requestType, final  String empCode){
//		Calendar cFrom= Calendar.getInstance();
//		cFrom.setTime(fromDate);
//		cFrom.set(Calendar.HOUR_OF_DAY, 0);
//		cFrom.set(Calendar.MINUTE, 0);
//		cFrom.set(Calendar.SECOND, 0);
//		//cFrom.set(Calendar.AM_PM, -1);
//		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
//		final Date dateFrom=cFrom.getTime();
//		log.debug("------dateFrom---"+dateFrom);
//
//		Calendar cTo= Calendar.getInstance();
//		cTo.setTime(toDate);
//		cTo.set(Calendar.HOUR_OF_DAY, 23);
//		cTo.set(Calendar.MINUTE, 59);
//		cTo.set(Calendar.SECOND, 59);
//
//		final Date dateTo=cTo.getTime();
//		log.debug("------dateTo---"+dateTo);
//
//		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		List list =new ArrayList();
//		try {
//			final Date startDate =(Date) format.parse(format.format(dateFrom));
//			final Date endDate = (Date)format.parse(format.format(dateTo));
//
//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			criteria.add(Expression.ge("request_date", startDate));
//			criteria.add(Expression.le("request_date", endDate));
//			criteria.add(Restrictions.eq("request_id.id", requestType));
//			criteria.add(Restrictions.eq("empCode", empCode));
//			criteria.addOrder(Property.forName("period_from").desc());
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			list = criteria.list();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;	
		
		Calendar cFrom = Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);

		final Date dateFrom = cFrom.getTime();

		log.debug("------dateFrom---" + dateFrom);

		Calendar cTo = Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo = cTo.getTime();

		log.debug("------dateTo---" + dateTo);

		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<LoginUsersRequests> query =
		        builder.createQuery(LoginUsersRequests.class);

		Root<LoginUsersRequests> root =
		        query.from(LoginUsersRequests.class);

		List<Predicate> predicates = new ArrayList<>();

		/*
		 * request_date between
		 */
		predicates.add(
		        builder.greaterThanOrEqualTo(
		                root.get("request_date"),
		                dateFrom
		        )
		);

		predicates.add(
		        builder.lessThanOrEqualTo(
		                root.get("request_date"),
		                dateTo
		        )
		);

		/*
		 * request_id.id = requestType
		 */
		predicates.add(
		        builder.equal(
		                root.get("request_id").get("id"),
		                requestType
		        )
		);

		/*
		 * empCode = ?
		 */
		predicates.add(
		        builder.equal(
		                root.get("empCode"),
		                empCode
		        )
		);

		query.select(root)
		     .where(predicates.toArray(new Predicate[0]))
		     .distinct(true)
		     .orderBy(
		             builder.desc(root.get("period_from"))
		     );

		return session.createQuery(query).getResultList();
	}

	public List getRequestsByExactDatePeriodAndRequestTypeForEmployee(final Date fromDate, final Date toDate, final Long requestType, final  String empCode){
//		Calendar cFrom= Calendar.getInstance();
//		cFrom.setTime(fromDate);
//		cFrom.set(Calendar.HOUR_OF_DAY, 0);
//		cFrom.set(Calendar.MINUTE, 0);
//		cFrom.set(Calendar.SECOND, 0);
//		//cFrom.set(Calendar.AM_PM, -1);
//		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
//		final Date dateFrom=cFrom.getTime();
//		log.debug("------dateFrom---"+dateFrom);
//
//		Calendar cTo= Calendar.getInstance();
//		cTo.setTime(toDate);
//		cTo.set(Calendar.HOUR_OF_DAY, 23);
//		cTo.set(Calendar.MINUTE, 59);
//		cTo.set(Calendar.SECOND, 59);
//
//		final Date dateTo=cTo.getTime();
//		log.debug("------dateTo---"+dateTo);
//
//		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		List list =new ArrayList();
//		try {
//			final Date startDate =(Date) format.parse(format.format(dateFrom));
//			final Date endDate = (Date)format.parse(format.format(dateTo));
//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			criteria.add(Expression.ge("from_date", startDate));
//			criteria.add(Expression.le("from_date", endDate));
//			criteria.add(Restrictions.eq("request_id.id", requestType));
//			criteria.add(Restrictions.eq("empCode", empCode));
//			criteria.addOrder(Property.forName("period_from").desc());
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			list = criteria.list();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
		
		
		Calendar cFrom = Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);

		final Date dateFrom = cFrom.getTime();

		log.debug("------dateFrom---" + dateFrom);

		Calendar cTo = Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo = cTo.getTime();

		log.debug("------dateTo---" + dateTo);

		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<LoginUsersRequests> query =
		        builder.createQuery(LoginUsersRequests.class);

		Root<LoginUsersRequests> root =
		        query.from(LoginUsersRequests.class);

		List<Predicate> predicates = new ArrayList<>();

		/*
		 * from_date between
		 */
		predicates.add(
		        builder.greaterThanOrEqualTo(
		                root.get("from_date"),
		                dateFrom
		        )
		);

		predicates.add(
		        builder.lessThanOrEqualTo(
		                root.get("from_date"),
		                dateTo
		        )
		);

		/*
		 * request_id.id = requestType
		 */
		predicates.add(
		        builder.equal(
		                root.get("request_id").get("id"),
		                requestType
		        )
		);

		/*
		 * empCode = ?
		 */
		predicates.add(
		        builder.equal(
		                root.get("empCode"),
		                empCode
		        )
		);

		query.select(root)
		     .where(predicates.toArray(new Predicate[0]))
		     .distinct(true)
		     .orderBy(
		             builder.desc(root.get("period_from"))
		     );

		return session.createQuery(query).getResultList();
	}


	public List getRequestsByDatePeriodForEmployee(final Date fromDate, final Date toDate, final String empCode){
//		Calendar cFrom= Calendar.getInstance();
//		cFrom.setTime(fromDate);
//		cFrom.set(Calendar.HOUR_OF_DAY, 0);
//		cFrom.set(Calendar.MINUTE, 0);
//		cFrom.set(Calendar.SECOND, 0);
//		//cFrom.set(Calendar.AM_PM, -1);
//		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
//		final Date dateFrom=cFrom.getTime();
//		log.debug("------dateFrom---"+dateFrom);
//
//		Calendar cTo= Calendar.getInstance();
//		cTo.setTime(toDate);
//		cTo.set(Calendar.HOUR_OF_DAY, 23);
//		cTo.set(Calendar.MINUTE, 59);
//		cTo.set(Calendar.SECOND, 59);
//
//		final Date dateTo=cTo.getTime();
//		log.debug("------dateTo---"+dateTo);
//		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		List list =new ArrayList();
//		try {
//			final Date startDate =(Date) format.parse(format.format(dateFrom));
//			final Date endDate = (Date)format.parse(format.format(dateTo));
//
//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			criteria.add(Expression.ge("request_date", startDate));
//			criteria.add(Expression.le("request_date", endDate));
//			criteria.add(Restrictions.eq("empCode", empCode));
//			criteria.addOrder(Property.forName("period_from").desc());
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			list = criteria.list();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
		
		
		Calendar cFrom = Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);

		final Date dateFrom = cFrom.getTime();

		log.debug("------dateFrom---" + dateFrom);

		Calendar cTo = Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo = cTo.getTime();

		log.debug("------dateTo---" + dateTo);

		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<LoginUsersRequests> query =
		        builder.createQuery(LoginUsersRequests.class);

		Root<LoginUsersRequests> root =
		        query.from(LoginUsersRequests.class);

		List<Predicate> predicates = new ArrayList<>();

		/*
		 * request_date between range
		 */
		predicates.add(
		        builder.greaterThanOrEqualTo(
		                root.get("request_date"),
		                dateFrom
		        )
		);

		predicates.add(
		        builder.lessThanOrEqualTo(
		                root.get("request_date"),
		                dateTo
		        )
		);

		/*
		 * empCode filter
		 */
		predicates.add(
		        builder.equal(
		                root.get("empCode"),
		                empCode
		        )
		);

		/*
		 * final query
		 */
		query.select(root)
		     .where(predicates.toArray(new Predicate[0]))
		     .distinct(true)
		     .orderBy(
		             builder.desc(root.get("period_from"))
		     );

		return session.createQuery(query).getResultList();
	}

	public List getRequestsByExactDatePeriodForEmployee(final Date fromDate, final Date toDate, final String empCode){
//		Calendar cFrom= Calendar.getInstance();
//		cFrom.setTime(fromDate);
//		cFrom.set(Calendar.HOUR_OF_DAY, 0);
//		cFrom.set(Calendar.MINUTE, 0);
//		cFrom.set(Calendar.SECOND, 0);
//		//cFrom.set(Calendar.AM_PM, -1);
//		//cFrom.set(Calendar.HOUR_OF_DAY, 23);
//		final Date dateFrom=cFrom.getTime();
//		log.debug("------dateFrom---"+dateFrom);
//
//		Calendar cTo= Calendar.getInstance();
//		cTo.setTime(toDate);
//		cTo.set(Calendar.HOUR_OF_DAY, 23);
//		cTo.set(Calendar.MINUTE, 59);
//		cTo.set(Calendar.SECOND, 59);
//
//		final Date dateTo=cTo.getTime();
//		log.debug("------dateTo---"+dateTo);
//		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		List list =new ArrayList();
//		try {
//			final Date startDate =(Date) format.parse(format.format(dateFrom));
//			final Date endDate = (Date)format.parse(format.format(dateTo));
//
//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			criteria.add(Expression.ge("from_date", startDate));
//			criteria.add(Expression.le("from_date", endDate));
//			criteria.add(Restrictions.eq("empCode", empCode));
//			criteria.addOrder(Property.forName("period_from").desc());
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			list = criteria.list();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
		
		Calendar cFrom = Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);

		final Date dateFrom = cFrom.getTime();

		log.debug("------dateFrom---" + dateFrom);

		Calendar cTo = Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo = cTo.getTime();

		log.debug("------dateTo---" + dateTo);

		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<LoginUsersRequests> query =
		        builder.createQuery(LoginUsersRequests.class);

		Root<LoginUsersRequests> root =
		        query.from(LoginUsersRequests.class);

		List<Predicate> predicates = new ArrayList<>();

		/*
		 * from_date BETWEEN dateFrom and dateTo
		 */
		predicates.add(
		        builder.greaterThanOrEqualTo(
		                root.get("from_date"),
		                dateFrom
		        )
		);

		predicates.add(
		        builder.lessThanOrEqualTo(
		                root.get("from_date"),
		                dateTo
		        )
		);

		/*
		 * empCode filter
		 */
		predicates.add(
		        builder.equal(
		                root.get("empCode"),
		                empCode
		        )
		);

		query.select(root)
		     .where(predicates.toArray(new Predicate[0]))
		     .distinct(true)
		     .orderBy(
		             builder.desc(root.get("period_from"))
		     );

		return session.createQuery(query).getResultList();
	}

	public List getEmployeesByCodes(final String codeFrom,final String codeTo){
//		Criteria criteria = getCurrentSession()
//				.createCriteria(LoginUsersRequests.class);
//		criteria.add(Restrictions.between("empCode", codeFrom, codeTo));
////		criteria.addOrder(Property.forName("period_from").asc());
//		//copied from lehaa////////////////////////////////////////
//		criteria.addOrder(Property.forName("period_from").asc());
//		//////////////////////////////////////////////////////
//		criteria
//		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<LoginUsersRequests> query =
		        builder.createQuery(LoginUsersRequests.class);

		Root<LoginUsersRequests> root =
		        query.from(LoginUsersRequests.class);

		Predicate empCodePredicate = builder.between(
		        root.get("empCode"),
		        codeFrom,
		        codeTo
		);

		query.select(root)
		     .where(empCodePredicate)
		     .distinct(true)
		     .orderBy(
		             builder.asc(root.get("period_from"))
		     );

		return session.createQuery(query).getResultList();
	}

	public List getEmployeesByCodesAndDatePeriod(final String codeFrom,final String codeTo,final Date fromDate, final Date toDate){
//		Calendar cFrom= Calendar.getInstance();
//		cFrom.setTime(fromDate);
//		cFrom.set(Calendar.HOUR_OF_DAY, 0);
//		cFrom.set(Calendar.MINUTE, 0);
//		cFrom.set(Calendar.SECOND, 0);
//		//		cFrom.set(Calendar.AM_PM, -1);
//		//		cFrom.set(Calendar.HOUR_OF_DAY, 23);
//		final Date dateFrom=cFrom.getTime();
//
//		log.debug("------dateFrom---"+dateFrom);
//
//		Calendar cTo= Calendar.getInstance();
//		cTo.setTime(toDate);
//		cTo.set(Calendar.HOUR_OF_DAY, 23);
//		cTo.set(Calendar.MINUTE, 59);
//		cTo.set(Calendar.SECOND, 59);
//
//		final Date dateTo=cTo.getTime();
//		log.debug("------dateTo---"+dateTo);
//		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		List list =new ArrayList();
//		try {
//			final Date startDate =(Date) format.parse(format.format(dateFrom));
//			final Date endDate = (Date)format.parse(format.format(dateTo));
//
//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			criteria.add(Expression.ge("request_date", startDate));
//			criteria.add(Expression.le("request_date", endDate));
//			criteria.add(Restrictions.between("empCode", codeFrom, codeTo));
//			criteria.addOrder(Property.forName("period_from").asc());
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			list = criteria.list();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;

		Calendar cFrom = Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);

		final Date dateFrom = cFrom.getTime();

		log.debug("------dateFrom---" + dateFrom);

		Calendar cTo = Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo = cTo.getTime();

		log.debug("------dateTo---" + dateTo);

		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<LoginUsersRequests> query =
		        builder.createQuery(LoginUsersRequests.class);

		Root<LoginUsersRequests> root =
		        query.from(LoginUsersRequests.class);

		List<Predicate> predicates = new ArrayList<>();

		/*
		 * request_date BETWEEN dateFrom AND dateTo
		 */
		predicates.add(
		        builder.greaterThanOrEqualTo(
		                root.get("request_date"),
		                dateFrom
		        )
		);

		predicates.add(
		        builder.lessThanOrEqualTo(
		                root.get("request_date"),
		                dateTo
		        )
		);

		/*
		 * empCode BETWEEN codeFrom AND codeTo
		 */
		predicates.add(
		        builder.between(
		                root.get("empCode"),
		                codeFrom,
		                codeTo
		        )
		);

		query.select(root)
		     .where(predicates.toArray(new Predicate[0]))
		     .distinct(true)
		     .orderBy(
		             builder.asc(root.get("period_from"))
		     );

		return session.createQuery(query).getResultList();
	}

	public List getEmployeesByCodesAndRequestType(final String codeFrom,final String codeTo , final Long requestType){
//		Criteria criteria = getCurrentSession()
//				.createCriteria(LoginUsersRequests.class);
//		criteria.add(Restrictions.between("empCode", codeFrom, codeTo));
//		criteria.add(Restrictions.eq("request_id.id", requestType));
//		criteria.addOrder(Property.forName("period_from").asc());
//		criteria
//		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<LoginUsersRequests> query =
		        builder.createQuery(LoginUsersRequests.class);

		Root<LoginUsersRequests> root =
		        query.from(LoginUsersRequests.class);

		List<Predicate> predicates = new ArrayList<>();

		predicates.add(
		        builder.between(
		                root.get("empCode"),
		                codeFrom,
		                codeTo
		        )
		);

		predicates.add(
		        builder.equal(
		                root.get("request_id").get("id"),
		                requestType
		        )
		);

		query.select(root)
		     .where(predicates.toArray(new Predicate[0]))
		     .distinct(true)
		     .orderBy(
		             builder.asc(root.get("period_from"))
		     );

		return session.createQuery(query).getResultList();
	}

	public List getEmployeesByCodesAndDatePeriodAndRequestType(final String codeFrom,final String codeTo,final Date fromDate, final Date toDate, final Long requestType){
//		Calendar cFrom= Calendar.getInstance();
//		cFrom.setTime(fromDate);
//		cFrom.set(Calendar.HOUR_OF_DAY, 0);
//		cFrom.set(Calendar.MINUTE, 0);
//		cFrom.set(Calendar.SECOND, 0);
//		final Date dateFrom=cFrom.getTime();
//
//		log.debug("------dateFrom---"+dateFrom);
//
//		Calendar cTo= Calendar.getInstance();
//		cTo.setTime(toDate);
//		cTo.set(Calendar.HOUR_OF_DAY, 23);
//		cTo.set(Calendar.MINUTE, 59);
//		cTo.set(Calendar.SECOND, 59);
//
//		final Date dateTo=cTo.getTime();
//		log.debug("------dateTo---"+dateTo);
//		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		List list =new ArrayList();
//		try {
//			final Date startDate =(Date) format.parse(format.format(dateFrom));
//			final Date endDate = (Date)format.parse(format.format(dateTo));
//
//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			criteria.add(Expression.ge("request_date", startDate));
//			criteria.add(Expression.le("request_date", endDate));
//			criteria.add(Restrictions.between("empCode", codeFrom, codeTo));
//			criteria.add(Restrictions.eq("request_id.id", requestType));
//			criteria.addOrder(Property.forName("period_from").asc());
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			list = criteria.list();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;

		Calendar cFrom = Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR_OF_DAY, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);

		final Date dateFrom = cFrom.getTime();

		log.debug("------dateFrom---" + dateFrom);

		Calendar cTo = Calendar.getInstance();
		cTo.setTime(toDate);
		cTo.set(Calendar.HOUR_OF_DAY, 23);
		cTo.set(Calendar.MINUTE, 59);
		cTo.set(Calendar.SECOND, 59);

		final Date dateTo = cTo.getTime();

		log.debug("------dateTo---" + dateTo);

		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<LoginUsersRequests> query =
		        builder.createQuery(LoginUsersRequests.class);

		Root<LoginUsersRequests> root =
		        query.from(LoginUsersRequests.class);

		List<Predicate> predicates = new ArrayList<>();

		/*
		 * request_date BETWEEN dateFrom AND dateTo
		 */
		predicates.add(
		        builder.greaterThanOrEqualTo(
		                root.get("request_date"),
		                dateFrom
		        )
		);

		predicates.add(
		        builder.lessThanOrEqualTo(
		                root.get("request_date"),
		                dateTo
		        )
		);

		/*
		 * empCode BETWEEN codeFrom AND codeTo
		 */
		predicates.add(
		        builder.between(
		                root.get("empCode"),
		                codeFrom,
		                codeTo
		        )
		);

		/*
		 * request_id.id = requestType
		 */
		predicates.add(
		        builder.equal(
		                root.get("request_id").get("id"),
		                requestType
		        )
		);

		query.select(root)
		     .where(predicates.toArray(new Predicate[0]))
		     .distinct(true)
		     .orderBy(
		             builder.asc(root.get("period_from"))
		     );

		return session.createQuery(query).getResultList();
	}


	public List getRequestStatus(final Long req_id){
//		try{
//			Criteria criteria = getCurrentSession()
//					.createCriteria(EmpReqApproval.class);
//			log.debug("request id " + req_id);
//			criteria.add(Restrictions.eq("req_id.id", req_id));
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			List result = criteria.list();
//			System.out.println("result size " + result.size());
//			return result;
//		}
//		catch (Exception e) {
//			System.out.println("exception");
//			e.printStackTrace();
//			return new ArrayList();
//		}
		
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<EmpReqApproval> query =
		        builder.createQuery(EmpReqApproval.class);

		Root<EmpReqApproval> root =
		        query.from(EmpReqApproval.class);

		Predicate reqIdPredicate = builder.equal(
		        root.get("req_id").get("id"),
		        req_id
		);

		query.select(root)
		     .where(reqIdPredicate)
		     .distinct(true);

		return session.createQuery(query).getResultList();

	}
	
	public List getRequestStatus(final Long req_id,final Long emp_id){
//		try{
//			Criteria criteria = getCurrentSession()
//					.createCriteria(EmpReqApproval.class);
//			log.debug("request id " + req_id);
//			criteria.add(Restrictions.eq("req_id.id", req_id));
//			if (emp_id!=null) {
//				criteria.add(Restrictions.eq("user_id.id", emp_id));
//			}
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			List result = criteria.list();
//			System.out.println("result size " + result.size());
//			List returnResult = new ArrayList();
//			Iterator resultItr = result.iterator();
//			while(resultItr.hasNext()) {
//				EmpReqApproval app = (EmpReqApproval)resultItr.next();
//				EmpReqApprovalJSON approvalJSON = new EmpReqApprovalJSON(app.getUser_id().getName(),app.getLevel_id().getOrder(),app.getApproval_date(),app.getNote(),app.getApproval());
//				returnResult.add(approvalJSON);
//			}
//			return returnResult;
//		}
//		catch (Exception e) {
//			System.out.println("exception");
//			e.printStackTrace();
//			return new ArrayList();
//		}
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<EmpReqApproval> query =
		        builder.createQuery(EmpReqApproval.class);

		Root<EmpReqApproval> root =
		        query.from(EmpReqApproval.class);

		List<Predicate> predicates = new ArrayList<>();

		predicates.add(
		        builder.equal(
		                root.get("req_id").get("id"),
		                req_id
		        )
		);

		if (emp_id != null) {
			predicates.add(
			        builder.equal(
			                root.get("user_id").get("id"),
			                emp_id
			        )
			);
		}

		query.select(root)
		     .where(predicates.toArray(new Predicate[0]))
		     .distinct(true);

		return session.createQuery(query).getResultList();

	}


	public void signInOut(AttendanceRequest userRequest) {
		// TODO Auto-generated method stub
		System.out.println("dao sign in/out");
		
	}


	public List getEmpReqTypeAccs(List accessLevels,Long requestType) {
//		try{
//			if (accessLevels.size()>0) {
//				Criteria criteria = getCurrentSession()
//						.createCriteria(EmpReqTypeAcc.class);
//				log.debug("request type " + requestType);
//				if (requestType == null) {
//					log.debug("requestType " + requestType);
//				} else if (requestType != null && !requestType.equals(new Long(6)) && !requestType.equals(new Long(7)) && !requestType.equals(new Long(8))) {
//					log.debug("request type not null  " + requestType);
//					criteria.createCriteria("req_id").add(Restrictions.eq("id", requestType));
//				 }else if (requestType.equals(new Long(8))) {////////////sign in & out together
//					log.debug("requestType " + requestType);
//					criteria.createCriteria("req_id").add(Restrictions.or(Restrictions.eq("id", new Long(10)), Restrictions.eq("id", new Long(11))));
//				} else if (requestType.equals(new Long(7))) {
//					log.debug("requestType (7) " + requestType);
//					criteria.createCriteria("req_id").add(Restrictions.or(Restrictions.eq("id", new Long(4)), Restrictions.eq("id", new Long(5))));
//				} else {
//					log.debug("requestType " + requestType);
//				}
//				log.debug("will query levels " + accessLevels.toArray() + " - " + accessLevels.size());
//				criteria.createCriteria("group_id").add(Restrictions.in("id", accessLevels));
//				log.debug("will query levels ");
//				criteria.addOrder(Property.forName("id").asc());
//				criteria
//				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//				List list = new ArrayList();
//				try {
//					list = criteria.list();
//					log.debug("queried levels");
//					Iterator itr = list.iterator();
//					while(itr.hasNext()) {
//						EmpReqTypeAcc acc = (EmpReqTypeAcc)itr.next();
////						log.debug(acc.getId());
//					}
//					log.debug("empreqtypeacc size " + list.size());
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					log.debug(e.getMessage());
//					e.printStackTrace();
//					e.printStackTrace();
//				}
////				while(itr.hasNext()) {
////					EmpReqTypeAcc acc = (EmpReqTypeAcc)itr.next();
////					log.debug("EmpReqTypeAcc " + acc.getId() + " group id " + acc.getGroup_id().getId() + " emp code " + acc.getEmp_id());
////				}
//				return list;
//			} else return new ArrayList();
//		}
//		catch (Exception e) {
//			return new ArrayList();
//		}
		
		if (accessLevels == null || accessLevels.isEmpty()) {
		    return new ArrayList<>();
		}

		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<EmpReqTypeAcc> query =
		        builder.createQuery(EmpReqTypeAcc.class);

		Root<EmpReqTypeAcc> root =
		        query.from(EmpReqTypeAcc.class);

		List<Predicate> predicates = new ArrayList<>();

		/*
		 * group_id IN (:accessLevels)
		 */
		predicates.add(
		        root.get("group_id").get("id").in(accessLevels)
		);

		/*
		 * requestType logic
		 */
		if (requestType != null) {

		    if (!requestType.equals(6L)
		            && !requestType.equals(7L)
		            && !requestType.equals(8L)) {

		        // req_id.id = requestType
		        predicates.add(
		                builder.equal(
		                        root.get("req_id").get("id"),
		                        requestType
		                )
		        );

		    } else if (requestType.equals(8L)) {

		        // (10 OR 11)
		        predicates.add(
		                builder.or(
		                        builder.equal(root.get("req_id").get("id"), 10L),
		                        builder.equal(root.get("req_id").get("id"), 11L)
		                )
		        );

		    } else if (requestType.equals(7L)) {

		        // (4 OR 5)
		        predicates.add(
		                builder.or(
		                        builder.equal(root.get("req_id").get("id"), 4L),
		                        builder.equal(root.get("req_id").get("id"), 5L)
		                )
		        );
		    }
		}

		query.select(root)
		     .where(predicates.toArray(new Predicate[0]))
		     .distinct(true)
		     .orderBy(
		             builder.asc(root.get("id"))
		     );

		return session.createQuery(query).getResultList();
	}


	@Transactional
	public int insertTimeAttend(String emp_code, Date date_,
			Date time_, String trans_type) {
		return externalQueries.insertTimeAttend(emp_code, date_, time_, trans_type);
	}


	public Map checkStartedRequests(RequestsApprovalQuery requestQuery,
			Employee emp) {
		log.debug("inside checkStartedRequests dao");
		Map response = new HashMap();
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		RestStatus status = new RestStatus();
		log.debug("requestQuery.getRequestType() " +requestQuery.getRequestType());
		Long requestType = null;
		if (requestQuery.getRequestType()!=null && !requestQuery.getRequestType().isEmpty()) {
			requestType =  Long.parseLong(requestQuery.getRequestType());
		} 
		log.debug("inside checkStartedRequests dao request type " + requestType);
		Date dateFrom = null;
		Date dateTo = null;
		
		log.debug("inside checkStartedRequests");
		Date newDate1 = null;
		log.debug(requestQuery.getDateFrom());
		if (requestQuery.getDateFrom()!=null && !requestQuery.getDateFrom().isEmpty()) {
			log.debug("not from date null");
			try {
				newDate1 = df.parse(requestQuery.getDateFrom());
			} catch(Exception e){
				log.debug(e.getMessage());
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
		log.debug(requestQuery.getDateTo());
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


		Session session = getCurrentSession();
		CriteriaBuilder builder = getBuilder();
		 if (session == null || session.isOpen()==false) {
	    		session = getCurrentSession();
	    }
		CriteriaQuery queryCriteria = builder.createQuery(LoginUsersRequests.class);
	    	Root<Object> root = queryCriteria.from(LoginUsersRequests.class);
	    	Predicate restrictions = null;
	    	log.debug("*************request type " + requestType);
	    	if (requestType== null){
	    		restrictions = builder.and(builder.notEqual(root.get("request_id").get("id"),  new Long(10)),builder.notEqual(root.get("request_id").get("id"),  new Long(11)));
	    		if (sDate !=null && eDate != null) {
	    			restrictions = builder.and(restrictions,	    					
	    					builder.or(
	    								builder.and(builder.isNull(root.<Date>get("period_to")),builder.between(root.<Date>get("period_from"), sDate, eDate)),
				    					builder.or(
				    							builder.or(builder.between(root.<Date>get("period_to"), sDate, eDate),builder.between(root.<Date>get("period_from"), sDate, eDate)),
				    							builder.and(builder.greaterThanOrEqualTo(root.<Date>get("period_to"), sDate),builder.lessThanOrEqualTo(root.<Date>get("period_from"), sDate))
				    							)
	    					));
	    		}
	    	} else if (requestType.equals(new Long(1))){
	    		restrictions = builder.and(builder.equal(root.get("request_id").get("id"), new Long(3)),
	    									builder.and(builder.notEqual(root.get("request_id").get("id"),new Long(10)),
	    											builder.notEqual(root.get("request_id").get("id"), new Long(11))));
	    		if (sDate !=null) {
	    			restrictions = builder.and(restrictions,builder.greaterThanOrEqualTo(root.<Date>get("period_from"), sDate));
	    		}
	    		if (eDate != null) {
	    			restrictions = builder.and(restrictions,builder.lessThanOrEqualTo(root.<Date>get("period_from"), eDate));
	    		}
	    	}  else if (requestType.equals(new Long(2))){
	    		restrictions = builder.and(builder.and(builder.isNull(root.<Date>get("period_to")),
	    												builder.equal(root.get("request_id").get("id"), new Long(5))),
						    				builder.and(builder.notEqual(root.get("request_id").get("id"),new Long(10)),
													builder.notEqual(root.get("request_id").get("id"), new Long(11))));
	    		if (sDate !=null) {
	    			restrictions = builder.and(restrictions,builder.greaterThanOrEqualTo(root.<Date>get("period_from"), sDate));
	    		}
	    		if (eDate != null) {
	    			restrictions = builder.and(restrictions,builder.lessThanOrEqualTo(root.<Date>get("period_from"), eDate));
	    		}
	    	}
	    		
	    	restrictions = builder.and(restrictions,builder.equal(root.get("empCode"), emp.getEmpCode()));

	    	CriteriaQuery c = queryCriteria.select(root);
	    	c = c.where(restrictions);
	    	c.orderBy(builder.desc(root.get("period_from"))).distinct(true);
	    	log.debug("checkStartedRequests - there is an error in the query not yet found");
	    	TypedQuery<Object> query = session.createQuery(queryCriteria);
	    	log.debug("querycriteria " + queryCriteria);
	    	log.debug("query " + query);
	    	
	        List list =  query.getResultList();
			log.debug("list size " + list.size());
	        if ((list.size() == 0)&&(log.isDebugEnabled())) {
	            log.debug("No objects found");
	        }
	        else if (log.isDebugEnabled()) {
	            log.debug("Got "+list.size()+" objects");
	        }
		
//		List list =new ArrayList();
//		Criteria criteria = getCurrentSession()
//				.createCriteria(LoginUsersRequests.class);
//	
//		
//		
//		log.debug("request type " + requestType);
//		if (requestType== null){
//			criteria.add(Restrictions.and(Restrictions.ne("request_id.id", new Long(10)), Restrictions.ne("request_id.id", new Long(11))));
//			if (sDate !=null && eDate != null) {
//				final Date startDate =sDate;
//				final Date endDate = eDate;
//				criteria.add(Restrictions.or(
//												Restrictions.and(Expression.isNull("period_to"),Expression.between("period_from", startDate, endDate)),
//												Restrictions.or(Restrictions.or(Expression.between("period_to", startDate,endDate),Expression.between("period_from",startDate,endDate)),
//																Restrictions.and(Expression.ge("period_to", startDate),Expression.le("period_from", startDate))
//												)
//											)
//							);
//			}					
//		} else if (requestType.equals(new Long(1))){
//			
//			log.debug("request type " + requestType);
//			
//			criteria.add(Expression.isNull("period_to"));
//			
//			criteria.add(Restrictions.eq("request_id.id", new Long(3)));
//			criteria.add(Restrictions.and(Restrictions.ne("request_id.id", new Long(10)), Restrictions.ne("request_id.id", new Long(11))));
//			if (sDate !=null) {
//				final Date startDate =sDate;
//				criteria.add(Expression.ge("period_from", startDate));
//			}
//			if (eDate != null) {
//				final Date endDate = eDate;
//				criteria.add(Expression.le("period_from", endDate));
//			}
//		} else if (requestType.equals(new Long(2))){
//			log.debug("request type " + requestType);
//			
//			criteria.add(Restrictions.and(Restrictions.isNull("period_to"), Restrictions.eq("request_id.id", new Long(5))));
////			criteria.add(Expression.isNull("period_to"));
//			
////			criteria.add(Restrictions.or(Restrictions.eq("request_id.id", new Long(4)),));
//			criteria.add(Restrictions.and(Restrictions.ne("request_id.id", new Long(10)), Restrictions.ne("request_id.id", new Long(11))));
//			
//			if (sDate !=null) {
//				final Date startDate =sDate;
//				criteria.add(Expression.ge("period_from", startDate));
//			}
//			if (eDate != null) {
//				final Date endDate = eDate;
//				criteria.add(Expression.le("period_from", endDate));
//			}
//		} 
//		
//		criteria.add(Restrictions.eq("empCode", emp.getEmpCode()));
//		criteria.addOrder(Property.forName("period_from").desc());
//		criteria
//		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		list = criteria.list();
		
		log.debug("check started requests list size " + list.size());
		status.setCode("200");
		status.setMessage("Successful Transaction");
		status.setStatus("True");
		response.put("Status", status);
		response.put("Response", list) ;
		return response;
	}
	
	public Map checkStartedRequestsIncludingAttendance(RequestsApprovalQuery requestQuery,
			Employee emp) {
//		log.debug("inside checkStartedRequests dao");
//		Map response = new HashMap();
//		DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
//		RestStatus status = new RestStatus();
//		log.debug("requestQuery.getRequestType() " +requestQuery.getRequestType());
//		Long requestType = null;
//		if (requestQuery.getRequestType()!=null && !requestQuery.getRequestType().isEmpty()) {
//			requestType =  Long.parseLong(requestQuery.getRequestType());
//		} 
//		log.debug("inside checkStartedRequests dao request type " + requestType);
//		Date dateFrom = null;
//		Date dateTo = null;
//		
//		log.debug("inside checkStartedRequests");
//		Date newDate1 = null;
//		log.debug(requestQuery.getDateFrom());
//		if (requestQuery.getDateFrom()!=null && !requestQuery.getDateFrom().isEmpty()) {
//			try {
//				newDate1 = df.parse(requestQuery.getDateFrom());
//			} catch(Exception e){
//				e.printStackTrace();
//				status.setCode("312");
//				status.setMessage("Date is not well formated");
//				status.setStatus("False");
//				response.put("Status", status);
//				return response;
//			}
//
//
//			Calendar cFrom= Calendar.getInstance();
//			cFrom.setTime(newDate1);
//			cFrom.set(Calendar.HOUR_OF_DAY, 0);
//			cFrom.set(Calendar.MINUTE, 0);
//			cFrom.set(Calendar.SECOND, 0);
//			dateFrom=cFrom.getTime();
//			log.debug("------dateFrom---"+dateFrom);
//		}
//		Date newDate2 = null;
//		log.debug(requestQuery.getDateTo());
//		if (requestQuery.getDateTo()!=null && !requestQuery.getDateTo().isEmpty()) {
//			try {
//				newDate2 = df.parse(requestQuery.getDateTo());
//			} catch(Exception e){
//				e.printStackTrace();
//				status.setCode("312");
//				status.setMessage("Date is not well formated");
//				status.setStatus("False");
//				response.put("Status", status);
//				return response;
//			}
//			Calendar cTo= Calendar.getInstance();
//			cTo.setTime(newDate2);
//			cTo.set(Calendar.HOUR_OF_DAY, 23);
//			cTo.set(Calendar.MINUTE, 59);
//			cTo.set(Calendar.SECOND, 59);
//
//			dateTo=cTo.getTime();
//			log.debug("------dateTo---"+dateTo);
//		}
//
//		DateFormat format =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		Date sDate =null;
//		if (dateFrom != null) {
//			try {
//				sDate = (Date) format.parse(format.format(dateFrom));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		Date eDate = null;
//		if (dateTo != null) {
//			try {
//				eDate = (Date)format.parse(format.format(dateTo));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//		log.debug("sDate " + sDate);
//		log.debug("eDate " + eDate);
//		
//		List list =new ArrayList();
//		Criteria criteria = getCurrentSession()
//				.createCriteria(LoginUsersRequests.class);
//	
//		
//		
//		log.debug("request type " + requestType);
////			criteria.add(Restrictions.and(Restrictions.ne("request_id.id", new Long(10)), Restrictions.ne("request_id.id", new Long(11))));
//			if (sDate !=null && eDate != null) {
//				final Date startDate =sDate;
//				final Date endDate = eDate;
//				criteria.add(Restrictions.or(
//												Restrictions.and(Expression.isNull("period_to"),Expression.between("period_from", startDate, endDate)),
//												Restrictions.or(Restrictions.or(Expression.between("period_to", startDate,endDate),Expression.between("period_from",startDate,endDate)),
//																Restrictions.and(Expression.ge("period_to", startDate),Expression.le("period_from", startDate))
//												)
//											)
//							);
//			}					
//		
//		
//		criteria.add(Restrictions.eq("empCode", emp.getEmpCode()));
//		criteria.addOrder(Property.forName("period_from").desc());
//		criteria
//		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		list = criteria.list();
//		log.debug("check started requests list size " + list.size());
//		status.setCode("200");
//		status.setMessage("Successful Transaction");
//		status.setStatus("True");
//		response.put("Status", status);
//		response.put("Response", list) ;
//		return response;
		
		log.debug("inside checkStartedRequests dao");

		Map<String, Object> response = new HashMap<>();
		RestStatus status = new RestStatus();

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		Long requestType = null;

		if (requestQuery.getRequestType() != null &&
		        !requestQuery.getRequestType().isEmpty()) {
		    requestType = Long.parseLong(requestQuery.getRequestType());
		}

		Date dateFrom = null;
		Date dateTo = null;

		/*
		 * Parse from date
		 */
		if (requestQuery.getDateFrom() != null &&
		        !requestQuery.getDateFrom().isEmpty()) {

		    try {
		        Date parsed = df.parse(requestQuery.getDateFrom());

		        Calendar cFrom = Calendar.getInstance();
		        cFrom.setTime(parsed);
		        cFrom.set(Calendar.HOUR_OF_DAY, 0);
		        cFrom.set(Calendar.MINUTE, 0);
		        cFrom.set(Calendar.SECOND, 0);

		        dateFrom = cFrom.getTime();

		    } catch (Exception e) {
		        status.setCode("312");
		        status.setMessage("Date is not well formatted");
		        status.setStatus("False");
		        response.put("Status", status);
		        return response;
		    }
		}

		/*
		 * Parse to date
		 */
		if (requestQuery.getDateTo() != null &&
		        !requestQuery.getDateTo().isEmpty()) {

		    try {
		        Date parsed = df.parse(requestQuery.getDateTo());

		        Calendar cTo = Calendar.getInstance();
		        cTo.setTime(parsed);
		        cTo.set(Calendar.HOUR_OF_DAY, 23);
		        cTo.set(Calendar.MINUTE, 59);
		        cTo.set(Calendar.SECOND, 59);

		        dateTo = cTo.getTime();

		    } catch (Exception e) {
		        status.setCode("312");
		        status.setMessage("Date is not well formatted");
		        status.setStatus("False");
		        response.put("Status", status);
		        return response;
		    }
		}

		log.debug("dateFrom " + dateFrom);
		log.debug("dateTo " + dateTo);

		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<LoginUsersRequests> query =
		        builder.createQuery(LoginUsersRequests.class);

		Root<LoginUsersRequests> root =
		        query.from(LoginUsersRequests.class);

		List<Predicate> predicates = new ArrayList<>();

		/*
		 * empCode filter
		 */
		predicates.add(
		        builder.equal(
		                root.get("empCode"),
		                emp.getEmpCode()
		        )
		);

		/*
		 * requestType logic (kept for completeness if needed later)
		 */
		if (requestType != null) {
		    predicates.add(
		            builder.equal(
		                    root.get("request_id").get("id"),
		                    requestType
		            )
		    );
		}

		/*
		 * COMPLEX DATE OVERLAP LOGIC (converted from Hibernate Restrictions.or)
		 *
		 * Equivalent to:
		 * - period_to IS NULL AND period_from between
		 * - OR period_to between range
		 * - OR period_from between range
		 * - OR period_to >= start AND period_from <= start
		 */
		if (dateFrom != null && dateTo != null) {

		    Predicate noEndButStartsInRange =
		            builder.and(
		                    builder.isNull(root.get("period_to")),
		                    builder.between(root.get("period_from"), dateFrom, dateTo)
		            );

		    Predicate periodToInRange =
		            builder.between(root.get("period_to"), dateFrom, dateTo);

		    Predicate periodFromInRange =
		            builder.between(root.get("period_from"), dateFrom, dateTo);

		    Predicate overlapsStart =
		            builder.and(
		                    builder.greaterThanOrEqualTo(root.get("period_to"), dateFrom),
		                    builder.lessThanOrEqualTo(root.get("period_from"), dateFrom)
		            );

		    Predicate dateOverlap =
		            builder.or(
		                    noEndButStartsInRange,
		                    builder.or(
		                            periodToInRange,
		                            builder.or(
		                                    periodFromInRange,
		                                    overlapsStart
		                            )
		                    )
		            );

		    predicates.add(dateOverlap);
		}

		query.select(root)
		     .where(predicates.toArray(new Predicate[0]))
		     .distinct(true)
		     .orderBy(builder.desc(root.get("period_from")));

		List<LoginUsersRequests> list =
		        session.createQuery(query).getResultList();

		log.debug("check started requests list size " + list.size());

		status.setCode("200");
		status.setMessage("Successful Transaction");
		status.setStatus("True");

		response.put("Status", status);
		response.put("Response", list);

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
		
//		List list =new ArrayList();
//
//		Criteria criteria = getCurrentSession()
//				.createCriteria(LoginUsersRequests.class);
//
//		criteria.add(Restrictions.eq("request_id.id", new Long(10)));
//		criteria.add(Restrictions.eq("empCode", empCode));
//		
////		if (sDate !=null) {
//			final Date startDate =sDate;
//			criteria.add(Expression.ge("period_from", startDate));
////		}
////		if (eDate != null) {
//			final Date endDate = eDate;
//			criteria.add(Expression.le("period_from", endDate));
////			}
//		criteria.addOrder(Property.forName("period_from").desc());
//		criteria
//		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Session session = getCurrentSession();
		CriteriaBuilder builder = getBuilder();
		log.debug("*^*^*^*^ session " + session);
		log.debug("*^*^*^*^ session is open " + session.isOpen());
		 if (session == null || session.isOpen()==false) {
	    		session = getCurrentSession();
	    }
		CriteriaQuery queryCriteria = builder.createQuery(LoginUsersRequests.class);
	    	Root<Object> root = queryCriteria.from(LoginUsersRequests.class);
	    	Predicate restrictions = builder.and(builder.equal(root.get("request_id").get("id"),  new Long(10)),builder.equal(root.get("empCode"), empCode));
	    	restrictions = builder.and(restrictions, builder.and(builder.greaterThanOrEqualTo(root.<Date>get("period_from"), sDate),builder.lessThanOrEqualTo(root.<Date>get("period_from"), eDate)));
	    	queryCriteria.select(root).where(restrictions).orderBy(builder.desc(root.get("period_from"))).distinct(true);
	    	TypedQuery<Object> query = session.createQuery(queryCriteria);
	        List list =  query.getResultList();
			
	        if ((list.size() == 0)&&(log.isDebugEnabled())) {
	            log.debug("No objects found");
	        }
	        else if (log.isDebugEnabled()) {
	            log.debug("Got "+list.size()+" objects");
	        }
		
//		list = criteria.list();
		log.debug("list size " + list.size());
		
		if (list.size()>0) {
			LoginUsersRequests req = (LoginUsersRequests)list.get(0);
			
			attStatus.setSignIn(new Boolean(true));
			log.debug(req.getPeriod_from().getTime());
			Long tsLong = req.getPeriod_from().getTime();
//			String tsString = tsLong.toString();
//			if (tsString.length()>10) {
//				tsLong = new Long (tsString.substring(0, 10));
//				log.debug(tsLong);
//			}
			attStatus.setSignInTime(tsLong);
//			Timestamp ts = new Timestamp(req.getPeriod_from().getTime());
//			log.debug(ts);
//			Calendar fromCal= Calendar.getInstance();
//			fromCal.setTime(req.getPeriod_from());
//			DateFormat ff =	new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.sss");
//			try {
//				final Date fDate =(Date) format.parse(ff.format(req.getPeriod_from()));
//				log.debug(fDate.getTime());
//				attStatus.setSignInTime(fDate.getTime());
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			log.debug(req.getId() + " - " + req.getPeriod_from());
//			response.put("Response",format.format(req.getPeriod_from()));
		} else {
			attStatus.setSignIn(new Boolean(false));
			attStatus.setSignInTime(null);
		}

		//////////////////////////////////////
		
//		List list2 =new ArrayList();
//		criteria = getCurrentSession()
//				.createCriteria(LoginUsersRequests.class);
//	
//		criteria.add(Restrictions.eq("request_id.id", new Long(11)));
//		criteria.add(Restrictions.eq("empCode", empCode));
//		
//			
////		final Date startDate2 =sDate;
//		criteria.add(Expression.ge("period_from", startDate));
////		final Date endDate2 = eDate;
//		criteria.add(Expression.le("period_from", endDate));
//		criteria.addOrder(Property.forName("period_from").desc());
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		list2 = criteria.list();
		
		queryCriteria = builder.createQuery(LoginUsersRequests.class);
    	root = queryCriteria.from(LoginUsersRequests.class);
    	restrictions = builder.and(builder.equal(root.get("request_id").get("id"),  new Long(11)),builder.equal(root.get("empCode"), empCode));
    	restrictions = builder.and(restrictions,builder.and(builder.greaterThanOrEqualTo(root.<Date>get("period_from"), sDate),builder.lessThanOrEqualTo(root.<Date>get("period_from"), eDate)));
    	queryCriteria.select(root).where(restrictions).orderBy(builder.desc(root.get("period_from"))).distinct(true);
    	TypedQuery<Object> query2 = session.createQuery(queryCriteria);
        List list2 =  query2.getResultList();

		if (list2.size()>0) {
			if (list2.size()==list.size()) {
				LoginUsersRequests req = (LoginUsersRequests)list2.get(0);

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
	
	public List getLoginUsersByCodes(final String codeFrom,final String codeTo){
//		Criteria criteria = getCurrentSession()
//				.createCriteria(LoginUsers.class);
//		criteria.add(Restrictions.between("empCode", codeFrom, codeTo));
//		criteria
//		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		Session session = getCurrentSession();
		CriteriaBuilder builder = getBuilder();
		
		CriteriaQuery queryCriteria = builder.createQuery(LoginUsers.class);
	  	Root<Object> root = queryCriteria.from(LoginUsers.class);
	  	Predicate restriction = builder.between(root.get("empCode"), codeFrom, codeTo);
	  	queryCriteria.where(restriction);
	  	queryCriteria.select(root);
	  	queryCriteria.distinct(true);
	  	TypedQuery<Object> query = null;
		query = session.createQuery(queryCriteria);
		List list = query.getResultList();
		return list;
	}


	public List getAccessLevelsBetweenCodes(GroupAcc groupId,
			String codeFrom, String codeTo) {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(AccessLevels.class);
//		if (codeFrom!= null && !codeFrom.isEmpty() && codeTo!= null && !codeTo.isEmpty() ) {
//			criteria.createCriteria("emp_id").add(Restrictions.between("empCode", codeFrom, codeTo));
//		}
//		criteria
//		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		Criteria criteria = getCurrentSession()
				.createCriteria(EmpReqTypeAcc.class);
		if (codeFrom!= null && !codeFrom.isEmpty() && codeTo!= null && !codeTo.isEmpty() ) {
			criteria.createCriteria("emp_id").createCriteria("empCode").add(Restrictions.between("empCode", codeFrom, codeTo));
		}
		criteria.add(Restrictions.eq("group_id", groupId));
		criteria
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}


//	public List<LoginUsers > getEmployeesByGroup(Long groupId) {
//		log.debug("DAO: groupid " + groupId);
//		Criteria criteria = getCurrentSession().createCriteria(EmpReqTypeAcc.class);
//		criteria.createCriteria("emp_id").add(Restrictions.isNull("endServ"));
//		criteria.setProjection(Projections.projectionList()
//                .add(Projections.groupProperty("emp_id")));
//		criteria.createCriteria("group_id")
//		.add(Restrictions.eq("id", groupId));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
//<<<<<<< HEAD
//	}
	
	//////////////testing it to improve performance of this method
//	public List<LoginUsers> getEmployeesByGroup(Long groupId) {
//	    log.debug("DAO: groupid " + groupId);
//	    Criteria criteria = getCurrentSession().createCriteria(EmpReqTypeAcc.class, "empReqTypeAcc");
//
//	    // Use a JOIN to filter by group and employee status
//	    criteria.createAlias("empReqTypeAcc.emp_id", "emp");
//	    criteria.createAlias("emp.empCode", "employee");
//	    criteria.createAlias("employee.users", "user");
//
//	    criteria.add(Restrictions.eq("empReqTypeAcc.group_id.id", groupId));
//	    criteria.add(Restrictions.isNull("emp.endServ"));
//	    
//	    // Explicitly fetch the associated objects to avoid N+1 selects.
//	    // This tells Hibernate to eagerly load these associations in the main query.
//	    criteria.setFetchMode("empReqTypeAcc.emp_id", FetchMode.JOIN);
//	    criteria.setFetchMode("emp.empCode", FetchMode.JOIN);
//	    criteria.setFetchMode("employee.users", FetchMode.JOIN);
//	    
//	    // Use DISTINCT_ROOT_ENTITY to get a list of unique LoginUsers objects.
//	    criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//
//	    return criteria.list();
//	}
	
	public List<LoginUsers> getEmployeesByGroup(Long groupId) {
	    log.debug("DAO: groupid " + groupId);

	    // Step 1: Create a subquery to get the list of unique employee IDs
	    DetachedCriteria subQuery = DetachedCriteria.forClass(EmpReqTypeAcc.class, "empReq");
	    subQuery.setProjection(Projections.property("empReq.emp_id.id"));
	    subQuery.add(Restrictions.eq("empReq.group_id.id", groupId));

	    // Step 2: Create the main criteria to select LoginUsers based on the IDs
	    Criteria mainCriteria = getCurrentSession().createCriteria(LoginUsers.class, "user");
	    mainCriteria.add(Restrictions.isNull("user.endServ"));
	    
	    // Corrected: Use Subqueries.propertyIn() to connect the main query to the subquery
	    mainCriteria.add(Subqueries.propertyIn("user.id", subQuery));

	    // Ensure a distinct list of users is returned
	    mainCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

	    return mainCriteria.list();
}
//=======
		
//		Session session = getCurrentSession();
//		CriteriaBuilder builder = getBuilder();
//		log.debug("*^*^*^*^ session " + session);
//		log.debug("*^*^*^*^ session is open " + session.isOpen());
//		
//		CriteriaQuery queryCriteria = builder.createQuery(LoginUsers.class);
//      	Root<Object> root = queryCriteria.from(EmpReqTypeAcc.class);
//      	Predicate restriction = builder.and(builder.isNull(root.get("emp_id").get("endServ")),
//      										builder.equal(root.get("group_id").get("id"), groupId));
//      	
//      	
//      	queryCriteria.where(restriction);
//      
////      	List groupBy = new ArrayList();
////      	groupBy.add(root.get("emp_id"));
//      	queryCriteria.select(root.get("emp_id"));//.groupBy(groupBy);
////      	queryCriteria.select(root);
//      	queryCriteria.distinct(true);
//      	TypedQuery<Object> query = null;
//		query = session.createQuery(queryCriteria);
//		List list = query.getResultList();
//		return list;
//	}
//	
	public List<LoginUsers> getMgrsByGroup(Long groupId) {
		log.debug("DAO: groupid " + groupId);
		
		Session session = getCurrentSession();
		CriteriaBuilder builder = getBuilder();
		
		CriteriaQuery queryCriteria = builder.createQuery(LoginUsers.class);
		Root<Object> root = queryCriteria.from(AccessLevels.class);
//		List groupBy = new ArrayList();
//		groupBy.add(root.get("emp_id"));
		Predicate restrictions = null;
		restrictions = builder.equal(root.get("level_id").get("id"), groupId);
		
		
		queryCriteria.select(root.get("emp_id")).where(restrictions);//.groupBy(groupBy);
      	queryCriteria.distinct(true);
      	TypedQuery<Object> query = null;
		query = session.createQuery(queryCriteria);
		List list = query.getResultList();
		return list;
	}
	/////////testing to improve performance
	
//	public List<LoginUsers> getMgrsByGroup(Long groupId) {
//	    log.debug("DAO: groupid " + groupId);
//	    Criteria criteria = getCurrentSession().createCriteria(AccessLevels.class);
//
//	    // Create an alias for the emp_id association
//	    criteria.createAlias("emp_id", "emp");
//	    // Add a restriction on the level_id
//	    criteria.add(Restrictions.eq("level_id.id", groupId));
//	    // Project the entire 'emp' object (the LoginUsers entity)
//	    criteria.setProjection(Projections.property("emp_id"));
//	    // Ensure only distinct LoginUsers are returned
//	    criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//
//	    return criteria.list();
//	}
//	
	


	@Override
	public List getAccessLevels() {
		Session session = getCurrentSession();
		CriteriaBuilder builder = getBuilder();
		
		CriteriaQuery queryCriteria = builder.createQuery(GroupAcc.class);
		Root<Object> root = queryCriteria.from(GroupAcc.class);
		root.fetch("accessLevel", JoinType.LEFT);
		queryCriteria.distinct(true);
		TypedQuery<Object> query = null;
		query = session.createQuery(queryCriteria);
		List list = query.getResultList();
		return list;
	}

	
//	public List getAttendanceRequests(Date date, String empCode,RequestTypes reqType) {
//		try{
//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			criteria.add(Restrictions.eq("request_id", reqType));
//			criteria.add(Restrictions.eq("empCode", empCode));
//			criteria.add(Restrictions.eq("request_id", date));
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			List list = criteria.list();
//			Iterator itr = list.iterator();
//			log.debug("empreqtypeacc size " + list.size());
//			while(itr.hasNext()) {
//				EmpReqTypeAcc acc = (EmpReqTypeAcc)itr.next();
//				log.debug("EmpReqTypeAcc " + acc.getId() + " group id " + acc.getGroup_id().getId());
//			}
//			return list;
//		}
//		catch (Exception e) {
//			return new ArrayList();
//		}
//	}
	
	





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
