package com._4s_.dbUpdate.dao;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com._4s_.common.dao.BaseDAOHibernate;

public class SQLDAOHibernate extends BaseDAOHibernate implements SQLDAO {
	
	private JdbcTemplate jt;
	private DataSource dataSource;
	public String sql;
	private final Log log = LogFactory.getLog(getClass());
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void excuteQuery(String query){

		log.debug(">>>>>>>>>>>>>>>>>>>>> starting reportParametersQuery");
		log.debug(">>>>>>>>>>>>>>>>>>>>> dataSource "+dataSource);
		jt = new JdbcTemplate(dataSource);
		log.debug(">>>>>>>>>>>>>>>>>>>>> jt "+jt);
		sql = query;
		log.debug(">>>>>>>>>>>>>>>>>>>>> sql "+sql);
		jt.execute(sql);
	}
}