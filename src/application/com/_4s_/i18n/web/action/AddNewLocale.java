package com._4s_.i18n.web.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.Country;
import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.i18n.model.MyLocale;
import com._4s_.i18n.model.MyMessage;
import com._4s_.i18n.service.MessageManager;

public class AddNewLocale extends BaseSimpleFormController {
	private MessageManager mgr = null;

	public MessageManager getMgr() {
		return mgr;
	}

	public void setMgr(MessageManager mgr) {
		this.mgr = mgr;
	}

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException error)
			throws Exception {
		log.debug(">>>>>>>>>>>>>>>.start onSubmit()>>>>>>>>>>>");
		// TODO Auto-generated method stub
		String clonedLocale = request.getParameter("clonedLocale");
		//String countryList = request.getParameter("countryList");
		log.debug(">>>>>>>>>>>>>>.clonedLocale "+clonedLocale);
		MyLocale myLocale = (MyLocale) command;
		String localeId = request.getParameter("localeId");	
		log.debug(">>>>>>>>>>>>>>...localeId "+localeId);
//		if(localeId!=null && localeId.length()>0)
//		{
//			//baseManager.saveObject(myLocale);
//					
//		}
//		else{
			myLocale.setVariant("");
			baseManager.saveObject(myLocale);
			//myLocale.setCountry(countryList);
//		}
		if (clonedLocale != null && !clonedLocale.equals("")) 
		{
			MyLocale cLocale = (MyLocale) baseManager.getObjectByParameter(MyLocale.class, "id", new Long(clonedLocale));
			//get all msgs of that locale
			 Iterator itr = mgr.getMessagesByLocale(cLocale).iterator();
			 log.debug(">>>>>>>>>>>>>>>>>>>>>> mgr.getMessagesByLocale(cLocale) "
			 + mgr.getMessagesByLocale(cLocale));
			 // save a copy of all msgs with the new local
			 while (itr.hasNext()) 
			 {
				 log.debug(">>>>>>>>>>>>>>>>>1");
				 MyMessage msg = (MyMessage) itr.next();
				 MyMessage m = new MyMessage();
				 m.setMessage(msg.getMessage());
				 m.setMyLocale(myLocale);
				 m.setKey(msg.getKey());
				 baseManager.saveObject(m);
			 }
		}
		 response.setContentType("text/html");
		 PrintWriter out = response.getWriter();
		 out.println("<script>window.opener.location.reload();window.close();</script>");
		 out.close();
		 log.debug(">>>>>>>>>>>>>>>.end onSubmit()>>>>>>>>>>>");
		return new ModelAndView();
	}

	protected Map referenceData(HttpServletRequest request, Object command,
			Errors error) throws Exception {
		// TODO Auto-generated method stub
		Map model = new HashMap();
		List countries = baseManager.getObjects(Country.class);
		List locales = baseManager.getObjects(MyLocale.class);
		model.put("locales", locales);
		model.put("countries",countries);
		return model;
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>.start formBackingObject()>>>>>>>>>>>");
		MyLocale myLocale;
		String localeId = request.getParameter("localeId");
		log.debug(">>>>>>>>>>> localeId :" + localeId);

		if (localeId != null && !localeId.equals("")) {
			{
				myLocale = (MyLocale) baseManager.getObject(MyLocale.class,new Long(localeId));
			}

		} else {
			log.debug(">>>>>>>>>>>>>>............localeId " + localeId);
			myLocale = new MyLocale();
			
		}
		log.debug(">>>>>>>>>>>>>>>.end formBackingObject()>>>>>>>>>>>");
		return myLocale;
	}

}
