package com._4s_.common.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Settings;
import com._4s_.common.service.BaseManager;
import com._4s_.security.model.SecurityApplication;

public class SettingsForm extends BaseSimpleFormController{

	protected BaseManager baseManager = null;

	public BaseManager getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}

	
	
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		
		Settings settings = (Settings)baseManager.getObject(Settings.class , new Long(1));
		
		if(request.getMethod().equals("POST")){
		
			String ipAddressEnabled = request.getParameter("ipAddressEnabled");
			if(ipAddressEnabled == null || ipAddressEnabled.equals("")){
				settings.setIpAddressEnabled(false);
			}
			
			String annualVacBalDaysEnabled = request.getParameter("annualVacBalDaysEnabled");
			if(annualVacBalDaysEnabled == null || annualVacBalDaysEnabled.equals("")){
				settings.setAnnualVacBalDaysEnabled(false);
			}
			
			String attendanceRequestEn = request.getParameter("attendanceRequestEn");
			if(attendanceRequestEn == null || attendanceRequestEn.equals("")){
				settings.setAttendanceRequestEn(false);
			}
			
			String automaticRequestsValidation = request.getParameter("automaticRequestsValidation");
			if(automaticRequestsValidation == null || automaticRequestsValidation.equals("")){
				settings.setAutomaticRequestsValidation(false);
			}
			
			String fingerprintEnabled = request.getParameter("fingerprintEnabled");
			if(fingerprintEnabled == null || fingerprintEnabled.equals("")){
				settings.setFingerprintEnabled(false);
			}
			
			String isLocationAccuracyEnabled = request.getParameter("isLocationAccuracyEnabled");
			if(isLocationAccuracyEnabled == null || isLocationAccuracyEnabled.equals("")){
				settings.setIsLocationAccuracyEnabled(false);
			}
			String showAddressOnForm = request.getParameter("showAddressOnForm");
			if(showAddressOnForm == null || showAddressOnForm.equals("")){
				settings.setShowAddressOnForm(false);
			}
			String showRequestsOnCalendar = request.getParameter("showRequestsOnCalendar");
			if(showRequestsOnCalendar == null || showRequestsOnCalendar.equals("")){
				settings.setShowRequestsOnCalendar(false);
			}
			String errandTimeFromSystem = request.getParameter("errandTimeFromSystem");
			if(errandTimeFromSystem == null || errandTimeFromSystem.equals("")){
				settings.setErrandTimeFromSystem(false);
			}
			String obligateNotes = request.getParameter("obligateNotes");
			if(obligateNotes == null || obligateNotes.equals("")){
				settings.setObligateNotes(false);
			}
			
			String signoutBeforePermissionErrand = request.getParameter("signoutBeforePermissionErrand");
			if (signoutBeforePermissionErrand == null || signoutBeforePermissionErrand.equals("")) {
				settings.setSignoutBeforePermissionErrand(false);
			}
			
			String managerCanModifyAttendance = request.getParameter("managerCanModifyAttendance");
			if (managerCanModifyAttendance == null || managerCanModifyAttendance.equals("")) {
				settings.setManagerCanModifyAttendance(false);
			}
			
			String androidAttAutomaticApproval = request.getParameter("androidAttAutomaticApproval");
			if (androidAttAutomaticApproval == null || androidAttAutomaticApproval.equals("")) {
				settings.setAndroidAttAutomaticApproval(false);
			}
			
			String isTimesheetEnabled = request.getParameter("isTimesheetEnabled");
			if (isTimesheetEnabled == null || isTimesheetEnabled.equals("")) {
				settings.setIsTimesheetEnabled(false);
			}
		}
		return settings;

	}

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse arg1, Object cmd, BindException arg3) throws Exception {
		Settings  settings = (Settings)cmd;
		log.error(">>>> start of onSubmit() ");
		
		SecurityApplication application = (SecurityApplication)baseManager.getObjectByParameter(SecurityApplication.class, "name", "timesheet");
		if (settings.getIsTimesheetEnabled()) {
			//set application active
			application.setIs_active(new Boolean(true));
		} else {
			// set application inactive
			application.setIs_active(new Boolean(false));
		}
		baseManager.saveObject(application);
		baseManager.saveObject(settings);
		
				
		request.getSession().removeAttribute("settings");
		request.getSession().setAttribute("settings", settings);
		log.error(">>>> end of onSubmit() ");
		return new ModelAndView(new RedirectView("settingsForm.html"));
		
	}
	
}
