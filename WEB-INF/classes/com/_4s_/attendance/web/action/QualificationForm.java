package com._4s_.attendance.web.action;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.attendance.model.Qualification;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.timesheet.web.validate.ValidationStatus;

@Controller
@RequestMapping(value="/qualificationForm.html",produces="text/html;charset=UTF-8")
public class QualificationForm extends BaseSimpleFormController{
	@Autowired
	private AttendanceManager attendanceManager;

	private String contentType= "text/html;charset=UTF-8";

	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String qualificationCode=request.getParameter("qualificationCode");
		log.debug("--------qualificationCode------"+qualificationCode);
		
		Qualification qualification = null;
		
		if(qualificationCode==null || qualificationCode.equals(""))
		{
			log.debug("--------qualificationCode==null------");
			qualification=new Qualification();
			
		} else {
			qualification=(Qualification)attendanceManager.getObjectByParameter(Qualification.class,"qual", qualificationCode);
		}
		log.debug("---------qualification-------"+qualification);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.put("qualification", qualification);
	   return "qualificationForm";
	}
	
	//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request, @ModelAttribute("qualification") Qualification command)
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Qualification qualification= (Qualification) command;
		
		log.debug("-----qualification code----"+qualification.getQual());
		Map model=new HashMap();
		String qualificationCode=request.getParameter("qualificationCode");
		log.debug("qualificationCode------"+qualificationCode);
		model.put("qualificationCode",qualificationCode);
		List qualificationsList=attendanceManager.getObjects(Qualification.class);
		model.put("qualificationsList", qualificationsList);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}

	//**************************************** onBind ***********************************************\\	
//	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//	}
////**************************************** onBindAndValidate ***********************************************\\
//	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
//	{
//		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		Qualification qualification= (Qualification) command;
//		ValidationStatus status = attendanceManager.validateQualification(qualification);
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
	
	//**************************************** onSubmit ***********************************************\\	
	@RequestMapping(method = RequestMethod.POST,
			consumes=MediaType.APPLICATION_FORM_URLENCODED)
	public String processSubmit(HttpServletRequest request,HttpServletResponse response,
			@Valid@ModelAttribute("qualification") Qualification command,
			BindingResult errors,
			Model model) throws Exception
	{
		try {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			log.debug("response " + response.getContentType());
			log.debug("request " + request.getContentType());
			response.setContentType(contentType);
			Qualification qualification= (Qualification) command;
			log.debug("----qualification code -onsubmit-----"+qualification.getQual());
			log.debug("qualification name " + command.getName());
			String qualificationCode=request.getParameter("qualificationCode");
			log.debug("qualificationCode------"+qualificationCode);
			if (errors.hasErrors()) {
				log.debug("qualification code"+qualificationCode);
				//			return new ModelAndView(new RedirectView("qualificationForm.html?qualificationCode="+qualificationCode));

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
				return "qualificationForm";
			} else {
				log.debug("no errors");
				log.debug("will save qualification");
				attendanceManager.saveObject(qualification);
				log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				//			return new ModelAndView(new RedirectView("qualificationView.html"));
				return "qualificationView";
			}
		}catch (Exception e) {
			System.out.println("Failed to validate <or better message>" + e); 
			e.printStackTrace();
			return "qualificationView";
		}
	}
}
