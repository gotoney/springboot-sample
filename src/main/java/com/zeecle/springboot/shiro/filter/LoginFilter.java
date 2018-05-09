package com.zeecle.springboot.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginFilter extends AccessControlFilter {
	private static final Logger LOG = LoggerFactory.getLogger(LoginFilter.class);

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		LOG.info("LoginFilter.isAccessAllowed():" + session.getId());
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		LOG.info("LoginFilter.onAccessDenied()");
		saveRequestAndRedirectToLogin(request, response);
		return false;
	}

}
