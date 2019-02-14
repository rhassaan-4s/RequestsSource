package com._4s_.HR.web.action;

	import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
	import java.util.List;
	import java.util.Map;

	import javax.servlet.ServletException;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;

	import org.springframework.validation.BindException;
	import org.springframework.validation.Errors;
	import org.springframework.web.servlet.ModelAndView;
	import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HRInternalDivision;
	import com._4s_.HR.model.HRQualificationDivision;
import com._4s_.HR.model.HRQualificationLevel;
import com._4s_.HR.model.HRSpecialtyDivision;
import com._4s_.HR.model.HRSpecialtyLevel;
	import com._4s_.HR.service.HRManager;
import com._4s_.common.model.Types;
import com._4s_.common.model.TypesData;
import com._4s_.common.web.action.BaseSimpleFormController;
//import com._4s_.gl.model.Account;
//import com._4s_.gl.model.AccountFinancialYearSummary;
//import com._4s_.gl.model.AccountLevel;
//import com._4s_.gl.model.AnalysisCategoryAccount;
//import com._4s_.gl.model.AnalysisLeafAccount;
//import com._4s_.gl.model.FinancialCategoryAccount;
//import com._4s_.gl.model.FinancialLeafAccount;
//import com._4s_.gl.web.binders.AccountBinderByCode;
import com.jenkov.prizetags.tree.itf.ITree;


public class QualificationDivisionForm  extends  TreeFormController {
			/*private HRManager hrManager;
			
			public HRManager getHrManager() {
				return hrManager;
			}
			public void setHrManager(HRManager hrManager) {
				this.hrManager = hrManager;
			}
			*/

