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

import com._4s_.HR.model.HRDefinedPreferences;
import com._4s_.HR.model.HRPreferences;
import com._4s_.HR.service.HRManager;
import com._4s_.HR.web.command.PreferencesCommand;
import com._4s_.common.model.Employee;
import com._4s_.common.web.action.BaseSimpleFormController;

public class PreferencesFormController extends BaseSimpleFormController {
	
	HRManager hrManager = null;
  
	public HRManager getHrManager() {
		return hrManager;
	}

	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		
		String stringCounter=request.getParameter("counter");
		log.debug("counter>>>>>>>>>>>>>>>>>>>>:::::::"+stringCounter);
		String type=request.getParameter("type");
		log.debug("type>>>>>>>>>>>>>>>>>>>>:::::::"+type);
		
		PreferencesCommand preferences=null;
		
		if(request.getMethod().equals("GET")){
			
			preferences=new PreferencesCommand();
			List hrPreferences=null;

			if((type!=null && !type.equals(""))&& type.equals("1")){
				//system Preferences
				hrPreferences=hrManager.getObjectsByParameter(HRPreferences.class, "user_type", new Long(1));
			}else if((type!=null && !type.equals(""))&& type.equals("2")){
				//userPreferences
				hrPreferences=hrManager.getObjectsByParameter(HRPreferences.class, "user_type", new Long(2));
			}else if((type!=null && !type.equals(""))&& type.equals("4")){
				//operatingPreferences
				 hrPreferences=hrManager.getObjectsByParameter(HRPreferences.class, "user_type", new Long(4));
			}
			//else if(hrPreferences.isEmpty() || hrPreferences.size()==0)
			else{
				log.debug("hrPreferences.size()"+hrPreferences.size());
				HRPreferences preference=new HRPreferences();
				hrPreferences.add(preference);
			}
			preferences.setPreferences(hrPreferences);
		}
		
		if(request.getMethod().equals("POST")){
			
			int counter=0;
			log.debug("request.getParameterMap()>>>>>>>>>>>>"+request.getParameterMap());
			List hrPreferences=null;
			preferences=new PreferencesCommand();
			
			if(stringCounter!=null && !stringCounter.equals("")){
				counter=new Long(stringCounter).intValue();
			}
			
			if((type!=null && !type.equals(""))&& type.equals("1")){
				//operatingPreferences
				hrPreferences=hrManager.getObjectsByParameter(HRPreferences.class, "user_type", new Long(1));
			}
			
			if((type!=null && !type.equals(""))&& type.equals("2")){
				//userPreferences
				hrPreferences=hrManager.getObjectsByParameter(HRPreferences.class, "user_type", new Long(2));
			}
			
			if((type!=null && !type.equals(""))&& type.equals("4")){
				//operatingPreferences
				 hrPreferences=hrManager.getObjectsByParameter(HRPreferences.class, "user_type", new Long(4));
			}
			
			if(hrPreferences==null || hrPreferences.isEmpty() || hrPreferences.size()==0){
				log.debug("inside years list ==null"+hrPreferences.size());
				HRPreferences preference=new HRPreferences();
				hrPreferences.add(preference);
				preferences.setPreferences(hrPreferences);
			}else{
				preferences.setPreferences(hrPreferences);
			}
			
			if(preferences!=null){
				int diff=counter-preferences.getPreferences().size();
				log.debug("the difference between counter and vacationcommand>>>>>>>>"+diff);
				for(int i=0;i<diff;i++){
					HRPreferences preference=new HRPreferences();
					preferences.getPreferences().add(preference);
				}
			}
			log.debug("preferencesCommand.getPreferences().size() post>>>>>>>>>>>>>>"+preferences.getPreferences().size());
		}
		return preferences;
	}
	
	@Override
	protected Map referenceData(HttpServletRequest request, Object command,Errors errors) throws Exception {
		
		Map model = new HashMap();
		
		PreferencesCommand result = (PreferencesCommand) command;
		
		String type = request.getParameter("type");
		
		List hrDefienedPreferences=null;
		
		if((type!=null && !type.equals(""))&& type.equals("1")){
			//system Preferences
			hrDefienedPreferences=hrManager.getObjectsByParameter(HRDefinedPreferences.class, "id.user_type", new String("1"));
		}
		
		if((type!=null && !type.equals(""))&& type.equals("2")){
			//userPreferences
			hrDefienedPreferences=hrManager.getObjectsByParameter(HRDefinedPreferences.class, "id.user_type", new String("2"));
		}
		
		if((type!=null && !type.equals(""))&& type.equals("4")){
			//operatingPreferences
			hrDefienedPreferences=hrManager.getObjectsByParameter(HRDefinedPreferences.class, "id.user_type", new String("4"));
		}

	    model.put("type",type);
	    model.put("defienedPreferences",hrDefienedPreferences);
		
	    return model;
	}
	
	@Override
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {

		PreferencesCommand preferencesCommand = (PreferencesCommand) command;
		String type = request.getParameter("type");
		for(int i=0;i<preferencesCommand.getPreferences().size();i++){
			String preference=request.getParameter("preference"+i);
			String choice=request.getParameter("choice"+i);
			String userCode=request.getParameter("userCode"+i);
			
			if(errors.getErrorCount()==0){
				if((preference==null||preference.equals(""))||(choice==null ||choice.equals("")) ) {
					errors.reject("commons.errors.requiredFields","reqiredFields");
				}
			}
			
			HRPreferences hrpreferences=(HRPreferences)preferencesCommand.getPreferences().get(i);
			if((type!=null && !type.equals(""))&& type.equals("1")){
				hrpreferences.setUser_code("system");
			}
			
			if((type!=null && !type.equals(""))&& type.equals("2")){
				Employee emp=(Employee)request.getSession().getAttribute("employee");
				hrpreferences.setUser_code(emp.getUser().getUsername());
			}
			
			if((type!=null && !type.equals(""))&& type.equals("4")){
				hrpreferences.setUser_code("4s");
			}
			
			hrpreferences.setUser_type(new Long(type));
			hrpreferences.setPvalue(choice);
			hrpreferences.setProperty(preference);
		}
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		
		PreferencesCommand result = (PreferencesCommand) command;
		
		String type = request.getParameter("type");
		
		for(int i=0; i<result.getPreferences().size();i++){
			hrManager.saveObject(result.getPreferences().get(i));
		}
		
		return new ModelAndView(new RedirectView("preferencesForm.html?type="+type));
	}
}
