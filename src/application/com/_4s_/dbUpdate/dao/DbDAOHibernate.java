package com._4s_.dbUpdate.dao;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.common.dao.BaseDAOHibernate;
import com.zaxxer.hikari.HikariDataSource;

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Repository
public class DbDAOHibernate extends BaseDAOHibernate implements DbDAO {

	protected final Log log = LogFactory.getLog(getClass());

	public HikariDataSource dataSource;
	public static Session session = null;

	public static Session getSession() {
		return session;
	}

	public HikariDataSource getDataSource() {
		return dataSource;
	}

	
	public void setDataSource(HikariDataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Autowired
	public SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getCurrentSession() {
		session = null;
		log.debug("$$$$$$$$$$$$$$$$$$getting current session");
		log.debug("session factory " + sessionFactory);
		try {
			session = sessionFactory.getCurrentSession();
			log.debug("***session available " + session);
			if (session == null || session.isOpen() == false) {
				session = sessionFactory.openSession();
				log.debug("session " + session);
			}
		} catch (HibernateException e) {
			log.debug("###Exception#### session not available, will open new session");
			session = sessionFactory.openSession();
			log.debug("***********new session opened****************");
		}
		log.debug("$$$$$$$$$$$$$$$$$$session " + session);
		return session;
	}

	public void executeQuery(String query) {
		Session session = getCurrentSession();
		session.createNativeQuery(query).executeUpdate();
	}

}