				//**************************************** formBackingObject ***********************************************\\
				protected Object formBackingObject(HttpServletRequest request) throws ServletException 
				{
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

					HRQualificationDivision result = null;
					String parentId = request.getParameter("parentId");
					String qualificationDivisionId = request.getParameter("resultId");
					log.debug("qualificationDivisionId"+qualificationDivisionId);
				    className=HRQualificationDivision.class;
					// check if there is a parent  to be created
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>parentId "+parentId);
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>time "+new Date());
					if (parentId != null && parentId.length() >0){
						log.debug(">>>>>>>>>>>>>>>>>>>>>>>parentId not null");
						HRQualificationDivision parent = (HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class,new Long(parentId));
						// if this parent is the last category account
						// then the account to be created is a leaf account
						// else it is a category account
						log.debug("parent.getIsLast()>>>>>>>>>>>>>>>>>>"+parent.getIsLast());
						if(parent.getIsLast()!=null && !parent.getIsLast().equals(""))
						{
							if(parent.getIsLast().booleanValue() == true){
								log.debug(">>>>>>>>>>>>>>>>>>>>>>>parentId is last ");
						
									log.debug(">>>>>>>>>>>>>>>>>>>>>>>creating new leaf  ");
									HRQualificationDivision child = new HRQualificationDivision();
									//setting is Last
									child.setIsLast(new Boolean(false));
									// setting account level
								//	child.setDivisionLevel(hrManager.getLastLevel());
									// setting parentAccount
									child.setParent((HRQualificationDivision)parent);
									//setting short code and long code
									List leafs = ((HRQualificationDivision)parent).getChilds();
									List codesList = hrManager.generateCodesList(leafs,"HRQualificationDivision");
									child.setCode(hrManager.zeroFill(codesList.toArray(),3));
								 child.setLongCode(parent.getLongCode().concat(child.getCode()));
	
									result = child;
								
								
							}
							
							else{
								
								log.debug("inside else where there is  parent is not last one");
								
									HRQualificationDivision child = new HRQualificationDivision();
									//setting is Last
									child.setIsLast(new Boolean(false));
									// setting account level
									//child.setDivisionLevel(hrManager.getLevelByLevelNo(parent.getDivisionLevel().getLevelNo() +1));
									// setting parentAccount
									child.setParent((HRQualificationDivision)parent);
									//setting short code and long code
									List siblings = hrManager.getCategoryAccountsByParentCategory(parent.getId(),HRQualificationDivision.class);
									List codesList = hrManager.generateCodesList(siblings,"HRQualificationDivision");
									//log.debug(">>>>>>>>>>>>>>>>>>>>>>>> "+child.getDivisionLevel().getLength());
									  child.setCode(hrManager.zeroFill(codesList.toArray(),3));
									log.debug("parent>>>>>>>>>>>>>>>>>>>"+parent);
									if(parent!=null)
									{
										log.debug("parent2222>>>>>>>>>>>>>>>>>>>"+parent);
									child.setLongCode(parent.getLongCode().concat(child.getCode()));
									}
									result = child;
								
							

							}	
						}
						// creating categoryAccount
						else{
							
							log.debug("inside else where there is  parent is not last one");
							
								HRQualificationDivision child = new HRQualificationDivision();
								//setting is Last
								child.setIsLast(new Boolean(false));
								// setting account level
								//child.setDivisionLevel(hrManager.getLevelByLevelNo(parent.getDivisionLevel().getLevelNo() +1));
								// setting parentAccount
								child.setParent((HRQualificationDivision)parent);
								//setting short code and long code
								List siblings = hrManager.getCategoryAccountsByParentCategory(parent.getId(),HRQualificationDivision.class);
								List codesList = hrManager.generateCodesList(siblings,"HRQualificationDivision");
								//log.debug(">>>>>>>>>>>>>>>>>>>>>>>> "+child.getDivisionLevel().getLength());
								  child.setCode(hrManager.zeroFill(codesList.toArray(),3));
								log.debug("parent>>>>>>>>>>>>>>>>>>>"+parent);
								if(parent!=null)
								{
									log.debug("parent2222>>>>>>>>>>>>>>>>>>>"+parent);
								child.setLongCode(parent.getLongCode().concat(child.getCode()));
								}
								result = child;
							
						

						}	
						
					}
					// if there is no parent for the account to be created
					else{
						log.debug("inside else where there is no parent");
							HRQualificationDivision intDivision = new HRQualificationDivision();
							//setting is Last
							intDivision.setIsLast(new Boolean(false));
							// setting account level
						  //intDivision.setDivisionLevel(hrManager.getLevelByLevelNo(1));
							//setting short code and long code
							List siblings = hrManager.getParents(HRQualificationDivision.class);
//							int size = siblingAccounts.size() + 1;
//							String nextNo = new Integer(size).toString();
//							String code = StringUtils.leftPad(nextNo, categoryAccount.getLevel().getLength(),"0");
							
							List codesList = hrManager.generateCodesList(siblings,"HRQualificationDivision");
							String code = hrManager.zeroFill(codesList.toArray(),3);
							intDivision.setCode(code);
							intDivision.setLongCode(intDivision.getCode());
							log.debug("code>?>>>>>>>>>>>>>>>>>>>>>"+code);
							log.debug("longCode>>>>>>>>>>>>"+intDivision.getLongCode());
							result = intDivision;
						
					
					}

					/**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**/
					/**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**/
					
					  /* needed after solving level problem	HRQualificationLevel lvl = (HRQualificationLevel)hrManager.getLastLevel();
						if(lvl.getLevelNo() == result.getDivisionLevel().getLevelNo()+1 )
						{
							result.setIsLast(true);
						}*/
					
					/**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**/
					if (qualificationDivisionId != null && qualificationDivisionId.length() > 0){
						log.debug("qualificationDivisionId != null:::::::::::::"+qualificationDivisionId);
						result = (HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class,new Long(qualificationDivisionId));

					}
					
				
					log.debug("qualificationDivision>>>>>>>>>"+result);
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				   return result;
				}
			//**************************************** referenceData ***********************************************\\
				protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
				{
					log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
					HRQualificationDivision result=(HRQualificationDivision)command;
					log.debug("qualificationDivision>>>>>>>>>>>"+result);
					Map model=new HashMap();
					boolean isLast = false;
					String resultId=request.getParameter("resultId");
					log.debug("resultId>>>>>>"+resultId);
					model.put("resultId",resultId);
					String isLeaf = request.getParameter("isLeaf");
					String parentId = request.getParameter("parentId");
					log.debug("parentId>>>>>>>>>>>>>>>>"+parentId);
					String copyId=request.getParameter("copyId");
					log.debug("copyId>>>>>>>>>>>>>>>>"+copyId);
					ITree qualificationDivisionTree = hrManager.createTreeIfNotFound(request,"HRQualificationDivision");
					model.put("qualificationDivisionTree",qualificationDivisionTree);
				
                
					
					if (result.getIsLast()) {
						isLast = true;
					}
				
					HRQualificationDivision parent=null;
					if(parentId!=null && !parentId.equals(""))
					{
						parent=(HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class, new Long(parentId));
						HRQualificationLevel level=null;
						if(parent.getDivisionLevel()==null)
						{
						   level=(HRQualificationLevel)hrManager.getObject(HRQualificationLevel.class, new Long(parent.getCode()));
						   model.put("divisionLevels",hrManager.getLevelsByDivisionParentId(level.getLevelNo(),HRQualificationLevel.class));

						}
						else
						{
							model.put("divisionLevels",hrManager.getLevelsByDivisionParentId(parent.getDivisionLevel().getLevelNo()+1,HRQualificationLevel.class));

						}
					}
					else
					{
						model.put("divisionLevels", hrManager.getObjects(HRQualificationLevel.class));
					}
					
					model.put("parentId", parentId);
					model.put("parent", parent);
					model.put("isLeaf", isLeaf);
                    model.put("copyId", copyId);
					model.put("getIsLast", isLast);
					model.put("long",result.getCode());
					model.put("formType", "qualification");
					model.put("resultId", resultId);
					model.put("divList", hrManager.getObjects(HRQualificationDivision.class));
					
                    log.debug("hrManager.getObjects(HRQualificationLevel.class)::::::://////////"+hrManager.getObjects(HRQualificationLevel.class));
                    log.debug("hrManager.getObjects(HRQualificationLevel.class)::::::://////////"+hrManager.getObjects(HRQualificationLevel.class).size());
					// deciding whether a parent category account can be added or not
					int parentsSize = hrManager.getParents(HRQualificationDivision.class).size();
					HRQualificationLevel level = hrManager.getQualLevelByLevelNo(new Integer(1));
					BigInteger bigInt = new BigInteger("10");
					int maxCode=1;
					 maxCode = bigInt.pow(level.getLength().intValue()).intValue() - 1;
					log.debug(">>>>>>>>>>>>>>>>>>>>>>maxCode "+maxCode);  
					if (parentsSize < maxCode){	
						log.debug(">>>>>>>>>>>>>>>>>>>>>>parentsSize < maxCode ");
						model.put("canAddCategory","true");
					}
					else{
						log.debug(">>>>>>>>>>>>>>>>>>>>>>parentsSize > maxCode ");
						model.put("canAddCategory","false");
					}


					// deciding whether isLast could be edited or not
						if
						(result.getChilds()!=null)
						{
							if (result.getChilds().size() == 0){
								model.put("canEditIsLast","true");
							}
							
						}
						else
						{
							model.put("canEditIsLast","false");
						}
					

				

					log.debug(">>>>>>>>>>>>>>>>>>>> isLast "+model.get("canEditIsLast"));
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>end referenceDate()");

					// parent account long code
							
						if (result.getParent() != null){
							model.put("parentLongCode",result.getParent().getLongCode().toString());
						}
					
					
					
					

					/**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**/
					/**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**/
					
						/* needed after solving level problem HRQualificationLevel lvl = (HRQualificationLevel)hrManager.getLastLevel();
						if(lvl.getLevelNo() == result.getDivisionLevel().getLevelNo()+1 )
						{
							model.put("lastCategory","yes");
						}*/

						//use copyId to decide which level to copy from
						HRQualificationDivision intDiv=null;
						HRQualificationLevel intLev=null;
						if(copyId!=null && !copyId.equals(""))
						{
							intDiv=(HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class, new Long(copyId));
							intLev=hrManager.getQualLevelByLevelNo(intDiv.getDivisionLevel().getLevelNo());
						}
						List allIntDiv=hrManager.getObjectsByParameter(HRQualificationDivision.class,"divisionLevel",intLev);
						Iterator itr=allIntDiv.iterator();
						while(itr.hasNext())
						{
							HRQualificationDivision intD=(HRQualificationDivision)itr.next();
							log.debug("intD.getChilds().size>>>>>>>>>>>>>>>"+intD.getChilds().size());
							if(intD.getId().equals(intDiv.getId()))
							{
								allIntDiv.remove(intD);
								break;
							}
						}
						log.debug("allIntDiv.size()>>>>>>>>>>>>>>>>"+allIntDiv.size());
						model.put("intDivList", allIntDiv);
						
						
						// deciding whether the account acn be edited or not
						/*Boolean canBeUpdated = new Boolean(true);
						int transactionListSize = 0;
						if ((account.getAccountClass().equals("FinancialLeafAccount")) || (account.getAccountClass().equals("AnalysisLeafAccount"))){
							log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>FinancialLeafAccount || AnalysisLeafAccount ");
							if (account.getAccountClass().equals("FinancialLeafAccount")){
								log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>FinancialLeafAccount ");
								transactionListSize = mgr.getTransactionsListSizeForAccount("financial",account.getId());
							}
							else if (account.getAccountClass().equals("AnalysisLeafAccount")){
								log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>AnalysisLeafAccount ");
								transactionListSize = mgr.getTransactionsListSizeForAccount("analysis",account.getId());
							}

							log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>listSize "+transactionListSize);
							// if no transactions are done on this account
							if (transactionListSize > 0){
								canBeUpdated = new Boolean(false);
							}
						}
	                   Types reportTypesss = (Types) mgr.getObjectByParameter(Types.class,"description", "reportType");
						Types canBeAnalyzedsss = (Types) mgr.getObjectByParameter(Types.class,"description", "canBeAnalyzed");
						List reportType = commonMgr.getDataByTypes(reportTypesss);
						List currency = mgr.getActiveCurrency();
						List canBeAnalyzed = commonMgr.getDataByTypes(canBeAnalyzedsss);
						
						model.put("reportType", reportType);
						model.put("currency", currency);

						model.put("canBeAnalyzed", canBeAnalyzed);
					
					    
					    
					    Types accountTypeHelperClass = (Types) mgr.getObject(Types.class , new Long(6)); // accountType
						List allAccountTypes = mgr.getObjectsByParameter(TypesData.class ,"type", accountTypeHelperClass); 
						model.put("allAccountTypes",allAccountTypes);
	                    */
						log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

					return model;
				}
				
