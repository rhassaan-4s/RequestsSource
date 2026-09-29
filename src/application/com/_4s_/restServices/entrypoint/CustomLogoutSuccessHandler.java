package com._4s_.restServices.entrypoint;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com._4s_.common.dao.TenantContext;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
    	System.out.println("#######*******Logout successful for user: " + (authentication != null ? authentication.getName() : "unknown"));
    	TenantContext.clear();
    	if (request.getSession(false) != null) {
    	    request.getSession(false).invalidate();
    	}
    	deleteCookie(response, "tenantID");
    	deleteCookie(response, "client");
    	deleteCookie(response, "token");
    	deleteCookie(response, "JSESSIONID");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().flush();
    }
    private void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/Requests");
        response.addCookie(cookie);
    }
}