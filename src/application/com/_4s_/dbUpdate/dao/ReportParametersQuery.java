package com._4s_.dbUpdate.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class ReportParametersQuery {
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
	
	String cause;
	
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public void reportParametersQuery(String query){

		log.debug(">>>>>>>>>>>>>>>>>>>>> starting reportParametersQuery");
		log.debug(">>>>>>>>>>>>>>>>>>>>> dataSource "+dataSource);
		jt = new JdbcTemplate(dataSource);
		log.debug(">>>>>>>>>>>>>>>>>>>>> jt "+jt);
		sql = query;
		log.debug(">>>>>>>>>>>>>>>>>>>>> sql "+sql);
		jt.execute(sql);
	}
	

	public List getList(String query, String id){
		log.debug(">>>>>>>>>>>>>>>>>>>>> starting reportParametersQuery");
		log.debug(">>>>>>>>>>>>>>>>>>>>> dataSource "+dataSource);
		jt = new JdbcTemplate(dataSource);
		log.debug(">>>>>>>>>>>>>>>>>>>>> jt "+jt);
		sql = query;
		log.debug(">>>>>>>>>>>>>>>>>>>>> sql "+sql);
		
		
		StringUtils su = new StringUtils();
		sql = su.replace(sql,"?",id);
		log.debug(">>>>>>>>>>>>>>>>>>>>> sql after el ma2sah"+sql);
		List reportParameters = jt.queryForList(sql);
		log.debug(">>>>>>>>>>>>>>>>>>>>> reportParameters "+reportParameters);
		return reportParameters;
	}
}
