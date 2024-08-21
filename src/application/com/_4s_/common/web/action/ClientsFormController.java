package com._4s_.common.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Clients;
import com._4s_.common.model.EmpBasic;
import com._4s_.common.service.BaseManager;

@Controller
@RequestMapping("/commonAdminClientsForm.html")
public class ClientsFormController extends BaseSimpleFormController {

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
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(HttpServletRequest request,
			@Valid @ModelAttribute("client") Clients command,
			BindingResult result, SessionStatus status,Model model) {

		if (log.isDebugEnabled()) {
			log.debug("entering 'onSubmit' method....");
		}
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Clients client = (Clients) command;

		baseManager.saveObject(client);
		log
				.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		return new ModelAndView(new RedirectView("commonAdminClientsView.html"));

	}

//	protected Object formBackingObject(HttpServletRequest request)
//			throws ServletException {
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model,HttpServletRequest request){
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String clientId = request.getParameter("clientId");
		Clients client = new Clients();
		if(clientId!=null && !clientId.equals(""))
		{
			log
			.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> clientsId: >>>>>>>>>>>>>>>>>>>>>>>>>>>"+clientId);

			client = (Clients) baseManager.getObject(Clients.class,
					new Long(clientId));
			log
			.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> before setting: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute("client", client);
//		return client;
		return "commonAdminClientsForm";
	}
	
//	protected Map referenceData(HttpServletRequest request, Object command,
//			Errors errors) throws ServletException {
	@ModelAttribute("model")
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request,@ModelAttribute("client") Clients command)
	{
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Map model = new HashMap();

		String clientId = request
				.getParameter("clientId");
		model.put("clientId",clientId);

	
		return model;

	}
}

