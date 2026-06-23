package com._4s_.attendance.web.action;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.attendance.model.PublicLeaves;
import com._4s_.attendance.model.PublicLeavesListWrapper;
import com._4s_.attendance.model.VacRulesListWrapper;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.common.web.binders.BiCalendarDateBinder;
import com._4s_.requestsApproval.model.Vacation;
import com._4s_.requestsApproval.web.binders.VacationBinder;

@Controller
@RequestMapping("/publicLeavesForm.html")
public class PublicLeavesForm extends BaseSimpleFormController{
	@Autowired
	private AttendanceManager attendanceManager;
	
	@Autowired
	@Qualifier("dateBinder")
	private BiCalendarDateBinder dateBinder;
	
	private List<PublicLeaves> oldLeaves = null;
	
	public BiCalendarDateBinder getDateBinder() {
		return dateBinder;
	}

	public void setDateBinder(BiCalendarDateBinder dateBinder) {
		this.dateBinder = dateBinder;
	}

	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}
	
	

	public List<PublicLeaves> getOldLeaves() {
		return oldLeaves;
	}

	public void setOldLeaves(List<PublicLeaves> oldLeaves) {
		this.oldLeaves = oldLeaves;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		PublicLeavesListWrapper pubLeaves = new PublicLeavesListWrapper();
		List<PublicLeaves> p = new ArrayList<PublicLeaves>();
		
		if (request.getMethod().equals("GET")) {
			oldLeaves = new ArrayList<PublicLeaves>();
			p = (List<PublicLeaves>)attendanceManager.getObjectsOrderedByField(PublicLeaves.class,"indate");
			oldLeaves.addAll(p);
		}
//		if (request.getMethod().equals("POST")) {
//			p = (List)attendanceManager.getObjectsOrderedByField(PublicLeaves.class,"indate");
//		}
		log.debug("##################  vac rules list size " + p.size());
		String addRow = request.getParameter("addRow");
		log.debug("addRow " + addRow);
		if (addRow!=null && !addRow.isEmpty()) {
			PublicLeaves leaves = new PublicLeaves();
			p.add(leaves);
		}
			
		pubLeaves.setPubLeaves(p);
		
		model.put("leaves", pubLeaves);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return "publicLeavesForm";
	}
	
	//**************************************** referenceData ***********************************************\\
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request,@ModelAttribute("leaves") PublicLeavesListWrapper command)
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		PublicLeavesListWrapper pubLeaves =  (PublicLeavesListWrapper)command;
		
		
		log.debug("-----rules----"+pubLeaves.getClass());
		Map model=new HashMap();
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}

	@Override
	public void initBinder(HttpServletRequest request,WebDataBinder binder) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Starting init binder: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		super.initBinder(request,binder);
		binder.registerCustomEditor(BiCalendarDateBinder.class, dateBinder);
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
		PublicLeavesListWrapper pubLeaves =  (PublicLeavesListWrapper)command;
//		ValidationStatus status = attendanceManager.validateTitle(title);
//		
//		if (status.getStatus().equals("False") && status.getMsg().equals("Mandatory")) {
//			errors.rejectValue(status.getObjAttribute(), "commons.errors.requiredFields");
//		}
//		if (status.getStatus().equals("False") && status.getMsg().equals("Duplicate")) {
//			errors.rejectValue(status.getObjAttribute(), "commons.errors.duplicateFieldEntry");
//		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(HttpServletRequest request,
			@ModelAttribute("leaves") PublicLeavesListWrapper command,
//			BindingResult result,
			Model model) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		PublicLeavesListWrapper pubLeaves =  (PublicLeavesListWrapper)command;
//		Iterator<PublicLeaves> itrLeaves = pubLeaves.getPubLeaves().iterator();
		List<PublicLeaves> newLeaves = pubLeaves.getPubLeaves();
		for (int i =0 ; i <newLeaves.size(); i++) {
			PublicLeaves leaveNew = newLeaves.get(i);
			PublicLeaves leaveOld = null;
			try {
				leaveOld = oldLeaves.get(i);
				if (!leaveOld.equals(leaveNew)) {
					attendanceManager.saveObject(leaveNew);
				}
			} catch (Exception ex) {
				log.debug("new leave record");
				attendanceManager.saveObject(leaveNew);
			}
			
//			 SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.s");
//			 String formatedDate = formatDate.format(leave.getIndate());
//			 Date indate = formatDate.parse(formatedDate);
//			 leave.setIndate(indate);
//
//			 
//			attendanceManager.saveObject(leave);
		}
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView("publicLeavesForm.html"));
	}
}
