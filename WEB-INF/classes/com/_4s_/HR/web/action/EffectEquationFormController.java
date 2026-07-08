package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HREffect;
import com._4s_.HR.model.HREffectEquation;
import com._4s_.HR.service.HRManager;
import com._4s_.HR.web.command.PreferencesCommand;
import com._4s_.common.web.action.BaseSimpleFormController;

public class EffectEquationFormController extends BaseSimpleFormController {

	HRManager hrManager = null;
  
	public HRManager getHrManager() {
		return hrManager;
	}

	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}
	
	
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		
		String stringCounter=request.getParameter("counter");
		log.debug("counter>>>>>>>>>>>>>>>>>>>>:::::::"+stringCounter);
		
		String type=request.getParameter("type");
		log.debug("type>>>>>>>>>>>>>>>>>>>>:::::::"+type);
		
		PreferencesCommand preferences=null;
		
		if(request.getMethod().equals("GET")){
			preferences=new PreferencesCommand();
			
			List hrEffectEquation=null;
			
			if((type!=null && !type.equals(""))&& type.equals("minValue")){
				hrEffectEquation=hrManager.getObjectsByParameter(HREffectEquation.class, "flag", "effmin");
			}
			
			if((type!=null && !type.equals(""))&& type.equals("maxValue")){
				hrEffectEquation=hrManager.getObjectsByParameter(HREffectEquation.class, "flag", "effmax");
			}
			
			if((type!=null && !type.equals(""))&& type.equals("taxDiscount")){
				hrEffectEquation=hrManager.getObjectsByParameter(HREffectEquation.class, "flag", "taxdiscount");
			}
			
			if((type!=null && !type.equals(""))&& type.equals("equation")){
				hrEffectEquation=hrManager.getObjectsByParameter(HREffectEquation.class, "flag", "");
			}
		}
		
		if(request.getMethod().equals("POST")){
			int counter=0;
			
			log.debug("request.getParameterMap()>>>>>>>>>>>>"+request.getParameterMap());
			
			preferences=new PreferencesCommand();
			
			if(stringCounter!=null && !stringCounter.equals("")){
				counter=new Long(stringCounter).intValue();
			}

			List hrEffectEquation=null;
			
			if((type!=null && !type.equals(""))&& type.equals("minValue")){
				hrEffectEquation=hrManager.getObjectsByParameter(HREffectEquation.class, "flag", "effmin");
			}
			
			if((type!=null && !type.equals(""))&& type.equals("maxValue")){
				hrEffectEquation=hrManager.getObjectsByParameter(HREffectEquation.class, "flag", "effmax");
			}
			
			if((type!=null && !type.equals(""))&& type.equals("taxDiscount")){
				hrEffectEquation=hrManager.getObjectsByParameter(HREffectEquation.class, "flag", "taxdiscount");
			}
			
			if((type!=null && !type.equals(""))&& type.equals("equation")){
				hrEffectEquation=hrManager.getObjectsByParameter(HREffectEquation.class, "flag", "");
			}
			
			if(hrEffectEquation==null || hrEffectEquation.isEmpty() || hrEffectEquation.size()==0){
				log.debug("inside years list ==null"+hrEffectEquation.size());
				HREffectEquation effEquation=new HREffectEquation();
				hrEffectEquation.add(effEquation);
				preferences.setPreferences(hrEffectEquation);
			}else{
				preferences.setPreferences(hrEffectEquation);
			}
			
			if(preferences!=null){
				int diff=counter-preferences.getPreferences().size();
				log.debug("the difference between counter and command>>>>>>>>"+diff);
				for(int i=0;i<diff;i++){
					HREffectEquation effectEquation=new HREffectEquation();
					preferences.getPreferences().add(effectEquation);
				}
			}
			log.debug("preferencesCommand.getPreferences().size() post>>>>>>>>>>>>>>"+preferences.getPreferences().size());
		}
		return preferences;
	}
	
	
	protected Map referenceData(HttpServletRequest request, Object command,Errors errors) throws Exception {
		
		Map model = new HashMap();
		
		PreferencesCommand result = (PreferencesCommand) command;
		
		String type = request.getParameter("type");
		String effectId = request.getParameter("effectId");
	    
		List hrEffectEquation=null;
		
		if((type!=null && !type.equals(""))&& type.equals("minValue")){
			hrEffectEquation=hrManager.getObjectsByParameter(HREffectEquation.class, "flag", "effmin");
		}
			
		if((type!=null && !type.equals(""))&& type.equals("maxValue")){
			hrEffectEquation=hrManager.getObjectsByParameter(HREffectEquation.class, "flag", "effmax");	
		}

		if((type!=null && !type.equals(""))&& type.equals("taxDiscount")){
			hrEffectEquation=hrManager.getObjectsByParameter(HREffectEquation.class, "flag", "taxdiscount");
		}

		if((type!=null && !type.equals(""))&& type.equals("equation")){
			hrEffectEquation=hrManager.getObjectsByParameter(HREffectEquation.class, "flag", "");
		}
		
	    model.put("effectId",effectId);
	    model.put("type",type);
	    model.put("defienedPreferences",hrEffectEquation);
		return model;
	}

	
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
		
		PreferencesCommand preferencesCommand = (PreferencesCommand) command;
		
		String type = request.getParameter("type");
		String effectId= request.getParameter("effectId");
		
		HREffect effect=null;
		if(effectId!=null && !effectId.equals("")){
			 effect=(HREffect)hrManager.getObject(HREffect.class, new Long(effectId));
		}
		
		for(int i=0;i<preferencesCommand.getPreferences().size();i++){
			String equationParm=request.getParameter("equationParm"+i);
			String number=request.getParameter("number"+i);
			String sign=request.getParameter("sign"+i);
			
			if(errors.getErrorCount()==0){
				if((equationParm==null||equationParm.equals(""))||((number==null ||number.equals("")) &&(sign==null ||sign.equals("")))) {
				errors.reject("commons.errors.requiredFields","reqiredFields");
				}
			}
			
			HREffectEquation hrEffectEquation=(HREffectEquation)preferencesCommand.getPreferences().get(i);
			hrEffectEquation.setEffect(effect);
		 // hrEffectEquation.setPvalue(choice);
		 // hrEffectEquation.setProperty(preference);
		}
	}
	
	
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		PreferencesCommand result = (PreferencesCommand) command;
		
		String type = request.getParameter("type");
		
		for(int i=0; i<result.getPreferences().size();i++){
			hrManager.saveObject(result.getPreferences().get(i));
		}
		
		return new ModelAndView(new RedirectView("effectEquation.html?type="+type));
	}
}

