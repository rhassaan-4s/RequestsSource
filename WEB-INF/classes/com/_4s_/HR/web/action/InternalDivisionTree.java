package com._4s_.HR.web.action;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.HR.model.HRInternalDivision;
//import com._4s_.gl.model.Account;
//import com._4s_.gl.model.AccountLevel;
//import com._4s_.gl.model.AnalysisCategoryAccount;
//import com._4s_.gl.model.AnalysisLeafAccount;
//import com._4s_.gl.model.FinancialCategoryAccount;
//import com._4s_.gl.model.FinancialLeafAccount;
import com.jenkov.prizetags.tree.itf.ITree;


public class InternalDivisionTree  extends TreeViewController {
	
	
	public ModelAndView handleRequest(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.start handleRequest()");

			Map model = new HashMap();
		
			String internalDivisionId = request.getParameter("resultId");
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> internalDivisionId "+internalDivisionId);
			String expand=request.getParameter("expand");
			log.debug("expand>>>>>>>>>>>>>>>"+expand);
			model.put("resultId", internalDivisionId);
				
			ITree internalDivisionTree = hrManager.createTreeIfNotFound(request,"HRInternalDivision");
			if(expand!=null && !expand.equals("") && expand.equals("true"))
			{
			  internalDivisionTree.expandAll();
			}
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> internalDivisionTreeCreated ");
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> internalDivisionTree.getSelectedNodes()"+internalDivisionTree.getSelectedNodes());
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> internalDivisionTree.getExpandedNodes()"+internalDivisionTree.getExpandedNodes());
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> internalDivisionTree.getRoot"+internalDivisionTree.getRoot());
			model.put("internalDivisionTree",internalDivisionTree);
		
	        
			//if it is the first time so we can add root(only one root)
			List inDivisionList=hrManager.getObjects(HRInternalDivision.class);
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
			   HRInternalDivision deletedIntDiv=(HRInternalDivision)hrManager.getObject(HRInternalDivision.class,new Long(deleteId));
			   log.debug(" deletedIntDiv.getChilds()>>>>>>>>>>>"+ deletedIntDiv.getChilds());
			
				
				// then delete Division
					if (deletedIntDiv.getChilds().size() == 0 && deletedIntDiv.getChilds().isEmpty()){
						hrManager.deleteNodeFromTree(internalDivisionTree, deletedIntDiv.getId().toString());
						deletedIntDiv.getParent().getChilds().remove(deletedIntDiv);
						hrManager.removeObject(deletedIntDiv);
					}
			}		
			
				// to show the selected object
				if ((internalDivisionId != null) && (!internalDivisionId.equals(0)) && (internalDivisionId.length() != 0)){
					HRInternalDivision internalDivision = (HRInternalDivision)hrManager.getObject(HRInternalDivision.class,new Long(internalDivisionId));
					log.debug(">>>>>>>>>>>>>>>>>>>>>>internalDivision.getEndesc() "+internalDivision.getEndesc());
						model.put("result",internalDivision);
						model.put("isLeaf", "false");
						
						
						List childs = internalDivision.getChilds();
						BigInteger bigInt = new BigInteger("10");
						
						// must get the length of the next level
						/*may be needed HRInternalLevel  lastLevel=(HRInternalLevel)hrManager.getObjectByParameter(HRInternalLevel.class,"isLastLevel",new Boolean(true));
						if(internalDivision.getDivisionLevel()!=null)
						{
								if(internalDivision.getDivisionLevel().getLevelNo()< lastLevel.getLevelNo())
								{
									HRInternalLevel  nextLevel=hrManager.getLevelByLevelNo(internalDivision.getDivisionLevel().getLevelNo()+1);
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
						
						
						
						
					if(internalDivision.getParent()!=null)
					{
					//needed 	if (internalDivision.getParent().getIsLast().equals(new Boolean(true))){
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
				
				
				model.put("formType", "internal");
		//	********************************************************************************
			return  new ModelAndView("internalDivisionTree", model);
		}
	}



	
		
	



