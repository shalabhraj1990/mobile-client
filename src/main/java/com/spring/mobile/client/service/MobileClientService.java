package com.spring.mobile.client.service;

import java.util.List;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.mobile.client.integration.CountryServiceIntegration;
import com.spring.mobile.client.integration.MobileAccessoryIntegration;
import com.spring.mobile.client.integration.MobileServiceIntegration;

import msk.spring.boot.common.mobile.dto.Country;
import msk.spring.boot.common.mobile.dto.MobileAccessoryDto;
import msk.spring.boot.common.mobile.dto.MobileClientDto;
import msk.spring.boot.common.mobile.dto.MobileDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class MobileClientService {
	@Autowired
	MobileServiceIntegration mobileServiceIntegration;

	@Autowired
	MobileAccessoryIntegration mobileAccessoryIntegration;

	@Autowired
	CountryServiceIntegration countryServiceIntegration;

	public Flux<MobileClientDto> getAllMobileInfo() {
		Flux<MobileDto> mobileDto = mobileServiceIntegration.getAllMobiles();

		return mobileDto.flatMap(this::getMobileDetails);
	}

	private Mono<MobileClientDto> getMobileDetails(MobileDto mobile) {
		Mono<Country> countryMono = countryServiceIntegration.getCountryInfoByCode(mobile.getCountryCode());
		Flux<MobileAccessoryDto> accessoryFlux = mobileAccessoryIntegration.getAccessoryInfo(mobile.getAccessoryType());
		// covert flux to mono
		Mono<List<MobileAccessoryDto>> accessoryMono = accessoryFlux.collectList();

		Mono<Tuple2<Country, List<MobileAccessoryDto>>> zip = Mono.zip(countryMono, accessoryMono);
		return zip.flatMap(tuple -> {
			Country country = tuple.getT1();
			List<MobileAccessoryDto> accessories = tuple.getT2();
			MobileClientDto mobileClientDto = MobileClientDto.builder().id(mobile.getId()).country(country)
					.accessoryInfo(accessories).lineOfBussiness(mobile.getLineOfBussiness()).status(mobile.getStatus())
					.name(mobile.getName()).price(mobile.getPrice()).publicationDate(mobile.getPublicationDate())
					.build();
			return Mono.just(mobileClientDto);
		});
	}

	public Mono<MobileClientDto> getAllMobileInfo(int id) {
		return mobileServiceIntegration.getMobileById(id).flatMap(this::getMobileDetails);

	}
}
