package com.zeecle.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class RedisTests {
	@Autowired
	StringRedisTemplate redisTemplate;
	
	@Test
	public void connect() {
		redisTemplate.opsForValue().set("eee", "222");
	}
	
}
