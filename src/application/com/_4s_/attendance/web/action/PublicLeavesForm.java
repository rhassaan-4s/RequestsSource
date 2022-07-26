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

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.attendance.model.PublicLeaves;
import com._4s_.attendance.model.PublicLeavesListWrapper;
import com._4s_.attendance.service.AttendanceManager;
import com._4s_.common.web.action.BaseSimpleFormController;

public class PublicLeavesForm extends BaseSimpleFormController{
	private AttendanceManager attendanceManager;
	
	
	private List<PublicLeaves> oldLeaves = null;

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

	protected Object formBackingObject(HttpServletRequest request) throws ServletException 
	{
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
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   return pubLeaves;
	}
	
	//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		PublicLeavesListWrapper pubLeaves =  (PublicLeavesListWrapper)command;
		
		
		log.debug("-----rules----"+pubLeaves.getClass());
		Map model=new HashMap();
		
		
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
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
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
