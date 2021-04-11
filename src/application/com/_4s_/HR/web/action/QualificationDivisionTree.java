package com._4s_.HR.web.action;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.HR.model.HRGeographicalDivision;
import com._4s_.HR.model.HRQualificationDivision;
import com._4s_.HR.model.HRQualificationLevel;
import com._4s_.HR.model.HRQualificationLevel;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseController;
//import com._4s_.gl.model.Account;
//import com._4s_.gl.model.AccountLevel;
//import com._4s_.gl.model.AnalysisCategoryAccount;
//import com._4s_.gl.model.AnalysisLeafAccount;
//import com._4s_.gl.model.FinancialCategoryAccount;
//import com._4s_.gl.model.FinancialLeafAccount;
import com.jenkov.prizetags.tree.itf.ITree;


public class QualificationDivisionTree  extends TreeViewController {
	
	
	public ModelAndView handleRequest(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.start handleRequest()");

			Map model = new HashMap();
		
			String qualificationDivisionId = request.getParameter("resultId");
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> qualificationDivisionId "+qualificationDivisionId);
			String expand=request.getParameter("expand");
			log.debug("expand>>>>>>>>>>>>>>>"+expand);
			model.put("resultId", qualificationDivisionId);
				
			ITree qualificationDivisionTree = hrManager.createTreeIfNotFound(request,"HRQualificationDivision");
			if(expand!=null && !expand.equals("")&& expand.equals("true"))
			{
			 qualificationDivisionTree.expandAll();
			}
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> qualificationDivisionTreeCreated ");
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> qualificationDivisionTree.getSelectedNodes()"+qualificationDivisionTree.getSelectedNodes());
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> qualificationDivisionTree.getExpandedNodes()"+qualificationDivisionTree.getExpandedNodes());
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> qualificationDivisionTree.getRoot"+qualificationDivisionTree.getRoot());
			model.put("qualificationDivisionTree",qualificationDivisionTree);
		
	        
			//if it is the first time so we can add root(only one root)
			List inDivisionList=hrManager.getObjects(HRQualificationDivision.class);
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
			   HRQualificationDivision deletedDiv=(HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class,new Long(deleteId));
			   log.debug(" deletedIntDiv.getChilds()>>>>>>>>>>>"+ deletedDiv.getChilds());
			
				
				// then delete Division
					if (deletedDiv.getChilds().size() == 0 && deletedDiv.getChilds().isEmpty()){
						hrManager.deleteNodeFromTree(qualificationDivisionTree, deletedDiv.getId().toString());
						hrManager.removeObject(deletedDiv);
					}
			}	
				
			
				// to show the selected object
				if ((qualificationDivisionId != null) && (!qualificationDivisionId.equals(0)) && (qualificationDivisionId.length() != 0)){
					HRQualificationDivision qualificationDivision = (HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class,new Long(qualificationDivisionId));
					log.debug(">>>>>>>>>>>>>>>>>>>>>>qualificationDivision.getEndesc() "+qualificationDivision.getEndesc());
						model.put("result",qualificationDivision);
						model.put("isLeaf", "false");
						
						
						List childs = qualificationDivision.getChilds();
						BigInteger bigInt = new BigInteger("10");
						
						// must get the length of the next level
					/*may be needed	HRQualificationLevel  lastLevel=(HRQualificationLevel)hrManager.getObjectByParameter(HRQualificationLevel.class,"isLastLevel",new Boolean(true));
						if(qualificationDivision.getDivisionLevel()!=null)
						{
								if(qualificationDivision.getDivisionLevel().getLevelNo()< lastLevel.getLevelNo())
								{
									HRQualificationLevel  nextLevel=hrManager.getQualLevelByLevelNo(qualificationDivision.getDivisionLevel().getLevelNo()+1);
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
						
						
						
						
					if(qualificationDivision.getParent()!=null)
					{
					//needed 	if (qualificationDivision.getParent().getIsLast().equals(new Boolean(true))){
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
				
				
				model.put("formType", "qualification");
		//	********************************************************************************
			return  new ModelAndView("internalDivisionTree", model);
		}


	
		
	}



