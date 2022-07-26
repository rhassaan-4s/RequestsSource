package com._4s_.timesheet.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.hibernate.hql.ast.tree.DeleteStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com._4s_.common.dao.CommonQueries;
import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.model.TimesheetCostCenter;
import com._4s_.timesheet.model.TimesheetTransactionParts;
//import com._4s_.stores.model.DBConnection;
//import com._4s_.stores.model.ExternalDependenceTo;
//import com._4s_.stores.model.ItemData;
//import com._4s_.stores.model.ShippingData;
//import com._4s_.stores.model.StoreTransactionM;
//import com._4s_.stores.model.StoreTransactionMDep;
//import com._4s_.stores.model.StoreTransactionMDepExt;
//import com._4s_.stores.model.StoreTransactionO;
//import com._4s_.stores.model.StoreTrnsDep;
//import com._4s_.stores.service.StoresManager;
//import com._4s_.stores.web.action.ExternalTypeAndCode;

public class TimesheetExternalQueries extends CommonQueries {
	protected final Log log = LogFactory.getLog(getClass());

	public Map getTimesheetTransactions(String empCode, Date fromDate,
			Date toDate, TimesheetCostCenter costcenter,
			TimesheetActivity activity, TimesheetTransactionParts part1,
			TimesheetTransactionParts part2, TimesheetTransactionParts part3,
			int pageNo, int pageSize, String sort) {
		Calendar cFrom= Calendar.getInstance();

		Date dateFrom=null;
		Date dateTo= null;
		log.debug("------fromDate---"+fromDate);
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

		//////////////////////////////////////////////////////////////////
		//////////////Date Strings Definition/////////////////////////////
		//////////////////////////////////////////////////////////////////
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String from_dateString=null;
		String to_dateString=null;
		//////////////////////////////////////////////////////////////////
		///////////////End of date strings definition
		/////////////////////////////////////////////////////////////////

		Map map = new HashMap();

		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		String query = "";
		String select = "";
		String where = "";
		String orderBy = "";

		select = "select trans.empCode empCode,to_char(trans.inDate , 'DD/MM/YYYY') inDate,trans.activity activityCode,act.name,trans.costcode costCode,cost.costName costName,"
				+ " (CASE WHEN trans.part1 = 9999999999 THEN null ELSE trans.part1 END) AS partCode1, "
				+ "part1.name partName1, "
				+ "(CASE WHEN trans.part2 = 9999999999 THEN null ELSE trans.part2 END) AS partCode2,"
				+ " part2.name partName2,"
				+ "(CASE WHEN trans.part3 = 9999999999 THEN null ELSE trans.part3 END) AS partCode3,part3.name partName3 "
				+ " , trans.cHour cHour, trans.cMinute cMinute, trans.remark remark "
				+ " from  emp_dist_time_sheet trans, cost cost, activity act, time_sheet_parts part1, time_sheet_parts part2, time_sheet_parts part3 ";
		where = " where trans.costcode=cost.costcode and act.activity=trans.activity and part1.code=trans.part1 and part2.code=trans.part2 and part3.code=trans.part3 "
				+ " and trans.empCode in (" + empCode+") ";

		log.debug("from date " + fromDate + "to date " + toDate);
		if (fromDate!= null && toDate!=null) {
			if(where == null || where.isEmpty()) {
				where = " where ";
			} else {
				where += " and ";
			}
			from_dateString=df.format(fromDate);
			to_dateString=df.format(toDate);
			where += " ((trunc(trans.inDate) >= TO_DATE('"+from_dateString+"','DD/MM/YYYY') and "
					+ "trunc(trans.inDate)  <= TO_DATE('"+to_dateString+"','DD/MM/YYYY'))) ";
		}
		if (costcenter!=null) {
			if(where == null || where.isEmpty()) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " trans.costCode =" + costcenter.getCostCode();
		}
		if (activity!=null) {
			if(where == null || where.isEmpty()) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " trans.activity= " + activity.getActivity();
		}
		if (part1!=null) {
			if(where == null || where.isEmpty()) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " trans.part1 = " + part1.getCode();
		}
		if (part2!=null) {
			if(where == null || where.isEmpty()) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " trans.part2 = " + part2.getCode(); 
		}
		if (part3!=null) {
			if(where == null || where.isEmpty()) {
				where = " where ";
			} else {
				where += " and ";
			}
			where += " trans.part3 = " + part3.getCode();
		}

		///////////////////////////////////////////////////////////////////

		if (sort!= null && !sort.isEmpty()) {
			if (sort.equalsIgnoreCase("desc")) {
				orderBy = " order by trans.inDate desc ";
			} else if (sort.equalsIgnoreCase("asc")) {
				orderBy = " order by trans.inDate asc ";
			}
		}
		/////////////////////////////////////////////////////////////////
		
		query = select + where + orderBy;
		log.debug(query);
		StringBuilder sql = new StringBuilder(query);
		
		List in=(List) getJdbcTemplate().queryForList(sql.toString());
		
		String listSizeQuery = "select count (*) from ("+query+")";
		log.debug(listSizeQuery);
		log.debug("listSizeQuery " + listSizeQuery);
		StringBuilder sqlListSize = new StringBuilder(listSizeQuery);
		List in2=(List) getJdbcTemplate().queryForList(sqlListSize.toString());
		
		log.debug(in2.size());
		map.put("Results", in);
		if (in2.size()>0) {
			log.debug("listSize " + ((LinkedCaseInsensitiveMap)in2.get(0)).get("count(*)"));
			map.put("listSize", ((BigDecimal)((LinkedCaseInsensitiveMap)in2.get(0)).get("count(*)")).intValue());
		} else {
			map.put("listSize", new Long(0));
		}
		return map;
	}
	
}
