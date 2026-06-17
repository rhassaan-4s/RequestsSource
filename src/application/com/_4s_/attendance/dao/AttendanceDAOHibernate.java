package com._4s_.attendance.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com._4s_.attendance.model.WorkPeriodMaster;
import com._4s_.common.dao.BaseDAOHibernate;
import com._4s_.common.model.EmpBasic;
import com._4s_.requestsApproval.model.LoginUsersRequests;

//@Transactional
@Repository
public class AttendanceDAOHibernate extends BaseDAOHibernate implements AttendanceDAO {
	
	private AttendanceExternalQueries externalQueries = null;

	public AttendanceExternalQueries getExternalQueries() {
		return externalQueries;
	}

	public void setExternalQueries(AttendanceExternalQueries externalQueries) {
		this.externalQueries = externalQueries;
	}

	
	
	
	
	public Integer getNumberOfAttendees(Date from, Date to) {
//		try{
//			Criteria criteria = getCurrentSession()
//					.createCriteria(LoginUsersRequests.class);
//			criteria.add(Restrictions.between("from_date", from, to));
//			criteria.add(Restrictions.eq("request_id", new Long(10)));
//			criteria
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			List list = criteria.list();
//			log.debug("attendees size " + list.size());
//			
//			return list.size();
//		}
//		catch (Exception e) {
//			return new Integer(0);
//		}
		
		try {

		    CriteriaBuilder builder = super.getBuilder();
		    Session session = super.getCurrentSession();

		    CriteriaQuery<Long> query =
		            builder.createQuery(Long.class);

		    Root<LoginUsersRequests> root =
		            query.from(LoginUsersRequests.class);

		    List<Predicate> predicates = new ArrayList<>();

		    predicates.add(
		            builder.between(
		                    root.get("from_date"),
		                    from,
		                    to
		            )
		    );

		    predicates.add(
		            builder.equal(
		                    root.get("request_id"),
		                    10L
		            )
		    );

		    query.select(builder.countDistinct(root))
		         .where(predicates.toArray(new Predicate[0]));

		    Long count = session.createQuery(query).getSingleResult();

		    return count.intValue();

		} catch (Exception e) {
		    return 0;
		}
	}

	@Override
	public WorkPeriodMaster getWorkPeriodMaster(String workperiodCode) {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(WorkPeriodMaster.class);
//		criteria.createCriteria("workperiods").add(Restrictions.eq("workperiods", workperiodCode));
//		criteria
//		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List list = criteria.list();
//		if (list.size()>0) {
//			return (WorkPeriodMaster)list.get(0);
//		} else {
//			return null;
//		}
		
		
		CriteriaBuilder builder = super.getBuilder();
	    Session session = super.getCurrentSession();

	    CriteriaQuery<WorkPeriodMaster> query =
	            builder.createQuery(WorkPeriodMaster.class);

	    Root<WorkPeriodMaster> root =
	            query.from(WorkPeriodMaster.class);

	    query.select(root)
	         .where(builder.equal(root.get("workperiods"), workperiodCode));

	    List<WorkPeriodMaster> list = session.createQuery(query).getResultList();

	    if (!list.isEmpty()) {
	        return list.get(0);
	    } else {
	        return null;
	    }
	}

	@Override
	public List getActiveEmpBasic() {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(EmpBasic.class);
//		criteria.add(Restrictions.isNull("end_serv"));
//		criteria
//		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		
		CriteriaBuilder builder = super.getBuilder();
	    Session session = super.getCurrentSession();

	    CriteriaQuery<EmpBasic> query =
	            builder.createQuery(EmpBasic.class);

	    Root<EmpBasic> root =
	            query.from(EmpBasic.class);

	    query.select(root)
	         .where(builder.isNull(root.get("end_serv")));

	    List<EmpBasic> list = session.createQuery(query).getResultList();

	    return list;
	}
	
	
	
}
