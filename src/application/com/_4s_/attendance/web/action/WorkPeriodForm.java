package com._4s_.attendance.web.action;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.attendance.model.WorkPeriod;
import com._4s_.attendance.model.WorkPeriodMaster;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.common.web.binders.BaseBinder;
import com._4s_.common.web.binders.TimestampBinder;
import com._4s_.common.web.binders.TimestampTimeBinder;

@Controller
@RequestMapping("/workperiodForm.html")
public class WorkPeriodForm extends BaseSimpleFormController{
	@Autowired
	private AttendanceManager attendanceManager;
	@Autowired
	private List customBinders = new ArrayList();
	
	
	private TimestampBinder timestampBinder;
	private TimestampTimeBinder timestampTimeBinder;
	
	

	public TimestampBinder getTimestampBinder() {
		return timestampBinder;
	}

	public void setTimestampBinder(TimestampBinder timestampBinder) {
		this.timestampBinder = timestampBinder;
	}

	public TimestampTimeBinder getTimestampTimeBinder() {
		return timestampTimeBinder;
	}

	public void setTimestampTimeBinder(TimestampTimeBinder timestampTimeBinder) {
		this.timestampTimeBinder = timestampTimeBinder;
	}

	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}

	
	public List getCustomBinders() {
		return customBinders;
	}

	public void setCustomBinders(List customBinders) {
		this.customBinders = customBinders;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request){
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String workperiodCode=request.getParameter("workperiodCode");
		log.debug("--------workperiodCode------"+workperiodCode);

		WorkPeriod period = null;
		WorkPeriodMaster master = null;

		if(workperiodCode==null || workperiodCode.equals(""))
		{
			log.debug("--------workperiodCode==null------");
			period=new WorkPeriod();
			master = new WorkPeriodMaster();
		} else {
			master = (WorkPeriodMaster)attendanceManager.getObjectByParameter(WorkPeriodMaster.class,"workperiods", workperiodCode);
			period=(WorkPeriod)attendanceManager.getObjectByParameter(WorkPeriod.class,"workperiods", master);
		}

		if(request.getMethod().equals("POST")) {
			String is_default = request.getParameter("is_default");
			if(is_default == null || is_default.equals("")){
				period.setIs_default("0");
				log.debug("period.is_default " + period.getIs_default());
			} else if (is_default.equals("on")) {
				period.setIs_default("1");;
				log.debug("period.is_default " + period.getIs_default());
			}

			String consider_delay = request.getParameter("considerdelay");
			if(consider_delay == null || consider_delay.equals("")){
				period.setConsider_delay(0);
				log.debug("period.setConsider_delay " + period.getConsider_delay());
			} else if (consider_delay.equals("on")) {
				period.setConsider_delay(1);
				log.debug("period.setConsider_delay " + period.getConsider_delay());
			}

			String consider_early = request.getParameter("considerearly");
			if(consider_early == null || consider_early.equals("")){
				period.setConsider_early(0);
				log.debug("period.setConsider_early " + period.getConsider_early());
			} else if (consider_early.equals("on")) {
				period.setConsider_early(1);
				log.debug("period.setConsider_early " + period.getConsider_early());
			}

			String consider_overtime = request.getParameter("considerovertime");
			if(consider_overtime == null || consider_overtime.equals("")){
				period.setConsider_overtime(0);
				log.debug("period.setConsider_overtime " + period.getConsider_overtime());
			} else if (consider_overtime.equals("on")) {
				period.setConsider_overtime(1);
				log.debug("period.setConsider_overtime " + period.getConsider_overtime());
			}
		}

		log.debug("---------period-------"+period);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.put("period",period);
		return "workperiodForm";
	}



	//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request,@ModelAttribute("period") WorkPeriod command)
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		WorkPeriod period= (WorkPeriod) command;
		log.debug("-----period code----"+period.getWorkperiods());
		Map model=new HashMap();
		String workperiodCode=request.getParameter("workperiodCode");
		log.debug("workperiodCode------"+workperiodCode);
		model.put("workperiodCode",workperiodCode);
		List periodsList=attendanceManager.getObjects(WorkPeriod.class);
		model.put("periodsList", periodsList);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}


	
	
	//**************************************** onBindAndValidate ***********************************************\\
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		WorkPeriod period= (WorkPeriod) command;
		log.debug(period.getConsider_delay());
		log.debug(period.getConsider_early());
		log.debug(period.getConsider_overtime());
		log.debug("errors.hasErrors() " + errors.hasErrors());

		//		ValidationStatus status = attendanceManager.validateWorkPeriod(period);
		//		
		//		if (status.getStatus().equals("False") && status.getMsg().equals("Mandatory")) {
		//			errors.rejectValue(status.getObjAttribute(), "commons.errors.requiredFields");
		//		}
		//		if (status.getStatus().equals("False") && status.getMsg().equals("Duplicate")) {
		//			errors.rejectValue(status.getObjAttribute(), "commons.errors.duplicateFieldEntry");
		//		}
		String is_default = request.getParameter("is_default");
		if(is_default == null || is_default.equals("")){
			period.setIs_default("0");
			log.debug("period.is_default " + period.getIs_default());
		} else if (is_default.equals("on")) {
			period.setIs_default("1");;
			log.debug("period.is_default " + period.getIs_default());
		}


		String consider_delay = request.getParameter("considerdelay");
		if(consider_delay == null || consider_delay.equals("")){
			period.setConsider_delay(0);
			log.debug("period.setConsider_delay " + period.getConsider_delay());
		} else if (consider_delay.equals("on")) {
			period.setConsider_delay(1);
			log.debug("period.setConsider_delay " + period.getConsider_delay());
		}

		String consider_early = request.getParameter("considerearly");
		if(consider_early == null || consider_early.equals("")){
			period.setConsider_early(0);
			log.debug("period.setConsider_early " + period.getConsider_early());
		} else if (consider_early.equals("on")) {
			period.setConsider_early(1);
			log.debug("period.setConsider_early " + period.getConsider_early());
		}

		String consider_overtime = request.getParameter("considerovertime");
		if(consider_overtime == null || consider_overtime.equals("")){
			period.setConsider_overtime(0);
			log.debug("period.setConsider_overtime " + period.getConsider_overtime());
		} else if (consider_overtime.equals("on")) {
			period.setConsider_overtime(1);
			log.debug("period.setConsider_overtime " + period.getConsider_overtime());
		}
		log.debug("errors.hasErrors() " + errors.hasErrors());
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	//**************************************** onSubmit ***********************************************\\	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(HttpServletRequest request,
			@ModelAttribute("period") WorkPeriod command,
