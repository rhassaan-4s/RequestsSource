package com._4s_.HR.web.action;

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

import com._4s_.HR.model.Test;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class TestForm  extends  BaseSimpleFormController{
	 

	
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
		String testId=request.getParameter("testId");
		log.debug("testId"+testId);
		Test test=null;
		if(testId==null || testId.equals(""))
		{
			test=new Test();
		}
		
		else
		{
			test=(Test)hrManager.getObject(Test.class, new Long(testId));
		}
		log.debug("test"+test);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute(test);
	   return "testForm";
	}
//**************************************** referenceData ***********************************************\\
	
	@ModelAttribute("model")	
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,HttpServletRequest request) 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		String testId=request.getParameter("testId");
		log.debug("testId"+testId);
		model.put("testId",testId);
		List testsList=hrManager.getObjects(Test.class);
		model.put("testsList", testsList);
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
		Test test=(Test)command;
		
		
		
		
		if(errors.getErrorCount()==0)
		{
		
			if(test.getName()==null || test.getName().equals(""))
			{
				errors.rejectValue("name", "commons.errors.requiredFields");
			}
			
		
		}
				

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Test test=(Test)command;
		log.debug("test.getId()__________>>>>>>>>>>>>>>>"+test.getId());
		
		hrManager.saveObject(test);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("test.html"));
	}
	

}


