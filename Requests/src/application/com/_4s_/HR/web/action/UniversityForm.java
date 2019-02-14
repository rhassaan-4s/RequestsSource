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

	import com._4s_.HR.model.HRUniversity;
		import com._4s_.HR.service.HRManager;
	import com._4s_.common.web.action.BaseSimpleFormController;


public class UniversityForm   extends  BaseSimpleFormController{
		
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
				String universityId=request.getParameter("universityId");
				log.debug("universityId"+universityId);
				HRUniversity university=null;
				if(universityId==null || universityId.equals(""))
				{
					university=new HRUniversity();
				}
				
				else
				{
					university=(HRUniversity)hrManager.getObject(HRUniversity.class, new Long(universityId));
				}
				log.debug("university"+university);
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			   return university;
			}
		//**************************************** referenceData ***********************************************\\
			protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
			{
				log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				Map model=new HashMap();
				String universityId=request.getParameter("universityId");
				log.debug("universityId"+universityId);
				model.put("universityId",universityId);
				List universitysList=hrManager.getObjects(HRUniversity.class);
				model.put("universitysList", universitysList);
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
				HRUniversity university=(HRUniversity)command;
				if(errors.getErrorCount()==0)
				{
					if(university.getUniverse().length()<3)
					{
						errors.rejectValue("universe", "hr.error.codeMustBeEqual","codeMustBeEqual");
					}
				}
				
				if(errors.getErrorCount()==0)
				{
					HRUniversity tit=(HRUniversity)hrManager.getObjectByParameter(HRUniversity.class,"universe", university.getUniverse());
					if(tit!=null && (!tit.getId().equals(university.getId())))
							{
						       errors.rejectValue("universe", "hr.error.codeExists","code exists");
							}
				}
				
				
				if(errors.getErrorCount()==0)
				{
				
					if(university.getName()==null || university.getName().equals(""))
					{
						errors.rejectValue("name", "commons.errors.requiredFields");
					}
					
					else if(university.getName()!=null && !university.getName().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());
			

						if(!university.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

						errors.reject("hr.errors.invalidArabicName");

						}

						}
				}
				
				if(errors.getErrorCount()==0)
				{
				
					if(university.getEname()==null || university.getEname().equals(""))
					{
						errors.rejectValue("ename", "commons.errors.requiredFields");
					}
					
					else if(university.getEname()!=null && !university.getEname().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());

						

						

						if(!university.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

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
				HRUniversity university=(HRUniversity)command;
				log.debug("university.getId()__________>>>>>>>>>>>>>>>"+university.getId());
				
				hrManager.saveObject(university);
				log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				return new ModelAndView(new RedirectView(getSuccessView()));
			}
			

		}