//			BindingResult result,
			Model model) throws Exception {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		WorkPeriod period= (WorkPeriod) command;
		log.debug("----activity code -onsubmit-----"+period.getWorkperiods());

		log.debug("period.getConsider_delay() " +period.getConsider_delay());
		log.debug("period.getConsider_early " +period.getConsider_early());
		log.debug("period.getConsider_overtime " +period.getConsider_overtime());
		String consider_delay = request.getParameter("considerdelay");
		String is_default = request.getParameter("is_default");
		if(is_default == null || is_default.equals("")){
			period.setIs_default("0");
			log.debug("period.is_default " + period.getIs_default());
		} else if (is_default.equals("on")) {
			period.setIs_default("1");;
			log.debug("period.is_default " + period.getIs_default());

			List defaultPeriods = attendanceManager.getObjectsByParameter(WorkPeriod.class, "is_default", "1");
			Iterator itr = defaultPeriods.iterator();
			while(itr.hasNext()) {
				WorkPeriod p = (WorkPeriod)itr.next();
				if (!p.equals(period)) {
					p.setIs_default("0");
					attendanceManager.saveObject(p);
				}
			}
		}

		if(consider_delay == null || consider_delay.equals("")){
			period.setConsider_delay(0);
			log.debug("period.setConsider_delay " + period.getConsider_delay());
		} else if (consider_delay.equals("on")) {
			period.setConsider_delay(1);
			log.debug("period.setConsider_delay " + period.getConsider_delay());
		}

		String consider_early = request.getParameter("considerearly");
		if(consider_early == null || consider_early.equals("")){
			period.setConsider_early(0);
			log.debug("period.setConsider_early " + period.getConsider_early());
		} else if (consider_early.equals("on")) {
			period.setConsider_early(1);
			log.debug("period.setConsider_early " + period.getConsider_early());
		}

		String consider_overtime = request.getParameter("considerovertime");
		if(consider_overtime == null || consider_overtime.equals("")){
			period.setConsider_overtime(0);
			log.debug("period.setConsider_overtime " + period.getConsider_overtime());
		} else if (consider_overtime.equals("on")) {
			period.setConsider_overtime(1);
			log.debug("period.setConsider_overtime " + period.getConsider_overtime());
		}

//		if (period.getWorkperiods()!=null && period.getWorkperiods().getWorkperiods()==null) {
//			period.getStart_date().setNanos(0);
////			Object obj = attendanceManager.getObjectByParameter(WorkPeriod.class, "start_date", period.getStart_date());
////			WorkPeriod wp = (WorkPeriod)obj;
////			log.debug(wp.getWorkperiods());
//			attendanceManager.saveObject(period.getWorkperiods());
//			attendanceManager.saveObject(period);
//		}  else if (period.getWorkperiods()!=null && period.getWorkperiods().getWorkperiods()!=null) {
//			period.getStart_date().setNanos(0);
//			attendanceManager.saveObject(period);
//			Object obj = attendanceManager.getObjectByParameter(WorkPeriod.class, "start_date", period.getStart_date());
//			WorkPeriod wp = (WorkPeriod)obj;
//			log.debug(wp);
//			WorkPeriodMaster master = attendanceManager.getWorkPeriodMaster(period.getWorkperiods());
//			master.setWorkperiods(wp);
//			master.setName(period.getName());
//			master.setEname(period.getEname());
//			attendanceManager.saveObject(master);
//		}

		
		attendanceManager.saveObject(period.getWorkperiods());
		attendanceManager.saveObject(period);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("workperiodView.html"));
	}

	
