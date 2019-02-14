
/*

Copyright (c) 2005 Joseph Yi

MIT License

Permission is hereby granted, free of charge, to any person obtaining a
copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

*/

package com._4s_.common.util;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.Element;

import uk.ltd.getahead.dwr.Creator;
import uk.ltd.getahead.dwr.ExecutionContext;

/**
 * Creator implementation that uses Spring's ApplicationContext
 * to get a bean defined in Spring directly via the ServletContext. This
 * is useful when your web application runs unpacked (i.e. stays in archive.war form),
 * you have multiple config files defined for the ContextLoaderListener in web.xml
 * (i.e. your bean definitions are in separate config files), and
 * when you don't want to use another bean factory instantiation.
 *
 * @author Joseph Yi dissonance@gmail.com
 */
public class SpringWebContextCreator implements Creator {
    private Class clazz;
    private String beanName;
    private static ApplicationContext applicationContext;
    private static final String BEAN_NAME_ATTR = "beanName";
    private final Log log = LogFactory.getLog(getClass());
    /**
     * Gets the bean name from DWR's config file.
     *
     * @param element the 'create' XML element with the 'beanName' attribute
     * @throws IllegalArgumentException if the argument was not allowed
     */
    public void init(Element element) throws IllegalArgumentException {
    	 log.debug(">>>>>>>>>>>>>>>init");
        this.beanName = element.getAttribute(BEAN_NAME_ATTR);
        log.debug(">>>>>>>>>>>>>>>this.beanName "+this.beanName);
    }

    /**
     * Gets the Class for the bean.
     *
     * @return class of the bean.
     */
    public Class getType() {
    	log.debug(">>>>>>>>>>>>>>>getType ");
        if (clazz == null) {
        	log.debug(">>>>>>>>>>>>>>>getType if");
            try {
            	log.debug(">>>>>>>>>>>>>>>getType try");
                clazz = getInstance().getClass();
                log.debug(">>>>>>>>>>>>>>>clazz "+clazz);
            } catch (InstantiationException ex) {
                ex.printStackTrace();
                log.debug(">>>>>>>>>>>>>>>getType catch");
                log.debug(">>>>>>>>>>>>>>>Object.class "+Object.class);
                return Object.class;
            }
        }

        return clazz;
    }

    /**
     * Gets an instance of the bean from the application context.
     *
     * @return an instance of the bean.
     * @throws InstantiationException if bean cannot be instantiated.
     */
    public Object getInstance() throws InstantiationException {
        if(applicationContext == null) {
        	log.debug(">>>>>>>>>.applicationContext == null");
            ServletContext ctx = ExecutionContext.get().getServletContext();
            log.debug(">>>>>>>>>>>>>>>ctx "+ctx);
            applicationContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
            log.debug(">>>>>>>>>>>>>>>applicationContext "+applicationContext);
        }
        log.debug(">>>>>>>>>>>>>>>applicationContext.getBean(beanName) "+applicationContext.getBean(beanName));
        return applicationContext.getBean(beanName);
    }

	public void setProperties(Map arg0) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		 log.debug(">>>>>>>>>>>>>>>setProperties(Map arg0) ");
	}

	public String getScope() {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>getScope() ");
		return new String();
	}

}