package com.apimq.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class ResponseInterceptor implements HandlerInterceptor{
	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseInterceptor.class);
	
	
	@Override
	public void postHandle(
	  HttpServletRequest request, 
	  HttpServletResponse response,
	  Object handler, 
	  ModelAndView modelAndView) throws Exception {
		if(response.getContentType() == null) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			LOGGER.debug("Changed response status");
		}
		
	}
	
	
}
