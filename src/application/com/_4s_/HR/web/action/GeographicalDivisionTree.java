package com._4s_.HR.web.action;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.HR.model.HRGeographicalDivision;
//import com._4s_.gl.model.Account;
//import com._4s_.gl.model.AccountLevel;
//import com._4s_.gl.model.AnalysisCategoryAccount;
//import com._4s_.gl.model.AnalysisLeafAccount;
//import com._4s_.gl.model.FinancialCategoryAccount;
//import com._4s_.gl.model.FinancialLeafAccount;
import com.jenkov.prizetags.tree.itf.ITree;


public class GeographicalDivisionTree  extends TreeViewController {
	
	
	public ModelAndView handleRequest(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.start handleRequest()");

			Map model = new HashMap();
		
			String geographicalDivisionId = request.getParameter("resultId");
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> geographicalDivisionId "+geographicalDivisionId);
			String expand=request.getParameter("expand");
			log.debug("expand>>>>>>>>>>>>>>>"+expand);
			model.put("resultId", geographicalDivisionId);
				
			ITree geographicalDivisionTree = hrManager.createTreeIfNotFound(request,"HRGeographicalDivision");
			if(expand!=null && !expand.equals("") && expand.equals("true"))
			{
			  geographicalDivisionTree.expandAll();
			}
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> geographicalDivisionTreeCreated ");
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> geographicalDivisionTree.getSelectedNodes()"+geographicalDivisionTree.getSelectedNodes());
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> geographicalDivisionTree.getExpandedNodes()"+geographicalDivisionTree.getExpandedNodes());
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> geographicalDivisionTree.getRoot"+geographicalDivisionTree.getRoot());
			model.put("geographicalDivisionTree",geographicalDivisionTree);
		
	        
			//if it is the first time so we can add root(only one root)
			List inDivisionList=hrManager.getObjects(HRGeographicalDivision.class);
			Boolean isFirstTime;
			if(inDivisionList!=null && !inDivisionList.isEmpty())
			{
				 isFirstTime=new Boolean(false);
			}
			else
			{
				isFirstTime=new Boolean(true);
			}
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> firstTime "+isFirstTime);
			model .put("firstTime", isFirstTime);
			String deleteId = request.getParameter("deleteId");
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> deleteId "+deleteId);
			
			//**************************************************************************************
			// deleting a division
			if (deleteId != null && !deleteId.equals("")) {
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> deleting");
				//getting division to be deleted
			   HRGeographicalDivision deletedDiv=(HRGeographicalDivision)hrManager.getObject(HRGeographicalDivision.class,new Long(deleteId));
			   log.debug(" deletedIntDiv.getChilds()>>>>>>>>>>>"+ deletedDiv.getChilds());
			
				
				// then delete Division
					if (deletedDiv.getChilds().size() == 0 && deletedDiv.getChilds().isEmpty()){
						hrManager.deleteNodeFromTree(geographicalDivisionTree, deletedDiv.getId().toString());
						hrManager.removeObject(deletedDiv);
					}
			}	
			
				// to show the selected object
				if ((geographicalDivisionId != null) && (!geographicalDivisionId.equals(0)) && (geographicalDivisionId.length() != 0)){
					HRGeographicalDivision geographicalDivision = (HRGeographicalDivision)hrManager.getObject(HRGeographicalDivision.class,new Long(geographicalDivisionId));
					log.debug(">>>>>>>>>>>>>>>>>>>>>>geographicalDivision.getEndesc() "+geographicalDivision.getEndesc());
						model.put("result",geographicalDivision);
						model.put("isLeaf", "false");
						
						
						List childs = geographicalDivision.getChilds();
						BigInteger bigInt = new BigInteger("10");
						
						// must get the length of the next level
	/*	may be needed				HRGeographicalLevel  lastLevel=(HRGeographicalLevel)hrManager.getObjectByParameter(HRGeographicalLevel.class,"isLastLevel",new Boolean(true));
						if(geographicalDivision.getDivisionLevel()!=null)
						{
								if(geographicalDivision.getDivisionLevel().getLevelNo()< lastLevel.getLevelNo())
								{
									HRGeographicalLevel  nextLevel=hrManager.getGeoLevelByLevelNo(geographicalDivision.getDivisionLevel().getLevelNo()+1);
									int maxCode = bigInt.pow(nextLevel.getLength().intValue()).intValue() - 1;
									
									if (childs.size() > maxCode || childs.size() > maxCode){
										model.put("canAddChild","false");
									}
									if (childs.size() < maxCode && childs.size() < maxCode){
										model.put("canAddChild","true");
									}
									
									//check if next level more than 2 so we can copy leafs from other object so show copy button
									if(nextLevel.getLevelNo()>2)
									{
										log.debug("nextLevel.getLevelNo()>>>>>>>>>>>>"+nextLevel.getLevelNo());
										log.debug("nextLevel.getId()>>>>>>>>>>>>"+nextLevel.getId());
										model.put("canCopyLeafs", "true");
									}
									
									else
									{
										log.debug("nextLevel.getLevelNo()>>>>>>>>>>>>"+nextLevel.getLevelNo());
										log.debug("nextLevel.getId()>>>>>>>>>>>>"+nextLevel.getId());
										model.put("canCopyLeafs", "false");
									}
								
								}
						}*/
						// is the account editable
						/* needed Boolean canBeEdited = new Boolean(true);
						if (childs.size() > 0){
							canBeEdited = hrManager.canRemoveCategoryAccount("financial",childs);
							log.debug(">>>>>>>>>>>>>>>>>>>>>>canBeEdited "+canBeEdited);
						}
						
						
						if (canBeEdited.booleanValue() == true){
							model.put("canBeEdited","true");
						}
						if (canBeEdited.booleanValue() == false){
							model.put("canBeEdited","false");
						}
						*/
						
						
						
						
					if(geographicalDivision.getParent()!=null)
					{
					//needed 	if (geographicalDivision.getParent().getIsLast().equals(new Boolean(true))){
							model.put("isLeaf", "true");
					//	}
					}
				
						
						
			
				}
				
				
			
			/*	int parentCategoryAccountsSize ;	// for AddCategoryAccount
				if (isFinancial.equals("true")){
				parentCategoryAccountsSize = mgr.getParentCategoryAccounts("true").size();
				}
				else{
					parentCategoryAccountsSize = mgr.getParentCategoryAccounts("false").size();
				}
				log.debug("parentCategoryAccountsSize: "+parentCategoryAccountsSize);
				
				//AccountLevel level = mgr.getLevelByLevelNo(new Integer(1),"true");
				AccountLevel level = mgr.getLevelByLevelNo(new Integer(1),isFinancial);
				
				BigInteger bigInt = new BigInteger("10");
				int maxCode = bigInt.pow(level.getLength().intValue()).intValue() - 1;
				log.debug(">>>>>>>>>>>>>>>>>>>>>>maxCode "+maxCode);
				
				if (parentCategoryAccountsSize < maxCode){	
					log.debug(">>>>>>>>>>>>>>>>>>>>>>parentCategoryAccountsSize < maxCode ");
					model.put("canAddCategoryAccount","true");
				}
				else{
					log.debug(">>>>>>>>>>>>>>>>>>>>>>parentCategoryAccountsSize > maxCode ");
					model.put("canAddCategoryAccount","false");
				}*/
				
				
				model.put("formType", "geographical");
		//	********************************************************************************
			return  new ModelAndView("internalDivisionTree", model);
		}


	
		
	}