//	@Override
//	public void initBinder(WebDataBinder binder) {
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Starting init binder: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		super.initBinder(binder);
//		binder.registerCustomEditor(Timestamp.class, timestampBinder);
//		binder.registerCustomEditor(Timestamp.class, timestampTimeBinder);
//	}
	
	
	@Override
	public void initBinder(HttpServletRequest request,WebDataBinder binder) {//(HttpServletRequest request, ServletRequestDataBinder binder) {
		// TODO Auto-generated method stub
//		super.initBinder(request, binder);
		Iterator itr = customBinders.iterator();
		BaseBinder custom = null;
		log.debug("Binding list of custom editors");
		log.debug("request.getParameterMap() " + request.getParameterMap().size());
		
		while (itr.hasNext()) {
			custom = (BaseBinder)itr.next();
			log.debug("custom binder class " + custom.getClass().getSimpleName());
			if (custom.getClass().getSimpleName().equals("TimestampBinder")) {
				log.debug(request.getParameter("period.time_in"));
				log.debug(request.getParameter("time_in"));
				log.debug("request.getParameterMap() " + request.getParameterMap().size());
				Iterator it = request.getParameterMap().keySet().iterator();
				 while(it.hasNext()) {
					 String param = (String)it.next();
					 log.debug("param " + param);
				        if (param.endsWith("date")){
				            binder.registerCustomEditor(custom.getBindedClass(), param, custom);
				        }
				    }
				 binder.registerCustomEditor(custom.getBindedClass(), "start_date", custom);
					binder.registerCustomEditor(custom.getBindedClass(), "end_date", custom);
			}
			if (custom.getClass().getSimpleName().equals("TimestampTimeBinder")) {
				log.debug("request.getParameterMap() " + request.getParameterMap().size());
				Iterator it = request.getParameterMap().keySet().iterator();
				 while(it.hasNext()) {
					 String param = (String)it.next();
					 log.debug("param " + param);
				        if (param.endsWith("_in")){
				            binder.registerCustomEditor(custom.getBindedClass(), param, custom);
				        }
				    }
				binder.registerCustomEditor(custom.getBindedClass(), "time_in", custom);
			}
		}
	}
}
