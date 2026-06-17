package com._4s_.common.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com._4s_.common.model.Branch;
import com._4s_.common.model.City;
import com._4s_.common.model.Country;
import com._4s_.common.model.Department;
import com._4s_.common.model.Employee;
import com._4s_.common.model.HijriDateAdjustment;
import com._4s_.common.model.LastSequence;
import com._4s_.common.model.Types;
import com._4s_.common.model.TypesData;

@Repository
public class CommonDAOHibernate extends BaseDAOHibernate implements CommonDAO{
	/*public Employee getEmployeeByAccount(Account account) {
		return (Employee) getObjectByParameter(Employee.class, "account",
				account);
	}*/

	public Employee getEmployeeByUsername(String userName) {
		return (Employee) getObjectByParameter(Employee.class, "userName",
				userName);
	}

	public LastSequence getSequenceByClassName(String className) {
		return (LastSequence) getObjectByParameter(LastSequence.class,
				"className", className);
	}

	public void saveSequence(LastSequence lastSequence) {
		saveObject(lastSequence);
	}

	public List getCitiesByCountry(final Long countryId){
//		Criteria criteria = getCurrentSession()
//				.createCriteria(City.class);
//		criteria.createCriteria("country")
//		.add(Restrictions.eq("id",countryId));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		criteria.addOrder(Property.forName("description").asc());
//		return criteria.list();
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<City> queryCriteria = builder.createQuery(City.class);
		Root<City> root = queryCriteria.from(City.class);

		Join<City, Country> countryJoin = root.join("country");

		queryCriteria.select(root)
		             .where(builder.equal(countryJoin.get("id"), countryId))
		             .distinct(true)
		             .orderBy(builder.asc(root.get("description")));

		return session.createQuery(queryCriteria).getResultList();
	}

	public Employee getEmployeeByUser(final Long userId) {
		CriteriaQuery queryCriteria = getBuilder().createQuery(Employee.class);
    	Root<Object> root = queryCriteria.from(Employee.class);
    	Predicate restrictions = getBuilder().equal(root.get("users").get("id"), userId);
    	queryCriteria.select(root).where(restrictions).distinct(true);
    	
    	Session session = getCurrentSession();
    	
    	if (session == null || session.isOpen()==false) {
    		getCurrentSession();
    	}
		
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
		query.setMaxResults(1);
		System.out.println("#### query "+query);
		List list = query.getResultList();
		
//		Criteria criteria = getCurrentSession
//		criteria.createCriteria(Employee.class);
//    	criteria.createCriteria("users").add(Restrictions.eq("id",userId));
//		criteria.setMaxResults(1);
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List list = criteria.list();

		if ((list.size() == 0) && (log.isDebugEnabled())) {
			log.debug("No objects found");
		} else if (log.isDebugEnabled()) {
			log.debug("Got " + list.size() + " objects");
		}
		return (Employee) list.iterator().next();
	}

	public List getEmployeesNotInInternalCommunicator() {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(Employee.class);
//		criteria.add(Restrictions.eq("isInternalCommunicator",new Boolean(false)));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<Employee> queryCriteria =
		        builder.createQuery(Employee.class);

		Root<Employee> root = queryCriteria.from(Employee.class);

		Predicate restriction =
		        builder.equal(root.get("isInternalCommunicator"), Boolean.FALSE);

		queryCriteria.select(root)
		             .where(restriction)
		             .distinct(true);

		TypedQuery<Employee> query = session.createQuery(queryCriteria);

		return query.getResultList();
	}

	public  List getBranchesRelatedByCompany(final Long companyId)
	{
//		Criteria criteria = getCurrentSession()
//				.createCriteria(Branch.class);
//		criteria.createCriteria("company").add(Restrictions.eq("id", companyId));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<Branch> queryCriteria =
		        builder.createQuery(Branch.class);

		Root<Branch> root = queryCriteria.from(Branch.class);

		Predicate restriction =
		        builder.equal(root.get("company").get("id"), companyId);

		queryCriteria.select(root)
		             .where(restriction)
		             .distinct(true);

		TypedQuery<Branch> query = session.createQuery(queryCriteria);

		return query.getResultList();
	}

	public List getDataByTypes(final Types type){
//		Criteria criteria = getCurrentSession()
//				.createCriteria(TypesData.class);
//		criteria.createCriteria("type")
//		.add(Restrictions.eq("id",type.getId()));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<TypesData> queryCriteria =
		        builder.createQuery(TypesData.class);

		Root<TypesData> root = queryCriteria.from(TypesData.class);

		Predicate restriction =
		        builder.equal(root.get("type").get("id"), type.getId());

		queryCriteria.select(root)
		             .where(restriction)
		             .distinct(true);

		TypedQuery<TypesData> query = session.createQuery(queryCriteria);

		return query.getResultList();
	}

