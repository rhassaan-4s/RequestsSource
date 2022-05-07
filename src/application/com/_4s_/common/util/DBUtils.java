package com._4s_.common.util; 

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DBUtils {

	protected final static Log log = LogFactory.getLog(DBUtils.class);
	
	private static DataSource dataSource;
	private static JdbcTemplate jdbcTemplate;
	private static Connection cachedConnection = null;
	
	
	// this method will used by Spring to inject dataSource
	@Autowired
	public void setDataSource(DataSource dataSource) {
		DBUtils.dataSource = dataSource;
		DBUtils.jdbcTemplate = new JdbcTemplate(dataSource);

		System.out.println("datasource " + dataSource);
		try {
			DBUtils.cachedConnection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * static method can be accessed anywhere to get connection from dataSource which is used by hibernate, 
	 * if connection closed on run time, it will open new one .<p>
	 * <b>Remark:</b> it is not recommended to cach this connection anywhere , because it may close on run time
	 * @return Connection
	 */
	public static Connection getConnection() {
		try {
			if(cachedConnection.isClosed()){
				cachedConnection = dataSource.getConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cachedConnection;
	}
	
	/**
	 * @return statement from cached connection<p>
	 * <b>Remark:</b> caller have to close this statement and any opened resultset after usage, just for performance.
	 * @throws SQLException
	 */
	public static Statement createStatement() throws SQLException{
		return getConnection().createStatement();
	}

	/**
	 * @return another connection instead of cached connection , caller <b>have to close</b> this connection after usage, to optmize performance
	 */
	public static Connection getAnotherConnection(){
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	public static DataSource getDataSource() {
		return dataSource;
	}
	public static JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public static int executeCountStatement(String sql){
		int result = -1;
		try {
			Statement stmt = createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			rs.first();
			result = rs.getInt(1);
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void execute(String sql){
		try {
			Statement stmt = createStatement();
			log.debug("SQL Statement " + sql);
			stmt.execute(sql);

			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void executeTransaction(String sql) throws SQLException  {
		try
		{
		  getConnection().setAutoCommit(false);

		   //1 or more queries or updates
		  Statement stmt = createStatement();
		  log.debug("SQL Statement: " + sql);
		  stmt.execute(sql);
		  log.debug("executed");
		  getConnection().commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.debug("will rollback");
			getConnection().rollback();
		}
		finally
		{
			log.debug("will close connection");
			getConnection().close();
		}
	}
}
