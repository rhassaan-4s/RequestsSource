package com._4s_.common.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com._4s_.common.util.DBUtils;
import com._4s_.common.util.Page;
//import com._4s_.stores.model.DBConnection;
//import com._4s_.stores.model.Destination;
//import com._4s_.stores.model.StoreTransactionM;
//import com._4s_.stores.model.StoreTrnsDepBranch;
import com.sun.org.apache.bcel.internal.generic.DADD;
    
public class Queries extends CommonQueries{
	protected final Log log = LogFactory.getLog(getClass());
	
	private JdbcTemplate exjt;
	private DataSource dataSource;
	private Connection conn;
	public String sql1;
	public String where1;
	public String whereFirstParam1;
	public String whereSecondParam1;
	public String sql2;
	public String where2;
	public String where3;
	public String where4;
	public String whereFirstParam2;
	public String whereSecondParam2;  
	public String limit;
	public String [] splitParamString;
	
	public void setDataSource(DataSource dataSource) {
	    this.dataSource = dataSource;
	}
	
	

	public Map search(String code,String description,String table,String firstParam,String secondParam,String paramString,String match1,String match2,String level,int pageNumber,int pageSize,String branchId){
		sql1= null;
		where1= null;
		whereFirstParam1= null;
		whereSecondParam1= null;
		sql2= null;
		where2= null;
		where3= null;
		where4= null;
		whereFirstParam2= null;
		whereSecondParam2= null;  
		limit= null;
		
		
		
			log.error(">>>>>>>>>>>>>>> paramString "+paramString);
		if (paramString != null && paramString.length() > 0){
			log.error(" paramString != null");
			paramString = paramString.replaceAll("\\{sq\\}","'");
			log.error(">>>>>>>>>>>>>>> paramString "+paramString);
		}
		if(table != null && !table.equals("") && table.equals("store_palette_number,contract")){
			String[] tableList = table.split(",");
			String[] paramList = paramString.split(",");
			table = tableList[0];
			List listRows = new ArrayList();
			JdbcTemplate jt = new JdbcTemplate(DBUtils.getDataSource());
			StringBuilder sql = new StringBuilder();
			sql.append("select distinct(palette_capacity) from store_trns_room ");
			sql.append("where " + paramList[0]);
			listRows = getJdbcTemplate().queryForList(sql.toString());
			ListOrderedMap codeMap ;
			paramString="";
			
			for(int x=0;x<listRows.size();x++) {
				codeMap = (ListOrderedMap) listRows.get(x);
				if(x==0){
					paramString =paramString+ " palette_capacity = " + codeMap.get("palette_capacity") + paramList[1] ;
				}else{
					paramString = paramString+ " or palette_capacity = " + codeMap.get("palette_capacity") + paramList[1];
				}
			}
			
		}
		
		
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		sql1 = "SELECT * FROM (";
		if (table.equals("store_c_trns_m,store_trns_def ")) {
			sql1 = sql1 +
				"select ROWNUM rnum," + firstParam +" as id" ;
		}
		else {
			sql1 = sql1 +
				"select id as identity,ROWNUM rnum," + firstParam +" as id" ;
		}
		if(secondParam != null && !secondParam.equals("")){
			sql1 = sql1 + " , "+secondParam+ " as description ";
		}
		sql1 = sql1 +" from " +table;
		
		where1 =" where ";
		log.debug("where1 " + where1);
		if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1code not null");
			log.debug(">>>>>>>>>>>>>>>>>> match1 "+match1);
			if (match1 != null && match1.length() > 0){
				if(match1.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 0");
					whereFirstParam1 =  firstParam+ " like '" +code+"'";
				}
				if(match1.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 1");
					whereFirstParam1 =  firstParam+ " like '%" +code+"%'";
				}
				if(match1.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 2");
					whereFirstParam1 =  firstParam+ " like '" +code+"%'";
				}
				if(match1.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 3");
					whereFirstParam1 = firstParam+ " like '%" +code+"'";
				}
				if(match1.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 4");
					whereFirstParam1 =  firstParam+ " like '" +code+"'";
				}
			}
		}
		
	
		
		if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1description not null");
			log.debug(">>>>>>>>>>>>>>>>>> match2 "+match2);
			if (match2 !=null && match2.length() > 0){
				if(match2.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 0");
					whereSecondParam1 =  secondParam+ " like '" +description+"'";
				}
				if(match2.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 1");
					whereSecondParam1 = secondParam+ " like '%" +description+"%'";
				}
				if(match2.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 2");
					whereSecondParam1 = secondParam+ " like '" +description+"%'";
				}
				if(match2.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 3");
					whereSecondParam1 = secondParam+ " like '%" +description+"'";
				}
				if(match2.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 4");
					whereSecondParam1 = secondParam+ " like '" +description+"'";
				}
			}
		}
		log.debug("whereSecondParam1 " + whereSecondParam1);

		if (code != null && code.length() > 0 && description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> both not null");
			where1 = where1  + whereFirstParam1 +" and "+ whereSecondParam1;
		}
		else if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2code not null");
			where1 = where1  + whereFirstParam1;
		}
		else if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2description not null");
			where1 =  where1 + whereSecondParam1;
		}
		else {
			log.debug(">>>>>>>>>>>>>>>>>else no where");
			where1 = "";
		}
		log.error("where 1 "+where1);
		
		
		log.error(">>>>>>>>>>>>>>paramString != null "+(paramString != null));
		log.error(">>>>>>>>>>>>>>!where1.equals() "+(!where1.equals("")));
		log.error(">>>>>>>>>>>>>>where1.equals() "+(where1.equals("")));
		if (paramString != null && paramString.length() > 0 && !where1.equals("")){
			log.error(">>>>>>>>>>>>>>> if");
			where3 = " and "+paramString;
		}
		else if (paramString != null && paramString.length() > 0 && where1.equals("")){
			log.error(">>>>>>>>>>>>>>> else");
			where3 = " where "+paramString;
		}
		
		String[] tableList = table.split(",");
		
		if(tableList != null && tableList.length>1 && tableList[0].equals("store_item_data") || tableList[0].equals("store_agriculture_terms")){
			log.error("have not branch");
		}else if(table.equals("store_item_data")){
			log.error("have not branch");
		}else{
			if(branchId != null && !branchId.equals("")){
				if(table != null && !table.equals("") &&  table.equals("store_c_trns_m")){
					where3 = where3 +" and branch='"+branchId+"'";
				}else{
					where3 = where3 +" and (substr(code,0,"+branchId.length()+")='"+branchId+"')";
				}

			}
		}
		
		String levelString = "";
		
		if (level!=null && !level.isEmpty()) {
			levelString = " empCode in ("+level+")";
		}
		
		if (where1.isEmpty() && !levelString.isEmpty()) {
			where1 = " where " + levelString;
		} else if (!where1.isEmpty() && !levelString.isEmpty()) {
			where1 += " and " + levelString;
		}
		if (!where1.equals("")){
			log.error(">>>>>>>>>>>>>>> where1 != ''");
			if (where3 != null && !where3.equals("")){
				log.error(">>>>>>>>>>>>>>> where3 != null ");
				sql1 = sql1 + where1 + where3 +" and END_SERV IS NULL"+") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
			else{
				sql1 = sql1 + where1 +" and END_SERV IS NULL"+") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
		}
		else{
			if (where3 != null && !where3.equals("")){
				log.error(">>>>>>>>>>>>>>> where3 != null ");
				sql1 = sql1 + where3 +" and END_SERV IS NULL"+") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
			else{
				sql1 = sql1 +" WHERE END_SERV IS NULL"+") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
		}
		
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> sql1"+sql1);
		List records = getJdbcTemplate().queryForList(sql1);
		log.debug(">>>>>>>>>>>>>>>>>>>>records "+records);
		
		//////////////////////////////////////////////////////////////////////////////////////////////////
		
		sql2 = 
			"select count(*) "+
			" from " +table;
		
		where2 =" where ";
		
		if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1code not null");
			log.debug(">>>>>>>>>>>>>>>>>> match1 "+match1);
			if (match1 != null && match1.length() > 0){
				if(match1.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 0");
					whereFirstParam2 =  firstParam+ " like '" +code+"'";
				}
				if(match1.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 1");
					whereFirstParam2 =  firstParam+ " like '%" +code+"%'";
				}
				if(match1.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 2");
					whereFirstParam2 =  firstParam+ " like '" +code+"%'";
				}
				if(match1.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 3");
					whereFirstParam2 = firstParam+ " like '%" +code+"'";
				}
				if(match1.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 4");
					whereFirstParam2 =  firstParam+ " like '" +code+"'";
				}
			}
		}
		
	
		
		if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1description not null");
			log.debug(">>>>>>>>>>>>>>>>>> match2 "+match2);
			if (match2 !=null && match2.length() > 0){
				if(match2.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 0");
					whereSecondParam2 =  secondParam+ " like '" +description+"'";
				}
				if(match2.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 1");
					whereSecondParam2 = secondParam+ " like '%" +description+"%'";
				}
				if(match2.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 2");
					whereSecondParam2 = secondParam+ " like '" +description+"%'";
				}
				if(match2.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 3");
					whereSecondParam2 = secondParam+ " like '%" +description+"'";
				}
				if(match2.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 4");
					whereSecondParam2 = secondParam+ " like '" +description+"'";
				}
			}
		}
		

//String levelString = "";
//		
//		if (level!=null && !level.isEmpty()) {
//			levelString = " empCode in ("+level+")";
//		}
		
		if (code != null && code.length() > 0 && description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> both not null");
			where2 = where2  + whereFirstParam2 +" and "+ whereSecondParam2;
		}
		else if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2code not null");
			where2 = where2  + whereFirstParam2;
		}
		else if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2description not null");
			where2 =  where2 + whereSecondParam2;
		}
		else {
			log.debug(">>>>>>>>>>>>>>>>>else no where");
			where2 = "";
		}
		

		if (where2.isEmpty() && !levelString.isEmpty()) {
			where2 = " where " + levelString;
		} else if (!where2.isEmpty() && !levelString.isEmpty()) {
			where2 += " and " + levelString;
		}
		
		if (paramString != null && paramString.length() > 0 && !where2.equals("")){
			where4 = " and "+paramString;
		}
		else if (paramString != null && paramString.length() > 0 && where2.equals("")){
			where4 = " where "+paramString;
		}
		
		if(tableList != null && tableList.length>1 && tableList[0].equals("store_item_data") || tableList[0].equals("store_agriculture_terms")){
			log.error("have not branch");
		}else if(table.equals("store_item_data")){
			log.error("have not branch");
		}else{
		if(branchId != null && !branchId.equals("")){
			if(table != null && !table.equals("") &&  table.equals("store_c_trns_m")){
				where4 = where4 +" and branch='"+branchId+"'";
			}else{
				where4 = where4 +" and (substr(code,0,"+branchId.length()+")='"+branchId+"')";
			}
		}
		}
		if (!where2.equals("")){
			if (where4 != null && !where4.equals("")){
				sql2 = sql2 + where2 + where4+" and END_SERV IS NULL";
			}
			else{
				sql2 = sql2 + where2+" and END_SERV IS NULL";
			}
		}
		else{
			if (where4!= null && !where4.equals("")){
				log.error(">>>>>>>>>>>>>>> where4 != null ");
				sql2 = sql2 + where4+" and END_SERV IS NULL";
			}
			else{
				sql2 = sql2+" WHERE END_SERV IS NULL";
			}
		}
		//sql2=sql2+" and END_SERV IS NULL";
		log.debug(">>>>>>>>>>>>>>sql2 "+sql2);
		int count = getJdbcTemplate().queryForInt(sql2);
		log.debug(">>>>>>>>>>>>>>>>>>>>count "+count);
		Map map = new HashMap();
		if(table != null && !table.equals("") && (table.equals("store_c_trns_m") || table.equals("view_store_dep_trans"))){
			String transId = null;
			ListOrderedMap recordMap ;
			List transList = new ArrayList();
			
			for(int i=0;i<records.size();i++){
				recordMap = (ListOrderedMap) records.get(i);
				transId = recordMap.get("identity").toString();
				transList.add(transId);
			}
			map.put("transList",transList);
			map.put("results",records);
			map.put("listSize",count);
		
		}else{
			map.put("results",records);
			map.put("listSize",count);
		}