	public List getEmployeesByFirstName(final String namePart){
//		Criteria criteria = getCurrentSession()
//				.createCriteria(Employee.class);
//		criteria.add(Restrictions.like("firstName",namePart,MatchMode.START));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<Employee> queryCriteria =
		        builder.createQuery(Employee.class);

		Root<Employee> root = queryCriteria.from(Employee.class);

		List<Predicate> predicates = new ArrayList<>();

		predicates.add(
		        builder.like(
		                root.get("firstName"),
		                namePart + "%"
		        )
		);

		queryCriteria.select(root)
		             .where(predicates.toArray(new Predicate[] {}))
		             .distinct(true);

		TypedQuery<Employee> query = session.createQuery(queryCriteria);

		List<Employee> list = query.getResultList();

		return list;
	}

	public List search(final String searchCommandId,final String searchCommandName, final Class className, final String firstParam,final String secondParam,final MatchMode match1,final MatchMode match2) {
		log.debug(">>>>>>>>>>>>>>>>>>> searchCommand.getId() "+searchCommandId);
		log.debug(">>>>>>>>>>>>>>>>>>> searchCommand.getName() "+searchCommandName);
		log.debug(">>>>>>>>>>>>>>>>>>> className "+className);
		log.debug(">>>>>>>>>>>>>>>>>>> firstParam "+firstParam);
		log.debug(">>>>>>>>>>>>>>>>>>> secondParam "+secondParam);



//		Criteria criteria = getCurrentSession()
//				.createCriteria(className);
//		if (searchCommandId != null && !searchCommandId.equals("")){
//			criteria.add(Restrictions.like(firstParam,searchCommandId));
//		}
//		if (searchCommandName != null && !searchCommandName.equals("")){
//			criteria.add(Restrictions.like(secondParam,searchCommandName,match2));
//		}
//		//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<Object> queryCriteria = builder.createQuery(className);
		Root<Object> root = queryCriteria.from(className);

		List<Predicate> predicates = new ArrayList<>();

		if (searchCommandId != null && !searchCommandId.equals("")) {
		    predicates.add(
		            builder.like(
		                    root.get(firstParam),
		                    searchCommandId
		            )
		    );
		}

		if (searchCommandName != null && !searchCommandName.equals("")) {

		    String pattern;

		    switch (match2) {
		        case START:
		            pattern = searchCommandName + "%";
		            break;
		        case END:
		            pattern = "%" + searchCommandName;
		            break;
		        case ANYWHERE:
		            pattern = "%" + searchCommandName + "%";
		            break;
		        case EXACT:
		        default:
		            pattern = searchCommandName;
		            break;
		    }

		    predicates.add(
		            builder.like(
		                    root.get(secondParam),
		                    pattern
		            )
		    );
		}

		queryCriteria.select(root);

		if (!predicates.isEmpty()) {
		    queryCriteria.where(predicates.toArray(new Predicate[] {}));
		}

		// Uncomment if you need the equivalent of DISTINCT_ROOT_ENTITY
		// queryCriteria.distinct(true);

		TypedQuery<?> query = session.createQuery(queryCriteria);

		return query.getResultList();
	}

	public List getCitiesByDescription(final String namePart){
//		Criteria criteria = getCurrentSession()
//				.createCriteria(City.class);
//		criteria.add(Restrictions.like("description",namePart,MatchMode.START));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<City> queryCriteria =
		        builder.createQuery(City.class);

		Root<City> root = queryCriteria.from(City.class);

		List<Predicate> predicates = new ArrayList<>();

		predicates.add(
		        builder.like(
		                root.get("description"),
		                namePart + "%"
		        )
		);

		queryCriteria.select(root)
		             .where(predicates.toArray(new Predicate[] {}))
		             .distinct(true);

		TypedQuery<City> query = session.createQuery(queryCriteria);

		return query.getResultList();
	}

