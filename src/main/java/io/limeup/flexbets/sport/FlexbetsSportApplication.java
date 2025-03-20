package io.limeup.flexbets.sport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FlexbetsSportApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlexbetsSportApplication.class, args);
	}

}
