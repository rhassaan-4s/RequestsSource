package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.HR.service.HRManager;
import com._4s_.common.model.TreeDivisions;
import com._4s_.common.web.action.BaseSimpleFormController;
/**
 * @author hoda
 *
 */
public class TreeFormController extends  BaseSimpleFormController{

	
	protected HRManager hrManager;
	protected Class className=null;
	
	public HRManager getHrManager() {
		return hrManager;
	}
	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}
	
	
	@RequestMapping(method = RequestMethod.GET)  
	public String initForm(ModelMap model,HttpServletRequest request){
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start parent formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
         
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End  parent formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute(new TreeDivisions());
		   return "treeForm";
		}
	
	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start  parent onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of parent onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start  parent onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of   parentonBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start parent onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.debug("command " + command);
		log.debug("command " + command.getClass());
		log.debug("hrManager " + hrManager);
		log.debug("className>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+className);
		log.debug("command.getClass().getDeclaredField(code)"+command.getClass().getDeclaredMethods());
		log.debug("className.cast(command); " + className.cast(command).getClass());
		Map model=new HashMap();
		TreeDivisions treeDivisions=(TreeDivisions)command;
		log.debug("treeDivisions.getId"+treeDivisions.getId());
		log.debug("treeDivisions.getArdesc()"+treeDivisions.getArdesc());
		log.debug("treeDivisions.getEndesc()"+treeDivisions.getEndesc());
//		className.cast(command).getClass();
	    hrManager.saveObject(treeDivisions);
	 
	     request.getSession().removeAttribute("internalDivTree");
	     
	    
	    request.getSession().removeAttribute("geographicalDivTree");
	      
		request.getSession().removeAttribute("qualificationDivTree");
		
		request.getSession().removeAttribute("specialtyDivTree");
		
		
	log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End parent onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	return new ModelAndView("internalDivisionTree");
  }
}
