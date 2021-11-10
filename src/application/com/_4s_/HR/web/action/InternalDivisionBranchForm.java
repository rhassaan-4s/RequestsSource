package com._4s_.HR.web.action;

		import java.util.HashMap;
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
import com._4s_.HR.model.HRInternalDivisionBranch;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.i18n.model.MyLocale;
import com._4s_.security.model.User;



public class InternalDivisionBranchForm  extends  BaseSimpleFormController{
			private HRManager hrManager;
			
			public HRManager getHrManager() {
				return hrManager;
			}
			public void setHrManager(HRManager hrManager) {
				this.hrManager = hrManager;
			}
			

				//**************************************** formBackingObject ***********************************************\\
			protected Object formBackingObject(HttpServletRequest request) throws ServletException 
			{
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				String internalDivisionBranchId=request.getParameter("internalDivisionBranchId");
				log.debug("internalDivisionBranchId"+internalDivisionBranchId);
				HRInternalDivisionBranch internalDivisionBranch=null;
				if(internalDivisionBranchId==null || internalDivisionBranchId.equals(""))
				{
					internalDivisionBranch=new HRInternalDivisionBranch();
				}
				
				else
				{
					internalDivisionBranch=(HRInternalDivisionBranch)hrManager.getObject(HRInternalDivisionBranch.class, new Long(internalDivisionBranchId));
				}
				log.debug("internalDivisionBranch>>>>>>>>>"+internalDivisionBranch);
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			   return internalDivisionBranch;
			}
		//**************************************** referenceData ***********************************************\\
			protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
			{
				log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				Map model=new HashMap();
				String internalDivisionBranchId=request.getParameter("internalDivisionBranchId");
				String typeId=request.getParameter("typeId");
				log.debug("internalDivisionBranchId>>>>>>"+internalDivisionBranchId);
				log.debug("typeId>>>>>>"+typeId);
				model.put("internalDivisionBranchId",internalDivisionBranchId);
				model.put("typeId", typeId);
				HRInternalDivision internalDivision=(HRInternalDivision)hrManager.getObject(HRInternalDivision.class,new Long(typeId));
				model.put("type",internalDivision );
				
				User user=(User)request.getSession().getAttribute("user");
				log.debug("user>>>>>>>>>>>>"+user.getId());
				MyLocale locale=user.getLanguage();
					model.put("locale", locale.getId());
				
				List internalDivisionBranchsList=hrManager.getObjects(HRInternalDivisionBranch.class);
				model.put("internalDivisionBranchsList", internalDivisionBranchsList);
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
				HRInternalDivisionBranch internalDivisionBranch=(HRInternalDivisionBranch)command;
				/*if(errors.getErrorCount()==0)
				{
					log.debug("internalDivisionBranch.getinternalDivisionBranch()>>>>>>>>>>>>>>>"+internalDivisionBranch.getInternal_division_branch());
					if(internalDivisionBranch.getHRInternalDivisionBranch().length()<3)
					{
						errors.rejectValue("internalDivisionBranch", "hr.error.codeMustBeEqual8");
					}
				}*/
				
				if(errors.getErrorCount()==0)
				{
					HRInternalDivisionBranch gra=(HRInternalDivisionBranch)hrManager.getObjectByParameter(HRInternalDivisionBranch.class,"internal_division_branch",internalDivisionBranch.getInternal_division_branch());
					if(gra!=null && (!gra.getId().equals(internalDivisionBranch.getId())))
							{
						       errors.rejectValue("internal_division_branch", "hr.error.codeExists","code exists");
							}
				}
				
				if(errors.getErrorCount()==0)
				{
				
					if(internalDivisionBranch.getName()==null || internalDivisionBranch.getName().equals(""))
					{
						errors.rejectValue("name", "commons.errors.requiredFields");
					}
					
					else if(internalDivisionBranch.getName()!=null && !internalDivisionBranch.getName().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());
			

						if(!internalDivisionBranch.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

						errors.reject("hr.errors.invalidArabicName");

						}

						}
				}
				
				if(errors.getErrorCount()==0)
				{
				
					if(internalDivisionBranch.getEname()==null || internalDivisionBranch.getEname().equals(""))
					{
						errors.rejectValue("ename", "commons.errors.requiredFields");
					}
					
					else if(internalDivisionBranch.getEname()!=null && !internalDivisionBranch.getEname().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());

						

						

						if(!internalDivisionBranch.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

							errors.reject("hr.errors.invalidEnglishName");

						}

						}


				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			}
			
			//**************************************** onSubmit ***********************************************\\	
			public ModelAndView onSubmit(HttpServletRequest request,
					HttpServletResponse response, Object command, BindException errors)throws Exception 
			{
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				HRInternalDivisionBranch internalDivisionBranch=(HRInternalDivisionBranch)command;
				log.debug("internalDivisionBranch.getId()__________>>>>>>>>>>>>>>>"+internalDivisionBranch.getId());
				String typeId=request.getParameter("typeId");
				log.debug("typeId__________>>>>>>>>>>>>>>>"+typeId);
				HRInternalDivision internalDivision=(HRInternalDivision)hrManager.getObject(HRInternalDivision.class,new Long(typeId));
				internalDivisionBranch.setType(internalDivision);
				hrManager.saveObject(internalDivisionBranch);
				log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							return new ModelAndView(new RedirectView("internalDivisionBranchesView.html?typeId="+typeId));
						}
						

			}


			




