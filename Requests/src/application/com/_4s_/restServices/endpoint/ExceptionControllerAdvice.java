package com._4s_.restServices.endpoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com._4s_.restServices.json.RestStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExceptionControllerAdvice  implements AccessDeniedHandler {
	private String accessDeniedUrl;

	@Autowired
	private MappingJackson2HttpMessageConverter jsonConverter;


	public MappingJackson2HttpMessageConverter getJsonConverter() {
		return jsonConverter;
	}

	public void setJsonConverter(MappingJackson2HttpMessageConverter jsonConverter) {
		this.jsonConverter = jsonConverter;
	}

	public ExceptionControllerAdvice() {
	}

	public ExceptionControllerAdvice(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}

	public void handle(HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException,
			ServletException {

		RestStatus status = new RestStatus();
		status.setCode("403");
		status.setMessage("Access Denied");
		status.setStatus("False");
		
		Map map = new HashMap();
		map.put("Status", status);
		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = jsonConverter.getObjectMapper();
		mapper.writeValue(writer, map);
		System.out.println("object mapper writting");
		writer.flush();
	}

	public String getAccessDeniedUrl() {
		return accessDeniedUrl;
	}

	public void setAccessDeniedUrl(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}


	//	
	//    @ExceptionHandler(AccessDeniedException.class)
	//    @ResponseBody
	//    public RestStatus exception(AccessDeniedException e) {
	//    	RestStatus status = new RestStatus();
	//    	status.setCode("403");
	//    	status.setMessage("Access Denied");
	//    	status.setStatus("False");
	//    	
	////    	PrintWriter writer = response.getWriter();
	////		ObjectMapper mapper = jsonConverter.getObjectMapper();
	////		mapper.writeValue(writer, status);
	////		System.out.println("object mapper writting");
	////		writer.flush();
	//        return status;
	//    } 
}