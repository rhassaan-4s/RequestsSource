package com._4s_.HR.web.action;

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

import com._4s_.HR.model.HRClosedMonthes;
import com._4s_.HR.model.HRMonth;
import com._4s_.HR.model.HRYear;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class CloseMonthFormController extends  BaseSimpleFormController{
	
	private HRManager hrManager;
	
	public HRManager getHrManager(){
		return hrManager;
	}
	public void setHrManager(HRManager hrManager){
		this.hrManager = hrManager;
	}
	
	//**************************************** formBackingObject ***********************************************\\
	protected Object formBackingObject(HttpServletRequest request) throws ServletException{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String closeId=request.getParameter("closeId");
		log.debug("closeId"+closeId);
		HRClosedMonthes closeMonth=null;
		if(closeId==null || closeId.equals("")){
			closeMonth=new HRClosedMonthes();
		}else{
			closeMonth=(HRClosedMonthes)hrManager.getObject(HRClosedMonthes.class, new Long(closeId));
		}
		log.debug("closeMonth"+closeMonth);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return closeMonth;
	}
	
	//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		Map model=new HashMap();
		String closeId=request.getParameter("closeId");
		log.debug("closeId"+closeId);
		model.put("closeId",closeId);
		
		List monthList=hrManager.getObjects(HRMonth.class);
		log.debug("monthList.size()>>>>>>>>____________"+monthList.size());
		model.put("monthList", monthList);
		
		List yearList=hrManager.getObjects(HRYear.class);
		log.debug("yearList.size()>>>>>>>>____________"+yearList.size());
		model.put("yearList", yearList);
		
		List closeMonthList=hrManager.getObjects(HRClosedMonthes.class);
		log.debug("closeMonthList.size()>>>>>>>>____________"+closeMonthList.size());
		model.put("closeMonthList", closeMonthList);
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}
	
	//**************************************** onBind ***********************************************\\	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onBindAndValidate ***********************************************\\		
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		HRClosedMonthes closeMonth = (HRClosedMonthes)command;
		
		if(errors.getErrorCount()==0){
			if(closeMonth.getMonth_id()==null || closeMonth.getMonth_id().equals("")){
				errors.rejectValue("month_id", "commons.errors.requiredFields");
			}
		}
		
		if(errors.getErrorCount()==0){
			if(closeMonth.getYear_id()==null || closeMonth.getYear_id().equals("")){
				errors.rejectValue("year_id", "commons.errors.requiredFields");
			}
		}
		
		if(errors.getErrorCount()==0){
			String monthId = request.getParameter("month_id");
			String yearId = request.getParameter("year_id");
			HRMonth month = (HRMonth) hrManager.getObjectByParameter(HRMonth.class, "id", new Long(monthId));
			HRYear year = (HRYear) hrManager.getObjectByParameter(HRYear.class, "id", new Long(yearId));
			HRClosedMonthes hrClosedMonthes = (HRClosedMonthes) hrManager.getObjectByTwoObjects(HRClosedMonthes.class, "month_id", month, "year_id", year);
			if(hrClosedMonthes != null){
				errors.rejectValue("month_id", "commons.errors.closedMonth", "closedMonth");
			}
		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,HttpServletResponse response, Object command, BindException errors)throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		HRClosedMonthes closeMonth=(HRClosedMonthes)command;
		log.debug("closeMonth.getId()__________>>>>>>>>>>>>>>>"+closeMonth.getId());

		hrManager.saveObject(closeMonth);
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
}
