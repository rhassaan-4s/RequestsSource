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

import com._4s_.HR.model.HRDocuments;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class DocumentsForm extends  BaseSimpleFormController{
	

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
		String hrDocumentsId=request.getParameter("hrDocumentsId");
		log.debug("hrDocumentsId"+hrDocumentsId);
		HRDocuments hrDocuments=null;
		if(hrDocumentsId==null || hrDocumentsId.equals(""))
		{
			hrDocuments=new HRDocuments();
			
			//to put code automatically
			List hrDocs=hrManager.getObjects(HRDocuments.class);
			List codesList=new ArrayList();
			Iterator itr=hrDocs.iterator();

			while(itr.hasNext())
			{
				HRDocuments document=(HRDocuments)itr.next();
				codesList.add(Integer.parseInt(document.getCode()));
				log.debug("--code before zerofill----hrDocuments"+document.getCode());
				
			}
			
			String code = hrManager.zeroFill(codesList.toArray(),3);
			log.debug("code after zerofill----hrDocuments"+code);
			hrDocuments.setCode(code);
		}
		
		else
		{
			hrDocuments=(HRDocuments)hrManager.getObject(HRDocuments.class, new Long(hrDocumentsId));
		}
		log.debug("hrDocuments"+hrDocuments);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute(hrDocuments);
	   return "documentsForm";
	}
//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		String hrDocumentsId=request.getParameter("hrDocumentsId");
		log.debug("hrDocumentsId"+hrDocumentsId);
		model.put("hrDocumentsId",hrDocumentsId);
		List hrDocumentssList=hrManager.getObjects(HRDocuments.class);
		model.put("hrDocumentssList", hrDocumentssList);
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
		HRDocuments hrDocuments=(HRDocuments)command;
		if(errors.getErrorCount()==0)
		{
			if(hrDocuments.getCode().length()>3)
			{
				errors.rejectValue("code", "hr.errors.codeLargerThanExpected","code larger than excepected");
			}
		}
		
		if(errors.getErrorCount()==0)
		{
			HRDocuments tit=(HRDocuments)hrManager.getObjectByParameter(HRDocuments.class,"code", hrDocuments.getCode());
			if(tit!=null && (!tit.getId().equals(hrDocuments.getId())))
					{
				       errors.rejectValue("code", "hr.error.codeExists","code exists");
					}
		}
		
		
		if(errors.getErrorCount()==0)
		{
		
			if(hrDocuments.getName()==null || hrDocuments.getName().equals(""))
			{
				errors.rejectValue("name", "commons.errors.requiredFields");
			}
			
			/*else if(hrDocuments.getName()!=null && !hrDocuments.getName().equals(""))
			{
				log.debug("errors.getErrorCount() " + errors.getErrorCount());
	

				if(!hrDocuments.getName().matches("((([\u0600-\u06FF]|[\u0750-\u077F]|[\uFB50-\uFDFF]|[\uFE70-\uFEFF])+\\s{0,1})*)")) {

				errors.reject("hr.errors.invalidArabicName");

				}

				}*/
		}
		
		/*if(errors.getErrorCount()==0)
		{
		
			if(hrDocuments.getEname()==null || hrDocuments.getEname().equals(""))
			{
				errors.rejectValue("ename", "commons.errors.requiredFields");
			}
			
			else if(hrDocuments.getEname()!=null && !hrDocuments.getEname().equals(""))
			{
				log.debug("errors.getErrorCount() " + errors.getErrorCount());

				

				

				if(!hrDocuments.getEname().matches("([a-zA-Z]+\\s{0,1})*"))

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
		HRDocuments hrDocuments=(HRDocuments)command;
		log.debug("hrDocuments.getId()__________>>>>>>>>>>>>>>>"+hrDocuments.getId());
		
		hrManager.saveObject(hrDocuments);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("documentsView.html"));
	}
	

}




