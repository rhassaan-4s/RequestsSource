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
import com._4s_.HR.model.HRInternalLevel;
	import com._4s_.HR.model.HRSpecialtyDivision;
import com._4s_.HR.model.HRSpecialtyLevel;
import com._4s_.HR.service.HRManager;

import com.jenkov.prizetags.tree.itf.ITree;


public class SpecialtyDivisionForm  extends  TreeFormController {
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

					HRSpecialtyDivision result = null;
					String parentId = request.getParameter("parentId");
					String specialtyDivisionId = request.getParameter("resultId");
					log.debug("specialtyDivisionId"+specialtyDivisionId);
				    className=HRSpecialtyDivision.class;
					// check if there is a parent  to be created
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>parentId "+parentId);
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>time "+new Date());
					if (parentId != null && parentId.length() >0){
						log.debug(">>>>>>>>>>>>>>>>>>>>>>>parentId not null");
						HRSpecialtyDivision parent = (HRSpecialtyDivision)hrManager.getObject(HRSpecialtyDivision.class,new Long(parentId));
						// if this parent is the last category account
						// then the account to be created is a leaf account
						// else it is a category account
						log.debug("parent.getIsLast()>>>>>>>>>>>>>>>>>>"+parent.getIsLast());
						if(parent.getIsLast()!=null && !parent.getIsLast().equals(""))
						{
							if(parent.getIsLast().booleanValue() == true){
								log.debug(">>>>>>>>>>>>>>>>>>>>>>>parentId is last ");
						
									log.debug(">>>>>>>>>>>>>>>>>>>>>>>creating new leaf  ");
									HRSpecialtyDivision child = new HRSpecialtyDivision();
									//setting is Last
									child.setIsLast(new Boolean(false));
									// setting account level
								//	child.setDivisionLevel(hrManager.getLastLevel());
									// setting parentAccount
									child.setParent((HRSpecialtyDivision)parent);
									//setting short code and long code
									List leafs = ((HRSpecialtyDivision)parent).getChilds();
									List codesList = hrManager.generateCodesList(leafs,"HRSpecialtyDivision");
									child.setCode(hrManager.zeroFill(codesList.toArray(),3));
								 child.setLongCode(parent.getLongCode().concat(child.getCode()));
	
									result = child;
								
								
							}
							
							else{
								
								log.debug("inside else where there is  parent is not last one");
								
									HRSpecialtyDivision child = new HRSpecialtyDivision();
									//setting is Last
									child.setIsLast(new Boolean(false));
									// setting account level
									//child.setDivisionLevel(hrManager.getLevelByLevelNo(parent.getDivisionLevel().getLevelNo() +1));
									// setting parentAccount
									child.setParent((HRSpecialtyDivision)parent);
									//setting short code and long code
									List siblings = hrManager.getCategoryAccountsByParentCategory(parent.getId(),HRSpecialtyDivision.class);
									List codesList = hrManager.generateCodesList(siblings,"HRSpecialtyDivision");
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
							
								HRSpecialtyDivision child = new HRSpecialtyDivision();
								//setting is Last
								child.setIsLast(new Boolean(false));
								// setting account level
								//child.setDivisionLevel(hrManager.getLevelByLevelNo(parent.getDivisionLevel().getLevelNo() +1));
								// setting parentAccount
								child.setParent((HRSpecialtyDivision)parent);
								//setting short code and long code
								List siblings = hrManager.getCategoryAccountsByParentCategory(parent.getId(),HRSpecialtyDivision.class);
								List codesList = hrManager.generateCodesList(siblings,"HRSpecialtyDivision");
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
							HRSpecialtyDivision intDivision = new HRSpecialtyDivision();
							//setting is Last
							intDivision.setIsLast(new Boolean(false));
							// setting account level
						  //intDivision.setDivisionLevel(hrManager.getLevelByLevelNo(1));
							//setting short code and long code
							List siblings = hrManager.getParents(HRSpecialtyDivision.class);
//							int size = siblingAccounts.size() + 1;
//							String nextNo = new Integer(size).toString();
//							String code = StringUtils.leftPad(nextNo, categoryAccount.getLevel().getLength(),"0");
							
							List codesList = hrManager.generateCodesList(siblings,"HRSpecialtyDivision");
							String code = hrManager.zeroFill(codesList.toArray(),3);
							intDivision.setCode(code);
							intDivision.setLongCode(intDivision.getCode());
							log.debug("code>?>>>>>>>>>>>>>>>>>>>>>"+code);
							log.debug("longCode>>>>>>>>>>>>"+intDivision.getLongCode());
							result = intDivision;
						
					
					}

					/**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**/
					/**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**/
					
					  /* needed after solving level problem	HRSpecialtyLevel lvl = (HRSpecialtyLevel)hrManager.getLastLevel();
						if(lvl.getLevelNo() == result.getDivisionLevel().getLevelNo()+1 )
						{
							result.setIsLast(true);
						}*/
					
					/**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**/
					if (specialtyDivisionId != null && specialtyDivisionId.length() > 0){
						log.debug("specialtyDivisionId != null:::::::::::::"+specialtyDivisionId);
						result = (HRSpecialtyDivision)hrManager.getObject(HRSpecialtyDivision.class,new Long(specialtyDivisionId));

					}
					
				
					log.debug("specialtyDivision>>>>>>>>>"+result);
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				   return result;
				}
			//**************************************** referenceData ***********************************************\\
				protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
				{
					log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
					HRSpecialtyDivision result=(HRSpecialtyDivision)command;
					log.debug("specialtyDivision>>>>>>>>>>>"+result);
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
					ITree specialtyDivisionTree = hrManager.createTreeIfNotFound(request,"HRSpecialtyDivision");
					model.put("specialtyDivisionTree",specialtyDivisionTree);
				
                
					
					/*if (result.getIsLast()) {
						isLast = true;
					}*/
				
					HRSpecialtyDivision parent=null;
					if(parentId!=null && !parentId.equals(""))
					{
						parent=(HRSpecialtyDivision)hrManager.getObject(HRSpecialtyDivision.class, new Long(parentId));
						HRSpecialtyLevel level=null;
						if(parent.getDivisionLevel()==null)
						{
						   level=(HRSpecialtyLevel)hrManager.getObject(HRSpecialtyLevel.class, new Long(parent.getCode()));
						   model.put("divisionLevels",hrManager.getLevelsByDivisionParentId(level.getLevelNo(),HRSpecialtyLevel.class));

						}
						else
						{
							model.put("divisionLevels",hrManager.getLevelsByDivisionParentId(parent.getDivisionLevel().getLevelNo()+1,HRSpecialtyLevel.class));

						}
					}
					else
					{
						model.put("divisionLevels", hrManager.getObjects(HRSpecialtyLevel.class));
					}
					
					model.put("parentId", parentId);
					model.put("parent", parent);
					model.put("isLeaf", isLeaf);
                    model.put("copyId", copyId);
					model.put("getIsLast", isLast);
					model.put("long",result.getCode());
					model.put("formType", "specialty");
					model.put("resultId", resultId);
					model.put("divList", hrManager.getObjects(HRSpecialtyDivision.class));
					
                    log.debug("hrManager.getObjects(HRSpecialtyLevel.class)::::::://////////"+hrManager.getObjects(HRSpecialtyLevel.class));
                    log.debug("hrManager.getObjects(HRSpecialtyLevel.class)::::::://////////"+hrManager.getObjects(HRSpecialtyLevel.class).size());
					// deciding whether a parent category account can be added or not
					int parentsSize = hrManager.getParents(HRSpecialtyDivision.class).size();
					HRSpecialtyLevel level = hrManager.getSpecialtyLevelByLevelNo(new Integer(1));
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
					
						/* needed after solving level problem HRSpecialtyLevel lvl = (HRSpecialtyLevel)hrManager.getLastLevel();
						if(lvl.getLevelNo() == result.getDivisionLevel().getLevelNo()+1 )
						{
							model.put("lastCategory","yes");
						}*/

						//use copyId to decide which level to copy from
						HRSpecialtyDivision intDiv=null;
						HRSpecialtyLevel intLev=null;
						if(copyId!=null && !copyId.equals(""))
						{
							intDiv=(HRSpecialtyDivision)hrManager.getObject(HRSpecialtyDivision.class, new Long(copyId));
							intLev=hrManager.getSpecialtyLevelByLevelNo(intDiv.getDivisionLevel().getLevelNo());
						}
						List allIntDiv=hrManager.getObjectsByParameter(HRSpecialtyDivision.class,"divisionLevel",intLev);
						Iterator itr=allIntDiv.iterator();
						while(itr.hasNext())
						{
							HRSpecialtyDivision intD=(HRSpecialtyDivision)itr.next();
							log.debug("intD.getChilds().size>>>>>>>>>>>>>>>"+intD.getChilds().size());
							if(intD.getId().equals(intDiv.getId()))
							{
								allIntDiv.remove(intD);
								break;
							}
						}
						log.debug("allIntDiv.size()>>>>>>>>>>>>>>>>"+allIntDiv.size());
						model.put("intDivList", allIntDiv);
						

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
					HRSpecialtyDivision result=(HRSpecialtyDivision)command;
					String copyId=request.getParameter("copyId");
					String parentId=request.getParameter("parentId");
					HRSpecialtyDivision parent=null;
					if(parentId!=null && !parentId.equals(""))
					{
						 parent=(HRSpecialtyDivision)hrManager.getObject(HRSpecialtyDivision.class, new Long(parentId));
					}
				
					/*if(errors.getErrorCount()==0)
					{
						HRSpecialtyDivision gra=(HRSpecialtyDivision)hrManager.getObjectByParameter(HRSpecialtyDivision.class,"code",result.getCode());
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
										HRSpecialtyLevel level=(HRSpecialtyLevel)hrManager.getObject(HRSpecialtyLevel.class,new Long(parent.getCode()));
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
				public void validateUpdateCodeNotDuplicated(HRSpecialtyDivision intDiv, Errors errors , String longCode)
				{

					if (intDiv.getId() != null)
					{
						
						log.debug(">>> at validateUpdateCodeNotDuplicated                           >>>>>>>>>>>LongCode:" + longCode );
						log.debug(">>> at validateUpdateCodeNotDuplicated                     my instance      >>>>> LongCode:" + intDiv.getLongCode() );

						List intDivs = hrManager.getAllByCode(longCode,HRSpecialtyDivision.class);
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
				public ModelAndView onSubmit(HttpServletRequest request,
						HttpServletResponse response, Object command, BindException errors)throws Exception 
				{
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start child onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
					HRSpecialtyDivision result=(HRSpecialtyDivision)command;
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
							divList=hrManager.getExistingDivisionsForparent(HRSpecialtyDivision.class,new Long(parentId),result.getDivisionLevel().getId().toString());
							log.debug("divList>>>>>>>>>>>"+divList);
						}
						HRSpecialtyDivision div1=null;
						if(!divList.isEmpty())
						{
	                      div1 =(HRSpecialtyDivision)divList.get(0);
						}
						if(div1==null)
						{
							log.debug("inside div1==null:::::::::::::::");
							HRSpecialtyDivision div=new HRSpecialtyDivision();
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
					  request.getSession().removeAttribute("specialtyDivTree");
					log.debug("result.getArdesc()>>>>>>>>>>"+result.getArdesc());
					log.debug("result.getEndesc()>>>>>>>>>>"+result.getEndesc());
			
					log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End child onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					return new ModelAndView(new RedirectView(getSuccessView()));
				}
				
}