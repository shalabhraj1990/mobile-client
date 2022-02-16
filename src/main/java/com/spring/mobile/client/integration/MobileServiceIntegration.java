package com.spring.mobile.client.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import msk.spring.boot.common.dto.Response;
import msk.spring.boot.common.mobile.dto.MobileDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MobileServiceIntegration {
	@Autowired
	private WebClient mobileServiceClient;

	public Mono<MobileDto> getMobileById(int id) {
		return mobileServiceClient.get().uri("/{mobile-id}", id).accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(new ParameterizedTypeReference<Response<MobileDto>>() {
				}).flatMap(res -> Mono.just(res.getData()));

	}

	public Flux<MobileDto> getAllMobiles() {
		Mono<Response<List<MobileDto>>> mobileServiceResponse = mobileServiceClient.get()
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(new ParameterizedTypeReference<Response<List<MobileDto>>>() {
				}).log();

		return mobileServiceResponse.flatMap(response -> {
			// Response<List<MobileDto>> body = response.getBody();
			List<MobileDto> data = response.getData();
			return Mono.just(data);
		}).flatMapMany(data -> {
			return Flux.fromIterable(data);
		});

	}

}
