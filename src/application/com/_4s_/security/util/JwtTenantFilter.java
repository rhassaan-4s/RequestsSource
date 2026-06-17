package com._4s_.security.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//import com._4s_.common.dao.CurrentTenantIdentifierResolverImpl;
import com._4s_.common.dao.TenantContext;
import com._4s_.restServices.service.RequestsServiceImpl;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtTenantFilter extends OncePerRequestFilter {
	Log log = LogFactory.getLog(JwtTenantFilter.class);
//    @Value("${jwt.secret}")
	@Autowired
	private JwtUtil jwtUtil;
    private String secretKey;
    @Autowired
    private RequestsServiceImpl requestsService;
    
    @Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
    

    public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	
	public JwtUtil getJwtUtil() {
		return jwtUtil;
	}

	public void setJwtUtil(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
		this.secretKey = jwtUtil.getSecretKey();
		System.out.println("JwtTenantFilter initialized with secretKey: " + (secretKey != null ? jwtUtil.getSecretKey() : "null"));
	}

	
	
	public RequestsServiceImpl getRequestsService() {
		return requestsService;
	}

	public void setRequestsService(RequestsServiceImpl requestsService) {
		this.requestsService = requestsService;
	}
	
	private String resolveTenant(HttpServletRequest request, String token) {

	    // 1) WEB: from session (after /clients.html)
	    try {
	        if (request.getSession(false) != null &&
	            request.getSession(false).getAttribute("tenantID") != null) {

	            String t = request.getSession(false).getAttribute("tenantID").toString();
	            log.warn(">>> Tenant from SESSION = " + t);
	            return t;
	        }
	    } catch (Exception e) {
	        log.error("Error reading tenant from session", e);
	    }

	    // 2) MOBILE: from JWT
	    if (token != null) {
	        try {
	            String t = jwtUtil.extractTenantId(token);
	            log.warn(">>> Tenant from JWT = " + t);
	            return t;
	        } catch (Exception e) {
	            log.error("Error extracting tenant from JWT", e);
	        }
	    }

	    return null;
	}
	
	@Override
	protected void doFilterInternal(
	        HttpServletRequest request,
	        HttpServletResponse response,
	        FilterChain filterChain)
	        throws ServletException, IOException {

	    String uri = request.getRequestURI();

	    // skip static
	    if (uri.contains("/css/")
	            || uri.contains("/js/")
	            || uri.contains("/images/")
	            || uri.contains(".css")
	            || uri.contains(".js")
	            || uri.contains(".png")
	            || uri.contains(".jpg")
	            || uri.contains(".gif")) {

	        filterChain.doFilter(request, response);
	        return;
	    }

	    try {

	        String tenant = null;

	        log.info("!!!!!!!!!!!!!Resolving tenant for request: " + uri);
	        log.info("!!!!!!!!!!!!!request session " + request.getSession(false));
	        // 1) session tenant (web login)
	        if (request.getSession(false) != null) {
	            Object t =
	                request.getSession(false).getAttribute("tenantID");
	            log.info("!!!!!!!!!!!!!tenant id: " + t);

	            if (t != null) {
	                tenant = t.toString();
	                log.info("!!!!!!!!!!!!!tenant id: " + tenant);
	            }
	        }

	        // 2) header tenant
	        if (tenant == null) {
	        	log.info("!!!!!!!!!!!!!Checking X-Tenant header...");
	            tenant = request.getHeader("X-Tenant");
	        }

//	        // 3) request param
//	        if (tenant == null) {
//	        	log.info("!!!!!!!!!!!!!Checking tenant request parameter...");
//	            tenant = request.getParameter("tenant");
//	        }

	        // 4) JWT tenant
	        String token = extractToken(request);
	        log.info("!!!!!!!!!!!!!Extracted token: " + (token != null ? "present" : "null") + " - " + token);
	        log.info("!!!!!!!!!!!!!tenant value: " + tenant);
	        if (tenant == null && token != null) {
	        	log.info("!!!!!!!!!!!!!Extracting tenant from JWT...");
	            try {
	                tenant = jwtUtil.extractTenantId(token);
	            } catch (Exception ignored) {}
	        }
	        log.info("!!!!!!!!!!!!!Resolved tenant: " + (tenant != null ? tenant : "null"));

	        // SET TENANT
	        if (tenant != null) {
	            TenantContext.setTenant(tenant);
	            MDC.put("tenantId", tenant); 
	        }

	        filterChain.doFilter(request, response);

	    } finally {
	        TenantContext.clear();
	        MDC.remove("tenantId"); 
	    }
	}

    private String extractToken(HttpServletRequest request) {
        // Check Authorization header
    	log.info("extractToken: FILTER ACTIVE: " + this.getClass().getName());
    	System.out.println("*********Filter: Extracting token from request...");
        String authHeader = request.getHeader("Authorization");
        System.out.println("Filter: Authorization header: " + (authHeader != null ? authHeader : "null"));
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        // Fallback to cookies (optional)
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
