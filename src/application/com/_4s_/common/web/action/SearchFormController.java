package com._4s_.common.web.action;

//import java.math.BigDecimal;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com._4s_.common.dao.Queries;
import com._4s_.common.model.Branch;
import com._4s_.common.model.Employee;
import com._4s_.common.service.CommonManager;
import com._4s_.requestsApproval.service.RequestsApprovalManager;
//import com._4s_.stores.model.DBConnection;
//import com._4s_.stores.model.Destination;
//import com._4s_.stores.model.Groupf;
//import com._4s_.stores.model.StoreTransactionM;
//import com._4s_.stores.model.StoreTransactionMDep;
//import com._4s_.stores.model.StoreTransactionO;
//import com._4s_.stores.model.StoreTrnsDef;
//import com._4s_.stores.model.StoreTrnsDep;
//import com._4s_.stores.model.StoreTrnsDepBranch;
//import com._4s_.stores.model.Stores;
//import com._4s_.stores.service.StoresManager;
//import com._4s_.stores.web.action.ExternalTypeAndCode;

public class SearchFormController implements Controller {
	CommonManager mgr;
//	private StoresManager storesManager;
//	
//	public void setStoresManager(StoresManager storesManager) {
//		this.storesManager = storesManager;
//	}
//
//
//	public StoresManager getStoresManager() {
//		return storesManager;
//	}
	
	private RequestsApprovalManager reqMgr;
	
	
	public RequestsApprovalManager getReqMgr() {
		return reqMgr;
	}


	public void setReqMgr(RequestsApprovalManager reqMgr) {
		this.reqMgr = reqMgr;
	}


	public CommonManager getMgr() {
		return mgr;
	}


	public void setMgr(CommonManager mgr) {
		this.mgr = mgr;
	}

	Queries qry ;
	
	public Queries getQry() {
		return qry;
	}


	public void setQry(Queries qry) {
		this.qry = qry;
	}  
	
