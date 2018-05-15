package com.zeecle.springboot.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * shiro-session操作
 * 	集成redis操作session
 * @author toney
 * @date 2018-04-23
 */
public class RedisSessionDAO extends AbstractSessionDAO {
	private static final Logger LOG = LoggerFactory.getLogger(RedisSessionDAO.class);
	private static final String SHIRO_SESSION_PREFIX = "zeecle-shiro-session";
	private Long TIMEOUT = 1800000L;
	
	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@Override
	public void delete(Session session) {
		if(session == null || session.getId() == null){
 			LOG.error("session or session id is null");
 			return;
 		}
		this.redisTemplate.delete(getByteKey(session.getId()));
		//redisTemplate.opsForValue().getOperations().delete(session.getId().toString());
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<String> keys = redisTemplate.keys(SHIRO_SESSION_PREFIX + "*");
		Collection<Session> sessions = new LinkedList<>();
		keys.forEach( key -> {
			Session session = this.readSession(key);
			sessions.add(session);
		});
		return sessions;
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		LOG.info("update session");
		this.saveSession(session);
	}

	@Override
	protected Serializable doCreate(Session session) {
		LOG.info("create session");
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		this.saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if(sessionId == null){
 			LOG.error("session or session id is null");
 			return null;
 		}
		return (Session)this.redisTemplate.opsForValue().get(this.getByteKey(sessionId));
	}
	
	private void saveSession(Session session) throws UnknownSessionException{
 		if(session == null || session.getId() == null){
 			LOG.error("session or session id is null");
 			return;
 		}
 		String key = this.getByteKey(session.getId());
 		session.setTimeout(TIMEOUT);		
 		this.redisTemplate.opsForValue().set(key, session, TIMEOUT, TimeUnit.MILLISECONDS);
 	}
	
	private String getByteKey(Serializable sessionId){
 		String preKey = SHIRO_SESSION_PREFIX + sessionId;
		return preKey;
	}

}
