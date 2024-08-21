package com._4s_.common.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.EmpBasic;
import com._4s_.common.model.Settings;
import com._4s_.common.service.BaseManager;
@Controller
@RequestMapping("/commonAdminEmpBasic.html")
public class EmpBasicController extends BaseSimpleFormController {

	@Autowired
	protected BaseManager baseManager = null;

	public BaseManager getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}

//	public ModelAndView onSubmit(HttpServletRequest request,
//			HttpServletResponse response, Object command, BindException errors)
//			throws Exception {
//		
		@RequestMapping(method = RequestMethod.POST)
		public ModelAndView processSubmit(HttpServletRequest request,
				@Valid @ModelAttribute("empBasic") EmpBasic command,
				BindingResult result, SessionStatus status,Model model) {

		log.debug("error count " + result.getErrorCount());
		if (log.isDebugEnabled()) {
			log.debug("entering 'onSubmit' method....");
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		EmpBasic emp = (EmpBasic)command;
		log.debug("emp basic id " + emp.getId());
		String empCode=request.getParameter("empCodeHidden");
		log.debug("emp code " + empCode);
		
		log.debug("employee arabic name " + emp.getEmpName());
//		if(empCode==null || empCode.equals(""))
//		{
//			log
//			.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>will save new emp basic with empCode: >>>>>>>>>>>>>>>>>>>>>>>>>>>"+empCode);
//
//			baseManager.saveObject(emp);
//		}
//		else{
//			log
//			.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>will save modified emp basic with empCode: >>>>>>>>>>>>>>>>>>>>>>>>>>>"+empCode);
//			baseManager.saveObject(emp);
//		}
		
		try {
			baseManager.saveObject(emp);
			return new ModelAndView(new RedirectView("commonAdminEmpBasicView.html"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("##########################Validation Error##############");
			e.printStackTrace();
			return new ModelAndView(new RedirectView("commonAdminEmpBasic.html?empCodeHidden="+empCode));
		}
//		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		

	}
	
		
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindingResult errors) throws Exception
	{
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>");
		EmpBasic emp = (EmpBasic)command;
		String empCode=request.getParameter("empCodeHidden");
		
		Settings settings = (Settings)baseManager.getObject(Settings.class, new Long(1));
		log.debug("on bind emp code " + emp.getEmpCode());
		
		List empbasics = baseManager.getObjects(EmpBasic.class);
		log.debug("settings.getMaxEmp() " + settings.getMaxEmp());
		log.debug("empbasics.size() " + empbasics.size());
		log.debug("errors.getErrorCount() " + errors.getErrorCount());
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
		
		/////////////////////
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> end of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>");
	}

//	protected Object formBackingObject(HttpServletRequest request)
//			throws ServletException {
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request){
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		EmpBasic emp = new EmpBasic();

		String empCode=request.getParameter("empCodeHidden");
		log.debug("empCodeHidden " +empCode);
		if (empCode == null || empCode.equals("")) {
			emp = new EmpBasic();
		} else {
			emp = (EmpBasic) baseManager.getObject(EmpBasic.class, empCode);
		}

		log.debug("emp " + emp);
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute(emp);
		return "commonAdminEmpBasic";
	}
	
	
	
//	protected Map referenceData(HttpServletRequest request, Object command,
//			Errors errors) throws ServletException {
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request,@ModelAttribute("empBasic") EmpBasic command)
	{
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Map model = new HashMap();

		String empCode = request
				.getParameter("empCodeHidden");
		model.put("eCode",empCode);

	
		return model;

	}
	
	
}

