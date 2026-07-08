package com._4s_.restServices.entrypoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import com._4s_.restServices.json.RestStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Component
public final class RestAuthenticationEntryPoint 
extends BasicAuthenticationEntryPoint {

	@Autowired
	private MappingJackson2HttpMessageConverter jsonConverter;


	public MappingJackson2HttpMessageConverter getJsonConverter() {
		return jsonConverter;
	}

	public void setJsonConverter(MappingJackson2HttpMessageConverter jsonConverter) {
		this.jsonConverter = jsonConverter;
	}

	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
			throws IOException {
		//		System.out.println("commence");
		//        response.addHeader("WWW-Authenticate", "Basic realm=" +getRealmName());
		//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		//        PrintWriter writer = response.getWriter();
		//        writer.println("HTTP Status 401 - " + authEx.getMessage());

		//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "HTTP Status 401 - " + authEx.getMessage());

		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = jsonConverter.getObjectMapper();
		RestStatus status = new RestStatus();
		status.setStatus("false");
		status.setCode("401");
		status.setMessage("Unauthorized Access");
		
		Map map = new HashMap();
		map.put("Status", status);
		
		mapper.writeValue(writer, map);
		System.out.println("object mapper writting");
		writer.flush();
	}

	@Override
	public void afterPropertiesSet() {
		//		System.out.println("After properties set");
		setRealmName("DeveloperStack");
		try {
			super.afterPropertiesSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}