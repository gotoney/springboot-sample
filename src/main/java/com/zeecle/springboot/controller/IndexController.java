package com.zeecle.springboot.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zeecle.springboot.service.UserService;

@Controller
public class IndexController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "index")
	public String index(ModelMap modelMap) {
		String username = SecurityUtils.getSubject().getSession().getAttribute("username").toString();
		modelMap.put("username", username);
		return "index";
	}
	
	@GetMapping(value = {"/", "login"})
	public String login() {
		return "login";
	}
	
	@GetMapping(value = "403")
	public String err403() {
		return "error/403";
	}
	
	@GetMapping(value = "websocket")
	public String webSocketIndex() {
		return "websocket";
	}
}
