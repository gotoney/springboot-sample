package com.zeecle.springboot.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.zeecle.springboot.dao.UserDao;
import com.zeecle.springboot.pojo.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void add(User user) {
		String sql = "insert into t_sys_user(username, password, name, salt, state) values(?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getSalt(), user.getState());
	}

}
