package com.zeecle.springboot.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
	
	private static final Logger LOG = LoggerFactory.getLogger(WebSocketController.class);
	
	@MessageMapping("/chat")
	@SendTo("/topic/chatRoom")
	public String intoRoom(String message) {
		LOG.info("get client send message");
		return message;
	}
}
