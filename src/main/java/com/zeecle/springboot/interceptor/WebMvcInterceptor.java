package com.zeecle.springboot.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 类似于MVC中<mvc:interceptor />
 * 	用于MVC初始化配置
 * @author toney
 * @date 2018-04-20
 */
@Configuration
public class WebMvcInterceptor extends WebMvcConfigurerAdapter{
	
	private static final Logger LOG = LoggerFactory.getLogger(WebMvcInterceptor.class);

	@Autowired
	RequestHandler requestHandler;
	/**
	 * 跨域请求拦截
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		LOG.info("WebMvcInterceptor.addCorsMappings()");
		super.addCorsMappings(registry);
	}

	/**
	 * 注册拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LOG.info("WebMvcInterceptor.addInterceptors()");
		registry.addInterceptor(requestHandler);
		super.addInterceptors(registry);
	}
	
	
}
