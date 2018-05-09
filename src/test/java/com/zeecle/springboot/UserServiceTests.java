package com.zeecle.springboot;

import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zeecle.springboot.pojo.User;
import com.zeecle.springboot.pojo.UserVo;
import com.zeecle.springboot.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class UserServiceTests {
	
	@Autowired
	UserService userService;
	
	@Test
	public void add() {
		User user = new User();
		user.setUsername("admin");
		user.setName("超管");
		String salt = UUID.randomUUID().toString().replaceAll("-", "");
		user.setSalt(salt);
		ByteSource bSource = ByteSource.Util.bytes(salt);
		SimpleHash shash = new SimpleHash("MD5", "sdnware", bSource, 1024);
		user.setPassword(shash.toString());
		user.setState(0);
		userService.add(user);
	}
	
	@Test
	public void update() {
		User user = new User();
		user.setId(9);
		user.setUsername("gogo");
		user.setPassword("sdnware");
		userService.update(user);
	}
	
	@Test
	public void findByName() {
		User user = userService.findByName("tanwei");
		System.out.println(user.getName());
	}
	
	@Test
	public void queryUserPermission() {
		UserVo user = userService.queryUserPermission("admin");
		System.out.println(user.getName());
	}
	
}
