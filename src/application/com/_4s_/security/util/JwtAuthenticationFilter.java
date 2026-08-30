package com._4s_.security.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com._4s_.clients.service.CustomUserDetailsService;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private Log log = LogFactory.getLog(JwtAuthenticationFilter.class);
    private JwtUtil jwtUtil;
    private CustomUserDetailsService userDetailsService;

    public JwtUtil getJwtUtil() {
        return jwtUtil;
    }

    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    
    

    public CustomUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        log.debug("========== JWT AUTHENTICATION FILTER ==========");
        log.debug("URI = " + request.getRequestURI());
        log.debug("Authorization = "
                + (authorizationHeader != null ? "Bearer token present" : "null"));

        try {

            if (authorizationHeader != null
                    && authorizationHeader.startsWith("Bearer ")) {

                String token = authorizationHeader.substring(7);

                try {

                    String username = jwtUtil.extractUsername(token);
//                    List<String> roles = jwtUtil.extractRoles(token);
                    
                   
	    

                    log.debug("*************JWT username = " + username);
//                    log.debug("JWT roles = " + roles);

                    log.debug("JWT jwtUtil "+jwtUtil);
                    log.debug("JWT userDetailsService " + userDetailsService);
                    if (username != null
                            && !username.trim().isEmpty()
                            && !jwtUtil.isTokenExpired(token)) {
                    	log.debug("JWT token is valid, setting authentication in SecurityContext");
                    	 UserDetails userDetails =
                                 userDetailsService.loadUserByUsername(username);
                    	 log.debug("JWT userDetails = " + userDetails);
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        username,
                                        null,
                                        userDetails.getAuthorities());

                        SecurityContextHolder
                                .getContext()
                                .setAuthentication(authentication);

                        log.debug(
                                "JWT authentication successfully set in SecurityContext");

                    } else {

                        log.debug(
                                "JWT authentication failed: username is null/empty or token expired");
                    }

                } catch (Exception e) {

                    log.debug(
                            "JWT validation failed: "
                            + e.getClass().getSimpleName()
                            + " - "
                            + e.getMessage());

                    SecurityContextHolder.clearContext();
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {

            SecurityContextHolder.clearContext();

            throw e;

        } finally {

            log.debug(
                    "JWT AUTH FILTER END - Authentication = "
                    + SecurityContextHolder.getContext().getAuthentication());
        }
    }
}