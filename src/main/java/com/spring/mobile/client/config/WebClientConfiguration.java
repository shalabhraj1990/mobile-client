package com.spring.mobile.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
	@Value("${mobile-service-base-uri}")
	private String mobileServiceBaseUri;
	@Value("${country-service-base-uri}")
	private String countryServiceBaseUri;
	@Value("${mobile-accessory-base-uri}")
	private String accessoryServiceBaseUri;

	@Bean
	public WebClient mobileServiceClient() {
		return WebClient.builder().baseUrl(mobileServiceBaseUri).build();
	}

	@Bean
	public WebClient countryServiceClient() {
		return WebClient.builder().baseUrl(countryServiceBaseUri).build();
	}

	@Bean
	public WebClient accessoryServiceClient() {
		return WebClient.builder().baseUrl(accessoryServiceBaseUri).build();
	}
}
