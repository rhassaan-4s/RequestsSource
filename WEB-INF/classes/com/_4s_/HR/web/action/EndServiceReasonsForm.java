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

import com._4s_.HR.model.EndServiceReasons;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class EndServiceReasonsForm extends  BaseSimpleFormController{
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
			String endServiceReasonsId=request.getParameter("endServiceReasonsId");
			log.debug("endServiceReasonsId"+endServiceReasonsId);
			EndServiceReasons endServiceReasons=null;
			if(endServiceReasonsId==null || endServiceReasonsId.equals(""))
			{
				endServiceReasons=new EndServiceReasons();
				
				//to put code automatically
				List endSerReasons=hrManager.getObjects(EndServiceReasons.class);
				List codesList=new ArrayList();
				Iterator itr=endSerReasons.iterator();

				while(itr.hasNext())
				{
					EndServiceReasons reasons=(EndServiceReasons)itr.next();
					codesList.add(Integer.parseInt(reasons.getCode()));
					log.debug("--code before zerofill----reasons.getCode()"+reasons.getCode());
					
				}
				
				String code = hrManager.zeroFill(codesList.toArray(),6);
				log.debug("code after zerofill----endServiceReasons"+code);
				endServiceReasons.setCode(code);
			}
			
			else
			{
				endServiceReasons=(EndServiceReasons)hrManager.getObject(EndServiceReasons.class, new Long(endServiceReasonsId));
			}
			log.debug("endServiceReasons>>>>>>>>>"+endServiceReasons);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			model.addAttribute(endServiceReasons);
		   return "endServiceReasonsForm";
		}
	//**************************************** referenceData ***********************************************\\
		@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Map model=new HashMap();
			String endServiceReasonsId=request.getParameter("endServiceReasonsId");
			log.debug("endServiceReasonsId>>>>>>"+endServiceReasonsId);
			model.put("endServiceReasonsId",endServiceReasonsId);
			List endServiceReasonssList=hrManager.getObjects(EndServiceReasons.class);
			model.put("endServiceReasonssList", endServiceReasonssList);
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
			EndServiceReasons endServiceReasons=(EndServiceReasons)command;
			if(errors.getErrorCount()==0)
			{
				log.debug("endServiceReasons.getEndServiceReasons()>>>>>>>>>>>>>>>"+endServiceReasons.getCode());
				if(endServiceReasons.getCode().length()>6)
				{
					errors.rejectValue("code", "hr.errors.codeLargerThanExpected","codeLargerThanExpected");
				}
			}
			
			if(errors.getErrorCount()==0)
			{
				EndServiceReasons deg=(EndServiceReasons)hrManager.getObjectByParameter(EndServiceReasons.class,"code",endServiceReasons.getCode());
				if(deg!=null && (!deg.getId().equals(endServiceReasons.getId())))
						{
					       errors.rejectValue("code", "hr.error.codeExists","code exists");
						}
			}
			
			if(errors.getErrorCount()==0)
			{
			
				if(endServiceReasons.getAname()==null || endServiceReasons.getAname().equals(""))
				{
					errors.rejectValue("aname", "commons.errors.requiredFields");
				}
				
				/*else if(endServiceReasons.getAname()!=null && !endServiceReasons.getAname().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());
		

					if(!endServiceReasons.getAname().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

					errors.reject("hr.errors.invalidArabicName");

					}

					}*/
			}
			
		/*	if(errors.getErrorCount()==0)
			{
			
				if(endServiceReasons.getEname()==null || endServiceReasons.getEname().equals(""))
				{
					errors.rejectValue("ename", "commons.errors.requiredFields");
				}
				
				else if(endServiceReasons.getEname()!=null && !endServiceReasons.getEname().equals(""))
				{
					log.debug("errors.getErrorCount() " + errors.getErrorCount());

					

					

					if(!endServiceReasons.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

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
			EndServiceReasons endServiceReasons=(EndServiceReasons)command;
			log.debug("EndServiceReasons.getId()__________>>>>>>>>>>>>>>>"+endServiceReasons.getId());
			hrManager.saveObject(endServiceReasons);
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			return new ModelAndView(new RedirectView("endServiceReasonsView.html"));
		}
		

	}

