package com.zeecle.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.zeecle.springboot.pojo.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class HelloControllerTests {
	
	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private User user;
	
	@Test
	public void hello() {
		String res = restTemplate.getForObject("/hello", String.class);
		System.out.println(res);
		System.out.println("init username:" + user);
	}

}
