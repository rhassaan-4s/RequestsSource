package com._4s_.attendance.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.attendance.model.WorkPeriodMaster;
import com._4s_.common.dao.BaseDAOHibernate;
import com._4s_.common.model.EmpBasic;
import com._4s_.requestsApproval.model.LoginUsersRequests;

@Transactional
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
		try{
			Criteria criteria = getCurrentSession()
					.createCriteria(LoginUsersRequests.class);
			criteria.add(Restrictions.between("from_date", from, to));
			criteria.add(Restrictions.eq("request_id", new Long(10)));
			criteria
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List list = criteria.list();
			log.debug("attendees size " + list.size());
			
			return list.size();
		}
		catch (Exception e) {
			return new Integer(0);
		}
	}

	@Override
	public WorkPeriodMaster getWorkPeriodMaster(String workperiodCode) {
		Criteria criteria = getCurrentSession()
				.createCriteria(WorkPeriodMaster.class);
		criteria.createCriteria("workperiods").add(Restrictions.eq("workperiods", workperiodCode));
		criteria
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List list = criteria.list();
		if (list.size()>0) {
			return (WorkPeriodMaster)list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List getActiveEmpBasic() {
		Criteria criteria = getCurrentSession()
				.createCriteria(EmpBasic.class);
		criteria.add(Restrictions.isNull("end_serv"));
		criteria
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
	
	
	
}
