package com.zeecle.springboot.shiro;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zeecle.springboot.pojo.Permission;
import com.zeecle.springboot.pojo.RoleVo;
import com.zeecle.springboot.pojo.User;
import com.zeecle.springboot.pojo.UserVo;
import com.zeecle.springboot.service.UserService;

public class ShiroUserRealm extends AuthorizingRealm{
	
	private static final Logger LOG = LoggerFactory.getLogger(ShiroUserRealm.class);
	
	@Autowired
	UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		String username = principal.getPrimaryPrincipal().toString();
		LOG.info("USER IS AUTHORIZING: " + username);
		UserVo userVo = userService.queryUserPermission(username);
		Set<RoleVo> roles = userVo.getRoles();
		for (RoleVo roleVo : roles) {
			simpleAuthorizationInfo.addRole(roleVo.getRole());
			Set<Permission> permissions = roleVo.getPermissions();
			for (Permission permission : permissions) {
				simpleAuthorizationInfo.addStringPermission(permission.getPermission());
			}
		}
		return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		String username = token.getUsername();
		Object principal = token.getPrincipal();
		
		User user = userService.findByName(username);
		if (user != null) {
			ByteSource salt = ByteSource.Util.bytes(user.getSalt());
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal, user.getPassword(), salt, getName());
			return simpleAuthenticationInfo;
		}
		
		/**
		if (!username.isEmpty()) {
			ByteSource salt = ByteSource.Util.bytes(username);
			SimpleHash hashedCredentials = new SimpleHash("MD5", "sdnware", salt, 1024);
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal, hashedCredentials, salt, getName());
			return simpleAuthenticationInfo;
		}**/
		
		return null;
	}

}
