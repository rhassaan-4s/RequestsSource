package com._4s_.common.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Company;
import com._4s_.common.model.Settings;
import com._4s_.common.service.BaseManager;
import com._4s_.security.model.SecurityApplication;

@Controller
@RequestMapping("/settingsForm.html")
public class SettingsForm extends BaseSimpleFormController {

	@Autowired
	protected BaseManager baseManager = null;

	public BaseManager getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model, HttpServletRequest request) {

		Settings settings = (Settings) baseManager.getObject(Settings.class, new Long(1));

//		if(request.getMethod().equals("POST")){
//		
//			String ipAddressEnabled = request.getParameter("ipAddressEnabled");
//			if(ipAddressEnabled == null || ipAddressEnabled.equals("")){
//				settings.setIpAddressEnabled(false);
//			}
//			
//			String annualVacBalDaysEnabled = request.getParameter("annualVacBalDaysEnabled");
//			if(annualVacBalDaysEnabled == null || annualVacBalDaysEnabled.equals("")){
//				settings.setAnnualVacBalDaysEnabled(false);
//			}
//			
//			String attendanceRequestEn = request.getParameter("attendanceRequestEn");
//			if(attendanceRequestEn == null || attendanceRequestEn.equals("")){
//				settings.setAttendanceRequestEn(false);
//			}
//			
//			String automaticRequestsValidation = request.getParameter("automaticRequestsValidation");
//			if(automaticRequestsValidation == null || automaticRequestsValidation.equals("")){
//				settings.setAutomaticRequestsValidation(false);
//			}
//			
//			String fingerprintEnabled = request.getParameter("fingerprintEnabled");
//			if(fingerprintEnabled == null || fingerprintEnabled.equals("")){
//				settings.setFingerprintEnabled(false);
//			}
//			
//			String isLocationAccuracyEnabled = request.getParameter("isLocationAccuracyEnabled");
//			if(isLocationAccuracyEnabled == null || isLocationAccuracyEnabled.equals("")){
//				settings.setIsLocationAccuracyEnabled(false);
//			}
//			String showAddressOnForm = request.getParameter("showAddressOnForm");
//			if(showAddressOnForm == null || showAddressOnForm.equals("")){
//				settings.setShowAddressOnForm(false);
//			}
//			String showRequestsOnCalendar = request.getParameter("showRequestsOnCalendar");
//			if(showRequestsOnCalendar == null || showRequestsOnCalendar.equals("")){
//				settings.setShowRequestsOnCalendar(false);
//			}
//			String errandTimeFromSystem = request.getParameter("errandTimeFromSystem");
//			if(errandTimeFromSystem == null || errandTimeFromSystem.equals("")){
//				settings.setErrandTimeFromSystem(false);
//			}
//			String obligateNotes = request.getParameter("obligateNotes");
//			if(obligateNotes == null || obligateNotes.equals("")){
//				settings.setObligateNotes(false);
//			}
//			
//			String signoutBeforePermissionErrand = request.getParameter("signoutBeforePermissionErrand");
//			if (signoutBeforePermissionErrand == null || signoutBeforePermissionErrand.equals("")) {
//				settings.setSignoutBeforePermissionErrand(false);
//			}
//			
//			String managerCanModifyAttendance = request.getParameter("managerCanModifyAttendance");
//			if (managerCanModifyAttendance == null || managerCanModifyAttendance.equals("")) {
//				settings.setManagerCanModifyAttendance(false);
//			}
//			
//			String androidAttAutomaticApproval = request.getParameter("androidAttAutomaticApproval");
//			if (androidAttAutomaticApproval == null || androidAttAutomaticApproval.equals("")) {
//				settings.setAndroidAttAutomaticApproval(false);
//			}
//			
//			String isTimesheetEnabled = request.getParameter("isTimesheetEnabled");
//			if (isTimesheetEnabled == null || isTimesheetEnabled.equals("")) {
//				settings.setIsTimesheetEnabled(false);
//			}
//			
//			String sqlServerConnectionEnabled = request.getParameter("sqlServerConnectionEnabled");
//			if (sqlServerConnectionEnabled == null || sqlServerConnectionEnabled.equals("")) {
//				settings.setSqlServerConnectionEnabled(false);
//			}
//			String desktopHrApplication = request.getParameter("desktopHrApplication");
//			if (desktopHrApplication == null || desktopHrApplication.equals("")) {
//				settings.setDesktopHrApplication(false);
//			}
//		}
		model.addAttribute(settings);
		return "settingsForm";

	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(HttpServletRequest request, @Valid @ModelAttribute("settings") Settings command,
			BindingResult result, SessionStatus status, Model model) {
		Settings settings = (Settings) command;
		log.error(">>>> start of processSubmit");

		String ipAddressEnabled = request.getParameter("ipAddressEnabled");
		if (ipAddressEnabled == null || ipAddressEnabled.equals("")) {
			settings.setIpAddressEnabled(false);
		}

		String annualVacBalDaysEnabled = request.getParameter("annualVacBalDaysEnabled");
		if (annualVacBalDaysEnabled == null || annualVacBalDaysEnabled.equals("")) {
			settings.setAnnualVacBalDaysEnabled(false);
		}

		String attendanceRequestEn = request.getParameter("attendanceRequestEn");
		if (attendanceRequestEn == null || attendanceRequestEn.equals("")) {
			settings.setAttendanceRequestEn(false);
		}

		String automaticRequestsValidation = request.getParameter("automaticRequestsValidation");
		if (automaticRequestsValidation == null || automaticRequestsValidation.equals("")) {
			settings.setAutomaticRequestsValidation(false);
		}

		String fingerprintEnabled = request.getParameter("fingerprintEnabled");
		if (fingerprintEnabled == null || fingerprintEnabled.equals("")) {
			settings.setFingerprintEnabled(false);
		}

		String isLocationAccuracyEnabled = request.getParameter("isLocationAccuracyEnabled");
		if (isLocationAccuracyEnabled == null || isLocationAccuracyEnabled.equals("")) {
			settings.setIsLocationAccuracyEnabled(false);
		}
		String showAddressOnForm = request.getParameter("showAddressOnForm");
		if (showAddressOnForm == null || showAddressOnForm.equals("")) {
			settings.setShowAddressOnForm(false);
		}
		String showRequestsOnCalendar = request.getParameter("showRequestsOnCalendar");
		if (showRequestsOnCalendar == null || showRequestsOnCalendar.equals("")) {
			settings.setShowRequestsOnCalendar(false);
		}
		String errandTimeFromSystem = request.getParameter("errandTimeFromSystem");
		if (errandTimeFromSystem == null || errandTimeFromSystem.equals("")) {
			settings.setErrandTimeFromSystem(false);
		}
		String obligateNotes = request.getParameter("obligateNotes");
		if (obligateNotes == null || obligateNotes.equals("")) {
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
		System.out.println("isTimesheetEnabled " + isTimesheetEnabled);
		if (isTimesheetEnabled == null || isTimesheetEnabled.equals("")) {
			settings.setIsTimesheetEnabled(false);
		}
		System.out.println("*****isTimesheetEnabled " + settings.getIsTimesheetEnabled());
		String sqlServerConnectionEnabled = request.getParameter("sqlServerConnectionEnabled");
		if (sqlServerConnectionEnabled == null || sqlServerConnectionEnabled.equals("")) {
			settings.setSqlServerConnectionEnabled(false);
		}
		String desktopHrApplication = request.getParameter("desktopHrApplication");
		if (desktopHrApplication == null || desktopHrApplication.equals("")) {
			settings.setDesktopHrApplication(false);
		}
		SecurityApplication application = (SecurityApplication) baseManager
				.getObjectByParameter(SecurityApplication.class, "name", "timesheet");
		if (settings.getIsTimesheetEnabled()) {
			// set application active
			application.setIs_active(new Boolean(true));
		} else {
			// set application inactive
			application.setIs_active(new Boolean(false));
		}

		////////////////////////////////////////
		/// Default values not in form
		//////////////////////////////////////

		if (settings.getAccessLevelsCheckDate() == null) {
			settings.setAccessLevelsCheckDate(false);
		}
		if (settings.getEmpRequestCheckDate() == null) {
			settings.setEmpRequestCheckDate(false);
		}
		if (settings.getAddNewData() == null) {
			settings.setAddNewData(false);
		}
		if (settings.getCheckPostedRequests() == null) {
			settings.setCheckPostedRequests(false);
		}
		if (settings.getEmpRequestTypeException() == null) {
			settings.setEmpRequestTypeException(false);
		}
		if (settings.getTAttRepWithHrsMin() == null) {
			settings.setTAttRepWithHrsMin(true);
		}
		if (settings.getSpecialVacExcep() == null) {
			settings.setSpecialVacExcep(false);
		}
		if (settings.getCreatingMail() == null) {
			settings.setCreatingMail(true);
		}
		if (settings.getVacLimitProblem() == null) {
			settings.setVacLimitProblem(false);
		}
		if (settings.getVacationRequestExcep() == null) {
			settings.setVacationRequestExcep(true);
		}
		if (settings.getReqPeriodDate() == null) {
			settings.setReqPeriodDate(false);
		}
		if (settings.getWithdrawDaysQuartPolicy() == null) {
			settings.setWithdrawDaysQuartPolicy(false);
		}
		if (settings.getNotesValidation() == null) {
			settings.setNotesValidation(true);
		}
		if (settings.getWithoutSalPeriodValidation() == null) {
			settings.setWithoutSalPeriodValidation(true);
		}
		if (settings.getFromToRequestVald() == null) {
			settings.setFromToRequestVald(false);
		}
		if (settings.getWithoutSalaryVacEnabled() == null) {
			settings.setWithoutSalaryVacEnabled(false);
		}
		if (settings.getPeriodFromToEnabled() == null) {
			settings.setPeriodFromToEnabled(true);
		}
		if (settings.getFooterCopyrightsEnabled() == null) {
			settings.setFooterCopyrightsEnabled(true);
		}
		if (settings.getCompanyRulesEn() == null) {
			settings.setCompanyRulesEn(false);
		}
		if (settings.getExecuseEnabled() == null) {
			settings.setExecuseEnabled(false);
		}
		if (settings.getAutomaticSignInOut() == null) {
			settings.setAutomaticSignInOut(false);
		}
		if (settings.getFingerprintEnabled() == null) {
			settings.setFingerprintEnabled(false);
		}
		if (settings.getWebAttendanceAppEnabled() == null) {
			settings.setWebAttendanceAppEnabled(true);
		}
		///////////////////////////////////////
		Company c = settings.getCompany();
		baseManager.saveObject(c);
		baseManager.saveObject(application);
		baseManager.saveObject(settings);
		System.out.println("****saved settings");

		request.getSession().removeAttribute("settings");
		request.getSession().setAttribute("settings", settings);
		log.error(">>>> end of processSubmit");
		return new ModelAndView(new RedirectView("settingsForm.html"));

	}

}
