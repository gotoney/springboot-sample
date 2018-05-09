package com.zeecle.springboot.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 监听session变化情况
 * @author toney
 * @date 2018-04-23
 */
public class SimpleSessionListener implements SessionListener {
	private static final Logger LOG = LoggerFactory.getLogger(SimpleSessionListener.class);
	private static final String SHIRO_SESSION_PREFIX = "zeecle-shiro-session";
	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public void onExpiration(Session session) {
		LOG.info("session is expired: " + session.getId());
		this.redisTemplate.delete(SHIRO_SESSION_PREFIX + session.getId());
	}

	@Override
	public void onStart(Session session) {
		LOG.info("session is created: " + session.getId());
	}

	@Override
	public void onStop(Session session) {
		LOG.info("session is stop: " + session.getId());
		this.redisTemplate.delete(SHIRO_SESSION_PREFIX + session.getId());
	}

}
