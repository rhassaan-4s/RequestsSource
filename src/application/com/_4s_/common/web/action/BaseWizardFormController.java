package com._4s_.common.web.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com._4s_.common.service.BaseManager;
import com._4s_.common.web.binders.BaseBinder;

@Controller
@SessionAttributes("command")
public class BaseWizardFormController {
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
	
	
	@InitBinder
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
	
//	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
//		log.debug(">>> Start:BaseSimpleFormController.processFormSubmission");
//
//		if(rollbackMode.equals(ROLLBACK_ON_ERROR)){
//			if (errors.hasErrors()) {
//				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//			}
//			log.info("Binding or validation errors found: Transaction will rollback");
//		}
//		log.debug("End:BaseSimpleFormController.processFormSubmission <<<");
//		log.debug("Super:SimpleFormController.processFormSubmission");
//		
//		return super.processFormSubmission(request,response,command,errors);
//	}
	
	 /**
     * The default handler (page=0)
     */
    @RequestMapping
    public String getInitialPage(final ModelMap modelMap) {
        // put your initial command
        modelMap.addAttribute("command", new Object());
        // populate the model Map as needed
        return "initialView";
    }

    /**
     * First step handler (if you want to map each step individually to a method). You should probably either use this
     * approach or the one below (mapping all pages to the same method and getting the page number as parameter).
     */
    @RequestMapping(params = "_step=1")
    public String processFirstStep(final @ModelAttribute("command") Object command,
                                   final Errors errors) {
        // do something with command, errors, request, response,
        // model map or whatever you include among the method
        // parameters. See the documentation for @RequestMapping
        // to get the full picture.
        return "firstStepView";
    }

    /**
     * Maybe you want to be provided with the _page parameter (in order to map the same method for all), as you have in
     * AbstractWizardFormController.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String processPage(@RequestParam("_page") final int currentPage,
                              final @ModelAttribute("command") Object command,
                              final HttpServletResponse response) {
        // do something based on page number
        return "pageViews[currentPage]";
    }

    /**
     * The successful finish step ('_finish' request param must be present)
     */
    @RequestMapping(params = "_finish")
    public String processFinish(final @ModelAttribute("command") Object command,
                                final Errors errors,
                                final ModelMap modelMap,
                                final SessionStatus status) {
        // some stuff
        status.setComplete();
        return "successView";
    }

    @RequestMapping(params = "_cancel")
    public String processCancel(final HttpServletRequest request,
                                final HttpServletResponse response,
                                final SessionStatus status) {
        status.setComplete();
        return "canceledView";
    }
	
}
