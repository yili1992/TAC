package com.lee.tac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:dubbo.xml")
@ComponentScan(basePackages = "com.lee.tac.executor")
@SpringBootApplication
public class TacExecutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacExecutorApplication.class, args);
	}
}
