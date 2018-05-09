package com.zeecle.springboot.interceptor;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * MethodHandlerInterceptor
 * 	实现对方法级别的拦截处理
 * @author toney
 * @date 2018-04-20
 */
@Component
@Aspect
public class RequestMapingInterceptor {
	private static final Logger LOG = LoggerFactory.getLogger(RequestMapingInterceptor.class);
	
	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method method = signature.getMethod();
		Class<?> declaringClass = method.getDeclaringClass();
		LOG.info("method invoke: " + declaringClass.getName() + "." +method.getName());
		return joinPoint.proceed();
	}
}
