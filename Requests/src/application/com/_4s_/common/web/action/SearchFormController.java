package com._4s_.common.web.action;

//import java.math.BigDecimal;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
import java.util.Date;

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
			//searchCommandId = (String)request.getSession().getAttribute("searchCommandId");
			//searchCommandName = (String)request.getSession().getAttribute("searchCommandName");
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
//		StoreTrnsDef storeTrnsDef = new StoreTrnsDef();
//		String[] strTrans = paramString.split("=");
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
//		log.debug("transTypeId...."+transTypeId);
//		
//		model.put("transTypeId",transTypeId);
	     
//		if(transTypeId != null && !transTypeId.equals("") && table.equals("view_store_dep_trans")){
////					storeTrnsDef = new StoreTrnsDef();
////					storeTrnsDef = (StoreTrnsDef) mgr.getObject(StoreTrnsDef.class, new Long(transTypeId));
////					if(storeTrnsDef.getStoreTrnsDefSpec().getIs_contract()==0 
////							&& storeTrnsDef.getStoreTrnsDefSpec().getIs_invoice()==0
////							&& storeTrnsDef.getStoreTrnsDefSpec().getIs_reservation()==0)
//					transTableFlag = true;
//					log.debug("transTableFlag...."+transTableFlag);
////					model.put("transType",storeTrnsDef);
//					
////					model.put("groupfList", mgr.getObjectsByParameter(Groupf.class,
////							"is_destination", "1"));
//					if(fromDate != null && !fromDate.equals("")){
//						paramString = paramString + " and trns_date >= to_date('"+fromDate+" 00:00:00','yyyy-MM-dd hh24:mi:ss')";
//						String [] fDate = fromDate.split("-");
//						fromDate = fDate[2]+"/"+fDate[1]+"/"+fDate[0];
//						model.put("fromDate",fromDate);
//					}
//					if(toDate != null && !toDate.equals("")){
//						paramString = paramString+" and trns_date <= to_date('"+toDate+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
//						String [] tDate = toDate.split("-");
//						toDate = tDate[2]+"/"+tDate[1]+"/"+tDate[0];
//						model.put("toDate",toDate);
//					}
//		}
		
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
//					else if(table.equals("store_trns_m")) {
//						map = qry.searchExternalTrans(searchCommandId,searchCommandName,table,firstParam,secondParam,paramString,match1,match2,pageNumber,10);
//						
//					} 
//					else {
//						log.debug("In.... ");
//						log.debug("branchId.... "+branchId);
//						
//						 map = qry.search(searchCommandId,searchCommandName,table,firstParam,secondParam,paramString,match1,match2,pageNumber,10,branchId);	
//						 log.debug("Out.... ");
//						 if(table.equals("store_c_trns_m") || table.equals("view_store_dep_trans")){
//							 transList =(List) map.get("transList");
//							 storeTrans = new StoreTransactionM();
//							 boolean setTrans = true;
//							 for(int x=0;x<transList.size();x++){
//								 storeTrans = new StoreTransactionM();
//								 storeTrans = (StoreTransactionM) mgr.getObject(StoreTransactionM.class, new Long (transList.get(x).toString()));
//								 if(transTypeId != null && !transTypeId.equals("")){
//								 List storeMDepList = storeTrans.getStoreTransactionMDep();
//								 for(int a=0;a<storeMDepList.size();a++){
//									 StoreTransactionMDep storeTransMdep = (StoreTransactionMDep) storeMDepList.get(a);
//									 if(storeTransMdep.getTrans_dep_id() != null 
//											 && storeTransMdep.getTrans_dep_id().getTrns_code().getStoreTrnsDefSpec().getIs_contract()==1
//											 && storeTransMdep.getTrans_dep_id().getIs_locked()==1){
//										 setTrans = false;
//									 }
//								 }
//							 }
//								 
//								 if(setTrans == true){									 
//								 if(storeTrans.getTrns_code().getStoreTrnsDefSpec().getIs_contract()==0
//										&& storeTrans.getTrns_code().getStoreTrnsDefSpec().getIs_invoice()==0
//										&& storeTrans.getTrns_code().getStoreTrnsDefSpec().getIs_reservation()==0){
//								
//									 if(storeTrans.getExtdestfrom()==true){
//											destCodeList = storesManager.getExtDestCode(dbConnection, 
//													storeTrans.getExtdestfromid(), storeTrans.getExtdestfromcodeid());
//											if(destCodeList.size()>0){
//											  ExternalTypeAndCode externalTypeAndCode = (ExternalTypeAndCode) destCodeList.get(0);
//											  storeTrans.setExtFromCode(externalTypeAndCode.getTypedesc());
//									 		}
//									}
//										 if(storeTrans.getExtdestto()==true){
//												destCodeList = storesManager.getExtDestCode(dbConnection, 
//														storeTrans.getExtdesttoid(), storeTrans.getExtdesttocodeid());
//												if(destCodeList.size()>0){
//													ExternalTypeAndCode externalTypeAndCode = (ExternalTypeAndCode) destCodeList.get(0);
//													storeTrans.setExtToCode(externalTypeAndCode.getTypedesc());
//												}
//									}									 
//								 transMapList.add(storeTrans);
//								 
//							 }else if (storeTrans.getTrns_code().getStoreTrnsDefSpec().getIs_contract()==1){
//								 if(branchForValidRoom != null && !branchForValidRoom.equals("")){
//									 StoreTransactionM transValidContract = (StoreTransactionM)storesManager.getTransactionByCodeAndNumberAndContractBranch(
//								 				storeTrans.getTrns_no(), storeTrans.getTrns_code().getId(),branchForValidRoom,storeTrans.getBranch().getCode());
//									 
//									 if(transValidContract != null){
//										 transMapList.add(storeTrans); 
//									 }
//								 	}else{
//								 		transMapList.add(storeTrans); 
//								 	}
//							 	}
//							 }
//							 }
//							 model.put("transMapList",transMapList);
//							 map.put("listSize",transMapList.size());
//							 log.debug("transList......."+transMapList.size());
//						 }
//					}
					
				}
			}
			else{
				if((table.equals("car_type") || table.equals("area"))){
					map = qry.searchExternalDest(searchCommandId,searchCommandName,table,firstParam,secondParam,paramString,match1,match2,pageNumber,10);
				} else if(table.equals("store_trns_m")) {
					map = qry.searchExternalTrans(searchCommandId,searchCommandName,table,firstParam,secondParam,paramString,match1,match2,pageNumber,10);
					
				}
				else {
					if(!table.equals("dist_names") || !table.equals("driver") || !table.equals("cars")){
						
						 map = qry.search(searchCommandId,searchCommandName,table,firstParam,secondParam,paramString,match1,match2,pageNumber,10,branchId);	
						
//						 if(table.equals("store_c_trns_m") || table.equals("view_store_dep_trans")){
//								 transList =(List) map.get("transList");
//								 storeTrans = new StoreTransactionM();
//								 boolean setTrans = true;
//								 for(int x=0;x<transList.size();x++){
//									 storeTrans = new StoreTransactionM();
//									 storeTrans = (StoreTransactionM) mgr.getObject(StoreTransactionM.class, new Long (transList.get(x).toString()));
//									 if(transTypeId != null && !transTypeId.equals("")){
//									 List storeMDepList = storeTrans.getStoreTransactionMDep();
//									 for(int a=0;a<storeMDepList.size();a++){
//										 StoreTransactionMDep storeTransMdep = (StoreTransactionMDep) storeMDepList.get(a);
//										 if(storeTransMdep.getTrans_dep_id() != null 
//												 && storeTransMdep.getTrans_dep_id().getTrns_code().getStoreTrnsDefSpec().getIs_contract()==1
//												 && storeTransMdep.getTrans_dep_id().getIs_locked()==1){
//											 setTrans = false;
//										 }
//									 }
//									 }
//									 if(setTrans == true){
//									 if(storeTrans.getTrns_code().getStoreTrnsDefSpec().getIs_contract()==0
//												&& storeTrans.getTrns_code().getStoreTrnsDefSpec().getIs_invoice()==0
//												&& storeTrans.getTrns_code().getStoreTrnsDefSpec().getIs_reservation()==0){
//									 
//										 if(storeTrans.getExtdestfrom()==true){
//											destCodeList = storesManager.getExtDestCode(dbConnection, 
//													storeTrans.getExtdestfromid(), storeTrans.getExtdestfromcodeid());
//											if(destCodeList.size()>0){
//												ExternalTypeAndCode externalTypeAndCode = (ExternalTypeAndCode) destCodeList.get(0);
//												storeTrans.setExtFromCode(externalTypeAndCode.getTypedesc());
//											}
//										 }
//										 if(storeTrans.getExtdestto()==true){
//												destCodeList = storesManager.getExtDestCode(dbConnection, 
//														storeTrans.getExtdesttoid(), storeTrans.getExtdesttocodeid());
//												if(destCodeList.size()>0){
//													ExternalTypeAndCode externalTypeAndCode = (ExternalTypeAndCode) destCodeList.get(0);
//													storeTrans.setExtToCode(externalTypeAndCode.getTypedesc());
//												}
//											 }										 
//										 transMapList.add(storeTrans);
//										 
//									 }else if (storeTrans.getTrns_code().getStoreTrnsDefSpec().getIs_contract()==1){
//										 if(branchForValidRoom != null && !branchForValidRoom.equals("")){
//											 StoreTransactionM transValidContract = (StoreTransactionM)storesManager.getTransactionByCodeAndNumberAndContractBranch(
//										 				storeTrans.getTrns_no(), storeTrans.getTrns_code().getId(),branchForValidRoom,storeTrans.getBranch().getCode());
//											 
//											 if(transValidContract != null){
//												 transMapList.add(storeTrans); 
//											 }
//										 	}else{
//										 		transMapList.add(storeTrans); 
//										 	}
//									 	}
//									 }
//								 }
//								 model.put("transMapList",transMapList);
//								 map.put("listSize",transMapList.size());
//								 log.debug("transList......."+transList.size());							 
//						 }
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
