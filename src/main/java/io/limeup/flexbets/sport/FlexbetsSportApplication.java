package io.limeup.flexbets.sport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
@ConfigurationPropertiesScan
public class FlexbetsSportApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlexbetsSportApplication.class, args);
	}

}
