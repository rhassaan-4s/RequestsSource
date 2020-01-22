package com._4s_.common.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Branch;
import com._4s_.common.model.Clients;
import com._4s_.common.model.Company;
import com._4s_.common.service.BaseManager;

public class ClientsFormController extends BaseSimpleFormController {

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

		Clients client = (Clients) command;

		baseManager.saveObject(client);
		log
				.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		return new ModelAndView(new RedirectView("commonAdminClientsView.html"));

	}

	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {

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
		return client;
	}
	
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws ServletException {
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Map model = new HashMap();

		String clientId = request
				.getParameter("clientId");
		model.put("clientId",clientId);

	
		return model;

	}
}

