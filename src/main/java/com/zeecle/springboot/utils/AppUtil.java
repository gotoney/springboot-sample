package com.zeecle.springboot.utils;

import org.apache.shiro.SecurityUtils;

public class AppUtil {
	
	private static final String SESSION_USERNAME = "username";
	
	public static String getCurrentUserName() {
		return SecurityUtils.getSubject().getSession().getAttribute(SESSION_USERNAME).toString();
	}

}
