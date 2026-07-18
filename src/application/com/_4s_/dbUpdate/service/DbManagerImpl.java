package com._4s_.dbUpdate.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.common.service.BaseManagerImpl;
import com._4s_.dbUpdate.dao.DbDAO;
import com._4s_.dbUpdate.dao.SQLDAO;

@Service("DbManager")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class DbManagerImpl extends BaseManagerImpl implements DbManager {
	@Autowired
	private DbDAO dbDao;

		
	public DbDAO getDbDao() {
		return dbDao;
	}

	public void setDbDao(DbDAO dbDao) {
		this.dbDao = dbDao;
	}

	public void executeQuery(String query)
	{
		dbDao.executeQuery(query);
	}
	

}