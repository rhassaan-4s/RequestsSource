package com._4s_.HR.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.Degree;
import com._4s_.HR.model.Punishment;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;


public class DegreeForm  extends  BaseSimpleFormController{
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
			String degreeId=request.getParameter("degreeId");
			log.debug("degreeId"+degreeId);
			Degree degree=null;
			if(degreeId==null || degreeId.equals(""))
			{
				degree=new Degree();
				
				//to put code automatically
				List degrees=hrManager.getObjects(Degree.class);
				List codesList=new ArrayList();
				Iterator itr=degrees.iterator();
				
				while(itr.hasNext())
				{
					Degree deg=(Degree)itr.next();
					codesList.add(Integer.parseInt(deg.getDegree()));
					log.debug("--code before zerofill----deg.getDegree()"+deg.getDegree());
				}
				
				String code = hrManager.zeroFill(codesList.toArray(),8);
				log.debug("code after zerofill----degree"+code);
				degree.setDegree(code);
			}
			
			else
			{
				degree=(Degree)hrManager.getObject(Degree.class, new Long(degreeId));
			}
			log.debug("degree>>>>>>>>>"+degree);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		   return degree;
		}
	//**************************************** referenceData ***********************************************\\
		protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Map model=new HashMap();
			String degreeId=request.getParameter("degreeId");
			log.debug("degreeId>>>>>>"+degreeId);
			model.put("degreeId",degreeId);
			List degreesList=hrManager.getObjects(Degree.class);
			model.put("degreesList", degreesList);
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
			Degree degree=(Degree)command;
			if(errors.getErrorCount()==0)
			{
				log.debug("degree.getDegree()>>>>>>>>>>>>>>>"+degree.getDegree());
				if(degree.getDegree().length()>8)
				{
					errors.rejectValue("degree", "hr.errors.codeLargerThanExpected","hr.errors.codeLargerThanExpected");
				}
			}
			
			if(errors.getErrorCount()==0)
			{
				Degree deg=(Degree)hrManager.getObjectByParameter(Degree.class,"degree",degree.getDegree());
				if(deg!=null && (!deg.getId().equals(degree.getId())))
						{
					       errors.rejectValue("degree", "hr.error.codeExists","code exists");
						}
			}
			
			if(errors.getErrorCount()==0)
			{
			
				if(degree.getName()==null || degree.getName().equals(""))
				{
					errors.rejectValue("name", "commons.errors.requiredFields");
				}
				
				/*else if(degree.getName()!=null && !degree.getName().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());
		

					if(!degree.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

					errors.reject("hr.errors.invalidArabicName");

					}

					}*/
			}
			
			/*if(errors.getErrorCount()==0)
			{
			
				if(degree.getEname()==null || degree.getEname().equals(""))
				{
					errors.rejectValue("salraise", "commons.errors.requiredFields");
				}
				
				else if(degree.getEname()!=null && !degree.getEname().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());

					

					

					if(!degree.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

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
			Degree degree=(Degree)command;
			log.debug("Degree.getId()__________>>>>>>>>>>>>>>>"+degree.getId());
			
			hrManager.saveObject(degree);
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			return new ModelAndView(new RedirectView(getSuccessView()));
		}
		

	}

