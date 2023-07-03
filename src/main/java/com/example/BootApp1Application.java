package com.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.util.LoggerUtil;


//(exclude=SecurityAutoConfiguration.class)
@SpringBootApplication
public class BootApp1Application {
	//main method
	public static void main(String[] args){
		//Starting
		SpringApplication.run(BootApp1Application.class, args);
		LoggerUtil.logInfo("Application Started");
		System.out.println("Application Started !!!!!!");
	}

}
