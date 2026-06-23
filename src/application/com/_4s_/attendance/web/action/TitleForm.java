package com._4s_.attendance.web.action;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.attendance.model.Qualification;
import com._4s_.attendance.model.Title;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.timesheet.model.TimesheetActivity;
import com._4s_.timesheet.web.validate.ValidationStatus;
@Controller
@RequestMapping(value="/titleForm.html",produces="text/html;charset=UTF-8")
public class TitleForm extends BaseSimpleFormController{
	@Autowired
	private AttendanceManager attendanceManager;


	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String titleCode=request.getParameter("titleCode");
		log.debug("--------titleCode------"+titleCode);

		Title title = null;

		if(titleCode==null || titleCode.equals(""))
		{
			log.debug("--------titleCode==null------");
			title=new Title();

		} else {
			title=(Title)attendanceManager.getObjectByParameter(Title.class,"title", titleCode);
		}
		log.debug("---------title-------"+title);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.put("title", title);
		return "titleForm";
	}

	//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request, @ModelAttribute("title") Title command) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Title title= (Title) command;
		log.debug("-----title code----"+title.getTitle());
		Map model=new HashMap();
		String titleCode=request.getParameter("titleCode");
		log.debug("titleCode------"+titleCode);
		model.put("titleCode",titleCode);
		List titlesList=attendanceManager.getObjects(Title.class);
		model.put("titlesList", titlesList);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}

	//	//**************************************** onBind ***********************************************\\	
	//	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
	//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	//	}
	////**************************************** onBindAndValidate ***********************************************\\
	//	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	//	{
	//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	//		Title title= (Title) command;
	//		ValidationStatus status = attendanceManager.validateTitle(title);
	//		
	//		if (status.getStatus().equals("False") && status.getMsg().equals("Mandatory")) {
	//			errors.rejectValue(status.getObjAttribute(), "commons.errors.requiredFields");
	//		}
	//		if (status.getStatus().equals("False") && status.getMsg().equals("Duplicate")) {
	//			errors.rejectValue(status.getObjAttribute(), "commons.errors.duplicateFieldEntry");
	//		}
	//		
	//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	//	}
	//	
	//**************************************** onSubmit ***********************************************\\	
	@RequestMapping(method = RequestMethod.POST,
			consumes=MediaType.APPLICATION_FORM_URLENCODED)
	public String processSubmit(HttpServletRequest request,HttpServletResponse response,
			@Valid@ModelAttribute("title") Title command,
			BindingResult errors,
			Model model) throws Exception {
		try {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Title title= (Title) command;
			log.debug("----activity code -onsubmit-----"+title.getTitle());
			if (errors.hasErrors()) {
				log.debug("code"+title.getTitle());
				log.debug("response " + response.getContentType());
				log.debug("request " + request.getContentType());
				log.debug("response.getHeaderNames() " + response.getHeaderNames().size());
				Iterator it = response.getHeaderNames().iterator();
				while(it.hasNext()) {
					log.debug("response " + (String)(it.next()));
				}
				Iterator itr = errors.getAllErrors().iterator();
				while(itr.hasNext()) {
					ObjectError error =  (ObjectError)itr.next();
					log.debug(error.getDefaultMessage());
				}
				//			return new ModelAndView("qualificationForm");
				return "titleForm";
			} else {
				attendanceManager.saveObject(title);
				return "titleView";
			}

		}catch (Exception e) {
			System.out.println("Failed to validate <or better message>" + e); 
			e.printStackTrace();
			return "titleView";
		}
		}
	}
