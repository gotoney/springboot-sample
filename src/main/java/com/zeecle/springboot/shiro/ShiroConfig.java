package com.zeecle.springboot.shiro;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.zeecle.springboot.shiro.filter.LoginFilter;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;

@Configuration
public class ShiroConfig {
	
	
	/**
	 * shiro初始化装配
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403"); // 只针对授权过滤器生效
		
		Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
		filters.put("login", new LoginFilter());
		shiroFilterFactoryBean.setFilters(filters);
		
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		return shiroFilterFactoryBean;
	}
	
	/**
	 * 安全管理器
	 * @param realm
	 * @param sessionManager
	 * @return
	 */
	@Bean
	public SecurityManager securityManager(ShiroUserRealm realm, ShrioSessionManager sessionManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm);
		securityManager.setSessionManager(sessionManager);
		return securityManager;
	}
	
	/**
	 * 用户认证、授权实现
	 * @param credentialsMatcher
	 * @return
	 */
	@Bean
	public ShiroUserRealm realm(HashedCredentialsMatcher credentialsMatcher) {
		ShiroUserRealm realm = new ShiroUserRealm();
		realm.setCredentialsMatcher(credentialsMatcher);
		return realm;
	}
	
	/**
	 * 信息加密算法
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher credentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(1024);
		return credentialsMatcher;
	}
	
	/**
	 * session管理器
	 * @return
	 */
	@Bean
	public ShrioSessionManager sessionManager() {
		ShrioSessionManager sessionManager = new ShrioSessionManager();
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		sessionManager.setSessionDAO(redisSessionDAO());
		Collection<SessionListener> listeners = new LinkedList<SessionListener>();
        listeners.add(new SimpleSessionListener());
		sessionManager.setSessionListeners(listeners);
		sessionManager.setSessionIdCookie(simpleCookie());
		return sessionManager;
	}
	
	
	/**
	 * 注入RedisSessionDAO
	 * @return
	 */
	@Bean
	public RedisSessionDAO redisSessionDAO() {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
		return redisSessionDAO;
	}
	
	/**
	 * sessionID生成器
	 * @return
	 */
	@Bean
	public JavaUuidSessionIdGenerator sessionIdGenerator() {
		return new JavaUuidSessionIdGenerator();
	}
	
	/**
	 * 自定义cookie模板
	 * @return
	 */
	@Bean
	public SimpleCookie simpleCookie() {
		SimpleCookie simpleCookie = new SimpleCookie();
		simpleCookie.setName("zeecle-session");
		simpleCookie.setHttpOnly(true);
		return simpleCookie;
	}
	
	/**
	 * shiro bean 生命周期管理
	 * @return
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
	/**
	 * 动态代理配置
	 * DependsOn:
	 * 	先初始化lifecycleBeanPostProcessor，再进行初始化当前Bean
	 * @return
	 */
	@Bean
	@DependsOn({"lifecycleBeanPostProcessor"})
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}
	
	/**
	 * 提供Authorization注解支持
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
	
	/**
	 * 简单异常处理（可使用统一异常处理）
	 * 	这里主要针对UnauthorizedException处理
	 * @return
	 */
	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
		Properties mappings = new Properties();
		mappings.setProperty("UnauthorizedException", "redirect:/403");
		simpleMappingExceptionResolver.setExceptionMappings(mappings);
		return simpleMappingExceptionResolver;
	}
	
	/**
	 * 提供thymeleaf的shiro标签
	 * @return
	 */
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

}
