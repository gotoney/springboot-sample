package com.zeecle.springboot.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("per")
public class PermissionController {
	
	@RequestMapping(value = "")
	@RequiresPermissions("per:view")
	public String list() {
		return "permission";
	}

}
