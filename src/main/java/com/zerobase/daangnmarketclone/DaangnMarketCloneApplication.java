package com.zerobase.daangnmarketclone;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DaangnMarketCloneApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
		+ "classpath:application.yml,"
		+ "classpath:aws.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(DaangnMarketCloneApplication.class)
			.properties(APPLICATION_LOCATIONS)
			.run(args);
	}



}
