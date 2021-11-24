package com._4s_.requestsApproval.web.action;


import java.util.ArrayList;
import java.util.Date;
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

import com._4s_.common.model.Settings;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.requestsApproval.model.AccessLevels;
import com._4s_.requestsApproval.model.GroupAcc;
import com._4s_.requestsApproval.model.LoginUsers;
import com._4s_.requestsApproval.service.RequestsApprovalManager;

public class AccessLevelsForm extends BaseSimpleFormController{
	RequestsApprovalManager requestsApprovalManager;

	public RequestsApprovalManager getRequestsApprovalManager() {
		return requestsApprovalManager;
	}

	public void setRequestsApprovalManager(
			RequestsApprovalManager requestsApprovalManager) {
		this.requestsApprovalManager = requestsApprovalManager;
	}
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException 
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String requestTypeId=request.getParameter("requestTypeId");
		log.debug("--------requestTypeId------"+requestTypeId);
		
		AccessLevels accessLevel=new AccessLevels();;
		
		return accessLevel;
	}
	
	//**************************************** referenceData ***********************************************\\
	protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");


		Map model=new HashMap();
		
		Settings settings = (Settings)request.getSession().getAttribute("settings");
		boolean accessLevelsCheckDate = settings.getAccessLevelsCheckDate();

		List loginUsers=requestsApprovalManager.getObjectsOrderedByField(LoginUsers.class, "name");
		log.debug("-------loginUsers.size---before"+loginUsers.size());
		List currentEmps=new ArrayList();
		// Copied from lotus /////////////////////////////
		Date date = new Date();
		//////////////////////////////////////////////////
		log.debug("date check  =  "+date);
		for (int i = 0; i < loginUsers.size(); i++) {
			LoginUsers loginUser=(LoginUsers) loginUsers.get(i);
			log.debug("----i----"+loginUser.getEmpCode());
			if(accessLevelsCheckDate == false){ //Lehaa
				if(loginUser.getEndServ()==null || loginUser.getEndServ().equals("")){
					log.debug("---before removing--i----"+loginUser.getEmpCode());
					currentEmps.add(loginUser);
	//				log.debug("----login----"+loginUsers.get(i));	
				}
			} else { // Lotus
				if(loginUser.getEndServ()==null || loginUser.getEndServ().equals("") || loginUser.getEndServ().equals(date) || loginUser.getEndServ().after(date)){
					log.debug("---before removing--i----"+loginUser.getEmpCode());
					currentEmps.add(loginUser);
	//				log.debug("----login----"+loginUsers.get(i));	
				}
			}
			
		}
		log.debug("-------currentEmps.size---after"+currentEmps.size());
		List tempList=requestsApprovalManager.getObjects(GroupAcc.class);
		List <GroupAcc> groupList= new ArrayList();
		
		for(int i=0;i<tempList.size();i++){
			GroupAcc group=(GroupAcc)tempList.get(i);
			
			if(group.getAccessLevel().size() !=0){
				
				groupList.add(group);
				
			}
			else {
				requestsApprovalManager.removeObject(group);
			}
				
			
		}
		
		model.put("loginUsers", currentEmps);
		
		model.put("groupList", groupList);
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return model;
	}

	//**************************************** onBind ***********************************************\\	
	protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		String groupTitle=request.getParameter("groupTitle");
	
		    
			GroupAcc req=(GroupAcc)requestsApprovalManager.getObjectByParameter(GroupAcc.class,"title",groupTitle);
		
		
		if(errors.getErrorCount()==0)
		{
		
			if(req!=null)
			{
				errors.reject("requestsApproval.errors.invalidEmpCodeOrName");
			}
			
		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
//**************************************** onBindAndValidate ***********************************************\\
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	//**************************************** onSubmit ***********************************************\\	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)throws Exception 
	{	
		
		AccessLevels accessLevel=(AccessLevels) command;
		String groupTitle=request.getParameter("groupTitle");
		String groupAdd=request.getParameter("groupAdd");
	    
		GroupAcc req=(GroupAcc)requestsApprovalManager.getObjectByParameter(GroupAcc.class,"title",groupTitle);
	
		String[] employee=request.getParameterValues("employee");
		if(groupTitle!=null&&!groupTitle.trim().equals("")&&employee!=null&&!employee.equals("")&&groupAdd.equals("")){
		
			GroupAcc groupacc=new GroupAcc();		
		    groupacc.setTitle(groupTitle);
		 
		
		requestsApprovalManager.saveObject(groupacc);
		
		//accessLevel.setLevel_id(groupacc);
		
		for(int i=0;i<employee.length;i++){			
			accessLevel=new AccessLevels();
			accessLevel.setLevel_id(groupacc);
		     LoginUsers loginuser=(LoginUsers)requestsApprovalManager.getObject(LoginUsers.class, new Long(employee[i]));
		
		    accessLevel.setEmp_id(loginuser);
		
		    requestsApprovalManager.saveObject(accessLevel);
		
		  }
		
		}
		
		if(employee!=null&&!employee.equals("")&&groupAdd!=null&&!groupAdd.equals("")){
		
			
			
			GroupAcc groupacc=(GroupAcc)requestsApprovalManager.getObject(GroupAcc.class,new Long(groupAdd));
		  		 
	
	
		for(int i=0;i<employee.length;i++){	
			
			LoginUsers loginuser=(LoginUsers)requestsApprovalManager.getObject(LoginUsers.class, new Long(employee[i]));
			
			AccessLevels accessLeve=(AccessLevels)requestsApprovalManager.getObjectByTwoObjects(AccessLevels.class, "level_id", groupacc, "emp_id", loginuser);
			
			if(accessLeve==null){
			accessLevel=new AccessLevels();
			
			accessLevel.setLevel_id(groupacc);
			
		     
		    accessLevel.setEmp_id(loginuser);
		
		    requestsApprovalManager.saveObject(accessLevel);
		    
			}
		
		  }
		
		}
		
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
}