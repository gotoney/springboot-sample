package com.zeecle.springboot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理类
 * @author toney
 * @date 2018-04-20
 */
@RestControllerAdvice
public class ExceptionAdapter {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExceptionAdapter.class);
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(TokenExcption.class)
	public Object handleTokenException() {
		LOG.error("token is invaild.");
		return "500";
	}
	
}
