package com.zeecle.springboot.pojo;

import java.util.Set;

public class UserVo extends User {

	private static final long serialVersionUID = 1L;
	
	private Set<RoleVo> roles;

	public Set<RoleVo> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleVo> roles) {
		this.roles = roles;
	}
	

}
