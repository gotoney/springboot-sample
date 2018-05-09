package com.zeecle.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zeecle.springboot.config.DruidSource;


@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class DruidSourceTests {
	
	@Autowired
	DruidSource druidSource;
	
	@Test
	public void get() {
		System.out.println(druidSource.getUrl());
	}
	
}
