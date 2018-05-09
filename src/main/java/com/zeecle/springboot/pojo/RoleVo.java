package com.zeecle.springboot.pojo;

import java.util.Set;

public class RoleVo extends Role {
	
	private Set<Permission> permissions;

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
}
