package com._4s_.HR.web.action;

import java.util.ArrayList;
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

import com._4s_.HR.model.Grade;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class GradeForm extends  BaseSimpleFormController{
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
				String gradeId=request.getParameter("gradeId");
				log.debug("gradeId"+gradeId);
				Grade grade=null;
				if(gradeId==null || gradeId.equals(""))
				{
					grade=new Grade();
					
					//to put code automatically
					List grades=hrManager.getObjects(Grade.class);
					List codesList=new ArrayList();
					Iterator itr=grades.iterator();
					
					while(itr.hasNext())
					{
						Grade gr=(Grade)itr.next();
						codesList.add(Integer.parseInt(gr.getGrade()));
						log.debug("--code before zerofill----gr.getGrade()"+gr.getGrade());
					}
					
					String code = hrManager.zeroFill(codesList.toArray(),3);
					log.debug("code after zerofill----grade"+code);
					grade.setGrade(code);
				}
				
				else
				{
					grade=(Grade)hrManager.getObject(Grade.class, new Long(gradeId));
				}
				log.debug("grade>>>>>>>>>"+grade);
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			   return grade;
			}
		//**************************************** referenceData ***********************************************\\
			protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
			{
				log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				Map model=new HashMap();
				String gradeId=request.getParameter("gradeId");
				log.debug("gradeId>>>>>>"+gradeId);
				model.put("gradeId",gradeId);
				List gradesList=hrManager.getObjects(Grade.class);
				model.put("gradesList", gradesList);
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
				Grade grade=(Grade)command;
				if(errors.getErrorCount()==0)
				{
					log.debug("grade.getgrade()>>>>>>>>>>>>>>>"+grade.getGrade());
					if(grade.getGrade()==null || grade.getGrade().equals(""))
					{
						errors.rejectValue("grade", "commons.errors.requiredFields","requiredFields");
					}
					if(grade.getGrade().length()>3)
					{
						errors.rejectValue("grade", "hr.errors.codeLargerThanExpected","codeLargerThanExpected");
					}
				}
				
				if(errors.getErrorCount()==0)
				{
					Grade gra=(Grade)hrManager.getObjectByParameter(Grade.class,"grade",grade.getGrade());
					if(gra!=null && (!gra.getId().equals(grade.getId())))
							{
						       errors.rejectValue("grade", "hr.error.codeExists","code exists");
							}
				}
				
				if(errors.getErrorCount()==0)
				{
				
					if(grade.getName()==null || grade.getName().equals(""))
					{
						errors.rejectValue("name", "commons.errors.requiredFields");
					}
					
					/*else if(grade.getName()!=null && !grade.getName().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());
			

						if(!grade.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

						errors.reject("hr.errors.invalidArabicName");

						}

						}*/
				}
				
				/*if(errors.getErrorCount()==0)
				{
				
					if(grade.getEname()==null || grade.getEname().equals(""))
					{
						errors.rejectValue("ename", "commons.errors.requiredFields");
					}
					
					else if(grade.getEname()!=null && !grade.getEname().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());

						

						

						if(!grade.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

							errors.reject("hr.errors.invalidEnglishName");

						}

						}*/


				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			}
			
			//**************************************** onSubmit ***********************************************\\	
			public ModelAndView onSubmit(HttpServletRequest request,
					HttpServletResponse response, Object command, BindException errors)throws Exception 
			{
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				Grade grade=(Grade)command;
				log.debug("grade.getId()__________>>>>>>>>>>>>>>>"+grade.getId());
				
				hrManager.saveObject(grade);
				log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				return new ModelAndView(new RedirectView("gradesView.html"));
			}
			

}
