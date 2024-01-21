/**
 * 
 */
package com._4s_.common.web.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com._4s_.common.service.BaseManager;
import com._4s_.common.web.binders.BaseBinder;

/**
 * @author mragab
 *
 */
@Controller
public class BaseSimpleFormController {

	public static final String ROLLBACK_ON_ERROR = "ROLLBACK_ON_ERROR";
	public static final String CONTINUE_WITH_ERROR = "CONTINUE_WITH_ERROR";
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * Base Manager
	 */
	@Autowired
	protected BaseManager baseManager = null;
	
	
	public BaseManager getBaseManager() {
		return baseManager;
	}
	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}

	
	/**
	 * Rolback Mode 
	 */
	private String rollbackMode = ROLLBACK_ON_ERROR ;
	public String getRollbackMode() {
		return rollbackMode;
	}
	public void setRollbackMode(String rollbackMode) {
		this.rollbackMode = rollbackMode;
	}
	
	/**
	 * Binders
	 */
	private List binders = new ArrayList();
	public List getBinders() {
		return binders;
	}
	public void setBinders(List binders) {
		this.binders = binders;
	}
	
	/**
	 * processFormSubmission: rollback in case of any binding or validation errors
	 */
//	protected ModelAndView processFormSubmission(
//			HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
//			throws Exception {
//		log.debug(">>> Start:BaseSimpleFormController.processFormSubmission");
//
//		if(rollbackMode.equals(ROLLBACK_ON_ERROR)){
//			if (errors.hasErrors() || isFormChangeRequest(request)) {
//				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//				log.debug("Binding or validation errors found: Transaction will rollback" + errors.getAllErrors());
//				
//			}
//		}
//		log.debug("End:BaseSimpleFormController.processFormSubmission <<<");
//		log.debug("Super:SimpleFormController.processFormSubmission");
//		return super.processFormSubmission(request, response, command, errors);
//	}

	
//	 @RequestMapping(method = RequestMethod.POST)
//	    public String processSubmit(
//	        @ModelAttribute("command") Object command,
//	        BindingResult result, SessionStatus status) {
//	        
//	        if(rollbackMode.equals(ROLLBACK_ON_ERROR)){
//	        	if (result.hasErrors()) {
//	        		//if validator failed
//	        		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//					log.debug("Binding or validation errors found: Transaction will rollback" + result.getAllErrors());
//					return "error";
//	        	} else {
//	        		status.setComplete();
//	        		//form success
//	        		return "";
//	        	}
//	        } else {
//	        	status.setComplete();
//        		//form success
//        		return "";
//	        }
//	    }
	/**
	 * initBinder: Bind custom editors for Long, Integer, and provided list of custom editors
	 */
//	public void initBinder(HttpServletRequest request,
//			ServletRequestDataBinder binder) {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		log.debug(">>> Start:BaseSimpleFormController.initBinder");

		log.debug("Binding custom editor for class "+Long.class.getName());
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, true));

		log.debug("Binding custom editor for class "+Integer.class.getName());
		binder.registerCustomEditor(Integer.class, null,new CustomNumberEditor(Integer.class, true));
		binder.registerCustomEditor(Double.class, null,new CustomNumberEditor(Double.class, true));
		binder.registerCustomEditor(Float.class, null,new CustomNumberEditor(Float.class, true));
		binder.registerCustomEditor(BigDecimal.class, null,new CustomNumberEditor(BigDecimal.class, true));
		Iterator itr = binders.iterator();
		BaseBinder baseBinder = null;
		log.debug("Binding list of custom editors");
		while (itr.hasNext()) {
			baseBinder = (BaseBinder) itr.next();
			log.debug("Binding custom editor for class "+baseBinder.getBindedClass().getName());

			binder.registerCustomEditor(baseBinder.getBindedClass(), null, baseBinder);
			log.debug("... Binded");
		}
		log.debug("Finished binding list of custom editors");

		log.debug("End:BaseSimpleFormController.initBinder <<<");
	}

	
	
	public void initBinder(HttpServletRequest request,WebDataBinder binder) {
		log.debug(">>> Start:BaseSimpleFormController.initBinder");

		log.debug("Binding custom editor for class "+Long.class.getName());
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, true));

		log.debug("Binding custom editor for class "+Integer.class.getName());
		binder.registerCustomEditor(Integer.class, null,new CustomNumberEditor(Integer.class, true));
		binder.registerCustomEditor(Double.class, null,new CustomNumberEditor(Double.class, true));
		binder.registerCustomEditor(Float.class, null,new CustomNumberEditor(Float.class, true));
		binder.registerCustomEditor(BigDecimal.class, null,new CustomNumberEditor(BigDecimal.class, true));
		Iterator itr = binders.iterator();
		BaseBinder baseBinder = null;
		log.debug("Binding list of custom editors");
		while (itr.hasNext()) {
			baseBinder = (BaseBinder) itr.next();
			log.debug("Binding custom editor for class "+baseBinder.getBindedClass().getName());

			binder.registerCustomEditor(baseBinder.getBindedClass(), null, baseBinder);
			log.debug("... Binded");
		}
		log.debug("Finished binding list of custom editors");

		log.debug("End:BaseSimpleFormController.initBinder <<<");
	}
}
