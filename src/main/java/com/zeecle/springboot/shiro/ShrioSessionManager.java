package com.zeecle.springboot.shiro;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShrioSessionManager extends DefaultWebSessionManager {
	private static final Logger LOG = LoggerFactory.getLogger(ShrioSessionManager.class);

	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		HttpServletRequest sRequest = (HttpServletRequest) request;
		LOG.info("requestURI:" + sRequest.getRequestURI());
		return super.getSessionId(request, response);
	}	

}
