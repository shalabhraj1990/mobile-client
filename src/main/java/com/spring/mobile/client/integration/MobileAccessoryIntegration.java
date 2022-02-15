package com.spring.mobile.client.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import msk.spring.boot.common.mobile.dto.MobileAccessoryDto;
import reactor.core.publisher.Flux;

@Component
public class MobileAccessoryIntegration {
	@Autowired
	private WebClient accessoryServiceClient;

	public Flux<MobileAccessoryDto> getAccessoryInfo(String mobileType) {
		return accessoryServiceClient.get().uri("find/{mobile-type}", mobileType)
				.accept(org.springframework.http.MediaType.APPLICATION_JSON).retrieve()
				.bodyToFlux(MobileAccessoryDto.class);
	}
}
