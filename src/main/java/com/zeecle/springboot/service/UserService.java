package com.zeecle.springboot.service;

import java.util.List;

import com.zeecle.springboot.pojo.User;
import com.zeecle.springboot.pojo.UserVo;

public interface UserService {
	
	void add(User user);
	void delete(int id);
	void update(User user);
	User findByName(String username);
	List<User> findAll();
	UserVo queryUserPermission(String username);
}
