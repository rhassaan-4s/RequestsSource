package com._4s_.security.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Clients;
import com._4s_.common.service.CommonManager;
import com._4s_.requestsApproval.model.LoginUsersRequests;

@Controller
@RequestMapping(value = "/clients.html")
public class ClientsView {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private CommonManager commonManager;

	public CommonManager getCommonManager() {
		return commonManager;
	}

	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}
	
	@RequestMapping(method = RequestMethod.GET)  
	public String initForm(ModelMap model,HttpServletRequest request){
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		return "clients";
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(HttpServletRequest request,
			SessionStatus sessStatus,Map model) throws Exception {
		String client = request.getParameter("client");
		System.out.println("client SCHEMA " + client);
		if (client!=null && !client.isEmpty()) {
			Clients c = (Clients)commonManager.getObjectByParameter(Clients.class, "id", client);
			System.out.println("client " + c.getClientName());
			if (c!=null) {
				model.put("tenantID", c.getSchema());
				model.put("client", c.getClientName());
				ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
				attr.getRequest().getSession().setAttribute("tenantID",  c.getSchema());
				attr.getRequest().getSession().setAttribute("client", c.getClientName());
				
				String url="login.html";
				return new ModelAndView("login",model);
			} else {
				return new ModelAndView("clients",model);	
			}
		} else {
			return new ModelAndView("clients",model);	
		}
	}

	@ModelAttribute("model")	
	public Map populateWebFrameworkList(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request) 
	{
		Map model=new HashMap();	
		List clients = commonManager.getObjectsOrderedByField(Clients.class,"id");
		ModelAndView view = new ModelAndView();
		model.put("clients",clients);
		return model;

	}

}
