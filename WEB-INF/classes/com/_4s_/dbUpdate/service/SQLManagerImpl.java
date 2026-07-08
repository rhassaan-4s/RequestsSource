package com._4s_.dbUpdate.service;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.common.service.BaseManagerImpl;
import com._4s_.dbUpdate.dao.SQLDAO;

@Service("SQLManager")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class SQLManagerImpl extends BaseManagerImpl implements SQLManager {

	private SQLDAO sqldao;

	public SQLDAO getSqldao() {
		return sqldao;
	}

	public void setSqldao(SQLDAO sqldao) {
		this.sqldao = sqldao;
	}
	
	public void excuteQuery(String query)
	{
		sqldao.excuteQuery(query);
	}
	
	public void setDataSource(DataSource dataSource)
	{
		sqldao.setDataSource(dataSource);
	}

}