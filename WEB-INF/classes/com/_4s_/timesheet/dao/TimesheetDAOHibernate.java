package com._4s_.timesheet.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.common.dao.BaseDAOHibernate;
import com._4s_.common.model.Employee;
import com._4s_.common.util.Page;
import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.model.TimesheetCostCenter;
import com._4s_.timesheet.model.TimesheetTransaction;
import com._4s_.timesheet.model.TimesheetTransactionParts;

@Transactional
@Repository
public class TimesheetDAOHibernate extends BaseDAOHibernate implements TimesheetDAO {
	
	private TimesheetExternalQueries externalQueries = null;

	public TimesheetExternalQueries getExternalQueries() {
		return externalQueries;
	}

	public void setExternalQueries(TimesheetExternalQueries externalQueries) {
		this.externalQueries = externalQueries;
	}

	public TimesheetTransaction getTimesheetTrans(Employee empCode,
			Date inDate, TimesheetCostCenter costcenter,
			TimesheetTransactionParts part1, TimesheetTransactionParts part2,
			TimesheetTransactionParts part3) {
//		Criteria criteria = getCurrentSession().createCriteria(TimesheetTransaction.class);
//		criteria.add(Restrictions.eq("empCode", empCode));
//		criteria.add(Restrictions.eq("inDate", inDate));
//		criteria.add(Restrictions.eq("costCode", costcenter));
//		criteria.add(Restrictions.eq("part1", part1));
//		criteria.add(Restrictions.eq("part2", part2));
//		criteria.add(Restrictions.eq("part3", part3));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		if (criteria.list()!=null && criteria.list().size()>0) {
//			return (TimesheetTransaction)criteria.list().get(0);
//		} else {
//			return null;
//		}
		
		CriteriaBuilder builder = super.getBuilder();
		Session session = super.getCurrentSession();

		CriteriaQuery<TimesheetTransaction> query =
		        builder.createQuery(TimesheetTransaction.class);

		Root<TimesheetTransaction> root =
		        query.from(TimesheetTransaction.class);

		List<Predicate> predicates = new ArrayList<>();

		predicates.add(builder.equal(root.get("empCode"), empCode));
		predicates.add(builder.equal(root.get("inDate"), inDate));
		predicates.add(builder.equal(root.get("costCode"), costcenter));
		predicates.add(builder.equal(root.get("part1"), part1));
		predicates.add(builder.equal(root.get("part2"), part2));
		predicates.add(builder.equal(root.get("part3"), part3));

		query.select(root)
		     .where(predicates.toArray(new Predicate[0]))
		     .distinct(true);

		TypedQuery<TimesheetTransaction> typedQuery =
		        session.createQuery(query);

		typedQuery.setMaxResults(1);

		List<TimesheetTransaction> result =
		        typedQuery.getResultList();

		return result.isEmpty() ? null : result.get(0);
	}
	
	public Map getTimesheetTransactions(String empCode, Date fromDate, Date toDate, TimesheetCostCenter costcenter, TimesheetActivity activity, TimesheetTransactionParts part1, TimesheetTransactionParts part2, TimesheetTransactionParts part3, int pageNo, int pageSize, String sort) {
		Page page = new Page();
		return page.getPage(externalQueries.getTimesheetTransactions(empCode, fromDate, toDate, costcenter, activity, part1, part2, part3, pageNo, pageSize, sort), pageNo, pageSize);
	}
}
