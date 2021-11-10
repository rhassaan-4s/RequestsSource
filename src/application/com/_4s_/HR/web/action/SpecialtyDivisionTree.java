package com._4s_.HR.web.action;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.HR.model.HRSpecialtyDivision;
import com._4s_.HR.model.HRSpecialtyLevel;
//import com._4s_.gl.model.Account;
//import com._4s_.gl.model.AccountLevel;
//import com._4s_.gl.model.AnalysisCategoryAccount;
//import com._4s_.gl.model.AnalysisLeafAccount;
//import com._4s_.gl.model.FinancialCategoryAccount;
//import com._4s_.gl.model.FinancialLeafAccount;
import com.jenkov.prizetags.tree.itf.ITree;


public class SpecialtyDivisionTree  extends TreeViewController {
	
	
	public ModelAndView handleRequest(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.start handleRequest()");

			Map model = new HashMap();
		
			String specialtyDivisionId = request.getParameter("resultId");
			String expand=request.getParameter("expand");
			log.debug("expand>>>>>>>>>>>>>>>"+expand);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> specialtyDivisionId "+specialtyDivisionId);
			model.put("resultId", specialtyDivisionId);
				
			ITree specialtyDivisionTree = hrManager.createTreeIfNotFound(request,"HRSpecialtyDivision");
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> specialtyDivisionTreeCreated ");
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> specialtyDivisionTree.getSelectedNodes()"+specialtyDivisionTree.getSelectedNodes());
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> specialtyDivisionTree.getExpandedNodes()"+specialtyDivisionTree.getExpandedNodes());
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> specialtyDivisionTree.getRoot"+specialtyDivisionTree.getRoot());
			if(expand!=null && !expand.equals("") && expand.equals("true"))
			{
			 specialtyDivisionTree.expandAll();
			}
			model.put("specialtyDivisionTree",specialtyDivisionTree);
		
	        
			//if it is the first time so we can add root(only one root)
			List inDivisionList=hrManager.getObjects(HRSpecialtyDivision.class);
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
			   HRSpecialtyDivision deletedDiv=(HRSpecialtyDivision)hrManager.getObject(HRSpecialtyDivision.class,new Long(deleteId));
			   log.debug(" deletedIntDiv.getChilds()>>>>>>>>>>>"+ deletedDiv.getChilds());
			
				
				// then delete Division
					if (deletedDiv.getChilds().size() == 0 && deletedDiv.getChilds().isEmpty()){
						hrManager.deleteNodeFromTree(specialtyDivisionTree, deletedDiv.getId().toString());
						hrManager.removeObject(deletedDiv);
					}
			}	
			
				// to show the selected object
				if ((specialtyDivisionId != null) && (!specialtyDivisionId.equals(0)) && (specialtyDivisionId.length() != 0)){
					HRSpecialtyDivision specialtyDivision = (HRSpecialtyDivision)hrManager.getObject(HRSpecialtyDivision.class,new Long(specialtyDivisionId));
					log.debug(">>>>>>>>>>>>>>>>>>>>>>specialtyDivision.getEndesc() "+specialtyDivision.getEndesc());
						model.put("result",specialtyDivision);
						model.put("isLeaf", "false");
						
						
						List childs = specialtyDivision.getChilds();
						BigInteger bigInt = new BigInteger("10");
						
						// must get the length of the next level
						HRSpecialtyLevel  lastLevel=(HRSpecialtyLevel)hrManager.getObjectByParameter(HRSpecialtyLevel.class,"isLastLevel",new Boolean(true));
						/*may be needed if(specialtyDivision.getDivisionLevel()!=null)
						{
								if(specialtyDivision.getDivisionLevel().getLevelNo()< lastLevel.getLevelNo())
								{
									HRSpecialtyLevel  nextLevel=hrManager.getSpecialtyLevelByLevelNo(specialtyDivision.getDivisionLevel().getLevelNo()+1);
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
						
						
						
						
					if(specialtyDivision.getParent()!=null)
					{
					//needed 	if (specialtyDivision.getParent().getIsLast().equals(new Boolean(true))){
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
				
				
				model.put("formType", "specialty");
		//	********************************************************************************
			return  new ModelAndView("internalDivisionTree", model);
		}


	
		
	}



