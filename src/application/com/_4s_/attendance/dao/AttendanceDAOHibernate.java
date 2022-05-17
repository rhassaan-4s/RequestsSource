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
	
	public TimesheetTransaction getTimesheetTrans(Employee empCode,
			Date inDate, TimesheetCostCenter costcenter,
			TimesheetTransactionParts part1, TimesheetTransactionParts part2,
			TimesheetTransactionParts part3) {
		Criteria criteria = getCurrentSession().createCriteria(TimesheetTransaction.class);
		criteria.add(Restrictions.eq("empCode", empCode));
		criteria.add(Restrictions.eq("inDate", inDate));
		criteria.add(Restrictions.eq("costCode", costcenter));
		criteria.add(Restrictions.eq("part1", part1));
		criteria.add(Restrictions.eq("part2", part2));
		criteria.add(Restrictions.eq("part3", part3));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (criteria.list()!=null && criteria.list().size()>0) {
			return (TimesheetTransaction)criteria.list().get(0);
		} else {
			return null;
		}
	}
	
	public Map getTimesheetTransactions(String hostName,String serviceName,String userName,String password,String empCode, Date fromDate, Date toDate, TimesheetCostCenter costcenter, TimesheetActivity activity, TimesheetTransactionParts part1, TimesheetTransactionParts part2, TimesheetTransactionParts part3, int pageNo, int pageSize, String sort) {
		Page page = new Page();
		return page.getPage(externalQueries.getTimesheetTransactions(hostName, serviceName, userName, password, empCode, fromDate, toDate, costcenter, activity, part1, part2, part3, pageNo, pageSize, sort), pageNo, pageSize);
	}
}
