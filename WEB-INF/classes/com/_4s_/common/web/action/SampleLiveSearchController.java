package com._4s_.common.web.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.Employee;

@Controller
@RequestMapping("/sampleLiveSearch.html")
public class SampleLiveSearchController extends BaseSimpleFormController{


	
	

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(HttpServletRequest request,
			@Valid @ModelAttribute("employee") Employee command,
			BindingResult result, SessionStatus status,Model model) {
		
		String value = request.getParameter("q");
		log.debug(">>>>>>>>>>>>>>>>>>>>>>> value "+value);
		return new ModelAndView("sampleLiveSearchSuccess","value",value);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request){
	
		log.debug("Start formBackingObject >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Employee emp = new Employee();
		model.addAttribute("emp" , emp);
		return "sampleLiveSearch";
	}
}
