package com.zeecle.springboot.mapper;

import org.apache.ibatis.annotations.Param;

import com.zeecle.springboot.pojo.User;
import com.zeecle.springboot.pojo.UserVo;

public interface UserMapper {
	
	void addUser(User user);
	void updateUser(User user);
	User findByName(@Param("username") String username);
	UserVo queryUserPermission(@Param("username") String username);

}