				//**************************************** onBind ***********************************************\\	
				protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception
				{
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
					
				
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				}
				
			//**************************************** onBindAndValidate ***********************************************\\		
				protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
				{
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
					HRQualificationDivision result=(HRQualificationDivision)command;
					String copyId=request.getParameter("copyId");
					String parentId=request.getParameter("parentId");
					HRQualificationDivision parent=null;
					if(parentId!=null && !parentId.equals(""))
					{
						parent=(HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class, new Long(parentId));
					}
				
					/*if(errors.getErrorCount()==0)
					{
						HRQualificationDivision gra=(HRQualificationDivision)hrManager.getObjectByParameter(HRQualificationDivision.class,"code",result.getCode());
						if(gra!=null && (!gra.getId().equals(result.getId())))
								{
							       errors.rejectValue("code", "hr.error.codeExists","code exists");
								}
					}*/
					if(copyId==null || copyId.equals(""))
					{
						if(errors.getErrorCount()==0)
						{
						
							if(result.getArdesc()==null || result.getArdesc().equals(""))
							{
								errors.rejectValue("ardesc", "commons.errors.requiredFields");
							}
						}
					
						if(errors.getErrorCount()==0)
						{
							if(result.getDivisionLevel()==null)
							{
								if(parent!=null && !parent.equals(""))
								{
									
									if(parent.getDivisionLevel()!=null)
									{
								      errors.rejectValue("divisionLevel", "commons.errors.requiredFields");
								    }
									else
									{
										HRQualificationLevel level=(HRQualificationLevel)hrManager.getObject(HRQualificationLevel.class,new Long(parent.getCode()));
										result.setDivisionLevel(level);
									}
								}
								else
								{
									errors.rejectValue("divisionLevel", "commons.errors.requiredFields");
								}
							}
						}
					
					
					/*	else if(result.getArdesc()!=null && !result.getArdesc().equals(""))
						{
							log.debug("errors.getErrorCount() " + errors.getErrorCount());
				

							if(!result.getArdesc().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

							errors.reject("hr.errors.invalidArabicName");

							}

							}*/
					
					
					/*if(errors.getErrorCount()==0)
					{
					
						if(result.getEndesc()==null || result.getEndesc().equals(""))
						{
							errors.rejectValue("endesc", "commons.errors.requiredFields");
						}
						
						else if(result.getEndesc()!=null && !result.getEndesc().equals(""))
						{
							log.debug("errors.getErrorCount() " + errors.getErrorCount());

							

							

							if(!result.getEndesc().matches("([a-zA-Z]+\\s{0,1})*"))

								errors.reject("hr.errors.invalidEnglishName");

							}

							}*/
				//******************************************************************************	
					Boolean newObject = new Boolean(false);
					if (result.getId() == null){
						newObject = new Boolean(true);
					}


					if (newObject.booleanValue() == false)
					{
						log.debug(">>>>>>newAObject.booleanValue() == false");
						// validate if the new long code is reserved "Duplicated"
						String new_LongCode =new String("");	// which will be validated

						
							log.debug(">>>>>>if(isFinancial.equals(true))");
                            
							if(result.getParent() != null && result.getParent().getIsLast()!=null){
							if (result.getParent().getIsLast().booleanValue()==false)
							{
								log.debug(">>>>>>result.getParent().getIsLast().booleanValue()==false)");

								
									String ParentLongCode = result.getParent().getLongCode().toString();
									new_LongCode = ParentLongCode + result.getCode();
								}
							}
								else{	//have no parent
									new_LongCode = result.getCode().toString();
								}
							
							// now you have the new_LongCode.
							log.debug(">>>>>> new_LongCode ****** : = " + new_LongCode);
						
							
						
						

						//start validation
						

						log.debug(">>> call  validateUpdateCodeNotDuplicated");

						validateUpdateCodeNotDuplicated(result,errors,new_LongCode);		
						log.debug(">>> validateUpdateCodeNotDuplicated   called");

					} // end of if (newAccount.booleanValue() == false)

					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				}
				}
				public void validateUpdateCodeNotDuplicated(HRQualificationDivision intDiv, Errors errors , String longCode)
				{

					if (intDiv.getId() != null)
					{
						
						log.debug(">>> at validateUpdateCodeNotDuplicated                           >>>>>>>>>>>LongCode:" + longCode );
						log.debug(">>> at validateUpdateCodeNotDuplicated                     my instance      >>>>> LongCode:" + intDiv.getLongCode() );

						List intDivs = hrManager.getAllByCode(longCode,HRQualificationDivision.class);
						log.debug(">>> at validateUpdateCodeNotDuplicated                           >>>>>>>>>>>accounts.size() " + intDivs.size() );
						if (intDivs.size() > 1 ){ // coz the updated object is one of them
							errors.rejectValue("longCode","gl.errors.codeIsDuplicates","");
						}
						if (intDivs.size() == 1 )
						{
							if(!intDiv.getLongCode().equals(longCode) )
							{// is to make sure that the user has updated the account record and also updated so this record is duplicated
								errors.rejectValue("longCode","hr.error.codeExists","code exists");
							}
						}

					}
				}
				

				
				
