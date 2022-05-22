package com._4s_.attendance.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.common.dao.BaseDAOHibernate;
import com._4s_.common.model.Employee;
import com._4s_.common.util.Page;
import com._4s_.requestsApproval.model.EmpReqTypeAcc;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.model.TimesheetCostCenter;
import com._4s_.timesheet.model.TimesheetTransaction;
import com._4s_.timesheet.model.TimesheetTransactionParts;

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
	
}
