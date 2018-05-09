package com.zeecle.springboot.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * HandlerInterceptor
 * 	实现对请求级别的拦截
 * @author toney
 * @date 2018-04-20
 */
@Component
public class RequestHandler extends HandlerInterceptorAdapter {
	
	private static final Logger LOG = LoggerFactory.getLogger(RequestHandler.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LOG.info("RequestHandler.preHandle():" + request.getRequestURI());
		return true;
	}

}
