package com._4s_.common.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
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
		Criteria criteria = getCurrentSession()
				.createCriteria(City.class);
		criteria.createCriteria("country")
		.add(Restrictions.eq("id",countryId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Property.forName("description").asc());
		return criteria.list();
	}

	public Employee getEmployeeByUser(final Long userId) {



		Criteria criteria = getCurrentSession()
				.createCriteria(Employee.class);
		criteria.createCriteria("users").add(Restrictions.eq("id",userId));
		criteria.setMaxResults(1);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List list = criteria.list();

		if ((list.size() == 0) && (log.isDebugEnabled())) {
			log.debug("No objects found");
		} else if (log.isDebugEnabled()) {
			log.debug("Got " + list.size() + " objects");
		}
		return (Employee) list.iterator().next();
	}

	public List getEmployeesNotInInternalCommunicator() {



		Criteria criteria = getCurrentSession()
				.createCriteria(Employee.class);
		criteria.add(Restrictions.eq("isInternalCommunicator",new Boolean(false)));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public  List getBranchesRelatedByCompany(final Long companyId)
	{
		Criteria criteria = getCurrentSession()
				.createCriteria(Branch.class);
		criteria.createCriteria("company").add(Restrictions.eq("id", companyId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getDataByTypes(final Types type){
		Criteria criteria = getCurrentSession()
				.createCriteria(TypesData.class);
		criteria.createCriteria("type")
		.add(Restrictions.eq("id",type.getId()));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getEmployeesByFirstName(final String namePart){
		Criteria criteria = getCurrentSession()
				.createCriteria(Employee.class);
		criteria.add(Restrictions.like("firstName",namePart,MatchMode.START));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List search(final String searchCommandId,final String searchCommandName, final Class className, final String firstParam,final String secondParam,final MatchMode match1,final MatchMode match2) {
		log.debug(">>>>>>>>>>>>>>>>>>> searchCommand.getId() "+searchCommandId);
		log.debug(">>>>>>>>>>>>>>>>>>> searchCommand.getName() "+searchCommandName);
		log.debug(">>>>>>>>>>>>>>>>>>> className "+className);
		log.debug(">>>>>>>>>>>>>>>>>>> firstParam "+firstParam);
		log.debug(">>>>>>>>>>>>>>>>>>> secondParam "+secondParam);



		Criteria criteria = getCurrentSession()
				.createCriteria(className);
		if (searchCommandId != null && !searchCommandId.equals("")){
			criteria.add(Restrictions.like(firstParam,searchCommandId));
		}
		if (searchCommandName != null && !searchCommandName.equals("")){
			criteria.add(Restrictions.like(secondParam,searchCommandName,match2));
		}
		//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getCitiesByDescription(final String namePart){
		Criteria criteria = getCurrentSession()
				.createCriteria(City.class);
		criteria.add(Restrictions.like("description",namePart,MatchMode.START));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getCountriesByDescription(final String namePart){
		Criteria criteria = getCurrentSession()
				.createCriteria(Country.class);
		criteria.add(Restrictions.like("description",namePart,MatchMode.START));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getCountries(){
		Criteria criteria = getCurrentSession()
				.createCriteria(Country.class);
		criteria.addOrder(Property.forName("description").asc());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public HijriDateAdjustment getHijriDateAdjustment(final Date date) {
		log.error(">>>>>>>>>>HijriDateAdjustment ");

		Criteria criteria = getCurrentSession()
				.createCriteria(HijriDateAdjustment.class);
		criteria.add(Restrictions.ge("miladiDate",date));
		List list = criteria.list();
		log.error(">>>>>>>>>>list "+list);
		if (list.size() > 0){
			log.error(">>>>>>>>>>>>>>>>> if");
			return (HijriDateAdjustment)list.iterator().next();
		}
		else{
			log.error(">>>>>>>>>>>>>>>>> else");
			return null;
		}
	}



	/*public TrialDestination getDestination(final Long id) {



						Criteria criteria = getCurrentSession()
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



						Criteria criteria = getCurrentSession()
								.createCriteria(TrialDestination.class);
						return criteria.list();
					}
				});
		return list;
	}*/


	public List getEmployeesByBranchAndDepartment(final String branchId,final String departmentId) {
		Criteria criteria = getCurrentSession()
				.createCriteria(Employee.class);

		if(branchId != null && !branchId.equals("")){
			Branch branch = (Branch) getObject(Branch.class , branchId);
			criteria.add(Restrictions.eq("branch" , branch));
		}
		if(departmentId != null && !departmentId.equals("")){
			Department department = (Department) getObject(Department.class , new Long(departmentId));
			criteria.add(Restrictions.eq("department" , department));
		}
		criteria.addOrder(Property.forName("firstName").asc());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
}

