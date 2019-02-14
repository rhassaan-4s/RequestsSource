package com._4s_.common.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com._4s_.common.dao.CommonDAO;
import com._4s_.common.model.LastSequence;
import com._4s_.common.util.DBUtils;

public class SequenceManagerImpl extends BaseManagerImpl implements
		SequenceManager {
	private CommonDAO commonDAO;

	
	public CommonDAO getCommonDAO() {
		return commonDAO;
	}
	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
	synchronized public Long getSequenceByClassName (String className){
		Long newSequence = null;
		
		try {
			Statement statement =  DBUtils.createStatement();
			ResultSet rs = statement.executeQuery("select * from common_last_sequence where  className='"+className+"'");
//			if(rs.first()){
			if(rs.next()) {
				newSequence = new Long(rs.getInt("classSequence") + 1);
				
				DBUtils.execute("BEGIN transaction");
				DBUtils.execute("update common_last_sequence set classSequence="+newSequence +
						" where id="+rs.getInt("id"));
				DBUtils.execute("commit transaction");
			}
			rs.close();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newSequence;
	}
	
	
	public Long getSequenceByClassName_old (String className){
		//try{
		log.debug("....................Date .................."+System.currentTimeMillis());
		log.debug(" Starting getSequenceByTableName ");
		LastSequence lastSequence =  commonDAO.getSequenceByClassName(className);
		Long newSequence = null;
		if (lastSequence!=null) {
			newSequence = lastSequence.increment();
		} else {
			log.debug("...........................................");
			log.debug("No sequence found for table ["+className+"]");
			log.debug("...........................................");
		}
		
		log.debug(" Ending getSequenceByTableName ");
		log.debug("...........................................");
		log.debug("....................Date .................."+System.currentTimeMillis());
		return newSequence;
		//}
		//catch (ConcurrentModificationException concEx){
		//	return getSequenceByClassName (className);
		//}
	}
	
	public void saveSequence(LastSequence lastSequence){
		commonDAO.saveSequence(lastSequence);
	}
	

}
