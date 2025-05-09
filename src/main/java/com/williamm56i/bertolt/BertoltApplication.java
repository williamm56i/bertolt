package com.williamm56i.bertolt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
public class BertoltApplication {

	public static void main(String[] args) {
		SpringApplication.run(BertoltApplication.class, args);
	}

}
