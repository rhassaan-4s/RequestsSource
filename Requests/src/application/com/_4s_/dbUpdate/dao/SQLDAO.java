package com._4s_.dbUpdate.dao;

import javax.sql.DataSource;

import com._4s_.common.dao.BaseDAO;

public interface SQLDAO extends BaseDAO {
	public abstract void excuteQuery(String query);
	public abstract void setDataSource(DataSource dataSource);
	}