//		map.put("results",records);
//		map.put("listSize",count);
		
		Page page = new Page();
		
		return page.getPage(map,pageNumber,pageSize);
	}
	
	public List getAutoCompleteSuggestions(String value,String table,String firstParam,String paramString){
		log.debug("value "+value);
		log.debug("table "+table);
		log.debug("firstParam "+firstParam);
		log.debug("paramString "+paramString);
		log.debug(">>>>>>>>>>>>>>> paramString ("+paramString+")");
		//value = StringEscapeUtils.escapeSql(value);
		//value = StringEscapeUtils.escapeJava(value);
		value = value.replaceAll("'","");
		value = value.replaceAll("\\\\","");
		log.debug("value "+value);	
		sql1= null;
		where1= null;
		where2=null;
		if (paramString != null && paramString.length() > 0){
			log.debug(" paramString != null");
			paramString = paramString.replaceAll("\\{sq\\}","'");
			log.debug(">>>>>>>>>>>>>>> paramString ("+paramString+")");
		}
		
		if(table != null && !table.equals("") && table.equals("store_palette_number,contract")){
			String[] tableList = table.split(",");
			String[] paramList = paramString.split(",");
			table = tableList[0];
			List listRows = new ArrayList();
			JdbcTemplate jt = new JdbcTemplate(DBUtils.getDataSource());
			StringBuilder sql = new StringBuilder();
			sql.append("select distinct(palette_capacity) from store_trns_room ");
			sql.append("where " + paramList[0]);
			listRows = getJdbcTemplate().queryForList(sql.toString());
			ListOrderedMap codeMap ;
			paramString="";
			
			for(int x=0;x<listRows.size();x++) {
				codeMap = (ListOrderedMap) listRows.get(x);
				if(x==0){
					paramString =paramString+ " palette_capacity = " + codeMap.get("palette_capacity") + paramList[1];
				}else{
					paramString = paramString+ " or palette_capacity = " + codeMap.get("palette_capacity") + paramList[1];
				}
			}
			
		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>value "+value);
		log.debug(">>>>>>>>>>>>>>>>>>>table "+table);
		log.debug(">>>>>>>>>>>>>>>>>>>firstParam "+firstParam);
		log.debug(">>>>>>>>>>>>>>>>>>>paramString ("+paramString+")");
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		sql1 = 
			"select row_.*, rownum from ( select id as id ," + firstParam+ " as result "+
			" from " +table;
		
		where1=" where " +firstParam+ " like '" +value+"%'";
		
		
		
		if (paramString != null && paramString.length() >0){
			log.debug(">>>>>>>>>>>>>>>>>>>>>. paramString != null");
			where2 = " and "+paramString;
		}
		
		limit = " ) row_ where rownum <= 10";
		
		if (where2 != null && where2.length() > 0 && !where2.equals("")){
			log.debug(">>>>>>>>>>>>>>>>>>>>>. where2 != null");
			sql1 = sql1 + where1 + where2 + limit;
			
		}else{
			log.debug(">>>>>>>>>>>>>>>>>>>>>. where2 = null");
			sql1 = sql1 + where1 + limit;
		}
		
		log.debug("sql1 "+sql1);
		List records = getJdbcTemplate().queryForList(sql1);
		return records;
	}
	
	public List getExternalDestination(String value,String table,String firstParam,String secondParam,String paramString){
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder("select * from store_db_connection " 
			+" where is_active='1'");
		String [] arrParamString = paramString.split(",");
		log.debug("paramString...1"+paramString);
		log.debug("arrParamString...."+arrParamString.length);
		log.debug(" arrParamString[0]...."+ arrParamString[0]);
		if(arrParamString.length<2){
			if(table.equals("car_type")){
				table="dist_names";
				paramString="name_type = 'car_type'";
			}else if(table.equals("area")){
				table="dist_names";
				paramString="name_type = 'area'";
			}
			log.debug("paramString...1"+paramString);
		}else if(arrParamString.length==2 && arrParamString[0].equals("driver")){
			String agentId=arrParamString[1];
			
			paramString = "name_type='driver' and agent_code ='"+agentId+"'";
			table = "dist_names";
		}else if(arrParamString.length==2 && arrParamString[0].equals("cars")){
			String agentId=arrParamString[1];
			
			
			paramString = "name_type='cars' and agent_code ='"+agentId+"'";
			table = "dist_names";
		}
		
		list =  getJdbcTemplate().queryForList(sql.toString());
		
		ListOrderedMap dbConnection ;
		dbConnection = (ListOrderedMap) list.get(0);
		exjt =  new JdbcTemplate(createDataSource());
		
		log.debug("value "+value);
		log.debug("table "+table);
		log.debug("firstParam "+firstParam);
		log.debug("paramString "+paramString);
		log.debug(">>>>>>>>>>>>>>> paramString ("+paramString+")");
		//value = StringEscapeUtils.escapeSql(value);
		//value = StringEscapeUtils.escapeJava(value);
		value = value.replaceAll("'","");
		value = value.replaceAll("\\\\","");
		log.debug("value "+value);	
		sql1= null;
		where1= null;
		where2=null;
		if (paramString != null && paramString.length() > 0){
			log.debug(" paramString != null");
			paramString = paramString.replaceAll("\\{sq\\}","'");
			log.debug(">>>>>>>>>>>>>>> paramString ("+paramString+")");
		}
		log.debug(">>>>>>>>>>>>>>>>>>>value "+value);
		log.debug(">>>>>>>>>>>>>>>>>>>table "+table);
		log.debug(">>>>>>>>>>>>>>>>>>>firstParam "+firstParam);
		log.debug(">>>>>>>>>>>>>>>>>>>paramString ("+paramString+")");
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		
		 if(arrParamString.length==2 && arrParamString[0].equals("cars")){
			 sql1 = 
					"select row_.*, rownum from ( select "+secondParam+" as id ," + secondParam+ " as result "+
					" from " +table;
		 }else{
			 sql1 = 
					"select row_.*, rownum from ( select "+secondParam+" as id ," + firstParam+ " as result "+
					" from " +table;
		 }
		
		
		where1=" where " +firstParam+ " like '" +value+"%'";
		
		
		
		if (paramString != null && paramString.length() >0){
			log.debug(">>>>>>>>>>>>>>>>>>>>>. paramString != null");
			where2 = " and "+paramString;
		}
		
		limit = ") row_  where rownum <= 10";
		
		if (where2 != null && where2.length() > 0 && !where2.equals("")){
			log.debug(">>>>>>>>>>>>>>>>>>>>>. where2 != null");
			sql1 = sql1 + where1 + where2 + limit;
			
		}else{
			log.debug(">>>>>>>>>>>>>>>>>>>>>. where2 = null");
			sql1 = sql1 + where1 + limit;
		}
		
		log.debug("sql1 "+sql1);
		List records = exjt.queryForList(sql1);
		return records;
	}
	
	public List getExternalTrans(	String value,
									String table,
									String firstParam,
									String secondParam,
									String paramString	) {
		
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder("select * from store_db_connection " 
											+ " where is_active='1'");
		
		list =  getJdbcTemplate().queryForList(sql.toString());
		
		ListOrderedMap dbConnection ;
		dbConnection = (ListOrderedMap) list.get(0);
		exjt = new JdbcTemplate(createDataSource());

		value = value.replaceAll("'","");
		value = value.replaceAll("\\\\","");
		log.debug("<value>----- "+value);	
		
		sql1= null;
		where1= null;
		where2=null;
		
		log.debug("<paramString-bef>----- "+paramString);	
		if (paramString != null && paramString.length() > 0){
			paramString = paramString.replaceAll("\\{sq\\}","'");

		}
		log.debug("<paramString-aft>----- "+paramString);	
		
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		
		sql1 = "select row_.*, rownum from ( select " + secondParam +
			   " as id ," + firstParam + " as result from " + table;
		 
		where1=" where " + firstParam + " like '" + value + "%'";
		
		if (paramString != null && paramString.length() > 0) {
			where2 = " and " + paramString;
			
		}
		
		limit = ") row_  where rownum <= 10";
		
		if (where2 != null && where2.length() > 0 && !where2.equals("")){
			sql1 = sql1 + where1 + where2 + limit;
			
		} else {
			sql1 = sql1 + where1 + limit;
		}
		
		log.debug("<sql1>----- " + sql1);
		List records = exjt.queryForList(sql1);
		return records;
	}
	
	public List getAutoCompleteSuggestionsComplex(String value,String table,String firstParam,String paramString, String secondParam){
		log.debug("value "+value);
		log.debug("table "+table);
		log.debug("firstParam "+firstParam);
		log.debug("paramString "+paramString);
		log.debug(">>>>>>>>>>>>>>> paramString ("+paramString+")");
		//value = StringEscapeUtils.escapeSql(value);
		//value = StringEscapeUtils.escapeJava(value);
		value = value.replaceAll("'","");
		value = value.replaceAll("\\\\","");
		log.debug("value "+value);	
		sql1= null;
		where1= null;
		where2=null;
		if (paramString != null && paramString.length() > 0){
			log.debug(" paramString != null");
			paramString = paramString.replaceAll("\\{sq\\}","'");
			log.debug(">>>>>>>>>>>>>>> paramString ("+paramString+")");
		}
		log.debug(">>>>>>>>>>>>>>>>>>>value "+value);
		log.debug(">>>>>>>>>>>>>>>>>>>table "+table);
		log.debug(">>>>>>>>>>>>>>>>>>>firstParam "+firstParam);
		log.debug(">>>>>>>>>>>>>>>>>>>paramString ("+paramString+")");
		String[] paramArr = null;
		if(firstParam != null && !firstParam.equals("")){
			paramArr = firstParam.split(",");
		}
		
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		sql1 = 
			"select row_.*, rownum from ( select ";
		if(paramArr != null){
			for(int i = 0; i < paramArr.length; i++){
				sql1 = sql1 + "destination." + paramArr[i] + " as code" + (i+1) + ", ";
			}
		}
		sql1 = sql1 + "destination." + secondParam+ " as result "+
			" from " +table + ",groupf_details,groupf";
		
		where1=" where destination.groupf_detail_code = groupf_details.groupf_detail_code";
		where1+=" and groupf_details.group_type_code = groupf.group_type_code";
		where1+=" and groupf_details.code_form = groupf.code_form and groupf.is_destination = '1' and " +secondParam+ " like '" +value+"%'";
		
		
		
		if (paramString != null && paramString.length() >0){
			log.debug(">>>>>>>>>>>>>>>>>>>>>. paramString != null");
			where2 = " and "+paramString;
		}
		
		limit = " ) row_ where rownum <= 10";
		
		if (where2 != null && where2.length() > 0 && !where2.equals("")){
			log.debug(">>>>>>>>>>>>>>>>>>>>>. where2 != null");
			sql1 = sql1 + where1 + where2 + limit;
			
		}else{
			log.debug(">>>>>>>>>>>>>>>>>>>>>. where2 = null");
			sql1 = sql1 + where1 + limit;
		}
		
		log.debug("sql1 "+sql1);
		List records = getJdbcTemplate().queryForList(sql1);
		return records;
	}
	public List getAutoCompleteSuggestionsItemData(String value,String table,String firstParam,String paramString, String secondParam){
		log.debug("value "+value);
		log.debug("table "+table);
		log.debug("firstParam "+firstParam);
		log.debug("paramString "+paramString);
		log.debug(">>>>>>>>>>>>>>> paramString ("+paramString+")");
		//value = StringEscapeUtils.escapeSql(value);
		//value = StringEscapeUtils.escapeJava(value);
		value = value.replaceAll("'","");
		value = value.replaceAll("\\\\","");
		log.debug("value "+value);	
		sql1= null;
		where1= null;
		where2=null;
		if (paramString != null && paramString.length() > 0){
			log.debug(" paramString != null");
			paramString = paramString.replaceAll("\\{sq\\}","'");
			log.debug(">>>>>>>>>>>>>>> paramString ("+paramString+")");
		}
		log.debug(">>>>>>>>>>>>>>>>>>>value "+value);
		log.debug(">>>>>>>>>>>>>>>>>>>table "+table);
		log.debug(">>>>>>>>>>>>>>>>>>>firstParam "+firstParam);
		log.debug(">>>>>>>>>>>>>>>>>>>paramString ("+paramString+")");
		String[] paramArr = null;
		if(firstParam != null && !firstParam.equals("")){
			paramArr = firstParam.split(",");
		}
		
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		sql1 = 
			"select row_.*, rownum from ( select ";
		if(paramArr != null){
			for(int i = 0; i < paramArr.length; i++){
				sql1 = sql1 + "store_item_data." + paramArr[i] + " as code" + (i+1) + ", ";
			}
		}
		sql1 = sql1 + "store_item_data." + secondParam+ " as result "+
			" from " +table + ",groupf_details";
		
		where1=" where store_item_data.groupf_detail_code = groupf_details.groupf_detail_code";
		
		
		
		if (paramString != null && paramString.length() >0){
			log.debug(">>>>>>>>>>>>>>>>>>>>>. paramString != null");
			where2 = " and "+paramString+" and "+secondParam+ " like '" +value+"%'";
		}
		
		limit = " ) row_ where rownum <= 10";
		
		if (where2 != null && where2.length() > 0 && !where2.equals("")){
			log.debug(">>>>>>>>>>>>>>>>>>>>>. where2 != null");
			sql1 = sql1 + where1 + where2 + limit;
			
		}else{
			log.debug(">>>>>>>>>>>>>>>>>>>>>. where2 = null");
			sql1 = sql1 + where1 + limit;
		}
		
		log.debug("sql1 "+sql1);
		List records = getJdbcTemplate().queryForList(sql1);
		return records;
	}
	
	public List getAutoCompleteSuggestionsWithoutId(String value,String table,String firstParam,String paramString){
		log.debug("value "+value);
		log.debug("table "+table);
		log.debug("firstParam "+firstParam);
		log.debug("paramString "+paramString);
		log.debug(">>>>>>>>>>>>>>> paramString ("+paramString+")");
		//value = StringEscapeUtils.escapeSql(value);
		//value = StringEscapeUtils.escapeJava(value);
		value = value.replaceAll("'","");
		value = value.replaceAll("\\\\","");
		log.debug("value "+value);	
		sql1= null;
		where1= null;
		where2=null;
		if (paramString != null && paramString.length() > 0){
			log.debug(" paramString != null");
			paramString = paramString.replaceAll("\\{sq\\}","'");
			log.debug(">>>>>>>>>>>>>>> paramString ("+paramString+")");
		}
		log.debug(">>>>>>>>>>>>>>>>>>>value "+value);
		log.debug(">>>>>>>>>>>>>>>>>>>table "+table);
		log.debug(">>>>>>>>>>>>>>>>>>>firstParam "+firstParam);
		log.debug(">>>>>>>>>>>>>>>>>>>paramString ("+paramString+")");
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		sql1 = 
			"select row_.*, rownum from ( select " + firstParam+ " as result "+
			" from " +table;
		
		where1=" where " +firstParam+ " like '" +value+"%'";
		
		
		
		if (paramString != null && paramString.length() >0){
			log.debug(">>>>>>>>>>>>>>>>>>>>>. paramString != null");
			where2 = " and "+paramString;
		}
		
		limit = " ) row_ where rownum <= 10";
		
		if (where2 != null && where2.length() > 0 && !where2.equals("")){
			log.debug(">>>>>>>>>>>>>>>>>>>>>. where2 != null");
			sql1 = sql1 + where1 + where2 + limit;
			
		}else{
			log.debug(">>>>>>>>>>>>>>>>>>>>>. where2 = null");
			sql1 = sql1 + where1 + limit;
		}
		
		log.debug("sql1 "+sql1);
		List records = getJdbcTemplate().queryForList(sql1);
		return records;
	}
	
	public List getObjectId (String table,String firstParam,String value,String paramString){
		log.debug(">>>>>>>>>>>>>>> paramString "+paramString);
		if (paramString != null && paramString.length() > 0){
			log.debug(" paramString != null");
			paramString = paramString.replaceAll("\\{sq\\}","'");
			log.debug(">>>>>>>>>>>>>>> paramString "+paramString);
		}
		log.debug(">>>>>>>>>>>>> starting getObjectId");
		log.debug(">>>>>>>>>>>>>>>> table "+table);
		log.debug(">>>>>>>>>>>>>>>> firstParam "+firstParam);
		log.debug(">>>>>>>>>>>>>>>> value "+value);
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
	
		//String sql = "select id as id from "+table+" where "+firstParam+" = '"+value+"'";
//		value = StringEscapeUtils.escapeSql(value);
//		value = StringEscapeUtils.escapeJava(value);
		value = value.replaceAll("'","");
		value = value.replaceAll("\\\\","");
		log.debug("value "+value);	
		String sql = "select * from "+table+" where "+firstParam+" = '"+value+"'";
		log.debug(">>>>>>>>>>>>>>>>>paramString "+paramString);
		if (paramString != null && !paramString.equals("") && !paramString.equals("null")){
			sql = sql + " and " +paramString;
		}
		log.debug(">>>>>>>>>>>>>>>>> sql "+sql);
		List result = getJdbcTemplate().queryForList(sql);
		log.debug(">>>>>>>>>>>>>>>>>result "+result);
		if (result != null && result.size() > 0 ){
			log.debug(">>>>>>>>>> id "+result);
			return result;
		}
		else{
			log.debug(">>>>>>>>>>>> null");
			return null;
		}
	}
	
	public List getAutoCompleteSuggestionsByBranch(String value,String table,String firstParam,String paramString,String branchId){
		log.debug("value "+value);
		log.debug("table "+table);
		log.debug("firstParam "+firstParam);
		log.debug("paramString "+paramString);
		log.debug(">>>>>>>>>>>>>>> paramString ("+paramString+")");
		
		
		/// Get length of Branch Code of Store /////////
		int length = 0;
		boolean storeTable = false;
		///////// Check for Store or Cooling Room or DEPARTMENT/////
		StringBuffer sqlStore = new StringBuffer("SELECT * FROM STORE_GROUPF_DETAILS where (DB ='STORE_STORES') AND "+paramString);
		try {
			log.debug("sqlStore1.... "+sqlStore);
			Statement statement = DBUtils.createStatement();
			ResultSet rs = statement.getResultSet();
			statement.execute(sqlStore.toString());
			log.debug("sqlStore1.... "+sqlStore);
			rs = statement.getResultSet();
			if (rs.next()){
				log.debug("true...");
				storeTable = true;
			}else{
				log.debug("false...");
				storeTable = false;
			}
			
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//// End Method /////////
		
///////// Check for DEPARTMENT /////
//		StringBuffer sqlDepart = new StringBuffer("SELECT * FROM STORE_GROUPF_DETAILS where DB ='STORE_DEPARTMENT' AND "+paramString);
//		try {
//			log.debug("sqlStore1.... "+sqlStore);
//			Statement statement = DBUtils.createStatement();
//			ResultSet rs = statement.getResultSet();
//			statement.execute(sqlDepart.toString());
//			log.debug("sqlStore1.... "+sqlDepart);
//			rs = statement.getResultSet();
//			if (rs.next()){
//				log.debug("true...");
//				storeTable = true;
//			}else{
//				log.debug("false...");
//				storeTable = false;
//			}
//			
//			rs.close();
//			statement.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		//// End Method /////////
		
		if (storeTable){
		StringBuffer sql = new StringBuffer("SELECT LENGTH FROM STORE_GROUPF_DETAILS where DB ='BRANCH' AND "+paramString);
		try {
			Statement statement = DBUtils.createStatement();
			ResultSet rs = statement.getResultSet();
			statement.execute(sql.toString());
			rs = statement.getResultSet();
			while (rs.next()) {
				length = rs.getInt("LENGTH");
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
		log.debug("length...... "+length);
		log.debug("storeTable...... "+storeTable);
		//////// End Method for Length /////////////
		
		//value = StringEscapeUtils.escapeSql(value);
		//value = StringEscapeUtils.escapeJava(value);
		value = value.replaceAll("'","");
		value = value.replaceAll("\\\\","");
		log.debug("value "+value);	
		sql1= null;
		where1= null;
		where2=null;
		if (paramString != null && paramString.length() > 0){
			log.debug(" paramString != null");
			paramString = paramString.replaceAll("\\{sq\\}","'");
			log.debug(">>>>>>>>>>>>>>> paramString ("+paramString+")");
		}
		log.debug(">>>>>>>>>>>>>>>>>>>value "+value);
		log.debug(">>>>>>>>>>>>>>>>>>>table "+table);
		log.debug(">>>>>>>>>>>>>>>>>>>firstParam "+firstParam);
		log.debug(">>>>>>>>>>>>>>>>>>>paramString ("+paramString+")");
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		sql1 = 
			"select row_.*, rownum from ( select id as id ," + firstParam+ " as result , code "+
			" from " +table;
		if (storeTable){
			where1=" where (substr(code,0,"+length+")='"+branchId+"') and " +firstParam+ " like '" +value+"%'";
		} else {
			where1=" where " +firstParam+ " like '" +value+"%'";
		}
		
		
		if (paramString != null && paramString.length() >0){
			log.debug(">>>>>>>>>>>>>>>>>>>>>. paramString != null");
			where2 = " and "+paramString;
		}
		
		limit = " ) row_ where rownum <= 10";
		
		if (where2 != null && where2.length() > 0 && !where2.equals("")){
			log.debug(">>>>>>>>>>>>>>>>>>>>>. where2 != null");
			sql1 = sql1 + where1 + where2 + limit;
			
		}else{
			log.debug(">>>>>>>>>>>>>>>>>>>>>. where2 = null");
			sql1 = sql1 + where1 + limit;
		}
		
		log.debug("sql1 "+sql1);
		List records = new ArrayList();
		if (paramString != null && paramString.length() >0){
		records = getJdbcTemplate().queryForList(sql1);
		}
		return records;
	}
	
	public Map searchByBranch(String code,String description,String table,String firstParam,String secondParam,String paramString,String match1,String match2,int pageNumber,int pageSize){
		sql1= null;
		where1= null;
		whereFirstParam1= null;
		whereSecondParam1= null;
		sql2= null;
		where2= null;
		where3= null;
		where4= null;
		whereFirstParam2= null;
		whereSecondParam2= null;  
		limit= null;
		
		/// Filter For Store or Cooling Room By Branch Code  //////////
		int length = 0;
		boolean storeTable = false;
		splitParamString =  paramString.split("@@");
//		log.error(">>>>>>>>>>>splitParamString...  "+splitParamString.length);
//		log.error(">>>>0..."+splitParamString[0].length()+".........1...."+splitParamString[1].length());
		String branchId =  splitParamString[1];
		if(splitParamString.length >1 && splitParamString[0].length()>0 ){
			log.debug("true  ");
			/// Get length of Branch Code of Store /////////
			
			///////// Check for Store or Cooling Room /////
			StringBuffer sqlStore = new StringBuffer("SELECT * FROM STORE_GROUPF_DETAILS where (DB ='STORE_STORES') AND "+splitParamString[0]);
			try {
				Statement statement = DBUtils.createStatement();
				ResultSet rs = statement.getResultSet();
				statement.execute(sqlStore.toString());
				log.debug("sqlStore1.... "+sqlStore);
				rs = statement.getResultSet();
				if (rs.next()){
					log.debug("true...");
					storeTable = true;
				}else{
					log.debug("false...");
					storeTable = false;
				}
				
				rs.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//// End Method /////////
			
	///////// Check for Department /////
//			StringBuffer sqlDepart = new StringBuffer("SELECT * FROM STORE_GROUPF_DETAILS where DB ='STORE_DEPARTMENT' AND "+splitParamString[0]);
//			try {
//				Statement statement = DBUtils.createStatement();
//				ResultSet rs = statement.getResultSet();
//				statement.execute(sqlDepart.toString());
//				log.debug("sqlStore1.... "+sqlDepart);
//				rs = statement.getResultSet();
//				if (rs.next()){
//					log.debug("true...");
//					storeTable = true;
//				}else{
//					log.debug("false...");
//					storeTable = false;
//				}
//				
//				rs.close();
//				statement.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			
			//// End Method /////////
			
			if (storeTable){
			StringBuffer sql = new StringBuffer("SELECT LENGTH FROM STORE_GROUPF_DETAILS where DB ='BRANCH' AND "+splitParamString[0]);
			try {
				Statement statement = DBUtils.createStatement();
				ResultSet rs = statement.getResultSet();
				statement.execute(sql.toString());
				rs = statement.getResultSet();
				while (rs.next()) {
					length = rs.getInt("LENGTH");
				}
				rs.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
			log.debug("length...... "+length);
			log.debug("storeTable...... "+storeTable);
			//////// End Method for Length /////////////
		}else{
			log.debug("false  ");
		}
		//////// End Method /////////
			log.error(">>>>>>>>>>>>>>> paramString "+paramString);
		if (paramString != null && paramString.length() > 0){
			log.error(" paramString != null");
			paramString = paramString.replaceAll("\\{sq\\}","'");
			log.error(">>>>>>>>>>>>>>> paramString "+paramString);
		}
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		sql1 = "SELECT * FROM (";
		if (table.equals("store_c_trns_m,store_trns_def ")) {
			sql1 = sql1 +
				"select ROWNUM rnum," + firstParam +" as id" ;
		}
		else {
			sql1 = sql1 +
				"select id as identity,ROWNUM rnum," + firstParam +" as id" ;
		}
		if(secondParam != null && !secondParam.equals("")){
			sql1 = sql1 + " , "+secondParam+ " as description ";
		}
		sql1 = sql1 +" from " +table;
		
		where1 =" where ";
		log.debug("where1 " + where1);
		if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1code not null");
			log.debug(">>>>>>>>>>>>>>>>>> match1 "+match1);
			if (match1 != null && match1.length() > 0){
				if(match1.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 0");
					whereFirstParam1 =  firstParam+ " like '" +code+"'";
				}
				if(match1.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 1");
					whereFirstParam1 =  firstParam+ " like '%" +code+"%'";
				}
				if(match1.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 2");
					whereFirstParam1 =  firstParam+ " like '" +code+"%'";
				}
				if(match1.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 3");
					whereFirstParam1 = firstParam+ " like '%" +code+"'";
				}
				if(match1.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 4");
					whereFirstParam1 =  firstParam+ " like '" +code+"'";
				}
			}
		}
		
	
		
		if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1description not null");
			log.debug(">>>>>>>>>>>>>>>>>> match2 "+match2);
			if (match2 !=null && match2.length() > 0){
				if(match2.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 0");
					whereSecondParam1 =  secondParam+ " like '" +description+"'";
				}
				if(match2.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 1");
					whereSecondParam1 = secondParam+ " like '%" +description+"%'";
				}
				if(match2.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 2");
					whereSecondParam1 = secondParam+ " like '" +description+"%'";
				}
				if(match2.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 3");
					whereSecondParam1 = secondParam+ " like '%" +description+"'";
				}
				if(match2.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 4");
					whereSecondParam1 = secondParam+ " like '" +description+"'";
				}
			}
		}
		log.debug("whereSecondParam1 " + whereSecondParam1);

		if (code != null && code.length() > 0 && description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> both not null");
			where1 = where1  + whereFirstParam1 +" and "+ whereSecondParam1;
		}
		else if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2code not null");
			where1 = where1  + whereFirstParam1;
		}
		else if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2description not null");
			where1 =  where1 + whereSecondParam1;
		}
		else {
			log.debug(">>>>>>>>>>>>>>>>>else no where");
			where1 = "";
		}
		log.error("where 1 "+where1);
		
		log.error(">>>>>>>>>>>>>>branchId--- "+branchId);
		log.error(">>>>>>>>>>>>>>paramString != null "+(paramString != null));
		log.error(">>>>>>>>>>>>>>!where1.equals() "+(!where1.equals("")));
		log.error(">>>>>>>>>>>>>>where1.equals() "+(where1.equals("")));
		if (splitParamString != null && splitParamString.length > 1 && !where1.equals("") && splitParamString[0].length()>0){
			log.error(">>>>>>>>>>>>>>> if");
			if(storeTable){
			where3 = " and (substr(code,0,"+length+")='"+branchId+"') and "+splitParamString[0];
			}else{
			where3 = " and "+splitParamString[0];
			}
		}
		else if (splitParamString != null && splitParamString.length > 1 && where1.equals("") && splitParamString[0].length()>0){
			log.error(">>>>>>>>>>>>>>> else");
			if(storeTable){
			where3 = " where (substr(code,0,"+length+")='"+branchId+"') and "+splitParamString[0];
			}else{
				where3 = " where "+splitParamString[0];	
			}
		}
		
		if (!where1.equals("")){
			log.error(">>>>>>>>>>>>>>> where1 != ''");
			if (where3 != null && !where3.equals("")){
				log.error(">>>>>>>>>>>>>>> where3 != null ");
				sql1 = sql1 + where1 + where3 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
			else{
				sql1 = sql1 + where1 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
		}
		else{
			if (where3 != null && !where3.equals("")){
				log.error(">>>>>>>>>>>>>>> where3 != null ");
				sql1 = sql1 + where3 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
			else{
				sql1 = sql1 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> sql1"+sql1);
		List records = new ArrayList();
		if (splitParamString[0].length()>0){
		records = getJdbcTemplate().queryForList(sql1);
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>records "+records);
		
		//////////////////////////////////////////////////////////////////////////////////////////////////
		
		sql2 = 
			"select count(*) "+
			" from " +table;
		
		where2 =" where ";
		
		if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1code not null");
			log.debug(">>>>>>>>>>>>>>>>>> match1 "+match1);
			if (match1 != null && match1.length() > 0){
				if(match1.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 0");
					whereFirstParam2 =  firstParam+ " like '" +code+"'";
				}
				if(match1.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 1");
					whereFirstParam2 =  firstParam+ " like '%" +code+"%'";
				}
				if(match1.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 2");
					whereFirstParam2 =  firstParam+ " like '" +code+"%'";
				}
				if(match1.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 3");
					whereFirstParam2 = firstParam+ " like '%" +code+"'";
				}
				if(match1.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 4");
					whereFirstParam2 =  firstParam+ " like '" +code+"'";
				}
			}
		}
		
	
		
		if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1description not null");
			log.debug(">>>>>>>>>>>>>>>>>> match2 "+match2);
			if (match2 !=null && match2.length() > 0){
				if(match2.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 0");
					whereSecondParam2 =  secondParam+ " like '" +description+"'";
				}
				if(match2.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 1");
					whereSecondParam2 = secondParam+ " like '%" +description+"%'";
				}
				if(match2.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 2");
					whereSecondParam2 = secondParam+ " like '" +description+"%'";
				}
				if(match2.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 3");
					whereSecondParam2 = secondParam+ " like '%" +description+"'";
				}
				if(match2.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 4");
					whereSecondParam2 = secondParam+ " like '" +description+"'";
				}
			}
		}
		

		if (code != null && code.length() > 0 && description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> both not null");
			where2 = where2  + whereFirstParam2 +" and "+ whereSecondParam2;
		}
		else if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2code not null");
			where2 = where2  + whereFirstParam2;
		}
		else if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2description not null");
			where2 =  where2 + whereSecondParam2;
		}
		else {
			log.debug(">>>>>>>>>>>>>>>>>else no where");
			where2 = "";
		}
		
		if (splitParamString != null && splitParamString.length > 1 && !where2.equals("") && splitParamString[0].length()>0){
			if(storeTable){
			where4 = " and (substr(code,0,"+length+")='"+branchId+"') and "+splitParamString[0];
			}else{
			where4 = " and "+splitParamString[0];	
			}
		}
		else if (splitParamString != null && splitParamString.length > 1 && where2.equals("") && splitParamString[0].length()>0){
			if(storeTable){
			where4 = " where (substr(code,0,"+length+")='"+branchId+"') and "+splitParamString[0];
			}else{
			where4 = " where "+splitParamString[0];	
			}
		}
		
		if (!where2.equals("")){
			if (where4 != null && !where4.equals("")){
				sql2 = sql2 + where2 + where4;
			}
			else{
				sql2 = sql2 + where2;
			}
		}
		else{
			if (where4!= null && !where4.equals("")){
				log.error(">>>>>>>>>>>>>>> where4 != null ");
				sql2 = sql2 + where4;
			}
			else{
				sql2 = sql2;
			}
		}
		log.debug(">>>>>>>>>>>>>>sql2 "+sql2);
		int count = 0;
		if (splitParamString[0].length()>0){
		 count = getJdbcTemplate().queryForInt(sql2);
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>count "+count);
		Map map = new HashMap();
		map.put("results",records);
		map.put("listSize",count);
		
		Page page = new Page();
		
		return page.getPage(map,pageNumber,pageSize);
	}

	public List getAutoCompleteSuggestionsByTypeAndBranch(String value,String table,String firstParam,String paramString,String groupFId,String branchId){
		log.debug("value "+value);
		log.debug("table "+table);
		log.debug("firstParam "+firstParam);
		log.debug("paramString "+paramString);
		log.debug(">>>>>>>>>>>>>>> paramString ("+paramString+")");
		log.debug("type...... "+groupFId);
		log.debug("branch "+branchId);
		
		/// Get length of Branch Code of Store /////////
		int length = 0;
		boolean storeTable = true;
		
		if(groupFId != null && !groupFId.equals("") && groupFId.length()>0){
		if (storeTable){
		StringBuffer sql = new StringBuffer("SELECT LENGTH FROM STORE_GROUPF_DETAILS where DB ='BRANCH' AND GROUPF_ID = "+groupFId);
		try {
			Statement statement = DBUtils.createStatement();
			ResultSet rs = statement.getResultSet();
			statement.execute(sql.toString());
			rs = statement.getResultSet();
			while (rs.next()) {
				length = rs.getInt("LENGTH");
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
		}else{
			length = branchId.length();
		}
		log.debug("length...... "+length);
		log.debug("storeTable...... "+storeTable);
		//////// End Method for Length /////////////
		
		//value = StringEscapeUtils.escapeSql(value);
		//value = StringEscapeUtils.escapeJava(value);
		value = value.replaceAll("'","");
		value = value.replaceAll("\\\\","");
		log.debug("value "+value);	
		sql1= null;
		where1= null;
		where2=null;
		if (paramString != null && paramString.length() > 0){
			log.debug(" paramString != null");
			paramString = paramString.replaceAll("\\{sq\\}","'");
			log.debug(">>>>>>>>>>>>>>> paramString ("+paramString+")");
		}
		log.debug(">>>>>>>>>>>>>>>>>>>value "+value);
		log.debug(">>>>>>>>>>>>>>>>>>>table "+table);
		log.debug(">>>>>>>>>>>>>>>>>>>firstParam "+firstParam);
		log.debug(">>>>>>>>>>>>>>>>>>>paramString ("+paramString+")");
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		sql1 = 
			"select row_.*, rownum from ( select id as id ," + firstParam+ " as result , code "+
			" from " +table;
		if (storeTable){
				if(groupFId != null && !groupFId.equals("") && groupFId.length()>0){
					where1=" where groupf_id = "+groupFId+"  and (substr(code,0,"+length+")='"+branchId+"') and " +firstParam+ " like '" +value+"%'";
				}else{
					where1=" where (substr(code,0,"+length+")='"+branchId+"') and " +firstParam+ " like '" +value+"%'";
				}
			} else {
			where1=" where " +firstParam+ " like '" +value+"%'";
		}
		
		
		if (paramString != null && paramString.length() >0){
			log.debug(">>>>>>>>>>>>>>>>>>>>>. paramString != null");
			where2 = " and "+paramString;
		}
		
		limit = " ) row_ where rownum <= 10";
		
		if (where2 != null && where2.length() > 0 && !where2.equals("")){
			log.debug(">>>>>>>>>>>>>>>>>>>>>. where2 != null");
			sql1 = sql1 + where1 + where2 + limit;
			
		}else{
			log.debug(">>>>>>>>>>>>>>>>>>>>>. where2 = null");
			sql1 = sql1 + where1 + limit;
		}
		
		log.debug("sql1 "+sql1);
		List records = new ArrayList();
		if (paramString != null && paramString.length() >0){
		records = getJdbcTemplate().queryForList(sql1);
		}
		return records;
	}
	
	public Map searchByTypeAndBranch(String code,String description,String table,String firstParam,String secondParam,String paramString,String match1,String match2,int pageNumber,int pageSize){
		sql1= null;
		where1= null;
		whereFirstParam1= null;
		whereSecondParam1= null;
		sql2= null;
		where2= null;
		where3= null;
		where4= null;
		whereFirstParam2= null;
		whereSecondParam2= null;  
		limit= null;
		
		/// Filter For Store or Cooling Room By Branch Code  //////////
		int length = 0;
		boolean storeTable = true;
		splitParamString =  paramString.split("@@");
//		log.error(">>>>>>>>>>>splitParamString...  "+splitParamString.length);
//		log.error(">>>>0..."+splitParamString[0].length()+".........1...."+splitParamString[1].length());
		String branchId =  splitParamString[2];
		if(splitParamString.length >1 && splitParamString[0].length()>0 ){
			log.debug("true  ");
			/// Get length of Branch Code of Store /////////
			if(splitParamString[1].length()>0){
			
			
			StringBuffer sql = new StringBuffer("SELECT LENGTH FROM STORE_GROUPF_DETAILS where DB ='BRANCH' AND GROUPF_ID = "+splitParamString[1]);
			log.debug("sql1...... "+sql);
			try {
				Statement statement = DBUtils.createStatement();
				ResultSet rs = statement.getResultSet();
				statement.execute(sql.toString());
				rs = statement.getResultSet();
				while (rs.next()) {
					length = rs.getInt("LENGTH");
				}
				rs.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			} else{
				
				length = branchId.length();
			}
			
			log.debug("length...... "+length);
			log.debug("storeTable...... "+storeTable);
			//////// End Method for Length /////////////
		}else{
			log.debug("false  ");
		}
		//////// End Method /////////
			log.error(">>>>>>>>>>>>>>> paramString "+paramString);
		if (paramString != null && paramString.length() > 0){
			log.error(" paramString != null");
			paramString = paramString.replaceAll("\\{sq\\}","'");
			log.error(">>>>>>>>>>>>>>> paramString "+paramString);
		}
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		sql1 = "SELECT * FROM (";
		if (table.equals("store_c_trns_m,store_trns_def ")) {
			sql1 = sql1 +
				"select ROWNUM rnum," + firstParam +" as id" ;
		}
		else {
			sql1 = sql1 +
				"select id as identity,ROWNUM rnum," + firstParam +" as id" ;
		}
		if(secondParam != null && !secondParam.equals("")){
			sql1 = sql1 + " , "+secondParam+ " as description ";
		}
		sql1 = sql1 +" from " +table;
		
		where1 =" where ";
		log.debug("where1 " + where1);
		if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1code not null");
			log.debug(">>>>>>>>>>>>>>>>>> match1 "+match1);
			if (match1 != null && match1.length() > 0){
				if(match1.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 0");
					whereFirstParam1 =  firstParam+ " like '" +code+"'";
				}
				if(match1.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 1");
					whereFirstParam1 =  firstParam+ " like '%" +code+"%'";
				}
				if(match1.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 2");
					whereFirstParam1 =  firstParam+ " like '" +code+"%'";
				}
				if(match1.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 3");
					whereFirstParam1 = firstParam+ " like '%" +code+"'";
				}
				if(match1.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 4");
					whereFirstParam1 =  firstParam+ " like '" +code+"'";
				}
			}
		}
		
	
		
		if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1description not null");
			log.debug(">>>>>>>>>>>>>>>>>> match2 "+match2);
			if (match2 !=null && match2.length() > 0){
				if(match2.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 0");
					whereSecondParam1 =  secondParam+ " like '" +description+"'";
				}
				if(match2.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 1");
					whereSecondParam1 = secondParam+ " like '%" +description+"%'";
				}
				if(match2.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 2");
					whereSecondParam1 = secondParam+ " like '" +description+"%'";
				}
				if(match2.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 3");
					whereSecondParam1 = secondParam+ " like '%" +description+"'";
				}
				if(match2.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 4");
					whereSecondParam1 = secondParam+ " like '" +description+"'";
				}
			}
		}
		log.debug("whereSecondParam1 " + whereSecondParam1);

		if (code != null && code.length() > 0 && description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> both not null");
			where1 = where1  + whereFirstParam1 +" and "+ whereSecondParam1;
		}
		else if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2code not null");
			where1 = where1  + whereFirstParam1;
		}
		else if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2description not null");
			where1 =  where1 + whereSecondParam1;
		}
		else {
			log.debug(">>>>>>>>>>>>>>>>>else no where");
			where1 = "";
		}
		log.error("where 1 "+where1);
		
		
		log.error(">>>>>>>>>>>>>>paramString != null "+(paramString != null));
		log.error(">>>>>>>>>>>>>>!where1.equals() "+(!where1.equals("")));
		log.error(">>>>>>>>>>>>>>where1.equals() "+(where1.equals("")));
		if (splitParamString != null && splitParamString.length > 1 && !where1.equals("") && splitParamString[0].length()>0 ){
			log.error(">>>>>>>>>>>>>>> if");
			if(storeTable){
					if(splitParamString[1].length()>0){
						where3 = " and groupf_id = "+splitParamString[1]+" and (substr(code,0,"+length+")='"+branchId+"') and "+splitParamString[0];
					}else{
						where3 = " and  (substr(code,0,"+length+")='"+branchId+"') and "+splitParamString[0];
					}
				}else{
			where3 = " and "+splitParamString[0];
			}
		}
		else if (splitParamString != null && splitParamString.length > 1 && where1.equals("") && splitParamString[0].length()>0){
			log.error(">>>>>>>>>>>>>>> else");
			if(storeTable){
				if(splitParamString[1].length()>0){
					where3 = " where groupf_id = "+splitParamString[1]+" and (substr(code,0,"+length+")='"+branchId+"') and "+splitParamString[0];
				}else{
					where3 = " where (substr(code,0,"+length+")='"+branchId+"') and "+splitParamString[0];
				}
			}else{
				where3 = " where "+splitParamString[0];	
			}
		}
		
		if (!where1.equals("")){
			log.error(">>>>>>>>>>>>>>> where1 != ''");
			if (where3 != null && !where3.equals("")){
				log.error(">>>>>>>>>>>>>>> where3 != null ");
				sql1 = sql1 + where1 + where3 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
			else{
				sql1 = sql1 + where1 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
		}
		else{
			if (where3 != null && !where3.equals("")){
				log.error(">>>>>>>>>>>>>>> where3 != null ");
				sql1 = sql1 + where3 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
			else{
				sql1 = sql1 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> sql1"+sql1);
		List records = new ArrayList();
		if (splitParamString[0].length()>0){
		records = getJdbcTemplate().queryForList(sql1);
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>records "+records);
		
		//////////////////////////////////////////////////////////////////////////////////////////////////
		
		sql2 = 
			"select count(*) "+
			" from " +table;
		
		where2 =" where ";
		
		if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1code not null");
			log.debug(">>>>>>>>>>>>>>>>>> match1 "+match1);
			if (match1 != null && match1.length() > 0){
				if(match1.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 0");
					whereFirstParam2 =  firstParam+ " like '" +code+"'";
				}
				if(match1.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 1");
					whereFirstParam2 =  firstParam+ " like '%" +code+"%'";
				}
				if(match1.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 2");
					whereFirstParam2 =  firstParam+ " like '" +code+"%'";
				}
				if(match1.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 3");
					whereFirstParam2 = firstParam+ " like '%" +code+"'";
				}
				if(match1.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 4");
					whereFirstParam2 =  firstParam+ " like '" +code+"'";
				}
			}
		}
		
	
		
		if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1description not null");
			log.debug(">>>>>>>>>>>>>>>>>> match2 "+match2);
			if (match2 !=null && match2.length() > 0){
				if(match2.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 0");
					whereSecondParam2 =  secondParam+ " like '" +description+"'";
				}
				if(match2.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 1");
					whereSecondParam2 = secondParam+ " like '%" +description+"%'";
				}
				if(match2.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 2");
					whereSecondParam2 = secondParam+ " like '" +description+"%'";
				}
				if(match2.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 3");
					whereSecondParam2 = secondParam+ " like '%" +description+"'";
				}
				if(match2.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 4");
					whereSecondParam2 = secondParam+ " like '" +description+"'";
				}
			}
		}
		

		if (code != null && code.length() > 0 && description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> both not null");
			where2 = where2  + whereFirstParam2 +" and "+ whereSecondParam2;
		}
		else if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2code not null");
			where2 = where2  + whereFirstParam2;
		}
		else if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2description not null");
			where2 =  where2 + whereSecondParam2;
		}
		else {
			log.debug(">>>>>>>>>>>>>>>>>else no where");
			where2 = "";
		}
		
		
		
		if (splitParamString != null && splitParamString.length > 1 && !where2.equals("") && splitParamString[0].length()>0){
			if(storeTable){
				if(splitParamString[1].length()>0){
					where4 = " and groupf_id = "+splitParamString[1]+" and (substr(code,0,"+length+")='"+branchId+"') and "+splitParamString[0];
				}else{
					where4 = " and  (substr(code,0,"+length+")='"+branchId+"') and "+splitParamString[0];
				}
				}else{
			where4 = " and "+splitParamString[0];	
			}
		}
		else if (splitParamString != null && splitParamString.length > 1 && where2.equals("") && splitParamString[0].length()>0){
			if(storeTable){
				if(splitParamString[1].length()>0){
					where4 = " where groupf_id = "+splitParamString[1]+" and (substr(code,0,"+length+")='"+branchId+"') and "+splitParamString[0];
				}else{
					where4 = " where (substr(code,0,"+length+")='"+branchId+"') and "+splitParamString[0];
				}
				}else{
			where4 = " where "+splitParamString[0];	
			}
		}
		
		if (!where2.equals("")){
			if (where4 != null && !where4.equals("")){
				sql2 = sql2 + where2 + where4;
			}
			else{
				sql2 = sql2 + where2;
			}
		}
		else{
			if (where4!= null && !where4.equals("")){
				log.error(">>>>>>>>>>>>>>> where4 != null ");
				sql2 = sql2 + where4;
			}
			else{
				sql2 = sql2;
			}
		}
		log.debug(">>>>>>>>>>>>>>sql2 "+sql2);
		int count = 0;
		if (splitParamString[0].length()>0){
		 count = getJdbcTemplate().queryForInt(sql2);
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>count "+count);
		Map map = new HashMap();
		map.put("results",records);
		map.put("listSize",count);
		
		Page page = new Page();
		
		return page.getPage(map,pageNumber,pageSize);
	}
	
	public Map searchExternalDest(String code,String description,String table,String firstParam,String secondParam,String paramString,String match1,String match2,int pageNumber,int pageSize){
		sql1= null;
		where1= null;
		whereFirstParam1= null;
		whereSecondParam1= null;
		sql2= null;
		where2= null;
		where3= null;
		where4= null;
		whereFirstParam2= null;
		whereSecondParam2= null;  
		limit= null;
		
		
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder("select * from store_db_connection " 
			+" where is_active='1'");
			
		String [] arrParamString = paramString.split(",");
		log.debug("paramString...1"+paramString);
		log.debug("arrParamString...."+arrParamString.length);
		log.debug(" arrParamString[0]...."+ arrParamString[0]);
		if(arrParamString.length<2){
			if(table.equals("car_type")){
				table="dist_names";
				paramString="name_type = 'car_type'";
			}else if(table.equals("area")){
				table="dist_names";
				paramString="name_type = 'area'";
			}
			log.debug("paramString...1"+paramString);
		}else if(arrParamString.length==2 && arrParamString[0].equals("driver")){
			String agentId=arrParamString[1];
			
			paramString = "name_type='driver' and agent_code ='"+agentId+"'";
			table = "dist_names";
		}else if(arrParamString.length==2 && arrParamString[0].equals("cars")){
			String agentId=arrParamString[1];
			
			paramString = "name_type='cars' and agent_code ='"+agentId+"'";
			table = "dist_names";
		}
		
		list =  getJdbcTemplate().queryForList(sql.toString());
		
		
		ListOrderedMap dbConnection ;
		dbConnection = (ListOrderedMap) list.get(0);
		exjt = new JdbcTemplate(createDataSource());
		
		
		
			log.error(">>>>>>>>>>>>>>> paramString "+paramString);
		if (paramString != null && paramString.length() > 0){
			log.error(" paramString != null");
			paramString = paramString.replaceAll("\\{sq\\}","'");
			log.error(">>>>>>>>>>>>>>> paramString "+paramString);
		}
		sql1 = "SELECT * FROM (";
		
		if(arrParamString.length==2 && arrParamString[0].equals("cars")){
			sql1 = sql1 +
				"select "+secondParam+ " as identity,ROWNUM rnum," + secondParam +" as id" ;
		}else{
			sql1 = sql1 +
			"select "+secondParam+ " as identity,ROWNUM rnum," + firstParam +" as id" ;
		}
		
		sql1 = sql1 +" from " +table;
		
		where1 =" where ";
		log.debug("where1 " + where1);
		if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1code not null");
			log.debug(">>>>>>>>>>>>>>>>>> match1 "+match1);
			if (match1 != null && match1.length() > 0){
				if(match1.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 0");
					whereFirstParam1 =  firstParam+ " like '" +code+"'";
				}
				if(match1.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 1");
					whereFirstParam1 =  firstParam+ " like '%" +code+"%'";
				}
				if(match1.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 2");
					whereFirstParam1 =  firstParam+ " like '" +code+"%'";
				}
				if(match1.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 3");
					whereFirstParam1 = firstParam+ " like '%" +code+"'";
				}
				if(match1.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 4");
					whereFirstParam1 =  firstParam+ " like '" +code+"'";
				}
			}
		}
		
	
		
		if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1description not null");
			log.debug(">>>>>>>>>>>>>>>>>> match2 "+match2);
			if (match2 !=null && match2.length() > 0){
				if(match2.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 0");
					whereSecondParam1 =  secondParam+ " like '" +description+"'";
				}
				if(match2.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 1");
					whereSecondParam1 = secondParam+ " like '%" +description+"%'";
				}
				if(match2.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 2");
					whereSecondParam1 = secondParam+ " like '" +description+"%'";
				}
				if(match2.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 3");
					whereSecondParam1 = secondParam+ " like '%" +description+"'";
				}
				if(match2.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 4");
					whereSecondParam1 = secondParam+ " like '" +description+"'";
				}
			}
		}
		log.debug("whereSecondParam1 " + whereSecondParam1);

		if (code != null && code.length() > 0 && description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> both not null");
			where1 = where1  + whereFirstParam1 +" and "+ whereSecondParam1;
		}
		else if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2code not null");
			where1 = where1  + whereFirstParam1;
		}
		else if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2description not null");
			where1 =  where1 + whereSecondParam1;
		}
		else {
			log.debug(">>>>>>>>>>>>>>>>>else no where");
			where1 = "";
		}
		log.error("where 1 "+where1);
		
		
		log.error(">>>>>>>>>>>>>>paramString != null "+(paramString != null));
		log.error(">>>>>>>>>>>>>>!where1.equals() "+(!where1.equals("")));
		log.error(">>>>>>>>>>>>>>where1.equals() "+(where1.equals("")));
		if (paramString != null && paramString.length() > 0 && !where1.equals("")){
			log.error(">>>>>>>>>>>>>>> if");
			where3 = " and "+paramString;
		}
		else if (paramString != null && paramString.length() > 0 && where1.equals("")){
			log.error(">>>>>>>>>>>>>>> else");
			where3 = " where "+paramString;
		}
		
		if (!where1.equals("")){
			log.error(">>>>>>>>>>>>>>> where1 != ''");
			if (where3 != null && !where3.equals("")){
				log.error(">>>>>>>>>>>>>>> where3 != null ");
				sql1 = sql1 + where1 + where3 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
			else{
				sql1 = sql1 + where1 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
		}
		else{
			if (where3 != null && !where3.equals("")){
				log.error(">>>>>>>>>>>>>>> where3 != null ");
				sql1 = sql1 + where3 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
			else{
				sql1 = sql1 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> sql1"+sql1);
		List records = new ArrayList();
		
		records= exjt.queryForList(sql1);
		 
		
		log.debug(">>>>>>>>>>>>>>>>>>>>records "+records);
		
		//////////////////////////////////////////////////////////////////////////////////////////////////
		if(arrParamString.length<2){
			if(table.equals("car_type")){
				table="dist_names";
				paramString="name_type = 'car_type'";
			}else if(table.equals("area")){
				table="dist_names";
				paramString="name_type = 'area'";
			}
			log.debug("paramString...1"+paramString);
		}else if(arrParamString.length==2 && arrParamString[0].equals("driver")){
			String agentId=arrParamString[1];
			
			
			paramString = "name_type='driver' and agent_code ='"+agentId+"'";
			table = "dist_names";
		}else if(arrParamString.length==2 && arrParamString[0].equals("cars")){
			String agentId=arrParamString[1];
			
						
			paramString = "name_type='cars' and agent_code ='"+agentId+"'";
			table = "dist_names";
		}
		sql2 = 
			"select count(*) "+
			" from " +table;
		
		where2 =" where ";
		
		if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1code not null");
			log.debug(">>>>>>>>>>>>>>>>>> match1 "+match1);
			if (match1 != null && match1.length() > 0){
				if(match1.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 0");
					whereFirstParam2 =  firstParam+ " like '" +code+"'";
				}
				if(match1.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 1");
					whereFirstParam2 =  firstParam+ " like '%" +code+"%'";
				}
				if(match1.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 2");
					whereFirstParam2 =  firstParam+ " like '" +code+"%'";
				}
				if(match1.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 3");
					whereFirstParam2 = firstParam+ " like '%" +code+"'";
				}
				if(match1.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 4");
					whereFirstParam2 =  firstParam+ " like '" +code+"'";
				}
			}
		}
		
	
		
		if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1description not null");
			log.debug(">>>>>>>>>>>>>>>>>> match2 "+match2);
			if (match2 !=null && match2.length() > 0){
				if(match2.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 0");
					whereSecondParam2 =  secondParam+ " like '" +description+"'";
				}
				if(match2.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 1");
					whereSecondParam2 = secondParam+ " like '%" +description+"%'";
				}
				if(match2.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 2");
					whereSecondParam2 = secondParam+ " like '" +description+"%'";
				}
				if(match2.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 3");
					whereSecondParam2 = secondParam+ " like '%" +description+"'";
				}
				if(match2.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 4");
					whereSecondParam2 = secondParam+ " like '" +description+"'";
				}
			}
		}
		

		if (code != null && code.length() > 0 && description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> both not null");
			where2 = where2  + whereFirstParam2 +" and "+ whereSecondParam2;
		}
		else if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2code not null");
			where2 = where2  + whereFirstParam2;
		}
		else if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2description not null");
			where2 =  where2 + whereSecondParam2;
		}
		else {
			log.debug(">>>>>>>>>>>>>>>>>else no where");
			where2 = "";
		}
		
		if (paramString != null && paramString.length() > 0 && !where2.equals("")){
			where4 = " and "+paramString;
		}
		else if (paramString != null && paramString.length() > 0 && where2.equals("")){
			where4 = " where "+paramString;
		}
		
		if (!where2.equals("")){
			if (where4 != null && !where4.equals("")){
				sql2 = sql2 + where2 + where4;
			}
			else{
				sql2 = sql2 + where2;
			}
		}
		else{
			if (where4!= null && !where4.equals("")){
				log.error(">>>>>>>>>>>>>>> where4 != null ");
				sql2 = sql2 + where4;
			}
			else{
				sql2 = sql2;
			}
		}
		log.debug(">>>>>>>>>>>>>>sql2 "+sql2);
		int count=0;
		
		 count = exjt.queryForInt(sql2);
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>count "+count);
		Map map = new HashMap();
		map.put("results",records);
		map.put("listSize",count);
		
		Page page = new Page();
		
		return page.getPage(map,pageNumber,pageSize);
	}
	
	public Map searchExternalTrans(	String code,
									String description,
									String table,
									String firstParam,
									String secondParam,
									String paramString,
									String match1,
									String match2,
									int pageNumber,
									int pageSize ) {
		sql1= null;
		where1= null;
		whereFirstParam1= null;
		whereSecondParam1= null;
		sql2= null;
		where2= null;
		where3= null;
		where4= null;
		whereFirstParam2= null;
		whereSecondParam2= null;  
		limit= null;
		
		
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder("select * from store_db_connection " +
											  " where is_active='1'");
			
		list =  getJdbcTemplate().queryForList(sql.toString());
		
		ListOrderedMap dbConnection ;
		dbConnection = (ListOrderedMap) list.get(0);
		exjt = new JdbcTemplate(createDataSource());
		
		if (paramString != null && paramString.length() > 0) {
			paramString = paramString.replaceAll("\\{sq\\}","'");

		}
		
		sql1 = "SELECT * FROM (select " + secondParam + " as identity,ROWNUM rnum," + firstParam + " as id from " + table;
		
		
		where1 =" where ";
		
		if (code != null && code.length() > 0) {
			
			if (match1 != null && match1.length() > 0){
				if(match1.equals("0")){
					
					whereFirstParam1 =  firstParam+ " like '" +code+"'";
				}
				if(match1.equals("1")){
					
					whereFirstParam1 =  firstParam+ " like '%" +code+"%'";
				}
				if(match1.equals("2")){
					
					whereFirstParam1 =  firstParam+ " like '" +code+"%'";
				}
				if(match1.equals("3")){
					
					whereFirstParam1 = firstParam+ " like '%" +code+"'";
				}
				if(match1.equals("4")){
					
					whereFirstParam1 =  firstParam+ " like '" +code+"'";
				}
			}
		}
		
		if (description != null && description.length() > 0) {
			
			if (match2 !=null && match2.length() > 0) {
				
				if(match2.equals("0")){
					
					whereSecondParam1 =  secondParam+ " like '" +description+"'";
				}
				if(match2.equals("1")){
					
					whereSecondParam1 = secondParam+ " like '%" +description+"%'";
				}
				if(match2.equals("2")){
					
					whereSecondParam1 = secondParam+ " like '" +description+"%'";
				}
				if(match2.equals("3")){
					
					whereSecondParam1 = secondParam+ " like '%" +description+"'";
				}
				if(match2.equals("4")){
					
					whereSecondParam1 = secondParam+ " like '" +description+"'";
				}
			}
		}
		
		if (code != null && code.length() > 0 && description != null && description.length() > 0){
			
			where1 = where1  + whereFirstParam1 +" and "+ whereSecondParam1;
		}
		else if (code != null && code.length() > 0){
			
			where1 = where1  + whereFirstParam1;
		}
		else if (description != null && description.length() > 0){
			
			where1 =  where1 + whereSecondParam1;
		}
		else {
			
			where1 = "";
		}
		
		if (paramString != null && paramString.length() > 0 && !where1.equals("")) {
			
			where3 = " and " + paramString;
		}
		else if (paramString != null && paramString.length() > 0 && where1.equals("")) {
			
			where3 = " where "+paramString;
		}
		
		if (!where1.equals("")){
			
			if (where3 != null && !where3.equals("")){
				sql1 = sql1 + where1 + where3 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			
			} else {
				
				sql1 = sql1 + where1 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
		} else {
			
			if (where3 != null && !where3.equals("")){
				log.error(">>>>>>>>>>>>>>> where3 != null ");
				sql1 = sql1 + where3 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			} else {
				sql1 = sql1 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
		}
		
		log.debug("<sql1>----- "+sql1);	
		
		List records = new ArrayList();
		
		records= exjt.queryForList(sql1);
		
		sql2 = "select count(*) from " + table;
		
		where2 =" where ";
		
		if (code != null && code.length() > 0){
			
			if (match1 != null && match1.length() > 0){
				if(match1.equals("0")){
					
					whereFirstParam2 =  firstParam+ " like '" +code+"'";
				}
				if(match1.equals("1")){
					
					whereFirstParam2 =  firstParam+ " like '%" +code+"%'";
				}
				if(match1.equals("2")){
					
					whereFirstParam2 =  firstParam+ " like '" +code+"%'";
				}
				if(match1.equals("3")){
					
					whereFirstParam2 = firstParam+ " like '%" +code+"'";
				}
				if(match1.equals("4")){
					
					whereFirstParam2 =  firstParam+ " like '" +code+"'";
				}
			}
		}
		
		if (description != null && description.length() > 0){
			
			if (match2 !=null && match2.length() > 0){
				if(match2.equals("0")){
					
					whereSecondParam2 =  secondParam+ " like '" +description+"'";
				}
				if(match2.equals("1")){
					
					whereSecondParam2 = secondParam+ " like '%" +description+"%'";
				}
				if(match2.equals("2")){
					
					whereSecondParam2 = secondParam+ " like '" +description+"%'";
				}
				if(match2.equals("3")){
					
					whereSecondParam2 = secondParam+ " like '%" +description+"'";
				}
				if(match2.equals("4")){
					
					whereSecondParam2 = secondParam+ " like '" +description+"'";
				}
			}
		}
		

		if (code != null && code.length() > 0 && description != null && description.length() > 0){
			
			where2 = where2  + whereFirstParam2 +" and "+ whereSecondParam2;
		}
		else if (code != null && code.length() > 0){
			
			where2 = where2  + whereFirstParam2;
		}
		else if (description != null && description.length() > 0){
			
			where2 =  where2 + whereSecondParam2;
		}
		else {
			
			where2 = "";
		}
		
		if (paramString != null && paramString.length() > 0 && !where2.equals("")){
			where4 = " and "+paramString;
		}
		else if (paramString != null && paramString.length() > 0 && where2.equals("")){
			where4 = " where "+paramString;
		}
		
		if (!where2.equals("")){
			if (where4 != null && !where4.equals("")){
				sql2 = sql2 + where2 + where4;
			}
			else{
				sql2 = sql2 + where2;
			}
		}
		else{
			if (where4!= null && !where4.equals("")){
				log.error(">>>>>>>>>>>>>>> where4 != null ");
				sql2 = sql2 + where4;
			}
			else{
				sql2 = sql2;
			}
		}
		log.debug("<sql2>----- "+sql2);	
		
		int count=0;
		
		count = exjt.queryForInt(sql2);
		
		Map map = new HashMap();
		map.put("results",records);
		map.put("listSize",count);
		
		Page page = new Page();
		
		return page.getPage(map,pageNumber,pageSize);
	}
	
	public Map searchDepTrans(String code,String description,String table,String firstParam,String secondParam,String paramString,String match1,String match2,int pageNumber,int pageSize,List transDepBranchList){
		sql1= null;
		where1= null;
		whereFirstParam1= null;
		whereSecondParam1= null;
		sql2= null;
		where2= null;
		where3= null;
		where4= null;
		whereFirstParam2= null;
		whereSecondParam2= null;  
		limit= null;
		
		
		
			log.error(">>>>>>>>>>>>>>> paramString "+paramString);
		if (paramString != null && paramString.length() > 0){
			log.error(" paramString != null");
			paramString = paramString.replaceAll("\\{sq\\}","'");
			log.error(">>>>>>>>>>>>>>> paramString "+paramString);
		}
		if(table != null && !table.equals("") && table.equals("store_palette_number,contract")){
			String[] tableList = table.split(",");
			String[] paramList = paramString.split(",");
			table = tableList[0];
			List listRows = new ArrayList();
			JdbcTemplate jt = new JdbcTemplate(DBUtils.getDataSource());
			StringBuilder sql = new StringBuilder();
			sql.append("select distinct(palette_capacity) from store_trns_room ");
			sql.append("where " + paramList[0]);
			listRows = getJdbcTemplate().queryForList(sql.toString());
			ListOrderedMap codeMap ;
			paramString="";
			
			for(int x=0;x<listRows.size();x++) {
				codeMap = (ListOrderedMap) listRows.get(x);
				if(x==0){
					paramString =paramString+ " palette_capacity = " + codeMap.get("palette_capacity") + paramList[1] ;
				}else{
					paramString = paramString+ " or palette_capacity = " + codeMap.get("palette_capacity") + paramList[1];
				}
			}
			
		}
		
		
		setJdbcTemplate(new JdbcTemplate(createDataSource()));
		sql1 = "SELECT * FROM (";
		if (table.equals("store_c_trns_m,store_trns_def ")) {
			sql1 = sql1 +
				"select ROWNUM rnum," + firstParam +" as id" ;
		}
		else {
			sql1 = sql1 +
				"select id as identity,ROWNUM rnum," + firstParam +" as id" ;
		}
		if(secondParam != null && !secondParam.equals("")){
			sql1 = sql1 + " , "+secondParam+ " as description ";
		}
		sql1 = sql1 +" from " +table;
		
		where1 =" where ";
		log.debug("where1 " + where1);
		if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1code not null");
			log.debug(">>>>>>>>>>>>>>>>>> match1 "+match1);
			if (match1 != null && match1.length() > 0){
				if(match1.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 0");
					whereFirstParam1 =  firstParam+ " like '" +code+"'";
				}
				if(match1.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 1");
					whereFirstParam1 =  firstParam+ " like '%" +code+"%'";
				}
				if(match1.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 2");
					whereFirstParam1 =  firstParam+ " like '" +code+"%'";
				}
				if(match1.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 3");
					whereFirstParam1 = firstParam+ " like '%" +code+"'";
				}
				if(match1.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 4");
					whereFirstParam1 =  firstParam+ " like '" +code+"'";
				}
			}
		}
		
	
		
		if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1description not null");
			log.debug(">>>>>>>>>>>>>>>>>> match2 "+match2);
			if (match2 !=null && match2.length() > 0){
				if(match2.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 0");
					whereSecondParam1 =  secondParam+ " like '" +description+"'";
				}
				if(match2.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 1");
					whereSecondParam1 = secondParam+ " like '%" +description+"%'";
				}
				if(match2.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 2");
					whereSecondParam1 = secondParam+ " like '" +description+"%'";
				}
				if(match2.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 3");
					whereSecondParam1 = secondParam+ " like '%" +description+"'";
				}
				if(match2.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 4");
					whereSecondParam1 = secondParam+ " like '" +description+"'";
				}
			}
		}
		log.debug("whereSecondParam1 " + whereSecondParam1);

		if (code != null && code.length() > 0 && description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> both not null");
			where1 = where1  + whereFirstParam1 +" and "+ whereSecondParam1;
		}
		else if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2code not null");
			where1 = where1  + whereFirstParam1;
		}
		else if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2description not null");
			where1 =  where1 + whereSecondParam1;
		}
		else {
			log.debug(">>>>>>>>>>>>>>>>>else no where");
			where1 = "";
		}
		log.error("where 1 "+where1);
		
		
		log.error(">>>>>>>>>>>>>>paramString != null "+(paramString != null));
		log.error(">>>>>>>>>>>>>>!where1.equals() "+(!where1.equals("")));
		log.error(">>>>>>>>>>>>>>where1.equals() "+(where1.equals("")));
		if (paramString != null && paramString.length() > 0 && !where1.equals("")){
			log.error(">>>>>>>>>>>>>>> if");
			where3 = " and "+paramString;
		}
		else if (paramString != null && paramString.length() > 0 && where1.equals("")){
			log.error(">>>>>>>>>>>>>>> else");
			where3 = " where "+paramString;
		}
		
		String[] tableList = table.split(",");
		
		if(tableList != null && tableList.length>1 && tableList[0].equals("store_item_data") || tableList[0].equals("store_agriculture_terms")){
			log.error("have not branch");
		}else if(table.equals("store_item_data")){
			log.error("have not branch");
		}
//			else{
//			if(transDepBranchList.size() >0){
//				String brList = " ";
//				StoreTrnsDepBranch storeTrnsDepBransch = (StoreTrnsDepBranch) transDepBranchList.get(0);
//				where3 = where3 +" and ( branch='"+storeTrnsDepBransch.getBranch().getCode()+"'";
//				for(int x=1;x<transDepBranchList.size();x++){
//					storeTrnsDepBransch = (StoreTrnsDepBranch) transDepBranchList.get(x);
//					brList = brList + " or branch='"+storeTrnsDepBransch.getBranch().getCode()+"'";				
//				}
//				brList = brList + " ) ";
//				where3 = where3 + brList ;
//			}
//		}
		if (!where1.equals("")){
			log.error(">>>>>>>>>>>>>>> where1 != ''");
			if (where3 != null && !where3.equals("")){
				log.error(">>>>>>>>>>>>>>> where3 != null ");
				sql1 = sql1 + where1 + where3 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
			else{
				sql1 = sql1 + where1 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
		}
		else{
			if (where3 != null && !where3.equals("")){
				log.error(">>>>>>>>>>>>>>> where3 != null ");
				sql1 = sql1 + where3 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
			else{
				sql1 = sql1 +") WHERE rnum BETWEEN "+((pageNumber * pageSize) + 1) +" and "+((pageNumber + 1) * pageSize);
			}
		}
		
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> sql1"+sql1);
		List records = getJdbcTemplate().queryForList(sql1);
		log.debug(">>>>>>>>>>>>>>>>>>>>records "+records);
		
		//////////////////////////////////////////////////////////////////////////////////////////////////
		
		sql2 = 
			"select count(*) "+
			" from " +table;
		
		where2 =" where ";
		
		if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1code not null");
			log.debug(">>>>>>>>>>>>>>>>>> match1 "+match1);
			if (match1 != null && match1.length() > 0){
				if(match1.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 0");
					whereFirstParam2 =  firstParam+ " like '" +code+"'";
				}
				if(match1.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 1");
					whereFirstParam2 =  firstParam+ " like '%" +code+"%'";
				}
				if(match1.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 2");
					whereFirstParam2 =  firstParam+ " like '" +code+"%'";
				}
				if(match1.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 3");
					whereFirstParam2 = firstParam+ " like '%" +code+"'";
				}
				if(match1.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match1 = 4");
					whereFirstParam2 =  firstParam+ " like '" +code+"'";
				}
			}
		}
		
	
		
		if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 1description not null");
			log.debug(">>>>>>>>>>>>>>>>>> match2 "+match2);
			if (match2 !=null && match2.length() > 0){
				if(match2.equals("0")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 0");
					whereSecondParam2 =  secondParam+ " like '" +description+"'";
				}
				if(match2.equals("1")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 1");
					whereSecondParam2 = secondParam+ " like '%" +description+"%'";
				}
				if(match2.equals("2")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 2");
					whereSecondParam2 = secondParam+ " like '" +description+"%'";
				}
				if(match2.equals("3")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 3");
					whereSecondParam2 = secondParam+ " like '%" +description+"'";
				}
				if(match2.equals("4")){
					log.debug(">>>>>>>>>>>>>>>>> match2 = 4");
					whereSecondParam2 = secondParam+ " like '" +description+"'";
				}
			}
		}
		

		if (code != null && code.length() > 0 && description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> both not null");
			where2 = where2  + whereFirstParam2 +" and "+ whereSecondParam2;
		}
		else if (code != null && code.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2code not null");
			where2 = where2  + whereFirstParam2;
		}
		else if (description != null && description.length() > 0){
			log.debug(">>>>>>>>>>>>>>>>> 2description not null");
			where2 =  where2 + whereSecondParam2;
		}
		else {
			log.debug(">>>>>>>>>>>>>>>>>else no where");
			where2 = "";
		}
		
		if (paramString != null && paramString.length() > 0 && !where2.equals("")){
			where4 = " and "+paramString;
		}
		else if (paramString != null && paramString.length() > 0 && where2.equals("")){
			where4 = " where "+paramString;
		}
		
		if(tableList != null && tableList.length>1 && tableList[0].equals("store_item_data") || tableList[0].equals("store_agriculture_terms")){
			log.error("have not branch");
		}else if(table.equals("store_item_data")){
			log.error("have not branch");
		}else{
//			if(transDepBranchList.size() >0){
//				String brList = " ";
//				StoreTrnsDepBranch storeTrnsDepBransch = (StoreTrnsDepBranch) transDepBranchList.get(0);
//				where3 = where3 +" and ( branch='"+storeTrnsDepBransch.getBranch().getCode()+"'";
//				for(int x=1;x<transDepBranchList.size();x++){
//					storeTrnsDepBransch = (StoreTrnsDepBranch) transDepBranchList.get(x);
//					brList = brList + " or branch='"+storeTrnsDepBransch.getBranch().getCode()+"'";				
//				}
//				brList = brList + " ) ";
//				where3 = where3 + brList ;
//			}
		}
		if (!where2.equals("")){
			if (where4 != null && !where4.equals("")){
				sql2 = sql2 + where2 + where4;
			}
			else{
				sql2 = sql2 + where2;
			}
		}
		else{
			if (where4!= null && !where4.equals("")){
				log.error(">>>>>>>>>>>>>>> where4 != null ");
				sql2 = sql2 + where4;
			}
			else{
				sql2 = sql2;
			}
		}
		log.debug(">>>>>>>>>>>>>>sql2 "+sql2);
		int count = getJdbcTemplate().queryForInt(sql2);
		log.debug(">>>>>>>>>>>>>>>>>>>>count "+count);
		Map map = new HashMap();
		if(table != null && !table.equals("") && table.equals("store_c_trns_m")){
			String transId = null;
			ListOrderedMap recordMap ;
			List transList = new ArrayList();
			
			for(int i=0;i<records.size();i++){
				recordMap = (ListOrderedMap) records.get(i);
				transId = recordMap.get("identity").toString();
				transList.add(transId);
			}
			map.put("transList",transList);
			map.put("results",records);
			map.put("listSize",count);
		
		}else{
			map.put("results",records);
			map.put("listSize",count);
		}
//		map.put("results",records);
//		map.put("listSize",count);
		
		Page page = new Page();
		
		return page.getPage(map,pageNumber,pageSize);
	}
	
}
