package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.Degree;
import com._4s_.HR.model.DiscDays;
import com._4s_.HR.model.HRCity;
import com._4s_.HR.model.HRClosedMonthes;
import com._4s_.HR.model.HREffect;
import com._4s_.HR.model.HREmployee;
import com._4s_.HR.model.HRMonth;
import com._4s_.HR.model.HRYear;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class DiscDaysForm extends BaseSimpleFormController{
	
	private HRManager hrManager;
	
	public HRManager getHrManager() {
		return hrManager;
	}
	
	public void setHrManager(HRManager hrManager) {
		this.hrManager = hrManager;
	}
	
	///**************************************** formBackingObject ***********************************************\\
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		String discId=request.getParameter("discId");
		log.debug("discId"+discId);
		DiscDays discDays=null;
		if(discId==null || discId.equals("")){
			discDays=new DiscDays();
		}else{
			discDays=(DiscDays)hrManager.getObject(DiscDays.class, new Long(discId));
		}
		log.debug("discDays"+discDays);

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return discDays;
	}
	
	//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		Map model=new HashMap();
		
		DiscDays dd = (DiscDays)command;
		
		String discId=request.getParameter("discId");
		log.debug("discId"+discId);
		model.put("discId",discId);
		
		if(discId==null || discId.equals("")){
			String disc_type=request.getParameter("disc_type");
			log.debug("disc_type"+disc_type);
			model.put("disc_type",disc_type);
		}else{
			DiscDays d = (DiscDays) hrManager.getObject(DiscDays.class, new Long(discId));
			String disc_type=d.getDisc_type()+"";
			log.debug("disc_type"+disc_type);
			model.put("disc_type",disc_type);
		}
		
		List monthList=hrManager.getObjects(HRMonth.class);
		log.debug("monthList.size()>>>>>>>>____________"+monthList.size());
		model.put("monthList", monthList);
		
		List yearList=hrManager.getObjects(HRYear.class);
		log.debug("yearList.size()>>>>>>>>____________"+yearList.size());
		model.put("yearList", yearList);
		
		List discDaysList=hrManager.getObjects(DiscDays.class);
		log.debug("discDaysList.size()>>>>>>>>____________"+discDaysList.size());
		model.put("discDaysList", discDaysList);
		
		model.put("employeeCode", dd.getEmp_id());
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}
	
	//**************************************** onBind ***********************************************\\	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		DiscDays dd = (DiscDays)command;
		
		String employeeName=request.getParameter("name");
		String employeeCode=request.getParameter("emp_id");
		log.debug("name-"+employeeName+" code-"+employeeCode);
		if((employeeName!=null && !employeeName.equals(""))){
		  HREmployee emp = (HREmployee) hrManager.getObjectByParameter(HREmployee.class, "name", employeeName);
		  dd.setEmp_id(emp.getEmpCode());
		  dd.setEmpName(emp.getName());
		}
		
		if((employeeCode!=null && !employeeCode.equals(""))){
			  HREmployee emp = (HREmployee) hrManager.getObjectByParameter(HREmployee.class, "empCode", employeeCode);
			  dd.setEmp_id(emp.getEmpCode());
			  dd.setEmpName(emp.getName());
		}
		log.debug("dd name-"+dd.getEmpName()+" dd code-"+dd.getEmp_id());
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	//**************************************** onBindAndValidate ***********************************************\\		
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		DiscDays discDays=(DiscDays)command;
		
		//Minute, Hour, and Day are @Transient so they aren't retrieved from DB 
		String minute = request.getParameter("minute");
		String hour = request.getParameter("hour");
		String day = request.getParameter("day");
		discDays.setNo_days(0.0);
		discDays.setNo_days(new Double(day), new Double(hour), new Double(minute));
		
		if(errors.getErrorCount()==0){
			if(discDays.getEmp_id()==null || discDays.getEmp_id().equals("")){
				errors.rejectValue("emp_id", "commons.errors.requiredFields","requiredFields");
			}
		}
		
		if(errors.getErrorCount()==0){
			DiscDays tit=(DiscDays)hrManager.getObjectByParameter(DiscDays.class,"id", discDays.getId());
			if(tit!=null && (!tit.getId().equals(discDays.getId()))){
				errors.rejectValue("id", "hr.error.codeExists","code exists");
			}
		}
		
		if(errors.getErrorCount()==0){
			if(discDays.getMonth()==null || discDays.getMonth().equals("")){
				errors.rejectValue("month", "commons.errors.requiredFields");
			}
		}
		
		if(errors.getErrorCount()==0){
			if(discDays.getYear()==null || discDays.getYear().equals("")){
				errors.rejectValue("year", "commons.errors.requiredFields");
			}
		}
		
		if(errors.getErrorCount()==0){
			String monthId = request.getParameter("month");
			String yearId = request.getParameter("year");
			HRMonth month = (HRMonth) hrManager.getObjectByParameter(HRMonth.class, "id", new Long(monthId));
			HRYear year = (HRYear) hrManager.getObjectByParameter(HRYear.class, "id", new Long(yearId));
			HRClosedMonthes hrClosedMonthes = (HRClosedMonthes) hrManager.getObjectByTwoObjects(HRClosedMonthes.class, "month_id", month, "year_id", year);
			if(hrClosedMonthes != null){
				errors.rejectValue("month", "commons.errors.closedMonth", "closedMonth");
			}
		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	//**************************************** onSubmit ***********************************************\\		
	public ModelAndView onSubmit(HttpServletRequest request,HttpServletResponse response, Object command, BindException errors)throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Map model=new HashMap();
		
		DiscDays discDays=(DiscDays)command;
		model.put("disc_type", discDays.getDisc_type());
		hrManager.saveObject(discDays);

		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView(getSuccessView()),model);
	}
}