	protected final Log log = LogFactory.getLog(getClass());
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse respons) throws Exception {
		
		Map model = new HashMap();
//		DBConnection dbConnection = (DBConnection) mgr.getObjectByParameter(
//				DBConnection.class, "isActive", new String("1"));
		List transList = new ArrayList();
		List destCodeList = new ArrayList();
//		StoreTransactionM storeTrans = new StoreTransactionM();
		List transMapList = new ArrayList();
		List branchList = (List) mgr.getObjects(Branch.class);
		model.put("branchList",branchList);
		
		
		Employee employee = (Employee)request.getSession().getAttribute("employee");
		
		String branchId = (String) request.getParameter("branchId");

		String paramString = request.getParameter("paramString");
//		String transTypeId = (String) request.getParameter("transTypeId");
//		String storeTransDefId = (String) request.getParameter("storeTransDefId");
		String branchForValidRoom = (String) request.getParameter("branchForValidRoom");
		model.put("branchForValidRoom",branchForValidRoom);
		if(branchId!=null && !branchId.equals("")){
			
			model.put("branchId",branchId);
			
		}else{
			if(employee.getCanSeeAllStore()){

				branchId = null;
			}else{
				log.debug("branchId4 ... " + branchId);
				String [] groupFType = paramString.split("=");
				String [] chechString = paramString.split("@@");
				log.debug("chechString.length............. "+chechString.length);
				if(chechString.length==0 && groupFType.length>1 && groupFType[0] != null && !groupFType[0].equals("") && groupFType[0].equals("groupf_id") && groupFType[1] != null && !groupFType[1].equals("")){
					
//					Groupf groupf = (Groupf) mgr.getObject(Groupf.class, new Long(groupFType[1]));
					log.debug("branchId4............. "+branchId);
//					if(groupf.getGroup_type_id().getMain_table().equals("STORE_CUSTOMER")){

//						branchId = null;
//						
//						model.put("branchId",branchId);
//					}else{
//						log.debug("branchId6 ... " + branchId);
//						branchId = employee.getBranch().getCode();
//						log.debug("branchId6............. "+branchId);
//						model.put("branchId",branchId);
//					}
					
				}
				
			}
			
		}
		
		
		model.put("canSeeAllStore", employee.getCanSeeAllStore());
		model.put("employee", employee);
		
		List roomList = new ArrayList();
		List roomMapList = new ArrayList();
//		Destination room = new Destination();
		
		log.debug("paramString............. "+request.getParameter("paramString"));
		
		String table = request.getParameter("table");
		model.put("table",table);
		String firstParam = request.getParameter("firstParam");
		model.put("firstParam",firstParam);
		String secondParam = request.getParameter("secondParam");
		model.put("secondParam",secondParam);
		String firstKey = request.getParameter("firstKey");
		model.put("firstKey",firstKey);
		String secondKey = request.getParameter("secondKey");
		model.put("secondKey",secondKey);
		
		String searchCommandId = null;
		String searchCommandName = null;
		if(request.getMethod().equals("POST")){
			searchCommandId = request.getParameter("searchCommandId");
			searchCommandName = request.getParameter("searchCommandName");
			request.getSession().setAttribute("searchCommandId",searchCommandId);
			request.getSession().setAttribute("searchCommandName",searchCommandName);
		}
		else{
			searchCommandId = "";
			searchCommandName = "";
		}
		
		model.put("searchCommandId",searchCommandId);
		
		model.put("searchCommandName",searchCommandName);
		String match1 = request.getParameter("match1");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>> match1 "+match1);
		log.debug(">>>>>>>searchCommandName "+searchCommandName);
		log.debug(">>>>>>>searchCommandId "+searchCommandId);
		if(match1 == null) {
			match1 = "1";
		}
		model.put("m1",match1);
		String match2 = request.getParameter("match2");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>> match2 "+match2);
		if(match2 == null) {
			match2 = "1";
		}
		model.put("m2",match2);
		
		String inputId = request.getParameter("inputId");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>> inputId "+inputId);
		model.put("inputId",inputId);
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>> paramString "+paramString);
		paramString = paramString.replace("--", "'");
		model.put("paramString",paramString);
		
		String pageString = request.getParameter("page");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> pageString "+pageString);
		int pageNumber = 0;
		if (pageString != null){	 
			pageNumber = new Long(pageString).intValue();
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>  page "+pageNumber);
		
		String onblur = request.getParameter("onblur");
		log.debug("onblur"+onblur);
		model.put("onblur",onblur);
		
		String jsIncludes = request.getParameter("jsIncludes");
		log.debug("jsIncludes"+jsIncludes);
		model.put("jsIncludes",jsIncludes);
		
		String onlinkclick = request.getParameter("onlinkclick");
		log.debug("onlinkclick"+onlinkclick);
		model.put("onlinkclick",onlinkclick);
		
		if(jsIncludes != null && !jsIncludes.equals("")){
			String[] jsIncludeList = jsIncludes.split(";");
			model.put("jsIncludeList",jsIncludeList);
		}
		String []splitParamString = null;
		Map map = new HashMap();
		boolean transTableFlag = false;
		
		String fromDate = (String) request.getParameter("fromDate");
		String toDate = (String) request.getParameter("toDate");
		if(fromDate != null && !fromDate.equals("")){
			String [] fDate = fromDate.split("/");
			fromDate = fDate[2]+"-"+fDate[1]+"-"+fDate[0];
		}
		if(toDate != null && !toDate.equals("")){
			String [] tDate = toDate.split("/");
			toDate = tDate[2]+"-"+tDate[1]+"-"+tDate[0];
		}
		log.debug("fromDate....."+fromDate);
		log.debug("toDate...."+toDate);
		
		model.put("transTableFlag",transTableFlag);
		
		if (searchCommandId != null || searchCommandName != null){
			log.debug(">>>>>>>>>>>>> before query");
			//// Check Filter Method for Store or Cooling room by Branch  ///////
			if(paramString != null && !paramString.equals("")){
				splitParamString =  paramString.split("@@");
			}
			log.debug("splitParamString.....  "+splitParamString);
//			log.error(">>>>>>>>>>>splitParamString...  "+splitParamString.length);
//			log.error(">>>>0...&"+splitParamString[0]+"&.........1...."+splitParamString[1]);
			if(splitParamString != null && !splitParamString.equals("")){
				if(splitParamString.length ==3){
					log.debug("true  ");
					 map = qry.searchByTypeAndBranch(searchCommandId,searchCommandName,table,firstParam,secondParam,paramString,match1,match2,pageNumber,10);
				}else if(splitParamString.length ==2){
					log.debug("true  ");
					 map = qry.searchByBranch(searchCommandId,searchCommandName,table,firstParam,secondParam,paramString,match1,match2,pageNumber,10);
				}else{
					if((table.equals("dist_names") || table.equals("driver") || table.equals("cars") || table.equals("car_type") || table.equals("area")) && paramString != null && !paramString.equals("")){
						String[] spString=paramString.split(",");
						if(table.equals("driver") || table.equals("cars")){
						if(spString != null && !spString.equals("") && spString.length>1){
							map = qry.searchExternalDest(searchCommandId,searchCommandName,table,firstParam,secondParam,paramString,match1,match2,pageNumber,10);
						}
						
						}else if(spString != null && !spString.equals("") && spString.length==1){
							String[] spFilter=paramString.split("=");
							if(spFilter.length>1 && spFilter[1] != null && !spFilter[1].equals("")){
								map = qry.searchExternalDest(searchCommandId,searchCommandName,table,firstParam,secondParam,paramString,match1,match2,pageNumber,10);
							}
							
						}
						
					}else if(table.equals("dist_names")  && paramString.equals("")){
						log.debug("Null Result  ");
					} 
				}
			}
			else{
				if((table.equals("car_type") || table.equals("area"))){
					log.debug("searching car type or area");
					map = qry.searchExternalDest(searchCommandId,searchCommandName,table,firstParam,secondParam,paramString,match1,match2,pageNumber,10);
				} else if(table.equals("store_trns_m")) {
					log.debug("searching stores transaction");
					map = qry.searchExternalTrans(searchCommandId,searchCommandName,table,firstParam,secondParam,paramString,match1,match2,pageNumber,10);
					
				}
				else {
					if(!table.equals("dist_names") || !table.equals("driver") || !table.equals("cars")){
						log.debug("searching " + table);
						String level = "";
						List empReqTypeAcc = new ArrayList();
						if (!table.equals("")) {
							empReqTypeAcc = reqMgr.getEmpReqTypeAcc(employee, null);
						}
						Iterator itr = empReqTypeAcc.iterator();
						while(itr.hasNext()) {
							Long eCode = (Long)itr.next();
							if(level.isEmpty()) {
								level +=eCode;
							} else {
								level += ","+eCode;
							}
						}
						if (level.equals("")) {
							level += employee.getEmpCode();
						} else {
							level += ","+employee.getEmpCode();
						}
						log.debug("level " + level);
						
						map = qry.search(searchCommandId,searchCommandName,table,firstParam,secondParam,paramString,match1,match2,level,pageNumber,10,branchId);	
					}else {
						log.debug("Null Result  ");
					}
				}
							
			}
			
			//////////  End Method    ////////////////
			
			log.debug(">>>>>>>>>>>>> after query");
			model.putAll(map);
		}
		return new ModelAndView("searchForm",model);
	}


	
	
}
