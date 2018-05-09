package com.zeecle.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.zeecle.springboot.mapper")
public class SpringbootSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSampleApplication.class, args);
	}
}
