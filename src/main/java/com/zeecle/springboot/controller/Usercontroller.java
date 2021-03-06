package com.zeecle.springboot.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zeecle.springboot.pojo.User;
import com.zeecle.springboot.service.UserService;
import com.zeecle.springboot.utils.AppUtil;

@Controller
@RequestMapping("user")
public class Usercontroller {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "")
	@RequiresPermissions("user:view")
	public String list(ModelMap modelMap) {
		String username = AppUtil.getCurrentUserName();
		User user = userService.findByName(username);
		modelMap.addAttribute("user", user);
		return "user";
	}

}
