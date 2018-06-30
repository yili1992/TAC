package com.lee.tac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import static com.lee.tac.job.ExecuteJob.setApplicationContext;

@EnableAsync
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		setApplicationContext(applicationContext);

	}
}
