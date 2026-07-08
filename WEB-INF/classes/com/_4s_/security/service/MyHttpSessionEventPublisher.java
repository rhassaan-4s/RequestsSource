package com._4s_.security.service;

import javax.servlet.http.HttpSessionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.HttpSessionEventPublisher;

public class MyHttpSessionEventPublisher extends HttpSessionEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(MyHttpSessionEventPublisher.class);

    public void sessionDestroyed(HttpSessionEvent event) {
        log.debug("*************unpublishing session******************");

        super.sessionDestroyed(event);

        log.debug("*************super.sessionDestroyed(event);******************");
    }

}