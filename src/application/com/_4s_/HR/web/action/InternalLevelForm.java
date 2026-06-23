package com._4s_.HR.web.action;


	import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HRInternalLevel;
import com._4s_.HR.service.HRManager;
import com._4s_.HR.web.command.LevelsCommand;
import com._4s_.common.web.action.BaseSimpleFormController;

	

public class InternalLevelForm  extends BaseSimpleFormController {

			HRManager hrManager = null;
	      
			public HRManager getHrManager() {
				return hrManager;
			}

			public void setHrManager(HRManager hrManager) {
				this.hrManager = hrManager;
			}
			

		
			protected Object formBackingObject(HttpServletRequest request)
					throws Exception {
				LevelsCommand levelsCommand=null;
				String stringCounter=request.getParameter("counter");
				log.debug("counter>>>>>>>>>>>>>>>>>>>>:::::::"+stringCounter);
				String deleteId=request.getParameter("selectedDeleteId");
				log.debug("deleteId>>>>>>"+deleteId);
				
				if(deleteId!=null && !deleteId.equals(""))
				{
					hrManager.deleteLevel(new Long(deleteId),"HRInternalLevel");
				}
				if(request.getMethod().equals("GET"))
				{
					levelsCommand=new LevelsCommand();
					List levels=hrManager.getObjects(HRInternalLevel.class);
					levelsCommand.setLevels(levels);
					if(levels.isEmpty() || levels==null)
					{
						log.debug("inside levels list ==null"+levels.size());
						HRInternalLevel level=new HRInternalLevel();
						levels.add(level);
						levelsCommand.setLevels(levels);
						log.debug(">>>>>>>>>>>>levelsCommand.getLevels() in get>>>>>>>>>>>"+levelsCommand.getLevels());
						
					}
					
					else
					{
						log.debug("inside levels list !=null"+levels.size());
						List existingLevels=hrManager.getObjectsOrderedByField(HRInternalLevel.class,"levelNo");
						levelsCommand.setLevels(existingLevels);
						
					}
					//request.getSession().setAttribute("levelsCommand", levelsCommand);
				}
				
				else if(request.getMethod().equals("POST"))
				{
					levelsCommand=new LevelsCommand();
					List levels=hrManager.getObjects(HRInternalLevel.class);
					levelsCommand.setLevels(levels);
					if(levels.isEmpty() || levels==null)
					{
						log.debug("inside levels list ==null"+levels.size());
						HRInternalLevel level=new HRInternalLevel();
						levels.add(level);
						levelsCommand.setLevels(levels);
						log.debug(">>>>>>>>>>>>levelsCommand.getLevels() in get>>>>>>>>>>>"+levelsCommand.getLevels());
						
					}
					
					else
					{
						log.debug("inside levels list !=null"+levels.size());
						List existingLevels=hrManager.getObjectsOrderedByField(HRInternalLevel.class,"levelNo");
						levelsCommand.setLevels(existingLevels);
						
					}	log.debug(">>>>>>>>>>>>levelsCommand.getLevels() before>>>>>>>>>>>"+levelsCommand.getLevels());
					 int counter=0;
					if(stringCounter!=null && !stringCounter.equals(""))
					{
					 counter=new Long(stringCounter).intValue();
					}
					int diff=counter-levelsCommand.getLevels().size();
					for(int i=0;i<diff;i++)
					{
						HRInternalLevel intLevel=new HRInternalLevel();
						levelsCommand.getLevels().add(intLevel);
					}
					
				}
				
				log.debug(">>>>>>>>>>>>levelsCommand.getLevels()>>>>>>>>>>>"+levelsCommand.getLevels());
				return levelsCommand;
			}

			protected Map referenceData(HttpServletRequest request, Object command,Errors errors) throws Exception {
				
				Map model = new HashMap();
				LevelsCommand result = (LevelsCommand) command;
				
				String edit = request.getParameter("edit");
				String add = request.getParameter("add");
				
			    
			     model.put("formType", "internal");
			     model.put("edit", edit);
			     model.put("add", add);
				return model;
			}

			
			protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
				// TODO Auto-generated method stub
				String stringCounter=request.getParameter("counter");
				log.debug("counter>>>>>>>>>>>>>>>>>>>>:::::::"+stringCounter);
				
