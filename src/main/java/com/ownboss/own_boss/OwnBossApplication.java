package com.ownboss.own_boss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.data.autoconfigure.web.DataWebAutoConfiguration;

@SpringBootApplication(exclude = DataWebAutoConfiguration.class)
public class OwnBossApplication {

	public static void main(String[] args) {
		SpringApplication.run(OwnBossApplication.class, args);
	}

}
