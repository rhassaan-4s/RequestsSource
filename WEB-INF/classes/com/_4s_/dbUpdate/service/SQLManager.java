package com._4s_.dbUpdate.service;

import javax.sql.DataSource;

import com._4s_.common.service.BaseManager;
public interface SQLManager extends BaseManager {
	
	public abstract void excuteQuery(String query);
	public abstract void setDataSource(DataSource dataSource);
}
