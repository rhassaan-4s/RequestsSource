package com._4s_.HR.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com._4s_.HR.model.Punishment;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class PunishmentForm extends  BaseSimpleFormController{
			private HRManager hrManager;
			
			public HRManager getHrManager() {
				return hrManager;
			}
			public void setHrManager(HRManager hrManager) {
				this.hrManager = hrManager;
			}
			

				//**************************************** formBackingObject ***********************************************\\
				@RequestMapping(method = RequestMethod.GET)  
				public String initForm(ModelMap model,HttpServletRequest request){
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
					String punishmentId=request.getParameter("punishmentId");
					log.debug("punishmentId"+punishmentId);
					Punishment punishment=null;
					if(punishmentId==null || punishmentId.equals(""))
					{
						punishment=new Punishment();
						
						
						//to put code automatically
						List punishments=hrManager.getObjects(Punishment.class);
						List codesList=new ArrayList();
						Iterator itr=punishments.iterator();
						Punishment punsh=null;
						while(itr.hasNext())
						{
							punsh=(Punishment)itr.next();
							codesList.add(Integer.parseInt(punsh.getPunch()));
							log.debug("--code before zerofill----punsh.getPunch()"+punishment.getPunch());
							
						}
						
						String code = hrManager.zeroFill(codesList.toArray(),2);
						log.debug("code after zerofill----Punsh"+code);
						punishment.setPunch(code);
						log.debug("---punishment.getPunch() after setting"+punishment.getPunch());
					}
					
					else
					{
						punishment=(Punishment)hrManager.getObject(Punishment.class, new Long(punishmentId));
					}
					log.debug("punishment>>>>>>>>>"+punishment);
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
					model.addAttribute(punishment);
				   return "punishmentForm";
				}
			//**************************************** referenceData ***********************************************\\
				@ModelAttribute("model")	
				public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
				{
					log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
					Map model=new HashMap();
					String punishmentId=request.getParameter("punishmentId");
					log.debug("punishmentId>>>>>>"+punishmentId);
					model.put("punishmentId",punishmentId);
					List punishmentsList=hrManager.getObjects(Punishment.class);
					model.put("punishmentsList", punishmentsList);
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
					Punishment punishment=(Punishment)command;
					if(errors.getErrorCount()==0)
					{
						log.debug("punishment.getPunishment()>>>>>>>>>>>>>>>"+punishment.getPunch());
						if(punishment.getPunch().length()>2)
						{
							errors.rejectValue("punch", "hr.errors.codeLargerThanExpected","codeLargerThanExpected");
						}
					}
					
					if(errors.getErrorCount()==0)
					{
						Punishment punish=(Punishment)hrManager.getObjectByParameter(Punishment.class,"punch", punishment.getPunch());
						if(punish!=null && (!punish.getId().equals(punishment.getId())))
								{
							       errors.rejectValue("punch", "hr.error.codeExists","code exists");
								}
					}
					
					
					if(errors.getErrorCount()==0)
					{
					
						if(punishment.getName()==null || punishment.getName().equals(""))
						{
							errors.rejectValue("name", "commons.errors.requiredFields");
						}
						
						/*else if(punishment.getName()!=null && !punishment.getName().equals(""))
						{
							log.debug("errors.getErrorCount() " + errors.getErrorCount());
				

							if(!punishment.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

							errors.reject("hr.errors.invalidArabicName");

							}

							}*/
					}
					
					/*if(errors.getErrorCount()==0)
					{
					
						if(punishment.getEname()==null || punishment.getEname().equals(""))
						{
							errors.rejectValue("ename", "commons.errors.requiredFields");
						}
						
						else if(punishment.getEname()!=null && !punishment.getEname().equals(""))
						{
							log.debug("errors.getErrorCount() " + errors.getErrorCount());

							

							

							if(!punishment.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

								errors.reject("hr.errors.invalidEnglishName");

							}

							}

*/
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				}
				
				//**************************************** onSubmit ***********************************************\\	
				public ModelAndView onSubmit(HttpServletRequest request,
						HttpServletResponse response, Object command, BindException errors)throws Exception 
				{
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
					Punishment punishment=(Punishment)command;
					log.debug("Punishment.getId()__________>>>>>>>>>>>>>>>"+punishment.getId());
					
					hrManager.saveObject(punishment);
					log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					return new ModelAndView(new RedirectView("punishmentsView.html"));
				}
				

			}




