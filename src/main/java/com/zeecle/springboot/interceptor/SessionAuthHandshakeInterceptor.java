package com.zeecle.springboot.interceptor;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class SessionAuthHandshakeInterceptor implements HandshakeInterceptor {
	
	private static final Logger LOG = LoggerFactory.getLogger(SessionAuthHandshakeInterceptor.class);

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Exception exception) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler,
			Map<String, Object> attributes) throws Exception {
		LOG.info("websocket's interceptor start:");
		HttpSession session = this.getSession(request);
		Object username = session.getAttribute("username");
		if ( session == null || username == null ) {
			return false;
		}
		LOG.info("websocket's session:" + username);
		attributes.put("username",username);
        return true;
	}
	
	private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession(false);
        }
        return null;
    }

}