				//**************************************** onSubmit ***********************************************\\	
			/*	public ModelAndView onSubmit(HttpServletRequest request,
						HttpServletResponse response, Object command, BindException errors)throws Exception 
				{
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
					HRQualificationDivision result=(HRQualificationDivision)command;
					log.debug("qualificationDivision.getId()__________>>>>>>>>>>>>>>>"+result.getId());
					
					if(result.getParent()==null)
					{
						result.setLongCode(result.getCode());
					}
					else
					{
						result.setLongCode(result.getParent().getLongCode().concat(result.getCode()));
					}
					Boolean newIntDiv = new Boolean(false);
					if (result.getId() == null){
						newIntDiv = new Boolean(true);
					}
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>qualificationDivision.getId() "+result.getId());

					String savedOldLongCode=new String(""); 

					savedOldLongCode = result.getLongCode().toString(); // copy Long Code

					log.debug(">>>>>start of on submit>>>  now SAVED long Code is: " +savedOldLongCode);

					
					
					
						 HRQualificationLevel lvl = new HRQualificationLevel();
						lvl = (HRQualificationLevel)hrManager.getLastLevel();
						if(lvl.getLevelNo() ==result.getDivisionLevel().getLevelNo()+1)
							result.setIsLast(true);
				
					


						List<HRQualificationDivision> childs = result.getChilds();
						if(childs != null && childs.size() > 0){
							Iterator itr = childs.iterator();
							while (itr.hasNext()) {
								HRQualificationDivision child = (HRQualificationDivision) itr.next();

								hrManager.saveObject(child);
							}
						}
					hrManager.saveObject(result);
					

					if (newIntDiv.booleanValue() == true){

						log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> intDivision.getId() == null");
						String parentId = null;
						ITree tree;
						
							tree = (ITree) request.getSession().getAttribute("qualificationDivTree");
							if(result.getParent()!=null)
							{
									parentId = result.getParent().getId().toString();
									
							}
							else
							{
								result.setLongCode(result.getCode());
							}
							log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>> before addNode ");
							hrManager.addNode(result.getId().toString(), result.getArdesc(),parentId, tree);
							//request.getSession().setAttribute("financialTree", tree);

							request.getSession().removeAttribute("qualificationDivTree");
							tree = hrManager.createTreeIfNotFound(request,"HRQualificationDivision"); // re-construce the  tree again 
							request.getSession().setAttribute("qualificationDivTree" , tree);


					}
					else	//in case of update
					{
						// this code for update the category account and update all children long code(s)..
						ITree tree ;

						log.debug(">>>>>>>>>>>>>>>>  this process is update object");
						
									if(result.getParent() != null){
									String ParentLongCode = result.getParent().getLongCode().toString();
									result.setLongCode(ParentLongCode + result.getCode());
								}
								else{	//have no parent
									result.setLongCode(result.getCode());
								}
							//no else
							tree = (ITree) request.getSession().getAttribute("qualificationDivTree");
							hrManager.updateNodeDescription(result.getId().toString(), result.getArdesc(), tree);

						}
						
						if(savedOldLongCode == null || savedOldLongCode.equals(""))
							log.debug("############################# Empty saved old long code");
						else
							hrManager.changeCode(result,result.getCode(),savedOldLongCode);


					log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					return new ModelAndView(new RedirectView(getSuccessView()));
				}*/
				
