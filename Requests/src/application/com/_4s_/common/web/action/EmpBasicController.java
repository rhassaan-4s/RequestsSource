package com._4s_.common.web.action;

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

import com._4s_.common.model.EmpBasic;
import com._4s_.common.model.Settings;
import com._4s_.common.service.BaseManager;

public class EmpBasicController extends BaseSimpleFormController {

	protected BaseManager baseManager = null;

	public BaseManager getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("entering 'onSubmit' method....");
		}
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		EmpBasic emp = (EmpBasic)command;

		String empCode=request.getParameter("empCodeHidden");
		log.debug("emp code " + empCode);
		if(empCode==null || empCode.equals(""))
		{
			log
			.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> empCode: >>>>>>>>>>>>>>>>>>>>>>>>>>>"+empCode);

			baseManager.saveObject(emp);
		}
		else{
			baseManager.saveObject(emp);
		}
		

		log
				.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		return new ModelAndView(new RedirectView("commonAdminEmpBasicView.html"));

	}
	
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>");
		EmpBasic emp = (EmpBasic)command;
		String empCode=request.getParameter("empCodeHidden");
		
		Settings settings = (Settings)baseManager.getObject(Settings.class, new Long(1));
		log.debug("on bind emp code " + emp.getEmpCode());
		
		List empbasics = baseManager.getObjects(EmpBasic.class);
		log.debug("settings.getMaxEmp() " + settings.getMaxEmp());
		log.debug("empbasics.size() " + empbasics.size());
		if(errors.getErrorCount()==0)
		{
			if(emp.getEmpCode()==null || emp.getEmpCode().equals(""))
			{
				log.debug("1");
				errors.reject("commons.errors.requiredFields");
			} else {
				log.debug("2");
				if (!(emp.getEmpCode().matches("[0-9]+") && emp.getEmpCode().length() == 8)) {
					log.debug("3");
					errors.reject("commons.errors.empCodeShouldBe8Digits");
				}
				if(empCode==null || empCode.equals("")) {
					List emps = baseManager.getObjectsByParameter(EmpBasic.class,"empCode", emp.getEmpCode());
					if (emps!=null && emps.size()>0) {
						errors.reject("commons.error.duplicateCode");
					}
				}
			}
			if(emp.getEmpName()==null || emp.getEmpName().equals(""))
			{
				errors.reject("commons.errors.requiredFields");
			}
			if(emp.getE_emp_name()==null || emp.getE_emp_name().equals(""))
			{
				errors.reject("commons.errors.requiredFields");
			}
			if (settings.getMaxEmp()<=empbasics.size()) {
				errors.reject("commons.errors.maxEmpReached");
			}
		}
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> end of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		EmpBasic emp = new EmpBasic();

		String empCode=request.getParameter("empCodeHidden");

		if (empCode == null || empCode.equals("")) {
			emp = new EmpBasic();
		} else {
			emp = (EmpBasic) baseManager.getObject(EmpBasic.class, empCode);
		}

		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return emp;
	}
	
	
	
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws ServletException {
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Map model = new HashMap();

		String empCode = request
				.getParameter("empCodeHidden");
		model.put("eCode",empCode);

	
		return model;

	}
	
	
}

