package com._4s_.dbUpdate.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.web.action.BaseSimpleFormController;
import com._4s_.dbUpdate.service.SQLManager;

public class AddNewQueryForm extends BaseSimpleFormController {

	SQLManager mgr = null;

	public SQLManager getMgr() {
		return mgr;
	}

	public void setMgr(SQLManager mgr) {
		this.mgr = mgr;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("entering 'onSubmit' method....");
		}
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

		String Query = request.getParameter("Query");
		ServletContext servletContext = request.getSession().getServletContext();
		String contextPath = request.getSession().getServletContext().getRealPath("/");
		//String contextPath= WebUtils.getRealPath(servletContext,"/");
		log.debug(">>>>>>>>>>>>>>>>>>>>contextPath "+contextPath);
		
		String fileName = contextPath+"/dbUpdates/ERPDBUpdate.xml";

		Object object = (Object) command;

		SAXBuilder builder = new SAXBuilder();

		Document doc = builder.build(new File(fileName));
		Element webapp = doc.getRootElement();

		int x = 0;

		List lst = webapp.getChildren();

		Iterator itr = lst.iterator();
		String QueryIndex = "";

		while (itr.hasNext()) {
			Element elm = (Element) itr.next();
			QueryIndex = elm.getChild("sqlindex").getText();
			x = Integer.parseInt(QueryIndex);
		}

		log
				.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< X IS: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
						+ x);

		log
				.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< QueryIndex IS: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
						+ QueryIndex);
		log
				.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< Query IS: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
						+ Query);

		Element parent = new Element("query");
		Element child1 = new Element("sqlindex").setText(String.valueOf(x+1));
		Element child2 = new Element("statment").setText(Query);

		parent.addContent(child1);
		parent.addContent(child2);

		webapp.addContent(parent);

		XMLOutputter oOut = new XMLOutputter();
		oOut.output(doc, new FileOutputStream(
				new File(fileName)));

		log
				.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		return new ModelAndView(new RedirectView("newQueryForm.html"));

	}

	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Object object = new Object();
		log
				.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return object;
	}
}