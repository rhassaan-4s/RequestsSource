
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

	     import com._4s_.HR.model.HRReligion;
		import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;


public class ReligionForm   extends  BaseSimpleFormController{
		
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
				String religionId=request.getParameter("religionId");
				log.debug("religionId"+religionId);
				HRReligion religion=null;
				if(religionId==null || religionId.equals(""))
				{
					religion=new HRReligion();
					
					//to put code automatically
					List religions=hrManager.getObjects(HRReligion.class);
					List codesList=new ArrayList();
					Iterator itr=religions.iterator();
					
					while(itr.hasNext())
					{
						HRReligion rel=(HRReligion)itr.next();
						codesList.add(Integer.parseInt(rel.getReligion()));
						log.debug("--code before zerofill----rel.getReligion()"+rel.getReligion());
					}
					
					String code = hrManager.zeroFill(codesList.toArray(),8);
					log.debug("code after zerofill----"+code);
					religion.setReligion(code);
				}
				
				else
				{
					religion=(HRReligion)hrManager.getObject(HRReligion.class, new Long(religionId));
				}
				log.debug("religion........ "+religion.getReligion());
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			   return religion;
			}
		//**************************************** referenceData ***********************************************\\
			protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
			{
				log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
				Map model=new HashMap();
				String religionId=request.getParameter("religionId");
				log.debug("religionId"+religionId);
				model.put("religionId",religionId);
				List religionsList=hrManager.getObjects(HRReligion.class);
				model.put("religionsList", religionsList);
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
				HRReligion religion=(HRReligion)command;
				log.debug("----religionid---"+religion.getId());
				if(errors.getErrorCount()==0)
				{
					if(religion.getReligion().length()<3)
					{
						errors.rejectValue("religion", "hr.errors.codeLargerThanExpected","codeLargerThanExpected");
					}
				}
				
				if(errors.getErrorCount()==0)
				{
					HRReligion tit=(HRReligion)hrManager.getObjectByParameter(HRReligion.class,"religion", religion.getReligion());
					if(tit!=null && (!tit.getId().equals(religion.getId())))
							{
						       errors.rejectValue("religion", "hr.error.codeExists","code exists");
							}
				}
				
				
				if(errors.getErrorCount()==0)
				{
				
					if(religion.getName()==null || religion.getName().equals(""))
					{
						errors.rejectValue("name", "commons.errors.requiredFields");
					}
					
					/*else if(religion.getName()!=null && !religion.getName().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());
			

						if(!religion.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

						errors.reject("hr.errors.invalidArabicName");

						}

						}*/
				}
				
			/*	if(errors.getErrorCount()==0)
				{
				
					if(religion.getEname()==null || religion.getEname().equals(""))
					{
						errors.rejectValue("ename", "commons.errors.requiredFields");
					}
					
					else if(religion.getEname()!=null && !religion.getEname().equals(""))
					{
						log.debug("errors.getErrorCount() " + errors.getErrorCount());

						

						

						if(!religion.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

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
				HRReligion religion=(HRReligion)command;
				log.debug("religion.getId()__________>>>>>>>>>>>>>>>"+religion.getId());
				
				hrManager.saveObject(religion);
				log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				return new ModelAndView(new RedirectView(getSuccessView()));
			}
			

		}





