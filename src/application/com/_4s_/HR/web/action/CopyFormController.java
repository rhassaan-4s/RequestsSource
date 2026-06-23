package com._4s_.HR.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HRGeographicalDivision;
import com._4s_.HR.model.HRGeographicalLevel;
import com._4s_.HR.model.HRInternalDivision;
import com._4s_.HR.model.HRInternalLevel;
import com._4s_.HR.model.HRQualificationDivision;
import com._4s_.HR.model.HRQualificationLevel;
import com._4s_.HR.model.HRSpecialtyDivision;
import com._4s_.HR.model.HRSpecialtyLevel;
import com._4s_.HR.service.HRManager;
import com._4s_.HR.web.command.CopyObjectsCommand;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.LoginUsersRequests;

public class CopyFormController extends BaseSimpleFormController{
	
	private HRManager hrManager;
	
	public HRManager getHrManager() {
		return hrManager;
	}
	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}
	

	//**************************************** formBackingObject ***********************************************\\
	@RequestMapping(method = RequestMethod.GET)  public String initForm(ModelMap model,HttpServletRequest request){
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
         CopyObjectsCommand copyCommand=new CopyObjectsCommand();
         
         String copyId=request.getParameter("copyId");
         log.debug("copyId>>>>>>>>>>"+copyId);
         String className=request.getParameter("className");
         log.debug("className>>>>>>>>>>"+className);
         String copyTo=request.getParameter("copyTo");
         log.debug("copyTo>>>>>>>>>"+copyTo);
         
         
         if(className!=null && !className.equals(""))
 		{
 			if(className.equals("HRInternalDivision"))
 			{
 				log.debug("inside className=HRInternalDivision");
 				HRInternalDivision objectToBeCopied=(HRInternalDivision)hrManager.getObject(HRInternalDivision.class, new Long(copyId));
 				if (copyTo != null && copyTo.length() >0){
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>parentId not null");
					HRInternalDivision parent = (HRInternalDivision)hrManager.getObject(HRInternalDivision.class,new Long(copyTo));
					log.debug("parent.getIsLast()>>>>>>>>>>>>>>>>>>"+parent.getIsLast());
					
								HRInternalDivision newObject = new HRInternalDivision();
									newObject.setIsLast(new Boolean(false));
									if(objectToBeCopied.getDivisionLevel()==null)
									{
										log.debug("inside objectToBeCopied.getDivisionLevel()==null");
										List divList=hrManager.getExistingDivisionsForparent(HRInternalDivision.class,parent.getId(), objectToBeCopied.getCode());
										if(divList==null || divList.isEmpty())
										{
										  newObject.setParent((HRInternalDivision)parent);
										//setting short code and long code
											List leafs = ((HRInternalDivision)parent).getChilds();
											List codesList = hrManager.generateCodesList(leafs,"HRInternalDivision");
											newObject.setCode(objectToBeCopied.getCode());
											log.debug("before setting longCode>>>>>>"+parent.getLongCode());
										   newObject.setLongCode(parent.getLongCode().concat(newObject.getCode()));
										}
										else
										{
											HRInternalDivision existingDiv=(HRInternalDivision)divList.get(0);
											newObject=existingDiv;
										}
 
									}
									
									if(objectToBeCopied.getDivisionLevel()!=null)
									{
										log.debug("inside objectToBeCopied.getDivisionLevel()!=null");
										List divList=hrManager.getExistingDivisionsForParentForCopy(HRInternalDivision.class,objectToBeCopied.getId(), objectToBeCopied.getDivisionLevel().getId().toString());
										if(divList==null || divList.isEmpty())
										{
										  newObject.setParent((HRInternalDivision)parent);
										//setting short code and long code
											List leafs = ((HRInternalDivision)parent).getChilds();
											List codesList = hrManager.generateCodesList(leafs,"HRInternalDivision");
											newObject.setCode(objectToBeCopied.getCode());
											log.debug("before setting longCode>>>>>>"+parent.getLongCode());
										   newObject.setLongCode(parent.getLongCode().concat(newObject.getCode()));
										}
										else
										{
											HRInternalDivision existingDiv=(HRInternalDivision)divList.get(0);
											newObject=existingDiv;
										}
 
									}
								
									
									
								
							 newObject.setDivisionLevel(objectToBeCopied.getDivisionLevel());
				 			copyCommand.setInternalDivision(newObject);
				 				
				}
 			
 					
 						
 			}
 			
 			else if(className.equals("HRQualificationDivision"))
 			{
 				HRQualificationDivision objectToBeCopied=(HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class, new Long(copyId));
 				if (copyTo != null && copyTo.length() >0){
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>parentId not null");
					HRQualificationDivision parent = (HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class,new Long(copyTo));
					// if this parent is the last category 
					// then the account to be created is a leaf 
					// else it is a category account
					log.debug("parent.getIsLast()>>>>>>>>>>>>>>>>>>"+parent.getIsLast());
					
					    HRQualificationDivision newObject = new HRQualificationDivision();
								//setting is Last
								newObject.setIsLast(new Boolean(false));
								if(objectToBeCopied.getDivisionLevel()==null)
								{
									List divList=hrManager.getExistingDivisionsForParentForCopy(HRQualificationDivision.class,parent.getId(), objectToBeCopied.getCode());
									if(divList==null || divList.isEmpty())
									{
										newObject.setParent((HRQualificationDivision)parent);
										//setting short code and long code
										List leafs = ((HRQualificationDivision)parent).getChilds();
										List codesList = hrManager.generateCodesList(leafs,"HRQualificationDivision");
										newObject.setCode(objectToBeCopied.getCode());
									    newObject.setLongCode(parent.getLongCode().concat(newObject.getCode()));
									}
									else
									{
										HRQualificationDivision existingDiv=(HRQualificationDivision)divList.get(0);
										newObject=existingDiv;
									}
								}
								
								
								
							 newObject.setDivisionLevel(objectToBeCopied.getDivisionLevel());
				 			copyCommand.setQualificationDivision(newObject);
				 				
				}
 						
 			}
 			
 			else if(className.equals("HRGeographicalDivision"))
 			{
 				HRGeographicalDivision objectToBeCopied=(HRGeographicalDivision)hrManager.getObject(HRGeographicalDivision.class, new Long(copyId));
 				
 				if (copyTo != null && copyTo.length() >0){
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>parentId not null");
					HRGeographicalDivision parent = (HRGeographicalDivision)hrManager.getObject(HRGeographicalDivision.class,new Long(copyTo));
					// if this parent is the last category 
					// then the account to be created is a leaf 
					// else it is a category account
					log.debug("parent.getIsLast()>>>>>>>>>>>>>>>>>>"+parent.getIsLast());
					
					HRGeographicalDivision newObject = new HRGeographicalDivision();
								//setting is Last
								newObject.setIsLast(new Boolean(false));
								if(objectToBeCopied.getDivisionLevel()==null)
								{
									List divList=hrManager.getExistingDivisionsForParentForCopy(HRGeographicalDivision.class,parent.getId(), objectToBeCopied.getCode());
									if(divList==null || divList.isEmpty())
									{
										newObject.setParent((HRGeographicalDivision)parent);
										//setting short code and long code
										List leafs = ((HRGeographicalDivision)parent).getChilds();
										List codesList = hrManager.generateCodesList(leafs,"HRGeographicalDivision");
										newObject.setCode(objectToBeCopied.getCode());
									    newObject.setLongCode(parent.getLongCode().concat(newObject.getCode()));
									}
									else
									{
										HRGeographicalDivision existingDiv=(HRGeographicalDivision)divList.get(0);
										newObject=existingDiv;
									}

								}
								
							
								
								
								
							 newObject.setDivisionLevel(objectToBeCopied.getDivisionLevel());
				 			copyCommand.setGeographicalDivision(newObject);
				 				
				}
 				}
 			
 			else if(className.equals("HRSpecialtyDivision"))
 			{
 				HRSpecialtyDivision objectToBeCopied=(HRSpecialtyDivision)hrManager.getObject(HRSpecialtyDivision.class, new Long(copyId));
 				if (copyTo != null && copyTo.length() >0){
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>parentId not null");
					HRSpecialtyDivision parent = (HRSpecialtyDivision)hrManager.getObject(HRSpecialtyDivision.class,new Long(copyTo));
					// if this parent is the last category 
					// then the account to be created is a leaf 
					// else it is a category account
					log.debug("parent.getIsLast()>>>>>>>>>>>>>>>>>>"+parent.getIsLast());
					
					HRSpecialtyDivision newObject = new HRSpecialtyDivision();
								//setting is Last
								newObject.setIsLast(new Boolean(false));
								if(objectToBeCopied.getDivisionLevel()==null)
								{
									List divList=hrManager.getExistingDivisionsForParentForCopy(HRSpecialtyDivision.class,parent.getId(), objectToBeCopied.getCode());
									if(divList==null || divList.isEmpty())
									{
										newObject.setParent((HRSpecialtyDivision)parent);
										//setting short code and long code
										List leafs = ((HRSpecialtyDivision)parent).getChilds();
										List codesList = hrManager.generateCodesList(leafs,"HRSpecialtyDivision");
										newObject.setCode(objectToBeCopied.getCode());
									   newObject.setLongCode(parent.getLongCode().concat(newObject.getCode()));
									}
									else
									{
										HRSpecialtyDivision existingDiv=(HRSpecialtyDivision)divList.get(0);
										newObject=existingDiv;
									}
								}
								
								
								
							 newObject.setDivisionLevel(objectToBeCopied.getDivisionLevel());
				 			copyCommand.setSpecialtyDivision(newObject);
				 				
				}
 				}
 		}
         
       
     	
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute(copyCommand);
		return "copyForm" ;
	}
	
	
	@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,@ModelAttribute CopyObjectsCommand command
			,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		CopyObjectsCommand copyCommand=(CopyObjectsCommand)command;
	         String copyId=request.getParameter("copyId");
	         log.debug("copyId>>>>>>>>>>"+copyId);
	         String className=request.getParameter("className");
	         log.debug("className>>>>>>>>>>"+className);
	         String copyTo=request.getParameter("copyTo");
	         log.debug("copyTo>>>>>>>>>"+copyTo);
	         
	    Map model=new HashMap();
	    if(className.equals("HRInternalDivision"))
	    {
	    	HRInternalDivision objectToBeCopied=(HRInternalDivision)hrManager.getObject(HRInternalDivision.class, new Long(copyId));
			HRInternalLevel level=objectToBeCopied.getDivisionLevel();
			if(level==null)
			{
				level=(HRInternalLevel)hrManager.getObject(HRInternalLevel.class, new Long(objectToBeCopied.getCode()));
			}
		
	       model.put("level",level);
		
	    }
	    
	    if(className.equals("HRQualificationDivision"))
	    {
	    	HRQualificationDivision objectToBeCopied=(HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class, new Long(copyId));
			HRQualificationLevel level=objectToBeCopied.getDivisionLevel();
			if(level==null)
			{
				level=(HRQualificationLevel)hrManager.getObject(HRQualificationLevel.class, new Long(objectToBeCopied.getCode()));
			}
		
	       model.put("level",level);
		
	    }
	    
	    if(className.equals("HRSpecialtyDivision"))
	    {
	    	HRSpecialtyDivision objectToBeCopied=(HRSpecialtyDivision)hrManager.getObject(HRSpecialtyDivision.class, new Long(copyId));
			HRSpecialtyLevel level=objectToBeCopied.getDivisionLevel();
			if(level==null)
			{
				level=(HRSpecialtyLevel)hrManager.getObject(HRSpecialtyLevel.class, new Long(objectToBeCopied.getCode()));
			}
		
	       model.put("level",level);
		
	    }
	    
	    if(className.equals("HRGeographicalDivision"))
	    {
	    	HRGeographicalDivision objectToBeCopied=(HRGeographicalDivision)hrManager.getObject(HRGeographicalDivision.class, new Long(copyId));
			HRGeographicalLevel level=objectToBeCopied.getDivisionLevel();
		
			if(level==null)
			{
				level=(HRGeographicalLevel)hrManager.getObject(HRGeographicalLevel.class, new Long(objectToBeCopied.getCode()));
			}
		
	       model.put("level",level);
		
	    }
		model.put("copyId",copyId );
		model.put("className",className );
		model.put("copyTo",copyTo);
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
		
	}
	

	//**************************************** onSubmit ***********************************************\\	
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start child onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		CopyObjectsCommand copyCommand=(CopyObjectsCommand)command;
		 String copyId=request.getParameter("copyId");
         log.debug("copyId>>>>>>>>>>"+copyId);
         String className=request.getParameter("className");
         log.debug("className>>>>>>>>>>"+className);
         String copyTo=request.getParameter("copyTo");
         log.debug("copyTo>>>>>>>>>"+copyTo);
         String parentLongCode=request.getParameter("parentLongCode");
         log.debug("parentLongCode>>>>>>>>>"+parentLongCode);

        List childerenList=new ArrayList();
        List divList=new ArrayList();
        
    	if(className!=null && !className.equals(""))
		{
			if(className.equals("HRInternalDivision"))
			{
				log.debug("inside className=HRInternalDivision");
				HRInternalDivision parent = (HRInternalDivision)hrManager.getObject(HRInternalDivision.class,new Long(copyTo));
				HRInternalDivision copiedObject=copyCommand.getInternalDivision();
				HRInternalDivision newForLevel=null;
				if(copiedObject.getDivisionLevel()!=null)
				{
				  	divList=hrManager.getExistingDivisionsForParentForCopy(HRInternalDivision.class,new Long(copyTo),copiedObject.getDivisionLevel().getId().toString());
					log.debug("divList>>>>>>>>>>>"+divList);
		         if(divList.isEmpty() || divList.equals("[]"))
				         {
							HRInternalLevel level=copiedObject.getDivisionLevel();
							newForLevel=new HRInternalDivision();
			 				newForLevel.setArdesc(level.getName());
			 				newForLevel.setEndesc(level.getEname());
			 				newForLevel.setCode(level.getId().toString());
			 				newForLevel.setParent(parent);
			 				newForLevel.setLongCode(parent.getLongCode().concat(level.getId().toString()));
			 				hrManager.saveObject(newForLevel);
			 				copiedObject.setParent(newForLevel);
				         }
		         if(copiedObject.getParent()==null)
		         {
		          copiedObject.setParent((HRInternalDivision)divList.get(0));
		         }
		        //setting short code and long code
		          log.debug("newForLevel>>>>>>>>>>>>>>"+newForLevel);
		        //  log.debug("newForLevel.getChilds()>>>>>>>>>>>>>>"+newForLevel.getChilds());
					List leafs = ((HRInternalDivision)copiedObject.getParent()).getChilds();
					List codesList = hrManager.generateCodesList(leafs,"HRInternalDivision");
					copiedObject.setCode(hrManager.zeroFill(codesList.toArray(),3));
					copiedObject.setLongCode(parent.getLongCode().concat(copiedObject.getCode()));

				}
				else
				{

				  	divList=hrManager.getExistingDivisionsForparent(HRInternalDivision.class,new Long(copyTo),copiedObject.getDivisionLevel().getId().toString());
					log.debug("divList>>>>>>>>>>>"+divList);
		         if(divList.isEmpty())
				         {
							HRInternalLevel level=copiedObject.getDivisionLevel();
							newForLevel=new HRInternalDivision();
			 				newForLevel.setArdesc(level.getName());
			 				newForLevel.setEndesc(level.getEname());
			 				newForLevel.setCode(level.getId().toString());
			 				newForLevel.setParent(parent);
			 				newForLevel.setLongCode(parent.getLongCode().concat(level.getId().toString()));
			 				hrManager.saveObject(newForLevel);
			 				copiedObject.setParent(newForLevel);
				         }
		         if(copiedObject.getParent()==null)
		         {
		          copiedObject.setParent((HRInternalDivision)divList.get(0));
		         }
		        //setting short code and long code
		          log.debug("newForLevel>>>>>>>>>>>>>>"+newForLevel);
		        //  log.debug("newForLevel.getChilds()>>>>>>>>>>>>>>"+newForLevel.getChilds());
					List leafs = ((HRInternalDivision)copiedObject.getParent()).getChilds();
					List codesList = hrManager.generateCodesList(leafs,"HRInternalDivision");
					copiedObject.setCode(hrManager.zeroFill(codesList.toArray(),3));
					copiedObject.setLongCode(parent.getLongCode().concat(copiedObject.getCode()));

				}
	             
				 HRInternalDivision objectToBeCopied=(HRInternalDivision)hrManager.getObject(HRInternalDivision.class,new Long(copyId));
				 childerenList=hrManager.getDivisionChildren(HRInternalDivision.class,objectToBeCopied.getLongCode());
				log.debug("childerenList>>>>>>>>>>>>>>>>>>"+childerenList);
				if(childerenList!=null && !childerenList.isEmpty())
				{
				 childerenList.remove(0);
				}
				log.debug("childerenList after removing first element>>>>>>>>>>>>>>>>>>"+childerenList);
				if(newForLevel!=null)
				{
				  copyCommand.getInternalDivision().setParent(newForLevel);
				}
				/*else
				{
					copyCommand.getInternalDivision().setParent((HRInternalDivision)divList.get(0));
				}*/
				hrManager.saveObject(copyCommand.getInternalDivision());
				childerenList.add(0, copyCommand.getInternalDivision());
				log.debug("copyCommand.getInternalDivision()>>>>>>>>>>"+copyCommand.getInternalDivision().getId());
				HRInternalDivision savedNewChild=null;
		    	for(int i=0;i<childerenList.size()-1;i++)
		    	{
		    		log.debug("inside for of adding childeren i="+i);
		    		HRInternalDivision child=(HRInternalDivision)childerenList.get(i+1);
		    		log.debug("child.getLongCode()>>>>>>>:::::::::"+child.getLongCode());
		    		HRInternalDivision parentForList=(HRInternalDivision)childerenList.get(i);
		    		HRInternalDivision newChild=new HRInternalDivision();
		    		List leafs=new ArrayList();
					log.debug("parent.getIsLast()>>>>>>>>>>>>>>>>>>"+parent.getIsLast());
					
					newChild.setIsLast(new Boolean(false));
					newChild.setParent((HRInternalDivision)parent);
					//setting short code and long code
					if(savedNewChild==null)
					{
						log.debug("inside savedNewChild==null  "+ savedNewChild);
						 newChild.setParent(parentForList);
						 leafs=hrManager.getCategoryAccountsByParentCategory (parentForList.getId(),HRInternalDivision.class);
						 log.debug("leafs>>>>>>>////////"+leafs);
					
					}
					else
					{
						log.debug("inside savedNewChild!=null  "+ savedNewChild);
						
						HRInternalDivision div=null;
						for(int m=0;m<childerenList.size();m++)
						{
							
							if((((HRInternalDivision)childerenList.get(m)).getParent().getId().equals(((HRInternalDivision)childerenList.get(i+1)).getParent().getId())) 
									&& !(((HRInternalDivision)childerenList.get(m)).getId().equals(((HRInternalDivision)childerenList.get(i+1)).getId())))
							{
							 div=(HRInternalDivision)hrManager.getObjectByParameter(HRInternalDivision.class,"oldLongCode", ((HRInternalDivision)childerenList.get(m)).getLongCode());
								log.debug("div>>>>>>>>>>>>>>>"+div);
							}
							
						}
						if(div!=null)
						{HRInternalDivision existDiv=(HRInternalDivision)hrManager.getObjectByParameter(HRInternalDivision.class,"parent",div.getParent());
						log.debug("existDiv>>>>>>>>>>>>>>>"+existDiv);
					
							log.debug("div.getParent()>>>>>>>>"+div.getParent());
							newChild.setParent(div.getParent());
							log.debug("div.parent>>>>>>>>>>>>>>>"+div.getParent());
							HRInternalDivision newParent=newChild.getParent();
							leafs=hrManager.getCategoryAccountsByParentCategory (newParent.getId(),HRInternalDivision.class); 
							
							log.debug("leafs.size>>>>>>>>>>>>>>>"+leafs);
						}
							
						else
						{
							log.debug("div==nullllllllllllll");
							leafs=hrManager.getCategoryAccountsByParentCategory (savedNewChild.getId(),HRInternalDivision.class);
							newChild.setParent(savedNewChild);
						}
					}
					List codesList = hrManager.generateCodesList(leafs,"HRInternalDivision");
					if(child.getDivisionLevel()!=null)
					{
					 newChild.setCode(hrManager.zeroFill(codesList.toArray(),3));
					}
					else
					{
						newChild.setCode(child.getCode());
					}
					newChild.setLongCode(newChild.getParent().getLongCode().concat(newChild.getCode()));
                    newChild.setArdesc(child.getArdesc());
		    		newChild.setEndesc(child.getEndesc());
		    		newChild.setDivisionLevel(child.getDivisionLevel());
		    		newChild.setOldLongCode(child.getLongCode());
		    		hrManager.saveObject(newChild);
		    		savedNewChild=newChild;
		    		
		    	}
		    
		    	 request.getSession().removeAttribute("internalDivTree");
		    	 request.getSession().removeAttribute("internalDivTreeForCopy");
		    	 return new ModelAndView(new RedirectView("internalDivisionTree.html"));
			     
		 	   	
					
			}
			
			else if(className.equals("HRQualificationDivision"))
			{
				log.debug("inside className=HRQualificationDivision");
				HRQualificationDivision parent = (HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class,new Long(copyTo));
				HRQualificationDivision copiedObject=copyCommand.getQualificationDivision();
				HRQualificationDivision newForLevel=null;
				if(copiedObject.getDivisionLevel()!=null)
				{
				  	divList=hrManager.getExistingDivisionsForParentForCopy(HRQualificationDivision.class,new Long(copyTo),copiedObject.getDivisionLevel().getId().toString());
					log.debug("divList>>>>>>>>>>>"+divList);
		         if(divList.isEmpty())
				         {
							HRQualificationLevel level=copiedObject.getDivisionLevel();
							newForLevel=new HRQualificationDivision();
			 				newForLevel.setArdesc(level.getName());
			 				newForLevel.setEndesc(level.getEname());
			 				newForLevel.setCode(level.getId().toString());
			 				newForLevel.setParent(parent);
			 				newForLevel.setLongCode(parent.getLongCode().concat(level.getId().toString()));
			 				hrManager.saveObject(newForLevel);
			 				copiedObject.setParent(newForLevel);
				         }
		         if(copiedObject.getParent()==null)
		         {
		          copiedObject.setParent((HRQualificationDivision)divList.get(0));
		         }
		        //setting short code and long code
		          log.debug("newForLevel>>>>>>>>>>>>>>"+newForLevel);
		        //  log.debug("newForLevel.getChilds()>>>>>>>>>>>>>>"+newForLevel.getChilds());
					List leafs = ((HRQualificationDivision)copiedObject.getParent()).getChilds();
					List codesList = hrManager.generateCodesList(leafs,"HRQualificationDivision");
					copiedObject.setCode(hrManager.zeroFill(codesList.toArray(),3));
					copiedObject.setLongCode(parent.getLongCode().concat(copiedObject.getCode()));

				}
	             
				 HRQualificationDivision objectToBeCopied=(HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class,new Long(copyId));
				 childerenList=hrManager.getDivisionChildren(HRQualificationDivision.class,objectToBeCopied.getLongCode());
				log.debug("childerenList>>>>>>>>>>>>>>>>>>"+childerenList);
				if(childerenList!=null && !childerenList.isEmpty())
				{
				 childerenList.remove(0);
				}
				log.debug("childerenList after removing first element>>>>>>>>>>>>>>>>>>"+childerenList);
				if(newForLevel!=null)
				{
				  copyCommand.getQualificationDivision().setParent(newForLevel);
				}
				/*else
				{
					copyCommand.getQualificationDivision().setParent((HRQualificationDivision)divList.get(0));
				}*/
				hrManager.saveObject(copyCommand.getQualificationDivision());
				childerenList.add(0, copyCommand.getQualificationDivision());
				log.debug("copyCommand.getQualificationDivision()>>>>>>>>>>"+copyCommand.getQualificationDivision().getId());
				HRQualificationDivision savedNewChild=null;
		    	for(int i=0;i<childerenList.size()-1;i++)
		    	{
		    		log.debug("inside for of adding childeren i="+i);
		    		HRQualificationDivision child=(HRQualificationDivision)childerenList.get(i+1);
		    		HRQualificationDivision parentForList=(HRQualificationDivision)childerenList.get(i);
		    		HRQualificationDivision newChild=new HRQualificationDivision();
		    		List leafs=new ArrayList();
					log.debug("parent.getIsLast()>>>>>>>>>>>>>>>>>>"+parent.getIsLast());
					
					newChild.setIsLast(new Boolean(false));
					newChild.setParent((HRQualificationDivision)parent);
					//setting short code and long code
					if(savedNewChild==null)
					{
						log.debug("inside savedNewChild==null  "+ savedNewChild);
					 leafs= hrManager.getCategoryAccountsByParentCategory (parentForList.getId(),HRQualificationDivision.class);
					 newChild.setParent(parentForList);
					}
					else
					{
						log.debug("inside savedNewChild!=null  "+ savedNewChild);
						
						HRQualificationDivision div=null;
							for(int m=0;m<childerenList.size();m++)
							{
								
								if(((HRQualificationDivision)childerenList.get(m)).getParent().getId().equals(((HRQualificationDivision)childerenList.get(i+1)).getParent().getId())
										&& !(((HRQualificationDivision)childerenList.get(m)).getId().equals(((HRQualificationDivision)childerenList.get(i+1)).getId())))
								{
									div=(HRQualificationDivision)hrManager.getObjectByParameter(HRQualificationDivision.class,"oldLongCode", ((HRQualificationDivision)childerenList.get(m)).getLongCode());
								}
								
							}
						
							if(div!=null)
							{
								leafs=hrManager.getCategoryAccountsByParentCategory (div.getParent().getId(),HRQualificationDivision.class); 
								newChild.setParent(div.getParent());
							}
								
							else
							{
								leafs=hrManager.getCategoryAccountsByParentCategory(savedNewChild.getId(),HRQualificationDivision.class); 
								newChild.setParent(savedNewChild);
							}
						
					
					}
					List codesList = hrManager.generateCodesList(leafs,"HRQualificationDivision");
					if(child.getDivisionLevel()!=null)
					{
					 newChild.setCode(hrManager.zeroFill(codesList.toArray(),3));
					}
					else
					{
						newChild.setCode(child.getCode());
					}
					newChild.setLongCode(newChild.getParent().getLongCode().concat(newChild.getCode()));
                    newChild.setArdesc(child.getArdesc());
		    		newChild.setEndesc(child.getEndesc());
		    		newChild.setDivisionLevel(child.getDivisionLevel());
		    		newChild.setOldLongCode(child.getLongCode());
		    		hrManager.saveObject(newChild);
		    		savedNewChild=newChild;
		    		
		    	}
		 	    
		    	request.getSession().removeAttribute("qualificationDivTree");
		 		request.getSession().removeAttribute("qualificationDivTreeForCopy");
		 		return new ModelAndView(new RedirectView("qualificationDivisionTree.html"));
		 	
		 			
			}
			
			else if(className.equals("HRGeographicalDivision"))
			{
				log.debug("inside className=HRGeographicalDivision");
				HRGeographicalDivision parent = (HRGeographicalDivision)hrManager.getObject(HRGeographicalDivision.class,new Long(copyTo));
				HRGeographicalDivision copiedObject=copyCommand.getGeographicalDivision();
				HRGeographicalDivision newForLevel=null;
				if(copiedObject.getDivisionLevel()!=null)
				{
				  	divList=hrManager.getExistingDivisionsForParentForCopy(HRGeographicalDivision.class,new Long(copyTo),copiedObject.getDivisionLevel().getId().toString());
					log.debug("divList>>>>>>>>>>>"+divList);
		         if(divList.isEmpty())
				         {
							HRGeographicalLevel level=copiedObject.getDivisionLevel();
							newForLevel=new HRGeographicalDivision();
			 				newForLevel.setArdesc(level.getName());
			 				newForLevel.setEndesc(level.getEname());
			 				newForLevel.setCode(level.getId().toString());
			 				newForLevel.setParent(parent);
			 				newForLevel.setLongCode(parent.getLongCode().concat(level.getId().toString()));
			 				hrManager.saveObject(newForLevel);
			 				copiedObject.setParent(newForLevel);
				         }
		         if(copiedObject.getParent()==null)
		         {
		          copiedObject.setParent((HRGeographicalDivision)divList.get(0));
		         }
		        //setting short code and long code
		          log.debug("newForLevel>>>>>>>>>>>>>>"+newForLevel);
		        //  log.debug("newForLevel.getChilds()>>>>>>>>>>>>>>"+newForLevel.getChilds());
					List leafs = ((HRGeographicalDivision)copiedObject.getParent()).getChilds();
					List codesList = hrManager.generateCodesList(leafs,"HRGeographicalDivision");
					copiedObject.setCode(hrManager.zeroFill(codesList.toArray(),3));
					copiedObject.setLongCode(parent.getLongCode().concat(copiedObject.getCode()));

				}
	             
				 HRGeographicalDivision objectToBeCopied=(HRGeographicalDivision)hrManager.getObject(HRGeographicalDivision.class,new Long(copyId));
				 childerenList=hrManager.getDivisionChildren(HRGeographicalDivision.class,objectToBeCopied.getLongCode());
				log.debug("childerenList>>>>>>>>>>>>>>>>>>"+childerenList);
				if(childerenList!=null && !childerenList.isEmpty())
				{
				 childerenList.remove(0);
				}
				log.debug("childerenList after removing first element>>>>>>>>>>>>>>>>>>"+childerenList);
				if(newForLevel!=null)
				{
				  copyCommand.getGeographicalDivision().setParent(newForLevel);
				}
				/*else
				{
					copyCommand.getGeographicalDivision().setParent((HRGeographicalDivision)divList.get(0));
				}*/
				hrManager.saveObject(copyCommand.getGeographicalDivision());
				childerenList.add(0, copyCommand.getGeographicalDivision());
				log.debug("copyCommand.getGeographicalDivision()>>>>>>>>>>"+copyCommand.getGeographicalDivision().getId());
				HRGeographicalDivision savedNewChild=null;
		    	for(int i=0;i<childerenList.size()-1;i++)
		    	{
		    		log.debug("inside for of adding childeren i="+i);
		    		HRGeographicalDivision child=(HRGeographicalDivision)childerenList.get(i+1);
		    		log.debug("child>>>>>>>>>>>>>>>"+child.getId());
		    		HRGeographicalDivision parentForList=(HRGeographicalDivision)childerenList.get(i);
		    		log.debug("parentForList>>>>>>>>>>>>>>>>::::::::"+parentForList.getId());
		    		HRGeographicalDivision newChild=new HRGeographicalDivision();
		    		List leafs=new ArrayList();
					log.debug("parent.getIsLast()>>>>>>>>>>>>>>>>>>"+parent.getIsLast());
					
					newChild.setIsLast(new Boolean(false));
					newChild.setParent((HRGeographicalDivision)parent);
					//setting short code and long code
					if(savedNewChild==null)
					{
						log.debug("inside savedNewChild==null  "+ savedNewChild);
					 leafs=hrManager.getCategoryAccountsByParentCategory(parentForList.getId(),HRGeographicalDivision.class); 
					 log.debug("leafs>>>>>>>>>>>"+leafs);
					 newChild.setParent(parentForList);
					}
					else
					{
						log.debug("inside savedNewChild!=null  "+ savedNewChild);
						
						HRGeographicalDivision div=null;
						for(int m=0;m<childerenList.size();m++)
						{
							
							if(((HRGeographicalDivision)childerenList.get(m)).getParent().getId().equals(((HRGeographicalDivision)childerenList.get(i+1)).getParent().getId())
									&& !(((HRGeographicalDivision)childerenList.get(m)).getId().equals(((HRGeographicalDivision)childerenList.get(i+1)).getId())))
							{
								div=(HRGeographicalDivision)hrManager.getObjectByParameter(HRGeographicalDivision.class,"oldLongCode", ((HRGeographicalDivision)childerenList.get(m)).getLongCode());
							}
							
						}
						if(div!=null)
						{
							 log.debug("div!=null>>>>>>>>>>>");
							 log.debug("div.getParent().id>>>>>>>"+div.getParent().getId());
							 log.debug("div.id"+div.getId());
							 
							leafs=hrManager.getCategoryAccountsByParentCategory(div.getParent().getId(),HRGeographicalDivision.class);
							 log.debug("leafs>>>>>>>>>>>"+leafs);
							newChild.setParent(div.getParent());
						}
							
						else
						{
							 log.debug("div==null>>>>>>>>>>>");
							leafs=hrManager.getCategoryAccountsByParentCategory(savedNewChild.getId(),HRGeographicalDivision.class); 
							 log.debug("leafs>>>>>>>>>>>"+leafs);
							newChild.setParent(savedNewChild);
						}
					
					}
					List codesList = hrManager.generateCodesList(leafs,"HRGeographicalDivision");
					if(child.getDivisionLevel()!=null)
					{
					 newChild.setCode(hrManager.zeroFill(codesList.toArray(),3));
					}
					else
					{
						newChild.setCode(child.getCode());
					}
					log.debug("newChild.getParent().getLongCode()>>>>>>>>>>"+newChild.getParent().getLongCode());
					newChild.setLongCode(newChild.getParent().getLongCode().concat(newChild.getCode()));
                    newChild.setArdesc(child.getArdesc());
		    		newChild.setEndesc(child.getEndesc());
		    		newChild.setDivisionLevel(child.getDivisionLevel());
		    		newChild.setOldLongCode(child.getLongCode());
		    		hrManager.saveObject(newChild);
		    		savedNewChild=newChild;
		    		
		    	}
		    	  request.getSession().removeAttribute("geographicalDivTree");
				 request.getSession().removeAttribute("geographicalDivTreeForCopy");
				 return new ModelAndView(new RedirectView("geographicalDivisionTree.html"));
			}
			
			else if(className.equals("HRSpecialtyDivision"))
			{
				log.debug("inside className=HRSpecialtyDivision");
				HRSpecialtyDivision parent = (HRSpecialtyDivision)hrManager.getObject(HRSpecialtyDivision.class,new Long(copyTo));
				HRSpecialtyDivision copiedObject=copyCommand.getSpecialtyDivision();
				HRSpecialtyDivision newForLevel=null;
				if(copiedObject.getDivisionLevel()!=null)
				{
				  	divList=hrManager.getExistingDivisionsForParentForCopy(HRSpecialtyDivision.class,new Long(copyTo),copiedObject.getDivisionLevel().getId().toString());
					log.debug("divList>>>>>>>>>>>"+divList);
		         if(divList.isEmpty())
				         {
							HRSpecialtyLevel level=copiedObject.getDivisionLevel();
							newForLevel=new HRSpecialtyDivision();
			 				newForLevel.setArdesc(level.getName());
			 				newForLevel.setEndesc(level.getEname());
			 				newForLevel.setCode(level.getId().toString());
			 				newForLevel.setParent(parent);
			 				newForLevel.setLongCode(parent.getLongCode().concat(level.getId().toString()));
			 				hrManager.saveObject(newForLevel);
			 				copiedObject.setParent(newForLevel);
				         }
		         if(copiedObject.getParent()==null)
		         {
		          copiedObject.setParent((HRSpecialtyDivision)divList.get(0));
		         }
		        //setting short code and long code
		          log.debug("newForLevel>>>>>>>>>>>>>>"+newForLevel);
		        //  log.debug("newForLevel.getChilds()>>>>>>>>>>>>>>"+newForLevel.getChilds());
					List leafs = ((HRSpecialtyDivision)copiedObject.getParent()).getChilds();
					List codesList = hrManager.generateCodesList(leafs,"HRSpecialtyDivision");
					copiedObject.setCode(hrManager.zeroFill(codesList.toArray(),3));
					copiedObject.setLongCode(parent.getLongCode().concat(copiedObject.getCode()));

				}
	             
				 HRSpecialtyDivision objectToBeCopied=(HRSpecialtyDivision)hrManager.getObject(HRSpecialtyDivision.class,new Long(copyId));
				 childerenList=hrManager.getDivisionChildren(HRSpecialtyDivision.class,objectToBeCopied.getLongCode());
				log.debug("childerenList>>>>>>>>>>>>>>>>>>"+childerenList);
				if(childerenList!=null && !childerenList.isEmpty())
				{
				 childerenList.remove(0);
				}
				log.debug("childerenList after removing first element>>>>>>>>>>>>>>>>>>"+childerenList);
				if(newForLevel!=null)
				{
				  copyCommand.getSpecialtyDivision().setParent(newForLevel);
				}
				/*else
				{
					copyCommand.getSpecialtyDivision().setParent((HRSpecialtyDivision)divList.get(0));
				}*/
				hrManager.saveObject(copyCommand.getSpecialtyDivision());
				childerenList.add(0, copyCommand.getSpecialtyDivision());
				log.debug("copyCommand.getSpecialtyDivision()>>>>>>>>>>"+copyCommand.getSpecialtyDivision().getId());
				HRSpecialtyDivision savedNewChild=null;
		    	for(int i=0;i<childerenList.size()-1;i++)
		    	{
		    		log.debug("inside for of adding childeren i="+i);
		    		HRSpecialtyDivision child=(HRSpecialtyDivision)childerenList.get(i+1);
		    		HRSpecialtyDivision parentForList=(HRSpecialtyDivision)childerenList.get(i);
		    		HRSpecialtyDivision newChild=new HRSpecialtyDivision();
		    		List leafs=new ArrayList();
					log.debug("parent.getIsLast()>>>>>>>>>>>>>>>>>>"+parent.getIsLast());
					
					newChild.setIsLast(new Boolean(false));
					newChild.setParent((HRSpecialtyDivision)parent);
					//setting short code and long code
					if(savedNewChild==null)
					{
						log.debug("inside savedNewChild==null  "+ savedNewChild);
					 leafs=hrManager.getCategoryAccountsByParentCategory (parentForList.getId(),HRSpecialtyDivision.class);
					 newChild.setParent(parentForList);
					}
					else
					{
						log.debug("inside savedNewChild!=null  "+ savedNewChild);
						
						HRSpecialtyDivision div=null;
						for(int m=0;m<childerenList.size();m++)
						{
							
							if(((HRSpecialtyDivision)childerenList.get(m)).getParent().getId().equals(((HRSpecialtyDivision)childerenList.get(i+1)).getParent().getId())
									&& !(((HRSpecialtyDivision)childerenList.get(m)).getId().equals(((HRSpecialtyDivision)childerenList.get(i+1)).getId())))
							{
								div=(HRSpecialtyDivision)hrManager.getObjectByParameter(HRSpecialtyDivision.class,"oldLongCode", ((HRSpecialtyDivision)childerenList.get(m)).getLongCode());
							}
							
						}
					
						if(div!=null)
						{
							leafs=hrManager.getCategoryAccountsByParentCategory (div.getParent().getId(),HRSpecialtyDivision.class);
							newChild.setParent(div.getParent());
						}
							
						else
						{
							leafs=hrManager.getCategoryAccountsByParentCategory (savedNewChild.getId(),HRSpecialtyDivision.class); 
							newChild.setParent(savedNewChild);
						}
						
					}
					List codesList = hrManager.generateCodesList(leafs,"HRSpecialtyDivision");
					if(child.getDivisionLevel()!=null)
					{
					 newChild.setCode(hrManager.zeroFill(codesList.toArray(),3));
					}
					else
					{
						newChild.setCode(child.getCode());
					}
					newChild.setLongCode(newChild.getParent().getLongCode().concat(newChild.getCode()));
                    newChild.setArdesc(child.getArdesc());
		    		newChild.setEndesc(child.getEndesc());
		    		newChild.setDivisionLevel(child.getDivisionLevel());
		    		newChild.setOldLongCode(child.getLongCode());
		    		hrManager.saveObject(newChild);
		    		savedNewChild=newChild;
		    		
		    	}
				
		    	request.getSession().removeAttribute("specialtyDivTree");
		 		request.getSession().removeAttribute("specialtyDivTreeForCopy");	
		 		return new ModelAndView(new RedirectView("specialtyDivisionTree.html"));
			}
		}
    	
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End child onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("internalDivisionTree.html"));
	}

}
