package com._4s_.security.service;

import javax.servlet.http.HttpSessionEvent;

import org.acegisecurity.ui.session.HttpSessionEventPublisher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyHttpSessionEventPublisher extends HttpSessionEventPublisher {
    private static final Log log = LogFactory.getLog(MyHttpSessionEventPublisher.class);

    public void sessionDestroyed(HttpSessionEvent event) {
        log.debug("*************unpublishing session******************");

        super.sessionDestroyed(event);

        log.debug("*************super.sessionDestroyed(event);******************");
    }

}