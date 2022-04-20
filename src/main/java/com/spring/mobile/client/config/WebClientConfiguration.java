package com.spring.mobile.client.config;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
//	@Value("${mobile-service-base-uri}")
//	private String mobileServiceBaseUri;
//	@Value("${country-service-base-uri}")
//	private String countryServiceBaseUri;
//	@Value("${mobile-accessory-base-uri}")
//	private String accessoryServiceBaseUri;
	@Autowired
	DiscoveryClient discoveryClient;

	@Bean
	public WebClient mobileServiceClient() {
		ServiceInstance instance =   discoveryClient.getInstances("MOBILE-SERVICE").get(0);
		URI uri =  instance.getUri();
		return WebClient.builder().filter(basicAuthentication("mobile-user", "mobile-user"))
				.baseUrl(uri+"/msk/mobiles").build();
	}

	@Bean
	public WebClient countryServiceClient() {
		ServiceInstance instance =   discoveryClient.getInstances("COUNTRY-SERVICE").get(0);
		URI uri =  instance.getUri();
		return WebClient.builder().filter(basicAuthentication("country-service", "country-service"))
				.baseUrl(uri+"/msk/countries").build();
	}

	@Bean
	public WebClient accessoryServiceClient() {
		ServiceInstance instance =   discoveryClient.getInstances("ACCESSORY-SERVICE").get(0);
		URI uri =  instance.getUri();
		return WebClient.builder().baseUrl(uri+"/mobile-accessories").build();
	}
}
