package com.zeecle.springboot.websocket;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.zeecle.springboot.interceptor.SessionAuthHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{
	
	private static final Logger LOG = LoggerFactory.getLogger(WebSocketConfig.class);
	private static CopyOnWriteArraySet<String> usernameSet = new CopyOnWriteArraySet<>();

	/**
	 * 注册服务，并设置允许跨域访问
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// TODO Auto-generated method stub
		registry.addEndpoint("/webSocketServer")
			.addInterceptors(new SessionAuthHandshakeInterceptor())
			.setHandshakeHandler(new DefaultHandshakeHandler() {
				@Override
				protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
						Map<String, Object> attributes) {
					String username = (String)attributes.get("username");
					return new SimplePrincipal(username);
				}
				
			})
			.setAllowedOrigins("*").withSockJS();
	}
	

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// TODO Auto-generated method stub
		registry.enableSimpleBroker("/topic");
		registry.setApplicationDestinationPrefixes("/app");
	}
	
	
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new ChannelInterceptorAdapter() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
                String username = (String)accessor.getSessionAttributes().get("username");
				MessageHeaders headers = message.getHeaders();
				SimpMessageType messageType = headers.get("simpMessageType", SimpMessageType.class);
				if (messageType == SimpMessageType.SUBSCRIBE) usernameSet.add(username);
				if (messageType.equals(SimpMessageType.DISCONNECT)) usernameSet.remove(username);
				LOG.info("接受客户端消息: "+message);
                LOG.info("当前客户端用户总数: " + usernameSet.size());
				return super.preSend(message, channel);
			}
			
		});
	}


	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		registration.interceptors(new ChannelInterceptorAdapter() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				LOG.info("向客户端发送消息 : "+ message);
				return super.preSend(message, channel);
			}
			
		});
	}


	class SimplePrincipal implements Principal {
		private String username;
		public SimplePrincipal(String username) {
			this.username = username;
		}
		@Override
		public String getName() {
			return username;
		}
	}
}
