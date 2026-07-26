package com._4s_.security.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.Clients;
import com._4s_.common.service.CommonManager;

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
	public String initForm(ModelMap model, HttpServletRequest request) {

	    log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject >>>>>>>>>>>>>>>>>>>>>>>>>>>");

	    HttpSession session = request.getSession(false);

	    if (session != null) {
	        String tenantID = (String) session.getAttribute("tenantID");

	        if (tenantID != null && !tenantID.trim().isEmpty()) {

	            // Optional: validate that the tenant still exists
	            Clients tenant = (Clients) commonManager.getObjectByParameter(
	                    Clients.class, "schema", tenantID);

	            if (tenant != null) {
	                log.debug("Tenant found in session. Redirecting to login page.");
	                return "redirect:/security/login.html";
	            }

	            // Invalid tenant in session
	            session.removeAttribute("tenantID");
	            session.removeAttribute("client");
	        }
	    }

	    return "clients";
	}
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(HttpServletRequest request,
			HttpServletResponse response,
			SessionStatus sessStatus,Map model) throws Exception {
		String client = request.getParameter("client");
		System.out.println("****Clients Form*******client SCHEMA " + client);
		if (client!=null && !client.isEmpty()) {
			Clients c = (Clients)commonManager.getObjectByParameter(Clients.class, "id", client);
			System.out.println("****Clients Form*******client " + c.getClientName());
			if (c!=null) {
				
//				model.put("tenantID", c.getSchema());
//				model.put("client", c.getClientName());
				request.getSession().setAttribute("tenantID", c.getSchema());
				request.getSession().setAttribute("client", c.getClientName());

				/////////////////////////////////////////////////////////////////////////////////////////////////
				////////////////////Create a cookie for tenantID and client and set it in the response///////////
				/// /////////////////////////////////////////////////////////////////////////////////////////////
				Cookie tenantCookie = new Cookie("tenantID", c.getSchema());
				tenantCookie.setHttpOnly(true);
				tenantCookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
				tenantCookie.setMaxAge(60 * 60 * 24 * 365); // 1 year
				response.addCookie(tenantCookie);

				Cookie clientCookie = new Cookie("client", c.getClientName());
				clientCookie.setHttpOnly(true);
				clientCookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
				clientCookie.setMaxAge(60 * 60 * 24 * 365); // 1 year
				response.addCookie(clientCookie);
				////////////////////////////////////////////////////////////////////////////////////////////////
				
				ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
				attr.getRequest().getSession().setAttribute("tenantID",  c.getSchema());
				attr.getRequest().getSession().setAttribute("client", c.getClientName());
				System.out.println("****Clients Form*******setting tenantID in session to " + c.getSchema());
				String url="login.html";
				System.out.println("****Clients Form*******redirecting to " + url);
//				return new ModelAndView("login",model);
				return new ModelAndView("redirect:/security/login.html");
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
