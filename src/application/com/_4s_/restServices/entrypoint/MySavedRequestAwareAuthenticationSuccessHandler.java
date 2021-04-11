package com._4s_.restServices.entrypoint;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

public class MySavedRequestAwareAuthenticationSuccessHandler 
extends SimpleUrlAuthenticationSuccessHandler {

  private RequestCache requestCache = new HttpSessionRequestCache();
  
  @Autowired
	private MappingJackson2HttpMessageConverter jsonConverter;


	public MappingJackson2HttpMessageConverter getJsonConverter() {
		return jsonConverter;
	}

	public void setJsonConverter(MappingJackson2HttpMessageConverter jsonConverter) {
		this.jsonConverter = jsonConverter;
	}

  @Override
  public void onAuthenticationSuccess(
    HttpServletRequest request,
    HttpServletResponse response, 
    Authentication authentication) 
    throws ServletException, IOException {

	  
	  /////////////////////////////////////
	  
	  System.out.println("authentication successful!");
      SavedRequest savedRequest
        = requestCache.getRequest(request, response);

      if (savedRequest == null) {
          clearAuthenticationAttributes(request);
          return;
      }
      String targetUrlParam = getTargetUrlParameter();
      if (isAlwaysUseDefaultTargetUrl()
        || (targetUrlParam != null
        && StringUtils.hasText(request.getParameter(targetUrlParam)))) {
          requestCache.removeRequest(request, response);
          clearAuthenticationAttributes(request);
          return;
      }

      clearAuthenticationAttributes(request);
      
      ////////////////////////////////////
      
//      try {
//    	  UserAuthentication authResultObject = (UserAuthentication) authentication;
//    	  tokenAuthenticationService.addAuthentication(response, authResultObject);
//    	  // Add the authentication to the Security context
//    	  SecurityContextHolder.getContext().setAuthentication(authentication);
//      } catch (Exception ex) {
//    	  ex.printStackTrace();
//      }
  }

  public void setRequestCache(RequestCache requestCache) {
      this.requestCache = requestCache;
  }
}
