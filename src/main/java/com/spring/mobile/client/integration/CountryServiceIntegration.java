package com.spring.mobile.client.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import msk.spring.boot.common.dto.Response;
import msk.spring.boot.common.mobile.dto.Country;
import reactor.core.publisher.Mono;

@Component
public class CountryServiceIntegration {
	@Autowired
	WebClient countryServiceClient;

	public Mono<Country> getCountryInfoByCode(String countryCode) {

		Mono<Response<Country>> response = countryServiceClient.get().uri("/{country-code}", countryCode).retrieve()
				.bodyToMono(new ParameterizedTypeReference<Response<Country>>() {
				});
		return response.flatMap(res -> {
			return Mono.just(res.getData());
		});
	}

}
