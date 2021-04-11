package com._4s_.restServices.entrypoint;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	private final ObjectMapper mapper;
	
//	@Autowired
	AuthFailureHandler() {
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        this.mapper = messageConverter.getObjectMapper();
        System.out.println("object mapper " + this.mapper);
    }
	
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
    	System.out.println("Failed to authenticate ");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        System.out.println("response status set ");
//        PrintWriter writer = response.getWriter();
////        writer.write(exception.getMessage());
//        writer.flush();
        
       
    }
}