				public ModelAndView onSubmit(HttpServletRequest request,
						HttpServletResponse response, Object command, BindException errors)throws Exception 
				{
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start child onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
					HRQualificationDivision result=(HRQualificationDivision)command;
					String parentId = request.getParameter("parentId");
					String resultId = request.getParameter("resultId");
					log.debug("resultId"+resultId);
					log.debug("result.getArdesc()>>>>>>>>>>"+result.getArdesc());
					log.debug("result.getEndesc()>>>>>>>>>>"+result.getEndesc());
					List divList=new ArrayList();
					
					if(resultId==null || resultId.equals(""))
					{
						if(parentId!=null && !parentId.equals(""))
						{
							divList=hrManager.getExistingDivisionsForparent(HRQualificationDivision.class,new Long(parentId),result.getDivisionLevel().getId().toString());
							log.debug("divList>>>>>>>>>>>"+divList);
						}
						HRQualificationDivision div1=null;
						if(!divList.isEmpty())
						{
	                      div1 =(HRQualificationDivision)divList.get(0);
						}
						if(div1==null)
						{
							log.debug("inside div1==null:::::::::::::::");
							HRQualificationDivision div=new HRQualificationDivision();
							div.setArdesc(result.getDivisionLevel().getName());
							div.setEndesc(result.getDivisionLevel().getEname());
							div.setParent(result.getParent());
							div.setCode(result.getDivisionLevel().getId().toString());
							if(parentId!=null && !parentId.equals(""))
							{
							  div.setLongCode(result.getParent().getLongCode().concat(result.getDivisionLevel().getId().toString()));
							  result.setParent(div);
							  result.setLongCode(result.getParent().getLongCode().concat(result.getCode()));
								
							}
							else
							{
							
								div.setLongCode(result.getDivisionLevel().getId().toString());
								result.setParent(div);
								result.setLongCode(div.getLongCode().concat(result.getCode()));	
							}
							//div.setDivisionLevel(divisionLevel);
							
							
							hrManager.saveObject(div);
						}
						
						else
						{
							log.debug("inside parent is new one and never added before");
							if(parentId!=null && !parentId.equals(""))
							{
							  result.setParent(div1);
							  result.setLongCode(result.getParent().getLongCode().concat(result.getCode()));
								
							}
							else
							{
								
								result.setParent(div1);
								result.setLongCode(div1.getLongCode().concat(result.getCode()));	
							}
							
							hrManager.saveObject(div1);
						}
					}
					
					  hrManager.saveObject(result);
					  request.getSession().removeAttribute("qualificationDivTree");
					log.debug("result.getArdesc()>>>>>>>>>>"+result.getArdesc());
					log.debug("result.getEndesc()>>>>>>>>>>"+result.getEndesc());
			
					log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End child onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					return new ModelAndView(new RedirectView(getSuccessView()));
				}
				
}