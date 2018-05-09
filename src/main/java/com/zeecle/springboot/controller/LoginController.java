package com.zeecle.springboot.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zeecle.springboot.pojo.User;

@Controller
public class LoginController {
	
	@PostMapping(value = "login")
	public String logon(HttpServletRequest request, HttpServletResponse response, 
			User user, ModelMap modelMap) {
		String userName = user.getUsername();
		String password = user.getPassword();
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			if (subject.isAuthenticated()) {
				subject.getSession().setAttribute("username", userName);
			}
		} catch (Exception e) {
			modelMap.addAttribute("user", userName);
			modelMap.addAttribute("message", "the user info is invalid.");
			return "login";
		}
		return "redirect:/index";
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public void logout() {
		SecurityUtils.getSubject().logout();
	}
}
