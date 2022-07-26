package com._4s_.attendance.web.action;


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

import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.Vacation;
import com._4s_.timesheet.web.validate.ValidationStatus;

public class VacationForm extends BaseSimpleFormController{
	AttendanceManager attendanceManager;


	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}

	protected Object formBackingObject(HttpServletRequest request) throws ServletException 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String vacationCode=request.getParameter("vacationCode");
		log.debug("--------vacationCode------"+vacationCode);
		
		Vacation vacation = null;
		
		if(vacationCode==null || vacationCode.equals(""))
		{
			log.debug("--------vacationCode==null------");
			vacation=new Vacation();
			
		} else {
			vacation=(Vacation)attendanceManager.getObjectByParameter(Vacation.class,"vacation", vacationCode);
		}
		
		
		
		
		log.debug("---------vacation-------"+vacation);
		log.debug("---------vacation.may transfer-------"+vacation.getMay_transfer());
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return vacation;
	}
	
	//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Vacation vacation= (Vacation) command;
		log.debug("-----vacation code----"+vacation.getVacation());
		log.debug("type " + vacation.getType());
		log.debug("name " + vacation.getName());
		Map model=new HashMap();
		String vacationCode=request.getParameter("vacationCode");
		log.debug("vacationCode------"+vacationCode);
		model.put("vacationCode",vacationCode);
		List vacationsList=attendanceManager.getObjects(Vacation.class);
		model.put("vacationsList", vacationsList);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}

	//**************************************** onBind ***********************************************\\	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
//**************************************** onBindAndValidate ***********************************************\\
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Vacation vacation= (Vacation) command;
		ValidationStatus status = attendanceManager.validateVacation(vacation);
		
		if (status.getStatus().equals("False") && status.getMsg().equals("Mandatory")) {
			errors.rejectValue(status.getObjAttribute(), "commons.errors.requiredFields");
		}
		if (status.getStatus().equals("False") && status.getMsg().equals("Duplicate")) {
			errors.rejectValue(status.getObjAttribute(), "commons.errors.duplicateFieldEntry");
		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{	
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Vacation vacation= (Vacation) command;
		log.debug("-----vacation code----"+vacation.getVacation());
		log.debug("type " + vacation.getType());
		log.debug("name " + vacation.getName());
		String may_transfer = request.getParameter("may_transfer");
		log.debug("--------may_transfer------"+may_transfer);
		if(may_transfer == null || may_transfer.equals("")){
			log.debug("--------may_transfer------"+may_transfer);
			vacation.setMay_transfer("0");
		} else if (may_transfer.equals("on")) {
			vacation.setMay_transfer("1");
		}
		log.debug("---------vacation.may transfer-------"+vacation.getMay_transfer());
		log.debug(vacation.getEname()+","+ vacation.getForm()+","+ vacation.getMax_days_trnsfer()+","+ vacation.getMay_transfer()+","+ 
				vacation.getMonthes_zero_entitled()+","+ vacation.getName()+","+ vacation.getPayed()+","+ vacation.getTransfer_to_vacation()+","+ vacation.getType()+","+ vacation.getVacation());
		attendanceManager.saveObject(vacation);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("vacationView.html"));
	}
}
