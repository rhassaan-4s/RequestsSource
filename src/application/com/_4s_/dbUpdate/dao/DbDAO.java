package com._4s_.dbUpdate.dao;

import javax.sql.DataSource;

import com._4s_.common.dao.BaseDAO;

public interface DbDAO extends BaseDAO {
	public abstract void executeQuery(String query);
}
