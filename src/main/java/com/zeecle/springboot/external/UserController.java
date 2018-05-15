package com.zeecle.springboot.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zeecle.springboot.pojo.User;
import com.zeecle.springboot.service.UserService;

@RestController
@RequestMapping("external")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("getUserData/{username}")
	public String getUserJSON(@PathVariable String username) {
		User user = userService.findByName(username);
		return JSONObject.toJSONString(user);
	}

}
