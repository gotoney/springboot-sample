package com.zeecle.springboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeecle.springboot.dao.UserDao;
import com.zeecle.springboot.mapper.UserMapper;
import com.zeecle.springboot.pojo.User;
import com.zeecle.springboot.pojo.UserVo;
import com.zeecle.springboot.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao; // jdbcTemplate
	
	@Autowired
	private UserMapper userMapper; // mybatis

	@Transactional
	@Override
	public void add(User user) {
		userDao.add(user);
//		int i = 1/0;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Transactional
	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		userMapper.updateUser(user);
//		int i = 1/0;
	}
	
	@Override
	public User findByName(String username) {
		return userMapper.findByName(username);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserVo queryUserPermission(String username) {
		return userMapper.queryUserPermission(username);
	}

}