	public List getCountriesByDescription(final String namePart){
//		Criteria criteria = getCurrentSession()
//				.createCriteria(Country.class);
//		criteria.add(Restrictions.like("description",namePart,MatchMode.START));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<Country> queryCriteria =
		        builder.createQuery(Country.class);

		Root<Country> root = queryCriteria.from(Country.class);

		List<Predicate> predicates = new ArrayList<>();

		predicates.add(
		        builder.like(
		                root.get("description"),
		                namePart + "%"
		        )
		);

		queryCriteria.select(root)
		             .where(predicates.toArray(new Predicate[] {}))
		             .distinct(true);

		TypedQuery<Country> query = session.createQuery(queryCriteria);

		return query.getResultList();
	}

	public List getCountries(){
//		Criteria criteria = getCurrentSession()
//				.createCriteria(Country.class);
//		criteria.addOrder(Property.forName("description").asc());
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<Country> queryCriteria =
		        builder.createQuery(Country.class);

		Root<Country> root = queryCriteria.from(Country.class);

		queryCriteria.select(root)
		             .distinct(true)
		             .orderBy(builder.asc(root.get("description")));

		TypedQuery<Country> query = session.createQuery(queryCriteria);

		return query.getResultList();
	}

	public HijriDateAdjustment getHijriDateAdjustment(final Date date) {
		log.error(">>>>>>>>>>HijriDateAdjustment ");

//		Criteria criteria = getCurrentSession()
//				.createCriteria(HijriDateAdjustment.class);
//		criteria.add(Restrictions.ge("miladiDate",date));
//		List list = criteria.list();
//		log.error(">>>>>>>>>>list "+list);
//		if (list.size() > 0){
//			log.error(">>>>>>>>>>>>>>>>> if");
//			return (HijriDateAdjustment)list.iterator().next();
//		}
//		else{
//			log.error(">>>>>>>>>>>>>>>>> else");
//			return null;
//		}
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<HijriDateAdjustment> queryCriteria =
		        builder.createQuery(HijriDateAdjustment.class);

		Root<HijriDateAdjustment> root =
		        queryCriteria.from(HijriDateAdjustment.class);

		Predicate restriction =
		        builder.greaterThanOrEqualTo(
		                root.get("miladiDate"),
		                date
		        );

		queryCriteria.select(root)
		             .where(restriction)
		             .orderBy(builder.asc(root.get("miladiDate")));

		TypedQuery<HijriDateAdjustment> query =
		        session.createQuery(queryCriteria);

		List<HijriDateAdjustment> list = query.getResultList();

		log.error(">>>>>>>>>>list " + list);

		if (!list.isEmpty()) {
		    log.error(">>>>>>>>>>>>>>>>> if");
		    return list.get(0);
		} else {
		    log.error(">>>>>>>>>>>>>>>>> else");
		    return null;
		}
	}



	/*public TrialDestination getDestination(final Long id) {



						Criteria criteria = getSession
								.createCriteria(TrialDestination.class);
						criteria.add(Restrictions.eq("id",id));
						criteria.uniqueResult();
						return criteria.list();
					}
				});
		return (TrialDestination)list.iterator().next();
	}

	 */
	/*public List getDestinations() {



						Criteria criteria = getSession
								.createCriteria(TrialDestination.class);
						return criteria.list();
					}
				});
		return list;
	}*/


	public List getEmployeesByBranchAndDepartment(final String branchId,final String departmentId) {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(Employee.class);
//
//		if(branchId != null && !branchId.equals("")){
//			Branch branch = (Branch) getObject(Branch.class , branchId);
//			criteria.add(Restrictions.eq("branch" , branch));
//		}
//		if(departmentId != null && !departmentId.equals("")){
//			Department department = (Department) getObject(Department.class , new Long(departmentId));
//			criteria.add(Restrictions.eq("department" , department));
//		}
//		criteria.addOrder(Property.forName("firstName").asc());
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<Employee> queryCriteria =
		        builder.createQuery(Employee.class);

		Root<Employee> root = queryCriteria.from(Employee.class);

		List<Predicate> predicates = new ArrayList<>();

		/*
		 * branch filter
		 */
		if (branchId != null && !branchId.equals("")) {
		    predicates.add(
		            builder.equal(
		                    root.get("branch").get("id"),
		                    branchId
		            )
		    );
		}

		/*
		 * department filter
		 */
		if (departmentId != null && !departmentId.equals("")) {
		    predicates.add(
		            builder.equal(
		                    root.get("department").get("id"),
		                    Long.valueOf(departmentId)
		            )
		    );
		}

		queryCriteria.select(root)
		             .where(predicates.toArray(new Predicate[] {}))
		             .orderBy(builder.asc(root.get("firstName")))
		             .distinct(true);

		TypedQuery<Employee> query =
		        session.createQuery(queryCriteria);

		return query.getResultList();
	}
}

