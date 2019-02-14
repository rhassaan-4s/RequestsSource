package com._4s_.common.web.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import com._4s_.common.service.BaseManager;
import com._4s_.common.web.binders.BaseBinder;

public class BaseWizardFormController extends AbstractWizardFormController {
	public static final String ROLLBACK_ON_ERROR = "ROLLBACK_ON_ERROR";
	public static final String CONTINUE_WITH_ERROR = "CONTINUE_WITH_ERROR";
	protected final Log log = LogFactory.getLog(getClass());
	
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
	
	
	
	public void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		log.debug(">>> Start:BaseSimpleFormController.initBinder");

		log.debug("Binding custom editor for class "+Long.class.getName());
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, true));

		log.debug("Binding custom editor for class "+Integer.class.getName());
		binder.registerCustomEditor(Integer.class, null,new CustomNumberEditor(Integer.class, true));

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
	
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		log.debug(">>> Start:BaseSimpleFormController.processFormSubmission");

		if(rollbackMode.equals(ROLLBACK_ON_ERROR)){
			if (errors.hasErrors()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			log.info("Binding or validation errors found: Transaction will rollback");
		}
		log.debug("End:BaseSimpleFormController.processFormSubmission <<<");
		log.debug("Super:SimpleFormController.processFormSubmission");
		
		return super.processFormSubmission(request,response,command,errors);
	}
	
}