				if(new Long(stringCounter).intValue()==10)
				{
					errors.reject("hr.error.levelsMustNotExceed10", "levels must be not exceed 10");
				}
			
				}
			
			protected ModelAndView onSubmit(HttpServletRequest request,
					HttpServletResponse response, Object command, BindException errors)
					throws Exception {
				LevelsCommand result = (LevelsCommand) command;
				//List intDivisions = hrManager.getObjects(HRInternalDivision.class);
				//log.debug("**************************************intDivisions "+intDivisions);
					
					
					int noOfLevelz = 0;
					noOfLevelz =result.getLevels().size();
					////////////////////////////////////////////////////////////////////////////////////////////
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>getLevel().size() after removing "
							+ result.getLevels().size());
					
					for (int i = 0; i < noOfLevelz; i++) {
					/*	String levelData = request.getParameter("levelData" + i);
						log.debug("-------------------------------levelData[" + i
								+ "]--------------" + levelData);
						if (levelData != null && levelData.length() > 0) {
							log
									.debug("----------------------------------if statement ");
							HRInternalLevel level = new HRInternalLevel();
							int x = Integer.parseInt(levelData);
							level.setLength(x);
							level.setLevelNo(i + 1);
							int j = i + 1;
							log.debug("------------------------------j= " + j);
							if (j == noOfLevelz) {
								level.setIsLastLevel(true);
							}
							//result.getLevels().add(level);
						}*/
						HRInternalLevel level=(HRInternalLevel)result.getLevels().get(i);
						String funcGroup = request.getParameter("FG"+i);
						log.debug("funcGroup>>>>>>>>>>>>>>>>"+funcGroup);
						String levelNo= request.getParameter("levelNo"+i);
						String name = request.getParameter("name"+i);
						log.debug("name>>>>>>>>>>>>>>>>"+name+i);
						String ename = request.getParameter("ename"+i);
						log.debug("-------------------------------name[" + i
								+ "]--------------" + name);
						log.debug("----------------ename[" + i
								+ "]--------------" + ename);
						log.debug("request.getParameterNames()>>>>>>>>>>"+request.getParameterNames());
						log.debug("request.getParameterMap()>>>>>>>>>>"+request.getParameterMap());
					
						if(levelNo!=null && !levelNo.equals(""))
						{
						 level.setLevelNo(new Integer(levelNo));
						}
						
						log.debug("level.getEname() after adding ename"+level.getEname());
						if(funcGroup!=null && !funcGroup.equals(""))
						{
						 level.setHasFunctionalGroup(new Boolean(true));
						}
						else
						{
							level.setHasFunctionalGroup(new Boolean(false));
						}
						log.debug("----------------ename[" + i
								+ "]--------------before setting" + ename);
						level.setName(name);
						level.setEname(ename);
						log.debug("----------------ename[" + i
								+ "]--------------after setting" + ename);
						int j = i + 1;
						log.debug("------------------------------j= " + j);
						/*if (j == noOfLevelz) {
							level.setIsLastLevel(true);
						}*/
						
					
				
				
					 
			/* else 
			      {
					
						int noOfLevelz = 0;
						noOfLevelz = Integer.parseInt(noOfLevels);
						//result.getLevels().clear();
						log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>getLevels().size() after clearing  "
								+ result.getLevels());
						
						for (int i = 0; i < noOfLevelz; i++) {
							log.debug("request.getParameterNames()>>>>>>>>>>"+request.getParameterNames());
							log.debug("request.getParameterMap()>>>>>>>>>>"+request.getParameterMap());
							String funcGroup = request.getParameter("fg"+i);
							log.debug("funcGroup>>>>>>>>>>>>>>>>"+funcGroup);
							String levelNo= request.getParameter("levelNo"+i);
							String name = request.getParameter("name"+i);
							log.debug("name>>>>>>>>>>>>>>>>"+name);
							String ename = request.getParameter("ename"+i);
							log.debug("-------------------------------name[" + i
									+ "]--------------" + name);
							
							
								HRInternalLevel level = new HRInternalLevel();
								//int x = Integer.parseInt(levelData);
								//level.setLength(x);
								if(levelNo!=null && !levelNo.equals(""))
								{
								 level.setLevelNo(new Integer(levelNo));
								}
								level.setName(name);
								level.setEname(ename);
								if(funcGroup!=null && !funcGroup.equals(""))
								{
								 level.setHasFunctionalGroup(new Boolean(true));
								}
								else
								{
									level.setHasFunctionalGroup(new Boolean(false));
								}
								int j = i + 1;
								log.debug("------------------------------j= " + j);
								if (j == noOfLevelz) {
									level.setIsLastLevel(true);
								}
								result.getLevels().add(level);
							
						}*/
						log.debug("level.getName()>>>>>>>>>>>>>>>>>>"+level.getName());
						log.debug("level.getEname()>>>>>>>>>"+level.getEname());
						log.debug("level.getId()>>>>>>>>"+level.getId());
							  hrManager.saveObject(level);
						   
						
					}
					
					
					
			
				return new ModelAndView(new RedirectView(
						"internalLevelForm.html"));
			}

			
			

			
			}
			

